package Server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;


public class WebServer  implements Runnable
{
	private ServerController  controller;
	private Channel ch;
	private int port;

	public WebServer(int port, ServerController  controller) 
	{
		this.controller = controller;
		this.port = port;
	}

	@Override
	public void run()  
	{
		final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		final EventLoopGroup workerGroup = new NioEventLoopGroup(2);

		try 
		{	
			final ServerBootstrap b = new ServerBootstrap();

			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class) 
			.childHandler(new ChannelInitializer<SocketChannel>() 
			{ 
				@Override
				public void initChannel(SocketChannel ch) throws Exception 
				{
					ch.pipeline().addLast(new WebSocketServerInitializer());
				}
			})
			.option(ChannelOption.SO_BACKLOG, 128)          
			.childOption(ChannelOption.SO_KEEPALIVE, true); 

			ChannelFuture f;
			
			try 
			{
				f = b.bind(port).sync();	
				f.channel().closeFuture().sync();
			} 
			
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			} 

			//			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new WebSocketServerInitializer());
			//			ch = b.bind("localhost", port).sync().channel();	
			//			ch.closeFuture().sync();
		}

		finally 
		{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	private class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> 
	{
		@Override
		public void initChannel(final SocketChannel ch) throws Exception 
		{
			WebConnection webConnection = new WebConnection(controller);
			final ChannelPipeline pipeline = ch.pipeline();
			pipeline.addLast("http-request-decoder", new HttpRequestDecoder());
			pipeline.addLast("aggregator", new HttpObjectAggregator(1));
			pipeline.addLast("http-response-encoder", new HttpResponseEncoder());
			pipeline.addLast("request-handler", new WebSocketServerProtocolHandler("/websocket"));
			pipeline.addLast("handler", webConnection);
			controller.clientsOnlineSocket.add(webConnection);
		}
	}
}
package Server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class WebConnection extends SimpleChannelInboundHandler<TextWebSocketFrame> implements OnlineClient
{
	String name = "unknown";
	String room = "Global";
	ServerController controller;
	ChannelHandlerContext outPut;
	String outMessaqe = null;

	
	public WebConnection(ServerController controller)
	{
		this.controller = controller;
	}


	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) 
	{
		String inputText = textWebSocketFrame.text();
		outPut = channelHandlerContext;
		System.out.println(textWebSocketFrame.text()); // входящее сообщение
		//channelHandlerContext.writeAndFlush(new TextWebSocketFrame(textWebSocketFrame.text())); //исходящее сообщение
		controller.identificationRequestSocket(inputText, this);
	}
	
	@Override
	public void sendMessage(String message) 
	{	
		try 
		{
			outMessaqe = message;
			channelActive(outPut);
		}
		
		catch (Exception e) 
		{
			System.out.println("WebConnection 41");
			e.printStackTrace();
		}
	}


	@Override
	public void channelActive(ChannelHandlerContext channelHandlerContext) 
	{
		channelHandlerContext.writeAndFlush(new TextWebSocketFrame(outMessaqe)); 	
	}


	@Override
	public String getName() 
	{
		return name;
	}

	@Override
	public String getRoom() 
	{
		return room;
	}


	@Override
	public void setName(String name) 
	{
		this.name = name;	
	}


	@Override
	public void setRoom(String room)
	{
		this.room = room;
	}
}

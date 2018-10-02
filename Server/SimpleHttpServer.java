package Server;

import com.sun.net.httpserver.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleHttpServer
{	
	private ServerController controller;
	private String query = null;
	private byte[] bytes = null;
	private OutputStream os = null;

	public SimpleHttpServer(int port, ServerController controller) throws IOException 
	{
		this.controller = controller;
		HttpServer server = HttpServer.create();
		server.bind( new InetSocketAddress(port),0);
		HttpContext context = server.createContext("/", new Handler());
		context.setAuthenticator(new Auth());
		server.setExecutor(null);
		server.start();
	}



	private  class Handler implements HttpHandler 
	{
		@Override
		public void handle(HttpExchange exchange) throws IOException 
		{
			StringBuilder builder = new StringBuilder();  
			query = exchange.getRequestURI().toString();
			
			System.out.println(query + "       zapros");

			query = query.substring(1, query.length());

			String answer = controller.identificationRequestHttp(query);
			
			builder.append(answer);
			bytes = builder.toString().getBytes();
			exchange.sendResponseHeaders(200, bytes.length);

			os = exchange.getResponseBody();
			os.write(bytes);
			os.close(); 
		}
	}

	private class Auth extends Authenticator
	{
		@Override
		public Result authenticate(HttpExchange httpExchange)
		{
			if ("/forbidden".equals(httpExchange.getRequestURI().toString()))
				return new Failure(403);
			else
				return new Success(new HttpPrincipal("c0nst", "realm"));
		}
	}
}
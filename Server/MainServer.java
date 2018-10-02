package Server;

public class MainServer 
{
	private static int HttpServerPort = 8060;
	private static int WEBServerPort = 8070;
	private static int SocketServerPort = 8080;

	private static ServerController  controller = new ServerController();


	public static void main(String[] args) 
	{
		try
		{
			new SimpleHttpServer(HttpServerPort, controller);		
			new Thread(new SocketServer(SocketServerPort, controller)).start();
			new Thread(new WebServer(WEBServerPort, controller)).start();	
		}

		catch (Exception e) 
		{
			System.out.println("In main Class Server not started");
			e.printStackTrace();
		}

	}
}

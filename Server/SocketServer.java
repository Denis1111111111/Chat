package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketServer implements Runnable
{
	Socket clientSocket = null;
	ServerSocket serverSocket = null;
	ServerController controller = null;
	int port;

	public SocketServer(int port, ServerController controller)
	{	
		this.port = port;
		this.controller = controller;
	}

	@Override
	public void run() 
	{
		try 
		{
			serverSocket = new ServerSocket(port);

			while (true) 
			{
				clientSocket = serverSocket.accept(); 
				ClientHandler client = new ClientHandler(clientSocket, this, controller);
				controller.clientsOnlineSocket.add(client);
				new Thread(client).start();
			}
		}

		catch (IOException ex) 
		{
			ex.printStackTrace();
		}

		finally 
		{
			try 
			{
				clientSocket.close();
				serverSocket.close();
			}

			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}		
	}
}
package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ClientHandler implements Runnable, OnlineClient
{
	private ServerController controller;
	private SocketServer socketServer;
	private PrintWriter outMessage;
	private Scanner inMessage;
	Socket clientSocket = null;
	String name = "unknown";
	String room = "Global";


	public ClientHandler(Socket socket, SocketServer socketServer,  ServerController controller) 
	{
		this.controller = controller;

		try 
		{
			this.clientSocket = socket;
			this.outMessage = new PrintWriter(socket.getOutputStream());
			this.inMessage = new Scanner(socket.getInputStream());
		} 

		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public void run() 
	{
		try
		{	

			while (true) 
			{
				if (inMessage.hasNext()) 
				{
					String clientMessage = inMessage.nextLine();


					if (clientMessage.equalsIgnoreCase("##session##end##"))
					{
						break;
					}

					System.out.println(clientMessage + "   request");
					
					controller.identificationRequestSocket(clientMessage, this);

				}
				Thread.sleep(100);
			}
		}

		catch (InterruptedException ex) 
		{
			ex.printStackTrace();
		}

	}
    
	@Override
	public void sendMessage(String message) 
	{
		System.out.println(message + "    answer");
		
		try 
		{
			outMessage.println(message);
			outMessage.flush();
		} 

		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
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

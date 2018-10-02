package Client;

import java.awt.Color;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import Gson.RequestContext;


public class Client 
{
	String name = "unknown";
	String password = null;
	String room = "Global";
	ClientFunctions func = new ClientFunctions(this);
	DialogWindow dialogWindow;
	FrameClient frameClient = null;
	FrameAdmin frameAdmin = null;
	RequestContext reqTxt;
	public ArrayList<String> rooms = new ArrayList<String>();
	HashMap<Integer, Color> colorMap = new HashMap<Integer, Color>();
	static final String SERVER_HOST = "localhost";
	Socket clientSocket;
	PrintWriter outMessage;
	BufferedReader inMessage;
	

	public Client(int port) 
	{	
		try 
		{	
			clientSocket = new Socket(SERVER_HOST, port);
			outMessage = new PrintWriter(clientSocket.getOutputStream(),true);
			inMessage = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			dialogWindow = new DialogWindow( this);	
		}

		catch (IOException e) 
		{
			e.printStackTrace();
		}		

		new Thread(new Runnable() 
		{
			@Override
			public void run() 
			{
				try 
				{
					while (true) 
					{
						String serverWord = inMessage.readLine();
						if (serverWord != null) 
						{
							String inMes = serverWord;

							func.identificateMessage(inMes);
						}
					}
				}

				catch (Exception e) 
				{
                e.printStackTrace();
				}
			}
		}).start();
	}
}
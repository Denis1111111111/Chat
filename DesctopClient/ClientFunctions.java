package Client;

import java.awt.Color;
import java.util.ArrayList;
import Gson.AnswerContext;
import Gson.ConverterJson;
import Gson.RequestContext;

public class ClientFunctions
{
	private ConverterJson converter = new ConverterJson();
	Client client;
	RequestContext reqTxt;
	
	public ClientFunctions(Client client)
	{
		this.client = client;
	}

	public void sendMessage(String message)
	{	
		RequestContext reqTxt = new RequestContext(client.name, client.password, client.room, "sendMessage", client.name + ": " + message);
		String json = converter.toGsonClient(reqTxt);

		sendingMessage(json);

		if(client.name.equals("Admin"))
		{
			client.frameAdmin.jtfMessage.setText("");
		}

		else
		{
			client.frameClient.jtfMessage.setText("");
		}	
	}

	public void CancelAutorize()
	{	
		RequestContext reqTxt = new RequestContext(client.name, client.password, client.room, "CancelAutorize", "");
		String json = converter.toGsonClient(reqTxt);

		sendingMessage(json);
	}

	public void setNameAndPassword()
	{	
		RequestContext reqTxt = new RequestContext(client.name, client.password, client.room, "setNameAndPassword", "");
		String json = converter.toGsonClient(reqTxt);

		sendingMessage(json);
	}


	public void SignIn()
	{
		RequestContext reqTxt = new RequestContext(client.name, client.password, client.room, "SignIn", "");
		String json = converter.toGsonClient(reqTxt);

		sendingMessage(json);
	}

	public void close(String autorize)
	{
		if(autorize.equals("autorize"))
		{
			RequestContext reqTxt = new RequestContext(client.name, client.password, client.room, "close", client.name + " offline");
			String json = converter.toGsonClient(reqTxt);
			sendingMessage(json);
			return;
		}
		RequestContext reqTxt = new RequestContext(client.name, client.password, client.room, "close", "Uknown person left the dialog");
		String json = converter.toGsonClient(reqTxt);

		sendingMessage(json);
	}

	public void createDialog(String nameDialog)
	{
		reqTxt = new RequestContext(client.name, client.password, client.room, "CreateDialog", nameDialog);
		String json = converter.toGsonClient(reqTxt);

		sendingMessage(json);
	}

	public void findDialog(String nameDialog)
	{
		reqTxt = new RequestContext(client.name, client.password, client.room, "FindDialog", nameDialog);
		String json = converter.toGsonClient(reqTxt);

		sendingMessage(json);
	}

	public void getMessages(String nameDialog)
	{
		reqTxt = new RequestContext(client.name, client.password, client.room, "getMessages", nameDialog);
		String json = converter.toGsonClient(reqTxt);

		sendingMessage(json);
	}

	public void CreatePrivateDialog(String nameDialog)
	{
		reqTxt = new RequestContext(client.name, client.password, client.room, "CreatePrivateDialog", nameDialog);
		String json = converter.toGsonClient(reqTxt);

		sendingMessage(json);
	}

	public void addPersonToDialog(String namePerson)
	{
		reqTxt = new RequestContext(client.name, client.password, client.room, "AddPersonToDialog", namePerson);
		String json = converter.toGsonClient(reqTxt);

		sendingMessage(json);
	}
	
	public void newRoom()
	{
		reqTxt = new RequestContext(client.name, client.password, client.room, "newRoom", client.room);
		String json = converter.toGsonClient(reqTxt);

		sendingMessage(json);
	}
	
	 void identificateMessage(String message)
	{
		AnswerContext context = converter.fromJsonClient(message);
		
		switch(context.commandName)
		{
		
		case "SendMessage":
		{
			switch(context.commandContext)
			{
			
			case "Wrong name or password":
			{
				client.dialogWindow.text.setText("Wrong name or password");
				client.dialogWindow.setVisible(true);

				break;
			}                     

			case "Account not authorized":   
			{
				client.frameClient = new FrameClient(client);
			
				client.rooms.add(client.room);
				client.colorMap.put(0, Color.WHITE);
				client.frameClient.table.listen.fireTableDataChanged();

				getMessages(client.room);
				
				break;
			}

			case "Use a different name":   
			{
				client.dialogWindow.text.setText("Use a different name");
				client.dialogWindow.setVisible(true);		

				break;         
			}

			case "Account created":   
			{
				client.frameClient = new FrameClient(client);
				showMessage("Account created");
				getMessages(client.room);
				
				reqTxt = new RequestContext(client.name, client.password, client.room, "AllDialog", "");
				String json = converter.toGsonClient(reqTxt);
				sendingMessage(json);

				break;      
			}

			case "Account authorized":   
			{
				if(client.name.equals("Admin")) 
				{
					client.frameAdmin = new FrameAdmin(client);
					showMessage("Hello Administrator!");
					getMessages(client.room);
					
					reqTxt = new RequestContext(client.name, client.password, client.room, "AllDialog", "");
					String json = converter.toGsonClient(reqTxt);
					sendingMessage(json);
					break; 
				}

				client.frameClient = new FrameClient(client);
				showMessage("Account authorized");
				getMessages(client.room);

				reqTxt = new RequestContext(client.name, client.password, client.room, "AllDialog", "");
				String json = converter.toGsonClient(reqTxt);
				sendingMessage(json);
				break;      
			}

			case "Room Created":   
			{
				client.room = reqTxt.commandContext;

				if(client.name.equals("Admin"))
				{
					client.frameAdmin.room.setText("Room: " + reqTxt.commandContext);
					client.frameAdmin.jtaTextAreaMessage.setText("");
				}

				else
				{
					client.frameClient.room.setText("Room: " + reqTxt.commandContext);
					client.frameClient.jtaTextAreaMessage.setText("");	
				}

				showMessage("Room Created");
				sendMessage("online");

				break;      
			}                

			case "Find room":   
			{
				client.room = reqTxt.commandContext;

				if(client.name.equals("Admin"))
				{
					client.frameAdmin.room.setText("Room: " + reqTxt.commandContext);
					client.frameAdmin.jtaTextAreaMessage.setText("");
				}

				else
				{
					client.frameClient.room.setText("Room: " + reqTxt.commandContext);
					client.frameClient.jtaTextAreaMessage.setText("");	
				}

				getMessages(reqTxt.commandContext);
				sendMessage("online");


				break;      
			}
                                            
			case "Access open":   
			{
				showMessage("Access open for " + reqTxt.commandContext);
				break;      
			}
			
			default:
			{
				showMessage(context.commandContext);				
			}
			
			}
			break;     
		}
		
		case "AllDialog":   
		{
			client.rooms = context.rooms;
			
			for(int i = 0; i < client.rooms.size(); i++)
			{
				client.colorMap.put(i, Color.WHITE);
			}
			
			if(client.name.equals("Admin"))
			{
				client.frameAdmin.table.listen.fireTableDataChanged();
				break;
			}
			
			client.frameClient.table.listen.fireTableDataChanged();
			
			break;
		}  
		
		case "AddNewDialog":   
		{
			addRoom(context.commandContext);
			
			if(client.name.equals("Admin"))
			{
				client.frameAdmin.table.listen.fireTableDataChanged();
				break;
			}
			
			client.frameClient.table.listen.fireTableDataChanged();

			break;
		}
		
		case "SendMessageInCloseRoom":   
		{	
			client.colorMap.put(((Integer)client.rooms.indexOf(context.room)), Color.RED);
			
			if(client.name.equals("Admin"))
			{
				client.frameAdmin.table.listen.fireTableDataChanged();
				break;
			}
			
			client.frameClient.table.listen.fireTableDataChanged();
			break;
		}

		}
	}

	private void showMessage(String message)
	{
		if(client.name.equals("Admin"))
		{
			client.frameAdmin.jtaTextAreaMessage.append(message + "\n");
			return;
		}
		client.frameClient.jtaTextAreaMessage.append(message + "\n");
	}


	public void sendingMessage(String message)
	{
		client.outMessage.println(message);
	}
	
	public void addRoom(String nameRoom)
	{
		ArrayList<String> tmp = new ArrayList<String>();
		tmp.add(nameRoom);

		for(int i = 0; i < client.rooms.size(); i++)
		{
			tmp.add(client.rooms.get(i));
			client.colorMap.put(i + 1, client.colorMap.get(i));
		}
		
		client.rooms = tmp;
		
		if(client.room.equals(nameRoom))
		{
			client.colorMap.put(client.rooms.indexOf(nameRoom), Color.WHITE);
		}
		
		else
		{
			client.colorMap.put(client.rooms.indexOf(nameRoom), Color.RED);
		}
	}
}

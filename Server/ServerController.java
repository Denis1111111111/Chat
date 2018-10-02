package Server;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import Gson.AnswerContext;
import Gson.ConverterJson;
import Gson.RequestContext;

public class ServerController 
{
	ReadFiles readfile = new ReadFiles();
	private ConverterJson converter = new ConverterJson();
	String jsonn;
	CopyOnWriteArrayList<OnlineClient> clientsOnlineSocket = new CopyOnWriteArrayList<OnlineClient>();
	ConcurrentHashMap<String, String> listofClients = new ConcurrentHashMap<String, String>();
	ConcurrentHashMap<String, Room> rooms = new ConcurrentHashMap<String, Room>();
	CopyOnWriteArrayList<String> clientsBanned = new CopyOnWriteArrayList<String>();


	public ServerController()
	{
		listofClients.put("Admin", "123");
		listofClients.put("unknown", "0000");
		createRoom("Global", "Admin", null, null);	
	}

	public String identificationRequestHttp(String request) throws UnsupportedEncodingException
	{
		if(request.equals(""))
		{
			return readfile.readFile("Index.html");
		}

		if(!isApi(request))
		{
			return readfile.readFile(request);		
		}

		else
		{
			RequestContext context = null;

			try 
			{
				request = URLDecoder.decode( request, "UTF-8" );
				String query = request.substring(4, request.length());
				context =  converter.fromJsonServer(query);
			} 

			catch (UnsupportedEncodingException e) 
			{
				e.printStackTrace();
			}

			switch(context.commandName)
			{

			case "CancelAutorize":
			{
				jsonn = converter.toGsonServer(new AnswerContext(context.room, "SendMessage", "unknown: online", null, null));

				sendMessageToAllClients(jsonn);

				return  converter.toGsonServer(new AnswerContext(context.room, "SendMessage", "Account not authorized", null, null))  ;
			}

			case "getMessages":
			{
				Room room = rooms.get(context.room);

				if(room != null)
				{	
					return	converter.toGsonServer(new AnswerContext(context.room, "SendMessage", room.getMessages(), null, null));
				}
				break;
			}  

			case "setNameAndPassword":
			{
				if(listofClients.containsKey(context.name))
				{
					return converter.toGsonServer(new AnswerContext(context.room, "SendMessage", "Use a different name", null, null));
				}

				else
				{
					listofClients.put(context.name, context.password);
					return converter.toGsonServer(new AnswerContext(context.room, "SendMessage", "Account created", null, null));

				}
			}

			case "SignIn":
			{	
				if(listofClients.containsKey(context.name) && listofClients.get(context.name).equals(context.password) )
				{
					jsonn = converter.toGsonServer(new AnswerContext(context.room, "SendMessage", context.name +  " online", null, null));

					sendMessageToAllClientsInRoom(jsonn, context.room);

					return converter.toGsonServer(new AnswerContext(context.room, "SendMessage", "Account authorized", null, null));
				}

				else
				{
					return converter.toGsonServer(new AnswerContext(context.room, "SendMessage", "Wrong name or password", null, null));
				}	
			}

			case "AllDialog":
			{
				ArrayList<String> rooms = new ArrayList<String>();

				for(Room r: this.rooms.values())
				{
					if(r.getlevelOpenes().equals("all") || r.getAdminOfRoom().equals(context.name) || r.getWhoHasAccess().contains(context.name) )
					{
						rooms.add(r.getNameRoom());
					} 
				}	

				String [] arr = new String [rooms.size()];
				int size = rooms.size();
				
				for(int i = 0; i < size; i++)
				{
					arr[i] = rooms.get(i);
				}

				return converter.toGsonServer(new AnswerContext(context.room, "AllDialog", "", rooms, arr));
			}
			}
		}

		return request;
	}




	public String identificationRequestSocket(String json, OnlineClient mover)
	{
		RequestContext context;

		try 
		{
			context = converter.fromJsonServer(json);
		} 

		catch (Exception e) 
		{
			return "";
		}


		switch(context.commandName)    
		{

		case "setName":
		{
			mover.setName(context.name);
			break;
		}

		case "CancelAutorize":
		{
			jsonn = converter.toGsonServer(new AnswerContext(context.room, "SendMessage", "Account not authorized", null, null));
			mover.sendMessage(jsonn);

			jsonn = converter.toGsonServer(new AnswerContext(context.room, "SendMessage", "unknown: online", null, null));

			sendMessageToAllClientsThanAuthor(jsonn, context.room, mover);
			break;
		}

		case "setNameAndPassword":
		{
			mover.setName(context.name);

			if(listofClients.containsKey(mover.getName()))
			{
				jsonn = converter.toGsonServer(new AnswerContext(context.room, "SendMessage", "Use a different name", null, null));

				mover.sendMessage(jsonn);
				break;
			}

			else
			{
				listofClients.put(context.name, context.password);

				jsonn = converter.toGsonServer(new AnswerContext(context.room, "SendMessage", "Account created", null, null));

				mover.sendMessage(jsonn);

				jsonn = converter.toGsonServer(new AnswerContext(context.room, "SendMessage", context.name +  " online", null, null));
				sendMessageToAllClientsThanAuthor(jsonn, context.room, mover);
				break;
			}
		}

		case "SignIn":
		{	

			//		try 
			//		{
			//			System.out.println(new Authorize().auth(json));
			//		}
			//		catch (IOException e) 
			//		{
			//			e.printStackTrace();
			//		}

			if(listofClients.containsKey(context.name) && listofClients.get(context.name).equals(context.password) )
			{
				mover.setName(context.name);

				jsonn = converter.toGsonServer(new AnswerContext(context.room, "SendMessage", "Account authorized", null, null));

				mover.sendMessage(jsonn);

				jsonn = converter.toGsonServer(new AnswerContext(context.room, "SendMessage", context.name +  " online", null, null));

				sendMessageToAllClientsThanAuthor(jsonn, context.room, mover);
				break;
			}

			else
			{
				jsonn = converter.toGsonServer(new AnswerContext(context.room, "SendMessage", "Wrong name or password", null, null));

				mover.sendMessage(jsonn);
				break;
			}	
		}

		case "close":
		{
			jsonn = converter.toGsonServer(new AnswerContext(context.room, "SendMessage", context.commandContext, null, null));
			sendMessageToAllClientsInRoom(jsonn, context.room);
			removeClient(mover);
			break;
		}

		case "sendMessage":
		{	
			if(clientsBanned.contains(context.name) || !listofClients.containsKey(context.name))
			{		
				break;
			}

			jsonn = converter.toGsonServer(new AnswerContext(context.room, "SendMessage", context.commandContext, null, null));		
			sendMessageToAllClientsInRoom(jsonn, context.room );


			jsonn = converter.toGsonServer(new AnswerContext(mover.getRoom(), "SendMessageInCloseRoom", context.commandContext, null, null));
			sendMessageAllSubscribersRoom(jsonn, mover.getRoom());
			break;
		}

		case "FindDialog":
		{

			if(context.name.equals("unknown") || context.commandContext.equals("Enter dialog name:      ") )
			{
				break;
			}

			Room room = rooms.get(context.commandContext);

			if(room != null)
			{
				if(room.getWhoHasAccess().contains(context.name) || room.getlevelOpenes().equals("all"))
				{
					mover.setRoom(context.commandContext);

					jsonn = converter.toGsonServer(new AnswerContext(context.room, "SendMessage", "Find room", null, null));

					mover.sendMessage(jsonn);
				}
			}
			break;
		}

		case "getMessages":
		{
			Room room = rooms.get(context.commandContext);

			if(room != null)
			{	
				jsonn = converter.toGsonServer(new AnswerContext(context.room, "SendMessage", room.getMessages(), null, null));

				mover.sendMessage(jsonn);
			}
			break;
		}


		case "CreateDialog":
		{
			if(context.name.equals("unknown") || context.commandContext.equals("Enter dialog name:      ") )
			{
				break;
			}

			rooms.put(context.commandContext, new Room(context.commandContext, context.name));
			mover.setRoom(context.commandContext);

			jsonn = converter.toGsonServer(new AnswerContext(context.commandContext, "SendMessage", "Room Created", null, null));

			mover.sendMessage(jsonn);

			jsonn = converter.toGsonServer(new AnswerContext(mover.getRoom(), "AddNewDialog", mover.getRoom(), null, null));
			informAboutNewRoom(jsonn, context.commandContext);

			break;
		}

		case "CreatePrivateDialog":
		{
			if(context.name.equals("unknown") || context.commandContext.equals("Enter dialog name:      ") )
			{
				break;
			}

			rooms.put(context.commandContext, new Room(context.commandContext, context.name, "private"));
			mover.setRoom(context.commandContext);

			jsonn = converter.toGsonServer(new AnswerContext(context.room, "SendMessage", "Room Created", null, null));

			mover.sendMessage(jsonn);


			jsonn = converter.toGsonServer(new AnswerContext(mover.getRoom(), "AddNewDialog", mover.getRoom(), null, null));
			informAboutNewRoom(jsonn, context.commandContext);

			break;
		}

		case "AddPersonToDialog":
		{
			if(context.name.equals("unknown") || context.commandContext.equals("Enter dialog name:      ") )
			{
				break;
			}

			Room room = rooms.get(context.room);

			if(room != null && context.name.equals(room.getAdminOfRoom()))
			{	
				room.addAccess(context.commandContext);
			}

			String nameAcces = context.commandContext;

			jsonn = converter.toGsonServer(new AnswerContext(context.room, "SendMessage", "Access open for " + nameAcces, null, null));

			mover.sendMessage(jsonn);

			jsonn = converter.toGsonServer(new AnswerContext(mover.getRoom(), "AddNewDialog", mover.getRoom(), null, null));
			sendMessageOneClient(jsonn, nameAcces);
			break;
		}

		case "Ban":
		{
			clientsBanned.add(context.commandContext);

			jsonn = converter.toGsonServer(new AnswerContext(context.room, "SendMessage", "you banned by admin", null, null));

			sendMessageOneClient(jsonn, context.commandContext);
			break;
		}

		case "RemoveBan":
		{
			clientsBanned.remove(context.commandContext);

			jsonn = converter.toGsonServer(new AnswerContext(context.room, "SendMessage", "ban lifted", null, null));

			sendMessageOneClient(jsonn, context.commandContext);
			break;
		}

		case "RemovePerson":
		{
			jsonn = converter.toGsonServer(new AnswerContext(context.room, "SendMessage", "youre account remove by admin", null, null));

			sendMessageOneClient(jsonn, context.commandContext);
			listofClients.remove(context.commandContext);
			clientsBanned.remove(context.commandContext);

			for(OnlineClient cl : clientsOnlineSocket)
			{
				if(cl.getName().equals(context.commandContext))
				{
					clientsOnlineSocket.remove(cl);

				}
			}

			break;
		}

		case "AllDialog":
		{
			ArrayList<String> rooms = new ArrayList<String>();

			for(Room r: this.rooms.values())
			{
				if(r.getlevelOpenes().equals("all") || r.getAdminOfRoom().equals(mover.getName()) || r.getWhoHasAccess().contains(mover.getName()) )
				{
					rooms.add(r.getNameRoom());
				} 
			}	

			jsonn = converter.toGsonServer(new AnswerContext(context.room, "AllDialog", "", rooms, null));
			sendMessageOneClient(jsonn, context.name);
			break;
		}

		case "newRoom":
		{
			mover.setRoom(context.commandContext);
			break;
		}
		}
		return "";
	}

	public void sendMessageToAllClientsInRoom(String message, String room)
	{
		AnswerContext context = converter.fromJsonClient(message);

		rooms.get(room).putMessage(context.commandContext);

		for (OnlineClient o : clientsOnlineSocket) 
		{	
			if(o.getRoom().equals(room))
			{
				o.sendMessage(message);
			} 	
		}
	}

	public void sendMessageToAllClientsThanAuthor(String message, String room, OnlineClient mover)
	{
		AnswerContext context = converter.fromJsonClient(message);

		rooms.get(room).putMessage(context.commandContext);

		for (OnlineClient o : clientsOnlineSocket) 
		{	
			if(o.getRoom().equals(room) && o != mover)
			{
				o.sendMessage(message);
			}   
		}
	}

	public void sendMessageOneClient(String message, String name)
	{
		for( OnlineClient cl : clientsOnlineSocket)
		{
			if(cl.getName().equals(name))
			{
				cl.sendMessage(message);
			}
		}
	}

	public void removeClient(OnlineClient client)
	{
		clientsOnlineSocket.remove(client);
	}

	public void createRoom(String nameRoom, String adminRoom, String levelOpenes, String toPerson)
	{  	
		if(levelOpenes == null)
		{
			Room room =  new Room( nameRoom,  adminRoom);	
			rooms.put(nameRoom, room);
			return;
		}

		Room room =  new Room( nameRoom,  adminRoom,  levelOpenes);	
		rooms.put(nameRoom, room);
	}

	public void informAboutNewRoom(String json, String room )
	{
		for (OnlineClient o : clientsOnlineSocket) 
		{	
			if(rooms.get(room).getWhoHasAccess().contains(o.getName()) || rooms.get(room).getlevelOpenes().equals("all") || rooms.get(room).getAdminOfRoom().equals(o.getName()) )
			{
				o.sendMessage(json);
			} 	
		}
	}

	public void sendMessageToAllClients(String message)
	{
		for (OnlineClient o : clientsOnlineSocket) 
		{	     	
			o.sendMessage(message);	
		}
	}

	public void sendMessageAllSubscribersRoom(String message, String roomName)
	{
		for (OnlineClient o : clientsOnlineSocket) 
		{		
			for(Room r : rooms.values())
			{
				if(r.getlevelOpenes().equals("all") && !o.getRoom().equals(roomName))
				{
					o.sendMessage(message);
				}

				for(String name : r.getWhoHasAccess())
				{
					if(name.equals(o.getName()) && !o.getRoom().equals(roomName))
					{
						o.sendMessage(message);
					}
				}
			}
		}
	}
	
	private  boolean isApi(String query)
	{
		if(query.substring(0, 3).equals("api") || query.substring(0, 3).equals("API"))
		{
			return true;
		}

		return false;
	}
}

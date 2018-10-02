package Server;

import java.util.concurrent.CopyOnWriteArrayList;

public class Room
{
	private String nameRoom = null;
	private String adminOfRoom = null;
	private CopyOnWriteArrayList<String> whoHasAccess = new CopyOnWriteArrayList<String>();
	private StringBuffer messages = new StringBuffer("");
	private volatile String levelOpenes = "all";

	public Room(String nameRoom, String adminOfRoom, String levelOpenes)
	{
		whoHasAccess.add(adminOfRoom);
		whoHasAccess.add("Admin");
		this.nameRoom = nameRoom;
		whoHasAccess.add(adminOfRoom);
		this.adminOfRoom = adminOfRoom;
		this.levelOpenes = levelOpenes;
	}

	public Room(String nameRoom, String adminOfRoom)
	{
		whoHasAccess.add(adminOfRoom);
		whoHasAccess.add("Admin");
		this.nameRoom = nameRoom;
		whoHasAccess.add(adminOfRoom);
		this.adminOfRoom = adminOfRoom;
	}

	public String getNameRoom()
	{
		return nameRoom;
	}

	public String getAdminOfRoom()
	{
		return adminOfRoom;
	}

	public CopyOnWriteArrayList<String> getWhoHasAccess()
	{
		return whoHasAccess;
	}

	public String getlevelOpenes()
	{
		return levelOpenes;
	}

	public String getMessages()
	{
		return messages.toString();
	}

	public void putMessage(String message)
	{
		messages.append("\n").append(message);
	}

	public void addAccess(String name)
	{
		whoHasAccess.add(name);
	}
}
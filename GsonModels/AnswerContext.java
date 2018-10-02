package Gson;

import java.util.ArrayList;

public class AnswerContext 
{
	public	String  room;
	public	String  commandName;
	public	String  commandContext;
	public ArrayList<String> rooms = new ArrayList<String>();
	public String [] arr;
	
	public AnswerContext(String room, String commandName, String commandContext, ArrayList<String> rooms, String [] arr )
	{
		this.room = room;
		this.commandName = commandName;
		this.commandContext = commandContext;
		this.rooms = rooms;
		this.arr = arr;
	}
}

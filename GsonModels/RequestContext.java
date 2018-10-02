package Gson;
public class RequestContext 
{
	public	String  name;
	public	String  password;
	public	String  room;
	public	String  commandName;
	public	String  commandContext;
	
	public RequestContext(String name, String password, String room, String commandName, String commandContext)
	{
		this.name = name;
		this.password = password;
		this.room = room;
		this.commandName = commandName;
		this.commandContext = commandContext;
	}
}

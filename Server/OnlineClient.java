package Server;

public interface OnlineClient 
{
	public void sendMessage(String message);
	public String getName();
	public String getRoom();
	public void setName(String name);
	public void setRoom(String room);
}

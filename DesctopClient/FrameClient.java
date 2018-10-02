package Client;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;



public class FrameClient extends JFrame 
{
	JTextField jtfMessage;
	JTextField jtfCreateRoom;
	JTextField jtfNameDialog;
	JTextField jtfSetName;
	TableRooms table;
	//TablePersons tablePers;
	
	JTextArea jtaTextAreaMessage;
	JLabel room;
	CommandClient command = null;

	Client client;
	volatile DialogWindow dialogWindow;

	public FrameClient(Client client)
	{
		this.client = client;	
		this.command = new CommandClient(client, null, this);
		
		
		setBounds(300, 150, 900, 600);
		setTitle("Client");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jtaTextAreaMessage = new JTextArea();
		jtaTextAreaMessage.setEditable(false);
		jtaTextAreaMessage.setLineWrap(true);
		JScrollPane jsp = new JScrollPane(jtaTextAreaMessage);
		add(jsp, BorderLayout.CENTER);

		room = new JLabel("Room: ");
		room.setText("Room: " + client.room);
		add(room, BorderLayout.NORTH);


		JPanel bottomPanelRight = new JPanel(new BorderLayout());
		add(bottomPanelRight, BorderLayout.EAST);
		
	    table = new TableRooms(client);
	    bottomPanelRight.add(table, BorderLayout.CENTER);
		
		
//		tablePers = new TablePersons(client);
//		bb.add(tablePers);
		
		JPanel findRoomAndCreateRoom = new JPanel(new BorderLayout());
		bottomPanelRight.add(findRoomAndCreateRoom, BorderLayout.SOUTH);

		//create Room
		JPanel createRoom = new JPanel(new BorderLayout());
		findRoomAndCreateRoom.add(createRoom, BorderLayout.CENTER);

	    jtfCreateRoom = new JTextField("Enter dialog name:      ");
	    jtfCreateRoom.setName("CreateRoom");
	    jtfCreateRoom.addFocusListener(command);
		createRoom.add(jtfCreateRoom, BorderLayout.WEST);
		JButton jbSendCreateRoom = new JButton("Create dialog");
		jbSendCreateRoom.setActionCommand("CreateDialog");
		jbSendCreateRoom.addActionListener(command);
		createRoom.add(jbSendCreateRoom);

		JPanel createDialogAndAddToChat = new JPanel(new BorderLayout());
		findRoomAndCreateRoom.add(createDialogAndAddToChat, BorderLayout.SOUTH);


		JPanel createDialog = new JPanel(new BorderLayout());
		createDialogAndAddToChat.add(createDialog, BorderLayout.NORTH);

	    jtfNameDialog = new JTextField("Enter dialog name:      ");
	    jtfNameDialog.setName("CreatePrivateDialog");
	    jtfNameDialog.addFocusListener(command);
		createDialog.add(jtfNameDialog, BorderLayout.WEST);
		JButton jbSendName = new JButton("Create private dialog");
		jbSendName.setActionCommand("CreatePrivateDialog");
		jbSendName.addActionListener(command);
		createDialog.add(jbSendName);


		JPanel addToDialog = new JPanel(new BorderLayout());
		createDialogAndAddToChat.add(addToDialog, BorderLayout.SOUTH);

	    jtfSetName = new JTextField("Enter name of person: ");
	    jtfSetName.setName("AddPerson");
	    jtfSetName.addFocusListener(command);
		addToDialog.add(jtfSetName, BorderLayout.WEST);
		JButton jbAdd = new JButton("Add person to dialog  ");
		jbAdd.setActionCommand("AddToDialog");
		jbAdd.addActionListener(command);
		addToDialog.add(jbAdd);
		
				
		JPanel bottomPanel = new JPanel(new BorderLayout());
		add(bottomPanel, BorderLayout.SOUTH);
		
		JButton jbSendMessage = new JButton("Send");
		jbSendMessage.setActionCommand("SendMessage");
		jbSendMessage.addActionListener(command);
		bottomPanel.add(jbSendMessage, BorderLayout.EAST);
		
		jtfMessage = new JTextField("Enter you message: ");
	    jtfMessage.setName("message");
	    jtfMessage.addFocusListener(command);
		bottomPanel.add(jtfMessage, BorderLayout.CENTER);
		
		addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e) 
			{
				super.windowClosing(e);
				try 
				{
					if (!client.name.isEmpty() && client.name != "no name") 
					{
						client.func.close("autorize");
					}
					else
					{
						client.func.close("not autorize");
					}
					
					client.outMessage.flush();
					client.outMessage.close();
					client.inMessage.close();
					client.clientSocket.close();
				} 

				catch (IOException exc) 
				{

				}
			}
		});
		setVisible(true);
	}

	public String getClientName() 
	{
		return client.name;
	}
}
package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class FrameAdmin extends JFrame 
{
	JTextField jtfMessage;
	JTextField jtfCreateRoom;
	JTextField jtfNameDialog;
	JTextField jtfSetName;
	JTextField jtfBan;
	JTextField jtfRemoveBan;
	JTextField jtfRemovePerson;
	TableRooms table;
	
	JTextArea jtaTextAreaMessage;
	JLabel room;
	CommandAdmin commandAdmin = null;

	Client client;
	volatile DialogWindow dialogWindow;

	public FrameAdmin(Client client)
	{
		this.client = client;	
		this.commandAdmin = new CommandAdmin(client, null, this);
		

		setBounds(300, 150, 900, 600);
		setTitle("Admin");
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

		JPanel findRoomAndCreateRoom = new JPanel(new BorderLayout());
		bottomPanelRight.add(findRoomAndCreateRoom, BorderLayout.NORTH);

		JPanel findRoom = new JPanel(new BorderLayout());
		findRoomAndCreateRoom.add(findRoom, BorderLayout.NORTH);

		JPanel createRoom = new JPanel(new BorderLayout());
		findRoomAndCreateRoom.add(createRoom, BorderLayout.CENTER);

	    jtfCreateRoom = new JTextField("Enter dialog name:      ");
	    jtfCreateRoom.setName("CreateRoom");
	    jtfCreateRoom.addFocusListener(commandAdmin);
		createRoom.add(jtfCreateRoom, BorderLayout.WEST);
		JButton jbSendCreateRoom = new JButton("Create dialog");
		jbSendCreateRoom.setActionCommand("CreateDialog");
		jbSendCreateRoom.addActionListener(commandAdmin);
		createRoom.add(jbSendCreateRoom);

		JPanel createDialogAndAddToChat = new JPanel(new BorderLayout());
		findRoomAndCreateRoom.add(createDialogAndAddToChat, BorderLayout.SOUTH);


		JPanel createDialog = new JPanel(new BorderLayout());
		createDialogAndAddToChat.add(createDialog, BorderLayout.NORTH);

	    jtfNameDialog = new JTextField("Enter dialog name:      ");
	    jtfNameDialog.setName("CreatePrivateDialog");
	    jtfNameDialog.addFocusListener(commandAdmin);
		createDialog.add(jtfNameDialog, BorderLayout.WEST);
		JButton jbSendName = new JButton("Create private dialog");
		jbSendName.setActionCommand("CreatePrivateDialog");
		jbSendName.addActionListener(commandAdmin);
		createDialog.add(jbSendName);


		JPanel addToDialog = new JPanel(new BorderLayout());
		createDialogAndAddToChat.add(addToDialog, BorderLayout.SOUTH);

	    jtfSetName = new JTextField("Enter name of person: ");
	    jtfSetName.setName("AddPerson");
	    jtfSetName.addFocusListener(commandAdmin);
		addToDialog.add(jtfSetName, BorderLayout.WEST);
		JButton jbAdd = new JButton("Add person to dialog  ");
		jbAdd.setActionCommand("AddToDialog");
		jbAdd.addActionListener(commandAdmin);
		addToDialog.add(jbAdd);
		
				
		JPanel bottomPanel = new JPanel(new BorderLayout());
		add(bottomPanel, BorderLayout.SOUTH);
		
		JButton jbSendMessage = new JButton("Send");
		jbSendMessage.setActionCommand("SendMessage");
		jbSendMessage.addActionListener(commandAdmin);
		bottomPanel.add(jbSendMessage, BorderLayout.EAST);
		
		jtfMessage = new JTextField("Enter you message: ");
	    jtfMessage.setName("message");
	    jtfMessage.addFocusListener(commandAdmin);
		bottomPanel.add(jtfMessage, BorderLayout.CENTER);
		
		JPanel bottomPanelRightAdmin = new JPanel(new BorderLayout());
		bottomPanelRight.add(bottomPanelRightAdmin, BorderLayout.SOUTH);
		
		JPanel panelBan = new JPanel(new BorderLayout());
		bottomPanelRightAdmin.add(panelBan, BorderLayout.NORTH);

		
	    jtfBan = new JTextField("Enter name of person:      ");
	    jtfBan.setName("Ban");
	    jtfBan.addFocusListener(commandAdmin);
	    panelBan.add(jtfBan, BorderLayout.WEST);
	    
		JButton jbBan = new JButton("Ban");
		jbBan.setActionCommand("Ban");
		jbBan.addActionListener(commandAdmin);
		panelBan.add(jbBan);	
		
		JPanel panelRemoveBan = new JPanel(new BorderLayout());
		bottomPanelRightAdmin.add(panelRemoveBan, BorderLayout.CENTER);
		
		jtfRemoveBan = new JTextField("Enter name of person:      ");
		jtfRemoveBan.setName("Remove ban");
		jtfRemoveBan.addFocusListener(commandAdmin);
		panelRemoveBan.add(jtfRemoveBan, BorderLayout.WEST);
	    
		JButton jbRemoveBan = new JButton("Remove ban");
		jbRemoveBan.setActionCommand("RemoveBan");
		jbRemoveBan.addActionListener(commandAdmin);
		panelRemoveBan.add(jbRemoveBan);
		
		JPanel panelRemovePerson = new JPanel(new BorderLayout());
		bottomPanelRightAdmin.add(panelRemovePerson, BorderLayout.SOUTH);
		
		jtfRemovePerson = new JTextField("Enter name of person:      ");
		jtfRemovePerson.setName("Remove person");
		jtfRemovePerson.addFocusListener(commandAdmin);
		panelRemovePerson.add(jtfRemovePerson, BorderLayout.WEST);
	    
		JButton jbRemovePerson = new JButton("Remove person");
		jbRemovePerson.setActionCommand("RemovePerson");
		jbRemovePerson.addActionListener(commandAdmin);
		panelRemovePerson.add(jbRemovePerson);

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
	
	public class TableRooms extends JPanel
	{
		public  JTable tb;
		Listen listen = null;
		Client client = null;
		
		public TableRooms(Client client)
		{
			setLayout(null);
			this.client = client;
			listen = new Listen();
			tb = new JTable(listen);
			tb.setTableHeader(null);
			tb.setShowGrid(false);
			tb.setShowVerticalLines(false);
			tb.setShowHorizontalLines(false);
			tb.setDefaultRenderer(Object.class, new Rendr());
			tb.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					if (e.getClickCount() == 1) 
					{
						int row = tb.rowAtPoint(e.getPoint()); 
						if (row > -1) 
						{
							room.setText(client.rooms.get(row));
							client.colorMap.put(row, Color.WHITE);
							client.frameAdmin.table.listen.fireTableDataChanged();
							client.room = client.rooms.get(row);
							jtaTextAreaMessage.setText("");		
							client.func.getMessages(client.rooms.get(row));
							client.func.newRoom();
						}
					}
				}
			});
			
			JScrollPane sp = new JScrollPane(tb);
			listen.addTableModelListener(tb);
			sp.setBounds(0, 0, 280, 370);
			add(sp);
		}
		
		public class Listen  extends DefaultTableModel
		{	
			@Override
			public int getColumnCount() 
			{
				return 1;
			}
			
			@Override
			public int getRowCount() 
			{
				return client.rooms.size();
			}

			@Override
			public Object getValueAt(int row, int col) 
			{
				return client.rooms.get(row);
			}
		}
		
		class Rendr extends DefaultTableCellRenderer
		{
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
					int column)
			{
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);	
				
				c.setBackground(client.colorMap.get(row));
				return c;
			}
		}
	}
}
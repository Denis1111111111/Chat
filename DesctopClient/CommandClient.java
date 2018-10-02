package Client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CommandClient implements ActionListener, FocusListener
{
	private DialogWindow dialogWindow;
	private FrameClient frameClient;
	private Client client;


	public CommandClient(Client client, DialogWindow dialogWindow, FrameClient frameClient)
	{
		this.client = client;
		this.frameClient = frameClient;
		this.dialogWindow = dialogWindow;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String event = e.getActionCommand();

		switch(event)
		{

		case "CancelAutorize":
		{
			dialogWindow.close();
			client.func.CancelAutorize();
			break;
		}

		case "SignIn":
		{
			client.name = dialogWindow.name.getText();
			client.password = dialogWindow.password.getText();
			dialogWindow.close();
			client.func.SignIn();
			break;
		}

		case "CreateAccount":    
		{
			client.name = dialogWindow.name.getText();
			client.password = dialogWindow.password.getText();
			dialogWindow.close();
			client.func.setNameAndPassword();
			break;
		}

		case "CreateDialog":
		{
         client.func.createDialog(frameClient.jtfCreateRoom.getText());
         break;
		}

		case "CreatePrivateDialog":
		{
			client.func.CreatePrivateDialog(frameClient.jtfNameDialog.getText());
			break;
		}

		case "AddToDialog":
		{
			client.func.addPersonToDialog(frameClient.jtfSetName.getText());
			break;
		}            

		case "SendMessage":
		{		
			client.func.sendMessage(frameClient.jtfMessage.getText());
			frameClient.jtfMessage.grabFocus(); 
			break;
		}
		}
	}

	@Override
	public void focusGained(FocusEvent e) 
	{
		Component component = e.getComponent();

		switch(component.getName())
		{

		case "password":
		{
			if(dialogWindow.password.getText().equals("Enter password"))
			{
				dialogWindow.password.setText("");	
			}
			
			break;
		}
		
		case "name":
		{
			if(dialogWindow.name.getText().equals("Enter name"))
			{
				dialogWindow.name.setText("");  	
			}
			          
			break;
		}
		
		case "message":
		{
			if(frameClient.jtfMessage.getText().equals("Enter you message: "))
			{
				frameClient.jtfMessage.setText("");	
			}
			
			break;
		}
		
		
		case "CreateRoom":    
		{
			if(frameClient.jtfCreateRoom.getText().equals("Enter dialog name:      "))
			{
				frameClient.jtfCreateRoom.setText("");	
			}
			
			break;
		}
		
		case "CreatePrivateDialog":       
		{
			if(frameClient.jtfNameDialog.getText().equals("Enter dialog name:      "))
			{
				frameClient.jtfNameDialog.setText("");	
			}
			
			break;
		}
		
		case "AddPerson":              
		{
			if(frameClient.jtfSetName.getText().equals("Enter name of person: "))
			{
				frameClient.jtfSetName.setText("");
			}
			
			break;
		}
	
		}

	}

	@Override
	public void focusLost(FocusEvent e)
	{
		Component component = e.getComponent();

		switch(component.getName())
		{

		case "password":
		{
			if(dialogWindow.password.getText().equals(""))
			{
				dialogWindow.password.setText("Enter password");	
			}
			
			break;
		}
		
		case "name":
		{
			if(dialogWindow.name.getText().equals(""))
			{
				dialogWindow.name.setText("Enter name"); 	
			}
			          
			break;
		}
		
		case "message":
		{
			if(frameClient.jtfMessage.getText().equals(""))
			{
				frameClient.jtfMessage.setText("Enter you message: ");	
			}
			
			break;
		}
		
		case "CreateRoom":    
		{
			if(frameClient.jtfCreateRoom.getText().equals(""))
			{
				frameClient.jtfCreateRoom.setText("Enter dialog name:      ");	
			}
			
			break;
		}
		
		case "CreatePrivateDialog":       
		{
			if(frameClient.jtfNameDialog.getText().equals(""))
			{
				frameClient.jtfNameDialog.setText("Enter dialog name:      ");	
			}
			
			break;
		}
		
		case "AddPerson":              
		{
			if(frameClient.jtfSetName.getText().equals(""))
			{
				frameClient.jtfSetName.setText("Enter name of person: ");
			}
			
			break;
		}
	
		}
	}
}

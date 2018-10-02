package Client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import Gson.ConverterJson;
import Gson.RequestContext;

public class CommandAdmin implements ActionListener, FocusListener
{
	private DialogWindow dialogWindow;
	private FrameAdmin frameAdmin;
	private Client client;
	private ConverterJson converter = new ConverterJson();
	RequestContext reqTxt;


	public CommandAdmin(Client client, DialogWindow dialogWindow, FrameAdmin frameAdmin)
	{
		this.client = client;
		this.frameAdmin = frameAdmin;
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
         client.func.createDialog(frameAdmin.jtfCreateRoom.getText());
         break;
		}

		case "CreatePrivateDialog":
		{
			client.func.CreatePrivateDialog(frameAdmin.jtfNameDialog.getText());
			break;
		}

		case "AddToDialog":
		{
			client.func.addPersonToDialog(frameAdmin.jtfSetName.getText());
			break;
		}            

		case "SendMessage":
		{		
			client.func.sendMessage(frameAdmin.jtfMessage.getText());
			frameAdmin.jtfMessage.grabFocus(); 
			break;
		}
		
		
		
		
		
		
		case "Ban":
		{	
			RequestContext reqTxt = new RequestContext(client.name, client.password, client.room, "Ban", frameAdmin.jtfBan.getText() );
			String json = converter.toGsonClient(reqTxt);

			client.func.sendingMessage(json);
			frameAdmin.jtfBan.setText("Enter name of person:      ");
			
			break;
		}
		
		case "RemoveBan":
		{		
			RequestContext reqTxt = new RequestContext(client.name, client.password, client.room, "RemoveBan", frameAdmin.jtfRemoveBan.getText() );
			String json = converter.toGsonClient(reqTxt);
			frameAdmin.jtfRemoveBan.setText("Enter name of person:      ");
			client.func.sendingMessage(json);
			
			break;
		}
		
		case "RemovePerson":
		{		
			RequestContext reqTxt = new RequestContext(client.name, client.password, client.room, "RemovePerson", frameAdmin.jtfRemovePerson.getText() );
			String json = converter.toGsonClient(reqTxt);
			frameAdmin.jtfRemovePerson.setText("Enter name of person:      ");
			client.func.sendingMessage(json);
			
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
			if(frameAdmin.jtfMessage.getText().equals("Enter you message: "))
			{
				frameAdmin.jtfMessage.setText("");	
			}
			
			break;
		}
		
		case "CreateRoom":    
		{
			if(frameAdmin.jtfCreateRoom.getText().equals("Enter dialog name:      "))
			{
				frameAdmin.jtfCreateRoom.setText("");	
			}
			
			break;
		}
		
		case "CreatePrivateDialog":       
		{
			if(frameAdmin.jtfNameDialog.getText().equals("Enter dialog name:      "))
			{
				frameAdmin.jtfNameDialog.setText("");	
			}
			
			break;
		}
		
		case "AddPerson":              
		{
			if(frameAdmin.jtfSetName.getText().equals("Enter name of person: "))
			{
				frameAdmin.jtfSetName.setText("");
			}
			
			break;
		}
		
		case "Ban":              
		{
			if(frameAdmin.jtfBan.getText().equals("Enter name of person:      "))
			{
				frameAdmin.jtfBan.setText("");
			}
			
			break;
		}
		
		case "Remove ban":              
		{
			if(frameAdmin.jtfRemoveBan.getText().equals("Enter name of person:      "))
			{
				frameAdmin.jtfRemoveBan.setText("");
			}
			
			break;
		}
		
		case "Remove person":              
		{
			if(frameAdmin.jtfRemovePerson.getText().equals("Enter name of person:      "))
			{
				frameAdmin.jtfRemovePerson.setText("");
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
			if(frameAdmin.jtfMessage.getText().equals(""))
			{
				frameAdmin.jtfMessage.setText("Enter you message: ");	
			}
			
			break;
		}
		
		case "CreateRoom":    
		{
			if(frameAdmin.jtfCreateRoom.getText().equals(""))
			{
				frameAdmin.jtfCreateRoom.setText("Enter dialog name:      ");	
			}
			
			break;
		}
		
		case "CreatePrivateDialog":       
		{
			if(frameAdmin.jtfNameDialog.getText().equals(""))
			{
				frameAdmin.jtfNameDialog.setText("Enter dialog name:      ");	
			}
			
			break;
		}
		
		case "AddPerson":              
		{
			if(frameAdmin.jtfSetName.getText().equals(""))
			{
				frameAdmin.jtfSetName.setText("Enter name of person: ");
			}
			
			break;
		}
		
		case "Ban":              
		{
			if(frameAdmin.jtfBan.getText().equals(""))
			{
				frameAdmin.jtfBan.setText("Enter name of person:      ");
			}
			
			break;
		}
		
		case "Remove ban":              
		{
			if(frameAdmin.jtfRemoveBan.getText().equals(""))
			{
				frameAdmin.jtfRemoveBan.setText("Enter name of person:      ");
			}
			
			break;
		}
		
		case "Remove person":              
		{
			if(frameAdmin.jtfRemovePerson.getText().equals(""))
			{
				frameAdmin.jtfRemovePerson.setText("Enter name of person:      ");
			}
			
			break;
		}		
		}
	}
}

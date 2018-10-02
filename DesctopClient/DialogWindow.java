package Client;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DialogWindow extends JDialog
{
	private Client client; 
	private CommandClient command;
	 JTextField password;
	 JTextField name;
	volatile JLabel text;
	

	DialogWindow( Client client)
	{      	
		this.client = client;	
		this.command = new CommandClient(client, this, null);

		setSize(400,300);
		setTitle("Autorization");
		setModalityType(ModalityType.TOOLKIT_MODAL);   
		setLayout(null);
		
	    text = new JLabel();
		text.setBounds(80, 0, 250, 80);
		text.setText("CREATE A NEW ACCOUNT OR AUTHORIZE");
		add(text);
		
	    name = new JTextField("Enter name");
		name.setBounds(40, 100, 120, 30);
		name.setName("name");
		name.addFocusListener(command);
		add(name);
		
		
	    password = new JTextField("Enter password");
		password.setBounds(230, 100, 120, 30);
		password.setName("password");
		password.addFocusListener(command);
		add(password);
		

		JButton btnSignIn = new JButton("Sign in");
		btnSignIn.setBounds(20, 200, 100, 40);
		btnSignIn.setActionCommand("SignIn");
		btnSignIn.addActionListener(command);
		btnSignIn.requestFocusInWindow();
		add(btnSignIn);	
		
		JButton btnCreate = new JButton("Create account");
		btnCreate.setBounds(125, 200, 140, 40);
		btnCreate.setActionCommand("CreateAccount");
		btnCreate.addActionListener(command);
		add(btnCreate);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(270, 200, 100, 40);
		btnCancel.setActionCommand("CancelAutorize");
		btnCancel.addActionListener(command);
		add(btnCancel);
		
		setVisible(true);
	}
	
	public void close()
	{
		DialogWindow.this.setVisible(false);
	}
}

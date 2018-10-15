import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.event.KeyEvent;



public class Welcome extends Thread{
	JFrame frame;
	JTextField usernameInput;
	JTextField groupnameInput;
	JTextField passwordInput;
	String[] info = new String[3];
	volatile Boolean finished;


	public Welcome(boolean isDone) throws InterruptedException {
		finished = isDone;
		frame = new JFrame();
		frame.setSize(800, 600);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBackground(Color.BLACK);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getGUI();
		Button welcomeButton = new Button(usernameInput, groupnameInput, passwordInput);
		frame.setVisible(true);
	}
	
	public void getGUI() {
		JLabel lblWelcomeToCchat = new JLabel("Welcome to CChat!");
		lblWelcomeToCchat.setVisible(true);
		lblWelcomeToCchat.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeToCchat.setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 30));
		lblWelcomeToCchat.setForeground(Color.YELLOW);
		lblWelcomeToCchat.setBounds(184, 16, 367, 54);
		frame.getContentPane().add(lblWelcomeToCchat);
		
		JLabel lblNewLabel_1 = new JLabel("Name of the Group Chat:");
		lblNewLabel_1.setVisible(true);
		lblNewLabel_1.setForeground(Color.YELLOW);
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 22));
		lblNewLabel_1.setBounds(41, 194, 331, 39);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password for the Group Chat:");
		lblNewLabel_2.setVisible(true);
		lblNewLabel_2.setForeground(Color.YELLOW);
		lblNewLabel_2.setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 22));
		lblNewLabel_2.setBounds(41, 287, 343, 39);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblIdentificationForGroup = new JLabel("Identification for Group Chat:");
		lblIdentificationForGroup.setVisible(true);
		lblIdentificationForGroup.setForeground(Color.YELLOW);
		lblIdentificationForGroup.setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 22));
		lblIdentificationForGroup.setBounds(41, 98, 331, 39);
		frame.getContentPane().add(lblIdentificationForGroup);
		
		usernameInput = new JTextField();
		usernameInput.setVisible(true);
		usernameInput.setFont(new Font("DejaVu Sans Condensed", Font.PLAIN, 20));
		usernameInput.setForeground(new Color(0, 0, 0));
		usernameInput.setBounds(399, 101, 257, 39);
		usernameInput.setColumns(10);
		frame.getContentPane().add(usernameInput);
		
		groupnameInput = new JTextField();
		groupnameInput.setVisible(true);
		groupnameInput.setFont(new Font("DejaVu Sans Condensed", Font.PLAIN, 20));
		groupnameInput.setForeground(Color.BLACK);
		groupnameInput.setColumns(10);
		groupnameInput.setBounds(399, 196, 257, 39);
		frame.getContentPane().add(groupnameInput);
		
		passwordInput = new JTextField();
		passwordInput.setVisible(true);
		passwordInput.setFont(new Font("DejaVu Sans Condensed", Font.PLAIN, 20));
		passwordInput.setForeground(Color.BLACK);
		passwordInput.setColumns(10);
		passwordInput.setBounds(399, 290, 257, 39);
		frame.getContentPane().add(passwordInput);

		
		JButton btnContinue = new JButton("CONTINUE");
		btnContinue.setBackground(Color.YELLOW);
		btnContinue.setForeground(Color.BLACK);
		btnContinue.setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 18));
		btnContinue.setBounds(294, 378, 139, 54);
		frame.getContentPane().add(btnContinue);
		btnContinue.addActionListener(new ActionListener() {
			private WrongInfo usernameError;
			private WrongInfo groupnameError;
			private WrongInfo passwordError;

			public void actionPerformed(ActionEvent e) {
				String[] tokens = null;	
				tokens = usernameInput.getText().split("\\s+");
				if (usernameInput.getText().isEmpty()) {
					System.out.println("made it");
					frame.dispose();
					System.out.println("made it here");
					usernameError = new WrongInfo("No username was inputed");
					//System.exit(0);
				}
				else if(tokens.length > 1) {
					WrongInfo username = new WrongInfo("You can only have a single word for your username.");
				}
				else if (groupnameInput.getText().isEmpty()) {
					frame.dispose();
					groupnameError = new WrongInfo("No group name was inputed");
					//System.exit(0);
				}
				else if (passwordInput.getText().isEmpty()) {
					frame.dispose();
					passwordError = new WrongInfo("No password was inputed");
					//System.exit(0);
				}
				else {
					info[0] = usernameInput.getText();
					info[1] = groupnameInput.getText();
					info[2] = passwordInput.getText();
					finished = true;
					frame.dispose();
				}
			}
		});
	}
	
	public String[] getInfo() {
		return info;
	}
	
	public boolean done() {
		return finished;
	}

	
	public class Button implements KeyListener{
		
		private WrongInfo usernameError;
		private WrongInfo groupnameError;
		private WrongInfo passwordError;
		private JTextField username;
		private JTextField groupname;
		private JTextField password;
		
		
		public Button(JTextField usernameInfo, JTextField groupnameInfo, JTextField passwordInfo) {
			usernameInfo.addKeyListener(this);
			groupnameInfo.addKeyListener(this);
			passwordInfo.addKeyListener(this);
			username = usernameInfo;
			groupname = groupnameInfo;
			password = passwordInfo;
		}
		
		
		public void keyTyped(java.awt.event.KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){

				String[] tokens = null;	
				tokens = username.getText().split("\\s+");
				if (username.getText().isEmpty()) {
					frame.dispose();
					System.out.println("made it here");
					usernameError = new WrongInfo("No username was inputed");
					//System.exit(0);
				}
				else if(tokens.length > 1) {
					WrongInfo username = new WrongInfo("You can only have a single word for your username.");
				}
				else if (groupname.getText().isEmpty()) {
					frame.dispose();
					groupnameError = new WrongInfo("No group name was inputed");
					//System.exit(0);
				}
				else if (password.getText().isEmpty()) {
					frame.dispose();
					passwordError = new WrongInfo("No password was inputed");
					//System.exit(0);
				}
				else {
					info[0] = username.getText();
					info[1] = groupname.getText();
					info[2] = password.getText();
					finished = true;
					frame.dispose();
				}
			
			    }
			
		}

		@Override
		public void keyReleased(java.awt.event.KeyEvent e) {
			// TODO Auto-generated method stub
		}
		
	}
}

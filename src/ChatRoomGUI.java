import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.util.Scanner;

import javax.swing.SwingConstants;

//import com.sun.glass.events.KeyEvent;
import java.awt.event.KeyEvent;

import javax.swing.JTextPane;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.Label;
import javax.swing.JButton;

public class ChatRoomGUI {
	private static JTextField textField;
	private static String chatroom;
	private static JFrame frame;
	private volatile static String messageTyped;
	private static JTextArea chat;
	private volatile static boolean left;
	
	public ChatRoomGUI(String groupname, boolean info) {
		left = info;
		chatroom = groupname;
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.getContentPane().setLayout(null);
		frame.setSize(790,670);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent e) {
		    	left = true;
		        e.getWindow().dispose();

		    }
		});
		createGUI();
		Button enterButton = new Button(textField);
		frame.setVisible(true);
	}
	
	public void createGUI() {
		JLabel label = new JLabel("");
		label.setForeground(Color.YELLOW);
		label.setBackground(Color.BLACK);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 28));
		label.setBounds(15, 25, 738, 85);
		frame.getContentPane().add(label);

		label.setText(chatroom + " Major " + "Chatroom");
		
		textField = new JTextField();
		textField.setBounds(35, 491, 697, 54);
		textField.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 20));
		frame.getContentPane().add(textField);
		
		JButton btnSend = new JButton("SEND");
		btnSend.setVerticalAlignment(SwingConstants.BOTTOM);
		btnSend.setBounds(617, 561, 115, 29);
		frame.getContentPane().add(btnSend);
		
		chat = new JTextArea();
		chat.setEditable(false);
		chat.setLineWrap(true);
		chat.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 18));
		chat.setBounds(35, 102, 697, 373);
		
		JScrollPane scrollPane = new JScrollPane(chat);
		scrollPane.setSize(697, 345);
		scrollPane.setLocation(35, 111);
		frame.getContentPane().add(scrollPane);
		
		
		btnSend.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String statement = textField.getText();
					if (statement.length() < 250 && !(statement.equals(""))) {
						messageTyped = statement;
						System.err.println("message: " + messageTyped + "\n");
						textField.setText("");
					}
					else if(statement.equals("")) {
						WrongInfo characterLimit = new WrongInfo("You didn't send any text in that message.");
					}
					else {
						int overlimit = statement.length() - 250;
						WrongInfo characterLimit = new WrongInfo("Your text had " + Integer.toString(overlimit) + " characters over the limit.");
					}
				}
			});
		
		frame.setVisible(true);
		
	}
	
	public void editMessage() {
		messageTyped = null;
	}
	
	public void addChat(String username, String message) {
		chat.append(username +": " + message + System.getProperty("line.separator"));
	}
	
	public void addUser(String username) {
		chat.append(username + System.getProperty("line.separator"));
	}
	
	public void removeUser(String username) {
		chat.append(username + System.getProperty("line.separator"));
	}
		
	public String getMessage() {
		return messageTyped;
	}
	
	public boolean left() {
		return left;
	}
	

	public class Button implements KeyListener{
		
		public Button(JTextField text) {
			text.addKeyListener(this);
		}
		
		
		public void keyTyped(java.awt.event.KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
					String statement = textField.getText();
					if (statement.length() < 250 && !(statement.equals(""))) {
						messageTyped = statement;
						System.err.println("message: " + messageTyped + "\n");
						textField.setText("");
					}
					else if(statement.equals("")) {
						WrongInfo characterLimit = new WrongInfo("You didn't send any text in that message.");
					}
					else {
						int overlimit = statement.length() - 250;
						WrongInfo characterLimit = new WrongInfo("Your text had " + Integer.toString(overlimit) + " characters over the limit.");
					}
			    }
			
		}

		@Override
		public void keyReleased(java.awt.event.KeyEvent e) {
			// TODO Auto-generated method stub
		}
		
	}
}

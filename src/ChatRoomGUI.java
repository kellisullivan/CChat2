import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.Scanner;

import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Label;
import javax.swing.JButton;

public class ChatRoomGUI {
	private static JTextField textField;
	private static String chatroom;
	private static JFrame frame;
	private volatile static String messageTyped;
	private static JTextArea chat;
	
	public ChatRoomGUI(String groupname) {
		chatroom = groupname;
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.getContentPane().setLayout(null);
		frame.setSize(790,670);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		createGUI();
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

		label.setText(chatroom);
		
		textField = new JTextField();
		textField.setBounds(35, 491, 697, 54);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnSend = new JButton("SEND");
		btnSend.setVerticalAlignment(SwingConstants.BOTTOM);
		btnSend.setBounds(617, 561, 115, 29);
		frame.getContentPane().add(btnSend);
		
		chat = new JTextArea();
		chat.setEditable(false);
		chat.setLineWrap(true);
		chat.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 12));
		chat.setBounds(35, 102, 697, 373);
		frame.getContentPane().add(chat);
		
		btnSend.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String statement = textField.getText();
					messageTyped = statement;
					System.err.println("message: " + messageTyped + "\n");
					textField.setText("");
				}
			});
		
		frame.setVisible(true);
		
	}
	
	public void editMessage() {
		messageTyped = null;
	}
	
	public void addChat(String username, String message) {
		chat.append( "<" + username + ">" + message);
	}
		
	public String getMessage() {
		return messageTyped;
	}
}

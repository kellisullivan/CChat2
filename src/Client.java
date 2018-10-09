import java.awt.EventQueue;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;

public class Client {
	
	private static String username;
	private static String groupname;
	private static String password;
	private static JTextField username_input;
	private static JTextField groupname_input;
	private static JTextField password_input;


	
//	public static void openSocket() throws IOException, UnsupportedEncodingException{
//	    Socket sock;
//	    InetAddress server_address;
//	    InetSocketAddress endpoint;
//
//	    // Setup the server side connection data
//	    server_address = InetAddress.getByName("127.0.0.1");
//	    endpoint = new InetSocketAddress(server_address, 8080);
//	    sock = new Socket();
//
//
//	    // Make the connection
//	    try {
//	    	sock.connect(endpoint);
//	    } catch(ConnectException e) {
//	        System.err.println("Cannot connect to server.");
//	        System.exit(1);
//			return;
//	    }
//	        // Success
//	        sock.close();
//	        System.exit(0);
//	}
	
	public static void builder() {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.getContentPane().setBackground(Color.BLACK);
		
		frame.setBackground(Color.BLACK);
		frame.getContentPane().setLayout(null);
		
		JLabel lblWelcomeToCchat = new JLabel("Welcome to CChat!");
		lblWelcomeToCchat.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeToCchat.setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 30));
		lblWelcomeToCchat.setForeground(Color.YELLOW);
		lblWelcomeToCchat.setBounds(184, 16, 367, 54);
		frame.getContentPane().add(lblWelcomeToCchat);
		
		JLabel lblNewLabel_1 = new JLabel("Name of the Group Chat:");
		lblNewLabel_1.setForeground(Color.YELLOW);
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 22));
		lblNewLabel_1.setBounds(41, 194, 331, 39);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password for the Group Chat:");
		lblNewLabel_2.setForeground(Color.YELLOW);
		lblNewLabel_2.setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 22));
		lblNewLabel_2.setBounds(41, 287, 343, 39);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblIdentificationForGroup = new JLabel("Identification for Group Chat:");
		lblIdentificationForGroup.setForeground(Color.YELLOW);
		lblIdentificationForGroup.setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 22));
		lblIdentificationForGroup.setBounds(41, 98, 331, 39);
		frame.getContentPane().add(lblIdentificationForGroup);
		
		username_input = new JTextField();
		username_input.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		username_input.setForeground(new Color(0, 0, 0));
		username_input.setBounds(399, 101, 257, 39);
		frame.getContentPane().add(username_input);
		username_input.setColumns(10);
		
		groupname_input = new JTextField();
		groupname_input.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		groupname_input.setForeground(Color.BLACK);
		groupname_input.setColumns(10);
		groupname_input.setBounds(399, 196, 257, 39);
		frame.getContentPane().add(groupname_input);
		
		password_input = new JTextField();
		password_input.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		password_input.setForeground(Color.BLACK);
		password_input.setColumns(10);
		password_input.setBounds(399, 290, 257, 39);
		frame.getContentPane().add(password_input);
	
		
		JButton btnContinue = new JButton("CONTINUE");
		btnContinue.setBackground(Color.YELLOW);
		btnContinue.setForeground(Color.BLACK);
		btnContinue.setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 18));
		btnContinue.setBounds(294, 378, 139, 54);
		frame.getContentPane().add(btnContinue);
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username = username_input.getText();
				groupname = groupname_input.getText();
				password = password_input.getText();
				System.out.println(username);
				System.out.println(groupname);
				System.out.println(password);
				
			}
		});
	}

	
	public static void main(String[] args) {
		
	
		builder();

		
	}
}

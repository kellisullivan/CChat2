import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import un.misc.*;


public class Client {
	
	private static String username;
	private static String groupname;
	private static String password;
	private static String initialize = "INIT ";
	private static String identification = "IDNT \n";
	private static ChatRoomGUI chatroom;


	
	public static void openCentralServerSocket() throws IOException, UnsupportedEncodingException{
	    Socket sock;
	    InetAddress server_address;
	    InetSocketAddress endpoint;

	
	    // Setup the server side connection data
	    server_address = InetAddress.getByName("127.0.0.1");
	    endpoint = new InetSocketAddress(server_address, 8080);
	    sock = new Socket();
	

	    // Make the connection
	    try {
	    	sock.connect(endpoint);
	    } catch(ConnectException e) {
	        System.err.println("Cannot connect to server.");
	        System.exit(1);
			return;
	    }
	    
		String send = initialize + username + " " + groupname + " " + password + " \n";
		sock.getOutputStream().write(send.getBytes("US-ASCII"),0,send.length());
		
		
		String[] tokens = null;		
		String message = null;
		
		InputStream stream = sock.getInputStream();
		Scanner scan = new Scanner(stream, "US-ASCII");
		while (scan.hasNextLine()) {
			message = scan.nextLine();
			System.err.println("read " + message);
			tokens = message.split("\\s+");
	    }
		if (tokens[0] == "ACPT") {
			String ipAddress = tokens[1];
			int port = Integer.parseInt(tokens[2]);
			groupRouterSocket(ipAddress, port);
		}
		else if (tokens[0] == "DENY") {
			WrongInfo error = new WrongInfo("Group name  or Password was incorrect.");
		}
		else {
			System.exit(1);
		}	
		
		sock.close();
    }
	
	
	public static void groupRouterSocket(String ipAddress, int port) throws IOException {
		
		chatroom = new ChatRoomGUI(groupname);
		
		Socket sock;
	    InetAddress server_address;
	    InetSocketAddress endpoint;

	
	    // Setup the server side connection data
	    server_address = InetAddress.getByName(ipAddress);
	    endpoint = new InetSocketAddress(server_address, port);
	    sock = new Socket();
	

	    // Make the connection
	    try {
	    	sock.connect(endpoint);
	    } catch(ConnectException e) {
	        System.err.println("Cannot connect to server.");
	        System.exit(1);
			return;
	    }
	    
		sock.getOutputStream().write(identification.getBytes("US-ASCII"),0,identification.length());
		
		
		String[] tokens = null;		
		String message = null;
		
		InputStream stream = sock.getInputStream();
		Scanner scan = new Scanner(stream, "US-ASCII");
		while (scan.hasNextLine()) {
			message = scan.nextLine();
			System.err.println("read " + message);
			tokens = message.split("\\s+");
	    }
		if (tokens[0] == "ACPT") {
			String finalIpAddress = tokens[1];
			int finalPort = Integer.parseInt(tokens[2]);
			chatServerSock(finalIpAddress, finalPort);
		}
		else if (tokens[0] == "DENY") {
			WrongInfo error = new WrongInfo("Cannot connect you to the chat room.");
		}
		else {
			System.exit(1);
		}	
		
		sock.close();

	    
	}
	
	public static void chatServerSock(String ipAddress, int port) throws IOException {
		Socket sock;
	    InetAddress server_address;
	    InetSocketAddress endpoint;

	
	    // Setup the server side connection data
	    server_address = InetAddress.getByName(ipAddress);
	    endpoint = new InetSocketAddress(server_address, port);
	    sock = new Socket();
	

	    // Make the connection
	    try {
	    	sock.connect(endpoint);
	    } catch(ConnectException e) {
	        System.err.println("Cannot connect to server.");
	        System.exit(1);
			return;
	    }		
	    
	    ReadClient read = new ReadClient(chatroom, sock);
	    WriteClient write = new WriteClient(chatroom, sock, username);
	    read.start();
	    write.start();
	    
	}
	
	



	
	public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException, IOException {
		//create a new lock to use to wait for our threads
		Lock lock = new Lock();
		
		//lock the lock before entering the GUI
		lock.lock();
		
		//create the GUI
		Welcome welcome = new Welcome(lock);
		
		//wait to get the lock once the GUI has gotten the
		//information from the user
		lock.lock();

		//get the information from the GUI
		//store it to be used to send to the Central Server
		String[] clientInfo = welcome.getInfo();
		username = clientInfo[0];
		groupname = clientInfo[1];
		password = clientInfo[2];
		
		//helpful print statements
		System.out.println("Username: " + username);
		System.out.println("Groupname: " + groupname);
		System.out.println("Password: " + password);
		
		openCentralServerSocket();
	}
}

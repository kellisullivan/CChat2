import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;


public class ChatServer extends Server {

	private byte[] rbuf;
	private int bytesRead;
	private String[] partsOfMessage;
	private String username;
	private String text;
	private String groupRouterIP = "127.0.0.1";
	private String left = "LEFT ";
	private Socket groupRouterSock;
	private InetAddress groupRouterAddress;
	private InetSocketAddress endpoint;
	private ServerSocket chatServerSock;
	private InetAddress localAddress;
	private String localIP;
	private static final String PING = "PING \n";

	public ChatServer() {

	}

	
//	private void listenForClient() {
//		try {
//			// Setup the Chat Server side connection data
//			localAddress = groupRouterSock.getLocalAddress();
//			localIP = localAddress.getHostAddress();
//			
//			// Make the server socket with a maximum queue of 12 connections
//			chatServerSock = new ServerSocket(2021, 12, localAddress);
//
//			
//			
//			// Read handle connections forever
//			while(true) {
//				// Get the next connection
//				Socket csSock = chatServerSock.accept();
//
//				// Assume read can be done in one bite
//				byte[] withClient = new byte[300];
//				csSock.getInputStream().read(withClient);
//				String helo = new String(withClient, "US-ASCII");
//
//			 //If the Client has disconnected, send Group Router the "LEFT" message
//		      if (!csSock.isConnected()) {
//		    	  left += localIP + " \n";
//		    	  csSock.getOutputStream().write(left.getBytes("US-ASCII"),0,left.length());
//		    	  csSock.close();
//		    	  chatServerSock.close();
//		      }
//			}
//			
//		}
//		catch(Exception e) {
//			System.err.println("Cannot set up a server.");
//			System.exit(1);
//			return;
//		}
//		
//	}
	
	
	
	
	
	
	// Make a new connection to a server
	private void connectToGroupRouter() {
		try{
			// Setup the server side connection data to Group Router
			groupRouterAddress = InetAddress.getByName(groupRouterIP);
			endpoint = new InetSocketAddress(groupRouterAddress, 2020);

			//Make a TCP connection 
			groupRouterSock = new Socket();

			// Make the connection
			try {
				groupRouterSock.connect(endpoint);
			} catch(ConnectException e) {
				System.err.println("Cannot connect to server.");
				System.exit(1);
				return;
			}

			//Send group router the PING message as soon as it connects
			groupRouterSock.getOutputStream().write(PING.getBytes("US-ASCII"),0,PING.length());
			ChatServerProcesses toGroupRouter = new ChatServerProcesses(this, groupRouterSock);
			toGroupRouter.start();

		}
		catch(Exception e) {
			System.err.println("Cannot connect to server.");
			System.exit(1);
			return;
		}

	}

	@Override
	public String read(Socket readSock) throws UnsupportedEncodingException, IOException {
		 //Check if Client has disconnected
		if (!readSock.isConnected()) {
	    	  left += localIP + " \n";
	    	  readSock.getOutputStream().write(left.getBytes("US-ASCII"),0,left.length());
	    	  readSock.close();
		}
		// Wait for client's request and then write the request to server socket (send to server)
		rbuf = new byte[300];
		String message;
		int rbufCounter = 0;
		boolean isNotEndOfLine = true;
		while (((bytesRead = readSock.getInputStream().read(rbuf)) > 0) && isNotEndOfLine) {  
			if(rbuf[rbufCounter] == '\n') {
				isNotEndOfLine = false;
			}
			rbufCounter++;
		}
		message = new String(rbuf, "US-ASCII");
		if (message.substring(0,3) == "FWRD") {
			System.out.println("Message to forward to Clients");
			return message;
		}

		if (message.substring(0,3) == "TEXT") {
			System.out.println("Client sent this message, must be forwarded.");
			partsOfMessage = message.split(" ");
			username = partsOfMessage[1];
			message = "FWRD "+username+ " ";
			for(int i = 2; i < partsOfMessage.length; i++) {
				message+= partsOfMessage[i]+" ";
			}
			message+= "\n";
			return message;
		}
		else {
			System.out.println("Chat Server received a message it shouldn't have.");
			return null;
		}

	}

	@Override
	public void write(Socket writeSock, String message) throws IOException {
		writeSock.getOutputStream().write(message.getBytes("US-ASCII"),0,message.length());
	}

	public static void main() {
		ChatServer chatserver = new ChatServer();
		chatserver.connectToGroupRouter();
		
	}
}

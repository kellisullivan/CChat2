import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer extends Server {
	
	private String[] tokens;
	private String username;
	private String groupRouterIP = "127.0.0.1";
	private String left = "LEFT ";
	private Socket groupRouterSock;
	private InetAddress groupRouterAddress;
	private InetSocketAddress endpoint;
	private String localIP;
	private static final String PING = "PING \n";

	public ChatServer() {

	}

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
		InputStream stream = readSock.getInputStream();
		Scanner scan = new Scanner(stream, "US-ASCII");
		String message;
		String prefix;
		while (scan.hasNextLine()) {
			message = scan.nextLine();
			System.err.println("read " + message);
			tokens = message.split("\\s+");
			prefix = tokens[0];
    		
            if (prefix.equals("FWRD")) {
            	System.err.println("Message to forward to Clients");
    			return message;
    		}
            else if (prefix.equals("TEXT")) {
    			System.err.println("Client sent this message, must be forwarded.");
    			tokens = message.split(" ");
    			username = tokens[1];
    			message = "FWRD "+username+ " ";
    			for(int i = 2; i < tokens.length; i++) {
    				message+= tokens[i]+" ";
    			}
    			message+= "\n";
    			return message;
    		}
            System.err.println("Chat server recieved a message it shouldn't have: " + message);
            return null;
    	}
		return null;
	}

	@Override
	public void write(Socket writeSock, String message) throws IOException {
		if(!message.equals(null)) {
			writeSock.getOutputStream().write(message.getBytes("US-ASCII"),0,message.length());
		}
	}

	public static void main() {
		ChatServer chatserver = new ChatServer();
		chatserver.connectToGroupRouter();
		
	}
}

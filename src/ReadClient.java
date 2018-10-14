import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class ReadClient extends Thread{

	ChatRoomGUI chatroom;
	Socket read;
	String[] tokens;
	String forward = "FRWD ";
	volatile Client client;
	
	public ReadClient(ChatRoomGUI gui, Socket sock, Client client) {
		chatroom = gui;
		read = sock;
		this.client = client;
	}
	
	public void run() {
		while(true) {
			InputStream stream = null;
			try {
				stream = read.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Scanner scan = new Scanner(stream, "US-ASCII");
			String message;
			String prefix;
			
			while (scan.hasNextLine()) {
				message = scan.nextLine();
				System.err.println("read " + message);
				tokens = message.split("\\s+");
				prefix = tokens[0];
	    		
	            if (prefix.equals("FWRD")) {
	            	String username = tokens[1];
	            	String printMessage = "";
	            	for(int i = 2; i< tokens.length; i++) {
	            		printMessage += tokens[i] + " ";
	            	}
	            	chatroom.addChat(username, printMessage);
	            	System.out.println(username + ": " + printMessage);
	    		}
	            else if(prefix.equals("HELO")) {
	            	String username = tokens[1];
	            	chatroom.addUser(username + " has joined the chat room.");
	            }
	            else if(prefix.equals("LEFT")) {
	            	String username = tokens[2];
	            	chatroom.removeUser(username + " has left the chat room.");
	            }
	            else if(prefix.equals("DEAD")) {
	            	try {
						read.close();
						client.groupRouterSocket(client.getGrIPAddress(), client.getGrPort());
					} catch (IOException e) {
						System.err.println(e);
						e.printStackTrace();
					}
	            }
	    	}
		}
	}
}
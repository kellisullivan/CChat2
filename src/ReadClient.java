import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class ReadClient extends Thread{

	ChatRoomGUI chatroom;
	Socket read;
	String[] tokens;
	String forward = "FRWD ";
	
	public ReadClient(ChatRoomGUI gui, Socket sock) {
		chatroom = gui;
		read = sock;
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
	            	String printMessage = tokens[2];
	            	System.out.println(username + ": " + printMessage);
	    		}
	    	}
		}
	}
}
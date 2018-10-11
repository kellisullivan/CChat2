import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class WriteClient extends Thread{

	ChatRoomGUI chatroom;
	Socket write;
	String username;
	String text = "TEXT ";
	
	public WriteClient(ChatRoomGUI gui, Socket sock, String username) {
		chatroom = gui;
		write = sock;
		username = this.username;
	}
	
	public void run() {
		while(true) {
			String message = chatroom.getMessage();
			if (message == null) {
				
			}
			else {
				String finalmessage = text + username + " " + message + " \n";
				byte[] rbuf = null;
				try {
					rbuf = finalmessage.getBytes("US-ASCII");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					write.getOutputStream().write(rbuf, 0, rbuf.length);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}

import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
public class ChatServerProcesses extends Thread{

	private Socket socket;
	private ChatServer chatServer;
	private volatile ArrayList<Socket> clientSocks;
	
	public ChatServerProcesses(ChatServer currChatServer,Socket mySocket, ArrayList<Socket> myClientSocks){
		chatServer = currChatServer;
		socket = mySocket;
		clientSocks = myClientSocks;
	}
	
	public void update(ArrayList<Socket> myClientSocks) {
		clientSocks = myClientSocks;
	}
	
	public void run(){
		String message;
		int counter = 0;
		try {
			while(true){
				System.err.println(counter);
				if (counter == 10) {
					//socket.close();
				}
				message = chatServer.read(socket);
				if (!message.equals("NULL \n")) {
					System.err.println("running thread");
				}
				if (message.substring(0, 4).equals("FWRD") || message.substring(0, 4).equals("LEFT") || message.substring(0, 4).equals("HELO")) {
					for(Socket clientSocket: clientSocks) {
						clientSocket.getOutputStream().write(message.getBytes("US-ASCII"),0,message.length());
	            		//chatServer.write(clientSocket, message);
	            	}
				}
				else{
					chatServer.write(socket, message);
				}
				counter ++;
			}
		} 
		catch (IOException e) {
			System.out.println("Something went wrong while threading...");
			e.printStackTrace();
			message = "DEAD \n";
			for(Socket clientSocket: clientSocks) {
				try {
					clientSocket.getOutputStream().write(message.getBytes("US-ASCII"),0,message.length());
				} catch (UnsupportedEncodingException e1) {
				} catch (IOException e1) {
				}
        	}
		}
	}
}
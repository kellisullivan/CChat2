import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;
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
		try {
			while(true){
				String message = chatServer.read(socket);
				if (!message.equals("NULL \n")) {
					System.err.println("running thread");
				}
				if (message.substring(0, 4).equals("FWRD") || message.substring(0, 4).equals("LEFT")) {
					for(Socket clientSocket: clientSocks) {
						clientSocket.getOutputStream().write(message.getBytes("US-ASCII"),0,message.length());
	            		//chatServer.write(clientSocket, message);
	            	}
				}
				else{
					chatServer.write(socket, message);
				}
			}
		} 
		catch (IOException e) {
			System.out.println("Something went wrong while threading...");
			e.printStackTrace();
		}
	}
}
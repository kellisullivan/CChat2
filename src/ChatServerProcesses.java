import java.net.Socket;
import java.io.IOException;
public class ChatServerProcesses extends Thread{

	private Socket socket;
	private ChatServer chatServer;

	public ChatServerProcesses(ChatServer currChatServer,Socket mySocket){
		chatServer = currChatServer;
		socket = mySocket;
	}
	public void run(){
		try {
			while(true){
				String message = chatServer.read(socket);
				System.err.println("GR " + message);
				chatServer.write(socket, message);
			}
		} 
		catch (IOException e) {
			System.out.println("Something went wrong while threading...");
			e.printStackTrace();
		}
	}
}
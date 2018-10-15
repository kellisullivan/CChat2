import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Server {

	// Listen for new incoming connections
	public void listenConnect(String ipAddress, int allowedConnections, int port) throws IOException {
		ServerSocket srvSock;
        InetAddress serverAddress;
        
        // Setup the server side connection data
        serverAddress = InetAddress.getByName(ipAddress);
       
        // Make the server socket with a maximum queue of 20 connections
		srvSock = new ServerSocket(port, allowedConnections, serverAddress);
		
        // Read and handle connections forever
        while(true) {
			Socket listeningSock = srvSock.accept();
			Connection conn = new Connection(this, listeningSock);
			conn.start();	
        }	
	}
	
	// Read message from socket and parse
	public abstract String read(Socket readSock) throws UnsupportedEncodingException, IOException;
	
	// Compile message and write to socket
	public abstract void write(Socket writeSock, String message) throws IOException;
	
}

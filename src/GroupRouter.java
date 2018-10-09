import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class GroupRouter extends Server {
	
	private byte[] rbuf;
	int bytesRead;
	String request;
	
	public GroupRouter() {
		rbuf = new byte[300];
	}

	@Override
	public void read(Socket readSock) throws UnsupportedEncodingException, IOException {
		// Wait for client's request and then write the request to server socket (send to server)
		while ((bytesRead = readSock.getInputStream().read(rbuf)) > 0) {                                                                                                                               
			request = new String(rbuf, "US-ASCII");
		            
		}
	}

	@Override
	public void write(Socket writeSock) throws IOException {
		writeSock.getOutputStream().write(rbuf, 0, bytesRead);
	}

	public static void main() {
		
	}

}

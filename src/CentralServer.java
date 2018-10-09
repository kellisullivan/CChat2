import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class CentralServer extends Server {
	
	int bytesRead;
	int counter;
	Boolean isEndOfLine;
	
	public CentralServer() {
	}

	@Override
	public String read(Socket readSock) throws UnsupportedEncodingException, IOException {
		byte[] rbuf = new byte[30];
		counter = 0;
		isEndOfLine = false;
		// Wait for client's request and then write the request to server socket (send to server)
		while ((bytesRead = readSock.getInputStream().read(rbuf)) > 0 && isEndOfLine == false) {     
			if (rbuf[counter] == '\n') {
				isEndOfLine = true;
			}
			counter++;
        }
		String message = new String(rbuf, "US-ASCII");
    	String[] tokens = message.split("\\s+");
    	if (tokens.length != 4) {
    		message = "DENY\n";
    	}
    	else {
    		String prefix = tokens[0];
        	String username = tokens[1];
        	String groupname = tokens[2];
        	String password = tokens[3];
            if (prefix.equals("INIT")) {
            	String gsIPAddress = "";
            	int gsPort = 0;
            	message = "ACPT " + gsIPAddress + " " + gsPort + " \n";
    		}
            else {
            	message = "DENY\n";
            }
    	}
        return message;
	}

	@Override
	public void write(Socket writeSock, String message) throws IOException {
		byte[] rbuf = new byte[30];
		for (int i = 0; i < message.length(); i++) {
			rbuf[i] = (byte) (int) message.charAt(i);
		}
		writeSock.getOutputStream().write(rbuf, 0, rbuf.length);
		// Close socket after responding
        writeSock.close();
	}

	public static void main(String[] args) throws IOException {
		CentralServer centralServer = new CentralServer();
		//IP address and port: 127.0.0.1, 8080
		String ipAddress = args[0];
		int port = Integer.parseInt(args[1]);
		centralServer.listenConnect(ipAddress, port);
	}

}

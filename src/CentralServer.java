import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Scanner;


public class CentralServer extends Server {
	
	GroupRoutersTable grTable;
	
	public CentralServer() {
		grTable = new GroupRoutersTable();
		grTable.inputFileData();
	}

	@Override
	public String read(Socket readSock) throws UnsupportedEncodingException, IOException {
		String message = null;
		String prefix;
		String groupname;
		String password;
		InputStream stream = readSock.getInputStream();
		Scanner scan = new Scanner(stream, "US-ASCII");
		while (scan.hasNextLine()) {
			message = scan.nextLine();
			System.err.println("read " + message);
			String[] tokens = message.split("\\s+");
			
	    	if (tokens.length != 3) {
	    		message = "DENY \n";
	    	}
	    	else {
	    		prefix = tokens[0];
	        	groupname = tokens[1];
	        	password = tokens[2];
	            if (prefix.equals("INIT")) {
	            	message = grTable.authenticateUser(groupname, password);
	    		}
	            else {
	            	message = "DENY \n";
	            }
	    	}
    	}
		System.err.println("mess " + message);
        return message;
	}

	@Override
	public void write(Socket writeSock, String message) throws IOException {
		byte[] rbuf = message.getBytes("US-ASCII");
		writeSock.getOutputStream().write(rbuf, 0, rbuf.length);
		
		// Close socket after writing message back to client
		writeSock.close();
	}

	public static void main(String[] args) throws IOException {
		CentralServer centralServer = new CentralServer();
		String ipAddress = args[0];
		int port = Integer.parseInt(args[1]);
		centralServer.listenConnect(ipAddress, port);
	}

}

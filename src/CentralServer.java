// Class that reroutes clients to appropriate Group Router based on information submitted

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Scanner;


public class CentralServer extends Server {
	
	private GroupRoutersTable grTable;
	private static final String INIT = "INIT";
	private static final String DENY = "DENY ";
	
	public CentralServer() {
		grTable = new GroupRoutersTable();
		grTable.inputFileData();
	}
	
	public static void usage() {
        System.err.println("Usage: java CentralServer <central server address> <central server port> \n");
        System.exit(1);
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
			String[] tokens = message.split("\\s+");
			
	    	if (tokens.length != 3) {
	    		message = DENY + System.getProperty("line.separator");
	    	}
	    	else {
	    		prefix = tokens[0];
	        	groupname = tokens[1];
	        	password = tokens[2];
	            if (prefix.equals(INIT)) {
	            	message = grTable.authenticateUser(groupname, password);
	    		}
	            else {
	            	message = DENY + System.getProperty("line.separator");
	            }
	    	}
	        return message;
    	}
		return message;
	}

	@Override
	public void write(Socket writeSock, String message) throws IOException {
		byte[] rbuf = message.getBytes("US-ASCII");
		writeSock.getOutputStream().write(rbuf, 0, rbuf.length);
		writeSock.close();
	}

	public static void main(String[] args) throws IOException {
		// Must have 2 arguments 
        if (args.length!=2) {
            usage();
            System.exit(1);
        }
  
        // IP address must follow proper format
        String ipAddress = args[0];
		if (!ipAddress.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
			usage();
            System.err.println("ERROR: IP Address " + ipAddress + " is not valid");
            System.exit(1);
        }
		
		// Port must be within appropriate range
		int port = Integer.parseInt(args[1]);
        if (port <= 0 || port > 65535) {
        	usage();
            System.err.println("ERROR: Port " + port + " is not valid");
            System.exit(1);
        }
  
        CentralServer centralServer = new CentralServer();
		centralServer.listenConnect(ipAddress, 20, port);
	}

}

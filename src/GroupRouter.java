import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.*;

public class GroupRouter extends Server {
	
	private byte[] rbuf;
	int bytesRead;
	String message;
	String prefix=new String();
	String PING="PING";
	HashMap<String,Integer[]> chatServers= new HashMap<String, Integer[]>();
	int numberClients;
	String ACPT="ACPT";
	String LEFT= "LEFT";
	public GroupRouter() {
		rbuf = new byte[300];
	}

	@Override
	public void read(Socket readSock) throws UnsupportedEncodingException, IOException {
		// Wait for client's request and then write the request to server socket (send to server)
		int rbufCounter=0;
		boolean isNotEndofLine=true;
		String csAddress;
		int csPort;
		Integer[] keyArray=new Integer[2];
		while ((bytesRead = readSock.getInputStream().read(rbuf)) > 0 && isNotEndofLine) {                                                                                                                               
			if(rbuf[rbufCounter]=='\n') {
				isNotEndofLine=false;
			}
			rbufCounter++;
		}
		message=new String(rbuf, "US-ASCII");
		prefix=message.substring(0,3);
		if(prefix.equals(PING)) {
			csAddress=readSock.getInetAddress().getHostAddress().toString();
			csPort=readSock.getPort();
			numberClients=0;
			keyArray[0]=csPort;
			keyArray[1]=numberClients;
			chatServers.put(csAddress, keyArray);
		}
		if(prefix.equals(LEFT)) {
			numberClients--;
		}
	}

	@Override
	public void write(Socket writeSock) throws IOException {	
		Set<String> chatIPs;	
		chatIPs=chatServers.keySet();
		int min=0;
		String[] chatServer=new String[2];
		while(min<10) {
			for(String key: chatIPs) {
				numberClients=chatServers.get(key)[1];
				if (numberClients==min) {
					chatServer[0]=key;
					chatServer[1]=chatServers.get(key)[0].toString();
					numberClients++;
				}
			}
			min++;
		}
		String messageTo= new String(ACPT + " " + chatServer[0] + " " + chatServer[1] + " " + '\n');
		writeSock.getOutputStream().write(messageTo.getBytes("US-ASCII"),0,messageTo.length());
	}

	public static void main() {
		GroupRouter gr=new GroupRouter();
		gr.listenConnect(ipAddress, port);
	}

}

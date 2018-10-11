import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
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
	String FWRD="FWRD";
	String IDNT="IDNT";
	String DENY= "DENY";
	String NULL="NULL \n";
	private ArrayList<Socket> sockArray=new ArrayList<Socket>();
	
	public GroupRouter() {
	}

	@Override
	public String read(Socket readSock) throws UnsupportedEncodingException, IOException {
		if(!sockArray.contains(readSock)) {
			sockArray.add(readSock);
		}
		// Wait for client's request and then write the request to server socket (send to server)
		String csAddress;
		int csPort;
		Integer[] keyArray=new Integer[2];

		InputStream stream = readSock.getInputStream();
		Scanner scan = new Scanner(stream, "US-ASCII");
		while (scan.hasNextLine()) {
			message = scan.nextLine();
			prefix=message.substring(0,3);
			if(prefix.equals(PING)) {
				csAddress=readSock.getInetAddress().getHostAddress().toString();
				csPort=readSock.getPort();
				numberClients=0;
				keyArray[0]=csPort;
				keyArray[1]=numberClients;
				chatServers.put(csAddress, keyArray);
				return NULL;
			}
			if(prefix.equals(IDNT)) {
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
						if(min==10) {
							String messageTo=new String(DENY + " " + '\n');
							return messageTo;
						}
					}
					min++;
				}
				String messageTo= new String(ACPT + " " + chatServer[0] + " " + chatServer[1] + " " + '\n');
				return messageTo;
			}
			if(prefix.equals(LEFT)) {
				numberClients--;
				return NULL;
			}
			if(prefix.equals(FWRD)) {
				for(Socket sock:sockArray){
					this.write(sock, message);
				}
			}
			if(prefix.equals(NULL)) {
				return NULL;
			}
		}
		return NULL;
	}
		@Override
		public void write(Socket writeSock, String message) throws IOException {
			if(!message.equals(null)) {
				writeSock.getOutputStream().write(message.getBytes("US-ASCII"),0,message.length());
			}		
		}

		public static void main() {
			GroupRouter gr=new GroupRouter();
			//gr.listenConnect(ipAddress, port);
		}

	}

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
	volatile String message;
	String prefix=new String();
	String PING="PING";
	HashMap<String,Integer[]> chatServers= new HashMap<String, Integer[]>();
	int numberClients;
	String ACPT= "ACPT";
	String LEFT= "LEFT";
	String FWRD= "FWRD";
	String IDNT= "IDNT";
	String DENY= "DENY";
	String NULL= "NULL \n";
	private ArrayList<Socket> sockArray=new ArrayList<Socket>();
	
	public GroupRouter() {
	}

	@Override
	public String read(Socket readSock) throws UnsupportedEncodingException, IOException {
		//System.err.println("Reading");
		// Wait for client's request and then write the request to server socket (send to server)
		String csAddress;
		int csPort;
		Integer[] keyArray=new Integer[2];
		InputStream stream = readSock.getInputStream();
		Scanner scan = new Scanner(stream, "US-ASCII");
		while (scan.hasNextLine()) {
			message = scan.nextLine();
			System.err.println("Message recieved: " + message);
			prefix=message.substring(0,4);
			System.err.println("prefix is: " + prefix);
			if(!sockArray.contains(readSock) && !prefix.equals(IDNT)) {
				sockArray.add(readSock);
			}
			if(prefix.equals(PING)) {
				csAddress=readSock.getInetAddress().getHostAddress().toString();
				//csPort=readSock.getPort();
				String[] tokens = message.split("\\s+");
				numberClients=0;
				keyArray[0]= Integer.parseInt(tokens[1]);
				System.err.println(keyArray[0]);
				keyArray[1]=numberClients;
				chatServers.put(csAddress, keyArray);
				System.err.println("Message is NULL");
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
							System.err.println("Message is " + messageTo);
							return messageTo;
						}
					}
					min++;
				}
				String messageTo= new String(ACPT + " " + chatServer[0] + " " + chatServer[1] + " " + '\n');
				System.err.println("Message is " + messageTo);
				return messageTo;
			}
			if(prefix.equals(LEFT)) {
				numberClients--;
				System.err.println("Message is NULL");
				return NULL;
			}
			if(prefix.equals(FWRD)) {
				message += " \n";
				for(Socket sock:sockArray){
					System.err.println("Forwarding to " + sock);
					this.write(sock, message);
				}
			}
			if(prefix.equals(NULL)) {
				System.err.println("Message is NULL");
				return NULL;
			}
		}
		//System.err.println("Incorrect message recieved, message is NULL");
		return NULL;
	}
		@Override
		public void write(Socket writeSock, String message) throws IOException {
			if (!message.equals(NULL)) {
				System.err.println("Writing message: " + message);
			}
			writeSock.getOutputStream().write(message.getBytes("US-ASCII"),0,message.length());	
			if (!message.equals(NULL)) {
				System.err.print("just wrote");
			}
		}

		public static void main(String[] args) throws IOException {
			GroupRouter gr=new GroupRouter();
			gr.listenConnect("127.0.0.1", 4065);
		}

	}

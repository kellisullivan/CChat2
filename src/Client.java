import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import sun.misc.Lock;


public class Client {
	
	private static String username;
	private static String groupname;
	private static String password;
	static String initialize = "INIT ";


	
	public static void openSocket() throws IOException, UnsupportedEncodingException{
	    Socket sock;
	    InetAddress server_address;
	    InetSocketAddress endpoint;

	
	    // Setup the server side connection data
	    server_address = InetAddress.getByName("127.0.0.1");
	    endpoint = new InetSocketAddress(server_address, 8080);
	    sock = new Socket();
	

	    // Make the connection
	    try {
	    	sock.connect(endpoint);
	    } catch(ConnectException e) {
	        System.err.println("Cannot connect to server.");
	        System.exit(1);
			return;
	    }
	    
		String send = initialize + groupname + " " + password + " \n";
		sock.getOutputStream().write(send.getBytes("US-ASCII"),0,send.length());
	}



	
	public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException, IOException {
		//create a new lock to use to wait for our threads
		Lock lock = new Lock();
		
		//lock the lock before entering the GUI
		lock.lock();
		
		//create the GUI
		Welcome welcome = new Welcome(lock);

		
		//wait to get the lock once the GUI has gotten the
		//information from the user
		lock.lock();

		//get the information from the GUI
		//store it to be used to send to the Central Server
		String[] clientInfo = welcome.getInfo();
		username = clientInfo[0];
		groupname = clientInfo[1];
		password = clientInfo[2];
		
		//helpful print statements
		System.out.println("Username: " + username);
		System.out.println("Groupname: " + groupname);
		System.out.println("Password: " + password);
		
		openSocket();
	}
}

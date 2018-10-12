import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {

	private Socket sock;
	Server serv;
	Boolean wroteSomething;
	
	public Connection(Server serv, Socket sock) {
		this.serv = serv;
		this.sock = sock;
	}

	public void run() {
		while (!sock.isClosed()) {
			
			try {
					String message = serv.read(sock);
					serv.write(sock, message);
				}
			catch (IOException e) {
				try {
					sock.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.err.println(e);
				e.printStackTrace();
			}

		}

	}
}


public class GroupRouterID {
	
	private String ipAddress;
	private int port;
	private String password;

	public GroupRouterID(String ipAddress, int port, String password) {
		this.ipAddress = ipAddress;
		this.port = port;
		this.password = password;
	}

	public String getIPAddress() {
		return ipAddress;
	}

	public int getPort() {
		return port;
	}

	public String getPassword() {
		return password;
	}

}

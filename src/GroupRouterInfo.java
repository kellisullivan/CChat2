
public class GroupRouterInfo {
	
	private String ipAddress;
	private int port;
	private String password;

	public GroupRouterInfo(String ipAddress, int port, String password) {
		this.ipAddress = ipAddress;
		this.port = port;
		this.password = password;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public int getPort() {
		return port;
	}

	public String getPassword() {
		return password;
	}

}

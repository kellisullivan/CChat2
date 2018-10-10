import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class GroupRoutersTable {
	
	HashMap<String, GroupRouterID> GroupRouterMap;
	
	public GroupRoutersTable() {
		GroupRouterMap = new HashMap<String, GroupRouterID>();
	}
	
	public void inputFileData() {
		try {
			Scanner scan = new Scanner(new File("GroupRouters.txt"));
			String[] currLine;
			while (scan.hasNextLine()) {
				currLine = scan.nextLine().split(":");
				String groupnameKey = currLine[0];
				GroupRouterID grIDValue = new GroupRouterID(currLine[1], Integer.parseInt(currLine[2]), currLine[3]);
				GroupRouterMap.putIfAbsent(groupnameKey, grIDValue);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e);
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	// Find group chat name entered by client in HashMap and 
	// check if password entered by client matches that of specified group chat
	public String authenticateUser(String groupname, String password) {
		if (GroupRouterMap.containsKey(groupname)) {
			GroupRouterID chosenGR = GroupRouterMap.get(groupname);
			if (password.equals(chosenGR.getPassword())) {
				return "ACPT " + chosenGR.getIPAddress() + " " + chosenGR.getPort() + " \n";
			}
		}
		return "DENY \n";
	}
	
}



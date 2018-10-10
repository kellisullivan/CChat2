import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class GroupRoutersTable {
	
	HashMap<String, GroupRouterInfo> grTable;
	
	public GroupRoutersTable() {
		grTable =  new HashMap<String, GroupRouterInfo>();
	}
	
	private void inputGRData() {
		File grFile = new File("GroupRouters.txt");
		Scanner scan = new Scanner(System.in);
		while (scan.hasNextLine()) {
			scan.next();
		}
	}
	
}



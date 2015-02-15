
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.HashMap; 


public class Meal {
	private String name;
	private ArrayList<HashMap<String,HashMap<String, String>>> menuItems;


	public Meal() {
		menuItems = new ArrayList<HashMap<String,HashMap<String, String>>>();
	}


	public HashMap<String,HashMap<String, String>> getMenuItem(String menuCategory){
		return menuItems.get(0);
	}
}
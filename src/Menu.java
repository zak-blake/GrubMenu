
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.HashMap; 


public class Menu {
	private String date;
	private String diningCommon;
	private String meal;
	ArrayList<HashMap<String,HashMap<String, String>>> menuItems;

	public Menu() {
		menuItems = new ArrayList<HashMap<String,HashMap<String,String>>>();
	}

	//getters

	public String getDate(){
		return date;
	}

	public String getDiningcommon(){
		return diningCommon;
	}

	public String getMeal(){
		return meal;
	}

	public HashMap<String,HashMap<String, String>> getMenuItem(String menuCategory){
		return menuItems.get(0);
	}

	//setters

	public void setDate(String date){
		this.date = date;
	}
	
	public void setDiningcommon(String diningCommon){
		this.diningCommon = diningCommon;
	}

	public void setMeal(String meal){
		this.meal = meal;
	}
}
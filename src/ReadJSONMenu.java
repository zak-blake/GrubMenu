import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJSONMenu {

	private static final String filePath = "OrtegaTestMenu.json";
	
	public static void main(String[] args) {

		try {
			// read the json file
			FileReader reader = new FileReader(filePath);

			JSONParser jsonParser = new JSONParser();
			JSONArray eventList = (JSONArray) jsonParser.parse(reader);

			// get a String from the JSON object
			JSONObject breakfastEvent = (JSONObject) eventList.get(0);
			JSONObject event = (JSONObject) breakfastEvent.get("Event");
			System.out.println("The Event name is: " + breakfastEvent);




			JSONObject lunchEvent = (JSONObject) eventList.get(1);
			JSONObject event2 = (JSONObject) lunchEvent.get("Event");
			System.out.println("The Event name is: " + event2);


		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

	}

}
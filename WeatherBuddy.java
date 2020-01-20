import java.io.*;
import javax.json.*;
import java.net.*;
import java.util.*;

public class WeatherBuddy{
	public static void main(String[] args) throws IOException, InterruptedException{
		Scanner console = new Scanner(System.in);

		ArrayList<String> cities = new ArrayList<>();
		ArrayList<String> attributes = new ArrayList<>();
		ArrayList<String> attributeTypes = new ArrayList<>();


		//create attributes array list
		attributeTypes.add("String");
		attributes.add("lon");
		attributeTypes.add("Double");
		attributes.add("lat");

		//take in user input for cities
		String temp = console.next();
		while(!temp.equals("done")){
			cities.add(temp);
			temp = console.next();
		}

		//Json stuff

			//temporary for test
			String city = cities.get(0);
			//

			URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=df72422c43d907dfc270e0dc5973d129");
			InputStream is = url.openStream();
			JsonReader reader = Json.createReader(new InputStreamReader(is, "UTF-8"));
			JsonObject jo = reader.readObject();
			JsonObject jop = jo.getJsonObject("coord");

			System.out.print("coordinates:\n" + "     longitude: " + jop.getJsonNumber("lon").doubleValue());
			System.out.println("\n     latitude: " + jop.getJsonNumber("lat").doubleValue());
	}


}
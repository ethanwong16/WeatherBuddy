import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class WeatherBuddyCommands {
	JFrame f;
	JLabel l, l0, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, imgL;
	JButton b1, b2;
	ImageIcon img;
	CreateReadOnlyJTextField readOnly;

	Map<Integer, String> myMap = new HashMap<>() {
		{
			put(0, "Longitude");
			put(1, "Latitude");
			put(2, "Overall weather conditions");
			put(3, "Specific weather conditions");
			put(4, "Current temperature in Celsius");
			put(5, "Pressure in hectopascals");
			put(6, "Percentage humidity");
			put(7, "Minimum temperature");
			put(8, "Maximum temperature");
			put(9, "Wind speed in meters / seconds");
			put(10, "Percent cloud cover");
			put(11, "Sunrise time");
			put(12, "Sunset time");
		}
	};

	private static String[][] masterCommandList = new String[][] { // list of ALL 13 commands possible by WeatherBuddy
			{ "coord", "coord", "weather", "weather", "main", "main", "main", "main", "main", "wind", "clouds", "sys",
					"sys" },
			{ "lon", "lat", "main", "description", "temp", "pressure", "humidity", "temp_min", "temp_max", "speed",
					"all", "sunrise", "sunset" } };

	public void implementMethods(ArrayList<String> cityNames, ArrayList<Integer> commandCodes)
			throws IOException, InterruptedException {
		// create array for weather advice to client
		ArrayList<String> clientAdvice = new ArrayList<String>();
		ArrayList<String> cleanedCityNames = new ArrayList<String>();

		for (int i = 0; i < cityNames.size(); i++) {
			f = new JFrame("Results");
			f.getContentPane().setLayout(null);
			f.getContentPane().setBackground(Color.white);

			URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + cityNames.get(i)
					+ "&APPID=df72422c43d907dfc270e0dc5973d129");
			// creates a full length URL
			cleanedCityNames = cleanCityName(cityNames, i);
			l = new JLabel("Weather details for " + cleanedCityNames.get(i) + ":");
			l.setBounds(50, 20, 600, 30);
			l.setFont(new Font("Arial", Font.BOLD, 36));
			l.setForeground(Color.RED);
			f.getContentPane().add(l);
			boolean continueTemperatureCheck = true; // placed here so no repeats of temperature checks
			int y = 50;
			for (int j = 0; j < commandCodes.size(); j++) {
				InputStream is = url.openStream();
				JsonReader reader = Json.createReader(new InputStreamReader(is, "UTF-8"));
				JsonObject jo = reader.readObject();
				boolean danger = false; // changes to true whenever weather reports warn of conditions
				l0 = new JLabel(myMap.get(commandCodes.get(j)) + ":");
				l0.setBounds(50, 100 + j * y, 600, 30);
				l0.setFont(new Font("Arial", Font.BOLD, 24));
				l0.setForeground(Color.black);
				f.getContentPane().add(l0);

				if (commandCodes.get(j) == 2 || commandCodes.get(j) == 3) { // from the "weather" array
					JsonArray jop = jo.getJsonArray(masterCommandList[0][commandCodes.get(j)]);
					if (commandCodes.get(j) == 2) { // command property is "main"
						JsonObject jojo = jop.getJsonObject(0);
						String main = jojo.getString("main");
						l1 = new JLabel(main);
						l1.setBounds(450, 100 + j * y, 600, 30);
						l1.setFont(new Font("Arial", Font.BOLD, 24));
						l1.setForeground(Color.black);
						f.getContentPane().add(l1);
						for (int d = 1; d < jop.size(); d++) { // to get rid of extra commas
							jojo = jop.getJsonObject(d);
							main = jojo.getString("main");
							l2 = new JLabel(", " + main);
							l2.setBounds(450 + 50 * d, 100 + j * y, 600, 30);
							l2.setFont(new Font("Arial", Font.BOLD, 24));
							l2.setForeground(Color.black);
							f.getContentPane().add(l2);
						}
						// analyze weather descriptions
						if (main.equals("Clear")) {
							clientAdvice.add("Wear sunscreen and enjoy the sun!");
							img = new ImageIcon("C:/Java/eclipse-workspace/APCS/src/clear.jpg");
							imgL = new JLabel(img);
							imgL.setBounds(760, 425, 400, 400);
							f.getContentPane().add(imgL);
						} else if (main.equals("Thunderstorm") || main.equals("Squall") || main.equals("Tornado")) {
							danger = true;
							img = new ImageIcon("C:/Java/eclipse-workspace/APCS/src/thunderstorm.jpg");
							imgL = new JLabel(img);
							imgL.setBounds(760, 425, 400, 400);
							f.getContentPane().add(imgL);
						} else if (main.equals("Drizzle") || main.equals("Rain")) {
							clientAdvice.add("Bring an umbrella or rain coat with you!");
							img = new ImageIcon("C:/Java/eclipse-workspace/APCS/src/rain.jpg");
							imgL = new JLabel(img);
							imgL.setBounds(760, 425, 400, 400);
							f.getContentPane().add(imgL);
						} else if (main.equals("Snow")) {
							clientAdvice.add("It's snowing! Bring snow boots, gloves, and a scarf with you!");
							img = new ImageIcon("C:/Java/eclipse-workspace/APCS/src/snow.jpg");
							imgL = new JLabel(img);
							imgL.setBounds(760, 425, 400, 400);
							f.getContentPane().add(imgL);
						} else if (main.equals("Smoke") || main.equals("Haze") || main.equals("Dust")
								|| main.equals("Sand") || main.equals("Ash")) {
							clientAdvice.add("The atmospheric conditions are not good. Bring a mask with you!");
							img = new ImageIcon("C:/Java/eclipse-workspace/APCS/src/fog.jpg");
							imgL = new JLabel(img);
							imgL.setBounds(760, 425, 400, 400);
							f.getContentPane().add(imgL);
						} else {
							img = new ImageIcon("C:/Java/eclipse-workspace/APCS/src/clouds.jpg");
							imgL = new JLabel(img);
							imgL.setBounds(760, 425, 400, 400);
							f.getContentPane().add(imgL);
							// only option left is "Cloudy"
						}

						if (danger) {
							clientAdvice.add(
									"Weather conditions are dangerous! Stay indoors and be prepared to evacuate if necessary!");
						}
					} else { // command property is "description"
						JsonObject jojo = jop.getJsonObject(0);
						String description = jojo.getString("description");
						l3 = new JLabel(description);
						l3.setBounds(450, 100 + j * y, 600, 30);
						l3.setFont(new Font("Arial", Font.BOLD, 24));
						l3.setForeground(Color.black);
						f.getContentPane().add(l3);
						for (int d = 1; d < jop.size(); d++) { // to get rid of extra commas
							jojo = jop.getJsonObject(d);
							description = jojo.getString("description");
							l4 = new JLabel(", " + description);
							l4.setBounds(450 + 50 * d, 100 + j * y, 600, 30);
							l4.setFont(new Font("Arial", Font.BOLD, 24));
							l4.setForeground(Color.black);
							f.getContentPane().add(l4);
						}
					}
				} else if (commandCodes.get(j) < 2) { // latitude or longitude
					JsonObject jop = jo.getJsonObject(masterCommandList[0][commandCodes.get(j)]);
					l5 = new JLabel(jop.get(masterCommandList[1][commandCodes.get(j)]) + " coordinate degrees");
					l5.setBounds(450, 100 + j * y, 600, 30);
					l5.setFont(new Font("Arial", Font.BOLD, 24));
					l5.setForeground(Color.black);
					f.getContentPane().add(l5);
				} else if (commandCodes.get(j) == 4 || commandCodes.get(j) == 7 || commandCodes.get(j) == 8) { // temperature
																												// values
					JsonObject jop = jo.getJsonObject(masterCommandList[0][commandCodes.get(j)]);
					int celsiusTemp = (jop.getInt(masterCommandList[1][commandCodes.get(j)])) - 273;
					l6 = new JLabel(celsiusTemp + " degrees Celsius");
					l6.setBounds(450, 100 + j * y, 600, 30);
					l6.setFont(new Font("Arial", Font.BOLD, 24));
					l6.setForeground(Color.black);
					f.getContentPane().add(l6);

					// analyze data to get client advice and prevent future temperature analysis if
					// already analyzed
					if (commandCodes.get(j) == 4) {
						continueTemperatureCheck = false;
						if (celsiusTemp <= 0) {
							clientAdvice.add("It's freezing outside!");
							clientAdvice.add("	Wear thick jackets and cover up!");
						} else if (celsiusTemp <= 18) {
							clientAdvice.add("It's chilly outside!");
							clientAdvice.add("	Wear long sleeve clothes and bring a jacket!");
						} else if (celsiusTemp >= 35) {
							clientAdvice.add("It's burning outside!");
							clientAdvice.add("	Stay indoors with the AC");
						} else if (celsiusTemp >= 27) {
							clientAdvice.add("It's warm outside!");
							clientAdvice.add("	Feel free to wear short sleeves and a hat!");
						} else {
							clientAdvice.add("The weather is nice and moderate!");
						}
					}
					if (continueTemperatureCheck) {
						continueTemperatureCheck = false;
						if (celsiusTemp <= 0) {
							clientAdvice.add("It's freezing outside!");
							clientAdvice.add("	Wear thick jackets and cover up.");
						} else if (celsiusTemp <= 18) {
							clientAdvice.add("It's chilly outside!");
							clientAdvice.add("	Wear long sleeve clothes and bring a jacket.");
						} else if (celsiusTemp >= 35) {
							clientAdvice.add("It's burning outside!");
							clientAdvice.add("	Stay indoors with the AC!");
						} else if (celsiusTemp >= 27) {
							clientAdvice.add("It's warm outside!");
							clientAdvice.add("	Feel free to wear short sleeves and a hat.");
						} else {
							clientAdvice.add("The weather is nice and moderate.");
						}
					}
				} else if (commandCodes.get(j) == 5) { // atmospheric pressure measurement
					JsonObject jop = jo.getJsonObject(masterCommandList[0][commandCodes.get(j)]);
					l7 = new JLabel(jop.get(masterCommandList[1][commandCodes.get(j)]) + " hectoPascals");
					l7.setBounds(450, 100 + j * y, 600, 30);
					l7.setFont(new Font("Arial", Font.BOLD, 24));
					l7.setForeground(Color.black);
					f.getContentPane().add(l7);
				} else if (commandCodes.get(j) == 6) { // humidity
					JsonObject jop = jo.getJsonObject(masterCommandList[0][commandCodes.get(j)]);
					l8 = new JLabel(jop.get(masterCommandList[1][commandCodes.get(j)]) + " percent humidity");
					l8.setBounds(450, 100 + j * y, 600, 30);
					l8.setFont(new Font("Arial", Font.BOLD, 24));
					l8.setForeground(Color.black);
					f.getContentPane().add(l8);

					// client advice for those with lung problems
					if (jop.getInt(masterCommandList[1][commandCodes.get(j)]) >= 70) {
						clientAdvice.add("Humidity levels are very high!");
						clientAdvice.add("	Please be mindful of your health concerns if applicable!");
					}
				} else if (commandCodes.get(j) == 9) { // wind speed
					JsonObject jop = jo.getJsonObject(masterCommandList[0][commandCodes.get(j)]);
					l9 = new JLabel(jop.get(masterCommandList[1][commandCodes.get(j)]) + " meters per second");
					l9.setBounds(450, 100 + j * y, 600, 30);
					l9.setFont(new Font("Arial", Font.BOLD, 24));
					l9.setForeground(Color.black);
					f.getContentPane().add(l9);
					if (jop.getInt(masterCommandList[1][commandCodes.get(j)]) >= 16) {
						clientAdvice.add("Wind speeds are very high!");
						clientAdvice.add("	We encourage you to stay in a sheltered location!");
					}
				} else if (commandCodes.get(j) == 10) { // percent cloud cover
					JsonObject jop = jo.getJsonObject(masterCommandList[0][commandCodes.get(j)]);
					l10 = new JLabel(jop.get(masterCommandList[1][commandCodes.get(j)]) + " percent cloud cover");
					l10.setBounds(450, 100 + j * y, 600, 30);
					l10.setFont(new Font("Arial", Font.BOLD, 24));
					l10.setForeground(Color.black);
					f.getContentPane().add(l10);
				} else {
					JsonObject jop = jo.getJsonObject(masterCommandList[0][commandCodes.get(j)]);
					int utcTime = (jop.getInt(masterCommandList[1][commandCodes.get(j)]));
					Date date = new java.util.Date(utcTime * 1000L);
					SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss z");
					sdf.setTimeZone(java.util.TimeZone.getTimeZone("PST"));
					String formattedDate = sdf.format(date);
					l11 = new JLabel(formattedDate);
					l11.setBounds(450, 100 + j * y, 600, 30);
					l11.setFont(new Font("Arial", Font.BOLD, 24));
					l11.setForeground(Color.black);
					f.getContentPane().add(l11);
				}

			}
			clientAdvice.add(""); // separates weather advice from different cities
			// print out weather advice
			int clientAdviceCounter = 0;

			l12 = new JLabel("Weather tips for city of " + cleanedCityNames.get(i) + " :");
			l12.setBounds(760, 100, 1200, 30);
			l12.setFont(new Font("Arial", Font.BOLD, 24));
			l12.setForeground(Color.BLUE);
			f.getContentPane().add(l12);
			if (clientAdvice.size() != 1) {
				while ((clientAdviceCounter < clientAdvice.size())
						&& (!(clientAdvice.get(clientAdviceCounter)).equalsIgnoreCase(""))) {
					l13 = new JLabel("	" + clientAdvice.get(clientAdviceCounter));
					l13.setBounds(760, 150 + clientAdviceCounter * 50, 1200, 30);
					l13.setFont(new Font("Arial", Font.BOLD, 24));
					l13.setForeground(Color.GRAY);
					f.getContentPane().add(l13);
					clientAdviceCounter++;
				}
			} else {
				l13 = new JLabel("None");
				l13.setBounds(760, 150, 1200, 30);
				l13.setFont(new Font("Arial", Font.BOLD, 24));
				l13.setForeground(Color.GRAY);
				f.getContentPane().add(l13);
			}
			f.setSize(1500, 1000);
			f.setVisible(true);
		}
	}

	public static ArrayList<String> cleanCityName(ArrayList<String> cityNames, int cityIndex) { // gets rid of the "%20"
																								// used to indicate
																								// spaces in URLs
		String temporaryCity = cityNames.get(cityIndex);
		for (int j = 0; j < temporaryCity.length() - 4; j++) {
			if ((temporaryCity.substring(j, j + 3)).equals("%20")) {
				cityNames.set(cityIndex,
						(temporaryCity.substring(0, j) + " " + temporaryCity.substring(j + 3, temporaryCity.length())));
			}
		}
		return cityNames;
	}
}
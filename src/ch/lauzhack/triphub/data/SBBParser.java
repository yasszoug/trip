package ch.lauzhack.triphub.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ch.lauzhack.triphub.trip.Path;
import ch.lauzhack.triphub.trip.Station;
import ch.lauzhack.triphub.trip.Stop;
import ch.lauzhack.triphub.trip.Train;
import lib.JSONArray;
import lib.JSONObject;

public class SBBParser implements Parser {
	private final String baseURL = "http://transport.opendata.ch/v1/";

	public SBBParser () {
	}

	@Override
	public ArrayList<Path> getConnections (Station startingStation, Station endStation, Calendar date) {
		String startId = startingStation.getId();
		String endId = endStation.getId();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		ArrayList<Path> paths = new ArrayList<>();
		try {
			URL url = new URL(baseURL+"connections?from="+startId+"&to="+endId+"&date="+dateFormat.format(date.getTime())+"&time="+timeFormat.format(date.getTime()));
			System.out.println(url);
			JSONObject json = getJSONFromURL(url);
			JSONArray connections = json.getJSONArray("connections");
			for (int i = 0; i < connections.length(); i++) {
				ArrayList<Stop> stops = new ArrayList<>();
				JSONArray sections = connections.getJSONObject(i).getJSONArray("sections");
				for (int j = 0; j < sections.length(); j++) {
					if (sections.getJSONObject(j).isNull("journey")) continue;
					JSONObject journey = sections.getJSONObject(j).getJSONObject("journey");
					int capacity = journey.isNull("capacity2nd") ? -1 : journey.getInt("capacity2nd");
					Train train = new Train(journey.getString("name"),journey.getString("operator"),capacity,journey.getString("category"));
					JSONArray pathList = journey.getJSONArray("passList");
					for (int k = 0; k < pathList.length(); k++) {
						JSONObject stationJSON = pathList.getJSONObject(k).getJSONObject("station");
						Station station = new Station(stationJSON.getString("name"),stationJSON.getJSONObject("coordinate").getDouble("x"),stationJSON.getJSONObject("coordinate").getDouble("y"),stationJSON.getString("id"));
						String departureAsString = pathList.getJSONObject(k).isNull("departure") ? null : pathList.getJSONObject(k).getString("departure");
						Calendar departureDate = departureAsString == null ? null : Calendar.getInstance();
						SimpleDateFormat jsonDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
						if (departureAsString != null)
							departureDate.setTime(jsonDateFormat.parse(departureAsString));

						String arrivalAsString = pathList.getJSONObject(k).isNull("arrival") ? null : pathList.getJSONObject(k).getString("arrival");
						Calendar arrivalDate = arrivalAsString == null ? null : Calendar.getInstance();

						if (arrivalAsString != null)
							arrivalDate.setTime(jsonDateFormat.parse(arrivalAsString));

						stops.add(new Stop(station,arrivalDate,departureDate , train));
					}
				}
				paths.add(new Path(stops));
			}
			return paths;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	private JSONObject getJSONFromURL(URL url) throws IOException {
		URLConnection urlConnection = url.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		StringBuilder jsonBuilder = new StringBuilder();
		int ch;

		while ((ch = reader.read()) != -1) {
			jsonBuilder.append((char) ch);
		}
		String jsonText = jsonBuilder.toString();
		reader.close();
		return new JSONObject(jsonText);
	}

	public ArrayList<Station> getClosestStopFromLocation (double x, double y, int maxDistanceFromStop){
		ArrayList<Station> stationList = new ArrayList<>();
		try {
			URL url = new URL(baseURL + "locations?x=" + String.valueOf(x) + "&y=" + String.valueOf(y));
			JSONObject json = getJSONFromURL(url);
			JSONArray stations = json.getJSONArray("stations");
			for (int i = 0; i < stations.length(); i++) {
				JSONObject station = stations.getJSONObject(i);
				if (station.getDouble("distance") <= maxDistanceFromStop){
					JSONObject coordinates = station.getJSONObject("coordinate");
					stationList.add(new Station(station.getString("name"),coordinates.getDouble("x"),coordinates.getDouble("y"), station.getString("id")));
				}
			}
			return stationList;
		} catch (MalformedURLException e){
			System.out.println("Something went wrong");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Station getStation(String name){
		try {
			String urlString = baseURL + "locations?query="+name;
			urlString = urlString.replace(" ","%20");
			URL url = new URL(urlString);
			JSONObject json = getJSONFromURL(url);
			JSONArray stations = json.getJSONArray("stations");
			if (stations.length() > 0){
				return new Station(stations.getJSONObject(0).getString("name"),stations.getJSONObject(0).getJSONObject("coordinate").getDouble("x"),stations.getJSONObject(0).getJSONObject("coordinate").getDouble("y"),stations.getJSONObject(0).getString("id"));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

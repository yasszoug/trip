package ch.lauzhack.triphub.social;

import java.util.Calendar;
import java.util.List;

import ch.lauzhack.triphub.trip.Path;
import ch.lauzhack.triphub.trip.Station;

public class User {

	private String name;
	private Station departure, arrival;
	private List<Path> path;
	private Calendar preferedTime;
	
	public User()
	{}
	
	public User(String name, Station departure, Station arrival, List<Path> path, Calendar  preferedTime) {
		this.name = name;
		this.departure = departure;
		this.arrival = arrival;
		this.path = path;
		this.preferedTime = preferedTime;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Station getDeparture() {
		return departure;
	}

	public void setDeparture(Station departure) {
		this.departure = departure;
	}

	public Station getArrival() {
		return arrival;
	}

	public void setArrival(Station arrival) {
		this.arrival = arrival;
	}

	public List<Path> getPath() {
		return path;
	}

	public void setPath(List<Path> path) {
		this.path = path;
	}

	public Calendar getPreferedTime() {
		return preferedTime;
	}

	public void setPreferedTime(Calendar preferedTime) {
		this.preferedTime = preferedTime;
	}
	
	
	
}

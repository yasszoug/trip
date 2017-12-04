package ch.lauzhack.triphub.trip;

import java.util.ArrayList;
import java.util.List;

import ch.lauzhack.triphub.social.User;

public class Trip {
	
	private List<User> trip;
	
	public Trip() {
		this.trip = new ArrayList<>();
	}

	public List<User> getTrip() {
		return trip;
	}
}

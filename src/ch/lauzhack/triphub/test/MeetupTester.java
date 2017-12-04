package ch.lauzhack.triphub.test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ch.lauzhack.triphub.data.SBBParser;
import ch.lauzhack.triphub.engine.Meetup;
import ch.lauzhack.triphub.social.User;
import ch.lauzhack.triphub.trip.Station;
import ch.lauzhack.triphub.trip.Trip;

public class MeetupTester {

	
	public static void main(String[] args) {
		SBBParser issou = new SBBParser();
		Station ge = issou.getStation("Geneva");
		Station zu = issou.getStation("Zurich");
		Station la = issou.getStation("Lausanne");
		Station yv = issou.getStation("Yverdon");
		Station ba = issou.getStation("Basel");
		Station be = issou.getStation("Bern");
		Station si = issou.getStation("Sion");

		
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(new SimpleDateFormat("yyyy-MM-dd'/'HH:mm").parse("2016-11-21/11:00"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(c.getTime().toString());
		
		User thomas = new User("Thomas", ge, zu, issou.getConnections(ge, zu, c), c);
		User thomas2 = new User("Thomas2", ge, zu, issou.getConnections(ge, zu, c), c);
		User maxime = new User("Maxime", yv, zu, issou.getConnections(yv, zu, c), c);
		User vincent = new User("Vincent", la, zu, issou.getConnections(la, zu, c), c);
		User claire = new User("Claire", ba, zu, issou.getConnections(ba, zu, c), c);
		User valentin = new User("Valentin", si, zu, issou.getConnections(si, zu, c), c);
		
		List<User> users = new ArrayList<>();
		users.add(thomas);
		users.add(thomas2);
		users.add(maxime);
		users.add(vincent);
		users.add(claire);
		users.add(valentin);
		
		Trip t = Meetup.getBestTrip(users);
		for (User user : users) {
			System.out.println(user.getPath());
			System.out.println("-------------------------------");
		}
	}
	
}

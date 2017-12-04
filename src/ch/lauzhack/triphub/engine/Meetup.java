package ch.lauzhack.triphub.engine;

import java.util.List;

import ch.lauzhack.triphub.data.SBBParser;
import ch.lauzhack.triphub.social.User;
import ch.lauzhack.triphub.trip.Path;
import ch.lauzhack.triphub.trip.Stop;
import ch.lauzhack.triphub.trip.Trip;

public class Meetup {

	private static SBBParser sbbParser = new SBBParser();

	
	public static Trip getBestTrip(List<User> users) {

		if(users.size() == 1) {
			Trip t = new Trip();
			Path p = users.get(0).getPath().get(0); 
			users.get(0).getPath().clear();
			users.get(0).getPath().add(p);
			t.getTrip().add(users.get(0));
			return t;
		}
		
		sbbParser = new SBBParser();

		Trip t = new Trip();
		Path masterPath = null;
		User masterUser = null;
		int greatestMetric = 0;
		for (User user : users) {
			for(User user2 : users) {
				if(user2 == user) continue;
				for(Path p : user.getPath()) {
					int i = 0;
					for(Path p2 : user2.getPath()) {
						for(Stop s : p.getPath()) {
							for(Stop s2 : p2.getPath()) {
								if(s.equals(s2)) {
									i++;
								}
							}
						}
					}
					if(i > greatestMetric) {
						greatestMetric = i;
						masterPath = p;
						masterUser = user;
					}
				}
			}
		}
		
		for (User user : users) {
			if (user == masterUser)
				continue;
			
			Stop mergePoint = null;
			
			List<Path> p = sbbParser.getConnections(user.getDeparture(), masterUser.getDeparture(), user.getPreferedTime());
			if(p.size() == 0) throw new RuntimeException("A user has no path :(");
			Path bestJoiner = p.get(0);
			int bjc = 0;
			for (Path path : p) {
				loop1: for (Stop s : path.getPath()) {
					int i = 0;
					for (Stop stop2 : masterPath.getPath()) {
						i++;
						if (s.getStation().equals(stop2.getStation())) {
							if (path.isMergableAt(s, stop2)) {
								if (bestJoiner == null || i  > bjc) {
									bestJoiner = path;
									bjc = i;
									mergePoint = s;
									break loop1;
								}
							}
						}
					}
				}
			}
			
			if (mergePoint != null) {
				bestJoiner.mergePaths(masterPath, mergePoint);
			} else {
				p = sbbParser.getConnections(user.getDeparture(), masterUser.getArrival(), user.getPreferedTime());
				bestJoiner = p.get(0);
				bjc = 0;
				for (Path path : p) {
					loop1: for (Stop s : path.getPath()) {
						int i = 0;
						for (Stop stop2 : masterPath.getPath()) {
							i++;
							if (s.getStation().equals(stop2.getStation())) {
								if (path.isMergableAt(s, stop2)) {
									if (bestJoiner == null || i  > bjc) {
										bestJoiner = path;
										bjc = i;
										mergePoint = s;
										break loop1;
									}
								}
							}
						}
					}
				}
				if(mergePoint != null) {
					bestJoiner.mergePaths(masterPath, mergePoint);
				} else {
					bestJoiner = user.getPath().get(0);
				}
			}
			user.getPath().clear();
			user.getPath().add(bestJoiner);
			t.getTrip().add(user);
		}
		masterUser.getPath().clear();
		assert (masterPath != null);
		masterUser.getPath().add(masterPath);
		t.getTrip().add(masterUser);

		return t;
	}
}

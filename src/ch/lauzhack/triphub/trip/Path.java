package ch.lauzhack.triphub.trip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Path {

	private List<Stop> path;
	
	public Path() {
		this.path = new ArrayList<>();
	}
	
	public Path(List<Stop> path) {
		this();
		this.path.addAll(path); 
	}

	public List<Stop> getPath() {
		return path;
	}

	public void setPath(List<Stop> path) {
		this.path = path;
	}
	
	public void mergePaths(Path p, Stop s) {
		Path p2 = new Path();
		for (Stop st : this.getPath()) {
			if(st.equals(s)) {
				p2.getPath().add(st);
				break;
			}
			p2.getPath().add(st);
		}
		
		boolean add = false;
		for (int i = 0; i < p.getPath().size(); i++) {
			if(add) {
				p2.getPath().add(p.getPath().get(i));
			}
			if(p.getPath().get(i).equals(s)) {
				add = true;
			}
		}
		this.path = p2.getPath();
	}
	
	public boolean isMergableAt(Stop s1, Stop s2) {
		if(this.getPath().contains(s1)) {
			if(s1.equals(s2)) {
				Calendar c1 = s1.getArrivalTime() != null ? s1.getArrivalTime() : s1.getDepartureTime();
				Calendar c2 = s2.getArrivalTime() != null ? s2.getArrivalTime() : s2.getDepartureTime();
				if(c1.before(c2)) {
					return true;
				}
			}
		} else {
			throw new IllegalArgumentException("Path/Stop invalid");
		}
		return false;
	}

	@Override
	public String toString () {
		String string = "";
		string += "[\n";
		for (Stop stop : path) {
			Date t = stop.getArrivalTime()==null?stop.getDepartureTime().getTime():stop.getArrivalTime().getTime();
			string += stop.getStation().getName() + " with train: " + stop.getTrain().getServiceName()+" @ "+t.getHours()+":"+t.getMinutes()+"\n";
		}
		string += "]";
		return string;
	}
}

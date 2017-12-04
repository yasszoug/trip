package ch.lauzhack.triphub.data;

import ch.lauzhack.triphub.trip.Path;
import ch.lauzhack.triphub.trip.Station;

import java.util.ArrayList;
import java.util.Calendar;

public interface Parser {

	ArrayList<Path> getConnections(Station startingStation, Station endStation, Calendar date);

}

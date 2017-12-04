package ch.lauzhack.triphub.test;

import ch.lauzhack.triphub.data.SBBParser;
import ch.lauzhack.triphub.trip.Path;
import ch.lauzhack.triphub.trip.Station;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class SBBParserTest {
	public static void main (String[] args) {
		SBBParser parser = new SBBParser();
		Station genevaStation = parser.getStation("Geneva");

		Station lausanneStation = parser.getStation("Lausanne");

		System.out.println(genevaStation);
		System.out.println(genevaStation.getId());
		System.out.println(lausanneStation);
		System.out.println(Calendar.getInstance().getTime());
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		System.out.println(dateFormat.format(Calendar.getInstance().getTime()));
		ArrayList<Path> paths = parser.getConnections(lausanneStation,genevaStation,Calendar.getInstance());
		for (Path path : paths) {
			System.out.println();
			System.out.println(path);
			System.out.println();
		}
	}
}

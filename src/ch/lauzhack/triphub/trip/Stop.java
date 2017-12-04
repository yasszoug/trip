package ch.lauzhack.triphub.trip;

import java.util.Calendar;

public class Stop {
	private Station station;
	private Calendar arrivalTime;
	private Calendar departureTime;
	private Train train;

	public Stop()
	{}

	public Stop (Station station, Calendar arrivalTime, Calendar departureTime, Train train) {
		this.station = station;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.train = train;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station)
	{
		this.station = station;
	}

	public Calendar getArrivalTime () {
		return arrivalTime;
	}

	public Calendar getDepartureTime() {
		return departureTime;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Stop) {
			return ((Stop) obj).getStation().getId().equals(this.getStation().getId());
		}
		return false;
	}

	public String getTimeOfDeparture()
	{
		if(departureTime == null)
			return "—";

		if(departureTime.getTime().getMinutes()<10){
			return departureTime.getTime().getHours()+":0"+departureTime.getTime().getMinutes();
		}
		return departureTime.getTime().getHours()+":"+departureTime.getTime().getMinutes();
	}

	public String getTimeOfArrival()
	{
		if(arrivalTime == null)
			return "—";

		if(arrivalTime.getTime().getMinutes()<10){
			return arrivalTime.getTime().getHours()+":0"+arrivalTime.getTime().getMinutes();
		}
		return arrivalTime.getTime().getHours()+":"+arrivalTime.getTime().getMinutes();
	}

	public Train getTrain () {
		return train;
	}
}

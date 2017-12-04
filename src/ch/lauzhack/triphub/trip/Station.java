package ch.lauzhack.triphub.trip;

public class Station {
	private String name;
	private double latitude;
	private double longitude;
	private String id;
	
	public Station()
	{}
	
	public Station (String name, double latitude, double longitude, String id)
	{
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.id = id;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public double getLatitude() 
	{
		return latitude;
	}

	public boolean setLatitude(double latitude) 
	{
		if(latitude > 90.0 || latitude < -90.0)
			return false;
		
		this.latitude = latitude;
		return true;
	}

	public double getLongitude() 
	{
		return longitude;
	}

	public boolean setLongitude(double longitude) 
	{
		if(longitude > 180.0 || longitude < -180.0)
			return false;
		this.longitude = longitude;
		return true;
	}

	@Override
	public String toString () {
		return "Name: "+this.name+"\n("+this.getLatitude()+","+this.getLongitude()+")";
	}

	public String getId () {
		return id;
	}

	public void setId (String id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Station) {
			return ((Station)obj).id.equals(this.id);
		}
		return false;
	}
}

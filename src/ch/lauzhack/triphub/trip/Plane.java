package ch.lauzhack.triphub.trip;

public class Plane implements Transport {
	String company;
	
	public Plane()
	{
		company = new String();
	}
	
	public Plane(String company)
	{
		this.company = company;
	}

	public String getCompany() 
	{
		return company;
	}

	public void setCompany(String company) 
	{
		this.company = company;
	}
}

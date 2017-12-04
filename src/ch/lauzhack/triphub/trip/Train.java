package ch.lauzhack.triphub.trip;

public class Train implements Transport {

	private String serviceName;
	private String company;
	private int load;
	private String category;

	public Train()
	{
		serviceName = "";
		company = "SBB CFF";
		load = -1;
		category = "IR";
	}
	
	public Train (String serviceName, String company, int load, String category)
	{
		this.serviceName = serviceName;
		this.company = company;
		this.load = load;
		this.category = category;
	}

	public String getCompany() 
	{
		return company;
	}

	public void setCompany(String company) 
	{
		this.company = company;
	}

	public String getLoad() 
	{
		if(load == -1)
			return new String("Unknown");
		return new Integer(load).toString();
	}

	public void setLoad(int load) 
	{
		this.load = load;
	}

	public String getServiceName () {
		return serviceName;
	}

	public void setServiceName (String serviceName) {
		this.serviceName = serviceName;
	}

	public String getCategory () {
		return category;
	}

	public void setCategory (String category) {
		this.category = category;
	}
}

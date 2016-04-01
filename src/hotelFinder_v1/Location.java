package hotelFinder_v1;

public class Location {
	String cityName;
	String address;
	double distanceFromCenter;
	
	public Location(String city, String address, double distFromCenter){
		super();
		this.cityName = city;
		this.address = address;
		this.distanceFromCenter = distFromCenter;
	}
	
	public String getCityName() {
		return cityName;
	}
	public String getAddress() {
		return address;
	}
	public double getDistanceFromCenter() {
		return distanceFromCenter;
	}
}

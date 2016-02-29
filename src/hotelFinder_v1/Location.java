package hotelFinder_v1;

public class Location {
	String cityName;
	String address;
	double distanceFromCenter;
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getDistanceFromCenter() {
		return distanceFromCenter;
	}
	public void setDistanceFromCenter(double distanceFromCenter) {
		this.distanceFromCenter = distanceFromCenter;
	}
}

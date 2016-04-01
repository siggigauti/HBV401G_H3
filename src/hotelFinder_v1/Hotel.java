package hotelFinder_v1;

import java.util.ArrayList;


public class Hotel {
	private int id;
	private String name;
	private String hotelChain;
	private String city;
	private ArrayList<HotelRoom> hotelRooms;
	private ArrayList<Facility> hotelFacilities;

	public Hotel(int id, String name, String city, String hotelChain, ArrayList<HotelRoom> hotelRooms, ArrayList<Facility> hotelFacilities){
		super();
		this.id = id;
		this.name = name;
		this.hotelChain = hotelChain;
		this.city = city;
		this.hotelRooms = hotelRooms;
		this.hotelFacilities = hotelFacilities;
	}
	public ArrayList<HotelRoom> getHotelRooms(){
		return hotelRooms;
	}
	
	public ArrayList<Facility> getFacilities(){
		return hotelFacilities;
	}
	
	public int getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}

	public String getCity()
	{
		return city;
	}
	
	public String getChain()
	{
		return hotelChain;
	}

	

	/*             MEÐAN VIÐ NOTUM EKKI LOCATION
	public void setLocation(String city, String address, double distFromCenter)
	{
		this.hotelLocation = new Location(city, address, distFromCenter);
	}
	
	public Location getLocation()
	{
		return hotelLocation;
	}
	*/
	@Override
	public String toString(){
		return "[ id : " + id + " ]"   + "\n" +  "[ name : " + name + " ]" + "\n" + "[ city : " + city + " ]" + "\n";
	}
	
	
	
	
}
/*
//-----------------------------------------------------------------------------------------------	
	private int hotelYear;
	
	public void setHotelYear(int year)
	{
		hotelYear = year;
	}
	public int getHotelYear(){
		System.out.println("This was build in: " + hotelYear);
		return hotelYear;
	}
//-----------------------------------------------------------------------------------------------	
//-----------------------------------------------------------------------------------------------
	
	public Hotel(String name)
	{
		//Constructur sem tekur nafn inn i object
		System.out.println("Passed Name is: " + name);
	}
//-----------------------------------------------------------------------------------------------
	
	public static void main(String []args)
	{
		//following statement will create an object of myName
		Hotel Fjorukrain = new Hotel("fjorukrain");
		
		//Sitjum byggingarar a hotel: 2009
		Fjorukrain.setHotelYear(2009);
		
		// saekjum sidan bygginarar hotel fjorukrain.
		Fjorukrain.getHotelYear();
		
		//Haegt er ad saekja gognin strax svona:
		System.out.println("Variable Value: " + Fjorukrain.hotelYear);
		
	}
	*/
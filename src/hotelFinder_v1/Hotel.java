package hotelFinder_v1;
// En ekki í gegnum sql.
// Author: Óðinn Helgason
// Hér ætla ég að gera hotel test classa sem heldur utan um object af "öllum hótelunum okkar"

import java.util.ArrayList;

// er med stillt a enskt keyboard og laga oll comment og faeri yfir a isl.
	
//id
//hotel
//name
//created
public class Hotel {
	private int id;
	private String name;
	//private String hotelkedja;
	private String city;
	//private int year;
	private Location hotelLocation;
	private ArrayList<HotelRoom> hotelRooms;
	
	// Hotel constructor
	public Hotel(int id, String name, String city, ArrayList<HotelRoom> hotelRooms){
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.hotelRooms = hotelRooms;
	}
	public ArrayList<HotelRoom> getHotelRooms(){
		return this.hotelRooms;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public void setCity(String city)
	{
		this.city = city;
	}
	
	public void setLocation(String city, String address, double distFromCenter)
	{
		this.hotelLocation = new Location(city, address, distFromCenter);
	}
	
	public Location getLocation()
	{
		return hotelLocation;
	}
	
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
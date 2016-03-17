package hotelFinder_v1;

public class HotelRoom {
	private int id;
	private int numPersons;
	private int rate;
	
	
	public HotelRoom(int id, int numPersons, int rate){
		this.id = id;
		this.numPersons = numPersons;
		this.rate = rate;
	}
	
	public int getRate(){
		return this.rate;
	}
	public int getNumPerson(){
		return this.numPersons;		
	}
	public int getId(){
		return this.id;	
	}
	
}

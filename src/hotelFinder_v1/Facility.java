package hotelFinder_v1;

public class Facility {
	private int type;
	private String name;
	private String description;
	
	public Facility(int type, String name, String description){
		this.type = type;
		this.name = name;
		this.description = description;
	}
	
	public int getType(){
		return type;
	}
	public String getName(){
		return name;
	}
	public String getDescription(){
		return description;
	}
}

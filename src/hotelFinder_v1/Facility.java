package hotelFinder_v1;

public class Facility {
	int type;
	String name;
	String description;
	
	public int getType(){
		return type;
	}
	public String getName(){
		return name;
	}
	public String getDescription(){
		return description;
	}
	public void setType(int type){
		this.type = type;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setDescription(String description){
		this.description = description;
	}
}

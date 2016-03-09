package hotelFinder_v1;

public class RoomType {
	String typeName;
	int noOfBeds;
	int personCapacity;
	String view;
	
	public String getTypeName(){
		return typeName;
	}
	public int getNoOfBeds(){
		return noOfBeds;
	}
	public int getPersonCapacity(){
		return personCapacity;
	}
	public String getView(){
		return view;
	}
	public void setTypeName(String typeName){
		this.typeName = typeName;
	}
	public void setNoOfBeds(int noOfBeds){
		this.noOfBeds = noOfBeds;
	}
	public void setPersonCapacity(int personCapacity){
		this.personCapacity = personCapacity;
	}
	public void setView(String view){
		this.view = view;
	}

}

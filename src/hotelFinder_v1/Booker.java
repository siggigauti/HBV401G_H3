package hotelFinder_v1;
import java.sql.Date;

public class Booker {
	DBconnect conn;
	
	public Booker(){
		conn  = new DBconnect();
	}
	
	public void book(Hotel hotel, int roomID, Date date1, Date date2){
		int hotelID = hotel.getId();
		conn.insertQueryDatabase( "{call createBooking(?,?,?,?)}", hotelID, roomID, date1, date2);
		//Búa til query til að tékka hvort booking fór í gegn, ef hún fór í gegn þá skilum við bookingID.
	}

	public void unbook(Hotel hotel,int BookID){
		conn.dropQueryDatabase( "{call dropBooking(?)}", BookID);
	}

}

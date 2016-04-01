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
		//B�a til query til a� t�kka hvort booking f�r � gegn, ef h�n f�r � gegn �� skilum vi� bookingID.
	}

	public void unbook(Hotel hotel,int BookID){
		conn.dropQueryDatabase( "{call dropBooking(?)}", BookID);
	}

}

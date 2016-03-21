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
	}
	//Vantar enn function til að eyða bókun.
}

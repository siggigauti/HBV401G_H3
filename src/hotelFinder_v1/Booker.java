package hotelFinder_v1;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Booker {
	DBconnect conn;
	
	public Booker(){
		conn  = new DBconnect();
	}
	
	public void book(int hotelID, int roomID, Date date1, Date date2){
		conn.insertQueryDatabase( "{call createBooking(?,?,?,?)}", hotelID, roomID, date1, date2);
	}
	//Vantar enn function til að eyða bókun.
}

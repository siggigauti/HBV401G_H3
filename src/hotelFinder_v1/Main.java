package hotelFinder_v1;

import java.util.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Main {
	
	private static int counter;
	private static int counterValue;
	public static void main(String[] args) throws SQLException{
		
		//Eftirfarandi forritsbútur finnur öll lausu herbergin í hóteli með hotelID = 1 og setur öll herbergin og hótelið upp í hluti.
		//Hlutirnir eru geymdir í ArrayList af Hotel hlutum.
		//Herbergin eru fundin með stored procedure sem heitir freeRoomsFromToAll(hotelID, checkindate, checkoutdate).
		//Hún skilar bara herbergjum á einu hóteli en ég er búinn að prófa classconstructorinn fyrir fleiri en 1 hótel og hann virkar fínt
		//það væri samt gott að geta minnkað gögnin eitthvað sem kemur frá sql og fiffað classconstructorinn til í samræmi við það.
		//Ef það heppnast ekki þá erum við samt með classconstructor sem virkar fínt! :)
		int hotelID = 1;
		String checkInDate = "2016-05-06", checkOutDate = "2016-05-08";
		HotelFinder hotelfinder = new HotelFinder();		
		ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromHotel(hotelID, checkInDate, checkInDate);
		for (int i = 0; i < result.size(); i++) {
			System.out.println("####################################################");
			System.out.println("Nafn á hóteli: " + result.get(i).getName());
			System.out.println("Staðsetning hótels: " + result.get(i).getCity());
			System.out.println("####################################################");
			for (int j = 0; j < result.get(i).getHotelRooms().size() ; j++) {
				//Prentar út öll hótelherbergin sem komu út í því hóteli.
				System.out.println("-------| Herbergjanúmer á lausu herbergi: " + result.get(i).getHotelRooms().get(j).getId() );
				System.out.println("-------| Pláss fyrir: " + result.get(i).getHotelRooms().get(j).getNumPerson() +" manneskjur."  );
			}	
		}
	}
}

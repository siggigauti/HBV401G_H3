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
		
		/*Hotel Fjorukrain = new Hotel(1, "Fjorukrain", "Hafnarfjordur");
		System.out.println(Fjorukrain.toString());
		
		
		Hotel Vik = new Hotel(2, "Vik", "Reykjavik");
		System.out.println(Vik.toString());
		*/
		
		//buum til array med objects.
		//Fyrir hotels sem inniheldur 0ll okkar hotel.
		//Fyrir Fundin hotel i leitarvelinni okkar.
		Hotel[] hotels = new Hotel[4];
		Hotel[] SearchResult = new Hotel[4];
		HotelRoom[] testRooms = new HotelRoom[2];
		
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
		
		
		/*
		//fyllum sidan inni arrayin.
		hotels[0] = new Hotel(1, "Fjorukrain", "Hafnarfjordur");
		hotels[1] = new Hotel(2, "Vik", "Reykjavik");
		hotels[2] = new Hotel(3, "Akur", "Akureyri");
		hotels[3] = new Hotel(4, "Reykur", "Reykjavik");
		*/
		//TestComment
		
		//prentar ut objectin
		/*for(int i = 0; i<hotels.length; i++)
		{
			System.out.println(hotels[i].toString());
		}
		*/
		
		//LeitarGLUGGINN
		//String search = "adaw";
		
		//test til a[ tjekka hvort getname virkar 
		//System.out.println("hotel[0].getName: " + hotels[2].getCity());
		
		/*
		boolean found = false;
		for(int i = 0; i<hotels.length; i++)
		{
			if(hotels[i].getName() != null && hotels[i].getName().matches(search) ||
			   hotels[i].getCity() != null && hotels[i].getCity().matches(search))
			{
				counterValue++;
				SearchResult[counter++] = hotels[i];
				found = true;
			}
		}

		
		
		
		
		
		//Tjekk ef leitinn hafi heppnast eda ekki.
		if(found == true)
		{
			//Nidurstadan hvad vid leitudum eftir og hvad vid fundum.
			System.out.println("Searched for: " + search);
			
			//Vid fundum hvad margar nidurstodur.
			System.out.println("We found: " + counter + " items");
			
			
			for(int b = 0; b < SearchResult.length; b++)
			{
				System.out.println(SearchResult[b]);
			}
		}
		//Ef vid finnum ekki neitt.
		else
		{
			System.out.println("Didnt find anything that matches: " + search);
		}
		
		*/
		
		
		
	}
}

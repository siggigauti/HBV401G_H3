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
		
		//Eftirfarandi forritsb�tur finnur �ll lausu herbergin � h�teli me� hotelID = 1 og setur �ll herbergin og h�teli� upp � hluti.
		//Hlutirnir eru geymdir � ArrayList af Hotel hlutum.
		//Herbergin eru fundin me� stored procedure sem heitir freeRoomsFromToAll(hotelID, checkindate, checkoutdate).
		//H�n skilar bara herbergjum � einu h�teli en �g er b�inn a� pr�fa classconstructorinn fyrir fleiri en 1 h�tel og hann virkar f�nt
		//�a� v�ri samt gott a� geta minnka� g�gnin eitthva� sem kemur fr� sql og fiffa� classconstructorinn til � samr�mi vi� �a�.
		//Ef �a� heppnast ekki �� erum vi� samt me� classconstructor sem virkar f�nt! :)
		int hotelID = 1;
		String checkInDate = "2016-05-06", checkOutDate = "2016-05-08";
		HotelFinder hotelfinder = new HotelFinder();		
		ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromHotel(hotelID, checkInDate, checkInDate);
		for (int i = 0; i < result.size(); i++) {
			System.out.println("####################################################");
			System.out.println("Nafn � h�teli: " + result.get(i).getName());
			System.out.println("Sta�setning h�tels: " + result.get(i).getCity());
			System.out.println("####################################################");
			for (int j = 0; j < result.get(i).getHotelRooms().size() ; j++) {
				//Prentar �t �ll h�telherbergin sem komu �t � �v� h�teli.
				System.out.println("-------| Herbergjan�mer � lausu herbergi: " + result.get(i).getHotelRooms().get(j).getId() );
				System.out.println("-------| Pl�ss fyrir: " + result.get(i).getHotelRooms().get(j).getNumPerson() +" manneskjur."  );
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

package hotelFinder_v1;


import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) throws SQLException{
		
		int whatToCall = 1; 
		String checkInDate = "2016-05-06", checkOutDate = "2016-05-08";
		if(whatToCall == 0){
			//Eftirfarandi forritsb�tur finnur �ll lausu herbergin � h�teli me� hotelID = 1 og setur �ll herbergin og h�teli� upp � hluti.
			//Hlutirnir eru geymdir � ArrayList af Hotel hlutum.
			//Herbergin eru fundin me� stored procedure sem heitir freeRoomsFromToAll(hotelID, checkindate, checkoutdate).
			//H�n skilar bara herbergjum � einu h�teli en �g er b�inn a� pr�fa classconstructorinn fyrir fleiri en 1 h�tel og hann virkar f�nt
			//�a� v�ri samt gott a� geta minnka� g�gnin eitthva� sem kemur fr� sql og fiffa� classconstructorinn til � samr�mi vi� �a�.
			//Ef �a� heppnast ekki �� erum vi� samt me� classconstructor sem virkar f�nt! :)
			int hotelID = 2;
			
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
		}
		else if(whatToCall == 1){
			//Veldu dags h�r a� ne�an, �etta *�tti* a� b�a til �ll h�tel sem eru me� laus herbergi � timabilinu.
			//Endilega debuggi� og t�kki� hvort �i� geti� broti� �etta. Ekki optimal k��i en virkar.
			HotelFinder hotelfinder = new HotelFinder();		
			ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromAnyHotel( checkInDate, checkInDate);
			for (int i = 0; i < result.size(); i++) {
				System.out.println("####################################################");
				System.out.println("Nafn � h�teli: " + result.get(i).getName());
				System.out.println("Sta�setning h�tels: " + result.get(i).getCity());
				System.out.println("Hotel ID er: " + result.get(i).getId());
				System.out.println("####################################################");
				for (int j = 0; j < result.get(i).getHotelRooms().size() ; j++) {
					//Prentar �t �ll h�telherbergin sem komu �t � �v� h�teli.
					System.out.println("-------| Herbergjan�mer � lausu herbergi: " + result.get(i).getHotelRooms().get(j).getId() );
					System.out.println("-------| Pl�ss fyrir: " + result.get(i).getHotelRooms().get(j).getNumPerson() +" manneskjur."  );
				}	
			}
		}
		
	}
}

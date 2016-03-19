package hotelFinder_v1;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) throws SQLException{
		
		//WhatToCall = 0 -> Leita eftir hótelnafni
		//WhatToCall = 1 -> Leita í öllum hótelum
		//WhatToCall = 2 -> Leita eftir staðsetningu
		//WhatToCall = 3 -> Leita eftir hótelkeðju
		int whatToCall = 3; 
		
		//Hér fyrir neðan eru breytur sem við getum notað til að þrengja leitina.
		String checkInDate = "2016-05-06", checkOutDate = "2016-05-08";
		String hotelName = "Hótel Reykjavík", hotelChain = "Radison", hotelLocation = "Reykjavík";
		
		//Leitum eftir hótelnafni
		if(whatToCall == 0){
			System.out.println("Leitum í hótelum með hótelnafninu: " + hotelName);
			HotelFinder hotelfinder = new HotelFinder();		
			ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromHotel(hotelName, checkInDate, checkInDate);
			for (int i = 0; i < result.size(); i++) {
				System.out.println("####################################################");
				System.out.println("Nafn á hóteli: " + result.get(i).getName());
				System.out.println("Staðsetning hótels: " + result.get(i).getCity());
				System.out.println("Hótelkeðja: " + result.get(i).getChain());
				System.out.println("Hotel ID er: " + result.get(i).getId());
				System.out.println("####################################################");
				for (int j = 0; j < result.get(i).getHotelRooms().size() ; j++) {
					//Prentar út öll hótelherbergin sem komu út í því hóteli.
					System.out.println("-------| Herbergjanúmer á lausu herbergi: " + result.get(i).getHotelRooms().get(j).getId() );
					System.out.println("-------| Pláss fyrir: " + result.get(i).getHotelRooms().get(j).getNumPerson() +" manneskjur."  );
				}	
			}
		}
		//Leitum í öllum hótelum
		else if(whatToCall == 1){
			System.out.println("Leitum í öllum hótelum");
			HotelFinder hotelfinder = new HotelFinder();		
			ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromAnyHotel( checkInDate, checkInDate);
			for (int i = 0; i < result.size(); i++) {
				System.out.println("####################################################");
				System.out.println("Nafn á hóteli: " + result.get(i).getName());
				System.out.println("Staðsetning hótels: " + result.get(i).getCity());
				System.out.println("Hótelkeðja: " + result.get(i).getChain());
				System.out.println("Hotel ID er: " + result.get(i).getId());
				System.out.println("####################################################");
				for (int j = 0; j < result.get(i).getHotelRooms().size() ; j++) {
					//Prentar út öll hótelherbergin sem komu út í því hóteli.
					System.out.println("-------| Herbergjanúmer á lausu herbergi: " + result.get(i).getHotelRooms().get(j).getId() );
					System.out.println("-------| Pláss fyrir: " + result.get(i).getHotelRooms().get(j).getNumPerson() +" manneskjur."  );
				}	
			}
		}
		//Leitum eftir hótel staðsetningu
		else if(whatToCall == 2){
			System.out.println("Leitum í hótelum með staðsetningu: " + hotelLocation);
			HotelFinder hotelfinder = new HotelFinder();		
			ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromHotelLocation( hotelLocation, checkInDate, checkInDate);
			for (int i = 0; i < result.size(); i++) {
				System.out.println("####################################################");
				System.out.println("Nafn á hóteli: " + result.get(i).getName());
				System.out.println("Staðsetning hótels: " + result.get(i).getCity());
				System.out.println("Hótelkeðja: " + result.get(i).getChain());
				System.out.println("Hotel ID er: " + result.get(i).getId());
				System.out.println("####################################################");
				for (int j = 0; j < result.get(i).getHotelRooms().size() ; j++) {
					//Prentar út öll hótelherbergin sem komu út í því hóteli.
					System.out.println("-------| Herbergjanúmer á lausu herbergi: " + result.get(i).getHotelRooms().get(j).getId() );
					System.out.println("-------| Pláss fyrir: " + result.get(i).getHotelRooms().get(j).getNumPerson() +" manneskjur."  );
				}	
			}
		}
		//Leitum eftir hótelkeðju
		else if(whatToCall == 3){
			System.out.println("Leitum í hótelum með hótelkeðju: " + hotelChain);
			HotelFinder hotelfinder = new HotelFinder();		
			ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromHotelChain(hotelChain, checkInDate, checkInDate);
			for (int i = 0; i < result.size(); i++) {
				System.out.println("####################################################");
				System.out.println("Nafn á hóteli: " + result.get(i).getName());
				System.out.println("Staðsetning hótels: " + result.get(i).getCity());
				System.out.println("Hótelkeðja: " + result.get(i).getChain());
				System.out.println("Hotel ID er: " + result.get(i).getId());
				System.out.println("####################################################");
				for (int j = 0; j < result.get(i).getHotelRooms().size() ; j++) {
					//Prentar út öll hótelherbergin sem komu út í því hóteli.
					System.out.println("-------| Herbergjanúmer á lausu herbergi: " + result.get(i).getHotelRooms().get(j).getId() );
					System.out.println("-------| Pláss fyrir: " + result.get(i).getHotelRooms().get(j).getNumPerson() +" manneskjur."  );
				}	
			}
		}	
	}
}

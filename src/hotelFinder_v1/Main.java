package hotelFinder_v1;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) throws SQLException{
		
		//WhatToCall = 0 -> Leita eftir hótelnafni
		//WhatToCall = 1 -> Leita í öllum hótelum
		//WhatToCall = 2 -> Leita eftir staðsetningu
		//WhatToCall = 3 -> Leita eftir hótelkeðju
		//WhatToCall = 4 -> Leita eftir substring í hótelnafni.
		int whatToCall = 1; 
		
		//Hér fyrir neðan eru breytur sem við getum notað til að þrengja leitina.
		String checkInDate = "2016-05-06", checkOutDate = "2016-05-08";
		String hotelName = "Hótel Reykjavík", hotelChain = "Radison", hotelLocation = "Reykjavík", hotelNameSubString = "Reyk";
				
		//Leitum eftir hótelnafni
		if(whatToCall == 0){
			System.out.println("Leitum í hótelum með hótelnafninu: " + hotelName);
			HotelFinder hotelfinder = new HotelFinder();		
			ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromHotel(hotelName, checkInDate, checkOutDate);
			printHotelArrayInfo( result );
		}
		//Leitum í öllum hótelum
		else if(whatToCall == 1){
			System.out.println("Leitum í öllum hótelum");
			HotelFinder hotelfinder = new HotelFinder();		
			ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromAnyHotel( checkInDate, checkOutDate);
			printHotelArrayInfo( result );
		}
		//Leitum eftir hótel staðsetningu
		else if(whatToCall == 2){
			System.out.println("Leitum í hótelum með staðsetningu: " + hotelLocation);
			HotelFinder hotelfinder = new HotelFinder();		
			ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromHotelLocation( hotelLocation, checkInDate, checkOutDate);
			printHotelArrayInfo( result );
		}
		//Leitum eftir hótelkeðju
		else if(whatToCall == 3){
			System.out.println("Leitum í hótelum með hótelkeðju: " + hotelChain);
			HotelFinder hotelfinder = new HotelFinder();		
			ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromHotelChain(hotelChain, checkInDate, checkOutDate);
			printHotelArrayInfo( result );
		}
		//Leitum eftir öllum hótelum með ákveðinn streng í nafninu. (Veit ekkert hvort þetta geti gagnast okkur,  fannst þetta bara kúl fídus)
		else if(whatToCall == 4){
			System.out.println("Leitum í hótelum sem innihalda " + hotelNameSubString + " í hótel nafninu.");
			HotelFinder hotelfinder = new HotelFinder();		
			ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromAnyHotelSubString(hotelNameSubString, checkInDate, checkOutDate);
			printHotelArrayInfo( result );
		}	
	}
	
	private static void printHotelArrayInfo( ArrayList<Hotel> hotelArray ){
		
		for (int i = 0; i < hotelArray.size(); i++) {
			System.out.println("\n####################################################\n");
			System.out.println("|----------------------------------------------|");
			System.out.println("|--------|    Upplýsingar um hótel    |--------|");
			System.out.println("|----------------------------------------------|");
			System.out.println("|------| Nafn á hóteli: " + hotelArray.get(i).getName());
			System.out.println("|------| Staðsetning hótels: " + hotelArray.get(i).getCity());
			System.out.println("|------| Hótelkeðja: " + hotelArray.get(i).getChain());
			System.out.println("|------| Hotel ID er: " + hotelArray.get(i).getId());
			System.out.println("|----------------------------------------------|");
			System.out.println("|--------|     Facilities í boði      |--------|");
			System.out.println("|----------------------------------------------|");
			for (int j = 0; j < hotelArray.get(i).getFacilities().size() ; j++) {
				System.out.println("|------| Facility: " + hotelArray.get(i).getFacilities().get(j).getName());
				System.out.println("|------| Lýsing: " + hotelArray.get(i).getFacilities().get(j).getDescription());
				System.out.println("|------|");
			}	
			
			System.out.println("|----------------------------------------------|");
			System.out.println("|------|  Upplýsingar um laus herbergi  |------|");
			System.out.println("|----------------------------------------------|");
			for (int j = 0; j < hotelArray.get(i).getHotelRooms().size() ; j++) {
				//Prentar út öll hótelherbergin sem komu út í því hóteli.
				System.out.println("|------| Herbergjanúmer: " + hotelArray.get(i).getHotelRooms().get(j).getId() );
				System.out.println("|------| Pláss fyrir: " + hotelArray.get(i).getHotelRooms().get(j).getNumPerson() +" manneskjur."  );
			}			
		}	
	}	
}

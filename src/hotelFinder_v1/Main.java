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
		//WhatToCall = 5 -> bóka eftir hotel og roomID.
		int whatToCall = 1; 
		int hotelNR = 1; //Þetta er hvaða hótel úr listanum á að bóka úr.
		int roomID = 11; //Þetta er roomID á því herbergi sem á að bóka, hendir villu ef roomID passar ekki við eitthvað herbergi.
		
		//Hér er listin af hótelunum sem eru með laus herbergi.
		ArrayList<Hotel> result = new ArrayList<Hotel>();
		
		//Hér fyrir neðan eru breytur sem við getum notað til að þrengja leitina.
		String checkInDate = "2016-05-06", checkOutDate = "2016-05-08";
		String hotelName = "Hótel Reykjavík", hotelChain = "Radison", hotelLocation = "Reykjavík", hotelNameSubString = "Reyk";
		HotelFinder hotelfinder = new HotelFinder(checkInDate, checkOutDate);
		
		//Leitum eftir hótelnafni
		if(whatToCall == 0){
			System.out.println("Leitum í hótelum með hótelnafninu: " + hotelName);
			result = hotelfinder.getFreeRoomsFromHotel(hotelName);
			printHotelArrayInfo( result, checkInDate, checkOutDate );
		}
		//Leitum í öllum hótelum
		else if(whatToCall == 1){
			System.out.println("Leitum í öllum hótelum");
			result = hotelfinder.getFreeRoomsFromAnyHotel();
			printHotelArrayInfo( result, checkInDate, checkOutDate );
		}
		//Leitum eftir hótel staðsetningu
		else if(whatToCall == 2){
			System.out.println("Leitum í hótelum með staðsetningu: " + hotelLocation);
			result = hotelfinder.getFreeRoomsFromHotelLocation( hotelLocation);
			printHotelArrayInfo( result, checkInDate, checkOutDate );
		}
		//Leitum eftir hótelkeðju
		else if(whatToCall == 3){
			System.out.println("Leitum í hótelum með hótelkeðju: " + hotelChain);
			result = hotelfinder.getFreeRoomsFromHotelChain(hotelChain);
			printHotelArrayInfo( result, checkInDate, checkOutDate );
		}
		//Leitum eftir öllum hótelum með ákveðinn streng í nafninu. (Veit ekkert hvort þetta geti gagnast okkur,  fannst þetta bara kúl fídus)
		else if(whatToCall == 4){
			System.out.println("Leitum í hótelum sem innihalda " + hotelNameSubString + " í hótel nafninu.");
			result = hotelfinder.getFreeRoomsFromAnyHotelSubString(hotelNameSubString);
			printHotelArrayInfo( result, checkInDate, checkOutDate );
		}	
		else if(whatToCall == 5){
			System.out.println("Ætlum að bóka. HótelID: "+result.get(hotelNR).getId()+" herbergi: "+roomID+". Þetta er manual núna.");
			hotelfinder.book(result.get(hotelNR), roomID);
		}
	}
	
	private static void printHotelArrayInfo( ArrayList<Hotel> hotelArray, String checkInDate, String checkOutDate ){
		
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
			if(hotelArray.get(i).getFacilities().size() == 0){
				System.out.println("|------| Þetta hótel hefur engin skráð facilities.");
			}
			else {
				for (int j = 0; j < hotelArray.get(i).getFacilities().size() ; j++) {
					System.out.println("|------| Facility: " + hotelArray.get(i).getFacilities().get(j).getName());
					System.out.println("|------| Lýsing: " + hotelArray.get(i).getFacilities().get(j).getDescription());
					System.out.println("|------|");
				}
			}	
			
			System.out.println("|----------------------------------------------|");
			System.out.println("|------|  Upplýsingar um laus herbergi  |------|");
			System.out.println("|----------------------------------------------|");
			if(hotelArray.get(i).getHotelRooms().size() == 0){
				System.out.println("|------| Þetta hótel hefur engin laus herbergi á gefnu tímabili: "+checkInDate+" til "+checkOutDate);
			}
			else{
				for (int j = 0; j < hotelArray.get(i).getHotelRooms().size() ; j++) {
					//Prentar út öll hótelherbergin sem komu út í því hóteli.
					System.out.println("|------| Herbergjanúmer: " + hotelArray.get(i).getHotelRooms().get(j).getId() );
					System.out.println("|------| Pláss fyrir: " + hotelArray.get(i).getHotelRooms().get(j).getNumPerson() +" manneskjur."  );
				}	
			}	
		}	
	}	
}

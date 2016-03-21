package hotelFinder_v1;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) throws SQLException{
		
		//WhatToCall = 0 -> Leita eftir h�telnafni
		//WhatToCall = 1 -> Leita � �llum h�telum
		//WhatToCall = 2 -> Leita eftir sta�setningu
		//WhatToCall = 3 -> Leita eftir h�telke�ju
		//WhatToCall = 4 -> Leita eftir substring � h�telnafni.
		int whatToCall = 1; 
		
		//H�r fyrir ne�an eru breytur sem vi� getum nota� til a� �rengja leitina.
		String checkInDate = "2016-05-06", checkOutDate = "2016-05-08";
		String hotelName = "H�tel Reykjav�k", hotelChain = "Radison", hotelLocation = "Reykjav�k", hotelNameSubString = "Reyk";
				
		//Leitum eftir h�telnafni
		if(whatToCall == 0){
			System.out.println("Leitum � h�telum me� h�telnafninu: " + hotelName);
			HotelFinder hotelfinder = new HotelFinder();		
			ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromHotel(hotelName, checkInDate, checkOutDate);
			printHotelArrayInfo( result );
		}
		//Leitum � �llum h�telum
		else if(whatToCall == 1){
			System.out.println("Leitum � �llum h�telum");
			HotelFinder hotelfinder = new HotelFinder();		
			ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromAnyHotel( checkInDate, checkOutDate);
			printHotelArrayInfo( result );
		}
		//Leitum eftir h�tel sta�setningu
		else if(whatToCall == 2){
			System.out.println("Leitum � h�telum me� sta�setningu: " + hotelLocation);
			HotelFinder hotelfinder = new HotelFinder();		
			ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromHotelLocation( hotelLocation, checkInDate, checkOutDate);
			printHotelArrayInfo( result );
		}
		//Leitum eftir h�telke�ju
		else if(whatToCall == 3){
			System.out.println("Leitum � h�telum me� h�telke�ju: " + hotelChain);
			HotelFinder hotelfinder = new HotelFinder();		
			ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromHotelChain(hotelChain, checkInDate, checkOutDate);
			printHotelArrayInfo( result );
		}
		//Leitum eftir �llum h�telum me� �kve�inn streng � nafninu. (Veit ekkert hvort �etta geti gagnast okkur,  fannst �etta bara k�l f�dus)
		else if(whatToCall == 4){
			System.out.println("Leitum � h�telum sem innihalda " + hotelNameSubString + " � h�tel nafninu.");
			HotelFinder hotelfinder = new HotelFinder();		
			ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromAnyHotelSubString(hotelNameSubString, checkInDate, checkOutDate);
			printHotelArrayInfo( result );
		}	
	}
	
	private static void printHotelArrayInfo( ArrayList<Hotel> hotelArray ){
		
		for (int i = 0; i < hotelArray.size(); i++) {
			System.out.println("\n####################################################\n");
			System.out.println("|----------------------------------------------|");
			System.out.println("|--------|    Uppl�singar um h�tel    |--------|");
			System.out.println("|----------------------------------------------|");
			System.out.println("|------| Nafn � h�teli: " + hotelArray.get(i).getName());
			System.out.println("|------| Sta�setning h�tels: " + hotelArray.get(i).getCity());
			System.out.println("|------| H�telke�ja: " + hotelArray.get(i).getChain());
			System.out.println("|------| Hotel ID er: " + hotelArray.get(i).getId());
			System.out.println("|----------------------------------------------|");
			System.out.println("|--------|     Facilities � bo�i      |--------|");
			System.out.println("|----------------------------------------------|");
			for (int j = 0; j < hotelArray.get(i).getFacilities().size() ; j++) {
				System.out.println("|------| Facility: " + hotelArray.get(i).getFacilities().get(j).getName());
				System.out.println("|------| L�sing: " + hotelArray.get(i).getFacilities().get(j).getDescription());
				System.out.println("|------|");
			}	
			
			System.out.println("|----------------------------------------------|");
			System.out.println("|------|  Uppl�singar um laus herbergi  |------|");
			System.out.println("|----------------------------------------------|");
			for (int j = 0; j < hotelArray.get(i).getHotelRooms().size() ; j++) {
				//Prentar �t �ll h�telherbergin sem komu �t � �v� h�teli.
				System.out.println("|------| Herbergjan�mer: " + hotelArray.get(i).getHotelRooms().get(j).getId() );
				System.out.println("|------| Pl�ss fyrir: " + hotelArray.get(i).getHotelRooms().get(j).getNumPerson() +" manneskjur."  );
			}			
		}	
	}	
}

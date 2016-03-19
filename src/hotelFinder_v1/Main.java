package hotelFinder_v1;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) throws SQLException{
		
		//WhatToCall = 0 -> Leita eftir h�telnafni
		//WhatToCall = 1 -> Leita � �llum h�telum
		//WhatToCall = 2 -> Leita eftir sta�setningu
		//WhatToCall = 3 -> Leita eftir h�telke�ju
		int whatToCall = 3; 
		
		//H�r fyrir ne�an eru breytur sem vi� getum nota� til a� �rengja leitina.
		String checkInDate = "2016-05-06", checkOutDate = "2016-05-08";
		String hotelName = "H�tel Reykjav�k", hotelChain = "Radison", hotelLocation = "Reykjav�k";
		
		//Leitum eftir h�telnafni
		if(whatToCall == 0){
			System.out.println("Leitum � h�telum me� h�telnafninu: " + hotelName);
			HotelFinder hotelfinder = new HotelFinder();		
			ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromHotel(hotelName, checkInDate, checkInDate);
			for (int i = 0; i < result.size(); i++) {
				System.out.println("####################################################");
				System.out.println("Nafn � h�teli: " + result.get(i).getName());
				System.out.println("Sta�setning h�tels: " + result.get(i).getCity());
				System.out.println("H�telke�ja: " + result.get(i).getChain());
				System.out.println("Hotel ID er: " + result.get(i).getId());
				System.out.println("####################################################");
				for (int j = 0; j < result.get(i).getHotelRooms().size() ; j++) {
					//Prentar �t �ll h�telherbergin sem komu �t � �v� h�teli.
					System.out.println("-------| Herbergjan�mer � lausu herbergi: " + result.get(i).getHotelRooms().get(j).getId() );
					System.out.println("-------| Pl�ss fyrir: " + result.get(i).getHotelRooms().get(j).getNumPerson() +" manneskjur."  );
				}	
			}
		}
		//Leitum � �llum h�telum
		else if(whatToCall == 1){
			System.out.println("Leitum � �llum h�telum");
			HotelFinder hotelfinder = new HotelFinder();		
			ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromAnyHotel( checkInDate, checkInDate);
			for (int i = 0; i < result.size(); i++) {
				System.out.println("####################################################");
				System.out.println("Nafn � h�teli: " + result.get(i).getName());
				System.out.println("Sta�setning h�tels: " + result.get(i).getCity());
				System.out.println("H�telke�ja: " + result.get(i).getChain());
				System.out.println("Hotel ID er: " + result.get(i).getId());
				System.out.println("####################################################");
				for (int j = 0; j < result.get(i).getHotelRooms().size() ; j++) {
					//Prentar �t �ll h�telherbergin sem komu �t � �v� h�teli.
					System.out.println("-------| Herbergjan�mer � lausu herbergi: " + result.get(i).getHotelRooms().get(j).getId() );
					System.out.println("-------| Pl�ss fyrir: " + result.get(i).getHotelRooms().get(j).getNumPerson() +" manneskjur."  );
				}	
			}
		}
		//Leitum eftir h�tel sta�setningu
		else if(whatToCall == 2){
			System.out.println("Leitum � h�telum me� sta�setningu: " + hotelLocation);
			HotelFinder hotelfinder = new HotelFinder();		
			ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromHotelLocation( hotelLocation, checkInDate, checkInDate);
			for (int i = 0; i < result.size(); i++) {
				System.out.println("####################################################");
				System.out.println("Nafn � h�teli: " + result.get(i).getName());
				System.out.println("Sta�setning h�tels: " + result.get(i).getCity());
				System.out.println("H�telke�ja: " + result.get(i).getChain());
				System.out.println("Hotel ID er: " + result.get(i).getId());
				System.out.println("####################################################");
				for (int j = 0; j < result.get(i).getHotelRooms().size() ; j++) {
					//Prentar �t �ll h�telherbergin sem komu �t � �v� h�teli.
					System.out.println("-------| Herbergjan�mer � lausu herbergi: " + result.get(i).getHotelRooms().get(j).getId() );
					System.out.println("-------| Pl�ss fyrir: " + result.get(i).getHotelRooms().get(j).getNumPerson() +" manneskjur."  );
				}	
			}
		}
		//Leitum eftir h�telke�ju
		else if(whatToCall == 3){
			System.out.println("Leitum � h�telum me� h�telke�ju: " + hotelChain);
			HotelFinder hotelfinder = new HotelFinder();		
			ArrayList<Hotel> result = hotelfinder.getFreeRoomsFromHotelChain(hotelChain, checkInDate, checkInDate);
			for (int i = 0; i < result.size(); i++) {
				System.out.println("####################################################");
				System.out.println("Nafn � h�teli: " + result.get(i).getName());
				System.out.println("Sta�setning h�tels: " + result.get(i).getCity());
				System.out.println("H�telke�ja: " + result.get(i).getChain());
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

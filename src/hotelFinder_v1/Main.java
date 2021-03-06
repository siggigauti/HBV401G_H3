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
		//WhatToCall = 5 -> b�ka eftir hotel og roomID.
		//WhatToCall = 6 -> Leita a� h�teli me� �kve�in facilities.
		int whatToCall = 1; 
		int hotelNR = 2; //�etta er hva�a h�tel �r listanum � a� b�ka �r.
		int roomID = 3; //�etta er roomID � �v� herbergi sem � a� b�ka, hendir villu ef roomID passar ekki vi� eitthva� herbergi.
		
		//H�r er listin af h�telunum sem eru me� laus herbergi.
		ArrayList<Hotel> result = new ArrayList<Hotel>();
		
		//H�r fyrir ne�an eru breytur sem vi� getum nota� til a� �rengja leitina.
		String checkInDate = "2016-05-06", checkOutDate = "2016-05-08";
		String hotelName = "H�tel Lagarflj�t", hotelChain = "Radison", hotelLocation = "Reykjav�k", hotelNameSubString = "Reyk";
		//Athuga hvort h�tel s� me� eftirfarandi facilities.
		int[] facilityToLookFor = {1, 2, 3, 6};
		HotelFinder hotelfinder = new HotelFinder(checkInDate, checkOutDate);
		
		//Leitum eftir h�telnafni
		if(whatToCall == 0){
			System.out.println("Leitum � h�telum me� h�telnafninu: " + hotelName);
			result = hotelfinder.getFreeRoomsFromHotel(hotelName);
			printHotelArrayInfo( result, checkInDate, checkOutDate );
		}
		//Leitum � �llum h�telum
		else if(whatToCall == 1){
			System.out.println("Leitum � �llum h�telum");
			result = hotelfinder.getFreeRoomsFromAnyHotel();
			printHotelArrayInfo( result, checkInDate, checkOutDate );
		}
		//Leitum eftir h�tel sta�setningu
		else if(whatToCall == 2){
			System.out.println("Leitum � h�telum me� sta�setningu: " + hotelLocation);
			result = hotelfinder.getFreeRoomsFromHotelLocation( hotelLocation);
			printHotelArrayInfo( result, checkInDate, checkOutDate );
		}
		//Leitum eftir h�telke�ju
		else if(whatToCall == 3){
			System.out.println("Leitum � h�telum me� h�telke�ju: " + hotelChain);
			result = hotelfinder.getFreeRoomsFromHotelChain(hotelChain);
			printHotelArrayInfo( result, checkInDate, checkOutDate );
		}
		//Leitum eftir �llum h�telum me� �kve�inn streng � nafninu. (Veit ekkert hvort �etta geti gagnast okkur,  fannst �etta bara k�l f�dus)
		else if(whatToCall == 4){
			System.out.println("Leitum � h�telum sem innihalda " + hotelNameSubString + " � h�tel nafninu.");
			result = hotelfinder.getFreeRoomsFromAnyHotelSubString(hotelNameSubString);
			printHotelArrayInfo( result, checkInDate, checkOutDate );
		}	
		else if(whatToCall == 5){
			System.out.println("�tlum a� b�ka. H�telID: "+result.get(hotelNR).getId()+" herbergi: "+roomID+". �etta er manual n�na.");
			
		}
		else if(whatToCall == 6){
			System.out.println("�tlum a� leita a� h�teli sem hefur �kve�in facilities");
			result = hotelfinder.getHotelWithFacilities(facilityToLookFor);
			printHotelArrayInfo(result, checkInDate, checkOutDate);		
		}
		//hotelfinder.book(result.get(1), 1);
		//hotelfinder.book(result.get(1), 11);
		//hotelfinder.unbook(result.get(1), 14);
		//hotelfinder.writeReview(4, 5, "K�l h�tel", "Flott h�tel", "2015-05-05", "J�n J�nsson");
	}
	
	private static void printHotelArrayInfo( ArrayList<Hotel> hotelArray, String checkInDate, String checkOutDate ){
		
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
			if(hotelArray.get(i).getFacilities().size() == 0){
				System.out.println("|------| �etta h�tel hefur engin skr�� facilities.");
			}
			else {
				for (int j = 0; j < hotelArray.get(i).getFacilities().size() ; j++) {
					System.out.println("|------| Facility: " + hotelArray.get(i).getFacilities().get(j).getName());
					System.out.println("|------| L�sing: " + hotelArray.get(i).getFacilities().get(j).getDescription());
					System.out.println("|------|");
				}
			}	
			
			System.out.println("|----------------------------------------------|");
			System.out.println("|------|  Uppl�singar um laus herbergi  |------|");
			System.out.println("|----------------------------------------------|");
			if(hotelArray.get(i).getHotelRooms().size() == 0){
				System.out.println("|------| �etta h�tel hefur engin laus herbergi � gefnu t�mabili: "+checkInDate+" til "+checkOutDate);
			}
			else{
				for (int j = 0; j < hotelArray.get(i).getHotelRooms().size() ; j++) {
					//Prentar �t �ll h�telherbergin sem komu �t � �v� h�teli.
					System.out.println("|------| Herbergjan�mer: " + hotelArray.get(i).getHotelRooms().get(j).getId() );
					System.out.println("|------| Pl�ss fyrir: " + hotelArray.get(i).getHotelRooms().get(j).getNumPerson() +" manneskjur."  );
				}	
			}
			System.out.println("|----------------------------------------------|");
			System.out.println("|------|      Umsagnir um h�teli�       |------|");
			System.out.println("|----------------------------------------------|");
			if(hotelArray.get(i).getReviews().size() == 0){
				System.out.println("|------| �etta h�tel hefur engar umsagnir");
			}
			else{
				for(int j = 0; j < hotelArray.get(i).getReviews().size(); j++){
					System.out.println("|------| Ums�gn fr�: " + hotelArray.get(i).getReviews().get(j).getReviewer());
					System.out.println("|------| Dagsetning: " + hotelArray.get(i).getReviews().get(j).getDate());
					System.out.println("|------| Stj�rnur: " + hotelArray.get(i).getReviews().get(j).getStars());
					System.out.println("|------| Titill: " + hotelArray.get(i).getReviews().get(j).getTitle());
					System.out.println("|------| Ums�gn: " + hotelArray.get(i).getReviews().get(j).getContent());
					System.out.println("|--------| ");
				}
			}	
		}	
	}	
}

package hotelFinder_v1;

import java.sql.Date;
import java.util.ArrayList;

public class HotelFinder {
	
	DBconnect conn = new DBconnect();
	
	public  ArrayList<Hotel> getHotelByName(String name, Date checkInDate, Date checkOutDate){
		
		
		//�etta return er bara temp til a� losna vi� villuna a� �a� vanti return.
		return new  ArrayList<Hotel>();
	}
	
	public ArrayList<Hotel> getHotelByID(int id, Date checkInDate, Date checkOutDate ){
		
		ArrayList<ArrayList<String>> hotelData;
		
		hotelData = conn.queryDataBase( "{call freeRoomsFromToAll(?, ?, ?)}",1 ,checkInDate, checkOutDate );	
		
		return hotelConstructor( hotelData );	
	}
	
	private ArrayList<Hotel> hotelConstructor( ArrayList<ArrayList<String>> hotelData ){
		
		String hotelName, hotelLocation, hotelChain;
		String prevHotelName = "",prevHotelLocation = "",prevHotelChain = "";
		int hotelID = -1, prevHotelID, roomID, numPersons, rate;
		//Breyta sem �kve�ur hvort eigi a� hreinsa herbergja listann e�a ekki.
		boolean clearRooms = false;
		
		ArrayList<Hotel> returnHotels = new ArrayList<Hotel>();
		ArrayList<HotelRoom> hotelRooms = new ArrayList<HotelRoom>();
		//hotelData[0] = hotelID
		//hotelData[1] = hotelname
		//hotelData[2] = hotelchain
		//hotelData[3] = hotellocation
		//hotelData[4] = roomID
		//hotelData[5] = numPersons
		//hotelData[6] = rate
		
		//Upphafstilli prevHotelID svona til �ess a� b�a ekki til h�tel
		//um lei� og lykkjan keyrir sig � gang.  H�telin eiga a� vera b�in til
		//�egar hotelID breytist e�a ef seinasta herbergi� er lesi� inn.
		prevHotelID =  Integer.parseInt(  hotelData.get(0).get(0) );
		
		for (int i = 0; i < hotelData.get(0).size(); i++) {
			
			if(clearRooms){ hotelRooms.clear();
							clearRooms = false;}
			
			/*System.out.println(hotelData.get(0).get(i));
			System.out.println(hotelData.get(1).get(i));
			System.out.println(hotelData.get(2).get(i));
			System.out.println(hotelData.get(3).get(i));
			System.out.println(hotelData.get(4).get(i));
			System.out.println(hotelData.get(5).get(i));
			System.out.println(hotelData.get(6).get(i));
			*/			
			hotelName = hotelData.get(1).get(i);
			hotelChain = hotelData.get(2).get(i);
			hotelLocation = hotelData.get(3).get(i);
			
			
			//Breytir string yfir � int �ar sem �arf.
			hotelID = Integer.parseInt(  hotelData.get(0).get(i) );
			roomID = Integer.parseInt(hotelData.get(4).get(i));
			numPersons = Integer.parseInt(hotelData.get(5).get(i));
			rate = Integer.parseInt(hotelData.get(6).get(i));
			
			//�a� �arf a� b�ta inn n�ju herbergi � hverri �trun lykkjunar.
			System.out.println("B�ti inn n�ju herbergi");
			//B�r til n�tt h�telherbergi og b�tir � lista.
			hotelRooms.add( new HotelRoom( roomID, numPersons, rate ));	
			
			//Ef hotel id breytist �� �arf a� b�a til n�jan hotel hlut
			//�v� vi� erum komnir � anna� hotel. B�um einnig til n�tt h�tel ef vi� erum komnir � seinasta herbergi�.
			if(hotelID != prevHotelID || i == hotelData.get(0).size()-1){	
				System.out.println("B�ti inn n�ju h�teli");
				//B�tum inn n�ju h�teli me� herbergjunum sem vi� bjuggum til � seinustu �trunum.
				//Setjum g�mlu gildin vegna �ess a� vi� erum a� b�a til h�tel sem er me� herbergin sem vi� vorum a�
				//b�a til � seinustu �trunum.  �egar vi� erum komnir � �ennan sta� � lykkjunni �� er komi� n�tt h�tel � HotelName.
				returnHotels.add( new Hotel( prevHotelID, prevHotelName, prevHotelLocation, hotelRooms ));	
				//Hreinsum �t hotelRooms listann okkar � n�stu �trun.
				clearRooms = true;
			}
			
							
			prevHotelID = hotelID;
			prevHotelChain = hotelChain;
			prevHotelLocation = hotelLocation;
			prevHotelName = hotelName;
			
			
		}
		
		
		
		return returnHotels;
	}
	
}

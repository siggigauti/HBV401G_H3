package hotelFinder_v1;

import java.sql.Date;
import java.util.ArrayList;

public class HotelFinder {
	
	DBconnect conn = new DBconnect();
	
	public  ArrayList<Hotel> getHotelByName(String name, Date checkInDate, Date checkOutDate){
		
		
		//Þetta return er bara temp til að losna við villuna að það vanti return.
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
		//Breyta sem ákveður hvort eigi að hreinsa herbergja listann eða ekki.
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
		
		//Upphafstilli prevHotelID svona til þess að búa ekki til hótel
		//um leið og lykkjan keyrir sig í gang.  Hótelin eiga að vera búin til
		//Þegar hotelID breytist eða ef seinasta herbergið er lesið inn.
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
			
			
			//Breytir string yfir í int þar sem þarf.
			hotelID = Integer.parseInt(  hotelData.get(0).get(i) );
			roomID = Integer.parseInt(hotelData.get(4).get(i));
			numPersons = Integer.parseInt(hotelData.get(5).get(i));
			rate = Integer.parseInt(hotelData.get(6).get(i));
			
			//Það þarf að bæta inn nýju herbergi í hverri ítrun lykkjunar.
			System.out.println("Bæti inn nýju herbergi");
			//Býr til nýtt hótelherbergi og bætir í lista.
			hotelRooms.add( new HotelRoom( roomID, numPersons, rate ));	
			
			//Ef hotel id breytist þá þarf að búa til nýjan hotel hlut
			//því við erum komnir í annað hotel. Búum einnig til nýtt hótel ef við erum komnir á seinasta herbergið.
			if(hotelID != prevHotelID || i == hotelData.get(0).size()-1){	
				System.out.println("Bæti inn nýju hóteli");
				//Bætum inn nýju hóteli með herbergjunum sem við bjuggum til í seinustu ítrunum.
				//Setjum gömlu gildin vegna þess að við erum að búa til hótel sem er með herbergin sem við vorum að
				//búa til í seinustu ítrunum.  Þegar við erum komnir á þennan stað í lykkjunni þá er komið nýtt hótel í HotelName.
				returnHotels.add( new Hotel( prevHotelID, prevHotelName, prevHotelLocation, hotelRooms ));	
				//Hreinsum út hotelRooms listann okkar í næstu ítrun.
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

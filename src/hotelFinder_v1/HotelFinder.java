package hotelFinder_v1;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HotelFinder {
	
	DBconnect conn = new DBconnect();
	
	public  ArrayList<Hotel> getHotelByName(String name, Date checkInDate, Date checkOutDate){
		
		
		//Þetta return er bara temp til að losna við villuna að það vanti return.
		return new  ArrayList<Hotel>();
	}
	
	public ArrayList<Hotel> getFreeRoomsFromHotel(int hotelID, String checkInDate, String checkOutDate ){
		
		ArrayList<ArrayList<String>> hotelData;
		
		hotelData = conn.queryDataBase( "{call freeRoomsFromToAll(?, ?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), hotelID );	
		
		return hotelConstructor( hotelData );	
	}
	
	private ArrayList<Hotel> hotelConstructor( ArrayList<ArrayList<String>> hotelData ){
		
		String hotelName="", hotelLocation="", hotelChain="";
		String prevHotelName = "",prevHotelLocation = "",prevHotelChain = "";
		int hotelID = -1, prevHotelID, roomID, numPersons, rate;
		//From og to breyturnar eru notaðar því ég lenti í óleysanlegu böggi með hotelRooms.clear() fallinu.
		int from=0, to=0;
	
	
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
			
			
			
			//Ef hotel id breytist þá þarf að búa til nýjan hotel hlut
			//því við erum komnir í annað hotel. Búum einnig til nýtt hótel ef við erum komnir á seinasta herbergið.
			if(hotelID != prevHotelID){	
				System.out.println("Bý til hlut fyrir hótelið "+prevHotelName+" það er með " + hotelRooms.subList(from, to).size()  + " laus herbergi.");
				//Bætum inn nýju hóteli með herbergjunum sem við bjuggum til í seinustu ítrunum.
				//Setjum gömlu gildin vegna þess að við erum að búa til hótel sem er með herbergin sem við vorum að
				//búa til í seinustu ítrunum.  Þegar við erum komnir á þennan stað í lykkjunni þá er komið nýtt hótel í HotelName.
				returnHotels.add( new Hotel( prevHotelID, prevHotelName, prevHotelLocation, new ArrayList<HotelRoom>(hotelRooms.subList(from, to)) ));	
				from = to;
				
			}
			
							
			prevHotelID = hotelID;
			prevHotelChain = hotelChain;
			prevHotelLocation = hotelLocation;
			prevHotelName = hotelName;
			
			
			//Það þarf að bæta inn nýju herbergi í hverri ítrun lykkjunar.
			//Býr til nýtt hótelherbergi og bætir í lista.
			System.out.println("Bæti hótelherbergi með roomID: " + roomID);
			hotelRooms.add( new HotelRoom( roomID, numPersons, rate ));
			to++;
		}
		
		//Bý til seinasta hótelið ef það eru einhver herbergi í herbergjalistanum.
		if(hotelRooms.size() > 0){
		System.out.println("Bý til hlut fyrir hótelið: " + hotelName+" það er með " + hotelRooms.subList(from, to).size() + " laus herbergi.");
		returnHotels.add( new Hotel( hotelID,hotelName, hotelLocation, new ArrayList<HotelRoom>(hotelRooms.subList(from, to)) ));	
		}
		
		
		return returnHotels;
	}
	
	private Date convertToSqlDate(String date){
		
		DateFormat formatter = new SimpleDateFormat("yyy-MM-dd");
		java.util.Date parsedDate = null;
		try {
			parsedDate = formatter.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date returnDate = new Date(parsedDate.getTime());
		
		return returnDate;
		
	}
	
}

package hotelFinder_v1;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HotelFinder {
	
	DBconnect conn;
	
	public HotelFinder(){
		conn  = new DBconnect();
	}
	
	public ArrayList<Hotel> getHotelByName(String name, Date checkInDate, Date checkOutDate){
		
		
		//Þetta return er bara temp til að losna við villuna að það vanti return.
		return new  ArrayList<Hotel>();
	}
	
	public ArrayList<Hotel> getFreeRoomsFromHotel(int hotelID, String checkInDate, String checkOutDate ){
				
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		hotelData = conn.queryDataBase( "{call freeRoomsInHotel(?, ?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), hotelID );		
		return hotelConstructor( hotelData );	
	}
	//Hér búum við svo til föll til að query'a DB með mismunandi skilyrðum.

	//Þetta *ætti* að finna öll rooms sem eru laus á þessum dögum sama hvaða hótel er.
	public ArrayList<Hotel> getFreeRoomsFromAnyHotel( String checkInDate, String checkOutDate ){
		
		/*DBconnect conn  = new DBconnect();
		
		ArrayList<Hotel> hotelData = new ArrayList<Hotel>();
		
		System.out.println("Sækja öll hotel IDS og setja í array.");
		int[] allHotels = conn.getHotelIDs();
		System.out.println("HotelID's sem ég sótti: " +allHotels[0]+", "+allHotels[1]+", "+allHotels[2]+", "+allHotels[3]);
		for(int i = 0; i<allHotels.length; i++ ){
			ArrayList<Hotel> tempHotel = getFreeRoomsFromHotel(allHotels[i], checkInDate, checkOutDate);
			for(int j = 0; j < tempHotel.size(); j++){
				//Fyllir hotelData með öllum hótelunum úr tempHotel.
				hotelData.add(tempHotel.get(j));
			}
			//Hreinsum listan fyrir næsta iteration.
			tempHotel.clear();
		}
		
		//result er ArrayList<Hotel>
		//result.get(0) er fyrsta hótelið.
		//result.size() er stærðin á listanum.
		*/
		
		
		//Spurning með að gera þetta svona i staðin?
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		// hotelID = -1 er til að láta vita að við ætlum ekki að leita í sérstöku hóteli.
		hotelData = conn.queryDataBase( "{call freeRoomsAllHotels(?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), -1 );	
		return hotelConstructor( hotelData );
	}
	
	private ArrayList<Hotel> hotelConstructor( ArrayList<ArrayList<String>> hotelData ){
		
		if(hotelData.get(0).isEmpty()){
			return new ArrayList<Hotel>();
		}
		else{
			System.out.println("Komin í hotelconstructor. Er með þessi gögn:");
			System.out.println(hotelData);
			String hotelName="", hotelLocation="", hotelChain="";
			String prevHotelName = "",prevHotelLocation = "",prevHotelChain = "";
			int hotelID = -1, prevHotelID, roomID, numPersons, rate;
			//From og to breyturnar eru notaðar því ég lenti í óleysanlegu böggi með hotelRooms.clear() fallinu.
			int from=0, to=0;
		
		
			ArrayList<Hotel> returnHotels = new ArrayList<Hotel>();
			ArrayList<HotelRoom> hotelRooms = new ArrayList<HotelRoom>();
		
			//Upphafstilli prevHotelID svona til þess að búa ekki til hótel
			//um leið og lykkjan keyrir sig í gang.  Hótelin eiga að vera búin til
			//Þegar hotelID breytist eða ef seinasta herbergið er lesið inn.
			prevHotelID =  Integer.parseInt(  hotelData.get(0).get(0) );
			
			for (int i = 0; i < hotelData.get(0).size(); i++) {
			
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
					System.out.println("Bý til hlut fyrir hótelið "+prevHotelName+" það er með " + hotelRooms.subList(from, to).size()  + " laus herbergi. HOTEL ID ER: "+ hotelID);
					//Bætum inn nýju hóteli með herbergjunum sem við bjuggum til í seinustu ítrunum.
					//Setjum gömlu gildin vegna þess að við erum að búa til hótel sem er með herbergin sem við vorum að
					//búa til í seinustu ítrunum.  Þegar við erum komnir á þennan stað í lykkjunni þá er komið nýtt hótel í HotelName.
					returnHotels.add( new Hotel( prevHotelID, prevHotelName, prevHotelLocation, prevHotelChain, new ArrayList<HotelRoom>(hotelRooms.subList(from, to)) ));	
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
			System.out.println("Bý til hlut fyrir hótelið: " + hotelName+", það er með " + hotelRooms.subList(from, to).size() + " laus herbergi. HotelID er: "+hotelID);
			returnHotels.add( new Hotel( hotelID,hotelName, hotelLocation, hotelChain, new ArrayList<HotelRoom>(hotelRooms.subList(from, to)) ));	
			}
					
			return returnHotels;
		}
	}
	
	private Date convertToSqlDate(String date){
		
		DateFormat formatter = new SimpleDateFormat("yyy-MM-dd");
		java.util.Date parsedDate = null;
		try {
			parsedDate = formatter.parse(date);
		} catch (ParseException e) {
			System.out.println(e);
		}
		Date returnDate = new Date(parsedDate.getTime());
		
		return returnDate;
		
	}
	
}

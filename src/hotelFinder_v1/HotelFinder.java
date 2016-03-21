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

	// ####################################################################################### //
	// #####    Hér búum við svo til föll til að query'a DB með mismunandi skilyrðum.    ##### //
	// ####################################################################################### //
	
	//Fall sem finnur öll laus hótelherbergi í ákveðnu hóteli
	public ArrayList<Hotel> getFreeRoomsFromHotel(String hotelName, String checkInDate, String checkOutDate ){
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		hotelData = conn.queryDataBase( "{call freeRoomsInHotel(?, ?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), hotelName );		
		return hotelConstructor( hotelData );	
	}
	
	//Fall sem finnur öll laus hótelherbergi í ákveðnu location
	public ArrayList<Hotel> getFreeRoomsFromHotelLocation(String hotelLocation, String checkInDate, String checkOutDate ){			
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		hotelData = conn.queryDataBase( "{call freeRoomsLocation(?, ?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), hotelLocation );
		return hotelConstructor( hotelData );	
	}
	
	//Fall sem finnur öll laus hótelherbergi í ákveðnu location
	public ArrayList<Hotel> getFreeRoomsFromHotelChain(String hotelChainName, String checkInDate, String checkOutDate ){				
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		hotelData = conn.queryDataBase( "{call freeRoomsHotelChain(?, ?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), hotelChainName );
		return hotelConstructor( hotelData );	
	}

	//Fall sem finnur öll laus hótelherbergi í öllum hótelum.
	public ArrayList<Hotel> getFreeRoomsFromAnyHotel( String checkInDate, String checkOutDate ){
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		// hotelString = null er til að láta vita að við ætlum ekki að leita í öllum hótelum
		hotelData = conn.queryDataBase( "{call freeRoomsAllHotels(?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), null );	
		return hotelConstructor( hotelData );
	}
	
	//Fall sem finnur öll laus hótelherbergi í öllum hótelum.
	public ArrayList<Hotel> getFreeRoomsFromAnyHotelSubString(String subString, String checkInDate, String checkOutDate ){
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		// hotelString = null er til að láta vita að við ætlum ekki að leita í öllum hótelum
		hotelData = conn.queryDataBase( "{call freeRoomsHotelSubName(?, ?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), subString );	
		return hotelConstructor( hotelData );
	}
	
	private ArrayList<Hotel> hotelConstructor( ArrayList<ArrayList<String>> hotelData ){
		
		if(hotelData.get(0).isEmpty()){
			System.out.println("Engin gögn fundust");
			return new ArrayList<Hotel>();
		}
		else{
			String hotelName="", hotelLocation="", hotelChain="";
			String prevHotelName = "",prevHotelLocation = "",prevHotelChain = "";
			int hotelID = -1, prevHotelID, roomID, numPersons, rate;
			//From og to breyturnar eru notaðar því ég lenti í óleysanlegu böggi með hotelRooms.clear() fallinu.
			int from=0, to=0;
		
			//Breyturnar sem geyma hótelin og hótelherbergin sem við ætlum að skila.
			ArrayList<Hotel> returnHotels = new ArrayList<Hotel>();
			ArrayList<HotelRoom> hotelRooms = new ArrayList<HotelRoom>();
			ArrayList<Facility> hotelFacilities = new ArrayList<Facility>();
				
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
					System.out.println("Næ í facilities fyrir hótelið");
					//Næ í facilities fyrir hótelið sem við ætlum að búa til hlut fyrir.
					hotelFacilities = getFacilitiesForHotel(prevHotelName);
					//Bætum inn nýju hóteli með herbergjunum sem við bjuggum til í seinustu ítrunum.
					//Setjum gömlu gildin vegna þess að við erum að búa til hótel sem er með herbergin sem við vorum að
					//búa til í seinustu ítrunum.  Þegar við erum komnir á þennan stað í lykkjunni þá er komið nýtt hótel í HotelName.
					returnHotels.add( new Hotel( prevHotelID, prevHotelName, prevHotelLocation, prevHotelChain, new ArrayList<HotelRoom>(hotelRooms.subList(from, to)), hotelFacilities));	
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
			System.out.println("Næ í facilities fyrir hótelið");
			hotelFacilities = getFacilitiesForHotel(prevHotelName);
			returnHotels.add( new Hotel( hotelID,hotelName, hotelLocation, hotelChain, new ArrayList<HotelRoom>(hotelRooms.subList(from, to)), hotelFacilities ));	
			}
			
			conn.closeConnection();
			return returnHotels;
		}
	}
	//Nær í öll facility fyrir gefið hótel.
	private ArrayList<Facility> getFacilitiesForHotel( String hotelName ){
		ArrayList<Facility> facilityList = new ArrayList<Facility>();
		ArrayList<ArrayList<String>> facilityData = new ArrayList<ArrayList<String>>();
		facilityData = conn.queryDataBase( "{call getHotelFacilities(?)}", null, null, hotelName );
		
		int facilityID;
		String facilityName = "", facilityDesc = "";
		
		for (int i = 0; i < facilityData.get(0).size(); i++) {
		
			facilityID = Integer.parseInt( facilityData.get(0).get(i) );
			facilityName = facilityData.get(1).get(i);
			facilityDesc = facilityData.get(2).get(i);
			
			facilityList.add( new Facility( facilityID, facilityName, facilityDesc ) );			
		}	
		return facilityList;
	}
	
	//Fall sem breytir dagsetningu á strengjaformi yfir á sql.date form
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

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
	// #####    H�r b�um vi� svo til f�ll til a� query'a DB me� mismunandi skilyr�um.    ##### //
	// ####################################################################################### //
	
	//Fall sem finnur �ll laus h�telherbergi � �kve�nu h�teli
	public ArrayList<Hotel> getFreeRoomsFromHotel(String hotelName, String checkInDate, String checkOutDate ){
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		hotelData = conn.queryDataBase( "{call freeRoomsInHotel(?, ?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), hotelName );		
		return hotelConstructor( hotelData );	
	}
	
	//Fall sem finnur �ll laus h�telherbergi � �kve�nu location
	public ArrayList<Hotel> getFreeRoomsFromHotelLocation(String hotelLocation, String checkInDate, String checkOutDate ){			
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		hotelData = conn.queryDataBase( "{call freeRoomsLocation(?, ?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), hotelLocation );
		return hotelConstructor( hotelData );	
	}
	
	//Fall sem finnur �ll laus h�telherbergi � �kve�nu location
	public ArrayList<Hotel> getFreeRoomsFromHotelChain(String hotelChainName, String checkInDate, String checkOutDate ){				
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		hotelData = conn.queryDataBase( "{call freeRoomsHotelChain(?, ?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), hotelChainName );
		return hotelConstructor( hotelData );	
	}

	//Fall sem finnur �ll laus h�telherbergi � �llum h�telum.
	public ArrayList<Hotel> getFreeRoomsFromAnyHotel( String checkInDate, String checkOutDate ){
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		// hotelString = null er til a� l�ta vita a� vi� �tlum ekki a� leita � �llum h�telum
		hotelData = conn.queryDataBase( "{call freeRoomsAllHotels(?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), null );	
		return hotelConstructor( hotelData );
	}
	
	//Fall sem finnur �ll laus h�telherbergi � �llum h�telum.
	public ArrayList<Hotel> getFreeRoomsFromAnyHotelSubString(String subString, String checkInDate, String checkOutDate ){
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		// hotelString = null er til a� l�ta vita a� vi� �tlum ekki a� leita � �llum h�telum
		hotelData = conn.queryDataBase( "{call freeRoomsHotelSubName(?, ?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), subString );	
		return hotelConstructor( hotelData );
	}
	
	private ArrayList<Hotel> hotelConstructor( ArrayList<ArrayList<String>> hotelData ){
		
		if(hotelData.get(0).isEmpty()){
			System.out.println("Engin g�gn fundust");
			return new ArrayList<Hotel>();
		}
		else{
			String hotelName="", hotelLocation="", hotelChain="";
			String prevHotelName = "",prevHotelLocation = "",prevHotelChain = "";
			int hotelID = -1, prevHotelID, roomID, numPersons, rate;
			//From og to breyturnar eru nota�ar �v� �g lenti � �leysanlegu b�ggi me� hotelRooms.clear() fallinu.
			int from=0, to=0;
		
			//Breyturnar sem geyma h�telin og h�telherbergin sem vi� �tlum a� skila.
			ArrayList<Hotel> returnHotels = new ArrayList<Hotel>();
			ArrayList<HotelRoom> hotelRooms = new ArrayList<HotelRoom>();
			ArrayList<Facility> hotelFacilities = new ArrayList<Facility>();
				
			//Upphafstilli prevHotelID svona til �ess a� b�a ekki til h�tel
			//um lei� og lykkjan keyrir sig � gang.  H�telin eiga a� vera b�in til
			//�egar hotelID breytist e�a ef seinasta herbergi� er lesi� inn.
			prevHotelID =  Integer.parseInt(  hotelData.get(0).get(0) );
			
			for (int i = 0; i < hotelData.get(0).size(); i++) {
			
				hotelName = hotelData.get(1).get(i);
				hotelChain = hotelData.get(2).get(i);
				hotelLocation = hotelData.get(3).get(i);
				
				//Breytir string yfir � int �ar sem �arf.
				hotelID = Integer.parseInt(  hotelData.get(0).get(i) );
				roomID = Integer.parseInt(hotelData.get(4).get(i));
				numPersons = Integer.parseInt(hotelData.get(5).get(i));
				rate = Integer.parseInt(hotelData.get(6).get(i));
						
				//Ef hotel id breytist �� �arf a� b�a til n�jan hotel hlut
				//�v� vi� erum komnir � anna� hotel. B�um einnig til n�tt h�tel ef vi� erum komnir � seinasta herbergi�.
				if(hotelID != prevHotelID){	
					System.out.println("B� til hlut fyrir h�teli� "+prevHotelName+" �a� er me� " + hotelRooms.subList(from, to).size()  + " laus herbergi. HOTEL ID ER: "+ hotelID);
					System.out.println("N� � facilities fyrir h�teli�");
					//N� � facilities fyrir h�teli� sem vi� �tlum a� b�a til hlut fyrir.
					hotelFacilities = getFacilitiesForHotel(prevHotelName);
					//B�tum inn n�ju h�teli me� herbergjunum sem vi� bjuggum til � seinustu �trunum.
					//Setjum g�mlu gildin vegna �ess a� vi� erum a� b�a til h�tel sem er me� herbergin sem vi� vorum a�
					//b�a til � seinustu �trunum.  �egar vi� erum komnir � �ennan sta� � lykkjunni �� er komi� n�tt h�tel � HotelName.
					returnHotels.add( new Hotel( prevHotelID, prevHotelName, prevHotelLocation, prevHotelChain, new ArrayList<HotelRoom>(hotelRooms.subList(from, to)), hotelFacilities));	
					from = to;
				}		
								
				prevHotelID = hotelID;
				prevHotelChain = hotelChain;
				prevHotelLocation = hotelLocation;
				prevHotelName = hotelName;
						
				//�a� �arf a� b�ta inn n�ju herbergi � hverri �trun lykkjunar.
				//B�r til n�tt h�telherbergi og b�tir � lista.
				System.out.println("B�ti h�telherbergi me� roomID: " + roomID);
				hotelRooms.add( new HotelRoom( roomID, numPersons, rate ));
				to++;
			}
			
			//B� til seinasta h�teli� ef �a� eru einhver herbergi � herbergjalistanum.
			if(hotelRooms.size() > 0){
			System.out.println("B� til hlut fyrir h�teli�: " + hotelName+", �a� er me� " + hotelRooms.subList(from, to).size() + " laus herbergi. HotelID er: "+hotelID);
			System.out.println("N� � facilities fyrir h�teli�");
			hotelFacilities = getFacilitiesForHotel(prevHotelName);
			returnHotels.add( new Hotel( hotelID,hotelName, hotelLocation, hotelChain, new ArrayList<HotelRoom>(hotelRooms.subList(from, to)), hotelFacilities ));	
			}
			
			conn.closeConnection();
			return returnHotels;
		}
	}
	//N�r � �ll facility fyrir gefi� h�tel.
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
	
	//Fall sem breytir dagsetningu � strengjaformi yfir � sql.date form
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

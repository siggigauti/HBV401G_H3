package hotelFinder_v1;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HotelFinder {
	
	DBconnect conn;
	String checkInDate;
	String checkOutDate;
	
	public HotelFinder(String newCheckInDate, String newCheckOutDate){
		conn  = new DBconnect();
		checkInDate = newCheckInDate;
		checkOutDate = newCheckOutDate;
	}

	// ####################################################################################### //
	// #####    H�r b�um vi� svo til f�ll til a� query'a DB me� mismunandi skilyr�um.    ##### //
	// ####################################################################################### //
	
	//Fall sem finnur �ll laus h�telherbergi � �kve�nu h�teli
	public ArrayList<Hotel> getFreeRoomsFromHotel(String hotelName){
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		hotelData = conn.queryDataBase( "{call freeRoomsInHotel(?, ?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), hotelName );		
		return hotelConstructor( hotelData );	
	}
	
	//Fall sem finnur �ll laus h�telherbergi � �kve�nu location
	public ArrayList<Hotel> getFreeRoomsFromHotelLocation(String hotelLocation ){			
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		hotelData = conn.queryDataBase( "{call freeRoomsLocation(?, ?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), hotelLocation );
		return hotelConstructor( hotelData );	
	}
	
	//Fall sem finnur �ll laus h�telherbergi � �kve�nu location
	public ArrayList<Hotel> getFreeRoomsFromHotelChain(String hotelChainName){				
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		hotelData = conn.queryDataBase( "{call freeRoomsHotelChain(?, ?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), hotelChainName );
		return hotelConstructor( hotelData );	
	}

	//Fall sem finnur �ll laus h�telherbergi � �llum h�telum.
	public ArrayList<Hotel> getFreeRoomsFromAnyHotel(){
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		// hotelString = null er til a� l�ta vita a� vi� �tlum ekki a� leita � �llum h�telum
		hotelData = conn.queryDataBase( "{call freeRoomsAllHotels(?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), null );	
		return hotelConstructor( hotelData );
	}
	
	//Fall sem finnur �ll laus h�telherbergi � �llum h�telum.
	public ArrayList<Hotel> getFreeRoomsFromAnyHotelSubString(String subString){
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
			ArrayList<Review> hotelReviews = new ArrayList<Review>();
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
					//N� � facilities fyrir h�teli� sem vi� �tlum a� b�a til hlut fyrir.
					hotelFacilities = getFacilitiesForHotel(prevHotelName);
					//N� � reviews fyrir h�teli�
					hotelReviews = getReviewsForHotel(prevHotelName);
					//B�tum inn n�ju h�teli me� herbergjunum sem vi� bjuggum til � seinustu �trunum.
					//Setjum g�mlu gildin vegna �ess a� vi� erum a� b�a til h�tel sem er me� herbergin sem vi� vorum a�
					//b�a til � seinustu �trunum.  �egar vi� erum komnir � �ennan sta� � lykkjunni �� er komi� n�tt h�tel � HotelName.
					returnHotels.add( new Hotel( prevHotelID, prevHotelName, prevHotelLocation, prevHotelChain, new ArrayList<HotelRoom>(hotelRooms.subList(from, to)), hotelFacilities, hotelReviews));	
					from = to;
				}		
								
				prevHotelID = hotelID;
				prevHotelChain = hotelChain;
				prevHotelLocation = hotelLocation;
				prevHotelName = hotelName;
						
				//�a� �arf a� b�ta inn n�ju herbergi � hverri �trun lykkjunar.
				//B�r til n�tt h�telherbergi og b�tir � lista.
				hotelRooms.add( new HotelRoom( roomID, numPersons, rate ));
				to++;
			}
			
			//B� til seinasta h�teli� ef �a� eru einhver herbergi � herbergjalistanum.
			if(hotelRooms.size() > 0){
			hotelFacilities = getFacilitiesForHotel(hotelName);
			hotelReviews = getReviewsForHotel(prevHotelName);
			returnHotels.add( new Hotel( hotelID,hotelName, hotelLocation, hotelChain, new ArrayList<HotelRoom>(hotelRooms.subList(from, to)), hotelFacilities, hotelReviews ));	
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
	//N�r � �ll reviews fyrir gefi� h�tel.
	private ArrayList<Review> getReviewsForHotel( String hotelName ){
		ArrayList<Review> reviewList = new ArrayList<Review>();
		ArrayList<ArrayList<String>> reviewData = new ArrayList<ArrayList<String>>();
		reviewData = conn.queryDataBase( "{call getReviews(?)}", null, null, hotelName );
		
		int reviewStars;
		String reviewTitle = "", reviewContent = "", reviewer = "", reviewDate = "";
		
		for (int i = 0; i < reviewData.get(0).size(); i++) {			
			reviewTitle = reviewData.get(0).get(i);
			reviewContent = reviewData.get(1).get(i);
			reviewStars = Integer.parseInt( reviewData.get(2).get(i) );
			reviewDate = reviewData.get(3).get(i);
			reviewer = reviewData.get(4).get(i);
			
			reviewList.add( new Review( reviewTitle, reviewContent, reviewStars, reviewer, reviewDate) );			
		}
		return reviewList;
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
	
	
	//Notkun: book(hotelID, roomID);
	//Fyrir: V��v�ru breyturnar checkIn og checkOut eru b�nar a� sta�festa a� herbergin eru laus.
	//       hotel er Hotel object og inniheldur herbergin sem eru laus � t�mabilinu.
	//       roomID er l�glegt id � herbergi.
	//Eftir: B�i� er a� b�a til n�ja r�� � bookings � DB sem tilgreinir a� herbergi� er fr�teki�.
	public void book(Hotel hotel, int roomID){
		boolean booked = false;
		for(int i = 0; i < hotel.getHotelRooms().size(); i++){
			if(hotel.getHotelRooms().get(i).getId() == roomID){
				Booker booker = new Booker();
				booker.book(hotel, roomID, convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate));
				booked = true;
			}
		}
		if(booked==true){
			System.out.println("B�kun t�kst, herbergi� er b�ka� fyrir t�man "+checkInDate+" til "+checkOutDate);
		}
		else{
			System.out.println("B�kun t�kst ekki! Gefi� roomID var ekki til � listanum af h�telherbergjum fyrir gefi� h�tel.");
		}		
	}
	
	public void unbook(Hotel hotel, int BookID){
		
		Booker booker = new Booker();
		booker.unbook( hotel ,BookID);
		System.out.println("Afb�kun t�kst, herbergi� sem var b�ka� fyrir t�man "+checkInDate+" til "+checkOutDate +" hefur veri� eytt.");
			
	}
	
	//Skrifar ums�gn um h�tel.
	public void writeReview(int hotelID, int stars, String title, String content, String date, String reviewerName){	
		conn.insertReview(hotelID, stars, title, content, convertToSqlDate(date), reviewerName);
	}

	//Fer � gegnum �ll h�telin sem eru laus og s�ar �t �au sem hafa facilities sem leita� er a�
	public ArrayList<Hotel> getHotelWithFacilities( int[] facilityID ){
		ArrayList<Hotel> hotels = getFreeRoomsFromAnyHotel();	
		ArrayList<Hotel> returnHotels = new ArrayList<Hotel>();
		for (int i = 0; i < hotels.size(); i++) {		
			if(hotelHasFacilities(hotels.get(i), facilityID)){
				returnHotels.add( hotels.get(i) );
			}			
		}
		return returnHotels;
	}
	
	//Fer � gegnum h�telin � hotels listanum og filterar �t �au h�tel sem eru me� r�tt facilities.
	public ArrayList<Hotel> filterHotelWithFacilities(ArrayList<Hotel> hotels, int[] facilityID ){	
		ArrayList<Hotel> returnHotels = new ArrayList<Hotel>();
		for (int i = 0; i < hotels.size(); i++) {		
			if(hotelHasFacilities(hotels.get(i), facilityID)){
				returnHotels.add( hotels.get(i) );
			}			
		}
		return returnHotels;
	}
	
	
	//Fall sem athugar hvort h�tel s� me� �ll facilities sem eru � integer fylkinu facilities.
	//Nota fylki svo �a� s� h�gt a� athuga fleiri en eitt facility.
	private boolean hotelHasFacilities(Hotel hotel, int[] facilityID){
		boolean found = false;
		//Lykkja sem fer � gegnum �ll facilities � h�teli.
		for (int i = 0; i < facilityID.length; i++) {
			//Lykkja sem athugar hvort eitthva� af �eim facilities s�u � facilityID fylkinu.
			for (int j = 0; j < hotel.getFacilities().size(); j++) {
				if( hotel.getFacilities().get(j).getType() == facilityID[i] || facilityID[i] == 0){
					found = true;
				}
			}
			if(!found){
				//H�ttum og skilum false um lei� og vi� finnum ekki eitt facility.
				return false;
			}
			//Setjum found = false fyrir n�stu umfer�.
			found = false;
		}		
		return true;
	}
}

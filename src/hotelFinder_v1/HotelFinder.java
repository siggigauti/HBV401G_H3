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
	// #####    Hér búum við svo til föll til að query'a DB með mismunandi skilyrðum.    ##### //
	// ####################################################################################### //
	
	//Fall sem finnur öll laus hótelherbergi í ákveðnu hóteli
	public ArrayList<Hotel> getFreeRoomsFromHotel(String hotelName){
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		hotelData = conn.queryDataBase( "{call freeRoomsInHotel(?, ?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), hotelName );		
		return hotelConstructor( hotelData );	
	}
	
	//Fall sem finnur öll laus hótelherbergi í ákveðnu location
	public ArrayList<Hotel> getFreeRoomsFromHotelLocation(String hotelLocation ){			
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		hotelData = conn.queryDataBase( "{call freeRoomsLocation(?, ?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), hotelLocation );
		return hotelConstructor( hotelData );	
	}
	
	//Fall sem finnur öll laus hótelherbergi í ákveðnu location
	public ArrayList<Hotel> getFreeRoomsFromHotelChain(String hotelChainName){				
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		hotelData = conn.queryDataBase( "{call freeRoomsHotelChain(?, ?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), hotelChainName );
		return hotelConstructor( hotelData );	
	}

	//Fall sem finnur öll laus hótelherbergi í öllum hótelum.
	public ArrayList<Hotel> getFreeRoomsFromAnyHotel(){
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		// hotelString = null er til að láta vita að við ætlum ekki að leita í öllum hótelum
		hotelData = conn.queryDataBase( "{call freeRoomsAllHotels(?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), null );	
		return hotelConstructor( hotelData );
	}
	
	//Fall sem finnur öll laus hótelherbergi í öllum hótelum.
	public ArrayList<Hotel> getFreeRoomsFromAnyHotelSubString(String subString){
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
			ArrayList<Review> hotelReviews = new ArrayList<Review>();
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
					//Næ í facilities fyrir hótelið sem við ætlum að búa til hlut fyrir.
					hotelFacilities = getFacilitiesForHotel(prevHotelName);
					//Næ í reviews fyrir hótelið
					hotelReviews = getReviewsForHotel(prevHotelName);
					//Bætum inn nýju hóteli með herbergjunum sem við bjuggum til í seinustu ítrunum.
					//Setjum gömlu gildin vegna þess að við erum að búa til hótel sem er með herbergin sem við vorum að
					//búa til í seinustu ítrunum.  Þegar við erum komnir á þennan stað í lykkjunni þá er komið nýtt hótel í HotelName.
					returnHotels.add( new Hotel( prevHotelID, prevHotelName, prevHotelLocation, prevHotelChain, new ArrayList<HotelRoom>(hotelRooms.subList(from, to)), hotelFacilities, hotelReviews));	
					from = to;
				}		
								
				prevHotelID = hotelID;
				prevHotelChain = hotelChain;
				prevHotelLocation = hotelLocation;
				prevHotelName = hotelName;
						
				//Það þarf að bæta inn nýju herbergi í hverri ítrun lykkjunar.
				//Býr til nýtt hótelherbergi og bætir í lista.
				hotelRooms.add( new HotelRoom( roomID, numPersons, rate ));
				to++;
			}
			
			//Bý til seinasta hótelið ef það eru einhver herbergi í herbergjalistanum.
			if(hotelRooms.size() > 0){
			hotelFacilities = getFacilitiesForHotel(hotelName);
			hotelReviews = getReviewsForHotel(prevHotelName);
			returnHotels.add( new Hotel( hotelID,hotelName, hotelLocation, hotelChain, new ArrayList<HotelRoom>(hotelRooms.subList(from, to)), hotelFacilities, hotelReviews ));	
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
	//Nær í öll reviews fyrir gefið hótel.
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
	
	
	//Notkun: book(hotelID, roomID);
	//Fyrir: Víðværu breyturnar checkIn og checkOut eru búnar að staðfesta að herbergin eru laus.
	//       hotel er Hotel object og inniheldur herbergin sem eru laus á tímabilinu.
	//       roomID er löglegt id á herbergi.
	//Eftir: Búið er að búa til nýja röð í bookings í DB sem tilgreinir að herbergið er frátekið.
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
			System.out.println("Bókun tókst, herbergið er bókað fyrir tíman "+checkInDate+" til "+checkOutDate);
		}
		else{
			System.out.println("Bókun tókst ekki! Gefið roomID var ekki til í listanum af hótelherbergjum fyrir gefið hótel.");
		}		
	}
	
	public void unbook(Hotel hotel, int BookID){
		
		Booker booker = new Booker();
		booker.unbook( hotel ,BookID);
		System.out.println("Afbókun tókst, herbergið sem var bókað fyrir tíman "+checkInDate+" til "+checkOutDate +" hefur verið eytt.");
			
	}
	
	//Skrifar umsögn um hótel.
	public void writeReview(int hotelID, int stars, String title, String content, String date, String reviewerName){	
		conn.insertReview(hotelID, stars, title, content, convertToSqlDate(date), reviewerName);
	}

	//Fer í gegnum öll hótelin sem eru laus og síar út þau sem hafa facilities sem leitað er að
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
	
	//Fer í gegnum hótelin í hotels listanum og filterar út þau hótel sem eru með rétt facilities.
	public ArrayList<Hotel> filterHotelWithFacilities(ArrayList<Hotel> hotels, int[] facilityID ){	
		ArrayList<Hotel> returnHotels = new ArrayList<Hotel>();
		for (int i = 0; i < hotels.size(); i++) {		
			if(hotelHasFacilities(hotels.get(i), facilityID)){
				returnHotels.add( hotels.get(i) );
			}			
		}
		return returnHotels;
	}
	
	
	//Fall sem athugar hvort hótel sé með öll facilities sem eru í integer fylkinu facilities.
	//Nota fylki svo það sé hægt að athuga fleiri en eitt facility.
	private boolean hotelHasFacilities(Hotel hotel, int[] facilityID){
		boolean found = false;
		//Lykkja sem fer í gegnum öll facilities á hóteli.
		for (int i = 0; i < facilityID.length; i++) {
			//Lykkja sem athugar hvort eitthvað af þeim facilities séu í facilityID fylkinu.
			for (int j = 0; j < hotel.getFacilities().size(); j++) {
				if( hotel.getFacilities().get(j).getType() == facilityID[i] || facilityID[i] == 0){
					found = true;
				}
			}
			if(!found){
				//Hættum og skilum false um leið og við finnum ekki eitt facility.
				return false;
			}
			//Setjum found = false fyrir næstu umferð.
			found = false;
		}		
		return true;
	}
}

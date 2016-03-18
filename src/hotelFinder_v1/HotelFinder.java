package hotelFinder_v1;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HotelFinder {
	
	DBconnect conn = new DBconnect();
	
	public  ArrayList<Hotel> getHotelByName(String name, Date checkInDate, Date checkOutDate){
		
		
		//�etta return er bara temp til a� losna vi� villuna a� �a� vanti return.
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
		//From og to breyturnar eru nota�ar �v� �g lenti � �leysanlegu b�ggi me� hotelRooms.clear() fallinu.
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
	
		//Upphafstilli prevHotelID svona til �ess a� b�a ekki til h�tel
		//um lei� og lykkjan keyrir sig � gang.  H�telin eiga a� vera b�in til
		//�egar hotelID breytist e�a ef seinasta herbergi� er lesi� inn.
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
			
			
			//Breytir string yfir � int �ar sem �arf.
			hotelID = Integer.parseInt(  hotelData.get(0).get(i) );
			roomID = Integer.parseInt(hotelData.get(4).get(i));
			numPersons = Integer.parseInt(hotelData.get(5).get(i));
			rate = Integer.parseInt(hotelData.get(6).get(i));
			
			
			
			//Ef hotel id breytist �� �arf a� b�a til n�jan hotel hlut
			//�v� vi� erum komnir � anna� hotel. B�um einnig til n�tt h�tel ef vi� erum komnir � seinasta herbergi�.
			if(hotelID != prevHotelID){	
				System.out.println("B� til hlut fyrir h�teli� "+prevHotelName+" �a� er me� " + hotelRooms.subList(from, to).size()  + " laus herbergi.");
				//B�tum inn n�ju h�teli me� herbergjunum sem vi� bjuggum til � seinustu �trunum.
				//Setjum g�mlu gildin vegna �ess a� vi� erum a� b�a til h�tel sem er me� herbergin sem vi� vorum a�
				//b�a til � seinustu �trunum.  �egar vi� erum komnir � �ennan sta� � lykkjunni �� er komi� n�tt h�tel � HotelName.
				returnHotels.add( new Hotel( prevHotelID, prevHotelName, prevHotelLocation, new ArrayList<HotelRoom>(hotelRooms.subList(from, to)) ));	
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
		System.out.println("B� til hlut fyrir h�teli�: " + hotelName+" �a� er me� " + hotelRooms.subList(from, to).size() + " laus herbergi.");
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

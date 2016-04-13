package hotelFinder_v1;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HotelConstructorTest {
	
	private Hotel hotelA, hotelB, hotelC;

	@Before
	public void setUp() throws Exception {
		
		ArrayList<HotelRoom> hotelRooms = new ArrayList<HotelRoom>();
		ArrayList<Facility> hotelFacilities = new ArrayList<Facility>();
		
		hotelRooms.add(new HotelRoom(23, 1, 10));
		hotelFacilities.add(new Facility(1, "pool", "A"));
		
		hotelA = new Hotel(23, "fjorukrain", "Hafnarfjordur", "vikings", hotelRooms , hotelFacilities );
		
		ArrayList<HotelRoom> hotelRooms2 = new ArrayList<HotelRoom>();
		ArrayList<Facility> hotelFacilities2 = new ArrayList<Facility>();
		
		hotelRooms2.add(new HotelRoom(10, 2, 20));
		hotelFacilities2.add(new Facility(2, "cleanwater", "B"));
		
		hotelB = new Hotel(10, "AdamHotel", "RVK", "badboys", hotelRooms2 , hotelFacilities2 );
		
		ArrayList<HotelRoom> hotelRooms3 = new ArrayList<HotelRoom>();
		ArrayList<Facility> hotelFacilities3 = new ArrayList<Facility>();
		
		hotelRooms3.add(new HotelRoom(9, 3, 30));
		hotelFacilities3.add(new Facility(3, "cpu", "C"));
		
		hotelC = new Hotel(3, "Akur", "Akureyri", "AK", hotelRooms3 , hotelFacilities3 );
		
	}

	@After
	public void tearDown() throws Exception {
		hotelA = null;
		hotelC = null;
		hotelB = null;
	}

	@Test
	public void testID() {
		assertEquals(23, hotelA.getId());
		assertEquals(10, hotelB.getId());
		assertEquals(3, hotelC.getId());
	}

	@Test
	public void testName() {
		assertEquals("fjorukrain", hotelA.getName());
		assertEquals("AdamHotel", hotelB.getName());
		assertEquals("Akur", hotelC.getName());
	}
	@Test
	public void testCity() {
		assertEquals("Hafnarfjordur", hotelA.getCity());
		assertEquals("RVK", hotelB.getCity());
		assertEquals("Akureyri", hotelC.getCity());
	}
	@Test
	public void testHotelChain() {
		assertEquals("vikings", hotelA.getChain());
		assertEquals("badboys", hotelB.getChain());
		assertEquals("AK", hotelC.getChain());
	}
	@Test
	public void testGetRate() {
		
		assertEquals(10, hotelA.getHotelRooms().get(0).getRate());
		assertEquals(20, hotelB.getHotelRooms().get(0).getRate());
		assertEquals(30, hotelC.getHotelRooms().get(0).getRate());	
		
	}
	@Test
	public void testGetNumPerson() {
		assertEquals(1, hotelA.getHotelRooms().get(0).getNumPerson());
		assertEquals(2, hotelB.getHotelRooms().get(0).getNumPerson());
		assertEquals(3, hotelC.getHotelRooms().get(0).getNumPerson());
	}
	@Test
	public void testGetFacilityType() {
		assertEquals(1, hotelA.getFacilities().get(0).getType());
		assertEquals(2, hotelB.getFacilities().get(0).getType());
		assertEquals(3, hotelC.getFacilities().get(0).getType());
	}
	@Test
	public void testGetFacilityName() {
		assertEquals("pool", hotelA.getFacilities().get(0).getName());
		assertEquals("cleanwater", hotelB.getFacilities().get(0).getName());
		assertEquals("cpu", hotelC.getFacilities().get(0).getName());
	}
	@Test
	public void testGetFacilityDescription() {
		assertEquals("A", hotelA.getFacilities().get(0).getDescription());
		assertEquals("B", hotelB.getFacilities().get(0).getDescription());
		assertEquals("C", hotelC.getFacilities().get(0).getDescription());
	}
	
	/*	public ArrayList<Hotel> getFreeRoomsFromHotelLocation(String hotelLocation ){			
		ArrayList<ArrayList<String>> hotelData = new ArrayList<ArrayList<String>>();
		hotelData = conn.queryDataBase( "{call freeRoomsLocation(?, ?, ?)}", convertToSqlDate(checkInDate), convertToSqlDate(checkOutDate), hotelLocation );
		return hotelConstructor( hotelData );	
	}*/
	
	@Test
	public void testGetFreeRoomsFromHotelLocation() {
		ArrayList<Hotel> hotelLocation = new ArrayList<Hotel>();
		
		HotelFinder h1 = new HotelFinder("2016-09-22", "2016-09-23");
		hotelLocation = h1.getFreeRoomsFromHotelLocation( "bla" );
		
		assertTrue(hotelLocation.isEmpty());
		
		HotelFinder h2 = new HotelFinder("2016-09-22", "2016-09-23");
		hotelLocation = h2.getFreeRoomsFromHotelLocation( "Reykjavík" );
		
		assertFalse(hotelLocation.isEmpty());
	}
	
	/*@Test(expected=NullPointerException.class)
	public void testNullPointerHotelLocation(){
		
		ArrayList<Hotel> hotelLocation = new ArrayList<Hotel>();
		
		HotelFinder h3 = new HotelFinder("2016-09-22", "2016-09-23");
		hotelLocation = h3.getFreeRoomsFromHotelLocation( null );
		
		
		//System.out.println(hotelLocation.get(0));
		
		assertEquals(null, hotelLocation);
		
	}*/
	
	
}


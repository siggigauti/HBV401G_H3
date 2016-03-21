
delimiter $$
drop procedure if exists createTempTable $$
create procedure createTempTable()
begin
	drop temporary table if exists freeRooms;
	CREATE TEMPORARY TABLE freeRooms(
		roomID int,
		hotelID int,
		numPersons int,
		rate int
);
end $$

/*  
 * Usage: call freeRoomsInHotel(hotel, fromDate, toDate )
 * Pre:	  fromDate and toDate are the checkin and checkout dates.
 * Post:  Returns the info about rooms that are not occupied during
 * 		  the two dates in specific hotel.
 * 		  
 */
delimiter $$
drop procedure if exists freeRoomsInHotel $$
create procedure freeRoomsInHotel(hotel VARCHAR(50), fromDate DATE, toDate DATE)
begin
		Call fillFreeRoomsTableAllHotels(fromDate, toDate);
        SELECT hotel.hotelID, hotelName, hotelChain, hotelLocation, roomID, numPersons, rate 
        FROM hotel JOIN freeRooms ON freeRooms.hotelID = hotel.hotelID WHERE hotel.hotelName = hotel;
end $$

/*
 * Usage: call createBooking( hotel, room, fromDate, toDate )
 * Pre:   hotel is the ID of the hotel to book a room in.
 *        room is the ID of the room to book.
 *        fromDate and toDate are the checkin and checkout dates.
 * Post:  Creates a booking with the above values.
 */
delimiter $$
drop procedure if exists createBooking $$
create procedure createBooking( hotel int, room int, fromDate DATE, toDate DATE )
begin
	INSERT INTO bookings( HotelID, RoomID, CheckInDate, CheckOutDate ) VALUES( hotel, room, fromDate, toDate ); 
end $$

/*
 * Usage: call fillFreeRoomsTableAllHotels(fromDate, toDate )
 * Pre:   fromDate and toDate are the checkin and checkout dates.
 * Post:  Fills the temp table with all free rooms in all hotels.
 */
 delimiter $$
drop procedure if exists fillFreeRoomsTableAllHotels $$
create procedure fillFreeRoomsTableAllHotels(fromDate DATE, toDate DATE )
begin
	call createTempTable();
	INSERT INTO freeRooms(roomID, hotelID, numPersons, rate)
    SELECT roomID, hotelID, numPersons, rate FROM hotelroom
											WHERE (roomID,hotelID) NOT IN ( SELECT roomID,hotelID
													 FROM bookings 
                                                     WHERE fromDate >= checkInDate AND fromDate < checkOutDate 
													 OR toDate > CheckInDate AND toDate <= checkOutDate );
end $$


/*
 * Usage: call freeRoomsAllHotels(fromDate, toDate )
 * Pre:   fromDate and toDate are the checkin and checkout dates.
 * Post:  Returns data about all hotels and rooms that are not occupied between the dates.
 */
delimiter $$
drop procedure if exists freeRoomsAllHotels $$
create procedure freeRoomsAllHotels(fromDate DATE, toDate DATE )
begin
		Call fillFreeRoomsTableAllHotels(fromDate, toDate );
        SELECT hotel.hotelID, hotelName, hotelChain, hotelLocation, roomID, numPersons, rate 
        FROM hotel JOIN freeRooms ON freeRooms.hotelID = hotel.hotelID;
end $$
delimiter $$

/*
 * Usage: call freeRoomsLocation(location, fromDate, toDate )
 * Pre:   location is the location we want to search in.  
 *		  fromDate and toDate are the checkin and checkout dates.
 * Post:  Returns data about all hotels and rooms that are not occupied between the dates.
 */
delimiter $$
drop procedure if exists freeRoomsLocation $$
create procedure freeRoomsLocation(location VARCHAR(50), fromDate DATE, toDate DATE )
begin
		Call fillFreeRoomsTableAllHotels(fromDate, toDate );
        SELECT hotel.hotelID, hotelName, hotelChain, hotelLocation, roomID, numPersons, rate 
        FROM hotel JOIN freeRooms ON freeRooms.hotelID = hotel.hotelID WHERE hotelLocation = location;
end $$
delimiter $$

/*
 * Usage: call freeRoomsHotelChain(chain, fromDate, toDate )
 * Pre:   chain is the hotelchain we want to search for hotels in.  
 *		  fromDate and toDate are the checkin and checkout dates.
 * Post:  Returns data about all hotels and rooms that are not occupied between the dates where hotel belongs to the hotelChain chain.
 */
delimiter $$
drop procedure if exists freeRoomsHotelChain $$
create procedure freeRoomsHotelChain(chainName VARCHAR(50), fromDate DATE, toDate DATE )
begin
		Call fillFreeRoomsTableAllHotels(fromDate, toDate );
        SELECT hotel.hotelID, hotelName, hotelChain, hotelLocation, roomID, numPersons, rate 
        FROM hotel JOIN freeRooms ON freeRooms.hotelID = hotel.hotelID WHERE hotelChain = chainName;
end $$
delimiter $$

/*
 * Usage: call freeRoomsHotelSubName(nameString, fromDate, toDate )
 * Pre:   nameString is a string.
 *		  fromDate and toDate are the checkin and checkout dates.
 * Post:  Returns data about all hotels and rooms where nameString is a substring in the hotel name.
 */
delimiter $$
drop procedure if exists freeRoomsHotelSubName $$
create procedure freeRoomsHotelSubName(nameString VARCHAR(50), fromDate DATE, toDate DATE )
begin
		Call fillFreeRoomsTableAllHotels(fromDate, toDate );
        SELECT hotel.hotelID, hotelName, hotelChain, hotelLocation, roomID, numPersons, rate 
        FROM hotel JOIN freeRooms ON freeRooms.hotelID = hotel.hotelID WHERE INSTR( LCASE(hotelName), LCASE(nameString) ) > 0;
end $$
delimiter $$

/*
 * Usage: call getHotelFacilities( hotelName )
 * Pre:   hotelName is the name of the hotel.
 * Post:  Returns data about all facilities that belong to the hotel.
 */
delimiter $$
drop procedure if exists getHotelFacilities $$
create procedure getHotelFacilities( hotelName VARCHAR(50) )
begin
	Select ID,name,description from facility join hotelhasFacility on ID=facilityID where hotelID = (Select hotelID from hotel where hotel.hotelName = hotelName);
end $$



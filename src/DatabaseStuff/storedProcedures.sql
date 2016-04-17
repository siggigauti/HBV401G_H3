
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
 *  Returns the info about rooms that are not occupied during
 *  the two dates in specific hotel.
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
 *  Creates a booking with the above values.
 */
delimiter $$
drop procedure if exists createBooking $$
create procedure createBooking( hotel int, room int, fromDate DATE, toDate DATE )
begin
	INSERT INTO bookings( HotelID, RoomID, CheckInDate, CheckOutDate ) VALUES( hotel, room, fromDate, toDate ); 
end $$

/*
 *  Fills the temp table with all free rooms in all hotels.
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
													 OR toDate > CheckInDate AND toDate <= checkOutDate )
											ORDER BY hotelID;
end $$


/*
 * Returns data about all hotels and rooms that are not occupied between the dates.
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

call freeRoomsAllHotels("05-05-2016", "06-06-2016")

/*
 * Returns data about all hotels and rooms that are not occupied between the dates.
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
 * Returns data about all hotels and rooms that are not occupied between the dates where hotel belongs to the hotelChain chain.
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
 * Returns data about all hotels and rooms where nameString is a substring in the hotel name.
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
 * Returns data about all facilities that belong to the hotel.
 */
delimiter $$
drop procedure if exists getHotelFacilities $$
create procedure getHotelFacilities( hotelName VARCHAR(50) )
begin
	Select ID,name,description from facility join hotelhasFacility on ID=facilityID where hotelID = (Select hotelID from hotel where hotel.hotelName = hotelName);
end $$



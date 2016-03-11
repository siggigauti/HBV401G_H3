
/*
 * Temp table to store information about free rooms.
 */
drop temporary table if exists freeRooms;
CREATE TEMPORARY TABLE freeRooms(
	roomID int,
    hotelID int,
    numPersons int,
    rate int
);

/*  
 * Usage: call fillFreeRoomsTable( hotel, fromDate, toDate )
 * Pre:	  hotel is the ID of the hotel to search in.
 * 		  fromDate and toDate are the checkin and checkout dates.
 * Post:  Fills the temp table freeRooms with information about free rooms in
 * 		  the hotel during the two dates.
 */
delimiter $$
drop procedure if exists fillFreeRoomsTable $$
create procedure fillFreeRoomsTable( hotel int, fromDate DATE, toDate DATE )
begin
	TRUNCATE TABLE freeRooms; -- Hreinsar freeRooms töfluna.
	INSERT INTO freeRooms(roomID, hotelID, numPersons, rate)
    SELECT roomID, hotelID, numPersons, rate FROM hotelroom WHERE hotelID = hotel 
								 AND roomID NOT IN ( SELECT RoomID
													 FROM bookings 
                                                     WHERE HotelID = hotel 
                                                     AND fromDate >= checkInDate AND fromDate < checkOutDate 
                                                     OR toDate > CheckInDate AND toDate <= checkOutDate );
end $$

/*  
 * Usage: call freeRoomsFromTo( hotel, fromDate, toDate )
 * Pre:	  hotel is the ID of the hotel to search in.
 * 		  fromDate and toDate are the checkin and checkout dates.
 * Post:  Returns the roomID's of rooms that are not occupied during
 * 		  the two dates and that are in the hotel searched for.
 * 		  
 */
delimiter $$
drop procedure if exists freeRoomsFromTo $$
create procedure freeRoomsFromTo( hotel int, fromDate DATE, toDate DATE )
begin
		Call fillFreeRoomsTable( hotel, fromDate, toDate );
        SELECT roomID FROM freeRooms;
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
 * Usage: call findFreeRoomsNumPeople( hotel, numPeople, fromDate, toDate, exactAmmount )
 * Pre:   hotel is the ID of hotel to search in.
 * 		  numPeople is the ammount of people the room should have space for.
 *        fromDate and toDate are the checkin and checkout dates.
 *		  exactAmmount is a boolean that determines if we should look for rooms that have
 * 					   space for exactly numPeople or >= numPeople.
 * Post:  Returns the roomID's of rooms that are not occupied in that timespan and can room atleast numPeople persons.
 */
delimiter $$
drop procedure if exists findFreeRoomsNumPeople $$
create procedure findFreeRoomsNumPeople( hotel int, numPeople int, fromDate Date, toDate Date, exactAmmount Boolean)
begin
	call fillFreeRoomsTable( hotel, fromDate, toDate ); -- Fyllir temp töfluna
    if( exactAmmount ) 
		Then SELECT roomID FROM freeRooms WHERE numPersons = numPeople;
    else
		SELECT roomID FROM freeRooms WHERE numPersons >= numPeople;
	end if;
end $$

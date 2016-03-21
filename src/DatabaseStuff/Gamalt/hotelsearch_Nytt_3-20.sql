-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 20, 2016 at 05:37 PM
-- Server version: 10.1.10-MariaDB
-- PHP Version: 5.6.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotelsearch`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `createBooking` (`hotel` INT, `room` INT, `fromDate` DATE, `toDate` DATE)  begin
	INSERT INTO bookings( HotelID, RoomID, CheckInDate, CheckOutDate ) VALUES( hotel, room, fromDate, toDate ); 
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `createTempTable` ()  begin
	drop temporary table if exists freeRooms;
	CREATE TEMPORARY TABLE freeRooms(
		roomID int,
		hotelID int,
		numPersons int,
		rate int
);
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `fillFreeRoomsTable` (IN `hotel` INT, IN `fromDate` DATE, IN `toDate` DATE)  BEGIN
	call createTempTable();
	INSERT INTO freeRooms(roomID, hotelID, numPersons, rate)
    SELECT roomID, hotelID, numPersons, rate FROM hotelroom
											WHERE hotel = hotelID AND
											roomID NOT IN ( SELECT RoomID
													 FROM bookings 
                                                     WHERE fromDate >= checkInDate AND fromDate < checkOutDate 
													 OR toDate > CheckInDate AND toDate <= checkOutDate );
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `fillFreeRoomsTableAllHotels` (`fromDate` DATE, `toDate` DATE)  begin
	call createTempTable();
	INSERT INTO freeRooms(roomID, hotelID, numPersons, rate)
    SELECT roomID, hotelID, numPersons, rate FROM hotelroom
											WHERE (roomID,hotelID) NOT IN ( SELECT roomID,hotelID
													 FROM bookings 
                                                     WHERE fromDate >= checkInDate AND fromDate < checkOutDate 
													 OR toDate > CheckInDate AND toDate <= checkOutDate );
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `findFreeRoomsNumPeople` (`hotel` INT, `numPeople` INT, `fromDate` DATE, `toDate` DATE, `exactAmmount` BOOLEAN)  begin
	call fillFreeRoomsTable( hotel, fromDate, toDate );     if( exactAmmount ) 
		Then SELECT roomID FROM freeRooms WHERE numPersons = numPeople;
    else
		SELECT roomID FROM freeRooms WHERE numPersons >= numPeople;
	end if;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `freeRoomsAllHotels` (`fromDate` DATE, `toDate` DATE)  begin
		Call fillFreeRoomsTableAllHotels(fromDate, toDate );
        SELECT hotel.hotelID, hotelName, hotelChain, hotelLocation, roomID, numPersons, rate 
        FROM hotel JOIN freeRooms ON freeRooms.hotelID = hotel.hotelID;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `freeRoomsFromTo` (`hotel` INT, `fromDate` DATE, `toDate` DATE)  begin
		Call fillFreeRoomsTable( hotel, fromDate, toDate );
        SELECT roomID FROM freeRooms;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `freeRoomsFromToAll` (IN `hotel` INT, IN `fromDate` DATE, IN `toDate` DATE)  BEGIN
		Call fillFreeRoomsTable(hotel,fromDate, toDate);
        SELECT hotel.hotelID, hotelName, hotelChain, hotelLocation, roomID, numPersons, rate 
        FROM hotel JOIN freeRooms ON freeRooms.hotelID = hotel.hotelID;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `freeRoomsHotelChain` (`chainName` VARCHAR(50), `fromDate` DATE, `toDate` DATE)  begin
		Call fillFreeRoomsTableAllHotels(fromDate, toDate );
        SELECT hotel.hotelID, hotelName, hotelChain, hotelLocation, roomID, numPersons, rate 
        FROM hotel JOIN freeRooms ON freeRooms.hotelID = hotel.hotelID WHERE hotelChain = chainName;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `freeRoomsInHotel` (`hotel` VARCHAR(50), `fromDate` DATE, `toDate` DATE)  begin
		Call fillFreeRoomsTableAllHotels(fromDate, toDate);
        SELECT hotel.hotelID, hotelName, hotelChain, hotelLocation, roomID, numPersons, rate 
        FROM hotel JOIN freeRooms ON freeRooms.hotelID = hotel.hotelID WHERE hotel.hotelName = hotel;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `freeRoomsLocation` (`location` VARCHAR(50), `fromDate` DATE, `toDate` DATE)  begin
		Call fillFreeRoomsTableAllHotels(fromDate, toDate );
        SELECT hotel.hotelID, hotelName, hotelChain, hotelLocation, roomID, numPersons, rate 
        FROM hotel JOIN freeRooms ON freeRooms.hotelID = hotel.hotelID WHERE hotelLocation = location;
end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE `bookings` (
  `BookingID` int(11) NOT NULL,
  `HotelID` int(11) NOT NULL,
  `RoomID` int(11) NOT NULL,
  `CheckInDate` date NOT NULL,
  `CheckOutDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bookings`
--

INSERT INTO `bookings` (`BookingID`, `HotelID`, `RoomID`, `CheckInDate`, `CheckOutDate`) VALUES
(1, 1, 1, '2016-03-01', '2016-03-05'),
(2, 1, 2, '2016-03-03', '2016-03-04'),
(8, 1, 3, '2016-03-03', '2016-03-17'),
(9, 2, 1, '2016-03-16', '2016-03-17'),
(10, 1, 2, '2016-03-08', '2016-03-15'),
(11, 1, 1, '2016-03-23', '2016-03-30'),
(12, 1, 2, '2016-03-30', '2016-04-07'),
(13, 3, 3, '2016-03-04', '2016-03-07');

-- --------------------------------------------------------

--
-- Table structure for table `facility`
--

CREATE TABLE `facility` (
  `ID` int(11) NOT NULL,
  `Name` char(255) NOT NULL,
  `Description` char(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `facility`
--

INSERT INTO `facility` (`ID`, `Name`, `Description`) VALUES
(1, 'Room service.', 'The hotel provides room service to it''s guests.'),
(2, 'Complimentary breakfast.', 'Between 6:30 AM and 10:00 AM the hotel provides a complimentary breakfast to it''s guests.'),
(3, 'In-house Restaurant.', 'We have an in-house restaurant at this hotel.'),
(4, 'Pool.', 'This hotel has a pool.'),
(5, 'Luggage storage.', 'The hotel can store luggage upon arrival or departure.'),
(6, 'Underground garage.', 'The hotel has a garage with heating.'),
(7, 'Bar.', 'The hotel has a bar.'),
(8, 'Gym.', 'The hotel has a gym for it''s guests.'),
(9, 'Special event prices.', 'The hotel often offers it''s guests tickets to events at a special price.'),
(10, 'WiFi.', 'The hotel offers free wifi to it''s guests.'),
(11, 'Handicap accessible.', 'The hotel is handicap accessible.'),
(12, 'Computer''s.', 'The hotel has computers for it''s guests to use to check e-mail or browse the web.'),
(13, 'Spa.', 'The hotel has a spa.');

-- --------------------------------------------------------

--
-- Table structure for table `hotel`
--

CREATE TABLE `hotel` (
  `HotelID` int(11) NOT NULL,
  `HotelName` varchar(50) NOT NULL,
  `HotelChain` varchar(50) NOT NULL,
  `HotelLocation` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hotel`
--

INSERT INTO `hotel` (`HotelID`, `HotelName`, `HotelChain`, `HotelLocation`) VALUES
(1, 'Hótel Reykjavík', 'Nordica', 'Reykjavík'),
(2, 'Hótel Laugarvegur', 'Hilton', 'Reykjavík'),
(3, 'Hótel Reykjavík', 'Nordica', 'Reykjavík'),
(4, 'Hótel Laugarvegur', 'Hilton', 'Reykjavík');

-- --------------------------------------------------------

--
-- Table structure for table `hotelhasfacility`
--

CREATE TABLE `hotelhasfacility` (
  `hotelID` int(11) DEFAULT NULL,
  `facilityID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hotelhasfacility`
--

INSERT INTO `hotelhasfacility` (`hotelID`, `facilityID`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 7),
(1, 8),
(1, 10),
(2, 1),
(2, 2),
(2, 5),
(2, 9),
(3, 1),
(3, 2),
(3, 3),
(3, 10),
(3, 11),
(3, 12),
(3, 8),
(3, 6),
(4, 1),
(4, 4);

-- --------------------------------------------------------

--
-- Table structure for table `hotelroom`
--

CREATE TABLE `hotelroom` (
  `RoomID` int(11) NOT NULL,
  `HotelID` int(11) NOT NULL,
  `numPersons` int(11) NOT NULL,
  `rate` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hotelroom`
--

INSERT INTO `hotelroom` (`RoomID`, `HotelID`, `numPersons`, `rate`) VALUES
(1, 1, 2, 20000),
(1, 2, 2, 20000),
(1, 3, 2, 2000),
(2, 1, 4, 50000),
(2, 2, 3, 20000),
(3, 1, 5, 30000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`BookingID`);

--
-- Indexes for table `facility`
--
ALTER TABLE `facility`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`HotelID`);

--
-- Indexes for table `hotelroom`
--
ALTER TABLE `hotelroom`
  ADD PRIMARY KEY (`RoomID`,`HotelID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookings`
--
ALTER TABLE `bookings`
  MODIFY `BookingID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `facility`
--
ALTER TABLE `facility`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `hotel`
--
ALTER TABLE `hotel`
  MODIFY `HotelID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

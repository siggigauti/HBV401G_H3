-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 21, 2016 at 02:40 AM
-- Server version: 10.1.9-MariaDB
-- PHP Version: 5.6.15

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

CREATE DEFINER=`root`@`localhost` PROCEDURE `freeRoomsHotelSubName` (`nameString` VARCHAR(50), `fromDate` DATE, `toDate` DATE)  begin
		Call fillFreeRoomsTableAllHotels(fromDate, toDate );
        SELECT hotel.hotelID, hotelName, hotelChain, hotelLocation, roomID, numPersons, rate 
        FROM hotel JOIN freeRooms ON freeRooms.hotelID = hotel.hotelID WHERE INSTR( LCASE(hotelName), LCASE(nameString) ) > 0;
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

CREATE DEFINER=`root`@`localhost` PROCEDURE `getHotelFacilities` (`hotelName` VARCHAR(50))  begin
	Select ID,name,description from facility join hotelhasFacility on ID=facilityID where hotelID = (Select hotelID from hotel where hotel.hotelName = hotelName);
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
(1, 'Room service', 'The hotel provides room service to its guests'),
(2, 'Complimentary breakfast', 'Between 6:30 AM and 10:00 AM the hotel provides a complimentary breakfast to its guests'),
(3, 'In-house Restaurant', 'We have an in-house restaurant at this hotel'),
(4, 'Pool', 'This hotel has a pool'),
(5, 'Luggage storage', 'The hotel can store luggage upon arrival or departure'),
(6, 'Underground garage', 'The hotel has a garage with heating'),
(7, 'Bar', 'The hotel has a bar'),
(8, 'Gym', 'The hotel has a gym for its guests'),
(9, 'Special event prices', 'The hotel often offers its guests tickets to events at a special price.'),
(10, 'WiFi', 'The hotel offers free wifi to its guests'),
(11, 'Handicap accessible', 'The hotel is handicap accessible'),
(12, 'Computers', 'The hotel has computers for its guests to use to check e-mail or browse the web'),
(13, 'Spa', 'The hotel has a spa');

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
(3, 'Hótel Hamar', 'Nordica', 'Egilsstaðir'),
(4, 'Hótel Njála', 'Hilton', 'Akureyri'),
(5, 'Hótel Reykhamar', 'Radison', 'Ísafjörður');

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
(4, 4),
(5, 1),
(5, 4),
(5, 3),
(5, 8),
(5, 10),
(5, 12),
(5, 1),
(5, 4),
(5, 3),
(5, 8),
(5, 10),
(5, 12);

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
(1, 1, 4, 20000),
(1, 2, 1, 11000),
(1, 3, 5, 20000),
(1, 4, 7, 84000),
(1, 5, 1, 9000),
(2, 1, 2, 24000),
(2, 2, 8, 48000),
(2, 3, 2, 14000),
(2, 4, 1, 4000),
(2, 5, 6, 60000),
(3, 1, 5, 20000),
(3, 2, 4, 44000),
(3, 3, 8, 40000),
(3, 4, 3, 24000),
(3, 5, 7, 28000),
(4, 1, 3, 12000),
(4, 2, 7, 56000),
(4, 3, 3, 30000),
(4, 4, 8, 80000),
(4, 5, 8, 32000),
(5, 1, 5, 50000),
(5, 2, 1, 11000),
(5, 3, 5, 20000),
(5, 4, 3, 18000),
(5, 5, 7, 77000),
(6, 1, 4, 40000),
(6, 2, 7, 63000),
(6, 3, 7, 63000),
(6, 4, 3, 36000),
(6, 5, 1, 7000),
(7, 1, 4, 36000),
(7, 2, 8, 40000),
(7, 3, 4, 48000),
(7, 4, 5, 20000),
(7, 5, 7, 84000),
(8, 1, 4, 20000),
(8, 2, 7, 28000),
(8, 3, 2, 14000),
(8, 4, 1, 5000),
(8, 5, 3, 33000),
(9, 1, 1, 9000),
(9, 2, 6, 42000),
(9, 3, 8, 56000),
(9, 4, 3, 24000),
(9, 5, 4, 24000),
(10, 1, 3, 36000),
(10, 2, 6, 60000),
(10, 3, 5, 30000),
(10, 4, 7, 28000),
(10, 5, 4, 40000),
(11, 1, 2, 20000),
(11, 2, 3, 21000),
(11, 3, 2, 16000),
(11, 4, 7, 77000),
(11, 5, 5, 35000),
(12, 1, 4, 36000),
(12, 2, 4, 36000),
(12, 3, 8, 48000),
(12, 4, 5, 30000),
(12, 5, 4, 44000),
(13, 1, 1, 12000),
(13, 2, 3, 18000),
(13, 3, 6, 42000),
(13, 4, 3, 12000),
(13, 5, 4, 44000),
(14, 1, 6, 54000),
(14, 2, 2, 20000),
(14, 3, 5, 50000),
(14, 4, 1, 4000),
(14, 5, 1, 11000),
(15, 1, 4, 48000),
(15, 2, 1, 12000),
(15, 3, 8, 88000),
(15, 4, 4, 36000),
(15, 5, 5, 20000),
(16, 1, 7, 35000),
(16, 2, 5, 50000),
(16, 3, 1, 4000),
(16, 4, 8, 64000),
(16, 5, 5, 60000),
(17, 1, 1, 4000),
(17, 2, 8, 40000),
(17, 3, 7, 70000),
(17, 4, 7, 35000),
(17, 5, 3, 36000),
(18, 1, 5, 35000),
(18, 2, 3, 12000),
(18, 3, 1, 7000),
(18, 4, 7, 35000),
(18, 5, 4, 20000),
(19, 1, 7, 49000),
(19, 2, 8, 88000),
(19, 3, 7, 35000),
(19, 4, 5, 45000),
(19, 5, 1, 6000),
(20, 1, 2, 16000),
(20, 2, 6, 66000),
(20, 4, 4, 32000),
(20, 5, 5, 25000),
(21, 1, 7, 56000),
(21, 4, 4, 48000),
(21, 5, 1, 10000),
(22, 1, 3, 21000),
(22, 4, 8, 72000),
(22, 5, 8, 32000),
(23, 1, 6, 72000),
(23, 4, 8, 80000),
(23, 5, 7, 28000),
(24, 1, 7, 70000),
(24, 4, 2, 10000),
(24, 5, 6, 60000),
(25, 1, 6, 54000),
(25, 4, 8, 32000),
(25, 5, 2, 24000),
(26, 4, 2, 22000),
(27, 4, 3, 33000),
(28, 4, 5, 45000),
(29, 4, 7, 63000),
(30, 4, 2, 22000),
(31, 4, 6, 36000),
(32, 4, 6, 48000);

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
  MODIFY `HotelID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

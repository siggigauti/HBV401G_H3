-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 19, 2016 at 03:25 AM
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
													 OR toDate > CheckInDate AND toDate <= checkOutDate )
											ORDER BY hotelID;
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

CREATE DEFINER=`root`@`localhost` PROCEDURE `getReviews` (`hotelName` VARCHAR(50))  begin
	SELECT title, content, stars, reviewDate, reviewerName FROM review WHERE hotelID = (Select hotelID from hotel where hotel.hotelName = hotelName);
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `writeReview` (`hotelID` INT, `title` VARCHAR(55), `content` VARCHAR(500), `stars` INT, `reviewDate` DATE, `reviewerName` VARCHAR(55))  begin
	INSERT INTO review(hotelID, title, content, stars, reviewDate, reviewerName) VALUES (hotelID, title, content, stars, reviewDate, reviewerName);
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
(241, 1, 18, '2016-11-12', '2016-11-16'),
(242, 1, 13, '2016-11-12', '2016-11-16'),
(243, 1, 10, '2016-11-12', '2016-11-16'),
(244, 2, 1, '2016-05-06', '2016-05-08');

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
(5, 'Hótel Reykhamar', 'Radison', 'Ísafjörður'),
(6, 'Hótel Vesturbær', 'Radison', 'Reykjavík'),
(7, 'Hótel Laugar', 'Nordica', 'Reykjavík'),
(8, 'Hótel Lagarfljót', 'EastHotels', 'Egilsstaðir'),
(9, 'Hótel Reykur', 'EastHotels', 'Egilsstaðir'),
(10, 'Hótel Djúpavík', 'Radisson', 'Djúpavík');

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
(1, 6, 5, 45000),
(1, 7, 5, 50000),
(1, 8, 2, 8000),
(1, 9, 1, 9000),
(1, 10, 3, 30000),
(2, 1, 2, 24000),
(2, 2, 8, 48000),
(2, 3, 2, 14000),
(2, 4, 1, 4000),
(2, 5, 6, 60000),
(2, 6, 3, 36000),
(2, 7, 1, 10000),
(2, 8, 7, 35000),
(2, 9, 4, 48000),
(2, 10, 1, 10000),
(3, 1, 5, 20000),
(3, 2, 4, 44000),
(3, 3, 8, 40000),
(3, 4, 3, 24000),
(3, 5, 7, 28000),
(3, 6, 4, 28000),
(3, 7, 1, 6000),
(3, 8, 5, 35000),
(3, 9, 6, 24000),
(3, 10, 7, 77000),
(4, 1, 3, 12000),
(4, 2, 7, 56000),
(4, 3, 3, 30000),
(4, 4, 8, 80000),
(4, 5, 8, 32000),
(4, 6, 5, 30000),
(4, 7, 4, 32000),
(4, 8, 4, 32000),
(4, 9, 5, 55000),
(4, 10, 3, 18000),
(5, 1, 5, 50000),
(5, 2, 1, 11000),
(5, 3, 5, 20000),
(5, 4, 3, 18000),
(5, 5, 7, 77000),
(5, 6, 2, 24000),
(5, 7, 8, 40000),
(5, 8, 8, 64000),
(5, 9, 5, 50000),
(5, 10, 2, 12000),
(6, 1, 4, 40000),
(6, 2, 7, 63000),
(6, 3, 7, 63000),
(6, 4, 3, 36000),
(6, 5, 1, 7000),
(6, 6, 1, 4000),
(6, 7, 7, 70000),
(6, 8, 3, 12000),
(6, 9, 1, 8000),
(6, 10, 2, 14000),
(7, 1, 4, 36000),
(7, 2, 8, 40000),
(7, 3, 4, 48000),
(7, 4, 5, 20000),
(7, 5, 7, 84000),
(7, 6, 7, 63000),
(7, 7, 1, 11000),
(7, 8, 3, 12000),
(7, 9, 6, 36000),
(7, 10, 2, 14000),
(8, 1, 4, 20000),
(8, 2, 7, 28000),
(8, 3, 2, 14000),
(8, 4, 1, 5000),
(8, 5, 3, 33000),
(8, 6, 3, 27000),
(8, 7, 3, 24000),
(8, 8, 6, 24000),
(8, 9, 3, 30000),
(8, 10, 8, 88000),
(9, 1, 1, 9000),
(9, 2, 6, 42000),
(9, 3, 8, 56000),
(9, 4, 3, 24000),
(9, 5, 4, 24000),
(9, 6, 2, 20000),
(9, 7, 2, 10000),
(9, 8, 8, 56000),
(9, 9, 6, 66000),
(9, 10, 4, 16000),
(10, 1, 3, 36000),
(10, 2, 6, 60000),
(10, 3, 5, 30000),
(10, 4, 7, 28000),
(10, 5, 4, 40000),
(10, 6, 6, 54000),
(10, 7, 7, 70000),
(10, 8, 4, 20000),
(10, 9, 2, 20000),
(10, 10, 3, 21000),
(11, 1, 2, 20000),
(11, 2, 3, 21000),
(11, 3, 2, 16000),
(11, 4, 7, 77000),
(11, 5, 5, 35000),
(11, 6, 7, 77000),
(11, 7, 1, 6000),
(11, 8, 4, 28000),
(11, 9, 1, 9000),
(11, 10, 4, 36000),
(12, 1, 4, 36000),
(12, 2, 4, 36000),
(12, 3, 8, 48000),
(12, 4, 5, 30000),
(12, 5, 4, 44000),
(12, 6, 6, 66000),
(12, 7, 6, 72000),
(12, 8, 5, 40000),
(12, 9, 2, 16000),
(12, 10, 8, 72000),
(13, 1, 1, 12000),
(13, 2, 3, 18000),
(13, 3, 6, 42000),
(13, 4, 3, 12000),
(13, 5, 4, 44000),
(13, 6, 1, 12000),
(13, 7, 5, 55000),
(13, 8, 8, 64000),
(13, 9, 3, 33000),
(13, 10, 5, 55000),
(14, 1, 6, 54000),
(14, 2, 2, 20000),
(14, 3, 5, 50000),
(14, 4, 1, 4000),
(14, 5, 1, 11000),
(14, 6, 5, 60000),
(14, 7, 2, 24000),
(14, 8, 6, 24000),
(14, 9, 6, 42000),
(14, 10, 3, 27000),
(15, 1, 4, 48000),
(15, 2, 1, 12000),
(15, 3, 8, 88000),
(15, 4, 4, 36000),
(15, 5, 5, 20000),
(15, 6, 3, 33000),
(15, 7, 2, 18000),
(15, 8, 6, 66000),
(15, 9, 4, 20000),
(15, 10, 8, 88000),
(16, 1, 7, 35000),
(16, 2, 5, 50000),
(16, 3, 1, 4000),
(16, 4, 8, 64000),
(16, 5, 5, 60000),
(16, 6, 6, 60000),
(16, 7, 5, 35000),
(16, 8, 8, 64000),
(16, 9, 7, 63000),
(16, 10, 8, 32000),
(17, 1, 1, 4000),
(17, 2, 8, 40000),
(17, 3, 7, 70000),
(17, 4, 7, 35000),
(17, 5, 3, 36000),
(17, 6, 7, 28000),
(17, 7, 3, 18000),
(17, 8, 6, 24000),
(17, 9, 8, 72000),
(17, 10, 8, 88000),
(18, 1, 5, 35000),
(18, 2, 3, 12000),
(18, 3, 1, 7000),
(18, 4, 7, 35000),
(18, 5, 4, 20000),
(18, 6, 6, 66000),
(18, 7, 3, 15000),
(18, 8, 5, 20000),
(18, 9, 6, 72000),
(18, 10, 2, 24000),
(19, 1, 7, 49000),
(19, 2, 8, 88000),
(19, 3, 7, 35000),
(19, 4, 5, 45000),
(19, 5, 1, 6000),
(19, 6, 6, 42000),
(19, 7, 6, 36000),
(19, 8, 8, 72000),
(19, 9, 1, 4000),
(19, 10, 2, 24000),
(20, 1, 2, 16000),
(20, 2, 6, 66000),
(20, 4, 4, 32000),
(20, 5, 5, 25000),
(20, 6, 5, 40000),
(20, 7, 1, 4000),
(20, 8, 7, 70000),
(20, 9, 2, 16000),
(20, 10, 6, 54000),
(21, 1, 7, 56000),
(21, 4, 4, 48000),
(21, 5, 1, 10000),
(21, 6, 4, 28000),
(21, 7, 1, 11000),
(21, 8, 4, 40000),
(21, 9, 3, 21000),
(21, 10, 5, 50000),
(22, 1, 3, 21000),
(22, 4, 8, 72000),
(22, 5, 8, 32000),
(22, 6, 3, 24000),
(22, 7, 7, 70000),
(22, 8, 4, 48000),
(22, 9, 2, 24000),
(22, 10, 8, 72000),
(23, 1, 6, 72000),
(23, 4, 8, 80000),
(23, 5, 7, 28000),
(23, 6, 5, 35000),
(23, 7, 2, 16000),
(23, 8, 1, 5000),
(23, 9, 5, 40000),
(23, 10, 8, 32000),
(24, 1, 7, 70000),
(24, 4, 2, 10000),
(24, 5, 6, 60000),
(24, 6, 1, 9000),
(24, 7, 6, 48000),
(24, 8, 8, 80000),
(24, 9, 1, 5000),
(24, 10, 7, 84000),
(25, 1, 6, 54000),
(25, 4, 8, 32000),
(25, 5, 2, 24000),
(25, 6, 2, 8000),
(25, 7, 5, 55000),
(25, 8, 3, 18000),
(25, 9, 1, 6000),
(25, 10, 5, 25000),
(26, 4, 2, 22000),
(26, 6, 5, 40000),
(26, 7, 2, 24000),
(26, 8, 3, 33000),
(26, 9, 1, 11000),
(26, 10, 7, 56000),
(27, 4, 3, 33000),
(27, 6, 4, 24000),
(27, 7, 6, 42000),
(27, 8, 4, 24000),
(27, 9, 6, 66000),
(27, 10, 4, 32000),
(28, 4, 5, 45000),
(28, 6, 3, 15000),
(28, 7, 3, 15000),
(28, 8, 6, 66000),
(28, 9, 4, 28000),
(28, 10, 3, 33000),
(29, 4, 7, 63000),
(29, 6, 3, 36000),
(29, 7, 8, 40000),
(29, 8, 3, 24000),
(29, 9, 1, 4000),
(29, 10, 1, 5000),
(30, 4, 2, 22000),
(30, 6, 5, 20000),
(30, 7, 8, 32000),
(30, 8, 5, 20000),
(30, 9, 8, 80000),
(30, 10, 8, 64000),
(31, 4, 6, 36000),
(31, 6, 1, 10000),
(31, 7, 6, 72000),
(31, 8, 7, 49000),
(31, 9, 5, 30000),
(31, 10, 6, 72000),
(32, 4, 6, 48000),
(32, 6, 6, 30000),
(32, 7, 2, 24000),
(32, 8, 3, 21000),
(32, 9, 1, 6000),
(32, 10, 3, 21000),
(33, 6, 1, 7000),
(33, 7, 3, 12000),
(33, 8, 1, 5000),
(33, 9, 8, 56000),
(33, 10, 8, 48000),
(34, 6, 6, 60000),
(34, 7, 8, 96000),
(34, 8, 2, 20000),
(34, 9, 2, 10000),
(35, 6, 2, 18000),
(35, 7, 6, 54000),
(35, 8, 4, 48000),
(35, 9, 3, 30000),
(36, 6, 1, 9000),
(36, 7, 4, 36000),
(36, 8, 3, 30000),
(36, 9, 3, 24000),
(37, 6, 3, 27000),
(37, 7, 8, 88000),
(37, 8, 1, 4000),
(37, 9, 6, 54000),
(38, 7, 1, 5000),
(38, 8, 3, 21000),
(38, 9, 5, 20000),
(39, 7, 6, 54000),
(39, 8, 8, 56000),
(39, 9, 8, 40000),
(40, 7, 1, 8000),
(40, 8, 6, 42000),
(40, 9, 4, 40000),
(41, 7, 6, 48000),
(41, 8, 6, 48000),
(41, 9, 3, 12000),
(42, 7, 4, 28000),
(42, 8, 6, 54000),
(42, 9, 4, 28000),
(43, 7, 7, 28000),
(43, 8, 2, 14000),
(43, 9, 2, 20000),
(44, 7, 6, 48000),
(44, 8, 1, 12000),
(44, 9, 2, 14000),
(45, 7, 6, 66000),
(45, 8, 5, 30000),
(45, 9, 2, 18000),
(46, 7, 6, 60000),
(46, 8, 3, 21000),
(46, 9, 6, 60000),
(47, 7, 2, 16000),
(47, 8, 6, 42000),
(47, 9, 4, 44000),
(48, 7, 1, 5000),
(48, 8, 1, 6000),
(48, 9, 5, 55000),
(49, 7, 4, 48000),
(49, 8, 8, 56000),
(49, 9, 6, 24000),
(50, 7, 4, 20000),
(50, 8, 3, 12000),
(51, 7, 5, 50000),
(51, 8, 3, 27000),
(52, 7, 1, 5000),
(52, 8, 3, 12000),
(53, 7, 3, 15000),
(53, 8, 4, 48000),
(54, 7, 6, 24000),
(54, 8, 8, 56000),
(55, 7, 3, 27000),
(55, 8, 2, 12000),
(56, 7, 8, 72000),
(56, 8, 6, 36000),
(57, 7, 6, 24000);

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `ReviewID` int(11) NOT NULL,
  `hotelID` int(11) NOT NULL,
  `title` varchar(55) NOT NULL,
  `content` varchar(500) NOT NULL,
  `stars` int(11) NOT NULL,
  `reviewDate` date NOT NULL,
  `reviewerName` varchar(55) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `review`
--

INSERT INTO `review` (`ReviewID`, `hotelID`, `title`, `content`, `stars`, `reviewDate`, `reviewerName`) VALUES
(4, 1, 'test', 'testtest', 5, '2015-05-05', 'Jón'),
(5, 1, 'asdasd', 'testasdasdasdasasdasdadsssssssasdasdasdtest', 5, '2015-05-05', 'JónAD'),
(6, 1, 'asdasd', 'testasdasdasdasasdasdadsssssssasdasdasdtest', 5, '2015-05-05', 'JónAD'),
(7, 1, 'Titill á ummæli', 'Flott hótel', 5, '2015-05-05', 'Jón Jónsson'),
(8, 2, 'Titill á ummæli2', 'Flott hótel', 5, '2015-05-05', 'Jón Jónsson'),
(9, 4, 'Kúl hótel', 'Flott hótel', 5, '2015-05-05', 'Jón Jónsson');

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
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`ReviewID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookings`
--
ALTER TABLE `bookings`
  MODIFY `BookingID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=245;
--
-- AUTO_INCREMENT for table `facility`
--
ALTER TABLE `facility`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `hotel`
--
ALTER TABLE `hotel`
  MODIFY `HotelID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
  MODIFY `ReviewID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

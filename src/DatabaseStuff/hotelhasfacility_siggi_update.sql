-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 19, 2016 at 11:10 PM
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
(5, 12),
(6, 1),
(6, 2),
(6, 6),
(6, 7),
(6, 9),
(6, 4),
(6, 11),
(6, 13),
(7, 1),
(7, 3),
(7, 5),
(7, 6),
(7, 7),
(7, 8),
(7, 12),
(8, 2),
(8, 4),
(8, 6),
(8, 10),
(9, 13),
(9, 3),
(9, 4),
(9, 6),
(9, 9),
(10, 1),
(10, 2),
(10, 6),
(10, 7),
(10, 8);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

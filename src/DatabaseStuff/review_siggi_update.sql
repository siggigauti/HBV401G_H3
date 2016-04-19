-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 19, 2016 at 11:09 PM
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
(9, 4, 'Kúl hótel', 'Flott hótel', 5, '2015-05-05', 'Jón Jónsson'),
(10, 6, 'Góð þjónusta', 'Góð þjónusta og vel þrifið.', 4, '2016-03-09', 'Kalli'),
(11, 6, 'Slæmt verð', 'of hátt verð á minibar', 2, '2016-02-16', 'John doe'),
(12, 6, 'Frábært', 'Gott hótel á góðum stað', 5, '2016-03-01', 'Happy Johnson'),
(13, 7, 'Slæmt', 'Ekki góð þjónusta og illa þrifið', 2, '2016-04-03', 'Fannar'),
(14, 7, 'Allt í lagi', 'Slæm þjónusta, fínt verð. Ekki á góðum stað.', 3, '2016-04-05', 'Lárus'),
(15, 7, 'Decent', 'Bad place, not as adverticed, but nice price. Charged for water.', 2, '2016-01-05', 'Matthew'),
(16, 7, 'Scammed', 'I payed for internet for 3 days but it cut out after only 8 hours and I would not get a refund.', 1, '2016-03-29', 'Sammy Koamer'),
(17, 8, 'Fínt', 'Góð staðsetning, og flottur matur. Í hærri kantinum en allt í lagi', 4, '2016-04-06', 'Ólafur'),
(18, 8, 'Fínt', 'Vantaði uppá þjónustu, en fékk gott herbergi með flottu útsýni', 4, '2015-12-23', 'Tómas'),
(19, 8, 'Would reccomend', 'Very nice service, a great spot for a hotel. Nice food and great beds.', 5, '2016-04-29', 'Lukas');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`ReviewID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
  MODIFY `ReviewID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

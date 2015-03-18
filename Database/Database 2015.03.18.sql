-- phpMyAdmin SQL Dump
-- version 4.3.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 18, 2015 at 01:11 PM
-- Server version: 5.6.23
-- PHP Version: 5.5.9-1ubuntu4.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `shnyubus`
--

-- --------------------------------------------------------

--
-- Table structure for table `drivers`
--

CREATE TABLE `drivers` (
  `id` varchar(30) NOT NULL,
  `name` varchar(30) NOT NULL,
  `phone` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `drivers_location`
--

CREATE TABLE `drivers_location` (
  `route` varchar(20) NOT NULL,
  `latitude` varchar(20) NOT NULL,
  `longitude` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `drivers_location`
--

INSERT INTO `drivers_location` (`route`, `latitude`, `longitude`) VALUES
('A', '31.231062200017', '121.53219983205'),
('B', '31.225361', '121.532746'),
('C', '31.226846', '121.543244');

-- --------------------------------------------------------

--
-- Table structure for table `logs`
--

CREATE TABLE `logs` (
  `status` varchar(10) NOT NULL,
  `route` varchar(5) NOT NULL,
  `driver` varchar(30) NOT NULL,
  `bus` varchar(20) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `logs`
--

INSERT INTO `logs` (`status`, `route`, `driver`, `bus`, `time`) VALUES
('started', 'A', 'e6984080fbd23820', '01', '2015-03-18 03:17:37'),
('stopped', 'A', 'e6984080fbd23820', '01', '2015-03-18 03:18:20'),
('started', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:43:51'),
('started', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:45:12'),
('stopped', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:45:26'),
('stopped', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:45:29'),
('started', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:47:47'),
('stopped', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:47:49'),
('started', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:49:12'),
('stopped', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:49:14'),
('started', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:49:15'),
('stopped', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:49:16'),
('started', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:49:17'),
('stopped', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:49:18'),
('started', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:49:20'),
('started', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:49:21'),
('started', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:50:06'),
('stopped', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:50:12'),
('started', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:50:16'),
('stopped', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:50:22'),
('started', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:56:14'),
('stopped', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:56:15'),
('started', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:56:18'),
('stopped', 'A', 'e6984080fbd23820', '01', '2015-03-18 10:56:21'),
('started', 'A', 'e6984080fbd23820', '01', '2015-03-18 11:43:07'),
('stopped', 'A', 'e6984080fbd23820', '01', '2015-03-18 11:43:10'),
('started', 'A', 'e6984080fbd23820', '01', '2015-03-18 11:43:12'),
('stopped', 'B', 'e6984080fbd23820', '01', '2015-03-18 11:43:14'),
('started', 'B', 'e6984080fbd23820', '01', '2015-03-18 11:43:15'),
('started', 'B', 'e6984080fbd23820', '01', '2015-03-18 11:43:17'),
('stopped', 'B', 'e6984080fbd23820', '01', '2015-03-18 11:43:18'),
('started', 'C', 'e6984080fbd23820', '01', '2015-03-18 11:43:21'),
('stopped', 'C', 'e6984080fbd23820', '01', '2015-03-18 11:43:23'),
('started', 'A', 'e6984080fbd23820', '01', '2015-03-18 11:47:55'),
('stopped', 'A', 'e6984080fbd23820', '01', '2015-03-18 11:47:57'),
('started', 'A', 'e6984080fbd23820', '01', '2015-03-18 11:47:58'),
('stopped', 'A', 'e6984080fbd23820', '01', '2015-03-18 11:48:16'),
('started', 'A', 'e6984080fbd23820', '01', '2015-03-18 11:50:52'),
('stopped', 'A', 'e6984080fbd23820', '01', '2015-03-18 11:51:01'),
('started', 'A', 'e6984080fbd23820', '01', '2015-03-18 12:05:52'),
('stopped', 'A', 'e6984080fbd23820', '01', '2015-03-18 12:06:00');

-- --------------------------------------------------------

--
-- Table structure for table `schedule`
--

CREATE TABLE `schedule` (
  `route` varchar(5) NOT NULL,
  `going_from` varchar(30) NOT NULL,
  `going_to` varchar(30) NOT NULL,
  `departure` time NOT NULL,
  `arrival` time NOT NULL,
  `monday` tinyint(1) NOT NULL,
  `tuesday` tinyint(1) NOT NULL,
  `wednesday` tinyint(1) NOT NULL,
  `thursday` tinyint(1) NOT NULL,
  `friday` tinyint(1) NOT NULL,
  `saturday` tinyint(1) NOT NULL,
  `sunday` tinyint(1) NOT NULL,
  `holiday` tinyint(1) DEFAULT NULL,
  `holiday_from` date DEFAULT NULL,
  `holiday_until` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `schedule`
--

INSERT INTO `schedule` (`route`, `going_from`, `going_to`, `departure`, `arrival`, `monday`, `tuesday`, `wednesday`, `thursday`, `friday`, `saturday`, `sunday`, `holiday`, `holiday_from`, `holiday_until`) VALUES
('A-01', 'Academic B', 'Motel 268', '21:00:00', '21:05:00', 1, 1, 1, 1, 1, 1, 1, NULL, NULL, NULL),
('A-02', 'Academic B', 'Motel 268', '21:30:00', '21:35:00', 1, 1, 1, 1, 1, 1, 1, NULL, NULL, NULL),
('A-03', 'Academic B', 'Motel 268', '22:00:00', '22:05:00', 1, 1, 1, 1, 1, 1, 1, NULL, NULL, NULL),
('A-04', 'Academic B', 'Motel 268', '22:30:00', '22:35:00', 1, 1, 1, 1, 1, 1, 1, NULL, NULL, NULL),
('A-05', 'Academic B', 'Motel 268', '23:00:00', '23:05:00', 1, 1, 1, 1, 1, 1, 1, NULL, NULL, NULL),
('A-06', 'Academic B', 'Motel 268', '23:30:00', '23:35:00', 1, 1, 1, 1, 1, 1, 1, NULL, NULL, NULL),
('A-07', 'Academic B', 'Motel 268', '00:00:00', '00:05:00', 1, 1, 1, 1, 1, 1, 1, NULL, NULL, NULL),
('A-08', 'Academic B', 'Motel 268', '00:30:00', '00:35:00', 1, 1, 1, 1, 1, 1, 1, NULL, NULL, NULL),
('A-09', 'Academic B', 'Motel 268', '01:00:00', '01:05:00', 1, 1, 1, 1, 1, 1, 1, NULL, NULL, NULL),
('A-10', 'Academic B', 'Motel 268', '01:30:00', '01:35:00', 1, 1, 1, 1, 1, 1, 1, NULL, NULL, NULL),
('A-11', 'Academic B', 'Motel 268', '02:00:00', '02:05:00', 1, 1, 1, 1, 1, 1, 1, NULL, NULL, NULL),
('B-01', 'Gr Pujian', 'Academic B', '07:45:00', '08:00:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('B-02', 'Gr Pujian', 'Academic B', '09:00:00', '09:15:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('B-03', 'Gr Pujian', 'Academic B', '10:45:00', '11:00:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('B-04', 'Gr Pujian', 'Academic B', '12:30:00', '12:45:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('B-05', 'Gr Pujian', 'Academic B', '14:00:00', '14:15:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('B-06', 'Gr Pujian', 'Academic B', '15:00:00', '15:15:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('B-07', 'Gr Pujian', 'Academic B', '16:00:00', '16:15:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('B-08', 'Gr Pujian', 'Academic B', '17:00:00', '17:15:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('B-09', 'Gr Pujian', 'Academic B', '18:00:00', '18:15:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('B-10', 'Gr Pujian', 'Academic B', '19:30:00', '19:45:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('B-11', 'Gr Pujian', 'Academic B', '09:00:00', '09:15:00', 0, 0, 0, 0, 1, 1, 1, 0, NULL, NULL),
('B-12', 'Gr Pujian', 'Academic B', '11:00:00', '11:15:00', 0, 0, 0, 0, 1, 1, 1, 0, NULL, NULL),
('B-13', 'Gr Pujian', 'Academic B', '16:00:00', '16:15:00', 0, 0, 0, 0, 1, 1, 1, 0, NULL, NULL),
('C-01', 'Academic B', 'Gr Pujian', '10:00:00', '10:15:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('C-02', 'Academic B', 'Gr Pujian', '11:15:00', '12:30:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('C-03', 'Academic B', 'Gr Pujian', '13:00:00', '13:15:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('C-04', 'Academic B', 'Gr Pujian', '14:30:00', '14:45:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('C-05', 'Academic B', 'Gr Pujian', '15:30:00', '15:45:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('C-06', 'Academic B', 'Gr Pujian', '16:30:00', '16:45:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('C-07', 'Academic B', 'Gr Pujian', '17:30:00', '17:45:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('C-08', 'Academic B', 'Gr Pujian', '18:45:00', '19:00:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('C-09', 'Academic B', 'Gr Pujian', '20:15:00', '20:30:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('C-10', 'Academic B', 'Gr Pujian', '21:00:00', '21:15:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('C-11', 'Academic B', 'Gr Pujian', '22:00:00', '22:15:00', 1, 1, 1, 1, 0, 0, 0, 0, NULL, NULL),
('C-12', 'Academic B', 'Gr Pujian', '11:30:00', '11:45:00', 0, 0, 0, 0, 1, 1, 1, 0, NULL, NULL),
('C-13', 'Academic B', 'Gr Pujian', '16:30:00', '16:45:00', 0, 0, 0, 0, 1, 1, 1, 0, NULL, NULL),
('C-14', 'Academic B', 'Gr Pujian', '19:00:00', '19:15:00', 0, 0, 0, 0, 1, 1, 1, 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `version`
--

CREATE TABLE `version` (
  `current_ver` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `version`
--

INSERT INTO `version` (`current_ver`) VALUES
('usr1.13-drv1.13');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

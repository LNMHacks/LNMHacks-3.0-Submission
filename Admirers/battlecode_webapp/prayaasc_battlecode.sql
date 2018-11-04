-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 04, 2018 at 12:40 PM
-- Server version: 10.0.37-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `prayaasc_battlecode`
--

-- --------------------------------------------------------

--
-- Table structure for table `game`
--

CREATE TABLE `game` (
  `gameid` varchar(10) NOT NULL,
  `invite` int(5) NOT NULL,
  `probid` int(4) NOT NULL,
  `user1` varchar(20) NOT NULL,
  `user2` varchar(20) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `winner` varchar(20) DEFAULT NULL,
  `start` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `game`
--

INSERT INTO `game` (`gameid`, `invite`, `probid`, `user1`, `user2`, `status`, `winner`, `start`) VALUES
('jq6jd', 21451, 1, 'hardik', 'himanshu', 'finish', 'himanshu', '2018-11-04 06:30:50'),
('o77z1', 64840, 1, 'hardik', 'himanshu', 'running', NULL, '2018-11-04 06:39:34'),
('o67io', 81547, 1, 'hardik', 'hardik', 'running', NULL, '2018-11-04 06:55:17'),
('t3i6e', 53710, 1, 'hardik', 'anubhav', 'running', NULL, '2018-11-04 06:57:38');

-- --------------------------------------------------------

--
-- Table structure for table `prefs`
--

CREATE TABLE `prefs` (
  `name` varchar(30) NOT NULL,
  `accept` int(11) NOT NULL,
  `c` int(11) NOT NULL,
  `cpp` int(11) NOT NULL,
  `java` int(11) NOT NULL,
  `python` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `prefs`
--

INSERT INTO `prefs` (`name`, `accept`, `c`, `cpp`, `java`, `python`) VALUES
('', 1, 1, 0, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `problems`
--

CREATE TABLE `problems` (
  `sl` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `text` text NOT NULL,
  `input` text NOT NULL,
  `output` text NOT NULL,
  `time` int(11) NOT NULL DEFAULT '3000'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `problems`
--

INSERT INTO `problems` (`sl`, `name`, `text`, `input`, `output`, `time`) VALUES
(1, 'Print the happiness!', 'HAPPY HAPPY, EVERYONE TODAY IS HAPAY\r\n<br><br>\r\nInput ::\r\nThe first line contains a N integer.\r\n<br><br>\r\nOutput :: Print N times the string \"HAPPY\" without quotes.\r\n<br><br>\r\nConstraints<br>\r\n1â‰¤Nâ‰¤20<br>\r\n<br><br>\r\nExample Input<br>\r\n5\r\n<br><br>\r\nExample Output<br>\r\nHAPPY\r\nHAPPY\r\nHAPPY\r\nHAPPY\r\nHAPPY', '3', 'HAPPY\r\nHAPPY\r\nHAPPY', 100);

-- --------------------------------------------------------

--
-- Table structure for table `solve`
--

CREATE TABLE `solve` (
  `sl` int(11) NOT NULL,
  `problem_id` int(11) NOT NULL,
  `username` varchar(25) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1',
  `attempts` int(11) NOT NULL DEFAULT '1',
  `soln` text NOT NULL,
  `filename` varchar(25) NOT NULL,
  `lang` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `solve`
--

INSERT INTO `solve` (`sl`, `problem_id`, `username`, `status`, `attempts`, `soln`, `filename`, `lang`) VALUES
(1, 1, 'himanshu', 2, 1, 'print(\"HELLO WORLD\");', '', 'python');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `sl` int(11) NOT NULL,
  `username` varchar(25) NOT NULL,
  `salt` varchar(6) NOT NULL,
  `hash` varchar(80) NOT NULL,
  `email` varchar(100) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`sl`, `username`, `salt`, `hash`, `email`, `status`) VALUES
(1, 'admin', 'xjnpa', 'xjsOj4w5zHVpQ', 'himaga1997@gmail.com', 1),
(2, 'hardik', '6xxcn', '6xvWjar/TMb4E', '', 1),
(3, 'himanshu', 'hq4tb', 'hqS0EijKsCEqI', '', 1),
(4, 'anubhav', 'xez7h', 'xeuvhv/kUKhNA', '', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `game`
--
ALTER TABLE `game`
  ADD PRIMARY KEY (`gameid`);

--
-- Indexes for table `problems`
--
ALTER TABLE `problems`
  ADD PRIMARY KEY (`sl`);

--
-- Indexes for table `solve`
--
ALTER TABLE `solve`
  ADD PRIMARY KEY (`sl`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`sl`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `problems`
--
ALTER TABLE `problems`
  MODIFY `sl` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `solve`
--
ALTER TABLE `solve`
  MODIFY `sl` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `sl` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

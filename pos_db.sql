-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 20, 2023 at 09:56 AM
-- Server version: 10.5.20-MariaDB
-- PHP Version: 7.3.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id21357081_pos_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `barcode` varchar(15) NOT NULL,
  `description` varchar(50) NOT NULL,
  `qty` int(11) NOT NULL DEFAULT 1,
  `amount` decimal(10,2) NOT NULL DEFAULT 0.00,
  `image` varchar(500) NOT NULL,
  `user_id` varchar(60) NOT NULL,
  `discount` varchar(155) NOT NULL DEFAULT '0',
  `discounted_price` int(255) NOT NULL DEFAULT 0,
  `date` datetime NOT NULL,
  `views` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `barcode`, `description`, `qty`, `amount`, `image`, `user_id`, `discount`, `discounted_price`, `date`, `views`) VALUES
(1, '2222900455659', 'Argentina', 66, 45.00, 'uploads/b1c1e37ec7aab4db16f96b391f6d8a245107a587_4567.jpg', '1', '', 0, '2023-12-08 16:03:24', 27),
(2, '2222993218061', 'Mang Juan', 63, 10.00, 'uploads/ccda404edb9a0ac2675bad3e3d5484dbdb171743_9267.jpg', '1', '', 0, '2023-12-08 16:03:41', 28),
(3, '2222320072634', 'Birch tree', 67, 15.00, 'uploads/596e87fe67696646171745e599172d635017c491_1537.jpg', '1', '', 0, '2023-12-08 16:04:09', 28),
(4, '2222572432885', 'Coca cola', 92, 45.00, 'uploads/46dd4b26e95bfc8d5151ecdeef86739e9cdf2104_8752.jpg', '1', '', 0, '2023-12-08 16:04:23', 7),
(6, '2222809825546', 'c2', 88, 32.00, 'uploads/bf7e25bf1ffa4384edf29d54f26e381ffee3f268_2082.jpg', '1', '', 0, '2023-12-08 16:04:52', 9),
(7, '2222172405146', 'Kopiko Black', 89, 8.00, 'uploads/7ebd35531cfbdda71e41d6a1225f4cdde890ddb8_2993.jfif', '1', '3', 0, '2023-12-08 16:05:08', 8),
(8, '4800092113437', 'Super Thins', 99, 10.00, 'uploads/9cbcd09745cf5a40f75be54e8b81f35140cd90c4_2988.png', '1', '', 0, '2023-12-09 07:53:32', 1),
(9, '4801688104129', 'cheese ball', 47, 25.00, 'uploads/a9ff4304967d08abfc1b4e1b8627c0efa25db68d_5572.png', 'Unknown', '8', 0, '2023-12-11 00:43:54', 3),
(13, '4800611546760', 'Cobra', 0, 20.00, 'uploads/13c8fa462595cdd7a69ac854bea5857a2cde0574_6272.jpg', '14', '0', 0, '2023-12-11 05:48:25', 6),
(14, '4800194185080', 'Smart C', 30, 50.00, 'uploads/23339a4dd0b4fc747220cdb1e16fd853f6de4953_7582.png', '16', '', 50, '2023-12-11 05:55:40', 0),
(15, '4800016138515', 'Tortillos', 50, 25.00, 'uploads/5102a8fd512aa555e6fe6e31a0159563cfa4a818_3150.jpg', '14', '', 25, '2023-12-11 07:18:42', 0),
(16, '4801688103511', 'Cheese ring', 40, 20.00, 'uploads/a83d4545e59dded2c4ebb15e3912b6a8a6e75873_7898.png', '14', '8', 12, '2023-12-11 07:19:50', 0),
(17, '4801688103511', 'Cheese ring', 40, 20.00, 'uploads/bed09b7850d032db66050f3a133ecb9ac1a0cf5d_3329.png', '14', '8', 12, '2023-12-11 07:19:57', 0);

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `id` int(11) NOT NULL,
  `barcode` varchar(15) NOT NULL,
  `receipt_no` int(11) NOT NULL,
  `description` varchar(50) NOT NULL,
  `qty` int(11) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `date` datetime NOT NULL,
  `user_id` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`id`, `barcode`, `receipt_no`, `description`, `qty`, `amount`, `total`, `date`, `user_id`) VALUES
(1, '4800016138515', 15503857, 'Tortillos', 2, 30.00, 60.00, '2023-12-11 02:32:04', '15'),
(2, '4801688103511', 15503857, 'Cheese ring', 1, 25.00, 25.00, '2023-12-11 02:32:04', '15'),
(3, '4801688104129', 15503857, 'cheese ball', 1, 25.00, 25.00, '2023-12-11 02:32:04', '15'),
(4, '2222900455659', 92498208, 'Argentina', 2, 45.00, 90.00, '2023-12-11 02:32:25', '15'),
(5, '2222320072634', 46284891, 'Birch tree', 2, 15.00, 30.00, '2023-12-11 02:33:10', '15'),
(6, '2222993218061', 46284891, 'Mang Juan', 1, 10.00, 10.00, '2023-12-11 02:33:10', '15'),
(7, '4801688104129', 24155506, 'cheese ball', 1, 25.00, 25.00, '2023-12-11 02:37:49', '15'),
(8, '4800016138515', 24155506, 'Tortillos', 1, 30.00, 30.00, '2023-12-11 02:37:49', '15'),
(9, '4801688103511', 24155506, 'Cheese ring', 1, 25.00, 25.00, '2023-12-11 02:37:49', '15'),
(10, '2222809825546', 29939666, 'c2', 2, 32.00, 64.00, '2023-12-11 04:16:24', '15'),
(11, '2222900455659', 21975029, 'Argentina', 1, 45.00, 45.00, '2023-12-11 04:31:06', '15'),
(12, '2222320072634', 21975029, 'Birch tree', 1, 15.00, 15.00, '2023-12-11 04:31:06', '15'),
(13, '4801688103511', 21975029, 'Cheese ring', 1, 25.00, 25.00, '2023-12-11 04:31:06', '15'),
(14, '4800016138515', 21975029, 'Tortillos', 1, 30.00, 30.00, '2023-12-11 04:31:06', '15'),
(15, '2222993218061', 22031876, 'Mang Juan', 1, 10.00, 10.00, '2023-12-11 04:48:37', '15'),
(16, '2222900455659', 22031876, 'Argentina', 1, 45.00, 45.00, '2023-12-11 04:48:37', '15'),
(17, '2222320072634', 91601591, 'Birch tree', 1, 15.00, 15.00, '2023-12-11 05:44:32', '16'),
(18, '2222993218061', 91601591, 'Mang Juan', 1, 10.00, 10.00, '2023-12-11 05:44:32', '16'),
(19, '2222809825546', 91601591, 'c2', 1, 32.00, 32.00, '2023-12-11 05:44:32', '16'),
(20, '2222172405146', 91601591, 'Kopiko Black', 1, 8.00, 8.00, '2023-12-11 05:44:32', '16'),
(21, '2222572432885', 45669100, 'Coca cola', 1, 45.00, 45.00, '2023-12-11 05:46:12', '16'),
(22, '2222172405146', 45669100, 'Kopiko Black', 1, 8.00, 8.00, '2023-12-11 05:46:12', '16'),
(23, '2222320072634', 45669100, 'Birch tree', 1, 15.00, 15.00, '2023-12-11 05:46:12', '16'),
(24, '4801688104129', 14662069, 'cheese ball', 1, 25.00, 25.00, '2023-12-11 05:57:49', '15'),
(25, '4800016138515', 14662069, 'Tortillos', 1, 30.00, 30.00, '2023-12-11 05:57:49', '15'),
(26, '4801688103511', 14662069, 'Cheese ring', 1, 25.00, 25.00, '2023-12-11 05:57:49', '15'),
(27, '4800611546760', 49688407, 'Cobra', 1, 20.00, 20.00, '2023-12-11 06:02:21', '15'),
(28, '4800611546760', 92451891, 'Cobra', 1, 20.00, 20.00, '2023-12-11 06:06:13', '15'),
(29, '4800611546760', 83121250, 'Cobra', 1, 20.00, 20.00, '2023-12-11 06:08:06', '15'),
(30, '4800611546760', 65225348, 'Cobra', 3, 20.00, 60.00, '2023-12-11 06:10:25', '15'),
(31, '2222993218061', 94847798, 'Mang Juan', 1, 10.00, 10.00, '2023-12-11 06:13:29', '15'),
(32, '2222900455659', 94847798, 'Argentina', 1, 45.00, 45.00, '2023-12-11 06:13:29', '15'),
(33, '2222572432885', 94847798, 'Coca cola', 1, 45.00, 45.00, '2023-12-11 06:13:29', '15'),
(34, '4800016138515', 90222685, 'Tortillos', 1, 30.00, 30.00, '2023-12-11 07:14:07', '17'),
(35, '4801688103511', 90222685, 'Cheese ring', 1, 25.00, 25.00, '2023-12-11 07:14:07', '17'),
(36, '4800611546760', 90222685, 'Cobra', 1, 20.00, 20.00, '2023-12-11 07:14:07', '17'),
(37, '2222572432885', 32913343, 'Coca cola', 1, 45.00, 45.00, '2023-12-11 07:16:08', '17'),
(38, '2222900455659', 32913343, 'Argentina', 1, 45.00, 45.00, '2023-12-11 07:16:08', '17'),
(39, '2222320072634', 32913343, 'Birch tree', 1, 15.00, 15.00, '2023-12-11 07:16:08', '17'),
(40, '2222993218061', 32913343, 'Mang Juan', 1, 10.00, 10.00, '2023-12-11 07:16:08', '17'),
(41, '2222809825546', 32913343, 'c2', 1, 32.00, 32.00, '2023-12-11 07:16:08', '17'),
(42, '2222172405146', 32913343, 'Kopiko Black', 1, 8.00, 8.00, '2023-12-11 07:16:08', '17'),
(43, '4800611546760', 32913343, 'Cobra', 1, 20.00, 20.00, '2023-12-11 07:16:08', '17'),
(44, '2222900455659', 94585536, 'Argentina', 2, 45.00, 90.00, '2023-12-15 02:18:36', '14'),
(45, '2222320072634', 94585536, 'Birch tree', 1, 15.00, 15.00, '2023-12-15 02:18:36', '14'),
(46, '2222993218061', 94585536, 'Mang Juan', 2, 10.00, 20.00, '2023-12-15 02:18:36', '14'),
(47, '2222320072634', 44014202, 'Birch tree', 1, 15.00, 15.00, '2023-12-19 03:02:23', '17'),
(48, '2222809825546', 44014202, 'c2', 1, 32.00, 32.00, '2023-12-19 03:02:23', '17'),
(49, '2222900455659', 45187445, 'Argentina', 1, 45.00, 45.00, '2023-12-19 03:18:05', '17'),
(50, '2222993218061', 45187445, 'Mang Juan', 1, 10.00, 10.00, '2023-12-19 03:18:05', '17'),
(51, '2222320072634', 12063740, 'Birch tree', 1, 15.00, 15.00, '2023-12-19 03:19:05', '17'),
(52, '2222900455659', 16279050, 'Argentina', 2, 45.00, 90.00, '2023-12-19 03:20:29', '17'),
(53, '2222993218061', 13226453, 'Mang Juan', 1, 10.00, 10.00, '2023-12-19 03:26:20', '17'),
(54, '2222993218061', 86610019, 'Mang Juan', 1, 10.00, 10.00, '2023-12-19 03:30:45', '17'),
(55, '2222320072634', 98537829, 'Birch tree', 1, 15.00, 15.00, '2023-12-19 03:31:11', '17'),
(56, '2222900455659', 61518978, 'Argentina', 1, 45.00, 45.00, '2023-12-19 03:36:05', '17');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `image` varchar(500) DEFAULT NULL,
  `role` varchar(20) NOT NULL,
  `gender` varchar(6) NOT NULL DEFAULT 'male',
  `rfid` varchar(50) NOT NULL,
  `unique_num` varchar(50) NOT NULL,
  `qr_image` varchar(100) DEFAULT NULL,
  `balance` decimal(10,0) NOT NULL DEFAULT 0,
  `pin_number_rfid` int(6) NOT NULL,
  `pin_number_qr` int(6) NOT NULL,
  `deletable` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `email`, `password`, `date`, `image`, `role`, `gender`, `rfid`, `unique_num`, `qr_image`, `balance`, `pin_number_rfid`, `pin_number_qr`, `deletable`) VALUES
(14, 'Christian Paul Galo', 'christianpaul@sample.com', '123123123', '2023-12-11', 'uploads/9308552eafb1c76a29eebd0b6ca54b2e21abaabd_1144.jpg', 'admin', 'male', '0000876923', 'd7b55063c53f8a1d3bb89c9ac77f16df', 'assets/qr/d7b55063c53f8a1d3bb89c9ac77f16df.png', 875, 123123, 123123, 1),
(16, 'John Ralph De lemos', 'johnralphdelemos@gmail.com', '123123123', '2023-12-11', NULL, 'supervisor', 'male', '0000761392', 'cb15e3a925bb7243512cea8f99212830', 'assets/qr/cb15e3a925bb7243512cea8f99212830.png', 883, 777777, 777777, 1),
(17, 'Danreve Kumar', 'dan@sample.com', '123123123', '2023-12-11', NULL, 'user', 'male', '0000749917', '326056846463f28a6edeba7b5e5ad307', 'assets/qr/326056846463f28a6edeba7b5e5ad307.png', 471, 123456, 123456, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `barcode` (`barcode`),
  ADD KEY `description` (`description`),
  ADD KEY `qty` (`qty`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `date` (`date`),
  ADD KEY `views` (`views`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`id`),
  ADD KEY `barcode` (`barcode`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `date` (`date`),
  ADD KEY `description` (`description`),
  ADD KEY `receipt_no` (`receipt_no`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uniquenum` (`unique_num`),
  ADD UNIQUE KEY `unique_num` (`unique_num`),
  ADD KEY `email` (`email`),
  ADD KEY `date` (`date`),
  ADD KEY `role` (`role`),
  ADD KEY `id_number` (`rfid`),
  ADD KEY `qrcode` (`unique_num`),
  ADD KEY `balance` (`balance`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

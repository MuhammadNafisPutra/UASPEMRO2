/*
SQLyog Community v13.3.0 (64 bit)
MySQL - 10.4.32-MariaDB : Database - barterskill
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`barterskill` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `barterskill`;

/*Table structure for table `barter_proposals` */

DROP TABLE IF EXISTS `barter_proposals`;

CREATE TABLE `barter_proposals` (
  `proposal_id` int(11) NOT NULL AUTO_INCREMENT,
  `requester_id` int(11) NOT NULL,
  `owner_id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  `status` varchar(20) DEFAULT 'PENDING',
  `message` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`proposal_id`),
  KEY `requester_id` (`requester_id`),
  KEY `owner_id` (`owner_id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `barter_proposals_ibfk_1` FOREIGN KEY (`requester_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `barter_proposals_ibfk_2` FOREIGN KEY (`owner_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `barter_proposals_ibfk_3` FOREIGN KEY (`post_id`) REFERENCES `skill_posts` (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `barter_proposals` */

/*Table structure for table `skill_posts` */

DROP TABLE IF EXISTS `skill_posts`;

CREATE TABLE `skill_posts` (
  `post_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `post_type` enum('OFFER','REQUEST') NOT NULL,
  `status` varchar(20) DEFAULT 'OPEN',
  `experience_years` int(11) DEFAULT 0,
  `portfolio_link` varchar(255) DEFAULT NULL,
  `deadline` date DEFAULT NULL,
  `urgency_level` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`post_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `skill_posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `skill_posts` */

insert  into `skill_posts`(`post_id`,`user_id`,`title`,`description`,`post_type`,`status`,`experience_years`,`portfolio_link`,`deadline`,`urgency_level`) values 
(19,2,'makan','makan','OFFER','OPEN',12,'21',NULL,NULL),
(20,1,'halo','memasak pian','OFFER','OPEN',2,'wa',NULL,NULL),
(21,1,'judul','12','OFFER','OPEN',2,'3',NULL,NULL),
(22,1,'asd','21','OFFER','OPEN',2,'12',NULL,NULL),
(23,1,'ML','Menggunakan lancelot','OFFER','OPEN',2,'22',NULL,NULL),
(24,1,'dwd','wda','OFFER','OPEN',2,'2',NULL,NULL),
(25,1,'32','3q','OFFER','OPEN',2,'21e',NULL,NULL),
(26,1,'main nols','wa','OFFER','OPEN',12,'ee',NULL,NULL),
(27,1,'sdw','wdqdw','OFFER','OPEN',2,'21',NULL,NULL),
(28,1,'333','323','OFFER','OPEN',2,'wadwd',NULL,NULL),
(29,1,'makan','makan ayam nih','REQUEST','OPEN',0,NULL,'2025-12-17','High'),
(30,1,'Game','mahjong proplayer','OFFER','OPEN',12,'as',NULL,NULL),
(31,2,'hai','weqe2','REQUEST','OPEN',0,NULL,'2025-12-12','High');

/*Table structure for table `transaksi` */

DROP TABLE IF EXISTS `transaksi`;

CREATE TABLE `transaksi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `postingan_id` int(11) NOT NULL,
  `peminat_id` int(11) NOT NULL,
  `status` varchar(20) DEFAULT 'PENDING',
  `tanggal` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `postingan_id` (`postingan_id`),
  KEY `peminat_id` (`peminat_id`),
  CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`postingan_id`) REFERENCES `skill_posts` (`post_id`) ON DELETE CASCADE,
  CONSTRAINT `transaksi_ibfk_2` FOREIGN KEY (`peminat_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `transaksi` */

insert  into `transaksi`(`id`,`postingan_id`,`peminat_id`,`status`,`tanggal`) values 
(4,30,2,'ACCEPTED','2025-12-23'),
(5,20,2,'ACCEPTED','2025-12-23');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `role` varchar(20) DEFAULT 'USER',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `users` */

insert  into `users`(`user_id`,`username`,`password`,`full_name`,`role`) values 
(1,'halo','yaya','nafis','STUDENT'),
(2,'Ghazi','1234','Muhammad Ghazi','STUDENT');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

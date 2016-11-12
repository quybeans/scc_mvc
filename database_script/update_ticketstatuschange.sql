CREATE DATABASE  IF NOT EXISTS `scc` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `scc`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: scc
-- ------------------------------------------------------
-- Server version	5.7.15-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ticketstatuschange`
--

DROP TABLE IF EXISTS `ticketstatuschange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticketstatuschange` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ticketid` int(11) DEFAULT NULL,
  `changeby` int(11) DEFAULT NULL,
  `statusid` int(11) DEFAULT NULL,
  `createdat` datetime DEFAULT NULL,
  `assignee` int(11) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `priorityid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticketstatuschange`
--

LOCK TABLES `ticketstatuschange` WRITE;
/*!40000 ALTER TABLE `ticketstatuschange` DISABLE KEYS */;
INSERT INTO `ticketstatuschange` VALUES (63,23,3,2,'2016-11-01 00:59:56',2,'ticket mới nè',0),(64,23,3,5,'2016-11-01 14:35:02',0,'đây là 1 cái note dùng để test độ dài nên phải viết cái gì đó dài thật dài, dài ơi là dài, thế này đã đủ dài chưa ? chắc chưa quá, nên viết cho nó dài thêm nữa . À nhiêu đây chắc đủ rồi',0),(65,23,3,2,'2016-11-01 14:45:02',5,'gh',0),(66,23,3,4,'2016-11-01 14:55:02',0,'cbgh',0),(67,0,3,2,'2016-11-06 16:01:33',4,'cxf',0),(68,0,3,2,'2016-11-06 17:07:39',1,'ndh',0),(69,27,3,2,'2016-11-06 17:08:08',6,'h',0),(70,27,3,4,'2016-11-06 17:08:14',0,'fgh',0),(71,23,3,2,'2016-11-06 17:08:53',6,'ght',0),(72,23,3,5,'2016-11-06 17:09:04',0,'eth',0),(74,23,3,2,'2016-11-06 19:20:35',6,' ticket kho qua nen ',0),(75,23,3,2,'2016-11-06 19:24:09',3,' test note',0),(76,23,3,4,'2016-11-06 19:27:49',0,' can duoc xu ly gap',0),(77,0,3,2,'2016-11-06 20:40:48',1,' ',0),(78,28,3,5,'2016-11-06 20:41:02',0,' ',0),(79,23,3,3,'2016-11-07 12:20:41',0,' test mau',0),(80,29,2,2,'2016-11-08 13:35:35',2,' ',0),(81,29,2,3,'2016-11-08 13:58:44',0,'Start follow ticket',0),(82,29,2,4,'2016-11-08 14:00:50',0,' đã xử lý xong, cần review',0),(83,29,2,4,'2016-11-08 14:02:48',0,' review lần 2',0),(84,23,3,0,'2016-11-08 17:45:12',0,' test mau',2);
/*!40000 ALTER TABLE `ticketstatuschange` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-08 17:50:27

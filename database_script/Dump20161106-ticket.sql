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
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `createdby` int(11) DEFAULT NULL,
  `createdtime` datetime DEFAULT NULL,
  `statusid` int(11) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `assignee` int(11) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (23,'Thái độ nhân viên tệ',3,'2016-11-01 14:20:33',5,'',6,'cần chấn chỉnh gấp, ngay và luôn trong ngày',1),(24,'sản phẩm tệ',3,'2016-11-03 10:15:26',5,'',5,'cần xem lại nguồn hàng',3),(25,'new ticket',3,'2016-11-02 10:19:07',2,'',6,'nè',1),(26,'test merge',3,'2016-11-06 16:01:33',2,'',4,'Merge code mệt quá trời quá đất',3),(27,'test merge source tree',3,'2016-11-06 17:07:39',4,'',6,'merge nhiều hao calo quá, mờ cả mắt',2);
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticketitem`
--

DROP TABLE IF EXISTS `ticketitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticketitem` (
  `ticketid` int(11) NOT NULL,
  `commentid` varchar(255) NOT NULL,
  `postid` varchar(255) NOT NULL,
  `messageid` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`ticketid`,`commentid`,`postid`,`messageid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticketitem`
--

LOCK TABLES `ticketitem` WRITE;
/*!40000 ALTER TABLE `ticketitem` DISABLE KEYS */;
INSERT INTO `ticketitem` VALUES (23,'535072116700660','0','0',NULL),(23,'535072166700655','0','0',NULL),(23,'536390736568798','0','0',NULL),(23,'536392043235334','0','0',NULL);
/*!40000 ALTER TABLE `ticketitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticketrequest`
--

DROP TABLE IF EXISTS `ticketrequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticketrequest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ticketid` int(11) DEFAULT NULL,
  `assigner` int(11) DEFAULT NULL,
  `assignee` int(11) DEFAULT NULL,
  `requestat` datetime DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `note` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticketrequest`
--

LOCK TABLES `ticketrequest` WRITE;
/*!40000 ALTER TABLE `ticketrequest` DISABLE KEYS */;
INSERT INTO `ticketrequest` VALUES (1,3,4,2,'2016-10-27 19:32:56',NULL,NULL),(2,9,4,5,'2016-10-27 22:21:23',NULL,NULL),(3,9,4,5,'2016-10-27 22:21:35',NULL,NULL),(4,3,4,3,'2016-10-28 00:40:04',NULL,NULL),(5,3,4,3,'2016-10-28 00:40:23',NULL,NULL),(6,3,4,2,'2016-10-28 00:40:48',NULL,NULL),(7,1,4,4,'2016-10-28 00:46:14',NULL,NULL),(8,10,4,2,'2016-10-28 00:46:52',NULL,NULL),(9,4,4,5,'2016-10-28 00:53:51',NULL,NULL),(10,1,2,6,'2016-10-30 19:12:38',NULL,'ticket kho qua');
/*!40000 ALTER TABLE `ticketrequest` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticketstatuschange`
--

LOCK TABLES `ticketstatuschange` WRITE;
/*!40000 ALTER TABLE `ticketstatuschange` DISABLE KEYS */;
INSERT INTO `ticketstatuschange` VALUES (63,23,3,2,'2016-11-06 15:59:56',2,NULL),(64,23,3,5,'2016-11-06 16:00:02',NULL,NULL),(65,23,3,2,'2016-11-06 16:00:32',5,NULL),(66,23,3,4,'2016-11-06 16:00:38',NULL,NULL),(67,0,3,2,'2016-11-06 16:01:33',4,NULL),(68,0,3,2,'2016-11-06 17:07:39',1,NULL),(69,27,3,2,'2016-11-06 17:08:08',6,NULL),(70,27,3,4,'2016-11-06 17:08:14',NULL,NULL),(71,23,3,2,'2016-11-06 17:08:53',6,NULL),(72,23,3,5,'2016-11-06 17:09:04',NULL,NULL);
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

-- Dump completed on 2016-11-06 18:11:46

CREATE DATABASE  IF NOT EXISTS `scc` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;
USE `scc`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: scc
-- ------------------------------------------------------
-- Server version	5.7.16-log

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
-- Table structure for table `attribute`
--

DROP TABLE IF EXISTS `attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `brandid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brand` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(245) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `sologan` varchar(200) DEFAULT NULL,
  `image` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `brandpage`
--

DROP TABLE IF EXISTS `brandpage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brandpage` (
  `brandpageid` int(11) NOT NULL AUTO_INCREMENT,
  `brandid` int(11) NOT NULL,
  `pageid` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `active` bit(1) NOT NULL,
  PRIMARY KEY (`brandpageid`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `content` longtext COLLATE utf8mb4_bin,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `created_by_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `post_id` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `sentiment_score` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comment_bak`
--

DROP TABLE IF EXISTS `comment_bak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment_bak` (
  `id` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `content` longtext COLLATE utf8mb4_bin,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `created_by_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `post_id` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `sentiment_score` int(11) DEFAULT NULL,
  KEY `id_index` (`id`) USING BTREE,
  KEY `post_id_index` (`post_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `commentattribute`
--

DROP TABLE IF EXISTS `commentattribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `commentattribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commentid` varchar(255) DEFAULT NULL,
  `attributeid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `facebookid` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `picture` varchar(300) COLLATE utf8mb4_bin DEFAULT NULL,
  `locale` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `timezone` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `gender` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`facebookid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `facebookaccount`
--

DROP TABLE IF EXISTS `facebookaccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `facebookaccount` (
  `facebookaccountid` int(11) NOT NULL AUTO_INCREMENT,
  `facebookuserid` varchar(45) DEFAULT NULL,
  `accesstoken` varchar(300) NOT NULL,
  `active` bit(1) NOT NULL,
  `facebookusername` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`facebookaccountid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `content` mediumtext COLLATE utf8mb4_bin,
  `created_at` datetime DEFAULT NULL,
  `receiver_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `receiverid` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `sender_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `senderid` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `seq` int(11) NOT NULL,
  `message_read` bit(1) DEFAULT b'0',
  `sentiment_scrore` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `messageitem`
--

DROP TABLE IF EXISTS `messageitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messageitem` (
  `item_id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id_start` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `message_id_end` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `read_status` bit(1) DEFAULT NULL,
  `createdat` datetime DEFAULT NULL,
  `message` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `userid` int(11) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `senderid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='This table for notification storage';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `page`
--

DROP TABLE IF EXISTS `page`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `page` (
  `pageid` varchar(20) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `accesstoken` varchar(300) NOT NULL,
  `type` varchar(45) DEFAULT NULL,
  `category` varchar(100) DEFAULT NULL,
  `crawler` bit(1) NOT NULL,
  `active` bit(1) NOT NULL,
  PRIMARY KEY (`pageid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post` (
  `id` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `comments_count` int(11) NOT NULL,
  `content` longtext COLLATE utf8mb4_bin,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `likes_count` int(11) NOT NULL,
  `shares_count` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `priority`
--

DROP TABLE IF EXISTS `priority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `priority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `brandid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile` (
  `userid` int(11) NOT NULL,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `gender` int(11) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `DoB` date DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `duetime` datetime DEFAULT NULL,
  `brand_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `messageid` int(11) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `create_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`ticketid`,`commentid`,`postid`,`messageid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `roleid` int(11) NOT NULL,
  `createdby` int(11) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `brandid` int(11) DEFAULT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `createby_idx` (`createdby`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `userfacebookaccount`
--

DROP TABLE IF EXISTS `userfacebookaccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userfacebookaccount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `facebookaccountid` int(11) NOT NULL,
  `active` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-16 12:58:05

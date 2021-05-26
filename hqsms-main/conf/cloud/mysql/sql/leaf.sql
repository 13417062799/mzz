CREATE DATABASE  IF NOT EXISTS `leaf` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `leaf`;
-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: leaf
-- ------------------------------------------------------
-- Server version	8.0.23-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `leaf_alloc`
--

DROP TABLE IF EXISTS `leaf_alloc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `leaf_alloc` (
  `biz_tag` varchar(128) NOT NULL DEFAULT '',
  `max_id` bigint NOT NULL DEFAULT '1',
  `step` int NOT NULL,
  `description` varchar(256) DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`biz_tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leaf_alloc`
--

LOCK TABLES `leaf_alloc` WRITE;
/*!40000 ALTER TABLE `leaf_alloc` DISABLE KEYS */;
INSERT INTO `leaf_alloc` VALUES ('alarm',2,2000,'Alarm Device Id','2021-04-28 06:39:26'),('alarm-log',1,2000,'Alarm Log Id','2021-03-11 03:03:30'),('broadcast',2,2000,'Broadcast Device Id','2021-04-28 06:39:26'),('broadcast-content',1,2000,'Broadcast Content Id','2021-03-11 03:03:30'),('camera',2,2000,'Camera Device Id','2021-04-28 06:39:26'),('camera-human',1,2000,'Camera Human Log Id','2021-04-28 06:39:26'),('camera-log',1,2000,'Camera Raw Log Id','2021-04-28 06:39:26'),('camera-vehicle',1,2000,'Camera Vehicle Log Id','2021-03-11 03:06:42'),('chager-order',1,2000,'Charger Order Log Id ','2021-03-11 03:03:30'),('charger',2,2000,'Charger Device Id','2021-04-28 06:39:26'),('charger-log',1,2000,'Charger Raw Log Id','2021-03-11 03:03:30'),('light',2,2000,'Light Device Id','2021-04-28 06:39:26'),('light-log',1,2000,'Light Log Id','2021-04-28 06:39:26'),('meter',2,2000,'Meter Device Id','2021-04-28 06:39:26'),('meter-log',1,2000,'Meter Log Id','2021-03-11 03:03:30'),('plan',1,2000,'Plan Id','2021-03-11 03:03:30'),('pole',2,2000,'Pole Id','2021-04-28 06:39:26'),('rule',1,2000,'Rule','2021-03-11 03:06:42'),('rule-event',1,2000,'Rule Event','2021-03-11 03:06:42'),('screen',2,2000,'Screen Device Id','2021-04-28 06:39:26'),('screen-content',1,2000,'Screen Content Id','2021-03-11 03:03:30'),('server',2,2000,'Edge Server Id','2021-04-28 06:39:26'),('weather',2,2000,'Weather Device Id','2021-04-28 06:39:26'),('weather-log',1,2000,'Weather Log Id','2021-03-11 03:03:30'),('wifi',2,2000,'WiFi Device Id','2021-04-28 06:39:26'),('wifi-log',1,2000,'Wifi Log Id','2021-03-11 03:03:30');
/*!40000 ALTER TABLE `leaf_alloc` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-28 14:40:05

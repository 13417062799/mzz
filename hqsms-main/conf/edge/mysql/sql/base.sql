CREATE DATABASE  IF NOT EXISTS `hqsms` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `hqsms`;
-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: hqsms
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `eg_alarm`
--

DROP TABLE IF EXISTS `eg_alarm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_alarm` (
  `id` bigint NOT NULL,
  `poleId` bigint NOT NULL,
  `groupId` bigint DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `isOn` tinyint NOT NULL,
  `status` tinyint DEFAULT NULL,
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `netmask` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gateway` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `productBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchaseBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchasedAt` timestamp NULL DEFAULT NULL,
  `supplier` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `model` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_alarm`
--

LOCK TABLES `eg_alarm` WRITE;
/*!40000 ALTER TABLE `eg_alarm` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_alarm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_alarm_log`
--

DROP TABLE IF EXISTS `eg_alarm_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_alarm_log` (
  `id` bigint NOT NULL,
  `alarmId` int NOT NULL,
  `videoUrl` varchar(2083) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `imageUrl` varchar(2083) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `audioUrl` varchar(2083) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  `latitude` float DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_alarm_log`
--

LOCK TABLES `eg_alarm_log` WRITE;
/*!40000 ALTER TABLE `eg_alarm_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_alarm_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_ammeter`
--

DROP TABLE IF EXISTS `eg_ammeter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_ammeter` (
  `id` bigint NOT NULL,
  `poleId` bigint NOT NULL,
  `groupId` bigint DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `isOn` tinyint NOT NULL,
  `status` tinyint DEFAULT NULL,
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `port` int DEFAULT NULL,
  `slaveId` int DEFAULT NULL,
  `mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `netmask` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gateway` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `productBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchaseBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchasedAt` timestamp NULL DEFAULT NULL,
  `supplier` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `model` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_ammeter`
--

LOCK TABLES `eg_ammeter` WRITE;
/*!40000 ALTER TABLE `eg_ammeter` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_ammeter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_broadcast`
--

DROP TABLE IF EXISTS `eg_broadcast`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_broadcast` (
  `id` bigint NOT NULL,
  `poleId` bigint NOT NULL,
  `groupId` bigint DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `volume` int NOT NULL,
  `isOn` tinyint NOT NULL,
  `status` tinyint DEFAULT NULL,
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `relayIP` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `netmask` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gateway` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `productBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchaseBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchasedAt` timestamp NULL DEFAULT NULL,
  `supplier` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `model` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `cookie` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_broadcast`
--

LOCK TABLES `eg_broadcast` WRITE;
/*!40000 ALTER TABLE `eg_broadcast` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_broadcast` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_broadcast_content`
--

DROP TABLE IF EXISTS `eg_broadcast_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_broadcast_content` (
  `id` bigint NOT NULL,
  `broadcastId` bigint NOT NULL,
  `programId` int DEFAULT '-1',
  `sessionId` int DEFAULT '-1',
  `length` int DEFAULT NULL COMMENT '时长',
  `size` bigint DEFAULT NULL,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `contentName` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `isPlayed` tinyint DEFAULT NULL,
  `ftpUrl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NULL DEFAULT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_broadcast_content`
--

LOCK TABLES `eg_broadcast_content` WRITE;
/*!40000 ALTER TABLE `eg_broadcast_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_broadcast_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_broadcast_plan`
--

DROP TABLE IF EXISTS `eg_broadcast_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_broadcast_plan` (
  `id` bigint NOT NULL,
  `contentId` bigint NOT NULL,
  `taskId` int DEFAULT NULL,
  `planName` varchar(45) DEFAULT NULL,
  `planVol` int DEFAULT NULL,
  `playMode` int DEFAULT NULL,
  `repeatTime` int DEFAULT NULL,
  `startTime` timestamp NULL DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `period` varchar(255) DEFAULT NULL,
  `isPlayed` tinyint DEFAULT '0',
  `isEnabled` tinyint DEFAULT '1',
  `createdAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  `updatedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_broadcast_plan`
--

LOCK TABLES `eg_broadcast_plan` WRITE;
/*!40000 ALTER TABLE `eg_broadcast_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_broadcast_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_broadcast_task`
--

DROP TABLE IF EXISTS `eg_broadcast_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_broadcast_task` (
  `id` bigint NOT NULL,
  `broadcastId` bigint DEFAULT NULL,
  `taskId` int DEFAULT NULL,
  `taskName` varchar(45) DEFAULT NULL,
  `taskVol` int DEFAULT NULL,
  `playMode` int DEFAULT NULL,
  `length` int DEFAULT NULL,
  `repeatTime` int DEFAULT NULL,
  `startTime` timestamp NULL DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `period` varchar(255) DEFAULT NULL,
  `isPlayed` tinyint DEFAULT NULL,
  `isFrozen` tinyint DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  `updatedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_broadcast_task`
--

LOCK TABLES `eg_broadcast_task` WRITE;
/*!40000 ALTER TABLE `eg_broadcast_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_broadcast_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_camera`
--

DROP TABLE IF EXISTS `eg_camera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_camera` (
  `id` bigint NOT NULL,
  `poleId` bigint NOT NULL,
  `groupId` bigint DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `isOn` tinyint NOT NULL,
  `status` tinyint DEFAULT NULL,
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `netmask` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gateway` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `productBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchaseBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchasedAt` timestamp NULL DEFAULT NULL,
  `supplier` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `model` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NULL DEFAULT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_camera`
--

LOCK TABLES `eg_camera` WRITE;
/*!40000 ALTER TABLE `eg_camera` DISABLE KEYS */;
INSERT INTO `eg_camera` VALUES (1,1,1,'camera1','cam-01',0,NULL,'192.168.18.51','10-12-FB-2F-99-84','255.255.255.0','192.168.1.1','1','1','2020-10-16 19:16:23','华全','iDS001','2020-10-16 19:16:23','2020-10-16 19:16:23',NULL,'admin','xt86611997'),(2,1,1,'camera2','cam-02',0,NULL,'192.168.18.53','bc:ba:c2:c4:c8:7c','255.255.255.0','192.168.1.1','1','1','2020-10-16 19:16:23','华全','DS-2DC4223IW-D','2020-10-16 19:16:23','2020-10-16 19:16:23',NULL,'admin','xt86611997'),(3,1,NULL,'camera3','cam-03',0,NULL,'192.168.18.52',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(4,1,NULL,'camera4',NULL,0,NULL,'192.168.18.65',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(5,1,NULL,'camera5',NULL,0,NULL,'192.168.18.60',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(6,1,NULL,'camera6',NULL,0,NULL,'192.168.18.68',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(7,1,NULL,'camera7',NULL,0,NULL,'192.168.18.67',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(8,1,NULL,'camera8',NULL,0,NULL,'192.168.18.64',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(9,1,NULL,'camera9',NULL,0,NULL,'192.168.18.70',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(10,1,NULL,'camera10',NULL,0,NULL,'192.168.18.50',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(11,1,NULL,'camera11',NULL,0,NULL,'192.168.18.71',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(12,1,NULL,'camera12',NULL,0,NULL,'192.168.18.72',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(13,1,NULL,'camera13',NULL,0,NULL,'192.168.18.69',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(14,1,NULL,'camera14',NULL,0,NULL,'192.168.18.38',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(15,1,NULL,'camera15',NULL,0,NULL,'192.168.18.66',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(16,1,NULL,'camera16',NULL,0,NULL,'192.168.18.73',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(17,1,NULL,'camera17',NULL,0,NULL,'192.168.18.62',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(18,1,NULL,'camera18',NULL,0,NULL,'192.168.18.40',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(19,1,NULL,'camera19',NULL,0,NULL,'192.168.18.41',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(20,1,NULL,'camera20',NULL,0,NULL,'192.168.18.24',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(21,1,NULL,'camera21',NULL,0,NULL,'192.168.18.10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(22,1,NULL,'camera22',NULL,0,NULL,'192.168.18.27',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(23,1,NULL,'camera23',NULL,0,NULL,'192.168.18.59',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(24,1,NULL,'camera24',NULL,0,NULL,'192.168.18.26',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(25,1,NULL,'camera25',NULL,0,NULL,'192.168.18.75',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','xt86611997'),(26,1,NULL,'cameraM','cam-M',1,NULL,'192.168.1.111',NULL,'255.255.255.0','192.168.1.1',NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','Hg123_123'),(27,1,NULL,'cameraN','cam-N',0,NULL,'192.168.1.18',NULL,'255.255.255.0','192.168.1.1',NULL,NULL,NULL,NULL,NULL,'2020-10-16 19:16:23',NULL,NULL,'admin','sanki123456');
/*!40000 ALTER TABLE `eg_camera` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_camera_human`
--

DROP TABLE IF EXISTS `eg_camera_human`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_camera_human` (
  `id` bigint NOT NULL,
  `cameraId` bigint NOT NULL,
  `longitude` float(10,7) DEFAULT NULL,
  `latitude` float DEFAULT NULL,
  `capturedAt` timestamp NULL DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `faceScore` float DEFAULT NULL,
  `faceRectX` float DEFAULT NULL,
  `faceRectY` float DEFAULT NULL,
  `faceRectW` float DEFAULT NULL,
  `faceRectH` float DEFAULT NULL,
  `humanSex` tinyint DEFAULT NULL,
  `humanGlass` tinyint DEFAULT NULL,
  `humanMask` tinyint DEFAULT NULL,
  `humanSmile` tinyint DEFAULT NULL,
  `humanBeard` tinyint DEFAULT NULL,
  `humanHat` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_camera_human`
--

LOCK TABLES `eg_camera_human` WRITE;
/*!40000 ALTER TABLE `eg_camera_human` DISABLE KEYS */;
INSERT INTO `eg_camera_human` VALUES (44001,26,113.1090012,23.0192,'2021-04-13 18:15:41','2021-04-13 18:18:03',0.3,0.393,0.411,0.05,0.094,NULL,NULL,NULL,NULL,NULL,NULL),(44002,26,113.1090012,23.0192,'2021-04-13 18:26:31','2021-04-13 18:28:40',0.21,0.662,0.411,0.056,0.097,NULL,NULL,NULL,NULL,NULL,NULL),(46001,26,113.1090012,23.0192,'2021-04-13 18:32:58','2021-04-13 18:36:28',0.24,0.418,0.288,0.043,0.086,NULL,NULL,NULL,NULL,NULL,NULL),(48001,26,113.1090012,23.0192,'2021-04-13 18:54:22','2021-04-13 18:56:10',0.24,0.731,0.366,0.043,0.086,NULL,NULL,NULL,NULL,NULL,NULL),(48002,26,113.1090012,23.0192,'2021-04-13 18:54:25','2021-04-13 18:56:15',0.24,0.725,0.366,0.043,0.086,NULL,NULL,NULL,NULL,NULL,NULL),(48003,26,113.1090012,23.0192,'2021-04-13 18:54:31','2021-04-13 18:56:19',0.23,0.718,0.366,0.056,0.097,NULL,NULL,NULL,NULL,NULL,NULL),(50001,26,113.1090012,23.0192,'2021-04-13 19:01:03','2021-04-13 19:02:58',0.23,0.337,0.366,0.043,0.086,NULL,NULL,NULL,NULL,NULL,NULL),(50002,26,113.1090012,23.0192,'2021-04-13 19:01:06','2021-04-13 19:03:22',0.29,0.406,0.388,0.05,0.091,NULL,NULL,NULL,NULL,NULL,NULL),(50003,26,113.1090012,23.0192,'2021-04-13 19:02:16','2021-04-13 19:04:03',0.27,0.275,0.488,0.056,0.105,NULL,NULL,NULL,NULL,NULL,NULL),(50004,26,113.1090012,23.0192,'2021-04-13 19:02:22','2021-04-13 19:04:10',0.26,0.275,0.488,0.056,0.097,NULL,NULL,NULL,NULL,NULL,NULL),(50005,26,113.1090012,23.0192,'2021-04-13 19:02:31','2021-04-13 19:04:19',0.26,0.268,0.488,0.056,0.105,NULL,NULL,NULL,NULL,NULL,NULL),(52001,26,113.1090012,23.0192,'2021-04-13 19:11:58','2021-04-13 19:14:14',0.32,0.393,0.377,0.043,0.083,NULL,NULL,NULL,NULL,NULL,NULL),(52002,26,113.1090012,23.0192,'2021-04-13 19:12:14','2021-04-13 19:14:28',0.21,0.325,0.533,0.05,0.091,NULL,NULL,NULL,NULL,NULL,NULL),(54001,26,113.1090012,23.0192,'2021-04-13 19:48:55','2021-04-13 19:51:13',0.28,0.368,0.433,0.05,0.091,NULL,NULL,NULL,NULL,NULL,NULL),(54002,26,113.1090012,23.0192,'2021-04-13 19:49:17','2021-04-13 19:51:21',0.23,0.3,0.422,0.05,0.091,NULL,NULL,NULL,NULL,NULL,NULL),(54003,26,113.1090012,23.0192,'2021-04-13 19:49:32','2021-04-13 19:51:23',0.21,0.368,0.433,0.05,0.091,NULL,NULL,NULL,NULL,NULL,NULL),(54004,26,113.1090012,23.0192,'2021-04-13 19:49:38','2021-04-13 19:51:26',0.3,0.287,0.577,0.043,0.086,NULL,NULL,NULL,NULL,NULL,NULL),(54005,26,113.1090012,23.0192,'2021-04-13 19:50:29','2021-04-13 19:52:25',0.26,0.662,0.666,0.043,0.083,NULL,NULL,NULL,NULL,NULL,NULL),(56001,26,113.1090012,23.0192,'2021-04-13 19:56:50','2021-04-13 19:59:12',0.29,0.25,0.366,0.056,0.1,NULL,NULL,NULL,NULL,NULL,NULL),(56002,26,113.1090012,23.0192,'2021-04-13 20:01:03','2021-04-13 21:12:19',0.23,0.612,0.655,0.056,0.105,NULL,NULL,NULL,NULL,NULL,NULL),(56003,26,113.1090012,23.0192,'2021-04-13 21:11:12','2021-04-13 21:13:28',0.26,0.7,0.433,0.043,0.086,NULL,NULL,NULL,NULL,NULL,NULL),(56004,26,113.1090012,23.0192,'2021-04-13 21:12:13','2021-04-13 21:18:55',0.21,0.375,0.377,0.05,0.091,NULL,NULL,NULL,NULL,NULL,NULL),(56005,26,113.1090012,23.0192,'2021-04-13 21:19:33','2021-04-13 21:21:27',0.27,0.318,0.6,0.043,0.086,NULL,NULL,NULL,NULL,NULL,NULL),(56006,26,113.1090012,23.0192,'2021-04-13 21:20:04','2021-04-13 21:22:31',0.28,0.4,0.411,0.05,0.091,NULL,NULL,NULL,NULL,NULL,NULL),(58001,26,113.1090012,23.0192,'2021-04-13 21:23:09','2021-04-13 21:24:55',0.28,0.281,0.522,0.043,0.091,NULL,NULL,NULL,NULL,NULL,NULL),(58002,26,113.1090012,23.0192,'2021-04-13 21:24:01','2021-04-13 21:25:53',0.33,0.412,0.366,0.056,0.097,NULL,NULL,NULL,NULL,NULL,NULL),(58003,26,113.1090012,23.0192,'2021-04-13 21:25:46','2021-04-13 21:27:38',0.37,0.418,0.311,0.056,0.105,NULL,NULL,NULL,NULL,NULL,NULL),(58004,26,113.1090012,23.0192,'2021-04-13 21:28:33','2021-04-13 21:30:21',0.31,0.7,0.388,0.043,0.086,NULL,NULL,NULL,NULL,NULL,NULL),(58005,26,113.1090012,23.0192,'2021-04-13 21:30:12','2021-04-13 21:32:01',0.37,0.312,0.577,0.05,0.094,NULL,NULL,NULL,NULL,NULL,NULL),(58006,26,113.1090012,23.0192,'2021-04-13 21:30:22','2021-04-13 21:32:10',0.28,0.318,0.566,0.05,0.091,NULL,NULL,NULL,NULL,NULL,NULL),(58007,26,113.1090012,23.0192,'2021-04-13 21:30:26','2021-04-13 21:32:14',0.28,0.318,0.566,0.05,0.091,NULL,NULL,NULL,NULL,NULL,NULL),(58008,26,113.1090012,23.0192,'2021-04-13 21:31:23','2021-04-13 21:33:13',0.22,0.368,0.444,0.056,0.097,NULL,NULL,NULL,NULL,NULL,NULL),(58009,26,113.1090012,23.0192,'2021-04-13 21:32:44','2021-04-13 21:34:30',0.32,0.093,0.855,0.056,0.1,NULL,NULL,NULL,NULL,NULL,NULL),(58010,26,113.1090012,23.0192,'2021-04-13 21:35:38','2021-04-13 21:37:26',0.24,0.693,0.355,0.043,0.083,NULL,NULL,NULL,NULL,NULL,NULL),(58011,26,113.1090012,23.0192,'2021-04-13 21:35:48','2021-04-13 21:37:35',0.25,0.7,0.333,0.043,0.086,NULL,NULL,NULL,NULL,NULL,NULL),(58012,26,113.1090012,23.0192,'2021-04-13 21:35:58','2021-04-13 21:37:46',0.27,0.7,0.333,0.043,0.086,NULL,NULL,NULL,NULL,NULL,NULL),(58013,26,113.1090012,23.0192,'2021-04-13 21:37:40','2021-04-13 21:39:27',0.28,0.368,0.344,0.043,0.086,NULL,NULL,NULL,NULL,NULL,NULL),(58014,26,113.1090012,23.0192,'2021-04-13 21:37:51','2021-04-13 21:39:38',0.28,0.306,0.488,0.043,0.086,NULL,NULL,NULL,NULL,NULL,NULL),(58015,26,113.1090012,23.0192,'2021-04-13 21:39:37','2021-04-13 21:41:30',0.24,0.65,0.455,0.056,0.097,NULL,NULL,NULL,NULL,NULL,NULL),(58016,26,113.1090012,23.0192,'2021-04-13 21:41:44','2021-04-13 21:43:38',0.22,0.625,0.6,0.05,0.091,NULL,NULL,NULL,NULL,NULL,NULL),(58017,26,113.1090012,23.0192,'2021-04-13 21:43:12','2021-04-13 21:45:07',0.25,0.656,0.455,0.043,0.086,NULL,NULL,NULL,NULL,NULL,NULL),(58018,26,113.1090012,23.0192,'2021-04-13 21:44:14','2021-04-13 21:46:09',0.25,0.7,0.5,0.056,0.097,NULL,NULL,NULL,NULL,NULL,NULL),(58019,26,113.1090012,23.0192,'2021-04-13 21:45:04','2021-04-13 21:47:09',0.22,0.081,0.5,0.05,0.094,NULL,NULL,NULL,NULL,NULL,NULL),(58020,26,113.1090012,23.0192,'2021-04-13 21:45:14','2021-04-13 21:47:17',0.2,0.668,0.488,0.05,0.094,NULL,NULL,NULL,NULL,NULL,NULL),(58021,26,113.1090012,23.0192,'2021-04-13 21:45:16','2021-04-13 21:48:10',0.21,0.312,0.511,0.05,0.094,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `eg_camera_human` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_camera_log`
--

DROP TABLE IF EXISTS `eg_camera_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_camera_log` (
  `id` bigint NOT NULL,
  `cameraId` bigint NOT NULL,
  `longitude` float DEFAULT NULL,
  `latitude` float DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `startedAt` timestamp(3) NULL DEFAULT NULL,
  `endedAt` timestamp(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_camera_log`
--

LOCK TABLES `eg_camera_log` WRITE;
/*!40000 ALTER TABLE `eg_camera_log` DISABLE KEYS */;
INSERT INTO `eg_camera_log` VALUES (140001,26,113.109,23.0192,'2021-04-13 18:16:32','2021-04-13 18:15:30.900','2021-04-13 18:16:30.900'),(140002,26,113.109,23.0192,'2021-04-13 18:17:32','2021-04-13 18:16:31.050','2021-04-13 18:17:31.050'),(140003,26,113.109,23.0192,'2021-04-13 18:18:32','2021-04-13 18:17:31.076','2021-04-13 18:18:31.076'),(140004,26,113.109,23.0192,'2021-04-13 18:19:32','2021-04-13 18:18:31.104','2021-04-13 18:19:31.104'),(140005,26,113.109,23.0192,'2021-04-13 18:20:32','2021-04-13 18:19:31.111','2021-04-13 18:20:31.111'),(140006,26,113.109,23.0192,'2021-04-13 18:21:32','2021-04-13 18:20:31.138','2021-04-13 18:21:31.138'),(140007,26,113.109,23.0192,'2021-04-13 18:22:32','2021-04-13 18:21:31.165','2021-04-13 18:22:31.165'),(140008,26,113.109,23.0192,'2021-04-13 18:23:32','2021-04-13 18:22:31.206','2021-04-13 18:23:31.206'),(140009,26,113.109,23.0192,'2021-04-13 18:24:32','2021-04-13 18:23:31.232','2021-04-13 18:24:31.232'),(140010,26,113.109,23.0192,'2021-04-13 18:25:32','2021-04-13 18:24:31.239','2021-04-13 18:25:31.239'),(140011,26,113.109,23.0192,'2021-04-13 18:26:33','2021-04-13 18:25:31.266','2021-04-13 18:26:31.266'),(140012,26,113.109,23.0192,'2021-04-13 18:27:33','2021-04-13 18:26:31.321','2021-04-13 18:27:31.321'),(140013,26,113.109,23.0192,'2021-04-13 18:28:33','2021-04-13 18:27:31.330','2021-04-13 18:28:31.330'),(140014,26,113.109,23.0192,'2021-04-13 18:29:33','2021-04-13 18:28:31.361','2021-04-13 18:29:31.361'),(142001,26,113.109,23.0192,'2021-04-13 18:31:42','2021-04-13 18:30:40.988','2021-04-13 18:31:40.988'),(142002,26,113.109,23.0192,'2021-04-13 18:32:42','2021-04-13 18:31:41.019','2021-04-13 18:32:41.019'),(142003,26,113.109,23.0192,'2021-04-13 18:33:42','2021-04-13 18:32:41.054','2021-04-13 18:33:41.054'),(144001,26,113.109,23.0192,'2021-04-13 18:36:28','2021-04-13 18:34:23.676','2021-04-13 18:35:23.676'),(146001,26,113.109,23.0192,'2021-04-13 18:51:57','2021-04-13 18:50:55.607','2021-04-13 18:51:55.607'),(146002,26,113.109,23.0192,'2021-04-13 18:52:57','2021-04-13 18:51:55.639','2021-04-13 18:52:55.639'),(146003,26,113.109,23.0192,'2021-04-13 18:53:57','2021-04-13 18:52:55.670','2021-04-13 18:53:55.670'),(146004,26,113.109,23.0192,'2021-04-13 18:54:57','2021-04-13 18:53:55.704','2021-04-13 18:54:55.704'),(146005,26,113.109,23.0192,'2021-04-13 18:55:57','2021-04-13 18:54:55.730','2021-04-13 18:55:55.730'),(146006,26,113.109,23.0192,'2021-04-13 18:56:57','2021-04-13 18:55:55.763','2021-04-13 18:56:55.763'),(146007,26,113.109,23.0192,'2021-04-13 18:57:57','2021-04-13 18:56:55.790','2021-04-13 18:57:55.790'),(148001,26,113.109,23.0192,'2021-04-13 19:03:51','2021-04-13 19:02:49.979','2021-04-13 19:03:49.979'),(148002,26,113.109,23.0192,'2021-04-13 19:04:51','2021-04-13 19:03:50.008','2021-04-13 19:04:50.008'),(148003,26,113.109,23.0192,'2021-04-13 19:05:51','2021-04-13 19:04:50.017','2021-04-13 19:05:50.017'),(148004,26,113.109,23.0192,'2021-04-13 19:06:51','2021-04-13 19:05:50.042','2021-04-13 19:06:50.042'),(150001,26,113.109,23.0192,'2021-04-13 19:13:41','2021-04-13 19:12:39.932','2021-04-13 19:13:39.932'),(150002,26,113.109,23.0192,'2021-04-13 19:14:41','2021-04-13 19:13:39.961','2021-04-13 19:14:39.961'),(150003,26,113.109,23.0192,'2021-04-13 19:16:48','2021-04-13 19:14:39.997','2021-04-13 19:15:39.997'),(152001,26,113.109,23.0192,'2021-04-13 19:48:11','2021-04-13 19:47:09.580','2021-04-13 19:48:09.580'),(152002,26,113.109,23.0192,'2021-04-13 19:49:11','2021-04-13 19:48:09.644','2021-04-13 19:49:09.644'),(152003,26,113.109,23.0192,'2021-04-13 19:50:11','2021-04-13 19:49:09.669','2021-04-13 19:50:09.669'),(152004,26,113.109,23.0192,'2021-04-13 19:51:11','2021-04-13 19:50:09.713','2021-04-13 19:51:09.713'),(152005,26,113.109,23.0192,'2021-04-13 19:52:11','2021-04-13 19:51:09.741','2021-04-13 19:52:09.741'),(152006,26,113.109,23.0192,'2021-04-13 19:53:11','2021-04-13 19:52:09.780','2021-04-13 19:53:09.780'),(154001,26,113.109,23.0192,'2021-04-13 19:55:03','2021-04-13 19:54:02.192','2021-04-13 19:55:02.192'),(154002,26,113.109,23.0192,'2021-04-13 19:56:03','2021-04-13 19:55:02.204','2021-04-13 19:56:02.204'),(154003,26,113.109,23.0192,'2021-04-13 19:57:03','2021-04-13 19:56:02.230','2021-04-13 19:57:02.230'),(154004,26,113.109,23.0192,'2021-04-13 19:58:03','2021-04-13 19:57:02.249','2021-04-13 19:58:02.249'),(154005,26,113.109,23.0192,'2021-04-13 19:59:13','2021-04-13 19:58:02.298','2021-04-13 19:59:02.298'),(154006,26,113.109,23.0192,'2021-04-13 20:00:13','2021-04-13 19:59:11.990','2021-04-13 20:00:11.990'),(154007,26,113.109,23.0192,'2021-04-13 20:01:13','2021-04-13 20:00:11.999','2021-04-13 20:01:11.999'),(154008,26,113.109,23.0192,'2021-04-13 20:02:13','2021-04-13 20:01:12.036','2021-04-13 20:02:12.036'),(154009,26,113.109,23.0192,'2021-04-13 21:12:19','2021-04-13 20:02:12.064','2021-04-13 20:03:12.064'),(154010,26,113.109,23.0192,'2021-04-13 21:12:24','2021-04-13 21:11:22.952','2021-04-13 21:12:22.952'),(154011,26,113.109,23.0192,'2021-04-13 21:13:29','2021-04-13 21:12:22.958','2021-04-13 21:13:22.958'),(154012,26,113.109,23.0192,'2021-04-13 21:18:56','2021-04-13 21:13:28.281','2021-04-13 21:14:28.281'),(154013,26,113.109,23.0192,'2021-04-13 21:19:57','2021-04-13 21:18:55.285','2021-04-13 21:19:55.285'),(154014,26,113.109,23.0192,'2021-04-13 21:20:57','2021-04-13 21:19:55.313','2021-04-13 21:20:55.313'),(154015,26,113.109,23.0192,'2021-04-13 21:22:33','2021-04-13 21:20:55.353','2021-04-13 21:21:55.353'),(156001,26,113.109,23.0192,'2021-04-13 21:25:33','2021-04-13 21:24:31.895','2021-04-13 21:25:31.895'),(156002,26,113.109,23.0192,'2021-04-13 21:26:33','2021-04-13 21:25:31.924','2021-04-13 21:26:31.924'),(156003,26,113.109,23.0192,'2021-04-13 21:27:33','2021-04-13 21:26:31.951','2021-04-13 21:27:31.951'),(156004,26,113.109,23.0192,'2021-04-13 21:28:33','2021-04-13 21:27:31.972','2021-04-13 21:28:31.972'),(156005,26,113.109,23.0192,'2021-04-13 21:29:33','2021-04-13 21:28:31.999','2021-04-13 21:29:31.999'),(156006,26,113.109,23.0192,'2021-04-13 21:30:33','2021-04-13 21:29:32.025','2021-04-13 21:30:32.025'),(156007,26,113.109,23.0192,'2021-04-13 21:31:33','2021-04-13 21:30:32.052','2021-04-13 21:31:32.052'),(156008,26,113.109,23.0192,'2021-04-13 21:32:33','2021-04-13 21:31:32.058','2021-04-13 21:32:32.058'),(156009,26,113.109,23.0192,'2021-04-13 21:33:33','2021-04-13 21:32:32.086','2021-04-13 21:33:32.086'),(156010,26,113.109,23.0192,'2021-04-13 21:34:33','2021-04-13 21:33:32.105','2021-04-13 21:34:32.105'),(156011,26,113.109,23.0192,'2021-04-13 21:35:33','2021-04-13 21:34:32.139','2021-04-13 21:35:32.139'),(156012,26,113.109,23.0192,'2021-04-13 21:36:33','2021-04-13 21:35:32.179','2021-04-13 21:36:32.179'),(156013,26,113.109,23.0192,'2021-04-13 21:37:33','2021-04-13 21:36:32.205','2021-04-13 21:37:32.205'),(156014,26,113.109,23.0192,'2021-04-13 21:38:33','2021-04-13 21:37:32.212','2021-04-13 21:38:32.212'),(156015,26,113.109,23.0192,'2021-04-13 21:39:33','2021-04-13 21:38:32.238','2021-04-13 21:39:32.238'),(156016,26,113.109,23.0192,'2021-04-13 21:40:33','2021-04-13 21:39:32.251','2021-04-13 21:40:32.251'),(156017,26,113.109,23.0192,'2021-04-13 21:41:34','2021-04-13 21:40:32.278','2021-04-13 21:41:32.278'),(156018,26,113.109,23.0192,'2021-04-13 21:42:34','2021-04-13 21:41:32.323','2021-04-13 21:42:32.323'),(156019,26,113.109,23.0192,'2021-04-13 21:43:39','2021-04-13 21:42:33.054','2021-04-13 21:43:33.054'),(156020,26,113.109,23.0192,'2021-04-13 21:44:39','2021-04-13 21:43:38.107','2021-04-13 21:44:38.107'),(156021,26,113.109,23.0192,'2021-04-13 21:45:39','2021-04-13 21:44:38.133','2021-04-13 21:45:38.133'),(156022,26,113.109,23.0192,'2021-04-13 21:46:39','2021-04-13 21:45:38.159','2021-04-13 21:46:38.159'),(156023,26,113.109,23.0192,'2021-04-13 21:47:59','2021-04-13 21:46:38.166','2021-04-13 21:47:38.166');
/*!40000 ALTER TABLE `eg_camera_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_camera_vehicle`
--

DROP TABLE IF EXISTS `eg_camera_vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_camera_vehicle` (
  `id` bigint NOT NULL,
  `cameraId` bigint NOT NULL,
  `longitude` float DEFAULT NULL,
  `latitude` float DEFAULT NULL,
  `capturedAt` timestamp NULL DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `plateType` tinyint DEFAULT NULL,
  `plateColor` tinyint DEFAULT NULL,
  `plateRectX` float DEFAULT NULL,
  `plateRectY` float DEFAULT NULL,
  `plateRectW` float DEFAULT NULL,
  `plateRectH` float DEFAULT NULL,
  `plateBright` tinyint DEFAULT NULL,
  `plateCharsLength` tinyint DEFAULT NULL,
  `plateChars` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `plateCharsScore` varchar(70) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `plateEntireScore` float DEFAULT NULL,
  `vehicleType` tinyint DEFAULT NULL,
  `vehicleColor` tinyint DEFAULT NULL,
  `vehicleColorDepth` tinyint DEFAULT NULL,
  `vehicleLogoRecog` tinyint DEFAULT NULL,
  `vecicleSubLogoRecog` tinyint DEFAULT NULL,
  `vehicleModel` tinyint DEFAULT NULL,
  `vehicleSubLogoRecog` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_camera_vehicle`
--

LOCK TABLES `eg_camera_vehicle` WRITE;
/*!40000 ALTER TABLE `eg_camera_vehicle` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_camera_vehicle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_charger`
--

DROP TABLE IF EXISTS `eg_charger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_charger` (
  `id` bigint NOT NULL,
  `poleId` bigint NOT NULL,
  `groupId` bigint DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `isOn` tinyint NOT NULL,
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `netmask` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gateway` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `productBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchaseBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchasedAt` timestamp NULL DEFAULT NULL,
  `supplier` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `model` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  `url` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `stationId` int NOT NULL,
  `jChargerId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_charger`
--

LOCK TABLES `eg_charger` WRITE;
/*!40000 ALTER TABLE `eg_charger` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_charger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_charger_log`
--

DROP TABLE IF EXISTS `eg_charger_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_charger_log` (
  `id` bigint NOT NULL,
  `chargeId` bigint NOT NULL,
  `outVoltage` float DEFAULT NULL,
  `outCurrent` float DEFAULT NULL,
  `power` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `latitude` float DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  `chargerId` decimal(19,2) DEFAULT NULL,
  `chargingTime` int DEFAULT NULL,
  `maxBatteryTemp` float DEFAULT NULL,
  `remoteTime` datetime DEFAULT NULL,
  `residualTime` int DEFAULT NULL,
  `soc` int DEFAULT NULL,
  `totalEnergy` float DEFAULT NULL,
  `workStatus` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_charger_log`
--

LOCK TABLES `eg_charger_log` WRITE;
/*!40000 ALTER TABLE `eg_charger_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_charger_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_charger_order`
--

DROP TABLE IF EXISTS `eg_charger_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_charger_order` (
  `id` bigint NOT NULL,
  `chargerId` bigint NOT NULL,
  `startTime` timestamp NULL DEFAULT NULL,
  `endTime` timestamp NULL DEFAULT NULL,
  `totalPower` float(10,2) DEFAULT NULL,
  `totalMoney` float DEFAULT NULL,
  `longitude` float(9,6) DEFAULT NULL,
  `latitude` float(9,6) DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_charger_order`
--

LOCK TABLES `eg_charger_order` WRITE;
/*!40000 ALTER TABLE `eg_charger_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_charger_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_charger_state`
--

DROP TABLE IF EXISTS `eg_charger_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_charger_state` (
  `id` bigint NOT NULL,
  `chargerId` bigint NOT NULL,
  `voltage` float(6,2) DEFAULT NULL,
  `current` float(6,2) DEFAULT NULL,
  `totalEnergy` float(10,2) DEFAULT NULL,
  `workTime` int DEFAULT NULL,
  `residualTime` int DEFAULT NULL,
  `latitude` float(9,6) DEFAULT NULL,
  `longitude` float(9,6) DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `chargingTime` int DEFAULT NULL,
  `maxBatteryTemp` float DEFAULT NULL,
  `outCurrent` float DEFAULT NULL,
  `outVoltage` float DEFAULT NULL,
  `remoteTime` datetime DEFAULT NULL,
  `soc` int DEFAULT NULL,
  `workStatus` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_charger_state`
--

LOCK TABLES `eg_charger_state` WRITE;
/*!40000 ALTER TABLE `eg_charger_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_charger_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_electric`
--

DROP TABLE IF EXISTS `eg_electric`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_electric` (
  `id` bigint NOT NULL,
  `poleId` bigint NOT NULL,
  `groupId` bigint DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `isOn` tinyint NOT NULL,
  `status` tinyint DEFAULT NULL,
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `netmask` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gateway` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `productBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchaseBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchasedAt` timestamp NULL DEFAULT NULL,
  `supplier` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `model` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_electric`
--

LOCK TABLES `eg_electric` WRITE;
/*!40000 ALTER TABLE `eg_electric` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_electric` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_electric_log`
--

DROP TABLE IF EXISTS `eg_electric_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_electric_log` (
  `id` bigint NOT NULL,
  `ammeterId` bigint NOT NULL,
  `energy` float DEFAULT NULL,
  `workVoltage` float DEFAULT NULL,
  `workCurrent` float DEFAULT NULL,
  `frequency` float DEFAULT NULL,
  `factor` float DEFAULT NULL,
  `phaseEnergy` float DEFAULT NULL,
  `residualCurrent` float DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_electric_log`
--

LOCK TABLES `eg_electric_log` WRITE;
/*!40000 ALTER TABLE `eg_electric_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_electric_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_light`
--

DROP TABLE IF EXISTS `eg_light`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_light` (
  `id` bigint NOT NULL,
  `poleId` bigint NOT NULL,
  `groupId` bigint DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `isOn` tinyint NOT NULL,
  `status` tinyint DEFAULT NULL,
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `netmask` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gateway` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `productBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchaseBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchasedAt` timestamp NULL DEFAULT NULL,
  `supplier` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `model` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_light`
--

LOCK TABLES `eg_light` WRITE;
/*!40000 ALTER TABLE `eg_light` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_light` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_light_log`
--

DROP TABLE IF EXISTS `eg_light_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_light_log` (
  `id` bigint NOT NULL,
  `lightId` bigint NOT NULL,
  `childNo` tinyint NOT NULL,
  `temperature` tinyint DEFAULT NULL,
  `inVoltage` float DEFAULT NULL,
  `inCurrent` float DEFAULT NULL,
  `outVoltage` float DEFAULT NULL,
  `outCurrent` float DEFAULT NULL,
  `power` float DEFAULT NULL,
  `factor` float DEFAULT NULL,
  `energy` float DEFAULT NULL,
  `brightness` int DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_light_log`
--

LOCK TABLES `eg_light_log` WRITE;
/*!40000 ALTER TABLE `eg_light_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_light_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_meter`
--

DROP TABLE IF EXISTS `eg_meter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_meter` (
  `id` bigint NOT NULL,
  `poleId` bigint NOT NULL,
  `groupId` bigint DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `isOn` tinyint NOT NULL,
  `status` tinyint DEFAULT NULL,
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `port` int DEFAULT NULL,
  `slaveId` int DEFAULT NULL,
  `mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `netmask` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gateway` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `productBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchaseBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchasedAt` timestamp NULL DEFAULT NULL,
  `supplier` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `model` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_meter`
--

LOCK TABLES `eg_meter` WRITE;
/*!40000 ALTER TABLE `eg_meter` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_meter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_meter_log`
--

DROP TABLE IF EXISTS `eg_meter_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_meter_log` (
  `id` bigint NOT NULL,
  `meterId` bigint NOT NULL,
  `aVoltage` float DEFAULT NULL,
  `bVoltage` float DEFAULT NULL,
  `cVoltage` float DEFAULT NULL,
  `abVoltage` float DEFAULT NULL,
  `cbVoltage` float DEFAULT NULL,
  `acVoltage` float DEFAULT NULL,
  `aCurrent` float DEFAULT NULL,
  `bCurrent` float DEFAULT NULL,
  `cCurrent` float DEFAULT NULL,
  `residualCurrent` float DEFAULT NULL,
  `aEnergy` float DEFAULT NULL,
  `bEnergy` float DEFAULT NULL,
  `cEnergy` float DEFAULT NULL,
  `totalEnergy` float DEFAULT NULL,
  `aPower` float DEFAULT NULL,
  `bPower` float DEFAULT NULL,
  `cPower` float DEFAULT NULL,
  `totalPower` float DEFAULT NULL,
  `aFactor` float DEFAULT NULL,
  `bFactor` float DEFAULT NULL,
  `cFactor` float DEFAULT NULL,
  `totalFactor` float DEFAULT NULL,
  `frequency` float DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `latitude` float DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_meter_log`
--

LOCK TABLES `eg_meter_log` WRITE;
/*!40000 ALTER TABLE `eg_meter_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_meter_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_pole`
--

DROP TABLE IF EXISTS `eg_pole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_pole` (
  `id` int NOT NULL,
  `serverId` int NOT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  `latitude` float DEFAULT NULL,
  `installedAt` timestamp NULL DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_pole`
--

LOCK TABLES `eg_pole` WRITE;
/*!40000 ALTER TABLE `eg_pole` DISABLE KEYS */;
INSERT INTO `eg_pole` VALUES (1,1,'pole1','pole001',113.109,23.0192,'2020-10-16 19:16:23','2020-10-16 19:16:23','2020-10-16 19:16:23',NULL);
/*!40000 ALTER TABLE `eg_pole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_rule`
--

DROP TABLE IF EXISTS `eg_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_rule` (
  `id` bigint NOT NULL,
  `deviceId` bigint NOT NULL,
  `deviceType` tinyint NOT NULL,
  `deviceLogType` tinyint NOT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `level` tinyint DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `reference` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_rule`
--

LOCK TABLES `eg_rule` WRITE;
/*!40000 ALTER TABLE `eg_rule` DISABLE KEYS */;
INSERT INTO `eg_rule` VALUES (1,1,8,2,'1',1,'2020-10-16 19:16:23','{\"faceScoreMin\":0.1,\"faceScoreMax\":0.3}'),(2,1,8,1,'2',1,'2020-10-16 19:16:23','{\"plateEntireScoreMin\":0.9,\"plateEntireScoreMax\":1.0}'),(3,1,8,2,'1',1,'2020-10-16 19:16:23','{\"faceScoreMin\":0.3,\"faceScoreMax\":1.0}');
/*!40000 ALTER TABLE `eg_rule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_rule_event`
--

DROP TABLE IF EXISTS `eg_rule_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_rule_event` (
  `id` bigint NOT NULL,
  `ruleId` bigint NOT NULL,
  `deviceLogId` bigint DEFAULT NULL,
  `deviceLogType` tinyint DEFAULT NULL,
  `level` tinyint DEFAULT NULL,
  `type` tinyint DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  `latitude` float DEFAULT NULL,
  `isDone` tinyint DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_rule_event`
--

LOCK TABLES `eg_rule_event` WRITE;
/*!40000 ALTER TABLE `eg_rule_event` DISABLE KEYS */;
INSERT INTO `eg_rule_event` VALUES (684001,2,658031,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:02',NULL),(684002,2,658032,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:02',NULL),(684003,2,658033,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:02',NULL),(684004,2,658034,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:02',NULL),(684005,2,658035,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:02',NULL),(684006,2,658036,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:02',NULL),(684007,2,658037,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:02',NULL),(684008,2,658038,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:02',NULL),(684009,2,658039,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:02',NULL),(684010,2,658040,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:03',NULL),(684011,2,658041,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:03',NULL),(684012,2,658042,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:03',NULL),(684013,2,658043,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:03',NULL),(684014,2,658044,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:03',NULL),(684015,2,658045,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:03',NULL),(684016,2,658046,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:03',NULL),(684017,2,658047,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:03',NULL),(684018,2,658048,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:03',NULL),(684019,2,658049,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:03',NULL),(684020,2,658050,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:03',NULL),(684021,2,658051,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:04',NULL),(684022,2,658052,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:04',NULL),(684023,2,658053,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:04',NULL),(684024,2,658054,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:04',NULL),(684025,2,658055,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:05',NULL),(684026,2,658056,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:05',NULL),(684027,2,658057,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:05',NULL),(684028,2,658058,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:05',NULL),(684029,2,658059,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:05',NULL),(684030,2,658060,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:05',NULL),(684031,2,658061,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:05',NULL),(684032,2,658062,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:05',NULL),(684033,2,658063,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:05',NULL),(684034,2,658064,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:05',NULL),(684035,2,658065,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:05',NULL),(684036,2,658066,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:05',NULL),(684037,2,658067,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:05',NULL),(684038,2,658068,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:06',NULL),(684039,2,658069,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:06',NULL),(684040,2,658070,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:06',NULL),(684041,2,658071,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:06',NULL),(684042,2,658072,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:06',NULL),(684043,2,658073,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:06',NULL),(684044,2,658074,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:06',NULL),(684045,2,658075,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:06',NULL),(684046,2,658076,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:06',NULL),(684047,2,658077,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:06',NULL),(684048,2,658078,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:06',NULL),(684049,2,658079,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:06',NULL),(684050,2,658080,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:06',NULL),(684051,2,658081,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:07',NULL),(684052,2,658082,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:07',NULL),(684053,2,658083,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:07',NULL),(684054,2,658084,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:07',NULL),(684055,2,658085,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:07',NULL),(684056,2,658086,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:07',NULL),(684057,2,658087,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:07',NULL),(684058,2,658088,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:07',NULL),(684059,2,658089,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:07',NULL),(684060,2,658090,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:07',NULL),(684061,2,658091,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:07',NULL),(684062,2,658092,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:07',NULL),(684063,2,658093,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:07',NULL),(684064,2,658094,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:08',NULL),(684065,2,658095,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:08',NULL),(684066,2,658096,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:08',NULL),(684067,2,658097,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:08',NULL),(684068,2,658098,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:08',NULL),(684069,2,658099,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:08',NULL),(684070,2,658100,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:08',NULL),(684071,2,658101,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:08',NULL),(684072,2,658102,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:08',NULL),(684073,2,658103,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:08',NULL),(684074,2,658104,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:08',NULL),(684075,2,658106,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:08',NULL),(684076,2,658107,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:08',NULL),(684077,2,658108,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:10',NULL),(684078,2,658109,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:12',NULL),(684079,2,658111,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:16',NULL),(684080,2,658112,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:18',NULL),(684081,2,658113,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:20',NULL),(684082,2,658114,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:22',NULL),(684083,2,658115,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:23',NULL),(684084,2,658116,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:27',NULL),(684085,2,658117,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:29',NULL),(684086,2,658118,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:32',NULL),(684087,2,658119,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:39',NULL),(684088,2,658120,1,0,0,113.109,23.0192,0,'2020-12-25 20:18:39',NULL),(684089,2,658121,1,0,0,113.109,23.0192,0,'2020-12-25 20:19:02',NULL),(684090,2,658122,1,0,0,113.109,23.0192,0,'2020-12-25 20:19:07',NULL),(684091,2,658123,1,0,0,113.109,23.0192,0,'2020-12-25 20:19:10',NULL),(684092,2,658124,1,0,0,113.109,23.0192,0,'2020-12-25 20:19:17',NULL),(684093,2,658125,1,0,0,113.109,23.0192,0,'2020-12-25 20:19:50',NULL),(684094,2,658126,1,0,0,113.109,23.0192,0,'2020-12-25 20:19:53',NULL),(684095,2,658127,1,0,0,113.109,23.0192,0,'2020-12-25 20:19:55',NULL),(684096,2,658128,1,0,0,113.109,23.0192,0,'2020-12-25 20:19:56',NULL),(684097,2,658129,1,0,0,113.109,23.0192,0,'2020-12-25 20:20:09',NULL),(752002,1,752001,2,0,0,113.109,23.0192,0,'2020-12-29 01:32:35',NULL),(752003,3,752001,2,0,0,113.109,23.0192,0,'2020-12-29 01:32:35',NULL),(752005,1,752004,2,0,0,113.109,23.0192,0,'2020-12-29 01:32:39',NULL),(752006,3,752004,2,0,0,113.109,23.0192,0,'2020-12-29 01:32:39',NULL),(752008,1,752007,2,0,0,113.109,23.0192,0,'2020-12-29 01:32:45',NULL),(752010,1,752009,2,0,0,113.109,23.0192,0,'2020-12-29 01:32:52',NULL),(752012,1,752011,2,0,0,113.109,23.0192,0,'2020-12-29 01:33:00',NULL),(752014,1,752013,2,0,0,113.109,23.0192,0,'2020-12-29 01:33:02',NULL),(752016,1,752015,2,0,0,113.109,23.0192,0,'2020-12-29 01:33:07',NULL),(752018,1,752017,2,0,0,113.109,23.0192,0,'2020-12-29 01:33:11',NULL),(752020,1,752019,2,0,0,113.109,23.0192,0,'2020-12-29 01:33:18',NULL),(826002,3,826001,2,0,0,113.109,23.0192,0,'2021-01-03 19:33:40',NULL),(826004,1,826003,2,0,0,113.109,23.0192,0,'2021-01-03 19:33:48',NULL),(828002,3,828001,2,0,0,113.109,23.0192,0,'2021-01-03 22:00:44',NULL),(830002,1,830001,2,0,0,113.109,23.0192,0,'2021-01-03 22:49:27',NULL),(954002,1,954001,2,0,0,113.109,23.0192,0,'2021-01-15 00:16:51',NULL),(956002,1,956001,2,0,0,113.109,23.0192,0,'2021-01-15 00:19:05',NULL),(956004,3,956003,2,0,0,113.109,23.0192,0,'2021-01-15 00:20:41',NULL),(956006,1,956005,2,0,0,113.109,23.0192,0,'2021-01-15 00:21:33',NULL),(956008,1,956007,2,0,0,113.109,23.0192,0,'2021-01-15 00:22:46',NULL),(956010,3,956009,2,0,0,113.109,23.0192,0,'2021-01-15 00:23:07',NULL),(956012,1,956011,2,0,0,113.109,23.0192,0,'2021-01-15 00:23:22',NULL),(956013,3,956011,2,0,0,113.109,23.0192,0,'2021-01-15 00:23:22',NULL),(956015,1,956014,2,0,0,113.109,23.0192,0,'2021-01-15 00:23:23',NULL),(956017,1,956016,2,0,0,113.109,23.0192,0,'2021-01-15 00:23:25',NULL),(956019,1,956018,2,0,0,113.109,23.0192,0,'2021-01-15 00:23:56',NULL),(956021,1,956020,2,0,0,113.109,23.0192,0,'2021-01-15 00:24:24',NULL),(956022,3,956020,2,0,0,113.109,23.0192,0,'2021-01-15 00:24:24',NULL),(956024,1,956023,2,0,0,113.109,23.0192,0,'2021-01-15 00:25:55',NULL),(956026,1,956025,2,0,0,113.109,23.0192,0,'2021-01-15 00:25:58',NULL),(956028,1,956027,2,0,0,113.109,23.0192,0,'2021-01-15 00:29:24',NULL),(956030,1,956029,2,0,0,113.109,23.0192,0,'2021-01-15 00:30:59',NULL),(956032,1,956031,2,0,0,113.109,23.0192,0,'2021-01-15 00:31:22',NULL),(956034,1,956033,2,0,0,113.109,23.0192,0,'2021-01-15 00:34:05',NULL),(956036,1,956035,2,0,0,113.109,23.0192,0,'2021-01-15 00:34:46',NULL),(956038,1,956037,2,0,0,113.109,23.0192,0,'2021-01-15 00:35:01',NULL),(956040,1,956039,2,0,0,113.109,23.0192,0,'2021-01-15 00:35:04',NULL),(956042,1,956041,2,0,0,113.109,23.0192,0,'2021-01-15 00:35:07',NULL),(956044,1,956043,2,0,0,113.109,23.0192,0,'2021-01-15 00:36:11',NULL),(956046,1,956045,2,0,0,113.109,23.0192,0,'2021-01-15 00:36:17',NULL),(956048,1,956047,2,0,0,113.109,23.0192,0,'2021-01-15 00:36:40',NULL),(956050,1,956049,2,0,0,113.109,23.0192,0,'2021-01-15 00:37:28',NULL),(956052,1,956051,2,0,0,113.109,23.0192,0,'2021-01-15 00:37:33',NULL),(956054,1,956053,2,0,0,113.109,23.0192,0,'2021-01-15 00:37:40',NULL),(956056,1,956055,2,0,0,113.109,23.0192,0,'2021-01-15 00:37:42',NULL),(956058,1,956057,2,0,0,113.109,23.0192,0,'2021-01-15 00:38:03',NULL),(958002,3,958001,2,0,0,113.109,23.0192,0,'2021-01-15 00:41:35',NULL),(958004,1,958003,2,0,0,113.109,23.0192,0,'2021-01-15 00:42:50',NULL),(958006,1,958005,2,0,0,113.109,23.0192,0,'2021-01-15 00:44:09',NULL),(958008,1,958007,2,0,0,113.109,23.0192,0,'2021-01-15 00:44:11',NULL),(958010,1,958009,2,0,0,113.109,23.0192,0,'2021-01-15 00:45:54',NULL),(958012,3,958011,2,0,0,113.109,23.0192,0,'2021-01-15 00:48:15',NULL),(958014,1,958013,2,0,0,113.109,23.0192,0,'2021-01-15 00:48:19',NULL),(958016,1,958015,2,0,0,113.109,23.0192,0,'2021-01-15 00:48:50',NULL),(958018,1,958017,2,0,0,113.109,23.0192,0,'2021-01-15 00:50:15',NULL),(958020,3,958019,2,0,0,113.109,23.0192,0,'2021-01-15 00:50:26',NULL),(958022,1,958021,2,0,0,113.109,23.0192,0,'2021-01-15 00:51:50',NULL),(958024,1,958023,2,0,0,113.109,23.0192,0,'2021-01-15 00:52:52',NULL),(958026,1,958025,2,0,0,113.109,23.0192,0,'2021-01-15 00:53:03',NULL),(958028,1,958027,2,0,0,113.109,23.0192,0,'2021-01-15 00:53:09',NULL),(958030,1,958029,2,0,0,113.109,23.0192,0,'2021-01-15 00:53:12',NULL),(958032,1,958031,2,0,0,113.109,23.0192,0,'2021-01-15 00:53:54',NULL),(958034,1,958033,2,0,0,113.109,23.0192,0,'2021-01-15 00:53:59',NULL),(958036,1,958035,2,0,0,113.109,23.0192,0,'2021-01-15 00:54:10',NULL),(958038,1,958037,2,0,0,113.109,23.0192,0,'2021-01-15 00:54:13',NULL),(958040,1,958039,2,0,0,113.109,23.0192,0,'2021-01-15 00:54:29',NULL),(958042,1,958041,2,0,0,113.109,23.0192,0,'2021-01-15 00:54:33',NULL),(958044,1,958043,2,0,0,113.109,23.0192,0,'2021-01-15 00:54:34',NULL),(958046,1,958045,2,0,0,113.109,23.0192,0,'2021-01-15 00:54:38',NULL),(958048,1,958047,2,0,0,113.109,23.0192,0,'2021-01-15 00:54:40',NULL),(958051,1,958050,2,0,0,113.109,23.0192,0,'2021-01-15 00:55:56',NULL),(958053,1,958052,2,0,0,113.109,23.0192,0,'2021-01-15 00:59:04',NULL),(958055,1,958054,2,0,0,113.109,23.0192,0,'2021-01-15 00:59:06',NULL),(958057,1,958056,2,0,0,113.109,23.0192,0,'2021-01-15 01:00:35',NULL),(958059,1,958058,2,0,0,113.109,23.0192,0,'2021-01-15 01:00:38',NULL),(958061,3,958060,2,0,0,113.109,23.0192,0,'2021-01-15 01:02:01',NULL),(958063,1,958062,2,0,0,113.109,23.0192,0,'2021-01-15 01:02:05',NULL),(962002,1,962001,2,0,0,113.109,23.0192,0,'2021-01-15 01:13:35',NULL),(962004,1,962003,2,0,0,113.109,23.0192,0,'2021-01-15 01:16:52',NULL),(962006,1,962005,2,0,0,113.109,23.0192,0,'2021-01-15 01:18:25',NULL),(962008,1,962007,2,0,0,113.109,23.0192,0,'2021-01-15 01:18:37',NULL),(962010,3,962009,2,0,0,113.109,23.0192,0,'2021-01-15 01:19:49',NULL),(962012,1,962011,2,0,0,113.109,23.0192,0,'2021-01-15 01:21:08',NULL),(964002,3,964001,2,0,0,113.109,23.0192,0,'2021-01-15 01:25:46',NULL),(964004,3,964003,2,0,0,113.109,23.0192,0,'2021-01-15 01:25:52',NULL),(964006,1,964005,2,0,0,113.109,23.0192,0,'2021-01-15 01:26:33',NULL),(964008,1,964007,2,0,0,113.109,23.0192,0,'2021-01-15 01:28:35',NULL),(964010,1,964009,2,0,0,113.109,23.0192,0,'2021-01-15 01:29:38',NULL),(964011,3,964009,2,0,0,113.109,23.0192,0,'2021-01-15 01:29:38',NULL),(964013,1,964012,2,0,0,113.109,23.0192,0,'2021-01-15 01:29:42',NULL),(964015,1,964014,2,0,0,113.109,23.0192,0,'2021-01-15 01:29:45',NULL),(964017,1,964016,2,0,0,113.109,23.0192,0,'2021-01-15 01:30:04',NULL),(966003,1,966002,2,0,0,113.109,23.0192,0,'2021-01-15 01:35:40',NULL),(966005,1,966004,2,0,0,113.109,23.0192,0,'2021-01-15 01:35:44',NULL),(966007,1,966006,2,0,0,113.109,23.0192,0,'2021-01-15 01:35:52',NULL),(968002,1,968001,2,0,0,113.109,23.0192,0,'2021-01-15 01:37:12',NULL),(968004,1,968003,2,0,0,113.109,23.0192,0,'2021-01-15 01:37:17',NULL),(968005,3,968003,2,0,0,113.109,23.0192,0,'2021-01-15 01:37:17',NULL),(968007,1,968006,2,0,0,113.109,23.0192,0,'2021-01-15 01:37:37',NULL),(968009,1,968008,2,0,0,113.109,23.0192,0,'2021-01-15 01:37:42',NULL),(968012,1,968011,2,0,0,113.109,23.0192,0,'2021-01-15 01:41:30',NULL),(968014,1,968013,2,0,0,113.109,23.0192,0,'2021-01-15 01:41:53',NULL),(968016,1,968015,2,0,0,113.109,23.0192,0,'2021-01-15 01:41:56',NULL),(968018,1,968017,2,0,0,113.109,23.0192,0,'2021-01-15 01:42:24',NULL),(978014,3,978013,2,0,0,113.109,23.0192,0,'2021-01-17 20:16:17',NULL),(978027,3,978026,2,0,0,113.109,23.0192,0,'2021-01-17 20:27:17',NULL),(978029,3,978028,2,0,0,113.109,23.0192,0,'2021-01-17 20:27:27',NULL),(978035,1,978034,2,0,0,113.109,23.0192,0,'2021-01-17 20:31:29',NULL),(978037,1,978036,2,0,0,113.109,23.0192,0,'2021-01-17 20:31:33',NULL),(978039,1,978038,2,0,0,113.109,23.0192,0,'2021-01-17 20:31:36',NULL),(978048,1,978047,2,0,0,113.109,23.0192,0,'2021-01-17 20:38:03',NULL),(978053,1,978052,2,0,0,113.109,23.0192,0,'2021-01-17 20:41:10',NULL),(978069,1,978068,2,0,0,113.109,23.0192,0,'2021-01-17 20:55:21',NULL),(978071,1,978070,2,0,0,113.109,23.0192,0,'2021-01-17 20:55:30',NULL),(978073,1,978072,2,0,0,113.109,23.0192,0,'2021-01-17 20:55:34',NULL),(980002,1,980001,2,0,0,113.109,23.0192,0,'2021-01-19 01:32:40',NULL),(980004,1,980003,2,0,0,113.109,23.0192,0,'2021-01-19 01:34:19',NULL),(980006,1,980005,2,0,0,113.109,23.0192,0,'2021-01-19 01:34:40',NULL),(980008,1,980007,2,0,0,113.109,23.0192,0,'2021-01-19 01:46:28',NULL),(980009,3,980007,2,0,0,113.109,23.0192,0,'2021-01-19 01:46:28',NULL),(980012,1,980011,2,0,0,113.109,23.0192,0,'2021-01-19 01:52:27',NULL),(980014,1,980013,2,0,0,113.109,23.0192,0,'2021-01-19 01:56:57',NULL),(980016,1,980015,2,0,0,113.109,23.0192,0,'2021-01-19 01:58:10',NULL),(980018,1,980017,2,0,0,113.109,23.0192,0,'2021-01-19 01:58:12',NULL),(980020,1,980019,2,0,0,113.109,23.0192,0,'2021-01-19 01:58:18',NULL),(980022,1,980021,2,0,0,113.109,23.0192,0,'2021-01-19 01:58:23',NULL),(980025,1,980024,2,0,0,113.109,23.0192,0,'2021-01-19 02:02:53',NULL),(980027,1,980026,2,0,0,113.109,23.0192,0,'2021-01-19 02:04:19',NULL),(980029,1,980028,2,0,0,113.109,23.0192,0,'2021-01-19 02:08:37',NULL),(980031,1,980030,2,0,0,113.109,23.0192,0,'2021-01-19 02:08:40',NULL),(980033,1,980032,2,0,0,113.109,23.0192,0,'2021-01-19 02:08:47',NULL),(980089,1,980088,2,0,0,113.109,23.0192,0,'2021-01-19 15:36:25',NULL),(980091,1,980090,2,0,0,113.109,23.0192,0,'2021-01-19 15:40:04',NULL),(980099,1,980098,2,0,0,113.109,23.0192,0,'2021-01-19 17:11:30',NULL),(980101,1,980100,2,0,0,113.109,23.0192,0,'2021-01-19 17:17:35',NULL),(980104,3,980103,2,0,0,113.109,23.0192,0,'2021-01-19 17:18:43',NULL),(980106,1,980105,2,0,0,113.109,23.0192,0,'2021-01-19 17:24:30',NULL),(980108,1,980107,2,0,0,113.109,23.0192,0,'2021-01-19 17:25:31',NULL),(980110,1,980109,2,0,0,113.109,23.0192,0,'2021-01-19 17:30:58',NULL),(982002,3,982001,2,0,0,113.109,23.0192,0,'2021-01-19 21:34:25',NULL),(982004,1,982003,2,0,0,113.109,23.0192,0,'2021-01-19 21:36:50',NULL),(982007,1,982006,2,0,0,113.109,23.0192,0,'2021-01-19 21:41:11',NULL),(982009,1,982008,2,0,0,113.109,23.0192,0,'2021-01-19 21:41:25',NULL),(982012,1,982011,2,0,0,113.109,23.0192,0,'2021-01-19 22:05:08',NULL),(982014,1,982013,2,0,0,113.109,23.0192,0,'2021-01-19 22:05:16',NULL),(982017,1,982016,2,0,0,113.109,23.0192,0,'2021-01-19 22:20:38',NULL),(982019,1,982018,2,0,0,113.109,23.0192,0,'2021-01-19 22:24:22',NULL),(982022,1,982021,2,0,0,113.109,23.0192,0,'2021-01-19 22:28:41',NULL),(984002,1,984001,2,0,0,113.109,23.0192,0,'2021-01-19 22:47:12',NULL),(984006,1,984005,2,0,0,113.109,23.0192,0,'2021-01-19 23:12:16',NULL),(984008,1,984007,2,0,0,113.109,23.0192,0,'2021-01-19 23:12:25',NULL),(984012,1,984011,2,0,0,113.109,23.0192,0,'2021-01-19 23:43:21',NULL),(984014,1,984013,2,0,0,113.109,23.0192,0,'2021-01-19 23:51:07',NULL),(984016,1,984015,2,0,0,113.109,23.0192,0,'2021-01-19 23:51:16',NULL),(984018,1,984017,2,0,0,113.109,23.0192,0,'2021-01-19 23:52:21',NULL);
/*!40000 ALTER TABLE `eg_rule_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_screen`
--

DROP TABLE IF EXISTS `eg_screen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_screen` (
  `id` bigint NOT NULL,
  `poleId` bigint NOT NULL,
  `groupId` bigint DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `volume` int DEFAULT NULL,
  `brightness` int DEFAULT NULL,
  `resolution` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `colorTa` int DEFAULT NULL,
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `isOn` tinyint DEFAULT NULL,
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `netmask` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gateway` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `productBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchaseBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchasedAt` timestamp NULL DEFAULT NULL,
  `supplier` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `model` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NULL DEFAULT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  `colorTemp` int NOT NULL,
  `colorTemperature` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_screen`
--

LOCK TABLES `eg_screen` WRITE;
/*!40000 ALTER TABLE `eg_screen` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_screen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_screen_content`
--

DROP TABLE IF EXISTS `eg_screen_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_screen_content` (
  `id` bigint NOT NULL,
  `planId` bigint DEFAULT NULL,
  `url` varchar(2083) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `startedAt` timestamp NULL DEFAULT NULL,
  `endedAt` timestamp NULL DEFAULT NULL,
  `isPlayed` tinyint DEFAULT '0',
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NULL DEFAULT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  `resolution` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `size` bigint DEFAULT NULL,
  `contentName` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `contentIndex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `screenId` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_screen_content`
--

LOCK TABLES `eg_screen_content` WRITE;
/*!40000 ALTER TABLE `eg_screen_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_screen_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_screen_log`
--

DROP TABLE IF EXISTS `eg_screen_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_screen_log` (
  `id` bigint NOT NULL,
  `screenId` bigint NOT NULL,
  `volumn` tinyint DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_screen_log`
--

LOCK TABLES `eg_screen_log` WRITE;
/*!40000 ALTER TABLE `eg_screen_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_screen_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_screen_plan`
--

DROP TABLE IF EXISTS `eg_screen_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_screen_plan` (
  `id` bigint NOT NULL,
  `screenId` bigint DEFAULT NULL,
  `planName` varchar(45) DEFAULT NULL,
  `cron` varchar(255) DEFAULT NULL,
  `jobName` varchar(255) DEFAULT NULL,
  `jobClassName` varchar(255) DEFAULT NULL,
  `enable` tinyint DEFAULT NULL,
  `startTime` timestamp NULL DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `createdAt` timestamp NULL DEFAULT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  `updatedAt` timestamp NULL DEFAULT NULL,
  `frequency` varchar(255) DEFAULT NULL,
  `jobDescription` varchar(255) DEFAULT NULL,
  `jobNumber` varchar(255) DEFAULT NULL,
  `contentId` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_screen_plan`
--

LOCK TABLES `eg_screen_plan` WRITE;
/*!40000 ALTER TABLE `eg_screen_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_screen_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_server`
--

DROP TABLE IF EXISTS `eg_server`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_server` (
  `id` bigint NOT NULL,
  `orgId` bigint NOT NULL,
  `groupId` bigint DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `isOn` tinyint DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `netmask` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gateway` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `productBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchaseBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchasedAt` timestamp NULL DEFAULT NULL,
  `supplier` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deleletedAt` timestamp NULL DEFAULT NULL,
  `deletedAt` datetime DEFAULT NULL,
  `model` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `poleId` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_server`
--

LOCK TABLES `eg_server` WRITE;
/*!40000 ALTER TABLE `eg_server` DISABLE KEYS */;
INSERT INTO `eg_server` VALUES (1,1,1,'server1','serv-01',0,NULL,'192.168.192.32','C4-00-AD-58-50-3D','255.255.255.0','192.168.192.1','1','1','2020-10-16 19:16:23','华全','2020-10-16 19:16:23','2020-10-16 19:16:23',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `eg_server` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_weather`
--

DROP TABLE IF EXISTS `eg_weather`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_weather` (
  `id` bigint NOT NULL,
  `poleId` bigint NOT NULL,
  `groupId` bigint DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `isOn` tinyint NOT NULL,
  `status` tinyint DEFAULT NULL,
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `port` int DEFAULT NULL,
  `mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `netmask` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gateway` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `productBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchaseBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchasedAt` timestamp NULL DEFAULT NULL,
  `supplier` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `model` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  `purchasedBatch` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_weather`
--

LOCK TABLES `eg_weather` WRITE;
/*!40000 ALTER TABLE `eg_weather` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_weather` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_weather_log`
--

DROP TABLE IF EXISTS `eg_weather_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_weather_log` (
  `id` bigint NOT NULL,
  `weatherId` bigint NOT NULL,
  `dn` int DEFAULT NULL,
  `dm` int DEFAULT NULL,
  `dx` int DEFAULT NULL,
  `sn` float DEFAULT NULL,
  `sm` float DEFAULT NULL,
  `sx` float DEFAULT NULL,
  `ta` float DEFAULT NULL,
  `ua` float DEFAULT NULL,
  `pa` float DEFAULT NULL,
  `rc` float DEFAULT NULL,
  `sr` float DEFAULT NULL,
  `uv` int DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `longitude` float DEFAULT NULL,
  `latitude` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_weather_log`
--

LOCK TABLES `eg_weather_log` WRITE;
/*!40000 ALTER TABLE `eg_weather_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_weather_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_wifi`
--

DROP TABLE IF EXISTS `eg_wifi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_wifi` (
  `id` bigint NOT NULL,
  `poleId` bigint NOT NULL,
  `groupId` bigint DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `isOn` tinyint NOT NULL,
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `netmask` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gateway` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `productBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchaseBatch` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `purchasedAt` timestamp NULL DEFAULT NULL,
  `supplier` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `model` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_wifi`
--

LOCK TABLES `eg_wifi` WRITE;
/*!40000 ALTER TABLE `eg_wifi` DISABLE KEYS */;
INSERT INTO `eg_wifi` VALUES (1,1,1,'wifi1','wf-01',1,1,'192.168.1.8','30-0D-9E-75-79-5C','255.255.255.0','192.168.1.1','1','1','2020-12-15 07:06:20','华全','RG-WS6108','2020-12-15 07:06:20','2020-12-15 07:06:20','2020-12-15 07:06:20',NULL,NULL);
/*!40000 ALTER TABLE `eg_wifi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_wifi_ap`
--

DROP TABLE IF EXISTS `eg_wifi_ap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_wifi_ap` (
  `id` bigint NOT NULL,
  `poleId` bigint DEFAULT NULL,
  `groupId` bigint DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `isOn` tinyint NOT NULL,
  `status` tinyint DEFAULT NULL,
  `ip` varchar(15) DEFAULT NULL,
  `mac` varchar(20) DEFAULT NULL,
  `netmask` varchar(15) DEFAULT NULL,
  `gateway` varchar(15) DEFAULT NULL,
  `productBatch` varchar(20) DEFAULT NULL,
  `purchaseBatch` varchar(20) DEFAULT NULL,
  `purchasedAt` timestamp NULL DEFAULT NULL,
  `supplier` varchar(20) DEFAULT NULL,
  `model` varchar(20) NOT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_wifi_ap`
--

LOCK TABLES `eg_wifi_ap` WRITE;
/*!40000 ALTER TABLE `eg_wifi_ap` DISABLE KEYS */;
INSERT INTO `eg_wifi_ap` VALUES (1,1,1,'wifi-ap1','Wifi-AP-01',1,1,'192.168.1.19','80-05-88-BE-8F-DF','255.255.255.0','192.168.1.1','1','1','2021-03-14 04:12:12','华全','RG-AP630','2021-03-14 04:12:12','2021-03-14 04:12:12',NULL);
/*!40000 ALTER TABLE `eg_wifi_ap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eg_wifi_log`
--

DROP TABLE IF EXISTS `eg_wifi_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eg_wifi_log` (
  `id` bigint NOT NULL,
  `apId` bigint NOT NULL,
  `ssid` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `userMac` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `userIp` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `clientType` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `up` int DEFAULT NULL,
  `down` int DEFAULT NULL,
  `totalUp` bigint DEFAULT NULL,
  `totalDown` bigint DEFAULT NULL,
  `login` timestamp NULL DEFAULT NULL,
  `logout` timestamp NULL DEFAULT NULL,
  `offType` int DEFAULT NULL,
  `latitude` float DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eg_wifi_log`
--

LOCK TABLES `eg_wifi_log` WRITE;
/*!40000 ALTER TABLE `eg_wifi_log` DISABLE KEYS */;
INSERT INTO `eg_wifi_log` VALUES (1328001,1,'Eweb_795B1','E6-9A-70-9E-6C-61','192.168.1.24','','',16,22,2223398655,134131624,'2021-03-21 17:05:30',NULL,1,23.0192,113.109,'2021-03-19 01:39:31'),(1332001,1,'Eweb_795B1','32-66-17-42-74-C0','192.168.1.28','','',0,0,2606,2172,'2021-03-21 19:54:00',NULL,1,23.0192,113.109,'2021-03-21 19:56:58'),(1332003,1,'Eweb_795B1','88-E9-FE-55-D7-78','192.168.1.13','','',1968,1009,956149646,443234107,'2021-03-21 19:56:28',NULL,1,23.0192,113.109,'2021-03-21 19:59:24');
/*!40000 ALTER TABLE `eg_wifi_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_area`
--

DROP TABLE IF EXISTS `tb_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_area` (
  `id` bigint NOT NULL,
  `fatherId` bigint DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `longitude` float DEFAULT NULL,
  `latitude` float DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_area`
--

LOCK TABLES `tb_area` WRITE;
/*!40000 ALTER TABLE `tb_area` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_dictionary`
--

DROP TABLE IF EXISTS `tb_dictionary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_dictionary` (
  `id` bigint NOT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `value` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_dictionary`
--

LOCK TABLES `tb_dictionary` WRITE;
/*!40000 ALTER TABLE `tb_dictionary` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_dictionary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_failure`
--

DROP TABLE IF EXISTS `tb_failure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_failure` (
  `id` bigint NOT NULL,
  `deviceId` bigint NOT NULL,
  `deviceType` tinyint NOT NULL,
  `reporterId` bigint DEFAULT NULL,
  `type` tinyint DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  `latitude` float DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_failure`
--

LOCK TABLES `tb_failure` WRITE;
/*!40000 ALTER TABLE `tb_failure` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_failure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_failure_log`
--

DROP TABLE IF EXISTS `tb_failure_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_failure_log` (
  `id` bigint NOT NULL,
  `failureId` int NOT NULL,
  `userId` bigint DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_failure_log`
--

LOCK TABLES `tb_failure_log` WRITE;
/*!40000 ALTER TABLE `tb_failure_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_failure_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_group`
--

DROP TABLE IF EXISTS `tb_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_group` (
  `id` bigint NOT NULL,
  `fatherId` bigint DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_group`
--

LOCK TABLES `tb_group` WRITE;
/*!40000 ALTER TABLE `tb_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_log`
--

DROP TABLE IF EXISTS `tb_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_log` (
  `id` bigint NOT NULL,
  `title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` tinyint DEFAULT NULL,
  `level` tinyint DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_log`
--

LOCK TABLES `tb_log` WRITE;
/*!40000 ALTER TABLE `tb_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_media_file`
--

DROP TABLE IF EXISTS `tb_media_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_media_file` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sourceId` int DEFAULT NULL,
  `source` tinyint DEFAULT NULL,
  `name` varchar(2083) DEFAULT NULL,
  `createdAt` timestamp(3) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2631 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_media_file`
--

LOCK TABLES `tb_media_file` WRITE;
/*!40000 ALTER TABLE `tb_media_file` DISABLE KEYS */;
INSERT INTO `tb_media_file` VALUES (1,1208001,7,'FFFC40CEF6C65768FC7FA0E1C924C589.txt','2021-03-03 22:45:19.948'),(2,1208001,8,'6DC790292C3953AA94E82B90DBDFED32.jpg','2021-03-03 22:45:20.009'),(3,1208002,7,'8D92E057A4EB47E944074A7BDDD2F447.jpg','2021-03-03 22:45:23.300'),(4,1208002,8,'18C02D6D14539CAF6D4B2903EBC8BE01.jpg','2021-03-03 22:45:23.330'),(5,1208003,7,'A8A74881BD382F7CCEB3E27BA4F7F2E3.jpg','2021-03-03 22:45:43.045'),(6,1208003,8,'DBD7E6DC095F4AE9E55FA04A0C662C9F.jpg','2021-03-03 22:45:43.066'),(7,1208004,7,'C08C2A6ABBF2B2B1DD5ADD02C4CA6F32.jpg','2021-03-03 22:45:45.462'),(8,1208004,8,'4D815F695DEAB54CEA4A4A12EF42542B.jpg','2021-03-03 22:45:45.485'),(9,1208005,7,'1CE1F814BBBDBCA9E70BBA4AA76D0CCE.jpg','2021-03-03 22:45:48.096'),(10,1208005,8,'579EF15831431EE529F96F16AA0CB677.jpg','2021-03-03 22:45:48.132'),(11,1208006,7,'B8E1BE1DC55FEE14A5CB28EFB0EF8263.jpg','2021-03-03 22:45:49.957'),(12,1208006,8,'B386FCAA3BA0B751A29466ED8B1F1EB9.jpg','2021-03-03 22:45:49.992'),(13,1208007,7,'842483C541F0FCAA1EB5E9EB466DD72E.jpg','2021-03-03 22:45:50.632'),(14,1208007,8,'B8F131E87A12BB4D350E8E4EE360E7DA.jpg','2021-03-03 22:45:50.653'),(15,1208008,7,'BB9931DFADCE9AD03B5A8C6009996BA1.jpg','2021-03-03 22:45:52.897'),(16,1208008,8,'7FE9B6F08A84AF21E012E446BB9C00A5.jpg','2021-03-03 22:45:52.922'),(17,1208009,7,'4D617754AE5E0FD4B6606B9D6EF8ECDB.jpg','2021-03-03 22:45:53.429'),(18,1208009,8,'A490093D239B0D3601400732CC2FB2CE.jpg','2021-03-03 22:45:53.449'),(19,1208010,7,'6D8B270A19DD4798DB6AADE77FAFB25F.jpg','2021-03-03 22:45:59.255'),(20,1208010,8,'7B0FFB78CC66274D0D0133BF5A08460B.jpg','2021-03-03 22:45:59.276'),(21,1208011,7,'9C6AB8E513C2CB700D872D81D696F84D.jpg','2021-03-03 22:46:01.430'),(22,1208011,8,'3BAC3E767F74F3F45ED48E755C672A7A.jpg','2021-03-03 22:46:01.452'),(23,1208012,7,'8D20D1F5E16BB8AB2C7034E2AADE600E.jpg','2021-03-03 22:46:06.776'),(24,1208012,8,'584B242CDADF5DE785A81775C8B8B445.jpg','2021-03-03 22:46:06.794'),(25,1208013,7,'226F31FB38B4E4AD04CD0A2D22EC18FE.jpg','2021-03-03 22:46:09.743'),(26,1208013,8,'71A01F5382756B15914E9D59B198751B.jpg','2021-03-03 22:46:09.767'),(27,1208005,6,'3475D6FE874390DB8F1C40360F0A1B77.mp4','2021-03-03 22:46:09.806'),(28,1208014,7,'118D27EA70B2CF863EC7FDDF09C8E909.jpg','2021-03-03 22:46:12.540'),(29,1208014,8,'52800E702DD6B0E2B9883A4906F151E2.jpg','2021-03-03 22:46:12.569'),(30,1208006,6,'93330718FEBC122601250FCD3DD147A9.mp4','2021-03-03 22:46:15.939'),(31,1208007,6,'3FA706E29AB3D542AE0AA8DAC35DFF9F.mp4','2021-03-03 22:46:21.079'),(32,1208015,7,'68BBC7698EBA7770016151EA644DF1F8.jpg','2021-03-03 22:46:21.825'),(33,1208015,8,'4B869B6123A34A4538858D2E05C75225.jpg','2021-03-03 22:46:21.858'),(34,1208008,6,'C2B8340C1CAE09E117085725D9D13D3C.mp4','2021-03-03 22:46:26.213'),(35,1208009,6,'C2B8340C1CAE09E117085725D9D13D3C.mp4','2021-03-03 22:46:31.307'),(36,1208016,7,'AEECEEFDEB3868C3442550701E65931D.jpg','2021-03-03 22:46:32.295'),(37,1208016,8,'54910713999CC58AB307923062F35B75.jpg','2021-03-03 22:46:32.318'),(38,1208010,6,'C477583A568E46577A21027B4F74F0D2.mp4','2021-03-03 22:46:36.381'),(39,1208017,7,'35154A6052F234E0A7B834CAA104AA92.jpg','2021-03-03 22:46:37.807'),(40,1208017,8,'421A03881825113C77CC9A6E1BB3AC5D.jpg','2021-03-03 22:46:37.831'),(41,1208011,6,'1BCC977775CBE46256922D955A9FE840.mp4','2021-03-03 22:46:42.463'),(42,1208001,6,'8A5FE7FB0C1E415EFD81AE08B7504C88.mp4','2021-03-03 22:47:07.618'),(43,1208002,6,'A5697CB44F40CE9531EA766E5A3764AE.mp4','2021-03-03 22:47:13.705'),(44,1208018,7,'3762A92B82FE4492A49B08F6590F62BE.jpg','2021-03-03 22:47:17.980'),(45,1208018,8,'D16D9486D5124C156437BBC09002AE0D.jpg','2021-03-03 22:47:18.004'),(46,1208019,7,'60910996C07CD86DAB980EAD330E5C65.jpg','2021-03-03 22:47:19.518'),(47,1208019,8,'C0AC2E75D4838A6DF01AA6AA7CA0C6B0.jpg','2021-03-03 22:47:19.540'),(48,1208020,7,'478E28852E783365D833CC954FF3EFD9.jpg','2021-03-03 22:47:23.227'),(49,1208020,8,'D40A4690AE8878B69776E4BD394F22C1.jpg','2021-03-03 22:47:23.250'),(50,1208021,7,'C3BE020E6E7835CA061518D089D1ACB0.jpg','2021-03-03 22:47:24.399'),(51,1208021,8,'979D2C4ED90524C15E6ABDD9B7D7261C.jpg','2021-03-03 22:47:24.421'),(52,1208022,7,'76607056ED820E1FCF66737B9A8E1EE4.jpg','2021-03-03 22:47:25.772'),(53,1208022,8,'65348C7A67BA6052408E058E957B6D25.jpg','2021-03-03 22:47:25.793'),(54,1208023,7,'D6099CC4C32255178C8BC773B8E99050.jpg','2021-03-03 22:47:26.818'),(55,1208023,8,'192C8E1834F873054C6BA65FE21C6151.jpg','2021-03-03 22:47:26.841'),(56,1208024,7,'3E89A3AFDBF1DA970D679ED2620D17EF.jpg','2021-03-03 22:47:28.557'),(57,1208024,8,'0E209E3565259E6C2930823AA88A730C.jpg','2021-03-03 22:47:28.578'),(58,1208003,6,'4506A4E9B5FB5FB73C17728A2684FDE2.mp4','2021-03-03 22:47:28.855'),(59,1208025,7,'500F7E7CB682565D15066FC868944D5B.jpg','2021-03-03 22:47:30.352'),(60,1208025,8,'5C2BE1E4FBF54A4DA1F3E9211B9E6785.jpg','2021-03-03 22:47:30.375'),(61,1208026,7,'E279C78F4DDFE782C356EE00ED1BC69E.jpg','2021-03-03 22:47:32.305'),(62,1208026,8,'1CE0DF8F3EBCEAA2B4079E3942518BC8.jpg','2021-03-03 22:47:32.326'),(63,1208004,6,'4C5C56664A627CDE40AA39836DD136B6.mp4','2021-03-03 22:47:33.927'),(64,1208027,7,'13ECABB9A146BE21713168FBFFF845BB.jpg','2021-03-03 22:47:35.936'),(65,1208027,8,'B60CD43A784B924D3A798F4CCC6B838A.jpg','2021-03-03 22:47:35.962'),(66,1208028,7,'FE1A94DBC0A614B89784140BF71C836F.jpg','2021-03-03 22:47:39.037'),(67,1208028,8,'DB57A5E11C3D5FC8BDADE5E58936285A.jpg','2021-03-03 22:47:39.056'),(68,1208029,7,'115F7EF75EA599455046FDA9D15F5EFC.jpg','2021-03-03 22:47:44.158'),(69,1208029,8,'142595D5FC37C229CD219A61A9064E60.jpg','2021-03-03 22:47:44.180'),(70,1208030,7,'58BA8AB49E83A22095A70B41E6FCF0A7.jpg','2021-03-03 22:47:48.645'),(71,1208030,8,'1C4F4AFEB5315D24A25046CFAAE6822E.jpg','2021-03-03 22:47:48.671'),(72,1208031,7,'FAE8C00D062298F22BEA983697E45B73.jpg','2021-03-03 22:47:54.568'),(73,1208031,8,'24AD5C83F366B31CBB8E0ED5B7F3CF63.jpg','2021-03-03 22:47:54.592'),(74,1208032,7,'EEC7E3B8F465C9A4824384B24A51AED6.jpg','2021-03-03 22:47:56.411'),(75,1208032,8,'48EBB636E47BCBFB383DD3E8D74BC6BC.jpg','2021-03-03 22:47:56.435'),(76,1208024,6,'51B5BE6F463C49B0E650C91DEC8A2193.mp4','2021-03-03 22:48:11.153'),(77,1208025,6,'D8EC504B1E533587A2EECB12E6DCECFF.mp4','2021-03-03 22:48:16.266'),(78,1208033,7,'9C86BC007D53E280D5228A281952C12B.jpg','2021-03-03 22:48:19.215'),(79,1208033,8,'2683C5D62F6709EB6CA52648BF0CB79B.jpg','2021-03-03 22:48:19.239'),(80,1208034,7,'BF6517BA1996E87C342F73BD2439B13D.jpg','2021-03-03 22:48:19.682'),(81,1208034,8,'1078D7B8E73F2E8DC53C1D5CA114069A.jpg','2021-03-03 22:48:19.707'),(82,1208026,6,'9793D2697D008D7C90F6B6700E53FE2F.mp4','2021-03-03 22:48:21.345'),(83,1208035,7,'BEF4F2525BCEC39B0CDCCF41EF9F0A52.jpg','2021-03-03 22:48:22.554'),(84,1208035,8,'5D6A8526C88C85329C5DED5FAF776C74.jpg','2021-03-03 22:48:22.574'),(85,1208036,7,'A4520ABF906345990CA4F86FDF644A0A.jpg','2021-03-03 22:48:23.028'),(86,1208036,8,'8069A448EA74B273708136899AF70E1D.jpg','2021-03-03 22:48:23.049'),(87,1208037,7,'BB5C3908E424270ED6CEF8802DF796C5.jpg','2021-03-03 22:48:25.526'),(88,1208037,8,'2252918B59C3C7AB367DA2E71CBCB68F.jpg','2021-03-03 22:48:25.550'),(89,1208038,7,'FE733D0449B09648B84DA8400EB6C977.jpg','2021-03-03 22:48:25.914'),(90,1208038,8,'2C93AC58D2F0F8FE7D4A6572911AAB71.jpg','2021-03-03 22:48:25.935'),(91,1208027,6,'06C2EFDDC6691969051BBA14036D560B.mp4','2021-03-03 22:48:26.423'),(92,1208039,7,'A500B1B4E0B1AC89CF1AFF296D56FB6E.jpg','2021-03-03 22:48:28.221'),(93,1208039,8,'7A0B3B8CFCCA7A3AE109FAAB65E4AD3E.jpg','2021-03-03 22:48:28.241'),(94,1208040,7,'790EE41C966FFA70F2E36F00F4A8ACDB.jpg','2021-03-03 22:48:28.927'),(95,1208040,8,'C5A971E7FF548A26D2750F24AB90141F.jpg','2021-03-03 22:48:28.949'),(96,1208041,7,'5AA21A17F00E57CBE617965EE7602067.jpg','2021-03-03 22:48:30.546'),(97,1208041,8,'A6407471C46F7E2938BF4E93014E1653.jpg','2021-03-03 22:48:30.567'),(98,1208012,6,'9F99A9CA66A63FFE76B5422256C8ED11.mp4','2021-03-03 22:48:31.554'),(99,1208042,7,'AB93B027A3CB30A7CF41C0219DFF526C.jpg','2021-03-03 22:48:32.249'),(100,1208042,8,'9A50194F6C08FE0B61238DC88EADFE0B.jpg','2021-03-03 22:48:32.268'),(101,1208043,7,'BEBD42B6616A29DD1350A932B3EBDFB3.jpg','2021-03-03 22:48:33.258'),(102,1208043,8,'79090FB3A880484C5DF649F9AECF2459.jpg','2021-03-03 22:48:33.277'),(103,1208044,7,'5B2F13F2D2514A84A47557BBEE13A8EF.jpg','2021-03-03 22:48:35.525'),(104,1208044,8,'43CFAD85D327C2B4EFEC11682AC6ACFC.jpg','2021-03-03 22:48:35.546'),(105,1208028,6,'850D6683ACF19CCAE5A5C2108952C79A.mp4','2021-03-03 22:48:36.644'),(106,1208045,7,'25E1BCF6CF0929EEF6D0217679DCF62C.jpg','2021-03-03 22:48:38.705'),(107,1208045,8,'6EAF4A6B17F9DF11C73B2728834950F9.jpg','2021-03-03 22:48:38.725'),(108,1208013,6,'56B0934C3744C145B7FB776CFA5A43C2.mp4','2021-03-03 22:48:41.705'),(109,1208046,7,'40CBFFAB3D0D0469FBB683B39EDB263F.jpg','2021-03-03 22:48:42.130'),(110,1208046,8,'5139533DB7E9C5C1B5BC2374F57D45D8.jpg','2021-03-03 22:48:42.154'),(111,1208029,6,'15FD1765702A8F7985409846D68D8757.mp4','2021-03-03 22:48:46.779'),(112,1208014,6,'6F721CF57E1A9B2B38B4FF372B51A0C8.mp4','2021-03-03 22:48:52.877'),(113,1208030,6,'EB1B43D510E367FA06AFEA344A9F1F23.mp4','2021-03-03 22:48:57.978'),(114,1208047,7,'9FB10C38CE6D5FC34936A069227A6353.jpg','2021-03-03 22:49:02.561'),(115,1208047,8,'7EDF92C04ED703E1F43D4173276D51CC.jpg','2021-03-03 22:49:02.602'),(116,1208015,6,'CFEA449596C7A5DCC3C089942BB11260.mp4','2021-03-03 22:49:03.052'),(117,1208031,6,'88EDC75A02722528C56E36C9714E6F79.mp4','2021-03-03 22:49:08.120'),(118,1208032,6,'C6C8EA93D7E137043F4B148853F856DC.mp4','2021-03-03 22:49:13.226'),(119,1208016,6,'1A9EA50369B950EB34121E51328B5B74.mp4','2021-03-03 22:49:18.316'),(120,1208017,6,'D6B1C43B4B886E57C03AC26BB775BBD5.mp4','2021-03-03 22:49:23.385'),(121,1208048,7,'9765563059D795B31631D2855ABC7AD5.jpg','2021-03-03 22:49:41.661'),(122,1208048,8,'32F439208A55D78FE599DEDD2103A257.jpg','2021-03-03 22:49:41.685'),(123,1208049,7,'84643DD6AA7B21BADF226869B3F7650A.jpg','2021-03-03 22:49:47.043'),(124,1208049,8,'66039299C29B5B47660CDAA3E38FC754.jpg','2021-03-03 22:49:47.068'),(125,1208050,7,'37B27684344D805A8AE4CEBCAF022BD4.jpg','2021-03-03 22:49:57.422'),(126,1208050,8,'CBF4EC962C82D0D04B809BDCAC0EA814.jpg','2021-03-03 22:49:57.449'),(127,1208039,6,'61ADCB59FD48936E6E1AC1660EBFCCBD.mp4','2021-03-03 22:49:58.689'),(128,1208051,7,'2DC68CDB65331D4436D184365A236B9F.jpg','2021-03-03 22:50:00.190'),(129,1208051,8,'728E8CEDEDE64E51B2E9413B49E5D962.jpg','2021-03-03 22:50:00.211'),(130,1208052,7,'53A0EB2E88B2433CBE9BCF2FD0A1EABE.jpg','2021-03-03 22:50:01.350'),(131,1208052,8,'5BA7F4F414A53503D96DA6DFC02597E2.jpg','2021-03-03 22:50:01.387'),(132,1208040,6,'94E3C332B7F9B170A7224EE9EE81FB8B.mp4','2021-03-03 22:50:03.785'),(133,1208053,7,'7B9198D42EF1D2D6B443B71B96D691DB.jpg','2021-03-03 22:50:04.974'),(134,1208053,8,'6BD78B948CC83270486C69DBD937C3F4.jpg','2021-03-03 22:50:04.994'),(135,1208054,7,'27BDC64A823F8D45141433D0D613966B.jpg','2021-03-03 22:50:06.468'),(136,1208054,8,'69C8E840F486AA476699C6CEDBFE0BD5.jpg','2021-03-03 22:50:06.487'),(137,1208055,7,'814B71AB9C178EBE8EF4075AE92AE0A7.jpg','2021-03-03 22:50:08.324'),(138,1208055,8,'C51A24A05F8C11CED72E3D2A960A5917.jpg','2021-03-03 22:50:08.342'),(139,1208056,7,'6B66F04CFBCA8ABE7672967145473464.jpg','2021-03-03 22:50:08.838'),(140,1208018,6,'074A0C3188339E080DAF429771F256FD.mp4','2021-03-03 22:50:08.848'),(141,1208056,8,'1841FAE7FD21EAEC3740A613F72452DE.jpg','2021-03-03 22:50:08.861'),(142,1208057,7,'A4EEFC94C4567A67594027B1B16116F2.jpg','2021-03-03 22:50:11.502'),(143,1208057,8,'CC044EC05AF51083DE88B32B0FA4FBD3.jpg','2021-03-03 22:50:11.525'),(144,1208059,7,'46CB2085E1D6D1910E0C81D1FDFA138C.jpg','2021-03-03 22:50:13.664'),(145,1208059,8,'9D9671C66BB23DA6995DC346C996075D.jpg','2021-03-03 22:50:13.686'),(146,1208058,0,'4A9C63D6306A54B89BEFF8C9AEC9C5B4.mp4','2021-03-03 22:50:13.738'),(147,1208041,6,'05091A75C0CEF4D10995E208390CFA12.mp4','2021-03-03 22:50:13.968'),(148,1208042,6,'1373EEFD84641936D0EFA82A768D95CB.mp4','2021-03-03 22:50:19.052'),(149,1208043,6,'7AFBAA7A66FCD5FF48E612AF2D9DCC81.mp4','2021-03-03 22:50:24.120'),(150,1208060,7,'468F5483494AF477F452AAE78AFF8511.jpg','2021-03-03 22:50:27.125'),(151,1208060,8,'3D2D6D5216F54CABF2AC369CCF0183F6.jpg','2021-03-03 22:50:27.145'),(152,1208019,6,'21B3B39AA35B8BE337671619A9C1E1F1.mp4','2021-03-03 22:50:29.187'),(153,1208044,6,'E6B55D1253304F28B358A8E2DCC2E305.mp4','2021-03-03 22:50:34.254'),(154,1208045,6,'2F1726D1276B9C8D8ADB4B88FF2BFDC5.mp4','2021-03-03 22:50:40.348'),(155,1208020,6,'1225DEE5035E8B43A3B1F89EF61D17E3.mp4','2021-03-03 22:50:45.411'),(156,1208061,7,'ED29C39A20A2467BB9713A2E68BB1416.jpg','2021-03-03 22:50:46.540'),(157,1208061,8,'3A6E135911FAFDF286F356E35C693AE9.jpg','2021-03-03 22:50:46.560'),(158,1208046,6,'92D61EC4E95D83F58270585741A9DAF9.mp4','2021-03-03 22:50:50.476'),(159,1208021,6,'E4EE4040C0CCB97B05641D0914D71B02.mp4','2021-03-03 22:50:55.568'),(160,1208062,7,'B20A6EC8700E40784BCE9166BA168F4F.jpg','2021-03-03 22:50:55.913'),(161,1208062,8,'95C610E2D0E621F20C9C6A67735E57AA.jpg','2021-03-03 22:50:55.930'),(162,1208063,7,'5EC072CB860E071294B9F3BA80BDA59A.jpg','2021-03-03 22:50:57.498'),(163,1208063,8,'4D6C9E51064D51FD2BA3355DA25C8A0F.jpg','2021-03-03 22:50:57.520'),(164,1208064,7,'6A4ABFAB651885CFBE4F1B31DBBF7079.jpg','2021-03-03 22:50:58.697'),(165,1208064,8,'600BA74570321F30B201A85E950A6E8F.jpg','2021-03-03 22:50:58.716'),(166,1208065,7,'6013F8B36B89F74192DE9840ECC1BCA9.jpg','2021-03-03 22:51:00.462'),(167,1208065,8,'12A2063E851929EF21234B14FE55995C.jpg','2021-03-03 22:51:00.480'),(168,1208022,6,'F8EB41FA4D02E989EECC6BF8133D4E94.mp4','2021-03-03 22:51:00.638'),(169,1208066,7,'B10DF612423450694A53272C9F9D7DAF.jpg','2021-03-03 22:51:01.178'),(170,1208066,8,'9EE315132ACB4531BF18DB0935761FA3.jpg','2021-03-03 22:51:01.198'),(171,1208023,6,'5C7C2F4D81B047DA6936AC79D1390A59.mp4','2021-03-03 22:51:05.726'),(172,1208060,6,'9A2F9B8A9D86E51EE246772CA925035D.mp4','2021-03-03 23:14:28.572'),(173,1210001,7,'94D3A7F88CFAA015C78F82C97E8505ED.jpg','2021-03-03 23:14:28.571'),(174,1210001,8,'7811C5999F678ACA38A618C80D9A5396.jpg','2021-03-03 23:14:28.632'),(175,1210002,7,'D156A97AFCDBFB004BFE488CA4160A5F.jpg','2021-03-03 23:14:31.288'),(176,1210002,8,'1BC2BDB5B016625BF313E83A034D49D5.jpg','2021-03-03 23:14:31.310'),(177,1210003,7,'C7FCD8CDC5EA9398D6C3CD21545D3B59.jpg','2021-03-03 23:14:33.030'),(178,1210003,8,'1E7314408FB87A9FBC189DA009F0C421.jpg','2021-03-03 23:14:33.052'),(179,1208035,6,'CC7B3FBD32E48E77B8C16ABA459DF5C9.mp4','2021-03-03 23:14:33.806'),(180,1208036,6,'CC7B3FBD32E48E77B8C16ABA459DF5C9.mp4','2021-03-03 23:14:38.919'),(181,1210004,7,'04A3393DEB53EAE7D43ED35EF092E9B8.jpg','2021-03-03 23:14:39.436'),(182,1210004,8,'EF62322BEA679B51296AD2492B2A6C46.jpg','2021-03-03 23:14:39.462'),(183,1208037,6,'36114BCA508FF227E8CB6DF6B545B65D.mp4','2021-03-03 23:14:43.980'),(184,1208038,6,'36114BCA508FF227E8CB6DF6B545B65D.mp4','2021-03-03 23:14:49.048'),(185,1208061,6,'F9E4B76C8CA9D7B8463624F48A177BD9.mp4','2021-03-03 23:14:54.147'),(186,1208062,6,'9B01402364C7DE3005832FA098747E5F.mp4','2021-03-03 23:14:59.217'),(187,1210005,7,'848638BBDDD26675381DCA188AA3D47A.jpg','2021-03-03 23:14:59.970'),(188,1210005,8,'449749772D3BCE06803AC9625B5D24AD.jpg','2021-03-03 23:14:59.991'),(189,1210006,7,'FB03DAC62FB32EA4905E9BDD891D17DB.jpg','2021-03-03 23:15:00.597'),(190,1210006,8,'86D95B137D52826F6AFA3007B9E17D8B.jpg','2021-03-03 23:15:00.616'),(191,1210007,7,'DB110C1D2D8CC8057210D057B4506FB3.jpg','2021-03-03 23:15:01.288'),(192,1210007,8,'3AC3103703AD45674E635A6B4904E366.jpg','2021-03-03 23:15:01.318'),(193,1208063,6,'87095D8E2C22ADF965D06BDD8AFFCA9E.mp4','2021-03-03 23:15:04.383'),(194,1210008,7,'91B6351C10B5BEA57CF4875D36E180F0.jpg','2021-03-03 23:15:05.167'),(195,1210008,8,'18BC2986AD2EDA99E8A87FE35134A9A9.jpg','2021-03-03 23:15:05.190'),(196,1210009,7,'E3BCF9E222B1218CB03C41C41B4EC37A.jpg','2021-03-03 23:15:05.716'),(197,1210009,8,'2866867CB27CD1A4659770CE4E092B06.jpg','2021-03-03 23:15:05.739'),(198,1210010,7,'BEAA33E110CC7E47672C220677254C58.jpg','2021-03-03 23:15:07.733'),(199,1210010,8,'FAF8AD071FDD7C02BE28A7A006DB6EA8.jpg','2021-03-03 23:15:07.751'),(200,1210011,7,'ADC1927B1D6BE6F93A22AC7C131E4A8C.jpg','2021-03-03 23:15:08.702'),(201,1210011,8,'F27529CB6BCEAA32EC3C0DE21EC7A2C5.jpg','2021-03-03 23:15:08.722'),(202,1208064,6,'36FC3E810764A285CE45D6D88B76CE16.mp4','2021-03-03 23:15:09.469'),(203,1210012,7,'59C29B4F4DF8ABB7A5861A7203936665.jpg','2021-03-03 23:15:10.975'),(204,1210012,8,'20852EBF2E5128710D0BC77AC3E05117.jpg','2021-03-03 23:15:10.995'),(205,1210013,7,'124EB280F6526B6691BAC37119353B62.jpg','2021-03-03 23:15:11.491'),(206,1210013,8,'F9283BFCB483F7CCA5194191F6239D7C.jpg','2021-03-03 23:15:11.513'),(207,1210014,7,'3FE4049892FDA00F2FC54483E419AF34.jpg','2021-03-03 23:15:13.337'),(208,1210014,8,'0E3FB75021E72A0B8E76A240D67FB8BC.jpg','2021-03-03 23:15:13.517'),(209,1210015,7,'27E7D0D684D81AED14CF0DBF904E544C.jpg','2021-03-03 23:15:14.498'),(210,1210015,8,'E7A0636759856F7E2A19473F8F2A8CBD.jpg','2021-03-03 23:15:14.519'),(211,1208065,6,'E86AB110439FE39AE28A4ADE226CDEB2.mp4','2021-03-03 23:15:14.562'),(212,1210016,7,'52C89632DA4CD2B0764C0AD3EB8D8897.jpg','2021-03-03 23:15:15.718'),(213,1210016,8,'5A9D250648D6D36A690502C007326474.jpg','2021-03-03 23:15:15.737'),(214,1208066,6,'E86AB110439FE39AE28A4ADE226CDEB2.mp4','2021-03-03 23:15:19.651'),(215,1210017,7,'668B3D6D32B8C90C3A836AE381FC32A0.jpg','2021-03-03 23:15:20.635'),(216,1210017,8,'835FFECF278CDD10923AB18DFBD482A9.jpg','2021-03-03 23:15:20.656'),(217,1210001,6,'30C8E724A7B36D8580B2300E13B51A1F.mp4','2021-03-03 23:15:25.777'),(218,1210002,6,'E1A5EED02B0FE530566F52E2CFB619D2.mp4','2021-03-03 23:15:30.845'),(219,1210003,6,'DB8C289EBB927F781EF74CF3B2C3BD64.mp4','2021-03-03 23:15:35.913'),(220,1210018,7,'F3FE27BE5033E7490ED9D7CA7632F2BA.jpg','2021-03-03 23:15:36.236'),(221,1210018,8,'1668F5E9E6BEE9B556D1C86689C6574A.jpg','2021-03-03 23:15:36.257'),(222,1210004,6,'CCF8EDC592F03FEB0FC3F2D7965C0B53.mp4','2021-03-03 23:15:41.008'),(223,1210019,7,'2A622FB669AAED937B1EA5AEB9479D0C.jpg','2021-03-03 23:15:53.424'),(224,1210019,8,'923AB3E9F51BCCAEDC1F166C34D97B06.jpg','2021-03-03 23:15:53.445'),(225,1210020,7,'DF6458CF1C7BDD613DB233F7F248076E.jpg','2021-03-03 23:16:11.743'),(226,1210020,8,'A35E0842CF54D9670A8C8A607F21A841.jpg','2021-03-03 23:16:11.763'),(227,1210021,7,'0A030D4AD9FA8EF144F26784D968A926.jpg','2021-03-03 23:16:38.385'),(228,1210021,8,'7F3FFF34366159F5C77E7C455E9DB4D4.jpg','2021-03-03 23:16:38.406'),(229,1210022,7,'BC9699E7CDEF040A68C7158B89582FF8.jpg','2021-03-03 23:16:38.951'),(230,1210022,8,'EEE9CF0031BC169D0EE5585E3A5D6595.jpg','2021-03-03 23:16:38.969'),(231,1210023,7,'48D2DAB9AB8D433ADD0046E13FEC88DD.jpg','2021-03-03 23:16:42.217'),(232,1210023,8,'98853D264E03628153B1BDC60A2891EA.jpg','2021-03-03 23:16:42.236'),(233,1210024,7,'E02DF15DA7D2728B6ABEFF994AEAB028.jpg','2021-03-03 23:16:42.681'),(234,1210024,8,'E52BEF6AF43EF9A750C4AE3D7F4AC4AB.jpg','2021-03-03 23:16:42.699'),(235,1210025,7,'A991643F9C5C301FB57B3580071F2E1E.jpg','2021-03-03 23:16:44.702'),(236,1210025,8,'4ADCFE930AA442EAB7350FD97D2B29BE.jpg','2021-03-03 23:16:44.721'),(237,1210026,7,'740632DDE896AF291D4AC41CCD1032E3.jpg','2021-03-03 23:16:46.958'),(238,1210026,8,'AF16183C76892569A74FEE2D0EE3EBE1.jpg','2021-03-03 23:16:47.005'),(239,1210027,7,'0BA1C6BC2AE388A84211138803DB2BE6.jpg','2021-03-03 23:16:47.557'),(240,1210027,8,'E1E31C8C445CFC524FF87475FC8B8C94.jpg','2021-03-03 23:16:47.578'),(241,1210028,7,'62D248697660B194AD56405DE55DDB8E.jpg','2021-03-03 23:16:50.137'),(242,1210028,8,'6BCA86A7BA25BC787BFAD22CA29C9582.jpg','2021-03-03 23:16:50.156'),(243,1210029,7,'692FDEE2311360DC92F95B643AFFD460.jpg','2021-03-03 23:16:51.016'),(244,1210029,8,'4C050030BE0767C9EA739BB4D3E03FDC.jpg','2021-03-03 23:16:51.035'),(245,1210030,7,'384278276C07053AA7955ACB793AAB39.jpg','2021-03-03 23:16:53.416'),(246,1210030,8,'89DEF210C8DAD67ED32D7EA55AF77836.jpg','2021-03-03 23:16:53.435'),(247,1210031,7,'C761E585FCA0F1FE4FC820C492EF6426.jpg','2021-03-03 23:16:54.420'),(248,1210031,8,'CB5D9DB0114F3E65C931B58EF09DF0AC.jpg','2021-03-03 23:16:54.441'),(249,1210032,7,'EB316343C3D47639041F57E99C5C8A9E.jpg','2021-03-03 23:16:56.675'),(250,1210032,8,'DB68CC7C8D2CD4CF8EDB5496406ECE9A.jpg','2021-03-03 23:16:56.693'),(251,1210033,7,'B8D0999434731556F6354832C3DBEC52.jpg','2021-03-03 23:16:57.569'),(252,1210033,8,'6DA1C97676BC357355E1AC1B2E33B5E7.jpg','2021-03-03 23:16:57.589'),(253,1210034,7,'586B077B8F5C31677767B48D9DD9879C.jpg','2021-03-03 23:16:58.834'),(254,1210034,8,'6B4D2311DE66B26D5696581178E61018.jpg','2021-03-03 23:16:58.854'),(255,1210035,7,'3C9FF7DB9A08A031141330306DF9477B.jpg','2021-03-03 23:17:00.037'),(256,1210035,8,'A412992ABD0EC0A81073ACCD800F4D55.jpg','2021-03-03 23:17:00.056'),(257,1210036,7,'F70771E62A2CD38A61BB43EF152F98D8.jpg','2021-03-03 23:17:01.661'),(258,1210036,8,'C2D868DCDEBF6BAB59FB7221FEEA9B33.jpg','2021-03-03 23:17:01.679'),(259,1210037,7,'24256CB15BC4975CBAAFFE0B355B7FD4.jpg','2021-03-03 23:17:02.845'),(260,1210037,8,'01F9CD95ABCC9116C603D19E9DDC3ADB.jpg','2021-03-03 23:17:02.869'),(261,1210020,6,'27859A378700D2BEB3BD4C9FFC0301F3.mp4','2021-03-03 23:17:03.401'),(262,1210038,7,'0FD56BCC152E4FF3DF1E3A3411CE75F6.jpg','2021-03-03 23:17:04.856'),(263,1210038,8,'F742A871DC9FCCBBE431EAE031FB556B.jpg','2021-03-03 23:17:04.875'),(264,1210039,7,'F8443CD90FFBC8B8761AA92ED741D5E1.jpg','2021-03-03 23:17:06.633'),(265,1210039,8,'26EA7B6C54E7ECFF2A40BDA54DF74755.jpg','2021-03-03 23:17:06.709'),(266,1210040,7,'073D1737907FC299B3200C0599ECB610.jpg','2021-03-03 23:17:07.779'),(267,1210040,8,'4173EA62DF1840C65C6EECBCA69AC453.jpg','2021-03-03 23:17:07.798'),(268,1210041,7,'59632FE75AB09CEFF404E4ED35AD6534.jpg','2021-03-03 23:17:08.466'),(269,1210041,8,'96C5817CA9DA8E4235B074131EFD5044.jpg','2021-03-03 23:17:08.484'),(270,1210005,6,'921F9634105C6C850C4F609F4E97EBD2.mp4','2021-03-03 23:17:08.499'),(271,1210042,7,'23C264F2CC8535137E7C5ABD60DF1EE2.jpg','2021-03-03 23:17:11.224'),(272,1210042,8,'97B91C32CC0023FC2ADC9CE1B94F6640.jpg','2021-03-03 23:17:11.243'),(273,1210021,6,'30E4BCFAB50A7D962CB0AEE3E1EC6719.mp4','2021-03-03 23:17:13.561'),(274,1210022,6,'30E4BCFAB50A7D962CB0AEE3E1EC6719.mp4','2021-03-03 23:17:18.628'),(275,1210043,7,'0A98799B0A93C88A28F898EAF1BFA598.jpg','2021-03-03 23:17:20.193'),(276,1210043,8,'86848BDC0B809DF600AFAB1EBCABB4FB.jpg','2021-03-03 23:17:20.214'),(277,1210006,6,'CBFAC2E9F258CBB15F06E347155DC0B4.mp4','2021-03-03 23:17:23.697'),(278,1210023,6,'F1DBAE7E1D96B667AB0DB036A77FF750.mp4','2021-03-03 23:17:28.796'),(279,1210024,6,'F9CA612B292DE8EC173DBC50C464260C.mp4','2021-03-03 23:17:33.869'),(280,1210025,6,'2863F867EED608CC96A3FD172DE768B3.mp4','2021-03-03 23:17:38.934'),(281,1210044,7,'E265C6FD64F48F70A81270D22C68A2FE.jpg','2021-03-03 23:17:43.359'),(282,1210044,8,'8BBBB6B3F58F5AF185BB68D3312C2B01.jpg','2021-03-03 23:17:43.394'),(283,1210045,7,'BD06296E0B2AB6EC1BE4D3C894755D77.jpg','2021-03-03 23:17:43.878'),(284,1210045,8,'5C1307293B4D542F422D92DFE311283C.jpg','2021-03-03 23:17:43.900'),(285,1210007,6,'CBFAC2E9F258CBB15F06E347155DC0B4.mp4','2021-03-03 23:17:44.036'),(286,1210028,6,'B3281158E295E7B6C4DD66E4DB32F72B.mp4','2021-03-03 23:36:54.515'),(287,1210029,6,'092607BB5AE78255620812154FFD2A49.mp4','2021-03-03 23:36:59.732'),(288,1212001,7,'928239263C326689B7A0DB978012BC43.jpg','2021-03-03 23:37:04.059'),(289,1212001,8,'76E910F298867D27E80BF53EDC7F4B1A.jpg','2021-03-03 23:37:04.082'),(290,1210008,6,'35D1F10999E87F256E2D42FAA77F25CA.mp4','2021-03-03 23:37:04.812'),(291,1210030,6,'6BDF0D130040B61FDDFB8A0F3CFF5327.mp4','2021-03-03 23:37:09.883'),(292,1210031,6,'7708BF52070C33A4FC0C164385A68117.mp4','2021-03-03 23:37:14.977'),(293,1210009,6,'EDACDE2E75D86999D29DD5032AF45ADE.mp4','2021-03-03 23:37:20.076'),(294,1210032,6,'B16DB5D771889CEEFB67EE820B0D565D.mp4','2021-03-03 23:37:25.159'),(295,1210033,6,'2ED152F8CF2B186A4A80A143E4C4391B.mp4','2021-03-03 23:37:30.264'),(296,1210034,6,'8ABEE2AF35F162F03C1A23BA702F6D4B.mp4','2021-03-03 23:37:35.353'),(297,1212002,7,'829B050DCF6D192B5E2AF2CD8784E545.jpg','2021-03-03 23:37:35.852'),(298,1212002,8,'B9F01031E019C98773CF66FC46A00057.jpg','2021-03-03 23:37:35.874'),(299,1212003,7,'3E080C7BEE162B3E50FBB199F07BE742.jpg','2021-03-03 23:37:39.714'),(300,1212003,8,'2C724E071B786F2B58AC852655A02173.jpg','2021-03-03 23:37:39.773'),(301,1210035,6,'AB46E8CEFB33E6682D252A45D2B50106.mp4','2021-03-03 23:37:40.462'),(302,1210010,6,'5EE6B83015693812DCAB794495D006E6.mp4','2021-03-03 23:37:45.573'),(303,1212004,7,'0C223200E91EB3ED384E2095A1C25DC4.jpg','2021-03-03 23:37:49.764'),(304,1212004,8,'50FF3767EB67A2DA9651A9A32327441B.jpg','2021-03-03 23:37:49.787'),(305,1212005,7,'EB01DB8B9EE2CFABA5FD6B012AC822F3.jpg','2021-03-03 23:37:50.296'),(306,1212005,8,'6D1C8F8EA34B0F628A9CED3B179E98A8.jpg','2021-03-03 23:37:50.319'),(307,1210036,6,'7032184142A5644AAB1CF796D31B6CC2.mp4','2021-03-03 23:37:50.675'),(308,1212006,7,'29DD9BE5E88D4CEB9C3A2D1A829888C2.jpg','2021-03-03 23:37:55.480'),(309,1212006,8,'61D4A8E649A670FC0B0C449727A33AE0.jpg','2021-03-03 23:37:55.505'),(310,1210037,6,'A9677F2AA22DD15CF78756EEBE1E8BDF.mp4','2021-03-03 23:37:55.750'),(311,1212007,7,'E657D7A1F61523C4DE97F0E3FDB53202.jpg','2021-03-03 23:37:57.324'),(312,1212007,8,'DD152B97BDB8E81D133C67FEFF0FE38E.jpg','2021-03-03 23:37:57.347'),(313,1212008,7,'BA9F0FFC792E52345F8EC5AAC46C4643.jpg','2021-03-03 23:37:58.592'),(314,1212008,8,'AB552183D2F7459E4C13D2EEEE6DF548.jpg','2021-03-03 23:37:58.614'),(315,1212009,7,'C2CFC19DDEC31EBE70772C79E04A6AEC.jpg','2021-03-03 23:38:00.340'),(316,1212009,8,'3C6258D0FB9D086514E3B8992C010128.jpg','2021-03-03 23:38:00.360'),(317,1210038,6,'AB64E20FAB89CDB0CA292AD5DA4FD92B.mp4','2021-03-03 23:38:00.831'),(318,1212010,7,'AF821BAACD83DF79F81DE707D8326C95.jpg','2021-03-03 23:38:01.081'),(319,1212010,8,'839FA3F4F2F520485ED337EE6F9C216C.jpg','2021-03-03 23:38:01.105'),(320,1212011,7,'1D1DC071774B004C6CB94C41A6273114.jpg','2021-03-03 23:38:03.886'),(321,1212011,8,'A252118270851DE9CBA304CA4E1CC525.jpg','2021-03-03 23:38:03.910'),(322,1212012,7,'4D0CA3C02404DD3B019032F881B9F1F7.jpg','2021-03-03 23:38:04.393'),(323,1212012,8,'39A808E6F2F53493DC81F6E3EB7B7110.jpg','2021-03-03 23:38:04.415'),(324,1210011,6,'0325F9F6916A10218F3BB02109A82A0E.mp4','2021-03-03 23:38:05.910'),(325,1212013,7,'C1670C2890C8A0B2EDB8CE3F2190451D.jpg','2021-03-03 23:38:05.924'),(326,1212013,8,'F96BDB980C7E389FF9EA8AB4A8D39A4B.jpg','2021-03-03 23:38:05.946'),(327,1212014,7,'6309A28FE1739C267AC1CC969E6EA4C4.jpg','2021-03-03 23:38:08.842'),(328,1212014,8,'9FE48520E3010696392A03DF5D490D93.jpg','2021-03-03 23:38:08.870'),(329,1210039,6,'F167D534F0827943DBA803FA8CEB84AB.mp4','2021-03-03 23:38:10.983'),(330,1212015,7,'26079C64BFD0E1D630153BF1F64C3DF9.jpg','2021-03-03 23:38:11.206'),(331,1212015,8,'EBCE4C0EE717295CE56434B7971F6329.jpg','2021-03-03 23:38:11.229'),(332,1212016,7,'E3881F388880D5E334F609AD9F968377.jpg','2021-03-03 23:38:12.095'),(333,1212016,8,'6136CF7E770CFB55223FC2BAC9747E87.jpg','2021-03-03 23:38:12.120'),(334,1212017,7,'99FD0CD0BCA7B3AAC1BFEE445D0A45E6.jpg','2021-03-03 23:38:15.252'),(335,1212017,8,'250D6C530CD92343004F7F2ACD9380A2.jpg','2021-03-03 23:38:15.276'),(336,1210040,6,'2A400945D709558D6DFEA7CA76D9C1FA.mp4','2021-03-03 23:38:16.092'),(337,1212018,7,'4EED23087A0A7B7405ED16C209698A7E.jpg','2021-03-03 23:38:17.132'),(338,1212018,8,'47BAC1A21102371FA024ED466E17E1E7.jpg','2021-03-03 23:38:17.154'),(339,1212019,7,'54C427D0FC5ABF370801A85D34DC7D0E.jpg','2021-03-03 23:38:19.248'),(340,1212019,8,'A64ED437D6F8FB122C665C0812E8B1B2.jpg','2021-03-03 23:38:19.274'),(341,1210041,6,'816E1882B490F38412724CED943AB636.mp4','2021-03-03 23:38:21.171'),(342,1212020,7,'812698A71896F70AEBC3A4025534FFAC.jpg','2021-03-03 23:38:24.865'),(343,1212020,8,'924F062C3259984D3E75312A7C0C594C.jpg','2021-03-03 23:38:24.889'),(344,1210042,6,'948FEFB8DEABCFA94D175B39F202D034.mp4','2021-03-03 23:38:26.242'),(345,1212021,7,'8CCA865A42D4172789FB8C2ECECCF94C.jpg','2021-03-03 23:38:28.895'),(346,1212021,8,'902B39AEB1515C92E20FD5C902A10C3D.jpg','2021-03-03 23:38:29.112'),(347,1210012,6,'7AB6F828595E8992535E293041F2BC2C.mp4','2021-03-03 23:38:31.311'),(348,1212022,7,'0E4917D0AA76EED43107ECECA5CD21CE.jpg','2021-03-03 23:38:34.935'),(349,1212022,8,'D461BE63AA000C445A90A890A94672CF.jpg','2021-03-03 23:38:34.958'),(350,1210013,6,'FFFF43E4F2E517603209F84ECA99EECF.mp4','2021-03-03 23:38:36.408'),(351,1212023,7,'691E7DFC8F35FA1DD1B42E9B2575311C.jpg','2021-03-03 23:38:38.116'),(352,1212023,8,'85CE40B49F85D077308754B98F4A4C20.jpg','2021-03-03 23:38:38.139'),(353,1210043,6,'240411591C4FFACE90CC11F103E1D5F2.mp4','2021-03-03 23:38:41.477'),(354,1210014,6,'67CAF4642243DAFA787ED32188EB3CD9.mp4','2021-03-03 23:38:46.570'),(355,1210015,6,'7FFC5D7488DB8F9DA377AE932094990D.mp4','2021-03-03 23:38:51.648'),(356,1210016,6,'0207F77658239D9D4287D72F52FA054E.mp4','2021-03-03 23:38:56.796'),(357,1212024,7,'B2B6BB9211266CD8EC2C5231B1D28ABA.jpg','2021-03-03 23:38:57.702'),(358,1212024,8,'84548970F4FF562C8CEFE49C29093829.jpg','2021-03-03 23:38:57.724'),(359,1212025,7,'0D42742DAB5BA2061C46365F6EABD0ED.jpg','2021-03-03 23:38:58.851'),(360,1212025,8,'5EC2AC4E13ABAB4A1673239E39984073.jpg','2021-03-03 23:38:58.880'),(361,1212026,7,'387A37E437CB57D32C44697220BA3A22.jpg','2021-03-03 23:39:00.316'),(362,1212026,8,'58E489FC73B17E1883E5D90C0BBD3387.jpg','2021-03-03 23:39:00.337'),(363,1210017,6,'12557A0923DFCC90B86D86AC28C0BFE5.mp4','2021-03-03 23:39:01.869'),(364,1212027,7,'8FA343496DAA34C65B86FFB421164376.jpg','2021-03-03 23:39:02.129'),(365,1212027,8,'EE3438B72243F5C841AC52B1AD73887E.jpg','2021-03-03 23:39:02.153'),(366,1212028,7,'12D4B13A83DC38E12249DAFEC30CF793.jpg','2021-03-03 23:39:04.376'),(367,1212028,8,'8C390CFE590F2482F3D28DEACD8C64D3.jpg','2021-03-03 23:39:04.667'),(368,1212029,7,'BF9E27594F25E4B06459D04E32A3AFE3.jpg','2021-03-03 23:39:04.980'),(369,1212029,8,'132928088FF55CB70190A72F88E76045.jpg','2021-03-03 23:39:05.002'),(370,1212030,7,'1D42A57E844B281AFF38362DD4729D7F.jpg','2021-03-03 23:39:05.949'),(371,1212030,8,'BDDECBDC4A0C525E50479DF8287F6980.jpg','2021-03-03 23:39:05.975'),(372,1210018,6,'128D4FF24A0CF01A8D5F23548E0DD380.mp4','2021-03-03 23:39:06.947'),(373,1212031,7,'20E6927FB423B064FDD9E60AC6C12C95.jpg','2021-03-03 23:39:08.402'),(374,1212031,8,'C0C4458E3AD55A59C6B791720FACA091.jpg','2021-03-03 23:39:08.424'),(375,1210044,6,'ADAD0877E22FB8093FE966867C6863DB.mp4','2021-03-03 23:39:12.034'),(376,1212032,7,'81C65E6D3E68C2000B792B5EE05C635E.jpg','2021-03-03 23:39:16.212'),(377,1212032,8,'91F4C094A4D2B70DEDD55771CFB19EDE.jpg','2021-03-03 23:39:16.269'),(378,1210045,6,'F79243600421ACA0B93B29CFB1FE9C1A.mp4','2021-03-03 23:39:17.139'),(379,1212033,7,'332A6DA4A7D6A81B8A45CDC6D3CD570B.jpg','2021-03-03 23:39:20.441'),(380,1212033,8,'E10A3E6BEECA9A6DF94611A1CDF3A375.jpg','2021-03-03 23:39:20.465'),(381,1210019,6,'72377FEAFEA004A5D482D02E0D643519.mp4','2021-03-03 23:39:22.313'),(382,1212034,7,'700BF7C22129F9F71163BD1037C5A21D.jpg','2021-03-03 23:39:22.905'),(383,1212034,8,'0862B364BEF92BB82D05BAD263409E4C.jpg','2021-03-03 23:39:22.927'),(384,1212032,6,'D7803B86C2423D3698424163F98A9AEF.mp4','2021-03-03 23:41:21.545'),(385,1212033,6,'E4B6718397202C58C846D45EDB6E8E4A.mp4','2021-03-03 23:41:26.735'),(386,1212034,6,'126456B4AD4D1883AC56EF723EAAE13C.mp4','2021-03-03 23:41:31.826'),(387,1214001,7,'63C3A4CDD9698CC492263CD5C3D680E4.jpg','2021-03-03 23:41:38.403'),(388,1214001,8,'B2051E200FC013B8C242C3181B5030E7.jpg','2021-03-03 23:41:38.424'),(389,1214002,7,'7F79E9B60D1BCF8A1671E0F53A6434D2.jpg','2021-03-03 23:41:38.775'),(390,1214002,8,'79A161117374E6C00660D2D49B8E86B8.jpg','2021-03-03 23:41:38.797'),(391,1214003,7,'54252133440E014BDECDE2D8BE2F8745.jpg','2021-03-03 23:41:40.963'),(392,1214003,8,'822D6D3DA3F3D4C5D0B9E23AF9279DFE.jpg','2021-03-03 23:41:40.996'),(393,1214004,7,'27A811392F6C2E1EFB2FEF583E58DE9B.jpg','2021-03-03 23:41:41.386'),(394,1214004,8,'D47D6CA16120998BC9F6FB934F787C4A.jpg','2021-03-03 23:41:41.415'),(395,1214005,7,'9DFA131E93A5EA1014885402F49C31E6.jpg','2021-03-03 23:41:43.530'),(396,1214005,8,'B0E604F6892C01E303A876AF648D28CA.jpg','2021-03-03 23:41:43.550'),(397,1214006,7,'8C545FAB0A3C029A921FFB5A05858AAA.jpg','2021-03-03 23:41:44.687'),(398,1214006,8,'BC6E77CA695104B60CB9B4AAB89153FD.jpg','2021-03-03 23:41:44.709'),(399,1214007,7,'5318B4A86C0BD8FA16176C5F8B7E46DF.jpg','2021-03-03 23:41:46.281'),(400,1214007,8,'DF95B6841B2D47423910377D85FBA2FF.jpg','2021-03-03 23:41:46.298'),(401,1214008,7,'CD25FAE5BF83D4E68F900F41D6811DCF.jpg','2021-03-03 23:41:48.120'),(402,1214008,8,'8ACB64A586405DC9C650212B5AE8B40F.jpg','2021-03-03 23:41:48.142'),(403,1214009,7,'77A0830F4D51C1C3CBF853479C0BC242.jpg','2021-03-03 23:41:48.743'),(404,1214009,8,'8F6CB3CD4C2EB7E63D7806244FAACAAB.jpg','2021-03-03 23:41:48.760'),(405,1214010,7,'05165AED0EF1C5E1E4D3F7CC2B444205.jpg','2021-03-03 23:41:51.017'),(406,1214010,8,'45097C7B019887AE844FBBB7E43D29D9.jpg','2021-03-03 23:41:51.039'),(407,1214011,7,'70C7C3AFF99B884C5F1F089550D8FC88.jpg','2021-03-03 23:41:51.330'),(408,1214011,8,'3EFD6088C171D4809C0AEBB180E35188.jpg','2021-03-03 23:41:51.355'),(409,1214012,7,'D45E77965FD453962201BCEB5A8A65AC.jpg','2021-03-03 23:41:53.322'),(410,1214012,8,'61CC4B8DEA89FDB7340076C6AAAB2487.jpg','2021-03-03 23:41:53.343'),(411,1214001,6,'607CB7DE95F6223C8622F087966C358E.mp4','2021-03-03 23:41:54.033'),(412,1214003,6,'DD31E131A74C745C47AC4BB4B4267CD2.mp4','2021-03-03 23:42:04.499'),(413,1214004,6,'DD31E131A74C745C47AC4BB4B4267CD2.mp4','2021-03-03 23:42:09.658'),(414,1216001,7,'047F6A25C5AEAF44265B7D3D6A2CB938.jpg','2021-03-03 23:42:10.663'),(415,1216001,8,'E6FA7FC6D46F9FF3844758F0A8451062.jpg','2021-03-03 23:42:10.685'),(416,1214005,6,'106169730B17EBE437A869411911F9FA.mp4','2021-03-03 23:42:14.768'),(417,1216002,7,'B74AA35245746D5C68162D880A449AC9.jpg','2021-03-03 23:42:18.596'),(418,1216002,8,'C580E5395EED29071E4C2195900137C8.jpg','2021-03-03 23:42:18.618'),(419,1214006,6,'106169730B17EBE437A869411911F9FA.mp4','2021-03-03 23:42:19.855'),(420,1216003,7,'BBEC472BA8B469C8A224275F4200F6F0.jpg','2021-03-03 23:42:20.667'),(421,1216003,8,'A87AC0DD2D8F75590CDC979A647088E4.jpg','2021-03-03 23:42:20.687'),(422,1216004,7,'641257A578FAC4AE04237EE9FD99D59E.jpg','2021-03-03 23:42:23.546'),(423,1216004,8,'7B7E00842D8DCE387F16D7A70AB4301E.jpg','2021-03-03 23:42:23.574'),(424,1216005,7,'22D05457E807E993DA3343AB3FECB8C9.jpg','2021-03-03 23:42:24.494'),(425,1216005,8,'8D3FF288F1012EEABB9321245CE773F4.jpg','2021-03-03 23:42:24.515'),(426,1216006,7,'293CC95E2BFB4A90D1A16A13F5CBC455.jpg','2021-03-03 23:42:32.159'),(427,1216006,8,'DA973C52EFBA0B67B9823A41404F5D68.jpg','2021-03-03 23:42:32.181'),(428,1216007,7,'D164161466E6AC79D6342683BBC4A99B.jpg','2021-03-03 23:42:58.835'),(429,1216007,8,'4A2E2CF31D5394D72A5B199F9D1EBCA2.jpg','2021-03-03 23:42:58.854'),(430,1216008,7,'4EC0257E2A7622D688B515F6DB00B420.jpg','2021-03-03 23:43:18.866'),(431,1216008,8,'E9E8D8C20ADAC5B1974A6A46196C757B.jpg','2021-03-03 23:43:18.886'),(432,1216009,7,'A97A3425575E8DD4927A108391D24403.jpg','2021-03-03 23:43:20.848'),(433,1216009,8,'AC60FD4DE23AE529C39E73815C3EE9DA.jpg','2021-03-03 23:43:20.870'),(434,1216010,7,'C263AE5761EAE86D0AC1CDD35474C810.jpg','2021-03-03 23:43:21.288'),(435,1216010,8,'FE90312F75145DEAF7C27942A8751952.jpg','2021-03-03 23:43:21.305'),(436,1216011,7,'7C34D45B8CF8FC13B639091AB1792793.jpg','2021-03-03 23:43:24.203'),(437,1216011,8,'579C6133C8B5BC3B6D1CA400E7B098E0.jpg','2021-03-03 23:43:24.223'),(438,1216012,7,'D29EFB77A02913CDFA5344C76C9C76A6.jpg','2021-03-03 23:43:24.684'),(439,1216012,8,'F3C52FA48C2D2B250D151D94B15A8681.jpg','2021-03-03 23:43:24.705'),(440,1216013,7,'567480C911D3BCFE643066B00D3E71C6.jpg','2021-03-03 23:43:28.378'),(441,1216013,8,'0C3F6115C001E1714D457772F549E42D.jpg','2021-03-03 23:43:28.410'),(442,1216014,7,'957519CCCFEC9DC2F0FF87109B63D5BE.jpg','2021-03-03 23:43:31.805'),(443,1216014,8,'39B3F4D77ACFCFEF246DFB10BE61A5CF.jpg','2021-03-03 23:43:31.827'),(444,1216015,7,'CB184EDBBF725F1F70CCE6BB81AA36F5.jpg','2021-03-03 23:43:32.989'),(445,1216015,8,'2858C68752A218E3D7487C5B540B254E.jpg','2021-03-03 23:43:33.013'),(446,1216016,7,'7BDE7EBFC183D386D6B0278EA8B51D21.jpg','2021-03-03 23:43:37.482'),(447,1216016,8,'BACC5E1060ED12164ABB48D10EB12A03.jpg','2021-03-03 23:43:37.503'),(448,1216017,7,'B1FBCF4E4AC49835B088083B8F1156D2.jpg','2021-03-03 23:43:39.561'),(449,1216017,8,'6772082C1589E7542F958CAC30F37A5F.jpg','2021-03-03 23:43:40.196'),(450,1216018,7,'CB4F5C0ED6494ECC6D3DCC77CC87BF84.jpg','2021-03-03 23:43:43.301'),(451,1216018,8,'66DA42FBF77CBF50D331F9294A52D4B3.jpg','2021-03-03 23:43:43.321'),(452,1216019,7,'CDAC4A8C3A775D7B72227F74AAE962F2.jpg','2021-03-03 23:43:48.961'),(453,1216019,8,'468BDC132676A64E0B4355C824754A1F.jpg','2021-03-03 23:43:48.998'),(454,1216009,6,'1B959F4C625039A0378698EC0CE215B9.mp4','2021-03-03 23:43:49.305'),(455,1216010,6,'1B959F4C625039A0378698EC0CE215B9.mp4','2021-03-03 23:43:54.427'),(456,1216020,7,'1B18CF5A3B5611CB8BABB9E92A1CAAE0.jpg','2021-03-03 23:43:55.219'),(457,1216020,8,'81A41415150DA2F324EF88EE925D6F66.jpg','2021-03-03 23:43:55.237'),(458,1216011,6,'525E56DA2D35B7CFF004D4A6F44AB89C.mp4','2021-03-03 23:44:00.502'),(459,1216012,6,'3DC5DF073BB03B14590E149A7B5DE648.mp4','2021-03-03 23:44:05.601'),(460,1214009,6,'4BE6FB32734492FDA08BE85C4257A74A.mp4','2021-03-03 23:44:10.695'),(461,1216013,6,'2405358C26C4AEAAA36FBF623A7F919E.mp4','2021-03-03 23:44:15.788'),(462,1216021,7,'ECDABEDB0F10C7F4888084A4C28853A1.jpg','2021-03-03 23:44:17.526'),(463,1216021,8,'D07C1026DFE71D746CBCB33F227CC9AA.jpg','2021-03-03 23:44:17.549'),(464,1216022,7,'822E54141E2A22221FDAC13B5F005BCA.jpg','2021-03-03 23:44:18.303'),(465,1216022,8,'6448558339AAF22211F1582A9E91D632.jpg','2021-03-03 23:44:18.324'),(466,1214010,6,'0F077AB27EC4379539C057BB1F46A2D4.mp4','2021-03-03 23:44:20.887'),(467,1216023,7,'88DFE6A7F69462AA88AE0122B449D79A.jpg','2021-03-03 23:44:21.543'),(468,1216023,8,'67FAB49025604EB4DEA2904AE8683FB8.jpg','2021-03-03 23:44:21.566'),(469,1216024,7,'42568BEEFE97DAB7AB6C21F25E22AA7A.jpg','2021-03-03 23:44:22.255'),(470,1216024,8,'4BF1DAB72D9D13628BA43B28C55F9F2B.jpg','2021-03-03 23:44:22.277'),(471,1216025,7,'1352BB342E0FEFD6D80C30823BD220D8.jpg','2021-03-03 23:44:24.045'),(472,1216025,8,'3126A5BB160BD099D08AF61D8F44AA23.jpg','2021-03-03 23:44:24.067'),(473,1216026,7,'113AB31928F2186B03431E5D876793CD.jpg','2021-03-03 23:44:24.468'),(474,1216026,8,'328AB47F5CE5986827A340726CF79C28.jpg','2021-03-03 23:44:24.488'),(475,1216014,6,'C95D767EAFFB1510030FDC3F9E210140.mp4','2021-03-03 23:44:25.957'),(476,1216027,7,'104AF6BDD290FA6FECE9080EBD3D8E88.jpg','2021-03-03 23:44:26.357'),(477,1216027,8,'961B611735196936135591F2BA9634FE.jpg','2021-03-03 23:44:26.378'),(478,1216028,7,'79678BA04290823F00D552EFC8D04AB7.jpg','2021-03-03 23:44:26.752'),(479,1216028,8,'F5F00C9A931D10B5DB0186D2E6496B06.jpg','2021-03-03 23:44:26.771'),(480,1216029,7,'7C1D6CDE1EC7C7A745D0FA310F1BDEEB.jpg','2021-03-03 23:44:28.259'),(481,1216029,8,'E6D3A2A5001671A83F93440A7BE1375D.jpg','2021-03-03 23:44:28.285'),(482,1216030,7,'2F63409D879AD00687D231D3E3FFD147.jpg','2021-03-03 23:44:29.502'),(483,1216030,8,'95F4249E8D050C9337FC1A65B50203B3.jpg','2021-03-03 23:44:29.523'),(484,1216031,7,'E434395B6D4664EF51DB45912E422823.jpg','2021-03-03 23:44:30.757'),(485,1216031,8,'70B6408C4243ABCC072F3C8F6FD2D2FB.jpg','2021-03-03 23:44:30.780'),(486,1216015,6,'A8DA6D4D338AEDC78F6C0D18A2CD8C64.mp4','2021-03-03 23:44:31.069'),(487,1216032,7,'E6A9B503CBA8F22E873C867C2C51F6B5.jpg','2021-03-03 23:44:32.384'),(488,1216032,8,'49113FE9F2E1DC3E4B85596BA7B0416B.jpg','2021-03-03 23:44:32.404'),(489,1216033,7,'EA981689E76C084442B6D1B3EBFCC602.jpg','2021-03-03 23:44:33.842'),(490,1216033,8,'6F0DBA3B13D04E17471F10B586DF029F.jpg','2021-03-03 23:44:33.864'),(491,1214011,6,'0F077AB27EC4379539C057BB1F46A2D4.mp4','2021-03-03 23:44:36.160'),(492,1216034,7,'03C6AAF91992D0A7B3C275D9DA9DB354.jpg','2021-03-03 23:44:36.429'),(493,1216034,8,'C2D9D2F94386B1FC897CBA5D20F1055F.jpg','2021-03-03 23:44:36.451'),(494,1216016,6,'2FE338575378F65200AAF3443CEB65E0.mp4','2021-03-03 23:44:41.227'),(495,1216035,7,'2E1D507678450B62585594CAA5B1CECA.jpg','2021-03-03 23:44:45.317'),(496,1216035,8,'B95063B3DE2CA902AEDC6884A732719D.jpg','2021-03-03 23:44:45.341'),(497,1216017,6,'3BD38A8F29793D7A42B9369A7CA86780.mp4','2021-03-03 23:44:46.301'),(498,1214012,6,'CD333AB6258F152412D27E6893B64285.mp4','2021-03-03 23:44:51.375'),(499,1216036,7,'A77244CECB0830782CF6BF09DF1AD2A7.jpg','2021-03-03 23:44:55.319'),(500,1216036,8,'7189E1AB7DED4FC2E5B6BE9E5AAEDE66.jpg','2021-03-03 23:44:55.342'),(501,1216018,6,'EE5C7E0ABE5C855A7F2EF663E822DD94.mp4','2021-03-03 23:44:56.428'),(502,1216037,7,'358B95D1B7F3344C2535A9D239BA2EA1.jpg','2021-03-03 23:45:01.538'),(503,1216037,8,'A48523C283409E6B55FF11DFD0366011.jpg','2021-03-03 23:45:01.565'),(504,1216001,6,'20CCAF7F8B7150324D55B1E8AEC4B467.mp4','2021-03-03 23:45:02.529'),(505,1216038,7,'9F8F93CB374B7606ACDFBEFCEF9F2001.jpg','2021-03-03 23:45:03.525'),(506,1216038,8,'DAB68AFB82EEEC6F81358C74CF62C42F.jpg','2021-03-03 23:45:03.547'),(507,1216039,7,'076A467FA813BB40D03C06A66CF74814.jpg','2021-03-03 23:45:10.786'),(508,1216039,8,'2855C861ACC2B66F9B43B7765523EB5D.jpg','2021-03-03 23:45:10.815'),(509,1216002,6,'1294112EAD824CAD32E552DBEB09C267.mp4','2021-03-03 23:45:12.649'),(510,1216040,7,'D1309D8A3D07D4504B62FDF8E2CA81BC.jpg','2021-03-03 23:45:20.294'),(511,1216040,8,'469B5BFA73FE76E8A6C8C596BF245A9A.jpg','2021-03-03 23:45:20.322'),(512,1216003,6,'A3A35523BC5885EBEFDCDBB45AF36A61.mp4','2021-03-03 23:45:22.767'),(513,1216004,6,'12BA9C8C22E6EE50F8C0DAE81524B2DE.mp4','2021-03-03 23:45:27.854'),(514,1216041,7,'5C5F8AC786472B30A85F98F5BF9211A6.jpg','2021-03-03 23:45:28.135'),(515,1216041,8,'F2205062AD7D5E81492E40072AA8C364.jpg','2021-03-03 23:45:28.154'),(516,1216005,6,'3887AD4C3D0BEF01E8BB6FE5CB7B67E7.mp4','2021-03-03 23:45:32.956'),(517,1216042,7,'DAC4991CC2E9F38F27AB770CDF4C121C.jpg','2021-03-03 23:45:35.464'),(518,1216042,8,'281BDADB88421496FE66FE214A7DCAF1.jpg','2021-03-03 23:45:35.486'),(519,1216043,7,'54AA2BD768C19C0507F77A73B4756596.jpg','2021-03-03 23:45:36.743'),(520,1216043,8,'CC27292B074B9732944D0DE815F63C09.jpg','2021-03-03 23:45:36.766'),(521,1216006,6,'DCA36D193CC94DB023878685007C2411.mp4','2021-03-03 23:45:38.056'),(522,1216007,6,'A37906EE8B6EE9D912A89082150C2004.mp4','2021-03-03 23:45:43.145'),(523,1216021,6,'5B9CC4E182D8A24F9A2E3135467822A9.mp4','2021-03-03 23:45:48.218'),(524,1216022,6,'72C6B00445CB334251F70F48CE4FC1DA.mp4','2021-03-03 23:45:53.311'),(525,1216044,7,'4DD09AAA62C325F9372DAD2DFBB6ACC1.jpg','2021-03-03 23:45:57.791'),(526,1216044,8,'53D97600E863DC82F615886F3E529E66.jpg','2021-03-03 23:45:57.812'),(527,1216023,6,'EFE97469431F99D5A4623A918DF8F04A.mp4','2021-03-03 23:45:58.400'),(528,1216045,7,'A7DA6985A5D6C7586F51A095225E5EC5.jpg','2021-03-03 23:45:59.308'),(529,1216045,8,'25AC744619346E074D28136DFB91AF75.jpg','2021-03-03 23:45:59.328'),(530,1216046,7,'CC636674DFE4EEE556E326E4360A3FB9.jpg','2021-03-03 23:46:01.155'),(531,1216046,8,'036AB35FC18E3857D3BABF31306DCF81.jpg','2021-03-03 23:46:01.176'),(532,1216047,7,'F2B54BE5575D9E9FC4D83DA607B8413E.jpg','2021-03-03 23:46:02.176'),(533,1216047,8,'A4E10DFE7EFF14A2459D0829986CA6DB.jpg','2021-03-03 23:46:02.196'),(534,1216024,6,'3D19459A2C1132D2598CCAAB4B6EF085.mp4','2021-03-03 23:46:03.521'),(535,1216048,7,'7CF0A5BFB7A4A576F255BA92952FB441.jpg','2021-03-03 23:46:04.537'),(536,1216048,8,'8B08E504377E3A4EAD2E4AD12BC5339D.jpg','2021-03-03 23:46:04.557'),(537,1216049,7,'046A18D21938CE9C4C0472192DC7B1CB.jpg','2021-03-03 23:46:05.040'),(538,1216049,8,'992F08B88F9EDB8A551175D334A16137.jpg','2021-03-03 23:46:05.060'),(539,1216050,7,'7438E2A8AE0DE57FC3A3B48A035E8E31.jpg','2021-03-03 23:46:07.296'),(540,1216050,8,'754E074F153381304561959FC9357D09.jpg','2021-03-03 23:46:07.316'),(541,1214007,6,'581BAAD968286DD667DA0BC160279719.mp4','2021-03-03 23:46:08.665'),(542,1216051,7,'69B29354328AD8313BF52A9D40F14EBE.jpg','2021-03-03 23:46:09.944'),(543,1216051,8,'D82225838E5EA3A4332D6A2D7602714A.jpg','2021-03-03 23:46:09.963'),(544,1216052,7,'48D9DC8024D64BC0EA91E43B879A7905.jpg','2021-03-03 23:46:12.654'),(545,1216052,8,'D01A478C97691422387510CA40E0800A.jpg','2021-03-03 23:46:12.673'),(546,1216025,6,'38664EB0A7C0F7E102D3643DAE4D6C2B.mp4','2021-03-03 23:46:13.731'),(547,1214013,0,'D36383889E5E75F4425542AFE81DEE5A.mp4','2021-03-03 23:46:15.770'),(548,1216053,7,'9F964A577320D364255C161063CA400E.jpg','2021-03-03 23:46:17.938'),(549,1216053,8,'24C1044C4947C811648D0006B9761EBE.jpg','2021-03-03 23:46:17.995'),(550,1216054,7,'626E36E3A5EF9ACB907D3469F49F7CD8.jpg','2021-03-03 23:46:18.346'),(551,1216054,8,'89BF0599443AE409A805FEF88E9A43D0.jpg','2021-03-03 23:46:18.365'),(552,1216026,6,'38664EB0A7C0F7E102D3643DAE4D6C2B.mp4','2021-03-03 23:46:18.799'),(553,1216027,6,'C58BCF41B1548ECA74E35B198D7780C2.mp4','2021-03-03 23:46:23.892'),(554,1216055,7,'94F9333368C0EF5738D00AD6F16B00E8.jpg','2021-03-03 23:46:23.922'),(555,1216055,8,'884932EA13360632A5457F108DDD07FE.jpg','2021-03-03 23:46:23.940'),(556,1216028,6,'C58BCF41B1548ECA74E35B198D7780C2.mp4','2021-03-03 23:46:28.985'),(557,1216029,6,'933C5DCA66F789CA41688533A0EA06DA.mp4','2021-03-03 23:46:34.095'),(558,1216056,7,'85612D3554D2CF25BC068BD931131826.jpg','2021-03-03 23:46:34.676'),(559,1216056,8,'60A9DF14BABF77F5B49E933AE9A17AA9.jpg','2021-03-03 23:46:34.696'),(560,1216057,7,'E2446C7F9A9CDF48DA90994C8E7FE3F4.jpg','2021-03-03 23:46:37.305'),(561,1216057,8,'7D7142D66F209517C3AF054E7CB71D5F.jpg','2021-03-03 23:46:37.321'),(562,1216008,6,'3563CFABF4A6E8D6D7B8A4CC62BFC61F.mp4','2021-03-03 23:46:39.189'),(563,1216030,6,'7409ED9484388EC393F4DC0253D142BF.mp4','2021-03-03 23:46:44.255'),(564,1216031,6,'F170E3C551E3C6D7DC937A69571D7CF6.mp4','2021-03-03 23:46:49.346'),(565,1216032,6,'21F1195C12FEAC4D0547E75A29E2135C.mp4','2021-03-03 23:46:54.510'),(566,1216058,7,'D0E18E399BBACB8FD8F385DA9E8C2DF7.jpg','2021-03-03 23:46:59.075'),(567,1216058,8,'BC0238ADBE24B8B8375EACC034319CC7.jpg','2021-03-03 23:46:59.094'),(568,1216033,6,'B797F7ED2C4F3187CEE4988ED5665EB4.mp4','2021-03-03 23:46:59.784'),(569,1216059,0,'9BEF1052C3AA0187AEB98F3B2F0ABBC1.mp4','2021-03-03 23:47:00.234'),(570,1216060,7,'A11548A6FA5BBA8467AED58DD6099730.jpg','2021-03-03 23:47:02.602'),(571,1216060,8,'ADA4855437A24A11ACDD97A39807459B.jpg','2021-03-03 23:47:02.620'),(572,1216061,7,'538246C481CAC31FEE8A239569B6C997.jpg','2021-03-03 23:47:03.559'),(573,1216061,8,'9BA2E4D785B034D963681ECFD020CD7C.jpg','2021-03-03 23:47:03.578'),(574,1214008,6,'4BE6FB32734492FDA08BE85C4257A74A.mp4','2021-03-03 23:47:04.875'),(575,1216062,7,'9A7C8202594235B409A308B6604AE5FE.jpg','2021-03-03 23:47:06.031'),(576,1216062,8,'38DB57060293732D213F62BCA269FDDC.jpg','2021-03-03 23:47:06.050'),(577,1216063,7,'F72ED57F233A1A8C4C2750CAC0B99B2E.jpg','2021-03-03 23:47:06.500'),(578,1216063,8,'6856035E8FBE6A5FA6F34FE612CB753B.jpg','2021-03-03 23:47:06.516'),(579,1216064,7,'9F83B29BE23C383222FEB0DF5A1268A2.jpg','2021-03-03 23:47:09.366'),(580,1216064,8,'82C5B9D52F52702CB224FBE7FB59C059.jpg','2021-03-03 23:47:09.385'),(581,1216034,6,'701F515B49DC01230A33EF394FF7BED6.mp4','2021-03-03 23:47:09.965'),(582,1216035,6,'984B954CADD8E5030BEDE06E0ACE5C4A.mp4','2021-03-03 23:47:15.045'),(583,1216065,7,'AEC3118D2C0C90737AE69D3B51BC1530.jpg','2021-03-03 23:47:15.279'),(584,1216065,8,'9DA1A9AF3CB7C7240D65502584CF61EC.jpg','2021-03-03 23:47:15.298'),(585,1216036,6,'09817AC51C67C9E22B827CCE73C8483E.mp4','2021-03-03 23:47:21.119'),(586,1216037,6,'1FD312B1A8B015E45747380A6382C50B.mp4','2021-03-03 23:47:26.202'),(587,1216066,7,'FD9C10BF36F2D77DCFF3F9742BFE07E4.jpg','2021-03-03 23:47:29.500'),(588,1216066,8,'3F751CF96CFEBA16EBC45CEF04A7C786.jpg','2021-03-03 23:47:29.520'),(589,1216038,6,'051E4CE878A7B6720202D6F7A8C21E39.mp4','2021-03-03 23:47:31.272'),(590,1216039,6,'D54CAAAB68EFEC473E8E091C3E06889F.mp4','2021-03-03 23:47:36.362'),(591,1216067,7,'B91F803EBD288D9A7731E7CF4912CAFB.jpg','2021-03-03 23:47:39.041'),(592,1216067,8,'3EA46420A85F2926E03E3449E191BE65.jpg','2021-03-03 23:47:39.060'),(593,1216040,6,'D148E7BB6AE27570DCBA96CC864807B3.mp4','2021-03-03 23:47:41.457'),(594,1216041,6,'33820E466D721263C28E3C11877D8F6B.mp4','2021-03-03 23:47:46.550'),(595,1216042,6,'F4AD6B5DBAE31AFAABE0FD6D74CFBBB5.mp4','2021-03-03 23:47:51.632'),(596,1216043,6,'CBFA3143C26C9A829F7AC6F5C081F086.mp4','2021-03-03 23:47:56.695'),(597,1216068,7,'730B29FB9FDCC0F99F4056FC12417E1B.jpg','2021-03-03 23:47:58.176'),(598,1216068,8,'33EBD0B6E7AA0B5851935493D371CED3.jpg','2021-03-03 23:47:58.195'),(599,1216019,6,'C968AAE03BA5F1850691CD7D6CAD548F.mp4','2021-03-03 23:48:01.792'),(600,1216044,6,'5A1DAF95B163B644473293748686C81D.mp4','2021-03-03 23:48:06.858'),(601,1216045,6,'A493F87C2909C363BC80960C93D1BBD8.mp4','2021-03-03 23:48:11.950'),(602,1216046,6,'9FD02CFD26448A062AF560EFC5F55506.mp4','2021-03-03 23:48:17.045'),(603,1216069,7,'479AC3F2CA1E8B6EB150C57C67175A80.jpg','2021-03-03 23:48:17.834'),(604,1216069,8,'6A57E96EE9E727E72F735E2E8A466729.jpg','2021-03-03 23:48:17.853'),(605,1216047,6,'E8FCE34ECBC797623AE52D1BE4ACAC4A.mp4','2021-03-03 23:48:22.140'),(606,1216048,6,'C03CCD3E1742909E4AFBC14BC9D3A686.mp4','2021-03-03 23:48:27.236'),(607,1216049,6,'C03CCD3E1742909E4AFBC14BC9D3A686.mp4','2021-03-03 23:48:32.471'),(608,1216062,6,'3A8E8E870382B376003E3B52FE0A88E0.mp4','2021-03-04 23:42:27.121'),(609,1216063,6,'AE50F05C0863B93453B6E60025914C42.mp4','2021-03-04 23:42:27.754'),(610,1216064,6,'549946B18F3418874C13EED8951C1731.mp4','2021-03-04 23:42:28.057'),(611,1216065,6,'E09C5785A5BB9801FAE24AC3D46603BC.mp4','2021-03-04 23:42:28.350'),(612,1216066,6,'18D592DDE3756088A35AC39B71E985B5.mp4','2021-03-04 23:42:28.653'),(613,1218001,0,'6127BF638EC23E7C0D86095580795D5D.mp4','2021-03-04 23:47:25.411'),(614,1218002,0,'A4853E332986130E2A8AD72285A19F07.mp4','2021-03-04 23:52:26.467'),(615,1218003,0,'826200D52057D3456B45FB6908C2FF1E.mp4','2021-03-04 23:57:27.339'),(616,1220001,7,'00CF518F99EDFA2DF7127B1A73D13330.jpg','2021-03-05 00:11:33.324'),(617,1220001,8,'0F93B16F65E6F9C31B853022DD4DC72B.jpg','2021-03-05 00:11:33.384'),(618,1222001,7,'81513248A032ADC0D3A711C1DDEEDC33.jpg','2021-03-05 00:48:43.818'),(619,1222001,8,'E9CC40BE720704631FECC3A7E4BE4B1D.jpg','2021-03-05 00:48:43.873'),(620,1222002,7,'593F69AC4E5567A7ED507436381C96E3.jpg','2021-03-05 00:48:45.014'),(621,1222002,8,'89AB2BBB01DB4EB8BBEAA492A3CD0F10.jpg','2021-03-05 00:48:45.035'),(622,1222003,7,'E9D5A1A8D5C96F3F4DF334C0E7077FB3.jpg','2021-03-05 00:48:46.223'),(623,1222003,8,'F93AD6E91CA90DC4AB31A209C9E5873D.jpg','2021-03-05 00:48:46.246'),(624,1222004,7,'4C20F0DB63102CD0B5E6EADC80775A1A.jpg','2021-03-05 00:48:47.650'),(625,1222004,8,'247822723BDB3DB9981018D19FC20A55.jpg','2021-03-05 00:48:47.670'),(626,1222005,7,'EA1671548793AE5A7D4482BFB2CC0EE6.jpg','2021-03-05 00:48:51.011'),(627,1222005,8,'3FA5A60AD3FE389CB60EAA8DDF98C931.jpg','2021-03-05 00:48:51.032'),(628,1222006,7,'9993FAE0C5BA5BD06C3317603CBF90F3.jpg','2021-03-05 00:48:51.351'),(629,1222006,8,'6A4B7FA8946F55321AB79FB196DB7E71.jpg','2021-03-05 00:48:51.372'),(630,1222007,7,'5F163A90975D149AD1434511197AAF36.jpg','2021-03-05 00:48:53.740'),(631,1222007,8,'FC4763C8BC86F0F9EFDB6FD3EE777084.jpg','2021-03-05 00:48:53.762'),(632,1222008,7,'21A6BC299BD2F0579F0BECE13975AE31.jpg','2021-03-05 00:48:54.342'),(633,1222008,8,'A5F2405D4CCB051762E9B2E6F1681579.jpg','2021-03-05 00:48:54.360'),(634,1222009,7,'49113931B77B60052C4E2D58E3EE1F75.jpg','2021-03-05 00:48:58.708'),(635,1222009,8,'85B5F8C73F0842FA14AE4A6AAADDE168.jpg','2021-03-05 00:48:58.729'),(636,1222010,7,'7D844DBA93C0A24F11C18E6779279517.jpg','2021-03-05 00:49:02.136'),(637,1222010,8,'DB98C25347F12DBA4900A7347FB7EDFA.jpg','2021-03-05 00:49:02.164'),(638,1222011,7,'F773026DA0F46533F5AF6A56F62A991E.jpg','2021-03-05 00:49:34.628'),(639,1222011,8,'69E0D8C53867A4C7D80E281237F92B6F.jpg','2021-03-05 00:49:34.652'),(640,1222012,7,'4F334159EB884317B39F6C7785F6065A.jpg','2021-03-05 00:49:35.133'),(641,1222012,8,'46E5B41210BC00C709D09372FA3173E9.jpg','2021-03-05 00:49:35.245'),(642,1224001,7,'658A041040FDFC16D28582732A8E0311.jpg','2021-03-05 00:50:06.602'),(643,1224001,8,'BF755E873757D01463668DCB1D3E3EB9.jpg','2021-03-05 00:50:06.663'),(644,1224002,7,'AE79CEFC475A130715E8BD08AF4300F7.jpg','2021-03-05 00:50:08.228'),(645,1224002,8,'0CA66F90DFC2B27A6444189C49481759.jpg','2021-03-05 00:50:08.253'),(646,1224003,7,'2B68B3094BF0AB1A4CA48ED623FE50D5.jpg','2021-03-05 00:50:10.060'),(647,1224003,8,'BAE66D993CD7CE2794C000FD7D228EB2.jpg','2021-03-05 00:50:10.083'),(648,1224004,7,'C3D075B6C1029FE59B7560ACC3962C64.jpg','2021-03-05 00:50:10.744'),(649,1224004,8,'B63247D01C6974F65FC675A32B7BD0F5.jpg','2021-03-05 00:50:10.767'),(650,1224005,7,'FD438DB60C28EF37EEC998DB10AB3708.jpg','2021-03-05 00:50:14.216'),(651,1224005,8,'A36DA2F26DA82BE0A8DAC0E3E7BA92B2.jpg','2021-03-05 00:50:14.238'),(652,1224006,7,'DE426488BC5F304F791EC809F8842E44.jpg','2021-03-05 00:50:15.255'),(653,1224006,8,'57B12168D23EF8BBAC9957139C8FE0DA.jpg','2021-03-05 00:50:15.276'),(654,1224007,7,'8C398AB3E05FAB9858E14CB776AB494C.jpg','2021-03-05 00:50:16.661'),(655,1224007,8,'6E3D0A3A702C818C398A73106828D6D5.jpg','2021-03-05 00:50:16.682'),(656,1224008,7,'FEA8B0D5B70E7F0C629A81E47A1CE94D.jpg','2021-03-05 00:50:18.917'),(657,1224008,8,'2EF23441C8ABA39CD8D4F97698D7FE4B.jpg','2021-03-05 00:50:18.938'),(658,1224009,7,'D4EF5348567D2AE77D5327B58BB31C53.jpg','2021-03-05 00:50:19.323'),(659,1224009,8,'A46B79BB11A9348C3830017921DB22FD.jpg','2021-03-05 00:50:19.343'),(660,1224010,7,'8434F0A225E9F19906B95C8F7587A390.jpg','2021-03-05 00:50:21.115'),(661,1224010,8,'D1E7E6CE99FA509817D74ABFD1182970.jpg','2021-03-05 00:50:21.142'),(662,1224011,7,'1B1F43B95220EBDFB4664E8F245810B6.jpg','2021-03-05 00:50:21.803'),(663,1224011,8,'9B508CD9E86BB400B8499F8DEDFF1201.jpg','2021-03-05 00:50:21.825'),(664,1224012,7,'2AF6A5FF4376A84C290BED2DAAB72CA0.jpg','2021-03-05 00:50:23.726'),(665,1224012,8,'AEEB04D0A7A5B831E80D34F04BBD2A58.jpg','2021-03-05 00:50:23.777'),(666,1224013,7,'66F48783F211AC0CDEB8F0089ED7DA62.jpg','2021-03-05 00:50:27.414'),(667,1224013,8,'9B500D2FB6501ED36EF5757686C3651E.jpg','2021-03-05 00:50:27.435'),(668,1224014,7,'805DFC4DFF175F5FD485A01B9B5EE435.jpg','2021-03-05 00:50:28.209'),(669,1224014,8,'828288A31B119F8772B01778DB4AC06F.jpg','2021-03-05 00:50:28.232'),(670,1224015,7,'081304C3E1898E4143854FE51BE0C81E.jpg','2021-03-05 00:50:30.992'),(671,1224015,8,'7DACEEF115626C4EE5FC2FD560BD75C9.jpg','2021-03-05 00:50:31.015'),(672,1224016,7,'28EE5BB9DAE774D20C6356DBA1E1EEDB.jpg','2021-03-05 00:50:33.255'),(673,1224016,8,'87706B63B7F4498A2F2E7A632B54539D.jpg','2021-03-05 00:50:33.281'),(674,1224017,7,'D887A75C857F6495EB9F7DD7FA1B193D.jpg','2021-03-05 00:50:34.398'),(675,1224017,8,'73E79FC8F26270C0B40DDCE5885C4468.jpg','2021-03-05 00:50:34.419'),(676,1224018,7,'66887BD957B704E3A513AA3053F8CD15.jpg','2021-03-05 00:50:35.083'),(677,1224018,8,'19CBA61C32AEB2218A103CC062B06DB3.jpg','2021-03-05 00:50:35.104'),(678,1224019,7,'353AD7CBF8A3E63CB565FB2C6E37ED8D.jpg','2021-03-05 00:50:37.486'),(679,1224019,8,'4796192374E374C2424C7C060F051E61.jpg','2021-03-05 00:50:37.507'),(680,1224020,7,'2D9B9B7C07AC9645B5B3C407374DAD4F.jpg','2021-03-05 00:50:39.812'),(681,1224020,8,'E0536A24903175080AE758B66885A2A1.jpg','2021-03-05 00:50:39.834'),(682,1224021,7,'267354A9BC7BF16986678E261A4530B9.jpg','2021-03-05 00:50:40.484'),(683,1224021,8,'BBF6A53537D9CD48B29C49D03350B0E4.jpg','2021-03-05 00:50:40.503'),(684,1224022,7,'3FBD32CCDAEBBCA8515FF1C85E186BD0.jpg','2021-03-05 00:50:42.214'),(685,1224022,8,'261292FB3F777FC1581E40444DD75715.jpg','2021-03-05 00:50:42.240'),(686,1226001,7,'DF3082C6198DD89ABF0BB91A4A5F22B0.jpg','2021-03-05 01:06:31.651'),(687,1226001,8,'260309B6DBBB6CF502150091F6D1509B.jpg','2021-03-05 01:06:31.707'),(688,1226002,7,'3F5A7DD31A1CD01E06516617AB393031.jpg','2021-03-05 01:06:31.893'),(689,1226002,8,'295D53FA763445D75609BF8227B43652.jpg','2021-03-05 01:06:31.912'),(690,1226003,7,'BB5D8BBCA99AEC713EBF7C084316F9A4.jpg','2021-03-05 01:06:34.059'),(691,1226003,8,'703BF39876F8699E4AB5E15A13F350C2.jpg','2021-03-05 01:06:34.084'),(692,1226004,7,'DA2CB515471D86A795B36763CF0CD6D1.jpg','2021-03-05 01:06:35.349'),(693,1226004,8,'B4D63FB510F61BA44F5FF3B48E11A548.jpg','2021-03-05 01:06:35.374'),(694,1226005,7,'5AF0447D758E72F53E5987A9455981B7.jpg','2021-03-05 01:06:36.542'),(695,1226005,8,'0A1E74F236DCC13A1D7239E08C103085.jpg','2021-03-05 01:06:36.564'),(696,1226006,7,'1BC36AE82188C0B875EA6C30982E9D54.jpg','2021-03-05 01:06:37.888'),(697,1226006,8,'1A8AC387148E0A6F9433A07F0FEF2672.jpg','2021-03-05 01:06:37.908'),(698,1226007,7,'D891CB03BE9E8AD346F438FF86113F72.jpg','2021-03-05 01:06:38.925'),(699,1226007,8,'A36F42F835E851F2673D976199B9A186.jpg','2021-03-05 01:06:38.945'),(700,1226008,7,'19E3367206ED2FCE67DAF9B2A7EE8385.jpg','2021-03-05 01:06:40.997'),(701,1226008,8,'AA2609CED1A1B5DD3697DAFA11B50584.jpg','2021-03-05 01:06:41.017'),(702,1226009,7,'DA01A8122101E3E01C60FFFC672535B4.jpg','2021-03-05 01:06:41.417'),(703,1226009,8,'918D40303EB6BC855AD873233F084C8D.jpg','2021-03-05 01:06:41.436'),(704,1226010,7,'D60FAB2BF9B57AFEB0C23EEE7D550FE6.jpg','2021-03-05 01:06:46.526'),(705,1226010,8,'36EED8BC3FE380EBAAA97D6F469AF345.jpg','2021-03-05 01:06:46.547'),(706,1226011,7,'4F00F4BEFE54E484830A3906FE55F95F.jpg','2021-03-05 01:06:48.361'),(707,1226011,8,'4517918EF2ADBAF5156CDC0188B9A0FE.jpg','2021-03-05 01:06:48.383'),(708,1226012,7,'0FCEB6BF70FC8E72510A382053BF064E.jpg','2021-03-05 01:06:48.917'),(709,1226012,8,'2CB3E204E6F77866C727A76212461F3F.jpg','2021-03-05 01:06:48.936'),(710,1226013,7,'FC0AD1586507DBB708021794D69FD05A.jpg','2021-03-05 01:06:50.610'),(711,1226013,8,'09791C18DB8FC98D490E37468D5300B7.jpg','2021-03-05 01:06:50.631'),(712,1226014,7,'960CB0F57373282DB4DBEDBF26BDE52C.jpg','2021-03-05 01:06:53.244'),(713,1226014,8,'162DF7E9A16003E0B857783F72754B4D.jpg','2021-03-05 01:06:53.263'),(714,1226015,7,'08DCC9612544489D41E87CB286A3C63E.jpg','2021-03-05 01:06:53.703'),(715,1226015,8,'ACF9EA3B9D2D1E1CAFC979EE683A7E97.jpg','2021-03-05 01:06:53.722'),(716,1226016,7,'7F18739D4E17605DC46258AADAD8D2EE.jpg','2021-03-05 01:06:55.566'),(717,1226016,8,'047676067DD04A3C396B0C260205D433.jpg','2021-03-05 01:06:55.586'),(718,1226017,7,'969FE3B271879D188D41B4307FD76D44.jpg','2021-03-05 01:06:56.416'),(719,1226017,8,'A6DBA0F3A0DA61AA6BD742D4A54211D4.jpg','2021-03-05 01:06:56.436'),(720,1226018,7,'57EF6172C83E948DE056920F39E4E3C6.jpg','2021-03-05 01:06:57.400'),(721,1226018,8,'6A82573C922B6242AF7ED1FDE51A312D.jpg','2021-03-05 01:06:57.421'),(722,1226019,7,'E82A8DB7F4B2530100C769B0A4B8937F.jpg','2021-03-05 01:06:58.406'),(723,1226019,8,'B5C8FF97CB0E4DA03FDE3C057CED36F0.jpg','2021-03-05 01:06:58.426'),(724,1226020,7,'AC6C608414AE9E614DC92815279F3787.jpg','2021-03-05 01:07:00.973'),(725,1226020,8,'37C148B035AA97CAD0700F536BBC233E.jpg','2021-03-05 01:07:00.992'),(726,1226021,7,'1D59DF4C725B1535861C53D3EBCE46AD.jpg','2021-03-05 01:07:02.967'),(727,1226021,8,'1CB7D13B630BB9D852A38878ECAF4F76.jpg','2021-03-05 01:07:02.986'),(728,1226022,7,'90E81C91913768233FCF4FA5060ACFB1.jpg','2021-03-05 01:07:05.216'),(729,1226022,8,'319CF72F5C97B0992CD470C5792BB8A4.jpg','2021-03-05 01:07:05.248'),(730,1226023,7,'40F879AC6250A96EAA0FE063DBB176F8.jpg','2021-03-05 01:07:07.464'),(731,1226023,8,'9D9086D92ED195FF942B09703431D374.jpg','2021-03-05 01:07:07.485'),(732,1226024,7,'C530BF8DDF2814309EDBE54F48726345.jpg','2021-03-05 01:07:14.910'),(733,1226024,8,'090233DC39AC0852599F0EBC1502C5B3.jpg','2021-03-05 01:07:14.931'),(734,1226025,7,'41F2BEE294F324C7ECBCAAABF03363A7.jpg','2021-03-05 01:07:22.887'),(735,1226025,8,'C24B42825146125B3C6C5E197FED5C3C.jpg','2021-03-05 01:07:23.151'),(736,1226026,7,'21F839C9B5A30701756B6D5015342E1D.jpg','2021-03-05 01:07:56.121'),(737,1226026,8,'E39F19A7F6702F2E54163A694183DCE4.jpg','2021-03-05 01:07:56.142'),(738,1226027,7,'2A60890E478C9C46B9754729A5A960C1.jpg','2021-03-05 01:07:56.576'),(739,1226027,8,'4636E41B898DFF28B9C1570BB8237C0E.jpg','2021-03-05 01:07:56.595'),(740,1226028,7,'FFA49E5471383637ACA3ECBEB360206B.jpg','2021-03-05 01:07:58.035'),(741,1226028,8,'23448F3B535A5690B263ED3AF9F81575.jpg','2021-03-05 01:07:58.056'),(742,1226029,7,'A6DEC92EB86E1EB5270BD7C898F3540E.jpg','2021-03-05 01:07:58.961'),(743,1226029,8,'739850A562939F12DBDF71644DC5D1EF.jpg','2021-03-05 01:07:58.980'),(744,1226030,7,'2777B01D4245DD0840E1A59C19D8C8E5.jpg','2021-03-05 01:08:08.476'),(745,1226030,8,'87DBD8A5DAD56480543A6218C09B216D.jpg','2021-03-05 01:08:08.536'),(746,1226031,7,'EF1E4D896D41C64F2511C06F043AA0D0.jpg','2021-03-05 01:08:11.049'),(747,1226031,8,'7C30093CE90B6EF4DA34795F84B5DF4C.jpg','2021-03-05 01:08:11.067'),(748,1226032,7,'DDBA71A7FC0B86E93F872915B088F7A8.jpg','2021-03-05 01:08:13.122'),(749,1226032,8,'93D5ED8C119B8B13D70E5EB1CFBB937C.jpg','2021-03-05 01:08:13.142'),(750,1226033,7,'9E09B85E3A9AA79E1138086C1023C738.jpg','2021-03-05 01:08:16.054'),(751,1226033,8,'CDE111583B1A366D869CACA50D5AA860.jpg','2021-03-05 01:08:16.081'),(752,1226034,7,'32D65D758B4DEDBB0656B1B37445CB89.jpg','2021-03-05 01:08:18.011'),(753,1226034,8,'4655821512CEE64A9D7DC99D7D8E1D68.jpg','2021-03-05 01:08:18.067'),(754,1226035,7,'57C7155E3EC7B5437A3BC6FFA7702883.jpg','2021-03-05 01:08:19.850'),(755,1226035,8,'5D85D88AE4BB5675ABE996B5BEFBAA54.jpg','2021-03-05 01:08:19.884'),(756,1226036,7,'678AC2340D1D07B6441B13459E7DC736.jpg','2021-03-05 01:08:22.490'),(757,1226036,8,'4B72E98D6C3BA6C563292ED75BD07E2F.jpg','2021-03-05 01:08:22.508'),(758,1226037,7,'B95ED2D71B9B4246733828AE7B57D355.jpg','2021-03-05 01:08:23.839'),(759,1226037,8,'6F0F519833445355C556DF68FA818CA0.jpg','2021-03-05 01:08:23.883'),(760,1226038,7,'9FC6BF5DBF3F123BB57518D5BF0E06CE.jpg','2021-03-05 01:08:26.517'),(761,1226038,8,'1126409F7FC72272855103DEFD424625.jpg','2021-03-05 01:08:26.538'),(762,1226039,7,'DBAAABF4F928F61C862F13FE3C1886D3.jpg','2021-03-05 01:08:28.481'),(763,1226039,8,'F365D7CB6A8729ADD9786C7FABE121A1.jpg','2021-03-05 01:08:28.504'),(764,1226040,7,'3F436CF798C8D47AE8662B7E94CD0039.jpg','2021-03-05 01:08:30.726'),(765,1226040,8,'742596874E0B08113413F1448841AC63.jpg','2021-03-05 01:08:30.746'),(766,1226041,7,'417E21BED4B28105E0CB09D5ABA95CDF.jpg','2021-03-05 01:08:36.092'),(767,1226041,8,'159F1F047EAC195BB24929CE41D606C9.jpg','2021-03-05 01:08:36.111'),(768,1226042,7,'CF533FDBFBE8D1D58CD034D1FD1B1832.jpg','2021-03-05 01:08:36.521'),(769,1226042,8,'524F33FF878EF310C234732A3D8512D3.jpg','2021-03-05 01:08:36.538'),(770,1226043,7,'28B7A0BF17C28A9FF842767B558E329D.jpg','2021-03-05 01:09:28.670'),(771,1226043,8,'974227199486F10A5658B64B419F339E.jpg','2021-03-05 01:09:28.694'),(772,1226044,7,'73007304F442EA4854ADF090002D4B44.jpg','2021-03-05 01:09:30.919'),(773,1226044,8,'D5470B99DF30599E07A239444997A49E.jpg','2021-03-05 01:09:30.939'),(774,1226045,7,'56BAC735A0A0CFEF08C549B008901165.jpg','2021-03-05 01:09:31.552'),(775,1226045,8,'986F4903A36575F693DE793282F131D7.jpg','2021-03-05 01:09:31.571'),(776,1226046,7,'98971DCEE7E328CEAC5A2E93E276AA1B.jpg','2021-03-05 01:09:33.955'),(777,1226046,8,'847BF589F2CC13CABB1023B86EBE14C8.jpg','2021-03-05 01:09:33.979'),(778,1226047,7,'CCD75296782762E3D3DBDA1A2D1C37E2.jpg','2021-03-05 01:09:34.735'),(779,1226047,8,'687153C12D0D7075BFA1E018962F5685.jpg','2021-03-05 01:09:34.754'),(780,1226048,7,'F90E37C38E7CEBB9ACF0485CEF0542AE.jpg','2021-03-05 01:09:37.333'),(781,1226048,8,'70797C2AF23175890087E4B83591BCFA.jpg','2021-03-05 01:09:37.353'),(782,1226049,7,'588EA0922E47C041A4A3B00CC7A605BF.jpg','2021-03-05 01:09:38.373'),(783,1226049,8,'90A9A234A8F67424371F8470224079C8.jpg','2021-03-05 01:09:38.391'),(784,1226050,7,'B44C875F65724B0CE65579125D25914B.jpg','2021-03-05 01:09:40.597'),(785,1226050,8,'A7DBB9D3DC5208E12087E9887D3E069F.jpg','2021-03-05 01:09:40.616'),(786,1226051,7,'AE78049B5CB2AA56292E566787E06B05.jpg','2021-03-05 01:09:41.732'),(787,1226051,8,'C8FA27F9C334E7D6545F53898B40418B.jpg','2021-03-05 01:09:41.751'),(788,1226052,7,'0E8A968E5141FE59E763B678A576A15A.jpg','2021-03-05 01:09:42.579'),(789,1226052,8,'7FFB7C9851540C120EAB22B5A79981E5.jpg','2021-03-05 01:09:42.602'),(790,1226053,7,'CADA8FC69FDB31C1EC5D9CBB5A2BA3E1.jpg','2021-03-05 01:09:43.718'),(791,1226053,8,'CBFAF17527656759B4EC1A4A8BBE1ED0.jpg','2021-03-05 01:09:43.736'),(792,1226054,7,'58ACC4AA0ED1F431522EFE578CC94881.jpg','2021-03-05 01:09:46.024'),(793,1226054,8,'6581CE6D30A304358D99CAFB5526BD9B.jpg','2021-03-05 01:09:46.048'),(794,1226055,7,'F5D8C5F4CAF9D907E1AEAA761319066D.jpg','2021-03-05 01:09:47.548'),(795,1226055,8,'D6B0B75DDD6007F669E7C616AA78ECDA.jpg','2021-03-05 01:09:47.568'),(796,1226056,7,'E96B6101CE3DB0A331F37963458E8024.jpg','2021-03-05 01:09:49.811'),(797,1226056,8,'C306F17AEC0E264B4DCC96EE0AE48EFB.jpg','2021-03-05 01:09:49.837'),(798,1226057,7,'9357831A7BB1433E82C2629AA47BBB7D.jpg','2021-03-05 01:09:51.840'),(799,1226057,8,'8E0EC5D86C7148C2856DF5BE7F860851.jpg','2021-03-05 01:09:51.861'),(800,1226058,7,'FD3AA6BD6C2FB9526B6C74FB882D3E46.jpg','2021-03-05 01:09:53.951'),(801,1226058,8,'024DDBBD1A76CEBABDA10C5846404536.jpg','2021-03-05 01:09:53.970'),(802,1226059,7,'EC4BA74DBAC9CBBC1C6998F2C4E8D1CD.jpg','2021-03-05 01:09:54.799'),(803,1226059,8,'C0C19799A822071BFAA431B1BFBDF8B1.jpg','2021-03-05 01:09:54.818'),(804,1226060,7,'63460CF21C218AEF6CB0F533275B717C.jpg','2021-03-05 01:09:59.616'),(805,1226060,8,'77C484B7D01FA6BE046ACBB385BAF281.jpg','2021-03-05 01:09:59.635'),(806,1226061,7,'A40B719A0F317AE818FA2160F6C1D9D0.jpg','2021-03-05 01:10:01.366'),(807,1226061,8,'D467B0D9A66618392200637295E8A289.jpg','2021-03-05 01:10:01.385'),(808,1226062,7,'35BCED87A5D39E8D32792E3BFA0568D1.jpg','2021-03-05 01:10:10.241'),(809,1226062,8,'B7E1FBF6930C0D5FECACD6C5F4C79428.jpg','2021-03-05 01:10:10.260'),(810,1226063,7,'0538FB92D81D4E8590624AD37680EA36.jpg','2021-03-05 01:10:22.891'),(811,1226063,8,'ABD43D37C52AD7C66919778C253E2408.jpg','2021-03-05 01:10:22.911'),(812,1226064,7,'41D84E21B8BC7069737DB5E123EB9C1C.jpg','2021-03-05 01:10:51.870'),(813,1226064,8,'222AB4892515342631422B4686296DB6.jpg','2021-03-05 01:10:51.896'),(814,1226065,7,'6938A32DC437AA3019B1F92749D6E98F.jpg','2021-03-05 01:10:55.408'),(815,1226065,8,'F6B834D232FADE06A683D3C47E261FE6.jpg','2021-03-05 01:10:55.433'),(816,1226066,7,'D5A14D366324CA3A4A84045F1F261140.jpg','2021-03-05 01:10:57.868'),(817,1226066,8,'144B628CB88FC63FD4DB7E8771E74694.jpg','2021-03-05 01:10:57.888'),(818,1226067,7,'CAFD839B834B4AF4857EA828653106F0.jpg','2021-03-05 01:10:59.073'),(819,1226067,8,'658EFAB86271F2790C2521780CFED16E.jpg','2021-03-05 01:10:59.093'),(820,1226068,7,'93C03A0194DEBECBCEBED1DCA7F4EDA5.jpg','2021-03-05 01:11:01.494'),(821,1226068,8,'AF07D796713BEEB762D6251A4E452541.jpg','2021-03-05 01:11:01.514'),(822,1226069,7,'BBEB34649690F67130FC4487F154F86E.jpg','2021-03-05 01:11:02.274'),(823,1226069,8,'3CAC69BF5CF4A2D5BE4F874872B86842.jpg','2021-03-05 01:11:02.298'),(824,1226070,7,'7290DEDC87BC81BED9D93C1945E2478D.jpg','2021-03-05 01:11:10.186'),(825,1226070,8,'FCDF4BBE6B92EC4060E8F5C1B4A9318B.jpg','2021-03-05 01:11:10.205'),(826,1226071,7,'E25EF4D4720B3C98681877E2360DC238.jpg','2021-03-05 01:11:14.897'),(827,1226071,8,'B01E6208E181EF39DDF5BE9236C9E870.jpg','2021-03-05 01:11:14.918'),(828,1226072,0,'E88B55833381E7343F84C31673A4BD55.mp4','2021-03-05 01:11:16.249'),(829,1226073,7,'77D57E478A1F834C374CABE604DB2056.jpg','2021-03-05 01:11:16.812'),(830,1226073,8,'7C0FC2388584B30D3E0618C610AAAD68.jpg','2021-03-05 01:11:16.830'),(831,1226074,7,'4C4AC0780218B6A943AACF72E829E166.jpg','2021-03-05 01:11:19.072'),(832,1226074,8,'94D63C2235356DF1FFF1B1468D22669F.jpg','2021-03-05 01:11:19.095'),(833,1226075,7,'EC392363742D188BDACB544495A067FC.jpg','2021-03-05 01:11:20.677'),(834,1226075,8,'AB31F17DB81D1645928BE2F52DB09B46.jpg','2021-03-05 01:11:20.696'),(835,1226076,7,'789C39A4DD2048DF66AD1A4DDB603E53.jpg','2021-03-05 01:11:23.088'),(836,1226076,8,'1FE490D31AEA2BC7CF169FAA65EC6262.jpg','2021-03-05 01:11:23.112'),(837,1226077,7,'AC013ACBEFBC148BC811CDA311DF81DA.jpg','2021-03-05 01:11:26.432'),(838,1226077,8,'4020F6DDC0B647561C3D293EA7BD0E43.jpg','2021-03-05 01:11:26.450'),(839,1226078,7,'BD959FA9EB843108A64ECA7B3EDF0B7A.jpg','2021-03-05 01:11:26.989'),(840,1226078,8,'14C8636DFDE173590571C25B9A743325.jpg','2021-03-05 01:11:27.008'),(841,1226079,7,'CC9B921033B2C80FB4824D1BECE2448F.jpg','2021-03-05 01:11:29.123'),(842,1226079,8,'0DA01D34803EFEA8A47016EBDAA25DE0.jpg','2021-03-05 01:11:29.143'),(843,1226080,7,'C63D58F798CA890861B4740FC01150B3.jpg','2021-03-05 01:11:35.783'),(844,1226080,8,'249E29E496EE220B5EE4DF78A7A8C0AD.jpg','2021-03-05 01:11:35.805'),(845,1226081,7,'D6B89813135D040B7EA63396CBC61C30.jpg','2021-03-05 01:11:41.323'),(846,1226081,8,'B2AF5148D90ECB2C11F0699C7D3342CF.jpg','2021-03-05 01:11:41.349'),(847,1226018,6,'71136C58A5CE47160ED655C599972D68.mp4','2021-03-05 01:11:58.972'),(848,1226019,6,'AFBA23DA45046AB81FBB636ACC8884B9.mp4','2021-03-05 01:12:00.308'),(849,1226020,6,'C7EA96ABF25F46837C0CE0AA9BEFC644.mp4','2021-03-05 01:12:01.672'),(850,1226021,6,'F16EEAD510457774868F06E77A454D89.mp4','2021-03-05 01:12:04.034'),(851,1226022,6,'03D28F6C822D9C5DE09F8C91F321BB3E.mp4','2021-03-05 01:12:06.346'),(852,1226023,6,'4C25E2BF98CC3CF8253B3F041FD87F0C.mp4','2021-03-05 01:12:08.673'),(853,1226024,6,'A3DE3D81305BCFD2664B2E2515B3E84D.mp4','2021-03-05 01:12:16.112'),(854,1226025,6,'5BABD2CB25F55BE5981B9C7CBB2AAB7C.mp4','2021-03-05 01:12:23.539'),(855,1226082,7,'EEE5DD3DE79EE675127AD4F11EAF57B1.jpg','2021-03-05 01:12:25.681'),(856,1226082,8,'1630C227C50974391C57CA5F200C6073.jpg','2021-03-05 01:12:25.700'),(857,1226083,7,'06CEF060397BB5C6D7BDFD4569F51CA1.jpg','2021-03-05 01:12:28.179'),(858,1226083,8,'DD617422E0E5E6D3B26A93D852B2FAFA.jpg','2021-03-05 01:12:28.201'),(859,1226084,7,'CDC83AE3A2E4EC935B02961CD38408D6.jpg','2021-03-05 01:12:28.883'),(860,1226084,8,'E72730A299457031D171AEE8E1D966C6.jpg','2021-03-05 01:12:28.901'),(861,1226085,7,'CF1091BEB7A37E8C65324EACF0F29EC8.jpg','2021-03-05 01:12:30.638'),(862,1226085,8,'872BC4CF328143243C998F2709015802.jpg','2021-03-05 01:12:30.656'),(863,1226086,7,'D84D534E38FB66FA32CF9DC9BBFC12B7.jpg','2021-03-05 01:12:33.756'),(864,1226086,8,'5A985DC4C35A56C37B799FD06E71724E.jpg','2021-03-05 01:12:33.775'),(865,1226087,7,'E28CA1A6E566B392070CE5D8C7A9934A.jpg','2021-03-05 01:12:35.863'),(866,1226087,8,'84BA3AFD33C5A0B5E441D222278AC3FE.jpg','2021-03-05 01:12:35.908'),(867,1226088,7,'55E5343C1EE8E98960518F36735114D3.jpg','2021-03-05 01:12:38.474'),(868,1226088,8,'CD56BBA8B0432B78ADA2B9DC722871C1.jpg','2021-03-05 01:12:38.494'),(869,1226089,7,'3BAFD7F2EE9B58B29052ED7521D41712.jpg','2021-03-05 01:12:40.722'),(870,1226089,8,'B7FBE28390B1AC5225CA415E418F5113.jpg','2021-03-05 01:12:40.740'),(871,1226090,7,'0ED2E8440DB45806589ABC9259253A51.jpg','2021-03-05 01:12:42.114'),(872,1226090,8,'690DB82794C8F6CF3BA80F09912B2DF3.jpg','2021-03-05 01:12:42.133'),(873,1226091,7,'B416204D3E3BE0AB47DCD1FFA3D6569E.jpg','2021-03-05 01:12:42.570'),(874,1226091,8,'F0DF9509CFB7E77F2C8B74C189F76C99.jpg','2021-03-05 01:12:42.588'),(875,1226092,7,'20E03D4AB745E6DCA16D588258249628.jpg','2021-03-05 01:12:44.814'),(876,1226092,8,'A8ACD1CE822D9F42B03EFA08814FE019.jpg','2021-03-05 01:12:44.837'),(877,1226093,7,'3820DFA18F55648E33F68FC8696F1363.jpg','2021-03-05 01:12:54.331'),(878,1226093,8,'FD85897A809510C4D56178BC27C39D74.jpg','2021-03-05 01:12:54.351'),(879,1226026,6,'DDD3C0620F94FBA3D931A3ED8125E9B4.mp4','2021-03-05 01:12:56.479'),(880,1226027,6,'DDD3C0620F94FBA3D931A3ED8125E9B4.mp4','2021-03-05 01:12:57.816'),(881,1226028,6,'26B58A134DD3B5EE3FF67A03C51C8141.mp4','2021-03-05 01:12:59.859'),(882,1226029,6,'909E7B66AF23AE0C67880375FBB9608B.mp4','2021-03-05 01:13:00.295'),(883,1226094,7,'FCC7E0347BCA606A249A651A8B0DCAA2.jpg','2021-03-05 01:13:03.055'),(884,1226094,8,'8EDD614F9226BA99DC3F2DD7200372E8.jpg','2021-03-05 01:13:03.191'),(885,1226030,6,'8CF24B1A1AB47AE2BCD7F5BF8163A8ED.mp4','2021-03-05 01:13:09.792'),(886,1226031,6,'BF40CE9246035A968C05D442E2380FB2.mp4','2021-03-05 01:13:12.227'),(887,1226032,6,'290A432CDF6720CF2D58571E0C5F02F3.mp4','2021-03-05 01:13:13.690'),(888,1226033,6,'8ABEE85AD914C696640373A2BF022980.mp4','2021-03-05 01:13:17.296'),(889,1226034,6,'65EF50154F3D390AADB6094CA417C158.mp4','2021-03-05 01:13:18.807'),(890,1226035,6,'80E1D322691CCE690B733741FA22D9C2.mp4','2021-03-05 01:13:21.257'),(891,1226036,6,'379058FD03E8D64BFCD08768D0F1D44C.mp4','2021-03-05 01:13:23.881'),(892,1226037,6,'785A3CD940E4D318B1B73165FF003CBE.mp4','2021-03-05 01:13:25.762'),(893,1226038,6,'D27369B8A9F709E28C4D988FAEE7E670.mp4','2021-03-05 01:13:27.153'),(894,1226039,6,'18BD8EE11EA19EC3FF0FD6F90E0DF8B4.mp4','2021-03-05 01:13:29.573'),(895,1226040,6,'BFA9E7F5AF81EE193D8CD0C479480ADC.mp4','2021-03-05 01:13:32.000'),(896,1226041,6,'BDC996561A720CD4D0CC4D348F29555A.mp4','2021-03-05 01:13:37.385'),(897,1226042,6,'BDC996561A720CD4D0CC4D348F29555A.mp4','2021-03-05 01:13:37.681'),(898,1226095,7,'A310ED5D9EC0F392861B69C4C26C5194.jpg','2021-03-05 01:13:48.574'),(899,1226095,8,'D0E01B99789BB38E011F18F23E792786.jpg','2021-03-05 01:13:48.595'),(900,1226096,7,'66057A3580729751685D24B13A1D1B62.jpg','2021-03-05 01:13:50.807'),(901,1226096,8,'D9B02090169AB1FE03F1413300290C8E.jpg','2021-03-05 01:13:50.831'),(902,1226097,7,'F82E5144AD6A8A1395222A23861AAB4A.jpg','2021-03-05 01:13:58.095'),(903,1226097,8,'758039A677A01E62DCACE03EDB8734FA.jpg','2021-03-05 01:13:58.126'),(904,1226098,7,'8BFBD746BF000AADAE01F6ABDF5440EA.jpg','2021-03-05 01:13:58.887'),(905,1226098,8,'344395D6B3C85E05E5157EF9A601F176.jpg','2021-03-05 01:13:58.910'),(906,1226099,7,'BCCE49EFE4E9D672CA3BCC7AFC43B4EA.jpg','2021-03-05 01:14:02.833'),(907,1226099,8,'ABF6872CCF854128270DD1591B87FD7D.jpg','2021-03-05 01:14:02.856'),(908,1226100,7,'FD9B63B5556BEA3213E27EAD67CD4647.jpg','2021-03-05 01:14:03.368'),(909,1226100,8,'36F330ABE9B1E18B9508D6D5DBC64420.jpg','2021-03-05 01:14:03.390'),(910,1226101,7,'D998DF0696C0EA4DD5DB8973A0A53140.jpg','2021-03-05 01:14:04.877'),(911,1226101,8,'FC34989828B1050D09FFCACAD69D9418.jpg','2021-03-05 01:14:04.898'),(912,1226102,7,'AB7929DDDD31F3D0A21AE9176C832246.jpg','2021-03-05 01:14:08.202'),(913,1226102,8,'C61069328A0042E44EAC900638684AE9.jpg','2021-03-05 01:14:08.224'),(914,1226103,7,'7013EB8F7AC3DFF259F111515561AB0A.jpg','2021-03-05 01:14:12.411'),(915,1226103,8,'8BCD536319C48ECAA542E4211E1549A6.jpg','2021-03-05 01:14:12.436'),(916,1226104,7,'70C30C4419DE3A488AD5CF82E0BB9886.jpg','2021-03-05 01:14:19.708'),(917,1226104,8,'FF3A0A03DF9C5BB143745BF1AEEF5F2D.jpg','2021-03-05 01:14:19.728'),(918,1226105,7,'F7829FE3FB2B3335977E2CC0D2221E2C.jpg','2021-03-05 01:14:28.990'),(919,1226105,8,'50CA763041C38BE43050DD91EA70C9EE.jpg','2021-03-05 01:14:29.020'),(920,1226043,6,'27ED2CBF3ECAFF89208EDF4A2E9D8889.mp4','2021-03-05 01:14:29.811'),(921,1226044,6,'04B8DA6CC7A7E51F21D655C932C66C13.mp4','2021-03-05 01:14:32.160'),(922,1226045,6,'48D005753225E71CB41B93EC596C3CA0.mp4','2021-03-05 01:14:32.455'),(923,1226046,6,'B092F01995159697A73F0AD8651718DD.mp4','2021-03-05 01:14:34.820'),(924,1226047,6,'11144AA47373C334299DEBAB305862C5.mp4','2021-03-05 01:14:36.140'),(925,1226048,6,'D5098BF287976DF33B9956B50FBF99F9.mp4','2021-03-05 01:14:38.504'),(926,1226049,6,'D9068E6B0E7F0A2FF7902B5CED15060C.mp4','2021-03-05 01:14:39.830'),(927,1226106,7,'8CE940CFB0AE17BDCC17B3D77C771893.jpg','2021-03-05 01:14:40.032'),(928,1226106,8,'9493641141521803F764BC3148B2B129.jpg','2021-03-05 01:14:40.056'),(929,1226107,7,'C9BB648EC38AF57B80CAC7A8B3E4C48C.jpg','2021-03-05 01:14:41.131'),(930,1226107,8,'26D62655C793F958F37DA37EAF9282C9.jpg','2021-03-05 01:14:41.152'),(931,1226050,6,'366F1D90AD6F14018A634D6E8763051F.mp4','2021-03-05 01:14:41.200'),(932,1226051,6,'366F1D90AD6F14018A634D6E8763051F.mp4','2021-03-05 01:14:42.519'),(933,1226052,6,'9A90C019EABA6715B787E1A27B7699B9.mp4','2021-03-05 01:14:43.872'),(934,1226053,6,'81E8FDB7430781A350A0C0F587FF5F42.mp4','2021-03-05 01:14:45.172'),(935,1226054,6,'7B01F9EE842362AE322440D7557B0290.mp4','2021-03-05 01:14:46.528'),(936,1226055,6,'2088ECA695BF5DD7A5250274D4DC7718.mp4','2021-03-05 01:14:48.870'),(937,1226056,6,'7751EB138147AE78DBB60469F87B399D.mp4','2021-03-05 01:14:50.174'),(938,1226057,6,'5DBB04B59FC733797DCCF4CB99F22D0A.mp4','2021-03-05 01:14:52.527'),(939,1226058,6,'1B5BD1D6D591FFF822E83AEC3059DA9A.mp4','2021-03-05 01:14:54.897'),(940,1226059,6,'DEBB14C8901544E73F26EF3F739716C9.mp4','2021-03-05 01:14:56.207'),(941,1226060,6,'CD12020F15995E780459817A31E55910.mp4','2021-03-05 01:15:00.605'),(942,1226061,6,'B59691988E4C172E562656D29FCA8A3E.mp4','2021-03-05 01:15:01.919'),(943,1226062,6,'49AB7D51DACE7D1653C19E0B8737F0DB.mp4','2021-03-05 01:15:11.405'),(944,1226063,6,'5777ED5F0CF3FAC75D8BBBE85AB9789E.mp4','2021-03-05 01:15:23.904'),(945,1226108,7,'EF3932A3E49D4A3FAD120AD32F6701F8.jpg','2021-03-05 01:15:25.505'),(946,1226108,8,'C6A78AA47C0E03D8E3EFE4B560E6B37B.jpg','2021-03-05 01:15:25.523'),(947,1226109,7,'6DEADB68EC474DFD87466D719FCD2AC2.jpg','2021-03-05 01:15:28.105'),(948,1226109,8,'B4E55ECC4D4E9DDBE8E8DC62DD78CE4E.jpg','2021-03-05 01:15:28.124'),(949,1226110,7,'BB660FC679B6710CC78E43FF54E656B0.jpg','2021-03-05 01:15:29.205'),(950,1226110,8,'BCD16911D2902808FB7BE56A300D4AA3.jpg','2021-03-05 01:15:29.224'),(951,1226111,7,'B7352F88F1C6334742A4CF16AD1ECB91.jpg','2021-03-05 01:15:31.625'),(952,1226111,8,'DA3A42414B89E756CFC2CB31A2EC4608.jpg','2021-03-05 01:15:31.666'),(953,1226112,7,'D744A80621242F991EED8972F0183F25.jpg','2021-03-05 01:15:34.264'),(954,1226112,8,'8A6A5E1A0001C9D4A91C894A0130D733.jpg','2021-03-05 01:15:34.283'),(955,1226113,7,'C4F24FC356959A493FD8215348A03081.jpg','2021-03-05 01:15:34.728'),(956,1226113,8,'B3ECCA9AE1A008D9CEC45703B7E8FD45.jpg','2021-03-05 01:15:34.747'),(957,1226114,7,'7390EE9B3DB883ECCD7A4758D97ECDB6.jpg','2021-03-05 01:15:36.351'),(958,1226114,8,'6AA89AF731AE17EC0C83E6355802EEB6.jpg','2021-03-05 01:15:36.371'),(959,1226115,7,'1E18663CB9C7B1B8DDF2F586572911E7.jpg','2021-03-05 01:15:37.793'),(960,1226115,8,'E4F64298BDEAD27FBE3F608225F035CB.jpg','2021-03-05 01:15:37.812'),(961,1226116,7,'990CC6204F6495AF3C96700B4B8EFBF0.jpg','2021-03-05 01:15:38.221'),(962,1226116,8,'F348626C1B9570BED24A811DB925DBD6.jpg','2021-03-05 01:15:38.237'),(963,1226117,7,'A09CFDFE749D80E048F55B1A09562617.jpg','2021-03-05 01:15:40.880'),(964,1226117,8,'C58F2BECE928F589F9A23F8EF1569463.jpg','2021-03-05 01:15:40.899'),(965,1226118,7,'5420E9096F68019CD04FDE9775B818BE.jpg','2021-03-05 01:15:42.408'),(966,1226118,8,'7FE8F1DEBA60E1E02CB2462798EEED15.jpg','2021-03-05 01:15:42.438'),(967,1226119,7,'0B4A346A916A0F2654758414CD3C249E.jpg','2021-03-05 01:15:43.126'),(968,1226119,8,'8E728F1455F3E660A2D37E2CAF1F792D.jpg','2021-03-05 01:15:43.145'),(969,1226120,7,'2AEBF1177A81E1049F2E3A5D4E716152.jpg','2021-03-05 01:15:48.346'),(970,1226120,8,'7AE5C3259021E2244C7F251B46B1C6E0.jpg','2021-03-05 01:15:48.364'),(971,1226064,6,'7CD032A3D0AEB82F71F03AE66757C84F.mp4','2021-03-05 01:15:52.677'),(972,1226065,6,'2B56F821FDA31D93B6EB993A5A978EDD.mp4','2021-03-05 01:15:56.033'),(973,1226066,6,'A1CB18377682368513E8FFD2E5AEA9B4.mp4','2021-03-05 01:15:58.405'),(974,1226067,6,'C5978550A508C0183B3EBFA715E6E62B.mp4','2021-03-05 01:15:59.770'),(975,1226068,6,'88B0A330761382F660250C532F700086.mp4','2021-03-05 01:16:02.115'),(976,1226069,6,'88B0A330761382F660250C532F700086.mp4','2021-03-05 01:16:03.456'),(977,1226070,6,'204E2588FD90123106F24E298CB98926.mp4','2021-03-05 01:16:10.852'),(978,1226071,6,'D3BA8C3EFEA81F5E7E74C710A168BEF8.mp4','2021-03-05 01:16:15.247'),(979,1226121,0,'78C453EADB17E35495A304F438037EA3.mp4','2021-03-05 01:16:17.107'),(980,1226073,6,'0F0CFFC1CDA3D78A6DDC657EF12BE7BF.mp4','2021-03-05 01:16:17.564'),(981,1226074,6,'4211A9F9C3739BF7158703F60D641950.mp4','2021-03-05 01:16:19.961'),(982,1226122,7,'0D3E7BD9C8D6084D56FB6F2EA7A03BA4.jpg','2021-03-05 01:16:20.723'),(983,1226122,8,'7160AE3398550ABDFD8659B2E851EF0B.jpg','2021-03-05 01:16:20.743'),(984,1226075,6,'F6F30EC524627E6B113B67AEF3A4024F.mp4','2021-03-05 01:16:21.292'),(985,1226076,6,'4B279E07073E982A42164CD0599FA3C3.mp4','2021-03-05 01:16:23.646'),(986,1226077,6,'8075BA670025DDD350FA236FAF634D30.mp4','2021-03-05 01:16:26.979'),(987,1226078,6,'8075BA670025DDD350FA236FAF634D30.mp4','2021-03-05 01:16:28.294'),(988,1226123,7,'E31C2AA7F0A450714EE6DF19767A00A2.jpg','2021-03-05 01:16:28.676'),(989,1226123,8,'A43243E0BC662711FFBDBEDA489B6CDB.jpg','2021-03-05 01:16:28.694'),(990,1226079,6,'B9626F0023EA4873DD83ECEC622ED54D.mp4','2021-03-05 01:16:29.655'),(991,1226124,7,'18542628C78A8F4188925B32A800DC89.jpg','2021-03-05 01:16:35.364'),(992,1226124,8,'505078C5D968393DD30809B035108082.jpg','2021-03-05 01:16:35.383'),(993,1226080,6,'DDBA9615A206DE5611FEA8584D806D03.mp4','2021-03-05 01:16:37.057'),(994,1226081,6,'1C32CAE8512754248710CCFAE2F1C69C.mp4','2021-03-05 01:16:42.422'),(995,1226125,7,'F9459CD74A558D098DEDDAF85598638F.jpg','2021-03-05 01:16:50.349'),(996,1226125,8,'65A213BE17B627C2523AEA8A4D0234EC.jpg','2021-03-05 01:16:50.371'),(997,1226126,7,'1ABB1230EE8CCE174AB37E7F5AEDDCF1.jpg','2021-03-05 01:16:51.754'),(998,1226126,8,'DAB5DFD6C5A7312EC233ADD9087865E9.jpg','2021-03-05 01:16:51.775'),(999,1226127,7,'E836BE218A408C2BB419417A8315F17C.jpg','2021-03-05 01:16:59.686'),(1000,1226127,8,'8A6F276E29F60EDDB982A089FBFBD7EF.jpg','2021-03-05 01:16:59.704'),(1001,1226128,7,'A0C8AD99D8D174D0F05CA7AF16E3A972.jpg','2021-03-05 01:17:04.490'),(1002,1226128,8,'F0E2FE0DF8A4EAE314B1EFDDB1EE583A.jpg','2021-03-05 01:17:04.510'),(1003,1226129,7,'51A4F6B1329103A949AEF342A25C280B.jpg','2021-03-05 01:17:05.989'),(1004,1226129,8,'8EA34D778F4C87FDED3B55BF05D0BB8F.jpg','2021-03-05 01:17:06.057'),(1005,1226130,7,'4E3717963D9246425127A7EA2AA4EA5C.jpg','2021-03-05 01:17:08.249'),(1006,1226130,8,'3A870F01D6EC2791FB6DA4723E01CC7E.jpg','2021-03-05 01:17:08.266'),(1007,1226131,7,'D2E4F98575A3CD8BFBE5103850B1B8D9.jpg','2021-03-05 01:17:09.636'),(1008,1226131,8,'41AFE84CA0EE2725BB4AD20B11914AC2.jpg','2021-03-05 01:17:09.655'),(1009,1226132,7,'9D25710254788A4235F07215592616C0.jpg','2021-03-05 01:17:10.756'),(1010,1226132,8,'E5252B539B65BDFCF10C3CC749CBAF8F.jpg','2021-03-05 01:17:10.774'),(1011,1226133,7,'FF4714133AC8C799E949751712D4CD47.jpg','2021-03-05 01:17:12.003'),(1012,1226133,8,'73B0463E1852F27A3DDED5D17B16EA75.jpg','2021-03-05 01:17:12.020'),(1013,1226134,7,'9E289E20D55F5DA9854E196737F35C45.jpg','2021-03-05 01:17:13.851'),(1014,1226134,8,'282FD1AA22971724B798920805AE6639.jpg','2021-03-05 01:17:13.869'),(1015,1226135,7,'AF800BCAB777E590D0AE16FC279EF938.jpg','2021-03-05 01:17:17.923'),(1016,1226135,8,'BF86061F9CDC73DAB5BDBB216919BB91.jpg','2021-03-05 01:17:17.942'),(1017,1226136,7,'1B8D040D1F5C1C3B9590C1AEB8B0EF47.jpg','2021-03-05 01:17:20.237'),(1018,1226136,8,'A466D20EC8C71A4118DF8BC9D2027313.jpg','2021-03-05 01:17:20.256'),(1019,1226137,7,'2BD59BA628092E5B87D0043592CFF5D7.jpg','2021-03-05 01:17:21.636'),(1020,1226137,8,'7BBBA92471329E074FB6C861B456D052.jpg','2021-03-05 01:17:21.655'),(1021,1226138,7,'FA727EEC3A009B5B334DE92F8EB9AEE9.jpg','2021-03-05 01:17:22.738'),(1022,1226138,8,'35F71F15B810DF95ED52011734DBEA6D.jpg','2021-03-05 01:17:22.780'),(1023,1226139,7,'61DB4FE2C8F1A3A8669B30BEB8622333.jpg','2021-03-05 01:17:24.829'),(1024,1226139,8,'F65E30B09C0BCB02794CE5DF879984DB.jpg','2021-03-05 01:17:24.850'),(1025,1226140,7,'2BBC0891772C57952A51DC2E439C96A1.jpg','2021-03-05 01:17:25.158'),(1026,1226140,8,'0EA1C2F4CAD695BF9C909DB8AEF594C3.jpg','2021-03-05 01:17:25.178'),(1027,1226082,6,'98E697D2194AA4CBDE65DCDE989D92B3.mp4','2021-03-05 01:17:26.402'),(1028,1226141,7,'420812E627D0F9BE4BE7A8F97ABB98D6.jpg','2021-03-05 01:17:26.985'),(1029,1226141,8,'7620C694F8817B3F17AE9AD3A01950A5.jpg','2021-03-05 01:17:27.002'),(1030,1226083,6,'ACC2969835871D6633ABEAD75334E7C5.mp4','2021-03-05 01:17:28.900'),(1031,1226142,7,'39AA8F7494FEF5C1A4F8A5D03C7307B9.jpg','2021-03-05 01:17:29.363'),(1032,1226142,8,'0B61ACB3003EC8E5E4191529A8A4A0DE.jpg','2021-03-05 01:17:29.384'),(1033,1226084,6,'5FD59367726573957ED27C3508931461.mp4','2021-03-05 01:17:30.226'),(1034,1226085,6,'837BC8DD49D940C939D69B120F0163C7.mp4','2021-03-05 01:17:31.554'),(1035,1226143,7,'16740BB6F24D9CDDE73D5E4365AE01CB.jpg','2021-03-05 01:17:32.410'),(1036,1226143,8,'A6CE6BEFEC2F250FB7673F457E0DAC98.jpg','2021-03-05 01:17:32.429'),(1037,1226086,6,'A14ECBAE1A212DD4795848387992C5EE.mp4','2021-03-05 01:17:34.966'),(1038,1226087,6,'B29CD4D95D829CBBE25C5A31CE38E83A.mp4','2021-03-05 01:17:36.298'),(1039,1226088,6,'B0F772CEAECD1841BE36BB01564A85E6.mp4','2021-03-05 01:17:39.671'),(1040,1226089,6,'A6785B0B7C4B08EFC90A17390AD41C90.mp4','2021-03-05 01:17:41.977'),(1041,1226090,6,'BCADD49647506688ADDD3FCA55448852.mp4','2021-03-05 01:17:43.298'),(1042,1226091,6,'828C561B5301BF3C23B4E22F1C17A351.mp4','2021-03-05 01:17:43.613'),(1043,1226092,6,'250E65E006465B406061334F6C857243.mp4','2021-03-05 01:17:45.985'),(1044,1226093,6,'586F7F316DD85DBE43B2A0609D1EBEA4.mp4','2021-03-05 01:17:55.396'),(1045,1226094,6,'45C97BD59DC6B7370751B940E6A23C40.mp4','2021-03-05 01:18:04.842'),(1046,1226144,7,'6542AC8B7FC656AFFC7FCA28A8FA44E6.jpg','2021-03-05 01:18:19.385'),(1047,1226144,8,'2827A342E68CB77B3C42B69E35A0565B.jpg','2021-03-05 01:18:19.402'),(1048,1226145,7,'E2CDD7AE4413DBBB75E2270D0D26F5E5.jpg','2021-03-05 01:18:20.108'),(1049,1226145,8,'45E65341B92A46641A0079061B0DFDB3.jpg','2021-03-05 01:18:20.128'),(1050,1226146,7,'EF633F0F98DC154C12F51D7308170D77.jpg','2021-03-05 01:18:22.600'),(1051,1226146,8,'719CBF15922272C62AF0C0733EA7AA5D.jpg','2021-03-05 01:18:22.616'),(1052,1226147,7,'8CE20E2D3C4D9950CEB2B59045CAEC7F.jpg','2021-03-05 01:18:23.272'),(1053,1226147,8,'C36A923DA2A76AC15029BF1AEF46776C.jpg','2021-03-05 01:18:23.291'),(1054,1226148,7,'4811D79450D53040EEAA4F604D5777E2.jpg','2021-03-05 01:18:25.795'),(1055,1226148,8,'3F3D30F8BE2B900A6B1286E5FACBEB08.jpg','2021-03-05 01:18:25.813'),(1056,1226149,7,'86E3A17EFFA8FA94C41A0087AB6FA085.jpg','2021-03-05 01:18:26.500'),(1057,1226149,8,'8AEA787E7D226595CC0FDCC9F6A0E376.jpg','2021-03-05 01:18:26.520'),(1058,1226150,7,'49C0B30AD0C001808C2E5E6262D18F9E.jpg','2021-03-05 01:18:27.627'),(1059,1226150,8,'9F1E1B541B19784AC2520745B17E1A98.jpg','2021-03-05 01:18:27.645'),(1060,1226151,7,'2EB105CADF8C92317BB257CADEA90704.jpg','2021-03-05 01:18:28.399'),(1061,1226151,8,'2970B46F891A801C4C3AD6C1A640978A.jpg','2021-03-05 01:18:28.414'),(1062,1226152,7,'0A0065C575FD2F75839920C0DF99C287.jpg','2021-03-05 01:18:30.250'),(1063,1226152,8,'1C9B8B5E9C1FF0EED232C8FC9C30816B.jpg','2021-03-05 01:18:30.267'),(1064,1226153,7,'03FCD2BD2F51C1ACBE2A9FE9A32C7A1A.jpg','2021-03-05 01:18:37.228'),(1065,1226153,8,'E6091341F845B8A35E29334C2C54D120.jpg','2021-03-05 01:18:37.251'),(1066,1226095,6,'0FAE5BEC7448AC8C7C21F0B1F722D233.mp4','2021-03-05 01:18:49.788'),(1067,1226154,7,'7718D1582203FE0BB29117A85C3C67D6.jpg','2021-03-05 01:18:51.579'),(1068,1226154,8,'B061035C85811A4AE150860D3F192B15.jpg','2021-03-05 01:18:51.602'),(1069,1226096,6,'E6FFB2B72BC587063645FD2F530F4372.mp4','2021-03-05 01:18:52.128'),(1070,1226155,7,'59D5DB0C8594A6B3458851235774FE78.jpg','2021-03-05 01:18:52.511'),(1071,1226155,8,'EDBB99E7005948AF6548E7B2FF66B637.jpg','2021-03-05 01:18:52.530'),(1072,1226156,7,'292B93B25E81BBF2A1EEF3027AA88ED5.jpg','2021-03-05 01:18:55.276'),(1073,1226156,8,'B85E2718354BCE27B947A9F263988DD6.jpg','2021-03-05 01:18:55.293'),(1074,1226157,7,'1F69BE0A1C03964B5E68E86D7D820B05.jpg','2021-03-05 01:18:58.160'),(1075,1226157,8,'5E8860F9CD609EECDB782E5C9E6BC3B4.jpg','2021-03-05 01:18:58.178'),(1076,1226097,6,'24EE693FEC68A72EFB4071E4784E8584.mp4','2021-03-05 01:18:58.595'),(1077,1226098,6,'A760FEC8CCFB2A1FF4EBA2A3D0C8D66C.mp4','2021-03-05 01:18:59.912'),(1078,1226099,6,'0266738326F8A38ABAF3D8EE2CD312A0.mp4','2021-03-05 01:19:03.285'),(1079,1226100,6,'0266738326F8A38ABAF3D8EE2CD312A0.mp4','2021-03-05 01:19:04.581'),(1080,1226101,6,'B806322B584A0B2600942FDFF6402E93.mp4','2021-03-05 01:19:05.892'),(1081,1226102,6,'9EF0D3FDE76A0424296E936BE574B800.mp4','2021-03-05 01:19:09.278'),(1082,1226103,6,'353562B4C3757BED9D33448E2D736B16.mp4','2021-03-05 01:19:13.604'),(1083,1226158,7,'C805502039570DE98E85CE842CE18C1B.jpg','2021-03-05 01:19:19.853'),(1084,1226158,8,'EEA92712C7E3B175B41A6603F01ED0D9.jpg','2021-03-05 01:19:19.870'),(1085,1226104,6,'C5B79DC4DF65D73A78FDB81D115537D4.mp4','2021-03-05 01:19:21.085'),(1086,1226159,7,'25F934C8F41C4A01A2F2E08FB138A43B.jpg','2021-03-05 01:19:23.550'),(1087,1226159,8,'7BEEEF2A1EB16BCD84B0F013660ADB72.jpg','2021-03-05 01:19:23.590'),(1088,1226105,6,'B89E3CB5ED77CCDCE99BEBB1197C44F5.mp4','2021-03-05 01:19:29.480'),(1089,1226106,6,'72B3A4A5818EBD63DFE037EB42BA7980.mp4','2021-03-05 01:19:40.923'),(1090,1226107,6,'A8DAEF39E89B73F3CEC8858D562C3521.mp4','2021-03-05 01:19:42.310'),(1091,1226160,7,'013FD7835FC83B84D88E9453FE5BD49C.jpg','2021-03-05 01:19:47.309'),(1092,1226160,8,'87D58511449C2FD830DE196815795B32.jpg','2021-03-05 01:19:47.328'),(1093,1226161,7,'0739CEEE5A4EE65A02E3F0A42B0C5372.jpg','2021-03-05 01:19:49.713'),(1094,1226161,8,'24A089164F7E3DC42B5C6F3C5ADC1988.jpg','2021-03-05 01:19:49.733'),(1095,1226162,7,'A5CD91EDD4F243DF4E73E03FB8BFF6BA.jpg','2021-03-05 01:19:50.247'),(1096,1226162,8,'D842C022F0FE27C2874076B4C67F0CB3.jpg','2021-03-05 01:19:50.266'),(1097,1226163,7,'3C7A6C2352DC4BD0B9F0E3C51EBC9835.jpg','2021-03-05 01:19:52.179'),(1098,1226163,8,'BDD9BF08E7DF4AD39D702FB45AD7FBCA.jpg','2021-03-05 01:19:52.197'),(1099,1226164,7,'A87C3AE128A4F4274FC5704B86B8F365.jpg','2021-03-05 01:19:52.684'),(1100,1226164,8,'F7E22F0C03FA5E321EE92811825E1FA4.jpg','2021-03-05 01:19:52.699'),(1101,1226165,7,'F3261CAFC569A0BF3A825BC052DFA76B.jpg','2021-03-05 01:19:55.143'),(1102,1226165,8,'5336EFE03680FC03CB391FB98272551C.jpg','2021-03-05 01:19:55.161'),(1103,1226166,7,'5062B9F03C02680EB8312881C37BF526.jpg','2021-03-05 01:19:56.084'),(1104,1226166,8,'C4FD55CF56420FF063F3831AD89C4604.jpg','2021-03-05 01:19:56.112'),(1105,1226167,7,'CC74FDBDA8129A3F5FD9CEEEA8FE179A.jpg','2021-03-05 01:19:57.754'),(1106,1226167,8,'E875DAB904CBA90F82D0170766169AE2.jpg','2021-03-05 01:19:57.772'),(1107,1226168,7,'31263DB8B280364CF848CF112D400969.jpg','2021-03-05 01:20:03.297'),(1108,1226168,8,'DCF75F1C0E3467BB6CDED2BBDA71D1C7.jpg','2021-03-05 01:20:03.315'),(1109,1226169,7,'E46E3FBD30E23747ED7C40F9EF094507.jpg','2021-03-05 01:20:06.820'),(1110,1226169,8,'18F29ACC4C36F6A73F856160C67845B0.jpg','2021-03-05 01:20:06.837'),(1111,1226170,7,'E7E6C976A2C25D9AFE8DE7E21D22245C.jpg','2021-03-05 01:20:07.219'),(1112,1226170,8,'7C00D25A241BA5C19EC0D10C296F8E02.jpg','2021-03-05 01:20:07.240'),(1113,1226171,7,'4866E71869B9A0F1484A0046E6EA269E.jpg','2021-03-05 01:20:12.729'),(1114,1226171,8,'13BC9C81486555E993D408C9F3AE3D9F.jpg','2021-03-05 01:20:12.750'),(1115,1226172,7,'9349A906158934C5B625992B45B5F5F4.jpg','2021-03-05 01:20:15.484'),(1116,1226172,8,'BAEDED0D0B77BD82103A558A5AF15421.jpg','2021-03-05 01:20:15.502'),(1117,1226173,7,'6FD2014C38F5F99C9D932B1C97F6EA84.jpg','2021-03-05 01:20:16.140'),(1118,1226173,8,'EA1A46AF67A685E8C47933C8E8D2CC20.jpg','2021-03-05 01:20:16.157'),(1119,1226108,6,'3A09DAE34E22F5D8BCE244A678933BB8.mp4','2021-03-05 01:20:26.237'),(1120,1226109,6,'EE88F42866E1161E58280DB8412FE213.mp4','2021-03-05 01:20:28.665'),(1121,1226110,6,'EE88F42866E1161E58280DB8412FE213.mp4','2021-03-05 01:20:30.106'),(1122,1226111,6,'DCF425FBB078DEA78A687451B44A491A.mp4','2021-03-05 01:20:32.523'),(1123,1226174,7,'C173AB8DBEA1A52D02A80340F1F0E7F8.jpg','2021-03-05 01:20:33.829'),(1124,1226174,8,'227C9D93704837DC3DF7DA98A1D71718.jpg','2021-03-05 01:20:33.942'),(1125,1226112,6,'94D39AC5E4C61DCE8FCFA6400E76B02B.mp4','2021-03-05 01:20:34.950'),(1126,1226113,6,'A972CAC00FF2E68E376FB81D21B5D2D9.mp4','2021-03-05 01:20:36.296'),(1127,1226114,6,'965B259ABD9BA0D0D97177847C21C615.mp4','2021-03-05 01:20:37.648'),(1128,1226115,6,'DF6E8696E46C7AD30357505DC438EC00.mp4','2021-03-05 01:20:39.005'),(1129,1226116,6,'DF6E8696E46C7AD30357505DC438EC00.mp4','2021-03-05 01:20:39.364'),(1130,1226175,7,'37296FC9CA30BBE2909AD98F56CF0E83.jpg','2021-03-05 01:20:39.836'),(1131,1226175,8,'2C0FA8EC6EE0858805D90A08A4CA6A07.jpg','2021-03-05 01:20:39.879'),(1132,1226117,6,'53C4234A7C9F1798BF70455D762742CF.mp4','2021-03-05 01:20:41.767'),(1133,1226118,6,'0D766C584B162F80611FC60B627DA331.mp4','2021-03-05 01:20:43.067'),(1134,1226119,6,'0D766C584B162F80611FC60B627DA331.mp4','2021-03-05 01:20:44.371'),(1135,1226120,6,'D7DC216DFEC05D271899F1F0399059F7.mp4','2021-03-05 01:20:48.794'),(1136,1226176,0,'EBDE8AECB062258888D3480B85DE9BAE.mp4','2021-03-05 01:21:17.935'),(1137,1226177,7,'0AD239A62E7A8772D19154157F2D3E81.jpg','2021-03-05 01:21:18.841'),(1138,1226177,8,'117CC7E8DE626FC2ED06DF97ECB7DE82.jpg','2021-03-05 01:21:18.870'),(1139,1226122,6,'23E480F652C6806E5DAB41BF3E87F5DC.mp4','2021-03-05 01:21:21.550'),(1140,1226178,7,'BDD5421FC0FE77C9B625F6CDB3AAEC65.jpg','2021-03-05 01:21:21.678'),(1141,1226178,8,'7B4D4D9601DA04A976C98A87161E9DB6.jpg','2021-03-05 01:21:21.696'),(1142,1226179,7,'42BBE78A45BE7CB8D2F0C539BAD7C76D.jpg','2021-03-05 01:21:22.578'),(1143,1226179,8,'A7A2FEDB352827B868910F3BA8394332.jpg','2021-03-05 01:21:22.599'),(1144,1226180,7,'24563CF9B8B2C003756448F26C831BF4.jpg','2021-03-05 01:21:25.549'),(1145,1226180,8,'E6C4A6DB5E5DC3F822F8512C02982020.jpg','2021-03-05 01:21:25.567'),(1146,1226181,7,'3917ED7F54151D61EDD1080F48A92717.jpg','2021-03-05 01:21:26.150'),(1147,1226181,8,'457CA90EEC4098B95DF858DD256C9B26.jpg','2021-03-05 01:21:26.168'),(1148,1226182,7,'2B3E2AF0EBDB724F29B295E48FFF844F.jpg','2021-03-05 01:21:28.882'),(1149,1226182,8,'C55801CBCEFAC74E79789234EAD78BF7.jpg','2021-03-05 01:21:28.901'),(1150,1226123,6,'40F32D9DFD1C75AADB01CB9A5A5F9631.mp4','2021-03-05 01:21:29.012'),(1151,1226183,7,'0254F8AE5721F65439F45A90EBA785A0.jpg','2021-03-05 01:21:31.839'),(1152,1226183,8,'F2012AB6CAD2B4BF6D948843567BAA40.jpg','2021-03-05 01:21:31.857'),(1153,1226124,6,'02B0B91B6428A61740E960651B4FC157.mp4','2021-03-05 01:21:36.378'),(1154,1226184,7,'15E2D9ABE634EF74E063CF371EC73E0D.jpg','2021-03-05 01:21:38.182'),(1155,1226184,8,'52BFCC93CC6428EB4250425FE355A559.jpg','2021-03-05 01:21:38.201'),(1156,1226185,7,'0A9F9DA1F1A4F896972B727F0E9C12CA.jpg','2021-03-05 01:21:38.738'),(1157,1226185,8,'DA98B4A51D8D53727D32F26AF1352CCB.jpg','2021-03-05 01:21:38.757'),(1158,1226186,7,'5BEE845B60F6E80B6E15C5686D823144.jpg','2021-03-05 01:21:40.219'),(1159,1226186,8,'8F1279E43743CE66958A0569C3EA1FA9.jpg','2021-03-05 01:21:40.240'),(1160,1226187,7,'ABF80264E6795C0DE0E1116BE018BC5D.jpg','2021-03-05 01:21:42.559'),(1161,1226187,8,'D4D7CA1AD6D77F9CBB8A1F4341F18927.jpg','2021-03-05 01:21:42.582'),(1162,1226188,7,'8417CDC595D84805C7FA8B4A7F1ADB61.jpg','2021-03-05 01:21:44.757'),(1163,1226188,8,'930AF89E44384A5A3F6C648BDF479976.jpg','2021-03-05 01:21:44.778'),(1164,1226189,7,'70F3E685FD7C46160A84663A97519BBE.jpg','2021-03-05 01:21:45.133'),(1165,1226189,8,'5D287A8194E8CF6755CEBAD6E5270240.jpg','2021-03-05 01:21:45.152'),(1166,1226190,7,'55737B279391E53D76CE615B1B60F94F.jpg','2021-03-05 01:21:47.347'),(1167,1226190,8,'83CC48FEE1AAD9F792B85723D76FC5D0.jpg','2021-03-05 01:21:47.367'),(1168,1226191,7,'1479542DE866CB3EF8BCC1A76DAC4C63.jpg','2021-03-05 01:21:47.782'),(1169,1226191,8,'5C3CCCEB428288985E826B6458B79775.jpg','2021-03-05 01:21:47.804'),(1170,1226125,6,'6824E0CDBA9E9D92DC0EA8935A8BE3D3.mp4','2021-03-05 01:21:50.954'),(1171,1226126,6,'B22A45D34F06198FAC0002DE7BEF9BE6.mp4','2021-03-05 01:21:52.296'),(1172,1226127,6,'ED68F4359502CFCE38ADEEE456B61E72.mp4','2021-03-05 01:22:00.780'),(1173,1226128,6,'82CA65E5386D0AD4478E661B81D3FE0D.mp4','2021-03-05 01:22:05.201'),(1174,1226129,6,'4D70F47B3624CD5771C56AE8F5F52ACB.mp4','2021-03-05 01:22:06.507'),(1175,1226130,6,'9858ABB14E52921E64702D6878325B54.mp4','2021-03-05 01:22:08.815'),(1176,1226131,6,'355ECE2C9DDBF8D1910CEDFFE79F80AB.mp4','2021-03-05 01:22:10.215'),(1177,1226132,6,'3B9E94F994C59E7E9617C6A93CB9299D.mp4','2021-03-05 01:22:11.545'),(1178,1226133,6,'09D4331765180DD992A8CA97F8ECEC02.mp4','2021-03-05 01:22:12.876'),(1179,1226192,7,'F8C12D90FA60771366FA1E2B60F4AA96.jpg','2021-03-05 01:22:12.958'),(1180,1226192,8,'67C2D9C0E97FF8629C62242E612A489F.jpg','2021-03-05 01:22:12.980'),(1181,1226134,6,'F8A77A810152733D5EF790E4D92568C2.mp4','2021-03-05 01:22:14.180'),(1182,1226135,6,'2E1413E0C81DF0D3BEAC612EDC280D9B.mp4','2021-03-05 01:22:18.564'),(1183,1226136,6,'862EBB82340DA2995EBE894E0A4FF9F0.mp4','2021-03-05 01:22:20.959'),(1184,1226137,6,'64BBD4094F310AA08C13B95F033EE9CB.mp4','2021-03-05 01:22:22.255'),(1185,1226138,6,'C8B261910B8B0802C922BCA9C0B3B10F.mp4','2021-03-05 01:22:23.608'),(1186,1226139,6,'83DE7F3C0D88AAF6C94DB8351F831F21.mp4','2021-03-05 01:22:25.974'),(1187,1226140,6,'83DE7F3C0D88AAF6C94DB8351F831F21.mp4','2021-03-05 01:22:26.338'),(1188,1226141,6,'5B886AADD44FE0A54B535741E6A4FC80.mp4','2021-03-05 01:22:27.797'),(1189,1226142,6,'7D2F803726FBD708A20F1C236310DD46.mp4','2021-03-05 01:22:30.119'),(1190,1226143,6,'4E4015A7435C7123D26698FD8B5F90A5.mp4','2021-03-05 01:22:33.517'),(1191,1226193,7,'0FF2C4837A23B16C2B1D37B5083367A9.jpg','2021-03-05 01:22:37.378'),(1192,1226193,8,'2BDF62033AA6D96EFFCD90221F772729.jpg','2021-03-05 01:22:37.398'),(1193,1226194,7,'5DD926F9265AC9F9246B13650F1A2256.jpg','2021-03-05 01:22:45.866'),(1194,1226194,8,'013B04739982E0E96AB4A212A60BB038.jpg','2021-03-05 01:22:45.885'),(1195,1226195,7,'A6B1B186071DBB81B8BE803B58F55BAC.jpg','2021-03-05 01:22:46.431'),(1196,1226195,8,'472A516E4B34AE6C003C0FF7ED428D22.jpg','2021-03-05 01:22:46.449'),(1197,1226196,7,'DF7DDFF89E97275E8B220459A276009E.jpg','2021-03-05 01:22:48.207'),(1198,1226196,8,'A8D1AA57A10447E4E3F342EAA99410E3.jpg','2021-03-05 01:22:48.228'),(1199,1226197,7,'75CFE61268F562BF99C172D025FD9EAF.jpg','2021-03-05 01:22:49.169'),(1200,1226197,8,'9EF6C1AEBE0046AF8AD40494052557C7.jpg','2021-03-05 01:22:49.186'),(1201,1226198,7,'9736FAEFA92C8A60560F8BB0B0DB3929.jpg','2021-03-05 01:22:51.636'),(1202,1226198,8,'8382C31E39AE109E9094FC85FBC33934.jpg','2021-03-05 01:22:51.652'),(1203,1226199,7,'3C96D0F1868FA06B695B6F6405620A52.jpg','2021-03-05 01:22:53.137'),(1204,1226199,8,'5FE7B6EFDE9FBA7ED379BA6E21FB94E5.jpg','2021-03-05 01:22:53.156'),(1205,1226200,7,'C81947E44000B7AB38AB2023A93D825D.jpg','2021-03-05 01:22:54.909'),(1206,1226200,8,'561EC512A0540BEE0BDA71A4D0C4866A.jpg','2021-03-05 01:22:54.925'),(1207,1226201,7,'5BD2B78F382226E0E4A6F97CCE282149.jpg','2021-03-05 01:22:57.136'),(1208,1226201,8,'43B9CAB5E57554A5D7B42CDE1E1F63F6.jpg','2021-03-05 01:22:57.154'),(1209,1226202,7,'486B3F4FD9365D4A0F3A2E1DE7C54306.jpg','2021-03-05 01:22:57.910'),(1210,1226202,8,'6161DA1DDE8F5368535CA42C1CC388FD.jpg','2021-03-05 01:22:57.927'),(1211,1226203,7,'A0355AB4B74EC1C27EC4C169698FCF0D.jpg','2021-03-05 01:23:00.471'),(1212,1226203,8,'30182546D62381B570997404F4B60654.jpg','2021-03-05 01:23:00.498'),(1213,1226204,7,'C8F681C5FE0EA8151B28073CD5D4D035.jpg','2021-03-05 01:23:00.915'),(1214,1226204,8,'8A812698FA211AD949C86C6A587B65A1.jpg','2021-03-05 01:23:00.934'),(1215,1226205,7,'68A71E9A8CA92006C23569CAF9BBA2BB.jpg','2021-03-05 01:23:04.567'),(1216,1226205,8,'9823D212039844A812D1750DB70F46D8.jpg','2021-03-05 01:23:04.586'),(1217,1226144,6,'AB8E9EBB11B17FFAE5B4E1CF99D216F0.mp4','2021-03-05 01:23:20.509'),(1218,1226145,6,'AB8E9EBB11B17FFAE5B4E1CF99D216F0.mp4','2021-03-05 01:23:20.812'),(1219,1226146,6,'EC6317EA321FA5226E48C8172C8753FB.mp4','2021-03-05 01:23:23.129'),(1220,1226147,6,'EC6317EA321FA5226E48C8172C8753FB.mp4','2021-03-05 01:23:24.528'),(1221,1226148,6,'3496EE26CAE63D1DE31D1A8E2062CAFD.mp4','2021-03-05 01:23:26.885'),(1222,1226149,6,'3496EE26CAE63D1DE31D1A8E2062CAFD.mp4','2021-03-05 01:23:27.186'),(1223,1226150,6,'56C8FE1AF9CC323B2791E1B60A39ABF2.mp4','2021-03-05 01:23:28.486'),(1224,1226151,6,'56C8FE1AF9CC323B2791E1B60A39ABF2.mp4','2021-03-05 01:23:29.772'),(1225,1226152,6,'91579C56B8E0BA18DAC6D17E915722E6.mp4','2021-03-05 01:23:31.124'),(1226,1226206,7,'0179616F7CCCEFB0307A4094C4827D4A.jpg','2021-03-05 01:23:33.781'),(1227,1226206,8,'32424D59B37733C8DA16EF3A9D1A589B.jpg','2021-03-05 01:23:33.799'),(1228,1226153,6,'9705185BBC08DE0EF95BB4427C716E11.mp4','2021-03-05 01:23:38.518'),(1229,1226207,7,'765807E8E6D0950ED32A6CBE4B0063EE.jpg','2021-03-05 01:23:39.555'),(1230,1226207,8,'45AC0CCC5D84603637CBA10F15023EDE.jpg','2021-03-05 01:23:39.573'),(1231,1226154,6,'6A302522478F3F04AA781B20840EF9A3.mp4','2021-03-05 01:23:52.051'),(1232,1226155,6,'8E2C744FE4EF5A4B22A60355FBBBCB5E.mp4','2021-03-05 01:23:53.403'),(1233,1226156,6,'AC39D35C3989493151859E9103F187F3.mp4','2021-03-05 01:23:55.710'),(1234,1226157,6,'2FF7C449C98DB9CCC25A1AC1E92E76C9.mp4','2021-03-05 01:23:59.099'),(1235,1226208,7,'74B1AE74442D148A4F83C41D9617F88F.jpg','2021-03-05 01:24:17.316'),(1236,1226208,8,'01A64B4C439DBCE5DCA536843EAA1968.jpg','2021-03-05 01:24:17.335'),(1237,1226209,7,'72032A1069BA449E810F2A6FDDA6B345.jpg','2021-03-05 01:24:18.807'),(1238,1226209,8,'C08618C907B5CAEC13498CE6E5D85830.jpg','2021-03-05 01:24:18.825'),(1239,1226158,6,'4EB305AFB8201F7215906D3D5D8776DE.mp4','2021-03-05 01:24:20.707'),(1240,1226210,7,'2349010C6849D3BE6D011102E1384FC7.jpg','2021-03-05 01:24:21.027'),(1241,1226210,8,'491F6D8F61F9BC7FA6DB013E56DA50E1.jpg','2021-03-05 01:24:21.046'),(1242,1226211,7,'4088A75B34078135EA44BDA5BE78B4F9.jpg','2021-03-05 01:24:21.540'),(1243,1226211,8,'0B7BDDF5C73E58F27D04F73080BF0011.jpg','2021-03-05 01:24:21.558'),(1244,1226212,7,'DFBC3047B85D5183DB19AFB6F3C90930.jpg','2021-03-05 01:24:23.480'),(1245,1226212,8,'0B22FB388DB55B8EAF79F3D4C874ED0E.jpg','2021-03-05 01:24:23.497'),(1246,1226159,6,'DF754582B4BEB21C52863ACBEEE60FB8.mp4','2021-03-05 01:24:24.084'),(1247,1226213,7,'DC2B91F311E17195239770E87821C5FA.jpg','2021-03-05 01:24:24.985'),(1248,1226213,8,'BD0FE6463DD427EA4D848682C35743C7.jpg','2021-03-05 01:24:25.005'),(1249,1226214,7,'926858579E5432926A51D5B13CC6853B.jpg','2021-03-05 01:24:25.933'),(1250,1226214,8,'11D70A590A339F4B859ABC667332ABFD.jpg','2021-03-05 01:24:25.960'),(1251,1226215,7,'BBF214CE268AE9B21977AADDF2A3C6B6.jpg','2021-03-05 01:24:27.123'),(1252,1226215,8,'C646D09D0D93E0B907135FAEFF1539B5.jpg','2021-03-05 01:24:27.142'),(1253,1226160,6,'FB427200F5E93A43AF143AA400D1B3DD.mp4','2021-03-07 19:48:03.942'),(1254,1226161,6,'70DC887BF94CD8412B9C9232968F3D5A.mp4','2021-03-07 19:48:04.369'),(1255,1226162,6,'70DC887BF94CD8412B9C9232968F3D5A.mp4','2021-03-07 19:48:04.714'),(1256,1226163,6,'24B8B45E14833869E087911B03E0047D.mp4','2021-03-07 19:48:05.042'),(1257,1226164,6,'24B8B45E14833869E087911B03E0047D.mp4','2021-03-07 19:48:05.383'),(1258,1226165,6,'340A1621A6709082D247A0B202C56127.mp4','2021-03-07 19:48:05.795'),(1259,1226166,6,'78874CC684ECA59414218B2F6E373BC1.mp4','2021-03-07 19:48:06.207'),(1260,1226167,6,'BAC4ADD441248065392F6657CABB9352.mp4','2021-03-07 19:48:06.575'),(1261,1226168,6,'22D0A941BFE9D4842B082E5A9F92C0D2.mp4','2021-03-07 19:48:06.888'),(1262,1226169,6,'7C638915D21177978D7E11260C4B8F8F.mp4','2021-03-07 19:48:07.203'),(1263,1226170,6,'7C638915D21177978D7E11260C4B8F8F.mp4','2021-03-07 19:48:07.520'),(1264,1226171,6,'3E1312942896315538A1053A868DD2BF.mp4','2021-03-07 19:48:07.837'),(1265,1226172,6,'633FEDFFFAE247520F5A63F9DF5CB8B6.mp4','2021-03-07 19:48:08.146'),(1266,1226173,6,'24C1B9D5E3BF4D9D7A794E4FBB2C8F57.mp4','2021-03-07 19:48:08.475'),(1267,1226174,6,'7C2DF78FFF1E078C919E67DA485E4603.mp4','2021-03-07 19:48:08.786'),(1268,1226175,6,'CB3C77AA7B0DFF64A04B7D3601A338E9.mp4','2021-03-07 19:48:09.097'),(1269,1226177,6,'2678B72CCD342FC79531F9A7FF832BD8.mp4','2021-03-07 19:48:09.415'),(1270,1226178,6,'93217DC3A852042AC3BC72FA57F6D2D6.mp4','2021-03-07 19:48:09.736'),(1271,1226179,6,'007F5932B3A41D8F2D7A8995C0EAE7AF.mp4','2021-03-07 19:48:10.056'),(1272,1226180,6,'3B3528D904E718C348ADF67F62D631A3.mp4','2021-03-07 19:48:10.363'),(1273,1226181,6,'3B3528D904E718C348ADF67F62D631A3.mp4','2021-03-07 19:48:10.679'),(1274,1226182,6,'C2909A2BC619678E3A5047F1CE392E10.mp4','2021-03-07 19:48:11.002'),(1275,1226183,6,'80F93F4C284FBDF46B53B85E44AE4F8D.mp4','2021-03-07 19:48:11.300'),(1276,1226184,6,'5E8CBD7FD3D36C4874DACA8039413DD2.mp4','2021-03-07 19:48:11.625'),(1277,1226185,6,'5E8CBD7FD3D36C4874DACA8039413DD2.mp4','2021-03-07 19:48:11.959'),(1278,1226186,6,'74118E43F82FA6E77C5437575D6A941B.mp4','2021-03-07 19:48:12.482'),(1279,1226187,6,'5BF9D14DC5C928E3AC77203DD79096D2.mp4','2021-03-07 19:48:12.850'),(1280,1226188,6,'9A45E738F09E2797D954F409BBDDB43F.mp4','2021-03-07 19:48:13.229'),(1281,1226189,6,'9A45E738F09E2797D954F409BBDDB43F.mp4','2021-03-07 19:48:13.556'),(1282,1226190,6,'6BBC4A7266984BBA21A977174AC855E1.mp4','2021-03-07 19:48:13.858'),(1283,1226191,6,'6BBC4A7266984BBA21A977174AC855E1.mp4','2021-03-07 19:48:14.186'),(1284,1230001,2,'4A364D90FF2873A5081B07E7EC6F3AEE.jpg','2021-03-07 19:49:28.331'),(1285,1230001,3,'47FA4BA498EC972CA8EB53F9AA872C27.jpg','2021-03-07 19:49:28.346'),(1286,1230002,2,'4923B0CA270A3E89C9DDBB99FF070DA1.jpg','2021-03-07 19:49:35.262'),(1287,1230002,3,'AC1E0612BE7458679C664B978E3F952A.jpg','2021-03-07 19:49:35.296'),(1288,1230002,3,'AC1E0612BE7458679C664B978E3F952A.jpg','2021-03-07 19:49:35.329'),(1289,1230003,0,'10F7444AD7BE39EDCF8591E16C4DAAAA.mp4','2021-03-07 19:53:02.173'),(1290,1230004,2,'C7A0D5B3E9E1480B17C82B19F5C5619E.jpg','2021-03-07 19:53:56.039'),(1291,1230004,3,'1D5A695A17597EA7386BC83EFD6D80EF.jpg','2021-03-07 19:53:56.071'),(1292,1230004,3,'1D5A695A17597EA7386BC83EFD6D80EF.jpg','2021-03-07 19:53:56.085'),(1293,1230001,1,'1D86C4765094C205865335A52BEFBA6C.mp4','2021-03-07 19:54:29.731'),(1294,1230002,1,'ACA9EBCA101374FF39BD156B8FBB7657.mp4','2021-03-07 19:54:36.212'),(1295,1230005,2,'C94ACBF828CCFF8458E32828EAFEE099.jpg','2021-03-07 19:55:56.139'),(1296,1230005,3,'36F124B0A576576A2378B39DD211E0EC.jpg','2021-03-07 19:55:56.165'),(1297,1230005,3,'36F124B0A576576A2378B39DD211E0EC.jpg','2021-03-07 19:55:56.181'),(1298,1230006,2,'2FD1D207E3B352C4CD849A7349C69B5E.jpg','2021-03-07 19:56:00.147'),(1299,1230006,3,'964A136A8F711814876075B98E23C3FE.jpg','2021-03-07 19:56:00.164'),(1300,1230006,3,'964A136A8F711814876075B98E23C3FE.jpg','2021-03-07 19:56:00.178'),(1301,1230007,2,'7815BFA50A0F7F50E6FC9E76AE562FA3.jpg','2021-03-07 19:56:21.138'),(1302,1230007,3,'38E52CC9EE1CAD9187C704DAFE2AD3FA.jpg','2021-03-07 19:56:21.154'),(1303,1230007,3,'38E52CC9EE1CAD9187C704DAFE2AD3FA.jpg','2021-03-07 19:56:21.168'),(1304,1230008,2,'D4ACACD14414A9C8D8CF4F9678C3E146.jpg','2021-03-07 19:56:36.760'),(1305,1230008,3,'02E0194B64CF32080A869E24C2970F4F.jpg','2021-03-07 19:56:36.775'),(1306,1230008,3,'02E0194B64CF32080A869E24C2970F4F.jpg','2021-03-07 19:56:36.790'),(1307,1230009,2,'76B3E81FC0832C6AC65B2BE753D2EEB8.jpg','2021-03-07 19:56:38.851'),(1308,1230009,3,'F481A3E3D5CAFBE55C43F6AEC4321B4C.jpg','2021-03-07 19:56:38.865'),(1309,1230010,0,'0AEBEB9483E7A8D06EB189ABABED1B00.mp4','2021-03-07 19:58:03.007'),(1310,1230011,2,'6CD282EA65B0E58B53FED4205F8A4A7A.jpg','2021-03-07 19:58:37.590'),(1311,1230011,3,'D081059429F7BFEB039DDF719DA9330E.jpg','2021-03-07 19:58:37.605'),(1312,1230012,2,'910C98247BF95369667AC252EB1CD32E.jpg','2021-03-07 19:58:54.022'),(1313,1230012,3,'A78716FF41BFEBFC213CB3FFC761B5D9.jpg','2021-03-07 19:58:54.037'),(1314,1230004,1,'F656DE1AA43A7C0C9D1453E4AAC53C1B.mp4','2021-03-07 19:58:57.272'),(1315,1230013,2,'95308BEAC9EF660BC6FDF1546512122B.jpg','2021-03-07 19:59:10.889'),(1316,1230013,3,'E71536E79135C329BDFC10D39F970449.jpg','2021-03-07 19:59:10.906'),(1317,1230014,2,'CECD8D29053DDF6B6F1ABA40E9C72E67.jpg','2021-03-07 20:00:10.622'),(1318,1230014,3,'8D6051F850573AE034C1CF88D53FF74C.jpg','2021-03-07 20:00:10.702'),(1319,1230014,3,'8D6051F850573AE034C1CF88D53FF74C.jpg','2021-03-07 20:00:10.716'),(1320,1230015,2,'781666FBA553C06F4DB36D4FAE6037B5.jpg','2021-03-07 20:00:16.158'),(1321,1230015,3,'557D466CDE8E914DD9EAD89E8A71AF96.jpg','2021-03-07 20:00:16.174'),(1322,1230015,3,'557D466CDE8E914DD9EAD89E8A71AF96.jpg','2021-03-07 20:00:16.187'),(1323,1230005,1,'6ADDD8B07C14CC396B4964935A44611A.mp4','2021-03-07 20:00:56.688'),(1324,1230006,1,'FF8574BBABDDC1474F1831B637B959E5.mp4','2021-03-07 20:01:01.131'),(1325,1230007,1,'189CF53E68C12D8E2ECBC7CDC4127B5C.mp4','2021-03-07 20:01:22.420'),(1326,1230008,1,'C7FADB8BDF306962F59042D33D60000D.mp4','2021-03-07 20:01:37.220'),(1327,1230009,1,'2EAD3AD58A54940F12B4D4057886ABE5.mp4','2021-03-07 20:01:39.736'),(1328,1232001,2,'336B5D4858DC1870585B9EBB9FDF598C.jpg','2021-03-07 20:03:14.383'),(1329,1232001,3,'ABA605AC1A40CAC9B3ADD4AE53F359B6.jpg','2021-03-07 20:03:14.404'),(1330,1232001,3,'ABA605AC1A40CAC9B3ADD4AE53F359B6.jpg','2021-03-07 20:03:14.417'),(1331,1230011,1,'563FC4BA1B3215C31891C5EB3A4F5F81.mp4','2021-03-07 20:03:38.620'),(1332,1232002,2,'ADC1F401F529BC8C2F3E2E25263D424F.jpg','2021-03-07 20:04:08.931'),(1333,1232002,3,'B7A0C387066F394EF7BA94DC161A80D0.jpg','2021-03-07 20:04:08.951'),(1334,1232003,2,'5C62664BA69A35FF096E9218C568EDC9.jpg','2021-03-07 20:06:12.605'),(1335,1232003,3,'6EF0E160DA2B7FAE8C3C5A1A5388F00A.jpg','2021-03-07 20:06:12.626'),(1336,1232003,3,'6EF0E160DA2B7FAE8C3C5A1A5388F00A.jpg','2021-03-07 20:06:12.642'),(1337,1230017,0,'D41D8CD98F00B204E9800998ECF8427E.mp4','2021-03-07 20:08:08.072'),(1338,1232004,2,'D2C85B1825253B756E76C39653E19A5F.jpg','2021-03-07 20:09:13.627'),(1339,1232004,3,'C250E257AE36DC24CFE51095CF950AE1.jpg','2021-03-07 20:09:13.644'),(1340,1232005,2,'7377A0292495B26D81BC8B3D21E80975.jpg','2021-03-07 20:09:29.618'),(1341,1232005,3,'DE87D2B29FB30BFE243A582074194AC1.jpg','2021-03-07 20:09:29.635'),(1342,1232006,2,'70C1C415DF136AF75FF729EBC3E2C1CB.jpg','2021-03-07 20:09:32.082'),(1343,1232006,3,'23B5EC9E518EDF046A83D423B81D1837.jpg','2021-03-07 20:09:32.099'),(1344,1232006,3,'23B5EC9E518EDF046A83D423B81D1837.jpg','2021-03-07 20:09:32.114'),(1345,1232007,2,'B4238A0F34E0CD67E71AC4D0216D44D2.jpg','2021-03-07 20:09:35.296'),(1346,1232007,3,'9F278C2276049EEDDA0C84A086583C73.jpg','2021-03-07 20:09:35.315'),(1347,1232008,2,'DE8E372FCE976793BFF1E7044AB1EC72.jpg','2021-03-07 20:09:38.402'),(1348,1232008,3,'4F6A512CEC795F842076D5EC00E86D55.jpg','2021-03-07 20:09:38.421'),(1349,1232009,2,'8C3FEDF95214A546DCA1D2E25E583E98.jpg','2021-03-07 20:09:40.683'),(1350,1232009,3,'CAF4A177B9240513C2C1DE93B0F44DA4.jpg','2021-03-07 20:09:40.744'),(1351,1232010,2,'FB15A00AB5E360E495C2B4DA04AE0CD8.jpg','2021-03-07 20:09:42.730'),(1352,1232010,3,'2336B43EBDB65C87B0E1AF98BA4C95F8.jpg','2021-03-07 20:09:42.748'),(1353,1232011,2,'7DC3A70C604116490BC4F52E10EE1185.jpg','2021-03-07 20:09:47.184'),(1354,1232011,3,'0F1C6FA6F3CC22399CD2EBE7C06B7365.jpg','2021-03-07 20:09:47.202'),(1355,1232011,3,'0F1C6FA6F3CC22399CD2EBE7C06B7365.jpg','2021-03-07 20:09:47.218'),(1356,1232012,2,'75E4AFBD44429C41F2A1C6662999A9CD.jpg','2021-03-07 20:11:32.527'),(1357,1232012,3,'24B60EA6A57FDE74E984A04D276EC487.jpg','2021-03-07 20:11:32.566'),(1358,1232013,2,'3C4F846BEA0959E673E2CDB3F1C2A7E8.jpg','2021-03-07 20:12:02.922'),(1359,1232013,3,'220F5A6A34482E4904AA521A5602B910.jpg','2021-03-07 20:12:02.963'),(1360,1232014,2,'CCD8E376D6BE8CF1FF4F537D5F905EFB.jpg','2021-03-07 20:12:26.320'),(1361,1232014,3,'DF9E1745C15885958CCA87A1227AB847.jpg','2021-03-07 20:12:26.336'),(1362,1232014,3,'DF9E1745C15885958CCA87A1227AB847.jpg','2021-03-07 20:12:26.355'),(1363,1232015,0,'D41D8CD98F00B204E9800998ECF8427E.mp4','2021-03-07 20:13:08.116'),(1364,1232016,2,'EF604939C5386CFA3D1D50134877073C.jpg','2021-03-07 20:14:14.846'),(1365,1232016,3,'2D9175F0AF2527DA748740A15441D7A6.jpg','2021-03-07 20:14:14.865'),(1366,1232017,2,'2384B9576FB8CAB3771C99B64CFCFB0F.jpg','2021-03-07 20:14:17.187'),(1367,1232017,3,'FD4F5C79D29F354B966759DB6F22FD83.jpg','2021-03-07 20:14:17.206'),(1368,1232017,3,'FD4F5C79D29F354B966759DB6F22FD83.jpg','2021-03-07 20:14:17.224'),(1369,1232018,2,'ACBEDC04BC902CC4E1907D8E5460EE8D.jpg','2021-03-07 20:14:19.675'),(1370,1232018,3,'3937E340E72EBAC7FF777AAF634782D3.jpg','2021-03-07 20:14:19.696'),(1371,1232018,3,'3937E340E72EBAC7FF777AAF634782D3.jpg','2021-03-07 20:14:19.712'),(1372,1232019,2,'B094F72B8AF4E5794665788F7F22CC81.jpg','2021-03-07 20:15:10.247'),(1373,1232019,3,'DCC3A9BF63021C8DA6382E46EA18BF05.jpg','2021-03-07 20:15:10.262'),(1374,1232020,2,'2AE2D76DA869A65278B2EF00D3004749.jpg','2021-03-07 20:17:12.356'),(1375,1232020,3,'3E4BB4D85EB35F7B4C514746F3795BB5.jpg','2021-03-07 20:17:12.376'),(1376,1230018,0,'D41D8CD98F00B204E9800998ECF8427E.mp4','2021-03-07 20:18:08.146'),(1377,1232021,2,'9259F0FC257D2A69DC94685888008A56.jpg','2021-03-07 20:18:52.608'),(1378,1232021,3,'AD8553708AC813F166E13E751BAD59FC.jpg','2021-03-07 20:18:52.625'),(1379,1232021,3,'AD8553708AC813F166E13E751BAD59FC.jpg','2021-03-07 20:18:52.643'),(1380,1232022,2,'692AF129EB440CC770ED582CEC477C74.jpg','2021-03-07 20:18:58.161'),(1381,1232022,3,'8FE5A4A51E15ED8911525A556AF933E1.jpg','2021-03-07 20:18:58.184'),(1382,1232023,0,'D41D8CD98F00B204E9800998ECF8427E.mp4','2021-03-07 20:23:08.173'),(1383,1232024,2,'44B738B5E696A1CE895581BDFEFEB010.jpg','2021-03-07 20:23:26.921'),(1384,1232024,3,'6B7FF904769BFC832A75C9760FEC260B.jpg','2021-03-07 20:23:26.938'),(1385,1232024,3,'6B7FF904769BFC832A75C9760FEC260B.jpg','2021-03-07 20:23:26.952'),(1386,1232025,2,'91963B968742823653C4AFC825A22D6F.jpg','2021-03-07 20:25:13.467'),(1387,1232025,3,'E783DCBC318EDCA7692E50C8E68668E7.jpg','2021-03-07 20:25:13.484'),(1388,1232026,2,'BD08704573CA03E66CABAF26062B0ADA.jpg','2021-03-07 20:26:54.406'),(1389,1232026,3,'20D73F1D01E4BA831B48DD8E20BE7C9A.jpg','2021-03-07 20:26:54.424'),(1390,1232027,2,'8DED9C72A894E694B3B0EE036CF14F8E.jpg','2021-03-07 20:27:05.546'),(1391,1232027,3,'8EE316D5911920A881848F151CF49B9B.jpg','2021-03-07 20:27:05.561'),(1392,1232027,3,'8EE316D5911920A881848F151CF49B9B.jpg','2021-03-07 20:27:05.575'),(1393,1230019,0,'D41D8CD98F00B204E9800998ECF8427E.mp4','2021-03-07 20:28:08.213'),(1394,1232028,2,'C687478D6BE57C7FCB69564BB46224E5.jpg','2021-03-07 20:28:38.409'),(1395,1232028,3,'D4B92739ADEE6DBD9A75D4DB627BC1CC.jpg','2021-03-07 20:28:38.426'),(1396,1232029,2,'6008782D5623E8F6CB244E20D9FCECDC.jpg','2021-03-07 20:28:43.023'),(1397,1232029,3,'9E693C3C793AD3F853EB919A27663D8A.jpg','2021-03-07 20:28:43.039'),(1398,1232030,2,'FA0D681E8980C902DBD3FC7A95B588E3.jpg','2021-03-07 20:28:55.930'),(1399,1232030,3,'D6478B031D37E5AA35C5BD084955F254.jpg','2021-03-07 20:28:55.956'),(1400,1232030,3,'D6478B031D37E5AA35C5BD084955F254.jpg','2021-03-07 20:28:55.969'),(1401,1232031,2,'D3D50EBFA70C7BB769E62F3DADEF8629.jpg','2021-03-07 20:29:48.391'),(1402,1232031,3,'8B9B2D36CC6797FB01F861D3265522ED.jpg','2021-03-07 20:29:48.419'),(1403,1232032,2,'606140E7303FD87A6DCE13D9CD26EABA.jpg','2021-03-07 20:32:22.846'),(1404,1232032,3,'79CF49B313D7292515AB456534432510.jpg','2021-03-07 20:32:22.861'),(1405,1232033,2,'0C230423155939BF3D13E378C3B35EE6.jpg','2021-03-07 20:32:36.524'),(1406,1232033,3,'D7AC68156374DD8FE194EEEE4ED58456.jpg','2021-03-07 20:32:36.539'),(1407,1232033,3,'D7AC68156374DD8FE194EEEE4ED58456.jpg','2021-03-07 20:32:36.552'),(1408,1232034,0,'D41D8CD98F00B204E9800998ECF8427E.mp4','2021-03-07 20:33:08.231'),(1409,1232035,2,'6812BC93F77EB40B754C4A6EB0962393.jpg','2021-03-07 20:34:10.965'),(1410,1232035,3,'961DDE50D8E526CADC12C0438B2E9CAF.jpg','2021-03-07 20:34:10.984'),(1411,1232036,2,'10EDBA71FB62E92FC64DEC04694D20EB.jpg','2021-03-07 20:35:18.618'),(1412,1232036,3,'1DC82A92ABF985020CD9029DEDBE52E4.jpg','2021-03-07 20:35:18.647'),(1413,1232037,2,'C184BCB10755999BDF12232D9DDAD44A.jpg','2021-03-07 20:36:14.673'),(1414,1232037,3,'849C4086D98070CD40A379B7EC02A0C7.jpg','2021-03-07 20:36:14.690'),(1415,1232037,3,'849C4086D98070CD40A379B7EC02A0C7.jpg','2021-03-07 20:36:14.705'),(1416,1230020,0,'D41D8CD98F00B204E9800998ECF8427E.mp4','2021-03-07 20:38:08.264'),(1417,1232038,2,'C26039BA67780872CBC55BAF295605F1.jpg','2021-03-07 20:39:01.732'),(1418,1232038,3,'71B32D2BED3F57C11E8C14C21C381974.jpg','2021-03-07 20:39:01.749'),(1419,1232039,2,'21E611EEDEC8E172A740DFC102A99FF3.jpg','2021-03-07 20:40:29.960'),(1420,1232039,3,'4CFE02FDF08943C92C32F3F6D5D7F1DC.jpg','2021-03-07 20:40:29.996'),(1421,1232040,2,'ACC13BB3F195E8C93D03D2E3398303EC.jpg','2021-03-07 20:40:35.348'),(1422,1232040,3,'FFC85B8DE77F53A59682063CCC16FCB6.jpg','2021-03-07 20:40:35.366'),(1423,1232040,3,'FFC85B8DE77F53A59682063CCC16FCB6.jpg','2021-03-07 20:40:35.381'),(1424,1232041,2,'2BC9B25110C32D08AD4159D478B8C573.jpg','2021-03-07 20:40:50.482'),(1425,1232041,3,'8C24F9622522E44A4DCBCEE68A0870FE.jpg','2021-03-07 20:40:50.500'),(1426,1232041,3,'8C24F9622522E44A4DCBCEE68A0870FE.jpg','2021-03-07 20:40:50.514'),(1427,1232042,2,'657F4C3E6433D40806494803F04176E0.jpg','2021-03-07 20:42:29.634'),(1428,1232042,3,'2158BE2D93AD7E6ADB39C9D1D9D62E73.jpg','2021-03-07 20:42:29.651'),(1429,1232042,3,'2158BE2D93AD7E6ADB39C9D1D9D62E73.jpg','2021-03-07 20:42:29.664'),(1430,1232043,2,'8F638C151D1F3B78DA104227A4E33977.jpg','2021-03-07 20:42:33.435'),(1431,1232043,3,'C2ABFE21E2ACB71E589281EC0992C779.jpg','2021-03-07 20:42:33.456'),(1432,1232044,2,'527F5CB34A49712631B155B658F6DE0C.jpg','2021-03-07 20:42:36.035'),(1433,1232044,3,'97E72ABBBBB1EF2373B0E5BDE4C7CA46.jpg','2021-03-07 20:42:36.055'),(1434,1232044,3,'97E72ABBBBB1EF2373B0E5BDE4C7CA46.jpg','2021-03-07 20:42:36.072'),(1435,1232045,2,'852655EB6E5B18C16D10A61F80CAA929.jpg','2021-03-07 20:42:38.580'),(1436,1232045,3,'D06EDF3B3946D849C45CF221F0A71E49.jpg','2021-03-07 20:42:38.599'),(1437,1232045,3,'D06EDF3B3946D849C45CF221F0A71E49.jpg','2021-03-07 20:42:38.615'),(1438,1232046,0,'D41D8CD98F00B204E9800998ECF8427E.mp4','2021-03-07 20:43:08.284'),(1439,1232047,2,'5821B1D9032D60E6E497236AEE5ED0A9.jpg','2021-03-07 20:46:11.351'),(1440,1232047,3,'0D32A20DE3BFE7D4AD2DDE63ECC4BE70.jpg','2021-03-07 20:46:11.368'),(1441,1232047,3,'0D32A20DE3BFE7D4AD2DDE63ECC4BE70.jpg','2021-03-07 20:46:11.399'),(1442,1232048,2,'D03410C0E7F2FC6A6E5B3F07AFCD17F5.jpg','2021-03-07 20:46:13.704'),(1443,1232048,3,'9C26F7D5EFB8EF9FC3BD211589D47F73.jpg','2021-03-07 20:46:13.721'),(1444,1232049,2,'DFB50669185CD3D5F31B0F94E21A2873.jpg','2021-03-07 20:46:18.080'),(1445,1232049,3,'644EF3E8BAA3C45E59FDD23CCB1F3095.jpg','2021-03-07 20:46:18.097'),(1446,1232050,2,'6CC5F222BA90479D1CEB87076EB6192D.jpg','2021-03-07 20:46:57.140'),(1447,1232050,3,'B5E2015F8CE3C145C5B5AF63988059EB.jpg','2021-03-07 20:46:57.158'),(1448,1232050,3,'B5E2015F8CE3C145C5B5AF63988059EB.jpg','2021-03-07 20:46:57.172'),(1449,1232051,2,'91387EE010BA07C8C841D37B4E0D68A9.jpg','2021-03-07 20:47:01.910'),(1450,1232051,3,'1C90018977553B89F70BBD82B408BDF3.jpg','2021-03-07 20:47:01.930'),(1451,1232052,2,'08DE806CB7B67F0079B77D089D36D19F.jpg','2021-03-07 20:47:11.949'),(1452,1232052,3,'DAD96615FC0B0BA61C6326D59AA1F174.jpg','2021-03-07 20:47:12.016'),(1453,1232052,3,'DAD96615FC0B0BA61C6326D59AA1F174.jpg','2021-03-07 20:47:12.031'),(1454,1232053,2,'163074103F88705A4AD0E829C379F706.jpg','2021-03-07 20:47:14.369'),(1455,1232053,3,'2E8C0530D67E7A3DDE482878B3B81D4C.jpg','2021-03-07 20:47:14.387'),(1456,1232054,2,'4BA69D8949FBAB33D938DF6C1CDDD497.jpg','2021-03-07 20:47:17.289'),(1457,1232054,3,'23CD8193B0C9789B3DE0A89A23A8F26F.jpg','2021-03-07 20:47:17.308'),(1458,1232054,3,'23CD8193B0C9789B3DE0A89A23A8F26F.jpg','2021-03-07 20:47:17.322'),(1459,1232055,2,'62092A334473F1168DE8E122ADA9733E.jpg','2021-03-07 20:47:19.362'),(1460,1232055,3,'6F03246C1E32B580AB0E6BEE824ADE2D.jpg','2021-03-07 20:47:19.380'),(1461,1230021,0,'D41D8CD98F00B204E9800998ECF8427E.mp4','2021-03-07 20:48:08.317'),(1462,1232056,2,'F554127CDD41EF8EC5A82A5BF4F0C6B3.jpg','2021-03-07 20:49:59.778'),(1463,1232056,3,'D38D8350C2502CA96D955C215B9FBAFE.jpg','2021-03-07 20:49:59.808'),(1464,1232056,3,'D38D8350C2502CA96D955C215B9FBAFE.jpg','2021-03-07 20:49:59.827'),(1465,1232057,2,'A962309A18F126246C0C5C7B32A54814.jpg','2021-03-07 20:50:02.393'),(1466,1232057,3,'A07B5340C63B0175448581E814B9B47E.jpg','2021-03-07 20:50:02.416'),(1467,1232057,3,'A07B5340C63B0175448581E814B9B47E.jpg','2021-03-07 20:50:02.432'),(1468,1232058,2,'D1FCAA6729EBE76C6105854D7A36D43D.jpg','2021-03-07 20:50:53.875'),(1469,1232058,3,'145224FF8C405F8983E7C3311FB9AA2E.jpg','2021-03-07 20:50:53.895'),(1470,1232059,0,'D41D8CD98F00B204E9800998ECF8427E.mp4','2021-03-07 20:53:08.355'),(1471,1232060,2,'9D2D881CE197C9E5B30D5EB8DFE2F9FF.jpg','2021-03-07 20:54:31.143'),(1472,1232060,3,'F95BFDB7E5189864E9E127C341BAC0BF.jpg','2021-03-07 20:54:31.162'),(1473,1232060,3,'F95BFDB7E5189864E9E127C341BAC0BF.jpg','2021-03-07 20:54:31.178'),(1474,1232061,2,'244B7486786A5EFF95CB6A32440E27AF.jpg','2021-03-07 20:54:33.250'),(1475,1232061,3,'8EE7DF14BF0834E6CE3D91F150B5EB92.jpg','2021-03-07 20:54:33.270'),(1476,1232062,2,'48191AADFB079922925F0CFFCA500F63.jpg','2021-03-07 20:55:30.527'),(1477,1232062,3,'77B7EB8C3C1A8C7D8584B65BE4AFBACA.jpg','2021-03-07 20:55:30.546'),(1478,1232063,2,'03A29098DDF3644CC636B49B18D50012.jpg','2021-03-07 20:58:04.116'),(1479,1232063,3,'BD6539282BD8CD4428E648D9323ACDB7.jpg','2021-03-07 20:58:04.134'),(1480,1230022,0,'D41D8CD98F00B204E9800998ECF8427E.mp4','2021-03-07 20:58:08.373'),(1481,1232064,0,'695D6E5A2A805B10A49171C69EF8EC75.mp4','2021-03-07 20:58:09.542'),(1482,1234001,0,'5C66F17823EB4CB61A616D2F8BC7F2BD.mp4','2021-03-07 21:19:11.940'),(1483,1234002,0,'B22963B63559A19C2FAA2CB07C220F3E.mp4','2021-03-07 21:24:12.878'),(1484,1234003,2,'8E5BA453E05AE63F689F737870980F11.jpg','2021-03-07 21:24:39.512'),(1485,1234003,3,'0CC9EF525FC841F5D8CEA6E3402B157D.jpg','2021-03-07 21:24:39.528'),(1486,1234003,3,'0CC9EF525FC841F5D8CEA6E3402B157D.jpg','2021-03-07 21:24:39.544'),(1487,1234004,2,'2DC6877EB5631A1991E154387A9D9745.jpg','2021-03-07 21:24:52.513'),(1488,1234004,3,'68C11E8B0823546604534EC6C3558DA0.jpg','2021-03-07 21:24:52.532'),(1489,1234004,3,'68C11E8B0823546604534EC6C3558DA0.jpg','2021-03-07 21:24:52.547'),(1490,1234005,0,'CB4FCC8A34DB9EF462DD5E85EE710AF7.mp4','2021-03-07 21:29:13.733'),(1491,1234003,1,'7A10A4085F3F97DF619AFF0719D3134B.mp4','2021-03-07 21:29:39.972'),(1492,1234004,1,'3ABFB0530C63EF420E505A3C1A110438.mp4','2021-03-07 21:29:53.306'),(1493,1234006,2,'D1C8E71328E0D0688E40E6A90A136D12.jpg','2021-03-07 21:31:02.876'),(1494,1234006,3,'68F7500090D657FA9B9F88E3A93B888F.jpg','2021-03-07 21:31:02.895'),(1495,1234007,2,'B8E52BBC3DDD1375352A2EAE2E7695D1.jpg','2021-03-07 21:33:58.152'),(1496,1234007,3,'9175B9A3A60460A64FE0BBE6F3A2DB54.jpg','2021-03-07 21:33:58.303'),(1497,1234008,2,'0F72BF18BD0EB00CA806A312B29B7B76.jpg','2021-03-07 21:34:03.303'),(1498,1234008,3,'94E67E39405F9BF536120E4540EB43A0.jpg','2021-03-07 21:34:03.329'),(1499,1234009,0,'250072D3FCE5BCBB40EF359658B77B7A.mp4','2021-03-07 21:34:14.547'),(1500,1234006,1,'CE8515A0471C7D161626DEB538F57355.mp4','2021-03-07 21:36:03.818'),(1501,1234007,1,'60213EAD3B525072B276E415E75D42D2.mp4','2021-03-07 21:38:59.255'),(1502,1234008,1,'A6DB2C1D451EAD5FDD2CFD9A71619601.mp4','2021-03-07 21:39:04.584'),(1503,1234010,0,'382A513D71EE4D11B12F6D641A7A4BDB.mp4','2021-03-07 21:39:15.561'),(1504,1234011,2,'9BC5C8CD49C85B01FBAB69A9AD167635.jpg','2021-03-07 21:43:04.602'),(1505,1234011,3,'BA45473CA48FA4B32596F7B935C32D05.jpg','2021-03-07 21:43:04.623'),(1506,1234011,3,'BA45473CA48FA4B32596F7B935C32D05.jpg','2021-03-07 21:43:04.643'),(1507,1234012,2,'2E05CB5E725664D70F125E90CD53B10B.jpg','2021-03-07 21:43:10.303'),(1508,1234012,3,'4557717F7FF8F1E7BFA538C94AE066A6.jpg','2021-03-07 21:43:10.322'),(1509,1234012,3,'4557717F7FF8F1E7BFA538C94AE066A6.jpg','2021-03-07 21:43:10.338'),(1510,1234013,0,'D04C881EA4DBB236DF5EBBDB337C942E.mp4','2021-03-07 21:44:16.391'),(1511,1234014,2,'E613FF663C11F583BF89319E5445D51A.jpg','2021-03-07 21:46:41.162'),(1512,1234014,3,'5F8F4E7575EBBDC237D0C524829B1208.jpg','2021-03-07 21:46:41.178'),(1513,1234015,2,'181903A8263DCA1E02C110C5FF877772.jpg','2021-03-07 21:46:48.119'),(1514,1234015,3,'182393F8EA97F78575873636331FAF5E.jpg','2021-03-07 21:46:48.135'),(1515,1234011,1,'480766688B3E2B9B6990088228391602.mp4','2021-03-07 21:48:05.182'),(1516,1234012,1,'36DDEA84EA6AFF1248614FAEB0039527.mp4','2021-03-07 21:48:11.556'),(1517,1234016,0,'CE9A456640EDE880C16605EDB9FEA0FA.mp4','2021-03-07 21:49:17.233'),(1518,1234014,1,'D79104019D16B17E13429EB73685A1D9.mp4','2021-03-07 21:51:41.996'),(1519,1234015,1,'FAF8EF0665208742CB08EBCC39F267D9.mp4','2021-03-07 21:51:49.314'),(1520,1234017,2,'82D24353AEACDE380AC1BD8505F9FDFF.jpg','2021-03-07 21:52:32.368'),(1521,1234017,3,'862618A679A5883BA34E8A7D06A0168A.jpg','2021-03-07 21:52:32.383'),(1522,1234018,2,'5D0F15F26FB50298A406E234FFD23BE0.jpg','2021-03-07 21:52:35.517'),(1523,1234018,3,'599AFD5C3E4044E38550A8B9B30C00F1.jpg','2021-03-07 21:52:35.531'),(1524,1234018,3,'599AFD5C3E4044E38550A8B9B30C00F1.jpg','2021-03-07 21:52:35.546'),(1525,1234019,2,'A369FEA4D18C53F09DD73AC81D92E756.jpg','2021-03-07 21:52:38.113'),(1526,1234019,3,'0D77F120D327FCAEE1660B03DEF34978.jpg','2021-03-07 21:52:38.129'),(1527,1234020,0,'66ED26CE24982BB0430E299122293480.mp4','2021-03-07 21:54:18.081'),(1528,1234021,2,'2420001276DD2ECF9645D58281F7680C.jpg','2021-03-07 21:54:49.323'),(1529,1234021,3,'2A515A80A6C3B77DEE5D459370135287.jpg','2021-03-07 21:54:49.338'),(1530,1234022,2,'8DC0ACF7555D05D0851C6CFF9EEFF393.jpg','2021-03-07 21:54:51.284'),(1531,1234022,3,'95867620AB9A25D36E0544F7E5F51B7F.jpg','2021-03-07 21:54:51.416'),(1532,1234023,2,'B1F967829F60C58765126260363F1024.jpg','2021-03-07 21:54:56.652'),(1533,1234023,3,'7627E2D57CB830F3DDD3D04B8066EAAE.jpg','2021-03-07 21:54:56.669'),(1534,1234024,2,'19C4894DF902B95BBD36F0D45E4F3FE1.jpg','2021-03-07 21:55:00.185'),(1535,1234024,3,'CB231ECEEDC9F2308DE42BD27E38CFFF.jpg','2021-03-07 21:55:00.202'),(1536,1234024,3,'CB231ECEEDC9F2308DE42BD27E38CFFF.jpg','2021-03-07 21:55:00.216'),(1537,1234025,2,'CE7EE6BAD6BEBEBDC66FD641805CBF19.jpg','2021-03-07 21:55:02.158'),(1538,1234025,3,'F6AA62E1160CE767DD49BDF641947B9A.jpg','2021-03-07 21:55:02.174'),(1539,1234026,2,'EBC0E56CA4173E5BE876AA70D3872D90.jpg','2021-03-07 21:55:27.629'),(1540,1234026,3,'C71F3D3D9F6800B0F4180C3D575FC7E0.jpg','2021-03-07 21:55:27.645'),(1541,1234017,1,'831220C8B52FEA34513E838EA1B9B475.mp4','2021-03-07 21:57:32.809'),(1542,1234018,1,'6497AC25E182290A2BADA43AD485883A.mp4','2021-03-07 21:57:36.115'),(1543,1234019,1,'CEC1200EB848F1F43F36E51FA41B9717.mp4','2021-03-07 21:57:39.438'),(1544,1234027,0,'C9B0CBB2B352A0905B60FB850C1A75DF.mp4','2021-03-07 21:59:18.959'),(1545,1234021,1,'3C3B8DFC5B2371C14D34AAA9A2DBB609.mp4','2021-03-07 21:59:49.862'),(1546,1234022,1,'4C8AB71F0C495B8B16712AE890F7E86D.mp4','2021-03-07 21:59:52.194'),(1547,1234023,1,'A9FC99CEDA1353043D217755602218A3.mp4','2021-03-07 21:59:57.508'),(1548,1234026,1,'E93F86C60F5EFA65F51367A67B3A4F43.mp4','2021-03-07 22:00:28.916'),(1549,1234028,2,'E75A5E13D120C109B274D67EA1B78258.jpg','2021-03-07 22:03:45.389'),(1550,1234028,3,'F11C515C479A60D0096B679117472F7D.jpg','2021-03-07 22:03:45.406'),(1551,1234028,3,'F11C515C479A60D0096B679117472F7D.jpg','2021-03-07 22:03:45.419'),(1552,1234029,0,'AFF8ACB33B15D65429251BB86E67C278.mp4','2021-03-07 22:04:19.825'),(1553,1234030,2,'A1B7C51B3CD0CF19A02138EC94995C98.jpg','2021-03-07 22:04:58.006'),(1554,1234030,3,'8AD4A6355CF1C93050E5D55CF3F0BD4B.jpg','2021-03-07 22:04:58.023'),(1555,1234030,3,'8AD4A6355CF1C93050E5D55CF3F0BD4B.jpg','2021-03-07 22:04:58.035'),(1556,1234031,2,'0DA04DB90E899E8CC45E488065896C38.jpg','2021-03-07 22:05:15.290'),(1557,1234031,3,'AD8D6E64574A851D095E3B1E80353A1F.jpg','2021-03-07 22:05:15.309'),(1558,1234031,3,'AD8D6E64574A851D095E3B1E80353A1F.jpg','2021-03-07 22:05:15.325'),(1559,1234032,2,'D0BBF8001F33DC6D4F2ECD07843F0735.jpg','2021-03-07 22:06:01.951'),(1560,1234032,3,'D76E720CA387C373F8B0A3DAC5A26C5C.jpg','2021-03-07 22:06:01.966'),(1561,1234032,3,'D76E720CA387C373F8B0A3DAC5A26C5C.jpg','2021-03-07 22:06:01.981'),(1562,1234028,1,'B0BE6FF404365F82474ADABF65C51AFB.mp4','2021-03-07 22:08:46.621'),(1563,1234033,0,'BD80AABB99F2CF9EDA6C2BC4B31A1652.mp4','2021-03-07 22:09:20.673'),(1564,1234030,1,'15EF766DBA37A298683ED8A3F1313AC7.mp4','2021-03-07 22:09:59.095'),(1565,1234031,1,'4C5250885DF0DDF25BD4B2A45EBBA002.mp4','2021-03-07 22:10:16.439'),(1566,1234032,1,'0314EE88DBFE41345C95AA06A60BA0EE.mp4','2021-03-07 22:11:02.886'),(1567,1234034,0,'E5E6BCEDB56F57551BF3259CF66D89FE.mp4','2021-03-07 22:14:21.511'),(1568,1234035,2,'6287256811C3847D198CDF1EE6B6074B.jpg','2021-03-07 22:14:45.366'),(1569,1234035,3,'8089ECE67B4CC2886A11ECB8D2EA6A32.jpg','2021-03-07 22:14:45.382'),(1570,1234035,3,'8089ECE67B4CC2886A11ECB8D2EA6A32.jpg','2021-03-07 22:14:45.395'),(1571,1234036,0,'683B7746511EE504EEB0FCD8D38ECCCD.mp4','2021-03-07 22:19:22.517'),(1572,1234035,1,'6FD6274C3B70B03F911A8DE37334B70D.mp4','2021-03-07 22:19:46.563'),(1573,1234037,2,'DBDF0138F0741F48FE675BE1B2FF783D.jpg','2021-03-07 22:20:28.526'),(1574,1234037,3,'F703EBFE2DC5B5C345CEFFA04E4E03A9.jpg','2021-03-07 22:20:28.540'),(1575,1234038,2,'9828DEACC131BF14648E3CC2B1B77A91.jpg','2021-03-07 22:21:46.992'),(1576,1234038,3,'363745FDAF569AFF9A6FBA8C2F9E21D9.jpg','2021-03-07 22:21:47.023'),(1577,1234038,3,'363745FDAF569AFF9A6FBA8C2F9E21D9.jpg','2021-03-07 22:21:47.036'),(1578,1234039,2,'783F4EAC8A976B7F9D6579E4B4D22732.jpg','2021-03-07 22:24:12.493'),(1579,1234039,3,'41319A12EF418AB2554DEDBC311D6F57.jpg','2021-03-07 22:24:12.508'),(1580,1234040,0,'06C11FA47136FE14BF65ED044DB8BFF7.mp4','2021-03-07 22:24:23.367'),(1581,1234037,1,'56E169EC82EA4282859D50156C08BA81.mp4','2021-03-07 22:28:32.121'),(1582,1234038,1,'C734B098A1A6A45AE32366A2C57BCD52.mp4','2021-03-07 22:28:32.508'),(1583,1234039,1,'643F5E0D404D2381C68D6264DF64FEE5.mp4','2021-03-07 22:29:13.035'),(1584,1236001,2,'A8E5ECDDF4F0BA8357A9C11A29092873.jpg','2021-03-07 22:41:55.643'),(1585,1236001,3,'8D9E069592E0481508E214D387414B24.jpg','2021-03-07 22:41:55.967'),(1586,1236001,4,'A03DA4277C847E2E937AB28E69FAB2DA.txt','2021-03-07 22:41:55.989'),(1587,1236002,2,'5A0E159ECD1408F8DC788C40D21B69C0.jpg','2021-03-07 22:41:57.998'),(1588,1236002,3,'0E8F14F8965996811BF84C3E182E59B9.jpg','2021-03-07 22:41:58.015'),(1589,1236003,2,'1DB954A182612B2EEFE9E9891A0B0830.jpg','2021-03-07 22:42:39.498'),(1590,1236003,3,'0A91C3C57CDE6084CF29260770D00A08.jpg','2021-03-07 22:42:39.517'),(1591,1236003,4,'2A3ACF44E9E6EC9490CE1D8A6F1D7E9E.txt','2021-03-07 22:42:39.539'),(1592,1236004,2,'FB0F19718F1921EA22514F10211D7626.jpg','2021-03-07 22:45:13.829'),(1593,1236004,3,'5A37BF3EED186067DD9D8E2B1C436910.jpg','2021-03-07 22:45:13.846'),(1594,1236004,4,'53812144EE87638DFEC6E6550ED42B98.txt','2021-03-07 22:45:13.869'),(1595,1236005,2,'2E56491CF0C5F83FD64A9010F283F268.jpg','2021-03-07 22:45:30.832'),(1596,1236005,3,'809DDD2F6F5AABE296A249377BCDCE64.jpg','2021-03-07 22:45:30.850'),(1597,1236005,4,'02D24A282F038F31BCBD3E991174E1F2.txt','2021-03-07 22:45:30.870'),(1598,1236006,2,'BB7E0449446E2C99FDB3A639FE56C31D.jpg','2021-03-07 22:45:33.912'),(1599,1236006,3,'5B1EC0415B124566B9837858449CE108.jpg','2021-03-07 22:45:33.927'),(1600,1236006,4,'D834ADBFE78C4267EB97937D3F61F7A8.txt','2021-03-07 22:45:33.948'),(1601,1236007,2,'0975A82D1E1C04B0C33B36464AEB7737.jpg','2021-03-07 22:45:36.157'),(1602,1236007,3,'317C3033776181B5BE0948784E79EADD.jpg','2021-03-07 22:45:36.173'),(1603,1236008,0,'42D0BC36CDE6E4035200D7C55875332D.mp4','2021-03-07 22:45:39.507'),(1604,1236009,2,'D20D70858397C298BE6DEDD250356F35.jpg','2021-03-07 22:46:39.680'),(1605,1236009,3,'B8FAF787A776CA1EA15B9D5BE7021CEE.jpg','2021-03-07 22:46:39.697'),(1606,1236001,1,'C1549AE041C612FF05284DFA9FA0BC15.mp4','2021-03-07 22:46:56.436'),(1607,1236002,1,'BA6AD67F46D0E20B2A069065A1320F0E.mp4','2021-03-07 22:46:58.761'),(1608,1236003,1,'F79510CC3920DDF6A9458C1245BA097E.mp4','2021-03-07 22:47:40.086'),(1609,1236004,1,'99CCF52AF5E2787D1D0B8B501C6DE38E.mp4','2021-03-07 22:50:14.502'),(1610,1236005,1,'22A3F467A6DA9D79652A4B48BB084C54.mp4','2021-03-07 22:50:31.815'),(1611,1236006,1,'A01B7DC87A7598DCEA76C118D1230C47.mp4','2021-03-07 22:50:35.118'),(1612,1236007,1,'CEB53AA976420FE08BB7AA04F82C2E70.mp4','2021-03-07 22:50:37.411'),(1613,1236010,0,'931CE8C7F8F0AC68A0D8BB8DC476C191.mp4','2021-03-07 22:50:40.458'),(1614,1236009,1,'8F82783BBCC4F5EC719B6022ABF25010.mp4','2021-03-07 22:51:40.995'),(1615,1236011,2,'25154A0330B621FD643D7C51542A072D.jpg','2021-03-07 22:53:27.518'),(1616,1236011,3,'F772C294AF7DE46228F3005ECD29F235.jpg','2021-03-07 22:53:27.536'),(1617,1236011,4,'32B47323B1195E0A802D02EDC22929A4.txt','2021-03-07 22:53:27.558'),(1618,1236012,2,'4AEB1EAED70690429144433AC52EF5DB.jpg','2021-03-07 22:53:47.813'),(1619,1236012,3,'4B0C8A70551B8B5D537213517748873F.jpg','2021-03-07 22:53:47.830'),(1620,1236013,0,'CBA2C448F9E1902CE627A3092BBEC593.mp4','2021-03-07 22:55:41.508'),(1621,1236014,2,'D5AD76E48D5FC888F71D92F65186F6F4.jpg','2021-03-07 22:56:15.254'),(1622,1236014,3,'AB0B00E54C6E7AE446DBD1C11688CCBF.jpg','2021-03-07 22:56:15.286'),(1623,1236011,1,'702209BBF1DDD4F716A6978150BDA828.mp4','2021-03-07 22:58:28.514'),(1624,1236012,1,'0AE0ECB8D5596CF13886B73DA09D7D92.mp4','2021-03-07 22:58:48.910'),(1625,1236015,2,'B16DE8D265F478B57796A0825156FE04.jpg','2021-03-07 22:58:55.367'),(1626,1236015,3,'F4F02173C098BEC1C4A88D5CD460FA9F.jpg','2021-03-07 22:58:55.385'),(1627,1236015,4,'91BB72F15DBE6D0E702AD49D02E818B9.txt','2021-03-07 22:58:55.409'),(1628,1236016,2,'B955AAE0BEA30845098DCABA93283E8C.jpg','2021-03-07 23:00:37.996'),(1629,1236016,3,'606D476E8C05E2CDCEB9AB6504F6CD90.jpg','2021-03-07 23:00:38.017'),(1630,1236017,2,'D42157295306A56FE0B958FCDC3D4827.jpg','2021-03-07 23:00:40.985'),(1631,1236017,3,'8902A7271963B4931BA4FAB3BBC2DDD2.jpg','2021-03-07 23:00:41.004'),(1632,1236017,4,'C1142933F9AC3A60DCA35AC5AB9B7A5B.txt','2021-03-07 23:00:41.028'),(1633,1236018,0,'AEB5DAA08AEF8D94A4ACA785C7219823.mp4','2021-03-07 23:00:42.332'),(1634,1236014,1,'7C72C76D41D81EF548B002B24B06C59C.mp4','2021-03-07 23:01:16.296'),(1635,1236015,1,'395CA859C5E06F243DF66E432FF9DCC3.mp4','2021-03-07 23:03:56.676'),(1636,1236019,2,'6395BFD64BEE8556F743B5DD0AA3C3C3.jpg','2021-03-07 23:04:12.872'),(1637,1236019,3,'E524D46DDA9171F018D41F9358E8CEA4.jpg','2021-03-07 23:04:12.892'),(1638,1236016,1,'8265AA199ABAA0FEFFFA34F4EDDE3A7A.mp4','2021-03-07 23:05:39.021'),(1639,1236017,1,'FD7435AC213F79B305E479354A45817C.mp4','2021-03-07 23:05:42.327'),(1640,1236020,0,'6162677D3568487BF885BC71984CBC5A.mp4','2021-03-07 23:05:43.190'),(1641,1236021,2,'153E9861DF267E27FD6053585B7CA527.jpg','2021-03-07 23:07:03.593'),(1642,1236021,3,'5B217AE577E9B078DBA69496E77E79F8.jpg','2021-03-07 23:07:03.611'),(1643,1236019,1,'79E2CD77620AAEB2A9DDF75817C736B3.mp4','2021-03-07 23:09:13.749'),(1644,1236022,2,'B313859F6AA3FB4ACCD9D73034CD9DBB.jpg','2021-03-07 23:09:23.872'),(1645,1236022,3,'B4F826CBD60105320AF55959B19D0A1E.jpg','2021-03-07 23:09:23.894'),(1646,1236022,4,'2736BD54411CC40AEDE2C44F9398D08F.txt','2021-03-07 23:09:23.920'),(1647,1236023,2,'2723D5874A16A3B3DCB3124231A670F4.jpg','2021-03-07 23:09:34.224'),(1648,1236023,3,'3F6EBD35B0CA0B7219C605F763B7803C.jpg','2021-03-07 23:09:34.243'),(1649,1236024,2,'DFCC734014A8BF915DDE24918056FFC9.jpg','2021-03-07 23:09:36.224'),(1650,1236024,3,'B6EA4641D10878038D31C3D5C27E4B81.jpg','2021-03-07 23:09:36.243'),(1651,1236025,0,'E1CBE431A4BA407554D1373887B4B3D9.mp4','2021-03-07 23:10:44.032'),(1652,1236021,1,'9B14E1767DE264C2C4562BD47F6CD0A1.mp4','2021-03-07 23:12:04.167'),(1653,1236026,2,'D9B7A23D42F9A9D4432716EC175F1604.jpg','2021-03-07 23:12:10.733'),(1654,1236026,3,'BF5F2C1F6E396A2D646D9BB85D9A33EA.jpg','2021-03-07 23:12:10.752'),(1655,1236026,4,'2DF9378DCF183D4D78BDC1986DFC9DA0.txt','2021-03-07 23:12:10.774'),(1656,1236027,2,'44D76D920CC634905A086028F4FFF287.jpg','2021-03-07 23:12:15.179'),(1657,1236027,3,'BDDCFC865820EC49028DA129AC4ABAB7.jpg','2021-03-07 23:12:15.195'),(1658,1236027,4,'C651833A008104118109792A7405C47B.txt','2021-03-07 23:12:15.217'),(1659,1236028,2,'C91EF3131953670CCC18A52C41A508D5.jpg','2021-03-07 23:13:16.383'),(1660,1236028,3,'9275B290C86277A152FAF452C1612C4D.jpg','2021-03-07 23:13:16.401'),(1661,1236029,2,'87800CF8F9DA4B11793E8685B14DD404.jpg','2021-03-07 23:13:22.356'),(1662,1236029,3,'2D3D6CF273EBCCC2F9BD932A286FCE06.jpg','2021-03-07 23:13:22.373'),(1663,1236030,2,'0548AB36A7044A2E064C510E7CB1D9CA.jpg','2021-03-07 23:13:34.755'),(1664,1236030,3,'C8D9106A633819DCE03A3BB2BA7B4006.jpg','2021-03-07 23:13:34.773'),(1665,1236031,2,'076C03B1C64036AFC80403286DB65F47.jpg','2021-03-07 23:13:38.139'),(1666,1236031,3,'73D0494506913CD9FA961E428457EB5E.jpg','2021-03-07 23:13:38.156'),(1667,1236032,2,'7109F6E66D07E699F11B08B79D5069CD.jpg','2021-03-07 23:13:40.128'),(1668,1236032,3,'55DB14AC28A870392BE74CC082D1793F.jpg','2021-03-07 23:13:40.146'),(1669,1236033,2,'CCB13CF1BE1DEF4E8052FFECD2C45D33.jpg','2021-03-07 23:13:53.603'),(1670,1236033,3,'1E77A0BC55DFF0728DA7F631D6059D8D.jpg','2021-03-07 23:13:53.622'),(1671,1236022,1,'C1E54BAF88E5CDE35D7A77200EBC65CD.mp4','2021-03-07 23:14:24.566'),(1672,1236023,1,'F7362B227BED8756D885BF30BF335EAD.mp4','2021-03-07 23:14:34.890'),(1673,1236024,1,'F07931DDDD8F7E2E9140A95DCC0361A9.mp4','2021-03-07 23:14:37.194'),(1674,1236034,2,'9292625A5FAE237B06DBC3B8FD24AC33.jpg','2021-03-07 23:14:56.419'),(1675,1236034,3,'D79C3B0EB5A24520BBBA3990D86C2559.jpg','2021-03-07 23:14:56.433'),(1676,1236035,0,'C1B49EC1673AE7633096F85BD1381C02.mp4','2021-03-07 23:15:44.998'),(1677,1236026,1,'88A1F5F1E8EB760C123709A4ACD71AAF.mp4','2021-03-07 23:17:11.621'),(1678,1236027,1,'036AD512C40B70C412CA1C7026C49752.mp4','2021-03-07 23:17:15.930'),(1679,1236028,1,'B8FAD778309374C1450A47950ED6B019.mp4','2021-03-07 23:18:17.278'),(1680,1236029,1,'506D40D24154795E7A58538A2F81445D.mp4','2021-03-07 23:18:23.902'),(1681,1236030,1,'E9F41A247758217FEBD41B700097D55F.mp4','2021-03-07 23:18:35.267'),(1682,1236031,1,'A3273EBF62B24AEB2C8E676FCC7623AB.mp4','2021-03-07 23:18:38.605'),(1683,1236032,1,'6BB300C33CAC3F7F4E9FEC1EB25CE8A7.mp4','2021-03-07 23:18:40.916'),(1684,1236033,1,'BE5FAD75FEABF0BD5216318BD6E3F9C0.mp4','2021-03-07 23:18:54.230'),(1685,1236036,2,'CAF992C3F10B509AFAA15C35985C30AE.jpg','2021-03-07 23:19:12.875'),(1686,1236036,3,'CB6562B61C9ED55FBD4A33532AA54782.jpg','2021-03-07 23:19:12.893'),(1687,1236036,4,'9035CFDA30DE60C5A893591EAEDC4AB7.txt','2021-03-07 23:19:12.914'),(1688,1236034,1,'41304623D6877745ED2FB024AF3FE515.mp4','2021-03-07 23:19:57.575'),(1689,1236037,0,'959005342B6F90295D0805CBF55E1135.mp4','2021-03-07 23:20:45.868'),(1690,1236036,1,'B1CE6F00B98FEA4DF82B13632DFEA438.mp4','2021-03-07 23:24:14.047'),(1691,1236038,0,'F669A0D99D2C28248398293773A1EA1B.mp4','2021-03-07 23:25:46.749'),(1692,1236039,2,'AA1ADFF53A72DA4B6F37955F069C575F.jpg','2021-03-07 23:29:41.632'),(1693,1236039,3,'E1677D1EC7108F445C277F42B8796D62.jpg','2021-03-07 23:29:41.648'),(1694,1236039,4,'1E237A0F339A5126E28D5807CDF715AC.txt','2021-03-07 23:29:41.669'),(1695,1236040,2,'5EDE737ACD26CF652846BC5A84A23A59.jpg','2021-03-07 23:29:44.018'),(1696,1236040,3,'907715D8CF227390E68039790DD7AFFD.jpg','2021-03-07 23:29:44.032'),(1697,1236040,4,'3E947AF7ECBD29F1150E895BF488C99F.txt','2021-03-07 23:29:44.052'),(1698,1236041,2,'80151748A406FAA72A53E69C36B556AC.jpg','2021-03-07 23:29:46.458'),(1699,1236041,3,'DEC794798CE09D5B1DA0161E7C9EF162.jpg','2021-03-07 23:29:46.474'),(1700,1236041,4,'F7E8903E1524EA0745B0BEE04DDF6ACB.txt','2021-03-07 23:29:46.496'),(1701,1236042,2,'325FD719A85B09DBC9C4CB95FEDD3F5A.jpg','2021-03-07 23:29:53.012'),(1702,1236042,3,'41558F9352EF7086E75893B4E17BAFA3.jpg','2021-03-07 23:29:53.028'),(1703,1236042,4,'88A852E729212611C08050C8CE323472.txt','2021-03-07 23:29:53.051'),(1704,1236043,2,'896D258ECA9E045F30698B710AB71BAF.jpg','2021-03-07 23:29:55.350'),(1705,1236043,3,'4E7FCFAFCB8C1ECD6DCDB7D7D0685E1D.jpg','2021-03-07 23:29:55.364'),(1706,1236043,4,'8E27153B6EC1E1E42301248C542386E0.txt','2021-03-07 23:29:55.385'),(1707,1236044,2,'9DF86873166B9E9A2CC4B14125FC7371.jpg','2021-03-07 23:29:58.080'),(1708,1236044,3,'933727F1D03810D6AFE26F4F7A1516A3.jpg','2021-03-07 23:29:58.098'),(1709,1236044,4,'32A071FF54DD1027741D7621F3E60506.txt','2021-03-07 23:29:58.118'),(1710,1236045,0,'A09DE85FF9A6D7D15A513008D7A94086.mp4','2021-03-07 23:30:47.575'),(1711,1236039,1,'60968EDB263A7BC516640C8BED98ECD5.mp4','2021-03-07 23:34:42.691'),(1712,1236040,1,'E432534F7F1926130439678EE1BAD2DF.mp4','2021-03-07 23:34:44.999'),(1713,1236041,1,'E378374C18EE81DB8DA3D958AB773D85.mp4','2021-03-07 23:34:47.284'),(1714,1236042,1,'476A1E55D8241CB9BF795C6BCEAFA8A0.mp4','2021-03-07 23:34:53.596'),(1715,1236043,1,'476A1E55D8241CB9BF795C6BCEAFA8A0.mp4','2021-03-07 23:34:55.888'),(1716,1236044,1,'7EB4D8CE9E5E248DE8A69EAD86E8059F.mp4','2021-03-07 23:34:59.187'),(1717,1236046,0,'A5D26A19926DB39426074BAF6A2B2513.mp4','2021-03-07 23:35:48.465'),(1718,1236047,2,'8BEDA5A1EACC5A53D08930680B57307F.jpg','2021-03-07 23:35:54.760'),(1719,1236047,3,'3ED59D8412C593446451185F11785D89.jpg','2021-03-07 23:35:54.777'),(1720,1236048,2,'4E67F325DA2710F9CF8D54F6B4D723C5.jpg','2021-03-07 23:35:57.973'),(1721,1236048,3,'E429297A349D9A003D2476A58156C7FC.jpg','2021-03-07 23:35:57.989'),(1722,1236049,2,'B76099992662A4A91F406E2C797FDA02.jpg','2021-03-07 23:36:05.949'),(1723,1236049,3,'2E903471723F44385420EC034945BEC3.jpg','2021-03-07 23:36:05.967'),(1724,1236050,2,'BD31278437634CD37E287B30FD10242A.jpg','2021-03-07 23:36:14.672'),(1725,1236050,3,'400D749E03290AC6CEC6B1A6475C52F8.jpg','2021-03-07 23:36:14.691'),(1726,1236051,2,'5D105D6078828EF3067A8D94B909725B.jpg','2021-03-07 23:36:19.207'),(1727,1236051,3,'52B083921809346B3F0A06777E66F17E.jpg','2021-03-07 23:36:19.226'),(1728,1236052,2,'AD119D3F990CCAA496FC575D4C1CF15F.jpg','2021-03-07 23:36:22.478'),(1729,1236052,3,'2D03FF9AEC36A84B7856E54095B7A409.jpg','2021-03-07 23:36:22.494'),(1730,1236053,2,'C5023852B1C599AF382D447B2E91BCD9.jpg','2021-03-07 23:40:26.251'),(1731,1236053,3,'432424264008EB99888411752C774B11.jpg','2021-03-07 23:40:26.270'),(1732,1236054,2,'413DFA13C89096989686B430BBB01A56.jpg','2021-03-07 23:40:37.218'),(1733,1236054,3,'DC3DCCD1090158A7696BFE6EB2DCDD14.jpg','2021-03-07 23:40:37.245'),(1734,1236054,4,'78FB99EFFAA49ED03B05D2547BEC9E65.txt','2021-03-07 23:40:37.266'),(1735,1236055,0,'F8F4FCBC8DE0155211963ED3D070B05B.mp4','2021-03-07 23:40:49.458'),(1736,1236047,1,'336EE123D573A11EFF84C73C5B77B704.mp4','2021-03-07 23:40:55.864'),(1737,1236048,1,'2075A3727A6DE1C6B64E2D10CD04A2BA.mp4','2021-03-07 23:40:59.163'),(1738,1236049,1,'6B04CB7B8EA428874BBDC24B9969DFED.mp4','2021-03-07 23:41:06.467'),(1739,1236050,1,'B8E18D9A66EB8F5B0A66C805B286E2B3.mp4','2021-03-07 23:41:15.793'),(1740,1236051,1,'3595AEDCC4552B5037F1EBE4E59A8E96.mp4','2021-03-07 23:41:20.091'),(1741,1236052,1,'F88FAE03D90C40E6AF3A8B3642FBBB38.mp4','2021-03-07 23:41:23.395'),(1742,1236053,1,'841D17BD01104955B5A039E1157E650C.mp4','2021-03-07 23:45:26.830'),(1743,1236054,1,'B3D3DF9700543460D16D973BFE9AE85B.mp4','2021-03-07 23:45:38.200'),(1744,1236056,2,'4F28283402CEE711CFBA803B582AB775.jpg','2021-03-07 23:45:45.667'),(1745,1236056,3,'09B9832BF0821C0D234FE7C2AC179B3A.jpg','2021-03-07 23:45:45.696'),(1746,1236057,0,'FDF66421F1479E42FFF5D7180564ADD1.mp4','2021-03-07 23:45:50.317'),(1747,1236058,2,'C277055DE6E4500331AEC91508801F04.jpg','2021-03-07 23:46:56.236'),(1748,1236058,3,'B80828463E4734EF7669CE614D0C35BB.jpg','2021-03-07 23:46:56.252'),(1749,1236056,1,'0DF4DCB0B3ACC810234000BFB6EAC497.mp4','2021-03-07 23:50:46.676'),(1750,1236059,0,'7602AF1FD4672CF887ED8C2E8F7684F8.mp4','2021-03-07 23:50:51.197'),(1751,1236060,2,'3422017E9C3F11F4F40B0914680590D0.jpg','2021-03-07 23:51:02.890'),(1752,1236060,3,'CCE1DABE655D2EDFEF420230DED6B0D4.jpg','2021-03-07 23:51:02.921'),(1753,1236060,4,'D99EEE7033AB7E56E7653BFBDE4DD670.txt','2021-03-07 23:51:02.946'),(1754,1236061,2,'A2DD09D66F0DF5AA64320421E788208C.jpg','2021-03-07 23:51:05.568'),(1755,1236061,3,'C93153E27F10E49BC06D9D3342CE9CC7.jpg','2021-03-07 23:51:05.584'),(1756,1236061,4,'C4CB4DEC8686A54B52FACAF57A4E4452.txt','2021-03-07 23:51:05.604'),(1757,1236062,2,'4A676C3D36E88FCBDE9C591DB45CB5E3.jpg','2021-03-07 23:51:14.203'),(1758,1236062,3,'1CE0FE62C1EFF092709AD2A6F27D1D90.jpg','2021-03-07 23:51:14.221'),(1759,1236062,4,'CCD79BEA6A6CDDFD938AF4A0762DD7F2.txt','2021-03-07 23:51:14.241'),(1760,1236063,2,'6183C5A5C3111A57C3D949813D827DCF.jpg','2021-03-07 23:51:17.498'),(1761,1236063,3,'C4291B242FD4874DBB0583A8934F85C2.jpg','2021-03-07 23:51:17.512'),(1762,1236058,1,'2B67213C8C620F625C9B19BB50468C7E.mp4','2021-03-07 23:51:57.167'),(1763,1236064,2,'F9413A6886D08187735E165A6C7F13E4.jpg','2021-03-07 23:52:07.919'),(1764,1236064,3,'856FB3BEFD1838C78DFC8D641212E3F6.jpg','2021-03-07 23:52:07.935'),(1765,1236065,2,'B34E3409C3EF58B3ECA192AC193184F5.jpg','2021-03-07 23:53:16.142'),(1766,1236065,3,'1D672A3E370EFA5D2FB53C1CDD7ADC0F.jpg','2021-03-07 23:53:16.157'),(1767,1236066,2,'FBF2CB187411C8D1BA17EC86980CEB65.jpg','2021-03-07 23:53:23.910'),(1768,1236066,3,'2C3B83049E6798A9D75AD954A49F5AAC.jpg','2021-03-07 23:53:23.926'),(1769,1236066,4,'1B601CB8FD0E5E3AD811BAB7E2DFFC26.txt','2021-03-07 23:53:23.946'),(1770,1236067,2,'218FD32C29B735292BB21ABB76676427.jpg','2021-03-07 23:53:33.875'),(1771,1236067,3,'2599E95A8EE6E7E1C60DDDE448675C33.jpg','2021-03-07 23:53:33.891'),(1772,1236067,4,'A8A36CBA196A8DD50FD116C4F1D60481.txt','2021-03-07 23:53:33.911'),(1773,1236068,2,'8434F471F41207E7043B2EA20B7C56A1.jpg','2021-03-07 23:53:36.584'),(1774,1236068,3,'1590D0AC38059E66BAA9B24A8927F158.jpg','2021-03-07 23:53:36.599'),(1775,1236068,4,'C081C6BB7AA0F7B254A801929A9FB1B7.txt','2021-03-07 23:53:36.621'),(1776,1236069,2,'74F4B5E8B9634B5069953D4EE8D23B20.jpg','2021-03-07 23:53:39.031'),(1777,1236069,3,'9095CB6C46FDE8BDE4CB96CE0F4A5593.jpg','2021-03-07 23:53:39.045'),(1778,1236069,4,'2505876236257037D1DCC453390186FC.txt','2021-03-07 23:53:39.069'),(1779,1236070,2,'CF9E4D24CB970B1BABF537CB3E07598C.jpg','2021-03-07 23:53:41.847'),(1780,1236070,3,'9CB525825E15A1B74BACD6A99B4599EA.jpg','2021-03-07 23:53:41.862'),(1781,1236070,4,'432BD73D6A01DB423F643D17552A04EE.txt','2021-03-07 23:53:41.882'),(1782,1236071,2,'7BC3AD5D39D3D6FC9F4B1B15C0020865.jpg','2021-03-07 23:53:47.944'),(1783,1236071,3,'793BFC8DF7375C8F8C66AF92CD7E5072.jpg','2021-03-07 23:53:47.975'),(1784,1236071,4,'CDB59E8F3D2E134BDC47528F8EBD6BA7.txt','2021-03-07 23:53:47.998'),(1785,1236072,2,'425FAB0416645EBC075CF8C8A48E61BD.jpg','2021-03-07 23:53:53.601'),(1786,1236072,3,'00CF5809FA4D1964575C1565DE856C03.jpg','2021-03-07 23:53:53.616'),(1787,1236073,2,'462A124961B87B524BE12E6F9995B882.jpg','2021-03-07 23:53:55.752'),(1788,1236073,3,'8915C7B297451C57D008944CF44E2687.jpg','2021-03-07 23:53:55.767'),(1789,1236074,0,'93D2722913CFA806B86CDD104691EE30.mp4','2021-03-07 23:55:52.041'),(1790,1236060,1,'B2DF24F8364BA09D5DB8D87E89D5A200.mp4','2021-03-07 23:56:03.600'),(1791,1236061,1,'B59A02B977CE9FD873567E3D872B8A67.mp4','2021-03-07 23:56:06.896'),(1792,1236062,1,'E795A9CD63B9681AD47C8194CC887F3E.mp4','2021-03-07 23:56:15.188'),(1793,1236063,1,'7F144381922400FE62393296642FAEE0.mp4','2021-03-07 23:56:18.496'),(1794,1236064,1,'FDEAE0BC39BB6334B285E3D9D36D0D13.mp4','2021-03-07 23:57:08.867'),(1795,1236075,2,'E51D6D739DFF32E684EE361F4933FFD0.jpg','2021-03-07 23:57:35.513'),(1796,1236075,3,'3CA46E4F6AC687F75559744B5C37ABFE.jpg','2021-03-07 23:57:35.528'),(1797,1236075,4,'F2B2F73B2FFDB3B4DC5BF08DD32A04FD.txt','2021-03-07 23:57:35.548'),(1798,1236076,2,'87B59AF2FDD63B35147CE61E24FDB0BB.jpg','2021-03-07 23:57:38.096'),(1799,1236076,3,'06CA5F3F32255832349BA450076ECE40.jpg','2021-03-07 23:57:38.114'),(1800,1236076,4,'8D67B1EFE8380411004AEA1F79C85A90.txt','2021-03-07 23:57:38.133'),(1801,1236065,1,'831296C028E7C8EB4F45797384166D92.mp4','2021-03-07 23:58:17.243'),(1802,1236066,1,'19D1A3E9D542CC8A1404BDD43896BEF3.mp4','2021-03-07 23:58:24.559'),(1803,1236067,1,'FF47171465F8C13C63EF6AF0DE2B9C4E.mp4','2021-03-07 23:58:34.912'),(1804,1236068,1,'178FE6987CB763819744BF3308459175.mp4','2021-03-07 23:58:37.220'),(1805,1236069,1,'F8C9B300CBC41910A03A68D217B48931.mp4','2021-03-07 23:58:39.538'),(1806,1236070,1,'5821C35A9B9A95B8117CBC772BEA96E9.mp4','2021-03-07 23:58:42.860'),(1807,1236071,1,'B1AFF0BDF14FB09985BD81C02657BA6F.mp4','2021-03-07 23:58:49.162'),(1808,1236072,1,'878ABCC95F5CFF1E4B81D13E12D33FBA.mp4','2021-03-07 23:58:54.477'),(1809,1236073,1,'878ABCC95F5CFF1E4B81D13E12D33FBA.mp4','2021-03-07 23:58:56.780'),(1810,1236077,0,'CFC4E3F54356706644D8A1C6BC0B6723.mp4','2021-03-08 00:00:53.110'),(1811,1236075,1,'D9351ABE4A4F50AD0B28CFBEECAFECD6.mp4','2021-03-08 00:02:36.208'),(1812,1236076,1,'383B00BC53183BE8135FC01F4A90D136.mp4','2021-03-08 00:02:38.524'),(1813,1236078,2,'3B026F3B3AC6D7CDDF7BEBC62B54C582.jpg','2021-03-08 00:02:55.047'),(1814,1236078,3,'33A806455374B9B5D30984B11B8F77B3.jpg','2021-03-08 00:02:55.064'),(1815,1236078,4,'82D2D0BC912043775731895BA6403558.txt','2021-03-08 00:02:55.086'),(1816,1236079,2,'F3792FB690774ABF383176FBBF1A5724.jpg','2021-03-08 00:04:12.064'),(1817,1236079,3,'D0E3636300B4F65254A4F8203E266147.jpg','2021-03-08 00:04:12.087'),(1818,1236079,4,'A4A600E0070B0B509B2D5FF459A21388.txt','2021-03-08 00:04:12.107'),(1819,1236080,0,'9AD29EB46AB18918275573FBE08EE299.mp4','2021-03-08 00:05:54.435'),(1820,1236078,1,'AF997B107473FDC2339F40BC547AEFCC.mp4','2021-03-08 00:07:56.158'),(1821,1236079,1,'27F9CDA0FE7FE834EFCFBAEDD95DD21D.mp4','2021-03-08 00:17:26.393'),(1822,1238001,2,'AB4E24A193390FEDB5978F61E083D5E9.jpg','2021-03-08 00:17:28.442'),(1823,1238001,3,'1EE25F28FC97E988F451348B2F94A0D1.jpg','2021-03-08 00:17:28.462'),(1824,1238002,2,'C2C183B2F3F31F7534D6176DF5EA5918.jpg','2021-03-08 00:17:32.588'),(1825,1238002,3,'DB77D80B9835361C69FE409F7479FDD7.jpg','2021-03-08 00:17:32.604'),(1826,1238003,2,'60C7BBEB123462B30E674F33332D76B1.jpg','2021-03-08 00:17:53.779'),(1827,1238003,3,'24408D3479E6FCD6C22C9F107FCB42E8.jpg','2021-03-08 00:17:53.798'),(1828,1238004,2,'A1D37AC0CA70D55D42968CAD37BD2092.jpg','2021-03-08 00:17:55.781'),(1829,1238004,3,'F9AED37730FE02F422D143EC0D8EEC79.jpg','2021-03-08 00:17:55.798'),(1830,1238005,2,'A497EA6EF95F86D64FCAE9317BDE7D8B.jpg','2021-03-08 00:18:51.561'),(1831,1238005,3,'D2A3A5EF389E771F772845AB10011844.jpg','2021-03-08 00:18:51.578'),(1832,1238006,2,'10B30C7B93C02EFECEF2DA12A578066C.jpg','2021-03-08 00:20:27.182'),(1833,1238006,3,'16387DC387B92AC93AC0C221C6B90FE6.jpg','2021-03-08 00:20:27.199'),(1834,1238007,2,'E53A7006B57A6E6CAC88850D3FCA6262.jpg','2021-03-08 00:21:35.536'),(1835,1238007,3,'A0A3FBBB02F1FA2643E2970CFC595CEA.jpg','2021-03-08 00:21:35.553'),(1836,1238007,4,'B491DB3E112E4ECCF6DD55B47B8ED729.txt','2021-03-08 00:21:35.576'),(1837,1238008,0,'79CBEABB06EA5E95D878319C8F4C0094.mp4','2021-03-08 00:22:24.852'),(1838,1238009,2,'AF1388429B36D82743B2E6311B8E795A.jpg','2021-03-08 00:23:07.692'),(1839,1238009,3,'FA54EBE4D88E3C0EF862881F2E9D61EE.jpg','2021-03-08 00:23:07.709'),(1840,1238009,4,'5F5F4603AEEECC64A20C09EA085F9BF8.txt','2021-03-08 00:23:07.727'),(1841,1238010,2,'EC2799B77DDB9BFBD14F793DBB48167A.jpg','2021-03-08 00:23:14.398'),(1842,1238010,3,'C98A1F5B35C68A27A7D508968F2A4567.jpg','2021-03-08 00:23:14.425'),(1843,1238005,1,'0A3C18771E98D60A2A3036B68062F62F.mp4','2021-03-08 00:23:52.051'),(1844,1238011,2,'BFAE150DF3C88C0AF65DDC1DF6B74DA2.jpg','2021-03-08 00:24:15.440'),(1845,1238011,3,'0CD5E5B4CBB778CDD9FEA78C9A125F62.jpg','2021-03-08 00:24:15.456'),(1846,1238006,1,'603B9052D2403AE1CC45682F6E253893.mp4','2021-03-08 00:25:28.436'),(1847,1238007,1,'D33C43D480EE30B5BDD2E00C3C195A8B.mp4','2021-03-08 00:26:36.777'),(1848,1238012,0,'F7A142DFF233D04856EF2060D7A029F4.mp4','2021-03-08 00:27:25.704'),(1849,1238009,1,'AED55131C5F491C467C1E05AAB497189.mp4','2021-03-08 00:28:08.159'),(1850,1238010,1,'3BB518A975685CBAE3CFD164C526F8AA.mp4','2021-03-08 00:28:15.511'),(1851,1238011,1,'096572BB2A130D9C4C862EBB0EDC6582.mp4','2021-03-08 00:29:15.903'),(1852,1238013,2,'7B616B47F79A493280EB78F27BD06FB1.jpg','2021-03-08 00:31:16.469'),(1853,1238013,3,'EE5493C3005C5B3C77BCFD46B9D775F2.jpg','2021-03-08 00:31:16.490'),(1854,1238013,4,'9062B6C3EF7923F2892B3D3C2634CBC3.txt','2021-03-08 00:31:16.514'),(1855,1238014,0,'B15D2C8CAFD5BA9A808A8111ECA5F8E6.mp4','2021-03-08 00:32:26.769'),(1856,1238015,2,'5AF765B1C108AC23E475FBF950BA3A7D.jpg','2021-03-08 00:34:55.672'),(1857,1238015,3,'5AE1F32870A57EEECB809AE297DA3B2D.jpg','2021-03-08 00:34:55.689'),(1858,1238015,4,'ADD46890B09B24D291D48D261C7FDEA7.txt','2021-03-08 00:34:55.709'),(1859,1238013,1,'6BE86BE19F04E65ED89FAC0C118E2363.mp4','2021-03-08 00:36:17.804'),(1860,1238016,0,'994EC22537F610C4C9F4DF8CEC6BFEB9.mp4','2021-03-08 00:37:27.665'),(1861,1238017,2,'71DC41E09CAA8A56B7729CC239E51058.jpg','2021-03-08 00:38:41.753'),(1862,1238017,3,'937BE4877E87B210311C79D9CBFCCFC3.jpg','2021-03-08 00:38:41.768'),(1863,1238017,4,'9CB1AB4163D0B30B7C224FFF50DE9B50.txt','2021-03-08 00:38:41.788'),(1864,1238015,1,'5E61477146CC85685A3AAC91E74DCAFB.mp4','2021-03-08 00:39:56.302'),(1865,1238018,2,'7261C4B2FD3322A69E5C5942B0E06EFE.jpg','2021-03-08 00:41:23.991'),(1866,1238018,3,'D7913E710B034CAF3FA17CFE485AB79C.jpg','2021-03-08 00:41:24.011'),(1867,1238018,4,'B2901909BD1A1944A7A9B6446368ED7A.txt','2021-03-08 00:41:24.031'),(1868,1238019,2,'B62176EEADAF863B34928960B4E68D78.jpg','2021-03-08 00:41:31.304'),(1869,1238019,3,'34F1BB85D6AC2BF602AB4450408E5614.jpg','2021-03-08 00:41:31.319'),(1870,1238019,4,'FB146BDF6B3D417111408A6ACCB365C4.txt','2021-03-08 00:41:31.339'),(1871,1238020,0,'B0A915336AE161CF871AA2B7FD0D7739.mp4','2021-03-08 00:42:28.567'),(1872,1238017,1,'7615A9424BFB366F954520CF6D2D794C.mp4','2021-03-08 00:43:42.740'),(1873,1238021,2,'8BCAB43E5AFD3D7F9D19BE7493A7A26A.jpg','2021-03-08 00:43:54.990'),(1874,1238021,3,'D3EAC8D25462E9470E290BF133A34A9C.jpg','2021-03-08 00:43:55.025'),(1875,1238022,2,'34CCC135BB75D6953ACD118CBE9E0DB3.jpg','2021-03-08 00:44:09.251'),(1876,1238022,3,'E0B571A0E0F39A96564246CE1AC335EA.jpg','2021-03-08 00:44:09.369'),(1877,1238023,2,'583392C229CC9A4974D13C65F055F098.jpg','2021-03-08 00:45:38.787'),(1878,1238023,3,'AEB7A3C954D82B801CAC6144FDFD73AB.jpg','2021-03-08 00:45:38.818'),(1879,1238018,1,'9A262348699FDB23B947A8F5C6B229C7.mp4','2021-03-08 00:46:25.148'),(1880,1238019,1,'CB2CCFC961FE332AAFEEDFF5FA4A7C53.mp4','2021-03-08 00:46:32.462'),(1881,1238024,2,'8232CE14CDA5CF2D05B6E8C20A1D6512.jpg','2021-03-08 00:47:07.213'),(1882,1238024,3,'43EED2CC4C13060FB43E224ACB44290D.jpg','2021-03-08 00:47:07.246'),(1883,1238025,0,'EB05A331CC720269BAA60AF7C2D31329.mp4','2021-03-08 00:47:29.423'),(1884,1238021,1,'02947F2D08FAE3FDF32D1C2F0C2C5338.mp4','2021-03-08 00:48:55.867'),(1885,1238022,1,'1247B7107EAFB27FA978A52C40BE008D.mp4','2021-03-08 00:49:10.176'),(1886,1238023,1,'A0D2D5F4326FBB82043463530E12308E.mp4','2021-03-08 00:50:39.643'),(1887,1238026,2,'8B8C21FE60BB3F23B45F744FF89E4F7C.jpg','2021-03-08 00:51:07.710'),(1888,1238026,3,'187A3D1F478168E66159E9CD576DA55E.jpg','2021-03-08 00:51:07.726'),(1889,1238027,2,'4C5C445E7DBF8EED6AE2E6C0A7F7CB07.jpg','2021-03-08 00:51:14.228'),(1890,1238027,3,'B9F24CD98D79D467228077C585917F90.jpg','2021-03-08 00:51:14.243'),(1891,1238027,4,'0BC94EFC61C1A4A03A442D5812A00AEE.txt','2021-03-08 00:51:14.263'),(1892,1238024,1,'D428D3F49D0366D8CBCD1DDAFBAEE209.mp4','2021-03-08 00:52:07.987'),(1893,1238028,0,'C778D221BD85294A5B03B944D4114AD6.mp4','2021-03-08 00:52:30.282'),(1894,1238029,2,'8BF93CC4225B1107551DDA5AF5783519.jpg','2021-03-08 00:56:01.150'),(1895,1238029,3,'7AB615F0F9B598AE776EFAF74A25C2DA.jpg','2021-03-08 00:56:01.166'),(1896,1238026,1,'41E56295F5A8C8FB94B9FAD2BE137C93.mp4','2021-03-08 00:56:08.434'),(1897,1238027,1,'54892970912C5C5C21FB40459513B357.mp4','2021-03-08 00:56:14.744'),(1898,1238030,2,'C5524C4734341E01783CD9FC2B80CEF5.jpg','2021-03-08 00:57:22.022'),(1899,1238030,3,'A8BAF89E803F07EC0A289C25A4D81F2B.jpg','2021-03-08 00:57:22.037'),(1900,1238030,4,'17584B558B68EB7BB79B2E580EDC6D80.txt','2021-03-08 00:57:22.056'),(1901,1238031,0,'F02BFEC9D237699B0189B266260E5195.mp4','2021-03-08 00:57:31.166'),(1902,1238032,2,'67A3B98BCF4112E15939B4173CD36EA5.jpg','2021-03-08 00:57:31.629'),(1903,1238032,3,'61FE7FD88C330BD6DCF8E6E7E9BBC8C8.jpg','2021-03-08 00:57:31.643'),(1904,1238033,2,'87361A1AD186E67E17544DF798FD944F.jpg','2021-03-08 00:58:29.526'),(1905,1238033,3,'85907CF65E7607AACE514708C8D92576.jpg','2021-03-08 00:58:29.542'),(1906,1238033,4,'D8029F39560954C21E9C07D71A9E4381.txt','2021-03-08 00:58:29.564'),(1907,1238034,2,'CAF936AFD934AD437CFB247A101422DB.jpg','2021-03-08 00:58:34.879'),(1908,1238034,3,'5885A52FF6A4918120084219F7ABEFAE.jpg','2021-03-08 00:58:34.897'),(1909,1238034,4,'A0FD0D3B72B49443141345D9845B8140.txt','2021-03-08 00:58:34.917'),(1910,1238035,2,'96400D21E9005C68DD2EE186E7A542C3.jpg','2021-03-08 00:58:59.776'),(1911,1238035,3,'4BD7D9B461260E4564A750753549350D.jpg','2021-03-08 00:58:59.791'),(1912,1238035,4,'A183503833151E7EF28B13F24DE26995.txt','2021-03-08 00:58:59.811'),(1913,1238036,2,'E5B2EC879A3BF8DD50909ABF41FD755F.jpg','2021-03-08 00:59:02.160'),(1914,1238036,3,'71DA4555BEC22322E1E114FFA9B2EB29.jpg','2021-03-08 00:59:02.174'),(1915,1238036,4,'E81C4AA701B66E263A48E8B992AF45B8.txt','2021-03-08 00:59:02.194'),(1916,1238029,1,'44FF7B71C091B27A8F971810ED377328.mp4','2021-03-08 01:01:02.242'),(1917,1238037,2,'9520C7AEBC6C3C4A0D9853D81230AC21.jpg','2021-03-08 01:01:48.070'),(1918,1238037,3,'E550351CC2F7A0DEF8A19C956A615EA8.jpg','2021-03-08 01:01:48.088'),(1919,1238030,1,'86B1D9597F1CBA9D6C4DFE5E08CAD66A.mp4','2021-03-08 01:02:22.669'),(1920,1238032,1,'01D07DAD64567210DA658E74A1DED289.mp4','2021-03-08 01:02:31.985'),(1921,1238038,0,'45B859D9F4C5CCDF781D811D1A9C2D58.mp4','2021-03-08 01:02:32.036'),(1922,1238039,2,'34CCFFF755D2646856B297F5D5009569.jpg','2021-03-08 01:03:29.541'),(1923,1238039,3,'EE19834925FFB3711CDCBDC7FAD1F287.jpg','2021-03-08 01:03:29.559'),(1924,1238039,4,'97BC82AD3322D126EAC44AC809C75D65.txt','2021-03-08 01:03:29.581'),(1925,1238033,1,'8F3D13B01CA5556749F5204C81488004.mp4','2021-03-08 01:03:30.330'),(1926,1238034,1,'BD084CA9A7C31E0D2BFEC7FE51D76316.mp4','2021-03-08 01:03:35.617'),(1927,1238035,1,'3BA62259AF3379444F6DEC1500664FA5.mp4','2021-03-08 01:04:00.954'),(1928,1238036,1,'D9E4A58977530AE3B856C5CD45622053.mp4','2021-03-08 01:04:03.239'),(1929,1238040,2,'29908620F0C497C9EDCA4580CA4CDACE.jpg','2021-03-08 01:05:17.600'),(1930,1238040,3,'96BB814C803AEB832AC8B821DE23334A.jpg','2021-03-08 01:05:17.617'),(1931,1238040,4,'8C798902BAB8484F60333D843F100B9E.txt','2021-03-08 01:05:17.640'),(1932,1238037,1,'13BFDA264DD82CE97191DCB9975EEBF4.mp4','2021-03-08 01:06:48.671'),(1933,1238041,0,'4580D9F02AE9E70FBA2C59B6399EC55A.mp4','2021-03-08 01:07:32.879'),(1934,1238039,1,'B40D1F3E0A498E79B16DEF4CA68CCB33.mp4','2021-03-08 01:08:30.051'),(1935,1238042,2,'3F85D9C501A96E74FF00DCCABA267CF1.jpg','2021-03-08 01:08:42.682'),(1936,1238042,3,'F1520BF5CE304B3AC79CC8FABB2F6E58.jpg','2021-03-08 01:08:42.700'),(1937,1238043,2,'2E9F20C184299AD5B6388FD0091CBC75.jpg','2021-03-08 01:08:45.298'),(1938,1238043,3,'384C3B03F91A5CA6074CA533A98982A1.jpg','2021-03-08 01:08:45.318'),(1939,1238043,4,'A895AE957A20FE557482632E0C5AB51A.txt','2021-03-08 01:08:45.342'),(1940,1238044,2,'1C899E1C095F82175E58D9F709E009DA.jpg','2021-03-08 01:08:57.319'),(1941,1238044,3,'2EA2C02F522A0F2F9F41012B1F66A488.jpg','2021-03-08 01:08:57.336'),(1942,1238044,4,'88EC497CDCB56F58787DA10E789F6DEE.txt','2021-03-08 01:08:57.356'),(1943,1238045,2,'F83435496B87BF40DFB2CE11DCE7111A.jpg','2021-03-08 01:09:01.037'),(1944,1238045,3,'46A46323A7AEC33F8254F199D0C2287F.jpg','2021-03-08 01:09:01.057'),(1945,1238045,4,'4BF9886601BDD6F0150F440908C90780.txt','2021-03-08 01:09:01.080'),(1946,1238046,2,'A90FF5B7F3EEBAD626EBF45BCDC8DCB3.jpg','2021-03-08 01:09:27.762'),(1947,1238046,3,'ECD7C777776857C7041D58508045EE62.jpg','2021-03-08 01:09:27.778'),(1948,1238046,4,'BEA407D1E47DB96A5C9E5A2C13AD15E9.txt','2021-03-08 01:09:27.803'),(1949,1238040,1,'BD82FEC366258FD45FBFC2455BDBD2D6.mp4','2021-03-08 01:10:18.448'),(1950,1238047,2,'14056DCEFC4E84ED11C8539F5AC8E422.jpg','2021-03-08 01:11:48.204'),(1951,1238047,3,'D1B441BFF67826EADF057307B56A33D1.jpg','2021-03-08 01:11:48.239'),(1952,1238048,2,'4D13C57C85D7E290E86974813FBF19E0.jpg','2021-03-08 01:12:08.657'),(1953,1238048,3,'5A762F426E7C6ECBBB018025ACF112FC.jpg','2021-03-08 01:12:08.746'),(1954,1238049,2,'666C5819E6028767C0C563F8DF7EDE1A.jpg','2021-03-08 01:12:24.621'),(1955,1238049,3,'E5056E68AAD1C6B1A298D3EFB9170344.jpg','2021-03-08 01:12:24.654'),(1956,1238050,0,'24F02C0EF72E4F7C4097985EDC57066A.mp4','2021-03-08 01:12:33.728'),(1957,1238042,1,'69D1F1BF28F9B60173DB6DFD0EFEB67C.mp4','2021-03-08 01:13:43.894'),(1958,1238043,1,'3D39333DFB7D7EBDA3AB17D7B5B28E34.mp4','2021-03-08 01:13:46.201'),(1959,1238044,1,'43E9A74302C431A2472F04D92AFC7F25.mp4','2021-03-08 01:13:58.587'),(1960,1238045,1,'479E76CD12266BF93D9B5231D7CF15CF.mp4','2021-03-08 01:14:01.923'),(1961,1238046,1,'55FB88E2B4F165D813081F0ECD3F81E7.mp4','2021-03-08 01:14:28.242'),(1962,1238051,2,'DCDE2B3CA8BCCC810C5416AB786EC8EA.jpg','2021-03-08 01:14:39.398'),(1963,1238051,3,'B7C0BD83D68AADE03ECD62FB514E82F6.jpg','2021-03-08 01:14:39.415'),(1964,1238052,2,'370BE7BF2C55E6AC4E30D58F32756EBD.jpg','2021-03-08 01:14:47.830'),(1965,1238052,3,'C8191A43B12538AEB6F154B531E90659.jpg','2021-03-08 01:14:47.847'),(1966,1238053,2,'5685A70563C71B0747F5E9B7464B0D4C.jpg','2021-03-08 01:14:57.265'),(1967,1238053,3,'8450ECD36B76EC9A8FBBE93E60F3867B.jpg','2021-03-08 01:14:57.280'),(1968,1238053,4,'496FFF90EE2AD1A0107E77FF04CE9F0B.txt','2021-03-08 01:14:57.302'),(1969,1238054,2,'4B5CF3D4D920AA796AD344B382DD61DB.jpg','2021-03-08 01:15:07.754'),(1970,1238054,3,'BAB34A176518CFB7FAA4043CBD21E6D4.jpg','2021-03-08 01:15:07.770'),(1971,1238054,4,'4D5626FBD1FC2BB7CDD53965D73043BF.txt','2021-03-08 01:15:07.790'),(1972,1238055,2,'0DF1E8FEF9F00BA7545B85576991C663.jpg','2021-03-08 01:15:21.051'),(1973,1238055,3,'4F168BEB64FBD23B7E4422C59A155166.jpg','2021-03-08 01:15:21.067'),(1974,1238056,2,'E5C0AE062354A17350F6E631C0DC0218.jpg','2021-03-08 01:15:23.439'),(1975,1238056,3,'99777548251C49355C70BCE62917DBE7.jpg','2021-03-08 01:15:23.454'),(1976,1238056,4,'658A0A70F739F53E9962408C534B7B14.txt','2021-03-08 01:15:23.473'),(1977,1238047,1,'585DFC19D7C7174443A9269C65084F9B.mp4','2021-03-09 20:29:39.781'),(1978,1238048,1,'A516C1AF6336AF4177E0CCEE031425EC.mp4','2021-03-09 20:29:40.185'),(1979,1238049,1,'762480E47F53F69D66D84C8D77C12758.mp4','2021-03-09 20:29:40.538'),(1980,1278001,2,'FFB011788028AD81E777008E15825926.jpg','2021-03-09 20:30:49.554'),(1981,1278001,3,'D2C4FD4F9C82F0E113765D05018DD4EA.jpg','2021-03-09 20:30:49.637'),(1982,1280001,2,'B3616FAF25A5EB1A36637056D19C1DEE.jpg','2021-03-09 21:02:37.462'),(1983,1280001,3,'ED2EA277D2E7879DE6B31DA5F5C8C8AC.jpg','2021-03-09 21:02:37.513'),(1984,1280002,2,'A73FE4F6473934C8011FA697395B1081.jpg','2021-03-09 21:04:29.874'),(1985,1280002,3,'EEC7A59E69C41C90EBB7D56B67E59D48.jpg','2021-03-09 21:04:29.896'),(1986,1280002,4,'88B5F3C4BEC2898463665B036759F5B0.txt','2021-03-09 21:04:29.918'),(1987,1280003,2,'0B05577FAFF300D98980FA8EB2792936.jpg','2021-03-09 21:04:39.157'),(1988,1280003,3,'CD664C3CCEDC62CC1D5923C3275C2BBB.jpg','2021-03-09 21:04:39.176'),(1989,1280003,4,'111BCAFEF43AC0E79A014FF91D4A2656.txt','2021-03-09 21:04:39.198'),(1990,1282001,2,'B204E0038F817757B39FE6C35923363C.jpg','2021-03-09 21:09:05.314'),(1991,1282001,3,'3182E6B793E100549CB08D463C1C7F7F.jpg','2021-03-09 21:09:05.363'),(1992,1282002,2,'B7B44CAF2112AA8499798DC770704308.jpg','2021-03-09 21:09:13.605'),(1993,1282002,3,'5651EE69B12808D4E6D02CE87BF2193E.jpg','2021-03-09 21:09:13.621'),(1994,1282003,2,'59BA3BBA8E457A274AC3D274E0820492.jpg','2021-03-09 21:10:09.627'),(1995,1282003,3,'673F98AB20EF9089D54F20FDDC715A6F.jpg','2021-03-09 21:10:09.644'),(1996,1282003,4,'0E2A652794DBFEC2EE930B406CA4DAE8.txt','2021-03-09 21:10:09.667'),(1997,1282004,0,'03BD968634801BF20DCCC259198D98EE.mp4','2021-03-09 21:10:49.646'),(1998,1282005,2,'9900A0B029FC0044C30E04724477B491.jpg','2021-03-09 21:11:32.551'),(1999,1282005,3,'263F7BAD3B135C9C850F777A74155661.jpg','2021-03-09 21:11:32.567'),(2000,1282005,4,'CAC1B656E5128CA38A236B2A2F1F5AA7.txt','2021-03-09 21:11:32.590'),(2001,1282006,2,'AD5633C31B95CE4E68E5D4E6DB7F872C.jpg','2021-03-09 21:11:40.450'),(2002,1282006,3,'737028B82B93E6BB4EACEBCEBD90D5F4.jpg','2021-03-09 21:11:40.467'),(2003,1282006,4,'A42B1807AD7D64364941F1C9037B9346.txt','2021-03-09 21:11:40.486'),(2004,1282001,1,'8990EC5AE0DF046D78E4B62252E03A04.mp4','2021-03-09 21:14:05.947'),(2005,1282002,1,'BF5B142346458302331229B0C23B87B8.mp4','2021-03-09 21:14:14.279'),(2006,1282007,2,'012C4FC70E35BD7EC024C1BEED51A0E6.jpg','2021-03-09 21:14:51.267'),(2007,1282007,3,'DEDB1B1441FE30C7CDFA016B8466B4F4.jpg','2021-03-09 21:14:51.286'),(2008,1282007,4,'4ED9CB98BCE78819124335A2924CF1F1.txt','2021-03-09 21:14:51.305'),(2009,1282003,1,'A0327C5F8207AA1A8EDD0F5A9CA4375C.mp4','2021-03-09 21:15:10.632'),(2010,1282008,0,'FF606F4229FE88D1904D8908C06847C4.mp4','2021-03-09 21:15:50.525'),(2011,1282006,1,'2EBB8713BF323C1DB12DE2E4070CA60C.mp4','2021-03-09 21:16:41.001'),(2012,1282009,2,'3F4C2F51FB0577968EBED2B485777F8E.jpg','2021-03-09 21:17:30.449'),(2013,1282009,3,'9634C306F3AE7768C5004AFB9292F978.jpg','2021-03-09 21:17:30.495'),(2014,1282009,4,'409238A11CE84CF943C1C3E0DA2266D5.txt','2021-03-09 21:17:30.518'),(2015,1282010,2,'57CDC72FC43D53C9D8B03CBA02A256B1.jpg','2021-03-09 21:19:22.581'),(2016,1282010,3,'7FCB6FC92E4895E86EDC773058504C1F.jpg','2021-03-09 21:19:22.621'),(2017,1282007,1,'08A562D0EBC70F807E01E8312EB6B5A0.mp4','2021-03-09 21:19:52.506'),(2018,1282011,2,'1CFBBD999A8EED3E11E11CA1299A2779.jpg','2021-03-09 21:20:00.834'),(2019,1282011,3,'3237BEB6478711FE3455563ED2AC57D2.jpg','2021-03-09 21:20:00.852'),(2020,1282012,0,'4955F1AB01A1D2BD3236BF95827824E9.mp4','2021-03-09 21:20:51.377'),(2021,1282013,2,'39868AA41D36407C69989C9FAC274001.jpg','2021-03-09 21:21:11.061'),(2022,1282013,3,'679C870A1B7726CE92A09FE327640686.jpg','2021-03-09 21:21:11.085'),(2023,1282014,2,'73868AB6A113F9D59E8F8596E1B3F324.jpg','2021-03-09 21:22:07.901'),(2024,1282014,3,'DBB1F5F29F7A2920CFC5DA3C5A89E6D6.jpg','2021-03-09 21:22:07.920'),(2025,1282009,1,'3BEC68E8F53B7E0ABD0B9DFD63678E8D.mp4','2021-03-09 21:22:30.894'),(2026,1282015,2,'009BFBB5398A1FF7739650F753BDB235.jpg','2021-03-09 21:23:02.920'),(2027,1282015,3,'51CFABB389291E31BBA3F09B6376EA2F.jpg','2021-03-09 21:23:02.936'),(2028,1282010,1,'8091EBB3D2B32EE371448965022F0F08.mp4','2021-03-09 21:24:23.253'),(2029,1282011,1,'7DD75AB99AF7C864981901C35E448248.mp4','2021-03-09 21:25:01.599'),(2030,1282016,0,'242D0DE249488B8DDB041D433D1D5749.mp4','2021-03-09 21:25:52.231'),(2031,1282013,1,'47A9AB4C5E2925E57AAC29251EE640EC.mp4','2021-03-09 21:26:11.943'),(2032,1282014,1,'5C9200B5663546D49102A51511DC5FE5.mp4','2021-03-09 21:27:08.292'),(2033,1282017,2,'6B84D05801EC498D25971FE4389A9379.jpg','2021-03-09 21:27:11.296'),(2034,1282017,3,'E10D3105A0B2C01F653F4BABD2455934.jpg','2021-03-09 21:27:11.311'),(2035,1282018,2,'A7EDEA2D2365985244E2C1A269A5E6E2.jpg','2021-03-09 21:27:31.432'),(2036,1282018,3,'8A0B25C3B6BA4CFB499B8A2B5F6694BB.jpg','2021-03-09 21:27:31.447'),(2037,1282015,1,'698DF2DD9FC74437CB4A7960A97FA309.mp4','2021-03-09 21:28:03.656'),(2038,1282019,2,'8327650579751C61BD16379B0AD62190.jpg','2021-03-09 21:29:41.729'),(2039,1282019,3,'D35D102ABB377A6B9154F3DEBF1F29A6.jpg','2021-03-09 21:29:41.808'),(2040,1282020,0,'51E2A3755BBFAE1160152CBD358DAE1F.mp4','2021-03-09 21:30:53.074'),(2041,1282017,1,'DB2876B61A77AF18B1D028261383CA11.mp4','2021-03-09 21:32:12.139'),(2042,1282018,1,'98F5CAD316F12AF8E4438A8C0B93C636.mp4','2021-03-09 21:32:32.455'),(2043,1282021,2,'F16BF8063A8D9CB218844596BC781A28.jpg','2021-03-09 21:33:40.433'),(2044,1282021,3,'6E20DE2BBB98B99F8A2BE3B3E727BB9A.jpg','2021-03-09 21:33:40.447'),(2045,1282021,4,'918AED5ED075C42A3F51656A06141DCD.txt','2021-03-09 21:33:40.468'),(2046,1282022,2,'46A534D3550BEDB12F3BC45B4AB65E83.jpg','2021-03-09 21:33:45.787'),(2047,1282022,3,'AFE9760F6C41AA82872D5DCBDED8E3A6.jpg','2021-03-09 21:33:45.803'),(2048,1282023,2,'525FC3EF4DDA09C6ACF5D560B08247E3.jpg','2021-03-09 21:33:55.538'),(2049,1282023,3,'2A29CF3891480A1030AB24C1B1C153BF.jpg','2021-03-09 21:33:55.552'),(2050,1282023,4,'49088161CC571885A08EC1BD7BA0A24E.txt','2021-03-09 21:33:55.572'),(2051,1282024,2,'C2188D0B5736E701EA7420A5D18E309C.jpg','2021-03-09 21:34:01.795'),(2052,1282024,3,'11D75CB00CE8059BD872A146DA0B1DDB.jpg','2021-03-09 21:34:01.810'),(2053,1282024,4,'9FABDA5A1F55B3880BCCE9CB06465B1A.txt','2021-03-09 21:34:01.831'),(2054,1282019,1,'2B54A89B285646555D4D09E08FDAE3E6.mp4','2021-03-09 21:34:42.848'),(2055,1282025,2,'34DF5B2064FBE3F120676A10FB8AAD2E.jpg','2021-03-09 21:34:56.684'),(2056,1282025,3,'509854653CE459687F24917C8CEEB079.jpg','2021-03-09 21:34:56.699'),(2057,1282026,0,'914A00BAFF24EF0EAF89D7A24E88EDDA.mp4','2021-03-09 21:35:54.109'),(2058,1282027,2,'920D635A3518EED7C530A37AF2A35226.jpg','2021-03-09 21:36:43.943'),(2059,1282027,3,'8E7527748B905D713A6313E54F81B97D.jpg','2021-03-09 21:36:43.958'),(2060,1282021,1,'A5D39076837025D4999A4CF77C955F6D.mp4','2021-03-09 21:38:41.285'),(2061,1282022,1,'619EC2BEDF581291725BF1F8015FF03D.mp4','2021-03-09 21:38:46.613'),(2062,1282023,1,'C3703C9A2A9699C9B78FAA5CB245B4CF.mp4','2021-03-09 21:38:55.912'),(2063,1282024,1,'2FD8FE071819CD895B0B5351F69A9DB5.mp4','2021-03-09 21:39:02.221'),(2064,1282025,1,'656ED2A30F3BC686FB5D6F4768F8F48C.mp4','2021-03-09 21:39:57.548'),(2065,1282028,2,'F96EFFFF080F1170D9F5D63875F7B897.jpg','2021-03-09 21:40:04.048'),(2066,1282028,3,'29DC3B9F663E2C5EF47211693AD84F5C.jpg','2021-03-09 21:40:04.063'),(2067,1282028,4,'0A66CDF27E554AED1D099985855431B7.txt','2021-03-09 21:40:04.085'),(2068,1282029,2,'4CB0AF7D346E05E00D61870B9A3A7AAA.jpg','2021-03-09 21:40:12.599'),(2069,1282029,3,'DE1859F6C5FF67E77290F987CEE64920.jpg','2021-03-09 21:40:12.614'),(2070,1282029,4,'9CB5BA7F6591CAE6A296DC2230D6FE9C.txt','2021-03-09 21:40:12.632'),(2071,1282030,2,'59CBEF02323E6E943CD36B398C708B73.jpg','2021-03-09 21:40:15.180'),(2072,1282030,3,'51BB7F2CFBA7D1100B56E7D8A2E6D995.jpg','2021-03-09 21:40:15.195'),(2073,1282030,4,'20EFC6FA0F5B62C5E8F682A09EC99482.txt','2021-03-09 21:40:15.215'),(2074,1282031,2,'13A0910604AAA6FBE3DBC4854983D7C8.jpg','2021-03-09 21:40:18.521'),(2075,1282031,3,'6129AF8E64A05EB2A4B5D99BA219DEB5.jpg','2021-03-09 21:40:18.536'),(2076,1282031,4,'B7DE72E5086996FDEAB6A57A65ECCD0F.txt','2021-03-09 21:40:18.556'),(2077,1282032,2,'A11834A1EB38885537B07B9F304B4C4A.jpg','2021-03-09 21:40:22.205'),(2078,1282032,3,'82EF1A0B7107B8724DE28ACDAD185F7C.jpg','2021-03-09 21:40:22.220'),(2079,1282032,4,'FDE76AC8D20D35745474243D82BD5032.txt','2021-03-09 21:40:22.242'),(2080,1282033,2,'D7583A5385CE3193DE8A1FCB88F8E04B.jpg','2021-03-09 21:40:25.992'),(2081,1282033,3,'4BB76CABF56144C9B8085D5FE2717B60.jpg','2021-03-09 21:40:26.009'),(2082,1282033,4,'1C90622E4BADEDEB0661D5A677BCA2A7.txt','2021-03-09 21:40:26.032'),(2083,1282034,0,'17366CCE063F035F92D87725FE33F3D6.mp4','2021-03-09 21:40:54.935'),(2084,1282035,2,'3907E12F51D18B0376E0E9E787E17CAF.jpg','2021-03-09 21:40:59.851'),(2085,1282035,3,'0FAA1D727B3537F296D956AD17F9E0BB.jpg','2021-03-09 21:40:59.866'),(2086,1282035,4,'7C42866ABBA930ACC893F4E46180BDA1.txt','2021-03-09 21:40:59.888'),(2087,1282036,2,'71EFE6F2B2E78635E0175631A23D218F.jpg','2021-03-09 21:41:18.598'),(2088,1282036,3,'B3D8CAF6CE56685C27E924D36A8B352E.jpg','2021-03-09 21:41:18.612'),(2089,1282037,2,'A1BA5289D6B121B76766C762D08B26B2.jpg','2021-03-09 21:41:20.936'),(2090,1282037,3,'0ADA85F71543D7206F85B5B23938D42D.jpg','2021-03-09 21:41:20.950'),(2091,1282037,4,'9853D21657C2ECEF992B00C1286AAEB7.txt','2021-03-09 21:41:20.968'),(2092,1282038,2,'2E49CF6A49207E9B23471F8D5BE0A2DF.jpg','2021-03-09 21:41:23.452'),(2093,1282038,3,'0971AA1932FFE93C506B13F29A401DFC.jpg','2021-03-09 21:41:23.467'),(2094,1282038,4,'23BA1E234A68E1D1866CAFDA3D61F747.txt','2021-03-09 21:41:23.487'),(2095,1282027,1,'FA222F41F2D83825797AA14D690FBDFF.mp4','2021-03-09 21:41:44.906'),(2096,1282028,1,'87356E186498594F340706F150AED5D9.mp4','2021-03-09 21:45:05.294'),(2097,1282029,1,'132A4C75C218B150571BF5EC04483A72.mp4','2021-03-09 21:45:13.594'),(2098,1282030,1,'2B53842DB9D7AF1384E28AA7842377AC.mp4','2021-03-09 21:45:15.897'),(2099,1282031,1,'DDD954414C89A389742F7173CACB38E5.mp4','2021-03-09 21:45:19.184'),(2100,1282032,1,'1E9079090CF6DF3EA2EB845AE9D89453.mp4','2021-03-09 21:45:23.470'),(2101,1282033,1,'B06DC1C448BB77D604F9188E965D68D2.mp4','2021-03-09 21:45:26.762'),(2102,1282039,0,'1F32253C90E3B858C0501558242DEC09.mp4','2021-03-09 21:45:55.920'),(2103,1282035,1,'873F90703A426580299F9ECEE5BEC2E0.mp4','2021-03-09 21:46:01.121'),(2104,1282036,1,'C2D100B6477C2B01D3B56F7B96CDDF23.mp4','2021-03-09 21:46:19.421'),(2105,1282037,1,'2F33D3490224D390A1FE73D9143B9E16.mp4','2021-03-09 21:46:21.731'),(2106,1282038,1,'32C1879D6DB6EC239DD9B8D0D71EB194.mp4','2021-03-09 21:46:24.071'),(2107,1282040,2,'22958CB051C8C1144F7FA63E83361689.jpg','2021-03-09 21:48:43.323'),(2108,1282040,3,'E28EB391BD63D6FD0EE0068072402C65.jpg','2021-03-09 21:48:43.338'),(2109,1282041,2,'652DFBAF6159F9B51E3FB5DD8028B177.jpg','2021-03-09 21:49:17.677'),(2110,1282041,3,'1A65A79AA160969934832020249FEA49.jpg','2021-03-09 21:49:17.694'),(2111,1282041,4,'F91550693693D1D1DC5EC90BDB42B0E1.txt','2021-03-09 21:49:17.714'),(2112,1282042,2,'7500D285C28062A270C1E8A9BBB98E90.jpg','2021-03-09 21:49:22.136'),(2113,1282042,3,'C5C02FA490DC2F1D72753E742EFE6670.jpg','2021-03-09 21:49:22.164'),(2114,1282042,4,'A585DB941431508926937E4B6B5E1330.txt','2021-03-09 21:49:22.192'),(2115,1282043,2,'AC5E4F694ECED526C7413AD9F70CC9F1.jpg','2021-03-09 21:49:36.200'),(2116,1282043,3,'A6A8356889C64FEB942CB08C70B110DD.jpg','2021-03-09 21:49:36.216'),(2117,1282044,0,'75D36BE747C8314B8A6A3544DA31EA3B.mp4','2021-03-09 21:50:56.785'),(2118,1282040,1,'07795D242C9CCC3298F37A2DF114559F.mp4','2021-03-09 21:53:44.608'),(2119,1282041,1,'CA98CADC8769C03B9559D23510C937D8.mp4','2021-03-09 21:54:18.973'),(2120,1282042,1,'64A36F5AF90D91410B0D2E7F3AF8BBBB.mp4','2021-03-09 21:54:23.272'),(2121,1282043,1,'6BC796F90D59BB98E33745EE476515F0.mp4','2021-03-09 21:54:36.582'),(2122,1282045,2,'73180FF18D96B68C0658F984D8E194C3.jpg','2021-03-09 21:55:00.096'),(2123,1282045,3,'61CB6827803CDCFB767D6B7C813429B2.jpg','2021-03-09 21:55:00.112'),(2124,1282045,4,'BBFACCA3AE5A10C9803644E5D7A0DF6D.txt','2021-03-09 21:55:00.133'),(2125,1282046,0,'D63FD3589ED20E41D4999534612B2F80.mp4','2021-03-09 21:55:57.629'),(2126,1282047,2,'396789382E765518551E8865E8FA2ECA.jpg','2021-03-09 21:56:46.849'),(2127,1282047,3,'443574BD78A2A2F1A88D271D69808010.jpg','2021-03-09 21:56:46.864'),(2128,1282048,2,'D172BAE48AB3BF0251AF9232DA1427B5.jpg','2021-03-09 21:56:49.700'),(2129,1282048,3,'D154E21FCA4D1BA7B3834E46C26B56A9.jpg','2021-03-09 21:56:49.715'),(2130,1282049,2,'572E275260E6CC1B87F5DF5A95D83AAF.jpg','2021-03-09 21:56:52.027'),(2131,1282049,3,'19F42F215A239940E2DB35863EF1172A.jpg','2021-03-09 21:56:52.168'),(2132,1282049,4,'1BB20C2C08D51611A84DA743D2D332DB.txt','2021-03-09 21:56:52.300'),(2133,1282050,2,'124533AC38F78CC4678A43BC288FD292.jpg','2021-03-09 21:56:54.996'),(2134,1282050,3,'61312DAEDAB04C4DF092FF7D48E2E659.jpg','2021-03-09 21:56:55.044'),(2135,1282050,4,'C4554E40A1555F28F303D16F2C05C1C5.txt','2021-03-09 21:56:55.108'),(2136,1282051,2,'38C62403199AC0B8BCBEEC24621DD3E6.jpg','2021-03-09 21:56:57.753'),(2137,1282051,3,'B1931F1683A10ECE5D57C12A681C58D5.jpg','2021-03-09 21:56:57.804'),(2138,1282051,4,'E00CD2B457FCDA09AF1483C49D713448.txt','2021-03-09 21:56:57.919'),(2139,1282052,2,'ABEBE1770C8B2443632FFE29D9493DBA.jpg','2021-03-09 21:57:00.514'),(2140,1282052,3,'7E807A8203EB1F1438164895415FCD3C.jpg','2021-03-09 21:57:00.597'),(2141,1282052,4,'0137059D664C5F680E5A20530FBCE5D3.txt','2021-03-09 21:57:00.703'),(2142,1282053,2,'DD4696432F57A89B035C0FC24DB5A66D.jpg','2021-03-09 21:58:40.909'),(2143,1282053,3,'5EC44CB3C4A8DD1D49D6495D90AA5031.jpg','2021-03-09 21:58:40.926'),(2144,1282045,1,'3999CB9E5C91351DE223C6CC7D92F8D9.mp4','2021-03-09 22:00:01.066'),(2145,1282054,0,'0BF9824BBB9CEDF7A65BB6CC9BF6C039.mp4','2021-03-09 22:00:58.498'),(2146,1282047,1,'AE050893B75DE3A9E7BAAF95157B5C65.mp4','2021-03-09 22:01:47.457'),(2147,1282048,1,'64132E22D74402EA1D081F3EA11738E2.mp4','2021-03-09 22:01:50.786'),(2148,1282049,1,'13A995F14DA9DB6A725D1AB8C2411BFC.mp4','2021-03-09 22:01:53.092'),(2149,1282050,1,'571EF4149E62427CDB4BE4F35C93EBFE.mp4','2021-03-09 22:01:56.398'),(2150,1282051,1,'4A34CEB0F5C607F8AFA662C66D4BDD72.mp4','2021-03-09 22:01:58.732'),(2151,1282052,1,'33FB160FFF941456F2985DED3EF5FEFE.mp4','2021-03-09 22:02:02.056'),(2152,1282053,1,'8BF81EC0D59C0A177D772278EC4578D5.mp4','2021-03-09 22:03:41.462'),(2153,1282055,0,'FB9FE108EC8ACAA3DD6F3915678691BD.mp4','2021-03-09 22:05:59.367'),(2154,1282056,2,'6D9F8BC89AAB8F0280F63E76ABBDA48B.jpg','2021-03-09 22:10:58.922'),(2155,1282056,3,'05FFE0CC62678627ECAF0D8CAA54576A.jpg','2021-03-09 22:10:58.937'),(2156,1282056,4,'E07000AB15CED3C0579F6BA167E7D96C.txt','2021-03-09 22:10:58.954'),(2157,1282057,0,'02413032488BD13A5EA1B8481591DAC7.mp4','2021-03-09 22:11:00.223'),(2158,1282058,2,'E4FC4E19A450425A648A96880379F06B.jpg','2021-03-09 22:12:37.111'),(2159,1282058,3,'A8FBFB057E35B0A2EBA8FAA7FCA85A0D.jpg','2021-03-09 22:12:37.128'),(2160,1282058,4,'F4A3F4CCEA92E43F404CC5A575F8AFFB.txt','2021-03-09 22:12:37.147'),(2161,1282059,2,'49867135C299E8ED0AB9B130FC4D214C.jpg','2021-03-09 22:12:42.914'),(2162,1282059,3,'DC535BFC00D7DADB6C040C5420DF5FFC.jpg','2021-03-09 22:12:42.929'),(2163,1282059,4,'C24222A22034CA91342EC521C9A6166E.txt','2021-03-09 22:12:42.950'),(2164,1282060,2,'E49EBC501296B03FF7CFF7FACE3C25D6.jpg','2021-03-09 22:12:52.459'),(2165,1282060,3,'2DB43FB4FED2934430FA9B75D53D1D50.jpg','2021-03-09 22:12:52.474'),(2166,1282061,2,'3CBC318999507CC832E5C51176532C4B.jpg','2021-03-09 22:13:27.314'),(2167,1282061,3,'716598942D1968CE9D30BE72D51D94F5.jpg','2021-03-09 22:13:27.329'),(2168,1282062,2,'95227C8FDFBEA87ED00FDD5C606C7E6D.jpg','2021-03-09 22:13:55.796'),(2169,1282062,3,'A46BEAF52100F6FB532048050B320FEA.jpg','2021-03-09 22:13:55.813'),(2170,1282062,4,'830C909AF9B78B13BE171F68A087E55E.txt','2021-03-09 22:13:55.831'),(2171,1282063,2,'0D8544FC0061B1D4C6E72BA81DFF0A28.jpg','2021-03-09 22:14:33.642'),(2172,1282063,3,'361912423473799417B55BBE97B6EFF8.jpg','2021-03-09 22:14:33.659'),(2173,1282064,2,'C7FD011C767934EA96EFE59054632F68.jpg','2021-03-09 22:14:38.074'),(2174,1282064,3,'1DCFB3FBB256D650417C5664B8BBF289.jpg','2021-03-09 22:14:38.091'),(2175,1282065,2,'22FFCDBDF2ADD268EAE7FBC7CCA77415.jpg','2021-03-09 22:14:45.914'),(2176,1282065,3,'130B8628A74F6F470BC8250FC3515E0D.jpg','2021-03-09 22:14:45.928'),(2177,1282066,2,'F328906CF10AEF2D3D64C6F5576C8605.jpg','2021-03-09 22:14:48.258'),(2178,1282066,3,'0AA35DBBFF8B90274DC8DFA5F42439E1.jpg','2021-03-09 22:14:48.274'),(2179,1282066,4,'D5B4B64080224C54BB7C000FFB5D036E.txt','2021-03-09 22:14:48.291'),(2180,1282067,2,'69E920E19CCC728D926DD704BBAEC431.jpg','2021-03-09 22:14:54.039'),(2181,1282067,3,'BE084F3053B4334576661FED1D3560C6.jpg','2021-03-09 22:14:54.055'),(2182,1282068,2,'8DF0B412DCB7E7925874A18A10E6B264.jpg','2021-03-09 22:15:15.976'),(2183,1282068,3,'84D01542892871CA76A9170F8547ED51.jpg','2021-03-09 22:15:15.991'),(2184,1282068,4,'68E6C5E7F82467CA018C25CA5144C754.txt','2021-03-09 22:15:16.011'),(2185,1282069,2,'2FD2E9833F69BC2B061DAE0C17D16C74.jpg','2021-03-09 22:15:17.970'),(2186,1282069,3,'EED4134863EE093AD9BB928B1F39C80D.jpg','2021-03-09 22:15:17.985'),(2187,1282056,1,'9FD29E36B6049BF3CCFDD72FC5CB0ACA.mp4','2021-03-09 22:16:00.156'),(2188,1282070,0,'86215872253CDFAD421DB43285F3AD04.mp4','2021-03-09 22:16:01.381'),(2189,1282058,1,'8A2A2F44AC180C27332E47C26D20DBDB.mp4','2021-03-09 22:17:37.510'),(2190,1282059,1,'E52452356053FBE8CC0D8C8F651BFED8.mp4','2021-03-09 22:17:43.863'),(2191,1282060,1,'2D378903BC4F14EC4CCA675838975BD5.mp4','2021-03-09 22:17:53.152'),(2192,1282071,2,'8A000BB61F00CBEFFC84E4F01B839917.jpg','2021-03-09 22:18:17.369'),(2193,1282071,3,'BC3C6E29E92135CCCF3558B6BC5CD7DA.jpg','2021-03-09 22:18:17.384'),(2194,1282061,1,'632DA0BA9A50E8F73B341EE821EA94E7.mp4','2021-03-09 22:18:28.597'),(2195,1282062,1,'B60581996F20473F6F07B9243AB0F503.mp4','2021-03-09 22:18:56.923'),(2196,1282063,1,'177997DD55C58D1183B719AE9DEDFE95.mp4','2021-03-09 22:19:34.252'),(2197,1282064,1,'9CB42FA9931F652B61A54AB90F72923E.mp4','2021-03-09 22:19:38.566'),(2198,1282065,1,'7A19B8E80946868ACE9C3FD80EFB8A34.mp4','2021-03-09 22:19:46.951'),(2199,1282066,1,'E107959D3C525EA2DE081C14C154E525.mp4','2021-03-09 22:19:49.245'),(2200,1282067,1,'DB6DE7BEA950D25856C01BC147F196EC.mp4','2021-03-09 22:19:54.562'),(2201,1282068,1,'AD9CC318DBF3B0E9D6D52817426DEDBF.mp4','2021-03-09 22:20:16.925'),(2202,1282069,1,'6C3DB1080DBB5A87FB2BB2347E964FB4.mp4','2021-03-09 22:20:19.254'),(2203,1282072,0,'61DA177B9705218A145CB159BD108A4E.mp4','2021-03-09 22:21:02.247'),(2204,1282073,2,'A985008D860048869FCB623ECFCBA796.jpg','2021-03-09 22:21:32.866'),(2205,1282073,3,'A6AC48A14C2B94E16117AC4264451BC6.jpg','2021-03-09 22:21:32.881'),(2206,1282074,2,'F13C692E0DCBDF4CB9BB95499284BD39.jpg','2021-03-09 22:21:34.922'),(2207,1282074,3,'14B49D385470240BCA21066D960159CA.jpg','2021-03-09 22:21:34.937'),(2208,1282075,2,'31130A00765A93D24DD0DFEADC6079D7.jpg','2021-03-09 22:21:37.471'),(2209,1282075,3,'76D2C6EDB45F632ADBB0913057181321.jpg','2021-03-09 22:21:37.486'),(2210,1282075,4,'7D1C57A79BB61E859357013C02B2FA8E.txt','2021-03-09 22:21:37.505'),(2211,1282076,2,'FEE39C49F3B83EA3DAC6A30319C68E13.jpg','2021-03-09 22:23:00.834'),(2212,1282076,3,'047059762068F5919AFF8D79AD4EE81E.jpg','2021-03-09 22:23:00.892'),(2213,1282077,2,'3E594F8D70D645A48DDB488217896C2B.jpg','2021-03-09 22:23:03.900'),(2214,1282077,3,'63EF115F1A0A236D7DF190CB84CF9BC6.jpg','2021-03-09 22:23:03.919'),(2215,1282077,4,'FCA591A7C94C1496EFDB0551DF4032D0.txt','2021-03-09 22:23:03.942'),(2216,1282078,2,'E653E4E8CC9A429ECE132825841F71D6.jpg','2021-03-09 22:23:14.369'),(2217,1282078,3,'3E9F29215B9B0A7D22B4555F516F6889.jpg','2021-03-09 22:23:14.386'),(2218,1282079,2,'B49D1204568FE445D7E907A1515AEE5F.jpg','2021-03-09 22:23:18.513'),(2219,1282079,3,'9CEC67BC1F43D72CE55AADDD552B3234.jpg','2021-03-09 22:23:18.530'),(2220,1282071,1,'3319785245C254008B3F55AE0D79FC85.mp4','2021-03-09 22:23:18.669'),(2221,1282080,2,'88457DEEFBBCD3CD694BEF8C5859F06E.jpg','2021-03-09 22:24:14.762'),(2222,1282080,3,'52ECA97189D502A1D8F912E475C8A04B.jpg','2021-03-09 22:24:14.781'),(2223,1282081,0,'66143B425E6F2E9E64C40131535C77AC.mp4','2021-03-09 22:26:03.194'),(2224,1282082,2,'AA6E74F712EF64A642C1751F908E9ABD.jpg','2021-03-09 22:26:13.329'),(2225,1282082,3,'A596B271C92D1A9E51B2347654DAD318.jpg','2021-03-09 22:26:13.373'),(2226,1282073,1,'48DAB2FACE366CA4174D8D9E24A7446D.mp4','2021-03-09 22:26:34.100'),(2227,1282074,1,'D32CC169F7FE9D62E849F34DA92DD124.mp4','2021-03-09 22:26:35.418'),(2228,1282075,1,'DB79506E7A3BD7788D926363EF777266.mp4','2021-03-09 22:26:38.740'),(2229,1282083,2,'896B4019A85931C6938484ED9102897B.jpg','2021-03-09 22:26:44.374'),(2230,1282083,3,'F4168B2A8DE94AFFD67290E795F1AF4C.jpg','2021-03-09 22:26:44.392'),(2231,1282083,4,'AE142C25BC64E93D0AD7DBF02BA99505.txt','2021-03-09 22:26:44.412'),(2232,1282084,2,'DA467BE383258D03210E56AC2EF8C1AC.jpg','2021-03-09 22:26:48.899'),(2233,1282084,3,'A126E0E82FDAA15B0B731541BDE6E396.jpg','2021-03-09 22:26:48.916'),(2234,1282084,4,'80D8F2AD38A919B149E41722B360026E.txt','2021-03-09 22:26:48.935'),(2235,1282085,2,'9EA088F56E334A3D13CB66F829D9BA30.jpg','2021-03-09 22:26:56.079'),(2236,1282085,3,'3AD24F1EB1DE4729C86D1D623ADB6D87.jpg','2021-03-09 22:26:56.097'),(2237,1282085,4,'45C9356562A9DFD2F10BC737BE4DE8AA.txt','2021-03-09 22:26:56.116'),(2238,1282076,1,'E205DEAD6E6147870B4A9E4F7B69D144.mp4','2021-03-09 22:28:02.109'),(2239,1282077,1,'0972D4F277ECF7C1FE018EB73F40266C.mp4','2021-03-09 22:28:04.408'),(2240,1282078,1,'BE93D1B2FCEF3BC0C9CF7E0ED6088585.mp4','2021-03-09 22:28:14.727'),(2241,1282079,1,'0A6946A985B147087D9061CB5BB1E964.mp4','2021-03-09 22:28:19.052'),(2242,1282086,2,'30BD45989F5FA15AEC2ABEEFC6496EDF.jpg','2021-03-09 22:28:30.543'),(2243,1282086,3,'9AA35EB37F3E56ECCC812FE4881649BD.jpg','2021-03-09 22:28:30.559'),(2244,1282080,1,'CF2FB7ABCC990F258AA4359F3E9225DB.mp4','2021-03-09 22:29:15.419'),(2245,1282087,2,'DFB7835A0A279650A49225BBF4518E8C.jpg','2021-03-09 22:29:36.943'),(2246,1282087,3,'DB1AEF2F12B29689F4F104753A980E74.jpg','2021-03-09 22:29:36.960'),(2247,1282088,2,'7155D8B94D0C0BC4B36323832947431B.jpg','2021-03-09 22:30:54.824'),(2248,1282088,3,'2B06943561211266E8B2E911E98B8B16.jpg','2021-03-09 22:30:54.855'),(2249,1282090,2,'3F545059BDEA86FBB1A3930D8BE48D2C.jpg','2021-03-09 22:31:03.768'),(2250,1282090,3,'D424E77FC6F3331C29588AEAF30E4C69.jpg','2021-03-09 22:31:03.782'),(2251,1282090,4,'D6A18D7D34EC983CBD3CABBC507EC434.txt','2021-03-09 22:31:03.801'),(2252,1282089,0,'0301A804A02E02612F9432E9C47C5FC6.mp4','2021-03-09 22:31:04.088'),(2253,1282091,2,'6AB80CD22CF7EBB56979D742538CDE69.jpg','2021-03-09 22:31:09.216'),(2254,1282091,3,'3AE7B3BEBEA070BE55046270D8530782.jpg','2021-03-09 22:31:09.231'),(2255,1282082,1,'A0B405154908B9644E39155F6B9A0A11.mp4','2021-03-09 22:31:13.803'),(2256,1282092,2,'B14ED737AEC3C10E77ABA300DBA55339.jpg','2021-03-09 22:31:15.458'),(2257,1282092,3,'F0504168BC1F5F74291945DE30E33E5E.jpg','2021-03-09 22:31:15.471'),(2258,1282092,4,'5BF85EF98E3D143D1913AB76B54F4A34.txt','2021-03-09 22:31:15.490'),(2259,1282093,2,'212DD0BE232D883938F219A87EED8AA8.jpg','2021-03-09 22:31:19.292'),(2260,1282093,3,'C05BA4659CC34E1973251EB832C25E90.jpg','2021-03-09 22:31:19.307'),(2261,1282093,4,'19E50D1BDDCBAC2C759519C9848F8FE9.txt','2021-03-09 22:31:19.325'),(2262,1282083,1,'4016A5837C566B31C9B9BB7FFD84A371.mp4','2021-03-09 22:31:45.128'),(2263,1282085,1,'6BB989C74F32E0F8F2E6F3181A1D0291.mp4','2021-03-09 22:31:56.466'),(2264,1282094,2,'DEE9667E67ECDF1FBD09E970B59C15D4.jpg','2021-03-09 22:31:59.863'),(2265,1282094,3,'CBD1C48D2711A10B6304B4940F77A892.jpg','2021-03-09 22:31:59.893'),(2266,1282095,2,'214FD63E4CF4CCECED2A308B07438D56.jpg','2021-03-09 22:32:01.839'),(2267,1282095,3,'F4E9572226DFD05EA2239BA38CC48599.jpg','2021-03-09 22:32:01.852'),(2268,1282096,2,'513F3578AF9F543E2FF93DB57DCE200F.jpg','2021-03-09 22:32:05.941'),(2269,1282096,3,'320F7C2E2E851199C84BF6AA19163BEF.jpg','2021-03-09 22:32:05.956'),(2270,1282097,2,'3D3DEEBACED2FE492CCA94217FB16337.jpg','2021-03-09 22:32:12.627'),(2271,1282097,3,'853F832751383E8BD367363A736985FD.jpg','2021-03-09 22:32:12.643'),(2272,1282097,4,'E171195B87F474CDA7C9AB73F8976BA3.txt','2021-03-09 22:32:12.661'),(2273,1282098,2,'6202C0369D650C788C8A508EEB00FFAD.jpg','2021-03-09 22:32:16.270'),(2274,1282098,3,'8ECA3288EB95DBEFAA865BEF6F012BCB.jpg','2021-03-09 22:32:16.286'),(2275,1282086,1,'FFFA3871A8BAFFE84030ECAE6111F3E8.mp4','2021-03-09 22:33:31.806'),(2276,1282099,2,'ADC494504C13B3DA1DDCD5DB122C0A4F.jpg','2021-03-09 22:33:35.791'),(2277,1282099,3,'33A48EC1AA98B56CC47D4B7408932E22.jpg','2021-03-09 22:33:35.806'),(2278,1282100,2,'9708A4510DDCFA9830D3322D9161F5D3.jpg','2021-03-09 22:34:35.581'),(2279,1282100,3,'F599B9374D76D6726D521C5D443453F3.jpg','2021-03-09 22:34:35.597'),(2280,1282087,1,'A37E29672C55170C5BB3C30B2CD3B75B.mp4','2021-03-09 22:34:38.150'),(2281,1282101,2,'281B7FBFDC71969F369A5281BAF30E5C.jpg','2021-03-09 22:34:39.708'),(2282,1282101,3,'B5A1AFB1201FA0469D986A2AC913DB44.jpg','2021-03-09 22:34:39.723'),(2283,1282101,4,'9C4AFFFC8EF176DBA2BBDEC5340948AB.txt','2021-03-09 22:34:39.742'),(2284,1282102,2,'08FE7D83EB2A8329B5BA22EA542531CF.jpg','2021-03-09 22:34:55.362'),(2285,1282102,3,'A6FC459A434935A303C99BC418F1D4DD.jpg','2021-03-09 22:34:55.377'),(2286,1282102,4,'C7CFDB1795A045B93375A0E2ABD3D53A.txt','2021-03-09 22:34:55.395'),(2287,1282103,2,'55F59B76719763C1C65DE8899169F0E9.jpg','2021-03-09 22:34:57.334'),(2288,1282103,3,'FE04C16E811BADE648858F23EA6A7F94.jpg','2021-03-09 22:34:57.349'),(2289,1282104,2,'A2D5547BD2B21D410ED278DCCCFE64CC.jpg','2021-03-09 22:35:10.413'),(2290,1282104,3,'154DA324CA3712BDFCC7968CE49E4B62.jpg','2021-03-09 22:35:10.430'),(2291,1282104,4,'A1C4352377AFA2BE2A3DD361AAE2B169.txt','2021-03-09 22:35:10.449'),(2292,1282088,1,'F4A8E6D9252E3F1E56A6A852400833CF.mp4','2021-03-09 22:35:55.490'),(2293,1282105,2,'487E1EA84899889DC17184F1228C7FA7.jpg','2021-03-09 22:36:03.709'),(2294,1282105,3,'5530D2F9F8384D40782722558044C312.jpg','2021-03-09 22:36:03.724'),(2295,1282090,1,'B9B2E28C3AD243028ECA8AAD1E4FFB3D.mp4','2021-03-09 22:36:04.803'),(2296,1282106,0,'E3C38A9965357436DB7B79B0A016DFD6.mp4','2021-03-09 22:36:04.919'),(2297,1282091,1,'5A45EF77B73B030D02AC2E0DA9057801.mp4','2021-03-09 22:36:10.104'),(2298,1282107,2,'59FC6A565FEAE88D67C59EA81F43AD6F.jpg','2021-03-09 22:36:13.722'),(2299,1282107,3,'5761D98DD3EC72B1DC7B9B5BA609DEAB.jpg','2021-03-09 22:36:13.737'),(2300,1282092,1,'1E3ADC188D12BE4B30B5A1B5E5F7A104.mp4','2021-03-09 22:36:16.429'),(2301,1282093,1,'C22B0F158EE14C25246FFCA3A0063D8C.mp4','2021-03-09 22:36:19.724'),(2302,1282108,2,'27CB443D647BE37C39FEF0AB38640649.jpg','2021-03-09 22:36:26.200'),(2303,1282108,3,'7250ED637B0955CC63111CF4F387A363.jpg','2021-03-09 22:36:26.217'),(2304,1282094,1,'59F81E4300B85D3946C10EA7E42DE82A.mp4','2021-03-09 23:10:28.562'),(2305,1282095,1,'A1763C33D3A1054CD03EB6DE4B67B1EF.mp4','2021-03-09 23:10:28.958'),(2306,1282096,1,'8168ED61B5D3D636A179D2BE147B2D1E.mp4','2021-03-09 23:10:29.304'),(2307,1282097,1,'761D901D45BBDD793F2C7973188ABE0B.mp4','2021-03-09 23:10:29.657'),(2308,1282098,1,'4B76F77E4D596B5A8FE215873896E480.mp4','2021-03-09 23:10:30.025'),(2309,1282099,1,'8127849839332B09BDD2E2DC29251AFF.mp4','2021-03-09 23:10:30.378'),(2310,1282100,1,'29EDEDF4E851074DD903894A03CFF408.mp4','2021-03-09 23:10:30.695'),(2311,1282101,1,'80FC1336ED680CD32C525663FFB5019B.mp4','2021-03-09 23:10:31.037'),(2312,1282102,1,'FE95F49FEBC921CC9CACD1F38365DC83.mp4','2021-03-09 23:10:31.363'),(2313,1282103,1,'60BAF240188C4782681947967C321541.mp4','2021-03-09 23:10:31.675'),(2314,1282104,1,'3A4FE17F69CA64EF8F3469491E5FE698.mp4','2021-03-09 23:10:32.018'),(2315,1282105,1,'DCD69C7C3E6BDE89117CBBA3E6D949F0.mp4','2021-03-09 23:10:32.342'),(2316,1282107,1,'36DA79C801EAE9ED0E727CF2D7AF0CE8.mp4','2021-03-09 23:10:32.659'),(2317,1282108,1,'E9BFED63A7A9E6C1514A5C48971DE8D0.mp4','2021-03-09 23:10:32.972'),(2318,1286001,2,'A1536288E85BCFAD06EB92DF7F38B60A.jpg','2021-03-09 23:10:40.874'),(2319,1286001,3,'97372CEBCB70078AB32EA9E9F6512A52.jpg','2021-03-09 23:10:40.890'),(2320,1286001,4,'34EAC6809D5800C5332FC323FEFD32C1.txt','2021-03-09 23:10:40.909'),(2321,1288001,2,'859A52E09DAA5B31D20F3A3900470932.jpg','2021-03-09 23:18:55.239'),(2322,1288001,3,'E6C33805BB369891C8BB74C830F57CE6.jpg','2021-03-09 23:18:55.291'),(2323,1288001,4,'91A0F6B0A78CA26379DEF59EC887606F.txt','2021-03-09 23:18:55.314'),(2324,1288002,2,'5A2F6CE4090FE7BA07026C5ACB915675.jpg','2021-03-09 23:19:01.388'),(2325,1288002,3,'B383C34F19B5CD71C03EC7223042D5D4.jpg','2021-03-09 23:19:01.404'),(2326,1288003,2,'81AE541C0706AF7495BCB66F7083A5E7.jpg','2021-03-09 23:19:05.518'),(2327,1288003,3,'260421B423245EE7FFF9F52FFC9DE837.jpg','2021-03-09 23:19:05.535'),(2328,1288003,4,'E71554159673BF0657513F32F360B2E5.txt','2021-03-09 23:19:05.556'),(2329,1288004,2,'A10283E95B0CD1BAED1E5F4FA053EDC3.jpg','2021-03-09 23:19:12.407'),(2330,1288004,3,'E0486B080C37B6194A92FF91947F0FC3.jpg','2021-03-09 23:19:12.424'),(2331,1290001,2,'B2B43EEA0290DBD3EF8CD202FB62E697.jpg','2021-03-09 23:21:54.593'),(2332,1290001,3,'C021ED519D6E5DA4DE1302276B11391C.jpg','2021-03-09 23:21:54.641'),(2333,1290001,4,'250F5475B34A9ED788B77A4F21E426EF.txt','2021-03-09 23:21:54.664'),(2334,1290002,2,'31E850B4D9724D2D523563E2A7D8DA36.jpg','2021-03-09 23:24:37.923'),(2335,1290002,3,'95DECD7C6A187318AD015101FD899A5F.jpg','2021-03-09 23:24:37.942'),(2336,1290003,0,'588D450F8192E66F4A9C6DC89769347E.mp4','2021-03-09 23:25:13.948'),(2337,1290001,1,'98760B028EE9026B7B52561593FF752D.mp4','2021-03-09 23:26:55.948'),(2338,1,2,'6838A8221E1CB90D8AA4232AD2F82ACF.jpg','2021-03-10 19:16:30.288'),(2339,1,3,'A89B8678DCB623F9ADA6397F47DE70CE.jpg','2021-03-10 19:16:30.393'),(2340,1,4,'6DE99216C9DFE9456FB6BD2CFE9C522E.txt','2021-03-10 19:16:30.412'),(2341,2001,2,'28A652408722DB5D00A86F6B39C27D97.jpg','2021-03-10 19:18:39.991'),(2342,2001,3,'17EC2DA79E275D35133BE6F83E88E008.jpg','2021-03-10 19:18:40.043'),(2343,2001,4,'EEBEA25855105A31E51CCE1E9B4E3812.txt','2021-03-10 19:18:40.063'),(2344,2002,2,'46C91248CF3CDD8055F216F77D5ADCE3.jpg','2021-03-10 19:19:14.395'),(2345,2002,3,'A73A18A24DA56D602B00A4C384B5102C.jpg','2021-03-10 19:19:14.412'),(2346,2002,4,'57578383F7BD82B0908EEB87F52F8ED7.txt','2021-03-10 19:19:14.434'),(2347,2003,2,'A8199BABEB09141410E8FC2B03264C03.jpg','2021-03-10 19:20:07.948'),(2348,2003,3,'141C0056854AA99EB97F41A0817FFF00.jpg','2021-03-10 19:20:07.964'),(2349,2003,4,'7E901664A6DB5358D621BC97C0C6E5C3.txt','2021-03-10 19:20:07.988'),(2350,2004,2,'FE33ECBDE1BA45CDB8A190E8AE5F705E.jpg','2021-03-10 19:20:15.175'),(2351,2004,3,'BD0375F43847A16BF6C827B83ACA9334.jpg','2021-03-10 19:20:15.193'),(2352,2005,2,'D1426CD62654B6219B1240BE8D269758.jpg','2021-03-10 19:20:18.768'),(2353,2005,3,'3AE98633890CA11BEE5A14827241ED5E.jpg','2021-03-10 19:20:18.784'),(2354,2006,2,'90479895BB4752ED37FE1735C1DABAD4.jpg','2021-03-10 19:22:53.482'),(2355,2006,3,'923EF5DE89414BCBF58CCE60DE0C6FF6.jpg','2021-03-10 19:22:53.499'),(2356,1,0,'7E519E68C869DFF8CB455FE3A2854E3B.mp4','2021-03-10 19:22:57.856'),(2357,2002,1,'71AB6D908F49BA8AE1C54FA1B5282148.mp4','2021-03-10 19:24:14.989'),(2358,2007,2,'314894A97E7BC06DA18FDAB2FD5FC561.jpg','2021-03-10 19:24:36.185'),(2359,2007,3,'91B5EB36DCB21A9FC3A63C8D36B8FCC2.jpg','2021-03-10 19:24:36.201'),(2360,2007,4,'F19B54D68BE1722E6454885BDC47C122.txt','2021-03-10 19:24:36.221'),(2361,2003,1,'0BA980717F16043DAA4F890B5CCDBDC9.mp4','2021-03-10 19:25:08.326'),(2362,2004,1,'E3DD290A8C5B9FC9F58DD71DF76E4FF3.mp4','2021-03-10 19:25:15.630'),(2363,2005,1,'B363F9B2A763ED4E13DB3F676FF5E5C5.mp4','2021-03-10 19:25:19.936'),(2364,2008,2,'86BC317C950865DA95AD1CF0EA4019F1.jpg','2021-03-10 19:26:46.082'),(2365,2008,3,'1F89E2864DE80EF951C9A42017C26B2D.jpg','2021-03-10 19:26:46.107'),(2366,2009,2,'2E1AC87D5113F2C720C8555F08A579CB.jpg','2021-03-10 19:26:50.070'),(2367,2009,3,'2E70A1D0BB47366A50163E1081CBDE1A.jpg','2021-03-10 19:26:50.100'),(2368,2010,2,'965AEE3FA882B23C5EE5907AACD1AFED.jpg','2021-03-10 19:27:10.111'),(2369,2010,3,'C963096EAF97A880E6E4C15D43FB5849.jpg','2021-03-10 19:27:10.136'),(2370,2006,1,'FDAD52B070DA78C3866476548EEFAE9C.mp4','2021-03-10 19:27:54.322'),(2371,2,0,'B7AF6123FA307FDF7EC6022EE432FA33.mp4','2021-03-10 19:27:57.860'),(2372,2007,1,'DCA576EB3ED2C03F104B11A03AB9C18D.mp4','2021-03-10 19:29:36.675'),(2373,2008,1,'5B93E5878D477BD61EAB3C58C1BF3C66.mp4','2021-03-10 19:31:47.075'),(2374,2009,1,'11536EA84324AB2334DADA9175AA7EB3.mp4','2021-03-10 19:31:51.363'),(2375,2010,1,'7404DA87319BFEA81A6F041791302C0A.mp4','2021-03-10 19:32:10.727'),(2376,2011,2,'6AEFC568BE0F26EC825DB6E615586CE2.jpg','2021-03-10 19:32:32.101'),(2377,2011,3,'599500FE0D84105C123F99DA85A568ED.jpg','2021-03-10 19:32:32.117'),(2378,2012,2,'36ADF06B9A720D59D6063E7E5FCA996B.jpg','2021-03-10 19:32:34.073'),(2379,2012,3,'0BBA33DDBC31F10F744C9F96CDE9FB96.jpg','2021-03-10 19:32:34.087'),(2380,3,0,'A540659E47E608873ADDEEEE4E3AA65F.mp4','2021-03-10 19:32:57.990'),(2381,2013,2,'95B7786C0CF31153299E6E0575E7807D.jpg','2021-03-10 19:33:37.964'),(2382,2013,3,'AA2FF7BBC5925F2EAF08B551EBE8045F.jpg','2021-03-10 19:33:37.978'),(2383,2013,4,'0182CD02952A23BBB1F7B35CA8A6F118.txt','2021-03-10 19:33:38.008'),(2384,2014,2,'B6F7716C62C74EA03B468B974A1750BF.jpg','2021-03-10 19:35:59.069'),(2385,2014,3,'2D77015B4B0246DE6C1FE53E95BE66ED.jpg','2021-03-10 19:35:59.084'),(2386,2015,2,'D22CE96068475AAE64012D62B15C77C1.jpg','2021-03-10 19:36:08.575'),(2387,2015,3,'DFAD135DCAC3529F5C9C774947B3980E.jpg','2021-03-10 19:36:08.590'),(2388,2016,2,'1DD92FE9825D97960D6025B0B5F4FD76.jpg','2021-03-10 19:36:19.596'),(2389,2016,3,'31635136D29E7286BEC871E2E131E283.jpg','2021-03-10 19:36:19.613'),(2390,2017,2,'E33E741F1517ECF860449E0EBB67B71F.jpg','2021-03-10 19:37:29.596'),(2391,2017,3,'9EB4D1BC9CD01AFF8CEA413DF804D6CC.jpg','2021-03-10 19:37:29.613'),(2392,2011,1,'93C5978521738AAE9DD943E5AFAEA54B.mp4','2021-03-10 19:37:33.211'),(2393,2012,1,'AFA42DBB9000767D1BC9FA9F3467C4DB.mp4','2021-03-10 19:37:34.519'),(2394,2018,2,'5E6C4C255D6FF925E39D012C2F018605.jpg','2021-03-10 19:37:36.082'),(2395,2018,3,'1F46DF889108E8AE96721AF0D99D000B.jpg','2021-03-10 19:37:36.100'),(2396,2018,4,'37865BC7F8FFFF8357E322B761777BAB.txt','2021-03-10 19:37:36.122'),(2397,2019,2,'B2B6CBF950C90298367E04DD99E16F67.jpg','2021-03-10 19:37:42.811'),(2398,2019,3,'1D797D70217DDC7EFD90E179CC6DABF0.jpg','2021-03-10 19:37:42.827'),(2399,2019,4,'488AE206A4F098B8DB8D3421E31A5FA7.txt','2021-03-10 19:37:42.849'),(2400,2020,2,'9CD155C3C5FDAADD0D05889692AC2E24.jpg','2021-03-10 19:37:53.182'),(2401,2020,3,'8BB4261A7D90CEA76B45F368A729B3E3.jpg','2021-03-10 19:37:53.196'),(2402,2021,2,'7EF7A9F3F0A8750C3D98BEE1681DF819.jpg','2021-03-10 19:37:55.625'),(2403,2021,3,'2C18F7097D789D287AE2DE8F335D9DE0.jpg','2021-03-10 19:37:55.641'),(2404,2021,4,'EB1FA5C99D85EE2C2C1582EEB592D257.txt','2021-03-10 19:37:55.663'),(2405,4,0,'B119450BF32AC42906B6B1ED580A7CC7.mp4','2021-03-10 19:37:57.864'),(2406,2013,1,'BD33FE5A28D023A72402C4E0ABAC9A10.mp4','2021-03-10 19:38:38.868'),(2407,2022,2,'BAA15DD194269034305FF3536A39432B.jpg','2021-03-10 19:40:15.693'),(2408,2022,3,'624E5FE51CC7057D2FF5E753B4D0DFE9.jpg','2021-03-10 19:40:15.710'),(2409,2023,2,'002CBB3CEC64C8E83E4A28F14FF2AFBC.jpg','2021-03-10 19:40:30.220'),(2410,2023,3,'CA1121252DD99BD8A035515214D3BEAF.jpg','2021-03-10 19:40:30.237'),(2411,2024,2,'8F9BC3D222A3CFF335C77A63AEBEE5E5.jpg','2021-03-10 19:40:42.034'),(2412,2024,3,'2A8135B5E5820F6308D364D2553C394E.jpg','2021-03-10 19:40:42.050'),(2413,2014,1,'04A3D86CC39693D1967E399421C6E900.mp4','2021-03-10 19:41:00.283'),(2414,2015,1,'FD5595E975E1E14204071D26DAFE0DC7.mp4','2021-03-10 19:41:09.621'),(2415,2016,1,'5D20CB72DA1B41622EEF4E953602BA9A.mp4','2021-03-10 19:41:19.962'),(2416,2025,2,'FAF813DCF236E50092A36DB976274964.jpg','2021-03-10 19:41:34.813'),(2417,2025,3,'5D5DFB4EA434EEC593DA2B56CCB8A44A.jpg','2021-03-10 19:41:34.828'),(2418,2025,4,'046AFA9E20105C7DC9CB2D544ED4CB84.txt','2021-03-10 19:41:34.847'),(2419,2017,1,'9D802DEBE5645B9B730F829741E0B3FA.mp4','2021-03-10 19:42:30.319'),(2420,2026,2,'8EE9A988495E345681E236846A670503.jpg','2021-03-10 19:42:31.677'),(2421,2026,3,'5B9FAF6B49C33EAAA08B8D911EA72F2E.jpg','2021-03-10 19:42:31.692'),(2422,2018,1,'A034BD172A9316600432233ECB571030.mp4','2021-03-10 19:42:36.640'),(2423,2019,1,'C8D579A6DB3805F87F90DF3087D52A8F.mp4','2021-03-10 19:42:43.969'),(2424,2027,2,'162540F1A3AFD54402DD21FF7A9026FE.jpg','2021-03-10 19:42:44.257'),(2425,2027,3,'8C34B5F7B9A5871E485473403FB6B859.jpg','2021-03-10 19:42:44.272'),(2426,2028,2,'BA5687E20D8ED6E44A90F21CAF445362.jpg','2021-03-10 19:42:46.141'),(2427,2028,3,'4CF8E389A8E7576AEDA07D2E9C33A6DC.jpg','2021-03-10 19:42:46.158'),(2428,2029,2,'E3400B24063F0F83C529E36002951E6F.jpg','2021-03-10 19:42:48.122'),(2429,2029,3,'E16A54EB9D1D907FC7794AD23380181A.jpg','2021-03-10 19:42:48.137'),(2430,2030,2,'AAF3DFF7666F001192B6628BBCF01479.jpg','2021-03-10 19:42:51.315'),(2431,2030,3,'07D35BA0DF5893363E7EB1119218FFBE.jpg','2021-03-10 19:42:51.331'),(2432,2020,1,'DF6908AB7229C6284CCCAD43BBB6DE8E.mp4','2021-03-10 19:42:54.288'),(2433,2021,1,'9C55F56F2316333C7483E6F67ED78EE4.mp4','2021-03-10 19:42:56.597'),(2434,5,0,'0DDE14866026BA6720DCFF1EE7B6ED24.mp4','2021-03-10 19:42:57.888'),(2435,2031,2,'A7A69709B03E23EF98AFE441C7B4A609.jpg','2021-03-10 19:43:00.458'),(2436,2031,3,'82D14104C9BEC5929FBBE6D2B7B60C88.jpg','2021-03-10 19:43:00.473'),(2437,2032,2,'D6D8D7129B3B9C01B5E9186A5CC2E8E7.jpg','2021-03-10 19:43:12.103'),(2438,2032,3,'BEEC960F9EF65E711E217AD7B7A2DD69.jpg','2021-03-10 19:43:12.269'),(2439,2033,2,'D84D791769391A99ECAF7F86A80A3A30.jpg','2021-03-10 19:43:14.475'),(2440,2033,3,'4E9DE4C7231E70366906C385AF106142.jpg','2021-03-10 19:43:14.810'),(2441,2022,1,'76C2A4FF142EC75E844B68A183FDCF64.mp4','2021-03-10 19:45:16.973'),(2442,2023,1,'0C12F0AF6A7F984723435949AC6688CE.mp4','2021-03-10 19:45:31.272'),(2443,2024,1,'16C74245DA02E76AB9D9750AEDF4BC9A.mp4','2021-03-10 19:45:42.559'),(2444,2034,2,'2EBB032266D48FEBF7CD4CBCC9B90E2F.jpg','2021-03-10 19:46:34.424'),(2445,2034,3,'59767B0C6FA5D48B71576947D0DC903C.jpg','2021-03-10 19:46:34.440'),(2446,2025,1,'9271523AEB8DC338A2327263931941EA.mp4','2021-03-10 19:46:35.891'),(2447,2035,2,'16A1761DE2AE81F99CE6D362B82ADE11.jpg','2021-03-10 19:46:51.010'),(2448,2035,3,'912AC517321BC5DC7D9797CAA949D79E.jpg','2021-03-10 19:46:51.028'),(2449,2036,2,'EA18FE967119EB26831A217FE75CAF5D.jpg','2021-03-10 19:46:53.493'),(2450,2036,3,'64297BF092E10FDD9859DEEC5988EB5F.jpg','2021-03-10 19:46:53.508'),(2451,2026,1,'C94717F1F9DA60F88F930870A08BBB5F.mp4','2021-03-10 19:47:32.218'),(2452,2027,1,'3273F1DF2FE79DBD87FFD54D0BC52796.mp4','2021-03-10 19:47:45.508'),(2453,2028,1,'37E4B920950158710C60592352A1BA04.mp4','2021-03-10 19:47:46.787'),(2454,2029,1,'44B227B9D754B86DFA8079C968045388.mp4','2021-03-10 19:47:49.102'),(2455,2030,1,'640B5AEC1E9EDBC080DBBEF15100EB19.mp4','2021-03-10 19:47:52.392'),(2456,6,0,'46AE5AD293578C6841A483B22D8C51BB.mp4','2021-03-10 19:47:57.842'),(2457,2031,1,'E761C6C477E64AA426CAC7A05E46D540.mp4','2021-03-10 19:48:01.692'),(2458,2032,1,'61A9F0E2B28401EFD27FB30CDFAC304E.mp4','2021-03-10 19:48:12.988'),(2459,2033,1,'F1518AF70E2104C6983D295B3B2235A7.mp4','2021-03-10 19:48:16.304'),(2460,2037,2,'74678F679BB66267D9A386AA64A852B6.jpg','2021-03-10 19:48:31.714'),(2461,2037,3,'0C39ECA50E98DD2C8103180A28D4F8D9.jpg','2021-03-10 19:48:31.728'),(2462,2038,2,'8C1996AE20B3F8C57DFB9890D7D5D451.jpg','2021-03-10 19:48:34.069'),(2463,2038,3,'941AC28D2A84F21780E590A7C7AFCF38.jpg','2021-03-10 19:48:34.085'),(2464,2038,4,'E5E9CCF552607D292A31CCDE81AB614D.txt','2021-03-10 19:48:34.118'),(2465,2039,2,'9AAC05AD3D71C118F0F56A15525D2191.jpg','2021-03-10 19:51:17.264'),(2466,2039,3,'8BE1CC78D79EF00FF26B40CCD66C3608.jpg','2021-03-10 19:51:17.280'),(2467,2034,1,'4DB7109FA766C390DA9A693CC1F332C6.mp4','2021-03-10 19:51:35.746'),(2468,2035,1,'9ED5B51196FA9E0B1CE6EC1B2FCA0565.mp4','2021-03-10 19:51:52.097'),(2469,2036,1,'96DD112F9473FA910912AA333A20A7C2.mp4','2021-03-10 19:51:54.402'),(2470,7,0,'113B8B46C063C20778F6204F2705A086.mp4','2021-03-10 19:52:57.882'),(2471,2037,1,'84D896C4DF41E04E94447F1CAEF07696.mp4','2021-03-10 19:53:32.785'),(2472,2038,1,'82D1FF5E26B8D75A6BB8A564ABA274BA.mp4','2021-03-10 19:53:35.111'),(2473,2040,2,'ABCADC42EAC6D6DC959D2A2C476B56AC.jpg','2021-03-10 19:53:40.565'),(2474,2040,3,'D35436FEBBD8713BE4A5B9C4AEA38F56.jpg','2021-03-10 19:53:40.580'),(2475,2041,2,'7E0957DF96B34072A23F0DE206FE5870.jpg','2021-03-10 19:53:54.129'),(2476,2041,3,'E1C9C5B89DE9FA08F5E87868EF3C2890.jpg','2021-03-10 19:53:54.145'),(2477,2042,2,'B09BDAD4257846DF8977CF6843D2D762.jpg','2021-03-10 19:54:57.301'),(2478,2042,3,'A6594481E6613C277A7CAFD2607364AB.jpg','2021-03-10 19:54:57.316'),(2479,2039,1,'301B593F5E065AEF6691CECE50D7ECED.mp4','2021-03-10 19:56:18.549'),(2480,2043,2,'91A4DB4B082AB9349FAD21B4D150296C.jpg','2021-03-10 19:56:32.514'),(2481,2043,3,'8019575209F8EAC8295E1450ABEEA836.jpg','2021-03-10 19:56:32.528'),(2482,2044,2,'B062DD2E1B04F885469021139B2CF76E.jpg','2021-03-10 19:56:34.916'),(2483,2044,3,'72D3CDDD3252B1ABFD6F41A456A6BA77.jpg','2021-03-10 19:56:34.929'),(2484,2044,4,'1CCD8E0E90BB83764DDD14690D3A408A.txt','2021-03-10 19:56:34.948'),(2485,2045,2,'098AF6C35554483975330CAD27AD9801.jpg','2021-03-10 19:56:37.463'),(2486,2045,3,'C15D559A61C359C62E9F64F7CA9E1040.jpg','2021-03-10 19:56:37.477'),(2487,2045,4,'525E3361E685D59F8B754932CE36939B.txt','2021-03-10 19:56:37.495'),(2488,8,0,'1998A5713996858BD81D2A80504D5E55.mp4','2021-03-10 19:57:57.889'),(2489,2046,2,'F49DBAC9BF8FB727494BC33BDF4B5200.jpg','2021-03-10 19:58:47.859'),(2490,2046,3,'26960F259CA544A546165C4D48A626BA.jpg','2021-03-10 19:58:47.874'),(2491,2041,1,'76F8F2FB206B51175A516F23F13CC9FB.mp4','2021-03-10 19:58:54.931'),(2492,2047,2,'B6C93AC734002F6E692E556043A465A4.jpg','2021-03-10 19:59:01.255'),(2493,2047,3,'787AA4F06A9E4145C3CD7500778E237E.jpg','2021-03-10 19:59:01.269'),(2494,2048,2,'451EE57ED6B21E741643CF8DFE85DA49.jpg','2021-03-10 19:59:13.472'),(2495,2048,3,'3F4A173F08B3EEFA391F6E922874B3F1.jpg','2021-03-10 19:59:13.487'),(2496,2042,1,'19F9AFEC05D1B2E025E80604A01E5F51.mp4','2021-03-10 19:59:58.265'),(2497,2049,2,'C9B4E7AFC2D4C82B371912423AFFB2CC.jpg','2021-03-10 20:00:06.715'),(2498,2049,3,'F1B75A07016C59BF08720A9FE664C6AA.jpg','2021-03-10 20:00:06.729'),(2499,2049,4,'099BE90CA9439E62416726642DA1C1FE.txt','2021-03-10 20:00:06.748'),(2500,2050,2,'948F92F49F66896FBEDA09B90A90F839.jpg','2021-03-10 20:00:19.479'),(2501,2050,3,'8B817C479FD2A7CAB3D8C60173790223.jpg','2021-03-10 20:00:19.496'),(2502,2050,4,'6E114322A6231BCD4A0FD87752370703.txt','2021-03-10 20:00:19.515'),(2503,2043,1,'F5288608B84ACB34BB55D572DC90CA5E.mp4','2021-03-10 20:01:33.632'),(2504,2044,1,'D1C2593DC8DD5B484B25F4FB6D083E17.mp4','2021-03-10 20:01:35.936'),(2505,2045,1,'8B0E67639D141BFC609AC259ECA87B7B.mp4','2021-03-10 20:01:38.270'),(2506,2051,2,'E0BCF853A235A0B92BE79B3A99578F27.jpg','2021-03-10 20:02:19.914'),(2507,2051,3,'80EE54A060F9673D2CDB500A3EB72F37.jpg','2021-03-10 20:02:19.928'),(2508,9,0,'32295E98637DCEB1DD6CFD6CD36A311B.mp4','2021-03-10 20:02:57.967'),(2509,2046,1,'8BD8C66E79D8FD43311F3E610608E518.mp4','2021-03-10 20:03:48.659'),(2510,2052,2,'482BB1C078C983ED9C1809478F1D9BF2.jpg','2021-03-10 20:03:53.100'),(2511,2052,3,'DCD0841E2625CEB53D59E01744EAF556.jpg','2021-03-10 20:03:53.115'),(2512,2047,1,'F977C6E846C20FE90C914B57308E5AD2.mp4','2021-03-10 20:04:01.966'),(2513,2048,1,'D3103A16871578118A61650392B4F8B2.mp4','2021-03-10 20:04:14.339'),(2514,2053,2,'70C915A1C962B9F7B1857F2B96ED3667.jpg','2021-03-10 20:04:38.504'),(2515,2053,3,'79107F2C5C3B99AA6C4D9C73A363F70C.jpg','2021-03-10 20:04:38.520'),(2516,2054,2,'CC479AA017F9CD563181EA001FE525AD.jpg','2021-03-10 20:04:41.635'),(2517,2054,3,'AA1F01108C5D122E4470267225CAB280.jpg','2021-03-10 20:04:41.649'),(2518,2054,4,'EE39B01DC01066E9694A49BF72E1B25F.txt','2021-03-10 20:04:41.668'),(2519,2055,2,'279C46383B57918DA6B29BE93089E85C.jpg','2021-03-10 20:04:56.989'),(2520,2055,3,'20FBC43655616B5D4E586F7E64DAD6B5.jpg','2021-03-10 20:04:57.004'),(2521,2055,4,'D5AC874804405D896D9D5A643F257E73.txt','2021-03-10 20:04:57.026'),(2522,2056,2,'0B4EF5C49B4B1DF9ACBBCFBB31EA9E4C.jpg','2021-03-10 20:04:59.369'),(2523,2056,3,'934C7FC269053256344E4CE3A3694962.jpg','2021-03-10 20:04:59.384'),(2524,2056,4,'4340D69C43C0B36546B8C10584B7124A.txt','2021-03-10 20:04:59.404'),(2525,2057,2,'AFB1F75C113D8B41A7D96EB4AC06B358.jpg','2021-03-10 20:05:01.340'),(2526,2057,3,'DD9C339ACAA578A79F2FC6A3C70F2F47.jpg','2021-03-10 20:05:01.355'),(2527,2049,1,'926A679158F49AE3A5131936DB51A6CC.mp4','2021-03-10 20:05:07.689'),(2528,2050,1,'971382D3567E304D565D46028E0AC596.mp4','2021-03-10 20:05:19.997'),(2529,2058,2,'D8C269E101B449BAB58051EF7038682A.jpg','2021-03-10 20:06:52.039'),(2530,2058,3,'9234954C985AE811C12F25FC95F519F2.jpg','2021-03-10 20:06:52.056'),(2531,2051,1,'50C0333A1D3DD872519FFFBC75B4C6AE.mp4','2021-03-10 20:07:20.394'),(2532,10,0,'BEBC4EBDC55F3E25EB45A5D4386A19C0.mp4','2021-03-10 20:07:58.039'),(2533,2052,1,'B464C79AD0B193164FE036CA12DB1E07.mp4','2021-03-10 20:08:53.750'),(2534,2053,1,'1244573E8A7B10D8B47B9A680EA9B267.mp4','2021-03-10 20:09:39.098'),(2535,2054,1,'1244573E8A7B10D8B47B9A680EA9B267.mp4','2021-03-10 20:09:42.395'),(2536,2055,1,'3E037F4BD66B5AE2CD190594CB29269F.mp4','2021-03-10 20:09:57.755'),(2537,2056,1,'D82E1C5D79074EE3C94FECC57CEC57AA.mp4','2021-03-10 20:10:00.076'),(2538,2057,1,'1FC408F18652ACE41C63ABA9CEAFE165.mp4','2021-03-10 20:10:02.420'),(2539,2059,2,'8C4DE46A2AEEF8FAE84F8D9F201B556B.jpg','2021-03-10 20:11:14.638'),(2540,2059,3,'EE6313F8B51584C1127F9D40CD3EF11A.jpg','2021-03-10 20:11:14.674'),(2541,2060,2,'F26376E1ADBABD88599EDC9A00E78A22.jpg','2021-03-10 20:11:31.451'),(2542,2060,3,'47A1713113FC42C906E1B60FD0CA4A49.jpg','2021-03-10 20:11:31.470'),(2543,2058,1,'2B87332301D64FD5115809169EEBF159.mp4','2021-03-10 20:11:52.799'),(2544,2061,2,'C52AF42134F08FF2B3E97604E2547CDB.jpg','2021-03-10 20:12:22.653'),(2545,2061,3,'7C7DF67F52796863942A3CB72C4BC3DA.jpg','2021-03-10 20:12:22.668'),(2546,11,0,'34EFA57AFEA4C549E26C72001336564B.mp4','2021-03-10 20:12:57.966'),(2547,2062,2,'1988D6FF050A6E001D83D73FB0D76C6A.jpg','2021-03-10 20:13:02.198'),(2548,2062,3,'286FAA2C7BA372179700DE5510FDD37D.jpg','2021-03-10 20:13:02.213'),(2549,2063,2,'F2EE682611C728B455206AFA03A1995E.jpg','2021-03-10 20:13:28.794'),(2550,2063,3,'089E4627C1F124275B510A79AA4C77F3.jpg','2021-03-10 20:13:28.813'),(2551,2063,4,'E6519A7B4DA017125A45C41D16B3D4E9.txt','2021-03-10 20:13:28.833'),(2552,2064,2,'D8F2B225AEF98A5507A3C4F133767CF2.jpg','2021-03-10 20:13:31.156'),(2553,2064,3,'9B715E0DAC87AC5F2E8FE80D58373B94.jpg','2021-03-10 20:13:31.171'),(2554,2064,4,'055F18087DC6CE8C42C0D95F540F917A.txt','2021-03-10 20:13:31.188'),(2555,2065,2,'A02925E65ED61F7DF27C042DFF540D68.jpg','2021-03-10 20:14:15.965'),(2556,2065,3,'3BEA51324ADDBE9094C3625F87798127.jpg','2021-03-10 20:14:15.980'),(2557,2066,2,'90C0BDF09D179F4B754FA792C0151969.jpg','2021-03-10 20:16:02.331'),(2558,2066,3,'5F7C910296EAC696F7165A9BFDAA698A.jpg','2021-03-10 20:16:02.347'),(2559,2059,1,'FAB6AC68BF5B46E5207E561DBE4D0926.mp4','2021-03-10 20:16:15.281'),(2560,2060,1,'55E5764D6F482D8F8923C667CD4EE235.mp4','2021-03-10 20:16:32.583'),(2561,2067,2,'460605E5F14A2828486C536C1F4A8FB8.jpg','2021-03-10 20:16:48.402'),(2562,2067,3,'C9C1E2913454D9CDEDD043ECB8DE61CE.jpg','2021-03-10 20:16:48.584'),(2563,2068,2,'A78F124FB25EA800134ADAC8FBCA1789.jpg','2021-03-10 20:16:59.718'),(2564,2068,3,'31BC451C8DC2EE7525CBAC43DC109789.jpg','2021-03-10 20:16:59.736'),(2565,2061,1,'B1775FF22F478233034E75E14FB0DA6D.mp4','2021-03-10 20:17:23.915'),(2566,12,0,'21C228E212F9C9CBD9D383B2AC88FC44.mp4','2021-03-10 20:17:57.989'),(2567,2062,1,'0E7B86A438E9E3B767759B0497B695B0.mp4','2021-03-10 20:18:03.238'),(2568,2063,1,'F77EB9ADDDDBB776BA2B01C38CACBB8E.mp4','2021-03-10 20:18:29.562'),(2569,2064,1,'69CEBA283B3A860324881AEA71B7D949.mp4','2021-03-10 20:18:31.914'),(2570,2065,1,'D15DC1A498CF8FF339F617E8D1488D5B.mp4','2021-03-10 20:19:17.295'),(2571,2066,1,'95D12552F5B35F1476376ACCA8413014.mp4','2021-03-10 20:21:02.673'),(2572,2069,2,'90360FD08F041067D4B4194CBE1B1610.jpg','2021-03-10 20:21:15.058'),(2573,2069,3,'C3A6B74B2CF8364F42FD76C25120CF33.jpg','2021-03-10 20:21:15.074'),(2574,2069,4,'401F7433E2D26C928110CC49C2C30B9B.txt','2021-03-10 20:21:15.097'),(2575,2067,1,'0B5FAED9CF01B0C07D1D2EF239D507DA.mp4','2021-03-10 20:21:49.006'),(2576,2068,1,'11568AAA45211D52B724C66416CEC456.mp4','2021-03-10 20:22:00.337'),(2577,13,0,'5A857E64E62A32E727F136166B86BBB8.mp4','2021-03-10 20:22:58.032'),(2578,2070,2,'B5F2832C81A0F6BF43FDC130A460926A.jpg','2021-03-10 20:23:27.401'),(2579,2070,3,'B4053BDE92ADB906B429FBA2CE5413C9.jpg','2021-03-10 20:23:27.416'),(2580,2070,4,'5A90131CF5742CDEDEE6A65E205C26B3.txt','2021-03-10 20:23:27.435'),(2581,2069,1,'29FDB3D107DE96C60045EDAAE100AA9B.mp4','2021-03-10 20:26:15.813'),(2582,2071,2,'31AABD0549F9B5165A5D4A6C4E83177A.jpg','2021-03-10 20:26:53.622'),(2583,2071,3,'E69C7A47C9044BC63FCB47AC3398F23C.jpg','2021-03-10 20:26:53.650'),(2584,14,0,'DEB7BF9779DE1B144C03241C351347CD.mp4','2021-03-10 20:27:58.078'),(2585,2070,1,'322B45F7AC0F6233C9A785A31B2C2064.mp4','2021-03-10 20:28:28.208'),(2586,2072,2,'BD2FEBBF725C48DD48F54ECDD63CB5E4.jpg','2021-03-10 20:31:17.900'),(2587,2072,3,'7704F987CBD161550038759DCDEA3986.jpg','2021-03-10 20:31:17.984'),(2588,2073,2,'F43C99797D21ACE763300C1814A4407E.jpg','2021-03-10 20:31:37.773'),(2589,2073,3,'5CCE7D5C3AA1F0085CF3E497147DB028.jpg','2021-03-10 20:31:37.788'),(2590,2071,1,'E3658CF1509B9DCE6ECF43DE8F0A356B.mp4','2021-03-10 20:31:54.648'),(2591,15,0,'7375957829A447897C52810D63762BE9.mp4','2021-03-10 20:32:58.094'),(2592,2074,2,'34BF5748936C69A74213166C8DE24A15.jpg','2021-03-10 20:33:30.702'),(2593,2074,3,'FA1E69D4B4B5C025BDFDD3E71E44CAA6.jpg','2021-03-10 20:33:30.716'),(2594,2075,2,'CCDF361706B60C4A09128FE32276FFD8.jpg','2021-03-10 20:35:47.887'),(2595,2075,3,'A8E7FD14315EFE4FC00B0AAF0F0285BE.jpg','2021-03-10 20:35:47.903'),(2596,2076,2,'132D36AF5EC035565DE3EC37E6DF3C0F.jpg','2021-03-10 20:36:03.192'),(2597,2076,3,'A69052C8F2AC4ECD5FEC29B877A64367.jpg','2021-03-10 20:36:03.210'),(2598,2072,1,'5931FA3C61BA15B6110BDEDC4C667656.mp4','2021-03-10 20:36:19.124'),(2599,2073,1,'69C2182268BCF44EAAA50CC30BA17797.mp4','2021-03-10 20:36:38.455'),(2600,2077,2,'29B7D1CEF63D14D4E1EA0D414B2C6EED.jpg','2021-03-10 20:37:15.370'),(2601,2077,3,'430F92A05BA5D6D8201FACA7B254A997.jpg','2021-03-10 20:37:15.384'),(2602,2077,4,'E6822BF43BC0737F98420D736262B302.txt','2021-03-10 20:37:15.403'),(2603,16,0,'3A5180D6627E997C6476300E99460F2F.mp4','2021-03-10 20:37:58.075'),(2604,2074,1,'5791F971A89E1B0771B9EADB8322652B.mp4','2021-03-10 20:38:31.826'),(2605,2078,2,'3841220AE629754A0D2A53F8967808E4.jpg','2021-03-10 20:39:53.894'),(2606,2078,3,'24BA07F6024690BED82240B976C3C394.jpg','2021-03-10 20:39:53.909'),(2607,2078,4,'17AD07ED7C323255079FA0730A27E071.txt','2021-03-10 20:39:53.924'),(2608,2075,1,'38D744DB0AA81F2B74D1BCAE819A10BF.mp4','2021-03-10 20:40:49.217'),(2609,2076,1,'719B6E34C5C96FA580B0088CFA800D23.mp4','2021-03-10 20:41:03.530'),(2610,2079,2,'41490EBE414B8DD85ED792C994F8EA5D.jpg','2021-03-10 20:41:29.381'),(2611,2079,3,'0C8ADB84FC5A2D19B1F174D5083078D5.jpg','2021-03-10 20:41:29.395'),(2612,2080,2,'6DCBFFA47BC149CA74C767747422CCDC.jpg','2021-03-10 20:41:33.837'),(2613,2080,3,'8A9484E88E7F311EB50BCE08404E4C0D.jpg','2021-03-10 20:41:33.852'),(2614,2081,2,'37E0672E20730F350681AB447BD20632.jpg','2021-03-10 20:41:40.293'),(2615,2081,3,'33A5188C24FC42738C44C2ABFEE96DB2.jpg','2021-03-10 20:41:40.306'),(2616,2077,1,'ED0A7F1B8670E228F0745F4A654C9FD5.mp4','2021-03-10 20:42:15.901'),(2617,17,0,'2B256F4BC22E43FE7E986B05ECA284F9.mp4','2021-03-10 20:42:58.115'),(2618,2078,1,'2BEC7ACD78767E6D4BBB88FA9B459FA2.mp4','2021-03-10 20:44:54.324'),(2619,2079,1,'D0D55219D263E7F45EBD07215A555F1D.mp4','2021-03-10 20:46:30.711'),(2620,2080,1,'08E0384B5DEB9F56D4E0F1F82161B12D.mp4','2021-03-10 20:46:35.059'),(2621,2081,1,'3042E1BBA4620C2E710BA198DE6E66B9.mp4','2021-03-10 20:46:41.417'),(2622,18,0,'5693C426F22E35EAAC4CCE4097B25271.mp4','2021-03-10 20:47:58.169'),(2623,2082,2,'D6F7960E48E42575D6BD4446541749ED.jpg','2021-03-10 20:48:07.248'),(2624,2082,3,'C2086407E2312B891AA1C126FF340A01.jpg','2021-03-10 20:48:07.283'),(2625,2082,4,'D163A55D0B49949C49EDA3E12F7F1616.txt','2021-03-10 20:48:07.303'),(2626,2083,2,'72964D1FAF004A191706F302CB038FB7.jpg','2021-03-10 20:49:55.458'),(2627,2083,3,'1CAE60DD2047325C2A4C1E5DC6264772.jpg','2021-03-10 20:49:55.477'),(2628,19,0,'D9E681313F63949416D29D1AE44A2BA7.mp4','2021-03-10 20:52:58.182'),(2629,2082,1,'C14A266B69A72FB0BC8808A33DCCC50E.mp4','2021-03-10 20:53:07.990'),(2630,2083,1,'F37CC2143BBB32468E6983E67CFB97F4.mp4','2021-03-10 20:54:56.579');
/*!40000 ALTER TABLE `tb_media_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_menu`
--

DROP TABLE IF EXISTS `tb_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_menu` (
  `Id` bigint NOT NULL,
  `fahterId` bigint DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_menu`
--

LOCK TABLES `tb_menu` WRITE;
/*!40000 ALTER TABLE `tb_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_module_role`
--

DROP TABLE IF EXISTS `tb_module_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_module_role` (
  `id` bigint NOT NULL,
  `roleId` bigint NOT NULL,
  `moduleId` bigint NOT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_module_role`
--

LOCK TABLES `tb_module_role` WRITE;
/*!40000 ALTER TABLE `tb_module_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_module_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_org`
--

DROP TABLE IF EXISTS `tb_org`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_org` (
  `id` bigint NOT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_org`
--

LOCK TABLES `tb_org` WRITE;
/*!40000 ALTER TABLE `tb_org` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_org` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_org_role`
--

DROP TABLE IF EXISTS `tb_org_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_org_role` (
  `id` bigint NOT NULL,
  `roleId` bigint NOT NULL,
  `orgId` bigint NOT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_org_role`
--

LOCK TABLES `tb_org_role` WRITE;
/*!40000 ALTER TABLE `tb_org_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_org_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_permission`
--

DROP TABLE IF EXISTS `tb_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_permission` (
  `id` bigint NOT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_permission`
--

LOCK TABLES `tb_permission` WRITE;
/*!40000 ALTER TABLE `tb_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_permission_module`
--

DROP TABLE IF EXISTS `tb_permission_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_permission_module` (
  `id` bigint NOT NULL,
  `moduleId` bigint NOT NULL,
  `permissionId` bigint NOT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_permission_module`
--

LOCK TABLES `tb_permission_module` WRITE;
/*!40000 ALTER TABLE `tb_permission_module` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_permission_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_role`
--

DROP TABLE IF EXISTS `tb_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_role` (
  `id` bigint NOT NULL,
  `fatherId` bigint DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_role`
--

LOCK TABLES `tb_role` WRITE;
/*!40000 ALTER TABLE `tb_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_ticket`
--

DROP TABLE IF EXISTS `tb_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_ticket` (
  `id` bigint NOT NULL,
  `reporterId` bigint NOT NULL,
  `handlerId` bigint DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `priority` tinyint DEFAULT NULL,
  `type` tinyint DEFAULT NULL,
  `template` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_ticket`
--

LOCK TABLES `tb_ticket` WRITE;
/*!40000 ALTER TABLE `tb_ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_ticket_log`
--

DROP TABLE IF EXISTS `tb_ticket_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_ticket_log` (
  `id` bigint NOT NULL,
  `ticketId` bigint NOT NULL,
  `userId` bigint NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `isApproved` tinyint DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_ticket_log`
--

LOCK TABLES `tb_ticket_log` WRITE;
/*!40000 ALTER TABLE `tb_ticket_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_ticket_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_unbox`
--

DROP TABLE IF EXISTS `tb_unbox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_unbox` (
  `id` bigint NOT NULL,
  `deviceId` bigint DEFAULT NULL,
  `deviceType` tinyint DEFAULT NULL,
  `reporterId` bigint NOT NULL,
  `status` tinyint DEFAULT NULL,
  `title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `template` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_unbox`
--

LOCK TABLES `tb_unbox` WRITE;
/*!40000 ALTER TABLE `tb_unbox` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_unbox` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_unbox_log`
--

DROP TABLE IF EXISTS `tb_unbox_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_unbox_log` (
  `id` bigint NOT NULL,
  `unboxId` bigint NOT NULL,
  `userId` bigint NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `isApproved` tinyint DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_unbox_log`
--

LOCK TABLES `tb_unbox_log` WRITE;
/*!40000 ALTER TABLE `tb_unbox_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_unbox_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user` (
  `id` bigint NOT NULL,
  `orgId` bigint DEFAULT NULL,
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `isPasswordConfirmed` tinyint DEFAULT NULL,
  `salt` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `isPhoneConfirmed` tinyint DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `isEmailConfirmed` tinyint DEFAULT NULL,
  `avatarUrl` varchar(2083) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_login`
--

DROP TABLE IF EXISTS `tb_user_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user_login` (
  `id` bigint NOT NULL,
  `userId` bigint NOT NULL,
  `name` tinyint NOT NULL,
  `unionId` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_login`
--

LOCK TABLES `tb_user_login` WRITE;
/*!40000 ALTER TABLE `tb_user_login` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_user_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_role`
--

DROP TABLE IF EXISTS `tb_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user_role` (
  `id` bigint NOT NULL,
  `userId` bigint NOT NULL,
  `roleId` bigint NOT NULL,
  `createdAt` timestamp NOT NULL,
  `updatedAt` timestamp NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_role`
--

LOCK TABLES `tb_user_role` WRITE;
/*!40000 ALTER TABLE `tb_user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-16 17:52:26

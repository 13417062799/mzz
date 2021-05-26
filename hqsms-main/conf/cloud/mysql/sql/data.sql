USE `hqsms`;
-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: hqsms
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Dumping data for table `eg_alarm`
--

LOCK TABLES `eg_alarm` WRITE;
/*!40000 ALTER TABLE `eg_alarm` DISABLE KEYS */;
INSERT INTO `eg_alarm` VALUES (1,1,1,'alarm1','Alarm-01',1,NULL,'192.168.1.34','C0-51-7E-02-AD-5B','255.255.255.0','192.168.1.1','1','1','2020-10-16 19:16:23','海康威视','DS-PEAP-CV1','2020-02-19 08:42:00','2020-02-19 08:42:00',NULL,'Hg123_123','admin');
/*!40000 ALTER TABLE `eg_alarm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_alarm_log`
--

LOCK TABLES `eg_alarm_log` WRITE;
/*!40000 ALTER TABLE `eg_alarm_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_alarm_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_broadcast`
--

LOCK TABLES `eg_broadcast` WRITE;
/*!40000 ALTER TABLE `eg_broadcast` DISABLE KEYS */;
INSERT INTO `eg_broadcast` VALUES (1,1,1,'broadcast1','Brca-01',6,0,NULL,'192.168.1.6','192.168.1.20','C8-AA-55-05-06-0A','255.255.255.0','192.168.1.1','1','1','2020-10-16 19:16:23','雷拓','RT-WA05','2020-10-16 19:16:23','2020-10-16 19:16:23',NULL,'admin','admin','JSESSIONID=6D8D35B2515711EB824C42F39EC55667');
/*!40000 ALTER TABLE `eg_broadcast` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_broadcast_content`
--

LOCK TABLES `eg_broadcast_content` WRITE;
/*!40000 ALTER TABLE `eg_broadcast_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_broadcast_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_broadcast_plan`
--

LOCK TABLES `eg_broadcast_plan` WRITE;
/*!40000 ALTER TABLE `eg_broadcast_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_broadcast_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_broadcast_task`
--

LOCK TABLES `eg_broadcast_task` WRITE;
/*!40000 ALTER TABLE `eg_broadcast_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_broadcast_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_camera`
--

LOCK TABLES `eg_camera` WRITE;
/*!40000 ALTER TABLE `eg_camera` DISABLE KEYS */;
INSERT INTO `eg_camera` VALUES (1,1,1,'camera1','cam-01',1,NULL,'192.168.1.111','10-12-FB-2F-99-84','255.255.255.0','192.168.1.1','1','1','2020-10-16 19:16:23','华全','iDS-2DF7C425IXR','2020-10-16 19:16:23','2020-10-16 19:16:23',NULL,'admin','Hg123_123');
/*!40000 ALTER TABLE `eg_camera` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_camera_human`
--

LOCK TABLES `eg_camera_human` WRITE;
/*!40000 ALTER TABLE `eg_camera_human` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_camera_human` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_camera_log`
--

LOCK TABLES `eg_camera_log` WRITE;
/*!40000 ALTER TABLE `eg_camera_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_camera_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_camera_vehicle`
--

LOCK TABLES `eg_camera_vehicle` WRITE;
/*!40000 ALTER TABLE `eg_camera_vehicle` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_camera_vehicle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_charger`
--

LOCK TABLES `eg_charger` WRITE;
/*!40000 ALTER TABLE `eg_charger` DISABLE KEYS */;
INSERT INTO `eg_charger` VALUES (1,1,1,'charger1','crg-01',1,1,'192.168.1.34','E0-89-7E-AA-78-9D','255.255.255.0','192.168.1.1','1','1','2021-02-24 16:00:00','华全','JAW1-7C10','2021-02-25 09:00:00','2021-02-25 09:00:00',NULL,'http://119.29.85.28:74/api/2/000000000',452,'0000000000696285');
/*!40000 ALTER TABLE `eg_charger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_charger_log`
--

LOCK TABLES `eg_charger_log` WRITE;
/*!40000 ALTER TABLE `eg_charger_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_charger_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_charger_order`
--

LOCK TABLES `eg_charger_order` WRITE;
/*!40000 ALTER TABLE `eg_charger_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_charger_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_light`
--

LOCK TABLES `eg_light` WRITE;
/*!40000 ALTER TABLE `eg_light` DISABLE KEYS */;
INSERT INTO `eg_light` VALUES (1,1,1,'light1','Light-01',1,1,'192.168.1.27',20108,'9C-A5-25-A1-50-EA','255.255.255.0','192.168.1.1','1','1','2021-04-11 12:12:12','华全','HQ-SLC-500','2021-04-11 12:12:12','2021-04-11 12:12:12',NULL);
/*!40000 ALTER TABLE `eg_light` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_light_log`
--

LOCK TABLES `eg_light_log` WRITE;
/*!40000 ALTER TABLE `eg_light_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_light_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_meter`
--

LOCK TABLES `eg_meter` WRITE;
/*!40000 ALTER TABLE `eg_meter` DISABLE KEYS */;
INSERT INTO `eg_meter` VALUES (1,1,1,'meter1','Meter-01',1,1,'192.168.1.5',20108,247,'9C-A5-25-A1-50-EA','255.255.255.0','192.168.1.1','1','1','2021-03-03 04:12:12','华全','ADL3000-KLH','2021-03-03 04:12:12','2021-03-03 04:12:12',NULL);
/*!40000 ALTER TABLE `eg_meter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_meter_log`
--

LOCK TABLES `eg_meter_log` WRITE;
/*!40000 ALTER TABLE `eg_meter_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_meter_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_pole`
--

LOCK TABLES `eg_pole` WRITE;
/*!40000 ALTER TABLE `eg_pole` DISABLE KEYS */;
INSERT INTO `eg_pole` VALUES (1,1,'pole1','pole001',113.109,23.0192,'2020-10-16 19:16:23','2020-10-16 19:16:23','2020-10-16 19:16:23',NULL);
/*!40000 ALTER TABLE `eg_pole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_rule`
--

LOCK TABLES `eg_rule` WRITE;
/*!40000 ALTER TABLE `eg_rule` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_rule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_rule_event`
--

LOCK TABLES `eg_rule_event` WRITE;
/*!40000 ALTER TABLE `eg_rule_event` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_rule_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_screen`
--

LOCK TABLES `eg_screen` WRITE;
/*!40000 ALTER TABLE `eg_screen` DISABLE KEYS */;
INSERT INTO `eg_screen` VALUES (1,1,1,'screen1',2,155,'4096x1536',6500,'scr-01',1,0,'192.168.1.23','A8-AA-40-00-0C-41','255.255.255.0','192.168.1.1','1','1','2020-12-15 07:06:20','华全','太龙400x600','2020-12-15 07:06:20',NULL,NULL,0,0);
/*!40000 ALTER TABLE `eg_screen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_screen_content`
--

LOCK TABLES `eg_screen_content` WRITE;
/*!40000 ALTER TABLE `eg_screen_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_screen_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_screen_log`
--

LOCK TABLES `eg_screen_log` WRITE;
/*!40000 ALTER TABLE `eg_screen_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_screen_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_screen_plan`
--

LOCK TABLES `eg_screen_plan` WRITE;
/*!40000 ALTER TABLE `eg_screen_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_screen_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_server`
--

LOCK TABLES `eg_server` WRITE;
/*!40000 ALTER TABLE `eg_server` DISABLE KEYS */;
INSERT INTO `eg_server` VALUES (1,1,1,'server1','serv-01',0,NULL,'192.168.192.32','C4-00-AD-58-50-3D',58080,'255.255.255.0','192.168.192.1','1','1','2020-10-16 19:16:23','华全','2020-10-16 19:16:23','2020-10-16 19:16:23',NULL);
/*!40000 ALTER TABLE `eg_server` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_weather`
--

LOCK TABLES `eg_weather` WRITE;
/*!40000 ALTER TABLE `eg_weather` DISABLE KEYS */;
INSERT INTO `eg_weather` VALUES (1,1,1,'weather1','wea-01',0,NULL,'192.168.1.7',20108,'9C-A5-25-A1-50-EA','255.255.255.0','192.168.2.1','1','1','2020-11-17 09:26:23','华全','FRT FWS500','2020-11-17 09:26:23','2020-11-17 09:26:23',NULL,'Hg123_123');
/*!40000 ALTER TABLE `eg_weather` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_weather_log`
--

LOCK TABLES `eg_weather_log` WRITE;
/*!40000 ALTER TABLE `eg_weather_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_weather_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_wifi`
--

LOCK TABLES `eg_wifi` WRITE;
/*!40000 ALTER TABLE `eg_wifi` DISABLE KEYS */;
INSERT INTO `eg_wifi` VALUES (1,1,1,'wifi1','wf-01',1,1,'192.168.1.8','30-0D-9E-75-79-5C','255.255.255.0','192.168.1.1','1','1','2020-12-15 07:06:20','华全','RG-WS6108','2020-12-15 07:06:20','2020-12-15 07:06:20','2020-12-15 07:06:20',NULL,NULL);
/*!40000 ALTER TABLE `eg_wifi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_wifi_ap`
--

LOCK TABLES `eg_wifi_ap` WRITE;
/*!40000 ALTER TABLE `eg_wifi_ap` DISABLE KEYS */;
INSERT INTO `eg_wifi_ap` VALUES (1,1,1,'wifi-ap1','Wifi-AP-01',1,1,'192.168.1.19','80-05-88-BE-8F-DF','255.255.255.0','192.168.1.1','1','1','2021-03-14 04:12:12','华全','RG-AP630','2021-03-14 04:12:12','2021-03-14 04:12:12',NULL);
/*!40000 ALTER TABLE `eg_wifi_ap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `eg_wifi_log`
--

LOCK TABLES `eg_wifi_log` WRITE;
/*!40000 ALTER TABLE `eg_wifi_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `eg_wifi_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_area`
--

LOCK TABLES `tb_area` WRITE;
/*!40000 ALTER TABLE `tb_area` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_dictionary`
--

LOCK TABLES `tb_dictionary` WRITE;
/*!40000 ALTER TABLE `tb_dictionary` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_dictionary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_failure`
--

LOCK TABLES `tb_failure` WRITE;
/*!40000 ALTER TABLE `tb_failure` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_failure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_failure_log`
--

LOCK TABLES `tb_failure_log` WRITE;
/*!40000 ALTER TABLE `tb_failure_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_failure_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_group`
--

LOCK TABLES `tb_group` WRITE;
/*!40000 ALTER TABLE `tb_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_log`
--

LOCK TABLES `tb_log` WRITE;
/*!40000 ALTER TABLE `tb_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_media_file`
--

LOCK TABLES `tb_media_file` WRITE;
/*!40000 ALTER TABLE `tb_media_file` DISABLE KEYS */;
INSERT INTO `tb_media_file` VALUES (3,1,0,'28990971B9F59CF670C4E74A14D882B1.mp4','2021-04-28 07:56:37.140'),(4,1,2,'B5440E3EB00F17A274661497B863E733.jpg','2021-04-28 07:57:36.196'),(5,1,3,'E6E319C5A861548C2FBC6222CAFC1F27.jpg','2021-04-28 07:57:36.275'),(6,1,4,'207F26B33D392A65F7DB1D54D4919F08.txt','2021-04-28 07:57:36.382'),(7,2,0,'25B8D6769C6E08996C4EE2440410FBEC.mp4','2021-04-28 07:57:36.953'),(8,2,2,'D4A7AC2ACCE14334267FCF0CD5D84699.jpg','2021-04-28 07:57:38.579'),(9,2,3,'2D9857BDD5579F5D47AAC8F9CD3B0F9C.jpg','2021-04-28 07:57:38.651'),(10,3,0,'1AE01C0BE1DB7C084E14F5AD9A9827D4.mp4','2021-04-28 07:58:37.016'),(11,2,1,'B0D5CAF3B93421A62FE0E72DF99ABBA6.mp4','2021-04-28 07:58:40.515'),(12,3,2,'356DAAD1DC35A84666E7BF61981C6875.jpg','2021-04-28 07:59:09.099'),(13,3,3,'1E9B47EE3E407469B528C5A36C3FD6EB.jpg','2021-04-28 07:59:09.217'),(14,4,0,'E9B6BDEF9FBDC936FB5314598AD21CAD.mp4','2021-04-28 07:59:37.067'),(15,3,1,'9A13C5F934CF2CB34D5B60FC67CBBA36.mp4','2021-04-28 08:00:10.279'),(16,5,0,'D2DDA31FBF6C634C0BCC983EC46AE739.mp4','2021-04-28 08:00:37.139'),(17,4,2,'3D46E7C28318D287F9928D40300B6D98.jpg','2021-04-28 08:01:11.283'),(18,4,3,'58379C321FCB8C21BA70735463EFC408.jpg','2021-04-28 08:01:11.428'),(19,4,4,'9B68F44526714DFE2AF917BCCCD2E384.txt','2021-04-28 08:01:11.626'),(20,5,2,'4D82F2DE1C6D83D26FF7B1669446733E.jpg','2021-04-28 08:01:16.919'),(21,5,3,'84CB39A2D5A4F2AE6F91DD14827C60B2.jpg','2021-04-28 08:01:17.026'),(22,6,2,'429AE727374606EAA098269B2E4B211C.jpg','2021-04-28 08:01:28.722'),(23,6,3,'9E2AFB9532DEA210A44494FE7A4AD484.jpg','2021-04-28 08:01:28.868'),(24,6,4,'9BEDC9E1CDF7A62BF731817E930AEAA5.txt','2021-04-28 08:01:28.952'),(25,7,2,'E24C4521CAFDDD33F90E9967C9CEFEF1.jpg','2021-04-28 08:01:33.425'),(26,7,3,'EBE55EFBBA28C87944F20AC661289713.jpg','2021-04-28 08:01:33.533'),(27,6,0,'AF9DBBA9E929E7B0B12EE36A79EA3A8C.mp4','2021-04-28 08:01:37.185'),(28,8,2,'7FCF1C3492E071D62A2696F27C883239.jpg','2021-04-28 08:01:39.818'),(29,8,3,'C5F1EAC0A9EBC80C4A0FC01FFAEF3351.jpg','2021-04-28 08:01:39.934'),(30,8,4,'33CA4B7E832D061C8B6DBFB8687D7ADD.txt','2021-04-28 08:01:40.014'),(31,9,2,'F7606CA391942A57D0960006305402A5.jpg','2021-04-28 08:01:51.220'),(32,9,3,'969D41C08AE6BB07F621E4B66F97F9F5.jpg','2021-04-28 08:01:51.323'),(33,10,2,'8985A0378D3190E9FB7ED9B7783B8248.jpg','2021-04-28 08:01:52.993'),(34,10,3,'711FECFAC14FA2C173CA7F4CC136736B.jpg','2021-04-28 08:01:53.209'),(35,11,2,'CBC64B03E6D755920520D8235D16B623.jpg','2021-04-28 08:01:58.348'),(36,11,3,'604800759FEFE3CB29621CAC2F4ADCF8.jpg','2021-04-28 08:01:58.479'),(37,4,1,'5C52407B70BB5A04815C632EEE5AF54F.mp4','2021-04-28 08:02:12.985'),(38,5,1,'041B2CF90F94B00F594662F6889710A3.mp4','2021-04-28 08:02:18.632'),(39,6,1,'38D899BDAA9B338AAACD41603C8084D7.mp4','2021-04-28 08:02:30.317'),(40,7,0,'744B579D329576871CCE4AEDFACB54E4.mp4','2021-04-28 08:02:37.263'),(41,8,1,'C2D9DFD5B71636D09C799698475C4D8D.mp4','2021-04-28 08:02:41.009'),(42,9,1,'4137E4FF72DB52156B82EB83966F0C02.mp4','2021-04-28 08:02:52.662'),(43,10,1,'0F7532C21E90A98DFADC4A5E82220B15.mp4','2021-04-28 08:02:54.326');
/*!40000 ALTER TABLE `tb_media_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_menu`
--

LOCK TABLES `tb_menu` WRITE;
/*!40000 ALTER TABLE `tb_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_module_role`
--

LOCK TABLES `tb_module_role` WRITE;
/*!40000 ALTER TABLE `tb_module_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_module_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_org`
--

LOCK TABLES `tb_org` WRITE;
/*!40000 ALTER TABLE `tb_org` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_org` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_org_role`
--

LOCK TABLES `tb_org_role` WRITE;
/*!40000 ALTER TABLE `tb_org_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_org_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_permission`
--

LOCK TABLES `tb_permission` WRITE;
/*!40000 ALTER TABLE `tb_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_permission_module`
--

LOCK TABLES `tb_permission_module` WRITE;
/*!40000 ALTER TABLE `tb_permission_module` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_permission_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_role`
--

LOCK TABLES `tb_role` WRITE;
/*!40000 ALTER TABLE `tb_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_ticket`
--

LOCK TABLES `tb_ticket` WRITE;
/*!40000 ALTER TABLE `tb_ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_ticket_log`
--

LOCK TABLES `tb_ticket_log` WRITE;
/*!40000 ALTER TABLE `tb_ticket_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_ticket_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_unbox`
--

LOCK TABLES `tb_unbox` WRITE;
/*!40000 ALTER TABLE `tb_unbox` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_unbox` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_unbox_log`
--

LOCK TABLES `tb_unbox_log` WRITE;
/*!40000 ALTER TABLE `tb_unbox_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_unbox_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_user_login`
--

LOCK TABLES `tb_user_login` WRITE;
/*!40000 ALTER TABLE `tb_user_login` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_user_login` ENABLE KEYS */;
UNLOCK TABLES;

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

-- Dump completed on 2021-04-28 16:12:21

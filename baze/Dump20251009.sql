-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: sistem_audio_snimci
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `audiosnimak`
--

DROP TABLE IF EXISTS `audiosnimak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audiosnimak` (
  `idAudiosnimak` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  `trajanje` int NOT NULL,
  `datumPostavljanja` date NOT NULL,
  `vremePostavljanja` time NOT NULL,
  `vlasnik` int NOT NULL,
  PRIMARY KEY (`idAudiosnimak`),
  KEY `FK_vlasnik_audiosnimak_idx` (`vlasnik`),
  CONSTRAINT `FK_vlasnik_audiosnimak` FOREIGN KEY (`vlasnik`) REFERENCES `korisnik` (`idKorisnik`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audiosnimak`
--

LOCK TABLES `audiosnimak` WRITE;
/*!40000 ALTER TABLE `audiosnimak` DISABLE KEYS */;
INSERT INTO `audiosnimak` VALUES (1,'Starboy',228,'2016-05-05','23:00:05',2),(2,'Heartless',198,'2019-10-22','16:09:33',2),(3,'Timeless',345,'2024-11-11','20:20:29',2),(4,'Often',218,'2015-07-08','11:23:45',2),(5,'The Hills',234,'2015-12-23','14:46:34',2),(6,'After Hours',420,'2022-02-03','18:35:13',2),(7,'Low Life',316,'2016-05-24','21:45:32',2),(8,'Hurt you',340,'2017-04-02','00:12:34',2),(9,'Kavali',201,'2013-11-21','00:13:25',4),(10,'Rulet',420,'2015-03-04','02:16:28',4),(11,'Limun',365,'2017-08-08','06:15:35',4),(12,'Mrak',153,'2016-05-09','09:09:09',4),(13,'Hotel',254,'2015-10-18','06:09:00',4),(14,'Avantura',321,'2025-01-05','12:21:12',4),(15,'Diamonds',300,'2012-02-19','21:21:23',3),(16,'S&M',210,'2013-07-17','13:13:12',3),(17,'Only Girl',403,'2015-08-09','15:52:09',3),(18,'Tec-9',172,'2025-06-06','01:23:45',5),(19,'Numb',196,'2025-06-06','01:23:45',5),(20,'Babylon',205,'2025-06-06','01:23:45',6),(21,'U njoj',213,'2025-06-06','01:23:45',6),(22,'danika house ',159,'2025-02-02','03:03:03',10);
/*!40000 ALTER TABLE `audiosnimak` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kategorija`
--

DROP TABLE IF EXISTS `kategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategorija` (
  `idKategorija` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  PRIMARY KEY (`idKategorija`),
  UNIQUE KEY `naziv_UNIQUE` (`naziv`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategorija`
--

LOCK TABLES `kategorija` WRITE;
/*!40000 ALTER TABLE `kategorija` DISABLE KEYS */;
INSERT INTO `kategorija` VALUES (3,'folk'),(4,'house'),(8,'klasicna muzika'),(7,'nnnnn'),(1,'pop'),(5,'rap'),(9,'rep'),(6,'rnb'),(2,'rok');
/*!40000 ALTER TABLE `kategorija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kategorijesnimaka`
--

DROP TABLE IF EXISTS `kategorijesnimaka`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategorijesnimaka` (
  `snimak` int NOT NULL,
  `kategorija` int NOT NULL,
  PRIMARY KEY (`snimak`,`kategorija`),
  KEY `FK_kategorija_kategorijesnimaka_idx` (`kategorija`),
  CONSTRAINT `FK_kategorija_kategorijesnimaka` FOREIGN KEY (`kategorija`) REFERENCES `kategorija` (`idKategorija`) ON UPDATE CASCADE,
  CONSTRAINT `FK_snimak_kategorijesnimaka` FOREIGN KEY (`snimak`) REFERENCES `audiosnimak` (`idAudiosnimak`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategorijesnimaka`
--

LOCK TABLES `kategorijesnimaka` WRITE;
/*!40000 ALTER TABLE `kategorijesnimaka` DISABLE KEYS */;
INSERT INTO `kategorijesnimaka` VALUES (1,1),(8,1),(18,1),(1,2),(9,2),(11,2),(13,2),(19,2),(21,2),(1,3),(10,3),(14,3),(19,3),(20,3),(2,4),(3,4),(12,4),(16,4),(21,4),(4,5),(6,5),(7,5),(17,5),(5,6),(12,6),(15,6),(19,6),(22,9);
/*!40000 ALTER TABLE `kategorijesnimaka` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `idKorisnik` int NOT NULL AUTO_INCREMENT,
  `ime` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `godiste` int NOT NULL,
  `pol` varchar(45) NOT NULL,
  `mestoDolaska` int NOT NULL,
  PRIMARY KEY (`idKorisnik`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `FK_mestoDolaska_korisnik_idx` (`mestoDolaska`),
  CONSTRAINT `FK_mestoDolaska_korisnik` FOREIGN KEY (`mestoDolaska`) REFERENCES `mesto` (`idMesto`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES (1,'Milica','milica123@gmail.com',2003,'zenski',2),(2,'The Weeknd','theweeknd@gmail.com',1990,'muski',1),(3,'Rihanna','rihanna@gmail.com',1991,'zenski',3),(4,'Rasta','balkatongeng@gmail.com',1988,'muski',2),(5,'Buba Corelli','cevap@gmail.com',1984,'muski',5),(6,'Jala Brat','kogadjaledom@gmail.com',1984,'muski',5),(7,'Iva ','ivy@gmail.com',2003,'zenski',4),(8,'Djordje','djoka@gmail.com',2002,'muski',2),(9,'Katarina','keti@gmail.com',2004,'zenski',4),(10,'zizela','susumejakk@gmail.com',1969,'zenski',2);
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesto`
--

DROP TABLE IF EXISTS `mesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mesto` (
  `idMesto` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  PRIMARY KEY (`idMesto`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesto`
--

LOCK TABLES `mesto` WRITE;
/*!40000 ALTER TABLE `mesto` DISABLE KEYS */;
INSERT INTO `mesto` VALUES (1,'Toronto'),(2,'Beograd'),(3,'Los Andjeles'),(4,'Novi Sad'),(5,'London'),(6,'floptropica'),(7,'temisvar'),(8,'kikinda'),(9,'vrsac'),(10,'peking');
/*!40000 ALTER TABLE `mesto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ocena`
--

DROP TABLE IF EXISTS `ocena`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ocena` (
  `idOcena` int NOT NULL AUTO_INCREMENT,
  `datum` date NOT NULL,
  `vreme` time NOT NULL,
  `ocena` int NOT NULL,
  `ocenjivac` int NOT NULL,
  `ocenjenSnimak` int NOT NULL,
  PRIMARY KEY (`idOcena`),
  KEY `FK_ocenjivac_ocena_idx` (`ocenjivac`),
  KEY `FK_ocenjenSnimak_ocena_idx` (`ocenjenSnimak`),
  CONSTRAINT `FK_ocenjenSnimak_ocena` FOREIGN KEY (`ocenjenSnimak`) REFERENCES `audiosnimak` (`idAudiosnimak`) ON UPDATE CASCADE,
  CONSTRAINT `FK_ocenjivac_ocena` FOREIGN KEY (`ocenjivac`) REFERENCES `korisnik` (`idKorisnik`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ocena`
--

LOCK TABLES `ocena` WRITE;
/*!40000 ALTER TABLE `ocena` DISABLE KEYS */;
INSERT INTO `ocena` VALUES (1,'2021-12-12','00:01:00',5,3,10),(2,'2011-03-02','08:09:07',3,3,15),(3,'2022-09-09','12:13:14',5,1,6),(4,'2023-08-07','08:16:34',1,7,18),(5,'2024-10-15','21:21:00',5,8,3),(6,'2025-03-16','13:13:14',4,9,20),(7,'2025-08-08','03:05:01',2,1,22);
/*!40000 ALTER TABLE `ocena` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `omiljenisnimci`
--

DROP TABLE IF EXISTS `omiljenisnimci`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `omiljenisnimci` (
  `korisnik` int NOT NULL,
  `snimak` int NOT NULL,
  PRIMARY KEY (`korisnik`,`snimak`),
  KEY `FK_snimak_omiljenisnimci_idx` (`snimak`),
  CONSTRAINT `FK_korisnik_omiljenisnimci` FOREIGN KEY (`korisnik`) REFERENCES `korisnik` (`idKorisnik`) ON UPDATE CASCADE,
  CONSTRAINT `FK_snimak_omiljenisnimci` FOREIGN KEY (`snimak`) REFERENCES `audiosnimak` (`idAudiosnimak`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `omiljenisnimci`
--

LOCK TABLES `omiljenisnimci` WRITE;
/*!40000 ALTER TABLE `omiljenisnimci` DISABLE KEYS */;
INSERT INTO `omiljenisnimci` VALUES (7,1),(1,2),(1,3),(8,3),(1,4),(8,4),(7,5),(9,6),(9,7),(7,9),(1,10),(9,10),(7,11),(8,11),(9,12),(7,13),(9,14),(1,15),(8,15),(9,16),(1,18),(7,20),(8,20),(9,21),(10,21);
/*!40000 ALTER TABLE `omiljenisnimci` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paket`
--

DROP TABLE IF EXISTS `paket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paket` (
  `idPaket` int NOT NULL AUTO_INCREMENT,
  `trenutnaCena` int NOT NULL,
  `naziv` varchar(45) NOT NULL,
  PRIMARY KEY (`idPaket`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paket`
--

LOCK TABLES `paket` WRITE;
/*!40000 ALTER TABLE `paket` DISABLE KEYS */;
INSERT INTO `paket` VALUES (1,1989,'jabuka'),(2,2001,'ananas'),(3,3456,'tresnja'),(4,4200,'mango'),(5,5689,'paketic');
/*!40000 ALTER TABLE `paket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pretplata`
--

DROP TABLE IF EXISTS `pretplata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pretplata` (
  `idPretplata` int NOT NULL AUTO_INCREMENT,
  `datumPocetka` date NOT NULL,
  `vremePocetka` time NOT NULL,
  `cena` int NOT NULL,
  `pretplacenKorisnik` int NOT NULL,
  `paketPretplate` int NOT NULL,
  PRIMARY KEY (`idPretplata`),
  KEY `FK_pretplacenKorisnik_pretplata_idx` (`pretplacenKorisnik`),
  KEY `FK_paketPretplate_pretplata_idx` (`paketPretplate`),
  CONSTRAINT `FK_paketPretplate_pretplata` FOREIGN KEY (`paketPretplate`) REFERENCES `paket` (`idPaket`) ON UPDATE CASCADE,
  CONSTRAINT `FK_pretplacenKorisnik_pretplata` FOREIGN KEY (`pretplacenKorisnik`) REFERENCES `korisnik` (`idKorisnik`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pretplata`
--

LOCK TABLES `pretplata` WRITE;
/*!40000 ALTER TABLE `pretplata` DISABLE KEYS */;
INSERT INTO `pretplata` VALUES (1,'2025-01-02','01:01:01',1900,1,1),(2,'2025-02-04','02:02:02',1969,1,1),(3,'2025-03-06','03:03:03',2001,1,2),(4,'2025-02-01','04:04:04',3000,7,3),(5,'2025-03-03','05:05:05',3456,7,3),(6,'2025-03-03','05:05:05',4200,8,4),(7,'2025-03-02','06:06:06',1989,9,1),(8,'2025-09-09','03:02:00',4200,1,4);
/*!40000 ALTER TABLE `pretplata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slusa`
--

DROP TABLE IF EXISTS `slusa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slusa` (
  `idSlusa` int NOT NULL AUTO_INCREMENT,
  `datumPoc` date NOT NULL,
  `vremePoc` time NOT NULL,
  `pocetniSekund` int NOT NULL,
  `odslusanoSekundi` int NOT NULL,
  `slusalac` int NOT NULL,
  `pustenSnimak` int NOT NULL,
  PRIMARY KEY (`idSlusa`),
  KEY `FK_slusalac_slusa_idx` (`slusalac`),
  KEY `FK_pustenSnimak_slusa_idx` (`pustenSnimak`),
  CONSTRAINT `FK_pustenSnimak_slusa` FOREIGN KEY (`pustenSnimak`) REFERENCES `audiosnimak` (`idAudiosnimak`) ON UPDATE CASCADE,
  CONSTRAINT `FK_slusalac_slusa` FOREIGN KEY (`slusalac`) REFERENCES `korisnik` (`idKorisnik`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slusa`
--

LOCK TABLES `slusa` WRITE;
/*!40000 ALTER TABLE `slusa` DISABLE KEYS */;
INSERT INTO `slusa` VALUES (1,'2025-06-09','00:01:02',0,100,1,1),(2,'2025-06-09','01:23:34',1,102,7,2),(3,'2025-06-09','12:23:15',2,50,8,3),(4,'2025-06-09','13:25:01',0,30,9,4),(5,'2025-06-09','20:15:56',3,24,2,5),(6,'2025-02-03','05:05:05',0,30,10,22);
/*!40000 ALTER TABLE `slusa` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-09 13:53:56

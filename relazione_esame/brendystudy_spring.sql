-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: brendystudy_spring
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `appuntamento`
--

DROP TABLE IF EXISTS `appuntamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appuntamento` (
  `id_appuntamento` bigint NOT NULL AUTO_INCREMENT,
  `data_appuntamento` date NOT NULL,
  `descrizione` text,
  `modalita` enum('ONLINE','PRESENZA') NOT NULL,
  `ora_appuntamento` time NOT NULL,
  `stato` enum('ANNULLATO','ATTIVO') NOT NULL,
  `id_richiesta` bigint NOT NULL,
  PRIMARY KEY (`id_appuntamento`),
  UNIQUE KEY `uk_appuntamento_richiesta` (`id_richiesta`),
  CONSTRAINT `FK8hf0ue8juhiy9c2t2hx9xyy2w` FOREIGN KEY (`id_richiesta`) REFERENCES `richiesta_supporto` (`id_richiesta`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appuntamento`
--

LOCK TABLES `appuntamento` WRITE;
/*!40000 ALTER TABLE `appuntamento` DISABLE KEYS */;
INSERT INTO `appuntamento` VALUES (1,'2026-08-20','Lezione via Google Meet','ONLINE','15:00:00','ANNULLATO',1),(3,'2026-05-15','ci vediamo in aula 15','PRESENZA','15:00:00','ANNULLATO',2),(4,'2026-08-20','Ci vediamo in aula 5 per ripassare gli integrali.','PRESENZA','15:00:00','ANNULLATO',4),(6,'2026-09-15','CI VEDIAMO DAVANTI ALL AULA 2 DEL POLO SCIENTIFICO','PRESENZA','19:30:00','ATTIVO',8);
/*!40000 ALTER TABLE `appuntamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competenza`
--

DROP TABLE IF EXISTS `competenza`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `competenza` (
  `id_competenza` bigint NOT NULL AUTO_INCREMENT,
  `descrizione` text,
  `livello` enum('AVANZATO','BASE','INTERMEDIO') NOT NULL,
  `id_materia` bigint NOT NULL,
  `id_utente` bigint NOT NULL,
  PRIMARY KEY (`id_competenza`),
  UNIQUE KEY `uk_competenza_utente_materia` (`id_utente`,`id_materia`),
  KEY `FK9hyp83bmwh18kc6f0p7mnlebh` (`id_materia`),
  CONSTRAINT `FK9hyp83bmwh18kc6f0p7mnlebh` FOREIGN KEY (`id_materia`) REFERENCES `materia` (`id_materia`),
  CONSTRAINT `FKfuhctuq1aw7tqld83iwbofj9w` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id_utente`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competenza`
--

LOCK TABLES `competenza` WRITE;
/*!40000 ALTER TABLE `competenza` DISABLE KEYS */;
INSERT INTO `competenza` VALUES (2,'Analisi 1 e 2 Algebra','AVANZATO',1,1),(3,'Tutor di matematica','INTERMEDIO',1,2),(4,'GRANDEZZE FISICHE','AVANZATO',3,4),(5,'Analisi matematica e algebra','AVANZATO',1,5),(6,'Ho bisogno di aiuto con gli esercizi di fisica.','BASE',3,6),(11,'buona conoscenza di java e springboot disponibile per supporto agli studenti','AVANZATO',7,9),(13,'HTML CSS JAVASCRIPT','AVANZATO',8,10);
/*!40000 ALTER TABLE `competenza` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materia`
--

DROP TABLE IF EXISTS `materia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materia` (
  `id_materia` bigint NOT NULL AUTO_INCREMENT,
  `descrizione` text,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`id_materia`),
  UNIQUE KEY `UK327l6c9d2i5c00g1gexca04dx` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materia`
--

LOCK TABLES `materia` WRITE;
/*!40000 ALTER TABLE `materia` DISABLE KEYS */;
INSERT INTO `materia` VALUES (1,'Analisi matematica e algebra','Matematica'),(3,'Fisica generale','FISICA'),(7,'java springboot progettazione di applicazione web','Ingegneria del software'),(8,'Html Css Javascript e svillupo applicazione we','Sistemi web');
/*!40000 ALTER TABLE `materia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `richiesta_supporto`
--

DROP TABLE IF EXISTS `richiesta_supporto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `richiesta_supporto` (
  `id_richiesta` bigint NOT NULL AUTO_INCREMENT,
  `data_richiesta` datetime(6) NOT NULL,
  `messaggio` text NOT NULL,
  `stato` enum('ACCETTATA','COMPLETATA','IN_ATTESA','RIFIUTATA') NOT NULL,
  `id_materia` bigint NOT NULL,
  `id_richiedente` bigint NOT NULL,
  `id_tutor` bigint NOT NULL,
  PRIMARY KEY (`id_richiesta`),
  KEY `FK7pnu6bitlo5silww9bds8us0r` (`id_materia`),
  KEY `FK1i3vqgre1nv40btpuvovmchik` (`id_richiedente`),
  KEY `FKe8wmehpwf1kg26pw34bxre1db` (`id_tutor`),
  CONSTRAINT `FK1i3vqgre1nv40btpuvovmchik` FOREIGN KEY (`id_richiedente`) REFERENCES `utente` (`id_utente`),
  CONSTRAINT `FK7pnu6bitlo5silww9bds8us0r` FOREIGN KEY (`id_materia`) REFERENCES `materia` (`id_materia`),
  CONSTRAINT `FKe8wmehpwf1kg26pw34bxre1db` FOREIGN KEY (`id_tutor`) REFERENCES `utente` (`id_utente`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `richiesta_supporto`
--

LOCK TABLES `richiesta_supporto` WRITE;
/*!40000 ALTER TABLE `richiesta_supporto` DISABLE KEYS */;
INSERT INTO `richiesta_supporto` VALUES (1,'2026-08-11 00:23:50.894485','Ho bisogno di aiuto con gli integrali','ACCETTATA',1,2,1),(2,'2026-08-11 18:19:09.083755','Vorrei un aiuto con gli integrali.','ACCETTATA',1,1,2),(3,'2026-08-12 21:33:23.500755','fisica generale','ACCETTATA',3,1,4),(4,'2026-08-12 21:59:25.592855','Ho bisogno di aiuto con gli integrali.','ACCETTATA',1,6,5),(5,'2026-08-12 22:18:40.125660','Ho bisogno di aiuto con gli esercizi di fisica.','RIFIUTATA',3,5,6),(8,'2026-09-04 17:15:54.777424','HO BISOGNO DI AIUTO','ACCETTATA',7,10,9),(9,'2026-09-04 17:18:02.551184','HO BISOGNO DI AIUTO','RIFIUTATA',8,9,10);
/*!40000 ALTER TABLE `richiesta_supporto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `id_utente` bigint NOT NULL AUTO_INCREMENT,
  `cognome` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `ruolo` enum('ADMIN','STUDENTE') NOT NULL,
  `stato` enum('ATTIVO','BLOCCATO') NOT NULL,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`id_utente`),
  UNIQUE KEY `UKgxvq4mjswnupehxnp35vawmo2` (`email`),
  UNIQUE KEY `UK2vq82crxh3p7upassu0k1kmte` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES (1,'Rossi','mario.rossi@test.it','Mario','1234','STUDENTE','ATTIVO','mario'),(2,'Bianchi','giulia.bianchi@test.it','Giulia','1234','STUDENTE','ATTIVO','giulia'),(3,'BrendyStudy','admin@brendystudy.it','Admin','admin123','ADMIN','ATTIVO','admin'),(4,'KAMGA','yvansteve.fotsokamga@edu.unife.it','YVAN','1234','STUDENTE','ATTIVO','yvan'),(5,'VERDI','luca.verdi@brendystudy.it','Luca','1234','STUDENTE','ATTIVO','luca'),(6,'NERI','sara.neri@brendystudy','Sara','1234','STUDENTE','ATTIVO','sara'),(7,'BELLINI','lorenzob@brendystudy.it','Lorenzo','1234','STUDENTE','ATTIVO','lorenzob'),(8,'MORETTI','sofiam@brendystudy.it','Sofia','1234','STUDENTE','ATTIVO','sofiaM'),(9,'ROMANO','marcor@brendystudy.it','Marco','1234','STUDENTE','ATTIVO','marcor'),(10,'CONTI','elenac@brendystudy.it','Elena','1234','STUDENTE','ATTIVO','elenac');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-09-04 19:59:46

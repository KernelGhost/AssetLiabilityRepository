-- MariaDB dump 10.19  Distrib 10.6.4-MariaDB, for osx10.16 (arm64)
--
-- Host: localhost    Database: ALR_DB_BLANK
-- ------------------------------------------------------
-- Server version	10.6.4-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `CreditCard`
--

DROP TABLE IF EXISTS `CreditCard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CreditCard` (
  `Entity_ID` int(11) NOT NULL,
  `Card_Limit` decimal(34,2) NOT NULL,
  PRIMARY KEY (`Entity_ID`),
  CONSTRAINT `FK_CreditCard_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CreditCard`
--

LOCK TABLES `CreditCard` WRITE;
/*!40000 ALTER TABLE `CreditCard` DISABLE KEYS */;
/*!40000 ALTER TABLE `CreditCard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Domn`
--

DROP TABLE IF EXISTS `Domn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Domn` (
  `DomnId` int(11) NOT NULL,
  `DomnName` varchar(50) NOT NULL,
  `DomnValue` varchar(255) NOT NULL,
  KEY `DomnId` (`DomnId`),
  KEY `DomnName` (`DomnName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Domn`
--

LOCK TABLES `Domn` WRITE;
/*!40000 ALTER TABLE `Domn` DISABLE KEYS */;
INSERT INTO `Domn` VALUES (0,'EntityStatus','Current'),(0,'EntityType','Savings Account'),(0,'ServiceType','Direct Debit (PP, PDC etc.)'),(0,'ShareTxnType','Buy'),(0,'TxnCatg','INT'),(1,'EntityStatus','Closed'),(1,'EntityType','Credit Card'),(1,'ServiceType','Direct Credit (Salary, Rent, Dividend etc.)'),(1,'ShareTxnType','Sell'),(1,'TxnCatg','DIV'),(2,'EntityType','Loan Account'),(2,'ServiceType','Net Details (URL, UserId, Pwd etc.)'),(2,'ShareTxnType','Dividend'),(2,'TxnCatg','FEE'),(3,'EntityType','Term Deposit'),(3,'ServiceType','Telebank Details (Number, UserId, Pwd etc.)'),(3,'ShareTxnType','DRP'),(3,'TxnCatg','OTHER'),(4,'EntityType','Superannuation'),(4,'ServiceType','Card Details (Number, Expiry Date, PIN etc.)'),(4,'TxnCatg','PAYMENT'),(5,'EntityType','Property'),(5,'ServiceType','Other'),(5,'TxnCatg','CREDIT'),(6,'EntityType','Shares/Managed Funds'),(6,'TxnCatg','DEP'),(7,'EntityType','Insurance'),(7,'TxnCatg','POS'),(8,'TxnCatg','XFER'),(9,'TxnCatg','DEBIT'),(10,'TxnCatg','SRVCHG'),(11,'TxnCatg','ATM'),(12,'TxnCatg','CHECK'),(13,'TxnCatg','CASH'),(14,'TxnCatg','DIRECTDEP'),(15,'TxnCatg','DIRECTDEBIT'),(16,'TxnCatg','REPEATPMT'),(17,'TxnCatg','TAX');
/*!40000 ALTER TABLE `Domn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Entity`
--

DROP TABLE IF EXISTS `Entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Entity` (
  `Entity_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Entity_Start_Date` date NOT NULL,
  `Entity_End_Date` date DEFAULT NULL,
  `Entity_Status` bit(1) NOT NULL,
  `Entity_Number` varchar(255) NOT NULL,
  `Entity_Type` int(11) NOT NULL,
  `Institution_ID` int(11) NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `Comments` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Entity_ID`),
  KEY `FK_Entity_Institution` (`Institution_ID`),
  CONSTRAINT `FK_Entity_Institution` FOREIGN KEY (`Institution_ID`) REFERENCES `Institution` (`Institution_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Entity`
--

LOCK TABLES `Entity` WRITE;
/*!40000 ALTER TABLE `Entity` DISABLE KEYS */;
/*!40000 ALTER TABLE `Entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EntityHistory`
--

DROP TABLE IF EXISTS `EntityHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EntityHistory` (
  `Linked_Entity_ID` int(11) NOT NULL,
  `Entity_ID` int(11) NOT NULL,
  KEY `Entity_ID` (`Entity_ID`),
  KEY `Linked_Entity_ID` (`Linked_Entity_ID`),
  CONSTRAINT `FK_EntityHistory_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EntityHistory`
--

LOCK TABLES `EntityHistory` WRITE;
/*!40000 ALTER TABLE `EntityHistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `EntityHistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EntityReltdService`
--

DROP TABLE IF EXISTS `EntityReltdService`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EntityReltdService` (
  `Entity_ID` int(11) NOT NULL,
  `Service_ID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Service_ID`),
  KEY `FK_EntityReltdService_Entity` (`Entity_ID`),
  CONSTRAINT `FK_EntityReltdService_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`),
  CONSTRAINT `FK_EntityReltdService_Service` FOREIGN KEY (`Service_ID`) REFERENCES `Service` (`Service_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EntityReltdService`
--

LOCK TABLES `EntityReltdService` WRITE;
/*!40000 ALTER TABLE `EntityReltdService` DISABLE KEYS */;
/*!40000 ALTER TABLE `EntityReltdService` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EntityShrTxn`
--

DROP TABLE IF EXISTS `EntityShrTxn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EntityShrTxn` (
  `Date` date NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `Share_Price` decimal(34,2) NOT NULL,
  `Brokerage_Charges` decimal(34,2) NOT NULL,
  `Other_Charges` decimal(34,2) NOT NULL,
  `Dividend_Amount` decimal(34,2) NOT NULL,
  `Shr_Txn_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Entity_ID` int(11) NOT NULL,
  `Txn_SubType` int(11) NOT NULL,
  `Number_Shares` float NOT NULL,
  `Capital_Gains_Tax` decimal(34,2) NOT NULL,
  `Franking_Percentage` decimal(34,2) NOT NULL,
  PRIMARY KEY (`Shr_Txn_ID`,`Entity_ID`),
  KEY `FK_EntityShrTxn_Entity` (`Entity_ID`),
  CONSTRAINT `FK_EntityShrTxn_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EntityShrTxn`
--

LOCK TABLES `EntityShrTxn` WRITE;
/*!40000 ALTER TABLE `EntityShrTxn` DISABLE KEYS */;
/*!40000 ALTER TABLE `EntityShrTxn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EntityTxn`
--

DROP TABLE IF EXISTS `EntityTxn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EntityTxn` (
  `Txn_Date` date NOT NULL,
  `Txn_Description` varchar(255) DEFAULT NULL,
  `Txn_DR` decimal(34,2) NOT NULL,
  `Txn_CR` decimal(34,2) NOT NULL,
  `Txn_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Entity_ID` int(11) NOT NULL,
  `Txn_Catg` int(11) NOT NULL,
  PRIMARY KEY (`Txn_ID`,`Entity_ID`),
  KEY `FK_EntityTxn_Entity` (`Entity_ID`),
  CONSTRAINT `FK_EntityTxn_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EntityTxn`
--

LOCK TABLES `EntityTxn` WRITE;
/*!40000 ALTER TABLE `EntityTxn` DISABLE KEYS */;
/*!40000 ALTER TABLE `EntityTxn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Holder`
--

DROP TABLE IF EXISTS `Holder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Holder` (
  `Holder_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Holder_Name` varchar(255) NOT NULL,
  `Holder_DOB` date DEFAULT NULL,
  `Holder_TFN` int(11) DEFAULT NULL,
  PRIMARY KEY (`Holder_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Holder`
--

LOCK TABLES `Holder` WRITE;
/*!40000 ALTER TABLE `Holder` DISABLE KEYS */;
/*!40000 ALTER TABLE `Holder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `HolderEntity`
--

DROP TABLE IF EXISTS `HolderEntity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HolderEntity` (
  `Holder_ID` int(11) NOT NULL,
  `Entity_ID` int(11) NOT NULL,
  KEY `FK_HolderEntity_Holder` (`Holder_ID`),
  KEY `FK_HolderEntity_Entity` (`Entity_ID`),
  CONSTRAINT `FK_HolderEntity_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`),
  CONSTRAINT `FK_HolderEntity_Holder` FOREIGN KEY (`Holder_ID`) REFERENCES `Holder` (`Holder_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HolderEntity`
--

LOCK TABLES `HolderEntity` WRITE;
/*!40000 ALTER TABLE `HolderEntity` DISABLE KEYS */;
/*!40000 ALTER TABLE `HolderEntity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Institution`
--

DROP TABLE IF EXISTS `Institution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Institution` (
  `Institution_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Code` varchar(5) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `Comments` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Institution_ID`),
  KEY `Code` (`Code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Institution`
--

LOCK TABLES `Institution` WRITE;
/*!40000 ALTER TABLE `Institution` DISABLE KEYS */;
/*!40000 ALTER TABLE `Institution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Insurance`
--

DROP TABLE IF EXISTS `Insurance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Insurance` (
  `Insurance_Premium_PA` decimal(34,2) NOT NULL,
  `Entity_ID` int(11) NOT NULL,
  `Insured_Amount` decimal(34,2) NOT NULL,
  PRIMARY KEY (`Entity_ID`),
  CONSTRAINT `FK_Insurance_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Insurance`
--

LOCK TABLES `Insurance` WRITE;
/*!40000 ALTER TABLE `Insurance` DISABLE KEYS */;
/*!40000 ALTER TABLE `Insurance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Loan`
--

DROP TABLE IF EXISTS `Loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Loan` (
  `Loan_Amount` decimal(34,2) NOT NULL,
  `Entity_ID` int(11) NOT NULL,
  `Current_Value` decimal(34,2) NOT NULL,
  PRIMARY KEY (`Entity_ID`),
  CONSTRAINT `FK_Loan_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Loan`
--

LOCK TABLES `Loan` WRITE;
/*!40000 ALTER TABLE `Loan` DISABLE KEYS */;
/*!40000 ALTER TABLE `Loan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Property`
--

DROP TABLE IF EXISTS `Property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Property` (
  `Address` varchar(255) NOT NULL,
  `Purchase_Amount` decimal(34,2) NOT NULL,
  `Solicitors_Fees_Buy` decimal(34,2) NOT NULL,
  `Solicitors_Fees_Sell` decimal(34,2) NOT NULL,
  `Govt_Charges_Buy` decimal(34,2) NOT NULL,
  `Govt_Charges_Sell` decimal(34,2) NOT NULL,
  `Agent_Fees_Buy` decimal(34,2) NOT NULL,
  `Agent_Fees_Sell` decimal(34,2) NOT NULL,
  `Sold_Amount` decimal(34,2) NOT NULL,
  `Entity_ID` int(11) NOT NULL,
  `Capital_Gains_Tax` decimal(34,2) NOT NULL,
  `Current_Value` decimal(34,2) NOT NULL,
  PRIMARY KEY (`Entity_ID`),
  CONSTRAINT `FK_Property_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Property`
--

LOCK TABLES `Property` WRITE;
/*!40000 ALTER TABLE `Property` DISABLE KEYS */;
/*!40000 ALTER TABLE `Property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Savings`
--

DROP TABLE IF EXISTS `Savings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Savings` (
  `Entity_ID` int(11) NOT NULL,
  PRIMARY KEY (`Entity_ID`),
  CONSTRAINT `FK_Savings_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Savings`
--

LOCK TABLES `Savings` WRITE;
/*!40000 ALTER TABLE `Savings` DISABLE KEYS */;
/*!40000 ALTER TABLE `Savings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Service`
--

DROP TABLE IF EXISTS `Service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Service` (
  `Service_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Description` varchar(255) DEFAULT NULL,
  `Institution_ID` int(11) NOT NULL,
  `User_ID` varchar(255) DEFAULT NULL,
  `Pwd_PIN` varchar(255) DEFAULT NULL,
  `Contact` varchar(255) DEFAULT NULL,
  `Expiry_Date` date DEFAULT NULL,
  `Service_Type` int(11) NOT NULL,
  `Frequency` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Service_ID`),
  KEY `FK_Service_Institution` (`Institution_ID`),
  CONSTRAINT `FK_Service_Institution` FOREIGN KEY (`Institution_ID`) REFERENCES `Institution` (`Institution_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Service`
--

LOCK TABLES `Service` WRITE;
/*!40000 ALTER TABLE `Service` DISABLE KEYS */;
/*!40000 ALTER TABLE `Service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Shares`
--

DROP TABLE IF EXISTS `Shares`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Shares` (
  `Entity_ID` int(11) NOT NULL,
  `Current_Unit_Value` decimal(34,2) NOT NULL,
  PRIMARY KEY (`Entity_ID`),
  CONSTRAINT `FK_Shares_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Shares`
--

LOCK TABLES `Shares` WRITE;
/*!40000 ALTER TABLE `Shares` DISABLE KEYS */;
/*!40000 ALTER TABLE `Shares` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Superannuation`
--

DROP TABLE IF EXISTS `Superannuation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Superannuation` (
  `Entity_ID` int(11) NOT NULL,
  PRIMARY KEY (`Entity_ID`),
  CONSTRAINT `FK_Superannuation_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Superannuation`
--

LOCK TABLES `Superannuation` WRITE;
/*!40000 ALTER TABLE `Superannuation` DISABLE KEYS */;
/*!40000 ALTER TABLE `Superannuation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TermDeposit`
--

DROP TABLE IF EXISTS `TermDeposit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TermDeposit` (
  `Opening_Balance` decimal(34,2) NOT NULL,
  `Interest_Amount` decimal(34,2) NOT NULL,
  `Other_Charges` decimal(34,2) NOT NULL,
  `Bank_Fees` decimal(34,2) NOT NULL,
  `Entity_ID` int(11) NOT NULL,
  `Interest_Rate` decimal(34,2) NOT NULL,
  PRIMARY KEY (`Entity_ID`),
  CONSTRAINT `FK_TermDeposit_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TermDeposit`
--

LOCK TABLES `TermDeposit` WRITE;
/*!40000 ALTER TABLE `TermDeposit` DISABLE KEYS */;
/*!40000 ALTER TABLE `TermDeposit` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-05 15:26:06

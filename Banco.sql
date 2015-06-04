CREATE DATABASE  IF NOT EXISTS `helpdesk` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `helpdesk`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: helpdesk
-- ------------------------------------------------------
-- Server version	5.5.40

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
-- Table structure for table `chamado`
--

DROP TABLE IF EXISTS `chamado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chamado` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuario` int(11) NOT NULL,
  `problema` int(11) NOT NULL,
  `dataAbertura` datetime NOT NULL,
  `dataRealizacao` datetime NOT NULL,
  `descricao` mediumtext NOT NULL,
  `prioridade` char(1) NOT NULL,
  `operador` varchar(45) NOT NULL,
  `solucao` varchar(100) NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `duracao` time NOT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Chamado_Usuario1` (`usuario`),
  KEY `fk_Chamado_Problema1` (`problema`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chamado`
--

LOCK TABLES `chamado` WRITE;
/*!40000 ALTER TABLE `chamado` DISABLE KEYS */;
INSERT INTO `chamado` VALUES (1,1,1,'2014-12-18 00:00:00','2001-01-01 00:00:00','dasdasd','3','','','2','00:00:00','P'),(2,2,1,'2014-12-17 00:00:00','2014-12-17 11:15:26','sdasdasd','2','DANIEL','','1','00:00:00','P'),(3,3,1,'2014-12-15 00:00:00','2014-12-15 10:56:00','carla','1','PEDRO','dsadasd','2','01:00:00','F'),(4,2,1,'2014-12-15 00:00:00','2014-12-15 09:39:08','','1','DANIEL','Feito','1','01:50:00','F'),(5,1,1,'2014-12-15 00:00:00','2014-12-15 09:36:11','','1','DANIEL','Feito','1','01:30:00','F'),(6,1,1,'2014-12-17 00:00:00','2001-01-01 00:00:00','Teste de protocolo IP mal sucedido','2','PEDRO','','1','00:00:00','P');
/*!40000 ALTER TABLE `chamado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipamento`
--

DROP TABLE IF EXISTS `equipamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equipamento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(150) NOT NULL,
  `dataCompra` date DEFAULT NULL,
  `fornecedor` int(11) NOT NULL,
  `sistemaOP` int(11) NOT NULL,
  `office` int(11) NOT NULL DEFAULT '0',
  `ip` varchar(20) DEFAULT NULL,
  `marca` int(11) NOT NULL,
  `numeroSerie` varchar(100) DEFAULT NULL,
  `setor` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Equipamento_Fornecedor1` (`fornecedor`),
  KEY `fk_Equipamento_SistemaOP1` (`sistemaOP`),
  KEY `fk_Equipamento_Marca1` (`marca`),
  KEY `fk_Equipamento_Office1` (`office`),
  KEY `fk_Equipamento_Setor1` (`setor`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipamento`
--

LOCK TABLES `equipamento` WRITE;
/*!40000 ALTER TABLE `equipamento` DISABLE KEYS */;
INSERT INTO `equipamento` VALUES (2,'Notebook Lenovo','2013-12-02',1,3,2,'192.168.1.11',3,'123456789',2),(3,'Notebook Lenovo','2012-05-21',1,1,1,'192.168.1.117',1,'987654321',8),(4,'Computador All in One Positivo','2014-12-01',1,1,0,'192.168.1.110',1,'456987123',2),(5,'Notebook Lenovo','2014-11-03',1,1,4,'192.168.1.11',1,'6548421351',10);
/*!40000 ALTER TABLE `equipamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fornecedor`
--

DROP TABLE IF EXISTS `fornecedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fornecedor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornecedor`
--

LOCK TABLES `fornecedor` WRITE;
/*!40000 ALTER TABLE `fornecedor` DISABLE KEYS */;
INSERT INTO `fornecedor` VALUES (1,'Lenovo');
/*!40000 ALTER TABLE `fornecedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marca`
--

DROP TABLE IF EXISTS `marca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `marca` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marca`
--

LOCK TABLES `marca` WRITE;
/*!40000 ALTER TABLE `marca` DISABLE KEYS */;
INSERT INTO `marca` VALUES (1,'HP'),(2,'DELL'),(3,'Samsung'),(4,'Microsoft');
/*!40000 ALTER TABLE `marca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `office`
--

DROP TABLE IF EXISTS `office`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `office` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `office`
--

LOCK TABLES `office` WRITE;
/*!40000 ALTER TABLE `office` DISABLE KEYS */;
INSERT INTO `office` VALUES (1,'Office 2003'),(2,'Office 2007'),(3,'Office 2010'),(4,'Office 365');
/*!40000 ALTER TABLE `office` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problema`
--

DROP TABLE IF EXISTS `problema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problema` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problema`
--

LOCK TABLES `problema` WRITE;
/*!40000 ALTER TABLE `problema` DISABLE KEYS */;
INSERT INTO `problema` VALUES (1,'Rede');
/*!40000 ALTER TABLE `problema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `setor`
--

DROP TABLE IF EXISTS `setor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `setor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setor`
--

LOCK TABLES `setor` WRITE;
/*!40000 ALTER TABLE `setor` DISABLE KEYS */;
INSERT INTO `setor` VALUES (1,'VACINA'),(2,'TEKNODADOS'),(3,'SEGURANÇA DO TRABALHO'),(4,'RECEPÇAO ONCOLOGIA'),(5,'RECEPÇAO INTERNAÇAO'),(6,'RECEPÇAO EMERGENCIA'),(7,'RADIOTERAPIA'),(8,'PROSOFT'),(9,'PRONTO ATENDIMENTO'),(10,'PROCESSAMENTO DE DADOS'),(11,'POSTO COLETA MOGI MIRIM'),(12,'POLISSONOGRAFIA'),(13,'NUTRIÇAO'),(14,'MANUTENÇAO'),(15,'LITOTRIPSIA'),(16,'LABVITTA'),(17,'LABORATORIO'),(18,'INTERCAMBIO'),(19,'GERENCIA FINANCEIRA'),(20,'GERENCIA ADMINISTRATIVA'),(21,'FISCAL'),(22,'FINANCEIRO'),(23,'FATURAMENTO'),(24,'ENFERMAGEM'),(25,'ECOCARDIOGRAMA'),(26,'DIRETORIA'),(27,'DEPARTAMENTO PESSOAL'),(28,'CONTABILIDADE'),(29,'COMPRAS');
/*!40000 ALTER TABLE `setor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sistemaop`
--

DROP TABLE IF EXISTS `sistemaop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sistemaop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sistemaop`
--

LOCK TABLES `sistemaop` WRITE;
/*!40000 ALTER TABLE `sistemaop` DISABLE KEYS */;
INSERT INTO `sistemaop` VALUES (1,'Windows 7'),(2,'Windows XP'),(3,'Linux Debian');
/*!40000 ALTER TABLE `sistemaop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `teste`
--

DROP TABLE IF EXISTS `teste`;
/*!50001 DROP VIEW IF EXISTS `teste`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `teste` (
  `id` tinyint NOT NULL,
  `usuario` tinyint NOT NULL,
  `problema` tinyint NOT NULL,
  `dataAbertura` tinyint NOT NULL,
  `dataRealizacao` tinyint NOT NULL,
  `descricao` tinyint NOT NULL,
  `prioridade` tinyint NOT NULL,
  `operador` tinyint NOT NULL,
  `solucao` tinyint NOT NULL,
  `tipo` tinyint NOT NULL,
  `duracao` tinyint NOT NULL,
  `status` tinyint NOT NULL,
  `nome` tinyint NOT NULL,
  `setorNome` tinyint NOT NULL,
  `problemaNome` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `tipoequipamento`
--

DROP TABLE IF EXISTS `tipoequipamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipoequipamento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipoequipamento`
--

LOCK TABLES `tipoequipamento` WRITE;
/*!40000 ALTER TABLE `tipoequipamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipoequipamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `senha` varchar(65) NOT NULL,
  `nomeCompleto` varchar(100) NOT NULL,
  `perfil` char(1) NOT NULL,
  `setor` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  KEY `fk_Usuario_Setor1` (`setor`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'daniel','FE2592B42A727E977F055947385B709CC82B16B9A87F88C6ABF3900D65D0CDC3','Daniel Silva','1',1),(2,'Pedro','FE2592B42A727E977F055947385B709CC82B16B9A87F88C6ABF3900D65D0CDC3','Pedro Ramalho','3',1),(3,'Queiroz','FE2592B42A727E977F055947385B709CC82B16B9A87F88C6ABF3900D65D0CDC3','José Ap. Queiroz','2',2);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `teste`
--

/*!50001 DROP TABLE IF EXISTS `teste`*/;
/*!50001 DROP VIEW IF EXISTS `teste`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`producao`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `teste` AS select `c`.`id` AS `id`,`c`.`usuario` AS `usuario`,`c`.`problema` AS `problema`,`c`.`dataAbertura` AS `dataAbertura`,`c`.`dataRealizacao` AS `dataRealizacao`,`c`.`descricao` AS `descricao`,`c`.`prioridade` AS `prioridade`,`c`.`operador` AS `operador`,`c`.`solucao` AS `solucao`,`c`.`tipo` AS `tipo`,`c`.`duracao` AS `duracao`,`c`.`status` AS `status`,`u`.`nomeCompleto` AS `nome`,`s`.`descricao` AS `setorNome`,`p`.`descricao` AS `problemaNome` from (((`chamado` `c` join `usuario` `u`) join `setor` `s`) join `problema` `p`) where ((`c`.`problema` = `p`.`id`) and (`c`.`usuario` = `u`.`id`) and (`u`.`setor` = `s`.`id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-12-24  9:56:41

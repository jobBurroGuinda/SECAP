-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.7.16-log


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema dbsecap
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ dbsecap;
USE dbsecap;

--
-- Table structure for table `dbsecap`.`cprivilegio`
--

DROP TABLE IF EXISTS `cprivilegio`;
CREATE TABLE `cprivilegio` (
  `idP_prv` tinyint(2) NOT NULL,
  `nom_prv` char(1) NOT NULL,
  PRIMARY KEY (`idP_prv`),
  UNIQUE KEY `nom_prv_UNIQUE` (`nom_prv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=4;

--
-- Dumping data for table `dbsecap`.`cprivilegio`
--

/*!40000 ALTER TABLE `cprivilegio` DISABLE KEYS */;
INSERT INTO `cprivilegio` (`idP_prv`,`nom_prv`) VALUES 
 (2,'A'),
 (3,'E'),
 (1,'M'),
 (4,'R');
/*!40000 ALTER TABLE `cprivilegio` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`csexo`
--

DROP TABLE IF EXISTS `csexo`;
CREATE TABLE `csexo` (
  `idO_sex` tinyint(2) NOT NULL,
  `xso_sex` char(1) NOT NULL,
  PRIMARY KEY (`idO_sex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`csexo`
--

/*!40000 ALTER TABLE `csexo` DISABLE KEYS */;
INSERT INTO `csexo` (`idO_sex`,`xso_sex`) VALUES 
 (1,'M'),
 (2,'F');
/*!40000 ALTER TABLE `csexo` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`ctipoparto`
--

DROP TABLE IF EXISTS `ctipoparto`;
CREATE TABLE `ctipoparto` (
  `idT_pto` tinyint(4) NOT NULL,
  `tpr_pto` varchar(10) NOT NULL,
  PRIMARY KEY (`idT_pto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`ctipoparto`
--

/*!40000 ALTER TABLE `ctipoparto` DISABLE KEYS */;
INSERT INTO `ctipoparto` (`idT_pto`,`tpr_pto`) VALUES 
 (1,'vaginal'),
 (2,'cesaria'),
 (3,'agua'),
 (4,'prematuro'),
 (5,'inducido');
/*!40000 ALTER TABLE `ctipoparto` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`dantecedentemed`
--

DROP TABLE IF EXISTS `dantecedentemed`;
CREATE TABLE `dantecedentemed` (
  `idH_atm` mediumint(9) NOT NULL AUTO_INCREMENT,
  `enf_atm` varchar(60) DEFAULT NULL,
  `med_atm` varchar(60) DEFAULT NULL,
  `alg_atm` varchar(60) DEFAULT NULL,
  `idN_atm` mediumint(9) NOT NULL,
  `idL_atm` int(11) NOT NULL,
  `idF_atm` mediumint(9) NOT NULL,
  PRIMARY KEY (`idH_atm`,`idN_atm`,`idL_atm`,`idF_atm`),
  KEY `fk_dAntecedenteMed_dExpediente1_idx` (`idN_atm`,`idL_atm`,`idF_atm`),
  CONSTRAINT `fk_dAntecedenteMed_dExpediente1` FOREIGN KEY (`idN_atm`, `idL_atm`, `idF_atm`) REFERENCES `dexpediente` (`idN_exp`, `idL_exp`, `idF_exp`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dantecedentemed`
--

/*!40000 ALTER TABLE `dantecedentemed` DISABLE KEYS */;
INSERT INTO `dantecedentemed` (`idH_atm`,`enf_atm`,`med_atm`,`alg_atm`,`idN_atm`,`idL_atm`,`idF_atm`) VALUES 
 (1,'mj8j77m','u8jllÃ±8','y8nhi',1,1,1),
 (2,'mjujio','joliugyb','olÃ±iojui',2,2,2),
 (3,'mhyyvy','ukuilhil','cvrtrfvg',3,3,3),
 (4,'No','No','No',4,4,7);
/*!40000 ALTER TABLE `dantecedentemed` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`dantecedentemedped`
--

DROP TABLE IF EXISTS `dantecedentemedped`;
CREATE TABLE `dantecedentemedped` (
  `idK_atp` mediumint(9) NOT NULL AUTO_INCREMENT,
  `tmg_atp` varchar(45) DEFAULT NULL,
  `cpl_atp` varchar(60) DEFAULT NULL,
  `inc_atp` varchar(45) DEFAULT NULL,
  `idT_atp` tinyint(4) NOT NULL,
  `idH_atp` mediumint(9) NOT NULL,
  PRIMARY KEY (`idK_atp`,`idT_atp`),
  KEY `fk_AntecedenteMedPed_cTipoParto1_idx` (`idT_atp`),
  KEY `fk_dAntecedenteMedPed_dAntecedenteMed1_idx` (`idH_atp`),
  CONSTRAINT `fk_AntecedenteMedPed_cTipoParto1` FOREIGN KEY (`idT_atp`) REFERENCES `ctipoparto` (`idT_pto`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_dAntecedenteMedPed_dAntecedenteMed1` FOREIGN KEY (`idH_atp`) REFERENCES `dantecedentemed` (`idH_atm`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dantecedentemedped`
--

/*!40000 ALTER TABLE `dantecedentemedped` DISABLE KEYS */;
INSERT INTO `dantecedentemedped` (`idK_atp`,`tmg_atp`,`cpl_atp`,`inc_atp`,`idT_atp`,`idH_atp`) VALUES 
 (1,'mh787','8ujyhy','nnnnnnnnu8',3,1);
/*!40000 ALTER TABLE `dantecedentemedped` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`dantecedentesmedag`
--

DROP TABLE IF EXISTS `dantecedentesmedag`;
CREATE TABLE `dantecedentesmedag` (
  `idQ_aag` int(11) NOT NULL AUTO_INCREMENT,
  `taa_aag` varchar(45) DEFAULT NULL,
  `ngg_aag` varchar(45) DEFAULT NULL,
  `idH_aag` mediumint(9) NOT NULL,
  PRIMARY KEY (`idQ_aag`,`idH_aag`),
  KEY `fk_dAntecedentesMedAG_dAntecedenteMed1_idx` (`idH_aag`),
  CONSTRAINT `fk_dAntecedentesMedAG_dAntecedenteMed1` FOREIGN KEY (`idH_aag`) REFERENCES `dantecedentemed` (`idH_atm`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dantecedentesmedag`
--

/*!40000 ALTER TABLE `dantecedentesmedag` DISABLE KEYS */;
INSERT INTO `dantecedentesmedag` (`idQ_aag`,`taa_aag`,`ngg_aag`,`idH_aag`) VALUES 
 (1,'juk8huyu','ihbiui',2),
 (2,'mmmmu',',uykuyu',3),
 (3,'N/A','N/A',4);
/*!40000 ALTER TABLE `dantecedentesmedag` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`dcontacto`
--

DROP TABLE IF EXISTS `dcontacto`;
CREATE TABLE `dcontacto` (
  `idC_con` mediumint(9) NOT NULL AUTO_INCREMENT,
  `dom_con` varchar(60) NOT NULL,
  `tel_con` decimal(10,0) NOT NULL,
  `tel_con_2` decimal(10,0) DEFAULT NULL,
  `mal_con` varchar(30) NOT NULL,
  `fbk_con` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`idC_con`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dcontacto`
--

/*!40000 ALTER TABLE `dcontacto` DISABLE KEYS */;
INSERT INTO `dcontacto` (`idC_con`,`dom_con`,`tel_con`,`tel_con_2`,`mal_con`,`fbk_con`) VALUES 
 (1,'Salvatierra Gto.','4438769768','4444444444','ajo@saw','Ana Cortes'),
 (2,'Chiapas','4432540801','4444444444','alfstarmac@gmail.com','Lupis'),
 (3,'Lomas','4438769768','4444444444','a@llll',''),
 (6,'Guadalajara','4432540800','4431613116','salmuel@gmail.com','Samuel Cortés');
/*!40000 ALTER TABLE `dcontacto` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`ddiagnostico`
--

DROP TABLE IF EXISTS `ddiagnostico`;
CREATE TABLE `ddiagnostico` (
  `ddi_dia` mediumint(9) NOT NULL AUTO_INCREMENT,
  `foo_dia` text,
  `nog_dia` tinyint(4) DEFAULT NULL,
  `hra_dia` time DEFAULT NULL,
  `idM_dia` mediumint(9) NOT NULL,
  `idW_dia` mediumint(9) NOT NULL,
  `idZ_dia` mediumint(9) NOT NULL,
  PRIMARY KEY (`ddi_dia`,`idZ_dia`),
  KEY `fk_dDiagnostico_dOjoDerecho1_idx` (`idW_dia`),
  KEY `fk_dDiagnostico_dResumenMedico1_idx` (`idM_dia`),
  KEY `fk_dDiagnostico_dOjoIzquierdo1_idx` (`idZ_dia`),
  CONSTRAINT `fk_dDiagnostico_dOjoDerecho1` FOREIGN KEY (`idW_dia`) REFERENCES `dojoderecho` (`idW_ojd`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_dDiagnostico_dOjoIzquierdo1` FOREIGN KEY (`idZ_dia`) REFERENCES `dojoizquierdo` (`idZ_oji`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_dDiagnostico_dResumenMedico1` FOREIGN KEY (`idM_dia`) REFERENCES `dresumenclinico` (`idM_cln`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`ddiagnostico`
--

/*!40000 ALTER TABLE `ddiagnostico` DISABLE KEYS */;
INSERT INTO `ddiagnostico` (`ddi_dia`,`foo_dia`,`nog_dia`,`hra_dia`,`idM_dia`,`idW_dia`,`idZ_dia`) VALUES 
 (1,'Dosis pediatrica',3,'15:00:00',1,1,1),
 (2,'Medicina adulta',3,'09:50:00',2,2,2),
 (3,'Meditilina arcainosacosa',3,'13:00:00',3,3,3);
/*!40000 ALTER TABLE `ddiagnostico` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`dexpediente`
--

DROP TABLE IF EXISTS `dexpediente`;
CREATE TABLE `dexpediente` (
  `idN_exp` mediumint(9) NOT NULL AUTO_INCREMENT,
  `fol_exp` decimal(15,0) NOT NULL,
  `idL_exp` int(11) NOT NULL,
  `idF_exp` mediumint(9) NOT NULL,
  PRIMARY KEY (`idN_exp`,`idL_exp`,`idF_exp`),
  KEY `fk_dExpediente_mpaciente1_idx` (`idL_exp`),
  KEY `fk_dExpediente_fechaExpediente1_idx` (`idF_exp`),
  CONSTRAINT `fk_dExpediente_fechaExpediente1` FOREIGN KEY (`idF_exp`) REFERENCES `dfechaexpediente` (`idF_fxp`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_dExpediente_mpaciente1` FOREIGN KEY (`idL_exp`) REFERENCES `mpaciente` (`idL_pte`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dexpediente`
--

/*!40000 ALTER TABLE `dexpediente` DISABLE KEYS */;
INSERT INTO `dexpediente` (`idN_exp`,`fol_exp`,`idL_exp`,`idF_exp`) VALUES 
 (1,'2016991033',1,1),
 (2,'2016991035',2,2),
 (3,'2016991037',3,3),
 (4,'2017211352',4,7);
/*!40000 ALTER TABLE `dexpediente` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`dfechaexpediente`
--

DROP TABLE IF EXISTS `dfechaexpediente`;
CREATE TABLE `dfechaexpediente` (
  `idF_fxp` mediumint(9) NOT NULL AUTO_INCREMENT,
  `fex_fxp` datetime NOT NULL,
  `fac_fxp` datetime NOT NULL,
  PRIMARY KEY (`idF_fxp`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dfechaexpediente`
--

/*!40000 ALTER TABLE `dfechaexpediente` DISABLE KEYS */;
INSERT INTO `dfechaexpediente` (`idF_fxp`,`fex_fxp`,`fac_fxp`) VALUES 
 (1,'2016-09-09 10:33:55','2016-09-09 10:33:55'),
 (2,'2016-09-09 10:35:15','2016-09-09 10:35:15'),
 (3,'2016-09-09 10:37:33','2016-09-09 10:37:33'),
 (4,'2016-09-09 10:38:11','2016-09-09 10:38:11'),
 (5,'2016-09-09 10:39:11','2016-09-09 10:39:11'),
 (6,'2016-09-09 10:40:42','2016-09-09 10:40:42'),
 (7,'2017-02-01 13:52:55','2017-02-01 13:52:55');
/*!40000 ALTER TABLE `dfechaexpediente` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`dgraduacion`
--

DROP TABLE IF EXISTS `dgraduacion`;
CREATE TABLE `dgraduacion` (
  `dgr_grd` mediumint(9) NOT NULL AUTO_INCREMENT,
  `gra_grd_1` varchar(35) NOT NULL,
  `gra_grd_2` varchar(35) NOT NULL,
  `gra_grd_3` varchar(35) NOT NULL,
  `add_grd` varchar(35) DEFAULT NULL,
  PRIMARY KEY (`dgr_grd`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dgraduacion`
--

/*!40000 ALTER TABLE `dgraduacion` DISABLE KEYS */;
INSERT INTO `dgraduacion` (`dgr_grd`,`gra_grd_1`,`gra_grd_2`,`gra_grd_3`,`add_grd`) VALUES 
 (1,'gradPedia1','gradPedia2','gradPedia3',NULL),
 (2,'grad2 Pedia_1','grad2 Pedia_2','grad2 Pedia_3',NULL),
 (3,'GraduacionOD1 adulto','GraduacionOD2 adulto','GraduacionOD3 adulto','ADDod adulto'),
 (4,'GraduacionOI1 adulto','GraduacionOI2 adulto','GraduacionOI3 adulto','ADDoi adulto'),
 (5,'+32.2','-89','+23','+12'),
 (6,'+32.2','-89','+23','+12');
/*!40000 ALTER TABLE `dgraduacion` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`doftalmoscopiadirecta`
--

DROP TABLE IF EXISTS `doftalmoscopiadirecta`;
CREATE TABLE `doftalmoscopiadirecta` (
  `dof_ofd` mediumint(9) NOT NULL AUTO_INCREMENT,
  `med_ofd` varchar(45) NOT NULL,
  `epp_ofd` smallint(6) NOT NULL,
  `clp_ofd` varchar(35) NOT NULL,
  `vss_ofd` varchar(20) NOT NULL,
  `mcl_ofd` varchar(40) NOT NULL,
  `rtp_ofd` varchar(40) NOT NULL,
  `pio_ofd` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`dof_ofd`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`doftalmoscopiadirecta`
--

/*!40000 ALTER TABLE `doftalmoscopiadirecta` DISABLE KEYS */;
INSERT INTO `doftalmoscopiadirecta` (`dof_ofd`,`med_ofd`,`epp_ofd`,`clp_ofd`,`vss_ofd`,`mcl_ofd`,`rtp_ofd`,`pio_ofd`) VALUES 
 (1,'Medios pediatricos',23,'color pediatrico','Vasitos pediatri','macula pediatrica','otro dato pediatrico',NULL),
 (2,'Medios pedia2',23,'color pedia2','Vasitos pedia 2','macula pedia 2','rtp pedia 2',NULL),
 (3,'Medios adultos 1',36,'color adulto1','Vasitos adultos1','macula adulta1','otro dato adulto1',NULL),
 (4,'Medios adultos2',98,'Color adulto2','Vasitos adultos2','macula adulta 2','otro dato adulto2',NULL),
 (5,'Medios asignados',23,'marillento','Vasitos amarillos','ML 98+','JK 989',NULL),
 (6,'Medios asignados',23,'marillento','Vasitos amarillos','ML 98+','JK 989',NULL);
/*!40000 ALTER TABLE `doftalmoscopiadirecta` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`dojoderecho`
--

DROP TABLE IF EXISTS `dojoderecho`;
CREATE TABLE `dojoderecho` (
  `idW_ojd` mediumint(9) NOT NULL AUTO_INCREMENT,
  `dxr_ojd` varchar(45) NOT NULL,
  `dxo_ojd` varchar(45) NOT NULL,
  `ref_ojd` varchar(45) NOT NULL,
  `dgr_ojd` mediumint(9) NOT NULL,
  `dof_ojd` mediumint(9) NOT NULL,
  `idY_ojd` mediumint(9) NOT NULL,
  PRIMARY KEY (`idW_ojd`,`dgr_ojd`,`dof_ojd`,`idY_ojd`),
  KEY `fk_ojoDerecho_dGraduacion1_idx` (`dgr_ojd`),
  KEY `fk_dOjoDerecho_dOftalmoscopiaDirecta1_idx` (`dof_ojd`),
  KEY `fk_dOjoDerecho_dPruebasPreliminares1_idx` (`idY_ojd`),
  CONSTRAINT `fk_dOjoDerecho_dOftalmoscopiaDirecta1` FOREIGN KEY (`dof_ojd`) REFERENCES `doftalmoscopiadirecta` (`dof_ofd`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_dOjoDerecho_dPruebasPreliminares1` FOREIGN KEY (`idY_ojd`) REFERENCES `dpruebaspreliminares` (`idY_pru`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ojoDerecho_dGraduacion1` FOREIGN KEY (`dgr_ojd`) REFERENCES `dgraduacion` (`dgr_grd`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dojoderecho`
--

/*!40000 ALTER TABLE `dojoderecho` DISABLE KEYS */;
INSERT INTO `dojoderecho` (`idW_ojd`,`dxr_ojd`,`dxo_ojd`,`ref_ojd`,`dgr_ojd`,`dof_ojd`,`idY_ojd`) VALUES 
 (1,'dxrPedia','dxoPedia','refPedia',1,1,1),
 (2,'dxr1 adulto','dxo1 adulto','ref1 adulto',3,3,3),
 (3,'-89','+24','+89.2',5,5,5);
/*!40000 ALTER TABLE `dojoderecho` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`dojoizquierdo`
--

DROP TABLE IF EXISTS `dojoizquierdo`;
CREATE TABLE `dojoizquierdo` (
  `idZ_oji` mediumint(9) NOT NULL AUTO_INCREMENT,
  `dxr_oji` varchar(45) NOT NULL,
  `dxo_oji` varchar(45) NOT NULL,
  `ref_oji` varchar(45) NOT NULL,
  `dof_oji` mediumint(9) NOT NULL,
  `dgr_oji` mediumint(9) NOT NULL,
  `idY_oji` mediumint(9) NOT NULL,
  PRIMARY KEY (`idZ_oji`,`dof_oji`,`idY_oji`),
  KEY `fk_ojoIzquierdo_dOftalmoscopiaDirecta1_idx` (`dof_oji`),
  KEY `fk_ojoIzquierdo_dGraduacion1_idx` (`dgr_oji`),
  KEY `fk_ojoIzquierdo_dPruebasPreliminares1_idx` (`idY_oji`),
  CONSTRAINT `fk_ojoIzquierdo_dGraduacion1` FOREIGN KEY (`dgr_oji`) REFERENCES `dgraduacion` (`dgr_grd`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ojoIzquierdo_dOftalmoscopiaDirecta1` FOREIGN KEY (`dof_oji`) REFERENCES `doftalmoscopiadirecta` (`dof_ofd`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ojoIzquierdo_dPruebasPreliminares1` FOREIGN KEY (`idY_oji`) REFERENCES `dpruebaspreliminares` (`idY_pru`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dojoizquierdo`
--

/*!40000 ALTER TABLE `dojoizquierdo` DISABLE KEYS */;
INSERT INTO `dojoizquierdo` (`idZ_oji`,`dxr_oji`,`dxo_oji`,`ref_oji`,`dof_oji`,`dgr_oji`,`idY_oji`) VALUES 
 (1,'dxrPedia 2','dxoPedia 2','refPedia 2',2,2,2),
 (2,'dxr2 adulto','dxo2 adulto','ref2 adulto',4,4,4),
 (3,'-89','+24','+89.2',6,6,6);
/*!40000 ALTER TABLE `dojoizquierdo` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`doptometria`
--

DROP TABLE IF EXISTS `doptometria`;
CREATE TABLE `doptometria` (
  `idB_opt` mediumint(9) NOT NULL AUTO_INCREMENT,
  `rod_opt` varchar(20) NOT NULL,
  `roi_opt` varchar(20) NOT NULL,
  `rdd_opt` varchar(20) DEFAULT NULL,
  `edl_opt` smallint(6) NOT NULL,
  `mcn_opt` varchar(45) NOT NULL,
  `utr_opt` datetime NOT NULL,
  `idM_opt` mediumint(9) NOT NULL,
  PRIMARY KEY (`idB_opt`),
  KEY `fk_dOptometria_dResumenMedico1_idx` (`idM_opt`),
  CONSTRAINT `fk_dOptometria_dResumenMedico1` FOREIGN KEY (`idM_opt`) REFERENCES `dresumenclinico` (`idM_cln`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`doptometria`
--

/*!40000 ALTER TABLE `doptometria` DISABLE KEYS */;
INSERT INTO `doptometria` (`idB_opt`,`rod_opt`,`roi_opt`,`rdd_opt`,`edl_opt`,`mcn_opt`,`utr_opt`,`idM_opt`) VALUES 
 (1,'Graduacion pediaOD','Graduacion pediaOI',NULL,5,'Motivo consulta pediatr','2015-08-23 12:08:00',1),
 (2,'graduacionOD adulto','GraduacionOI adulto','ADD adulto',5,'motivo adulto','2011-03-14 15:10:00',2),
 (3,'+34.2 -34.2 +23.9','+14.2 -33.2 -43.9','+2 +45 -2.56',5,'Dolor de nalgas','2015-08-23 12:08:00',3);
/*!40000 ALTER TABLE `doptometria` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`dplanmanejo`
--

DROP TABLE IF EXISTS `dplanmanejo`;
CREATE TABLE `dplanmanejo` (
  `dpm_plm` mediumint(9) NOT NULL AUTO_INCREMENT,
  `pln_plm` longtext NOT NULL,
  `idM_plm` mediumint(9) NOT NULL,
  PRIMARY KEY (`dpm_plm`),
  KEY `fk_dPlanManejo_dResumenMedico1_idx` (`idM_plm`),
  CONSTRAINT `fk_dPlanManejo_dResumenMedico1` FOREIGN KEY (`idM_plm`) REFERENCES `dresumenclinico` (`idM_cln`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dplanmanejo`
--

/*!40000 ALTER TABLE `dplanmanejo` DISABLE KEYS */;
INSERT INTO `dplanmanejo` (`dpm_plm`,`pln_plm`,`idM_plm`) VALUES 
 (1,'El plan de manejo pediatrico',1),
 (2,'El plan de manejo adulto',2),
 (3,'El paciente necesita buena dosis de chanclitis aguda en ayunas',3);
/*!40000 ALTER TABLE `dplanmanejo` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`dpruebaspreliminares`
--

DROP TABLE IF EXISTS `dpruebaspreliminares`;
CREATE TABLE `dpruebaspreliminares` (
  `idY_pru` mediumint(9) NOT NULL AUTO_INCREMENT,
  `acv_pru` varchar(30) NOT NULL,
  `scc_pru` varchar(30) NOT NULL,
  `cvv_pru` varchar(30) NOT NULL,
  `aoo_pru` varchar(30) NOT NULL,
  `ccc_pru` varchar(45) NOT NULL,
  `pup_pru` varchar(45) NOT NULL,
  PRIMARY KEY (`idY_pru`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dpruebaspreliminares`
--

/*!40000 ALTER TABLE `dpruebaspreliminares` DISABLE KEYS */;
INSERT INTO `dpruebaspreliminares` (`idY_pru`,`acv_pru`,`scc_pru`,`cvv_pru`,`aoo_pru`,`ccc_pru`,`pup_pru`) VALUES 
 (1,'acvPediatrico','sccPediatrico','cvvPediatrico','aooPediatrico','cccPediatrico','pupPediatrico'),
 (2,'acvPedia 2','sccPedia 2','cvvPedia 2','aooPedia 2','cccPedia 2','pupPedia 2'),
 (3,'ACV1 adulto','SC1 adulto','CV1 adulto','AO1 adulto','CC1 adulto','PUP1 adulto'),
 (4,'acv2 adulto','sc2 adulto','cv2 adulto','ao2 adulto','cc2 adulto','pup2 adulto'),
 (5,'898u','89k89','m89u89','gt78','5g656','89m89'),
 (6,'f3f34','jh56h5he','ldjk893','ns4rs4i','kkfhk83','ske8r48j');
/*!40000 ALTER TABLE `dpruebaspreliminares` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`dprupregeriatrico`
--

DROP TABLE IF EXISTS `dprupregeriatrico`;
CREATE TABLE `dprupregeriatrico` (
  `dpg_ppg` mediumint(9) NOT NULL AUTO_INCREMENT,
  `boo_ppg` varchar(45) NOT NULL,
  `rja_ppg` smallint(6) NOT NULL,
  `rla_ppg` varchar(45) NOT NULL,
  `idY_ppg` mediumint(9) NOT NULL,
  PRIMARY KEY (`dpg_ppg`),
  KEY `fk_dPruPreGeriatrico_dPruebasPreliminares1_idx` (`idY_ppg`),
  CONSTRAINT `fk_dPruPreGeriatrico_dPruebasPreliminares1` FOREIGN KEY (`idY_ppg`) REFERENCES `dpruebaspreliminares` (`idY_pru`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dprupregeriatrico`
--

/*!40000 ALTER TABLE `dprupregeriatrico` DISABLE KEYS */;
INSERT INTO `dprupregeriatrico` (`dpg_ppg`,`boo_ppg`,`rja_ppg`,`rla_ppg`,`idY_ppg`) VALUES 
 (1,'grt55',75,'h565',5),
 (2,'sfr4 344',29,'h56h5',6);
/*!40000 ALTER TABLE `dprupregeriatrico` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`dprupreladulto`
--

DROP TABLE IF EXISTS `dprupreladulto`;
CREATE TABLE `dprupreladulto` (
  `idV_ppa` mediumint(9) NOT NULL AUTO_INCREMENT,
  `bmm_ppa` varchar(45) NOT NULL,
  `rja_ppa` smallint(6) NOT NULL,
  `lcw_ppa` varchar(45) NOT NULL,
  `idY_ppa` mediumint(9) NOT NULL,
  PRIMARY KEY (`idV_ppa`),
  KEY `fk_dPruPrelAdulto_dPruebasPreliminares1_idx` (`idY_ppa`),
  CONSTRAINT `fk_dPruPrelAdulto_dPruebasPreliminares1` FOREIGN KEY (`idY_ppa`) REFERENCES `dpruebaspreliminares` (`idY_pru`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dprupreladulto`
--

/*!40000 ALTER TABLE `dprupreladulto` DISABLE KEYS */;
INSERT INTO `dprupreladulto` (`idV_ppa`,`bmm_ppa`,`rja_ppa`,`lcw_ppa`,`idY_ppa`) VALUES 
 (1,'BMM1 adulto',39,'RJA1 adulto',3),
 (2,'bmm2 adulto',42,'lcw2 adulto',4);
/*!40000 ALTER TABLE `dprupreladulto` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`dpruprelpediatra`
--

DROP TABLE IF EXISTS `dpruprelpediatra`;
CREATE TABLE `dpruprelpediatra` (
  `idG_ppp` mediumint(9) NOT NULL AUTO_INCREMENT,
  `meo_ppp` varchar(45) NOT NULL,
  `hib_ppp` varchar(45) NOT NULL,
  `lcw_ppp` varchar(45) NOT NULL,
  `ctt_ppp` varchar(45) NOT NULL,
  `idY_ppp` mediumint(9) NOT NULL,
  PRIMARY KEY (`idG_ppp`,`idY_ppp`),
  KEY `fk_dPruPrelPediatra_dPruebasPreliminares1_idx` (`idY_ppp`),
  CONSTRAINT `fk_dPruPrelPediatra_dPruebasPreliminares1` FOREIGN KEY (`idY_ppp`) REFERENCES `dpruebaspreliminares` (`idY_pru`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dpruprelpediatra`
--

/*!40000 ALTER TABLE `dpruprelpediatra` DISABLE KEYS */;
INSERT INTO `dpruprelpediatra` (`idG_ppp`,`meo_ppp`,`hib_ppp`,`lcw_ppp`,`ctt_ppp`,`idY_ppp`) VALUES 
 (1,'meoPediatrico','hibPediatrico','lcwPediatrico','cttPediatrico',1),
 (2,'meo pedia 2','hib pedia 2','lcw pedia 2','ctt pedia 2',2);
/*!40000 ALTER TABLE `dpruprelpediatra` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`drecetamedica`
--

DROP TABLE IF EXISTS `drecetamedica`;
CREATE TABLE `drecetamedica` (
  `dRM_rme` mediumint(9) NOT NULL AUTO_INCREMENT,
  `nta_rme` text NOT NULL,
  `frt_rme` datetime NOT NULL,
  `idN_rme` mediumint(9) NOT NULL,
  `idL_rme` int(11) NOT NULL,
  `idE_rme` mediumint(9) NOT NULL,
  `idF_fxp` mediumint(9) NOT NULL,
  PRIMARY KEY (`dRM_rme`,`idN_rme`,`idL_rme`),
  KEY `fk_dRecetaMedica_dExpediente1_idx` (`idN_rme`,`idL_rme`),
  KEY `fk_dRecetaMedica_mespecialista1_idx` (`idE_rme`),
  KEY `fk_dRecetaMedica_dfechaExpediente1_idx` (`idF_fxp`),
  CONSTRAINT `fk_dRecetaMedica_dExpediente1` FOREIGN KEY (`idN_rme`, `idL_rme`) REFERENCES `dexpediente` (`idN_exp`, `idL_exp`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_dRecetaMedica_dfechaExpediente1` FOREIGN KEY (`idF_fxp`) REFERENCES `dfechaexpediente` (`idF_fxp`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_dRecetaMedica_mespecialista1` FOREIGN KEY (`idE_rme`) REFERENCES `mespecialista` (`idE_esp`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`drecetamedica`
--

/*!40000 ALTER TABLE `drecetamedica` DISABLE KEYS */;
/*!40000 ALTER TABLE `drecetamedica` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`dresumenclinico`
--

DROP TABLE IF EXISTS `dresumenclinico`;
CREATE TABLE `dresumenclinico` (
  `idM_cln` mediumint(9) NOT NULL AUTO_INCREMENT,
  `idE_cln` mediumint(9) NOT NULL,
  `idH_cln` mediumint(9) NOT NULL,
  `idN_cln` mediumint(9) NOT NULL,
  `idL_cln` int(11) NOT NULL,
  `idF_cln` mediumint(9) NOT NULL,
  PRIMARY KEY (`idM_cln`,`idF_cln`),
  KEY `fk_dResumenMedico_mespecialista1_idx` (`idE_cln`),
  KEY `fk_dResumenClinico_dAntecedenteMed1_idx` (`idH_cln`,`idN_cln`,`idL_cln`),
  KEY `fk_dResumenClinico_dfechaExpediente1_idx` (`idF_cln`),
  CONSTRAINT `fk_dResumenClinico_dAntecedenteMed1` FOREIGN KEY (`idH_cln`, `idN_cln`, `idL_cln`) REFERENCES `dantecedentemed` (`idH_atm`, `idN_atm`, `idL_atm`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_dResumenClinico_dfechaExpediente1` FOREIGN KEY (`idF_cln`) REFERENCES `dfechaexpediente` (`idF_fxp`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_dResumenMedico_mespecialista1` FOREIGN KEY (`idE_cln`) REFERENCES `mespecialista` (`idE_esp`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dresumenclinico`
--

/*!40000 ALTER TABLE `dresumenclinico` DISABLE KEYS */;
INSERT INTO `dresumenclinico` (`idM_cln`,`idE_cln`,`idH_cln`,`idN_cln`,`idL_cln`,`idF_cln`) VALUES 
 (1,1,1,1,1,4),
 (2,1,2,2,2,5),
 (3,1,3,3,3,6);
/*!40000 ALTER TABLE `dresumenclinico` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`madministrador`
--

DROP TABLE IF EXISTS `madministrador`;
CREATE TABLE `madministrador` (
  `idA_adm` smallint(4) NOT NULL AUTO_INCREMENT,
  `nom_adm` varchar(25) NOT NULL,
  `app_adm` varchar(15) NOT NULL,
  `apm_adm` varchar(15) NOT NULL,
  `idU_adm` mediumint(9) NOT NULL,
  PRIMARY KEY (`idA_adm`),
  KEY `fk_mAdministrador_mUsuario1_idx` (`idU_adm`),
  CONSTRAINT `fk_mAdministrador_mUsuario1` FOREIGN KEY (`idU_adm`) REFERENCES `musuario` (`idU_usu`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`madministrador`
--

/*!40000 ALTER TABLE `madministrador` DISABLE KEYS */;
/*!40000 ALTER TABLE `madministrador` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`mespecialista`
--

DROP TABLE IF EXISTS `mespecialista`;
CREATE TABLE `mespecialista` (
  `idE_esp` mediumint(9) NOT NULL AUTO_INCREMENT,
  `nom_esp` varchar(25) NOT NULL,
  `app_esp` varchar(15) NOT NULL,
  `apm_esp` varchar(15) NOT NULL,
  `idU_esp` mediumint(9) NOT NULL,
  PRIMARY KEY (`idE_esp`),
  KEY `fk_mEspecialista_mUsuario1_idx` (`idU_esp`),
  CONSTRAINT `fk_mEspecialista_mUsuario1` FOREIGN KEY (`idU_esp`) REFERENCES `musuario` (`idU_usu`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`mespecialista`
--

/*!40000 ALTER TABLE `mespecialista` DISABLE KEYS */;
INSERT INTO `mespecialista` (`idE_esp`,`nom_esp`,`app_esp`,`apm_esp`,`idU_esp`) VALUES 
 (1,'Yajaira','Montevideo','Jimenez',43);
/*!40000 ALTER TABLE `mespecialista` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`mmostrador`
--

DROP TABLE IF EXISTS `mmostrador`;
CREATE TABLE `mmostrador` (
  `idR_mos` mediumint(9) NOT NULL AUTO_INCREMENT,
  `nom_mos` varchar(25) NOT NULL,
  `app_mos` varchar(15) NOT NULL,
  `apm_mos` varchar(15) NOT NULL,
  `idU_mos` mediumint(9) NOT NULL,
  PRIMARY KEY (`idR_mos`),
  KEY `fk_mRecepcionista_mUsuario1_idx` (`idU_mos`),
  CONSTRAINT `fk_mRecepcionista_mUsuario1` FOREIGN KEY (`idU_mos`) REFERENCES `musuario` (`idU_usu`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`mmostrador`
--

/*!40000 ALTER TABLE `mmostrador` DISABLE KEYS */;
/*!40000 ALTER TABLE `mmostrador` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`mpaciente`
--

DROP TABLE IF EXISTS `mpaciente`;
CREATE TABLE `mpaciente` (
  `idL_pte` int(11) NOT NULL AUTO_INCREMENT,
  `nom_pte` varchar(25) NOT NULL,
  `app_pte` varchar(15) NOT NULL,
  `apm_pte` varchar(15) NOT NULL,
  `ocp_pte` varchar(65) NOT NULL,
  `fhn_pte` date NOT NULL,
  `idC_pte` mediumint(9) NOT NULL,
  `idO_pte` tinyint(2) NOT NULL,
  PRIMARY KEY (`idL_pte`),
  KEY `fk_mCliente_dContacto1_idx` (`idC_pte`),
  KEY `fk_mpaciente_cSexo1_idx` (`idO_pte`),
  CONSTRAINT `fk_mCliente_dContacto1` FOREIGN KEY (`idC_pte`) REFERENCES `dcontacto` (`idC_con`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_mpaciente_cSexo1` FOREIGN KEY (`idO_pte`) REFERENCES `csexo` (`idO_sex`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`mpaciente`
--

/*!40000 ALTER TABLE `mpaciente` DISABLE KEYS */;
INSERT INTO `mpaciente` (`idL_pte`,`nom_pte`,`app_pte`,`apm_pte`,`ocp_pte`,`fhn_pte`,`idC_pte`,`idO_pte`) VALUES 
 (1,'Ana','Cortes','Jimenez','Secundaria','2010-03-04',1,2),
 (2,'Lupita','Tuxtla','Gutierrez','Chef','1999-03-05',2,2),
 (3,'Gerardo','Salinas','Gutierrez','Carpintero','1947-04-03',3,1),
 (4,'Samuel','Cortés','Jimenez','Ing. en sistemas computacionales','1987-12-23',6,1);
/*!40000 ALTER TABLE `mpaciente` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`musuario`
--

DROP TABLE IF EXISTS `musuario`;
CREATE TABLE `musuario` (
  `idU_usu` mediumint(9) NOT NULL AUTO_INCREMENT,
  `nom_usu` varchar(10) NOT NULL,
  `psw_usu` varchar(45) NOT NULL,
  `idP_usu` tinyint(10) NOT NULL,
  PRIMARY KEY (`idU_usu`),
  UNIQUE KEY `nom_usu_UNIQUE` (`nom_usu`),
  KEY `fk_mUsuario_cPrivilegio_idx` (`idP_usu`),
  CONSTRAINT `fk_mUsuario_cPrivilegio` FOREIGN KEY (`idP_usu`) REFERENCES `cprivilegio` (`idP_prv`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`musuario`
--

/*!40000 ALTER TABLE `musuario` DISABLE KEYS */;
INSERT INTO `musuario` (`idU_usu`,`nom_usu`,`psw_usu`,`idP_usu`) VALUES 
 (1,'jobadmin','*169004AA73844B48600040D51982B90CEEC635A9',2),
 (43,'yajaira','*306C88B6C41D01008526CEA607AC027A4010C72D',3),
 (44,'job','*0B4520F3C5A9F12414170CF2E78ABD62A7A684BF',2);
/*!40000 ALTER TABLE `musuario` ENABLE KEYS */;


--
-- View structure for view `dbsecap`.`vdatoscontactopaciente`
--

DROP VIEW IF EXISTS `vdatoscontactopaciente`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `dbsecap`.`vdatoscontactopaciente` AS select concat(`l`.`nom_pte`,' ',`l`.`app_pte`,' ',`l`.`apm_pte`) AS `nombrePaciente`,((year(curdate()) - year(`l`.`fhn_pte`)) - (right(curdate(),5) < right(`l`.`fhn_pte`,5))) AS `edad`,`c`.`tel_con` AS `telefono`,`c`.`mal_con` AS `email`,`c`.`fbk_con` AS `facebook` from (`dbsecap`.`mpaciente` `l` join `dbsecap`.`dcontacto` `c` on((`l`.`idC_pte` = `c`.`idC_con`))) order by `l`.`nom_pte`;


--
-- View structure for view `dbsecap`.`verdatoscontactopararedes`
--

DROP VIEW IF EXISTS `verdatoscontactopararedes`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `dbsecap`.`verdatoscontactopararedes` AS select concat(`l`.`nom_pte`,' ',`l`.`app_pte`,' ',`l`.`apm_pte`) AS `nombrePaciente`,((year(curdate()) - year(`l`.`fhn_pte`)) - (right(curdate(),5) < right(`l`.`fhn_pte`,5))) AS `edad`,`c`.`tel_con` AS `telefono`,`c`.`mal_con` AS `email`,`c`.`fbk_con` AS `facebook` from (`dbsecap`.`mpaciente` `l` join `dbsecap`.`dcontacto` `c` on((`l`.`idC_pte` = `c`.`idC_con`))) order by `l`.`nom_pte`;


--
-- View structure for view `dbsecap`.`vexpedientesag`
--

DROP VIEW IF EXISTS `vexpedientesag`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `dbsecap`.`vexpedientesag` AS select `n`.`idN_exp` AS `idExpediente`,`n`.`fol_exp` AS `folio`,`f`.`idF_fxp` AS `idFechaExpediente`,`f`.`fex_fxp` AS `fechaExpedicion`,`f`.`fac_fxp` AS `fechaActualizacion`,`l`.`idL_pte` AS `idPaciente`,`l`.`nom_pte` AS `nomPaciente`,`l`.`app_pte` AS `apellidoPaterno`,`l`.`apm_pte` AS `apellidoMaterno`,`l`.`ocp_pte` AS `ocupacion`,`l`.`fhn_pte` AS `fechaNacimiento`,((year(curdate()) - year(`l`.`fhn_pte`)) - (right(curdate(),5) < right(`l`.`fhn_pte`,5))) AS `edad`,`l`.`idO_pte` AS `idSexo`,`c`.`idC_con` AS `idContacto`,`c`.`dom_con` AS `domicilio`,`c`.`tel_con` AS `telefono`,`c`.`tel_con_2` AS `telefono2`,`c`.`mal_con` AS `email`,`c`.`fbk_con` AS `facebook`,`h`.`idH_atm` AS `idAntecedenteMed`,`h`.`enf_atm` AS `enfermedad`,`h`.`med_atm` AS `medicamento`,`h`.`alg_atm` AS `alergia`,`q`.`idQ_aag` AS `idAntecedenteAG`,`q`.`taa_aag` AS `TA`,`q`.`ngg_aag` AS `NG` from (((((`dbsecap`.`mpaciente` `l` join `dbsecap`.`dcontacto` `c` on((`l`.`idC_pte` = `c`.`idC_con`))) join `dbsecap`.`dexpediente` `n` on((`n`.`idL_exp` = `l`.`idL_pte`))) join `dbsecap`.`dfechaexpediente` `f` on((`f`.`idF_fxp` = `n`.`idF_exp`))) join `dbsecap`.`dantecedentemed` `h` on((`n`.`idN_exp` = `h`.`idN_atm`))) join `dbsecap`.`dantecedentesmedag` `q` on((`q`.`idH_aag` = `h`.`idH_atm`))) order by `l`.`idL_pte`;


--
-- View structure for view `dbsecap`.`vexpedientespediatricos`
--

DROP VIEW IF EXISTS `vexpedientespediatricos`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `dbsecap`.`vexpedientespediatricos` AS select `n`.`idN_exp` AS `idExpediente`,`n`.`fol_exp` AS `folio`,`f`.`idF_fxp` AS `idFechaExpediente`,`f`.`fex_fxp` AS `fechaExpedicion`,`f`.`fac_fxp` AS `fechaActualizacion`,`l`.`idL_pte` AS `idPaciente`,`l`.`nom_pte` AS `nomPaciente`,`l`.`app_pte` AS `apellidoPaterno`,`l`.`apm_pte` AS `apellidoMaterno`,`l`.`ocp_pte` AS `ocupacion`,`l`.`fhn_pte` AS `fechaNacimiento`,((year(curdate()) - year(`l`.`fhn_pte`)) - (right(curdate(),5) < right(`l`.`fhn_pte`,5))) AS `edad`,`l`.`idO_pte` AS `idSexo`,`c`.`idC_con` AS `idContacto`,`c`.`dom_con` AS `domicilio`,`c`.`tel_con` AS `telefono`,`c`.`tel_con_2` AS `telefono2`,`c`.`mal_con` AS `email`,`c`.`fbk_con` AS `facebook`,`h`.`idH_atm` AS `idAntecedenteMed`,`h`.`enf_atm` AS `enfermedad`,`h`.`med_atm` AS `medicamento`,`h`.`alg_atm` AS `alergia`,`k`.`idK_atp` AS `idAntecedentePediatrico`,`k`.`tmg_atp` AS `tiempoGestacion`,`k`.`cpl_atp` AS `complicacion`,`k`.`inc_atp` AS `incubadora`,`k`.`idT_atp` AS `tipoParto` from (((((`dbsecap`.`mpaciente` `l` join `dbsecap`.`dcontacto` `c` on((`l`.`idC_pte` = `c`.`idC_con`))) join `dbsecap`.`dexpediente` `n` on((`n`.`idL_exp` = `l`.`idL_pte`))) join `dbsecap`.`dfechaexpediente` `f` on((`f`.`idF_fxp` = `n`.`idF_exp`))) join `dbsecap`.`dantecedentemed` `h` on((`n`.`idN_exp` = `h`.`idN_atm`))) join `dbsecap`.`dantecedentemedped` `k` on((`h`.`idH_atm` = `k`.`idH_atp`))) order by `l`.`idL_pte`;


--
-- View structure for view `dbsecap`.`vresumenesadulto`
--

DROP VIEW IF EXISTS `vresumenesadulto`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `dbsecap`.`vresumenesadulto` AS select `n`.`idN_exp` AS `idExpediente`,`n`.`fol_exp` AS `folio`,`m`.`idM_cln` AS `idRecumenClinico`,`l`.`idL_pte` AS `idPaciente`,`l`.`nom_pte` AS `nomPaciente`,`l`.`app_pte` AS `apePaternoPaciente`,`l`.`apm_pte` AS `apeMaternoPaciente`,`f`.`idF_fxp` AS `idFechasResumen`,`f`.`fex_fxp` AS `fechaExpResumen`,`f`.`fac_fxp` AS `fechaActResumen`,`b`.`idB_opt` AS `idOptometria`,`b`.`utr_opt` AS `fechaUltimaRevision`,`b`.`rod_opt` AS `rxOjoDerecho`,`b`.`roi_opt` AS `rxOjoIzquierdo`,`ojod`.`ref_ojd` AS `refraccionOD`,`ojoi`.`ref_oji` AS `refraccionOI`,`b`.`edl_opt` AS `edadDesdeQueUsaLentes`,`b`.`mcn_opt` AS `motivoConsulta`,`yod`.`idY_pru` AS `idPruebasPreliminaresOD`,`yod`.`acv_pru` AS `AVod`,`yod`.`scc_pru` AS `SCod`,`yod`.`cvv_pru` AS `CVod`,`yod`.`aoo_pru` AS `AOod`,`yod`.`ccc_pru` AS `CCod`,`yod`.`pup_pru` AS `PUPod`,`god`.`idV_ppa` AS `idPrueAdultoOD`,`god`.`bmm_ppa` AS `bicromOD`,`god`.`rja_ppa` AS `relojAstOD`,`god`.`lcw_ppa` AS `lucesWOD`,`yoi`.`idY_pru` AS `idPruebasPreliminaresOI`,`yoi`.`acv_pru` AS `AVoi`,`yoi`.`scc_pru` AS `SCoi`,`yoi`.`cvv_pru` AS `CVoi`,`yoi`.`aoo_pru` AS `AOoi`,`yoi`.`ccc_pru` AS `CCoi`,`yoi`.`pup_pru` AS `PUPoi`,`goi`.`idV_ppa` AS `idPrueAdultoOI`,`goi`.`bmm_ppa` AS `bicromOI`,`goi`.`rja_ppa` AS `relojAstOI`,`goi`.`lcw_ppa` AS `lucesWOI`,`odd`.`dof_ofd` AS `idOftalmoscopiaOD`,`odd`.`med_ofd` AS `mediosOD`,`odd`.`epp_ofd` AS `EPod`,`odd`.`clp_ofd` AS `colorpOD`,`odd`.`vss_ofd` AS `vasosOD`,`odd`.`mcl_ofd` AS `maculaOD`,`odd`.`rtp_ofd` AS `retinaPerifericaOD`,`odi`.`dof_ofd` AS `idOftalmoscopiaOI`,`odi`.`med_ofd` AS `mediosOI`,`odi`.`epp_ofd` AS `EPoi`,`odi`.`clp_ofd` AS `colorpOI`,`odi`.`vss_ofd` AS `vasosOI`,`odi`.`mcl_ofd` AS `maculaOI`,`odi`.`rtp_ofd` AS `retinaPerifericaOI`,`grod`.`dgr_grd` AS `idGraduacionOD`,`grod`.`gra_grd_1` AS `graduacion1OD`,`grod`.`gra_grd_2` AS `graduacion2OD`,`grod`.`gra_grd_3` AS `graduacion3OD`,`ojod`.`idW_ojd` AS `idOjoDerecho`,`ojod`.`dxr_ojd` AS `dxRefractivoOD`,`ojod`.`dxo_ojd` AS `dxOrganicoOD`,`groi`.`dgr_grd` AS `idGraduacionOI`,`groi`.`gra_grd_1` AS `graduacion1OI`,`groi`.`gra_grd_2` AS `graduacion2OI`,`groi`.`gra_grd_3` AS `graduacion3OI`,`ojoi`.`idZ_oji` AS `idOjoIzquierdo`,`ojoi`.`dxr_oji` AS `dxRefractivoOI`,`ojoi`.`dxo_oji` AS `dxOrganicoOI`,`diagn`.`ddi_dia` AS `idDiagnostico`,`diagn`.`foo_dia` AS `farmaco`,`diagn`.`nog_dia` AS `numGotas`,`diagn`.`hra_dia` AS `hora`,`dplanm`.`dpm_plm` AS `idPlanManejo`,`dplanm`.`pln_plm` AS `planManejo`,`esp`.`idE_esp` AS `idEspecialista`,concat(`esp`.`nom_esp`,' ',`esp`.`app_esp`,' ',`esp`.`apm_esp`) AS `nombreEspecialista` from (((((((((((((((((`dbsecap`.`dexpediente` `n` join `dbsecap`.`dresumenclinico` `m` on((`n`.`idN_exp` = `m`.`idN_cln`))) join `dbsecap`.`mpaciente` `l` on((`m`.`idL_cln` = `l`.`idL_pte`))) join `dbsecap`.`dfechaexpediente` `f` on((`f`.`idF_fxp` = `m`.`idF_cln`))) join `dbsecap`.`doptometria` `b` on((`m`.`idM_cln` = `b`.`idM_opt`))) join `dbsecap`.`ddiagnostico` `diagn` on((`diagn`.`idM_dia` = `m`.`idM_cln`))) join `dbsecap`.`dojoderecho` `ojod` on((`ojod`.`idW_ojd` = `diagn`.`ddi_dia`))) join `dbsecap`.`dojoizquierdo` `ojoi` on((`ojoi`.`idZ_oji` = `diagn`.`idZ_dia`))) join `dbsecap`.`dpruebaspreliminares` `yod` on((`yod`.`idY_pru` = `ojod`.`idY_ojd`))) join `dbsecap`.`dprupreladulto` `god` on((`god`.`idY_ppa` = `yod`.`idY_pru`))) join `dbsecap`.`dpruebaspreliminares` `yoi` on((`yoi`.`idY_pru` = `ojoi`.`idY_oji`))) join `dbsecap`.`dprupreladulto` `goi` on((`goi`.`idY_ppa` = `yoi`.`idY_pru`))) join `dbsecap`.`doftalmoscopiadirecta` `odd` on((`ojod`.`dof_ojd` = `odd`.`dof_ofd`))) join `dbsecap`.`doftalmoscopiadirecta` `odi` on((`ojoi`.`dof_oji` = `odi`.`dof_ofd`))) join `dbsecap`.`dgraduacion` `grod` on((`grod`.`dgr_grd` = `ojod`.`dgr_ojd`))) join `dbsecap`.`dgraduacion` `groi` on((`groi`.`dgr_grd` = `ojoi`.`dgr_oji`))) join `dbsecap`.`dplanmanejo` `dplanm` on((`dplanm`.`idM_plm` = `m`.`idM_cln`))) join `dbsecap`.`mespecialista` `esp` on((`esp`.`idE_esp` = `m`.`idE_cln`))) order by `m`.`idM_cln`;


--
-- View structure for view `dbsecap`.`vresumenesgeriatricos`
--

DROP VIEW IF EXISTS `vresumenesgeriatricos`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `dbsecap`.`vresumenesgeriatricos` AS select `n`.`idN_exp` AS `idExpediente`,`n`.`fol_exp` AS `folio`,`m`.`idM_cln` AS `idRecumenClinico`,`l`.`idL_pte` AS `idPaciente`,`l`.`nom_pte` AS `nomPaciente`,`l`.`app_pte` AS `apePaternoPaciente`,`l`.`apm_pte` AS `apeMaternoPaciente`,`f`.`idF_fxp` AS `idFechasResumen`,`f`.`fex_fxp` AS `fechaExpResumen`,`f`.`fac_fxp` AS `fechaActResumen`,`b`.`idB_opt` AS `idOptometria`,`b`.`utr_opt` AS `fechaUltimaRevision`,`b`.`rod_opt` AS `rxOjoDerecho`,`b`.`roi_opt` AS `rxOjoIzquierdo`,`ojod`.`ref_ojd` AS `refraccionOD`,`ojoi`.`ref_oji` AS `refraccionOI`,`b`.`edl_opt` AS `edadDesdeQueUsaLentes`,`b`.`mcn_opt` AS `motivoConsulta`,`yod`.`idY_pru` AS `idPruebasPreliminaresOD`,`yod`.`acv_pru` AS `AVod`,`yod`.`scc_pru` AS `SCod`,`yod`.`cvv_pru` AS `CVod`,`yod`.`aoo_pru` AS `AOod`,`yod`.`ccc_pru` AS `CCod`,`yod`.`pup_pru` AS `PUPod`,`god`.`dpg_ppg` AS `idPrueGeriatricoOD`,`god`.`boo_ppg` AS `bicroOD`,`god`.`rja_ppg` AS `relojAstOD`,`god`.`rla_ppg` AS `rejillaOD`,`yoi`.`idY_pru` AS `idPruebasPreliminaresOI`,`yoi`.`acv_pru` AS `AVoi`,`yoi`.`scc_pru` AS `SCoi`,`yoi`.`cvv_pru` AS `CVoi`,`yoi`.`aoo_pru` AS `AOoi`,`yoi`.`ccc_pru` AS `CCoi`,`yoi`.`pup_pru` AS `PUPoi`,`goi`.`dpg_ppg` AS `idPrueGeriatricoOI`,`goi`.`boo_ppg` AS `bicroOI`,`goi`.`rja_ppg` AS `relojAstOI`,`goi`.`rla_ppg` AS `rejillaOI`,`odd`.`dof_ofd` AS `idOftalmoscopiaOD`,`odd`.`med_ofd` AS `mediosOD`,`odd`.`epp_ofd` AS `EPod`,`odd`.`clp_ofd` AS `colorpOD`,`odd`.`vss_ofd` AS `vasosOD`,`odd`.`mcl_ofd` AS `maculaOD`,`odd`.`rtp_ofd` AS `retinaPerifericaOD`,`odi`.`dof_ofd` AS `idOftalmoscopiaOI`,`odi`.`med_ofd` AS `mediosOI`,`odi`.`epp_ofd` AS `EPoi`,`odi`.`clp_ofd` AS `colorpOI`,`odi`.`vss_ofd` AS `vasosOI`,`odi`.`mcl_ofd` AS `maculaOI`,`odi`.`rtp_ofd` AS `retinaPerifericaOI`,`grod`.`dgr_grd` AS `idGraduacionOD`,`grod`.`gra_grd_1` AS `graduacion1OD`,`grod`.`gra_grd_2` AS `graduacion2OD`,`grod`.`gra_grd_3` AS `graduacion3OD`,`ojod`.`idW_ojd` AS `idOjoDerecho`,`ojod`.`dxr_ojd` AS `dxRefractivoOD`,`ojod`.`dxo_ojd` AS `dxOrganicoOD`,`groi`.`dgr_grd` AS `idGraduacionOI`,`groi`.`gra_grd_1` AS `graduacion1OI`,`groi`.`gra_grd_2` AS `graduacion2OI`,`groi`.`gra_grd_3` AS `graduacion3OI`,`ojoi`.`idZ_oji` AS `idOjoIzquierdo`,`ojoi`.`dxr_oji` AS `dxRefractivoOI`,`ojoi`.`dxo_oji` AS `dxOrganicoOI`,`diagn`.`ddi_dia` AS `idDiagnostico`,`diagn`.`foo_dia` AS `farmaco`,`diagn`.`nog_dia` AS `numGotas`,`diagn`.`hra_dia` AS `hora`,`dplanm`.`dpm_plm` AS `idPlanManejo`,`dplanm`.`pln_plm` AS `planManejo`,`esp`.`idE_esp` AS `idEspecialista`,concat(`esp`.`nom_esp`,' ',`esp`.`app_esp`,' ',`esp`.`apm_esp`) AS `nombreEspecialista` from (((((((((((((((((`dbsecap`.`dexpediente` `n` join `dbsecap`.`dresumenclinico` `m` on((`n`.`idN_exp` = `m`.`idN_cln`))) join `dbsecap`.`mpaciente` `l` on((`m`.`idL_cln` = `l`.`idL_pte`))) join `dbsecap`.`dfechaexpediente` `f` on((`f`.`idF_fxp` = `m`.`idF_cln`))) join `dbsecap`.`doptometria` `b` on((`m`.`idM_cln` = `b`.`idM_opt`))) join `dbsecap`.`ddiagnostico` `diagn` on((`diagn`.`idM_dia` = `m`.`idM_cln`))) join `dbsecap`.`dojoderecho` `ojod` on((`ojod`.`idW_ojd` = `diagn`.`ddi_dia`))) join `dbsecap`.`dojoizquierdo` `ojoi` on((`ojoi`.`idZ_oji` = `diagn`.`idZ_dia`))) join `dbsecap`.`dpruebaspreliminares` `yod` on((`yod`.`idY_pru` = `ojod`.`idY_ojd`))) join `dbsecap`.`dprupregeriatrico` `god` on((`god`.`idY_ppg` = `yod`.`idY_pru`))) join `dbsecap`.`dpruebaspreliminares` `yoi` on((`yoi`.`idY_pru` = `ojoi`.`idY_oji`))) join `dbsecap`.`dprupregeriatrico` `goi` on((`goi`.`idY_ppg` = `yoi`.`idY_pru`))) join `dbsecap`.`doftalmoscopiadirecta` `odd` on((`ojod`.`dof_ojd` = `odd`.`dof_ofd`))) join `dbsecap`.`doftalmoscopiadirecta` `odi` on((`ojoi`.`dof_oji` = `odi`.`dof_ofd`))) join `dbsecap`.`dgraduacion` `grod` on((`grod`.`dgr_grd` = `ojod`.`dgr_ojd`))) join `dbsecap`.`dgraduacion` `groi` on((`groi`.`dgr_grd` = `ojoi`.`dgr_oji`))) join `dbsecap`.`dplanmanejo` `dplanm` on((`dplanm`.`idM_plm` = `m`.`idM_cln`))) join `dbsecap`.`mespecialista` `esp` on((`esp`.`idE_esp` = `m`.`idE_cln`))) order by `m`.`idM_cln`;


--
-- View structure for view `dbsecap`.`vresumenespediatricos`
--

DROP VIEW IF EXISTS `vresumenespediatricos`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `dbsecap`.`vresumenespediatricos` AS select `n`.`idN_exp` AS `idExpediente`,`n`.`fol_exp` AS `folio`,`m`.`idM_cln` AS `idRecumenClinico`,`l`.`idL_pte` AS `idPaciente`,`l`.`nom_pte` AS `nomPaciente`,`l`.`app_pte` AS `apePaternoPaciente`,`l`.`apm_pte` AS `apeMaternoPaciente`,`f`.`idF_fxp` AS `idFechasResumen`,`f`.`fex_fxp` AS `fechaExpResumen`,`f`.`fac_fxp` AS `fechaActResumen`,`b`.`idB_opt` AS `idOptometria`,`b`.`utr_opt` AS `fechaUltimaRevision`,`b`.`rod_opt` AS `rxOjoDerecho`,`b`.`roi_opt` AS `rxOjoIzquierdo`,`ojod`.`ref_ojd` AS `refraccionOD`,`ojoi`.`ref_oji` AS `refraccionOI`,`b`.`edl_opt` AS `edadDesdeQueUsaLentes`,`b`.`mcn_opt` AS `motivoConsulta`,`yod`.`idY_pru` AS `idPruebasPreliminaresOD`,`yod`.`acv_pru` AS `AVod`,`yod`.`scc_pru` AS `SCod`,`yod`.`cvv_pru` AS `CVod`,`yod`.`aoo_pru` AS `AOod`,`yod`.`ccc_pru` AS `CCod`,`yod`.`pup_pru` AS `PUPod`,`god`.`idG_ppp` AS `idPruePediatrasOD`,`god`.`meo_ppp` AS `MEOod`,`god`.`hib_ppp` AS `HISBod`,`god`.`lcw_ppp` AS `LWod`,`god`.`ctt_ppp` AS `CTod`,`yoi`.`idY_pru` AS `idPruebasPreliminaresOI`,`yoi`.`acv_pru` AS `AVoi`,`yoi`.`scc_pru` AS `SCoi`,`yoi`.`cvv_pru` AS `CVoi`,`yoi`.`aoo_pru` AS `AOoi`,`yoi`.`ccc_pru` AS `CCoi`,`yoi`.`pup_pru` AS `PUPoi`,`goi`.`idG_ppp` AS `idPruePediatrasOI`,`goi`.`meo_ppp` AS `MEOoi`,`goi`.`hib_ppp` AS `HISBoi`,`goi`.`lcw_ppp` AS `LWoi`,`goi`.`ctt_ppp` AS `CToi`,`odd`.`dof_ofd` AS `idOftalmoscopiaOD`,`odd`.`med_ofd` AS `mediosOD`,`odd`.`epp_ofd` AS `EPod`,`odd`.`clp_ofd` AS `colorpOD`,`odd`.`vss_ofd` AS `vasosOD`,`odd`.`mcl_ofd` AS `maculaOD`,`odd`.`rtp_ofd` AS `retinaPerifericaOD`,`odi`.`dof_ofd` AS `idOftalmoscopiaOI`,`odi`.`med_ofd` AS `mediosOI`,`odi`.`epp_ofd` AS `EPoi`,`odi`.`clp_ofd` AS `colorpOI`,`odi`.`vss_ofd` AS `vasosOI`,`odi`.`mcl_ofd` AS `maculaOI`,`odi`.`rtp_ofd` AS `retinaPerifericaOI`,`grod`.`dgr_grd` AS `idGraduacionOD`,`grod`.`gra_grd_1` AS `graduacion1OD`,`grod`.`gra_grd_2` AS `graduacion2OD`,`grod`.`gra_grd_3` AS `graduacion3OD`,`ojod`.`idW_ojd` AS `idOjoDerecho`,`ojod`.`dxr_ojd` AS `dxRefractivoOD`,`ojod`.`dxo_ojd` AS `dxOrganicoOD`,`groi`.`dgr_grd` AS `idGraduacionOI`,`groi`.`gra_grd_1` AS `graduacion1OI`,`groi`.`gra_grd_2` AS `graduacion2OI`,`groi`.`gra_grd_3` AS `graduacion3OI`,`ojoi`.`idZ_oji` AS `idOjoIzquierdo`,`ojoi`.`dxr_oji` AS `dxRefractivoOI`,`ojoi`.`dxo_oji` AS `dxOrganicoOI`,`diagn`.`ddi_dia` AS `idDiagnostico`,`diagn`.`foo_dia` AS `farmaco`,`diagn`.`nog_dia` AS `numGotas`,`diagn`.`hra_dia` AS `hora`,`dplanm`.`dpm_plm` AS `idPlanManejo`,`dplanm`.`pln_plm` AS `planManejo`,`esp`.`idE_esp` AS `idEspecialista`,concat(`esp`.`nom_esp`,' ',`esp`.`app_esp`,' ',`esp`.`apm_esp`) AS `nombreEspecialista` from (((((((((((((((((`dbsecap`.`dexpediente` `n` join `dbsecap`.`dresumenclinico` `m` on((`n`.`idN_exp` = `m`.`idN_cln`))) join `dbsecap`.`mpaciente` `l` on((`m`.`idL_cln` = `l`.`idL_pte`))) join `dbsecap`.`dfechaexpediente` `f` on((`f`.`idF_fxp` = `m`.`idF_cln`))) join `dbsecap`.`doptometria` `b` on((`m`.`idM_cln` = `b`.`idM_opt`))) join `dbsecap`.`ddiagnostico` `diagn` on((`diagn`.`idM_dia` = `m`.`idM_cln`))) join `dbsecap`.`dojoderecho` `ojod` on((`ojod`.`idW_ojd` = `diagn`.`ddi_dia`))) join `dbsecap`.`dojoizquierdo` `ojoi` on((`ojoi`.`idZ_oji` = `diagn`.`idZ_dia`))) join `dbsecap`.`dpruebaspreliminares` `yod` on((`yod`.`idY_pru` = `ojod`.`idY_ojd`))) join `dbsecap`.`dpruprelpediatra` `god` on((`god`.`idY_ppp` = `yod`.`idY_pru`))) join `dbsecap`.`dpruebaspreliminares` `yoi` on((`yoi`.`idY_pru` = `ojoi`.`idY_oji`))) join `dbsecap`.`dpruprelpediatra` `goi` on((`goi`.`idY_ppp` = `yoi`.`idY_pru`))) join `dbsecap`.`doftalmoscopiadirecta` `odd` on((`ojod`.`dof_ojd` = `odd`.`dof_ofd`))) join `dbsecap`.`doftalmoscopiadirecta` `odi` on((`ojoi`.`dof_oji` = `odi`.`dof_ofd`))) join `dbsecap`.`dgraduacion` `grod` on((`grod`.`dgr_grd` = `ojod`.`dgr_ojd`))) join `dbsecap`.`dgraduacion` `groi` on((`groi`.`dgr_grd` = `ojoi`.`dgr_oji`))) join `dbsecap`.`dplanmanejo` `dplanm` on((`dplanm`.`idM_plm` = `m`.`idM_cln`))) join `dbsecap`.`mespecialista` `esp` on((`esp`.`idE_esp` = `m`.`idE_cln`))) order by `m`.`idM_cln`;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

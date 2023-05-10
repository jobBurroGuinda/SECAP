-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.7.13-log


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
  `enf_atm` varchar(45) DEFAULT NULL,
  `med_atm` varchar(45) DEFAULT NULL,
  `alg_atm` varchar(45) DEFAULT NULL,
  `idN_atm` mediumint(9) NOT NULL,
  `idL_atm` int(11) NOT NULL,
  `idF_atm` mediumint(9) NOT NULL,
  PRIMARY KEY (`idH_atm`,`idN_atm`,`idL_atm`,`idF_atm`),
  KEY `fk_dAntecedenteMed_dExpediente1_idx` (`idN_atm`,`idL_atm`,`idF_atm`),
  CONSTRAINT `fk_dAntecedenteMed_dExpediente1` FOREIGN KEY (`idN_atm`, `idL_atm`, `idF_atm`) REFERENCES `dexpediente` (`idN_exp`, `idL_exp`, `idF_exp`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dantecedentemed`
--

/*!40000 ALTER TABLE `dantecedentemed` DISABLE KEYS */;
/*!40000 ALTER TABLE `dantecedentemed` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`dantecedentemedped`
--

DROP TABLE IF EXISTS `dantecedentemedped`;
CREATE TABLE `dantecedentemedped` (
  `idK_atp` mediumint(9) NOT NULL AUTO_INCREMENT,
  `tmg_atp` varchar(10) DEFAULT NULL,
  `cpl_atp` varchar(45) DEFAULT NULL,
  `inc_atp` varchar(45) DEFAULT NULL,
  `idT_atp` tinyint(4) NOT NULL,
  `idH_atp` mediumint(9) NOT NULL,
  PRIMARY KEY (`idK_atp`,`idT_atp`),
  KEY `fk_AntecedenteMedPed_cTipoParto1_idx` (`idT_atp`),
  KEY `fk_dAntecedenteMedPed_dAntecedenteMed1_idx` (`idH_atp`),
  CONSTRAINT `fk_AntecedenteMedPed_cTipoParto1` FOREIGN KEY (`idT_atp`) REFERENCES `ctipoparto` (`idT_pto`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_dAntecedenteMedPed_dAntecedenteMed1` FOREIGN KEY (`idH_atp`) REFERENCES `dantecedentemed` (`idH_atm`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dantecedentemedped`
--

/*!40000 ALTER TABLE `dantecedentemedped` DISABLE KEYS */;
/*!40000 ALTER TABLE `dantecedentemedped` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`dantecedentesmedag`
--

DROP TABLE IF EXISTS `dantecedentesmedag`;
CREATE TABLE `dantecedentesmedag` (
  `idQ_aag` int(11) NOT NULL AUTO_INCREMENT,
  `taa_aag` varchar(20) DEFAULT NULL,
  `ngg_aag` varchar(30) DEFAULT NULL,
  `idH_aag` mediumint(9) NOT NULL,
  PRIMARY KEY (`idQ_aag`,`idH_aag`),
  KEY `fk_dAntecedentesMedAG_dAntecedenteMed1_idx` (`idH_aag`),
  CONSTRAINT `fk_dAntecedentesMedAG_dAntecedenteMed1` FOREIGN KEY (`idH_aag`) REFERENCES `dantecedentemed` (`idH_atm`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dantecedentesmedag`
--

/*!40000 ALTER TABLE `dantecedentesmedag` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dcontacto`
--

/*!40000 ALTER TABLE `dcontacto` DISABLE KEYS */;
/*!40000 ALTER TABLE `dcontacto` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`ddiagnostico`
--

DROP TABLE IF EXISTS `ddiagnostico`;
CREATE TABLE `ddiagnostico` (
  `ddi_dia` mediumint(9) NOT NULL AUTO_INCREMENT,
  `foo_dia` varchar(45) DEFAULT NULL,
  `nog_dia` tinyint(4) DEFAULT NULL,
  `hra_dia` time DEFAULT NULL,
  `idM_dia` mediumint(9) NOT NULL,
  `idW_dia` mediumint(9) NOT NULL,
  `idZ_dia` mediumint(9) NOT NULL,
  PRIMARY KEY (`ddi_dia`),
  KEY `fk_dDiagnostico_dOjoDerecho1_idx` (`idW_dia`),
  KEY `fk_dDiagnostico_ojoIzquierdo1_idx` (`idZ_dia`),
  KEY `fk_dDiagnostico_dResumenMedico1_idx` (`idM_dia`),
  CONSTRAINT `fk_dDiagnostico_dOjoDerecho1` FOREIGN KEY (`idW_dia`) REFERENCES `dojoderecho` (`idW_ojd`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_dDiagnostico_dResumenMedico1` FOREIGN KEY (`idM_dia`) REFERENCES `dresumenclinico` (`idM_cln`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_dDiagnostico_ojoIzquierdo1` FOREIGN KEY (`idZ_dia`) REFERENCES `dojoizquierdo` (`idZ_oji`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`ddiagnostico`
--

/*!40000 ALTER TABLE `ddiagnostico` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dexpediente`
--

/*!40000 ALTER TABLE `dexpediente` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dfechaexpediente`
--

/*!40000 ALTER TABLE `dfechaexpediente` DISABLE KEYS */;
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
  `add_grd` varchar(35) NOT NULL,
  PRIMARY KEY (`dgr_grd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dgraduacion`
--

/*!40000 ALTER TABLE `dgraduacion` DISABLE KEYS */;
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
  `pio_ofd` varchar(45) NOT NULL,
  PRIMARY KEY (`dof_ofd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`doftalmoscopiadirecta`
--

/*!40000 ALTER TABLE `doftalmoscopiadirecta` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dojoderecho`
--

/*!40000 ALTER TABLE `dojoderecho` DISABLE KEYS */;
/*!40000 ALTER TABLE `dojoderecho` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`dojoizquierdo`
--

DROP TABLE IF EXISTS `dojoizquierdo`;
CREATE TABLE `dojoizquierdo` (
  `idZ_oji` mediumint(9) NOT NULL AUTO_INCREMENT,
  `dxr_oji` varchar(45) NOT NULL,
  `dxo_oji` varchar(45) NOT NULL,
  `ref_oji` varchar(45) DEFAULT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dojoizquierdo`
--

/*!40000 ALTER TABLE `dojoizquierdo` DISABLE KEYS */;
/*!40000 ALTER TABLE `dojoizquierdo` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`doptometria`
--

DROP TABLE IF EXISTS `doptometria`;
CREATE TABLE `doptometria` (
  `idB_opt` mediumint(9) NOT NULL AUTO_INCREMENT,
  `edl_opt` smallint(6) NOT NULL,
  `mcn_opt` varchar(45) NOT NULL,
  `utr_opt` datetime NOT NULL,
  `idM_opt` mediumint(9) NOT NULL,
  `idW_opt` mediumint(9) NOT NULL,
  `idZ_opt` mediumint(9) NOT NULL,
  `dof_opt` mediumint(9) NOT NULL,
  PRIMARY KEY (`idB_opt`,`idW_opt`,`idZ_opt`,`dof_opt`),
  KEY `fk_dOptometria_dResumenMedico1_idx` (`idM_opt`),
  KEY `fk_dOptometria_dOjoDerecho1_idx` (`idW_opt`),
  KEY `fk_dOptometria_ojoIzquierdo1_idx` (`idZ_opt`,`dof_opt`),
  CONSTRAINT `fk_dOptometria_dOjoDerecho1` FOREIGN KEY (`idW_opt`) REFERENCES `dojoderecho` (`idW_ojd`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_dOptometria_dResumenMedico1` FOREIGN KEY (`idM_opt`) REFERENCES `dresumenclinico` (`idM_cln`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_dOptometria_ojoIzquierdo1` FOREIGN KEY (`idZ_opt`, `dof_opt`) REFERENCES `dojoizquierdo` (`idZ_oji`, `dof_oji`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`doptometria`
--

/*!40000 ALTER TABLE `doptometria` DISABLE KEYS */;
/*!40000 ALTER TABLE `doptometria` ENABLE KEYS */;


--
-- Table structure for table `dbsecap`.`dplanmanejo`
--

DROP TABLE IF EXISTS `dplanmanejo`;
CREATE TABLE `dplanmanejo` (
  `dpm_plm` mediumint(9) NOT NULL AUTO_INCREMENT,
  `pln_plm` text NOT NULL,
  `idM_plm` mediumint(9) NOT NULL,
  PRIMARY KEY (`dpm_plm`),
  KEY `fk_dPlanManejo_dResumenMedico1_idx` (`idM_plm`),
  CONSTRAINT `fk_dPlanManejo_dResumenMedico1` FOREIGN KEY (`idM_plm`) REFERENCES `dresumenclinico` (`idM_cln`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dplanmanejo`
--

/*!40000 ALTER TABLE `dplanmanejo` DISABLE KEYS */;
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
  `idG_pru` mediumint(9) NOT NULL,
  `idV_pru` mediumint(9) NOT NULL,
  `dpg_pru` mediumint(9) NOT NULL,
  PRIMARY KEY (`idY_pru`),
  KEY `fk_dPruebasPreliminares_dPruPrelPediatra1_idx` (`idG_pru`),
  KEY `fk_dPruebasPreliminares_dPruPrelAdulto1_idx` (`idV_pru`),
  KEY `fk_dPruebasPreliminares_dPruPreGeriatrico1_idx` (`dpg_pru`),
  CONSTRAINT `fk_dPruebasPreliminares_dPruPreGeriatrico1` FOREIGN KEY (`dpg_pru`) REFERENCES `dprupregeriatrico` (`dpg_ppg`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_dPruebasPreliminares_dPruPrelAdulto1` FOREIGN KEY (`idV_pru`) REFERENCES `dprupreladulto` (`idV_ppa`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_dPruebasPreliminares_dPruPrelPediatra1` FOREIGN KEY (`idG_pru`) REFERENCES `dpruprelpediatra` (`idG_ppp`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dpruebaspreliminares`
--

/*!40000 ALTER TABLE `dpruebaspreliminares` DISABLE KEYS */;
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
  PRIMARY KEY (`dpg_ppg`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dprupregeriatrico`
--

/*!40000 ALTER TABLE `dprupregeriatrico` DISABLE KEYS */;
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
  PRIMARY KEY (`idV_ppa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dprupreladulto`
--

/*!40000 ALTER TABLE `dprupreladulto` DISABLE KEYS */;
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
  PRIMARY KEY (`idG_ppp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dpruprelpediatra`
--

/*!40000 ALTER TABLE `dpruprelpediatra` DISABLE KEYS */;
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
  `idF_rme` mediumint(9) NOT NULL,
  `idE_rme` mediumint(9) NOT NULL,
  PRIMARY KEY (`dRM_rme`,`idN_rme`,`idL_rme`,`idF_rme`),
  KEY `fk_dRecetaMedica_dExpediente1_idx` (`idN_rme`,`idL_rme`,`idF_rme`),
  KEY `fk_dRecetaMedica_mespecialista1_idx` (`idE_rme`),
  CONSTRAINT `fk_dRecetaMedica_dExpediente1` FOREIGN KEY (`idN_rme`, `idL_rme`, `idF_rme`) REFERENCES `dexpediente` (`idN_exp`, `idL_exp`, `idF_exp`) ON DELETE CASCADE ON UPDATE CASCADE,
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
  PRIMARY KEY (`idM_cln`),
  KEY `fk_dResumenMedico_mespecialista1_idx` (`idE_cln`),
  KEY `fk_dResumenClinico_dAntecedenteMed1_idx` (`idH_cln`,`idN_cln`,`idL_cln`,`idF_cln`),
  CONSTRAINT `fk_dResumenClinico_dAntecedenteMed1` FOREIGN KEY (`idH_cln`, `idN_cln`, `idL_cln`, `idF_cln`) REFERENCES `dantecedentemed` (`idH_atm`, `idN_atm`, `idL_atm`, `idF_atm`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_dResumenMedico_mespecialista1` FOREIGN KEY (`idE_cln`) REFERENCES `mespecialista` (`idE_esp`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`dresumenclinico`
--

/*!40000 ALTER TABLE `dresumenclinico` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`mespecialista`
--

/*!40000 ALTER TABLE `mespecialista` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`mpaciente`
--

/*!40000 ALTER TABLE `mpaciente` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dbsecap`.`musuario`
--

/*!40000 ALTER TABLE `musuario` DISABLE KEYS */;
INSERT INTO `musuario` (`idU_usu`,`nom_usu`,`psw_usu`,`idP_usu`) VALUES 
 (1,'jobadmin','*169004AA73844B48600040D51982B90CEEC635A9',2);
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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

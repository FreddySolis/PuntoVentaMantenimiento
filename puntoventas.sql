-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.18 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para puntoventas
CREATE DATABASE IF NOT EXISTS `puntoventas` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `puntoventas`;

-- Volcando estructura para tabla puntoventas.datosproduct
CREATE TABLE IF NOT EXISTS `datosproduct` (
  `tipoProduct` varchar(50) DEFAULT NULL,
  `IDtipoProduct` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `folioproduct` varchar(50) DEFAULT NULL,
  `precio` float DEFAULT NULL,
  `nombreProveedor` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IDtipoProduct`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla puntoventas.datosproduct: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `datosproduct` DISABLE KEYS */;
INSERT INTO `datosproduct` (`tipoProduct`, `IDtipoProduct`, `nombre`, `folioproduct`, `precio`, `nombreProveedor`) VALUES
	('Cuaderno', 1, 'Scribe', '1234565', 12, 'Scribe'),
	('Papeleria', 2, 'Borrador', '123456', 2.9, 'Pelikan'),
	('Lapicero', 19, 'Pluma bic', '123497', 8, 'Norma');
/*!40000 ALTER TABLE `datosproduct` ENABLE KEYS */;

-- Volcando estructura para tabla puntoventas.flujocaja
CREATE TABLE IF NOT EXISTS `flujocaja` (
  `folio` varchar(50) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `montoEntrada` float DEFAULT NULL,
  `montoApagar` float DEFAULT NULL,
  `cambioAdar` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla puntoventas.flujocaja: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `flujocaja` DISABLE KEYS */;
/*!40000 ALTER TABLE `flujocaja` ENABLE KEYS */;

-- Volcando estructura para tabla puntoventas.listaproveedores
CREATE TABLE IF NOT EXISTS `listaproveedores` (
  `IDtipoProduct` int(11) NOT NULL AUTO_INCREMENT,
  `nombreProveedor` varchar(50) DEFAULT NULL,
  `nombreProduct` varchar(50) DEFAULT NULL,
  `folioProduct` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IDtipoProduct`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla puntoventas.listaproveedores: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `listaproveedores` DISABLE KEYS */;
INSERT INTO `listaproveedores` (`IDtipoProduct`, `nombreProveedor`, `nombreProduct`, `folioProduct`) VALUES
	(1, 'Norm', 'Lapicero rojo', '7373379'),
	(2, 'BIC', 'Lapiceros', '645345');
/*!40000 ALTER TABLE `listaproveedores` ENABLE KEYS */;

-- Volcando estructura para tabla puntoventas.listausuarios
CREATE TABLE IF NOT EXISTS `listausuarios` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `tipoUsuario` varchar(50) DEFAULT NULL,
  `apellidoPaterno` varchar(50) DEFAULT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `Correo` varchar(50) DEFAULT NULL,
  `contrasena` varchar(50) DEFAULT NULL,
  `contrasena2` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla puntoventas.listausuarios: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `listausuarios` DISABLE KEYS */;
INSERT INTO `listausuarios` (`ID`, `tipoUsuario`, `apellidoPaterno`, `nombre`, `Correo`, `contrasena`, `contrasena2`) VALUES
	(3, NULL, 'Alan Ernesto', 'Chanona', 'alan@htomail.com', 'jajak', '1111'),
	(4, NULL, 'Isaac', 'Bezares', 'isaac@hotmail.com', '1111', '1111'),
	(5, NULL, 'Ana', 'Navarro', 'ana@htomail.com', 'ananana', 'nananaa'),
	(6, NULL, 'f', 's', 'e', 'r', 'r');
/*!40000 ALTER TABLE `listausuarios` ENABLE KEYS */;

-- Volcando estructura para tabla puntoventas.login
CREATE TABLE IF NOT EXISTS `login` (
  `NombreUsuario` varchar(50) DEFAULT NULL,
  `Contrasenia` varchar(50) DEFAULT NULL,
  `tipoUsuario` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla puntoventas.login: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` (`NombreUsuario`, `Contrasenia`, `tipoUsuario`) VALUES
	('Diana', 'cacacaca', NULL);
/*!40000 ALTER TABLE `login` ENABLE KEYS */;

-- Volcando estructura para tabla puntoventas.reportedeventas
CREATE TABLE IF NOT EXISTS `reportedeventas` (
  `fecha` date DEFAULT NULL,
  `folioReporte` varchar(50) DEFAULT NULL,
  `cantidadArticulos` int(11) DEFAULT NULL,
  `folioProduct` varchar(50) DEFAULT NULL,
  `descripcionProduct` varchar(50) DEFAULT NULL,
  `precioC/U` float DEFAULT NULL,
  `importetotal` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla puntoventas.reportedeventas: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `reportedeventas` DISABLE KEYS */;
/*!40000 ALTER TABLE `reportedeventas` ENABLE KEYS */;

-- Volcando estructura para tabla puntoventas.ticket
CREATE TABLE IF NOT EXISTS `ticket` (
  `fecha` date DEFAULT NULL,
  `folioticket` varchar(50) DEFAULT NULL,
  `cantidadproduct` int(11) DEFAULT NULL,
  `descripcionproduct` varchar(50) DEFAULT NULL,
  `precioC/U` float DEFAULT NULL,
  `importe` float DEFAULT NULL,
  `totarticulos` int(11) DEFAULT NULL,
  `subtotal` float DEFAULT NULL,
  `totalIVA` float DEFAULT NULL,
  `totalApagar` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla puntoventas.ticket: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

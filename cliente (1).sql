-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-05-2025 a las 23:19:09
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `cliente`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `codiClie` int(11) NOT NULL,
  `ndniClie` varchar(8) NOT NULL,
  `appaClie` varchar(50) NOT NULL,
  `apmaClie` varchar(50) NOT NULL,
  `nombClie` varchar(50) NOT NULL,
  `fechNaciClie` date NOT NULL,
  `logiClie` varchar(100) NOT NULL,
  `passClie` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`codiClie`, `ndniClie`, `appaClie`, `apmaClie`, `nombClie`, `fechNaciClie`, `logiClie`, `passClie`) VALUES
(1, '72768888', 'Santamaria', 'AzaÃÂÃÂ±ero', 'Daniel', '2005-03-25', 'Daniex25', '12345'),
(2, '23456789', 'Santamaria', 'Sanb', 'Leinad', '2025-04-30', 'asd23', 'asdf'),
(3, '72768888', 'AzaÃ±ero', 'San', 'Victor', '2025-05-02', 'dsasf', '|123'),
(8, '12345678', 'Palma', 'Guzman', 'nBrya', '2025-05-01', 'Bryan123', '1234'),
(9, '12345432', 'Palma', 'Guzman', 'nBrya', '2025-05-17', 'Bryan1234567', '123456789'),
(12, '12345456', 'Palma', 'ZMAD', 'Jorge', '2025-04-30', 'Jorge1234', '1234'),
(22, '12345450', 'San', 'Aza', 'Victor', '2005-03-25', 'Danielll25', '123456');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`codiClie`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

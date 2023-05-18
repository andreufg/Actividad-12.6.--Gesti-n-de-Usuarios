-- phpMyAdmin SQL Dump
-- version 4.9.5deb2
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 18-05-2023 a las 11:28:56
-- Versión del servidor: 10.6.4-MariaDB-1:10.6.4+maria~focal
-- Versión de PHP: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `GestionUsuarios`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Users`
--

CREATE TABLE `Users` (
  `name` varchar(20) NOT NULL,
  `surname` varchar(40) NOT NULL,
  `dni` varchar(9) NOT NULL,
  `zipCode` varchar(20) NOT NULL,
  `mobilePhone` varchar(15) NOT NULL,
  `birthday` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `email` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `Users`
--

INSERT INTO `Users` (`name`, `surname`, `dni`, `zipCode`, `mobilePhone`, `birthday`, `password`, `email`) VALUES
('Batoi', 'surname 1 surname 2', '00000000A', '03801', '+34600000111', '1990-01-12', '!Aa23456789', 'test@test.es'),
('Informática', 'surname 2 surname 3', '00000000B', '03801', '+34600000222', '1990-02-12', '01234567!Aa', 'test1@test.es');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`dni`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

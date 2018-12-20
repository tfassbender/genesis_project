-- phpMyAdmin SQL Dump
-- version 4.6.6deb4
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Erstellungszeit: 20. Dez 2018 um 14:48
-- Server-Version: 10.1.23-MariaDB-9+deb9u1
-- PHP-Version: 7.0.30-0+deb9u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `genesis_project`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `alliance_const`
--

CREATE TABLE `alliance_const` (
  `id` int(11) NOT NULL,
  `used` tinyint(1) DEFAULT NULL,
  `min_planets` int(11) DEFAULT NULL,
  `min_planets_others` int(11) DEFAULT NULL,
  `min_buildings` int(11) DEFAULT NULL,
  `min_buildings_others` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `alliance_const`
--

INSERT INTO `alliance_const` (`id`, `used`, `min_planets`, `min_planets_others`, `min_buildings`, `min_buildings_others`) VALUES
(1, 1, 3, 1, 6, 2);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `attack_const`
--

CREATE TABLE `attack_const` (
  `id` int(11) NOT NULL,
  `used` tinyint(1) DEFAULT NULL,
  `probability_factor_private` float DEFAULT NULL,
  `turn_start_pirate` int(11) DEFAULT NULL,
  `turn_end_pirate` int(11) DEFAULT NULL,
  `player_turns_parasite_attack` int(11) DEFAULT NULL,
  `player_turns_parasite_attack_decrease` int(11) DEFAULT NULL,
  `turn_start_parasite_mid` int(11) DEFAULT NULL,
  `turn_start_parasite_end` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `attack_const`
--

INSERT INTO `attack_const` (`id`, `used`, `probability_factor_private`, `turn_start_pirate`, `turn_end_pirate`, `player_turns_parasite_attack`, `player_turns_parasite_attack_decrease`, `turn_start_parasite_mid`, `turn_start_parasite_end`) VALUES
(1, 1, 0.015, 2, 5, 15, 3, 3, 2);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `attack_values_const`
--

CREATE TABLE `attack_values_const` (
  `turn` int(11) NOT NULL,
  `attack_strength_pirate_base` int(11) DEFAULT NULL,
  `attack_strength_pirate_per_player` float DEFAULT NULL,
  `attack_strength_pirate_random` int(11) DEFAULT NULL,
  `attack_strength_parasite_mid_base` int(11) DEFAULT NULL,
  `attack_strength_parasite_mid_per_player` float DEFAULT NULL,
  `attack_strength_parasite_mid_random` int(11) DEFAULT NULL,
  `attack_strength_parasite_end_base` int(11) DEFAULT NULL,
  `attack_strength_parasite_end_per_player` float DEFAULT NULL,
  `attack_strength_parasite_end_random` int(11) DEFAULT NULL,
  `points_pirate_1` int(11) DEFAULT NULL,
  `points_pirate_2` int(11) DEFAULT NULL,
  `points_pirate_3` int(11) DEFAULT NULL,
  `points_parasite_mid_1` int(11) DEFAULT NULL,
  `points_parasite_mid_2` int(11) DEFAULT NULL,
  `points_parasite_mid_3` int(11) DEFAULT NULL,
  `points_parasite_end_1` int(11) DEFAULT NULL,
  `points_parasite_end_2` int(11) DEFAULT NULL,
  `points_parasite_end_3` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `attack_values_const`
--

INSERT INTO `attack_values_const` (`turn`, `attack_strength_pirate_base`, `attack_strength_pirate_per_player`, `attack_strength_pirate_random`, `attack_strength_parasite_mid_base`, `attack_strength_parasite_mid_per_player`, `attack_strength_parasite_mid_random`, `attack_strength_parasite_end_base`, `attack_strength_parasite_end_per_player`, `attack_strength_parasite_end_random`, `points_pirate_1`, `points_pirate_2`, `points_pirate_3`, `points_parasite_mid_1`, `points_parasite_mid_2`, `points_parasite_mid_3`, `points_parasite_end_1`, `points_parasite_end_2`, `points_parasite_end_3`) VALUES
(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(2, 1, 1, 3, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0),
(3, 1, 1, 3, 1, 1, 1, 3, 3, 0, 1, 0, 0, 1, 0, 0, 2, 1, 0),
(4, 2, 1.5, 3, 1, 3, 2, 4, 6, 0, 2, 1, 0, 2, 1, 0, 3, 2, 1),
(5, 2, 1.5, 3, 2, 5, 2, 5, 8, 0, 2, 1, 0, 3, 2, 1, 4, 3, 2);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `buildings`
--

CREATE TABLE `buildings` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `price_p0_p` int(11) DEFAULT NULL,
  `price_p0_s` int(11) DEFAULT NULL,
  `price_p0_t` int(11) DEFAULT NULL,
  `price_p1_p` int(11) DEFAULT NULL,
  `price_p1_s` int(11) DEFAULT NULL,
  `price_p1_t` int(11) DEFAULT NULL,
  `price_p2_p` int(11) DEFAULT NULL,
  `price_p2_s` int(11) DEFAULT NULL,
  `price_p2_t` int(11) DEFAULT NULL,
  `price_p3_p` int(11) DEFAULT NULL,
  `price_p3_s` int(11) DEFAULT NULL,
  `price_p3_t` int(11) DEFAULT NULL,
  `number_buildings` int(11) DEFAULT NULL,
  `discount_neighbour_p` int(11) DEFAULT NULL,
  `discount_neighbour_s` int(11) DEFAULT NULL,
  `discount_neighbour_t` int(11) DEFAULT NULL,
  `discount_neighbour_other_p` int(11) DEFAULT NULL,
  `discount_neighbour_other_s` int(11) DEFAULT NULL,
  `discount_neighbour_other_t` int(11) DEFAULT NULL,
  `earnings_p` int(11) DEFAULT NULL,
  `earnings_s` int(11) DEFAULT NULL,
  `earnings_t` int(11) DEFAULT NULL,
  `earnings_fp` int(11) DEFAULT NULL,
  `earnings_ek` int(11) DEFAULT NULL,
  `earnings_vp` int(11) DEFAULT NULL,
  `previouse_building` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `buildings`
--

INSERT INTO `buildings` (`id`, `name`, `price_p0_p`, `price_p0_s`, `price_p0_t`, `price_p1_p`, `price_p1_s`, `price_p1_t`, `price_p2_p`, `price_p2_s`, `price_p2_t`, `price_p3_p`, `price_p3_s`, `price_p3_t`, `number_buildings`, `discount_neighbour_p`, `discount_neighbour_s`, `discount_neighbour_t`, `discount_neighbour_other_p`, `discount_neighbour_other_s`, `discount_neighbour_other_t`, `earnings_p`, `earnings_s`, `earnings_t`, `earnings_fp`, `earnings_ek`, `earnings_vp`, `previouse_building`) VALUES
(1, 'Kolonie', 2, 1, 0, 2, 2, 0, 3, 3, 0, 3, 3, 2, 10, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, NULL),
(2, 'Miene', 2, 2, 0, 3, 3, 0, 5, 4, 1, 5, 5, 2, 6, 0, 0, 0, 1, 1, 0, 4, 0, 0, 0, 0, 0, 1),
(3, 'Handelsposten', 3, 2, 0, 3, 3, 0, 3, 3, 1, 4, 3, 1, 6, 0, 0, 0, 1, 1, 1, 0, 2, 1, 0, 0, 0, 1),
(4, 'Labor', 2, 2, 1, 3, 2, 1, 3, 2, 2, 4, 3, 3, 5, 0, 0, 0, 0, 0, 1, 0, 0, 0, 4, 0, 0, 1),
(5, 'Regierungssitz', 8, 8, 3, 9, 9, 3, 9, 9, 4, 10, 10, 4, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3),
(6, 'Stadt', 5, 5, 1, 6, 6, 2, 8, 8, 4, 9, 9, 4, 2, 0, 0, 0, 1, 1, 0, 2, 1, 1, 0, 0, 0, 3),
(7, 'Forschungsanlage', 5, 5, 2, 6, 6, 2, 8, 8, 4, 9, 9, 4, 3, 0, 0, 0, 0, 1, 1, 0, 0, 0, 6, 1, 0, 4),
(8, 'Drohne', 2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(9, 'Raumstation', 4, 4, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 8),
(10, 'Satelit', 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `cell_positions`
--

CREATE TABLE `cell_positions` (
  `id` int(11) NOT NULL,
  `coordinate_x` int(11) DEFAULT NULL,
  `coordinate_y` int(11) DEFAULT NULL,
  `position_x` int(11) DEFAULT NULL,
  `position_y` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `cell_positions`
--

INSERT INTO `cell_positions` (`id`, `coordinate_x`, `coordinate_y`, `position_x`, `position_y`) VALUES
(1, 1, 1, 82, 65),
(2, 2, 1, 192, 130),
(3, 3, 1, 309, 65),
(4, 4, 1, 430, 130),
(5, 5, 1, 543, 65),
(6, 6, 1, 656, 130),
(7, 7, 1, 769, 65),
(8, 8, 1, 882, 130),
(9, 9, 1, 1003, 65),
(10, 10, 1, 1119, 130),
(11, 11, 1, 1232, 65),
(12, 12, 1, 1342, 130),
(13, 13, 1, 1456, 65),
(14, 14, 1, 1577, 130),
(15, 15, 1, 1692, 65),
(16, 16, 1, 1807, 130),
(17, 17, 1, 1920, 65),
(18, 1, 2, 82, 195),
(19, 2, 2, 192, 260),
(20, 3, 2, 309, 195),
(21, 4, 2, 430, 260),
(22, 5, 2, 543, 195),
(23, 6, 2, 656, 260),
(24, 7, 2, 769, 195),
(25, 8, 2, 882, 260),
(26, 9, 2, 1003, 195),
(27, 10, 2, 1119, 260),
(28, 11, 2, 1232, 195),
(29, 12, 2, 1342, 260),
(30, 13, 2, 1456, 195),
(31, 14, 2, 1577, 260),
(32, 15, 2, 1692, 195),
(33, 16, 2, 1807, 260),
(34, 17, 2, 1920, 195),
(35, 1, 3, 82, 325),
(36, 2, 3, 192, 390),
(37, 3, 3, 309, 325),
(38, 4, 3, 430, 390),
(39, 5, 3, 543, 325),
(40, 6, 3, 656, 390),
(41, 7, 3, 769, 325),
(42, 8, 3, 882, 390),
(43, 9, 3, 1003, 325),
(44, 10, 3, 1119, 390),
(45, 11, 3, 1232, 325),
(46, 12, 3, 1342, 390),
(47, 13, 3, 1456, 325),
(48, 14, 3, 1577, 390),
(49, 15, 3, 1692, 325),
(50, 16, 3, 1807, 390),
(51, 17, 3, 1920, 325),
(52, 1, 4, 82, 462),
(53, 2, 4, 192, 530),
(54, 3, 4, 309, 462),
(55, 4, 4, 430, 530),
(56, 5, 4, 543, 462),
(57, 6, 4, 656, 530),
(58, 7, 4, 769, 462),
(59, 8, 4, 882, 530),
(60, 9, 4, 1003, 462),
(61, 10, 4, 1119, 530),
(62, 11, 4, 1232, 462),
(63, 12, 4, 1342, 530),
(64, 13, 4, 1456, 462),
(65, 14, 4, 1577, 530),
(66, 15, 4, 1692, 462),
(67, 16, 4, 1807, 530),
(68, 17, 4, 1920, 462),
(69, 1, 5, 82, 592),
(70, 2, 5, 192, 658),
(71, 3, 5, 309, 592),
(72, 4, 5, 430, 658),
(73, 5, 5, 543, 592),
(74, 6, 5, 656, 658),
(75, 7, 5, 769, 592),
(76, 8, 5, 882, 658),
(77, 9, 5, 1003, 592),
(78, 10, 5, 1119, 658),
(79, 11, 5, 1232, 592),
(80, 12, 5, 1342, 658),
(81, 13, 5, 1456, 592),
(82, 14, 5, 1577, 658),
(83, 15, 5, 1692, 592),
(84, 16, 5, 1807, 658),
(85, 17, 5, 1920, 592),
(86, 1, 6, 82, 725),
(87, 2, 6, 192, 790),
(88, 3, 6, 309, 725),
(89, 4, 6, 430, 790),
(90, 5, 6, 543, 725),
(91, 6, 6, 656, 790),
(92, 7, 6, 769, 725),
(93, 8, 6, 882, 790),
(94, 9, 6, 1003, 725),
(95, 10, 6, 1119, 790),
(96, 11, 6, 1232, 725),
(97, 12, 6, 1342, 790),
(98, 13, 6, 1456, 725),
(99, 14, 6, 1577, 790),
(100, 15, 6, 1692, 725),
(101, 16, 6, 1807, 790),
(102, 17, 6, 1920, 725),
(103, 1, 7, 82, 855),
(104, 2, 7, 192, 927),
(105, 3, 7, 309, 855),
(106, 4, 7, 430, 927),
(107, 5, 7, 543, 855),
(108, 6, 7, 656, 927),
(109, 7, 7, 769, 855),
(110, 8, 7, 882, 927),
(111, 9, 7, 1003, 855),
(112, 10, 7, 1119, 927),
(113, 11, 7, 1232, 855),
(114, 12, 7, 1342, 927),
(115, 13, 7, 1456, 855),
(116, 14, 7, 1577, 927),
(117, 15, 7, 1692, 855),
(118, 16, 7, 1807, 927),
(119, 17, 7, 1920, 855),
(120, 1, 8, 82, 993),
(121, 2, 8, 192, 1060),
(122, 3, 8, 309, 993),
(123, 4, 8, 430, 1060),
(124, 5, 8, 543, 993),
(125, 6, 8, 656, 1060),
(126, 7, 8, 769, 993),
(127, 8, 8, 882, 1060),
(128, 9, 8, 1003, 993),
(129, 10, 8, 1119, 1060),
(130, 11, 8, 1232, 993),
(131, 12, 8, 1342, 1060),
(132, 13, 8, 1456, 993),
(133, 14, 8, 1577, 1060),
(134, 15, 8, 1692, 993),
(135, 16, 8, 1807, 1060),
(136, 17, 8, 1920, 993),
(137, 1, 9, 82, 1125),
(138, 3, 9, 309, 1125),
(139, 5, 9, 543, 1125),
(140, 7, 9, 769, 1125),
(141, 9, 9, 1003, 1125),
(142, 11, 9, 1232, 1125),
(143, 13, 9, 1456, 1125),
(144, 15, 9, 1692, 1125),
(145, 17, 9, 1920, 1125);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `classes`
--

CREATE TABLE `classes` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `resource_p` int(11) DEFAULT NULL,
  `resource_s` int(11) DEFAULT NULL,
  `resource_t` int(11) DEFAULT NULL,
  `color` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `classes`
--

INSERT INTO `classes` (`id`, `name`, `resource_p`, `resource_s`, `resource_t`, `color`) VALUES
(1, 'Borac', 2, 3, 1, 1),
(2, 'Cragons', 2, 3, 1, 1),
(3, 'Joravas', 2, 1, 3, 3),
(4, 'Heratics', 2, 1, 3, 3),
(5, 'Novox', 1, 2, 3, 4),
(6, 'Salaranier', 1, 2, 3, 4),
(7, 'Encor', 1, 3, 2, 5),
(8, 'Munen', 1, 3, 2, 5),
(9, 'Wannarack', 3, 1, 2, 6),
(10, 'Ygdrack', 3, 1, 2, 6),
(11, 'Legion', 3, 2, 1, 7),
(12, 'Gunracs', 3, 2, 1, 7);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `class_colors`
--

CREATE TABLE `class_colors` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `class_colors`
--

INSERT INTO `class_colors` (`id`, `name`) VALUES
(1, 'Schwarz'),
(3, 'Grau'),
(4, 'Grün'),
(5, 'Blau'),
(6, 'Rot'),
(7, 'Gelb');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `enemies`
--

CREATE TABLE `enemies` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `enemies`
--

INSERT INTO `enemies` (`id`, `name`) VALUES
(1, 'Der Parasit'),
(2, 'Piraten');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `games`
--

CREATE TABLE `games` (
  `id` int(11) NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `game_overview` blob,
  `game_data` mediumblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `main_menu_dynamic_content`
--

CREATE TABLE `main_menu_dynamic_content` (
  `id` int(11) NOT NULL,
  `content` text,
  `priority` int(11) DEFAULT NULL,
  `display` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `penalty_lost_const`
--

CREATE TABLE `penalty_lost_const` (
  `id` int(11) NOT NULL,
  `description` text,
  `use_turn_pirate` int(11) DEFAULT NULL,
  `use_turn_parasite_mid` int(11) DEFAULT NULL,
  `use_turn_parasite_end` int(11) DEFAULT NULL,
  `lowered_by_1` float DEFAULT NULL,
  `lowered_by_2` float DEFAULT NULL,
  `lowered` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `penalty_lost_const`
--

INSERT INTO `penalty_lost_const` (`id`, `description`, `use_turn_pirate`, `use_turn_parasite_mid`, `use_turn_parasite_end`, `lowered_by_1`, `lowered_by_2`, `lowered`) VALUES
(1, 'Ein Gebäude (außer Regierungssitz, Stadt und Forschungsanlage) wird zu einer Kolonie zurückgebaut', 4, 1, 1, 0.5, 0.2, NULL),
(2, 'Ein Gebäude (außer Regierungssitz, Stadt und Forschungseinrichtung) wird zerstört', -1, -1, 4, 0.5, 0.2, NULL),
(3, 'Ein Gebäude (außer Regierungssitz) wird um eine Stufe zurückgebaut', 4, 1, 4, 0.5, 0.2, NULL),
(4, 'Ein Gebäude (außer Regierungssitz) wird zerstört', -1, -1, 5, 0.5, 0.2, NULL),
(5, 'Die nächste Drohne in Reichweite wird zerstört', 1, 1, 1, 0.5, 0.2, NULL),
(6, 'Die nächste Raumstation in Reichweite wird zu einer Drohne zurückgebaut', 4, 1, 1, 0.5, 0.2, NULL),
(7, 'Die nächste Raumstation in Reichweite wird zerstört', -1, 5, 4, 0.5, 0.2, NULL),
(8, 'Ein Spieler wird im Forschungsbereich Militär oder Entwicklung um eine Stufe zurückgeworfen (nicht unterhalb eines Resourcenlevels)', -1, 4, 3, 0.5, 0.2, NULL),
(9, 'Ein Gebäude wird blockiert, dass in dieser Runde nicht mehr aufgerüstet werden kann', 1, 1, 1, 0.5, 0.2, NULL),
(10, 'Ein Gebäude wird blockiert, dass in den nächsten 3 Zügen des Spielers nicht mehr aufgerüstet werden kann', 1, 1, 1, 0.5, 0.2, NULL),
(11, 'Resourcen von allen Spielern auf dem angegiffenen Planeten werden gestohlen', 1, -1, -1, 0.5, 0.2, NULL),
(12, 'Resourcen von dem Spieler, der auf dem angegriffenen Planeten die meisten Gebäude hat werden gestohlen (bei unentschieden wird aufgeteilt)', 1, -1, -1, 0.5, 0.2, NULL),
(13, 'Resourcen von dem Spieler, der auf dem angegriffenen Planeten das Größte Gebäude hat werden gestohlen (bei unentschieden wird aufgeteilt)', 1, -1, -1, 0.5, 0.2, NULL);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `research_areas`
--

CREATE TABLE `research_areas` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `research_areas`
--

INSERT INTO `research_areas` (`id`, `name`) VALUES
(1, 'Mienen'),
(2, 'Wirtschaft'),
(3, 'Militär'),
(4, 'Entwicklung'),
(5, 'FTL-Antrieb'),
(6, 'Die Waffe');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `research_resources_const`
--

CREATE TABLE `research_resources_const` (
  `id` int(11) NOT NULL,
  `level_reached` int(11) DEFAULT NULL,
  `research_area` int(11) DEFAULT NULL,
  `resources_c` float DEFAULT NULL,
  `resources_fe` float DEFAULT NULL,
  `resources_si` float DEFAULT NULL,
  `resources_ek` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `research_resources_const`
--

INSERT INTO `research_resources_const` (`id`, `level_reached`, `research_area`, `resources_c`, `resources_fe`, `resources_si`, `resources_ek`) VALUES
(1, 2, 3, 1, 1, 1, 0),
(2, 4, 3, 2, 2, 2, 0),
(3, 2, 4, 1, 1, 1, 0),
(4, 4, 4, 2, 2, 2, 0),
(5, 2, 5, 1, 1, 1, 0),
(6, 4, 5, 2, 2, 2, 0),
(7, 2, 6, 0.9, 0.9, 0.9, 0),
(8, 4, 6, 1.4, 1.4, 1.4, 0),
(9, 5, 6, 1, 1, 1, 0),
(10, 6, 6, 1.8, 1.8, 1.8, 0),
(11, 7, 6, 1.4, 1.4, 1.4, 0.4),
(12, 8, 6, 1.8, 1.8, 1.8, 0.7),
(13, 9, 6, 1.8, 1.8, 1.8, 1),
(14, 10, 6, 3.6, 3.6, 3.6, 2);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `resources`
--

CREATE TABLE `resources` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `resources`
--

INSERT INTO `resources` (`id`, `name`) VALUES
(1, 'Kohlenstoff'),
(2, 'Eisen'),
(3, 'Silizium');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `statistics`
--

CREATE TABLE `statistics` (
  `user_id` int(11) NOT NULL,
  `game_id` int(11) NOT NULL,
  `points` int(11) DEFAULT NULL,
  `position` int(11) DEFAULT NULL,
  `buildings` int(11) DEFAULT NULL,
  `planets` int(11) DEFAULT NULL,
  `alliances` int(11) DEFAULT NULL,
  `class_played` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `target_values`
--

CREATE TABLE `target_values` (
  `id` int(11) NOT NULL,
  `enemy_id` int(11) DEFAULT NULL,
  `used` tinyint(1) DEFAULT NULL,
  `base_value` float DEFAULT NULL,
  `goverment_attackable` tinyint(1) DEFAULT NULL,
  `goverment_planet_attackable` tinyint(1) DEFAULT NULL,
  `research_station_attackable` tinyint(1) DEFAULT NULL,
  `research_station_planet_attackable` tinyint(1) DEFAULT NULL,
  `alliances_attackable` tinyint(1) DEFAULT NULL,
  `num_players` float DEFAULT NULL,
  `num_buildings` float DEFAULT NULL,
  `colonies` float DEFAULT NULL,
  `mines` float DEFAULT NULL,
  `traiding_posts` float DEFAULT NULL,
  `labs` float DEFAULT NULL,
  `cities` float DEFAULT NULL,
  `research_stations` float DEFAULT NULL,
  `goverments` float DEFAULT NULL,
  `unshared_planets_main_player` float DEFAULT NULL,
  `shared_planets_main_player` float DEFAULT NULL,
  `players_points` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `target_values`
--

INSERT INTO `target_values` (`id`, `enemy_id`, `used`, `base_value`, `goverment_attackable`, `goverment_planet_attackable`, `research_station_attackable`, `research_station_planet_attackable`, `alliances_attackable`, `num_players`, `num_buildings`, `colonies`, `mines`, `traiding_posts`, `labs`, `cities`, `research_stations`, `goverments`, `unshared_planets_main_player`, `shared_planets_main_player`, `players_points`) VALUES
(1, 1, 1, 100, 0, 1, 1, 1, 1, -8, 3, 0, 1, -1, 2, -1, 3, -3, 3, -4, 1),
(2, 2, 1, 100, 0, 0, 0, 1, 0, -15, 2, 0, 2, 2, -1, 1, -1, 0, 5, 4, 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `passwd` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `alliance_const`
--
ALTER TABLE `alliance_const`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `attack_const`
--
ALTER TABLE `attack_const`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `attack_values_const`
--
ALTER TABLE `attack_values_const`
  ADD PRIMARY KEY (`turn`);

--
-- Indizes für die Tabelle `buildings`
--
ALTER TABLE `buildings`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `cell_positions`
--
ALTER TABLE `cell_positions`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `classes`
--
ALTER TABLE `classes`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `class_colors`
--
ALTER TABLE `class_colors`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `enemies`
--
ALTER TABLE `enemies`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `games`
--
ALTER TABLE `games`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `main_menu_dynamic_content`
--
ALTER TABLE `main_menu_dynamic_content`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `penalty_lost_const`
--
ALTER TABLE `penalty_lost_const`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `research_areas`
--
ALTER TABLE `research_areas`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `research_resources_const`
--
ALTER TABLE `research_resources_const`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `resources`
--
ALTER TABLE `resources`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `statistics`
--
ALTER TABLE `statistics`
  ADD PRIMARY KEY (`user_id`,`game_id`);

--
-- Indizes für die Tabelle `target_values`
--
ALTER TABLE `target_values`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `alliance_const`
--
ALTER TABLE `alliance_const`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT für Tabelle `attack_const`
--
ALTER TABLE `attack_const`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT für Tabelle `buildings`
--
ALTER TABLE `buildings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT für Tabelle `cell_positions`
--
ALTER TABLE `cell_positions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=146;
--
-- AUTO_INCREMENT für Tabelle `classes`
--
ALTER TABLE `classes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT für Tabelle `class_colors`
--
ALTER TABLE `class_colors`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT für Tabelle `enemies`
--
ALTER TABLE `enemies`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT für Tabelle `games`
--
ALTER TABLE `games`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `main_menu_dynamic_content`
--
ALTER TABLE `main_menu_dynamic_content`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `penalty_lost_const`
--
ALTER TABLE `penalty_lost_const`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT für Tabelle `research_areas`
--
ALTER TABLE `research_areas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT für Tabelle `research_resources_const`
--
ALTER TABLE `research_resources_const`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT für Tabelle `resources`
--
ALTER TABLE `resources`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT für Tabelle `target_values`
--
ALTER TABLE `target_values`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT für Tabelle `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

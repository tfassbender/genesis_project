-- phpMyAdmin SQL Dump
-- version 4.6.6deb4
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Erstellungszeit: 28. Jun 2018 um 15:24
-- Server-Version: 10.1.23-MariaDB-9+deb9u1
-- PHP-Version: 7.0.27-0+deb9u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `genesis_project`
--
CREATE DATABASE IF NOT EXISTS `genesis_project` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `genesis_project`;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `alliance_const`
--

CREATE TABLE IF NOT EXISTS `alliance_const` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `used` tinyint(1) DEFAULT NULL,
  `min_planets` int(11) DEFAULT NULL,
  `min_planets_others` int(11) DEFAULT NULL,
  `min_buildings` int(11) DEFAULT NULL,
  `min_buildings_others` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `alliance_const`
--

INSERT INTO `alliance_const` (`id`, `used`, `min_planets`, `min_planets_others`, `min_buildings`, `min_buildings_others`) VALUES
(1, 1, 3, 1, 6, 2);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `attack_const`
--

CREATE TABLE IF NOT EXISTS `attack_const` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `used` tinyint(1) DEFAULT NULL,
  `probability_factor_private` float DEFAULT NULL,
  `turn_start_pirate` int(11) DEFAULT NULL,
  `turn_end_pirate` int(11) DEFAULT NULL,
  `player_turns_parasite_attack` int(11) DEFAULT NULL,
  `player_turns_parasite_attack_decrease` int(11) DEFAULT NULL,
  `turn_start_parasite_mid` int(11) DEFAULT NULL,
  `turn_start_parasite_end` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `attack_const`
--

INSERT INTO `attack_const` (`id`, `used`, `probability_factor_private`, `turn_start_pirate`, `turn_end_pirate`, `player_turns_parasite_attack`, `player_turns_parasite_attack_decrease`, `turn_start_parasite_mid`, `turn_start_parasite_end`) VALUES
(1, 1, 0.015, 2, 5, 15, 3, 3, 2);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `attack_values_const`
--

CREATE TABLE IF NOT EXISTS `attack_values_const` (
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
  `points_parasite_end_3` int(11) DEFAULT NULL,
  PRIMARY KEY (`turn`)
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

CREATE TABLE IF NOT EXISTS `buildings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
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
  `previouse_building` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

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
-- Tabellenstruktur für Tabelle `classes`
--

CREATE TABLE IF NOT EXISTS `classes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `resource_p` int(11) DEFAULT NULL,
  `resource_s` int(11) DEFAULT NULL,
  `resource_t` int(11) DEFAULT NULL,
  `color` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

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

CREATE TABLE IF NOT EXISTS `class_colors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

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

CREATE TABLE IF NOT EXISTS `enemies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

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

CREATE TABLE IF NOT EXISTS `games` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) DEFAULT NULL,
  `game_overview` blob,
  `game_data` mediumblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `main_menu_dynamic_content`
--

CREATE TABLE IF NOT EXISTS `main_menu_dynamic_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text,
  `priority` int(11) DEFAULT NULL,
  `display` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `penalty_lost_const`
--

CREATE TABLE IF NOT EXISTS `penalty_lost_const` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text,
  `use_turn_pirate` int(11) DEFAULT NULL,
  `use_turn_parasite_mid` int(11) DEFAULT NULL,
  `use_turn_parasite_end` int(11) DEFAULT NULL,
  `lowered_by_1` float DEFAULT NULL,
  `lowered_by_2` float DEFAULT NULL,
  `lowered` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

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

CREATE TABLE IF NOT EXISTS `research_areas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

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

CREATE TABLE IF NOT EXISTS `research_resources_const` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level_reached` int(11) DEFAULT NULL,
  `research_area` int(11) DEFAULT NULL,
  `resources_c` float DEFAULT NULL,
  `resources_fe` float DEFAULT NULL,
  `resources_si` float DEFAULT NULL,
  `resources_ek` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

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

CREATE TABLE IF NOT EXISTS `resources` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

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

CREATE TABLE IF NOT EXISTS `statistics` (
  `user_id` int(11) NOT NULL,
  `game_id` int(11) NOT NULL,
  `points` int(11) DEFAULT NULL,
  `position` int(11) DEFAULT NULL,
  `buildings` int(11) DEFAULT NULL,
  `planets` int(11) DEFAULT NULL,
  `alliances` int(11) DEFAULT NULL,
  `class_played` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `target_values`
--

CREATE TABLE IF NOT EXISTS `target_values` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
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
  `players_points` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

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

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `passwd` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

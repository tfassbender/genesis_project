CREATE DATABASE IF NOT EXISTS genesis_project;

CREATE TABLE IF NOT EXISTS genesis_project.users (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(50) UNIQUE KEY,
	passwd VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS genesis_project.main_menu_dynamic_content (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	content TEXT,
	priority INT,
	display BOOLEAN
);

CREATE TABLE IF NOT EXISTS genesis_project.games (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	active BOOLEAN,
	game_overview BLOB,
	game_data MEDIUMBLOB
);

CREATE TABLE IF NOT EXISTS genesis_project.resources (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS genesis_project.class_colors (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS genesis_project.classes (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(50),
	resource_p INT REFERENCES genesis_project.resources (id),
	resource_s INT REFERENCES genesis_project.resources (id),
	resource_t INT REFERENCES genesis_project.resources (id),
	color INT REFERENCES genesis_project.class_colors (id)
);

CREATE TABLE IF NOT EXISTS genesis_project.statistics (
	user_id INT REFERENCES genesis_project.users (id),
	game_id INT REFERENCES genesis_project.games (id),
	points INT,
	position INT,
	buildings INT,
	planets INT,
	alliances INT,
	class_played INT REFERENCES genesis_project.classes (id),
	PRIMARY KEY (user_id, game_id)
);

CREATE TABLE IF NOT EXISTS genesis_project.alliance_const (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	used BOOLEAN,
	min_planets INT,
	min_planets_others INT,
	min_buildings INT,
	min_buildings_others INT
);

CREATE TABLE IF NOT EXISTS genesis_project.buildings (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(50),
	price_p0_p INT,
	price_p0_s INT,
	price_p0_t INT,
	price_p1_p INT,
	price_p1_s INT,
	price_p1_t INT,
	price_p2_p INT,
	price_p2_s INT,
	price_p2_t INT,
	price_p3_p INT,
	price_p3_s INT,
	price_p3_t INT,
	number_buildings INT,
	discount_neighbour_p INT,
	discount_neighbour_s INT,
	discount_neighbour_t INT,
	discount_neighbour_other_p INT,
	discount_neighbour_other_s INT,
	discount_neighbour_other_t INT,
	earnings_p INT,
	earnings_s INT,
	earnings_t INT,
	earnings_fp INT,
	earnings_ek INT,
	earnings_vp INT,
	previouse_building INT REFERENCES genesis_project.buildings (id)
);

CREATE TABLE IF NOT EXISTS genesis_project.research_areas (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS genesis_project.research_resources_const (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	level_reached INT,
	research_area INT REFERENCES genesis_project.research_areas (id),
	resources_c FLOAT,
	resources_fe FLOAT,
	resources_si FLOAT,
	resources_ek FLOAT,
);

CREATE TABLE IF NOT EXISTS genesis_project.attack_const (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	used BOOLEAN,
	probability_factor_private FLOAT,
	turn_start_pirate INT,
	turn_end_pirate INT,
	player_turns_parasite_attack INT,
	player_turns_parasite_attack_decrease INT,
	turn_start_parasite_mid INT,
	turn_start_parasite_end INT
);

CREATE TABLE IF NOT EXISTS genesis_project.attack_values_const (
	turn INT NOT NULL PRIMARY KEY,
	attack_strength_pirate_base INT,
	attack_strength_pirate_per_player FLOAT,
	attack_strength_pirate_random INT,
	attack_strength_parasite_mid_base INT,
	attack_strength_parasite_mid_per_player FLOAT,
	attack_strength_parasite_mid_random INT,
	attack_strength_parasite_end_base INT,
	attack_strength_parasite_end_per_player FLOAT,
	attack_strength_parasite_end_random INT,
	points_pirate_1 INT,
	points_pirate_2 INT,
	points_pirate_3 INT,
	points_parasite_mid_1 INT,
	points_parasite_mid_2 INT,
	points_parasite_mid_3 INT,
	points_parasite_end_1 INT,
	points_parasite_end_2 INT,
	points_parasite_end_3 INT
);

CREATE TABLE IF NOT EXISTS genesis_project.penalty_lost_const (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	description TEXT,
	use_turn_pirate INT,
	use_turn_parasite_mid INT,
	use_turn_parasite_end INT,
	lowered_by_1 FLOAT,
	lowered_by_2 FLOAT,
	lowered INT REFERENCES genesis_project.penalty_lost_const (id)
);

CREATE TABLE IF NOT EXISTS genesis_project.enemies (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS genesis_project.target_values (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	enemy_id INT REFERENCES genesis_project.enemies (id),
	used BOOLEAN,
	base_value FLOAT,
	goverment_attackable BOOLEAN,
	goverment_planet_attackable BOOLEAN,
	research_station_attackable BOOLEAN,
	research_station_planet_attackable BOOLEAN,
	alliances_attackable BOOLEAN,
	num_players FLOAT,
	num_buildings FLOAT,
	colonies FLOAT,
	mines FLOAT,
	traiding_posts FLOAT,
	labs FLOAT,
	cities FLOAT,
	research_stations FLOAT,
	goverments FLOAT,
	unshared_planets_main_player FLOAT,
	shared_planets_main_player FLOAT,
	players_points FLOAT
);

CREATE TABLE IF NOT EXISTS genesis_project.cell_positions (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	coordinate_x INT,
	coordinate_y INT,
	position_x INT,
	position_y INT
);

CREATE TABLE IF NOT EXISTS genesis_project.start_research_states (
	class_id INT REFERENCES genesis_project.classes (id),
	research_id INT REFERENCES genesis_project.research_areas (id),
	starting_state INT
);
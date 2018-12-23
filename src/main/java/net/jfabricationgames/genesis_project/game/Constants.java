package net.jfabricationgames.genesis_project.game;

import java.util.Map;

/**
 * This class is used to keep all game constants as public static fields.</br>
 * No final fields are used because the constants are loaded from the database at game start
 */
public abstract class Constants {
	
	//basic
	public static int BUILDINGS_PER_PLANET = 3;
	public static int TURNS_PLAYED = 6;
	public static int MAX_RESEARCH_STATE_DEFAULT = 6;
	public static int MAX_RESEARCH_STATE_WEAPON = 10;
	
	//alliances
	public static int ALLIANCE_MIN_PLANETS;
	public static int ALLIANCE_MIN_PLANETS_OTHER_PLAYERS;
	public static int ALLIANCE_MIN_BUILDINGS;
	public static int ALLIANCE_MIN_BUILDINGS_OTHER_PLAYERS;
	
	//attacks
	public static int ATTACK_PROBABILITY_FACTOR_PRIVATE;
	public static int TURN_START_PIRATE;
	public static int TURN_END_PIRATE;
	public static int PLAYER_TURNS_PARASITE_ATTACK;
	public static int PLAYER_TURNS_PARASITE_ATTACK_DECREASE;
	public static int TURN_START_PARASITE_MID;
	public static int TURN_START_PARASITE_END;
	
	/**
	 * ATTACK_STRENGTH_... arrays keep all attack strength informations about each game turn (index: game turn -1)
	 */
	public static double[] ATTACK_STRENGTH_PIRATE_BASE;
	public static double[] ATTACK_STRENGTH_PIRATE_PER_PLAYER;
	public static double[] ATTACK_STRENGTH_PIRATE_RANDOM;
	public static double[] ATTACK_STRENGTH_PARASITE_MID_BASE;
	public static double[] ATTACK_STRENGTH_PARASITE_MID_PER_PLAYER;
	public static double[] ATTACK_STRENGTH_PARASITE_MID_RANDOM;
	public static double[] ATTACK_STRENGTH_PARASITE_END_BASE;
	public static double[] ATTACK_STRENGTH_PARASITE_END_PER_PLAYER;
	public static double[] ATTACK_STRENGTH_PARASITE_END_RANDOM;
	
	/**
	 * ATTACK_POINT_... arrays keep all informations about the points players gain for defense in each game turn
	 * <p>
	 * first index: game turn -1</br>
	 * second index: players position -1
	 * </p>
	 */
	public static int[][] ATTACK_POINTS_PIRATE;
	public static int[][] ATTACK_POINTS_PARASITE_MID;
	public static int[][] ATTACK_POINTS_PARASITE_END;
	
	//research
	/**
	 * Map keys for RESEARCH_RESOURCES are listed below:
	 * <p>
	 * first key: research area (listed below)</br>
	 * second key: level to be reached </br>
	 * third key: resource type
	 * </p>
	 */
	public static Map<ResearchArea, Map<Integer, Map<Resource, Double>>> RESEARCH_RESOURCES;
	/**
	 * The starting positions on the research board for every playable class.
	 */
	public static Map<PlayerClass, Map<ResearchArea, Integer>> STARTING_RESEARCH_STATES;
	public static int RESEARCH_AREA_MINES;
	public static int RESEARCH_AREA_ECONOMY;
	public static int RESEARCH_AREA_MILITARY;
	public static int RESEARCH_AREA_DEVELOPMENT;
	public static int RESEARCH_AREA_FTL;
	public static int RESEARCH_AREA_WEAPON;
	
	//buildings
	/**
	 * BUILDING_COSTS_... arrays keep all information about the costs of a building on each planet type:
	 * <p>
	 * first index: planet type (distance 0 to 3) </br>
	 * second index: resource type (0 -> prime, ...)
	 * </p>
	 * WARNING: don't set to other arrays but insert the values because they are referenced by the Building-Enum.
	 */
	public static int[][] BUILDING_COSTS_COLONIE = new int[4][3];
	public static int[][] BUILDING_COSTS_MINE = new int[4][3];
	public static int[][] BUILDING_COSTS_TRAIDING_POST = new int[4][3];
	public static int[][] BUILDING_COSTS_LABORATORY = new int[4][3];
	public static int[][] BUILDING_COSTS_GOVERMENT = new int[4][3];
	public static int[][] BUILDING_COSTS_CITY = new int[4][3];
	public static int[][] BUILDING_COSTS_RESEARCH_CENTER = new int[4][3];
	public static int[][] BUILDING_COSTS_DRONE = new int[1][3];
	public static int[][] BUILDING_COSTS_SPACE_STATION = new int[1][3];
	public static int[][] BUILDING_COSTS_SATELLITE = new int[1][3];
	
	/**
	 * The number of buildings per player at game start (on field and class board).
	 */
	public static Map<Building, Integer> BUILDING_NUMBERS;
}
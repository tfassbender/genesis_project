package net.jfabricationgames.genesis_project.game;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.genesis_project.json.deserializer.CustomBoardPositionDeserializer;
import net.jfabricationgames.genesis_project.json.serializer.CustomBoardPositionSerializer;

/**
 * This class is used to keep all game constants as public static fields.</br>
 * No final fields are used because the constants are loaded from the database at game start
 */
public class Constants {
	
	private static Constants instance;
	
	/**
	 * DO NOT USE - empty constructor for json deserialization
	 */
	@Deprecated
	public Constants() {
		
	}
	
	public static synchronized Constants getInstance() {
		if (instance == null) {
			instance = new Constants();
		}
		return instance;
	}
	
	//basic
	public int BUILDINGS_PER_PLANET = 3;
	public int TURNS_PLAYED = 6;
	public int MAX_RESEARCH_STATE_DEFAULT = 6;
	public int MAX_RESEARCH_STATE_WEAPON = 10;
	public int RESEARCH_POINTS_FOR_STATE_INCREASE = 5;
	public int RESEARCH_SCIENTISTS_FOR_LOW_STATE = 0;
	public int RESEARCH_SCIENTISTS_FOR_HIGH_STATE = 1;
	public int RESEARCH_STATE_HIGH = 5;//5 or above
	public int ALLIANCE_BONUS_COPIES = 2;
	
	//alliances
	public int ALLIANCE_MIN_PLANETS;
	public int ALLIANCE_MIN_PLANETS_OTHER_PLAYERS;
	public int ALLIANCE_MIN_BUILDINGS;
	public int ALLIANCE_MIN_BUILDINGS_OTHER_PLAYERS;
	
	//attacks
	public int ATTACK_PROBABILITY_FACTOR_PRIVATE;
	public int TURN_START_PIRATE;
	public int TURN_END_PIRATE;
	public int PLAYER_TURNS_PARASITE_ATTACK;
	public int PLAYER_TURNS_PARASITE_ATTACK_DECREASE;
	public int TURN_START_PARASITE_MID;
	public int TURN_START_PARASITE_END;
	
	/**
	 * ATTACK_STRENGTH_... arrays keep all attack strength informations about each game turn (index: game turn -1)
	 */
	public double[] ATTACK_STRENGTH_PIRATE_BASE;
	public double[] ATTACK_STRENGTH_PIRATE_PER_PLAYER;
	public double[] ATTACK_STRENGTH_PIRATE_RANDOM;
	public double[] ATTACK_STRENGTH_PARASITE_MID_BASE;
	public double[] ATTACK_STRENGTH_PARASITE_MID_PER_PLAYER;
	public double[] ATTACK_STRENGTH_PARASITE_MID_RANDOM;
	public double[] ATTACK_STRENGTH_PARASITE_END_BASE;
	public double[] ATTACK_STRENGTH_PARASITE_END_PER_PLAYER;
	public double[] ATTACK_STRENGTH_PARASITE_END_RANDOM;
	
	/**
	 * ATTACK_POINT_... arrays keep all informations about the points players gain for defense in each game turn
	 * <p>
	 * first index: game turn -1</br>
	 * second index: players position -1
	 * </p>
	 */
	public int[][] ATTACK_POINTS_PIRATE;
	public int[][] ATTACK_POINTS_PARASITE_MID;
	public int[][] ATTACK_POINTS_PARASITE_END;
	
	//research
	/**
	 * Map keys for RESEARCH_RESOURCES are listed below:
	 * <p>
	 * first key: research area (listed below)</br>
	 * second key: level to be reached </br>
	 * third key: resource type
	 * </p>
	 */
	public Map<ResearchArea, Map<Integer, Map<Resource, Double>>> RESEARCH_RESOURCES;
	/**
	 * The starting positions on the research board for every playable class.
	 */
	public Map<PlayerClass, Map<ResearchArea, Integer>> STARTING_RESEARCH_STATES;
	public int RESEARCH_AREA_MINES;
	public int RESEARCH_AREA_ECONOMY;
	public int RESEARCH_AREA_MILITARY;
	public int RESEARCH_AREA_DEVELOPMENT;
	public int RESEARCH_AREA_FTL;
	public int RESEARCH_AREA_WEAPON;
	
	//buildings
	/**
	 * BUILDING_COSTS_... arrays keep all information about the costs of a building on each planet type:
	 * <p>
	 * first index: planet type (distance 0 to 3) </br>
	 * second index: resource type (0 -> prime, ...)
	 * </p>
	 * WARNING: don't set to other arrays but insert the values because they are referenced by the Building-Enum.
	 */
	public int[][] BUILDING_COSTS_COLONIE = new int[4][3];
	public int[][] BUILDING_COSTS_MINE = new int[4][3];
	public int[][] BUILDING_COSTS_TRAIDING_POST = new int[4][3];
	public int[][] BUILDING_COSTS_LABORATORY = new int[4][3];
	public int[][] BUILDING_COSTS_GOVERNMENT = new int[4][3];
	public int[][] BUILDING_COSTS_CITY = new int[4][3];
	public int[][] BUILDING_COSTS_RESEARCH_CENTER = new int[4][3];
	public int[][] BUILDING_COSTS_DRONE = new int[1][3];
	public int[][] BUILDING_COSTS_SPACE_STATION = new int[1][3];
	public int[][] BUILDING_COSTS_SATELLITE = new int[1][3];
	
	public int[] getBuildingCosts(Building building, PlayerClass playerClass, Planet planet) {
		int planetDistance = playerClass.getPlanetDistance(planet);
		switch (building) {
			case CITY:
				return BUILDING_COSTS_CITY[planetDistance];
			case COLONY:
				return BUILDING_COSTS_COLONIE[planetDistance];
			case DRONE:
				return BUILDING_COSTS_DRONE[planetDistance];
			case GOVERNMENT:
				return BUILDING_COSTS_GOVERNMENT[planetDistance];
			case LABORATORY:
				return BUILDING_COSTS_LABORATORY[planetDistance];
			case MINE:
				return BUILDING_COSTS_MINE[planetDistance];
			case RESEARCH_CENTER:
				return BUILDING_COSTS_RESEARCH_CENTER[planetDistance];
			case SATELLITE:
				return BUILDING_COSTS_SATELLITE[planetDistance];
			case SPACE_STATION:
				return BUILDING_COSTS_SPACE_STATION[planetDistance];
			case TRADING_POST:
				return BUILDING_COSTS_TRAIDING_POST[planetDistance];
			default:
				throw new IllegalArgumentException("The Building type " + building + " is unknown");
		}
	}
	
	/**
	 * Building earnings for research points, scientists and dependent resources (primary, secondary and tertiary because they depend on the planet
	 * the're built on)
	 */
	public Map<Building, Integer> BUILDING_EARNINGS_RESEARCH_POINTS;
	public Map<Building, Integer> BUILDING_EARNINGS_SCIENTISTS;
	public Map<Building, DependentResources> BUILDING_EARNINGS_DEPENDENT;
	public Map<Building, Integer> BUILDING_EARNINGS_DEFENSE;
	
	/**
	 * The number of buildings per player at game start (on field and class board).
	 */
	public Map<Building, Integer> BUILDING_NUMBERS;
	
	/**
	 * The positions of the cells on the board.
	 */
	@JsonSerialize(keyUsing = CustomBoardPositionSerializer.class)
	@JsonDeserialize(keyUsing = CustomBoardPositionDeserializer.class)
	public Map<Position, int[]> CELL_COORDINATES;
}
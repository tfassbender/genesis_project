package net.jfabricationgames.genesis_project.testUtils;

import java.util.HashMap;
import java.util.Map;

import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.BuildingResources;
import net.jfabricationgames.genesis_project.game.Constants;
import net.jfabricationgames.genesis_project.game.DependentResources;
import net.jfabricationgames.genesis_project.game.PlayerClass;
import net.jfabricationgames.genesis_project.game.ResearchArea;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Resource;
import net.jfabricationgames.genesis_project.game.Board.Position;

public class ConstantsInitializerUtil {
	
	public static void initBuildingNumbers() {
		//initialize the BUILDING_NUMBERS field in the Constants class
		Constants.BUILDING_NUMBERS = new HashMap<Building, Integer>();
		
		Constants.BUILDING_NUMBERS.put(Building.COLONY, 10);
		Constants.BUILDING_NUMBERS.put(Building.MINE, 6);
		Constants.BUILDING_NUMBERS.put(Building.TRADING_POST, 6);
		Constants.BUILDING_NUMBERS.put(Building.LABORATORY, 5);
		Constants.BUILDING_NUMBERS.put(Building.GOVERNMENT, 1);
		Constants.BUILDING_NUMBERS.put(Building.CITY, 2);
		Constants.BUILDING_NUMBERS.put(Building.RESEARCH_CENTER, 3);
		Constants.BUILDING_NUMBERS.put(Building.DRONE, 10);
		Constants.BUILDING_NUMBERS.put(Building.SPACE_STATION, 5);
		Constants.BUILDING_NUMBERS.put(Building.SATELLITE, 99);
	}
	
	public static void initStartingResearchStates() {
		Constants.STARTING_RESEARCH_STATES = new HashMap<PlayerClass, Map<ResearchArea, Integer>>((int) (1.3 * PlayerClass.values().length));
		for (PlayerClass playerClass : PlayerClass.values()) {
			Map<ResearchArea, Integer> initStates = new HashMap<ResearchArea, Integer>((int) (1.3 * ResearchArea.values().length));
			//initialize with state 0 for all research areas for all classes
			for (ResearchArea research : ResearchArea.values()) {
				initStates.put(research, Integer.valueOf(0));
			}
			Constants.STARTING_RESEARCH_STATES.put(playerClass, initStates);
		}
	}
	
	public static void initResearchResources() {
		//initialize with some default values (from the database state 181223)
		Constants.RESEARCH_RESOURCES = new HashMap<ResearchArea, Map<Integer, Map<Resource, Double>>>();
		
		//first initialize all empty
		for (ResearchArea research : ResearchArea.values()) {
			Map<Integer, Map<Resource, Double>> resources = new HashMap<Integer, Map<Resource, Double>>();
			int researchStates = Constants.MAX_RESEARCH_STATE_DEFAULT;
			if (research == ResearchArea.WEAPON) {
				researchStates = Constants.MAX_RESEARCH_STATE_WEAPON;
			}
			for (int i = 0; i < researchStates; i++) {
				Map<Resource, Double> researchResources = new HashMap<Resource, Double>();
				for (Resource researchResource : ResearchResources.RESEARCH_RESOURCES) {
					researchResources.put(researchResource, Double.valueOf(0));
				}
				resources.put(Integer.valueOf(i + 1), researchResources);
			}
			Constants.RESEARCH_RESOURCES.put(research, resources);
		}
		
		//only building resources needed for all but the WEAPON research area
		for (Resource resource : BuildingResources.BUILDING_RESOURCES) {
			//levels and areas are hard-coded here because this is just for testing
			Constants.RESEARCH_RESOURCES.get(ResearchArea.MILITARY).get(2).put(resource, Double.valueOf(1));
			Constants.RESEARCH_RESOURCES.get(ResearchArea.MILITARY).get(4).put(resource, Double.valueOf(2));
			
			Constants.RESEARCH_RESOURCES.get(ResearchArea.RESEARCH).get(2).put(resource, Double.valueOf(1));
			Constants.RESEARCH_RESOURCES.get(ResearchArea.RESEARCH).get(4).put(resource, Double.valueOf(2));
			
			Constants.RESEARCH_RESOURCES.get(ResearchArea.FTL).get(2).put(resource, Double.valueOf(1));
			Constants.RESEARCH_RESOURCES.get(ResearchArea.FTL).get(4).put(resource, Double.valueOf(2));
			
			Constants.RESEARCH_RESOURCES.get(ResearchArea.WEAPON).get(2).put(resource, Double.valueOf(0.9));
			Constants.RESEARCH_RESOURCES.get(ResearchArea.WEAPON).get(4).put(resource, Double.valueOf(1.4));
			Constants.RESEARCH_RESOURCES.get(ResearchArea.WEAPON).get(5).put(resource, Double.valueOf(1));
			Constants.RESEARCH_RESOURCES.get(ResearchArea.WEAPON).get(6).put(resource, Double.valueOf(1.8));
			Constants.RESEARCH_RESOURCES.get(ResearchArea.WEAPON).get(7).put(resource, Double.valueOf(1.4));
			Constants.RESEARCH_RESOURCES.get(ResearchArea.WEAPON).get(8).put(resource, Double.valueOf(1.8));
			Constants.RESEARCH_RESOURCES.get(ResearchArea.WEAPON).get(9).put(resource, Double.valueOf(1.8));
			Constants.RESEARCH_RESOURCES.get(ResearchArea.WEAPON).get(10).put(resource, Double.valueOf(3.6));
		}
		
		//for the research area WEAPON also SCIENTISTS are needed
		Constants.RESEARCH_RESOURCES.get(ResearchArea.WEAPON).get(7).put(Resource.SCIENTISTS, Double.valueOf(0.4));
		Constants.RESEARCH_RESOURCES.get(ResearchArea.WEAPON).get(8).put(Resource.SCIENTISTS, Double.valueOf(0.7));
		Constants.RESEARCH_RESOURCES.get(ResearchArea.WEAPON).get(9).put(Resource.SCIENTISTS, Double.valueOf(1));
		Constants.RESEARCH_RESOURCES.get(ResearchArea.WEAPON).get(10).put(Resource.SCIENTISTS, Double.valueOf(2));
	}
	
	public static void initBuildingCosts() {
		initBuildingCostsForColonies();
		initBuildingCostsForMines();
		initBuildingCostsForResearchCenters();
		initializeBuildingCostsForSpaceBuildings();
	}
	public static void initBuildingCostsForColonies() {
		Constants.BUILDING_COSTS_COLONIE[0][0] = 2;
		Constants.BUILDING_COSTS_COLONIE[0][1] = 1;
		Constants.BUILDING_COSTS_COLONIE[0][2] = 0;
		
		Constants.BUILDING_COSTS_COLONIE[1][0] = 2;
		Constants.BUILDING_COSTS_COLONIE[1][1] = 2;
		Constants.BUILDING_COSTS_COLONIE[1][2] = 0;
		
		Constants.BUILDING_COSTS_COLONIE[2][0] = 3;
		Constants.BUILDING_COSTS_COLONIE[2][1] = 3;
		Constants.BUILDING_COSTS_COLONIE[2][2] = 0;
		
		Constants.BUILDING_COSTS_COLONIE[3][0] = 3;
		Constants.BUILDING_COSTS_COLONIE[3][1] = 3;
		Constants.BUILDING_COSTS_COLONIE[3][2] = 2;
	}
	public static void initBuildingCostsForMines() {
		Constants.BUILDING_COSTS_MINE[0][0] = 2;
		Constants.BUILDING_COSTS_MINE[0][1] = 2;
		Constants.BUILDING_COSTS_MINE[0][2] = 0;
		
		Constants.BUILDING_COSTS_MINE[1][0] = 3;
		Constants.BUILDING_COSTS_MINE[1][1] = 3;
		Constants.BUILDING_COSTS_MINE[1][2] = 0;
		
		Constants.BUILDING_COSTS_MINE[2][0] = 5;
		Constants.BUILDING_COSTS_MINE[2][1] = 4;
		Constants.BUILDING_COSTS_MINE[2][2] = 1;
		
		Constants.BUILDING_COSTS_MINE[3][0] = 5;
		Constants.BUILDING_COSTS_MINE[3][1] = 5;
		Constants.BUILDING_COSTS_MINE[3][2] = 2;
	}
	public static void initBuildingCostsForResearchCenters() {
		Constants.BUILDING_COSTS_RESEARCH_CENTER[0][0] = 5;
		Constants.BUILDING_COSTS_RESEARCH_CENTER[0][1] = 5;
		Constants.BUILDING_COSTS_RESEARCH_CENTER[0][2] = 2;
		
		Constants.BUILDING_COSTS_RESEARCH_CENTER[1][0] = 6;
		Constants.BUILDING_COSTS_RESEARCH_CENTER[1][1] = 6;
		Constants.BUILDING_COSTS_RESEARCH_CENTER[1][2] = 2;
		
		Constants.BUILDING_COSTS_RESEARCH_CENTER[2][0] = 8;
		Constants.BUILDING_COSTS_RESEARCH_CENTER[2][1] = 8;
		Constants.BUILDING_COSTS_RESEARCH_CENTER[2][2] = 4;
		
		Constants.BUILDING_COSTS_RESEARCH_CENTER[3][0] = 9;
		Constants.BUILDING_COSTS_RESEARCH_CENTER[3][1] = 9;
		Constants.BUILDING_COSTS_RESEARCH_CENTER[3][2] = 4;
	}
	
	public static void initializeBuildingCostsForSpaceBuildings() {
		Constants.BUILDING_COSTS_SATELLITE[0][0] = 1;
		Constants.BUILDING_COSTS_SATELLITE[0][1] = 1;
		Constants.BUILDING_COSTS_SATELLITE[0][2] = 0;
		
		Constants.BUILDING_COSTS_DRONE[0][0] = 2;
		Constants.BUILDING_COSTS_DRONE[0][1] = 2;
		Constants.BUILDING_COSTS_DRONE[0][2] = 1;
		
		Constants.BUILDING_COSTS_SPACE_STATION[0][0] = 4;
		Constants.BUILDING_COSTS_SPACE_STATION[0][1] = 4;
		Constants.BUILDING_COSTS_SPACE_STATION[0][2] = 2;
	}
	
	public static void initAllianceValues() {
		Constants.ALLIANCE_MIN_PLANETS = 3;
		Constants.ALLIANCE_MIN_PLANETS_OTHER_PLAYERS = 2;
		Constants.ALLIANCE_MIN_BUILDINGS = 6;//normally its 7 but 6 is better for the tests...
		Constants.ALLIANCE_MIN_BUILDINGS_OTHER_PLAYERS = 2;
	}
	
	public static void initBuildingEarnings() {
		Constants.BUILDING_EARNINGS_DEPENDENT = new HashMap<Building, DependentResources>();
		Constants.BUILDING_EARNINGS_RESEARCH_POINTS = new HashMap<Building, Integer>();
		Constants.BUILDING_EARNINGS_SCIENTISTS = new HashMap<Building, Integer>();
		
		Constants.BUILDING_EARNINGS_DEPENDENT.put(Building.COLONY, new DependentResources(1, 0, 0));
		Constants.BUILDING_EARNINGS_DEPENDENT.put(Building.MINE, new DependentResources(4, 0, 0));
		Constants.BUILDING_EARNINGS_DEPENDENT.put(Building.TRADING_POST, new DependentResources(0, 2, 1));
		Constants.BUILDING_EARNINGS_DEPENDENT.put(Building.LABORATORY, new DependentResources(0, 0, 0));
		Constants.BUILDING_EARNINGS_DEPENDENT.put(Building.GOVERNMENT, new DependentResources(0, 0, 0));
		Constants.BUILDING_EARNINGS_DEPENDENT.put(Building.CITY, new DependentResources(2, 1, 1));
		Constants.BUILDING_EARNINGS_DEPENDENT.put(Building.RESEARCH_CENTER, new DependentResources(0, 0, 0));
		
		Constants.BUILDING_EARNINGS_RESEARCH_POINTS.put(Building.COLONY, 0);
		Constants.BUILDING_EARNINGS_RESEARCH_POINTS.put(Building.MINE, 0);
		Constants.BUILDING_EARNINGS_RESEARCH_POINTS.put(Building.TRADING_POST, 0);
		Constants.BUILDING_EARNINGS_RESEARCH_POINTS.put(Building.LABORATORY, 4);
		Constants.BUILDING_EARNINGS_RESEARCH_POINTS.put(Building.GOVERNMENT, 0);
		Constants.BUILDING_EARNINGS_RESEARCH_POINTS.put(Building.CITY, 0);
		Constants.BUILDING_EARNINGS_RESEARCH_POINTS.put(Building.RESEARCH_CENTER, 6);
		
		Constants.BUILDING_EARNINGS_SCIENTISTS.put(Building.COLONY, 0);
		Constants.BUILDING_EARNINGS_SCIENTISTS.put(Building.MINE, 0);
		Constants.BUILDING_EARNINGS_SCIENTISTS.put(Building.TRADING_POST, 0);
		Constants.BUILDING_EARNINGS_SCIENTISTS.put(Building.LABORATORY, 0);
		Constants.BUILDING_EARNINGS_SCIENTISTS.put(Building.GOVERNMENT, 0);
		Constants.BUILDING_EARNINGS_SCIENTISTS.put(Building.CITY, 0);
		Constants.BUILDING_EARNINGS_SCIENTISTS.put(Building.RESEARCH_CENTER, 1);
	}
	
	/**
	 * Initialize with some default cell positions (proportional to the coordinates; these are not the right positions).
	 */
	public static void initCellPositions() {
		Constants.CELL_COORDINATES = new HashMap<Position, int[]>((int) (170 * 1.3));//about 170 cells * default load factor
		for (int i = 0; i < 17; i++) {
			for (int j = 0; j < 9; j++) {
				Position pos = new Position(i, j);
				int[] boardPosition = new int[] {i * 120 + 30, j * 60 + 10};
				Constants.CELL_COORDINATES.put(pos, boardPosition);
			}
		}
	}
}
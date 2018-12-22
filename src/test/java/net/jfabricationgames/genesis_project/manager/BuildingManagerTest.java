package net.jfabricationgames.genesis_project.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import net.jfabricationgames.genesis_project.game.Board;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Constants;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.Planet;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.PlayerBuilding;
import net.jfabricationgames.genesis_project.user.User;

class BuildingManagerTest {
	
	private void initBuildingNumbers() {
		//initialize the BUILDING_NUMBERS field in the Constants class
		Constants.BUILDING_NUMBERS = new HashMap<Building, Integer>();
		
		Constants.BUILDING_NUMBERS.put(Building.COLONY, 10);
		Constants.BUILDING_NUMBERS.put(Building.MINE, 6);
		Constants.BUILDING_NUMBERS.put(Building.TRAIDING_POST, 6);
		Constants.BUILDING_NUMBERS.put(Building.LABORATORY, 5);
		Constants.BUILDING_NUMBERS.put(Building.GOVERMENT, 1);
		Constants.BUILDING_NUMBERS.put(Building.CITY, 2);
		Constants.BUILDING_NUMBERS.put(Building.RESEARCH_CENTER, 3);
		Constants.BUILDING_NUMBERS.put(Building.DRONE, 10);
		Constants.BUILDING_NUMBERS.put(Building.SPACE_STATION, 5);
		Constants.BUILDING_NUMBERS.put(Building.SATELLITE, 99);
	}
	
	private BuildingManager getBuildingManager() {
		initBuildingNumbers();
		Player player = new Player(new User("User"));
		return getBuildingManager(player);
	}
	private BuildingManager getBuildingManager(Player player) {
		BuildingManager buildingManager = new BuildingManager(player);
		return buildingManager;
	}
	
	private Field getFieldWithBuildings(PlayerBuilding building1, PlayerBuilding building2, PlayerBuilding building3) {
		Field field = new Field(new Board.Position(0, 0), Planet.GENESIS);
		if (building1 != null) {
			field.build(building1, 0);
		}
		if (building2 != null) {
			field.build(building2, 1);
		}
		if (building3 != null) {
			field.build(building3, 2);
		}
		return field;
	}
	
	@Test
	public void testGetNumBuildingsLeft() {
		BuildingManager manager = getBuildingManager();
		
		assertEquals(10, manager.getNumBuildingsLeft(Building.COLONY));
		assertEquals(1, manager.getNumBuildingsLeft(Building.GOVERMENT));
		assertEquals(2, manager.getNumBuildingsLeft(Building.CITY));
	}
	
	@Test
	public void testGetNumBuildingsOnField() {
		BuildingManager manager = getBuildingManager();
		
		manager.numBuildingsLeft.put(Building.COLONY, 5);
		manager.numBuildingsLeft.put(Building.CITY, 1);
		
		assertEquals(5, manager.getNumBuildingsOnField(Building.COLONY));
		assertEquals(0, manager.getNumBuildingsOnField(Building.GOVERMENT));
		assertEquals(1, manager.getNumBuildingsOnField(Building.CITY));
	}
	
	@Test
	public void testFindFirstPossibleBuildingPosition_emptyPlanet() {
		BuildingManager manager = getBuildingManager();
		Field field = getFieldWithBuildings(null, null, null);
		
		assertEquals(0, manager.findFirstPossibleBuildingPosition(Building.COLONY, field));
		assertEquals(-1, manager.findFirstPossibleBuildingPosition(Building.CITY, field));
		assertEquals(-1, manager.findFirstPossibleBuildingPosition(Building.DRONE, field));
	}
	
	@Test
	public void testFindFirstPossibleBuildingPosition_upgradeBuilding() {
		initBuildingNumbers();
		Player player1 = new Player(new User("Player1"));
		Player player2 = new Player(new User("Player2"));
		BuildingManager manager1 = getBuildingManager(player1);
		BuildingManager manager2 = getBuildingManager(player2);
		Field field = getFieldWithBuildings(new PlayerBuilding(Building.COLONY, player1), new PlayerBuilding(Building.LABORATORY, player2), null);
		
		assertEquals(0, manager1.findFirstPossibleBuildingPosition(Building.MINE, field));
		assertEquals(0, manager1.findFirstPossibleBuildingPosition(Building.TRAIDING_POST, field));
		assertEquals(0, manager1.findFirstPossibleBuildingPosition(Building.LABORATORY, field));
		
		assertEquals(-1, manager1.findFirstPossibleBuildingPosition(Building.RESEARCH_CENTER, field));
		assertEquals(1, manager2.findFirstPossibleBuildingPosition(Building.RESEARCH_CENTER, field));
		
		assertEquals(-1, manager1.findFirstPossibleBuildingPosition(Building.CITY, field));
		assertEquals(-1, manager2.findFirstPossibleBuildingPosition(Building.CITY, field));
		assertEquals(-1, manager1.findFirstPossibleBuildingPosition(Building.DRONE, field));
		assertEquals(-1, manager2.findFirstPossibleBuildingPosition(Building.SPACE_STATION, field));
	}
	
	@Test
	public void testFindFirstPossibleBuildingPosition_emptySpaceField() {
		initBuildingNumbers();
		Player player1 = new Player(new User("Player1"));
		Player player2 = new Player(new User("Player2"));
		BuildingManager manager1 = getBuildingManager(player1);
		BuildingManager manager2 = getBuildingManager(player2);
		Field field = new Field(new Board.Position(0, 0), null);
		
		assertEquals(0, manager1.findFirstPossibleBuildingPosition(Building.DRONE, field));
		assertEquals(0, manager2.findFirstPossibleBuildingPosition(Building.DRONE, field));
		assertEquals(0, manager1.findFirstPossibleBuildingPosition(Building.SATELLITE, field));
		assertEquals(0, manager2.findFirstPossibleBuildingPosition(Building.SATELLITE, field));

		assertEquals(-1, manager1.findFirstPossibleBuildingPosition(Building.SPACE_STATION, field));
		assertEquals(-1, manager2.findFirstPossibleBuildingPosition(Building.SPACE_STATION, field));
		assertEquals(-1, manager1.findFirstPossibleBuildingPosition(Building.COLONY, field));
		assertEquals(-1, manager2.findFirstPossibleBuildingPosition(Building.COLONY, field));
		assertEquals(-1, manager1.findFirstPossibleBuildingPosition(Building.MINE, field));
		assertEquals(-1, manager2.findFirstPossibleBuildingPosition(Building.GOVERMENT, field));
	}
	
	@Test
	public void testFindFirstPossibleBuildingPosition_spaceFieldWithSatellitesAndDrones() {
		initBuildingNumbers();
		Player player1 = new Player(new User("Player1"));
		Player player2 = new Player(new User("Player2"));
		BuildingManager manager1 = getBuildingManager(player1);
		BuildingManager manager2 = getBuildingManager(player2);
		Field field = new Field(new Board.Position(0, 0), null);
		
		field.build(new PlayerBuilding(Building.DRONE, player1), 0);
		field.build(new PlayerBuilding(Building.SATELLITE, player2), 0);
		
		assertEquals(0, manager1.findFirstPossibleBuildingPosition(Building.SATELLITE, field));
		assertEquals(-1, manager1.findFirstPossibleBuildingPosition(Building.DRONE, field));
		assertEquals(0, manager1.findFirstPossibleBuildingPosition(Building.SPACE_STATION, field));

		assertEquals(-1, manager2.findFirstPossibleBuildingPosition(Building.SATELLITE, field));
		assertEquals(-1, manager2.findFirstPossibleBuildingPosition(Building.DRONE, field));
		assertEquals(-1, manager2.findFirstPossibleBuildingPosition(Building.SPACE_STATION, field));
	}
	
	@Test
	public void testCanBuild_emptyPlanet() {
		BuildingManager manager = getBuildingManager();
		Field field = getFieldWithBuildings(null, null, null);
		
		assertTrue(manager.canBuild(Building.COLONY, field));
		assertFalse(manager.canBuild(Building.GOVERMENT, field));
		assertFalse(manager.canBuild(Building.DRONE, field));
	}
	
	@Test
	public void testCanBuild_noBuildingsLeft() {
		BuildingManager manager = getBuildingManager();
		Field field = getFieldWithBuildings(null, null, null);
		manager.numBuildingsLeft.put(Building.COLONY, 0);
		
		assertFalse(manager.canBuild(Building.COLONY, field));
	}
	
	@Test
	public void testBuild() {
		BuildingManager manager = getBuildingManager();
		Field field = getFieldWithBuildings(null, null, new PlayerBuilding(Building.COLONY, manager.getPlayer()));
		
		manager.build(Building.COLONY, field);
		manager.build(Building.MINE, field);
		manager.build(Building.TRAIDING_POST, field);
		
		assertEquals(Building.MINE, field.getBuildings()[0].getBuilding());
		assertNull(field.getBuildings()[1]);
		assertEquals(Building.TRAIDING_POST, field.getBuildings()[2].getBuilding());
	}
	
	@Test
	public void testBuild_noValidBuilding() {
		BuildingManager manager = getBuildingManager();
		Field field = getFieldWithBuildings(null, new PlayerBuilding(Building.COLONY, manager.getPlayer()), null);
		
		assertThrows(IllegalArgumentException.class, () -> manager.build(Building.GOVERMENT, field));
		assertThrows(IllegalArgumentException.class, () -> manager.build(Building.DRONE, field));
	}
}
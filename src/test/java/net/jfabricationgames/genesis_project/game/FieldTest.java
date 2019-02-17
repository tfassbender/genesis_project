package net.jfabricationgames.genesis_project.game;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class FieldTest {
	
	private void initBuildingNumbers() {
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
	
	@Test
	public void testBuild() {
		initBuildingNumbers();
		Player player = mock(Player.class);
		Field field = new Field(new Board.Position(0, 0), Planet.GENESIS, 0);
		
		field.build(new PlayerBuilding(Building.CITY, player), 0);
		field.build(new PlayerBuilding(Building.COLONY, player), 0);//overrides the city
		field.build(new PlayerBuilding(Building.GOVERNMENT, player), 1);
		
		assertEquals(Building.COLONY, field.getBuildings()[0].getBuilding());
		assertEquals(Building.GOVERNMENT, field.getBuildings()[1].getBuilding());
		assertNull(field.getBuildings()[2]);
	}
	
	@Test
	public void testBuild_spaceStation_replacesDrone() {
		initBuildingNumbers();
		Player player = mock(Player.class);
		Field field = new Field(new Board.Position(0, 0), null, 0);
		
		field.build(new PlayerBuilding(Building.DRONE, player), 0);
		
		Optional<Building> droneBeforeSpaceStation = field.getSpaceBuildings().stream().filter((b) -> b.getBuilding() == Building.DRONE)
				.map((b) -> b.getBuilding()).findAny();
		
		field.build(new PlayerBuilding(Building.SPACE_STATION, player), 1);//removes the drone (regardless of position)
		
		Optional<Building> droneAfterSpaceStation = field.getSpaceBuildings().stream().filter((b) -> b.getBuilding() == Building.DRONE)
				.map((b) -> b.getBuilding()).findAny();
		
		assertTrue(droneBeforeSpaceStation.isPresent());
		assertFalse(droneAfterSpaceStation.isPresent());
	}
	
	@Test
	public void testNumBuildingSpaces_centerPlanet() {
		initBuildingNumbers();
		Field field = new Field(new Board.Position(0, 0), null, 0);
		Field centerField = new Field(new Board.Position(0, 0), Planet.CENTER, 5);
		
		assertEquals(3, field.getBuildings().length);
		assertEquals(5, centerField.getBuildings().length);
	}
}
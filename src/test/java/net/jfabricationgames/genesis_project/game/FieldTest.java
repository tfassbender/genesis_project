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
	
	@Test
	public void testDistance() {
		Field origin = new Field(new Board.Position(0, 0), null, 0);
		Field originOdd = new Field(new Board.Position(1, 0), null, 0);
		Field field10 = new Field(new Board.Position(1, 0), null, 0);
		Field field31 = new Field(new Board.Position(3, 1), null, 0);
		Field field11 = new Field(new Board.Position(1, 1), null, 0);
		Field field22 = new Field(new Board.Position(2, 2), null, 0);
		Field field32 = new Field(new Board.Position(3, 2), null, 0);
		Field field03 = new Field(new Board.Position(0, 3), null, 0);
		
		//distance to the field itself
		assertEquals(0, origin.distanceTo(origin));
		assertEquals(0, originOdd.distanceTo(originOdd));
		
		//distance from an even start
		assertEquals(1, origin.distanceTo(field10));
		assertEquals(3, origin.distanceTo(field31));
		assertEquals(2, origin.distanceTo(field11));
		assertEquals(3, origin.distanceTo(field22));
		assertEquals(4, origin.distanceTo(field32));
		assertEquals(3, origin.distanceTo(field03));
		
		//distances from an odd start
		assertEquals(0, originOdd.distanceTo(field10));
		assertEquals(2, originOdd.distanceTo(field31));
		assertEquals(1, originOdd.distanceTo(field11));
		assertEquals(2, originOdd.distanceTo(field22));
		assertEquals(3, originOdd.distanceTo(field32));
		assertEquals(3, originOdd.distanceTo(field03));
		
		//some commutative tests
		assertEquals(field11.distanceTo(origin), origin.distanceTo(field11));
		assertEquals(field22.distanceTo(origin), origin.distanceTo(field22));
		assertEquals(field32.distanceTo(origin), origin.distanceTo(field32));
		assertEquals(field03.distanceTo(origin), origin.distanceTo(field03));
		assertEquals(field11.distanceTo(originOdd), originOdd.distanceTo(field11));
		assertEquals(field22.distanceTo(originOdd), originOdd.distanceTo(field22));
		assertEquals(field32.distanceTo(originOdd), originOdd.distanceTo(field32));
		assertEquals(field03.distanceTo(originOdd), originOdd.distanceTo(field03));
	}
}
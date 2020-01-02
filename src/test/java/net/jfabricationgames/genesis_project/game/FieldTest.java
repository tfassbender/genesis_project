package net.jfabricationgames.genesis_project.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.genesis_project.manager.IAllianceManager;
import net.jfabricationgames.genesis_project.testUtils.GameCreationUtil;

class FieldTest {
	
	private void initBuildingNumbers() {
		//initialize the BUILDING_NUMBERS field in the Constants class
		Constants.getInstance().BUILDING_NUMBERS = new HashMap<Building, Integer>();
		
		Constants.getInstance().BUILDING_NUMBERS.put(Building.COLONY, 10);
		Constants.getInstance().BUILDING_NUMBERS.put(Building.MINE, 6);
		Constants.getInstance().BUILDING_NUMBERS.put(Building.TRADING_POST, 6);
		Constants.getInstance().BUILDING_NUMBERS.put(Building.LABORATORY, 5);
		Constants.getInstance().BUILDING_NUMBERS.put(Building.GOVERNMENT, 1);
		Constants.getInstance().BUILDING_NUMBERS.put(Building.CITY, 2);
		Constants.getInstance().BUILDING_NUMBERS.put(Building.RESEARCH_CENTER, 3);
		Constants.getInstance().BUILDING_NUMBERS.put(Building.DRONE, 10);
		Constants.getInstance().BUILDING_NUMBERS.put(Building.SPACE_STATION, 5);
		Constants.getInstance().BUILDING_NUMBERS.put(Building.SATELLITE, 99);
	}
	
	protected static Game getInitializedGameWithDefenseBuildings() {
		//create an initialized game with planets, buildings, ... (drone on 7|0, space station on 6|0)
		Game game = GameCreationUtil.createGame();
		
		//add some additional buildings (player1 buildings have default range of 2; player2 buildings have range of 0)
		Player player1 = game.getLocalPlayer();
		Board board = game.getBoard();
		board.getFields().get(new Position(1, 1)).build(new PlayerBuilding(Building.DRONE, player1), 0);
		board.getFields().get(new Position(3, 3)).build(new PlayerBuilding(Building.SPACE_STATION, player1), 0);
		board.getFields().get(new Position(0, 3)).build(new PlayerBuilding(Building.SPACE_STATION, player1), 0);
		return game;
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
	
	@Test
	public void testHasDefenseBuilding() {
		Field fieldDrone = new Field(new Board.Position(0, 0), Planet.GENESIS, 0);
		Field fieldSpaceStation = new Field(new Board.Position(1, 0), Planet.GENESIS, 0);
		Field fieldColony = new Field(new Board.Position(2, 0), Planet.GENESIS, 0);
		Field fieldSatellites = new Field(new Board.Position(1, 0), Planet.GENESIS, 0);
		Field fieldEmpty = new Field(new Board.Position(2, 0), Planet.GENESIS, 0);
		Player player1 = mock(Player.class);
		Player player2 = mock(Player.class);
		fieldDrone.build(new PlayerBuilding(Building.DRONE, player1), 0);
		fieldSpaceStation.build(new PlayerBuilding(Building.SPACE_STATION, player2), 0);
		fieldColony.build(new PlayerBuilding(Building.COLONY, player1), 0);
		fieldSatellites.build(new PlayerBuilding(Building.SATELLITE, player1), 0);
		fieldSatellites.build(new PlayerBuilding(Building.SATELLITE, player2), 1);
		
		assertTrue(fieldDrone.hasDefenseBuilding());
		assertTrue(fieldSpaceStation.hasDefenseBuilding());
		assertFalse(fieldColony.hasDefenseBuilding());
		assertFalse(fieldSatellites.hasDefenseBuilding());
		assertFalse(fieldEmpty.hasDefenseBuilding());
	}
	
	@Test
	public void testGetDefense() {
		Game game = getInitializedGameWithDefenseBuildings();
		
		assertEquals(2, game.getBoard().getFields().get(new Position(0, 0)).calculateDefense(game));
		assertEquals(12, game.getBoard().getFields().get(new Position(2, 3)).calculateDefense(game));
		assertEquals(10, game.getBoard().getFields().get(new Position(2, 4)).calculateDefense(game));
		assertEquals(5, game.getBoard().getFields().get(new Position(3, 3)).calculateDefense(game));
	}
	
	@Test
	public void testGetAlliances() {
		Game game = GameCreationUtil.createGame();
		
		//add two alliances
		Player player = game.getLocalPlayer();
		IAllianceManager allianceManager = player.getAllianceManager();
		Map<Position, Field> fields = game.getBoard().getFields();
		Field planet1 = fields.get(new Position(0, 0));
		Field planet2 = fields.get(new Position(1, 1));
		Field planet3 = fields.get(new Position(0, 3));
		Field spaceField1 = fields.get(new Position(1, 0));
		Field spaceField2 = fields.get(new Position(0, 1));
		Field spaceField3 = fields.get(new Position(0, 2));
		allianceManager.addAlliance(Arrays.asList(planet1, planet2, planet3), Arrays.asList(spaceField2, spaceField3), AllianceBonus.POINTS, 0);
		allianceManager.addAlliance(Arrays.asList(planet1, planet2), Arrays.asList(spaceField1), AllianceBonus.SCIENTISTS, 0);
		
		List<Alliance> alliancesPlanet1 = planet1.getAlliances(game);
		List<Alliance> alliancesPlanet3 = planet3.getAlliances(game);
		List<Alliance> alliancesSpaceField1 = spaceField1.getAlliances(game);
		List<Alliance> alliancesSpaceField2 = spaceField2.getAlliances(game);
		List<Alliance> alliancesPlanet4 = fields.get(new Position(2, 0)).getAlliances(game);
		List<Alliance> alliancesSpaceField4 = fields.get(new Position(2, 2)).getAlliances(game);
		
		assertEquals(2, alliancesPlanet1.size());
		assertEquals(1, alliancesPlanet3.size());
		assertEquals(1, alliancesSpaceField1.size());
		assertEquals(1, alliancesSpaceField2.size());
		assertEquals(0, alliancesPlanet4.size());
		assertEquals(0, alliancesSpaceField4.size());
	}
	
	@Test
	public void testGetPlayerBuildings() {
		Player player1 = mock(Player.class);
		Player player2 = mock(Player.class);
		
		Field field1 = new Field(new Position(0, 0), Planet.GENESIS, 0);
		Field field2 = new Field(new Position(0, 1), Planet.GENESIS, 0);
		Field field3 = new Field(new Position(0, 2), Planet.GENESIS, 0);
		
		field1.build(new PlayerBuilding(Building.COLONY, player1), 0);
		field1.build(new PlayerBuilding(Building.COLONY, player1), 1);
		field1.build(new PlayerBuilding(Building.MINE, player1), 2);
		field2.build(new PlayerBuilding(Building.COLONY, player1), 0);
		field2.build(new PlayerBuilding(Building.COLONY, player2), 1);
		field2.build(new PlayerBuilding(Building.COLONY, player1), 2);
		
		assertEquals(3, field1.getPlayerBuildings(player1).size());
		assertEquals(2, field2.getPlayerBuildings(player1).size());
		assertEquals(0, field3.getPlayerBuildings(player1).size());
		
		assertEquals(0, field1.getPlayerBuildings(player2).size());
		assertEquals(1, field2.getPlayerBuildings(player2).size());
		assertEquals(0, field3.getPlayerBuildings(player2).size());
		
		assertTrue(field1.getPlayerBuildings(player1).contains(new PlayerBuilding(Building.COLONY, player1)));
		assertTrue(field1.getPlayerBuildings(player1).contains(new PlayerBuilding(Building.MINE, player1)));
		assertFalse(field1.getPlayerBuildings(player1).contains(new PlayerBuilding(Building.GOVERNMENT, player1)));
	}
	
	@Test
	public void testGetOtherPlayersBuildings() {
		Player player1 = mock(Player.class);
		Player player2 = mock(Player.class);
		
		Field field1 = new Field(new Position(0, 0), Planet.GENESIS, 0);
		Field field2 = new Field(new Position(0, 1), Planet.GENESIS, 0);
		Field field3 = new Field(new Position(0, 2), Planet.GENESIS, 0);
		
		field1.build(new PlayerBuilding(Building.COLONY, player1), 0);
		field1.build(new PlayerBuilding(Building.COLONY, player1), 1);
		field1.build(new PlayerBuilding(Building.MINE, player1), 2);
		field2.build(new PlayerBuilding(Building.COLONY, player1), 0);
		field2.build(new PlayerBuilding(Building.COLONY, player2), 1);
		field2.build(new PlayerBuilding(Building.COLONY, player1), 2);
		
		assertEquals(0, field1.getOtherPlayersBuildings(player1).size());
		assertEquals(1, field2.getOtherPlayersBuildings(player1).size());
		assertEquals(0, field3.getOtherPlayersBuildings(player1).size());
		
		assertEquals(3, field1.getOtherPlayersBuildings(player2).size());
		assertEquals(2, field2.getOtherPlayersBuildings(player2).size());
		assertEquals(0, field3.getOtherPlayersBuildings(player2).size());
		
		assertTrue(field1.getOtherPlayersBuildings(player2).contains(new PlayerBuilding(Building.COLONY, player1)));
		assertTrue(field1.getOtherPlayersBuildings(player2).contains(new PlayerBuilding(Building.MINE, player1)));
		assertFalse(field1.getOtherPlayersBuildings(player2).contains(new PlayerBuilding(Building.GOVERNMENT, player1)));
	}
	
	@Test
	public void testContainsPlayersBuildings() {
		Game game = GameCreationUtil.createGame();
		
		Field fieldWithoutBuildings = new Field(new Position(0, 0), Planet.BLACK, 3);
		Field fieldWithPlayersBuildings = new Field(new Position(0, 0), Planet.BLACK, 3);
		Field fieldWithOtherPlayersBuildings = new Field(new Position(0, 0), Planet.BLACK, 3);
		Field fieldWithMultiplePlayersBuildings = new Field(new Position(0, 0), Planet.BLACK, 3);
		
		fieldWithPlayersBuildings.build(new PlayerBuilding(Building.COLONY, game.getPlayers().get(0)), 0);
		fieldWithOtherPlayersBuildings.build(new PlayerBuilding(Building.COLONY, game.getPlayers().get(1)), 1);
		fieldWithMultiplePlayersBuildings.build(new PlayerBuilding(Building.COLONY, game.getPlayers().get(0)), 0);
		fieldWithMultiplePlayersBuildings.build(new PlayerBuilding(Building.COLONY, game.getPlayers().get(1)), 1);
		
		PlayerClass localPlayersClass = game.getPlayers().get(0).getPlayerClass();
		assertFalse(fieldWithoutBuildings.containsPlayersBuildings(localPlayersClass));
		assertTrue(fieldWithPlayersBuildings.containsPlayersBuildings(localPlayersClass));
		assertFalse(fieldWithOtherPlayersBuildings.containsPlayersBuildings(localPlayersClass));
		assertTrue(fieldWithMultiplePlayersBuildings.containsPlayersBuildings(localPlayersClass));
	}
	
	@Test
	public void testContainsOtherPlayersBuildings() {
		Game game = GameCreationUtil.createGame();
		
		Field fieldWithoutBuildings = new Field(new Position(0, 0), Planet.BLACK, 3);
		Field fieldWithPlayersBuildings = new Field(new Position(0, 0), Planet.BLACK, 3);
		Field fieldWithOtherPlayersBuildings = new Field(new Position(0, 0), Planet.BLACK, 3);
		Field fieldWithMultiplePlayersBuildings = new Field(new Position(0, 0), Planet.BLACK, 3);
		
		fieldWithPlayersBuildings.build(new PlayerBuilding(Building.COLONY, game.getPlayers().get(0)), 0);
		fieldWithOtherPlayersBuildings.build(new PlayerBuilding(Building.COLONY, game.getPlayers().get(1)), 1);
		fieldWithMultiplePlayersBuildings.build(new PlayerBuilding(Building.COLONY, game.getPlayers().get(0)), 0);
		fieldWithMultiplePlayersBuildings.build(new PlayerBuilding(Building.COLONY, game.getPlayers().get(1)), 1);
		
		PlayerClass localPlayersClass = game.getPlayers().get(0).getPlayerClass();
		assertFalse(fieldWithoutBuildings.containsOtherPlayersBuildings(localPlayersClass));
		assertFalse(fieldWithPlayersBuildings.containsOtherPlayersBuildings(localPlayersClass));
		assertTrue(fieldWithOtherPlayersBuildings.containsOtherPlayersBuildings(localPlayersClass));
		assertTrue(fieldWithMultiplePlayersBuildings.containsOtherPlayersBuildings(localPlayersClass));
	}
}
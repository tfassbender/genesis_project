package net.jfabricationgames.genesis_project.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;

import net.jfabricationgames.genesis_project.game.Board;
import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.BuildingResources;
import net.jfabricationgames.genesis_project.game.CompleteResources;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.Planet;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.PlayerBuilding;
import net.jfabricationgames.genesis_project.game.PlayerClass;
import net.jfabricationgames.genesis_project.testUtils.ConstantsInitializerUtil;
import net.jfabricationgames.genesis_project.testUtils.GameCreationUtil;

class BuildingManagerTest {
	
	private BuildingManager getBuildingManager() {
		ConstantsInitializerUtil.initBuildingNumbers();
		Player player = mock(Player.class);
		when(player.getPlayerClass()).thenReturn(PlayerClass.ENCOR);
		IResourceManager resourceManager = mock(ResourceManager.class);
		when(resourceManager.isResourcesAvailable(any(BuildingResources.class))).thenReturn(true);
		when(player.getResourceManager()).thenReturn(resourceManager);
		Game game = mock(Game.class);
		Board board = new Board();
		when(game.getBoard()).thenReturn(board);
		when(player.getGame()).thenReturn(game);
		return getBuildingManager(player);
	}
	private BuildingManager getBuildingManager(Player player) {
		BuildingManager buildingManager = new BuildingManager(player);
		return buildingManager;
	}
	
	private Field getFieldWithBuildings(PlayerBuilding building1, PlayerBuilding building2, PlayerBuilding building3) {
		Field field = new Field(new Board.Position(0, 0), Planet.GENESIS, 0);
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
		assertEquals(1, manager.getNumBuildingsLeft(Building.GOVERNMENT));
		assertEquals(2, manager.getNumBuildingsLeft(Building.CITY));
	}
	
	@Test
	public void testGetNumBuildingsOnField() {
		BuildingManager manager = getBuildingManager();
		
		manager.setNumBuildingsLeft(Building.COLONY, 5);
		manager.setNumBuildingsLeft(Building.CITY, 1);
		
		assertEquals(5, manager.getNumBuildingsOnField(Building.COLONY));
		assertEquals(0, manager.getNumBuildingsOnField(Building.GOVERNMENT));
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
		ConstantsInitializerUtil.initBuildingNumbers();
		Player player1 = mock(Player.class);
		Player player2 = mock(Player.class);
		BuildingManager manager1 = getBuildingManager(player1);
		BuildingManager manager2 = getBuildingManager(player2);
		Field field = getFieldWithBuildings(new PlayerBuilding(Building.COLONY, player1), new PlayerBuilding(Building.LABORATORY, player2), null);
		
		assertEquals(0, manager1.findFirstPossibleBuildingPosition(Building.MINE, field));
		assertEquals(0, manager1.findFirstPossibleBuildingPosition(Building.TRADING_POST, field));
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
		ConstantsInitializerUtil.initBuildingNumbers();
		Player player1 = mock(Player.class);
		Player player2 = mock(Player.class);
		BuildingManager manager1 = getBuildingManager(player1);
		BuildingManager manager2 = getBuildingManager(player2);
		Field field = new Field(new Board.Position(0, 0), null, 0);
		
		assertEquals(0, manager1.findFirstPossibleBuildingPosition(Building.DRONE, field));
		assertEquals(0, manager2.findFirstPossibleBuildingPosition(Building.DRONE, field));
		assertEquals(0, manager1.findFirstPossibleBuildingPosition(Building.SATELLITE, field));
		assertEquals(0, manager2.findFirstPossibleBuildingPosition(Building.SATELLITE, field));
		
		assertEquals(-1, manager1.findFirstPossibleBuildingPosition(Building.SPACE_STATION, field));
		assertEquals(-1, manager2.findFirstPossibleBuildingPosition(Building.SPACE_STATION, field));
		assertEquals(-1, manager1.findFirstPossibleBuildingPosition(Building.COLONY, field));
		assertEquals(-1, manager2.findFirstPossibleBuildingPosition(Building.COLONY, field));
		assertEquals(-1, manager1.findFirstPossibleBuildingPosition(Building.MINE, field));
		assertEquals(-1, manager2.findFirstPossibleBuildingPosition(Building.GOVERNMENT, field));
	}
	
	@Test
	public void testFindFirstPossibleBuildingPosition_spaceFieldWithSatellitesAndDrones() {
		ConstantsInitializerUtil.initBuildingNumbers();
		Player player1 = mock(Player.class);
		Player player2 = mock(Player.class);
		BuildingManager manager1 = getBuildingManager(player1);
		BuildingManager manager2 = getBuildingManager(player2);
		Field field = new Field(new Board.Position(0, 0), null, 0);
		
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
		assertFalse(manager.canBuild(Building.GOVERNMENT, field));
		assertFalse(manager.canBuild(Building.DRONE, field));
	}
	
	@Test
	public void testCanBuild_noBuildingsLeft() {
		BuildingManager manager = getBuildingManager();
		Field field = getFieldWithBuildings(null, null, null);
		manager.setNumBuildingsLeft(Building.COLONY, 0);
		
		assertFalse(manager.canBuild(Building.COLONY, field));
	}
	
	@Test
	public void testBuild() {
		Player player1 = mock(Player.class);
		when(player1.getPlayerClass()).thenReturn(PlayerClass.ENCOR);
		IResourceManager resourceManager = mock(ResourceManager.class);
		when(player1.getResourceManager()).thenReturn(resourceManager);
		BuildingManager manager = getBuildingManager(player1);
		Field field = getFieldWithBuildings(null, null, new PlayerBuilding(Building.COLONY, player1));
		
		manager.build(Building.COLONY, field);
		manager.build(Building.MINE, field);
		manager.build(Building.TRADING_POST, field);
		
		assertEquals(Building.MINE, field.getBuildings()[0].getBuilding());
		assertNull(field.getBuildings()[1]);
		assertEquals(Building.TRADING_POST, field.getBuildings()[2].getBuilding());
		//verify the resources were reduced (or tried to, because it's a mock)
		verify(resourceManager, times(3)).reduceResources(any(BuildingResources.class));
	}
	
	@Test
	public void testPlaceStartBuildings() {
		Player player1 = mock(Player.class);
		when(player1.getPlayerClass()).thenReturn(PlayerClass.ENCOR);
		IResourceManager resourceManager = mock(ResourceManager.class);
		when(player1.getResourceManager()).thenReturn(resourceManager);
		BuildingManager manager = getBuildingManager(player1);
		Field field = getFieldWithBuildings(null, null, new PlayerBuilding(Building.COLONY, player1));
		
		manager.placeStartBuilding(Building.COLONY, field);
		manager.placeStartBuilding(Building.MINE, field);
		manager.placeStartBuilding(Building.TRADING_POST, field);
		
		assertEquals(Building.MINE, field.getBuildings()[0].getBuilding());
		assertNull(field.getBuildings()[1]);
		assertEquals(Building.TRADING_POST, field.getBuildings()[2].getBuilding());
		//verify the resources were NOT reduced
		verify(resourceManager, times(0)).reduceResources(any(BuildingResources.class));
	}
	
	@Test
	public void testBuild_noValidBuilding() {
		Player player1 = mock(Player.class);
		BuildingManager manager = getBuildingManager(player1);
		Field field = getFieldWithBuildings(null, new PlayerBuilding(Building.COLONY, player1), null);
		
		assertThrows(IllegalArgumentException.class, () -> manager.build(Building.GOVERNMENT, field));
		assertThrows(IllegalArgumentException.class, () -> manager.build(Building.DRONE, field));
	}
	
	@Test
	public void testBuildOnInvalidFields() {
		Player player1 = mock(Player.class);
		BuildingManager manager = getBuildingManager(player1);
		Field planetField = new Field(new Position(0, 0), Planet.BLACK, 0);
		Field spaceField = new Field(new Position(3, 3), null, 0);
		
		assertThrows(IllegalArgumentException.class, () -> manager.build(Building.COLONY, spaceField));
		assertThrows(IllegalArgumentException.class, () -> manager.build(Building.SATELLITE, planetField));
	}
	
	@Test
	public void testIsResourcesAvailable() {
		ConstantsInitializerUtil.initBuildingCostsForColonies();
		ConstantsInitializerUtil.initBuildingNumbers();
		
		Player player = mock(Player.class);
		when(player.getPlayerClass()).thenReturn(PlayerClass.ENCOR);//blue class
		IResourceManager resourceManager = new ResourceManager(player);
		when(player.getResourceManager()).thenReturn(resourceManager);
		resourceManager.addResources(new BuildingResources(3, 3, 1));//enough for colonies on 0, 1, and 2 distance planets, but not 3 distance
		//create a board that is needed (use mocks to get it)
		Game game = mock(Game.class);
		Board board = new Board();
		when(game.getBoard()).thenReturn(board);
		when(player.getGame()).thenReturn(game);
		
		BuildingManager manager = new BuildingManager(player);
		
		Field fieldBlue = new Field(new Position(0, 0), Planet.BLUE, 0);
		Field fieldGenesis = new Field(new Position(0, 0), Planet.GENESIS, 0);
		Field fieldCenter = new Field(new Position(0, 0), Planet.CENTER, 5);
		Field fieldGreen = new Field(new Position(0, 0), Planet.GREEN, 0);
		Field fieldGray = new Field(new Position(0, 0), Planet.GRAY, 0);
		Field fieldBlack = new Field(new Position(0, 0), Planet.BLACK, 0);
		Field fieldYellow = new Field(new Position(0, 0), Planet.YELLOW, 0);
		Field fieldRed = new Field(new Position(0, 0), Planet.RED, 0);
		
		assertTrue(manager.canBuild(Building.COLONY, fieldBlue));
		assertTrue(manager.canBuild(Building.COLONY, fieldGenesis));
		assertTrue(manager.canBuild(Building.COLONY, fieldCenter));
		assertTrue(manager.canBuild(Building.COLONY, fieldGreen));
		assertTrue(manager.canBuild(Building.COLONY, fieldGray));
		assertFalse(manager.canBuild(Building.COLONY, fieldBlack));
		assertTrue(manager.canBuild(Building.COLONY, fieldYellow));
		assertTrue(manager.canBuild(Building.COLONY, fieldRed));
	}
	
	@Test
	public void testIsResourcesAvailable_spaceBuildings() {
		ConstantsInitializerUtil.initializeBuildingCostsForSpaceBuildings();
		ConstantsInitializerUtil.initBuildingNumbers();
		
		Player player = mock(Player.class);
		when(player.getPlayerClass()).thenReturn(PlayerClass.ENCOR);//blue class
		IResourceManager resourceManager = new ResourceManager(player);
		when(player.getResourceManager()).thenReturn(resourceManager);
		resourceManager.addResources(new BuildingResources(3, 3, 1));//enough for satellites and drones but not for space stations
		//create a board that is needed (use mocks to get it)
		Game game = mock(Game.class);
		Board board = new Board();
		when(game.getBoard()).thenReturn(board);
		when(player.getGame()).thenReturn(game);
		
		BuildingManager manager = new BuildingManager(player);
		
		Field spaceField = new Field(new Position(0, 0), null, 0);
		
		assertTrue(manager.canBuild(Building.SATELLITE, spaceField));
		assertTrue(manager.canBuild(Building.DRONE, spaceField));
		assertFalse(manager.canBuild(Building.SPACE_STATION, spaceField));
	}
	
	@Test
	public void testGetNextTurnsStartingResources() {
		ConstantsInitializerUtil.initBuildingEarnings();
		Game game = GameCreationUtil.createGame();
		Player player1 = game.getLocalPlayer();
		IBuildingManager manager = player1.getBuildingManager();
		
		CompleteResources earnings = manager.getNextTurnsStartingResources();
		
		assertEquals(new CompleteResources(7, 3, 10, 0, 8, 0), earnings);
	}
	
	@Test
	public void testIsFieldReachable() {
		Player player = mock(Player.class);
		//create a board that is needed (use mocks to get it)
		Game game = mock(Game.class);
		Board board = GameCreationUtil.getBoardWithFields(10, 10);
		when(game.getBoard()).thenReturn(board);
		when(player.getGame()).thenReturn(game);
		ResourceManager resourceManager = mock(ResourceManager.class);
		when(resourceManager.getFTL()).thenReturn(2);//FTL level 2
		when(player.getResourceManager()).thenReturn(resourceManager);
		
		//add some planets and buildings to the board
		Field field00 = new Field(new Position(0, 0), Planet.GENESIS, 0);
		field00.build(new PlayerBuilding(Building.COLONY, player), 0);
		board.getFields().put(new Position(0, 0), field00);
		
		BuildingManager manager = new BuildingManager(player);
		
		assertTrue(manager.isFieldReachable(field00));
		assertTrue(manager.isFieldReachable(new Field(new Position(1, 0), null, 0)));
		assertTrue(manager.isFieldReachable(new Field(new Position(2, 0), null, 0)));
		assertTrue(manager.isFieldReachable(new Field(new Position(0, 1), null, 0)));
		assertTrue(manager.isFieldReachable(new Field(new Position(1, 1), null, 0)));
		assertTrue(manager.isFieldReachable(new Field(new Position(2, 1), null, 0)));
		assertTrue(manager.isFieldReachable(new Field(new Position(0, 2), null, 0)));
		
		assertFalse(manager.isFieldReachable(new Field(new Position(1, 2), null, 0)));
		assertFalse(manager.isFieldReachable(new Field(new Position(0, 3), null, 0)));
		assertFalse(manager.isFieldReachable(new Field(new Position(3, 0), null, 0)));
		assertFalse(manager.isFieldReachable(new Field(new Position(2, 2), null, 0)));
	}
}
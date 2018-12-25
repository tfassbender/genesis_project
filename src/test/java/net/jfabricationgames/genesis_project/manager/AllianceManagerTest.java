package net.jfabricationgames.genesis_project.manager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Matchers;

import net.jfabricationgames.genesis_project.game.AllianceBonus;
import net.jfabricationgames.genesis_project.game.Board;
import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.BuildingResources;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.Planet;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.PlayerBuilding;
import net.jfabricationgames.genesis_project.game.PlayerClass;
import net.jfabricationgames.genesis_project.user.User;

class AllianceManagerTest {
	
	private List<Field> alliance1;
	private List<Field> alliance2;
	private List<Field> alliance3;
	private List<Field> alliance4;
	private List<Field> alliance5;
	private List<Field> alliance6;
	private List<Field> alliance7;
	private List<Field> alliance8;
	private List<Field> alliance9;
	private List<Field> alliance10;
	
	private List<Field> satellites1;
	private List<Field> satellites2;
	private List<Field> satellites3;
	private List<Field> satellites4;
	private List<Field> satellites5;
	private List<Field> satellites6;
	private List<Field> satellites7;
	private List<Field> satellites8;
	private List<Field> satellites9;
	private List<Field> satellites10;
	
	private Board initializeBoard(Player player) {
		Board board = new Board();
		//initialize the board with some fields
		for (int x = 0; x < 6; x++) {
			for (int y = 0; y < 6; y++) {
				Position pos = new Position(x, y);
				Field field = new Field(pos, null);
				board.getFields().put(pos, field);
			}
		}
		
		Player player2 = mock(Player.class);
		//Player player2 = new Player(new User("player2"));
		
		//planet with 2 player buildings and one opponent building
		board.getFields().get(new Position(0, 0)).setPlanet(Planet.BLACK);
		board.getFields().get(new Position(0, 0)).build(new PlayerBuilding(Building.COLONY, player), 0);
		board.getFields().get(new Position(0, 0)).build(new PlayerBuilding(Building.MINE, player), 1);
		board.getFields().get(new Position(0, 0)).build(new PlayerBuilding(Building.MINE, player2), 2);
		
		//planet with 2 player buildings (one is a CITY) and 1 opponent building
		board.getFields().get(new Position(1, 1)).setPlanet(Planet.GENESIS);
		board.getFields().get(new Position(1, 1)).build(new PlayerBuilding(Building.COLONY, player), 0);
		board.getFields().get(new Position(1, 1)).build(new PlayerBuilding(Building.CITY, player), 1);
		board.getFields().get(new Position(1, 1)).build(new PlayerBuilding(Building.COLONY, player2), 2);
		
		//planet with 3 player buildings
		board.getFields().get(new Position(0, 3)).setPlanet(Planet.BLUE);
		board.getFields().get(new Position(0, 3)).build(new PlayerBuilding(Building.COLONY, player), 0);
		board.getFields().get(new Position(0, 3)).build(new PlayerBuilding(Building.COLONY, player), 1);
		board.getFields().get(new Position(0, 3)).build(new PlayerBuilding(Building.COLONY, player), 2);
		
		//planet with 2 player buildings and one opponent building
		board.getFields().get(new Position(2, 0)).setPlanet(Planet.BLACK);
		board.getFields().get(new Position(2, 0)).build(new PlayerBuilding(Building.LABORATORY, player), 0);
		board.getFields().get(new Position(2, 0)).build(new PlayerBuilding(Building.COLONY, player), 1);
		board.getFields().get(new Position(2, 0)).build(new PlayerBuilding(Building.COLONY, player2), 2);
		
		//planet with 1 player building
		board.getFields().get(new Position(3, 1)).setPlanet(Planet.GREEN);
		board.getFields().get(new Position(3, 1)).build(new PlayerBuilding(Building.COLONY, player), 0);
		
		//planet with 2 opponent buildings
		board.getFields().get(new Position(2, 3)).setPlanet(Planet.RED);
		board.getFields().get(new Position(2, 3)).build(new PlayerBuilding(Building.LABORATORY, player2), 0);
		board.getFields().get(new Position(2, 3)).build(new PlayerBuilding(Building.COLONY, player2), 1);
		
		//planet with 1 player building and one opponent building
		board.getFields().get(new Position(4, 2)).setPlanet(Planet.YELLOW);
		board.getFields().get(new Position(4, 2)).build(new PlayerBuilding(Building.COLONY, player), 0);
		board.getFields().get(new Position(4, 2)).build(new PlayerBuilding(Building.COLONY, player2), 1);
		
		//CENTER planet with 1 player building and one opponent building
		board.getFields().get(new Position(5, 0)).setPlanet(Planet.CENTER);
		board.getFields().get(new Position(5, 0)).build(new PlayerBuilding(Building.COLONY, player), 0);
		board.getFields().get(new Position(5, 0)).build(new PlayerBuilding(Building.COLONY, player2), 1);
		
		//possibleCombinations are: 
		//1, 2, 3
		//1, 2, 4
		//2, 3, 4
		//2, 3, 4, 5
		//2, 3, 5, 7
		//not possible are:
		//2, 4, 5 (only one opponent building)
		//1, 3, 4 (no city or government)
		//2, 3, 6 (empty planet, not enough buildings)
		//2, 3, 6, 5 (empty planet)
		//1, 2, 4, 8 (including center)
		return board;
	}
	
	private void initializeAlliancePlanets(Map<Position, Field> fields) {
		//possible combinations
		alliance1 = Arrays.asList(new Field[] {fields.get(new Position(0, 0)), fields.get(new Position(1, 1)), fields.get(new Position(0, 3))});
		alliance2 = Arrays.asList(new Field[] {fields.get(new Position(0, 0)), fields.get(new Position(1, 1)), fields.get(new Position(2, 0))});
		alliance3 = Arrays.asList(new Field[] {fields.get(new Position(1, 1)), fields.get(new Position(0, 3)), fields.get(new Position(2, 0))});
		alliance4 = Arrays.asList(new Field[] {fields.get(new Position(1, 1)), fields.get(new Position(0, 3)), fields.get(new Position(2, 0)),
				fields.get(new Position(3, 1))});
		alliance5 = Arrays.asList(new Field[] {fields.get(new Position(1, 1)), fields.get(new Position(0, 3)), fields.get(new Position(3, 1)),
				fields.get(new Position(4, 2))});
		
		//impossible combinations
		alliance6 = Arrays.asList(new Field[] {fields.get(new Position(1, 1)), fields.get(new Position(2, 0)), fields.get(new Position(3, 1))});
		alliance7 = Arrays.asList(new Field[] {fields.get(new Position(0, 0)), fields.get(new Position(0, 3)), fields.get(new Position(2, 0))});
		alliance8 = Arrays.asList(new Field[] {fields.get(new Position(1, 1)), fields.get(new Position(0, 3)), fields.get(new Position(2, 3))});
		alliance9 = Arrays.asList(new Field[] {fields.get(new Position(1, 1)), fields.get(new Position(0, 3)), fields.get(new Position(2, 3)),
				fields.get(new Position(3, 1))});
		alliance10 = Arrays.asList(new Field[] {fields.get(new Position(0, 0)), fields.get(new Position(1, 1)), fields.get(new Position(2, 0)),
				fields.get(new Position(5, 0))});
	}
	
	private void initializeAllianceSatellites(Map<Position, Field> fields) {
		satellites1 = Arrays.asList(new Field[] {fields.get(new Position(0, 1)), fields.get(new Position(0, 2))});
		satellites2 = Arrays.asList(new Field[] {fields.get(new Position(1, 0))});//valid (but single satellite)
		satellites3 = Arrays.asList(new Field[] {fields.get(new Position(1, 2)), fields.get(new Position(2, 2)), fields.get(new Position(2, 1))});//touching another planet
		satellites4 = Arrays.asList(new Field[] {fields.get(new Position(0, 2)), fields.get(new Position(2, 1))});//planet touching another planet
		satellites5 = Arrays.asList(new Field[] {fields.get(new Position(0, 2)), fields.get(new Position(2, 2))});//touching another planet that contains no buildings of the player (valid)
		satellites6 = Arrays.asList(new Field[] {fields.get(new Position(2, 1)), fields.get(new Position(3, 0))});//more than needed but possible
		satellites7 = Arrays.asList(new Field[] {fields.get(new Position(0, 1)), fields.get(new Position(0, 2)), fields.get(new Position(1, 0))});//touching another planet
		satellites8 = Arrays.asList(new Field[] {fields.get(new Position(1, 2))});
		satellites9 = Arrays.asList(new Field[] {fields.get(new Position(1, 2)), fields.get(new Position(2, 2))});//touching another planet
		satellites10 = Arrays.asList(new Field[] {fields.get(new Position(1, 0)), fields.get(new Position(4, 0))});//separated planets and satellites
	}
	
	@Test
	public void testIsAllianceValid() {
		ConstantsInitializerUtil.initAllianceValues();
		AllianceManager manager = mock(AllianceManager.class);
		//satellite connections are tested separately
		when(manager.isSatelliteConnectionValid(Matchers.<List<Field>> any(), Matchers.<List<Field>> any())).thenReturn(true);
		when(manager.isAllianceValid(Matchers.<List<Field>> any(), Matchers.<List<Field>> any(), any(AllianceBonus.class))).thenCallRealMethod();
		when(manager.isSatelliteResourcesAvailable(anyInt())).thenReturn(true);
		Player player = mock(Player.class);
		when(manager.getPlayer()).thenReturn(player);
		Board board = initializeBoard(player);
		when(manager.getBoard()).thenReturn(board);
		
		Map<Position, Field> fields = board.getFields();
		
		initializeAlliancePlanets(fields);
		
		List<Field> satellites = new ArrayList<Field>();//no satellites are included in this test
		AllianceBonus bonus = AllianceBonus.MILITARY_RANGE;
		
		assertTrue(manager.isAllianceValid(alliance1, satellites, bonus));
		assertTrue(manager.isAllianceValid(alliance2, satellites, bonus));
		assertTrue(manager.isAllianceValid(alliance3, satellites, bonus));
		assertTrue(manager.isAllianceValid(alliance4, satellites, bonus));
		assertTrue(manager.isAllianceValid(alliance5, satellites, bonus));
		
		assertFalse(manager.isAllianceValid(alliance6, satellites, bonus));
		assertFalse(manager.isAllianceValid(alliance7, satellites, bonus));
		assertFalse(manager.isAllianceValid(alliance8, satellites, bonus));
		assertFalse(manager.isAllianceValid(alliance9, satellites, bonus));
		assertFalse(manager.isAllianceValid(alliance10, satellites, bonus));
	}
	
	@Test
	public void testIsSatelliteConnectionValid() {
		ConstantsInitializerUtil.initAllianceValues();
		ConstantsInitializerUtil.initBuildingNumbers();
		AllianceManager manager = mock(AllianceManager.class);
		when(manager.isSatelliteConnectionValid(Matchers.<List<Field>> any(), Matchers.<List<Field>> any())).thenCallRealMethod();
		when(manager.isAllFieldsConnected(Matchers.<List<Field>> any())).thenCallRealMethod();
		when(manager.isSatelliteResourcesAvailable(anyInt())).thenReturn(true);
		Player player = new Player(new User("player1"));//using a real player here because equals(Object) can't be mocked
		when(manager.getPlayer()).thenReturn(player);
		Board board = initializeBoard(player);
		when(manager.getBoard()).thenReturn(board);
		
		Map<Position, Field> fields = board.getFields();
		
		initializeAlliancePlanets(fields);
		initializeAllianceSatellites(fields);
		
		assertTrue(manager.isSatelliteConnectionValid(alliance1, satellites1));
		assertTrue(manager.isSatelliteConnectionValid(alliance2, satellites2));
		assertFalse(manager.isSatelliteConnectionValid(alliance3, satellites3));
		assertFalse(manager.isSatelliteConnectionValid(alliance4, satellites4));
		assertTrue(manager.isSatelliteConnectionValid(alliance5, satellites5));
		assertFalse(manager.isSatelliteConnectionValid(alliance6, satellites6));
		assertFalse(manager.isSatelliteConnectionValid(alliance7, satellites7));
		assertTrue(manager.isSatelliteConnectionValid(alliance8, satellites8));
		assertFalse(manager.isSatelliteConnectionValid(alliance9, satellites9));
		assertFalse(manager.isSatelliteConnectionValid(alliance10, satellites10));
	}
	
	@Test
	public void testIsAllianceValid_and_isSatelliteConnectionValid() {
		ConstantsInitializerUtil.initAllianceValues();
		AllianceManager manager = mock(AllianceManager.class);
		when(manager.isSatelliteConnectionValid(Matchers.<List<Field>> any(), Matchers.<List<Field>> any())).thenCallRealMethod();
		when(manager.isAllianceValid(Matchers.<List<Field>> any(), Matchers.<List<Field>> any(), any(AllianceBonus.class))).thenCallRealMethod();
		when(manager.isAllFieldsConnected(Matchers.<List<Field>> any())).thenCallRealMethod();
		when(manager.isSatelliteResourcesAvailable(anyInt())).thenReturn(true);
		Player player = mock(Player.class);
		when(manager.getPlayer()).thenReturn(player);
		Board board = initializeBoard(player);
		when(manager.getBoard()).thenReturn(board);
		
		Map<Position, Field> fields = board.getFields();
		
		initializeAlliancePlanets(fields);
		initializeAllianceSatellites(fields);
		
		AllianceBonus bonus = AllianceBonus.MILITARY_RANGE;
		
		assertTrue(manager.isAllianceValid(alliance1, satellites1, bonus));
		assertTrue(manager.isAllianceValid(alliance2, satellites2, bonus));
		assertFalse(manager.isAllianceValid(alliance3, satellites3, bonus));
		assertFalse(manager.isAllianceValid(alliance4, satellites4, bonus));
		assertTrue(manager.isAllianceValid(alliance5, satellites5, bonus));
		assertFalse(manager.isAllianceValid(alliance6, satellites6, bonus));
		assertFalse(manager.isAllianceValid(alliance7, satellites7, bonus));
		assertFalse(manager.isAllianceValid(alliance8, satellites8, bonus));
		assertFalse(manager.isAllianceValid(alliance9, satellites9, bonus));
		assertFalse(manager.isAllianceValid(alliance10, satellites10, bonus));
	}
	
	@Test
	public void testIsSatelliteResourcesAvailable() {
		ConstantsInitializerUtil.initializeBuildingCostsForSpaceBuildings();
		Player player = mock(Player.class);
		when(player.getPlayerClass()).thenReturn(PlayerClass.ENCOR);//blue class
		IResourceManager resourceManager = new ResourceManager(player);
		when(player.getResourceManager()).thenReturn(resourceManager);
		resourceManager.addResources(new BuildingResources(3, 4, 1));//enough for 3 satellites
		BuildingManager buildingManager = mock(BuildingManager.class);
		when(buildingManager.getResourcesNeededForBuilding(any(Building.class), any(Field.class))).thenCallRealMethod();
		when(buildingManager.getPlayer()).thenReturn(player);
		when(player.getBuildingManager()).thenReturn(buildingManager);
		
		AllianceManager manager = mock(AllianceManager.class);
		when(manager.isSatelliteResourcesAvailable(anyInt())).thenCallRealMethod();
		when(manager.getPlayer()).thenReturn(player);
		
		assertTrue(manager.isSatelliteResourcesAvailable(1));
		assertTrue(manager.isSatelliteResourcesAvailable(2));
		assertTrue(manager.isSatelliteResourcesAvailable(3));
		assertFalse(manager.isSatelliteResourcesAvailable(4));
		assertFalse(manager.isSatelliteResourcesAvailable(5));
	}
}
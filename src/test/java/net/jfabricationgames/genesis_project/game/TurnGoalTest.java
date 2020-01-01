package net.jfabricationgames.genesis_project.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.genesis_project.move.IMove;
import net.jfabricationgames.genesis_project.testUtils.MoveCreaterUtil;

class TurnGoalTest {
	
	private Game getInitializedGame(Player player) {
		Game game = mock(Game.class);
		Board board = new Board();
		when(game.getBoard()).thenReturn(board);
		//initialize the board with some fields
		for (int x = 0; x < 6; x++) {
			for (int y = 0; y < 6; y++) {
				Position pos = new Position(x, y);
				Field field = new Field(pos, null, 0);
				board.getFields().put(pos, field);
			}
		}
		
		//planet with 2 player buildings and one opponent building
		board.getFields().get(new Position(0, 0)).setPlanet(Planet.BLACK);
		board.getFields().get(new Position(0, 0)).build(new PlayerBuilding(Building.COLONY, player), 0);
		
		//planet with 2 player buildings (one is a CITY) and 1 opponent building
		board.getFields().get(new Position(1, 1)).setPlanet(Planet.GENESIS);
		board.getFields().get(new Position(1, 1)).build(new PlayerBuilding(Building.COLONY, player), 0);
		
		return game;
	}
	
	@Test
	public void testAllianceTurnGoal() {
		Player player = mock(Player.class);
		IMove move = MoveCreaterUtil.getAllianceMove(player.getUsername(), new Field[0], new Field[0], null, 0);
		
		assertEquals(5, TurnGoal.ALLIANCE.getPointsForMove(move));
	}
	@Test
	public void testColonyTurnGoal() {
		Player player = mock(Player.class);
		Game game = getInitializedGame(player);
		IMove move = MoveCreaterUtil.getBuildingMove(game.getBoard(), player.getUsername(), Building.COLONY, 0, 0);
		
		assertEquals(2, TurnGoal.COLONY.getPointsForMove(move));
	}
	@Test
	public void testGenesisPlanetTurnGoal() {
		Player player = mock(Player.class);
		Game game = getInitializedGame(player);
		IMove move = MoveCreaterUtil.getBuildingMove(game.getBoard(), player.getUsername(), Building.COLONY, 1, 1);
		IMove move2 = MoveCreaterUtil.getBuildingMove(game.getBoard(), player.getUsername(), Building.TRADING_POST, 1, 1);
		IMove move3 = MoveCreaterUtil.getBuildingMove(game.getBoard(), player.getUsername(), Building.COLONY, 0, 0);
		
		assertEquals(2, TurnGoal.GENESIS_PLANET.getPointsForMove(move));
		assertEquals(2, TurnGoal.GENESIS_PLANET.getPointsForMove(move2));
		assertEquals(0, TurnGoal.GENESIS_PLANET.getPointsForMove(move3));
	}
	@Test
	public void testGovernmentCityTurnGoal() {
		Player player = mock(Player.class);
		Game game = getInitializedGame(player);
		IMove move = MoveCreaterUtil.getBuildingMove(game.getBoard(), player.getUsername(), Building.CITY, 1, 1);
		IMove move2 = MoveCreaterUtil.getBuildingMove(game.getBoard(), player.getUsername(), Building.GOVERNMENT, 1, 1);
		IMove move3 = MoveCreaterUtil.getBuildingMove(game.getBoard(), player.getUsername(), Building.COLONY, 1, 1);
		
		assertEquals(5, TurnGoal.GOVERNMENT_CITY.getPointsForMove(move));
		assertEquals(5, TurnGoal.GOVERNMENT_CITY.getPointsForMove(move2));
		assertEquals(0, TurnGoal.GOVERNMENT_CITY.getPointsForMove(move3));
	}
	@Test
	public void testLaboratoryResearchCenterTurnGoal() {
		Player player = mock(Player.class);
		Game game = getInitializedGame(player);
		IMove move = MoveCreaterUtil.getBuildingMove(game.getBoard(), player.getUsername(), Building.LABORATORY, 1, 1);
		IMove move2 = MoveCreaterUtil.getBuildingMove(game.getBoard(), player.getUsername(), Building.RESEARCH_CENTER, 1, 1);
		IMove move3 = MoveCreaterUtil.getBuildingMove(game.getBoard(), player.getUsername(), Building.COLONY, 1, 1);
		
		assertEquals(3, TurnGoal.LABORATORY_RESEARCH_CENTER.getPointsForMove(move));
		assertEquals(3, TurnGoal.LABORATORY_RESEARCH_CENTER.getPointsForMove(move2));
		assertEquals(0, TurnGoal.LABORATORY_RESEARCH_CENTER.getPointsForMove(move3));
	}
	@Test
	public void testMineTradingPostTurnGoal() {
		Player player = mock(Player.class);
		Game game = getInitializedGame(player);
		IMove move = MoveCreaterUtil.getBuildingMove(game.getBoard(), player.getUsername(), Building.TRADING_POST, 1, 1);
		IMove move2 = MoveCreaterUtil.getBuildingMove(game.getBoard(), player.getUsername(), Building.MINE, 1, 1);
		IMove move3 = MoveCreaterUtil.getBuildingMove(game.getBoard(), player.getUsername(), Building.COLONY, 1, 1);
		
		assertEquals(3, TurnGoal.MINE_TRADING_POST.getPointsForMove(move));
		assertEquals(3, TurnGoal.MINE_TRADING_POST.getPointsForMove(move2));
		assertEquals(0, TurnGoal.MINE_TRADING_POST.getPointsForMove(move3));
	}
	@Test
	public void testNewPlanetsTurnGoal() {
		Player player = mock(Player.class);
		Player player2 = mock(Player.class);
		when(player.getUsername()).thenReturn("Player1");
		when(player.getUsername()).thenReturn("Player2");
		Game game = getInitializedGame(player);
		IMove move = MoveCreaterUtil.getBuildingMove(game.getBoard(), player2.getUsername(), Building.COLONY, 1, 1);
		IMove move2 = MoveCreaterUtil.getBuildingMove(game.getBoard(), player.getUsername(), Building.COLONY, 1, 1);
		
		assertEquals(2, TurnGoal.NEW_PLANETS.getPointsForMove(move));
		assertEquals(0, TurnGoal.NEW_PLANETS.getPointsForMove(move2));
	}
	@Test
	public void testNeighborsTurnGoal() {
		Player player = mock(Player.class);
		Player player2 = mock(Player.class);
		when(player.getUsername()).thenReturn("Player1");
		when(player.getUsername()).thenReturn("Player2");
		Game game = getInitializedGame(player);
		IMove move = MoveCreaterUtil.getBuildingMove(game.getBoard(), player2.getUsername(), Building.COLONY, 1, 1);
		IMove move2 = MoveCreaterUtil.getBuildingMove(game.getBoard(), player.getUsername(), Building.COLONY, 1, 1);
		
		assertEquals(3, TurnGoal.NEIGHBORS.getPointsForMove(move));
		assertEquals(0, TurnGoal.NEIGHBORS.getPointsForMove(move2));
	}
}
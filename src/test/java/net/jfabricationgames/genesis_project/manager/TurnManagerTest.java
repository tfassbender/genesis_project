package net.jfabricationgames.genesis_project.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.Planet;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.PlayerClass;
import net.jfabricationgames.genesis_project.game.TurnGoal;
import net.jfabricationgames.genesis_project.move.IMove;
import net.jfabricationgames.genesis_project.move.MoveBuilder;
import net.jfabricationgames.genesis_project.move.MoveType;
import net.jfabricationgames.genesis_project.testUtils.ConstantsInitializerUtil;

class TurnManagerTest {
	
	private TurnManager getTurnManager() {
		Game game = mock(Game.class);
		return getTurnManager(game);
	}
	private TurnManager getTurnManager(Game game) {
		List<Player> players = new ArrayList<Player>(2);
		for (int i = 0; i < 2; i++) {
			Player player = new Player("Player" + (i + 1), PlayerClass.ENCOR);
			players.add(player);
		}
		when(game.getPlayers()).thenReturn(players);
		TurnManager turnManager = new TurnManager(game);
		return turnManager;
	}
	
	@Test
	public void testChooseRandomOrder() {
		ConstantsInitializerUtil.initAll();
		TurnManager turnManager = getTurnManager();
		//test if it's really random by executing it many times
		turnManager.chooseRandomTurnGoals();
		List<TurnGoal> last = turnManager.getTurnGoals();
		boolean equal = true;
		for (int i = 0; i < 20; i++) {
			//20 times the same order seems not randomly distributed
			turnManager.chooseRandomTurnGoals();
			equal &= last.equals(turnManager.getTurnGoals());
		}
		assertFalse(equal);
	}
	
	@Test
	public void testReceiveTurnGoalPoints() {
		ConstantsInitializerUtil.initAll();
		Game game = mock(Game.class);
		TurnManager turnManager = getTurnManager(game);
		
		Random random = new Random(42);
		//using random state 42 MINE_TRAIDING_POST is the first TurnGoal
		turnManager.chooseRandomTurnGoals(TurnGoal.values(), random);
		turnManager.nextTurn();//start the first turn
		
		Player player1 = mock(Player.class);
		when(player1.getUsername()).thenReturn("Player1");
		IPointManager pointManager = new PointManager(player1);
		GamePointManager gamePointManager = mock(GamePointManager.class);
		when(game.getPointManager()).thenReturn(gamePointManager);
		when(game.getPlayer(any(String.class))).thenReturn(player1);
		when(player1.getPointManager()).thenReturn(pointManager);
		when(player1.getGame()).thenReturn(game);
		
		//create moves
		IMove buildColony;
		IMove buildMine;
		MoveBuilder builder = new MoveBuilder();
		builder.setType(MoveType.BUILD);
		builder.setPlayer(player1.getUsername());
		builder.setBuilding(Building.COLONY);
		builder.setField(new Field(new Position(0, 0), Planet.GRAY, 0));
		buildColony = builder.build();
		
		builder = new MoveBuilder();
		builder.setType(MoveType.BUILD);
		builder.setPlayer(player1.getUsername());
		builder.setBuilding(Building.MINE);
		builder.setField(new Field(new Position(0, 0), Planet.GRAY, 0));
		buildMine = builder.build();
		
		turnManager.receivePointsForMove(buildColony);
		assertEquals(0, pointManager.getPoints());//no points for colonies
		turnManager.receivePointsForMove(buildMine);
		assertEquals(3, pointManager.getPoints());//3 points for building a mine in this turn
	}
}
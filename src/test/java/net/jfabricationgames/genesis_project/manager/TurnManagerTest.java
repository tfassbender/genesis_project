package net.jfabricationgames.genesis_project.manager;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import net.jfabricationgames.genesis_project.user.User;

class TurnManagerTest {
	
	private TurnManager getTurnManager() {
		Game game = mock(Game.class);
		List<Player> players = new ArrayList<Player>(2);
		for (int i = 0; i < 2; i++) {
			Player player = new Player(new User("Player" + (i + 1)), PlayerClass.ENCOR);
			players.add(player);
		}
		when(game.getPlayers()).thenReturn(players);
		TurnManager turnManager = new TurnManager(game);
		return turnManager;
	}
	
	@Test
	public void testChooseRandomOrder() {
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
		TurnManager turnManager = getTurnManager();
		
		Random random = new Random(42);
		//using random state 42 MINE_TRAIDING_POST is the first TurnGoal
		turnManager.chooseRandomTurnGoals(TurnGoal.values(), random);
		turnManager.nextTurn();//start the first turn

		Game game = mock(Game.class);
		Player player1 = mock(Player.class);
		IPointManager pointManager = new PointManager(player1);
		GamePointManager gamePointManager = mock(GamePointManager.class);
		when(game.getPointManager()).thenReturn(gamePointManager);
		when(player1.getPointManager()).thenReturn(pointManager);
		when(player1.getGame()).thenReturn(game);
		
		//create moves
		IMove buildColony;
		IMove buildMine;
		MoveBuilder builder = new MoveBuilder(game);
		builder.buildMove();
		builder.setType(MoveType.BUILD);
		builder.setPlayer(player1);
		builder.setBuilding(Building.COLONY);
		builder.setField(new Field(new Position(0, 0), Planet.GRAY, 0));
		buildColony = builder.getMove();
		builder.buildMove();
		builder.setType(MoveType.BUILD);
		builder.setPlayer(player1);
		builder.setBuilding(Building.MINE);
		builder.setField(new Field(new Position(0, 0), Planet.GRAY, 0));
		buildMine = builder.getMove();
		
		turnManager.receivePointsForMove(buildColony);
		assertEquals(0, pointManager.getPoints());//no points for colonies
		turnManager.receivePointsForMove(buildMine);
		assertEquals(3, pointManager.getPoints());//3 points for building a mine in this turn
	}
}
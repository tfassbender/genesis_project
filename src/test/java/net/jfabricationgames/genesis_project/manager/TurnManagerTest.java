package net.jfabricationgames.genesis_project.manager;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.TurnGoal;

class TurnManagerTest {
	
	@Test
	public void testChooseRandomOrder() {
		Game game = mock(Game.class);
		TurnManager turnManager = new TurnManager(game);
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
}
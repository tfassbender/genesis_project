package net.jfabricationgames.genesis_project.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.jfabricationgames.genesis_project.game.Player;

public class PlayerOrderTest {
	
	private PlayerOrder<Player> playerOrder;
	private Player user1;
	private Player user2;
	private Player user3;
	private Player user4;
	private Player user5;
	
	@BeforeEach
	public void initPlayerOrder() {
		playerOrder = new PlayerOrder<Player>(5);
		user1 = mock(Player.class);
		user2 = mock(Player.class);
		user3 = mock(Player.class);
		user4 = mock(Player.class);
		user5 = mock(Player.class);
		Random randomGenerator = new Random(42);//use a deterministic random generator for the tests
		playerOrder.chooseRandomOrder(Arrays.asList(user1, user2, user3, user4, user5), randomGenerator);
	}
	
	@Test
	public void testChooseRandomOrder() {
		assertEquals(5, playerOrder.getOrder().size());
		
		//test if it's really random by executing it many times
		List<Player> last = playerOrder.getOrder();
		boolean equal = true;
		for (int i = 0; i < 20; i++) {
			//20 times the same order seems not randomly distributed
			playerOrder.chooseRandomOrder(Arrays.asList(user1, user2, user3, user4, user5));
			equal &= last.equals(playerOrder.getOrder());
		}
		assertFalse(equal);
	}
	
	@Test
	public void testGetNext() {
		List<Player> order = playerOrder.getOrder();
		assertEquals(order.get(1), playerOrder.getNext());
		
		playerOrder.nextMove();
		assertEquals(order.get(2), playerOrder.getNext());
		
		for (int i = 0; i < 3; i++) {
			playerOrder.nextMove();
		}
		assertEquals(order.get(0), playerOrder.getNext());
	}
	
	@Test
	public void testGetNext_playersPassed() {
		//deterministic order: 2, 4, 3, 1, 5 (random seed: 42)
		List<Player> order = playerOrder.getOrder();
		
		playerOrder.playerPassed(user4);
		
		assertEquals(order.get(2), playerOrder.getNext());
	}
	
	@Test
	public void testGetNextTurnOrder() {
		List<Player> order = playerOrder.getOrder();
		assertEquals(Arrays.asList(new Player[0]), playerOrder.getNextTurnOrder());//null entries are not added to the list
		
		playerOrder.playerPassed(order.get(1));
		playerOrder.playerPassed(order.get(3));
		playerOrder.playerPassed(order.get(0));
		assertEquals(Arrays.asList(new Player[] {order.get(1), order.get(3), order.get(0)}), playerOrder.getNextTurnOrder());
		
		playerOrder.playerPassed(order.get(2));
		playerOrder.playerPassed(order.get(4));
		assertEquals(Arrays.asList(new Player[] {order.get(1), order.get(3), order.get(0), order.get(2), order.get(4)}),
				playerOrder.getNextTurnOrder());
	}
	
	@Test
	public void testGetActivePlayer() {
		List<Player> order = playerOrder.getOrder();
		
		assertEquals(order.get(0), playerOrder.getActivePlayer());
		
		playerOrder.nextMove();
		assertEquals(order.get(1), playerOrder.getActivePlayer());
		
		playerOrder.nextMove();
		assertEquals(order.get(2), playerOrder.getActivePlayer());
		
		playerOrder.playerPassed(order.get(2));
		playerOrder.nextMove();
		assertEquals(order.get(3), playerOrder.getActivePlayer());
		
		playerOrder.playerPassed(order.get(4));
		playerOrder.nextMove();
		assertEquals(order.get(0), playerOrder.getActivePlayer());
	}
	
	@Test
	public void testIsPlayersTurn() {
		List<Player> order = playerOrder.getOrder();
		
		assertTrue(playerOrder.isPlayersTurn(order.get(0)));
		
		playerOrder.nextMove();
		assertTrue(playerOrder.isPlayersTurn(order.get(1)));
		
		playerOrder.nextMove();
		assertTrue(playerOrder.isPlayersTurn(order.get(2)));
		
		playerOrder.playerPassed(order.get(2));
		playerOrder.nextMove();
		assertTrue(playerOrder.isPlayersTurn(order.get(3)));
		
		playerOrder.playerPassed(order.get(4));
		playerOrder.nextMove();
		assertTrue(playerOrder.isPlayersTurn(order.get(0)));
	}
	
	@Test
	public void testIsTurnEnd() {
		List<Player> order = playerOrder.getOrder();
		
		assertFalse(playerOrder.isTurnEnd());
		
		playerOrder.playerPassed(order.get(0));
		playerOrder.playerPassed(order.get(2));
		
		assertFalse(playerOrder.isTurnEnd());
		
		playerOrder.playerPassed(order.get(4));
		
		assertFalse(playerOrder.isTurnEnd());
		
		playerOrder.playerPassed(order.get(1));
		playerOrder.playerPassed(order.get(3));
		
		assertTrue(playerOrder.isTurnEnd());
	}
	
	@Test
	public void testAllPlayersPassed() {
		playerOrder.playerPassed(user1);
		playerOrder.playerPassed(user2);
		playerOrder.playerPassed(user3);
		playerOrder.playerPassed(user4);
		playerOrder.playerPassed(user5);
		
		assertThrows(IllegalStateException.class, () -> playerOrder.getNext());
		assertThrows(IllegalStateException.class, () -> playerOrder.getActivePlayer());
		assertThrows(IllegalStateException.class, () -> playerOrder.nextMove());
	}
	
	@Test
	public void testNextTurn() {
		Map<Integer, Player> nextOrder = playerOrder.getNextOrder();
		playerOrder.nextTurn();
		
		assertEquals(nextOrder, playerOrder.getCurrentOrder());
		assertEquals(0, playerOrder.getNextOrder().entrySet().size());
	}
}
package net.jfabricationgames.genesis_project.manager;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.user.User;

public class PlayerOrderTest {
	
	private PlayerOrder playerOrder;
	private Player user1;
	private Player user2;
	private Player user3;
	private Player user4;
	private Player user5;
	
	@Before
	public void initPlayerOrder() {
		playerOrder = new PlayerOrder(5);
		user1 = new Player(new User("user1"));
		user2 = new Player(new User("user2"));
		user3 = new Player(new User("user3"));
		user4 = new Player(new User("user4"));
		user5 = new Player(new User("user5"));
		Random randomGenerator = new Random(42);//use a deterministic random generator for the tests
		playerOrder.chooseRandomOrder(Arrays.asList(user1, user2, user3, user4, user5), randomGenerator);
	}
	
	@Test
	public void testChooseRandomOrder() {
		assertEquals(5, playerOrder.getOrder().length);
		
		//test if it's really random by executing it many times
		Player[] last = playerOrder.getOrder();
		boolean equal = true;
		for (int i = 0; i < 20; i++) {
			//20 times the same order seems not randomly distributed
			playerOrder.chooseRandomOrder(Arrays.asList(user1, user2, user3, user4, user5));
			equal &= Arrays.equals(last, playerOrder.getOrder());
		}
		assertFalse(equal);
	}
	
	@Test
	public void testGetNext() {
		Player[] order = playerOrder.getOrder();
		assertEquals(order[1], playerOrder.getNext());
		
		playerOrder.nextMove();
		assertEquals(order[2], playerOrder.getNext());
		
		for (int i = 0; i < 3; i++) {
			playerOrder.nextMove();
		}
		assertEquals(order[0], playerOrder.getNext());
	}
	
	@Test
	public void testGetNext_playersPassed() {
		//deterministic order: 2, 4, 3, 1, 5 (random seed: 42)
		Player[] order = playerOrder.getOrder();
		
		playerOrder.playerPassed(user4);
		
		assertEquals(order[2], playerOrder.getNext());
	}
	
	@Test
	public void testGetNextTurnOrder() {
		Player[] order = playerOrder.getOrder();
		assertArrayEquals(new User[] {null, null, null, null, null}, playerOrder.getNextTurnOrder());
		
		playerOrder.playerPassed(order[1]);
		playerOrder.playerPassed(order[3]);
		playerOrder.playerPassed(order[0]);
		assertArrayEquals(new Player[] {order[1], order[3], order[0], null, null}, playerOrder.getNextTurnOrder());
		
		playerOrder.playerPassed(order[2]);
		playerOrder.playerPassed(order[4]);
		assertArrayEquals(new Player[] {order[1], order[3], order[0], order[2], order[4]}, playerOrder.getNextTurnOrder());
	}
	
	@Test
	public void testGetActivePlayer() {
		Player[] order = playerOrder.getOrder();
		
		assertEquals(order[0], playerOrder.getActivePlayer());
		
		playerOrder.nextMove();
		assertEquals(order[1], playerOrder.getActivePlayer());
		
		playerOrder.nextMove();
		assertEquals(order[2], playerOrder.getActivePlayer());
		
		playerOrder.playerPassed(order[2]);
		playerOrder.nextMove();
		assertEquals(order[3], playerOrder.getActivePlayer());
		
		playerOrder.playerPassed(order[4]);
		playerOrder.nextMove();
		assertEquals(order[0], playerOrder.getActivePlayer());
	}
	
	@Test
	public void testIsPlayersTurn() {
		Player[] order = playerOrder.getOrder();
		
		assertTrue(playerOrder.isPlayersTurn(order[0]));
		
		playerOrder.nextMove();
		assertTrue(playerOrder.isPlayersTurn(order[1]));
		
		playerOrder.nextMove();
		assertTrue(playerOrder.isPlayersTurn(order[2]));
		
		playerOrder.playerPassed(order[2]);
		playerOrder.nextMove();
		assertTrue(playerOrder.isPlayersTurn(order[3]));
		
		playerOrder.playerPassed(order[4]);
		playerOrder.nextMove();
		assertTrue(playerOrder.isPlayersTurn(order[0]));
	}
	
	@Test
	public void testIsTurnEnd() {
		Player[] order = playerOrder.getOrder();
		
		assertFalse(playerOrder.isTurnEnd());
		
		playerOrder.playerPassed(order[0]);
		playerOrder.playerPassed(order[2]);
		
		assertFalse(playerOrder.isTurnEnd());
		
		playerOrder.playerPassed(order[4]);
		
		assertFalse(playerOrder.isTurnEnd());
		
		playerOrder.playerPassed(order[1]);
		playerOrder.playerPassed(order[3]);
		
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
package net.jfabricationgames.genesis_project.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.annotations.VisibleForTesting;

import net.jfabricationgames.genesis_project.game.Player;

/**
 * The order in which the players execute their moves (for the current and the next turn).
 */
public class PlayerOrder<T> {

	private Map<Integer, T> order;//player order starting with 0
	private Map<Integer, T> nextOrder;
	private int move;
	private final int players;
	
	public PlayerOrder(int players) {
		order = new HashMap<Integer, T>();
		nextOrder = new HashMap<Integer, T>();
		this.players = players;
	}

	/**
	 * Select a random order for the first turn.
	 * 
	 * @param players
	 * 		The players joining the game.
	 */
	public void chooseRandomOrder(List<T> players) {
		Random random = new Random((long) (Math.random() * Long.MAX_VALUE));
		chooseRandomOrder(players, random);
	}
	@VisibleForTesting
	protected void chooseRandomOrder(List<T> players, Random randomGenerator) {
		int[] positions = new int[players.size()];
		int swap;
		int random;
		//initialize the positions array with values from 0 to players.size()
		for (int i = 0; i < positions.length; i++) {
			positions[i] = i;
		}
		for (int i = 0; i < positions.length; i++) {
			random = (int) (randomGenerator.nextDouble() * (positions.length - i)) + i;//select a random integer from i to players.size()
			swap = positions[random];//swap the chosen integer to position i
			positions[random] = positions[i];
			positions[i] = swap;
			order.put(positions[i], players.get(i));//add the player order
		}
	}
	
	/**
	 * Get the next player.
	 * 
	 * @return
	 * 		The next player.
	 */
	public T getNext() throws IllegalStateException {
		int nextMove = move+1;
		//find the next player that hasn't passed yet
		while (order.get(nextMove % players) == null) {
			nextMove++;
			if (nextMove > move+players) {
				throw new IllegalStateException("No next Player available. All players have passed.");
			}
		}
		return order.get(nextMove % players);
	}
	
	/**
	 * Get the order of the players in this turn.
	 * If the players have already passed they don't show up in the array.
	 * 
	 * @return
	 * 		The player's order.
	 */
	public List<T> getOrder() {
		List<T> users = new ArrayList<T>(order.size());//T[order.size()];
		int index = 0;
		for (int i : order.keySet()) {
			users.add(index, order.get(i));
			index++;
		}
		return users;
	}
	/**
	 * Get the player order of the next turn.
	 * 
	 * @return
	 * 		The player order as an array.
	 */
	public List<T> getNextTurnOrder() {
		List<T> users = new ArrayList<T>(players);
		for (int i : nextOrder.keySet()) {
			users.add(i, nextOrder.get(i));
		}
		return users;
	}
	
	public T getActivePlayer() throws IllegalStateException {
		T user = order.get(move % players);
		if (user == null) {
			throw new IllegalStateException("No active player found.");
		}
		return user;
	}
	
	public boolean isPlayersTurn(Player player) {
		return player.equals(getActivePlayer());
	}
	
	/**
	 * Inform the player order that a user has passed and set counter to the next move.
	 * 
	 * @param user
	 * 		The user that has passed.
	 */
	public void playerPassed(T user) {
		int playersPassed = nextOrder.size();
		nextOrder.put(playersPassed, user);
		Integer userOrder = null;
		for (Integer i : order.keySet()) {
			if (order.get(i).equals(user)) {
				userOrder = i;
			}
		}
		if (userOrder != null) {
			order.remove(userOrder);
		}
	}
	
	/**
	 * End a players move and let the next player do his move.
	 * 
	 * @throws IllegalStateException
	 * 		An IllegalStateException is thrown when all players have passed and therefore there is no next move. 
	 */
	public void nextMove() throws IllegalStateException {
		move++;
		int additions = 0;//the additional moves to skip the players that have already passed
		while (order.get(move % players) == null) {
			move++;
			additions++;
			if (additions == players) {
				throw new IllegalStateException("No next move possible. All players have passed.");
			}
		}
	}
	
	/**
	 * Start the next turn and set the active player to the first player of this turn.
	 */
	protected void nextTurn() {
		order = nextOrder;
		nextOrder = new HashMap<Integer, T>();
		move = 0;
	}
	
	/**
	 * Check whether all players have passed.
	 * 
	 * @return
	 * 		Returns true if all players have passed.
	 */
	public boolean isTurnEnd() {
		return nextOrder.size() == players;
	}
	
	@VisibleForTesting
	protected Map<Integer, T> getCurrentOrder() {
		return order;
	}
	@VisibleForTesting
	protected Map<Integer, T> getNextOrder() {
		return nextOrder;
	}
}
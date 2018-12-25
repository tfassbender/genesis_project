package net.jfabricationgames.genesis_project.manager;

import java.util.List;

import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.TurnGoal;
import net.jfabricationgames.genesis_project.move.IMove;

public interface ITurnManager {
	
	public int getTurn();
	public void nextTurn();
	
	public void playerPassed(Player player);
	
	public void receivePointsForMove(IMove move);
	
	public boolean gameEnded();
	
	/**
	 * Get the active turn goal of this turn.
	 */
	public TurnGoal getActiveTurnGoal();
	/**
	 * Get the turn goals for this game (starting with index 0 for turn 1).
	 */
	public List<TurnGoal> getTurnGoals();
	public PlayerOrder<Player> getPlayerOrder();
}
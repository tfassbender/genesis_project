package net.jfabricationgames.genesis_project.manager;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.TurnGoal;
import net.jfabricationgames.genesis_project.move.IMove;

public interface ITurnManager {
	
	public int getTurn();
	public void nextTurn();
	
	
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
	
	public Player getNextPlayer();
	public List<Player> getPlayerOrder();
	public List<Player> getNextTurnOrder();
	public Player getActivePlayer();
	public void playerPassed(Player player);
	public void nextMove();
	public boolean isTurnEnd();
	public boolean isPlayersTurn(Player player);
	
	public ObservableList<Player> getCurrentTurnPlayerOrder();
	public ObservableList<Player> getNextTurnPlayerOrder();
	public ObjectProperty<Player> getCurrentPlayerProperty();
}
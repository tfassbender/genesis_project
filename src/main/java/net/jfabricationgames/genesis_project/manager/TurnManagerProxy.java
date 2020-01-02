package net.jfabricationgames.genesis_project.manager;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.TurnGoal;
import net.jfabricationgames.genesis_project.move.IMove;

public class TurnManagerProxy implements ITurnManager {
	
	private ITurnManager manager;
	
	public TurnManagerProxy(ITurnManager manager) {
		this.manager = manager;
	}
	
	@Override
	public int getTurn() {
		return manager.getTurn();
	}
	
	@Override
	public void nextTurn() {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public void receivePointsForMove(IMove move) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public boolean gameEnded() {
		return manager.gameEnded();
	}
	
	@Override
	public TurnGoal getActiveTurnGoal() {
		return manager.getActiveTurnGoal();
	}
	
	@Override
	public List<TurnGoal> getTurnGoals() {
		return manager.getTurnGoals();
	}
	
	@Override
	public Player getNextPlayer() {
		return manager.getNextPlayer();
	}
	
	@Override
	public List<Player> getPlayerOrder() {
		return manager.getPlayerOrder();
	}
	
	@Override
	public List<Player> getNextTurnOrder() {
		return manager.getNextTurnOrder();
	}
	
	@Override
	public Player getActivePlayer() {
		return manager.getActivePlayer();
	}
	
	@Override
	public void playerPassed(Player player) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public void nextMove() {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public boolean isTurnEnd() {
		return manager.isTurnEnd();
	}
	
	@Override
	public boolean isPlayersTurn(Player player) {
		return manager.isPlayersTurn(player);
	}
	
	@Override
	public ObservableList<Player> getCurrentTurnPlayerOrder() {
		return manager.getCurrentTurnPlayerOrder();
	}
	
	@Override
	public ObservableList<Player> getNextTurnPlayerOrder() {
		return manager.getNextTurnPlayerOrder();
	}
	
	@Override
	public ObjectProperty<Player> getCurrentPlayerProperty() {
		return manager.getCurrentPlayerProperty();
	}
}
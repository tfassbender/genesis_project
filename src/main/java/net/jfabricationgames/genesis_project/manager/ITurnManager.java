package net.jfabricationgames.genesis_project.manager;

import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.move.IMove;

public interface ITurnManager {
	
	public int getTurn();
	public void nextTurn();
	
	public void receivePointsForMove(IMove move);
	
	public boolean gameEnded();
	
	public PlayerOrder<Player> getPlayerOrder();
}
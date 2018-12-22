package net.jfabricationgames.genesis_project.manager;

import net.jfabricationgames.genesis_project.game.Player;

public interface ITurnManager {
	
	public int getTurn();
	public void nextTurn();
	public boolean gameEnded();
	
	public PlayerOrder<Player> getPlayerOrder();
}
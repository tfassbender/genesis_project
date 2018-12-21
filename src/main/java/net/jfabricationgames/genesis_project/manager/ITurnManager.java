package net.jfabricationgames.genesis_project.manager;

public interface ITurnManager {
	
	public int getTurn();
	public void nextTurn();
	public boolean gameEnded();
	
	public PlayerOrder getPlayerOrder();
}
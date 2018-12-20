package net.jfabricationgames.genesis_project.manager;

import net.jfabricationgames.genesis_project.game.Player;

public interface IPointManager {
	
	public void addPoints(int points);
	public void setPoints(int points);
	public int getPoints();
	
	public Player getPlayer();
}
package net.jfabricationgames.genesis_project.manager;

import net.jfabricationgames.genesis_project.game.Player;

public class PointManager implements IPointManager {
	
	private Player player;
	
	private int points;
	
	public PointManager(Player player) {
		this.player = player;
	}
	
	@Override
	public void addPoints(int points) {
		this.points += points;
	}
	
	@Override
	public void setPoints(int points) {
		this.points = points;
	}
	
	@Override
	public int getPoints() {
		return points;
	}
	
	@Override
	public Player getPlayer() {
		return player;
	}
}
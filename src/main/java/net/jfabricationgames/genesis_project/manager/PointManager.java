package net.jfabricationgames.genesis_project.manager;

public class PointManager implements IPointManager {
	
	private int points;
	
	public PointManager() {
		
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
}
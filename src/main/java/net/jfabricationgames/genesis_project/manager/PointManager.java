package net.jfabricationgames.genesis_project.manager;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import net.jfabricationgames.genesis_project.game.Player;

public class PointManager implements IPointManager {
	
	private IntegerProperty points = new SimpleIntegerProperty(this, "points");
	private IntegerProperty position = new SimpleIntegerProperty(this, "position");
	
	private Player player;
	
	public PointManager(Player player) {
		this.player = player;
	}
	
	@Override
	public void addPoints(int points) {
		this.points.set(this.points.get() + points);
		player.getGame().getPointManager().setScore(player, getPoints());
		position.set(getPosition());
	}
	
	@Override
	public void setPoints(int points) {
		this.points.set(points);
		player.getGame().getPointManager().setScore(player, getPoints());
		position.set(getPosition());
	}
	
	@Override
	public int getPoints() {
		return points.get();
	}
	
	@Override
	public int getPosition() {
		return player.getGame().getPointManager().getPosition(player);
	}
	
	@Override
	public IntegerProperty getPointsProperty() {
		return points;
	}
	
	@Override
	public IntegerProperty getPositionProperty() {
		return position;
	}
}
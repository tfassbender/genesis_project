package net.jfabricationgames.genesis_project.manager;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PointManager implements IPointManager {
	
	private IntegerProperty points = new SimpleIntegerProperty(this, "points");
	
	public PointManager() {
		
	}
	
	@Override
	public void addPoints(int points) {
		this.points.set(this.points.get() + points);
	}
	
	@Override
	public void setPoints(int points) {
		this.points.set(points);
	}
	
	@Override
	public int getPoints() {
		return points.get();
	}

	@Override
	public IntegerProperty getPointsProperty() {
		return points;
	}
}
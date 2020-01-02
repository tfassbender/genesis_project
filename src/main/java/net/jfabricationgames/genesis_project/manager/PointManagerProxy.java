package net.jfabricationgames.genesis_project.manager;

import javafx.beans.property.IntegerProperty;

public class PointManagerProxy implements IPointManager {
	
	private IPointManager manager;
	
	public PointManagerProxy(IPointManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void addPoints(int points) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public void setPoints(int points) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public int getPoints() {
		return manager.getPoints();
	}
	
	@Override
	public int getPosition() {
		return manager.getPosition();
	}
	
	@Override
	public IntegerProperty getPointsProperty() {
		return manager.getPointsProperty();
	}
	
	@Override
	public IntegerProperty getPositionProperty() {
		return manager.getPositionProperty();
	}
}
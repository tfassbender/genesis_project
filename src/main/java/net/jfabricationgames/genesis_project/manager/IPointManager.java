package net.jfabricationgames.genesis_project.manager;

import javafx.beans.property.IntegerProperty;

public interface IPointManager {
	
	public void addPoints(int points);
	public void setPoints(int points);
	public int getPoints();
	public int getPosition();
	
	public IntegerProperty getPointsProperty();
	public IntegerProperty getPositionProperty();
}
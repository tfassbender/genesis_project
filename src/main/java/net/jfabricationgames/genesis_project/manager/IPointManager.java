package net.jfabricationgames.genesis_project.manager;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javafx.beans.property.IntegerProperty;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({@JsonSubTypes.Type(value = PointManager.class, name = "PointManager")})
public interface IPointManager {
	
	public void addPoints(int points);
	public void setPoints(int points);
	public int getPoints();
	public int getPosition();
	
	public IntegerProperty getPointsProperty();
	public IntegerProperty getPositionProperty();
}
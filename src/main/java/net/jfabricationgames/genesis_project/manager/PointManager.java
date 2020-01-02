package net.jfabricationgames.genesis_project.manager;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.json.deserializer.CustomIntegerPropertyDeserializer;
import net.jfabricationgames.genesis_project.json.serializer.CustomIntegerPropertySerializer;

public class PointManager implements IPointManager {
	
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	@JsonDeserialize(using = CustomIntegerPropertyDeserializer.class)
	private IntegerProperty points = new SimpleIntegerProperty(this, "points");
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	@JsonDeserialize(using = CustomIntegerPropertyDeserializer.class)
	private IntegerProperty position = new SimpleIntegerProperty(this, "position");
	
	private Player player;
	
	/**
	 * DO NOT USE - empty constructor for json deserialization
	 */
	@Deprecated
	public PointManager() {
		
	}
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
	@JsonGetter("points")
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	public IntegerProperty getPointsProperty() {
		return points;
	}
	
	@Override
	@JsonGetter("position")
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	public IntegerProperty getPositionProperty() {
		return position;
	}
}
package net.jfabricationgames.genesis_project.game;

public class Field {
	
	private Board.Position position;
	private Planet planet;
	private final Building[] buildings = new Building[Constants.BUILDINGS_PER_PLANET];
	
	public Field(Board.Position position, Planet planet) {
		this.position = position;
		this.planet = planet;
	}
	
	public Board.Position getPosition() {
		return position;
	}
	
	public Planet getPlanet() {
		return planet;
	}
	
	public Building[] getBuildings() {
		return buildings;
	}
}
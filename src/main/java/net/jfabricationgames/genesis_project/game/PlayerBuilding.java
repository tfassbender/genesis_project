package net.jfabricationgames.genesis_project.game;

/**
 * Represents a building that is mapped to a specific player (the player that owns the building).
 */
public class PlayerBuilding {
	
	private Building building;
	private Player player;
	
	public PlayerBuilding(Building building, Player player) {
		this.building = building;
		this.player = player;
	}
	
	public Building getBuilding() {
		return building;
	}
	
	public Player getPlayer() {
		return player;
	}
}
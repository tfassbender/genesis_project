package net.jfabricationgames.genesis_project.game;

import java.util.Objects;

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
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PlayerBuilding) {
			PlayerBuilding pb = (PlayerBuilding) obj;
			return pb.getBuilding().equals(building) && pb.getPlayer().equals(player);
		}
		else {
			return super.equals(obj);
		}
	}
	@Override
	public String toString() {
		return "PlayerBuilding[" + building + "; " + player + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(building, player);
	}
	
	public Building getBuilding() {
		return building;
	}
	
	public Player getPlayer() {
		return player;
	}
}
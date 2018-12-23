package net.jfabricationgames.genesis_project.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Field {
	
	private Board.Position position;
	private Planet planet;
	private final PlayerBuilding[] buildings = new PlayerBuilding[Constants.BUILDINGS_PER_PLANET];
	private final List<PlayerBuilding> spaceBuildings;
	
	public Field(Board.Position position, Planet planet) {
		this.position = position;
		this.planet = planet;
		spaceBuildings = new ArrayList<PlayerBuilding>();
	}
	
	public Board.Position getPosition() {
		return position;
	}
	
	public Planet getPlanet() {
		return planet;
	}
	
	public PlayerBuilding[] getBuildings() {
		return buildings;
	}
	public List<PlayerBuilding> getSpaceBuildings() {
		return spaceBuildings;
	}
	
	/**
	 * Set a building without checking for any building conditions.
	 */
	public void build(PlayerBuilding building, int position) {
		if (building.getBuilding().isSpaceBuilding()) {
			if (building.getBuilding() == Building.SPACE_STATION) {
				//space station is an upgrade -> remove the drone
				Iterator<PlayerBuilding> iter = spaceBuildings.iterator();
				while (iter.hasNext()) {
					PlayerBuilding spaceBuilding = iter.next();
					if (spaceBuilding.getBuilding() == Building.DRONE && spaceBuilding.getPlayer().equals(building.getPlayer())) {
						iter.remove();//remove the DRONE that is upgraded
					}
				}
			}
			spaceBuildings.add(building);
		}
		else {
			buildings[position] = building;			
		}
	}
	
	public boolean isPlanetField() {
		return planet != null;
	}
}
package net.jfabricationgames.genesis_project.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.google.common.annotations.VisibleForTesting;

public class Field {
	
	private Board.Position position;
	private Planet planet;
	private final PlayerBuilding[] buildings;
	private final List<PlayerBuilding> spaceBuildings;
	
	public Field(Board.Position position, Planet planet, int players) {
		this.position = position;
		this.planet = planet;
		if (planet == Planet.CENTER) {
			//the center planet can have up to 5 buildings (one for each player)
			buildings = new PlayerBuilding[players];
		}
		else {
			//normal planets have a fixed number of buildings
			buildings = new PlayerBuilding[Constants.BUILDINGS_PER_PLANET];
		}
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
	
	@Override
	public int hashCode() {
		return Objects.hash(position, planet);
	}
	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}
	@Override
	public String toString() {
		return "Field[Position: " + position + "; Planet: " + planet.name() + "]";
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
	
	/**
	 * Indicates whether this field needs to be displayed.
	 * 
	 * @return Returns true if the field has any content that is to be displayed.
	 */
	public boolean isDisplayed() {
		return isPlanetField() || !getSpaceBuildings().isEmpty();
	}
	
	@VisibleForTesting
	public void setPlanet(Planet planet) {
		this.planet = planet;
	}
	
	public int calculateDefence() {
		//TODO Auto-generated method stub
		return -1;
	}
	
	public List<Alliance> getAlliances() {
		// TODO Auto-generated method stub
		return new ArrayList<Alliance>();
	}
}
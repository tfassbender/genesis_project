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
	 * Get the distance between the hexa-grid fields.
	 * 
	 * Algorithm from:<br>
	 * https://www.redblobgames.com/grids/hexagons/#conversions-offset (select odd-q or even-q)<br>
	 * https://www.redblobgames.com/grids/hexagons/#distances-cube
	 */
	public int distanceTo(Field field) {
		//convert both to cube coordinates
		int[] cubeThis = getAsCubeCoordinates();
		int[] cubeField = field.getAsCubeCoordinates();
		
		//distance in cube coordinates
		int distance = ((Math.abs(cubeThis[0] - cubeField[0]) + Math.abs(cubeThis[1] - cubeField[1]) + Math.abs(cubeThis[2] - cubeField[2])) / 2);
		
		return distance;
	}
	
	/**
	 * Get the position as cube coordinate.
	 * 
	 * Formula from:<br>
	 * https://www.redblobgames.com/grids/hexagons/#conversions-offset (select odd-q or even-q)
	 */
	public int[] getAsCubeCoordinates() {
		int x = position.getX();
		int z;
		if (position.getX() % 2 == 0) {
			//even-q
			z = position.getY() - (position.getX() + (position.getX() & 1)) / 2;
		}
		else {
			//odd-q
			z = position.getY() - (position.getX() - (position.getX() & 1)) / 2;
		}
		int y = -x - z;
		return new int[] {x, y, z};
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
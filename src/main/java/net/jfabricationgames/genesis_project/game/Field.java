package net.jfabricationgames.genesis_project.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.annotations.VisibleForTesting;

import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.genesis_project.manager.IAllianceManager;
import net.jfabricationgames.genesis_project.manager.IResearchManager;
import net.jfabricationgames.linear_algebra.Vector2D;

public class Field {
	
	private Board.Position position;
	private Planet planet;
	private PlayerBuilding[] buildings;
	private List<PlayerBuilding> spaceBuildings;
	
	/**
	 * DO NOT USE - empty constructor for json deserialization
	 */
	@Deprecated
	public Field() {
		
	}
	
	public Field(Board.Position position, Planet planet, int players) {
		this.position = position;
		this.planet = planet;
		if (planet == Planet.CENTER) {
			//the center planet can have up to 5 buildings (one for each player)
			buildings = new PlayerBuilding[players];
		}
		else {
			//normal planets have a fixed number of buildings
			buildings = new PlayerBuilding[Constants.getInstance().BUILDINGS_PER_PLANET];
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
		return "Field[Position: " + position + "; Planet: " + planet + "]";
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
	
	@JsonIgnore
	public boolean isPlanetField() {
		return planet != null;
	}
	
	/**
	 * Indicates whether this field needs to be displayed.
	 * 
	 * @return Returns true if the field has any content that is to be displayed.
	 */
	@JsonIgnore
	public boolean isDisplayed() {
		return isPlanetField() || !getSpaceBuildings().isEmpty();
	}
	
	@VisibleForTesting
	public void setPlanet(Planet planet) {
		this.planet = planet;
	}
	
	protected void setPosition(Position position) {
		this.position = position;
	}
	
	public boolean hasDefenseBuilding() {
		boolean hasDefenseBuildings = getSpaceBuildings().stream()
				.anyMatch(building -> building.getBuilding() == Building.DRONE || building.getBuilding() == Building.SPACE_STATION);
		return hasDefenseBuildings;
	}
	
	/**
	 * Calculate the total defense of this field (consists of WEAPON defense and defense buildings)
	 */
	public int calculateDefense(Board board, IResearchManager researchManager) {
		if (planet == Planet.CENTER) {
			return 99;//the center planet can't be attacked (till the end of the game)
		}
		List<Field> defenseFields = board.getFields().values().stream().filter(field -> field.hasDefenseBuilding()).collect(Collectors.toList());
		int defense = 0;
		//check every defense field if it's in range
		for (Field field : defenseFields) {
			//find a defense building (any because it can only be one)
			Optional<PlayerBuilding> defenseBuilding = field.getSpaceBuildings().stream()
					.filter(building -> building.getBuilding() == Building.DRONE || building.getBuilding() == Building.SPACE_STATION).findAny();
			
			if (defenseBuilding.isPresent()) {//should always be the case because we just iterate these fields...
				PlayerBuilding building = defenseBuilding.get();
				//get the range and defense of the building
				int range = 0;
				int defensePower = 0;
				if (building.getBuilding() == Building.DRONE) {
					range = building.getPlayer().getBuildingManager().getDroneFtl();
					defensePower = building.getPlayer().getBuildingManager().getDroneDefense();
				}
				else if (building.getBuilding() == Building.SPACE_STATION) {
					range = building.getPlayer().getBuildingManager().getSpaceStationFtl();
					defensePower = building.getPlayer().getBuildingManager().getSpaceStationDefense();
				}
				//check whether the field is in range
				if (range >= field.distanceTo(this)) {
					defense += defensePower;
				}
			}
		}
		
		//add the global additional defense by the WEAPON research area
		defense += researchManager.getAdditionalWeaponDefense();
		
		return defense;
	}
	
	/**
	 * Get a list of all alliances in which this field is included.
	 */
	public List<Alliance> getAlliances(IAllianceManager allianceManager) {
		List<Alliance> alliancesOnField = new ArrayList<Alliance>();
		List<Alliance> allAlliances = allianceManager.getAlliances();
		
		for (Alliance alliance : allAlliances) {
			boolean allianceIncludesField = false;
			List<Field> allianceFields;
			if (isPlanetField()) {
				allianceFields = alliance.getPlanets();
			}
			else {
				allianceFields = alliance.getConnectingSatellites();
			}
			
			for (Field field : allianceFields) {
				allianceIncludesField |= field.equals(this);
			}
			
			if (allianceIncludesField) {
				alliancesOnField.add(alliance);
			}
		}
		
		return alliancesOnField;
	}
	
	public List<PlayerBuilding> getPlayerBuildings(Player player) {
		List<PlayerBuilding> playerBuildings = Arrays.asList(buildings).stream()
				.filter(building -> building != null && building.getPlayer().equals(player)).collect(Collectors.toList());
		return playerBuildings;
	}
	public List<PlayerBuilding> getOtherPlayersBuildings(Player player) {
		List<PlayerBuilding> otherPlayerBuildings = Arrays.asList(buildings).stream()
				.filter(building -> building != null && !building.getPlayer().equals(player)).collect(Collectors.toList());
		return otherPlayerBuildings;
	}
	
	/**
	 * Check whether the planet contains at least one building of the player.
	 */
	public boolean containsPlayersBuildings(PlayerClass playerClass) {
		boolean containsBuilding = false;
		for (PlayerBuilding building : buildings) {
			containsBuilding |= building != null && building.getPlayer().getPlayerClass().equals(playerClass);
		}
		return containsBuilding;
	}
	
	/**
	 * Check whether the planet contains at least one building of any other player.
	 */
	public boolean containsOtherPlayersBuildings(PlayerClass playerClass) {
		boolean containsBuilding = false;
		for (PlayerBuilding building : buildings) {
			containsBuilding |= building != null && !building.getPlayer().getPlayerClass().equals(playerClass);
		}
		return containsBuilding;
	}
	
	public int getNumBuildings() {
		int numBuildings = 0;
		if (isPlanetField()) {
			for (PlayerBuilding building : buildings) {
				if (building != null) {
					numBuildings++;
				}
			}
		}
		else {
			numBuildings = spaceBuildings.size();
		}
		return numBuildings;
	}
	
	public static Vector2D toVector2D(Field field) {
		return field.getPosition().toVector2D();
	}
}
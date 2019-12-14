package net.jfabricationgames.genesis_project.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.annotations.VisibleForTesting;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import net.jfabricationgames.genesis_project.game.Board;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.BuildingResources;
import net.jfabricationgames.genesis_project.game.CompleteResources;
import net.jfabricationgames.genesis_project.game.Constants;
import net.jfabricationgames.genesis_project.game.DependentResources;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.Planet;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.PlayerBuilding;
import net.jfabricationgames.genesis_project.game.Resource;
import net.jfabricationgames.genesis_project.json.CustomIntegerPropertySerializer;

public class BuildingManager implements IBuildingManager {
	
	private Player player;
	
	//the buildings left on the class board
	@VisibleForTesting
	@JsonSerialize(contentUsing = CustomIntegerPropertySerializer.class)
	protected Map<Building, IntegerProperty> numBuildingsLeft;
	
	public BuildingManager(Player player) {
		this.player = player;
		if (Constants.BUILDING_NUMBERS != null) {
			numBuildingsLeft = new HashMap<Building, IntegerProperty>();
			for (Map.Entry<Building, Integer> buildings : Constants.BUILDING_NUMBERS.entrySet()) {
				IntegerProperty property = new SimpleIntegerProperty(this, "buildingsLeft_" + buildings.getKey().name());
				property.set(buildings.getValue().intValue());
				numBuildingsLeft.put(buildings.getKey(), property);
			}
		}
		else {
			throw new IllegalStateException(
					"The field Constants.BUILDING_NUMBERS has not yet bin initialized. Initialize it before creating a BuildingManager");
		}
	}
	
	@Override
	public int getNumBuildingsLeft(Building building) {
		return numBuildingsLeft.get(building).get();
	}
	@VisibleForTesting
	public void setNumBuildingsLeft(Building building, int left) {
		IntegerProperty buildingsLeft = numBuildingsLeft.get(building);
		buildingsLeft.set(left);
	}
	
	@Override
	public int getNumBuildingsOnField(Building building) {
		return Constants.BUILDING_NUMBERS.get(building).intValue() - getNumBuildingsLeft(building);
	}
	
	@Override
	public void build(Building building, Field field) throws IllegalStateException {
		if (!field.isPlanetField() && !(building == Building.SATELLITE || building == Building.DRONE || building == Building.SPACE_STATION)) {
			throw new IllegalArgumentException("Can't build a planetary building (" + building + ") on a space field.");
		}
		else if (field.isPlanetField() && (building == Building.SATELLITE || building == Building.DRONE || building == Building.SPACE_STATION)) {
			throw new IllegalArgumentException("Can't build a space building (" + building + ") on a planet field.");
		}
		
		PlayerBuilding playerBuilding = new PlayerBuilding(building, player);
		int position = findFirstPossibleBuildingPosition(building, field);
		if (position != -1) {
			field.build(playerBuilding, position);
			
			//take the resources
			BuildingResources resources = getResourcesNeededForBuilding(building, field);
			IResourceManager resourceManager = player.getResourceManager();
			resourceManager.reduceResources(resources);
		}
		else {
			throw new IllegalArgumentException("No possible position found for this building on this field.");
		}
	}
	
	/**
	 * Find a position on which the building can be built (or -1 if there is no such position). </br>
	 * For space buildings the returned index is always 0 because they are stored in a list (or -1 for not possible).
	 */
	@VisibleForTesting
	protected int findFirstPossibleBuildingPosition(Building building, Field field) {
		int firstPossibleField = -1;
		if (building.isSpaceBuilding()) {
			if (!field.isPlanetField()) {
				List<PlayerBuilding> spaceBuildings = field.getSpaceBuildings();
				if (building == Building.SATELLITE) {
					//one satellite per player is allowed on a space field
					Optional<PlayerBuilding> satelite = spaceBuildings.stream().filter((b) -> b.getPlayer().equals(player))
							.filter((b) -> b.getBuilding() == Building.SATELLITE).findAny();
					if (!satelite.isPresent()) {
						//satellite can be built
						firstPossibleField = 0;
					}
				}
				else if (building == Building.DRONE) {
					//only one drone or space station is allowed on a space field
					Optional<PlayerBuilding> militaryBuilding = spaceBuildings.stream()
							.filter((b) -> b.getBuilding() == Building.DRONE || b.getBuilding() == Building.SPACE_STATION).findAny();
					if (!militaryBuilding.isPresent()) {
						//drone can be built
						firstPossibleField = 0;
					}
				}
				else if (building == Building.SPACE_STATION) {
					//only one drone (or any satellites) of the building player has to be on the space field to upgrade it to a space station
					List<PlayerBuilding> militaryBuildings = spaceBuildings.stream()
							.filter((b) -> b.getBuilding() == Building.DRONE || b.getBuilding() == Building.SPACE_STATION)
							.collect(Collectors.toList());
					if (militaryBuildings.size() == 1) {
						PlayerBuilding onlyMilitaryBuilding = militaryBuildings.get(0);
						if (onlyMilitaryBuilding.getBuilding() == Building.DRONE && onlyMilitaryBuilding.getPlayer().equals(player)) {
							//only one drone of the building player on the space field -> space station can be built
							firstPossibleField = 0;
						}
					}
				}
			}
		}
		else if (field.isPlanetField()) {
			PlayerBuilding[] buildingsOnField = field.getBuildings();
			for (int i = 0; i < buildingsOnField.length; i++) {
				if (firstPossibleField == -1) {
					//no field found yet
					
					if (building == Building.COLONY && buildingsOnField[i] == null) {
						firstPossibleField = i;
					}
					else {
						//Colony is the only planetary building with no previous building
						Building prev = building.getPreviousBuilding();
						PlayerBuilding current = buildingsOnField[i];
						
						if (current != null && current.getPlayer().equals(player) && current.getBuilding() == prev) {
							//found a building that can be upgraded
							firstPossibleField = i;
						}
					}
				}
			}
		}
		return firstPossibleField;
	}
	
	@Override
	public boolean isResourcesAvailable(Building building, Field field) {
		BuildingResources buildingResources = getResourcesNeededForBuilding(building, field);
		return player.getResourceManager().isResourcesAvailable(buildingResources);
	}
	
	@Override
	public BuildingResources getResourcesNeededForBuilding(Building building, Field field) {
		BuildingResources resourcesNeeded = new BuildingResources();
		
		int[] costs = Constants.getBuildingCosts(building, getPlayer().getPlayerClass(), field.getPlanet());
		resourcesNeeded.addResources(getPlayer().getPlayerClass().getPrimaryResource(), costs[0]);
		resourcesNeeded.addResources(getPlayer().getPlayerClass().getSecundaryResource(), costs[1]);
		resourcesNeeded.addResources(getPlayer().getPlayerClass().getTertiaryResource(), costs[2]);
		
		return resourcesNeeded;
	}
	
	/**
	 * Check whether the field can be reached from the nearest planet of the player.
	 */
	@Override
	public boolean isFieldReachable(Field field) {
		Game game = player.getGame();
		Board board = game.getBoard();
		int distanceToField = board.getDistanceToNextPlayerField(field, player);
		int ftl = player.getResourceManager().getFTL();
		return ftl >= distanceToField;
	}
	
	@Override
	public CompleteResources getNextTurnsStartingResources() {
		CompleteResources earnings = new CompleteResources();
		
		Board board = player.getGame().getBoard();
		List<Field> playersPlanets = board.getPlayersPlanets(player);
		
		//iterate over all players buildings and count the resources
		for (Field field : playersPlanets) {
			Planet planet = field.getPlanet();
			
			//select the resources for primary, secondary and tertiary
			Resource primary;
			Resource secondary;
			Resource tertiary;
			if (planet.getPrimaryResource() != null) {
				//on normal planets the planets resources are used
				primary = planet.getPrimaryResource();
				secondary = planet.getSecondaryResource();
				tertiary = planet.getTertiaryResource();
			}
			else {
				//on genesis or center planets the players resources are used
				primary = player.getPlayerClass().getPrimaryResource();
				secondary = player.getPlayerClass().getSecundaryResource();
				tertiary = player.getPlayerClass().getTertiaryResource();
			}
			
			for (PlayerBuilding playerBuilding : field.getBuildings()) {
				if (playerBuilding != null && playerBuilding.getPlayer().equals(player)) {
					Building building = playerBuilding.getBuilding();
					
					//find the resources the building produces and add them to the total earnings
					DependentResources resources = Constants.BUILDING_EARNINGS_DEPENDENT.get(building);
					earnings.addResources(primary, resources.getResourcesPrimary());
					earnings.addResources(secondary, resources.getResourcesSecondary());
					earnings.addResources(tertiary, resources.getResourcesTertiary());
					
					earnings.addResearchPoints(Constants.BUILDING_EARNINGS_RESEARCH_POINTS.get(building));
					earnings.addScientists(Constants.BUILDING_EARNINGS_SCIENTISTS.get(building));
				}
			}
		}
		
		return earnings;
	}
	
	@Override
	public boolean canBuild(Building building, Field field) {
		return findFirstPossibleBuildingPosition(building, field) != -1 && getNumBuildingsLeft(building) > 0 && isResourcesAvailable(building, field)
				&& isFieldReachable(field);
	}
	
	/**
	 * Calculate the power of drones (depending on the the research state)
	 */
	@Override
	public int getDroneDefense() {
		int defense = 0;
		
		defense += Constants.BUILDING_EARNINGS_DEFENSE.get(Building.DRONE);
		defense += player.getResearchManager().getDroneAdditionalDefense();
		
		return defense;
	}
	/**
	 * Calculate the power of space stations (depending on the the research state)
	 */
	@Override
	public int getSpaceStationDefense() {
		int defense = 0;
		
		defense += Constants.BUILDING_EARNINGS_DEFENSE.get(Building.SPACE_STATION);
		defense += player.getResearchManager().getSpaceStationAdditionalDefense();
		
		return defense;
	}
	/**
	 * Calculate the range of drones (depending on the default FTL, alliance bonuses, technology bonuses and the research state)
	 */
	@Override
	public int getDroneFtl() {
		int range = 0;
		
		range += player.getResourceManager().getFTL();
		range += player.getResearchManager().getDroneAdditionalRange();
		range += player.getAllianceManager().getDefenseBuildingAdditionalRange();
		range += player.getTechnologyManager().getDefenseBuildingAdditionalRange();
		
		return range;
		
	}
	/**
	 * Calculate the range of space stations (depending on the default FTL, alliance bonuses, technology bonuses and the research state)
	 */
	@Override
	public int getSpaceStationFtl() {
		int range = 0;
		
		range += player.getResourceManager().getFTL();
		range += player.getResearchManager().getSpaceStationAdditionalRange();
		range += player.getAllianceManager().getDefenseBuildingAdditionalRange();
		range += player.getTechnologyManager().getDefenseBuildingAdditionalRange();
		
		return range;
	}
	
	@VisibleForTesting
	protected Player getPlayer() {
		return player;
	}
	
	@Override
	public IntegerProperty getNumBuildingsLeftProperty(Building building) {
		return numBuildingsLeft.get(building);
	}
}
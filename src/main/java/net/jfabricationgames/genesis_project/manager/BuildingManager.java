package net.jfabricationgames.genesis_project.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.annotations.VisibleForTesting;

import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Constants;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.PlayerBuilding;

public class BuildingManager implements IBuildingManager {
	
	private Player player;
	
	//the buildings left on the class board
	@VisibleForTesting
	protected Map<Building, Integer> numBuildingsLeft;
	
	public BuildingManager(Player player) {
		this.player = player;
		if (Constants.BUILDING_NUMBERS != null) {
			numBuildingsLeft = new HashMap<Building, Integer>(Constants.BUILDING_NUMBERS);
		}
		else {
			throw new IllegalStateException(
					"The field Constants.BUILDING_NUMBERS has not yet bin initialized. Initialize it before creating a BuildingManager");
		}
	}
	
	@Override
	public int getNumBuildingsLeft(Building building) {
		return numBuildingsLeft.get(building).intValue();
	}
	
	@Override
	public int getNumBuildingsOnField(Building building) {
		return Constants.BUILDING_NUMBERS.get(building).intValue() - getNumBuildingsLeft(building);
	}
	
	@Override
	public void build(Building building, Field field) throws IllegalStateException {
		PlayerBuilding playerBuilding = new PlayerBuilding(building, player);
		int position = findFirstPossibleBuildingPosition(building, field);
		if (position != -1) {
			field.build(playerBuilding, position);
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
							.filter((b) -> b.getBuilding() == Building.DRONE || b.getBuilding() == Building.SPACE_STATION).collect(Collectors.toList());
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
	
	/**
	 * Checks whether the building can be build on the field (without testing for any resources, ...)
	 * 
	 * Tested conditions are:
	 * <p>
	 * - Space left on the field (only for mines, drones and satellites) <br>
	 * - Player has a building on the field that can be upgraded (all but the above) <br>
	 * - The player has left at least one building of this type
	 * </p>
	 */
	@Override
	public boolean canBuild(Building building, Field field) {
		return findFirstPossibleBuildingPosition(building, field) != -1 && getNumBuildingsLeft(building) > 0;
	}
}
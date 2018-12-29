package net.jfabricationgames.genesis_project.manager;

import javafx.beans.property.IntegerProperty;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.BuildingResources;
import net.jfabricationgames.genesis_project.game.Field;

public interface IBuildingManager {
	
	public int getNumBuildingsLeft(Building building);
	public int getNumBuildingsOnField(Building building);
	
	/**
	 * Build the given building on the field and take the resources from the player.
	 */
	public void build(Building building, Field field);
	/**
	 * Checks whether the building can be build on the field (also checks for resources)
	 * 
	 * Tested conditions are:
	 * <p>
	 * - Space left on the field (only for mines, drones and satellites) </br>
	 * - Player has a building on the field that can be upgraded (all but the above) </br>
	 * - The player has left at least one building of this type </br>
	 * - The player has enough resources
	 * </p>
	 */
	public boolean canBuild(Building building, Field field);
	
	public boolean isResourcesAvailable(Building building, Field field);
	public BuildingResources getResourcesNeededForBuilding(Building building, Field field);
	
	public IntegerProperty getNumBuildingsLeftProperty(Building building);
}
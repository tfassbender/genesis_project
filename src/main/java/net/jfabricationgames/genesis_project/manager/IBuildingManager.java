package net.jfabricationgames.genesis_project.manager;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javafx.beans.property.IntegerProperty;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.BuildingResources;
import net.jfabricationgames.genesis_project.game.CompleteResources;
import net.jfabricationgames.genesis_project.game.Field;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({@JsonSubTypes.Type(value = BuildingManager.class, name = "BuildingManager")})
public interface IBuildingManager {
	
	public int getNumBuildingsLeft(Building building);
	public int getNumBuildingsOnField(Building building);
	
	/**
	 * Build the given building on the field and take the resources from the player.
	 */
	public void build(Building building, Field field);
	/**
	 * Build the given building on the field without paying because it is a start building (that is placed before the game is started).
	 */
	public void placeStartBuilding(Building building, Field field);
	/**
	 * Checks whether the building can be build on the field (also checks for resources)
	 * 
	 * Tested conditions are:
	 * <p>
	 * - Space left on the field (only for colonies, drones and satellites) </br>
	 * - Player has a building on the field that can be upgraded (all but colonies and drones) </br>
	 * - The player has left at least one building of this type </br>
	 * - The player has enough resources</br>
	 * - The player can reach the field with his current FTL state
	 * </p>
	 */
	public boolean canBuild(Building building, Field field);
	
	public boolean isFieldReachable(Field field);
	
	public boolean isResourcesAvailable(Building building, Field field);
	public BuildingResources getResourcesNeededForBuilding(Building building, Field field);
	
	/**
	 * Calculate the power of drones (depending on the the research state)
	 */
	public int getDroneDefense();
	/**
	 * Calculate the power of space stations (depending on the the research state)
	 */
	public int getSpaceStationDefense();
	/**
	 * Calculate the range of drones (depending on the default FTL, alliance bonuses, technology bonuses and the research state)
	 */
	public int getDroneFtl();
	/**
	 * Calculate the range of space stations (depending on the default FTL, alliance bonuses, technology bonuses and the research state)
	 */
	public int getSpaceStationFtl();
	
	public IntegerProperty getNumBuildingsLeftProperty(Building building);
	
	/**
	 * Calculate all resources a player would get from the buildings at the beginning of the next turn.
	 */
	public CompleteResources getNextTurnsStartingResources();
}
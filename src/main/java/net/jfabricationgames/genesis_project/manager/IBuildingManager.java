package net.jfabricationgames.genesis_project.manager;

import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Field;

public interface IBuildingManager {
	
	public int getNumBuildingsLeft(Building building);
	public int getNumBuildingsOnField(Building building);
	
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
}
package net.jfabricationgames.genesis_project.manager;

import javafx.beans.property.IntegerProperty;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.BuildingResources;
import net.jfabricationgames.genesis_project.game.CompleteResources;
import net.jfabricationgames.genesis_project.game.Field;

public class BuildingManagerProxy implements IBuildingManager {
	
	private IBuildingManager manager;
	
	public BuildingManagerProxy(IBuildingManager manager) {
		this.manager = manager;
	}
	
	@Override
	public int getNumBuildingsLeft(Building building) {
		return manager.getNumBuildingsLeft(building);
	}
	
	@Override
	public int getNumBuildingsOnField(Building building) {
		return manager.getNumBuildingsOnField(building);
	}
	
	@Override
	public void build(Building building, Field field) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	@Override
	public void placeStartBuilding(Building building, Field field) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public boolean canBuild(Building building, Field field) {
		return manager.canBuild(building, field);
	}
	
	@Override
	public boolean isFieldReachable(Field field) {
		return manager.isFieldReachable(field);
	}
	
	@Override
	public boolean isResourcesAvailable(Building building, Field field) {
		return manager.isResourcesAvailable(building, field);
	}
	
	@Override
	public BuildingResources getResourcesNeededForBuilding(Building building, Field field) {
		return manager.getResourcesNeededForBuilding(building, field);
	}
	
	@Override
	public int getDroneDefense() {
		return manager.getDroneDefense();
	}
	
	@Override
	public int getSpaceStationDefense() {
		return manager.getSpaceStationDefense();
	}
	
	@Override
	public int getDroneFtl() {
		return manager.getDroneFtl();
	}
	
	@Override
	public int getSpaceStationFtl() {
		return manager.getSpaceStationFtl();
	}
	
	@Override
	public IntegerProperty getNumBuildingsLeftProperty(Building building) {
		return manager.getNumBuildingsLeftProperty(building);
	}
	
	@Override
	public CompleteResources getNextTurnsStartingResources() {
		return manager.getNextTurnsStartingResources();
	}
}
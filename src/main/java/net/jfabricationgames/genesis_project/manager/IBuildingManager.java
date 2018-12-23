package net.jfabricationgames.genesis_project.manager;

import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Field;

public interface IBuildingManager {
	
	public int getNumBuildingsLeft(Building building);
	public int getNumBuildingsOnField(Building building);
	
	public void build(Building building, Field field);
	public boolean canBuild(Building building, Field field);
}
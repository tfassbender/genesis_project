package net.jfabricationgames.genesis_project.manager;

import net.jfabricationgames.genesis_project.game.BuildingResources;
import net.jfabricationgames.genesis_project.game.ResearchArea;

public interface IResearchManager {
	
	public int getState(ResearchArea area);
	public int increaseState(ResearchArea area);
	
	public BuildingResources getBuildingResourcesNeededTotal(ResearchArea area, int state);
	public BuildingResources getBuildingResourcesNeededLeft(ResearchArea area, int state);
	public BuildingResources getBuildingResourcesAdded(ResearchArea area, int state);
	
	public int getScientistsNeededTotal(ResearchArea area, int state);
	public int getScientistsNeededLeft(ResearchArea area, int state);
	public int getScientistsAdded(ResearchArea area, int state);
}
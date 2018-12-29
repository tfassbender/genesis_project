package net.jfabricationgames.genesis_project.manager;

import javafx.beans.property.IntegerProperty;
import net.jfabricationgames.genesis_project.game.BuildingResources;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Resource;

public interface IResourceManager {
	
	public int getResourcesC();
	public void setResourcesC(int resources);
	public void addResourcesC(int resources);
	
	public int getResourcesSi();
	public void setResourcesSi(int resources);
	public void addResourcesSi(int resources);
	
	public int getResourcesFe();
	public void setResourcesFe(int resources);
	public void addResourcesFe(int resources);
	
	public int getResourcesPrimary();
	public void setResourcesPrimary(int resources);
	public void addResourcesPrimary(int resources);
	
	public int getResourcesSecundary();
	public void setResourcesSecundary(int resources);
	public void addResourcesSecundary(int resources);
	
	public int getResourcesTertiary();
	public void setResourcesTertiary(int resources);
	public void addResourcesTertiary(int resources);
	
	public int getResearchPoints();
	public void setResearchPoints(int points);
	public void addResearchPoints(int points);
	
	public int getScientists();
	public void setScientists(int scientists);
	public void addScientists(int scientists);
	
	public int getFTL();
	public void setFTL(int ftl);
	public void addFTL(int ftl);
	
	public boolean isResourceAvailable(Resource resource, int amount);
	public boolean isResourcesAvailable(BuildingResources resources);
	public boolean isResourcesAvailable(ResearchResources resources);
	
	public int getResources(Resource resource);
	public void setResources(Resource resource, int amount);
	public void addResources(Resource resource, int amount);
	public void reduceResources(Resource resource, int amount);
	
	public void addResources(BuildingResources resources);
	public void reduceResources(BuildingResources resources);
	
	public void addResources(ResearchResources resources);
	public void reduceResources(ResearchResources resources);
	
	/**
	 * Collect all resources a player gets at the start of the game.
	 */
	public void collectGameStartResources();
	/**
	 * Collect all resources a player gets at the start of the turn.
	 */
	public void collectTurnStartResources();
	

	public IntegerProperty getResourcesCProperty();
	public IntegerProperty getResourcesSiProperty();
	public IntegerProperty getResourcesFeProperty();
	public IntegerProperty getResearchPointsProperty();
	public IntegerProperty getScientistsProperty();
	public IntegerProperty getFTLProperty();
}
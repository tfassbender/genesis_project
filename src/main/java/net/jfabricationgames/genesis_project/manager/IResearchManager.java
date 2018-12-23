package net.jfabricationgames.genesis_project.manager;

import net.jfabricationgames.genesis_project.game.ResearchArea;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Resource;

public interface IResearchManager {
	
	public int getState(ResearchArea area);
	public void increaseState(ResearchArea area);
	
	public boolean isStateAccessible(ResearchArea area, int state);
	
	public ResearchResources getResearchResourcesNeededTotal(ResearchArea area, int state);
	public ResearchResources getResearchResourcesNeededLeft(ResearchArea area, int state);
	public ResearchResources getResearchResourcesAdded(ResearchArea area, int state);
	
	public void addResearchResources(Resource resource, int amount, ResearchArea area, int state);
	public void addResearchResources(ResearchResources resources, ResearchArea area, int state);
}
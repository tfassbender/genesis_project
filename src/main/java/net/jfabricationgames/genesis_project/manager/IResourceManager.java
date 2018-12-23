package net.jfabricationgames.genesis_project.manager;

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
	
	public void collectGameStartResources();
	public void collectTurnStartResources();
}
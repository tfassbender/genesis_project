package net.jfabricationgames.genesis_project.manager;

import net.jfabricationgames.genesis_project.game.BuildingResources;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Resource;

public class ResourceManager implements IResourceManager {
	
	private Player player;
	
	private BuildingResources buildingResources;
	private int researchPoints;
	private int scientists;
	private int ftl;
	
	public ResourceManager(Player player) {
		this.player = player;
		buildingResources = new BuildingResources();
	}
	
	@Override
	public int getResourcesC() {
		return buildingResources.getResourcesC();
	}
	@Override
	public void setResourcesC(int resources) {
		buildingResources.setResourcesC(resources);
	}
	@Override
	public void addResourcesC(int resources) {
		buildingResources.addResourcesC(resources);
	}
	
	@Override
	public int getResourcesSi() {
		return buildingResources.getResourcesSi();
	}
	@Override
	public void setResourcesSi(int resources) {
		buildingResources.setResourcesSi(resources);
	}
	@Override
	public void addResourcesSi(int resources) {
		buildingResources.addResourcesSi(resources);
	}
	
	@Override
	public int getResourcesFe() {
		return buildingResources.getResourcesFe();
	}
	@Override
	public void setResourcesFe(int resources) {
		buildingResources.setResourcesFe(resources);
	}
	
	@Override
	public void addResourcesFe(int resources) {
		buildingResources.addResourcesFe(resources);
	}
	
	@Override
	public int getResearchPoints() {
		return researchPoints;
	}
	@Override
	public void setResearchPoints(int points) {
		this.researchPoints = points;
	}
	@Override
	public void addResearchPoints(int points) {
		this.researchPoints += points;
	}
	
	@Override
	public int getScientists() {
		return scientists;
	}
	@Override
	public void setScientists(int scientists) {
		this.scientists = scientists;
	}
	@Override
	public void addScientists(int scientists) {
		this.scientists += scientists;
	}
	
	@Override
	public int getFTL() {
		return ftl;
	}
	@Override
	public void setFTL(int ftl) {
		this.ftl = ftl;
	}
	@Override
	public void addFTL(int ftl) {
		this.ftl += ftl;
	}
	
	@Override
	public boolean isResourceAvailable(Resource resource, int amount) {
		return getResources(resource) >= amount;
	}
	@Override
	public boolean isResourcesAvailable(BuildingResources resources) {
		boolean resourcesAvailable = true;
		
		for (Resource resource : BuildingResources.BUILDING_RESOURCES) {
			resourcesAvailable &= isResourceAvailable(resource, resources.getResources(resource));
		}
		
		return resourcesAvailable;
	}
	@Override
	public boolean isResourcesAvailable(ResearchResources resources) {
		boolean resourcesAvailable = true;
		
		for (Resource resource : ResearchResources.RESEARCH_RESOURCES) {
			resourcesAvailable &= isResourceAvailable(resource, resources.getResources(resource));
		}
		
		return resourcesAvailable;
	}

	@Override
	public int getResources(Resource resource) {
		switch (resource) {
			case CARBON:
				return getResourcesC();
			case FTL:
				return getFTL();
			case IRON:
				return getResourcesFe();
			case RESEARCH_POINTS:
				return getResearchPoints();
			case SCIENTISTS:
				return getScientists();
			case SILICIUM:
				return getResourcesSi();
			default:
				throw new IllegalArgumentException("The Resource " + resource + " is unknown");
		}
	}
	@Override
	public void setResources(Resource resource, int amount) {
		switch (resource) {
			case CARBON:
				setResourcesC(amount);
				break;
			case FTL:
				setFTL(amount);
				break;
			case IRON:
				setResourcesFe(amount);
				break;
			case RESEARCH_POINTS:
				setResearchPoints(amount);
				break;
			case SCIENTISTS:
				setScientists(amount);
				break;
			case SILICIUM:
				setResourcesSi(amount);
				break;
			default:
				throw new IllegalArgumentException("The Resource " + resource + " is unknown");
		}
	}
	@Override
	public void addResources(Resource resource, int amount) {
		switch (resource) {
			case CARBON:
				addResourcesC(amount);
				break;
			case FTL:
				addFTL(amount);
				break;
			case IRON:
				addResourcesFe(amount);
				break;
			case RESEARCH_POINTS:
				addResearchPoints(amount);
				break;
			case SCIENTISTS:
				addScientists(amount);
				break;
			case SILICIUM:
				addResourcesSi(amount);
				break;
			default:
				throw new IllegalArgumentException("The Resource " + resource + " is unknown");
		}
	}
	@Override
	public void reduceResources(Resource resource, int amount) {
		switch (resource) {
			case CARBON:
				addResourcesC(-amount);
				break;
			case FTL:
				addFTL(-amount);
				break;
			case IRON:
				addResourcesFe(-amount);
				break;
			case RESEARCH_POINTS:
				addResearchPoints(-amount);
				break;
			case SCIENTISTS:
				addScientists(-amount);
				break;
			case SILICIUM:
				addResourcesSi(-amount);
				break;
			default:
				throw new IllegalArgumentException("The Resource " + resource + " is unknown");
		}
	}

	@Override
	public void addResources(BuildingResources resources) {
		for (Resource resource : BuildingResources.BUILDING_RESOURCES) {
			addResources(resource, resources.getResources(resource));
		}
	}
	@Override
	public void reduceResources(BuildingResources resources) {
		for (Resource resource : BuildingResources.BUILDING_RESOURCES) {
			reduceResources(resource, resources.getResources(resource));
		}
	}

	@Override
	public void addResources(ResearchResources resources) {
		for (Resource resource : ResearchResources.RESEARCH_RESOURCES) {
			addResources(resource, resources.getResources(resource));
		}
	}
	@Override
	public void reduceResources(ResearchResources resources) {
		for (Resource resource : ResearchResources.RESEARCH_RESOURCES) {
			reduceResources(resource, resources.getResources(resource));
		}
	}
	
	@Override
	public void collectGameStartResources() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void collectTurnStartResources() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getResourcesPrimary() {
		return buildingResources.getResourcesPrimary(player.getPlayerClass());
	}
	@Override
	public void setResourcesPrimary(int resources) {
		buildingResources.setResourcesPrimary(player.getPlayerClass(), resources);
	}
	@Override
	public void addResourcesPrimary(int resources) {
		buildingResources.addResourcesPrimary(player.getPlayerClass(), resources);
	}
	
	@Override
	public int getResourcesSecundary() {
		return buildingResources.getResourcesSecundary(player.getPlayerClass());
	}
	@Override
	public void setResourcesSecundary(int resources) {
		buildingResources.setResourcesSecundary(player.getPlayerClass(), resources);
	}
	@Override
	public void addResourcesSecundary(int resources) {
		buildingResources.addResourcesSecundary(player.getPlayerClass(), resources);
	}
	
	@Override
	public int getResourcesTertiary() {
		return buildingResources.getResourcesTertiary(player.getPlayerClass());
	}
	@Override
	public void setResourcesTertiary(int resources) {
		buildingResources.setResourcesTertiary(player.getPlayerClass(), resources);
	}
	@Override
	public void addResourcesTertiary(int resources) {
		buildingResources.addResourcesTertiary(player.getPlayerClass(), resources);
	}
}
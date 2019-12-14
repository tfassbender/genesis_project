package net.jfabricationgames.genesis_project.manager;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import net.jfabricationgames.genesis_project.game.BuildingResources;
import net.jfabricationgames.genesis_project.game.CompleteResources;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Resource;
import net.jfabricationgames.genesis_project.json.deserializer.CustomIntegerPropertyDeserializer;
import net.jfabricationgames.genesis_project.json.serializer.CustomIntegerPropertySerializer;

public class ResourceManager implements IResourceManager {
	
	private Player player;
	
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	@JsonDeserialize(using = CustomIntegerPropertyDeserializer.class)
	private IntegerProperty resourcesC = new SimpleIntegerProperty(this, "resourcesC");
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	@JsonDeserialize(using = CustomIntegerPropertyDeserializer.class)
	private IntegerProperty resourcesSi = new SimpleIntegerProperty(this, "resourcesSi");
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	@JsonDeserialize(using = CustomIntegerPropertyDeserializer.class)
	private IntegerProperty resourcesFe = new SimpleIntegerProperty(this, "resourcesFe");
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	@JsonDeserialize(using = CustomIntegerPropertyDeserializer.class)
	private IntegerProperty researchPoints = new SimpleIntegerProperty(this, "researchPoints");
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	@JsonDeserialize(using = CustomIntegerPropertyDeserializer.class)
	private IntegerProperty scientists = new SimpleIntegerProperty(this, "scientists");
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	@JsonDeserialize(using = CustomIntegerPropertyDeserializer.class)
	private IntegerProperty ftl = new SimpleIntegerProperty(this, "ftl");
	
	/**
	 * DO NOT USE - empty constructor for json deserialization
	 */
	@Deprecated
	public ResourceManager() {
		
	}
	
	public ResourceManager(Player player) {
		this.player = player;
	}
	
	@Override
	public int getResourcesC() {
		return resourcesC.get();
	}
	@Override
	public void setResourcesC(int resources) {
		resourcesC.set(resources);
	}
	@Override
	public void addResourcesC(int resources) {
		resourcesC.set(resourcesC.get() + resources);
	}
	
	@Override
	public int getResourcesSi() {
		return resourcesSi.get();
	}
	@Override
	public void setResourcesSi(int resources) {
		resourcesSi.set(resources);
	}
	@Override
	public void addResourcesSi(int resources) {
		resourcesSi.set(resourcesSi.get() + resources);
	}
	
	@Override
	public int getResourcesFe() {
		return resourcesFe.get();
	}
	@Override
	public void setResourcesFe(int resources) {
		resourcesFe.set(resources);
	}
	
	@Override
	public void addResourcesFe(int resources) {
		resourcesFe.set(resourcesFe.get() + resources);
	}
	
	@Override
	public int getResearchPoints() {
		return researchPoints.get();
	}
	@Override
	public void setResearchPoints(int points) {
		this.researchPoints.set(points);
	}
	@Override
	public void addResearchPoints(int points) {
		this.researchPoints.set(this.researchPoints.get() + points);
	}
	
	@Override
	public int getScientists() {
		return scientists.get();
	}
	@Override
	public void setScientists(int scientists) {
		this.scientists.set(scientists);
	}
	@Override
	public void addScientists(int scientists) {
		this.scientists.set(this.scientists.get() + scientists);
	}
	
	@Override
	public int getFTL() {
		return ftl.get();
	}
	@Override
	public void setFTL(int ftl) {
		this.ftl.set(ftl);
	}
	@Override
	public void addFTL(int ftl) {
		this.ftl.set(this.ftl.get() + ftl);
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
	public boolean isResourcesAvailable(CompleteResources resources) {
		boolean resourcesAvailable = true;
		
		for (Resource resource : CompleteResources.COMPLETE_RESOURCES) {
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
		if (getResources(resource) < amount) {
			throw new IllegalArgumentException("Trying to take more resources than present (type: " + resource + "current: " + getResources(resource)
					+ "; to be taken: " + amount + ")");
		}
		
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
		if (!isResourcesAvailable(resources)) {
			throw new IllegalArgumentException(
					"Trying to take more resources than present (current: " + getBuildingResources() + "; to be taken: " + resources + ")");
		}
		for (Resource resource : BuildingResources.BUILDING_RESOURCES) {
			reduceResources(resource, resources.getResources(resource));
		}
	}
	
	@Override
	public void addResources(CompleteResources resources) {
		for (Resource resource : CompleteResources.COMPLETE_RESOURCES) {
			addResources(resource, resources.getResources(resource));
		}
	}
	@Override
	public void reduceResources(CompleteResources resources) {
		if (!isResourcesAvailable(resources)) {
			throw new IllegalArgumentException(
					"Trying to take more resources than present (current: " + getCompleteResources() + "; to be taken: " + resources + ")");
		}
		for (Resource resource : CompleteResources.COMPLETE_RESOURCES) {
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
		if (!isResourcesAvailable(resources)) {
			throw new IllegalArgumentException(
					"Trying to take more resources than present (current: " + getResearchResources() + "; to be taken: " + resources + ")");
		}
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
		return getResources(player.getPlayerClass().getPrimaryResource());
	}
	@Override
	public void setResourcesPrimary(int resources) {
		setResources(player.getPlayerClass().getPrimaryResource(), resources);
	}
	@Override
	public void addResourcesPrimary(int resources) {
		addResources(player.getPlayerClass().getPrimaryResource(), resources);
	}
	
	@Override
	public int getResourcesSecundary() {
		return getResources(player.getPlayerClass().getSecundaryResource());
	}
	@Override
	public void setResourcesSecundary(int resources) {
		setResources(player.getPlayerClass().getSecundaryResource(), resources);
	}
	@Override
	public void addResourcesSecundary(int resources) {
		addResources(player.getPlayerClass().getSecundaryResource(), resources);
	}
	
	@Override
	public int getResourcesTertiary() {
		return getResources(player.getPlayerClass().getTertiaryResource());
	}
	@Override
	public void setResourcesTertiary(int resources) {
		setResources(player.getPlayerClass().getTertiaryResource(), resources);
	}
	@Override
	public void addResourcesTertiary(int resources) {
		addResources(player.getPlayerClass().getTertiaryResource(), resources);
	}
	
	@Override
	public BuildingResources getBuildingResources() {
		return new BuildingResources(getResourcesC(), getResourcesSi(), getResourcesFe());
	}
	@Override
	public ResearchResources getResearchResources() {
		return new ResearchResources(getResourcesC(), getResourcesSi(), getResourcesFe(), getScientists());
	}
	@Override
	public CompleteResources getCompleteResources() {
		return new CompleteResources(getResourcesC(), getResourcesSi(), getResourcesFe(), getScientists(), getResearchPoints(), getFTL());
	}
	
	@Override
	@JsonGetter("resourcesC")
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	public IntegerProperty getResourcesCProperty() {
		return resourcesC;
	}
	@Override
	@JsonGetter("resourcesSi")
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	public IntegerProperty getResourcesSiProperty() {
		return resourcesSi;
	}
	@Override
	@JsonGetter("resourcesFe")
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	public IntegerProperty getResourcesFeProperty() {
		return resourcesFe;
	}
	@Override
	@JsonGetter("researchPoints")
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	public IntegerProperty getResearchPointsProperty() {
		return researchPoints;
	}
	@Override
	@JsonGetter("scientists")
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	public IntegerProperty getScientistsProperty() {
		return scientists;
	}
	@Override
	@JsonGetter("ftl")
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	public IntegerProperty getFTLProperty() {
		return ftl;
	}
}
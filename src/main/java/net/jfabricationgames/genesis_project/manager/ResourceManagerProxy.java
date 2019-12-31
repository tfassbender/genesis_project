package net.jfabricationgames.genesis_project.manager;

import javafx.beans.property.IntegerProperty;
import net.jfabricationgames.genesis_project.game.BuildingResources;
import net.jfabricationgames.genesis_project.game.CompleteResources;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Resource;

public class ResourceManagerProxy implements IResourceManager {
	
	private IResourceManager manager;
	
	public ResourceManagerProxy(IResourceManager manager) {
		this.manager = manager;
	}
	
	@Override
	public int getResourcesC() {
		return manager.getResourcesC();
	}
	@Override
	public void setResourcesC(int resources) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	@Override
	public void addResourcesC(int resources) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public int getResourcesSi() {
		return manager.getResourcesSi();
	}
	@Override
	public void setResourcesSi(int resources) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	@Override
	public void addResourcesSi(int resources) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public int getResourcesFe() {
		return manager.getResourcesFe();
	}
	@Override
	public void setResourcesFe(int resources) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	@Override
	public void addResourcesFe(int resources) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public int getResourcesPrimary() {
		return manager.getResourcesPrimary();
	}
	@Override
	public void setResourcesPrimary(int resources) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	@Override
	public void addResourcesPrimary(int resources) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public int getResourcesSecundary() {
		return manager.getResourcesSecundary();
	}
	@Override
	public void setResourcesSecundary(int resources) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	@Override
	public void addResourcesSecundary(int resources) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public int getResourcesTertiary() {
		return manager.getResourcesTertiary();
	}
	@Override
	public void setResourcesTertiary(int resources) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	@Override
	public void addResourcesTertiary(int resources) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public int getResearchPoints() {
		return manager.getResearchPoints();
	}
	@Override
	public void setResearchPoints(int points) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	@Override
	public void addResearchPoints(int points) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public int getScientists() {
		return manager.getScientists();
	}
	@Override
	public void setScientists(int scientists) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	@Override
	public void addScientists(int scientists) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public int getFTL() {
		return manager.getFTL();
	}
	@Override
	public void setFTL(int ftl) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	@Override
	public void addFTL(int ftl) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public boolean isResourceAvailable(Resource resource, int amount) {
		return manager.isResourceAvailable(resource, amount);
	}
	
	@Override
	public boolean isResourcesAvailable(BuildingResources resources) {
		return manager.isResourcesAvailable(resources);
	}
	
	@Override
	public boolean isResourcesAvailable(ResearchResources resources) {
		return manager.isResourcesAvailable(resources);
	}
	
	@Override
	public boolean isResourcesAvailable(CompleteResources resources) {
		return manager.isResourcesAvailable(resources);
	}
	
	@Override
	public int getResources(Resource resource) {
		return manager.getResources(resource);
	}
	@Override
	public void setResources(Resource resource, int amount) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	@Override
	public void addResources(Resource resource, int amount) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	@Override
	public void reduceResources(Resource resource, int amount) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public void addResources(BuildingResources resources) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	@Override
	public void reduceResources(BuildingResources resources) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public void addResources(ResearchResources resources) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	@Override
	public void reduceResources(ResearchResources resources) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public void addResources(CompleteResources resources) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	@Override
	public void reduceResources(CompleteResources resources) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public BuildingResources getBuildingResources() {
		return manager.getBuildingResources();
	}
	@Override
	public ResearchResources getResearchResources() {
		return manager.getResearchResources();
	}
	@Override
	public CompleteResources getCompleteResources() {
		return manager.getCompleteResources();
	}
	
	@Override
	public void collectGameStartResources() {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	@Override
	public void collectTurnStartResources() {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public IntegerProperty getResourcesCProperty() {
		return manager.getResourcesCProperty();
	}
	
	@Override
	public IntegerProperty getResourcesSiProperty() {
		return manager.getResourcesSiProperty();
	}
	
	@Override
	public IntegerProperty getResourcesFeProperty() {
		return manager.getResourcesFeProperty();
	}
	
	@Override
	public IntegerProperty getResearchPointsProperty() {
		return manager.getResearchPointsProperty();
	}
	
	@Override
	public IntegerProperty getScientistsProperty() {
		return manager.getScientistsProperty();
	}
	
	@Override
	public IntegerProperty getFTLProperty() {
		return manager.getFTLProperty();
	}
}
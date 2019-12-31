package net.jfabricationgames.genesis_project.manager;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import net.jfabricationgames.genesis_project.game.ResearchArea;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Resource;

public class ResearchManagerProxy implements IResearchManager {
	
	private IResearchManager manager;
	
	public ResearchManagerProxy(IResearchManager manager) {
		this.manager = manager;
	}
	
	@Override
	public int getState(ResearchArea area) {
		return manager.getState(area);
	}
	
	@Override
	public void increaseState(ResearchArea area) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public boolean isStateAccessible(ResearchArea area, int state) {
		return manager.isStateAccessible(area, state);
	}
	
	@Override
	public int getNextResourceNeedingState(ResearchArea area) {
		return manager.getNextResourceNeedingState(area);
	}
	
	@Override
	public ResearchResources getResearchResourcesNeededTotal(ResearchArea area, int state) {
		return manager.getResearchResourcesNeededTotal(area, state);
	}
	
	@Override
	public ResearchResources getResearchResourcesNeededLeft(ResearchArea area, int state) {
		return manager.getResearchResourcesNeededLeft(area, state);
	}
	
	@Override
	public ResearchResources getResearchResourcesAdded(ResearchArea area, int state) {
		return manager.getResearchResourcesAdded(area, state);
	}
	
	@Override
	public int getDroneAdditionalDefense() {
		return manager.getDroneAdditionalDefense();
	}
	
	@Override
	public int getSpaceStationAdditionalDefense() {
		return manager.getSpaceStationAdditionalDefense();
	}
	
	@Override
	public int getDroneAdditionalRange() {
		return manager.getDroneAdditionalRange();
	}
	
	@Override
	public int getSpaceStationAdditionalRange() {
		return manager.getSpaceStationAdditionalRange();
	}
	
	@Override
	public int getAdditionalWeaponDefense() {
		return manager.getAdditionalWeaponDefense();
	}
	
	@Override
	public void addResearchResources(Resource resource, int amount, ResearchArea area) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public void addResearchResources(ResearchResources resources, ResearchArea area) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public IntegerProperty getStateProperty(ResearchArea area) {
		return manager.getStateProperty(area);
	}
	
	@Override
	public IntegerProperty getMaxReachableStateProperty(ResearchArea area) {
		return manager.getMaxReachableStateProperty(area);
	}
	
	@Override
	public ObjectProperty<ResearchResources> getResearchResourcesNeededLeftProperties(ResearchArea area) {
		return manager.getResearchResourcesNeededLeftProperties(area);
	}
}
package net.jfabricationgames.genesis_project.manager;

import com.google.common.annotations.VisibleForTesting;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.ResearchArea;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Resource;

public class ResearchManagerCompositum implements IResearchManager {
	
	private Game game;
	
	private IResearchManager globalManager;
	
	/**
	 * DO NOT USE - empty constructor for json deserialization
	 */
	@Deprecated
	public ResearchManagerCompositum() {
		
	}
	
	public ResearchManagerCompositum(Game game) {
		this.game = game;
		globalManager = new ResearchManager(null, game.getPlayers().size());
	}
	
	@Override
	public int getState(ResearchArea area) {
		if (area == ResearchArea.WEAPON) {
			return globalManager.getState(ResearchArea.WEAPON);
		}
		else {
			throw new IllegalArgumentException("The compositum implementation can only handle WEAPON states");
		}
	}
	
	@Override
	public void increaseState(ResearchArea area) {
		if (area == ResearchArea.WEAPON) {
			globalManager.increaseState(ResearchArea.WEAPON);
			game.getPlayers().forEach((p) -> p.getResearchManager().increaseState(ResearchArea.WEAPON));
		}
		else {
			throw new IllegalArgumentException("The compositum implementation can only handle WEAPON states");
		}
	}
	
	@Override
	public boolean isStateAccessible(ResearchArea area, int state) {
		return globalManager.isStateAccessible(area, state);
	}
	
	@Override
	public int getNextResourceNeedingState(ResearchArea area) {
		return globalManager.getNextResourceNeedingState(area);
	}
	
	@Override
	public ResearchResources getResearchResourcesNeededTotal(ResearchArea area, int state) {
		return globalManager.getResearchResourcesNeededTotal(area, state);
	}
	
	@Override
	public ResearchResources getResearchResourcesNeededLeft(ResearchArea area, int state) {
		return globalManager.getResearchResourcesNeededLeft(area, state);
	}
	
	@Override
	public ResearchResources getResearchResourcesAdded(ResearchArea area, int state) {
		return globalManager.getResearchResourcesAdded(area, state);
	}
	
	@Override
	public void addResearchResources(Resource resource, int amount, ResearchArea area) {
		globalManager.addResearchResources(resource, amount, area);
	}
	
	@Override
	public void addResearchResources(ResearchResources resources, ResearchArea area) {
		globalManager.addResearchResources(resources, area);
	}
	
	@VisibleForTesting
	protected Game getGame() {
		return game;
	}
	
	@Override
	public IntegerProperty getStateProperty(ResearchArea area) {
		if (area == ResearchArea.WEAPON) {
			return globalManager.getStateProperty(ResearchArea.WEAPON);
		}
		else {
			throw new IllegalArgumentException("The compositum implementation can only handle WEAPON states");
		}
	}
	
	@Override
	public int getDroneAdditionalDefense() {
		throw new UnsupportedOperationException("The compositum implementation has no user bound functions");
	}
	@Override
	public int getSpaceStationAdditionalDefense() {
		throw new UnsupportedOperationException("The compositum implementation has no user bound functions");
	}
	@Override
	public int getDroneAdditionalRange() {
		throw new UnsupportedOperationException("The compositum implementation has no user bound functions");
	}
	@Override
	public int getSpaceStationAdditionalRange() {
		throw new UnsupportedOperationException("The compositum implementation has no user bound functions");
	}
	/**
	 * Get the additional defense by the WEAPON research area (for all fields)
	 */
	@Override
	public int getAdditionalWeaponDefense() {
		return globalManager.getAdditionalWeaponDefense();
	}
	
	@Override
	public IntegerProperty getMaxReachableStateProperty(ResearchArea area) {
		return globalManager.getMaxReachableStateProperty(area);
	}
	
	@Override
	public ObjectProperty<ResearchResources> getResearchResourcesNeededLeftProperties(ResearchArea area) {
		return globalManager.getResearchResourcesNeededLeftProperties(area);
	}
}
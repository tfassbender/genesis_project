package net.jfabricationgames.genesis_project.manager;

import com.google.common.annotations.VisibleForTesting;

import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.ResearchArea;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Resource;

public class ResearchManagerCompositum implements IResearchManager {
	
	private Game game;
	
	private ResearchManager globalManager;
	
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
		if (area == ResearchArea.WEAPON) {
			return globalManager.isStateAccessible(area, state);
		}
		else {
			throw new IllegalArgumentException("The compositum implementation can only handle WEAPON states");
		}
	}
	
	@Override
	public int getNextResourceNeedingState(ResearchArea area) {
		if (area == ResearchArea.WEAPON) {
			return globalManager.getNextResourceNeedingState(area);
		}
		else {
			throw new IllegalArgumentException("The compositum implementation can only handle WEAPON states");
		}
	}
	
	@Override
	public ResearchResources getResearchResourcesNeededTotal(ResearchArea area, int state) {
		if (area == ResearchArea.WEAPON) {
			return globalManager.getResearchResourcesNeededTotal(area, state);
		}
		else {
			throw new IllegalArgumentException("The compositum implementation can only handle WEAPON states");
		}
	}
	
	@Override
	public ResearchResources getResearchResourcesNeededLeft(ResearchArea area, int state) {
		if (area == ResearchArea.WEAPON) {
			return globalManager.getResearchResourcesNeededLeft(area, state);
		}
		else {
			throw new IllegalArgumentException("The compositum implementation can only handle WEAPON states");
		}
	}
	
	@Override
	public ResearchResources getResearchResourcesAdded(ResearchArea area, int state) {
		if (area == ResearchArea.WEAPON) {
			return globalManager.getResearchResourcesAdded(area, state);
		}
		else {
			throw new IllegalArgumentException("The compositum implementation can only handle WEAPON states");
		}
	}
	
	@Override
	public void addResearchResources(Resource resource, int amount, ResearchArea area) {
		if (area == ResearchArea.WEAPON) {
			globalManager.addResearchResources(resource, amount, area);
		}
		else {
			throw new IllegalArgumentException("The compositum implementation can only handle WEAPON states");
		}
	}
	
	@Override
	public void addResearchResources(ResearchResources resources, ResearchArea area) {
		if (area == ResearchArea.WEAPON) {
			globalManager.addResearchResources(resources, area);
		}
		else {
			throw new IllegalArgumentException("The compositum implementation can only handle WEAPON states");
		}
	}
	
	@VisibleForTesting
	protected Game getGame() {
		return game;
	}
}
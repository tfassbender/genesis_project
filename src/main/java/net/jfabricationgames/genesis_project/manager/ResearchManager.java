package net.jfabricationgames.genesis_project.manager;

import java.util.HashMap;
import java.util.Map;

import net.jfabricationgames.genesis_project.game.Constants;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.ResearchArea;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Resource;

public class ResearchManager implements IResearchManager {
	
	private Map<ResearchArea, Integer> researchStates;
	private Map<ResearchArea, Map<Integer, ResearchResources>> researchResourcesAdded;
	
	private int playersInGame;
	
	private static final double EPSILON = 1e-2;//for rounding values because of the double epsilon
	
	public ResearchManager(Player player, int playersInGame) {
		this.playersInGame = playersInGame;
		initResearchResourcesAdded();
		if (Constants.STARTING_RESEARCH_STATES != null) {
			researchStates = new HashMap<ResearchArea, Integer>(Constants.STARTING_RESEARCH_STATES.get(player.getPlayerClass()));
		}
		else {
			throw new IllegalStateException("The field STARTING_RESEARCH_STATES in the class Constants has not been initialized.");
		}
		if (Constants.RESEARCH_RESOURCES == null) {
			throw new IllegalStateException("The field RESEARCH_RESOURCES in the class Constants has not been inizialized.");
		}
	}
	
	/**
	 * Add an empty ResearchResource object to every research state for every research area.
	 */
	private void initResearchResourcesAdded() {
		researchResourcesAdded = new HashMap<ResearchArea, Map<Integer, ResearchResources>>((int) (1.3 * ResearchArea.values().length));
		for (ResearchArea area : ResearchArea.values()) {
			Map<Integer, ResearchResources> resouceMap = new HashMap<Integer, ResearchResources>();
			int researchStates = 7;
			if (area == ResearchArea.WEAPON) {
				researchStates = 11;
			}
			//add an empty ResearchResources object for every state
			for (int i = 0; i < researchStates; i++) {
				resouceMap.put(Integer.valueOf(i), new ResearchResources());
			}
			researchResourcesAdded.put(area, resouceMap);
		}
	}
	
	@Override
	public int getState(ResearchArea area) {
		return researchStates.get(area).intValue();
	}
	
	@Override
	public void increaseState(ResearchArea area) {
		int currentState = getState(area);
		if (currentState < Constants.MAX_RESEARCH_STATE_DEFAULT
				|| (area == ResearchArea.WEAPON && currentState < Constants.MAX_RESEARCH_STATE_WEAPON)) {
			researchStates.put(area, currentState + 1);
		}
		else {
			throw new IllegalStateException("The maximum research state (in research area: " + area + ") is already reached.");
		}
	}
	
	@Override
	public boolean isStateAccessible(ResearchArea area, int state) {
		return getResearchResourcesNeededLeft(area, state).isEmpty();
	}
	
	@Override
	public ResearchResources getResearchResourcesNeededTotal(ResearchArea area, int state) {
		Map<Resource, Double> resources = Constants.RESEARCH_RESOURCES.get(area).get(state);
		if (resources != null) {
			ResearchResources researchResources = new ResearchResources();
			for (Resource resource : ResearchResources.RESEARCH_RESOURCES) {
				researchResources.setResources(resource, (int) (resources.get(resource) * playersInGame + EPSILON));
			}
			return researchResources;
		}
		else {
			//no resources needed
			return new ResearchResources();
		}
	}
	
	@Override
	public ResearchResources getResearchResourcesNeededLeft(ResearchArea area, int state) {
		ResearchResources resourcesNeeded = getResearchResourcesNeededTotal(area, state);
		ResearchResources resourcesAdded = getResearchResourcesAdded(area, state);
		ResearchResources diff = new ResearchResources();
		for (Resource resource : ResearchResources.RESEARCH_RESOURCES) {
			diff.setResources(resource, resourcesNeeded.getResources(resource) - resourcesAdded.getResources(resource));
		}
		return diff;
	}
	
	@Override
	public ResearchResources getResearchResourcesAdded(ResearchArea area, int state) {
		return researchResourcesAdded.get(area).get(state).clone();
	}
	
	@Override
	public void addResearchResources(Resource resource, int amount, ResearchArea area, int state) {
		ResearchResources adding = new ResearchResources();
		adding.addResources(resource, amount);
		addResearchResources(adding, area, state);
	}
	
	@Override
	public void addResearchResources(ResearchResources resources, ResearchArea area, int state) {
		ResearchResources added = researchResourcesAdded.get(area).get(state);//don't call getter here because it returns a clone
		ResearchResources neededLeft = getResearchResourcesNeededLeft(area, state);
		
		//check whether the resources that shall be added are more than needed
		boolean moreThanNeeded = false;
		for (Resource res : ResearchResources.RESEARCH_RESOURCES) {
			moreThanNeeded |= neededLeft.getResources(res) - resources.getResources(res) < 0;
		}
		//check whether any resource is negative
		boolean negativeResources = false;
		for (Resource res : ResearchResources.RESEARCH_RESOURCES) {
			negativeResources |= resources.getResources(res) < 0;
		}
		
		if (moreThanNeeded) {
			throw new IllegalArgumentException("Trying to add more resources for research than needed in the chosen state (Needed: " + neededLeft
					+ "; Trying to add: " + resources + ")");
		}
		else if (negativeResources) {
			throw new IllegalArgumentException("Trying to add resources but (at least one of) the resources is negative: " + resources);
		}
		else if (resources.isEmpty()) {
			throw new IllegalArgumentException("Trying to add resources but the resources are empty: " + resources);
		}
		else {
			//add the resources
			added.addResources(resources);
		}
	}
}
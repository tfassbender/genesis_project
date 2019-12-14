package net.jfabricationgames.genesis_project.manager;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.jfabricationgames.genesis_project.game.Constants;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.ResearchArea;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Resource;
import net.jfabricationgames.genesis_project.json.CustomIntegerPropertySerializer;
import net.jfabricationgames.genesis_project.json.CustomObjectPropertyResearchResourcesSerializer;

public class ResearchManager implements IResearchManager {
	
	@JsonSerialize(contentUsing = CustomIntegerPropertySerializer.class)
	private Map<ResearchArea, IntegerProperty> researchStates;
	private Map<ResearchArea, Map<Integer, ResearchResources>> researchResourcesAdded;
	@JsonSerialize(contentUsing = CustomIntegerPropertySerializer.class)
	private Map<ResearchArea, IntegerProperty> maxReachableState;
	@JsonSerialize(contentUsing = CustomObjectPropertyResearchResourcesSerializer.class)
	private Map<ResearchArea, ObjectProperty<ResearchResources>> researchResourcesNeededLeftProperties;
	
	private int playersInGame;
	
	private Player player;
	
	private static final double EPSILON = 1e-2;//for rounding values because of the double epsilon
	
	public ResearchManager(Player player) {
		this(player, -1);
	}
	public ResearchManager(Player player, int playersInGame) {
		this.player = player;
		this.playersInGame = playersInGame;
		initResearchResourcesAdded();
		if (Constants.STARTING_RESEARCH_STATES != null) {
			if (player != null) {
				researchStates = new HashMap<ResearchArea, IntegerProperty>();
				for (Map.Entry<ResearchArea, Integer> startStates : Constants.STARTING_RESEARCH_STATES.get(player.getPlayerClass()).entrySet()) {
					IntegerProperty property = new SimpleIntegerProperty(this, "researchState_" + startStates.getKey().name());
					property.set(startStates.getValue().intValue());
					researchStates.put(startStates.getKey(), property);
				}
			}
			else {
				//when player is null the research manager is for a composite implementation -> states are not needed
				researchStates = new HashMap<ResearchArea, IntegerProperty>();
				researchStates.put(ResearchArea.WEAPON, new SimpleIntegerProperty(this, "researchState_WEAPON"));
			}
		}
		else {
			throw new IllegalStateException("The field STARTING_RESEARCH_STATES in the class Constants has not been initialized.");
		}
		if (Constants.RESEARCH_RESOURCES == null) {
			throw new IllegalStateException("The field RESEARCH_RESOURCES in the class Constants has not been inizialized.");
		}
		
		//initialize maxReachableState properties and researchResourcesNeededLeftProperties when the number of players in the game is known (till then just use default values)
		maxReachableState = new HashMap<ResearchArea, IntegerProperty>();
		for (ResearchArea area : ResearchArea.values()) {
			IntegerProperty property = new SimpleIntegerProperty(this, "maxResearchStateAccessible_" + area.name());
			property.set(Constants.MAX_RESEARCH_STATE_DEFAULT);
			maxReachableState.put(area, property);
		}
		
		researchResourcesNeededLeftProperties = new HashMap<ResearchArea, ObjectProperty<ResearchResources>>();
		for (ResearchArea area : ResearchArea.values()) {
			ObjectProperty<ResearchResources> property = new SimpleObjectProperty<>(this, "researchResourcesNeededLeft_" + area.name());
			property.set(new ResearchResources());
			researchResourcesNeededLeftProperties.put(area, property);
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
	
	private void updateMaxReachableState() {
		for (ResearchArea area : ResearchArea.values()) {
			IntegerProperty property = maxReachableState.get(area);
			int nextResourceNeedingState = getNextResourceNeedingState(area);
			if (nextResourceNeedingState == -1) {
				if (area == ResearchArea.WEAPON) {
					property.set(Constants.MAX_RESEARCH_STATE_WEAPON);
				}
				else {
					property.set(Constants.MAX_RESEARCH_STATE_DEFAULT);
				}
			}
			else {
				property.set(nextResourceNeedingState - 1);
			}
		}
	}
	private void updateResearchResourcesNeededLeftProperties() {
		for (ResearchArea area : ResearchArea.values()) {
			ObjectProperty<ResearchResources> property = researchResourcesNeededLeftProperties.get(area);
			int nextResourceNeedingState = getNextResourceNeedingState(area);
			if (nextResourceNeedingState == -1) {
				property.set(new ResearchResources());
			}
			else {
				ResearchResources neededLeft = getResearchResourcesNeededLeft(area, nextResourceNeedingState);
				property.set(neededLeft);
			}
		}
	}
	
	@Override
	public int getState(ResearchArea area) {
		return researchStates.get(area).get();
	}
	
	@Override
	public void increaseState(ResearchArea area) {
		int currentState = getState(area);
		if (currentState < Constants.MAX_RESEARCH_STATE_DEFAULT
				|| (area == ResearchArea.WEAPON && currentState < Constants.MAX_RESEARCH_STATE_WEAPON)) {
			
			IntegerProperty researchState = researchStates.get(area);
			researchState.set(currentState + 1);
		}
		else {
			throw new IllegalStateException("The maximum research state (in research area: " + area + ") is already reached.");
		}
	}
	
	@Override
	public boolean isStateAccessible(ResearchArea area, int state) {
		int maximumState = Constants.MAX_RESEARCH_STATE_DEFAULT;
		if (area == ResearchArea.WEAPON) {
			maximumState = Constants.MAX_RESEARCH_STATE_WEAPON;
		}
		
		if (state < 0 || state > maximumState) {
			throw new IllegalArgumentException(
					"The requested state is out of the valid range (range: [" + 0 + " - " + maximumState + "], state: " + state + ")");
		}
		
		boolean allStatesAccessible = true;
		for (int i = 0; i <= state; i++) {
			allStatesAccessible &= getResearchResourcesNeededLeft(area, i).isEmpty();
		}
		return allStatesAccessible;
	}
	
	@Override
	public int getNextResourceNeedingState(ResearchArea area) {
		int nextResourceNeedingState = -1;
		int maxResearchState = Constants.MAX_RESEARCH_STATE_DEFAULT;
		
		if (area == ResearchArea.WEAPON) {
			maxResearchState = Constants.MAX_RESEARCH_STATE_WEAPON;
		}
		
		for (int i = 1; i < maxResearchState + 1; i++) {
			if (nextResourceNeedingState == -1 && !isStateAccessible(area, i)) {
				nextResourceNeedingState = i;
			}
		}
		
		return nextResourceNeedingState;
	}
	
	@Override
	public ResearchResources getResearchResourcesNeededTotal(ResearchArea area, int state) {
		Map<Resource, Double> resources = Constants.RESEARCH_RESOURCES.get(area).get(state);
		if (resources != null) {
			ResearchResources researchResources = new ResearchResources();
			for (Resource resource : ResearchResources.RESEARCH_RESOURCES) {
				researchResources.setResources(resource, (int) (resources.get(resource) * getNumPlayersInGame() + EPSILON));
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
	public void addResearchResources(Resource resource, int amount, ResearchArea area) {
		ResearchResources adding = new ResearchResources();
		adding.addResources(resource, amount);
		addResearchResources(adding, area);
		
		//update the properties
		updateMaxReachableState();
		updateResearchResourcesNeededLeftProperties();
	}
	
	@Override
	public void addResearchResources(ResearchResources resources, ResearchArea area) {
		int nextResourcesNeedingState = getNextResourceNeedingState(area);
		if (nextResourcesNeedingState == -1) {
			throw new IllegalArgumentException("Trying to add resources to a research area that is already completely accessible.");
		}
		
		ResearchResources added = researchResourcesAdded.get(area).get(nextResourcesNeedingState);//don't call getter here because it returns a clone
		ResearchResources neededLeft = getResearchResourcesNeededLeft(area, nextResourcesNeedingState);
		
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
		
		//update the properties
		updateMaxReachableState();
		updateResearchResourcesNeededLeftProperties();
	}
	
	public int getNumPlayersInGame() {
		if (playersInGame == -1) {
			Game game = player.getGame();
			if (game != null) {
				playersInGame = game.getPlayers().size();
				updateMaxReachableState();
				updateResearchResourcesNeededLeftProperties();
			}
			else {
				throw new IllegalStateException("The field 'playersInGame' has not yet been set.");
			}
		}
		return playersInGame;
	}
	
	@Override
	public int getDroneAdditionalDefense() {
		int additionalDefense = 0;
		
		int militaryState = getState(ResearchArea.MILITARY);
		if (militaryState >= 3) {
			additionalDefense = 1;
		}
		
		return additionalDefense;
	}
	@Override
	public int getSpaceStationAdditionalDefense() {
		int additionalDefense = 0;
		
		int militaryState = getState(ResearchArea.MILITARY);
		if (militaryState >= 5) {
			additionalDefense = 2;
		}
		else if (militaryState >= 4) {
			additionalDefense = 1;
		}
		return additionalDefense;
	}
	@Override
	public int getDroneAdditionalRange() {
		int additionalRange = 0;
		
		int militaryState = getState(ResearchArea.MILITARY);
		if (militaryState >= 4) {
			additionalRange += 2;
		}
		else if (militaryState >= 1) {
			additionalRange += 1;
		}
		
		int ftlState = getState(ResearchArea.FTL);
		if (ftlState >= 5) {
			additionalRange += 2;
		}
		else if (ftlState >= 3) {
			additionalRange += 1;
		}
		
		return additionalRange;
	}
	@Override
	public int getSpaceStationAdditionalRange() {
		int additionalRange = 0;
		
		int militaryState = getState(ResearchArea.MILITARY);
		if (militaryState >= 5) {
			additionalRange += 2;
		}
		else if (militaryState >= 4) {
			additionalRange += 1;
		}
		
		int ftlState = getState(ResearchArea.FTL);
		if (ftlState >= 5) {
			additionalRange += 2;
		}
		else if (ftlState >= 3) {
			additionalRange += 1;
		}
		
		return additionalRange;
	}
	
	/**
	 * Get the additional defense by the WEAPON research area (for all fields)
	 */
	@Override
	public int getAdditionalWeaponDefense() {
		int additionalDefense = 0;
		
		int weaponState = getState(ResearchArea.WEAPON);
		if (weaponState >= 10) {
			additionalDefense = 999;
		}
		else if (weaponState >= 8) {
			additionalDefense = 15;
		}
		else if (weaponState >= 6) {
			additionalDefense = 10;
		}
		else if (weaponState >= 4) {
			additionalDefense = 5;
		}
		
		return additionalDefense;
	}
	
	@Override
	public IntegerProperty getStateProperty(ResearchArea area) {
		return researchStates.get(area);
	}
	@Override
	public IntegerProperty getMaxReachableStateProperty(ResearchArea area) {
		return maxReachableState.get(area);
	}
	@Override
	public ObjectProperty<ResearchResources> getResearchResourcesNeededLeftProperties(ResearchArea area) {
		return researchResourcesNeededLeftProperties.get(area);
	}
}
package net.jfabricationgames.genesis_project.manager;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import net.jfabricationgames.genesis_project.game.ResearchArea;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Resource;

public interface IResearchManager {
	
	public int getState(ResearchArea area);
	/**
	 * Increases the state of an area if possible.<br>
	 * The next state has to be accessible to increase to the state.
	 * 
	 * The resources for the state are NOT taken here because that causes problems when increasing the weapon state (not everyone pays for this
	 * increase, but everyone gets the increase).
	 */
	public void increaseState(ResearchArea area);
	
	/**
	 * Checks whether a state of an research area is accessible.<br>
	 * A state is accessible when there are no resources needed for this state and all states below this state are accessible too.
	 */
	public boolean isStateAccessible(ResearchArea area, int state);
	/**
	 * Find the next state of an area that needs resources to be made accessible.
	 * 
	 * If there is no such state (all states are already accessible) -1 is returned.
	 */
	public int getNextResourceNeedingState(ResearchArea area);
	
	public ResearchResources getResearchResourcesNeededTotal(ResearchArea area, int state);
	public ResearchResources getResearchResourcesNeededLeft(ResearchArea area, int state);
	public ResearchResources getResearchResourcesAdded(ResearchArea area, int state);
	
	public int getDroneAdditionalDefense();
	public int getSpaceStationAdditionalDefense();
	public int getDroneAdditionalRange();
	public int getSpaceStationAdditionalRange();
	/**
	 * Get the additional defense by the WEAPON research area (for all fields).
	 */
	public int getAdditionalWeaponDefense();
	
	/**
	 * Add resources to the next state of the given area that needs resources to be made accessible.
	 * 
	 * Only resources of the given type are added.
	 */
	public void addResearchResources(Resource resource, int amount, ResearchArea area);
	/**
	 * Add resources to the next state of the given area that needs resources to be made accessible.
	 */
	public void addResearchResources(ResearchResources resources, ResearchArea area);
	
	public IntegerProperty getStateProperty(ResearchArea area);
	public IntegerProperty getMaxReachableStateProperty(ResearchArea area);
	public ObjectProperty<ResearchResources> getResearchResourcesNeededLeftProperties(ResearchArea area);
}
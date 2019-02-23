package net.jfabricationgames.genesis_project.manager;

import java.util.HashMap;
import java.util.Map;

import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.Technology;

public class TechnologyManager implements ITechnologyManager {
	
	private Map<Technology, Boolean> exploredTechnologies;
	
	//private Player player;
	
	public TechnologyManager(Player player) {
		//this.player = player;
		initExploredTechnologies();
	}
	
	private void initExploredTechnologies() {
		exploredTechnologies = new HashMap<Technology, Boolean>();
		for (Technology tech : Technology.values()) {
			exploredTechnologies.put(tech, Boolean.valueOf(false));
		}
	}
	
	@Override
	public boolean isTechnologyExplored(Technology technology) {
		return exploredTechnologies.get(technology).booleanValue();
	}
	
	@Override
	public void exploreTechnology(Technology technology) {
		exploredTechnologies.put(technology, Boolean.valueOf(true));
	}
	
	@Override
	public int getDefenseBuildingAdditionalRange() {
		int additionalRange = 0;
		if (exploredTechnologies.get(Technology.MILITARY_FTL)) {
			additionalRange = 1;
		}
		return additionalRange;
	}
}
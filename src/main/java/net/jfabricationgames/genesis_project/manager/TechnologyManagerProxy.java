package net.jfabricationgames.genesis_project.manager;

import net.jfabricationgames.genesis_project.game.Technology;

public class TechnologyManagerProxy implements ITechnologyManager {
	
	private ITechnologyManager manager;
	
	public TechnologyManagerProxy(ITechnologyManager manager) {
		this.manager = manager;
	}
	
	@Override
	public boolean isTechnologyExplored(Technology technology) {
		return manager.isTechnologyExplored(technology);
	}
	
	@Override
	public void exploreTechnology(Technology technology) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");		
	}
	
	@Override
	public int getDefenseBuildingAdditionalRange() {
		return manager.getDefenseBuildingAdditionalRange();
	}
}
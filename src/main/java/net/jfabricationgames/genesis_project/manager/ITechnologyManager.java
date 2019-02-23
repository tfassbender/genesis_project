package net.jfabricationgames.genesis_project.manager;

import net.jfabricationgames.genesis_project.game.Technology;

public interface ITechnologyManager {
	
	public boolean isTechnologyExplored(Technology technology);
	public void exploreTechnology(Technology technology);
	
	public int getDefenseBuildingAdditionalRange();
}
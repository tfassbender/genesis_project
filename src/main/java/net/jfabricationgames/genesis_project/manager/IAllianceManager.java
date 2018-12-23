package net.jfabricationgames.genesis_project.manager;

import java.util.List;

import net.jfabricationgames.genesis_project.game.Alliance;
import net.jfabricationgames.genesis_project.game.AllianceBonus;

public interface IAllianceManager {
	
	public List<Alliance> getAlliances();
	public List<AllianceBonus> getAllianceBonuses();
	public void addAlliance(Alliance alliance);
}
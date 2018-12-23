package net.jfabricationgames.genesis_project.manager;

import java.util.List;
import java.util.stream.Collectors;

import net.jfabricationgames.genesis_project.game.Alliance;
import net.jfabricationgames.genesis_project.game.AllianceBonus;

public class AllianceManager implements IAllianceManager {
	
	private List<Alliance> alliances;
	
	@Override
	public List<Alliance> getAlliances() {
		return alliances;
	}

	@Override
	public List<AllianceBonus> getAllianceBonuses() {
		return alliances.stream().map(a -> a.getBonus()).collect(Collectors.toList());
	}

	@Override
	public void addAlliance(Alliance alliance) {
		alliances.add(alliance);
	}
}
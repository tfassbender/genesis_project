package net.jfabricationgames.genesis_project.game;

import java.util.List;

public class Alliance {
	
	private List<Field> planets;
	private List<Field> connectingSatellites;
	private AllianceBonus bonus;
	
	/**
	 * DO NOT USE - empty constructor for json deserialization
	 */
	@Deprecated
	public Alliance() {
		
	}
	
	public Alliance(List<Field> planets, List<Field> connectingSatellites, AllianceBonus bonus) {
		this.planets = planets;
		this.connectingSatellites = connectingSatellites;
		this.bonus = bonus;
	}
	
	public List<Field> getPlanets() {
		return planets;
	}
	public List<Field> getConnectingSatellites() {
		return connectingSatellites;
	}
	public AllianceBonus getBonus() {
		return bonus;
	}
}
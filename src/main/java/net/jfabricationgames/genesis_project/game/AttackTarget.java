package net.jfabricationgames.genesis_project.game;

public enum AttackTarget {
	
	RESOURCES("Resourcen"),//take resources from the loosing players
	RESEARCH("Forschungspunkte"),//take research points from the losing players
	POINTS("Punkte"),//take points from the loosing players
	WEAPON("Die Waffe");//reduce the WEAPON research state if the players don't defend enough
	
	private final String name;
	
	private AttackTarget(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
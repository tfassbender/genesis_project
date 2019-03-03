package net.jfabricationgames.genesis_project.game;

public enum Enemy {
	
	PARASITE("Parasit"),//
	PIRATES("Piraten");
	
	private final String name;
	
	private Enemy(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
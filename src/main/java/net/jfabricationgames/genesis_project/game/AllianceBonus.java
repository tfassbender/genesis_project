package net.jfabricationgames.genesis_project.game;

public enum AllianceBonus {
	
	MILITARY_RANGE(4),//
	PRIMARY_RESOURCES(5),//
	SECUNDARY_RESOURCES(6),//
	RESEARCH_POINTS(7),//
	SCIENTISTS(6),//
	POINTS(10),//
	ANY(5);//A bonus that is not limited to two in a game (can be taken when all others are taken)
	
	private final int points;
	
	private AllianceBonus(int points) {
		this.points = points;
	}
	
	public int getPoints() {
		return points;
	}
}
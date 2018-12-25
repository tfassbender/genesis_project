package net.jfabricationgames.genesis_project.game;

import net.jfabricationgames.genesis_project.move.IMove;

public enum TurnGoal {
	
	ALLIANCE(5),//create an alliance
	COLONY(2),//build any colony
	GENESIS_PLANET(2),//build a colony on a genesis planet
	GOVERNMENT_CITY(5),//build a government or city
	LABORATORY_RESEARCH_CENTER(3),//build a laboratory or research center
	MINE_TRADING_POST(3),//build a mine or trading post
	NEW_PLANETS(2),//build a colony on a new planet
	NEIGHBORS(3);//build a colony on a new planet that has neighbors
	
	private final int points;
	
	private TurnGoal(int points) {
		this.points = points;
	}
	
	/**
	 * Calculate the points for a move in the turn this goal is active (without checking whether the move is valid).
	 */
	public int getPointsForMove(IMove move) {
		int points = 0;
		
		//TODO move implementations needed before implementing the method
		switch (this) {
			case ALLIANCE:
				break;
			case COLONY:
				break;
			case GENESIS_PLANET:
				break;
			case GOVERNMENT_CITY:
				break;
			case LABORATORY_RESEARCH_CENTER:
				break;
			case MINE_TRADING_POST:
				break;
			case NEIGHBORS:
				break;
			case NEW_PLANETS:
				break;
			default:
				throw new IllegalStateException("Unknown turn goal: " + this);
		}
		
		return points;
	}
	
	public int getPoints() {
		return points;
	}
}
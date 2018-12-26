package net.jfabricationgames.genesis_project.game;

import net.jfabricationgames.genesis_project.move.IMove;
import net.jfabricationgames.genesis_project.move.MoveType;

public enum TurnGoal {
	
	ALLIANCE(5),//create an alliance
	COLONY(2),//build any colony
	GENESIS_PLANET(2),//build anything on a genesis planet
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
		Player player = move.getPlayer();
		MoveType type = move.getType();
		Building building = move.getBuilding();
		Field field = move.getField();
		PlayerBuilding[] fieldBuildings;
		
		boolean pointMove = false;
		switch (this) {
			case ALLIANCE:
				pointMove = type == MoveType.ALLIANCE;
				break;
			case COLONY:
				pointMove = type == MoveType.BUILD && building == Building.COLONY;
				break;
			case GENESIS_PLANET:
				pointMove = type == MoveType.BUILD && (field.getPlanet() == Planet.GENESIS || field.getPlanet() == Planet.CENTER);
				break;
			case GOVERNMENT_CITY:
				pointMove = type == MoveType.BUILD && (building == Building.GOVERNMENT || building == Building.CITY);
				break;
			case LABORATORY_RESEARCH_CENTER:
				pointMove = type == MoveType.BUILD && (building == Building.LABORATORY || building == Building.RESEARCH_CENTER);
				break;
			case MINE_TRADING_POST:
				pointMove = type == MoveType.BUILD && (building == Building.MINE || building == Building.TRADING_POST);
				break;
			case NEIGHBORS:
				fieldBuildings = field.getBuildings();
				boolean neighbor = false;
				for (PlayerBuilding playerBuilding : fieldBuildings) {
					neighbor |= (playerBuilding != null && !playerBuilding.getPlayer().equals(player));
				}
				pointMove = type == MoveType.BUILD && building == Building.COLONY && neighbor;
				break;
			case NEW_PLANETS:
				fieldBuildings = field.getBuildings();
				boolean newPlanet = true;
				for (PlayerBuilding playerBuilding : fieldBuildings) {
					newPlanet &= (playerBuilding == null || !playerBuilding.getPlayer().equals(player));
				}
				pointMove = type == MoveType.BUILD && building == Building.COLONY && newPlanet;
				break;
			default:
				throw new IllegalStateException("Unknown turn goal: " + this);
		}
		
		if (pointMove) {
			return points;
		}
		else {
			return 0;
		}
	}
	
	public int getPoints() {
		return points;
	}
}
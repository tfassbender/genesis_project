package net.jfabricationgames.genesis_project.game;

import net.jfabricationgames.genesis_project.move.IMove;
import net.jfabricationgames.genesis_project.move.MoveType;

public enum TurnGoal {
	
	ALLIANCE(5, "cards/turn_goals/turn_goal_alliances.png", "Allianzen"),//create an alliance
	COLONY(2, "cards/turn_goals/turn_goal_colonie.png", "Kolonien"),//build any colony
	GENESIS_PLANET(2, "cards/turn_goals/turn_goal_genesis_planets.png", "Genesis Planeten"),//build anything on a genesis planet
	GOVERNMENT_CITY(5, "cards/turn_goals/turn_goal_goverments_cities.png", "Regierungssitze und Städte"),//build a government or city
	LABORATORY_RESEARCH_CENTER(3, "cards/turn_goals/turn_goal_laboratories_research_centers.png", "Labore und Forschungsanlagen"),//build a laboratory or research center
	MINE_TRADING_POST(3, "cards/turn_goals/turn_goal_mines_traiding_posts.png", "Minen und Handelsposten"),//build a mine or trading post
	NEW_PLANETS(2, "cards/turn_goals/turn_goal_new_planets.png", "Neue Planeten"),//build a colony on a new planet
	NEIGHBORS(3, "cards/turn_goals/turn_goal_new_planets_neighbour.png", "Nachbarn");//build a colony on a new planet that has neighbors
	
	private final int points;
	
	private final String imagePath;
	private final String name;
	
	private TurnGoal(int points, String imagePath, String name) {
		this.points = points;
		this.imagePath = imagePath;
		this.name = name;
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
	
	public String getImagePath() {
		return imagePath;
	}
	
	public String getName() {
		return name;
	}
	
}
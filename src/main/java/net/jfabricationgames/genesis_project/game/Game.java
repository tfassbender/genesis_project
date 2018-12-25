package net.jfabricationgames.genesis_project.game;

import java.util.List;
import java.util.Objects;

import net.jfabricationgames.genesis_project.manager.IAllianceManager;
import net.jfabricationgames.genesis_project.manager.IBuildingManager;
import net.jfabricationgames.genesis_project.manager.IResearchManager;
import net.jfabricationgames.genesis_project.manager.ITurnManager;
import net.jfabricationgames.genesis_project.move.IMove;

public class Game {
	
	private List<Player> players;
	private Board board;
	
	private ITurnManager turnManager;
	private IResearchManager researchManager;
	
	public void collectGameStartResources() {
		for (Player player : players) {
			player.getResourceManager().collectGameStartResources();
		}
	}
	public void collectTurnStartResources() {
		for (Player player : players) {
			player.getResourceManager().collectTurnStartResources();
		}
	}
	
	/**
	 * Executes a move without checking for it's validity. When the move can't be executed a RuntimeException will occur.
	 */
	public void executeMove(IMove move) {
		Objects.requireNonNull(move, "Can't execute a move that is null");
		
		ResearchArea area;
		
		switch (move.getType()) {
			case BUILD:
				Building building = move.getBuilding();
				Field field = move.getField();
				IBuildingManager buildingManager = move.getPlayer().getBuildingManager();
				buildingManager.build(building, field);
				break;
			case ALLIANCE:
				List<Field> planets = move.getAlliancePlanets();
				List<Field> satellites = move.getSatelliteFields();
				AllianceBonus bonus = move.getAllianceBonus();
				
				IAllianceManager allianceManager = move.getPlayer().getAllianceManager();
				allianceManager.addAlliance(planets, satellites, bonus);
				break;
			case RESEARCH:
				area = move.getResearchArea();
				if (area == ResearchArea.WEAPON) {
					//WEAPON upgrades are executed on the global IResearchManager (composite)
					researchManager.increaseState(area);
				}
				else {
					//other ResearchAreas are executed locally on the player's IResearchManager
					move.getPlayer().getResearchManager().increaseState(area);
				}
				break;
			case RESEARCH_RESOURCES:
				ResearchResources resources = move.getResearchResources();
				area = move.getResearchArea();
				
				researchManager.addResearchResources(resources, area);
				break;
			case PASS:
				Player player = move.getPlayer();
				turnManager.playerPassed(player);
				break;
			default:
				throw new IllegalArgumentException("The MoveType " + move.getType() + " is unknown.");
		}
		
		//receive turn points for a move
		turnManager.receivePointsForMove(move);
	}
	/**
	 * Check whether a given move would be valid and could be executed.
	 */
	public boolean isMoveExecutable(IMove move) {
		Objects.requireNonNull(move, "Can't test a move that is null");
		
		boolean moveExecutable = true;
		
		//it has to be the players turn for every move
		moveExecutable &= turnManager.getPlayerOrder().isPlayersTurn(move.getPlayer());
		
		ResearchArea area;
		Player player;
		
		switch (move.getType()) {
			case BUILD:
				//the building has to be valid and the resources for the building have to be there
				Field field = move.getField();
				Building building = move.getBuilding();
				
				//the IBuildingManager checks for the resources available
				moveExecutable &= move.getPlayer().getBuildingManager().canBuild(building, field);
				break;
			case ALLIANCE:
				//enough buildings, government or city included, planets, opponent's buildings, none already in an alliance of this player
				player = move.getPlayer();
				IAllianceManager allianceManager = player.getAllianceManager();
				List<Field> planets = move.getAlliancePlanets();
				List<Field> satellites = move.getSatelliteFields();
				AllianceBonus bonus = move.getAllianceBonus();
				
				moveExecutable &= allianceManager.isAllianceValid(planets, satellites, bonus);
				break;
			case RESEARCH:
				//the research step has to be accessible and the player has to have the needed research points
				area = move.getResearchArea();
				int currentState = move.getPlayer().getResearchManager().getState(area);
				int nextState = currentState + 1;
				
				moveExecutable &= move.getPlayer().getResearchManager().isStateAccessible(area, nextState);
				break;
			case RESEARCH_RESOURCES:
				//the player has to have the resources and the resources have to be needed
				ResearchResources resourcesAdded = move.getResearchResources();
				
				player = move.getPlayer();
				moveExecutable &= player.getResourceManager().isResourcesAvailable(resourcesAdded);
				
				area = move.getResearchArea();
				int nextResoucesNeedingState = researchManager.getNextResourceNeedingState(area);
				
				if (nextResoucesNeedingState != -1) {
					ResearchResources neededLeft = researchManager.getResearchResourcesNeededLeft(area, nextResoucesNeedingState);
					
					for (Resource resource : ResearchResources.RESEARCH_RESOURCES) {
						moveExecutable &= neededLeft.getResources(resource) >= resourcesAdded.getResources(resource);
					}
				}
				break;
			case PASS:
				//as long as it's the players turn it's always possible to pass
				break;
			default:
				throw new IllegalArgumentException("The MoveType " + move.getType() + " is unknown.");
		}
		
		return moveExecutable;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public ITurnManager getTurnManager() {
		return turnManager;
	}
	public IResearchManager getResearchManager() {
		return researchManager;
	}
}
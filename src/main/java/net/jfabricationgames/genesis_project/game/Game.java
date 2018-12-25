package net.jfabricationgames.genesis_project.game;

import java.util.List;
import java.util.Objects;

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
		
		switch (move.getType()) {
			case BUILD:
				Building building = move.getBuilding();
				Field field = move.getField();
				move.getPlayer().getBuildingManager().build(building, field);
				break;
			case ALLIANCE:
				//TODO see isMoveExecutable
				break;
			case RESEARCH:
				ResearchArea area = move.getResearchArea();
				if (area == ResearchArea.WEAPON) {
					//WEAPON upgrades are executed on the global IResearchManager (compositum)
					researchManager.increaseState(area);
				}
				else {
					//other ResearchAreas are executed locally on the player's IResearchManager
					move.getPlayer().getResearchManager().increaseState(area);
				}
				break;
			case RESEARCH_RESOURCES:
				//TODO see isMoveExecutable
				break;
			case PASS:
				//TODO new method in the ITurnManager needs to be added to let a player pass (not the one in the PlayerOrder class) 
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
		
		switch (move.getType()) {
			case BUILD:
				//the building has to be valid and the resources for the building have to be there
				Field field = move.getField();
				Building building = move.getBuilding();
				//TODO canBuild(Building, Field) in IBuildingManager has to check the resources
				moveExecutable &= move.getPlayer().getBuildingManager().canBuild(building, field);
				break;
			case ALLIANCE:
				//enough buildings, government or city included, planets, opponent's buildings, none already in an alliance of this player
				//TODO IAllianceManager has to be refactored:
				//- new method needs to be added to check whether an alliance would be valid
				//- addAlliance method needs to be overloaded to create an alliance out of planets and satellite fields
				break;
			case RESEARCH:
				//the research step has to be accessible and the player has to have the needed research points
				ResearchArea area = move.getResearchArea();
				int currentState = move.getPlayer().getResearchManager().getState(area);
				int nextState = currentState + 1;
				
				moveExecutable &= move.getPlayer().getResearchManager().isStateAccessible(area, nextState);
				break;
			case RESEARCH_RESOURCES:
				//the player has to have the resources and the resources have to be needed
				//TODO IResearchManager has to be refactored: 
				//- resources always added to lowest state
				//- increase has to check whether the state is accessible
				//- is accessible has to check for all lower states
				//- new method needed to get the maximum reachable state of an area
				//- is state accessible has to check for maximum states
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
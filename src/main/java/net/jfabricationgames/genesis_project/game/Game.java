package net.jfabricationgames.genesis_project.game;

import java.util.List;

import net.jfabricationgames.genesis_project.manager.IResearchManager;
import net.jfabricationgames.genesis_project.manager.ITurnManager;
import net.jfabricationgames.genesis_project.move.IMove;

public class Game {
	
	private List<Player> players;
	private Board board;
	
	private ITurnManager turnManager;
	private IResearchManager researchManager;
	
	public void executeMove(IMove move) {
		//TODO implement method
	}
	public boolean isMoveResourcesAvailable(IMove move) {
		//TODO implement method
		return false;
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
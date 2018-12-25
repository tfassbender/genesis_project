package net.jfabricationgames.genesis_project.game;

import java.util.List;

import net.jfabricationgames.genesis_project.manager.IResearchManager;
import net.jfabricationgames.genesis_project.manager.ITurnManager;

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
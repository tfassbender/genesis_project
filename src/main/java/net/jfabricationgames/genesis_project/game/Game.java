package net.jfabricationgames.genesis_project.game;

import java.util.List;

import net.jfabricationgames.genesis_project.manager.IBuildingManager;
import net.jfabricationgames.genesis_project.manager.IPointManager;
import net.jfabricationgames.genesis_project.manager.IResourceManager;
import net.jfabricationgames.genesis_project.manager.ITurnManager;
import net.jfabricationgames.genesis_project.manager.TurnManager;

public class Game {
	
	private List<Player> players;
	private Board board;
	
	private IPointManager pointManager;
	private IResourceManager resourceManager;
	private IBuildingManager buildingManager;
	private TurnManager turnManager;
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public IPointManager getPointManager() {
		return pointManager;
	}
	public IResourceManager getResourceManager() {
		return resourceManager;
	}
	public IBuildingManager getBuildingManager() {
		return buildingManager;
	}
	public ITurnManager getTurnManager() {
		return turnManager;
	}
}
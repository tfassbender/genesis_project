package net.jfabricationgames.genesis_project.game;

import net.jfabricationgames.genesis_project.manager.BuildingManager;
import net.jfabricationgames.genesis_project.manager.IBuildingManager;
import net.jfabricationgames.genesis_project.manager.IPointManager;
import net.jfabricationgames.genesis_project.manager.IResearchManager;
import net.jfabricationgames.genesis_project.manager.IResourceManager;
import net.jfabricationgames.genesis_project.manager.ITechnologyManager;
import net.jfabricationgames.genesis_project.manager.PointManager;
import net.jfabricationgames.genesis_project.manager.ResourceManager;
import net.jfabricationgames.genesis_project.user.User;

public class Player {
	
	private User user;
	
	private IPointManager pointManager;
	private IResourceManager resourceManager;
	private IBuildingManager buildingManager;
	private IResearchManager researchManager;
	private ITechnologyManager technologyManager;
	
	private PlayerClass playerClass;
	
	private Game game;
	
	public Player(User user) {
		this(user, null);
	}
	public Player(User user, Game game) {
		this.game = game;
		this.user = user;
		pointManager = new PointManager(this);
		resourceManager = new ResourceManager(this);
		buildingManager = new BuildingManager(this);
	}
	
	@Override
	public String toString() {
		return user.toString();
	}
	
	public User getUser() {
		return user;
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
	public IResearchManager getResearchManager() {
		return researchManager;
	}
	public ITechnologyManager getTechnologyManager() {
		return technologyManager;
	}
	
	public PlayerClass getPlayerClass() {
		return playerClass;
	}
	
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
}
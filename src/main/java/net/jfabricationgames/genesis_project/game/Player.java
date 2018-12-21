package net.jfabricationgames.genesis_project.game;

import net.jfabricationgames.genesis_project.manager.BuildingManager;
import net.jfabricationgames.genesis_project.manager.IBuildingManager;
import net.jfabricationgames.genesis_project.manager.IPointManager;
import net.jfabricationgames.genesis_project.manager.IResourceManager;
import net.jfabricationgames.genesis_project.manager.PointManager;
import net.jfabricationgames.genesis_project.manager.ResourceManager;
import net.jfabricationgames.genesis_project.user.User;

public class Player {
	
	private User user;
	
	private IPointManager pointManager;
	private IResourceManager resourceManager;
	private IBuildingManager buildingManager;
	
	private PlayerClass playerClass;
	
	public Player(User user) {
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
	
	public PlayerClass getPlayerClass() {
		return playerClass;
	}
}
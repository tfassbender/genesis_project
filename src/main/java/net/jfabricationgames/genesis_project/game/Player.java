package net.jfabricationgames.genesis_project.game;

import net.jfabricationgames.genesis_project.manager.IBuildingManager;
import net.jfabricationgames.genesis_project.manager.IPointManager;
import net.jfabricationgames.genesis_project.manager.IResourceManager;
import net.jfabricationgames.genesis_project.user.User;

public class Player {
	
	private User user;
	
	private IPointManager pointManager;
	private IResourceManager resourceManager;
	private IBuildingManager buildingManager;
	
	public Player(User user) {
		this.user = user;
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
}
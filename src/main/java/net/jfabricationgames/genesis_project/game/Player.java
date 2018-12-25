package net.jfabricationgames.genesis_project.game;

import java.util.Objects;

import net.jfabricationgames.genesis_project.manager.BuildingManager;
import net.jfabricationgames.genesis_project.manager.IAllianceManager;
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
	private IAllianceManager allianceManager;
	
	private PlayerClass playerClass;
	
	private Game game;
	
	public Player(User user) {
		this(user, null);
	}
	public Player(User user, Game game) {
		this.game = game;
		this.user = user;
		pointManager = new PointManager();
		resourceManager = new ResourceManager(this);
		buildingManager = new BuildingManager(this);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(user.getUsername());
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Player) {
			Player p = (Player) obj;
			return p.user.getUsername().equals(this.getUser().getUsername());
		}
		else {
			return super.equals(obj);
		}
	}
	@Override
	public String toString() {
		return "Player[" + user.toString() + "]";
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
	public IAllianceManager getAllianceManager() {
		return allianceManager;
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
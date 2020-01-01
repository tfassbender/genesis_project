package net.jfabricationgames.genesis_project.game;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import net.jfabricationgames.genesis_project.json.serializer.SerializationIdGenerator;
import net.jfabricationgames.genesis_project.manager.AllianceManager;
import net.jfabricationgames.genesis_project.manager.BuildingManager;
import net.jfabricationgames.genesis_project.manager.IAllianceManager;
import net.jfabricationgames.genesis_project.manager.IBuildingManager;
import net.jfabricationgames.genesis_project.manager.IPointManager;
import net.jfabricationgames.genesis_project.manager.IResearchManager;
import net.jfabricationgames.genesis_project.manager.IResourceManager;
import net.jfabricationgames.genesis_project.manager.ITechnologyManager;
import net.jfabricationgames.genesis_project.manager.PointManager;
import net.jfabricationgames.genesis_project.manager.ResearchManager;
import net.jfabricationgames.genesis_project.manager.ResourceManager;
import net.jfabricationgames.genesis_project.manager.TechnologyManager;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Player {
	
	//final id for json serialization
	private final int id = SerializationIdGenerator.getNextId();
	
	private String username;
	
	private IPointManager pointManager;
	private IResourceManager resourceManager;
	private IBuildingManager buildingManager;
	private IResearchManager researchManager;
	private ITechnologyManager technologyManager;
	private IAllianceManager allianceManager;
	
	private PlayerClass playerClass;
	
	private Game game;
	
	/**
	 * DO NOT USE - empty constructor for json deserialization
	 */
	@Deprecated
	public Player() {
		
	}
	
	public Player(String username) {
		this(username, null);
	}
	public Player(String username, PlayerClass playerClass) {
		this.username = username;
		this.playerClass = playerClass;
		pointManager = new PointManager(this);
		resourceManager = new ResourceManager(this);
		buildingManager = new BuildingManager(this);
		researchManager = new ResearchManager(this);
		technologyManager = new TechnologyManager(this);
		allianceManager = new AllianceManager(this);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(username);
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Player) {
			Player p = (Player) obj;
			return p.getUsername().equals(this.getUsername());
		}
		else {
			return super.equals(obj);
		}
	}
	@Override
	public String toString() {
		return getUsername();
	}
	
	public String getUsername() {
		return username;
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
		//initialize the number of players in the research manager by calling the counter method
		((ResearchManager) researchManager).getNumPlayersInGame();
	}
	
	public int getId() {
		return id;
	}
}
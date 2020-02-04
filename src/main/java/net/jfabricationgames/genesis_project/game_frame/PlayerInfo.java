package net.jfabricationgames.genesis_project.game_frame;

import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Player;

/**
 * Information about the player used for the game overview pane
 */
public class PlayerInfo {
	
	private String name;
	private int points;
	private int planets;
	private int alliances;
	private int resourcesC;
	private int resourcesFe;
	private int resourcesSi;
	private int researchPoints;
	private int scientists;
	private int buildings;
	private int colonies;
	private int mines;
	private int tradingPosts;
	private int laboratories;
	private int governments;
	private int cities;
	private int researchCenters;
	private int drones;
	private int spaceStations;
	
	/**
	 * DO NOT USE - empty constructor for json deserialization
	 */
	@Deprecated
	public PlayerInfo() {
		
	}
	
	public PlayerInfo(Player player) {
		initializeValues(player);
	}
	
	private void initializeValues(Player player) {
		name = player.getUsername();
		if (player.isManagersInitialized()) {
			points = player.getPointManager().getPoints();
			planets = player.getGame().getBoard().getPlayersPlanets(player).size();
			alliances = player.getAllianceManager().getAlliances().size();
			resourcesC = player.getResourceManager().getResourcesC();
			resourcesFe = player.getResourceManager().getResourcesFe();
			resourcesSi = player.getResourceManager().getResourcesSi();
			researchPoints = player.getResourceManager().getResearchPoints();
			scientists = player.getResourceManager().getScientists();
			colonies = player.getBuildingManager().getNumBuildingsOnField(Building.COLONY);
			mines = player.getBuildingManager().getNumBuildingsOnField(Building.MINE);
			tradingPosts = player.getBuildingManager().getNumBuildingsOnField(Building.TRADING_POST);
			laboratories = player.getBuildingManager().getNumBuildingsOnField(Building.LABORATORY);
			governments = player.getBuildingManager().getNumBuildingsOnField(Building.GOVERNMENT);
			cities = player.getBuildingManager().getNumBuildingsOnField(Building.CITY);
			researchCenters = player.getBuildingManager().getNumBuildingsOnField(Building.RESEARCH_CENTER);
			drones = player.getBuildingManager().getNumBuildingsOnField(Building.DRONE);
			spaceStations = player.getBuildingManager().getNumBuildingsOnField(Building.SPACE_STATION);
			buildings = colonies + mines + tradingPosts + laboratories + governments + cities + researchCenters + drones + spaceStations;
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
	public int getPlanets() {
		return planets;
	}
	public void setPlanets(int planets) {
		this.planets = planets;
	}
	
	public int getAlliances() {
		return alliances;
	}
	public void setAlliances(int alliances) {
		this.alliances = alliances;
	}
	
	public int getResourcesC() {
		return resourcesC;
	}
	public void setResourcesC(int resourcesC) {
		this.resourcesC = resourcesC;
	}
	
	public int getResourcesFe() {
		return resourcesFe;
	}
	public void setResourcesFe(int resourcesFe) {
		this.resourcesFe = resourcesFe;
	}
	
	public int getResourcesSi() {
		return resourcesSi;
	}
	public void setResourcesSi(int resourcesSi) {
		this.resourcesSi = resourcesSi;
	}
	
	public int getResearchPoints() {
		return researchPoints;
	}
	public void setResearchPoints(int researchPoints) {
		this.researchPoints = researchPoints;
	}
	
	public int getScientists() {
		return scientists;
	}
	public void setScientists(int scientists) {
		this.scientists = scientists;
	}
	
	public int getBuildings() {
		return buildings;
	}
	public void setBuildings(int buildings) {
		this.buildings = buildings;
	}
	
	public int getColonies() {
		return colonies;
	}
	public void setColonies(int colonies) {
		this.colonies = colonies;
	}
	
	public int getMines() {
		return mines;
	}
	public void setMines(int mines) {
		this.mines = mines;
	}
	
	public int getTradingPosts() {
		return tradingPosts;
	}
	public void setTradingPosts(int tradingPosts) {
		this.tradingPosts = tradingPosts;
	}
	
	public int getLaboratories() {
		return laboratories;
	}
	public void setLaboratories(int laboratories) {
		this.laboratories = laboratories;
	}
	
	public int getGovernments() {
		return governments;
	}
	public void setGovernments(int governments) {
		this.governments = governments;
	}
	
	public int getCities() {
		return cities;
	}
	public void setCities(int cities) {
		this.cities = cities;
	}
	
	public int getResearchCenters() {
		return researchCenters;
	}
	public void setResearchCenters(int researchCenters) {
		this.researchCenters = researchCenters;
	}
	
	public int getDrones() {
		return drones;
	}
	public void setDrones(int drones) {
		this.drones = drones;
	}
	
	public int getSpaceStations() {
		return spaceStations;
	}
	public void setSpaceStations(int spaceStations) {
		this.spaceStations = spaceStations;
	}
}
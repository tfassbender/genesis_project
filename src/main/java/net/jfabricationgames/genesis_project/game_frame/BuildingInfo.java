package net.jfabricationgames.genesis_project.game_frame;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Player;

/**
 * Information about buildings used for the CostOverviewPane.
 */
public class BuildingInfo {
	
	private Building building;
	private Player player;
	
	private int id;
	private String buildingName;
	private String planetClass1;
	private String planetClass2;
	private String planetClass3;
	private String planetClass4;
	private String neighboursSelf;
	private String neighboursOtherPlayers;
	
	private int receivingPrimaryResources;
	private int receivingSecundaryResources;
	private int receivingTertiaryResources;
	private int receivingResearchPoints;
	private int receivingScientists;
	private int defence;
	private int built;
	private int left;
	
	public BuildingInfo(Building building, Player player) {
		this.building = building;
		gatherInfo();
	}
	
	public static ObservableList<BuildingInfo> forAllBuildings(Player player) {
		List<BuildingInfo> infos = new ArrayList<BuildingInfo>(Building.values().length);
		for (Building building : Building.values()) {
			infos.add(new BuildingInfo(building, player));
		}
		return FXCollections.observableArrayList(infos);
	}
	
	private void gatherInfo() {
		//TODO get the information from the Constants class
	}
	
	public Building getBuilding() {
		return building;
	}
	public Player getPlayer() {
		return player;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	
	public String getPlanetClass1() {
		return planetClass1;
	}
	public void setPlanetClass1(String planetClass1) {
		this.planetClass1 = planetClass1;
	}
	
	public String getPlanetClass2() {
		return planetClass2;
	}
	public void setPlanetClass2(String planetClass2) {
		this.planetClass2 = planetClass2;
	}
	
	public String getPlanetClass3() {
		return planetClass3;
	}
	public void setPlanetClass3(String planetClass3) {
		this.planetClass3 = planetClass3;
	}
	
	public String getPlanetClass4() {
		return planetClass4;
	}
	public void setPlanetClass4(String planetClass4) {
		this.planetClass4 = planetClass4;
	}
	
	public String getNeighboursSelf() {
		return neighboursSelf;
	}
	public void setNeighboursSelf(String neighboursSelf) {
		this.neighboursSelf = neighboursSelf;
	}
	
	public String getNeighboursOtherPlayers() {
		return neighboursOtherPlayers;
	}
	public void setNeighboursOtherPlayers(String neighboursOtherPlayers) {
		this.neighboursOtherPlayers = neighboursOtherPlayers;
	}
	
	public int getReceivingPrimaryResources() {
		return receivingPrimaryResources;
	}
	public void setReceivingPrimaryResources(int receivingPrimaryResources) {
		this.receivingPrimaryResources = receivingPrimaryResources;
	}
	
	public int getReceivingSecundaryResources() {
		return receivingSecundaryResources;
	}
	public void setReceivingSecundaryResources(int receivingSecundaryResources) {
		this.receivingSecundaryResources = receivingSecundaryResources;
	}
	
	public int getReceivingTertiaryResources() {
		return receivingTertiaryResources;
	}
	public void setReceivingTertiaryResources(int receivingTertiaryResources) {
		this.receivingTertiaryResources = receivingTertiaryResources;
	}
	
	public int getReceivingResearchPoints() {
		return receivingResearchPoints;
	}
	public void setReceivingResearchPoints(int receivingResearchPoints) {
		this.receivingResearchPoints = receivingResearchPoints;
	}
	
	public int getReceivingScientists() {
		return receivingScientists;
	}
	public void setReceivingScientists(int receivingScientists) {
		this.receivingScientists = receivingScientists;
	}
	
	public int getDefence() {
		return defence;
	}
	public void setDefence(int defence) {
		this.defence = defence;
	}
	
	public int getBuilt() {
		return built;
	}
	public void setBuilt(int built) {
		this.built = built;
	}
	
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
}
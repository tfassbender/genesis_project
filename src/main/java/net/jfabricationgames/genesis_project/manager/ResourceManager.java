package net.jfabricationgames.genesis_project.manager;

import net.jfabricationgames.genesis_project.game.BuildingResources;
import net.jfabricationgames.genesis_project.game.Player;

public class ResourceManager implements IResourceManager {
	
	private Player player;
	
	private BuildingResources buildingResources;
	private int researchPoints;
	private int scientists;
	private int ftl;
	
	public ResourceManager(Player player) {
		this.player = player;
		buildingResources = new BuildingResources();
	}
	
	@Override
	public int getResourcesC() {
		return buildingResources.getResourcesC();
	}
	@Override
	public void setResourcesC(int resources) {
		buildingResources.setResourcesC(resources);
	}
	@Override
	public void addResourcesC(int resources) {
		buildingResources.addResourcesC(resources);
	}
	
	@Override
	public int getResourcesSi() {
		return buildingResources.getResourcesSi();
	}
	@Override
	public void setResourcesSi(int resources) {
		buildingResources.setResourcesSi(resources);
	}
	@Override
	public void addResourcesSi(int resources) {
		buildingResources.addResourcesSi(resources);
	}
	
	@Override
	public int getResourcesFe() {
		return buildingResources.getResourcesFe();
	}
	@Override
	public void setResourcesFe(int resources) {
		buildingResources.setResourcesFe(resources);
	}
	
	@Override
	public void addResourcesFe(int resources) {
		buildingResources.addResourcesFe(resources);
	}
	
	@Override
	public int getResearchPoints() {
		return researchPoints;
	}
	@Override
	public void setResearchPoints(int points) {
		this.researchPoints = points;
	}
	@Override
	public void addResearchPoints(int points) {
		this.researchPoints += points;
	}
	
	@Override
	public int getScientists() {
		return scientists;
	}
	@Override
	public void setScientists(int scientists) {
		this.scientists = scientists;
	}
	@Override
	public void addScientists(int scientists) {
		this.scientists += scientists;
	}
	
	@Override
	public int getFTL() {
		return ftl;
	}
	@Override
	public void setFTL(int ftl) {
		this.ftl = ftl;
	}
	@Override
	public void addFTL(int ftl) {
		this.ftl += ftl;
	}
	
	@Override
	public void collectGameStartResources() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void collectTurnStartResources() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getResourcesPrimary() {
		return buildingResources.getResourcesPrimary(player.getPlayerClass());
	}
	@Override
	public void setResourcesPrimary(int resources) {
		buildingResources.setResourcesPrimary(player.getPlayerClass(), resources);
	}
	@Override
	public void addResourcesPrimary(int resources) {
		buildingResources.addResourcesPrimary(player.getPlayerClass(), resources);
	}
	
	@Override
	public int getResourcesSecundary() {
		return buildingResources.getResourcesSecundary(player.getPlayerClass());
	}
	@Override
	public void setResourcesSecundary(int resources) {
		buildingResources.setResourcesSecundary(player.getPlayerClass(), resources);
	}
	@Override
	public void addResourcesSecundary(int resources) {
		buildingResources.addResourcesSecundary(player.getPlayerClass(), resources);
	}
	
	@Override
	public int getResourcesTertiary() {
		return buildingResources.getResourcesTertiary(player.getPlayerClass());
	}
	@Override
	public void setResourcesTertiary(int resources) {
		buildingResources.setResourcesTertiary(player.getPlayerClass(), resources);
	}
	@Override
	public void addResourcesTertiary(int resources) {
		buildingResources.addResourcesTertiary(player.getPlayerClass(), resources);
	}
}
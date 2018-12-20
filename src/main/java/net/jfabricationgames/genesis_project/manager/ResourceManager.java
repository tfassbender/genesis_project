package net.jfabricationgames.genesis_project.manager;

import net.jfabricationgames.genesis_project.game.Player;

public class ResourceManager implements IResourceManager {
	
	private Player player;
	
	private int resourceC;
	private int resourceSi;
	private int resourceFe;
	private int researchPoints;
	private int scientists;
	private int ftl;
	
	public ResourceManager(Player player) {
		this.player = player;
	}
	
	@Override
	public int getResourcesC() {
		return resourceC;
	}
	@Override
	public void setResourcesC(int resources) {
		this.resourceC = resources;
	}
	@Override
	public void addResourcesC(int resources) {
		this.resourceC += resources;
	}
	
	@Override
	public int getResourcesSi() {
		return resourceSi;
	}
	@Override
	public void setResourcesSi(int resources) {
		this.resourceSi = resources;
	}
	@Override
	public void addResourcesSi(int resources) {
		this.resourceSi += resources;
	}
	
	@Override
	public int getResourcesFe() {
		return resourceFe;
	}
	@Override
	public void setResourcesFe(int resources) {
		this.resourceFe = resources;
	}
	
	@Override
	public void addResourcesFe(int resources) {
		this.resourceFe += resources;
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
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public int getResourcesPrimary() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setResourcesPrimary(int resources) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addResourcesPrimary(int resources) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getResourcesSecundary() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setResourcesSecundary(int resources) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addResourcesSecundary(int resources) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getResourcesTertiary() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setResourcesTertiary(int resources) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addResourcesTertiary(int resources) {
		// TODO Auto-generated method stub
		
	}
}
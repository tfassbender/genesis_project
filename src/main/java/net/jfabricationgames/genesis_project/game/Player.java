package net.jfabricationgames.genesis_project.game;

import net.jfabricationgames.genesis_project.user.User;

public class Player {
	
	private User user;
	
	private int points;
	private int resourceP;
	private int resourceS;
	private int resourceT;
	private int researchPoints;
	private int scientists;
	private int ftl;
	
	public Player(User user, int points, int resourceP, int resourceS, int resourceT, int researchPoints, int scientists, int ftl) {
		this.user = user;
		this.points = points;
		this.resourceP = resourceP;
		this.resourceS = resourceS;
		this.resourceT = resourceT;
		this.researchPoints = researchPoints;
		this.scientists = scientists;
		this.ftl = ftl;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
	public int getResourceP() {
		return resourceP;
	}
	public void setResourceP(int resourceP) {
		this.resourceP = resourceP;
	}
	
	public int getResourceS() {
		return resourceS;
	}
	public void setResourceS(int resourceS) {
		this.resourceS = resourceS;
	}
	
	public int getResourceT() {
		return resourceT;
	}
	public void setResourceT(int resourceT) {
		this.resourceT = resourceT;
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
	
	public int getFtl() {
		return ftl;
	}
	public void setFtl(int ftl) {
		this.ftl = ftl;
	}
}
package net.jfabricationgames.genesis_project.manager;

import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.Player;

public class BuildingManager implements IBuildingManager {
	
	private Player player;
	
	public BuildingManager(Player player) {
		this.player = player;
	}
	
	@Override
	public int getNumBuildingsLeft(Building building) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getNumBuildingsOnField(Building building) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void build(Building building, Field field) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean canBuild(Building building, Field field) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Player getPlayer() {
		return player;
	}
}
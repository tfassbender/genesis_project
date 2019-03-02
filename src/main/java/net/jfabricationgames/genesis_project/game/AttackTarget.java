package net.jfabricationgames.genesis_project.game;

public enum AttackTarget {
	
	RESOURCES,//take resources from the loosing players
	RESEARCH,//take research points from the losing players
	POINTS,//take points from the loosing players
	WEAPON;//reduce the WEAPON research state if the players don't defend enough
}
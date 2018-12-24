package net.jfabricationgames.genesis_project.game;

public enum PlayerColor {
	
	BLUE(0),//
	GREEN(1),//
	GRAY(2),//
	BLACK(3),//
	YELLOW(4),//
	RED(5);//
	
	private final int colorIndex;
	
	private PlayerColor(int colorIndex) {
		this.colorIndex = colorIndex;
	}
	
	public int getColorIndex() {
		return colorIndex;
	}
}
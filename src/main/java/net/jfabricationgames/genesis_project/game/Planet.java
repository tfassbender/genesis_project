package net.jfabricationgames.genesis_project.game;

import javafx.scene.image.Image;

public enum Planet {
	
	CENTER("planet_center.png"),
	GENESIS("planet_genesis.png"),
	BLUE("planet_blue.png"),
	GREEN("planet_green.png"),
	GRAY("planet_gray.png"),
	BLACK("planet_black.png"),
	YELLOW("planet_yellow.png"),
	RED("planet_red.png");
	
	private final Image image;
	
	private Planet(String imagePath) {
		image = new Image("net/jfabricationgames/genesis_project/images/planets/" + imagePath);
	}
	
	public Image getImage() {
		return image;
	}
}
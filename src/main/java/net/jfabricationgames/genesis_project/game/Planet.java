package net.jfabricationgames.genesis_project.game;

public enum Planet {
	
	CENTER("planet_center.png", null),//
	GENESIS("planet_genesis.png", null),//
	BLUE("planet_blue.png", PlayerColor.BLUE),//
	GREEN("planet_green.png", PlayerColor.GREEN),//
	GRAY("planet_gray.png", PlayerColor.GRAY),//
	BLACK("planet_black.png", PlayerColor.BLACK),//
	YELLOW("planet_yellow.png", PlayerColor.YELLOW),//
	RED("planet_red.png", PlayerColor.RED);
	
	//private final Image image;
	private final String imagePath;
	private final PlayerColor playerColor;
	
	private Planet(String imagePath, PlayerColor playerColor) {
		this.imagePath = "net/jfabricationgames/genesis_project/images/planets/" + imagePath;
		this.playerColor = playerColor;
		//image = new Image(this.imagePath);
	}
	
	/*public Image getImage() {
		return image;
	}*/
	public String getImagePath() {
		return imagePath;
	}
	public PlayerColor getPlayerColor() {
		return playerColor;
	}
}
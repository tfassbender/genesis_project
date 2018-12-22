package net.jfabricationgames.genesis_project.game;

public enum Planet {
	
	CENTER("planet_center.png"),
	GENESIS("planet_genesis.png"),
	BLUE("planet_blue.png"),
	GREEN("planet_green.png"),
	GRAY("planet_gray.png"),
	BLACK("planet_black.png"),
	YELLOW("planet_yellow.png"),
	RED("planet_red.png");
	
	//private final Image image;
	private String imagePath;
	
	private Planet(String imagePath) {
		this.imagePath = "net/jfabricationgames/genesis_project/images/planets/" + imagePath;
		//image = new Image(this.imagePath);
	}
	
	/*public Image getImage() {
		return image;
	}*/
	public String getImagePath() {
		return imagePath;
	}
}
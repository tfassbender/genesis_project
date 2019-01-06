package net.jfabricationgames.genesis_project.game;

public enum Planet {
	
	CENTER("planet_center.png", null, null, null, null),//
	GENESIS("planet_genesis.png", null, null, null, null),//
	BLUE("planet_blue.png", PlayerColor.BLUE, Resource.CARBON, Resource.SILICIUM, Resource.IRON),//
	GREEN("planet_green.png", PlayerColor.GREEN, Resource.CARBON, Resource.IRON, Resource.SILICIUM),//
	GRAY("planet_gray.png", PlayerColor.GRAY, Resource.IRON, Resource.CARBON, Resource.SILICIUM),//
	BLACK("planet_black.png", PlayerColor.BLACK, Resource.IRON, Resource.SILICIUM, Resource.CARBON),//
	YELLOW("planet_yellow.png", PlayerColor.YELLOW, Resource.SILICIUM, Resource.IRON, Resource.CARBON),//
	RED("planet_red.png", PlayerColor.RED, Resource.SILICIUM, Resource.CARBON, Resource.IRON);
	
	//private final Image image;
	private final String imagePath;
	private final PlayerColor playerColor;
	
	private final Resource primaryResource;
	private final Resource secondaryResource;
	private final Resource tertiaryResource;
	
	private Planet(String imagePath, PlayerColor playerColor, Resource primaryResource, Resource secondaryResource, Resource tertiaryResource) {
		this.imagePath = "net/jfabricationgames/genesis_project/images/planets/" + imagePath;
		this.playerColor = playerColor;
		//image = new Image(this.imagePath);
		this.primaryResource = primaryResource;
		this.secondaryResource = secondaryResource;
		this.tertiaryResource = tertiaryResource;
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
	
	public Resource getPrimaryResource() {
		return primaryResource;
	}
	public Resource getSecondaryResource() {
		return secondaryResource;
	}
	public Resource getTertiaryResource() {
		return tertiaryResource;
	}
}
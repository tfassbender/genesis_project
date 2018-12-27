package net.jfabricationgames.genesis_project.game;

public enum PlayerClass {
	
	ENCOR(PlayerColor.BLUE, Resource.CARBON, Resource.SILICIUM, Resource.IRON),//
	MUNEN(PlayerColor.BLUE, Resource.CARBON, Resource.SILICIUM, Resource.IRON),//
	SALARANIER(PlayerColor.GREEN, Resource.CARBON, Resource.IRON, Resource.SILICIUM),//
	NOVOX(PlayerColor.GREEN, Resource.CARBON, Resource.IRON, Resource.SILICIUM),//
	WANNARACK(PlayerColor.RED, Resource.SILICIUM, Resource.CARBON, Resource.IRON),//
	YGDRACK(PlayerColor.RED, Resource.SILICIUM, Resource.CARBON, Resource.IRON),//
	LEGION(PlayerColor.YELLOW, Resource.SILICIUM, Resource.IRON, Resource.CARBON),//
	GUNRACS(PlayerColor.YELLOW, Resource.SILICIUM, Resource.IRON, Resource.CARBON),//
	BORAC(PlayerColor.BLACK, Resource.IRON, Resource.SILICIUM, Resource.CARBON),//
	CRAGONS(PlayerColor.BLACK, Resource.IRON, Resource.SILICIUM, Resource.CARBON),//
	JORAVAS(PlayerColor.GRAY, Resource.IRON, Resource.CARBON, Resource.SILICIUM),//
	HERATICS(PlayerColor.GRAY, Resource.IRON, Resource.CARBON, Resource.SILICIUM);//
	
	private final PlayerColor color;
	
	private final Resource primaryResource;
	private final Resource secundaryResource;
	private final Resource tertiaryResource;
	
	private PlayerClass(PlayerColor color, Resource primaryResource, Resource secundaryResource, Resource tertiaryResource) {
		this.color = color;
		this.primaryResource = primaryResource;
		this.secundaryResource = secundaryResource;
		this.tertiaryResource = tertiaryResource;
	}
	
	public int getPlanetDistance(Planet planet) {
		if (planet == Planet.GENESIS || planet == Planet.CENTER || planet == null) {
			return 0;
		}
		
		int distance = Math.abs(getColor().getColorIndex() - planet.getPlayerColor().getColorIndex());
		if (distance > 3) {
			distance = 6 - distance;
		}
		
		return distance;
	}
	
	public PlayerColor getColor() {
		return color;
	}
	
	public Resource getPrimaryResource() {
		return primaryResource;
	}
	public Resource getSecundaryResource() {
		return secundaryResource;
	}
	public Resource getTertiaryResource() {
		return tertiaryResource;
	}
}
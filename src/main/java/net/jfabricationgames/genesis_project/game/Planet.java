package net.jfabricationgames.genesis_project.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.jfabricationgames.genesis_project.game_frame.util.GuiUtils;

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
	
	public static List<Planet> getNearColoredPlanets(Planet planet) {
		List<Planet> nearColored = new ArrayList<Planet>();
		switch (planet) {
			case BLUE:
				nearColored.addAll(Arrays.asList(GREEN, RED, GENESIS));
				break;
			case GREEN:
				nearColored.addAll(Arrays.asList(BLUE, GRAY, GENESIS));
				break;
			case GRAY:
				nearColored.addAll(Arrays.asList(GREEN, BLACK, GENESIS));
				break;
			case BLACK:
				nearColored.addAll(Arrays.asList(GRAY, YELLOW, GENESIS));
				break;
			case YELLOW:
				nearColored.addAll(Arrays.asList(BLACK, RED, GENESIS));
				break;
			case RED:
				nearColored.addAll(Arrays.asList(YELLOW, BLUE, GENESIS));
				break;
			case CENTER:
			case GENESIS:
				nearColored.addAll(Arrays.asList(BLUE, GREEN, GRAY, BLACK, YELLOW, RED, GENESIS));
				break;
			default:
				throw new IllegalStateException("The planet type is unknown: " + planet);
		}
		return nearColored;
	}
	
	/**
	 * Get the name of the enumeration field with a leading upper case letter.
	 */
	public String getTypeName() {
		return GuiUtils.toLeadingCapitalLetter(name());
	}
}
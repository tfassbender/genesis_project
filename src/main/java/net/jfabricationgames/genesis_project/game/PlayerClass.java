package net.jfabricationgames.genesis_project.game;

public enum PlayerClass {
	
	ENCOR(PlayerColor.BLUE, 1, Resource.CARBON, Resource.SILICIUM, Resource.IRON, false, false, "classes/class_boards/class_board_blue_1.png"),//
	MUNEN(PlayerColor.BLUE, 2, Resource.CARBON, Resource.SILICIUM, Resource.IRON, false, false, "classes/class_boards/class_board_blue_2.png"),//
	SALARANIER(PlayerColor.GREEN, 1, Resource.CARBON, Resource.IRON, Resource.SILICIUM, false, false, "classes/class_boards/class_board_green_1.png"),//
	NOVOX(PlayerColor.GREEN, 2, Resource.CARBON, Resource.IRON, Resource.SILICIUM, false, true, "classes/class_boards/class_board_green_2.png"),//
	WANNARACK(PlayerColor.RED, 1, Resource.SILICIUM, Resource.CARBON, Resource.IRON, false, true, "classes/class_boards/class_board_red_1.png"),//
	YGDRACK(PlayerColor.RED, 2, Resource.SILICIUM, Resource.CARBON, Resource.IRON, false, true, "classes/class_boards/class_board_red_2.png"),//
	LEGION(PlayerColor.YELLOW, 1, Resource.SILICIUM, Resource.IRON, Resource.CARBON, false, true, "classes/class_boards/class_board_yellow_1.png"),//
	GUNRACS(PlayerColor.YELLOW, 2, Resource.SILICIUM, Resource.IRON, Resource.CARBON, false, true, "classes/class_boards/class_board_yellow_2.png"),//
	BORAC(PlayerColor.BLACK, 1, Resource.IRON, Resource.SILICIUM, Resource.CARBON, false, false, "classes/class_boards/class_board_black_1.png"),//
	CRAGONS(PlayerColor.BLACK, 2, Resource.IRON, Resource.SILICIUM, Resource.CARBON, false, false, "classes/class_boards/class_board_black_2.png"),//
	JORAVAS(PlayerColor.GRAY, 1, Resource.IRON, Resource.CARBON, Resource.SILICIUM, false, true, "classes/class_boards/class_board_gray_1.png"),//
	HERATICS(PlayerColor.GRAY, 2, Resource.IRON, Resource.CARBON, Resource.SILICIUM, true, false, "classes/class_boards/class_board_gray_2.png");//
	
	private final PlayerColor color;
	private final int colorClass;
	
	private final String classPaneImagePath;
	
	private final Resource primaryResource;
	private final Resource secundaryResource;
	private final Resource tertiaryResource;
	
	//indicates whether the class or government ability is a move that can be executed by the player
	private final boolean classAbilityMove;
	private final boolean governmentAbilityMove;
	
	private static final String classCircleImagePath = "classes/class_circles/class_circle_";//remaining part of the path is added dynamically
	private static final String classEffectImagePath = "classes/class_effects/class_effect_";//remaining part of the path is added dynamically
	
	private PlayerClass(PlayerColor color, int colorClass, Resource primaryResource, Resource secundaryResource, Resource tertiaryResource,
			boolean classAbilityMove, boolean governmentAbilityMove, String classPaneImagePath) {
		this.color = color;
		this.colorClass = colorClass;
		this.primaryResource = primaryResource;
		this.secundaryResource = secundaryResource;
		this.tertiaryResource = tertiaryResource;
		this.classPaneImagePath = classPaneImagePath;
		this.classAbilityMove = classAbilityMove;
		this.governmentAbilityMove = governmentAbilityMove;
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
	
	public String getClassEffectName() {
		return DescriptionTexts.getInstance().CLASS_EFFECT_NAMES.get(this);
	}
	public String getClassEffectDescription() {
		return DescriptionTexts.getInstance().CLASS_EFFECT_DESCRIPTIONS.get(this);
	}
	public String getGovernmentEffectName() {
		return DescriptionTexts.getInstance().GOVERNMENT_EFFECT_NAMES.get(this);
	}
	public String getGovernmentEffectDescription() {
		return DescriptionTexts.getInstance().GOVERNMENT_EFFECT_DESCRIPTIONS.get(this);
	}
	
	public String getClassPaneImagePath() {
		return classPaneImagePath;
	}
	public String getClassCircleImagePath() {
		return classCircleImagePath + color.name().toLowerCase() + ".png";
	}
	public String getClassEffectBaseImagePath() {
		return classEffectImagePath + "base_" + color.name().toLowerCase() + "_" + colorClass + ".png";
	}
	public String getClassEffectGovernmentImagePath() {
		return classEffectImagePath + "government_" + color.name().toLowerCase() + "_" + colorClass + ".png";
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
	
	public boolean isClassAbilityMove() {
		return classAbilityMove;
	}
	public boolean isGovernmentAbilityMove() {
		return governmentAbilityMove;
	}
}
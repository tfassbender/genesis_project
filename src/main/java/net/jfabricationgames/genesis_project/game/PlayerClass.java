package net.jfabricationgames.genesis_project.game;

public enum PlayerClass {
	
	ENCOR(PlayerColor.BLUE, Resource.CARBON, Resource.SILICIUM, Resource.IRON, false, false, "classes/class_boards/class_board_blue_1.png"),//
	MUNEN(PlayerColor.BLUE, Resource.CARBON, Resource.SILICIUM, Resource.IRON, false, false, "classes/class_boards/class_board_blue_2.png"),//
	SALARANIER(PlayerColor.GREEN, Resource.CARBON, Resource.IRON, Resource.SILICIUM, false, false, "classes/class_boards/class_board_green_1.png"),//
	NOVOX(PlayerColor.GREEN, Resource.CARBON, Resource.IRON, Resource.SILICIUM, false, true, "classes/class_boards/class_board_green_2.png"),//
	WANNARACK(PlayerColor.RED, Resource.SILICIUM, Resource.CARBON, Resource.IRON, false, true, "classes/class_boards/class_board_red_1.png"),//
	YGDRACK(PlayerColor.RED, Resource.SILICIUM, Resource.CARBON, Resource.IRON, false, true, "classes/class_boards/class_board_red_2.png"),//
	LEGION(PlayerColor.YELLOW, Resource.SILICIUM, Resource.IRON, Resource.CARBON, false, true, "classes/class_boards/class_board_yellow_1.png"),//
	GUNRACS(PlayerColor.YELLOW, Resource.SILICIUM, Resource.IRON, Resource.CARBON, false, true, "classes/class_boards/class_board_yellow_2.png"),//
	BORAC(PlayerColor.BLACK, Resource.IRON, Resource.SILICIUM, Resource.CARBON, false, false, "classes/class_boards/class_board_black_1.png"),//
	CRAGONS(PlayerColor.BLACK, Resource.IRON, Resource.SILICIUM, Resource.CARBON, false, false, "classes/class_boards/class_board_black_2.png"),//
	JORAVAS(PlayerColor.GRAY, Resource.IRON, Resource.CARBON, Resource.SILICIUM, false, true, "classes/class_boards/class_board_gray_1.png"),//
	HERATICS(PlayerColor.GRAY, Resource.IRON, Resource.CARBON, Resource.SILICIUM, true, false, "classes/class_boards/class_board_gray_2.png");//
	
	private final PlayerColor color;
	
	private final String classPaneImagePath;
	
	private final Resource primaryResource;
	private final Resource secundaryResource;
	private final Resource tertiaryResource;
	
	//indicates whether the class or government ability is a move that can be executed by the player
	private final boolean classAbilityMove;
	private final boolean governmentAbilityMove;
	
	private PlayerClass(PlayerColor color, Resource primaryResource, Resource secundaryResource, Resource tertiaryResource, boolean classAbilityMove,
			boolean governmentAbilityMove, String classPaneImagePath) {
		this.color = color;
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
		//TODO
		return "";
	}
	public String getClassEffectDescription() {
		//TODO implement method
		return "";
	}
	public String getGovernmentEffectName() {
		//TODO
		return "";
	}
	public String getGovernmentEffectDescription() {
		//TODO implement method
		return "";
	}
	
	public String getClassPaneImagePath() {
		return classPaneImagePath;
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
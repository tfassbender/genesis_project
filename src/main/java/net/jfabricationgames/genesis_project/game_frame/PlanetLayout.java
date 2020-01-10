package net.jfabricationgames.genesis_project.game_frame;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Predicate;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Region;
import net.jfabricationgames.genesis_project.connection.exception.ServerCommunicationException;
import net.jfabricationgames.genesis_project.game.AllianceBuilder;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.DescriptionTexts;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.PlayerBuilding;
import net.jfabricationgames.genesis_project.game.PlayerColor;
import net.jfabricationgames.genesis_project.game_frame.util.DialogUtils;
import net.jfabricationgames.genesis_project.game_frame.util.GuiUtils;
import net.jfabricationgames.genesis_project.manager.GameManager;
import net.jfabricationgames.genesis_project.manager.IAllianceManager;
import net.jfabricationgames.genesis_project.move.IMove;
import net.jfabricationgames.genesis_project.move.InvalidMoveException;
import net.jfabricationgames.genesis_project.move.MoveBuilder;
import net.jfabricationgames.genesis_project.move.MoveType;
import net.jfabricationgames.genesis_project.user.UserManager;

/**
 * A Layout class to relocate the buildings on a field.
 */
public class PlanetLayout extends Region {
	
	private static final Logger LOGGER = LogManager.getLogger(PlanetLayout.class);
	
	private int gameId;
	private boolean preGame;
	//the field that is to be displayed
	private Field field;
	
	private static final Predicate<PlayerBuilding> predicateDronesOrSpaceStations = building -> building.getBuilding() == Building.DRONE
			|| building.getBuilding() == Building.SPACE_STATION;
	private static final Predicate<PlayerBuilding> predicateSatellites = building -> building.getBuilding() == Building.SATELLITE;
	
	private static final int buildingImageWidth = 60;
	private static final int buildingImageHeight = 60;
	private static final int satelliteImageWidth = 30;
	private static final int satelliteImageHeight = 30;
	private static final int planetImageWidth = 120;
	private static final int planetImageHeight = 120;
	//center images with this factor; no idea why but it seems to work...
	private static final int centerOffsetX = (int) (planetImageWidth / 3.5);
	private static final int centerOffsetY = (int) (planetImageHeight / 3.5);
	
	public PlanetLayout(int gameId, Field field) {
		this(gameId, field, false);
	}
	public PlanetLayout(int gameId, Field field, boolean preGame) {
		this.gameId = gameId;
		this.field = field;
		this.preGame = preGame;
		//generate and add a context menu
		addContextMenu(this);
		//add all content from the field as child nodes
		if (field.isPlanetField()) {
			//the planet image is the first that is added
			Image planetImage = GuiUtils.loadImage(field.getPlanet().getImagePath(), false);
			ImageView planetImageView = new ImageView(planetImage);
			planetImageView.setFitWidth(planetImageWidth);
			planetImageView.setFitHeight(planetImageHeight);
			getChildren().add(planetImageView);
			
			//add the building images next
			for (int i = 0; i < field.getBuildings().length; i++) {
				PlayerBuilding building = field.getBuildings()[i];
				ImageView buildingImageView;
				if (building != null) {
					Map<PlayerColor, String> imagePathes = building.getBuilding().getImagePathes();
					PlayerColor color = building.getPlayer().getPlayerClass().getColor();
					Image buildingImage = GuiUtils.loadImage(imagePathes.get(color), false);
					buildingImageView = new ImageView(buildingImage);
				}
				else {
					//if there is no building at a position add an empty imageView
					buildingImageView = new ImageView();
				}
				buildingImageView.setFitWidth(buildingImageWidth);
				buildingImageView.setFitHeight(buildingImageHeight);
				getChildren().add(buildingImageView);
			}
		}
		else {
			//space field
			
			//first search for drones or space stations
			Optional<PlayerBuilding> defenseBuilding = field.getSpaceBuildings().stream().filter(predicateDronesOrSpaceStations).findAny();
			if (defenseBuilding.isPresent()) {
				Map<PlayerColor, String> imagePathes = defenseBuilding.get().getBuilding().getImagePathes();
				PlayerColor color = defenseBuilding.get().getPlayer().getPlayerClass().getColor();
				Image defenseBuildingImage = GuiUtils.loadImage(imagePathes.get(color), false);
				ImageView defenseBuildingImageView = new ImageView(defenseBuildingImage);
				defenseBuildingImageView.setFitWidth(buildingImageWidth);
				defenseBuildingImageView.setFitHeight(buildingImageHeight);
				getChildren().add(defenseBuildingImageView);
			}
			
			//add all satellites
			List<PlayerBuilding> satellites = field.getSpaceBuildings().stream().filter(predicateSatellites).collect(Collectors.toList());
			for (PlayerBuilding satellite : satellites) {
				Map<PlayerColor, String> imagePathes = satellite.getBuilding().getImagePathes();
				PlayerColor color = satellite.getPlayer().getPlayerClass().getColor();
				Image satelliteImage = GuiUtils.loadImage(imagePathes.get(color), false);
				ImageView satelliteImageView = new ImageView(satelliteImage);
				satelliteImageView.setFitWidth(satelliteImageWidth);
				satelliteImageView.setFitHeight(satelliteImageHeight);
				getChildren().add(satelliteImageView);
			}
			
			//add an empty image view if nothing is on the field
			if (!defenseBuilding.isPresent() && satellites.isEmpty()) {
				ImageView emptyImageView = new ImageView();
				emptyImageView.setFitWidth(100);
				emptyImageView.setFitHeight(100);
				getChildren().add(emptyImageView);
			}
		}
	}
	
	private void addContextMenu(Node node) {
		ContextMenu contextMenu = new ContextMenu();
		
		if (preGame) {
			MenuItem placeBuilding = new MenuItem("Startgebäude platzieren");
			placeBuilding.setOnAction(e -> executePlaceStartBuildingMove());
			if (!canPlaceStartBuilding()) {
				placeBuilding.setDisable(true);
			}
			contextMenu.getItems().add(placeBuilding);
		}
		else {
			//building menu
			Menu buildMenu = createBuildMenu();
			Menu allianceMenu = createAllianceMenu();
			
			//add menus to context menu
			contextMenu.getItems().add(buildMenu);
			contextMenu.getItems().add(allianceMenu);
			
			//show or hide the context menu
			node.setOnMouseClicked((e) -> {
				if (e.getButton() == MouseButton.SECONDARY) {
					contextMenu.show(node, e.getScreenX(), e.getScreenY());
				}
				else {
					contextMenu.hide();
				}
			});
		}
	}
	
	private Menu createBuildMenu() {
		Menu buildMenu = new Menu("Bauen");
		for (Building building : Building.values()) {
			if (building != Building.SATELLITE) {
				//all buildings but satellites can be built
				MenuItem build = new MenuItem(building.getName() + " bauen");
				//set the action event
				build.setOnAction(e -> executeBuildMove(building));
				//check whether the building can be built (disable the menu item if not)
				if (!isBuildable(building)) {
					build.setDisable(true);
				}
				buildMenu.getItems().add(build);
			}
		}
		return buildMenu;
	}
	
	private Menu createAllianceMenu() {
		Menu allianceMenu = new Menu("Allianz");
		MenuItem addToSelection = new MenuItem("Zur Auswahl hinzufügen");
		MenuItem removeFromSelection = new MenuItem("Von der Auswahl entfernen");
		
		//disable an item when the field is already added or not
		GameManager gameManager = GameManager.getInstance();
		IAllianceManager allianceManager = gameManager.getAllianceManager(gameId, gameManager.getLocalPlayer());
		AllianceBuilder builder = allianceManager.getAllianceBuilder();
		if (builder.containsField(field)) {
			addToSelection.setDisable(true);
		}
		else {
			removeFromSelection.setDisable(true);
		}
		
		addToSelection.setOnAction(e -> addFieldToAllianceSelection());
		removeFromSelection.setOnAction(e -> removeFieldFromAllianceSelection());
		allianceMenu.getItems().add(addToSelection);
		allianceMenu.getItems().add(removeFromSelection);
		return allianceMenu;
	}
	
	private void executePlaceStartBuildingMove() {
		IMove move = createPlaceStartBuildingMove();
		GameManager gameManager = GameManager.getInstance();
		try {
			gameManager.executeMove(gameId, move);
		}
		catch (IllegalArgumentException | InvalidMoveException | ServerCommunicationException e) {
			LOGGER.error("Error in move execution", e);
			DialogUtils.showExceptionDialog("Move execution error", DescriptionTexts.getInstance().ERROR_TEXT_MOVE_EXECUTION, e, true);
		}
	}
	private boolean canPlaceStartBuilding() {
		IMove move = createPlaceStartBuildingMove();
		GameManager gameManager = GameManager.getInstance();
		return gameManager.isMoveExecutable(gameId, move);
	}
	
	private void executeBuildMove(Building building) {
		IMove move = buildMove(building);
		try {
			GameManager.getInstance().executeMove(gameId, move);
		}
		catch (IllegalArgumentException | IllegalStateException | InvalidMoveException | ServerCommunicationException e) {
			LOGGER.error("Error in move execution", e);
			DialogUtils.showExceptionDialog("Move execution error", DescriptionTexts.getInstance().ERROR_TEXT_MOVE_EXECUTION, e, true);
		}
	}
	
	private void addFieldToAllianceSelection() {
		GameManager gameManager = GameManager.getInstance();
		IAllianceManager allianceManager = gameManager.getAllianceManager(gameId, gameManager.getLocalPlayer());
		AllianceBuilder builder = allianceManager.getAllianceBuilder();
		
		if (field.isPlanetField()) {
			builder.addPlanetField(field);
		}
		else {
			builder.addConnectingField(field);
		}
	}
	
	private void removeFieldFromAllianceSelection() {
		GameManager gameManager = GameManager.getInstance();
		IAllianceManager allianceManager = gameManager.getAllianceManager(gameId, gameManager.getLocalPlayer());
		AllianceBuilder builder = allianceManager.getAllianceBuilder();
		
		if (field.isPlanetField()) {
			builder.removePlanetField(field);
		}
		else {
			builder.removeConnectingField(field);
		}
	}
	
	private boolean isBuildable(Building building) {
		IMove move = buildMove(building);
		return GameManager.getInstance().isMoveExecutable(gameId, move);
	}
	
	private IMove buildMove(Building building) {
		MoveBuilder builder = new MoveBuilder();
		builder.setType(MoveType.BUILD);
		builder.setBuilding(building);
		builder.setField(field);
		builder.setPlayer(UserManager.getInstance().getLocalUsername());
		IMove move = builder.build();
		
		return move;
	}
	
	private IMove createPlaceStartBuildingMove() {
		IMove move = new MoveBuilder().setType(MoveType.PLACE_START_BUILDING).setField(field).setPlayer(UserManager.getInstance().getLocalUsername())
				.build();
		return move;
	}
	
	/**
	 * Create the layout for all children that are added.
	 */
	@Override
	protected void layoutChildren() {
		super.layoutChildren();
		ObservableList<Node> children = getChildren();
		if (field.isPlanetField()) {
			relocatePlanetFieldChilds(children);
		}
		else {
			relocateSpaceFieldChilds(children);
		}
	}
	
	private void relocatePlanetFieldChilds(ObservableList<Node> children) {
		//the first image is the planet image that is not relocated
		
		//the other images are the buildings (if there are any)
		int numBuildings = field.getBuildings().length;
		for (int i = 0; i < numBuildings; i++) {
			//arrange the buildings on a circle around the center of the planet
			double relocateAngle = (2d * Math.PI / numBuildings) * i;
			double relocateDistance = 40;
			int relocateX = (int) (Math.sin(relocateAngle) * relocateDistance) + centerOffsetX;
			int relocateY = (int) (-Math.cos(relocateAngle) * relocateDistance) + centerOffsetY;
			Node child = children.get(i + 1);
			child.relocate(relocateX, relocateY);
		}
	}
	
	private void relocateSpaceFieldChilds(ObservableList<Node> children) {
		//the first image is a drone or space station; all others are satellites
		boolean hasDefenseBuilding = field.getSpaceBuildings().stream().anyMatch(predicateDronesOrSpaceStations);
		boolean hasSatelliteBuildings = field.getSpaceBuildings().stream().anyMatch(predicateSatellites);
		
		int satelliteYOffset = centerOffsetY;
		if (hasDefenseBuilding) {
			//both building types present -> place the defense building above the satellites
			Node defenceBuilding = children.get(0);
			defenceBuilding.relocate(centerOffsetX, centerOffsetY - 30);
			if (hasSatelliteBuildings) {
				satelliteYOffset = 30 + centerOffsetY;
			}
		}
		if (hasSatelliteBuildings) {
			int satellites = children.size();
			int defenseBuildingOffset = 0;
			if (hasDefenseBuilding) {
				satellites--;//the first is no satellite
				defenseBuildingOffset = 1;
			}
			for (int i = 0; i < satellites; i++) {
				int xOffset = 30 * i - (15 * (i - 1)) + centerOffsetX;
				Node satelliteBuilding = children.get(i + defenseBuildingOffset);
				satelliteBuilding.relocate(xOffset, satelliteYOffset);
			}
		}
	}
}
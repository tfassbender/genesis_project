package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import net.jfabricationgames.genesis_project.game.Board;
import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.genesis_project.game_frame.util.GuiUtils;
import net.jfabricationgames.genesis_project.manager.GameManager;
import net.jfabricationgames.genesis_project.game.Field;

public class BoardPaneController implements Initializable {
	
	@FXML
	private ImageView imageViewBoardBackground;
	@FXML
	private AnchorPane anchorPaneFields;
	
	private int gameId;
	private boolean preGame;
	
	public BoardPaneController(int gameId) {
		this(gameId, false);
	}
	public BoardPaneController(int gameId, boolean preGame) {
		this.gameId = gameId;
		this.preGame = preGame;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GuiUtils.loadImageToView("basic/background.png", true, imageViewBoardBackground);
		//addPositionContextMenu(anchorPaneFields);
		buildField();
	}
	
	public void updateAll() {
		buildField();
	}
	
	/**
	 * Add all planet and building images to the field.
	 */
	public void buildField() {
		GameManager gameManager = GameManager.getInstance();
		Board board = gameManager.getBoard(gameId);
		
		//remove all old images first
		anchorPaneFields.getChildren().clear();
		//check every field on the board
		boolean[] addBackground = new boolean[] {true, false};//first add all background image views (the ones that are empty space fields)
		for (boolean background : addBackground) {
			for (Entry<Position, Field> fieldPosition : board.getFields().entrySet()) {
				Position position = fieldPosition.getKey();
				Field field = fieldPosition.getValue();
				if (field.isDisplayed() != background) {//keep the empty space fields in the background
					PlanetLayout fieldLayout = new PlanetLayout(gameId, field, preGame);
					//add the field to the board
					anchorPaneFields.getChildren().add(fieldLayout);
					//relocate the field to it's position
					int[] boardPosition = position.getBoardLocation();
					if (boardPosition != null) {
						fieldLayout.relocate(boardPosition[0] - PlanetLayout.BUILDING_IMAGE_WIDTH,
								boardPosition[1] - PlanetLayout.BUILDING_IMAGE_HEIGHT);
					}
				}
			}
		}
	}
	
	/**
	 * Used to define the positions of the field's cells. Not used in game.
	 */
	@SuppressWarnings("unused")
	private void addPositionContextMenu(Node node) {
		ContextMenu contextMenu = new ContextMenu();
		MenuItem printPosition = new MenuItem("Print Position");
		printPosition.setOnAction((e2) -> {
			Bounds boundsInScreen = node.localToScreen(node.getBoundsInLocal());
			double backgroundXOnScreen = boundsInScreen.getMinX();
			double backgroundYOnScreen = boundsInScreen.getMinY();
			System.out.println(String.format("Position: %4.0f, %4.0f", contextMenu.getAnchorX() - backgroundXOnScreen,
					contextMenu.getAnchorY() - backgroundYOnScreen));
		});
		contextMenu.getItems().add(printPosition);
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
package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.jfabricationgames.genesis_project.game.Board;
import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.genesis_project.game.Field;

public class BoardPaneController implements Initializable {
	
	@FXML
	private ImageView imageViewBoardBackground;
	@FXML
	private AnchorPane anchorPaneFields;
	
	private Board board;
	
	public BoardPaneController(Board board) {
		this.board = board;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GuiUtils.loadImageToView("basic/background.png", true, imageViewBoardBackground);
		//addPositionContextMenu(imageViewBoardBackground);
		buildField();
	}
	
	/**
	 * Add all planet and building images to the field.
	 */
	public void buildField() {
		//remove all old images first
		anchorPaneFields.getChildren().clear();
		//check every field on the board
		for (Entry<Position, Field> fieldPosition : board.getFields().entrySet()) {
			Position position = fieldPosition.getKey();
			Field field = fieldPosition.getValue();
			if (field.isDisplayed()) {
				PlanetLayout fieldLayout = new PlanetLayout(field);
				//add the field to the board
				anchorPaneFields.getChildren().add(fieldLayout);
				//relocate the field to it's position
				int[] boardPosition = position.getBoardLocation();
				fieldLayout.relocate(boardPosition[0], boardPosition[1]);
			}
		}
	}
	
	/**
	 * Used to define the positions of the field's cells. Not used in game.
	 */
	/*private void addPositionContextMenu(ImageView background) {
		ContextMenu contextMenu = new ContextMenu();
		MenuItem printPosition = new MenuItem("Print Position");
		printPosition.setOnAction((e2) -> {
			Bounds boundsInScreen = background.localToScreen(background.getBoundsInLocal());
			double backgroundXOnScreen = boundsInScreen.getMinX();
			double backgroundYOnScreen = boundsInScreen.getMinY();
			System.out.println(String.format("Position: %4.0f, %4.0f", contextMenu.getAnchorX() - backgroundXOnScreen,
					contextMenu.getAnchorY() - backgroundYOnScreen));
		});
		contextMenu.getItems().add(printPosition);
		background.setOnMouseClicked((e) -> {
			if (e.getButton() == MouseButton.SECONDARY) {
				contextMenu.show(background, e.getScreenX(), e.getScreenY());
			}
		});
	}*/
}
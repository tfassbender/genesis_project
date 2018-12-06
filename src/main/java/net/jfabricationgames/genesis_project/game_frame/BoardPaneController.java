package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

public class BoardPaneController implements Initializable {
	
	@FXML
	private ImageView imageViewBoardBackground;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GuiUtils.loadImageToView("basic/background.png", true, imageViewBoardBackground);
		//addPositionContextMenu(imageViewBoardBackground);
	}
	
	/**
	 * Used to define the positions of the field's cells.
	 * Not used in game.
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
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
	}
}
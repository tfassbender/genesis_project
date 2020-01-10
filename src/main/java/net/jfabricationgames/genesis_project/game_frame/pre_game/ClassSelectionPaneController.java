package net.jfabricationgames.genesis_project.game_frame.pre_game;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import net.jfabricationgames.genesis_project.game.PlayerClass;
import net.jfabricationgames.genesis_project.game_frame.util.GuiUtils;

public class ClassSelectionPaneController implements Initializable {
	
	@FXML
	private VBox vboxPlayerClasses;
	
	private int gameId;
	
	public ClassSelectionPaneController(int gameId) {
		this.gameId = gameId;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//add a panel for every player class
		for (PlayerClass playerClass : PlayerClass.values()) {
			Parent classPane = new GuiUtils().loadPane("PlayerClassPane.fxml", new PlayerClassPaneController(gameId, playerClass), null);
			vboxPlayerClasses.getChildren().add(classPane);
		}
	}
}
package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class TurnPaneController implements Initializable {
	
	@FXML
	private ImageView imageTurn1;
	@FXML
	private ImageView imageTurn2;
	@FXML
	private ImageView imageTurn3;
	@FXML
	private ImageView imageTurn4;
	@FXML
	private ImageView imageTurn5;
	@FXML
	private ImageView imageTurn6;
	
	@FXML
	private Label labelTurnName1;
	@FXML
	private Label labelTurnName2;
	@FXML
	private Label labelTurnName3;
	@FXML
	private Label labelTurnName4;
	@FXML
	private Label labelTurnName5;
	@FXML
	private Label labelTurnName6;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GuiUtils.loadImageToView("cards/turn_goals/turn_goal_turn_over.png", true, imageTurn1);
		GuiUtils.loadImageToView("cards/turn_goals/turn_goal_turn_over.png", true, imageTurn2);
		GuiUtils.loadImageToView("cards/turn_goals/turn_goal_turn_over.png", true, imageTurn3);
		GuiUtils.loadImageToView("cards/turn_goals/turn_goal_turn_over.png", true, imageTurn4);
		GuiUtils.loadImageToView("cards/turn_goals/turn_goal_turn_over.png", true, imageTurn5);
		GuiUtils.loadImageToView("cards/turn_goals/turn_goal_turn_over.png", true, imageTurn6);
	}
}
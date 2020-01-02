package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.jfabricationgames.genesis_project.game.Constants;
import net.jfabricationgames.genesis_project.manager.GameManager;
import net.jfabricationgames.genesis_project.manager.ITurnManager;

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
	
	private final String turnOverImage = "cards/turn_goals/turn_goal_turn_over.png";
	
	private int gameId;
	
	public TurnPaneController(int gameId) {
		this.gameId = gameId;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeTurnLabels();
		updateTurnImages();
	}
	
	private void initializeTurnLabels() {
		GameManager gameManager = GameManager.getInstance();
		ITurnManager turnManager = gameManager.getTurnManager(gameId);
		
		Label[] labels = new Label[] {labelTurnName1, labelTurnName2, labelTurnName3, labelTurnName4, labelTurnName5, labelTurnName6};
		
		for (int i = 0; i < Constants.getInstance().TURNS_PLAYED; i++) {
			Label label = labels[i];
			label.setText(turnManager.getTurnGoals().get(i).getName());
		}
	}
	
	public void updateTurnImages() {
		GameManager gameManager = GameManager.getInstance();
		ITurnManager turnManager = gameManager.getTurnManager(gameId);
		
		ImageView[] images = new ImageView[] {imageTurn1, imageTurn2, imageTurn3, imageTurn4, imageTurn5, imageTurn6};
		
		for (int i = 0; i < turnManager.getTurn() - 1; i++) {
			Image image = GuiUtils.loadImage(turnOverImage, true);
			images[i].setImage(image);
			images[i].setCache(true);
		}
		for (int i = turnManager.getTurn() - 1; i < Constants.getInstance().TURNS_PLAYED; i++) {
			String imagePath = turnManager.getTurnGoals().get(i).getImagePath();
			Image image = GuiUtils.loadImage(imagePath, true);
			images[i].setImage(image);
			images[i].setCache(true);
		}
	}
}
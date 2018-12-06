package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class AttackPaneController implements Initializable {
	
	@FXML
	private ImageView imageViewAttackCard;
	
	@FXML
	private Label labelAttackAttacker;
	@FXML
	private Label labelAttackTarget;
	@FXML
	private Label labelAttackDefence;
	@FXML
	private Label labelAttackDefenceResult;
	
	@FXML
	private TextArea textAreaAttackResult;
	@FXML
	private Button buttonAttackCommit;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GuiUtils.loadImageToView("basic/attack_card.png", true, imageViewAttackCard);
	}
}
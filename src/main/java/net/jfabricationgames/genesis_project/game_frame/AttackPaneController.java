package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import net.jfabricationgames.genesis_project.game.Attack;

public class AttackPaneController implements Initializable {
	
	@FXML
	private ImageView imageViewAttackCard;
	
	@FXML
	private Label labelAttackCardTitle;
	@FXML
	private Label labelAttackCardStrength;
	@FXML
	private Label labelAttackCardText;
	
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
		
		setAttack(null);
	}
	
	public void setAttack(Attack attack) {
		if (attack != null) {
			labelAttackAttacker.setText(attack.getEnemy().getName());
			labelAttackTarget.setText(attack.getTarget().getPosition().asCoordinateString());
			labelAttackDefence.setText(Integer.toString(attack.calculateDefenseStrength()));
			String defenseStateText;
			if (attack.defenseSuccessful()) {
				defenseStateText = "Erfolgreich";
			}
			else {
				defenseStateText = "Nicht Erfolgreich";
			}
			labelAttackDefenceResult.setText(defenseStateText);
			labelAttackCardTitle.setText("Angriff durch " + attack.getEnemy().getName());
			labelAttackCardStrength.setText(Integer.toString(attack.getStrength()));
			String cardText = generateCardText(attack);
			labelAttackCardText.setText(cardText);
		}
		else {
			String empty = "---";
			labelAttackAttacker.setText(empty);
			labelAttackTarget.setText(empty);
			labelAttackDefence.setText(empty);
			labelAttackDefenceResult.setText(empty);
			labelAttackCardTitle.setText("");
			labelAttackCardStrength.setText("");
			labelAttackCardText.setText("");
			textAreaAttackResult.setText("");
		}
	}
	
	private String generateCardText(Attack attack) {
		StringBuilder sb = new StringBuilder();
		sb.append("Angriffsziel: ");
		sb.append(attack.getAttackTarget().getName());
		sb.append("\nVerteidigung   -   Abzug\n");
		int[] penalties = attack.getAttackPenalty();
		int[] penaltyOffsets = attack.getPenaltyOffsets();
		for (int i = 0; i < penalties.length; i++) {
			sb.append(String.format("<  %2d   -   %2d", (attack.getStrength() - penaltyOffsets[i]), penalties[i]));
		}
		sb.append(String.format(">= %2d   -    0", attack.getStrength()));
		return sb.toString();
	}
}
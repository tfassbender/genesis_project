package net.jfabricationgames.genesis_project.game_frame.pre_game;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import net.jfabricationgames.genesis_project.game.DescriptionTexts;
import net.jfabricationgames.genesis_project.game.PlayerClass;
import net.jfabricationgames.genesis_project.game_frame.util.DialogUtils;
import net.jfabricationgames.genesis_project.game_frame.util.GuiUtils;
import net.jfabricationgames.genesis_project.manager.GameManager;
import net.jfabricationgames.genesis_project.move.IMove;
import net.jfabricationgames.genesis_project.move.InvalidMoveException;
import net.jfabricationgames.genesis_project.move.MoveBuilder;
import net.jfabricationgames.genesis_project.move.MoveType;
import net.jfabricationgames.genesis_project.user.UserManager;

public class PlayerClassPaneController implements Initializable {
	
	private static final Logger LOGGER = LogManager.getLogger(PlayerClassPaneController.class);
	
	@FXML
	private ImageView imageViewClassEffect;
	@FXML
	private ImageView imageViewGovernmentEffect;
	@FXML
	private Label labelClassName;
	@FXML
	private ImageView imageViewClassCircle;
	@FXML
	private Button buttonSelectClass;
	
	private int gameId;
	
	private PlayerClass playerClass;
	
	public PlayerClassPaneController(int gameId, PlayerClass playerClass) {
		this.gameId = gameId;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GuiUtils.loadImageToView(playerClass.getClassCircleImagePath(), true, imageViewClassCircle);
		GuiUtils.loadImageToView(playerClass.getClassEffectBaseImagePath(), true, imageViewClassEffect);
		GuiUtils.loadImageToView(playerClass.getClassEffectGovernmentImagePath(), true, imageViewGovernmentEffect);
		Tooltip.install(imageViewClassEffect, new Tooltip(playerClass.getClassEffectDescription()));
		Tooltip.install(imageViewGovernmentEffect, new Tooltip(playerClass.getGovernmentEffectDescription()));
		
		labelClassName.setText(GuiUtils.toLeadingCapitalLetter(playerClass.name()));
		buttonSelectClass.setOnAction(e -> selectClass());
	}
	
	private void selectClass() {
		IMove move = new MoveBuilder().setType(MoveType.CHOOSE_CLASS).setPlayerClass(playerClass)
				.setPlayer(UserManager.getInstance().getLocalUsername()).build();
		GameManager gameManager = GameManager.getInstance();
		try {
			gameManager.executeMove(gameId, move);
		}
		catch (IllegalArgumentException | InvalidMoveException e) {
			LOGGER.error("Error in move execution", e);
			DialogUtils.showExceptionDialog("Move execution error", DescriptionTexts.getInstance().ERROR_TEXT_MOVE_EXECUTION, e, true);
		}
	}
}
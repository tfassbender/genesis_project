package net.jfabricationgames.genesis_project.game_frame.pre_game;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import net.jfabricationgames.genesis_project.connection.exception.ServerCommunicationException;
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
		this.playerClass = playerClass;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GuiUtils.loadImageToView(playerClass.getClassCircleImagePath(), true, imageViewClassCircle);
		GuiUtils.loadImageToView(playerClass.getClassEffectBaseImagePath(), true, imageViewClassEffect);
		GuiUtils.loadImageToView(playerClass.getClassEffectGovernmentImagePath(), true, imageViewGovernmentEffect);
		
		Tooltip classEffect = new Tooltip(playerClass.getClassEffectDescription());
		classEffect.setStyle("-fx-font-size: 16");
		Tooltip.install(imageViewClassEffect, classEffect);
		
		Tooltip governmentEffect = new Tooltip(playerClass.getGovernmentEffectDescription());
		governmentEffect.setStyle("-fx-font-size: 16");
		Tooltip.install(imageViewGovernmentEffect, governmentEffect);
		
		labelClassName.setText(GuiUtils.toLeadingCapitalLetter(playerClass.name()));
		buttonSelectClass.setOnAction(e -> selectClass());
		
		updateDisabledState();
	}
	
	private void selectClass() {
		IMove move = new MoveBuilder().setType(MoveType.CHOOSE_CLASS).setPlayerClass(playerClass)
				.setPlayer(UserManager.getInstance().getLocalUsername()).build();
		GameManager gameManager = GameManager.getInstance();
		try {
			gameManager.executeMove(gameId, move);
		}
		catch (IllegalArgumentException | InvalidMoveException | ServerCommunicationException e) {
			LOGGER.error("Error in move execution", e);
			DialogUtils.showExceptionDialog("Move execution error", DescriptionTexts.getInstance().ERROR_TEXT_MOVE_EXECUTION, e, true);
		}
	}
	
	public void updateDisabledState() {
		GameManager gameManager = GameManager.getInstance();
		Set<PlayerClass> possibleClasses = gameManager.getPlayerClassesToChoose(gameId);
		boolean disable = !possibleClasses.contains(playerClass);
		
		buttonSelectClass.setDisable(disable);
	}
}
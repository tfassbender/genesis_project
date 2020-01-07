package net.jfabricationgames.genesis_project.game_frame.pre_game;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import net.jfabricationgames.genesis_project.game_frame.BoardPaneController;
import net.jfabricationgames.genesis_project.game_frame.GuiUtils;
import net.jfabricationgames.genesis_project.game_frame.TurnPaneController;

public class PreGameSelectionController implements Initializable {
	
	@FXML
	private AnchorPane anchorClassSelectionPane;
	@FXML
	private AnchorPane anchorBoardPane;
	@FXML
	private AnchorPane anchorPlayerInfoPane;
	@FXML
	private AnchorPane anchorTurnPane;
	
	private BoardPaneController boardController;
	private ClassSelectionPaneController classSelectionController;
	private PreGamePlayerInfoPaneController playerInfoController;
	private TurnPaneController turnController;
	
	private int gameId;
	
	public PreGameSelectionController(int gameId) {
		this.gameId = gameId;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		GuiUtils guiUtils = new GuiUtils();
		boardController = new BoardPaneController(gameId, true);
		classSelectionController = new ClassSelectionPaneController(gameId);
		playerInfoController = new PreGamePlayerInfoPaneController(gameId);
		turnController = new TurnPaneController(gameId);
		guiUtils.insertPane("BoardPane.fxml", anchorBoardPane, boardController, null);
		guiUtils.insertPane("ClassSelectionPane.fxml", anchorClassSelectionPane, classSelectionController, null);
		guiUtils.insertPane("PreGamePlayerInfoPane.fxml", anchorPlayerInfoPane, playerInfoController, null);
		guiUtils.insertPane("TurnPane.fxml", anchorTurnPane, turnController, null);
	}
}
package net.jfabricationgames.genesis_project.game_frame.pre_game;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.manager.GameManager;
import net.jfabricationgames.genesis_project.manager.ITurnManager;

public class PreGamePlayerInfoPaneController implements Initializable {
	
	@FXML
	private Label labelPlayersTurn;
	@FXML
	private ListView<Player> listViewPlayersTurn;
	@FXML
	private ListView<String> listViewPlayersClasses;
	
	private ObservableList<String> playerClasses = FXCollections.observableArrayList();
	
	private int gameId;
	
	public PreGamePlayerInfoPaneController(int gameId) {
		this.gameId = gameId;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GameManager gameManager = GameManager.getInstance();
		ITurnManager turnManager = gameManager.getTurnManager(gameId);
		
		listViewPlayersTurn.setItems(turnManager.getCurrentTurnPlayerOrder());
		labelPlayersTurn.textProperty().bind(Bindings.convert(turnManager.getCurrentPlayerProperty()));
		listViewPlayersClasses.setItems(playerClasses);
		
		updatePlayerClasses();
	}
	
	public void updatePlayerClasses() {
		GameManager gameManager = GameManager.getInstance();
		//create a list of player names and classes
		List<String> players = gameManager.getPlayers(gameId).stream().map(p -> p + ": " + gameManager.getPlayerClass(gameId, p))
				.collect(Collectors.toList());
		
		playerClasses.clear();
		playerClasses.addAll(players);
	}
}
package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.PlayerScore;
import net.jfabricationgames.genesis_project.manager.GameManager;
import net.jfabricationgames.genesis_project.manager.IGamePointManager;
import net.jfabricationgames.genesis_project.manager.IPointManager;
import net.jfabricationgames.genesis_project.manager.ITurnManager;

public class GameOverviewPaneController implements Initializable {
	
	@FXML
	private ListView<PlayerScore> listGameOverviewPoints;
	@FXML
	private Label labelGameOverviewPlayerPoints;
	@FXML
	private Label labelGameOverviewPlayerPosition;
	@FXML
	private ListView<Player> listGameOverviewPlayerOrder;
	@FXML
	private Label labelGameOverviewPlayersTurn;
	@FXML
	private ListView<Player> listGameOverviewNextTurnPlayerOrder;
	
	@FXML
	private TableView<PlayerInfo> tableGameOverview;
	@FXML
	private TableColumn<PlayerInfo, String> tableColumnGameOverviewPlayerName;
	@FXML
	private TableColumn<PlayerInfo, Integer> tableColumnGameOverviewPoints;
	@FXML
	private TableColumn<PlayerInfo, Integer> tableColumnGameOverviewPlanets;
	@FXML
	private TableColumn<PlayerInfo, Integer> tableColumnGameOverviewAlliances;
	@FXML
	private TableColumn<PlayerInfo, Integer> tableColumnGameOverviewResourcesC;
	@FXML
	private TableColumn<PlayerInfo, Integer> tableColumnGameOverviewResourcesFe;
	@FXML
	private TableColumn<PlayerInfo, Integer> tableColumnGameOverviewResourcesSi;
	@FXML
	private TableColumn<PlayerInfo, Integer> tableColumnGameOverviewResearchPoints;
	@FXML
	private TableColumn<PlayerInfo, Integer> tableColumnGameOverviewScientists;
	@FXML
	private TableColumn<PlayerInfo, Integer> tableColumnGameOverviewNumBuildings;
	@FXML
	private TableColumn<PlayerInfo, Integer> tableColumnGameOverviewColonies;
	@FXML
	private TableColumn<PlayerInfo, Integer> tableColumnGameOverviewMines;
	@FXML
	private TableColumn<PlayerInfo, Integer> tableColumnGameOverviewTraidingPosts;
	@FXML
	private TableColumn<PlayerInfo, Integer> tableColumnGameOverviewLaboratories;
	@FXML
	private TableColumn<PlayerInfo, Integer> tableColumnGameOverviewGoverments;
	@FXML
	private TableColumn<PlayerInfo, Integer> tableColumnGameOverviewCities;
	@FXML
	private TableColumn<PlayerInfo, Integer> tableColumnGameOverviewResearchStations;
	@FXML
	private TableColumn<PlayerInfo, Integer> tableColumnGameOverviewDrones;
	@FXML
	private TableColumn<PlayerInfo, Integer> tableColumnGameOverviewSpaceStations;
	
	private int gameId;
	
	public GameOverviewPaneController(int gameId) {
		this.gameId = gameId;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bindPlayerOrderLists();
		bindLabels();
		addTableContent();
	}
	
	private void bindPlayerOrderLists() {
		GameManager gameManager = GameManager.getInstance();
		ITurnManager turnManager = gameManager.getTurnManager(gameId);
		IGamePointManager pointManager = gameManager.getGamePointManager(gameId);
		
		listGameOverviewPoints.setItems(pointManager.getScoreList());
		listGameOverviewPlayerOrder.setItems(turnManager.getCurrentTurnPlayerOrder());
		listGameOverviewNextTurnPlayerOrder.setItems(turnManager.getNextTurnPlayerOrder());
	}
	
	private void bindLabels() {
		GameManager gameManager = GameManager.getInstance();
		IPointManager pointManager = gameManager.getPointManager(gameId, gameManager.getLocalPlayer());
		ITurnManager turnManager = gameManager.getTurnManager(gameId);
		
		labelGameOverviewPlayerPoints.textProperty().bind(Bindings.convert(pointManager.getPointsProperty()));
		labelGameOverviewPlayerPosition.textProperty().bind(Bindings.convert(pointManager.getPositionProperty()));
		labelGameOverviewPlayersTurn.textProperty().bind(Bindings.convert(turnManager.getCurrentPlayerProperty()));
	}
	
	private void addTableContent() {
		tableColumnGameOverviewPlayerName.setCellValueFactory(new PropertyValueFactory<PlayerInfo, String>("name"));
		tableColumnGameOverviewPoints.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("points"));
		tableColumnGameOverviewPlanets.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("planets"));
		tableColumnGameOverviewAlliances.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("alliances"));
		tableColumnGameOverviewResourcesC.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("resourcesC"));
		tableColumnGameOverviewResourcesFe.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("resourcesFe"));
		tableColumnGameOverviewResourcesSi.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("resourcesSi"));
		tableColumnGameOverviewResearchPoints.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("researchPoints"));
		tableColumnGameOverviewScientists.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("scientists"));
		tableColumnGameOverviewNumBuildings.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("buildings"));
		tableColumnGameOverviewColonies.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("colonies"));
		tableColumnGameOverviewMines.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("mines"));
		tableColumnGameOverviewTraidingPosts.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("tradingPosts"));
		tableColumnGameOverviewLaboratories.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("laboratories"));
		tableColumnGameOverviewGoverments.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("governments"));
		tableColumnGameOverviewCities.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("cities"));
		tableColumnGameOverviewResearchStations.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("researchCenters"));
		tableColumnGameOverviewDrones.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("drones"));
		tableColumnGameOverviewSpaceStations.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("spaceStations"));
		
		GameManager gameManager = GameManager.getInstance();
		tableGameOverview.setItems(gameManager.getPlayerInfoList(gameId));
	}
}
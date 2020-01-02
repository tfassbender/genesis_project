package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.jfabricationgames.genesis_project.game.CompleteResources;
import net.jfabricationgames.genesis_project.manager.GameManager;

public class CostOverviewPaneController implements Initializable {
	
	@FXML
	private TableView<BuildingInfo> tableBuildingCosts;
	@FXML
	private TableColumn<BuildingInfo, Integer> tableColumnBuildingCostsId;
	@FXML
	private TableColumn<BuildingInfo, String> tableColumnBuildingCostsBuilding;
	@FXML
	private TableColumn<BuildingInfo, String> tableColumnBuildingCostsPlanet1;
	@FXML
	private TableColumn<BuildingInfo, String> tableColumnBuildingCostsPlanet2;
	@FXML
	private TableColumn<BuildingInfo, String> tableColumnBuildingCostsPlanet3;
	@FXML
	private TableColumn<BuildingInfo, String> tableColumnBuildingCostsPlanet4;
	@FXML
	private TableColumn<BuildingInfo, String> tableColumnBuildingCostsNeighboursSelf;
	@FXML
	private TableColumn<BuildingInfo, String> tableColumnBuildingCostsNeighboursOther;
	
	@FXML
	private TableView<BuildingInfo> tableBuildingEarnings;
	@FXML
	private TableColumn<BuildingInfo, Integer> tableColumnBuildingEarningsId;
	@FXML
	private TableColumn<BuildingInfo, String> tableColumnBuildingEarningsBuilding;
	@FXML
	private TableColumn<BuildingInfo, String> tableColumnBuildingEarningsPrimeResources;
	@FXML
	private TableColumn<BuildingInfo, String> tableColumnBuildingEarningsSekResources;
	@FXML
	private TableColumn<BuildingInfo, String> tableColumnBuildingEarningsTertResources;
	@FXML
	private TableColumn<BuildingInfo, String> tableColumnBuildingEarningsResearchPoints;
	@FXML
	private TableColumn<BuildingInfo, String> tableColumnBuildingEarningsScientists;
	@FXML
	private TableColumn<BuildingInfo, String> tableColumnBuildingEarningsDefence;
	
	@FXML
	private Label labelBuildingEarningsCarbon;
	@FXML
	private Label labelBuildingEarningsSilicium;
	@FXML
	private Label labelBuildingEarningsIron;
	@FXML
	private Label labelBuildingEarningsResearchPoints;
	@FXML
	private Label labelBuildingEarningsScientists;
	
	private int gameId;
	
	public CostOverviewPaneController(int gameId) {
		this.gameId = gameId;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addTableContent();
		bindTotalEarningLabels();
	}
	
	public void updateAll() {
		addTableContent();
		bindTotalEarningLabels();
	}
	
	private void addTableContent() {
		ObservableList<BuildingInfo> buildingInfos = BuildingInfo.forAllBuildings();
		
		tableColumnBuildingCostsId.setCellValueFactory(new PropertyValueFactory<BuildingInfo, Integer>("id"));
		tableColumnBuildingCostsBuilding.setCellValueFactory(new PropertyValueFactory<BuildingInfo, String>("building"));
		tableColumnBuildingCostsPlanet1.setCellValueFactory(new PropertyValueFactory<BuildingInfo, String>("planetClass1"));
		tableColumnBuildingCostsPlanet2.setCellValueFactory(new PropertyValueFactory<BuildingInfo, String>("planetClass2"));
		tableColumnBuildingCostsPlanet3.setCellValueFactory(new PropertyValueFactory<BuildingInfo, String>("planetClass3"));
		tableColumnBuildingCostsPlanet4.setCellValueFactory(new PropertyValueFactory<BuildingInfo, String>("planetClass4"));
		tableColumnBuildingCostsNeighboursSelf.setCellValueFactory(new PropertyValueFactory<BuildingInfo, String>("neighboursSelf"));
		tableColumnBuildingCostsNeighboursOther.setCellValueFactory(new PropertyValueFactory<BuildingInfo, String>("neighboursOtherPlayers"));
		tableBuildingCosts.setItems(buildingInfos);
		
		tableColumnBuildingEarningsId.setCellValueFactory(new PropertyValueFactory<BuildingInfo, Integer>("id"));
		tableColumnBuildingEarningsBuilding.setCellValueFactory(new PropertyValueFactory<BuildingInfo, String>("building"));
		tableColumnBuildingEarningsPrimeResources.setCellValueFactory(new PropertyValueFactory<BuildingInfo, String>("receivingPrimaryResources"));
		tableColumnBuildingEarningsSekResources.setCellValueFactory(new PropertyValueFactory<BuildingInfo, String>("receivingSecundaryResources"));
		tableColumnBuildingEarningsTertResources.setCellValueFactory(new PropertyValueFactory<BuildingInfo, String>("receivingTertiaryResources"));
		tableColumnBuildingEarningsResearchPoints.setCellValueFactory(new PropertyValueFactory<BuildingInfo, String>("receivingResearchPoints"));
		tableColumnBuildingEarningsScientists.setCellValueFactory(new PropertyValueFactory<BuildingInfo, String>("receivingScientists"));
		tableColumnBuildingEarningsDefence.setCellValueFactory(new PropertyValueFactory<BuildingInfo, String>("defence"));
		tableBuildingEarnings.setItems(buildingInfos);
	}
	
	private void bindTotalEarningLabels() {
		ListChangeListener<PlayerInfo> changeListener = (c) -> updateTotalEarningLabels();
		
		GameManager gameManager = GameManager.getInstance();
		gameManager.getPlayerInfoList(gameId).addListener(changeListener);
	}
	
	private void updateTotalEarningLabels() {
		GameManager gameManager = GameManager.getInstance();
		CompleteResources receivingResources = gameManager.getBuildingManager(gameId, gameManager.getLocalPlayer()).getNextTurnsStartingResources();
		
		labelBuildingEarningsCarbon.setText(Integer.toString(receivingResources.getResourcesC()));
		labelBuildingEarningsSilicium.setText(Integer.toString(receivingResources.getResourcesSi()));
		labelBuildingEarningsIron.setText(Integer.toString(receivingResources.getResourcesFe()));
		labelBuildingEarningsResearchPoints.setText(Integer.toString(receivingResources.getResearchPoints()));
		labelBuildingEarningsScientists.setText(Integer.toString(receivingResources.getScientists()));
	}
}
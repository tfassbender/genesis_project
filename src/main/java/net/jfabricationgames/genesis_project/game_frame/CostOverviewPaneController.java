package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CostOverviewPaneController implements Initializable {
	
	@FXML
	private TableView<?> tableBuildingCosts;
	@FXML
	private TableColumn<?, ?> tableColumnBuildingCostsId;
	@FXML
	private TableColumn<?, ?> tableColumnBuildingCostsBuilding;
	@FXML
	private TableColumn<?, ?> tableColumnBuildingCostsPlanet1;
	@FXML
	private TableColumn<?, ?> tableColumnBuildingCostsPlanet2;
	@FXML
	private TableColumn<?, ?> tableColumnBuildingCostsPlanet3;
	@FXML
	private TableColumn<?, ?> tableColumnBuildingCostsPlanet4;
	@FXML
	private TableColumn<?, ?> tableColumnBuildingCostsNeighboursSelf;
	@FXML
	private TableColumn<?, ?> tableColumnBuildingCostsNeighboursOther;
	
	@FXML
	private TableView<?> tableBuildingEarnings;
	@FXML
	private TableColumn<?, ?> tableColumnBuildingEarningsId;
	@FXML
	private TableColumn<?, ?> tableColumnBuildingEarningsBuilding;
	@FXML
	private TableColumn<?, ?> tableColumnBuildingEarningsPrimeResources;
	@FXML
	private TableColumn<?, ?> tableColumnBuildingEarningsSekResources;
	@FXML
	private TableColumn<?, ?> tableColumnBuildingEarningsTertResources;
	@FXML
	private TableColumn<?, ?> tableColumnBuildingEarningsResearchPoints;
	@FXML
	private TableColumn<?, ?> tableColumnBuildingEarningsScientists;
	@FXML
	private TableColumn<?, ?> tableColumnBuildingEarningsDefence;
	@FXML
	private TableColumn<?, ?> tableColumnBuildingEarningsBuilt;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
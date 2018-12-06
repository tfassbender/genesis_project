package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GameOverviewPaneController implements Initializable {
	
	@FXML
	private ListView<?> listGameOverviewPoints;
	@FXML
	private Label labelGameOverviewPlayerPoints;
	@FXML
	private Label labelGameOverviewPlayerPosition;
	@FXML
	private ListView<?> listGameOverviewPlayerOrder;
	@FXML
	private Label labelGameOverviewPlayersTurn;
	
	@FXML
	private TableView<?> tableGameOverview;
	@FXML
	private TableColumn<?, ?> tableColumnGameOverviewPlayerName;
	@FXML
	private TableColumn<?, ?> tableColumnGameOverviewPoints;
	@FXML
	private TableColumn<?, ?> tableColumnGameOverviewPlanets;
	@FXML
	private TableColumn<?, ?> tableColumnGameOverviewAlliances;
	@FXML
	private TableColumn<?, ?> tableColumnGameOverviewResourcesC;
	@FXML
	private TableColumn<?, ?> tableColumnGameOverviewResourcesFe;
	@FXML
	private TableColumn<?, ?> tableColumnGameOverviewResourcesSi;
	@FXML
	private TableColumn<?, ?> tableColumnGameOverviewResearchPoints;
	@FXML
	private TableColumn<?, ?> tableColumnGameOverviewScientists;
	@FXML
	private TableColumn<?, ?> tableColumnGameOverviewNumBuildings;
	@FXML
	private TableColumn<?, ?> tableColumnGameOverviewColonies;
	@FXML
	private TableColumn<?, ?> tableColumnGameOverviewMines;
	@FXML
	private TableColumn<?, ?> tableColumnGameOverviewTraidingPosts;
	@FXML
	private TableColumn<?, ?> tableColumnGameOverviewLaboratories;
	@FXML
	private TableColumn<?, ?> tableColumnGameOverviewGoverments;
	@FXML
	private TableColumn<?, ?> tableColumnGameOverviewCities;
	@FXML
	private TableColumn<?, ?> tableColumnGameOverviewResearchStations;
	@FXML
	private TableColumn<?, ?> tableColumnGameOverviewDrones;
	@FXML
	private TableColumn<?, ?> tableColumnGameOverviewSpaceStations;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
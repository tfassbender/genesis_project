package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;

public class PlaningToolPaneController implements Initializable {
	
	@FXML
	private Spinner<?> planingToolStartResourcesPrimary;
	@FXML
	private Spinner<?> planingToolStartResourcesSecundary;
	@FXML
	private Spinner<?> planingToolStartResourcesTertiary;
	@FXML
	private Spinner<?> planingToolStartResourcesResearch;
	@FXML
	private Spinner<?> planingToolStartResourcesScientists;
	@FXML
	private Spinner<?> planingToolStartResourcesPoints;
	@FXML
	private Spinner<?> planingToolStartResourcesFTL;
	@FXML
	private Button planingToolStartResourcesResetAll;
	
	@FXML
	private VBox planingToolPlanedSteps;
	@FXML
	private Label planingToolResourcesLeftPrimary;
	@FXML
	private Label planingToolResourcesLeftSecundary;
	@FXML
	private Label planingToolResourcesLeftTertiary;
	@FXML
	private Label planingToolResourcesLeftResearch;
	@FXML
	private Label planingToolResourcesLeftScientists;
	@FXML
	private Label planingToolResourcesLeftPoints;
	@FXML
	private Label planingToolResourcesLeftFTL;
	@FXML
	private Button planingToolAddStep;
	@FXML
	private Button planingToolDeleteAllSteps;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
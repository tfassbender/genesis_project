package net.jfabricationgames.genesis_project.game_frame;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;
import net.jfabricationgames.genesis_project.manager.GameManager;
import net.jfabricationgames.genesis_project.manager.IPointManager;
import net.jfabricationgames.genesis_project.manager.IResourceManager;

public class PlaningToolPaneController implements Initializable {
	
	private final Logger LOGGER = LogManager.getLogger(getClass());
	
	@FXML
	private Spinner<Integer> planingToolStartResourcesPrimary;
	@FXML
	private Spinner<Integer> planingToolStartResourcesSecundary;
	@FXML
	private Spinner<Integer> planingToolStartResourcesTertiary;
	@FXML
	private Spinner<Integer> planingToolStartResourcesResearch;
	@FXML
	private Spinner<Integer> planingToolStartResourcesScientists;
	@FXML
	private Spinner<Integer> planingToolStartResourcesPoints;
	@FXML
	private Spinner<Integer> planingToolStartResourcesFTL;
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
	
	private List<PlaningToolStep> planingSteps;
	
	private int gameId;
	
	public PlaningToolPaneController(int gameId) {
		this.gameId = gameId;
	}

	public void updateAll() {
		//changes here have to be only made by the player and not by updated games
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		planingSteps = new ArrayList<PlaningToolStep>();
		initSpinnersWithRange();
		initSpinnersWithEvents();
		initButtonControlls();
		resetStartResourcesToCurrentResources();
		resetPlaningSteps();
		calculateResourcesLeft();
	}
	
	private void resetStartResourcesToCurrentResources() {
		GameManager gameManager = GameManager.getInstance();
		IResourceManager resourceManager = gameManager.getResourceManager(gameId, gameManager.getLocalPlayer());
		IPointManager pointManager = gameManager.getPointManager(gameId, gameManager.getLocalPlayer());
		
		planingToolStartResourcesPrimary.getValueFactory().setValue(Integer.valueOf(resourceManager.getResourcesPrimary()));
		planingToolStartResourcesSecundary.getValueFactory().setValue(Integer.valueOf(resourceManager.getResourcesSecundary()));
		planingToolStartResourcesTertiary.getValueFactory().setValue(Integer.valueOf(resourceManager.getResourcesTertiary()));
		planingToolStartResourcesResearch.getValueFactory().setValue(Integer.valueOf(resourceManager.getResearchPoints()));
		planingToolStartResourcesScientists.getValueFactory().setValue(Integer.valueOf(resourceManager.getScientists()));
		planingToolStartResourcesFTL.getValueFactory().setValue(Integer.valueOf(resourceManager.getFTL()));
		
		planingToolStartResourcesPoints.getValueFactory().setValue(Integer.valueOf(pointManager.getPoints()));
	}
	
	private void resetPlaningSteps() {
		planingSteps.clear();
		addPlaningStep(1);
	}
	
	private void updatePlaningSteps() {
		planingToolPlanedSteps.getChildren().clear();
		for (PlaningToolStep step : planingSteps) {
			FXMLLoader stepPaneLoader = getPlaningStepPaneLoader();
			PlaningToolStepPaneController controller = new PlaningToolStepPaneController(step, this);
			stepPaneLoader.setController(controller);
			try {
				Node stepPane = stepPaneLoader.load();
				planingToolPlanedSteps.getChildren().add(stepPane);
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
				LOGGER.warn("Couldn't update the planing steps", ioe);
			}
		}
		calculateResourcesLeft();
	}
	
	private void addPlaningStep(int steps) {
		for (int i = 0; i < steps; i++) {
			planingSteps.add(new PlaningToolStep());
		}
		updatePlaningSteps();
	}
	
	private FXMLLoader getPlaningStepPaneLoader() {
		final String PlaningToolStepPanePath = "PlaningToolStepPane.fxml";
		URL fxmlUrl = getClass().getResource(PlaningToolStepPanePath);
		FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
		return fxmlLoader;
	}
	
	public void calculateResourcesLeft() {
		int primaryResources = planingToolStartResourcesPrimary.getValue();
		int secundaryResources = planingToolStartResourcesSecundary.getValue();
		int tertiaryResources = planingToolStartResourcesTertiary.getValue();
		int researchPoints = planingToolStartResourcesResearch.getValue();
		int scientists = planingToolStartResourcesScientists.getValue();
		int points = planingToolStartResourcesPoints.getValue();
		int ftl = planingToolStartResourcesFTL.getValue();
		
		for (PlaningToolStep step : planingSteps) {
			primaryResources += step.getPrimaryResourceProperty().get();
			secundaryResources += step.getSecundaryResourceProperty().get();
			tertiaryResources += step.getTertiaryResourceProperty().get();
			researchPoints += step.getResearchProperty().get();
			scientists += step.getScientistsProperty().get();
			points += step.getPointsProperty().get();
			ftl += step.getFtlProperty().get();
		}
		
		planingToolResourcesLeftPrimary.setText(Integer.toString(primaryResources));
		planingToolResourcesLeftSecundary.setText(Integer.toString(secundaryResources));
		planingToolResourcesLeftTertiary.setText(Integer.toString(tertiaryResources));
		planingToolResourcesLeftResearch.setText(Integer.toString(researchPoints));
		planingToolResourcesLeftScientists.setText(Integer.toString(scientists));
		planingToolResourcesLeftPoints.setText(Integer.toString(points));
		planingToolResourcesLeftFTL.setText(Integer.toString(ftl));
	}
	
	private void initSpinnersWithRange() {
		final int minimumValue = 0;
		final int minimumValueFTL = 1;
		final int maximumValue = 99;
		final int maximumValuePoints = 300;
		final int maximumValueFTL = 4;
		final int initialValue = 0;
		
		//initialize with value factories
		planingToolStartResourcesPrimary
				.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(minimumValue, maximumValue, initialValue));
		planingToolStartResourcesSecundary
				.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(minimumValue, maximumValue, initialValue));
		planingToolStartResourcesTertiary
				.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(minimumValue, maximumValue, initialValue));
		planingToolStartResourcesResearch
				.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(minimumValue, maximumValue, initialValue));
		planingToolStartResourcesScientists
				.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(minimumValue, maximumValue, initialValue));
		planingToolStartResourcesPoints
				.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(minimumValue, maximumValuePoints, initialValue));
		planingToolStartResourcesFTL
				.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(minimumValueFTL, maximumValueFTL, initialValue));
		
		//make the spinners editable (for text input)
		planingToolStartResourcesPrimary.setEditable(true);
		planingToolStartResourcesSecundary.setEditable(true);
		planingToolStartResourcesTertiary.setEditable(true);
		planingToolStartResourcesResearch.setEditable(true);
		planingToolStartResourcesScientists.setEditable(true);
		planingToolStartResourcesPoints.setEditable(true);
		planingToolStartResourcesFTL.setEditable(true);
	}
	
	private void initSpinnersWithEvents() {
		planingToolStartResourcesPrimary.valueProperty().addListener((observer, oldVal, newVal) -> calculateResourcesLeft());
		planingToolStartResourcesSecundary.valueProperty().addListener((observer, oldVal, newVal) -> calculateResourcesLeft());
		planingToolStartResourcesTertiary.valueProperty().addListener((observer, oldVal, newVal) -> calculateResourcesLeft());
		planingToolStartResourcesResearch.valueProperty().addListener((observer, oldVal, newVal) -> calculateResourcesLeft());
		planingToolStartResourcesScientists.valueProperty().addListener((observer, oldVal, newVal) -> calculateResourcesLeft());
		planingToolStartResourcesPoints.valueProperty().addListener((observer, oldVal, newVal) -> calculateResourcesLeft());
		planingToolStartResourcesFTL.valueProperty().addListener((observer, oldVal, newVal) -> calculateResourcesLeft());
	}
	
	private void initButtonControlls() {
		planingToolStartResourcesResetAll.setOnAction((e) -> resetStartResourcesToCurrentResources());
		planingToolAddStep.setOnAction((e) -> addPlaningStep(1));
		planingToolDeleteAllSteps.setOnAction((e) -> resetPlaningSteps());
	}
	
	public int getPlaningStepIndex(PlaningToolStep step) {
		return planingSteps.indexOf(step);
	}
	public int getPlaningStepListSize() {
		return planingSteps.size();
	}
	public void deletePlaningStep(PlaningToolStep step) {
		planingSteps.remove(step);
		updatePlaningSteps();
	}
	public void addPlaningStep(PlaningToolStep step, int index) {
		planingSteps.add(index, step);
		updatePlaningSteps();
	}
}
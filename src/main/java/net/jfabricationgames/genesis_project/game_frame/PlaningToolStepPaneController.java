package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class PlaningToolStepPaneController implements Initializable {
	
	private PlaningToolStep step;
	private PlaningToolPaneController superController;
	
	@FXML
	private TextField textFieldPlaningToolStepDescription;
	@FXML
	private Spinner<Integer> spinnerPlaningToolStepPrimary;
	@FXML
	private Spinner<Integer> spinnerPlaningToolStepSecundary;
	@FXML
	private Spinner<Integer> spinnerPlaningToolStepTertiary;
	@FXML
	private Spinner<Integer> spinnerPlaningToolStepPoints;
	@FXML
	private Spinner<Integer> spinnerPlaningToolStepResearch;
	@FXML
	private Spinner<Integer> spinnerPlaningToolStepScientists;
	@FXML
	private Spinner<Integer> spinnerPlaningToolStepFTL;
	
	@FXML
	private Button buttonPlaningToolStepUp;
	@FXML
	private Button buttonPlaningToolStepDown;
	@FXML
	private Button buttonPlaningToolStepClear;
	@FXML
	private Button buttonPlaningToolStepDelete;
	
	public PlaningToolStepPaneController(PlaningToolStep step, PlaningToolPaneController superController) {
		this.step = step;
		this.superController = superController;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		textFieldPlaningToolStepDescription.setText(step.getDescriptionProperty().get());
		initSpinnersWithRange();
		initSpinnersWithEvents();
		bindSpinnerProperties();
		initButtonControlls();
	}
	
	private void bindSpinnerProperties() {
		step.getDescriptionProperty().bind(textFieldPlaningToolStepDescription.textProperty());
		step.getPrimaryResourceProperty().bind(spinnerPlaningToolStepPrimary.valueProperty());
		step.getSecundaryResourceProperty().bind(spinnerPlaningToolStepSecundary.valueProperty());
		step.getTertiaryResourceProperty().bind(spinnerPlaningToolStepTertiary.valueProperty());
		step.getPointsProperty().bind(spinnerPlaningToolStepPoints.valueProperty());
		step.getResearchProperty().bind(spinnerPlaningToolStepResearch.valueProperty());
		step.getScientistsProperty().bind(spinnerPlaningToolStepScientists.valueProperty());
		step.getFtlProperty().bind(spinnerPlaningToolStepFTL.valueProperty());
	}
	
	private void initSpinnersWithRange() {
		final int minimumValue = -99;
		final int minimumValueFTL = -10;
		final int maximumValue = 99;
		final int maximumValueFTL = 10;
		
		spinnerPlaningToolStepPrimary.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(minimumValue, maximumValue, step.getPrimaryResourceProperty().get()));
		spinnerPlaningToolStepSecundary.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(minimumValue, maximumValue, step.getSecundaryResourceProperty().get()));
		spinnerPlaningToolStepTertiary.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(minimumValue, maximumValue, step.getTertiaryResourceProperty().get()));
		spinnerPlaningToolStepPoints.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(minimumValue, maximumValue, step.getPointsProperty().get()));
		spinnerPlaningToolStepResearch.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(minimumValue, maximumValue, step.getResearchProperty().get()));
		spinnerPlaningToolStepScientists.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(minimumValue, maximumValue, step.getScientistsProperty().get()));
		spinnerPlaningToolStepFTL.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(minimumValueFTL, maximumValueFTL, step.getFtlProperty().get()));
		
		spinnerPlaningToolStepPrimary.setEditable(true);
		spinnerPlaningToolStepSecundary.setEditable(true);
		spinnerPlaningToolStepTertiary.setEditable(true);
		spinnerPlaningToolStepPoints.setEditable(true);
		spinnerPlaningToolStepResearch.setEditable(true);
		spinnerPlaningToolStepScientists.setEditable(true);
		spinnerPlaningToolStepFTL.setEditable(true);
	}
	
	private void initSpinnersWithEvents() {
		spinnerPlaningToolStepPrimary.valueProperty().addListener((observer, oldVal, newVal) -> superController.calculateResourcesLeft());
		spinnerPlaningToolStepSecundary.valueProperty().addListener((observer, oldVal, newVal) -> superController.calculateResourcesLeft());
		spinnerPlaningToolStepTertiary.valueProperty().addListener((observer, oldVal, newVal) -> superController.calculateResourcesLeft());
		spinnerPlaningToolStepResearch.valueProperty().addListener((observer, oldVal, newVal) -> superController.calculateResourcesLeft());
		spinnerPlaningToolStepScientists.valueProperty().addListener((observer, oldVal, newVal) -> superController.calculateResourcesLeft());
		spinnerPlaningToolStepPoints.valueProperty().addListener((observer, oldVal, newVal) -> superController.calculateResourcesLeft());
		spinnerPlaningToolStepFTL.valueProperty().addListener((observer, oldVal, newVal) -> superController.calculateResourcesLeft());
	}
	
	private void initButtonControlls() {
		buttonPlaningToolStepUp.setOnAction((e) -> moveUpStep());
		buttonPlaningToolStepDown.setOnAction((e) -> moveDownStep());
		buttonPlaningToolStepClear.setOnAction((e) -> resetValues());
		buttonPlaningToolStepDelete.setOnAction((e) -> deleteStep());
	}
	
	private void moveUpStep() {
		int index = superController.getPlaningStepIndex(step);
		int listSize = superController.getPlaningStepListSize();
		superController.deletePlaningStep(step);
		
		//set new position
		index--;
		index = Math.max(0, index);
		index = Math.min(listSize - 1, index);
		
		superController.addPlaningStep(step, index);
	}
	
	private void moveDownStep() {
		int index = superController.getPlaningStepIndex(step);
		int listSize = superController.getPlaningStepListSize();
		superController.deletePlaningStep(step);
		
		//set new position
		index++;
		index = Math.max(0, index);
		index = Math.min(listSize - 1, index);
		
		superController.addPlaningStep(step, index);
	}
	
	private void resetValues() {
		spinnerPlaningToolStepPrimary.getValueFactory().setValue(0);
		spinnerPlaningToolStepSecundary.getValueFactory().setValue(0);
		spinnerPlaningToolStepTertiary.getValueFactory().setValue(0);
		spinnerPlaningToolStepResearch.getValueFactory().setValue(0);
		spinnerPlaningToolStepScientists.getValueFactory().setValue(0);
		spinnerPlaningToolStepPoints.getValueFactory().setValue(0);
		spinnerPlaningToolStepFTL.getValueFactory().setValue(0);
	}
	
	private void deleteStep() {
		superController.deletePlaningStep(step);
	}
}
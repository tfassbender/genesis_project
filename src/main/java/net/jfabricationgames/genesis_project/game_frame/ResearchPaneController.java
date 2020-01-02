package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import net.jfabricationgames.genesis_project.game.Constants;
import net.jfabricationgames.genesis_project.game.ResearchArea;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Resource;
import net.jfabricationgames.genesis_project.manager.GameManager;
import net.jfabricationgames.genesis_project.manager.IResearchManager;
import net.jfabricationgames.genesis_project.manager.IResourceManager;

public class ResearchPaneController implements Initializable {
	
	@FXML
	private ImageView imageViewResearchBackground;
	
	@FXML
	private Pane panelResearchCoverWeapon5;
	@FXML
	private Pane panelResearchCoverWeapon4;
	@FXML
	private Pane panelResearchCoverWeapon3;
	@FXML
	private Pane panelResearchCoverWeapon2;
	@FXML
	private Pane panelResearchCoverWeapon1;
	@FXML
	private Pane panelResearchCoverWeapon10;
	@FXML
	private Pane panelResearchCoverWeapon9;
	@FXML
	private Pane panelResearchCoverWeapon8;
	@FXML
	private Pane panelResearchCoverWeapon7;
	@FXML
	private Pane panelResearchCoverWeapon6;
	@FXML
	private Pane panelResearchCoverWeapon0;
	@FXML
	private Pane panelResearchCoverMines6;
	@FXML
	private Pane panelResearchCoverEconomy6;
	@FXML
	private Pane panelResearchCoverFtl6;
	@FXML
	private Pane panelResearchCoverResearch6;
	@FXML
	private Pane panelResearchCoverMilitary6;
	@FXML
	private Pane panelResearchCoverMines5;
	@FXML
	private Pane panelResearchCoverEconomy5;
	@FXML
	private Pane panelResearchCoverFtl5;
	@FXML
	private Pane panelResearchCoverResearch5;
	@FXML
	private Pane panelResearchCoverMilitary5;
	@FXML
	private Pane panelResearchCoverMines4;
	@FXML
	private Pane panelResearchCoverEconomy4;
	@FXML
	private Pane panelResearchCoverFtl4;
	@FXML
	private Pane panelResearchCoverResearch4;
	@FXML
	private Pane panelResearchCoverMilitary4;
	@FXML
	private Pane panelResearchCoverMines3;
	@FXML
	private Pane panelResearchCoverEconomy3;
	@FXML
	private Pane panelResearchCoverFtl3;
	@FXML
	private Pane panelResearchCoverResearch3;
	@FXML
	private Pane panelResearchCoverMilitary3;
	@FXML
	private Pane panelResearchCoverMines2;
	@FXML
	private Pane panelResearchCoverEconomy2;
	@FXML
	private Pane panelResearchCoverFtl2;
	@FXML
	private Pane panelResearchCoverResearch2;
	@FXML
	private Pane panelResearchCoverMilitary2;
	@FXML
	private Pane panelResearchCoverMines1;
	@FXML
	private Pane panelResearchCoverEconomy1;
	@FXML
	private Pane panelResearchCoverFtl1;
	@FXML
	private Pane panelResearchCoverResearch1;
	@FXML
	private Pane panelResearchCoverMilitary1;
	@FXML
	private Pane panelResearchCoverMines0;
	@FXML
	private Pane panelResearchCoverEconomy0;
	@FXML
	private Pane panelResearchCoverFtl0;
	@FXML
	private Pane panelResearchCoverResearch0;
	@FXML
	private Pane panelResearchCoverMilitary0;
	
	@FXML
	private Spinner<Integer> spinnerResearchAddCarbon;
	@FXML
	private Spinner<Integer> spinnerResearchAddIron;
	@FXML
	private Spinner<Integer> spinnerResearchAddSilicium;
	@FXML
	private Spinner<Integer> spinnerResearchAddScientists;
	
	@FXML
	private ComboBox<ResearchArea> comboBoxResearchAddResourcesSelectArea;
	
	@FXML
	private Button buttonResearchAddResources;
	
	@FXML
	private Label labelResearchCarbonNeeded;
	@FXML
	private Label labelResearchSiliciumNeeded;
	@FXML
	private Label labelResearchIronNeeded;
	@FXML
	private Label labelResearchScientistsNeeded;
	
	@FXML
	private Label labelAccessibleResearchState;
	@FXML
	private Label labelResearchStateMine;
	@FXML
	private Label labelResearchStateEconomy;
	@FXML
	private Label labelResearchStateFtl;
	@FXML
	private Label labelResearchStateResearch;
	@FXML
	private Label labelResearchStateMilitary;
	@FXML
	private Label labelResearchStateWeapon;
	
	@FXML
	private Button buttonResearchPromotionMines;
	@FXML
	private Button buttonResearchPromotionEconomy;
	@FXML
	private Button buttonResearchPromotionFtl;
	@FXML
	private Button buttonResearchPromotionResearch;
	@FXML
	private Button buttonResearchPromotionMilitary;
	@FXML
	private Button buttonResearchPromotionWeapon;
	
	@FXML
	private Label labelResearchResourcesMines;
	@FXML
	private Label labelResearchResourcesEconomy;
	@FXML
	private Label labelResearchResourcesFtl;
	@FXML
	private Label labelResearchResourcesResearch;
	@FXML
	private Label labelResearchResourcesMilitary;
	@FXML
	private Label labelResearchResourcesWeapon;
	
	@FXML
	private Label labelResearchPossibleStateMines;
	@FXML
	private Label labelResearchPossibleStateEconomy;
	@FXML
	private Label labelResearchPossibleStateFtl;
	@FXML
	private Label labelResearchPossibleStateResearch;
	@FXML
	private Label labelResearchPossibleStateMilitary;
	@FXML
	private Label labelResearchPossibleStateWeapon;
	
	private int gameId;
	
	public ResearchPaneController(int gameId) {
		this.gameId = gameId;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GuiUtils.loadImageToView("research/research_panel.png", true, imageViewResearchBackground);
		
		bindStateLabels();
		bindIncreaseStateButtonsDisabledProperties();
		bindResourcesNeededLabels();
		bindReachableStateLabels();
		
		addResourceAddingListeners();
		initializeResourceSpinners();
		initializeResearchAreaComboBox();
		
		//TODO add research state descriptions
	}
	
	public void updateAll() {
		unbindAll();
		
		bindStateLabels();
		bindIncreaseStateButtonsDisabledProperties();
		bindResourcesNeededLabels();
		bindReachableStateLabels();
		
		addResourceAddingListeners();
		updateResourceAdding();
		updateAddResourcesButtonState();
	}
	
	private void unbindAll() {
		labelResearchStateMine.textProperty().unbind();
		labelResearchStateEconomy.textProperty().unbind();
		labelResearchStateFtl.textProperty().unbind();
		labelResearchStateResearch.textProperty().unbind();
		labelResearchStateMilitary.textProperty().unbind();
		
		labelResearchStateWeapon.textProperty().unbind();
		buttonResearchPromotionMines.disableProperty().unbind();
		buttonResearchPromotionEconomy.disableProperty().unbind();
		buttonResearchPromotionFtl.disableProperty().unbind();
		buttonResearchPromotionResearch.disableProperty().unbind();
		buttonResearchPromotionMilitary.disableProperty().unbind();
		buttonResearchPromotionWeapon.disableProperty().unbind();
		labelResearchResourcesMines.textProperty().unbind();
		labelResearchResourcesEconomy.textProperty().unbind();
		labelResearchResourcesFtl.textProperty().unbind();
		labelResearchResourcesResearch.textProperty().unbind();
		labelResearchResourcesMilitary.textProperty().unbind();
		labelResearchResourcesWeapon.textProperty().unbind();
		labelResearchPossibleStateMines.textProperty().unbind();
		labelResearchPossibleStateEconomy.textProperty().unbind();
		labelResearchPossibleStateFtl.textProperty().unbind();
		labelResearchPossibleStateResearch.textProperty().unbind();
		labelResearchPossibleStateMilitary.textProperty().unbind();
		labelResearchPossibleStateWeapon.textProperty().unbind();
	}
	
	private void bindStateLabels() {
		GameManager gameManager = GameManager.getInstance();
		IResearchManager researchManager = gameManager.getResearchManager(gameId, gameManager.getLocalPlayer());
		labelResearchStateMine.textProperty().bind(Bindings.convert(researchManager.getStateProperty(ResearchArea.MINES)));
		labelResearchStateEconomy.textProperty().bind(Bindings.convert(researchManager.getStateProperty(ResearchArea.ECONOMY)));
		labelResearchStateFtl.textProperty().bind(Bindings.convert(researchManager.getStateProperty(ResearchArea.FTL)));
		labelResearchStateResearch.textProperty().bind(Bindings.convert(researchManager.getStateProperty(ResearchArea.RESEARCH)));
		labelResearchStateMilitary.textProperty().bind(Bindings.convert(researchManager.getStateProperty(ResearchArea.MILITARY)));
		
		labelResearchStateWeapon.textProperty().bind(Bindings.convert(gameManager.getResearchManager(gameId).getStateProperty(ResearchArea.WEAPON)));
	}
	
	private void bindIncreaseStateButtonsDisabledProperties() {
		GameManager gameManager = GameManager.getInstance();
		IResourceManager resourceManager = gameManager.getResourceManager(gameId, gameManager.getLocalPlayer());
		BooleanBinding researchPointsAvailable = resourceManager.getResearchPointsProperty()
				.greaterThanOrEqualTo(Constants.getInstance().RESEARCH_POINTS_FOR_STATE_INCREASE);
		
		buttonResearchPromotionMines.disableProperty().bind(researchPointsAvailable.and(stateAccessible(ResearchArea.MINES)).not());
		buttonResearchPromotionEconomy.disableProperty().bind(researchPointsAvailable.and(stateAccessible(ResearchArea.ECONOMY)).not());
		buttonResearchPromotionFtl.disableProperty().bind(researchPointsAvailable.and(stateAccessible(ResearchArea.FTL)).not());
		buttonResearchPromotionResearch.disableProperty().bind(researchPointsAvailable.and(stateAccessible(ResearchArea.RESEARCH)).not());
		buttonResearchPromotionMilitary.disableProperty().bind(researchPointsAvailable.and(stateAccessible(ResearchArea.MILITARY)).not());
		buttonResearchPromotionWeapon.disableProperty().bind(researchPointsAvailable.and(stateAccessible(ResearchArea.WEAPON)).not());
	}
	
	private BooleanBinding stateAccessible(ResearchArea area) {
		GameManager gameManager = GameManager.getInstance();
		IResearchManager researchManager = gameManager.getResearchManager(gameId, gameManager.getLocalPlayer());
		BooleanBinding promotionEnabled = gameManager.getResearchManager(gameId).getMaxReachableStateProperty(ResearchArea.MINES)
				.greaterThanOrEqualTo(researchManager.getStateProperty(ResearchArea.MINES).add(1));
		
		return promotionEnabled;
	}
	
	private void bindResourcesNeededLabels() {
		GameManager gameManager = GameManager.getInstance();
		IResearchManager researchManager = gameManager.getResearchManager(gameId, gameManager.getLocalPlayer());
		labelResearchResourcesMines.textProperty().bind(new SimpleStringProperty(
				researchManager.getResearchResourcesNeededLeftProperties(ResearchArea.MINES).getValue().getShortenedDescription()));
		labelResearchResourcesEconomy.textProperty().bind(new SimpleStringProperty(
				researchManager.getResearchResourcesNeededLeftProperties(ResearchArea.ECONOMY).getValue().getShortenedDescription()));
		labelResearchResourcesFtl.textProperty().bind(new SimpleStringProperty(
				researchManager.getResearchResourcesNeededLeftProperties(ResearchArea.FTL).getValue().getShortenedDescription()));
		labelResearchResourcesResearch.textProperty().bind(new SimpleStringProperty(
				researchManager.getResearchResourcesNeededLeftProperties(ResearchArea.RESEARCH).getValue().getShortenedDescription()));
		labelResearchResourcesMilitary.textProperty().bind(new SimpleStringProperty(
				researchManager.getResearchResourcesNeededLeftProperties(ResearchArea.MILITARY).getValue().getShortenedDescription()));
		labelResearchResourcesWeapon.textProperty().bind(new SimpleStringProperty(
				researchManager.getResearchResourcesNeededLeftProperties(ResearchArea.WEAPON).getValue().getShortenedDescription()));
	}
	
	private void bindReachableStateLabels() {
		GameManager gameManager = GameManager.getInstance();
		IResearchManager researchManager = gameManager.getResearchManager(gameId, gameManager.getLocalPlayer());
		labelResearchPossibleStateMines.textProperty().bind(Bindings.convert(researchManager.getMaxReachableStateProperty(ResearchArea.MINES)));
		labelResearchPossibleStateEconomy.textProperty().bind(Bindings.convert(researchManager.getMaxReachableStateProperty(ResearchArea.ECONOMY)));
		labelResearchPossibleStateFtl.textProperty().bind(Bindings.convert(researchManager.getMaxReachableStateProperty(ResearchArea.FTL)));
		labelResearchPossibleStateResearch.textProperty().bind(Bindings.convert(researchManager.getMaxReachableStateProperty(ResearchArea.RESEARCH)));
		labelResearchPossibleStateMilitary.textProperty().bind(Bindings.convert(researchManager.getMaxReachableStateProperty(ResearchArea.MILITARY)));
		labelResearchPossibleStateWeapon.textProperty().bind(Bindings.convert(researchManager.getMaxReachableStateProperty(ResearchArea.WEAPON)));
	}
	
	private void initializeResearchAreaComboBox() {
		comboBoxResearchAddResourcesSelectArea.getItems().addAll(ResearchArea.values());
		comboBoxResearchAddResourcesSelectArea.getSelectionModel().select(0);
	}
	
	private void initializeResourceSpinners() {
		SpinnerValueFactory<Integer> carbonSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0);
		SpinnerValueFactory<Integer> siliciumSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0);
		SpinnerValueFactory<Integer> ironSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0);
		SpinnerValueFactory<Integer> scientistsSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0);
		
		spinnerResearchAddCarbon.setValueFactory(carbonSpinnerValueFactory);
		spinnerResearchAddSilicium.setValueFactory(siliciumSpinnerValueFactory);
		spinnerResearchAddIron.setValueFactory(ironSpinnerValueFactory);
		spinnerResearchAddScientists.setValueFactory(scientistsSpinnerValueFactory);
		
		carbonSpinnerValueFactory.valueProperty().addListener((observer, oldVal, newVal) -> updateAddResourcesButtonState());
		siliciumSpinnerValueFactory.valueProperty().addListener((observer, oldVal, newVal) -> updateAddResourcesButtonState());
		ironSpinnerValueFactory.valueProperty().addListener((observer, oldVal, newVal) -> updateAddResourcesButtonState());
		scientistsSpinnerValueFactory.valueProperty().addListener((observer, oldVal, newVal) -> updateAddResourcesButtonState());
	}
	
	private void addResourceAddingListeners() {
		GameManager gameManager = GameManager.getInstance();
		IResearchManager researchManager = gameManager.getResearchManager(gameId, gameManager.getLocalPlayer());
		comboBoxResearchAddResourcesSelectArea.valueProperty().addListener((observer, oldVal, newVal) -> updateResourceAdding());
		for (ResearchArea area : ResearchArea.values()) {
			researchManager.getResearchResourcesNeededLeftProperties(area).addListener((observer, oldVal, newVal) -> updateResourceAdding());
		}
	}
	
	private void updateResourceAdding() {
		GameManager gameManager = GameManager.getInstance();
		IResearchManager researchManager = gameManager.getResearchManager(gameId, gameManager.getLocalPlayer());
		ResearchArea area = comboBoxResearchAddResourcesSelectArea.getValue();
		int nextResouceNeedingState = researchManager.getNextResourceNeedingState(area);
		
		//find the needed resources
		ResearchResources neededResources;
		if (nextResouceNeedingState == -1) {
			neededResources = new ResearchResources();
		}
		else {
			neededResources = researchManager.getResearchResourcesNeededLeft(area, nextResouceNeedingState);
		}
		
		//set the spinner maximum
		((IntegerSpinnerValueFactory) spinnerResearchAddCarbon.getValueFactory()).setMax(neededResources.getResources(Resource.CARBON));
		((IntegerSpinnerValueFactory) spinnerResearchAddSilicium.getValueFactory()).setMax(neededResources.getResources(Resource.SILICIUM));
		((IntegerSpinnerValueFactory) spinnerResearchAddIron.getValueFactory()).setMax(neededResources.getResources(Resource.IRON));
		((IntegerSpinnerValueFactory) spinnerResearchAddScientists.getValueFactory()).setMax(neededResources.getResources(Resource.SCIENTISTS));
		
		//set the labels
		labelResearchCarbonNeeded.setText("/ " + neededResources.getResources(Resource.CARBON));
		labelResearchSiliciumNeeded.setText("/ " + neededResources.getResources(Resource.SILICIUM));
		labelResearchIronNeeded.setText("/ " + neededResources.getResources(Resource.IRON));
		labelResearchScientistsNeeded.setText("/ " + neededResources.getResources(Resource.SCIENTISTS));
		
		int maxAccessibleState = researchManager.getMaxReachableStateProperty(area).get();
		labelAccessibleResearchState.setText(Integer.toString(maxAccessibleState));
		
		//set the add resources button's disabled property
		updateAddResourcesButtonState();
	}
	
	private void updateAddResourcesButtonState() {
		GameManager gameManager = GameManager.getInstance();
		IResearchManager researchManager = gameManager.getResearchManager(gameId, gameManager.getLocalPlayer());
		ResearchArea area = comboBoxResearchAddResourcesSelectArea.getValue();
		//enable the button
		boolean enableAdding = false;
		//any resource to add
		enableAdding |= spinnerResearchAddCarbon.getValue().intValue() > 0;
		enableAdding |= spinnerResearchAddSilicium.getValue().intValue() > 0;
		enableAdding |= spinnerResearchAddIron.getValue().intValue() > 0;
		enableAdding |= spinnerResearchAddScientists.getValue().intValue() > 0;
		//resources needed
		enableAdding &= researchManager.getNextResourceNeedingState(area) != -1;
		
		buttonResearchAddResources.setDisable(!enableAdding);
	}
}
package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

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
	private Spinner<?> spinnerResearchAddCarbon;
	@FXML
	private Spinner<?> spinnerResearchAddIron;
	@FXML
	private Spinner<?> spinnerResearchAddSilicium;
	
	@FXML
	private ComboBox<?> comboBoxResearchAddResourcesSelectArea;
	
	@FXML
	private Button buttonResearchAddResources;
	
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
	private Label labelResearchNextStateResourcesMines;
	@FXML
	private Label labelResearchPossibleStateEconomy;
	@FXML
	private Label labelResearchNextStateResourcesEconomy;
	@FXML
	private Label labelResearchPossibleStateFtl;
	@FXML
	private Label labelResearchNextStateResourcesFtl;
	@FXML
	private Label labelResearchPossibleStateResearch;
	@FXML
	private Label labelResearchNextStateResourcesResearch;
	@FXML
	private Label labelResearchPossibleStateMilitary;
	@FXML
	private Label labelResearchNextStateResourcesMilitary;
	@FXML
	private Label labelResearchPossibleStateWeapon;
	@FXML
	private Label labelResearchNextStateResourcesWeapon;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GuiUtils.loadImageToView("research/research_panel.png", true, imageViewResearchBackground);
	}
}
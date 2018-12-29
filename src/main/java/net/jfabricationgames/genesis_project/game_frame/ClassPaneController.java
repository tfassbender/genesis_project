package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.PlayerClass;
import net.jfabricationgames.genesis_project.manager.IBuildingManager;
import net.jfabricationgames.genesis_project.manager.IPointManager;
import net.jfabricationgames.genesis_project.manager.IResourceManager;

public class ClassPaneController implements Initializable {
	
	@FXML
	private ImageView imageViewGameClassBackground;
	@FXML
	private Label labelGameClassCarbon;
	@FXML
	private Label labelGameClassPoints;
	@FXML
	private Label labelGameClassFerum;
	@FXML
	private Label labelGameClassSilicium;
	@FXML
	private Label labelGameClassResearchPoints;
	@FXML
	private Label labelGameClassScientists;
	@FXML
	private Label labelGameClassFTL;
	@FXML
	private Label labelGameClassGovermentBuildings;
	@FXML
	private Label labelGameClassCityBuildings;
	@FXML
	private Label labelGameClassResearchStationBuildings;
	@FXML
	private Label labelGameClassMineBuildings;
	@FXML
	private Label labelGameClassTraidingPostBuildings;
	@FXML
	private Label labelGameClassLaboratoryBuildings;
	@FXML
	private Label labelGameClassSpaceStationBuildings;
	@FXML
	private Label labelGameClassColonyBuildings;
	@FXML
	private Label labelGameClassDroneBuildings;
	@FXML
	private Pane panelGameClassGovermentEffectCover;
	@FXML
	private Pane panelGameClassClassEffectCover;
	
	private Player player;
	private PlayerClass playerClass;
	
	public ClassPaneController(Player player) {
		this.player = player;
		playerClass = player.getPlayerClass();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GuiUtils.loadImageToView(playerClass.getClassPaneImagePath(), true, imageViewGameClassBackground);
		
		bindResourceLabels();
		bindPointLabel();
		bindBuildingLabels();
		
		addSpecialAbilityExplenations();
	}

	private void bindResourceLabels() {
		IResourceManager resourceManager = player.getResourceManager();
		labelGameClassCarbon.textProperty().bind(Bindings.convert(resourceManager.getResourcesCProperty()));
		labelGameClassSilicium.textProperty().bind(Bindings.convert(resourceManager.getResourcesSiProperty()));
		labelGameClassFerum.textProperty().bind(Bindings.convert(resourceManager.getResourcesFeProperty()));
		labelGameClassResearchPoints.textProperty().bind(Bindings.convert(resourceManager.getResearchPointsProperty()));
		labelGameClassScientists.textProperty().bind(Bindings.convert(resourceManager.getScientistsProperty()));
		labelGameClassFTL.textProperty().bind(Bindings.convert(resourceManager.getFTLProperty()));
	}
	
	private void bindPointLabel() {
		IPointManager pointManager = player.getPointManager();
		labelGameClassPoints.textProperty().bind(Bindings.convert(pointManager.getPointsProperty()));
	}
	
	private void bindBuildingLabels() {
		IBuildingManager buildingManager = player.getBuildingManager();
		labelGameClassColonyBuildings.textProperty().bind(Bindings.convert(buildingManager.getNumBuildingsLeftProperty(Building.COLONY)));
		labelGameClassMineBuildings.textProperty().bind(Bindings.convert(buildingManager.getNumBuildingsLeftProperty(Building.MINE)));
		labelGameClassTraidingPostBuildings.textProperty().bind(Bindings.convert(buildingManager.getNumBuildingsLeftProperty(Building.TRADING_POST)));
		labelGameClassLaboratoryBuildings.textProperty().bind(Bindings.convert(buildingManager.getNumBuildingsLeftProperty(Building.LABORATORY)));
		labelGameClassGovermentBuildings.textProperty().bind(Bindings.convert(buildingManager.getNumBuildingsLeftProperty(Building.GOVERNMENT)));
		labelGameClassCityBuildings.textProperty().bind(Bindings.convert(buildingManager.getNumBuildingsLeftProperty(Building.CITY)));
		labelGameClassResearchStationBuildings.textProperty().bind(Bindings.convert(buildingManager.getNumBuildingsLeftProperty(Building.RESEARCH_CENTER)));
		labelGameClassDroneBuildings.textProperty().bind(Bindings.convert(buildingManager.getNumBuildingsLeftProperty(Building.DRONE)));
		labelGameClassSpaceStationBuildings.textProperty().bind(Bindings.convert(buildingManager.getNumBuildingsLeftProperty(Building.SPACE_STATION)));
	}
	
	private void addSpecialAbilityExplenations() {
		Tooltip tooltipClassEffect = new Tooltip();
		Tooltip tooltipGovernmentEffect = new Tooltip();
		tooltipClassEffect.setText(player.getPlayerClass().getClassEffectDescription());
		tooltipGovernmentEffect.setText(player.getPlayerClass().getGovernmentEffectDescription());
		
		Tooltip.install(panelGameClassClassEffectCover, tooltipClassEffect);
		Tooltip.install(panelGameClassGovermentEffectCover, tooltipGovernmentEffect);
	}
}
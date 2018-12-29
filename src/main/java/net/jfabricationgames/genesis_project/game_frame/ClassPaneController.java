package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.PlayerClass;
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
}
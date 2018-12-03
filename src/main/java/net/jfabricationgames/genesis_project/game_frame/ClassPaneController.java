package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GuiUtils.loadImageToView("classes/class_boards/class_board_blue_1.png", true, imageViewGameClassBackground);
	}
}
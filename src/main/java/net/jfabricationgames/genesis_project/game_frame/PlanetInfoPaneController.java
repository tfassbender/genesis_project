package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class PlanetInfoPaneController implements Initializable {
	
	@FXML
	private Label labelPlanetInfoPosition;
	@FXML
	private Label labelPlanetInfoType;
	@FXML
	private Label labelPlanetInfoPrimariResource;
	@FXML
	private Label labelPlanetInfoDefence;
	@FXML
	private Label labelPlanetInfoAlliances;
	
	@FXML
	private Label labelPlanetInfoBuilding1Player;
	@FXML
	private Label labelPlanetInfoBuilding1Type;
	@FXML
	private Label labelPlanetInfoBuilding2Player;
	@FXML
	private Label labelPlanetInfoBuilding2Type;
	@FXML
	private Label labelPlanetInfoBuilding3Player;
	@FXML
	private Label labelPlanetInfoBuilding3Type;
	
	@FXML
	private ImageView imageViewPlanet;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GuiUtils.loadImageToView("planets/planet_center.png", true, imageViewPlanet);
	}
}
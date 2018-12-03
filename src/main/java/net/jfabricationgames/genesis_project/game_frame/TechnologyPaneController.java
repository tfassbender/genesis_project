package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class TechnologyPaneController implements Initializable {
	
	@FXML
	private ImageView imageTechnologyColoniePoints;
	@FXML
	private ImageView imageTechnologyTraidingPostPoints;
	@FXML
	private ImageView imageTechnologyBonusResources;
	@FXML
	private ImageView imageTechnologyBonusResearchPoints;
	
	@FXML
	private Button buttonTechnologyPurchaseColoniePoints;
	@FXML
	private Button buttonTechnologyPurchaseTraidingPostPoints;
	@FXML
	private Button buttonTechnologyPurchaseBonusResources;
	@FXML
	private Button buttonTechnologyPurchaseBonusResearchPoints;
	
	@FXML
	private ImageView imageTechnologyPurchasedColoniePoints;
	@FXML
	private ImageView imageTechnologyPurchasedTraidingPostPoints;
	@FXML
	private ImageView imageTechnologyPurchasedBonusResources;
	@FXML
	private ImageView imageTechnologyPurchasedBonusResearchPoints;
	
	@FXML
	private Button buttonTechnologyPurchaseNewPlanetPoints;
	@FXML
	private Button buttonTechnologyPurchaseMilitaryFtl;
	@FXML
	private Button buttonTechnologyPurchaseAllianceBuildings;
	@FXML
	private Button buttonTechnologyPurchaseBonusPoints;
	
	@FXML
	private ImageView imageTechnologyNewPlanetPoints;
	@FXML
	private ImageView imageTechnologyMilitaryFtl;
	@FXML
	private ImageView imageTechnologyAllianceBuildings;
	@FXML
	private ImageView imageTechnologyBonusPoints;
	@FXML
	private ImageView imageTechnologyPurchasedNewPlanetPoints;
	@FXML
	private ImageView imageTechnologyPurchasedMilitaryFtl;
	@FXML
	private ImageView imageTechnologyPurchasedAllianceBuildings;
	@FXML
	private ImageView imageTechnologyPurchasedBonusPoints;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GuiUtils.loadImageToView("cards/tech/tech_colonie_points.png", true, imageTechnologyColoniePoints);
		GuiUtils.loadImageToView("cards/tech/tech_traiding_post_points.png", true, imageTechnologyTraidingPostPoints);
		GuiUtils.loadImageToView("cards/tech/tech_resources.png", true, imageTechnologyBonusResources);
		GuiUtils.loadImageToView("cards/tech/tech_research_points.png", true, imageTechnologyBonusResearchPoints);
		GuiUtils.loadImageToView("cards/tech/tech_new_planet_points.png", true, imageTechnologyNewPlanetPoints);
		GuiUtils.loadImageToView("cards/tech/tech_drone_station_range.png", true, imageTechnologyMilitaryFtl);
		GuiUtils.loadImageToView("cards/tech/tech_alliance_buildings.png", true, imageTechnologyAllianceBuildings);
		GuiUtils.loadImageToView("cards/tech/tech_points.png", true, imageTechnologyBonusPoints);

		GuiUtils.loadImageToView("basic/cross.png", true, imageTechnologyPurchasedColoniePoints);
		GuiUtils.loadImageToView("basic/cross.png", true, imageTechnologyPurchasedTraidingPostPoints);
		GuiUtils.loadImageToView("basic/cross.png", true, imageTechnologyPurchasedBonusResources);
		GuiUtils.loadImageToView("basic/cross.png", true, imageTechnologyPurchasedBonusResearchPoints);
		GuiUtils.loadImageToView("basic/cross.png", true, imageTechnologyPurchasedNewPlanetPoints);
		GuiUtils.loadImageToView("basic/cross.png", true, imageTechnologyPurchasedMilitaryFtl);
		GuiUtils.loadImageToView("basic/cross.png", true, imageTechnologyPurchasedAllianceBuildings);
		GuiUtils.loadImageToView("basic/cross.png", true, imageTechnologyPurchasedBonusPoints);
	}
}
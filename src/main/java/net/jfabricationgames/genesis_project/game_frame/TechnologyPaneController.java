package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.Technology;
import net.jfabricationgames.genesis_project.manager.ITechnologyManager;

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
	
	private final String crossImagePath = "basic/cross.png";
	private final String hookImagePath = "basic/hook.png";
	
	private Player player;
	
	private Map<Technology, ImageView> exploredImageMap = new HashMap<Technology, ImageView>();
	private Map<Technology, Button> exploreButtons = new HashMap<Technology, Button>();
	
	public TechnologyPaneController(Player player) {
		this.player = player;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addTechnologyCardImages();
		initializeExploredMap();
		updateExploredImages();
	}
	
	private void addTechnologyCardImages() {
		GuiUtils.loadImageToView("cards/tech/tech_colonie_points.png", true, imageTechnologyColoniePoints);
		GuiUtils.loadImageToView("cards/tech/tech_traiding_post_points.png", true, imageTechnologyTraidingPostPoints);
		GuiUtils.loadImageToView("cards/tech/tech_resources.png", true, imageTechnologyBonusResources);
		GuiUtils.loadImageToView("cards/tech/tech_research_points.png", true, imageTechnologyBonusResearchPoints);
		GuiUtils.loadImageToView("cards/tech/tech_new_planet_points.png", true, imageTechnologyNewPlanetPoints);
		GuiUtils.loadImageToView("cards/tech/tech_drone_station_range.png", true, imageTechnologyMilitaryFtl);
		GuiUtils.loadImageToView("cards/tech/tech_alliance_buildings.png", true, imageTechnologyAllianceBuildings);
		GuiUtils.loadImageToView("cards/tech/tech_points.png", true, imageTechnologyBonusPoints);
	}
	
	private void initializeExploredMap() {
		exploredImageMap.put(Technology.COLONY_POINTS, imageTechnologyPurchasedColoniePoints);
		exploredImageMap.put(Technology.TRAIDING_POST_POINTS, imageTechnologyPurchasedTraidingPostPoints);
		exploredImageMap.put(Technology.BONUS_RESOURCES, imageTechnologyPurchasedBonusResources);
		exploredImageMap.put(Technology.BONUS_RESEARCH_POINTS, imageTechnologyPurchasedBonusResearchPoints);
		exploredImageMap.put(Technology.NEW_PLANET_POINTS, imageTechnologyPurchasedNewPlanetPoints);
		exploredImageMap.put(Technology.MILITARY_FTL, imageTechnologyPurchasedMilitaryFtl);
		exploredImageMap.put(Technology.ALLIANCE_BUILDINGS, imageTechnologyPurchasedAllianceBuildings);
		exploredImageMap.put(Technology.BONUS_POINTS, imageTechnologyPurchasedBonusPoints);
		
		exploreButtons.put(Technology.COLONY_POINTS, buttonTechnologyPurchaseColoniePoints);
		exploreButtons.put(Technology.TRAIDING_POST_POINTS, buttonTechnologyPurchaseTraidingPostPoints);
		exploreButtons.put(Technology.BONUS_RESOURCES, buttonTechnologyPurchaseBonusResources);
		exploreButtons.put(Technology.BONUS_RESEARCH_POINTS, buttonTechnologyPurchaseBonusResearchPoints);
		exploreButtons.put(Technology.NEW_PLANET_POINTS, buttonTechnologyPurchaseNewPlanetPoints);
		exploreButtons.put(Technology.MILITARY_FTL, buttonTechnologyPurchaseMilitaryFtl);
		exploreButtons.put(Technology.ALLIANCE_BUILDINGS, buttonTechnologyPurchaseAllianceBuildings);
		exploreButtons.put(Technology.BONUS_POINTS, buttonTechnologyPurchaseBonusPoints);
	}
	
	private void updateExploredImages() {
		Image crossImage = GuiUtils.loadImage(crossImagePath, true);
		Image hookImage = GuiUtils.loadImage(hookImagePath, true);
		
		ITechnologyManager technologyManager = player.getTechnologyManager();
		
		for (Technology technology : Technology.values()) {
			ImageView imageView = exploredImageMap.get(technology);
			Image image;
			if (technologyManager.isTechnologyExplored(technology)) {
				image = hookImage;
			}
			else {
				image = crossImage;
			}
			imageView.setImage(image);
			imageView.setCache(true);
		}
	}
	
	/**
	 * Enables all exploration buttons of technologies that the player has not yet explored.
	 */
	public void enableExploreButtons() {
		ITechnologyManager technologyManager = player.getTechnologyManager();
		
		for (Technology technology : Technology.values()) {
			if (!technologyManager.isTechnologyExplored(technology)) {
				Button button = exploreButtons.get(technology);
				button.setDisable(false);
			}
		}
	}
	
	/**
	 * Disable all explore buttons.
	 */
	public void disableExploreButtons() {
		for (Technology technology : Technology.values()) {
			Button button = exploreButtons.get(technology);
			button.setDisable(true);
		}
	}
}
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
import net.jfabricationgames.genesis_project.game.AllianceBonus;
import net.jfabricationgames.genesis_project.game.Constants;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.manager.IAllianceManager;

public class AlliancePaneController implements Initializable {
	
	@FXML
	private ImageView imageAllianceBonusMilitaryRange1;
	@FXML
	private ImageView imageAllianceBonusPrimaryResources1;
	@FXML
	private ImageView imageAllianceBonusSecundaryResources1;
	@FXML
	private ImageView imageAllianceBonusScientists1;
	@FXML
	private ImageView imageAllianceBonusResearchPoints1;
	@FXML
	private ImageView imageAllianceBonusPoints1;
	
	@FXML
	private ImageView imageAllianceBonusSelectedMilitaryRange1;
	@FXML
	private ImageView imageAllianceBonusSelectedPrimaryResources1;
	@FXML
	private ImageView imageAllianceBonusSelectedSecundaryResources1;
	@FXML
	private ImageView imageAllianceBonusSelectedScientists1;
	@FXML
	private ImageView imageAllianceBonusSelectedResearchPoints1;
	@FXML
	private ImageView imageAllianceBonusSelectedPoints1;
	
	@FXML
	private Button buttonPurchaseAllianceBonusMilitaryRange1;
	@FXML
	private Button buttonPurchaseAllianceBonusPrimaryResources1;
	@FXML
	private Button buttonPurchaseAllianceBonusSecundaryResources1;
	@FXML
	private Button buttonPurchaseAllianceBonusScientists1;
	@FXML
	private Button buttonPurchaseAllianceBonusResearchPoints1;
	@FXML
	private Button buttonPurchaseAllianceBonusPoints1;
	
	@FXML
	private ImageView imageAllianceBonusMilitaryRange2;
	@FXML
	private ImageView imageAllianceBonusPrimaryResources2;
	@FXML
	private ImageView imageAllianceBonusSecundaryResources2;
	@FXML
	private ImageView imageAllianceBonusPoints2;
	@FXML
	private ImageView imageAllianceBonusResearchPoints2;
	@FXML
	private ImageView imageAllianceBonusScientists2;
	
	@FXML
	private ImageView imageAllianceBonusSelectedMilitaryRange2;
	@FXML
	private ImageView imageAllianceBonusSelectedPrimaryResources2;
	@FXML
	private ImageView imageAllianceBonusSelectedSecundaryResources2;
	@FXML
	private ImageView imageAllianceBonusSelectedScientists2;
	@FXML
	private ImageView imageAllianceBonusSelectedResearchPoints2;
	@FXML
	private ImageView imageAllianceBonusSelectedPoints2;
	
	@FXML
	private Button buttonPurchaseAllianceBonusMilitaryRange2;
	@FXML
	private Button buttonPurchaseAllianceBonusPrimaryResources2;
	@FXML
	private Button buttonPurchaseAllianceBonusSecundaryResources2;
	@FXML
	private Button buttonPurchaseAllianceBonusScientists2;
	@FXML
	private Button buttonPurchaseAllianceBonusResearchPoints2;
	@FXML
	private Button buttonPurchaseAllianceBonusPoints2;
	
	private final String crossImagePath = "basic/cross.png";
	private final String hookImagePath = "basic/hook.png";
	
	private Player player;
	
	private Map<AllianceBonus, ImageView[]> exploredImageMap = new HashMap<AllianceBonus, ImageView[]>();
	private Map<AllianceBonus, Button[]> exploreButtons = new HashMap<AllianceBonus, Button[]>();
	
	public AlliancePaneController(Player player) {
		this.player = player;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addAllianceCardImages();
		initializeExploredMap();
		updateExploredImages();
	}
	
	private void addAllianceCardImages() {
		GuiUtils.loadImageToView("cards/alliance_markers/alliance_marker_drone_station_range.png", true, imageAllianceBonusMilitaryRange1);
		GuiUtils.loadImageToView("cards/alliance_markers/alliance_marker_drone_station_range.png", true, imageAllianceBonusMilitaryRange2);
		GuiUtils.loadImageToView("cards/alliance_markers/alliance_marker_prim_resource.png", true, imageAllianceBonusPrimaryResources1);
		GuiUtils.loadImageToView("cards/alliance_markers/alliance_marker_prim_resource.png", true, imageAllianceBonusPrimaryResources2);
		GuiUtils.loadImageToView("cards/alliance_markers/alliance_marker_sec_resource.png", true, imageAllianceBonusSecundaryResources1);
		GuiUtils.loadImageToView("cards/alliance_markers/alliance_marker_sec_resource.png", true, imageAllianceBonusSecundaryResources2);
		GuiUtils.loadImageToView("cards/alliance_markers/alliance_marker_eierkoeppe.png", true, imageAllianceBonusScientists1);
		GuiUtils.loadImageToView("cards/alliance_markers/alliance_marker_eierkoeppe.png", true, imageAllianceBonusScientists2);
		GuiUtils.loadImageToView("cards/alliance_markers/alliance_marker_research_points.png", true, imageAllianceBonusResearchPoints1);
		GuiUtils.loadImageToView("cards/alliance_markers/alliance_marker_research_points.png", true, imageAllianceBonusResearchPoints2);
		GuiUtils.loadImageToView("cards/alliance_markers/alliance_marker_points.png", true, imageAllianceBonusPoints1);
		GuiUtils.loadImageToView("cards/alliance_markers/alliance_marker_points.png", true, imageAllianceBonusPoints2);
	}
	
	private void initializeExploredMap() {
		exploredImageMap.put(AllianceBonus.MILITARY_RANGE,
				new ImageView[] {imageAllianceBonusSelectedMilitaryRange1, imageAllianceBonusSelectedMilitaryRange2});
		exploredImageMap.put(AllianceBonus.PRIMARY_RESOURCES,
				new ImageView[] {imageAllianceBonusSelectedPrimaryResources1, imageAllianceBonusSelectedPrimaryResources2});
		exploredImageMap.put(AllianceBonus.SECUNDARY_RESOURCES,
				new ImageView[] {imageAllianceBonusSelectedSecundaryResources1, imageAllianceBonusSelectedSecundaryResources2});
		exploredImageMap.put(AllianceBonus.SCIENTISTS,
				new ImageView[] {imageAllianceBonusSelectedScientists1, imageAllianceBonusSelectedScientists2});
		exploredImageMap.put(AllianceBonus.RESEARCH_POINTS,
				new ImageView[] {imageAllianceBonusSelectedResearchPoints1, imageAllianceBonusSelectedResearchPoints2});
		exploredImageMap.put(AllianceBonus.POINTS, new ImageView[] {imageAllianceBonusSelectedPoints1, imageAllianceBonusSelectedPoints2});
		
		exploreButtons.put(AllianceBonus.MILITARY_RANGE,
				new Button[] {buttonPurchaseAllianceBonusMilitaryRange1, buttonPurchaseAllianceBonusMilitaryRange2});
		exploreButtons.put(AllianceBonus.PRIMARY_RESOURCES,
				new Button[] {buttonPurchaseAllianceBonusPrimaryResources1, buttonPurchaseAllianceBonusPrimaryResources2});
		exploreButtons.put(AllianceBonus.SECUNDARY_RESOURCES,
				new Button[] {buttonPurchaseAllianceBonusSecundaryResources1, buttonPurchaseAllianceBonusSecundaryResources2});
		exploreButtons.put(AllianceBonus.SCIENTISTS, new Button[] {buttonPurchaseAllianceBonusScientists1, buttonPurchaseAllianceBonusScientists2});
		exploreButtons.put(AllianceBonus.RESEARCH_POINTS,
				new Button[] {buttonPurchaseAllianceBonusResearchPoints1, buttonPurchaseAllianceBonusResearchPoints2});
		exploreButtons.put(AllianceBonus.POINTS, new Button[] {buttonPurchaseAllianceBonusPoints1, buttonPurchaseAllianceBonusPoints2});
	}
	
	private void updateExploredImages() {
		Image crossImage = GuiUtils.loadImage(crossImagePath, true);
		Image hookImage = GuiUtils.loadImage(hookImagePath, true);
		
		IAllianceManager allianceManager = player.getAllianceManager();
		
		for (AllianceBonus bonus : AllianceBonus.values()) {
			ImageView[] imageView = exploredImageMap.get(bonus);
			for (int i = 0; i < imageView.length; i++) {
				Image image;
				if (allianceManager.isAllianceBonusTaken(bonus, i)) {
					image = hookImage;
				}
				else {
					image = crossImage;
				}
				imageView[i].setImage(image);
				imageView[i].setCache(true);
			}
		}
	}
	
	/**
	 * Enables all exploration buttons of bonuses that are not yet taken.
	 */
	public void enableExploreButtons() {
		IAllianceManager allianceManager = player.getAllianceManager();
		
		for (AllianceBonus bonus : AllianceBonus.values()) {
			for (int i = 0; i < Constants.ALLIANCE_BONUS_COPIES; i++) {
				if (!allianceManager.isAllianceBonusTaken(bonus, i)) {
					Button button = exploreButtons.get(bonus)[i];
					button.setDisable(false);
				}
			}
		}
	}
	
	/**
	 * Disable all explore buttons.
	 */
	public void disableExploreButtons() {
		for (AllianceBonus bonus : AllianceBonus.values()) {
			for (int i = 0; i < Constants.ALLIANCE_BONUS_COPIES; i++) {
				Button button = exploreButtons.get(bonus)[i];
				button.setDisable(true);
			}
		}
	}
}
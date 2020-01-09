package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.jfabricationgames.genesis_project.connection.exception.ServerCommunicationException;
import net.jfabricationgames.genesis_project.game.Alliance;
import net.jfabricationgames.genesis_project.game.AllianceBonus;
import net.jfabricationgames.genesis_project.game.AllianceBuilder;
import net.jfabricationgames.genesis_project.game.Constants;
import net.jfabricationgames.genesis_project.game.DescriptionTexts;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game_frame.util.DialogUtils;
import net.jfabricationgames.genesis_project.game_frame.util.GuiUtils;
import net.jfabricationgames.genesis_project.manager.GameManager;
import net.jfabricationgames.genesis_project.manager.IAllianceManager;
import net.jfabricationgames.genesis_project.move.IMove;
import net.jfabricationgames.genesis_project.move.InvalidMoveException;
import net.jfabricationgames.genesis_project.move.MoveBuilder;
import net.jfabricationgames.genesis_project.move.MoveType;
import net.jfabricationgames.genesis_project.user.UserManager;

public class AlliancePaneController implements Initializable {
	
	private static final Logger LOGGER = LogManager.getLogger(AlliancePaneController.class);
	
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
	
	@FXML
	private ImageView imageAllianceBonusAny;
	@FXML
	private Button buttonPurchaseAllianceBonusAny;
	
	@FXML
	private ListView<Field> listAlliancePlanetFields;
	@FXML
	private ListView<Field> listAllianceSpaceFields;
	@FXML
	private CheckBox checkboxAllianceMarkPlanets;
	@FXML
	private CheckBox checkboxAllianceMarkSpaceFields;
	@FXML
	private Button buttonAllianceDeletePlanet;
	@FXML
	private Button buttonAllianceDeleteAllPlanets;
	@FXML
	private Button buttonAllianceDeleteSpaceField;
	@FXML
	private Button buttonAllianceDeleteAllSpaceFields;
	
	@FXML
	private Label labelAllianceNumPlanets;
	@FXML
	private Label labelAllianceBuildings;
	@FXML
	private Label labelAllianceNumNeighbourPlanets;
	@FXML
	private Label labelAllianceValid;
	@FXML
	private Label labelAllianceNeighbourBuildings;
	@FXML
	private Label labelAllianceMainBuilding;
	
	private final String crossImagePath = "basic/cross.png";
	private final String hookImagePath = "basic/hook.png";
	
	private BooleanProperty allianceMarkPlanetsBound;
	private BooleanProperty allianceMarkSpaceFieldsBound;
	
	private int gameId;
	
	private Map<AllianceBonus, ImageView[]> exploredImageMap = new HashMap<AllianceBonus, ImageView[]>();
	private Map<AllianceBonus, Button[]> exploreButtons = new HashMap<AllianceBonus, Button[]>();
	
	public AlliancePaneController(int gameId) {
		this.gameId = gameId;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addAllianceCardImages();
		initializeExploredMap();
		updateExploredImages();
		bindAllianceBuilderProperties();
		addPurchaseButtonFunctions();
		addDeleteButtonFunctions();
	}
	
	public void updateAll() {
		unbindAll();
		
		updateExploredImages();
		bindAllianceBuilderProperties();
		addPurchaseButtonFunctions();
		addDeleteButtonFunctions();
	}
	
	private void unbindAll() {
		checkboxAllianceMarkPlanets.selectedProperty().unbindBidirectional(allianceMarkPlanetsBound);
		checkboxAllianceMarkSpaceFields.selectedProperty().unbindBidirectional(allianceMarkSpaceFieldsBound);
		
		labelAllianceNumPlanets.textProperty().unbind();
		labelAllianceNumNeighbourPlanets.textProperty().unbind();
		labelAllianceBuildings.textProperty().unbind();
		labelAllianceNeighbourBuildings.textProperty().unbind();
		labelAllianceMainBuilding.textProperty().unbind();
		
		for (Entry<AllianceBonus, Button[]> entry : exploreButtons.entrySet()) {
			Button[] buttons = entry.getValue();
			for (int i = 0; i < buttons.length; i++) {
				buttons[i].disableProperty().unbind();
			}
		}
		
		buttonPurchaseAllianceBonusAny.disableProperty().unbind();
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
		GuiUtils.loadImageToView("cards/alliance_markers/alliance_marker_any.png", true, imageAllianceBonusAny);
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
		
		GameManager gameManager = GameManager.getInstance();
		IAllianceManager allianceManager = gameManager.getAllianceManager(gameId, gameManager.getLocalPlayer());
		
		for (AllianceBonus bonus : AllianceBonus.values()) {
			if (bonus != AllianceBonus.ANY) {
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
	}
	
	private void bindAllianceBuilderProperties() {
		GameManager gameManager = GameManager.getInstance();
		IAllianceManager allianceManager = gameManager.getAllianceManager(gameId, gameManager.getLocalPlayer());
		AllianceBuilder allianceBuilder = allianceManager.getAllianceBuilder();
		
		//set list items
		listAlliancePlanetFields.setItems(allianceBuilder.getPlanets());
		listAllianceSpaceFields.setItems(allianceBuilder.getConnectingSatellites());
		//bind checkboxes bidirectional
		allianceMarkPlanetsBound = allianceBuilder.getMarkPlanetFieldsProperty();
		allianceMarkSpaceFieldsBound = allianceBuilder.getMarkSatelliteFieldsProperty();
		checkboxAllianceMarkPlanets.selectedProperty().bindBidirectional(allianceMarkPlanetsBound);
		checkboxAllianceMarkSpaceFields.selectedProperty().bindBidirectional(allianceMarkSpaceFieldsBound);
		
		//bind the text labels using StringBindings from IntegerProperties
		labelAllianceNumPlanets.textProperty()
				.bind(Bindings.concat(allianceBuilder.getNumPlanetsProperty().asString(), " / " + Constants.getInstance().ALLIANCE_MIN_PLANETS));
		labelAllianceNumNeighbourPlanets.textProperty().bind(Bindings.concat(allianceBuilder.getNumNeighbourPlanetsProperty().asString(),
				" / " + Constants.getInstance().ALLIANCE_MIN_PLANETS_OTHER_PLAYERS));
		labelAllianceBuildings.textProperty()
				.bind(Bindings.concat(allianceBuilder.getNumBuildingsProperty(), " / " + Constants.getInstance().ALLIANCE_MIN_BUILDINGS));
		labelAllianceNeighbourBuildings.textProperty().bind(Bindings.concat(allianceBuilder.getNumNeighbourBuildingsProperty().asString(),
				" / " + Constants.getInstance().ALLIANCE_MIN_BUILDINGS_OTHER_PLAYERS));
		labelAllianceMainBuilding.textProperty().bind(Bindings.concat(allianceBuilder.getNumMainBuildingsProperty().asString(), " / 1"));
		
		//use a listener because the translation would be difficult
		allianceBuilder.getAllianceValidProperty().addListener((observer, oldVal, newVal) -> {
			if (newVal.booleanValue()) {
				labelAllianceValid.setText("Ja");
			}
			else {
				labelAllianceValid.setText("Nein");
			}
		});
		//initialize with default
		labelAllianceValid.setText("Nein");
	}
	
	private void addPurchaseButtonFunctions() {
		//get the AllianceBuilder of this player to manage creating the alliances and enabling/disabling the buttons
		GameManager gameManager = GameManager.getInstance();
		IAllianceManager allianceManager = gameManager.getAllianceManager(gameId, gameManager.getLocalPlayer());
		AllianceBuilder allianceBuilder = allianceManager.getAllianceBuilder();
		
		for (Entry<AllianceBonus, Button[]> entry : exploreButtons.entrySet()) {
			AllianceBonus bonus = entry.getKey();
			Button[] buttons = entry.getValue();
			for (int i = 0; i < buttons.length; i++) {
				//bind the disabled properties (enable when alliance is valid and the bonus is not taken)
				buttons[i].disableProperty()
						.bind(allianceBuilder.getAllianceValidProperty().and(allianceManager.getAllianceBonusTakenProperty(bonus, i)).not());
				//set the button actions (execute the move and create the alliance)
				final int index = i;
				buttons[i].setOnAction(e -> createAlliance(bonus, index));
			}
		}
		
		//set the ANY AllianceBonus actions and property
		BooleanBinding allBonusesTaken = null;
		for (AllianceBonus bonus : AllianceBonus.values()) {
			for (int i = 0; i < Constants.getInstance().ALLIANCE_BONUS_COPIES; i++) {
				if (!allianceManager.isAllianceBonusTaken(bonus, i)) {
					Button button = exploreButtons.get(bonus)[i];
					button.setDisable(false);
				}
			}
		}
		//enable the last bonus only when all others are taken
		buttonPurchaseAllianceBonusAny.disableProperty().bind(allianceBuilder.getAllianceValidProperty().and(allBonusesTaken).not());
		//set the button action
		buttonPurchaseAllianceBonusAny.setOnAction(e -> createAlliance(AllianceBonus.ANY, 0));
	}
	
	private void createAlliance(AllianceBonus bonus, int bonusIndex) {
		GameManager gameManager = GameManager.getInstance();
		IAllianceManager allianceManager = gameManager.getAllianceManager(gameId, gameManager.getLocalPlayer());
		AllianceBuilder allianceBuilder = allianceManager.getAllianceBuilder();
		
		allianceBuilder.setBonus(bonus);
		allianceBuilder.setBonusIndex(bonusIndex);
		Alliance alliance = allianceBuilder.build();
		
		MoveBuilder builder = new MoveBuilder();
		builder.setPlayer(UserManager.getInstance().getLocalUsername());
		builder.setType(MoveType.ALLIANCE);
		builder.setAllianceBonus(bonus);
		builder.setAllianceBonusIndex(bonusIndex);
		builder.setAlliancePlanets(alliance.getPlanets());
		builder.setSatelliteFields(alliance.getConnectingSatellites());
		
		IMove move = builder.build();
		if (gameManager.isMoveExecutable(gameId, move)) {
			try {
				gameManager.executeMove(gameId, move);
			}
			catch (IllegalArgumentException | InvalidMoveException | ServerCommunicationException e) {
				LOGGER.error("Error in move execution", e);
				DialogUtils.showExceptionDialog("Move execution error", DescriptionTexts.getInstance().ERROR_TEXT_MOVE_EXECUTION, e, true);
			}
			updateExploredImages();
			allianceBuilder.clear();
		}
		else {
			throw new IllegalStateException("The move can't be executed");
		}
	}
	
	/**
	 * Disable all explore buttons.
	 */
	public void disableExploreButtons() {
		for (AllianceBonus bonus : AllianceBonus.values()) {
			for (int i = 0; i < Constants.getInstance().ALLIANCE_BONUS_COPIES; i++) {
				Button button = exploreButtons.get(bonus)[i];
				button.setDisable(true);
			}
		}
	}
	
	private void addDeleteButtonFunctions() {
		GameManager gameManager = GameManager.getInstance();
		IAllianceManager allianceManager = gameManager.getAllianceManager(gameId, gameManager.getLocalPlayer());
		AllianceBuilder allianceBuilder = allianceManager.getAllianceBuilder();
		
		buttonAllianceDeletePlanet.setOnAction(e -> {
			List<Field> selected = listAlliancePlanetFields.getSelectionModel().getSelectedItems();
			for (Field field : selected) {
				allianceBuilder.removePlanetField(field);
			}
		});
		buttonAllianceDeleteSpaceField.setOnAction(e -> {
			List<Field> selected = listAllianceSpaceFields.getSelectionModel().getSelectedItems();
			for (Field field : selected) {
				allianceBuilder.removeConnectingField(field);
			}
		});
		buttonAllianceDeleteAllPlanets.setOnAction(e -> allianceBuilder.removeAllPlanetFields());
		buttonAllianceDeleteAllSpaceFields.setOnAction(e -> allianceBuilder.removeAllConnectingFields());
	}
}
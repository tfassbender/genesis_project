package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.PlayerClass;
import net.jfabricationgames.genesis_project.manager.GameManager;
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
	
	private int gameId;
	
	public ClassPaneController(int gameId) {
		this.gameId = gameId;
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GuiUtils.loadImageToView(getPlayerClass().getClassPaneImagePath(), true, imageViewGameClassBackground);
		
		bindResourceLabels();
		bindPointLabel();
		bindBuildingLabels();
		
		addSpecialAbilityExplenations();
		addSpecialAbilityMoveContextMenus();
	}
	
	public void updateAll() {
		unbindAll();
		
		bindResourceLabels();
		bindPointLabel();
		bindBuildingLabels();
	}
	
	private void unbindAll() {
		labelGameClassCarbon.textProperty().unbind();
		labelGameClassSilicium.textProperty().unbind();
		labelGameClassFerum.textProperty().unbind();
		labelGameClassResearchPoints.textProperty().unbind();
		labelGameClassScientists.textProperty().unbind();
		labelGameClassFTL.textProperty().unbind();
		labelGameClassPoints.textProperty().unbind();
		labelGameClassColonyBuildings.textProperty().unbind();
		labelGameClassMineBuildings.textProperty().unbind();
		labelGameClassTraidingPostBuildings.textProperty().unbind();
		labelGameClassLaboratoryBuildings.textProperty().unbind();
		labelGameClassGovermentBuildings.textProperty().unbind();
		labelGameClassCityBuildings.textProperty().unbind();
		labelGameClassResearchStationBuildings.textProperty().unbind();
		labelGameClassDroneBuildings.textProperty().unbind();
		labelGameClassSpaceStationBuildings.textProperty().unbind();
	}
	
	private void bindResourceLabels() {
		GameManager gameManager = GameManager.getInstance();
		IResourceManager resourceManager = gameManager.getResourceManager(gameId, gameManager.getLocalPlayer());
		labelGameClassCarbon.textProperty().bind(Bindings.convert(resourceManager.getResourcesCProperty()));
		labelGameClassSilicium.textProperty().bind(Bindings.convert(resourceManager.getResourcesSiProperty()));
		labelGameClassFerum.textProperty().bind(Bindings.convert(resourceManager.getResourcesFeProperty()));
		labelGameClassResearchPoints.textProperty().bind(Bindings.convert(resourceManager.getResearchPointsProperty()));
		labelGameClassScientists.textProperty().bind(Bindings.convert(resourceManager.getScientistsProperty()));
		labelGameClassFTL.textProperty().bind(Bindings.convert(resourceManager.getFTLProperty()));
	}
	
	private void bindPointLabel() {
		GameManager gameManager = GameManager.getInstance();
		IPointManager pointManager = gameManager.getPointManager(gameId, gameManager.getLocalPlayer());
		labelGameClassPoints.textProperty().bind(Bindings.convert(pointManager.getPointsProperty()));
	}
	
	private void bindBuildingLabels() {
		GameManager gameManager = GameManager.getInstance();
		IBuildingManager buildingManager = gameManager.getBuildingManager(gameId, gameManager.getLocalPlayer());
		labelGameClassColonyBuildings.textProperty().bind(Bindings.convert(buildingManager.getNumBuildingsLeftProperty(Building.COLONY)));
		labelGameClassMineBuildings.textProperty().bind(Bindings.convert(buildingManager.getNumBuildingsLeftProperty(Building.MINE)));
		labelGameClassTraidingPostBuildings.textProperty().bind(Bindings.convert(buildingManager.getNumBuildingsLeftProperty(Building.TRADING_POST)));
		labelGameClassLaboratoryBuildings.textProperty().bind(Bindings.convert(buildingManager.getNumBuildingsLeftProperty(Building.LABORATORY)));
		labelGameClassGovermentBuildings.textProperty().bind(Bindings.convert(buildingManager.getNumBuildingsLeftProperty(Building.GOVERNMENT)));
		labelGameClassCityBuildings.textProperty().bind(Bindings.convert(buildingManager.getNumBuildingsLeftProperty(Building.CITY)));
		labelGameClassResearchStationBuildings.textProperty()
				.bind(Bindings.convert(buildingManager.getNumBuildingsLeftProperty(Building.RESEARCH_CENTER)));
		labelGameClassDroneBuildings.textProperty().bind(Bindings.convert(buildingManager.getNumBuildingsLeftProperty(Building.DRONE)));
		labelGameClassSpaceStationBuildings.textProperty()
				.bind(Bindings.convert(buildingManager.getNumBuildingsLeftProperty(Building.SPACE_STATION)));
	}
	
	private void addSpecialAbilityExplenations() {
		Tooltip tooltipClassEffect = new Tooltip();
		Tooltip tooltipGovernmentEffect = new Tooltip();
		tooltipClassEffect.setText(getPlayerClass().getClassEffectDescription());
		tooltipGovernmentEffect.setText(getPlayerClass().getGovernmentEffectDescription());
		
		Tooltip.install(panelGameClassClassEffectCover, tooltipClassEffect);
		Tooltip.install(panelGameClassGovermentEffectCover, tooltipGovernmentEffect);
	}
	
	private void addSpecialAbilityMoveContextMenus() {
		if (getPlayerClass().isClassAbilityMove()) {
			addContextMenu("Spezialfähigkeit: " + getPlayerClass().getClassEffectName(), panelGameClassClassEffectCover,
					e -> executeClassAbilityMove());
		}
		if (getPlayerClass().isGovernmentAbilityMove()) {
			addContextMenu("Spezialfähigkeit: " + getPlayerClass().getGovernmentEffectName(), panelGameClassGovermentEffectCover,
					e -> executeGovernmentAbilityMove());
		}
	}
	
	private void executeClassAbilityMove() {
		// TODO Auto-generated method stub
	}
	
	private void executeGovernmentAbilityMove() {
		// TODO Auto-generated method stub
	}
	
	private void addContextMenu(String itemText, Node node, EventHandler<ActionEvent> handler) {
		ContextMenu contextMenu = new ContextMenu();
		MenuItem menu = new MenuItem(itemText);
		menu.setOnAction(handler);
		contextMenu.getItems().add(menu);
		
		//show or hide the context menu
		node.setOnMouseClicked((e) -> {
			if (e.getButton() == MouseButton.SECONDARY) {
				contextMenu.show(node, e.getScreenX(), e.getScreenY());
			}
			else {
				contextMenu.hide();
			}
		});
	}
	
	private PlayerClass getPlayerClass() {
		GameManager gameManager = GameManager.getInstance();
		return gameManager.getPlayerClass(gameId, gameManager.getLocalPlayer());
	}
}
package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.Planet;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.PlayerBuilding;
import net.jfabricationgames.genesis_project.game.PlayerClass;
import net.jfabricationgames.genesis_project.user.User;

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
	private Label labelPlanetInfoBuilding4Player;
	@FXML
	private Label labelPlanetInfoBuilding4Type;
	@FXML
	private Label labelPlanetInfoBuilding5Player;
	@FXML
	private Label labelPlanetInfoBuilding5Type;
	
	@FXML
	private Label labelPlanetInfoBuildingNumber4;
	@FXML
	private Label labelPlanetInfoBuildingNumber5;
	
	@FXML
	private AnchorPane anchorPaneImage;
	
	private Game game;
	
	public PlanetInfoPaneController(Game game) {
		this.game = game;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//GuiUtils.loadImageToView("planets/planet_center.png", true, imageViewPlanet);
		
		//set a field for testing TODO delete after tests
		Field field = new Field(new Position(1, 1), Planet.GENESIS, 4);
		Player player = new Player(new User("Player1"), PlayerClass.YGDRACK);
		field.build(new PlayerBuilding(Building.MINE, player), 0);
		field.build(new PlayerBuilding(Building.COLONY, player), 1);
		field.build(new PlayerBuilding(Building.LABORATORY, player), 2);
		setSelectedField(field);
	}
	
	public void setSelectedField(Field field) {
		final String noInfo = "---";
		labelPlanetInfoPosition.setText(field.getPosition().asCoordinateString());
		Planet planet = field.getPlanet();
		if (planet != null) {
			labelPlanetInfoType.setText(planet.getTypeName());
			if (planet.getPrimaryResource() != null) {
				//genesis planets have no primary resource
				labelPlanetInfoPrimariResource.setText(planet.getPrimaryResource().getName());
			}
			else {
				labelPlanetInfoPrimariResource.setText(noInfo);
			}
			labelPlanetInfoDefence.setText(Integer.toString(field.calculateDefence(game)));
			labelPlanetInfoAlliances.setText(Integer.toString(field.getAlliances(game).size()));
			PlayerBuilding[] buildings = field.getBuildings();
			if (buildings.length > 3) {
				//it's the center planet that can have up to 5 buildings (one for each player) set all building labels visible
				labelPlanetInfoBuildingNumber4.setVisible(true);
				labelPlanetInfoBuildingNumber5.setVisible(true);
				labelPlanetInfoBuilding4Player.setVisible(true);
				labelPlanetInfoBuilding5Player.setVisible(true);
				labelPlanetInfoBuilding4Type.setVisible(true);
				labelPlanetInfoBuilding5Type.setVisible(true);
			}
			else {
				labelPlanetInfoBuildingNumber4.setVisible(false);
				labelPlanetInfoBuildingNumber5.setVisible(false);
				labelPlanetInfoBuilding4Player.setVisible(false);
				labelPlanetInfoBuilding5Player.setVisible(false);
				labelPlanetInfoBuilding4Type.setVisible(false);
				labelPlanetInfoBuilding5Type.setVisible(false);
				labelPlanetInfoBuilding4Player.setText("");
				labelPlanetInfoBuilding5Player.setText("");
				labelPlanetInfoBuilding4Type.setText("");
				labelPlanetInfoBuilding5Type.setText("");
			}
			Label[] playerLabels = new Label[] {labelPlanetInfoBuilding1Player, labelPlanetInfoBuilding2Player, labelPlanetInfoBuilding3Player,
					labelPlanetInfoBuilding4Player, labelPlanetInfoBuilding5Player};
			Label[] typeLabels = new Label[] {labelPlanetInfoBuilding1Type, labelPlanetInfoBuilding2Type, labelPlanetInfoBuilding3Type,
					labelPlanetInfoBuilding4Type, labelPlanetInfoBuilding5Type};
			for (int i = 0; i < buildings.length; i++) {
				PlayerBuilding building = buildings[i];
				if (building != null) {
					playerLabels[i].setText(building.getPlayer().toString());
					typeLabels[i].setText(building.getBuilding().getName());
				}
				else {
					playerLabels[i].setText(noInfo);
					typeLabels[i].setText(noInfo);
				}
			}
		}
		else {
			//space field
			labelPlanetInfoType.setText(noInfo);
			labelPlanetInfoPrimariResource.setText(noInfo);
			labelPlanetInfoDefence.setText(Integer.toString(field.calculateDefence(game)));
			labelPlanetInfoAlliances.setText(noInfo);
			//search for a space building except a satellite (satellites are not listed here)
			Optional<PlayerBuilding> spaceBuilding = field.getSpaceBuildings().stream()
					.filter(building -> building.getBuilding() == Building.DRONE || building.getBuilding() == Building.SPACE_STATION).findAny();
			if (spaceBuilding.isPresent()) {
				PlayerBuilding building = spaceBuilding.get();
				labelPlanetInfoBuilding1Player.setText(building.getPlayer().toString());
				labelPlanetInfoBuilding1Type.setText(building.getBuilding().getName());
			}
			else {
				labelPlanetInfoBuilding1Player.setText(noInfo);
				labelPlanetInfoBuilding1Type.setText(noInfo);
			}
			labelPlanetInfoBuilding2Player.setText(noInfo);
			labelPlanetInfoBuilding2Type.setText(noInfo);
			labelPlanetInfoBuilding3Player.setText(noInfo);
			labelPlanetInfoBuilding3Type.setText(noInfo);
		}
		
		//set the image
		PlanetLayout planetLayout = new PlanetLayout(game, field);
		anchorPaneImage.getChildren().add(planetLayout);
	}
}
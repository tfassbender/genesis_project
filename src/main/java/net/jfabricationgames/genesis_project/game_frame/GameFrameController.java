package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameFrameController implements Initializable {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameFrameController.class);
	
	@FXML
	private AnchorPane anchorBoardPane;
	@FXML
	private AnchorPane anchorClassPane;
	@FXML
	private AnchorPane anchorResearchPane;
	@FXML
	private AnchorPane anchorTechnologyPane;
	@FXML
	private AnchorPane anchorAlliancePanel;
	@FXML
	private AnchorPane anchorTurnPane;
	@FXML
	private AnchorPane anchorPlanetInfoPane;
	@FXML
	private AnchorPane anchorPlaningToolPane;
	@FXML
	private AnchorPane anchorAttackPane;
	@FXML
	private AnchorPane anchorPlayerOverviePane;
	@FXML
	private AnchorPane anchorGameOverviewPane;
	@FXML
	private AnchorPane anchorCostOverviewPane;
	@FXML
	private AnchorPane anchorChatPane;
	
	private Stage stage;
	
	private BoardPaneController boardPaneController;
	private ClassPaneController classPaneController;
	private ResearchPaneController researchPaneController;
	private TechnologyPaneController technologyPaneController;
	private AlliancePaneController alliancePaneController;
	private TurnPaneController turnPaneController;
	private PlanetInfoPaneController planetInfoPaneController;
	private PlaningToolPaneController planingToolPaneController;
	private AttackPaneController attackPaneController;
	private GameOverviewPaneController gameOverviewPaneController;
	private CostOverviewPaneController costOverviewPaneController;
	private ChatPaneController chatPaneController;
	
	private int gameId;
	
	public GameFrameController(int gameId) {
		this.gameId = gameId;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		GuiUtils guiUtils = new GuiUtils();
		boardPaneController = new BoardPaneController(gameId);
		guiUtils.insertPane("BoardPane.fxml", anchorBoardPane, boardPaneController, null);
		classPaneController = new ClassPaneController(gameId);
		guiUtils.insertPane("ClassPane.fxml", anchorClassPane, classPaneController, null);
		researchPaneController = new ResearchPaneController(gameId);
		guiUtils.insertPane("ResearchPane.fxml", anchorResearchPane, researchPaneController, null);
		technologyPaneController = new TechnologyPaneController(gameId);
		guiUtils.insertPane("TechnologyPane.fxml", anchorTechnologyPane, technologyPaneController, null);
		alliancePaneController = new AlliancePaneController(gameId);
		guiUtils.insertPane("AlliancePane.fxml", anchorAlliancePanel, alliancePaneController, null);
		turnPaneController = new TurnPaneController(gameId);
		guiUtils.insertPane("TurnPane.fxml", anchorTurnPane, turnPaneController, null);
		planetInfoPaneController = new PlanetInfoPaneController(gameId);
		guiUtils.insertPane("PlanetInfoPane.fxml", anchorPlanetInfoPane, planetInfoPaneController, null);
		planingToolPaneController = new PlaningToolPaneController(gameId);
		guiUtils.insertPane("PlaningToolPane.fxml", anchorPlaningToolPane, planingToolPaneController, null);
		attackPaneController = new AttackPaneController(gameId);
		guiUtils.insertPane("AttackPane.fxml", anchorAttackPane, attackPaneController, null);
		gameOverviewPaneController = new GameOverviewPaneController(gameId);
		guiUtils.insertPane("GameOverviewPane.fxml", anchorGameOverviewPane, gameOverviewPaneController, null);
		costOverviewPaneController = new CostOverviewPaneController(gameId);
		guiUtils.insertPane("CostOverviewPane.fxml", anchorCostOverviewPane, costOverviewPaneController, null);
		chatPaneController = new ChatPaneController(gameId);
		guiUtils.insertPane("ChatPane.fxml", anchorChatPane, chatPaneController, null);
	}
	
	/**
	 * Update the complete UI (after a new game object was loaded from the server)
	 */
	public void updateAll() {
		boardPaneController.updateAll();
		classPaneController.updateAll();
		researchPaneController.updateAll();
		technologyPaneController.updateAll();
		alliancePaneController.updateAll();
		turnPaneController.updateAll();
		planetInfoPaneController.updateAll();
		planingToolPaneController.updateAll();
		attackPaneController.updateAll();
		gameOverviewPaneController.updateAll();
		costOverviewPaneController.updateAll();
		chatPaneController.updateAll();
	}
	
	private void addIcon() {
		String iconPath = "net/jfabricationgames/genesis_project/images/basic/icon.png";
		stage.getIcons().add(new Image(iconPath));
	}
	
	public BoardPaneController getBoardPaneController() {
		return boardPaneController;
	}
	public ClassPaneController getClassPaneController() {
		return classPaneController;
	}
	public ResearchPaneController getResearchPaneController() {
		return researchPaneController;
	}
	public TechnologyPaneController getTechnologyPaneController() {
		return technologyPaneController;
	}
	public AlliancePaneController getAlliancePaneController() {
		return alliancePaneController;
	}
	public TurnPaneController getTurnPaneController() {
		return turnPaneController;
	}
	public PlanetInfoPaneController getPlanetInfoPaneController() {
		return planetInfoPaneController;
	}
	public PlaningToolPaneController getPlaningToolPaneController() {
		return planingToolPaneController;
	}
	public AttackPaneController getAttackPaneController() {
		return attackPaneController;
	}
	public GameOverviewPaneController getGameOverviewPaneController() {
		return gameOverviewPaneController;
	}
	public CostOverviewPaneController getCostOverviewPaneController() {
		return costOverviewPaneController;
	}
	public ChatPaneController getChatPaneController() {
		return chatPaneController;
	}
	
	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
		if (stage != null) {
			addIcon();
		}
	}
}
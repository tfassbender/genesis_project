package net.jfabricationgames.genesis_project.game_frame;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import net.jfabricationgames.genesis_project.game.Game;

public class GameFrameController implements Initializable {
	
	private final Logger LOGGER = LogManager.getLogger(getClass());
	
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
	
	private Game game;
	
	public GameFrameController(Game game) {
		this.game = game;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		boardPaneController = new BoardPaneController(game, game.getBoard());
		insertPane("BoardPane.fxml", anchorBoardPane, boardPaneController, null);
		classPaneController = new ClassPaneController(game.getLocalPlayer());
		insertPane("ClassPane.fxml", anchorClassPane, classPaneController, null);
		researchPaneController = new ResearchPaneController(game.getResearchManager(), game.getLocalPlayer());
		insertPane("ResearchPane.fxml", anchorResearchPane, researchPaneController, null);
		technologyPaneController = new TechnologyPaneController(game.getLocalPlayer());
		insertPane("TechnologyPane.fxml", anchorTechnologyPane, technologyPaneController, null);
		alliancePaneController = new AlliancePaneController(game.getLocalPlayer());
		insertPane("AlliancePane.fxml", anchorAlliancePanel, alliancePaneController, null);
		turnPaneController = new TurnPaneController(game.getTurnManager());
		insertPane("TurnPane.fxml", anchorTurnPane, turnPaneController, null);
		planetInfoPaneController = new PlanetInfoPaneController(game);
		insertPane("PlanetInfoPane.fxml", anchorPlanetInfoPane, planetInfoPaneController, null);
		planingToolPaneController = new PlaningToolPaneController(game.getLocalPlayer());
		insertPane("PlaningToolPane.fxml", anchorPlaningToolPane, planingToolPaneController, null);
		attackPaneController = new AttackPaneController();
		insertPane("AttackPane.fxml", anchorAttackPane, attackPaneController, null);
		gameOverviewPaneController = new GameOverviewPaneController(game, game.getLocalPlayer());
		insertPane("GameOverviewPane.fxml", anchorGameOverviewPane, gameOverviewPaneController, null);
		costOverviewPaneController = new CostOverviewPaneController(game, game.getLocalPlayer());
		insertPane("CostOverviewPane.fxml", anchorCostOverviewPane, costOverviewPaneController, null);
		chatPaneController = new ChatPaneController();
		insertPane("ChatPane.fxml", anchorChatPane, chatPaneController, null);
	}
	
	private void insertPane(String fxmlFileName, AnchorPane parent, Initializable controller, String cssFileName) {
		try {
			URL fxmlUrl = getClass().getResource(fxmlFileName);
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
			fxmlLoader.setController(controller);
			Parent pane = fxmlLoader.load();
			if (cssFileName != null) {
				pane.getStylesheets().add(getClass().getResource(cssFileName).toExternalForm());
			}
			parent.getChildren().add(pane);
			AnchorPane.setBottomAnchor(pane, 0d);
			AnchorPane.setTopAnchor(pane, 0d);
			AnchorPane.setLeftAnchor(pane, 0d);
			AnchorPane.setRightAnchor(pane, 0d);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			LOGGER.error("An exception occured while inserting a pane to the main frame", ioe);
		}
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
}
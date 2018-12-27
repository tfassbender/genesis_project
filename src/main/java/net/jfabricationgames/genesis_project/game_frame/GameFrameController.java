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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		insertPane("BoardPane.fxml", anchorBoardPane, new BoardPaneController(), null);
		insertPane("ClassPane.fxml", anchorClassPane, new ClassPaneController(), null);
		insertPane("ResearchPane.fxml", anchorResearchPane, new ResearchPaneController(), null);
		insertPane("TechnologyPane.fxml", anchorTechnologyPane, new TechnologyPaneController(), null);
		insertPane("AlliancePane.fxml", anchorAlliancePanel, new AlliancePaneController(), null);
		insertPane("TurnPane.fxml", anchorTurnPane, new TurnPaneController(), null);
		insertPane("PlanetInfoPane.fxml", anchorPlanetInfoPane, new PlanetInfoPaneController(), null);
		insertPane("PlaningToolPane.fxml", anchorPlaningToolPane, new PlaningToolPaneController(), null);
		insertPane("AttackPane.fxml", anchorAttackPane, new AttackPaneController(), null);
		insertPane("GameOverviewPane.fxml", anchorGameOverviewPane, new GameOverviewPaneController(), null);
		insertPane("CostOverviewPane.fxml", anchorCostOverviewPane, new CostOverviewPaneController(), null);
		insertPane("ChatPane.fxml", anchorChatPane, new ChatPaneController(), null);
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
}
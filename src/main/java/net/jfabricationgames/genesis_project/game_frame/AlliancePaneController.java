package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
		
		GuiUtils.loadImageToView("basic/cross.png", true, imageAllianceBonusSelectedMilitaryRange1);
		GuiUtils.loadImageToView("basic/cross.png", true, imageAllianceBonusSelectedMilitaryRange2);
		GuiUtils.loadImageToView("basic/cross.png", true, imageAllianceBonusSelectedPrimaryResources1);
		GuiUtils.loadImageToView("basic/cross.png", true, imageAllianceBonusSelectedPrimaryResources2);
		GuiUtils.loadImageToView("basic/cross.png", true, imageAllianceBonusSelectedSecundaryResources1);
		GuiUtils.loadImageToView("basic/cross.png", true, imageAllianceBonusSelectedSecundaryResources2);
		GuiUtils.loadImageToView("basic/cross.png", true, imageAllianceBonusSelectedScientists1);
		GuiUtils.loadImageToView("basic/cross.png", true, imageAllianceBonusSelectedScientists2);
		GuiUtils.loadImageToView("basic/cross.png", true, imageAllianceBonusSelectedResearchPoints1);
		GuiUtils.loadImageToView("basic/cross.png", true, imageAllianceBonusSelectedResearchPoints2);
		GuiUtils.loadImageToView("basic/cross.png", true, imageAllianceBonusSelectedPoints1);
		GuiUtils.loadImageToView("basic/cross.png", true, imageAllianceBonusSelectedPoints2);
	}
}
package net.jfabricationgames.genesis_project.move;

import java.util.List;
import java.util.Objects;

import net.jfabricationgames.genesis_project.game.AllianceBonus;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.ResearchArea;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Technology;

/**
 * Create a move using a builder pattern.
 */
public class MoveBuilder {
	
	private Game game;
	
	private boolean buildingMove = false;
	
	private MoveType type;
	private Player player;
	private Field field;
	private Building building;
	private ResearchArea researchArea;
	private ResearchResources researchResources;
	private Technology technology;
	private List<Field> alliancePlanets;
	private List<Field> satelliteFields;
	private AllianceBonus allianceBonus;
	private int allianceBonusIndex;
	private boolean pass;
	
	public MoveBuilder(Game game) {
		Objects.requireNonNull(game, "The game object mussn't be null.");
		this.game = game;
	}
	
	/**
	 * Start building a new move.</br>
	 * Removes all old field values to construct a completely new move.<br>
	 * 
	 * @throws IllegalStateException
	 *         An {@link IllegalStateException} is thrown when this method is called while the last move is still being constructed.
	 */
	public void buildMove() throws IllegalStateException {
		if (buildingMove) {
			throw new IllegalStateException("Can't build a new move while there is already a move builded.");
		}
		else {
			buildingMove = true;
			type = null;
			player = null;
			field = null;
			building = null;
			researchArea = null;
			researchResources = null;
			technology = null;
			alliancePlanets = null;
			satelliteFields = null;
			allianceBonus = null;
			allianceBonusIndex = -1;
			pass = false;
		}
	}
	
	/**
	 * Build and return the move that was constructed
	 * 
	 * @throws IllegalStateException
	 *         An {@link IllegalStateException} is thrown when a move this method is called while there is no move under construction.
	 */
	public IMove getMove() throws IllegalStateException {
		if (buildingMove) {
			IMove move = new Move(game, type, player, field, building, researchArea, researchResources, technology, alliancePlanets, satelliteFields,
					allianceBonus, allianceBonusIndex, pass);
			buildingMove = false;
			return move;
		}
		else {
			throw new IllegalStateException("Can't build a move because there is currently no move under construction.");
		}
	}
	
	/**
	 * Abort the building of a move without constructing the move (no matter if there is really a move that is built at the time this method is
	 * called).
	 */
	public void abortBuild() {
		buildingMove = false;
	}
	
	public boolean isBuildingMove() {
		return buildingMove;
	}
	
	public MoveType getType() {
		return type;
	}
	public void setType(MoveType type) {
		this.type = type;
	}
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	
	public Building getBuilding() {
		return building;
	}
	public void setBuilding(Building building) {
		this.building = building;
	}
	
	public ResearchArea getResearchArea() {
		return researchArea;
	}
	public void setResearchArea(ResearchArea researchArea) {
		this.researchArea = researchArea;
	}
	
	public ResearchResources getResearchResources() {
		return researchResources;
	}
	public void setResearchResources(ResearchResources researchResources) {
		this.researchResources = researchResources;
	}
	
	public Technology getTechnology() {
		return technology;
	}
	public void setTechnology(Technology technology) {
		this.technology = technology;
	}
	
	public List<Field> getAlliancePlanets() {
		return alliancePlanets;
	}
	public void setAlliancePlanets(List<Field> alliancePlanets) {
		this.alliancePlanets = alliancePlanets;
	}
	
	public List<Field> getSatelliteFields() {
		return satelliteFields;
	}
	public void setSatelliteFields(List<Field> satelliteFields) {
		this.satelliteFields = satelliteFields;
	}
	
	public AllianceBonus getAllianceBonus() {
		return allianceBonus;
	}
	public void setAllianceBonus(AllianceBonus allianceBonus) {
		this.allianceBonus = allianceBonus;
	}
	
	public int getAllianceBonusIndex() {
		return allianceBonusIndex;
	}
	public void setAllianceBonusIndex(int allianceBonusIndex) {
		this.allianceBonusIndex = allianceBonusIndex;
	}
	
	public boolean isPass() {
		return pass;
	}
	public void setPass(boolean pass) {
		this.pass = pass;
	}
}
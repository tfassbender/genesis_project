package net.jfabricationgames.genesis_project.move;

import java.util.List;

import net.jfabricationgames.genesis_project.game.AllianceBonus;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.ResearchArea;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Technology;

/**
 * Create a move using a builder pattern.
 */
public class MoveBuilder {
	
	private MoveType type;
	private String player;
	private Field field;
	private Building building;
	private ResearchArea researchArea;
	private ResearchResources researchResources;
	private Technology technology;
	private List<Field> alliancePlanets;
	private List<Field> satelliteFields;
	private AllianceBonus allianceBonus;
	private int allianceBonusIndex;
	
	public MoveBuilder() {
		
	}
	
	/**
	 * Build and return the move that was constructed.
	 */
	public IMove build() throws IllegalStateException {
		IMove move = new Move(type, player, field, building, researchArea, researchResources, technology, alliancePlanets, satelliteFields,
				allianceBonus, allianceBonusIndex);
		return move;
	}
	
	/**
	 * Reset all old field values to construct a completely new move.
	 */
	public void reset() {
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
	}
	
	public MoveType getType() {
		return type;
	}
	public MoveBuilder setType(MoveType type) {
		this.type = type;
		return this;
	}
	
	public String getPlayer() {
		return player;
	}
	public MoveBuilder setPlayer(String player) {
		this.player = player;
		return this;
	}
	
	public Field getField() {
		return field;
	}
	public MoveBuilder setField(Field field) {
		this.field = field;
		return this;
	}
	
	public Building getBuilding() {
		return building;
	}
	public MoveBuilder setBuilding(Building building) {
		this.building = building;
		return this;
	}
	
	public ResearchArea getResearchArea() {
		return researchArea;
	}
	public MoveBuilder setResearchArea(ResearchArea researchArea) {
		this.researchArea = researchArea;
		return this;
	}
	
	public ResearchResources getResearchResources() {
		return researchResources;
	}
	public MoveBuilder setResearchResources(ResearchResources researchResources) {
		this.researchResources = researchResources;
		return this;
	}
	
	public Technology getTechnology() {
		return technology;
	}
	public MoveBuilder setTechnology(Technology technology) {
		this.technology = technology;
		return this;
	}
	
	public List<Field> getAlliancePlanets() {
		return alliancePlanets;
	}
	public MoveBuilder setAlliancePlanets(List<Field> alliancePlanets) {
		this.alliancePlanets = alliancePlanets;
		return this;
	}
	
	public List<Field> getSatelliteFields() {
		return satelliteFields;
	}
	public MoveBuilder setSatelliteFields(List<Field> satelliteFields) {
		this.satelliteFields = satelliteFields;
		return this;
	}
	
	public AllianceBonus getAllianceBonus() {
		return allianceBonus;
	}
	public MoveBuilder setAllianceBonus(AllianceBonus allianceBonus) {
		this.allianceBonus = allianceBonus;
		return this;
	}
	
	public int getAllianceBonusIndex() {
		return allianceBonusIndex;
	}
	public MoveBuilder setAllianceBonusIndex(int allianceBonusIndex) {
		this.allianceBonusIndex = allianceBonusIndex;
		return this;
	}
	
	public boolean isPass() {
		return type == MoveType.PASS;
	}
}
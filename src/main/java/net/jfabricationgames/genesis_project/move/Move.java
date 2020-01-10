package net.jfabricationgames.genesis_project.move;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.jfabricationgames.genesis_project.game.AllianceBonus;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.PlayerClass;
import net.jfabricationgames.genesis_project.game.ResearchArea;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Technology;

public class Move implements IMove {
	
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
	private PlayerClass playerClass;
	private boolean pass;
	
	/**
	 * DO NOT USE - empty constructor for json deserialization
	 */
	@Deprecated
	public Move() {
		
	}
	
	protected Move(MoveType type, String player, Field field, Building building, ResearchArea researchArea, ResearchResources researchResources,
			Technology technology, List<Field> alliancePlanets, List<Field> satelliteFields, AllianceBonus allianceBonus, int allianceBonusIndex,
			PlayerClass playerClass) {
		this.type = type;
		this.player = player;
		this.field = field;
		this.building = building;
		this.researchArea = researchArea;
		this.researchResources = researchResources;
		this.technology = technology;
		this.alliancePlanets = alliancePlanets;
		this.satelliteFields = satelliteFields;
		this.allianceBonus = allianceBonus;
		this.allianceBonusIndex = allianceBonusIndex;
		this.playerClass = playerClass;
		this.pass = (type == MoveType.PASS);
	}
	
	@Override
	public MoveType getType() {
		return type;
	}
	
	@Override
	public String getPlayer() {
		return player;
	}
	
	@Override
	public Field getField() {
		return field;
	}
	
	@Override
	public Building getBuilding() {
		return building;
	}
	
	@Override
	public ResearchArea getResearchArea() {
		return researchArea;
	}
	
	@Override
	public ResearchResources getResearchResources() {
		return researchResources;
	}
	
	@Override
	public Technology getTechnology() {
		return technology;
	}
	
	@Override
	public List<Field> getAlliancePlanets() {
		return alliancePlanets;
	}
	
	@Override
	public List<Field> getSatelliteFields() {
		return satelliteFields;
	}
	
	@Override
	public AllianceBonus getAllianceBonus() {
		return allianceBonus;
	}
	
	@Override
	public int getAllianceBonusIndex() {
		return allianceBonusIndex;
	}
	
	@Override
	public PlayerClass getPlayerClass() {
		return playerClass;
	}
	
	@Override
	@JsonIgnore
	public boolean isPassing() {
		return pass;
	}
}
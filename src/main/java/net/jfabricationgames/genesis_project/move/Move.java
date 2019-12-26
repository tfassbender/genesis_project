package net.jfabricationgames.genesis_project.move;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.jfabricationgames.genesis_project.game.AllianceBonus;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.ResearchArea;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Technology;

public class Move implements IMove {
	
	private Game game;
	
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
	
	/**
	 * DO NOT USE - empty constructor for json deserialization
	 */
	@Deprecated
	public Move() {
		
	}
	
	protected Move(Game game, MoveType type, Player player, Field field, Building building, ResearchArea researchArea,
			ResearchResources researchResources, Technology technology, List<Field> alliancePlanets, List<Field> satelliteFields,
			AllianceBonus allianceBonus, int allianceBonusIndex) {
		this.game = game;
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
		this.pass = (type == MoveType.PASS);
	}
	
	@Override
	public void execute() {
		game.executeMove(this);
	}
	
	@Override
	@JsonIgnore
	public boolean isExecutable() {
		return game.isMoveExecutable(this);
	}
	
	@Override
	public MoveType getType() {
		return type;
	}
	
	@Override
	public Player getPlayer() {
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
	@JsonIgnore
	public boolean isPassing() {
		return pass;
	}
}
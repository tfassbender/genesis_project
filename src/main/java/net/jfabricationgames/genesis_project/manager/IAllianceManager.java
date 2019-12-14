package net.jfabricationgames.genesis_project.manager;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import net.jfabricationgames.genesis_project.game.Alliance;
import net.jfabricationgames.genesis_project.game.AllianceBonus;
import net.jfabricationgames.genesis_project.game.Field;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({@JsonSubTypes.Type(value = AllianceManager.class, name = "AllianceManager"),
		@JsonSubTypes.Type(value = AllianceManagerCompositum.class, name = "AllianceManagerCompositum")})
public interface IAllianceManager {
	
	public List<Alliance> getAlliances();
	public List<AllianceBonus> getAllianceBonuses();
	
	/**
	 * Creates a new alliance consulting of the given planets and adds it to the list of alliances.</br>
	 * The satellites for the alliance are built and the points are collected.
	 * 
	 * The input is not tested for validity.<br>
	 * Use isAllianceValid(List<Field>, List<Field>, AllianceBonus) to check this before adding an alliance.
	 */
	public void addAlliance(List<Field> planets, List<Field> satelliteFields, AllianceBonus bonus, int bonusIndex);
	/**
	 * Checks whether an alliance would be valid.</br>
	 * 
	 * Checked criteria is:</br>
	 * - enough buildings</br>
	 * - government or city included</br>
	 * - enough planets</br>
	 * - enough opponent's buildings</br>
	 * - none of the planets already in an alliance of this player</br>
	 * - the bonus is not already taken
	 */
	public boolean isAllianceValid(List<Field> planets, List<Field> satelliteFields, AllianceBonus bonus, int bonusIndex);
	
	/**
	 * Checks whether the bonus is already taken by a player.
	 * 
	 * The bonus can be any bonus.</br>
	 * The bonus index has to be 0 or 1 (because there are two copies of each bonus).
	 */
	public boolean isAllianceBonusTaken(AllianceBonus bonus, int bonusIndex);
	public void setAllianceBonusTaken(AllianceBonus bonus, int bonusIndex, boolean taken);
	
	public int getDefenseBuildingAdditionalRange();
}
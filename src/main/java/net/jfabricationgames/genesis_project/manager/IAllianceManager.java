package net.jfabricationgames.genesis_project.manager;

import java.util.List;

import net.jfabricationgames.genesis_project.game.Alliance;
import net.jfabricationgames.genesis_project.game.AllianceBonus;
import net.jfabricationgames.genesis_project.game.Field;

public interface IAllianceManager {
	
	public List<Alliance> getAlliances();
	public List<AllianceBonus> getAllianceBonuses();
	
	public void addAlliance(Alliance alliance);
	/**
	 * Creates a new alliance consulting of the given planets and adds it to the list of alliances.
	 * 
	 * The input is not tested for validity.<br>
	 * Use isAllianceValid(List<Field>, List<Field>, AllianceBonus) to check this before adding an alliance.
	 */
	public void addAlliance(List<Field> planets, List<Field> satelliteFields, AllianceBonus bonus);
	/**
	 * Checks whether an alliance would be valid. </br>
	 * 
	 * Checked criteria is:</br>
	 * - enough buildings</br>
	 * - government or city included</br>
	 * - enough planets</br>
	 * - enough opponent's buildings</br>
	 * - none of the planets already in an alliance of this player
	 */
	public boolean isAllianceValid(List<Field> planets, List<Field> satelliteFields, AllianceBonus bonus);
}
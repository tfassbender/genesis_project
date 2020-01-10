package net.jfabricationgames.genesis_project.manager;

import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.BooleanProperty;
import net.jfabricationgames.genesis_project.game.Alliance;
import net.jfabricationgames.genesis_project.game.AllianceBonus;
import net.jfabricationgames.genesis_project.game.AllianceBuilder;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.Game;

public class AllianceManagerCompositum implements IAllianceManager {
	
	private Game game;
	
	private AllianceManager globalAllianceManager;
	
	/**
	 * DO NOT USE - empty constructor for json deserialization
	 */
	@Deprecated
	public AllianceManagerCompositum() {
		
	}
	
	public AllianceManagerCompositum(Game game) {
		this.game = game;
		globalAllianceManager = new AllianceManager(null);
	}
	
	@Override
	public List<Alliance> getAlliances() {
		List<Alliance> allAlliances = game.getPlayers().stream().flatMap(player -> player.getAllianceManager().getAlliances().stream())
				.collect(Collectors.toList());
		return allAlliances;
	}
	
	@Override
	public List<AllianceBonus> getAllianceBonuses() {
		List<AllianceBonus> allAllianceBonuses = game.getPlayers().stream()
				.flatMap(player -> player.getAllianceManager().getAllianceBonuses().stream()).collect(Collectors.toList());
		return allAllianceBonuses;
	}
	
	@Override
	public void addAlliance(List<Field> planets, List<Field> satelliteFields, AllianceBonus bonus, int bonusIndex) {
		throw new UnsupportedOperationException("Adding an alliance is not possible in the composite implementation.");
	}
	
	@Override
	public boolean isAllianceValid(List<Field> planets, List<Field> satelliteFields, AllianceBonus bonus, int bonusIndex) {
		throw new UnsupportedOperationException("Adding an alliance is not possible in the composite implementation.");
	}
	
	@Override
	public boolean isAllianceBonusTaken(AllianceBonus bonus, int bonusIndex) {
		return globalAllianceManager.isAllianceBonusTaken(bonus, bonusIndex);
	}
	
	@Override
	public void setAllianceBonusTaken(AllianceBonus bonus, int bonusIndex, boolean taken) {
		globalAllianceManager.setAllianceBonusTaken(bonus, bonusIndex, taken);
	}
	
	@Override
	public BooleanProperty getAllianceBonusTakenProperty(AllianceBonus bonus, int bonusIndex) {
		return globalAllianceManager.getAllianceBonusTakenProperty(bonus, bonusIndex);
	}
	
	@Override
	public int getDefenseBuildingAdditionalRange() {
		throw new UnsupportedOperationException("Calculating the additional defense is not possible in the composite implementation.");
	}

	@Override
	public AllianceBuilder getAllianceBuilder() {
		throw new UnsupportedOperationException("The composite implementation does not contain an alliance builder.");
	}
}
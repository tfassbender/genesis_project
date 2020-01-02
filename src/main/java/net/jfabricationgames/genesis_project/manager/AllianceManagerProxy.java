package net.jfabricationgames.genesis_project.manager;

import java.util.List;

import javafx.beans.property.BooleanProperty;
import net.jfabricationgames.genesis_project.game.Alliance;
import net.jfabricationgames.genesis_project.game.AllianceBonus;
import net.jfabricationgames.genesis_project.game.AllianceBuilder;
import net.jfabricationgames.genesis_project.game.Field;

public class AllianceManagerProxy implements IAllianceManager {
	
	private IAllianceManager manager;
	
	public AllianceManagerProxy(IAllianceManager manager) {
		this.manager = manager;
	}
	
	@Override
	public List<Alliance> getAlliances() {
		return manager.getAlliances();
	}
	
	@Override
	public List<AllianceBonus> getAllianceBonuses() {
		return manager.getAllianceBonuses();
	}
	
	@Override
	public void addAlliance(List<Field> planets, List<Field> satelliteFields, AllianceBonus bonus, int bonusIndex) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public boolean isAllianceValid(List<Field> planets, List<Field> satelliteFields, AllianceBonus bonus, int bonusIndex) {
		return manager.isAllianceValid(planets, satelliteFields, bonus, bonusIndex);
	}
	
	@Override
	public boolean isAllianceBonusTaken(AllianceBonus bonus, int bonusIndex) {
		return manager.isAllianceBonusTaken(bonus, bonusIndex);
	}
	
	@Override
	public void setAllianceBonusTaken(AllianceBonus bonus, int bonusIndex, boolean taken) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public int getDefenseBuildingAdditionalRange() {
		return manager.getDefenseBuildingAdditionalRange();
	}
	
	@Override
	public BooleanProperty getAllianceBonusTakenProperty(AllianceBonus bonus, int bonusIndex) {
		return manager.getAllianceBonusTakenProperty(bonus, bonusIndex);
	}
	
	@Override
	public AllianceBuilder getAllianceBuilder() {
		return manager.getAllianceBuilder();
	}
}
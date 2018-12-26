package net.jfabricationgames.genesis_project.move;

import java.util.List;

import net.jfabricationgames.genesis_project.game.AllianceBonus;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.ResearchArea;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Technology;

public interface IMove {
	
	public void execute();
	public boolean isExecutable();
	
	public MoveType getType();
	public Player getPlayer();
	
	public Field getField();
	public Building getBuilding();
	public ResearchArea getResearchArea();
	public ResearchResources getResearchResources();
	public Technology getTechnology();
	public List<Field> getAlliancePlanets();
	public List<Field> getSatelliteFields();
	public AllianceBonus getAllianceBonus();
	public boolean isPassing();
}
package net.jfabricationgames.genesis_project.manager;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import net.jfabricationgames.genesis_project.game.Technology;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({@JsonSubTypes.Type(value = TechnologyManager.class, name = "TechnologyManager")})
public interface ITechnologyManager {
	
	public boolean isTechnologyExplored(Technology technology);
	public void exploreTechnology(Technology technology);
	
	public int getDefenseBuildingAdditionalRange();
}
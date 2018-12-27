package net.jfabricationgames.genesis_project.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.annotations.VisibleForTesting;

import net.jfabricationgames.genesis_project.game.Alliance;
import net.jfabricationgames.genesis_project.game.AllianceBonus;
import net.jfabricationgames.genesis_project.game.Board;
import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.BuildingResources;
import net.jfabricationgames.genesis_project.game.Constants;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.Planet;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.PlayerBuilding;
import net.jfabricationgames.genesis_project.game.Resource;

public class AllianceManager implements IAllianceManager {
	
	private List<Alliance> alliances;
	
	private Player player;
	
	public AllianceManager(Player player) {
		this.player = player;
		alliances = new ArrayList<Alliance>(3);
	}
	
	@Override
	public List<Alliance> getAlliances() {
		return alliances;
	}
	
	@Override
	public List<AllianceBonus> getAllianceBonuses() {
		return alliances.stream().map(a -> a.getBonus()).collect(Collectors.toList());
	}
	
	@Override
	public void addAlliance(Alliance alliance) {
		alliances.add(alliance);
	}
	@Override
	public void addAlliance(List<Field> planets, List<Field> satelliteFields, AllianceBonus bonus) {
		//build the satellites
		IBuildingManager buildingManager = player.getBuildingManager();
		for (Field satelliteField : satelliteFields) {
			buildingManager.build(Building.SATELLITE, satelliteField);
		}
		
		Alliance alliance = new Alliance(planets, satelliteFields, bonus);
		alliances.add(alliance);
		player.getPointManager().addPoints(bonus.getPoints());
	}
	
	@Override
	public boolean isAllianceValid(List<Field> planets, List<Field> satelliteFields, AllianceBonus bonus) {
		int numPlanets = planets.size();//planets included in the alliance
		int numOpponentPlanets = 0;//planets with at least one opponent building
		int numPlayerBuildings = 0;//buildings of the player that creates the alliance
		int numOpponentBuildings = 0;//buildings of other players
		boolean govermentOrCityIncluded = false;//a government or a city of the player that creates the alliance is included
		boolean allPlanetsValid = true;//all planets must contain at least one planet of the player (the central planet is not valid)
		boolean satelliteFieldsValid = true;//the satellite fields connect the planets and can be built on the given fields
		boolean centerPlanetIncluded = false;//the center planet can not be used for an alliance
		boolean satelliteResourcesAvailable = false;//the player has to have enough resources to build the satellites
		
		for (Field field : planets) {
			boolean planetValid = true;
			boolean opponentOnPlanet = false;
			int playersBuildings = 0;
			for (PlayerBuilding building : field.getBuildings()) {
				if (building != null) {
					if (building.getPlayer().equals(getPlayer())) {
						playersBuildings++;
						numPlayerBuildings++;
						if (building.getBuilding() == Building.GOVERNMENT || building.getBuilding() == Building.CITY) {
							govermentOrCityIncluded = true;
						}
					}
					else {
						opponentOnPlanet = true;
						numOpponentBuildings++;
					}
				}
			}
			if (opponentOnPlanet) {
				numOpponentPlanets++;
			}
			planetValid &= playersBuildings > 0;//at least one building of the player
			allPlanetsValid &= planetValid;
			centerPlanetIncluded |= field.isPlanetField() && field.getPlanet() == Planet.CENTER;
		}
		
		satelliteFieldsValid = isSatelliteConnectionValid(planets, satelliteFields);
		satelliteResourcesAvailable = isSatelliteResourcesAvailable(satelliteFields.size());
		
		boolean allianceValid = true;
		
		allianceValid &= numPlanets >= Constants.ALLIANCE_MIN_PLANETS;
		allianceValid &= numOpponentPlanets >= Constants.ALLIANCE_MIN_PLANETS_OTHER_PLAYERS;
		allianceValid &= numPlayerBuildings >= Constants.ALLIANCE_MIN_BUILDINGS;
		allianceValid &= numOpponentBuildings >= Constants.ALLIANCE_MIN_BUILDINGS_OTHER_PLAYERS;
		allianceValid &= govermentOrCityIncluded;
		allianceValid &= allPlanetsValid;
		allianceValid &= satelliteFieldsValid;
		allianceValid &= !centerPlanetIncluded;
		allianceValid &= satelliteResourcesAvailable;
		allianceValid &= bonus != null;
		
		return allianceValid;
	}
	
	@VisibleForTesting
	protected boolean isSatelliteResourcesAvailable(int satellites) {
		IBuildingManager manager = getPlayer().getBuildingManager();
		
		//use a dummy field here because the implementation only needs to know what type of planet is on the field
		Field field = new Field(new Position(0, 0), null);
		BuildingResources satelliteResources = manager.getResourcesNeededForBuilding(Building.SATELLITE, field);
		
		BuildingResources allSatelliteResources = new BuildingResources();
		for (Resource resource : BuildingResources.BUILDING_RESOURCES) {
			allSatelliteResources.addResources(resource, satelliteResources.getResources(resource) * satellites);
		}
		
		IResourceManager resourceManager = getPlayer().getResourceManager();
		boolean resourcesAvailable = resourceManager.isResourcesAvailable(allSatelliteResources);
		
		return resourcesAvailable;
	}
	
	@VisibleForTesting
	protected boolean isSatelliteConnectionValid(List<Field> planets, List<Field> satelliteFields) {
		boolean connectionValid = true;
		
		boolean allFieldsConnected = true;//all satellites connected to each other
		boolean allPlanetsConnected = true;//all planets connected to the satellites
		boolean noOtherPlanetsTouched = true;//no planets that contain buildings of the player are touched by the satellites
		boolean noOtherSatellitesTouched = true;//no other satellites of the player are touched
		boolean onlySpaceFieldsUsed = true;//the satellite fields are all space fields
		
		//neighbor fields can be using satellites or direct links between planets
		List<Field> satellitesAndPlanets = new ArrayList<Field>(satelliteFields);
		satellitesAndPlanets.addAll(planets);
		List<Field> allNeighborFields = satellitesAndPlanets.stream().flatMap(field -> getBoard().getNeighbourFields(field).stream()).distinct()
				.collect(Collectors.toList());
		
		allFieldsConnected = isAllFieldsConnected(satellitesAndPlanets);
		for (Field satelliteField : satelliteFields) {
			onlySpaceFieldsUsed &= !satelliteField.isPlanetField();
		}
		for (Field planetField : planets) {
			allPlanetsConnected &= allNeighborFields.contains(planetField);
		}
		for (Field neighborField : allNeighborFields) {
			boolean playersPlanet = false;
			boolean otherSatelliteTouched = false;
			if (neighborField.isPlanetField()) {
				if (!planets.contains(neighborField)) {
					//neighbor fields contain a planet that is not one of the alliance planets
					for (PlayerBuilding building : neighborField.getBuildings()) {
						playersPlanet |= building != null && building.getPlayer().equals(getPlayer());
					}
				}
			}
			else {
				for (PlayerBuilding spaceBuilding : neighborField.getSpaceBuildings()) {
					//no satellite of the player must be touched because the new satellites of this alliance are not yet built
					otherSatelliteTouched |= spaceBuilding.getBuilding() == Building.SATELLITE && spaceBuilding.getPlayer().equals(getPlayer());
				}
			}
			
			noOtherPlanetsTouched &= !playersPlanet;
			noOtherSatellitesTouched &= !otherSatelliteTouched;
		}
		
		connectionValid &= allFieldsConnected;
		connectionValid &= allPlanetsConnected;
		connectionValid &= noOtherPlanetsTouched;
		connectionValid &= noOtherSatellitesTouched;
		connectionValid &= onlySpaceFieldsUsed;
		
		return connectionValid;
	}
	
	@VisibleForTesting
	protected boolean isAllFieldsConnected(List<Field> satellitesAndPlanets) {
		//check whether the fields are connected using a spanning tree
		List<Field> reachedFields = new ArrayList<Field>();
		List<Field> currentFields = new ArrayList<Field>();
		List<Field> nextFields = new ArrayList<Field>();
		//start with any field in the list
		nextFields.add(satellitesAndPlanets.get(0));
		
		while (!nextFields.isEmpty()) {
			reachedFields.addAll(nextFields);
			currentFields = nextFields;
			nextFields = new ArrayList<Field>();
			
			//add every field to nextFields that has not been reached, is reachable from any field in currentFields and is in satellitesAndPlanets
			for (Field current : currentFields) {
				List<Field> neighbors = getBoard().getNeighbourFields(current);
				for (Field field : neighbors) {
					if (satellitesAndPlanets.contains(field) && !reachedFields.contains(field) && !currentFields.contains(field)
							&& !nextFields.contains(field)) {
						nextFields.add(field);
					}
				}
			}
		}
		
		//when all fields have been reached the fields are connected
		boolean fieldsConnected = true;
		
		for (Field field : satellitesAndPlanets) {
			fieldsConnected &= reachedFields.contains(field);
		}
		
		return fieldsConnected;
	}
	
	@VisibleForTesting
	protected Player getPlayer() {
		return player;
	}
	@VisibleForTesting
	protected Board getBoard() {
		return player.getGame().getBoard();
	}
}
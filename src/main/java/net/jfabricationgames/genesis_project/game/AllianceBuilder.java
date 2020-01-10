package net.jfabricationgames.genesis_project.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import net.jfabricationgames.genesis_project.json.deserializer.CustomBooleanPropertyDeserializer;
import net.jfabricationgames.genesis_project.json.deserializer.CustomIntegerPropertyDeserializer;
import net.jfabricationgames.genesis_project.json.serializer.CustomBooleanPropertySerializer;
import net.jfabricationgames.genesis_project.json.serializer.CustomIntegerPropertySerializer;

public class AllianceBuilder {
	
	private Player player;
	
	//Custom serialization for observable lists via customized getter and setter methods
	private ObservableList<Field> planets;
	private ObservableList<Field> connectingSatellites;
	private AllianceBonus bonus;
	private int bonusIndex;
	
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	@JsonDeserialize(using = CustomIntegerPropertyDeserializer.class)
	private IntegerProperty numPlanets;
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	@JsonDeserialize(using = CustomIntegerPropertyDeserializer.class)
	private IntegerProperty numNeighbourPlanets;
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	@JsonDeserialize(using = CustomIntegerPropertyDeserializer.class)
	private IntegerProperty numBuildings;
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	@JsonDeserialize(using = CustomIntegerPropertyDeserializer.class)
	private IntegerProperty numNeighbourBuildings;
	@JsonSerialize(using = CustomIntegerPropertySerializer.class)
	@JsonDeserialize(using = CustomIntegerPropertyDeserializer.class)
	private IntegerProperty numMainBuildings;
	@JsonSerialize(using = CustomBooleanPropertySerializer.class)
	@JsonDeserialize(using = CustomBooleanPropertyDeserializer.class)
	private BooleanProperty allianceValid;
	
	//these properties only tell the PlanetLayout whether the fields shall be marked
	@JsonSerialize(using = CustomBooleanPropertySerializer.class)
	@JsonDeserialize(using = CustomBooleanPropertyDeserializer.class)
	private BooleanProperty markPlanetFields;
	@JsonSerialize(using = CustomBooleanPropertySerializer.class)
	@JsonDeserialize(using = CustomBooleanPropertyDeserializer.class)
	private BooleanProperty markSatelliteFields;
	
	/**
	 * DO NOT USE - empty constructor for json deserialization
	 */
	@Deprecated
	public AllianceBuilder() {
		
	}
	
	public AllianceBuilder(Player player) {
		Objects.requireNonNull(player, "The AllianceBuilder needs a Player object different from null");
		this.player = player;
		planets = FXCollections.observableArrayList();
		connectingSatellites = FXCollections.observableArrayList();
		
		planets.addListener((ListChangeListener.Change<? extends Field> change) -> updateProperties());
		connectingSatellites.addListener((ListChangeListener.Change<? extends Field> change) -> updateProperties());
		
		numPlanets = new SimpleIntegerProperty(this, "numPlanets", 0);
		numNeighbourPlanets = new SimpleIntegerProperty(this, "numNeighbourPlanets", 0);
		numBuildings = new SimpleIntegerProperty(this, "numBuildings", 0);
		numNeighbourBuildings = new SimpleIntegerProperty(this, "numNeighbourBuildings", 0);
		numMainBuildings = new SimpleIntegerProperty(this, "numMainBuildings", 0);
		allianceValid = new SimpleBooleanProperty(this, "allianceValid", false);
		
		markPlanetFields = new SimpleBooleanProperty(this, "markPlanetFields", false);
		markSatelliteFields = new SimpleBooleanProperty(this, "markSatelliteFields", false);
	}
	
	public Alliance build() {
		if (player.getAllianceManager().isAllianceValid(planets, connectingSatellites, bonus, bonusIndex)) {
			return new Alliance(planets, connectingSatellites, bonus);
		}
		else {
			throw new IllegalStateException("The alliance can't be built because it's not valid.");
		}
	}
	
	/**
	 * Count the new properties after one of the lists was changed and update them.
	 */
	private void updateProperties() {
		int numPlanets = planets.size();
		int numNeighbourPlanets = (int) planets.stream().filter(planet -> !planet.getOtherPlayersBuildings(player).isEmpty()).count();
		int numBuildings = planets.stream().mapToInt(planet -> planet.getPlayerBuildings(player).size()).sum();
		int numNeighbourBuildings = planets.stream().mapToInt(planet -> planet.getOtherPlayersBuildings(player).size()).sum();
		int numMainBuildings = (int) planets.stream().flatMap(planet -> planet.getPlayerBuildings(player).stream())
				.filter(building -> building.getBuilding() == Building.GOVERNMENT || building.getBuilding() == Building.CITY).count();
		boolean allianceValid = player.getAllianceManager().isAllianceValid(planets, connectingSatellites, AllianceBonus.ANY, 0);
		
		this.numPlanets.set(numPlanets);
		this.numNeighbourPlanets.set(numNeighbourPlanets);
		this.numBuildings.set(numBuildings);
		this.numNeighbourBuildings.set(numNeighbourBuildings);
		this.numMainBuildings.set(numMainBuildings);
		this.allianceValid.set(allianceValid);
	}
	
	public void clear() {
		removeAllPlanetFields();
		removeAllConnectingFields();
		setBonus(null);
		setBonusIndex(0);
	}
	
	public ObservableList<Field> getPlanets() {
		return planets;
	}
	public void addPlanetField(Field field) {
		planets.add(field);
	}
	public void removePlanetField(Field field) {
		planets.remove(field);
	}
	public void removeAllPlanetFields() {
		planets.clear();
	}
	
	public ObservableList<Field> getConnectingSatellites() {
		return connectingSatellites;
	}
	public void addConnectingField(Field field) {
		connectingSatellites.add(field);
	}
	public void removeConnectingField(Field field) {
		connectingSatellites.remove(field);
	}
	public void removeAllConnectingFields() {
		connectingSatellites.clear();
	}
	
	public boolean containsField(Field field) {
		return planets.contains(field) || connectingSatellites.contains(field);
	}
	
	public AllianceBonus getBonus() {
		return bonus;
	}
	public void setBonus(AllianceBonus bonus) {
		this.bonus = bonus;
	}
	
	public int getBonusIndex() {
		return bonusIndex;
	}
	public void setBonusIndex(int bonusIndex) {
		this.bonusIndex = bonusIndex;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public IntegerProperty getNumPlanetsProperty() {
		return numPlanets;
	}
	public IntegerProperty getNumNeighbourPlanetsProperty() {
		return numNeighbourPlanets;
	}
	public IntegerProperty getNumBuildingsProperty() {
		return numBuildings;
	}
	public IntegerProperty getNumNeighbourBuildingsProperty() {
		return numNeighbourBuildings;
	}
	public IntegerProperty getNumMainBuildingsProperty() {
		return numMainBuildings;
	}
	public BooleanProperty getAllianceValidProperty() {
		return allianceValid;
	}
	
	public BooleanProperty getMarkPlanetFieldsProperty() {
		return markPlanetFields;
	}
	public BooleanProperty getMarkSatelliteFieldsProperty() {
		return markSatelliteFields;
	}
	
	@JsonGetter("planets")
	public List<Field> getPlanetsAsList() {
		return new ArrayList<>(planets);
	}
	@JsonSetter("planets")
	public void setPlanets(List<Field> planets) {
		this.planets = FXCollections.observableArrayList(planets);
	}
	@JsonGetter("connectingSatellites")
	public List<Field> getConnectingSatellitesAsList() {
		return new ArrayList<>(connectingSatellites);
	}
	@JsonSetter("connectingSatellites")
	public void setConnectingSatellites(List<Field> connectingSatellites) {
		this.connectingSatellites = FXCollections.observableArrayList(connectingSatellites);
	}
}
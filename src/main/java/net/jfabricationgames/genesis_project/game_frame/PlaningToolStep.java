package net.jfabricationgames.genesis_project.game_frame;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlaningToolStep {
	
	private StringProperty descriptionProperty;
	private IntegerProperty primaryResourceProperty;
	private IntegerProperty secundaryResourceProperty;
	private IntegerProperty tertiaryResourceProperty;
	private IntegerProperty researchProperty;
	private IntegerProperty scientistsProperty;
	private IntegerProperty pointsProperty;
	private IntegerProperty ftlProperty;
	
	public PlaningToolStep() {
		descriptionProperty = new SimpleStringProperty();
		primaryResourceProperty = new SimpleIntegerProperty();
		secundaryResourceProperty = new SimpleIntegerProperty();
		tertiaryResourceProperty = new SimpleIntegerProperty();
		researchProperty = new SimpleIntegerProperty();
		scientistsProperty = new SimpleIntegerProperty();
		pointsProperty = new SimpleIntegerProperty();
		ftlProperty = new SimpleIntegerProperty();
	}
	
	public StringProperty getDescriptionProperty() {
		return descriptionProperty;
	}
	public IntegerProperty getPrimaryResourceProperty() {
		return primaryResourceProperty;
	}
	public IntegerProperty getSecundaryResourceProperty() {
		return secundaryResourceProperty;
	}
	public IntegerProperty getTertiaryResourceProperty() {
		return tertiaryResourceProperty;
	}
	public IntegerProperty getResearchProperty() {
		return researchProperty;
	}
	public IntegerProperty getScientistsProperty() {
		return scientistsProperty;
	}
	public IntegerProperty getPointsProperty() {
		return pointsProperty;
	}
	public IntegerProperty getFtlProperty() {
		return ftlProperty;
	}
}
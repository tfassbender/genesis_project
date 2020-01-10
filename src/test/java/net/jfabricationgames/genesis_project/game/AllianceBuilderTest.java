package net.jfabricationgames.genesis_project.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Matchers;

import javafx.collections.ListChangeListener;
import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.genesis_project.manager.IAllianceManager;

class AllianceBuilderTest {
	
	public static Player getAllAcceptingPlayer() {
		Player player = mock(Player.class);
		IAllianceManager manager = mock(IAllianceManager.class);
		when(player.getAllianceManager()).thenReturn(manager);
		when(manager.isAllianceValid(Matchers.anyListOf(Field.class), Matchers.anyListOf(Field.class), any(AllianceBonus.class), anyInt()))
				.thenReturn(true);
		return player;
	}
	
	@Test
	public void testBuild() {
		Player player = getAllAcceptingPlayer();
		AllianceBuilder builder = new AllianceBuilder(player);
		Field planet1 = new Field(new Position(0, 0), Planet.GENESIS, 0);
		Field planet2 = new Field(new Position(0, 1), Planet.GENESIS, 0);
		Field planet3 = new Field(new Position(0, 2), Planet.GENESIS, 0);
		Field satellite1 = new Field(new Position(1, 0), null, 0);
		
		builder.addPlanetField(planet1);
		builder.addPlanetField(planet2);
		builder.addPlanetField(planet3);
		builder.addConnectingField(satellite1);
		builder.setBonus(AllianceBonus.POINTS);
		builder.setBonusIndex(0);
		
		Alliance alliance = builder.build();
		
		assertEquals(3, alliance.getPlanets().size());
		assertEquals(1, alliance.getConnectingSatellites().size());
		assertEquals(AllianceBonus.POINTS, alliance.getBonus());
	}
	
	@Test
	public void testBuild_invalidAlliance() {
		Player player = mock(Player.class);
		IAllianceManager manager = mock(IAllianceManager.class);
		when(player.getAllianceManager()).thenReturn(manager);
		when(manager.isAllianceValid(Matchers.anyListOf(Field.class), Matchers.anyListOf(Field.class), any(AllianceBonus.class), anyInt()))
				.thenReturn(false);
		AllianceBuilder builder = new AllianceBuilder(player);
		Field planet1 = new Field(new Position(0, 0), Planet.GENESIS, 0);
		Field satellite1 = new Field(new Position(1, 0), null, 0);
		
		builder.addPlanetField(planet1);
		builder.addConnectingField(satellite1);
		
		assertThrows(IllegalStateException.class, () -> builder.build());
	}
	
	@Test
	public void testUpdateProperties() {
		Player player = getAllAcceptingPlayer();
		AllianceBuilder builder = new AllianceBuilder(player);
		//count method calls on this object (using the build() method)
		AllianceBuilder counterObject = mock(AllianceBuilder.class);
		
		//add listeners to the observable lists
		builder.getPlanets().addListener((ListChangeListener.Change<? extends Field> change) -> counterObject.build());
		builder.getConnectingSatellites().addListener((ListChangeListener.Change<? extends Field> change) -> counterObject.build());
		
		Field planet1 = new Field(new Position(0, 0), Planet.GENESIS, 0);
		Field planet2 = new Field(new Position(0, 1), Planet.GENESIS, 0);
		Field satellite1 = new Field(new Position(1, 0), null, 0);
		
		builder.addPlanetField(planet1);
		builder.addPlanetField(planet2);
		builder.addConnectingField(satellite1);
		builder.removePlanetField(planet1);
		builder.removeAllConnectingFields();
		
		verify(counterObject, times(5)).build();
	}
	
	@Test
	public void testAddFields() {
		Player player = getAllAcceptingPlayer();
		AllianceBuilder builder = new AllianceBuilder(player);
		
		Field planet1 = new Field(new Position(0, 0), Planet.GENESIS, 0);
		Field planet2 = new Field(new Position(0, 1), Planet.GENESIS, 0);
		Field satellite1 = new Field(new Position(1, 0), null, 0);
		
		builder.addPlanetField(planet1);
		builder.addConnectingField(satellite1);
		
		assertTrue(builder.getPlanets().contains(planet1));
		assertFalse(builder.getPlanets().contains(planet2));
		assertTrue(builder.getConnectingSatellites().contains(satellite1));
	}
	
	@Test
	public void testRemoveFields() {
		Player player = getAllAcceptingPlayer();
		AllianceBuilder builder = new AllianceBuilder(player);
		
		Field planet1 = new Field(new Position(0, 0), Planet.GENESIS, 0);
		Field planet2 = new Field(new Position(0, 1), Planet.GENESIS, 0);
		Field satellite1 = new Field(new Position(1, 0), null, 0);
		
		builder.addPlanetField(planet1);
		builder.addPlanetField(planet2);
		builder.addConnectingField(satellite1);
		builder.removePlanetField(planet1);
		builder.removeAllConnectingFields();
		
		assertFalse(builder.getPlanets().contains(planet1));
		assertTrue(builder.getPlanets().contains(planet2));
		assertFalse(builder.getConnectingSatellites().contains(satellite1));
	}
	
	@Test
	public void testContainsField() {
		Player player = getAllAcceptingPlayer();
		AllianceBuilder builder = new AllianceBuilder(player);
		
		Field planet1 = new Field(new Position(0, 0), Planet.GENESIS, 0);
		Field planet2 = new Field(new Position(0, 1), Planet.GENESIS, 0);
		Field satellite1 = new Field(new Position(1, 0), null, 0);
		Field satellite2 = new Field(new Position(2, 0), null, 0);
		
		builder.addPlanetField(planet1);
		builder.addConnectingField(satellite1);
		
		assertTrue(builder.containsField(planet1));
		assertTrue(builder.containsField(satellite1));
		assertFalse(builder.containsField(planet2));
		assertFalse(builder.containsField(satellite2));
	}
}
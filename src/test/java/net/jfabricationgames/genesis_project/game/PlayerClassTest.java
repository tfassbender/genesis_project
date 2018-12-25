package net.jfabricationgames.genesis_project.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerClassTest {
	
	@Test
	public void testGetPlanetDistance() {
		assertEquals(0, PlayerClass.ENCOR.getPlanetDistance(Planet.BLUE));
		assertEquals(0, PlayerClass.ENCOR.getPlanetDistance(Planet.GENESIS));
		assertEquals(0, PlayerClass.ENCOR.getPlanetDistance(Planet.CENTER));
		assertEquals(1, PlayerClass.ENCOR.getPlanetDistance(Planet.GREEN));
		assertEquals(2, PlayerClass.ENCOR.getPlanetDistance(Planet.GRAY));
		assertEquals(3, PlayerClass.ENCOR.getPlanetDistance(Planet.BLACK));
		assertEquals(2, PlayerClass.ENCOR.getPlanetDistance(Planet.YELLOW));
		assertEquals(1, PlayerClass.ENCOR.getPlanetDistance(Planet.RED));
		
		assertEquals(2, PlayerClass.HERATICS.getPlanetDistance(Planet.BLUE));
		assertEquals(3, PlayerClass.HERATICS.getPlanetDistance(Planet.RED));
		
		assertEquals(1, PlayerClass.LEGION.getPlanetDistance(Planet.RED));
		assertEquals(2, PlayerClass.LEGION.getPlanetDistance(Planet.BLUE));
	}
}
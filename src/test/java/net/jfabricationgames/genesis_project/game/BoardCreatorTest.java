package net.jfabricationgames.genesis_project.game;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import net.jfabricationgames.genesis_project.game.Board.Position;

class BoardCreatorTest {
	
	private static final int numPlayers = 3;
	
	private BoardCreator creator;
	private Map<Position, Field> fields;
	
	@BeforeEach
	public void initializeBoardCreator() {
		creator = new BoardCreator(new Board(), numPlayers);
		fields = creator.getFields();
		creator.initializeEmptyField();
	}
	
	@Test
	public void testInitializeEmptyField() {
		assertNotNull(creator.getFields().get(new Position(0, 0)));
		assertNotNull(creator.getFields().get(new Position(Board.WIDTH - 1, 0)));
		assertNotNull(creator.getFields().get(new Position(0, Board.HEIGHT_EVEN - 1)));
		assertNotNull(creator.getFields().get(new Position(1, Board.HEIGHT_ODD - 1)));
		assertNull(creator.getFields().get(new Position(1, Board.HEIGHT_EVEN - 1)));
		
		assertTrue(creator.getFields().values().stream().allMatch(field -> field.getPlanet() == null));
	}
	
	@RepeatedTest(5)//repeat because of the randomness
	public void testMoveRandomPlanet() {
		addPlanet(0, 0, Planet.BLACK);
		addPlanet(1, 1, Planet.BLUE);
		addPlanet(2, 2, Planet.YELLOW);
		
		Supplier<Boolean> onlyPlanetsOnCoordinates0To5Allowed = () -> creator.getFields().values().stream()
				.anyMatch(field -> field.getPlanet() != null && (field.getPosition().getX() > 5 || field.getPosition().getY() > 5));
		creator.moveRandomPlanet(Arrays.asList(new Position(1, 1)), onlyPlanetsOnCoordinates0To5Allowed);
		Position newPosition = fields.values().stream().filter(field -> field.getPlanet() == Planet.YELLOW).findAny().get().getPosition();
		
		assertTrue(getFieldAt(0, 0).getPlanet() == Planet.BLACK);
		assertTrue(getFieldAt(2, 2).getPlanet() == Planet.YELLOW);
		assertFalse(getFieldAt(1, 1).getPlanet() == Planet.BLUE);
		assertTrue(newPosition.getX() <= 5);
		assertTrue(newPosition.getY() <= 5);
	}
	
	@Test
	public void testIsBasicRulesViolated_planetCloseToCenter() {
		addPlanet(Board.CENTER.getX(), Board.CENTER.getY() - 1, Planet.BLACK);
		
		assertTrue(creator.isBasicRulesViolated());
	}
	@Test
	public void testIsBasicRulesViolated_sameColoredPlanetsTouching() {
		addPlanet(0, 0, Planet.BLACK);
		addPlanet(0, 1, Planet.BLACK);
		
		assertTrue(creator.isBasicRulesViolated());
	}
	@Test
	public void testIsBasicRulesViolated_threePlanetsBuildingAChain() {
		addPlanet(0, 0, Planet.BLACK);
		addPlanet(1, 0, Planet.BLUE);
		addPlanet(2, 0, Planet.RED);
		
		assertTrue(creator.isBasicRulesViolated());
	}
	@Test
	public void testIsBasicRulesViolated_sixTimesTwoPlanetsTouching() {
		addPlanet(0, 0, Planet.BLACK);
		addPlanet(0, 1, Planet.BLUE);
		addPlanet(2, 0, Planet.BLACK);
		addPlanet(2, 1, Planet.BLUE);
		addPlanet(4, 0, Planet.BLACK);
		addPlanet(4, 1, Planet.BLUE);
		addPlanet(6, 0, Planet.BLACK);
		addPlanet(6, 1, Planet.BLUE);
		addPlanet(8, 0, Planet.BLACK);
		addPlanet(8, 1, Planet.BLUE);
		addPlanet(10, 0, Planet.BLACK);
		addPlanet(10, 1, Planet.BLUE);
		
		assertTrue(creator.isBasicRulesViolated());
	}
	@Test
	public void testIsBasicRulesViolated_noViolations() {
		addPlanet(1, 5, Planet.BLUE);
		addPlanet(2, 5, Planet.RED);
		
		addPlanet(6, 5, Planet.BLUE);
		addPlanet(7, 5, Planet.RED);
		
		addPlanet(0, 0, Planet.BLACK);
		addPlanet(0, 1, Planet.BLUE);
		
		addPlanet(6, 0, Planet.BLACK);
		addPlanet(6, 1, Planet.BLUE);
		
		addPlanet(10, 0, Planet.BLACK);
		addPlanet(10, 1, Planet.BLUE);
		
		assertFalse(creator.isBasicRulesViolated());
	}
	
	@Test
	public void testIsSamePlanetRulesViolated_pointOfMassNotNearCenter() {
		addPlanet(1, 1, Planet.BLACK);
		addPlanet(6, 1, Planet.BLACK);
		addPlanet(1, 6, Planet.BLACK);
		
		assertTrue(creator.isSamePlanetRulesViolated());
	}
	@Test
	public void testIsSamePlanetRulesViolated_planetsNotSpread() {
		addPlanet(8, 8, Planet.BLACK);
		addPlanet(5, 5, Planet.BLACK);
		addPlanet(15, 8, Planet.BLACK);
		
		assertTrue(creator.isSamePlanetRulesViolated());
	}
	@Test
	public void testIsSamePlanetRulesViolated_noViolations() {
		addPlanet(1, 8, Planet.BLACK);
		addPlanet(7, 1, Planet.BLACK);
		addPlanet(15, 8, Planet.BLACK);
		
		assertFalse(creator.isSamePlanetRulesViolated());
	}
	
	@Test
	public void testFindAllPlanetRulesViolatingPositions() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFindCenterOfMassRuleViolatingPositions() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFindPlanetSpreadingViolatingPositions() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFindLowColorDistanceRuleViolatingPositions() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testIsCenterOfMassNearCenter_notNearCenter() {
		List<Field> fields = Arrays.asList(getFieldAt(1, 1), getFieldAt(6, 2), getFieldAt(2, 6));
		assertFalse(creator.isCenterOfMassNearCenter(fields, 5));
	}
	
	@Test
	public void testIsCenterOfMassNearCenter_nearCenter() {
		List<Field> fields = Arrays.asList(getFieldAt(1, 1), getFieldAt(15, 1), getFieldAt(0, 7), getFieldAt(15, 7));
		assertTrue(creator.isCenterOfMassNearCenter(fields, 2));
	}
	
	@Test
	public void testCalculateCenterOfMass() {
		List<Field> fields = Arrays.asList(getFieldAt(1, 1), getFieldAt(6, 2), getFieldAt(2, 6));
		double[] centerOfMass = creator.calculateCenterOfMass(fields);
		assertArrayEquals(new double[] {3, 3}, centerOfMass);
	}
	
	@Test
	public void testIsPlanetsSpread() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testCalculateAverageSpreadDistance() {
		fail("Not yet implemented");
	}
	
	private void addPlanet(int x, int y, Planet planet) {
		Position pos = new Position(x, y);
		creator.getFields().put(pos, new Field(pos, planet, numPlayers));
	}
	private Field getFieldAt(int x, int y) {
		return fields.get(new Position(x, y));
	}
}
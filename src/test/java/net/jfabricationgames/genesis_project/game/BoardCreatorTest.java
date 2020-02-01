package net.jfabricationgames.genesis_project.game;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.linear_algebra.Vector2D;

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
	
	@RepeatedTest(5)
	@Timeout(15)
	public void testCreateBoard() {
		creator = new BoardCreator(new Board(), numPlayers);
		creator.createBoard();
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
		addPlanet(7, 8, Planet.BLACK);
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
		addPlanet(1, 1, Planet.BLACK);
		addPlanet(10, 3, Planet.GRAY);
		addPlanet(5, 7, Planet.YELLOW);
		addPlanet(3, 3, Planet.GENESIS);
		addPlanet(15, 0, Planet.RED);
		addPlanet(6, 8, Planet.BLUE);
		addPlanet(9, 5, Planet.YELLOW);
		
		assertTrue(creator.findAllPlanetRulesViolatingPositions().isEmpty());
	}
	
	@Test
	public void testFindCenterOfMassRuleViolatingPositions() {
		addPlanet(1, 1, Planet.BLACK);
		addPlanet(3, 1, Planet.BLACK);
		addPlanet(1, 3, Planet.BLACK);
		addPlanet(3, 3, Planet.BLACK);
		addPlanet(15, 0, Planet.BLACK);
		addPlanet(6, 8, Planet.BLACK);
		addPlanet(9, 5, Planet.BLACK);
		
		List<Position> violatingPositions = creator.findCenterOfMassRuleViolatingPositions();
		List<Position> expectedViolatingPositions = Arrays.asList(new Position(1, 1), new Position(3, 1), new Position(1, 3), new Position(3, 3));
		assertEquals(expectedViolatingPositions.size(), violatingPositions.size());
		assertTrue(violatingPositions.containsAll(expectedViolatingPositions));
	}
	
	@Test
	public void testFindPlanetSpreadingViolatingPositions() {
		addPlanet(1, 1, Planet.BLACK);
		addPlanet(3, 1, Planet.BLACK);
		addPlanet(1, 3, Planet.BLACK);
		addPlanet(15, 0, Planet.BLACK);
		addPlanet(6, 8, Planet.BLACK);
		
		List<Position> violatingPositions = creator.findPlanetSpreadingViolatingPositions();
		List<Position> expectedViolatingPositions = Arrays.asList(new Position(1, 1), new Position(3, 1), new Position(1, 3));
		assertEquals(expectedViolatingPositions.size(), violatingPositions.size());
		assertTrue(violatingPositions.containsAll(expectedViolatingPositions));
	}
	
	@Test
	public void testFindLowColorDistanceRuleViolatingPositions() {
		addPlanet(1, 1, Planet.BLACK);
		addPlanet(3, 1, Planet.GRAY);
		addPlanet(1, 3, Planet.YELLOW);
		addPlanet(3, 3, Planet.GENESIS);
		addPlanet(15, 0, Planet.RED);
		addPlanet(6, 8, Planet.BLUE);
		addPlanet(9, 5, Planet.YELLOW);
		
		List<Position> violatingPositions = creator.findLowColorDistanceRuleViolatingPositions();
		List<Position> expectedViolatingPositions = Arrays.asList(new Position(3, 1));
		assertEquals(expectedViolatingPositions.size(), violatingPositions.size());
		assertTrue(violatingPositions.containsAll(expectedViolatingPositions));
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
		addPlanet(1, 1, Planet.BLACK);
		addPlanet(3, 1, Planet.GRAY);
		addPlanet(1, 3, Planet.YELLOW);
		addPlanet(3, 3, Planet.GENESIS);
		addPlanet(15, 0, Planet.RED);
		addPlanet(6, 8, Planet.BLUE);
		addPlanet(9, 5, Planet.YELLOW);
		
		List<Field> planetFields = fields.values().stream().filter(field -> field.getPlanet() != null).collect(Collectors.toList());
		List<Vector2D> initialClusterCenters = Arrays.asList(new Vector2D(0, 0), new Vector2D(15, 7));
		
		assertFalse(creator.isPlanetsSpread(planetFields, 2, 2, initialClusterCenters, 3));
		assertFalse(creator.isPlanetsSpread(planetFields, 2, 3, initialClusterCenters, 3));
		assertFalse(creator.isPlanetsSpread(planetFields, 2, 4, initialClusterCenters, 2));
	}
	
	@Test
	public void testCalculateAverageSpreadDistance() {
		Map<Vector2D, Set<Field>> classification = new HashMap<>();
		classification.put(new Vector2D(0, 0),
				new HashSet<Field>(Arrays.asList(getFieldAt(2, 2), getFieldAt(3, 3), getFieldAt(0, 1), getFieldAt(5, 0))));
		classification.put(new Vector2D(4, 4), new HashSet<Field>(Arrays.asList(getFieldAt(2, 2), getFieldAt(3, 3))));
		
		double expectedDistance = (Math.hypot(2, 2) + Math.hypot(3, 3) + Math.hypot(0, 1) + Math.hypot(5, 0)) / 4d;
		expectedDistance += (Math.hypot(2, 2) + Math.hypot(1, 1)) / 2;
		expectedDistance /= 2;
		
		assertEquals(expectedDistance, creator.calculateAverageSpreadDistance(classification), 1e-5);
	}
	
	private void addPlanet(int x, int y, Planet planet) {
		Position pos = new Position(x, y);
		creator.getFields().put(pos, new Field(pos, planet, numPlayers));
	}
	private Field getFieldAt(int x, int y) {
		return fields.get(new Position(x, y));
	}
}
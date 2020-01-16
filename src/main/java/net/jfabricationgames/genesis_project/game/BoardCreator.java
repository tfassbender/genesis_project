package net.jfabricationgames.genesis_project.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import net.jfabricationgames.genesis_project.game.Board.Position;

public class BoardCreator {
	
	private Board board;
	private Map<Position, Field> fields;
	private final int numPlayers;
	
	private final int width = Board.WIDTH;
	private final int height = Board.HEIGHT_ODD;//use the minimum height (on odd columns)
	
	public BoardCreator(Board board, int numPlayers) {
		this.board = board;
		this.fields = board.getFields();
		this.numPlayers = numPlayers;
	}
	
	/**
	 * Creates a board that follows all given rules (on the board that was given as constructor parameter)
	 */
	public void createBoard() {
		
	}
	
	/**
	 * Create the board with planets, following the rules for boards.
	 */
	public void initializeBoard() throws IllegalStateException {
		if (fields.size() > 0) {
			throw new IllegalStateException("the board has already been initialized");
		}
		else {
			//initialize with empty fields
			initializeEmptyField();
		}
		
		//place planets of each color (equal number of planets)
		for (Planet planet : Planet.values()) {
			//the number of planets of this color that is placed on the board
			int numPlanets = Board.PLANETS_PER_COLOR;
			if (planet == Planet.GENESIS) {
				numPlanets = Board.PLANETS_GENESIS;
			}
			
			//save the positions where the planets of the current color were saved
			List<Position> addedPositions = new ArrayList<Position>(numPlanets);
			
			for (int i = 0; i < numPlanets; i++) {
				//the position where the planet will be placed
				int x = (int) (Math.random() * width);
				int y = (int) (Math.random() * height);
				Position pos = new Position(x, y);
				
				if (fields.get(pos).getPlanet() == null) {
					//add the new planet
					fields.put(pos, new Field(pos, planet, numPlayers));
					addedPositions.add(pos);
					
					//check whether basic rules are violated and use backtracking, if they are violated
					if (isBasicRulesViolated()) {
						fields.put(pos, new Field(pos, null, numPlanets));
						addedPositions.remove(addedPositions.size() - 1);
						i--;
					}
				}
				else {
					//don't override a planet that was already placed
					i--;
				}
			}
			
			//check whether the placed planets of the current color violate any rules
			while (isSamePlanetRulesViolated()) {
				//if rules are violated, move a randomly chosen planet (out of the violating planets)
				moveRandomPlanet(addedPositions, () -> isBasicRulesViolated());
			}
			
			//check whether any rule is still violated and move some planets if there are rule violations
			List<Position> violatingPositions;
			while (!(violatingPositions = findAllPlanetRulesViolatingPositions()).isEmpty()) {
				//if rules are violated, move a randomly chosen planet (out of the violating planets)
				moveRandomPlanet(violatingPositions, () -> (isBasicRulesViolated() || isSamePlanetRulesViolated()));
			}
		}
	}
	
	/**
	 * Initialize the board with empty fields for all positions
	 */
	private void initializeEmptyField() {
		for (int i = 0; i < Board.WIDTH; i++) {
			int height = Board.HEIGHT_EVEN;
			if (i % 2 == 1) {
				height = Board.HEIGHT_ODD;
			}
			for (int j = 0; j < height; j++) {
				Position pos = new Position(i, j);
				fields.put(pos, new Field(pos, null, numPlayers));
			}
		}
	}
	
	/**
	 * Move a random planet of the list (and check for basic rules to undo the movement if it violates a basic rule)
	 */
	private void moveRandomPlanet(List<Position> movable, Supplier<Boolean> supplier) {
		int movedPlanetIndex = (int) Math.random() * movable.size();
		Position movedPlanetPos = movable.get(movedPlanetIndex);
		Field movedField = fields.get(movedPlanetPos);
		
		int x = (int) (Math.random() * width);
		int y = (int) (Math.random() * height);
		Position pos = new Position(x, y);
		
		//move the planet
		if (fields.get(pos).getPlanet() == null) {
			//add the field at the new position
			fields.put(pos, movedField);
			movedField.setPosition(pos);
			//reset the field on the old position
			fields.put(movedPlanetPos, new Field(movedPlanetPos, null, numPlayers));
			
			if (supplier.get()) {
				//undo the last move, if the new position violates a basic rule
				fields.put(movedPlanetPos, movedField);
				movedField.setPosition(movedPlanetPos);
				fields.put(pos, new Field(pos, null, numPlayers));
			}
		}
	}
	
	/**
	 * Check whether the current placed planets violate a basic rule
	 * 
	 * Rules:
	 * <ul>
	 * <li>No planets beneath the center planet</li>
	 * <li>No two planets of the same color touching each other</li>
	 * <li>Maximum of touching planets (in a chain) is 2 planets</li>
	 * <li>Maximum of touching planets (in the whole field) is 5 (5 times 2 planets touching)</li>
	 * </ul>
	 */
	private boolean isBasicRulesViolated() {
		boolean planetsBeneathCenter = board.getNeighbourFields(fields.get(Board.CENTER)).stream().filter(field -> field.getPlanet() != null)
				.findAny().isPresent();
		
		//if the rule is already violated there is no need for checking the other rules
		if (planetsBeneathCenter) {
			return true;
		}
		
		boolean planetsOfSameColorTouching = false;
		for (Field field : fields.values()) {
			if (field.getPlanet() != null) {
				planetsOfSameColorTouching |= board.getNeighbourFields(field).stream().filter(f -> f.getPlanet() == field.getPlanet()).findAny()
						.isPresent();
			}
		}
		
		if (planetsOfSameColorTouching) {
			return true;
		}
		
		//search for a planet that has two or more neighbors
		boolean moreThanTwoPlanetsTouching = fields.values().stream().filter(f -> f.getPlanet() != null)
				.filter(f -> board.getNeighbourFields(f).stream().filter(neighbor -> neighbor.getPlanet() != null).count() >= 2).findAny()
				.isPresent();
		
		if (moreThanTwoPlanetsTouching) {
			return true;
		}
		
		boolean moreThanFiveTouchingPlanets = fields.values().stream().filter(f -> f.getPlanet() != null)
				.filter(f -> board.getNeighbourFields(f).stream().filter(neighbor -> neighbor.getPlanet() != null).findAny().isPresent()).count() > 5;
		
		return moreThanFiveTouchingPlanets;
	}
	/**
	 * Check whether the current placed planets of the same color violate any rules
	 * 
	 * Rules:
	 * <ul>
	 * <li>The point of mass of the planets has to be near the center</li>
	 * <li>The planets have to be spread</li>
	 * </ul>
	 */
	private boolean isSamePlanetRulesViolated() {
		//center of mass
		double centerOfMassThreshold = 4;//just guessing what could be a good value...
		for (Planet planet : Planet.values()) {
			List<Field> planetFields = fields.values().stream().filter(f -> f.getPlanet() == planet).collect(Collectors.toList());
			
			//don't check all other conditions if one is not satisfied
			if (!isCenterOfMassNearCenter(planetFields, centerOfMassThreshold)) {
				return true;
			}
		}
		
		//spreading of planets
		double minimumAverageDistanceOfSpreadPlanets = 3;//just guessing what could be a good value...
		for (Planet planet : Planet.values()) {
			List<Field> planetFields = fields.values().stream().filter(f -> f.getPlanet() == planet).collect(Collectors.toList());
			
			//don't check all other conditions if one is not satisfied
			if (!isPlanetsSpread(planetFields, minimumAverageDistanceOfSpreadPlanets)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Find planet positions that violate the global rules
	 * 
	 * Rules:
	 * <ul>
	 * <li>The point of mass of the planets has to be near the center</li>
	 * <li>The planets have to be spread</li>
	 * <li>Average distance to the next near-colored planet (also genesis planets) has to be above a threshold</li>
	 * </ul>
	 */
	private List<Position> findAllPlanetRulesViolatingPositions() {
		//TODO
		return null;
	}
	
	/**
	 * Check whether the center of mass of the given planets is near the center field
	 * 
	 * @return true if the difference from the center of mass to the center field is less than the differenceThreshold
	 */
	private boolean isCenterOfMassNearCenter(List<Field> planets, double differenceThreshold) {
		double[] centerOfMass = calculateCenterOfMass(planets);
		double distanceToCenter = Math.hypot(Board.CENTER.getX() - centerOfMass[0], Board.CENTER.getY() - centerOfMass[1]);
		
		return distanceToCenter <= differenceThreshold;
	}
	
	private double[] calculateCenterOfMass(List<Field> planetFields) {
		double[] centerOfMass = new double[2];
		for (Field field : planetFields) {
			centerOfMass[0] += field.getPosition().getX();
			centerOfMass[1] += field.getPosition().getY();
		}
		centerOfMass[0] /= planetFields.size();
		centerOfMass[1] /= planetFields.size();
		return centerOfMass;
	}
	
	/**
	 * Check whether the planets are spread enough (using an x-means cluster-analysis-algorithm and a threshold)
	 */
	private boolean isPlanetsSpread(List<Field> planetFields, double minimumAverageDistanceOfSpreadPlanets) {
		//TODO use x means algorithm
		return false;
	}
}
package net.jfabricationgames.genesis_project.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import net.jfabricationgames.algorithm.XMeans;
import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.linear_algebra.Vector2D;

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
	public void createBoard() throws IllegalStateException {
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
			//the number of clusters for the x-means algorithm that checks the spreading of the planets
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
		
		//add the center planet at last
		fields.put(Board.CENTER, new Field(Board.CENTER, Planet.CENTER, numPlayers));
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
	private void moveRandomPlanet(List<Position> movable, Supplier<Boolean> ruleViolationSupplier) {
		int movedPlanetIndex = (int) Math.random() * movable.size();
		Position movedPlanetPos = movable.get(movedPlanetIndex);
		Field movedField = fields.get(movedPlanetPos);
		
		int x = (int) (Math.random() * width);
		int y = (int) (Math.random() * height);
		Position pos = new Position(x, y);
		
		//move the planet
		boolean moved = false;
		while (!moved) {
			if (fields.get(pos).getPlanet() == null) {
				//add the field at the new position
				fields.put(pos, movedField);
				movedField.setPosition(pos);
				//reset the field on the old position
				fields.put(movedPlanetPos, new Field(movedPlanetPos, null, numPlayers));
				
				if (ruleViolationSupplier.get()) {
					//undo the last move, if the new position violates a basic rule
					fields.put(movedPlanetPos, movedField);
					movedField.setPosition(movedPlanetPos);
					fields.put(pos, new Field(pos, null, numPlayers));
				}
				else {
					moved = true;
				}
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
			
			//the maximum number of clusters for the x-means algorithm that checks the spreading of the planets
			int maxClusters = 2;
			if (planet == Planet.GENESIS) {
				maxClusters = 3;
			}
			
			//don't check all other conditions if one is not satisfied
			if (!isPlanetsSpread(planetFields, maxClusters, minimumAverageDistanceOfSpreadPlanets)) {
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
		List<Position> violatingPositions = new ArrayList<Position>();
		List<Field> planetFields = fields.values().stream().filter(f -> f.getPlanet() != null).collect(Collectors.toList());
		
		//check whether the point of mass of the planets is near the center
		double centerOfMassDifferenceThreshold = 2;//just guessing some values here...
		boolean centerOfMassNearCenterField = isCenterOfMassNearCenter(planetFields, centerOfMassDifferenceThreshold);
		
		if (!centerOfMassNearCenterField) {
			//calculate the direction in which the center of mass is moved and select the planets in this direction as cause
			double[] centerOfMass = calculateCenterOfMass(planetFields);
			
			//calculate the vector to the center of mass and check whether a field is in a certain angle to the position
			Vector2D center = Board.CENTER.toVector2D();
			Vector2D vectorToCenterOfMass = center.vectorTo(new Vector2D(centerOfMass));
			for (Field field : fields.values()) {
				Vector2D toField = center.vectorTo(Field.toVector2D(field));
				//calculate the difference in the angle between the two vectors
				double angleDifference = vectorToCenterOfMass.getAngleDeltaTo(toField);
				
				//choose an angle threshold to select the fields as violating fields (just guessing a value here...)
				double angleThreshold = 45;//45 degrees
				
				if (angleDifference < angleThreshold) {
					violatingPositions.add(field.getPosition());
				}
			}
			
			//return the violating positions directly (other positions are not needed at the moment)
			return violatingPositions;
		}
		
		//check whether the planets are spread
		//use an X-Means algorithm to calculate the clustering of the planets
		int minClusters = 4;
		int maxClusters = 6;
		//use the four edges as starting centers
		List<Vector2D> initialCenters = Arrays.asList(new Vector2D(0, 0), new Vector2D(0, width), new Vector2D(0, height),
				new Vector2D(width, height));
		XMeans<Field> xMeans = new XMeans<>(planetFields, minClusters, maxClusters, initialCenters, Field::toVector2D);
		Map<Vector2D, Set<Field>> clusters = xMeans.findClusters();
		
		//calculate the average distance from the center to all fields in the cluster for all clusters
		boolean spreadRuleViolated = false;
		//the number of planets that need to be in a cluster to identify it as possible problem (because clusters of 1 will always have a average distance of 0)
		int planetsPerClusterToIdentifySpreadViolation = 3;
		for (Vector2D key : clusters.keySet()) {
			//the lower threshold that indicates a problem in spreading of the planets (depending on the number of planets in the cluster)
			double averageDistanceThreshold = clusters.get(key).size();
			if (clusters.get(key).size() >= planetsPerClusterToIdentifySpreadViolation) {
				double avgDist = clusters.get(key).stream().map(Field::toVector2D).mapToDouble(v -> v.distance(key)).sum() / clusters.get(key).size();
				if (avgDist < averageDistanceThreshold) {
					//the cluster is not spread very well and should be moved
					violatingPositions.addAll(clusters.get(key).stream().map(field -> field.getPosition()).collect(Collectors.toList()));
					spreadRuleViolated = true;
				}
			}
		}
		
		if (spreadRuleViolated) {
			//don't wait to find more violating positions but just return the ones that were found
			return violatingPositions;
		}
		
		//the average distance to the next near-colored planet (also genesis planets) has to be above a threshold
		double averageDistanceToNearColoredPlanetThreshold = 2.5;
		for (Planet planet : Planet.values()) {
			if (planet != Planet.GENESIS) {
				//calculate the average distance from a planet of this color to the next planet which's color is near to this planet's color
				double averageDistanceToNextNearColoredPlanet = 0;
				
				List<Planet> nearColoredPlanets = Planet.getNearColoredPlanets(planet);
				List<Field> currentColorPlanetFields = fields.values().stream().filter(field -> field.getPlanet() == planet)
						.collect(Collectors.toList());
				List<Field> nearColoredPlanetFields = fields.values().stream().filter(field -> nearColoredPlanets.contains(field.getPlanet()))
						.collect(Collectors.toList());
				
				//for every planet of the current color...
				for (Field field : currentColorPlanetFields) {
					double minimumDistance = Double.POSITIVE_INFINITY;
					//... find the minimum distance to the next colored planet
					for (Field nearColoredField : nearColoredPlanetFields) {
						double dist = field.getPosition().toVector2D().distance(nearColoredField.getPosition().toVector2D());
						if (dist < minimumDistance) {
							minimumDistance = dist;
						}
					}
					averageDistanceToNextNearColoredPlanet += minimumDistance;
				}
				
				averageDistanceToNextNearColoredPlanet /= currentColorPlanetFields.size();
				
				if (averageDistanceToNextNearColoredPlanet < averageDistanceToNearColoredPlanetThreshold) {
					//this type of planet is to close to many other near colored planets
					violatingPositions.addAll(currentColorPlanetFields.stream().map(field -> field.getPosition()).collect(Collectors.toList()));
					
					//directly return the violating positions without waiting for possible other positions to minimize the number of violating positions
					return violatingPositions;
				}
			}
		}
		
		//return the empty list to indicate that no rule is violated
		return violatingPositions;
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
	
	/**
	 * Calculates the center of mass of all planet fields in the list
	 */
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
	private boolean isPlanetsSpread(List<Field> planetFields, int maxClusters, double minimumAverageDistanceOfSpreadPlanets) {
		//use an X-Means algorithm to calculate the clustering of the planets
		XMeans<Field> xMeans = new XMeans<>(planetFields, 2, maxClusters, null, Field::toVector2D);
		Map<Vector2D, Set<Field>> clusters = xMeans.findClusters();
		//calculate the average distance of centers to planets
		double averageSpreadDistance = calculateAverageSpreadDistance(clusters);
		
		return averageSpreadDistance >= minimumAverageDistanceOfSpreadPlanets;
	}
	
	/**
	 * Calculate the average distance from a cluster center to the fields in the cluster
	 */
	private double calculateAverageSpreadDistance(Map<Vector2D, Set<Field>> classification) {
		double avgDist = 0;
		for (Vector2D key : classification.keySet()) {
			avgDist += classification.get(key).stream().map(Field::toVector2D).mapToDouble(v -> v.distance(key)).sum()
					/ classification.get(key).size();
		}
		avgDist /= classification.size();
		
		return avgDist;
	}
}
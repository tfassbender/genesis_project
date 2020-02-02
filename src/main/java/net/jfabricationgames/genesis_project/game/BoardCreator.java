package net.jfabricationgames.genesis_project.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import net.jfabricationgames.algorithm.XMeans;
import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.linear_algebra.Vector2D;

public class BoardCreator {
	
	private static final Logger LOGGER = LogManager.getLogger(BoardCreator.class);
	
	private Map<Position, Field> fields;
	private final Board board;
	private final int numPlayers;
	
	private final int width = Board.WIDTH;
	private final int height = Board.HEIGHT_ODD;//use the minimum height (on odd columns)
	
	public BoardCreator(Board board, int numPlayers) {
		this.board = board;
		this.fields = new HashMap<>(board.getFields());
		this.numPlayers = numPlayers;
	}
	
	/**
	 * Creates a board that follows all given rules (on the board that was given as constructor parameter).
	 * 
	 * This method tries to create several boards, because the algorithm can get stuck and doesn't create a board.<br>
	 * If a board creation fails the thread is interrupted and the algorithm is restarted.<br>
	 * To speed up the execution there are 3 threads running parallel to create a game.
	 */
	public void createBoard() throws IllegalStateException {
		final int boardsCreatedParallel = 3;
		
		//create an executor service to execute the board creators
		ExecutorService executor = Executors.newFixedThreadPool(boardsCreatedParallel);
		CompletionService<Map<Position, Field>> completionService = new ExecutorCompletionService<>(executor);
		
		//create another thread-pool to execute the board creations (to stop the board creations after a specified amout of time)
		ExecutorService singleBoardCreatorExecutor = Executors.newFixedThreadPool(boardsCreatedParallel);
		
		for (int i = 0; i < boardsCreatedParallel; i++) {
			completionService.submit(() -> {
				BoardCreator creator;
				//a flag to restart the calculation after the last try failed
				boolean restart;
				
				do {
					restart = false;
					
					//create a new board creator to create a board
					creator = new BoardCreator(board, numPlayers);
					final BoardCreator finalCreator = creator;
					
					//try to create a single board...
					Future<?> future = singleBoardCreatorExecutor.submit(() -> finalCreator.createSingleBoard());
					//... and abort the calculation after at most 5 seconds
					try {
						future.get(5, TimeUnit.SECONDS);
					}
					catch (TimeoutException te) {
						//interrupt the execution after the timeout
						future.cancel(true);
						
						//start a new calculation in this thread
						restart = true;
					}
				} while (restart);
				
				//return the fields if the calculation succeeds
				return creator.fields;
			});
		}
		
		Future<Map<Position, Field>> result;
		try {
			result = completionService.take();
			//copy the result of the board creator that finished first to this board creator and to the board
			this.fields = result.get();
			this.board.setFields(fields);
		}
		catch (InterruptedException | ExecutionException e) {
			LOGGER.error("couldn't take a result from the service", e);
			throw new IllegalStateException("Board couldn't be created", e);
		}
		finally {
			//shutdown the executors
			singleBoardCreatorExecutor.shutdownNow();
			executor.shutdownNow();
		}
	}
	
	/**
	 * Creates a board that follows all given rules
	 */
	private void createSingleBoard() {
		if (fields.size() > 0) {
			throw new IllegalStateException("the board has already been initialized");
		}
		else {
			//initialize with empty fields
			initializeEmptyField();
		}
		
		//place planets of each color (equal number of planets)
		for (Planet planet : Planet.values()) {
			if (planet != Planet.CENTER) {
				//the number of planets of this color that is placed on the board
				int numPlanets = Board.PLANETS_PER_COLOR;
				//the number of clusters for the x-means algorithm that checks the spreading of the planets
				if (planet == Planet.GENESIS) {
					numPlanets = Board.PLANETS_GENESIS;
				}
				
				//save the positions where the planets of the current color were saved
				List<Position> addedPositions = new ArrayList<Position>(numPlanets);
				
				for (int i = 0; i < numPlanets && !Thread.currentThread().isInterrupted(); i++) {
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
				while (isSamePlanetRulesViolated() && !Thread.currentThread().isInterrupted()) {
					//if rules are violated, move a randomly chosen planet (out of the violating planets)
					Position[] planetMovement = moveRandomPlanet(addedPositions, () -> isBasicRulesViolated());
					//update the planets position
					addedPositions.remove(planetMovement[0]);
					addedPositions.add(planetMovement[1]);
				}
				
				//check whether any rule is still violated and move some planets if there are rule violations
				List<Position> violatingPositions;
				
				//test for thread interruption so JUnit can use the timeout
				while (!(violatingPositions = findAllPlanetRulesViolatingPositions()).isEmpty() && !Thread.currentThread().isInterrupted()) {
					//if rules are violated, move a randomly chosen planet (out of the violating planets)
					moveRandomPlanet(violatingPositions, () -> (isBasicRulesViolated() || isSamePlanetRulesViolated()));
					//ignore the planet movement that is returned from the moveRandomPlanet method, because the violating positions are re-calculated anyway
				}
			}
		}
		
		//add the center planet at last
		fields.put(Board.CENTER, new Field(Board.CENTER, Planet.CENTER, numPlayers));
	}
	
	/**
	 * Initialize the board with empty fields for all positions
	 */
	@VisibleForTesting
	protected void initializeEmptyField() {
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
	 * Move a random planet of the list (and check for rules from the ruleViolationSupplier to undo the movement if it violates a basic rule)
	 * 
	 * @return Returns the previous position (index 0) and the new position (index 1) of the moved planet.
	 */
	@VisibleForTesting
	protected Position[] moveRandomPlanet(List<Position> movable, Supplier<Boolean> ruleViolationSupplier) {
		int movedPlanetIndex = (int) Math.random() * movable.size();
		Position previousePosition = movable.get(movedPlanetIndex);
		Field movedField = fields.get(previousePosition);
		
		//move the planet
		boolean moved = false;
		Position movingTo = null;
		while (!moved && !Thread.currentThread().isInterrupted()) {
			int x = (int) (Math.random() * width);
			int y = (int) (Math.random() * height);
			movingTo = new Position(x, y);
			
			if (fields.get(movingTo).getPlanet() == null) {
				//add the field at the new position
				fields.put(movingTo, movedField);
				movedField.setPosition(movingTo);
				//reset the field on the old position
				fields.put(previousePosition, new Field(previousePosition, null, numPlayers));
				
				if (ruleViolationSupplier.get()) {
					//undo the last move, if the new position violates a basic rule
					fields.put(previousePosition, movedField);
					movedField.setPosition(previousePosition);
					fields.put(movingTo, new Field(movingTo, null, numPlayers));
				}
				else {
					moved = true;
				}
			}
		}
		
		return new Position[] {previousePosition, movingTo};
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
	@VisibleForTesting
	protected boolean isBasicRulesViolated() {
		boolean planetsBeneathCenter = Board.getNeighbourFields(fields, fields.get(Board.CENTER)).stream().filter(field -> field.getPlanet() != null)
				.findAny().isPresent();
		
		//if the rule is already violated there is no need for checking the other rules
		if (planetsBeneathCenter) {
			return true;
		}
		
		boolean planetsOfSameColorTouching = false;
		for (Field field : fields.values()) {
			if (field.getPlanet() != null) {
				planetsOfSameColorTouching |= Board.getNeighbourFields(fields, field).stream().filter(f -> f.getPlanet() == field.getPlanet())
						.findAny().isPresent();
			}
		}
		
		if (planetsOfSameColorTouching) {
			return true;
		}
		
		//search for a planet that has two or more neighbors
		boolean moreThanTwoPlanetsTouching = fields.values().stream().filter(f -> f.getPlanet() != null)
				.filter(f -> Board.getNeighbourFields(fields, f).stream().filter(neighbor -> neighbor.getPlanet() != null).count() >= 2).findAny()
				.isPresent();
		
		if (moreThanTwoPlanetsTouching) {
			return true;
		}
		
		//search for touching planets (10 planets mean 5 times two planets touching)
		boolean moreThanFiveTouchingPlanets = fields.values().stream().filter(f -> f.getPlanet() != null)
				.filter(f -> Board.getNeighbourFields(fields, f).stream().filter(neighbor -> neighbor.getPlanet() != null).findAny().isPresent())
				.count() > 10;
		
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
	@VisibleForTesting
	protected boolean isSamePlanetRulesViolated() {
		//center of mass
		double centerOfMassThreshold = 4;//just guessing what could be a good value...
		for (Planet planet : Planet.values()) {
			List<Field> planetFields = fields.values().stream().filter(f -> f.getPlanet() == planet).collect(Collectors.toList());
			
			//if there are not yet planets of this type the test must not fail
			if (!planetFields.isEmpty()) {
				//don't check all other conditions if one is not satisfied
				if (!isCenterOfMassNearCenter(planetFields, centerOfMassThreshold)) {
					return true;
				}
			}
		}
		
		//spreading of planets
		double minimumAverageDistanceOfSpreadPlanets = 1.2;//just guessing what could be a good value...
		for (Planet planet : Planet.values()) {
			List<Field> planetFields = fields.values().stream().filter(f -> f.getPlanet() == planet).collect(Collectors.toList());
			
			//if there are not yet planets of this type the test must not fail
			if (!planetFields.isEmpty()) {
				int minClusters = 2;
				List<Vector2D> initialClusterCenters = Arrays.asList(new Vector2D(0, 0), new Vector2D(15, 7));
				//the maximum number of clusters for the x-means algorithm that checks the spreading of the planets
				int maxClusters = 2;
				if (planet == Planet.GENESIS) {
					maxClusters = 3;
				}
				
				//don't check all other conditions if one is not satisfied
				if (!isPlanetsSpread(planetFields, minClusters, maxClusters, initialClusterCenters, minimumAverageDistanceOfSpreadPlanets)) {
					return true;
				}
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
	@VisibleForTesting
	protected List<Position> findAllPlanetRulesViolatingPositions() {
		List<Position> violatingPositions = new ArrayList<Position>();
		
		//check whether the point of mass of the planets is near the center
		violatingPositions.addAll(findCenterOfMassRuleViolatingPositions());
		if (!violatingPositions.isEmpty()) {
			return violatingPositions;
		}
		
		//check whether the planets are spread
		violatingPositions.addAll(findPlanetSpreadingViolatingPositions());
		if (!violatingPositions.isEmpty()) {
			return violatingPositions;
		}
		
		//the average distance to the next near-colored planet (also genesis planets) has to be above a threshold
		violatingPositions.addAll(findLowColorDistanceRuleViolatingPositions());
		
		return violatingPositions;
	}
	
	/**
	 * Find all positions that violate the center of mass rule for all planet fields
	 */
	@VisibleForTesting
	protected List<Position> findCenterOfMassRuleViolatingPositions() {
		List<Position> violatingPositions = new ArrayList<Position>();
		List<Field> planetFields = fields.values().stream().filter(f -> f.getPlanet() != null).collect(Collectors.toList());
		
		double centerOfMassDifferenceThreshold = 2;//just guessing some values here...
		boolean centerOfMassNearCenterField = isCenterOfMassNearCenter(planetFields, centerOfMassDifferenceThreshold);
		
		if (!centerOfMassNearCenterField) {
			//calculate the direction in which the center of mass is moved and select the planets in this direction as cause
			double[] centerOfMass = calculateCenterOfMass(planetFields);
			
			//calculate the vector to the center of mass and check whether a field is in a certain angle to the position
			Vector2D center = Board.CENTER.toVector2D();
			Vector2D vectorToCenterOfMass = center.vectorTo(new Vector2D(centerOfMass));
			for (Field field : planetFields) {
				Vector2D toField = center.vectorTo(Field.toVector2D(field));
				//calculate the difference in the angle between the two vectors
				double angleDifference = vectorToCenterOfMass.getAngleDeltaTo(toField);
				
				//choose an angle threshold to select the fields as violating fields (just guessing a value here...)
				double angleThreshold = 45;//45 degrees
				
				if (angleDifference < angleThreshold) {
					violatingPositions.add(field.getPosition());
				}
			}
		}
		return violatingPositions;
	}
	
	/**
	 * Find all positions that violate the rule of spreading planets in the board
	 */
	@VisibleForTesting
	protected List<Position> findPlanetSpreadingViolatingPositions() {
		List<Position> violatingPositions = new ArrayList<Position>();
		List<Field> planetFields = fields.values().stream().filter(f -> f.getPlanet() != null).collect(Collectors.toList());
		
		//use an X-Means algorithm to calculate the clustering of the planets
		int minClusters = Math.min(4, planetFields.size());
		int maxClusters = Math.min(6, planetFields.size());
		
		List<Vector2D> initialCenters;
		if (planetFields.size() >= 4) {
			//use the four edges as starting centers
			initialCenters = Arrays.asList(new Vector2D(0, 0), new Vector2D(width, 0), new Vector2D(0, height), new Vector2D(width, height));
		}
		else {
			//try to use edges as starting fields
			if (planetFields.size() == 3) {
				initialCenters = Arrays.asList(new Vector2D(0, 0), new Vector2D(width, 0), new Vector2D(width, height / 2));
			}
			else if (planetFields.size() == 2) {
				initialCenters = Arrays.asList(new Vector2D(0, height / 2), new Vector2D(width, height / 2));
			}
			else {
				//should never happen and will lead to an error in the k-means anyway
				initialCenters = null;
			}
		}
		XMeans<Field> xMeans = new XMeans<>(planetFields, minClusters, maxClusters, initialCenters, Field::toVector2D);
		xMeans.setImprovementNeededToAcceptTheNewSolution(0.20);
		Map<Vector2D, Set<Field>> clusters = xMeans.findClusters();
		
		//calculate the average distance from the center of the cluster to all fields in the cluster for all clusters
		//the number of planets that need to be in a cluster to identify it as possible problem (because clusters of 1 will always have a average distance of 0)
		int planetsPerClusterToIdentifySpreadViolation = 3;
		for (Vector2D key : clusters.keySet()) {
			//the lower threshold that indicates a problem in spreading of the planets (depending on the number of planets in the cluster)
			double averageDistanceThreshold = Math.min(((double) clusters.get(key).size()) / 2, 2.5);
			if (clusters.get(key).size() >= planetsPerClusterToIdentifySpreadViolation) {
				double avgDist = clusters.get(key).stream().map(Field::toVector2D).mapToDouble(v -> v.distance(key)).sum() / clusters.get(key).size();
				if (avgDist < averageDistanceThreshold) {
					//the cluster is not spread very well and should be moved
					violatingPositions.addAll(clusters.get(key).stream().map(field -> field.getPosition()).collect(Collectors.toList()));
				}
			}
		}
		
		return violatingPositions;
	}
	
	/**
	 * Find all positions that violate the rule of not to many low-color-distance plants to be near to each other
	 */
	@VisibleForTesting
	protected List<Position> findLowColorDistanceRuleViolatingPositions() {
		List<Position> violatingPositions = new ArrayList<Position>();
		
		double averageDistanceToNearColoredPlanetThreshold = 2.01;
		for (Planet planet : Planet.values()) {
			if (planet != Planet.GENESIS && planet != Planet.CENTER) {
				//calculate the average distance from a planet of this color to the next planet which's color is near to this planet's color
				double averageDistanceToNextNearColoredPlanet = 0;
				
				List<Planet> nearColoredPlanets = Planet.getNearColoredPlanets(planet);
				List<Field> currentColorPlanetFields = fields.values().stream().filter(field -> field.getPlanet() == planet)
						.collect(Collectors.toList());
				List<Field> nearColoredPlanetFields = fields.values().stream().filter(field -> nearColoredPlanets.contains(field.getPlanet()))
						.collect(Collectors.toList());
				
				if (currentColorPlanetFields.size() > 0) {
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
		}
		
		//should be empty in this case
		return violatingPositions;
	}
	
	/**
	 * Check whether the center of mass of the given planets is near the center field
	 * 
	 * @return true if the difference from the center of mass to the center field is less than the differenceThreshold
	 */
	@VisibleForTesting
	protected boolean isCenterOfMassNearCenter(List<Field> planets, double differenceThreshold) {
		if (planets.isEmpty()) {
			throw new IllegalArgumentException("no fields to calculate the center of mass");
		}
		double[] centerOfMass = calculateCenterOfMass(planets);
		double distanceToCenter = Math.hypot(Board.CENTER.getX() - centerOfMass[0], Board.CENTER.getY() - centerOfMass[1]);
		
		return distanceToCenter <= differenceThreshold;
	}
	
	/**
	 * Calculates the center of mass of all planet fields in the list
	 */
	@VisibleForTesting
	protected double[] calculateCenterOfMass(List<Field> planetFields) {
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
	@VisibleForTesting
	protected boolean isPlanetsSpread(List<Field> planetFields, int minClusters, int maxClusters, List<Vector2D> initialCenters,
			double minimumAverageDistanceOfSpreadPlanets) {
		//use an X-Means algorithm to calculate the clustering of the planets
		XMeans<Field> xMeans = new XMeans<>(planetFields, minClusters, maxClusters, initialCenters, Field::toVector2D);
		Map<Vector2D, Set<Field>> clusters = xMeans.findClusters();
		//calculate the average distance of centers to planets
		double averageSpreadDistance = calculateAverageSpreadDistance(clusters);
		
		return averageSpreadDistance >= minimumAverageDistanceOfSpreadPlanets;
	}
	
	/**
	 * Calculate the average distance from a cluster center to the fields in the cluster
	 */
	@VisibleForTesting
	protected double calculateAverageSpreadDistance(Map<Vector2D, Set<Field>> classification) {
		double avgDist = 0;
		for (Vector2D key : classification.keySet()) {
			avgDist += classification.get(key).stream().map(Field::toVector2D).mapToDouble(v -> v.distance(key)).sum()
					/ classification.get(key).size();
		}
		avgDist /= classification.size();
		
		return avgDist;
	}
	
	@VisibleForTesting
	protected Map<Position, Field> getFields() {
		return fields;
	}
}
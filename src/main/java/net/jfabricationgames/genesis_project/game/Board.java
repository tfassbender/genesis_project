package net.jfabricationgames.genesis_project.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.jfabricationgames.genesis_project.json.deserializer.CustomBoardPositionDeserializer;
import net.jfabricationgames.genesis_project.json.serializer.CustomBoardPositionSerializer;
import net.jfabricationgames.genesis_project.json.serializer.SerializationIdGenerator;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Board {
	
	public static class Position {
		
		private int x;
		private int y;
		
		/**
		 * DO NOT USE - empty constructor for json deserialization
		 */
		@Deprecated
		public Position() {
			
		}
		
		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		protected Position getPositionOffset(int[] offset) {
			return new Position(x + offset[0], y + offset[1]);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Position) {
				Position pos = (Position) obj;
				return this.x == pos.x && this.y == pos.y;
			}
			else {
				return super.equals(obj);
			}
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}
		
		@Override
		public String toString() {
			return "Board.Position[" + x + "; " + y + "]";
		}
		
		public String asCoordinateString() {
			return x + " | " + y;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		public int[] getBoardLocation() {
			return Constants.getInstance().CELL_COORDINATES.get(this);
		}
	}
	
	private final int[][] neighboursEvenX = new int[][] {{0, -1}, {0, 1}, {-1, -1}, {-1, 0}, {1, -1}, {1, 0}};
	private final int[][] neighboursOddX = new int[][] {{0, -1}, {0, 1}, {-1, 1}, {-1, 0}, {1, 1}, {1, 0}};
	
	private final Position center = new Position(8, 4);
	
	public static final int WIDTH = 17;
	public static final int HEIGHT_EVEN = 9;//number of rows in even columns
	public static final int HEIGHT_ODD = 8;//number of rows in odd columns
	public static final int PLANETS_PER_COLOR = 3;//the number of planets of each color placed on the board
	public static final int PLANETS_GENESIS = 5;//the number of genesis planets placed on the board
	
	@JsonSerialize(keyUsing = CustomBoardPositionSerializer.class)
	@JsonDeserialize(keyUsing = CustomBoardPositionDeserializer.class)
	private Map<Position, Field> fields;
	
	//final id for json serialization
	private final int id = SerializationIdGenerator.getNextId();
	
	public Board() {
		this.fields = new HashMap<Position, Field>();
	}
	
	public Field getCenterField() {
		return fields.get(center);
	}
	
	/**
	 * Get the neighbors of a field (the field itself is not included)
	 */
	public List<Field> getNeighbourFields(Field field) {
		List<Field> neighbours = new ArrayList<Field>(6);
		
		Position pos = field.getPosition();
		int[][] offsets;
		if (pos.getX() % 2 == 0) {
			//even x values
			offsets = neighboursEvenX;
		}
		else {
			//odd x values
			offsets = neighboursOddX;
		}
		
		for (int[] neighbourOffset : offsets) {
			Position neighbourPosition = pos.getPositionOffset(neighbourOffset);
			Field neighbourField = fields.get(neighbourPosition);
			if (neighbourField != null) {
				neighbours.add(neighbourField);
			}
		}
		
		return neighbours;
	}
	
	public List<Field> getPlayersPlanets(Player player) {
		List<Field> playersPlanets = new ArrayList<Field>();
		for (Field field : fields.values()) {
			if (field.isPlanetField()) {
				boolean playerOnPlanet = false;
				for (PlayerBuilding building : field.getBuildings()) {
					playerOnPlanet |= building != null && building.getPlayer().equals(player);
				}
				if (playerOnPlanet) {
					playersPlanets.add(field);
				}
			}
		}
		return playersPlanets;
	}
	
	public int getDistanceToNextPlayerField(Field field, Player player) {
		List<Field> playersFields = getPlayersPlanets(player);
		OptionalInt minDistance = playersFields.stream().mapToInt(field2 -> field.distanceTo(field2)).min();
		return minDistance.orElse(-1);
	}
	
	/**
	 * Create the board with planets, following the rules for boards.
	 */
	public void initializeBoard(int numPlayers) throws IllegalStateException {
		if (fields.size() > 0) {
			throw new IllegalStateException("the board has already been initialized");
		}
		else {
			//initialize with empty fields
			initializeEmptyField(numPlayers);
		}
		
		int width = WIDTH;
		int height = HEIGHT_ODD;//use the minimum height (on odd columns)
		
		//place planets of each color (equal number of planets)
		for (Planet planet : Planet.values()) {
			//the number of planets of this color that is placed on the board
			int numPlanets = PLANETS_PER_COLOR;
			if (planet == Planet.GENESIS) {
				numPlanets = PLANETS_GENESIS;
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
				moveRandomPlanet(addedPositions, () -> isBasicRulesViolated(), width, height, numPlayers);
			}
			
			//check whether any rule is still violated and move some planets if there are rule violations
			List<Position> violatingPositions;
			while (!(violatingPositions = findAllPlanetRulesViolatingPositions()).isEmpty()) {
				//if rules are violated, move a randomly chosen planet (out of the violating planets)
				moveRandomPlanet(violatingPositions, () -> (isBasicRulesViolated() || isSamePlanetRulesViolated()), width, height, numPlayers);
			}
		}
	}
	
	/**
	 * Initialize the board with empty fields for all positions
	 */
	private void initializeEmptyField(int numPlayers) {
		for (int i = 0; i < WIDTH; i++) {
			int height = HEIGHT_EVEN;
			if (i % 2 == 1) {
				height = HEIGHT_ODD;
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
	private void moveRandomPlanet(List<Position> movable, Supplier<Boolean> supplier, int width, int height, int numPlayers) {
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
		boolean planetsBeneathCenter = getNeighbourFields(fields.get(center)).stream().filter(field -> field.getPlanet() != null).findAny()
				.isPresent();
		
		//if the rule is already violated there is no need for checking the other rules
		if (planetsBeneathCenter) {
			return true;
		}
		
		boolean planetsOfSameColorTouching = false;
		for (Field field : fields.values()) {
			if (field.getPlanet() != null) {
				planetsOfSameColorTouching |= getNeighbourFields(field).stream().filter(f -> f.getPlanet() == field.getPlanet()).findAny()
						.isPresent();
			}
		}
		
		if (planetsOfSameColorTouching) {
			return true;
		}
		
		//search for a planet that has two or more neighbors
		boolean moreThanTwoPlanetsTouching = fields.values().stream().filter(f -> f.getPlanet() != null)
				.filter(f -> getNeighbourFields(f).stream().filter(neighbor -> neighbor.getPlanet() != null).count() >= 2).findAny().isPresent();
		
		if (moreThanTwoPlanetsTouching) {
			return true;
		}
		
		boolean moreThanFiveTouchingPlanets = fields.values().stream().filter(f -> f.getPlanet() != null)
				.filter(f -> getNeighbourFields(f).stream().filter(neighbor -> neighbor.getPlanet() != null).findAny().isPresent()).count() > 5;
		
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
		double distanceToCenter = Math.hypot(center.x - centerOfMass[0], center.y - centerOfMass[1]);
		
		return distanceToCenter <= differenceThreshold;
	}
	
	private double[] calculateCenterOfMass(List<Field> planetFields) {
		double[] centerOfMass = new double[2];
		for (Field field : planetFields) {
			centerOfMass[0] += field.getPosition().x;
			centerOfMass[1] += field.getPosition().y;
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
	
	public Map<Position, Field> getFields() {
		return fields;
	}
	
	public Field getField(int x, int y) {
		return fields.get(new Position(x, y));
	}
	
	public int getId() {
		return id;
	}
}
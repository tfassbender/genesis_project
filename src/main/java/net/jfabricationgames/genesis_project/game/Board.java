package net.jfabricationgames.genesis_project.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.jfabricationgames.genesis_project.json.deserializer.CustomBoardPositionDeserializer;
import net.jfabricationgames.genesis_project.json.serializer.CustomBoardPositionSerializer;
import net.jfabricationgames.genesis_project.json.serializer.SerializationIdGenerator;
import net.jfabricationgames.linear_algebra.Vector2D;

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
		
		public Vector2D toVector2D() {
			return new Vector2D(x, y);
		}
		public static Vector2D toVector2D(Position position) {
			return new Vector2D(position.x, position.y);
		}
		
		public int[] getBoardLocation() {
			return Constants.getInstance().CELL_COORDINATES.get(this);
		}
	}
	
	private static final int[][] neighboursEvenX = new int[][] {{0, -1}, {0, 1}, {-1, -1}, {-1, 0}, {1, -1}, {1, 0}};
	private static final int[][] neighboursOddX = new int[][] {{0, -1}, {0, 1}, {-1, 1}, {-1, 0}, {1, 1}, {1, 0}};
	
	public static final Position CENTER = new Position(8, 4);
	
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
	
	/**
	 * Initialize the board with planets on random positions following the rules for creating boards (see {@link BoardCreator})
	 */
	public void initializeBoard(int numPlayers) throws IllegalStateException {
		new BoardCreator(this, numPlayers).createBoard();
	}
	
	public Field getCenterField() {
		return fields.get(CENTER);
	}
	
	/**
	 * Get the neighbors of a field on the current board (the field itself is not included)
	 */
	public List<Field> getNeighbourFields(Field field) {
		return getNeighbourFields(fields, field);
	}
	/**
	 * Get the neighbors of a field in a map of fields (the field itself is not included)
	 */
	public static List<Field> getNeighbourFields(Map<Position, Field> fields, Field field) {
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
	
	public Map<Position, Field> getFields() {
		return fields;
	}
	
	public Field getField(int x, int y) {
		return fields.get(new Position(x, y));
	}
	protected void setFields(Map<Position, Field> fields) {
		this.fields = fields;
	}
	
	public int getId() {
		return id;
	}
}
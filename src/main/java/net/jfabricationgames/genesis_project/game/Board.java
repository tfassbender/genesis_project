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
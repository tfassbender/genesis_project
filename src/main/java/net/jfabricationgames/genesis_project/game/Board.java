package net.jfabricationgames.genesis_project.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Board {
	
	public static class Position {
		
		private int x;
		private int y;
		
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
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
	}
	
	private final int[][] neighboursEvenX = new int[][] {{0, -1}, {0, 1}, {-1, -1}, {-1, 0}, {1, -1}, {1, 0}};
	private final int[][] neighboursOddX = new int[][] {{0, -1}, {0, 1}, {-1, 1}, {-1, 0}, {1, 1}, {1, 0}};
	
	private final Position center = new Position(8, 4);
	
	public Field getCenterField() {
		return fields.get(center);
	}
	
	private Map<Position, Field> fields;
	
	public Board() {
		this.fields = new HashMap<Position, Field>();
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
	
	public Map<Position, Field> getFields() {
		return fields;
	}
}
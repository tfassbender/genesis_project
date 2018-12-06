package net.jfabricationgames.genesis_project.game;

import java.util.HashMap;
import java.util.Map;

public class Board {
	
	public class Position {
		
		private int x;
		private int y;
		
		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
	}
	
	private Map<Position, Field> fields;
	
	public Board() {
		this.fields = new HashMap<Position, Field>();
	}
	
	public Map<Position, Field> getFields() {
		return fields;
	}
}
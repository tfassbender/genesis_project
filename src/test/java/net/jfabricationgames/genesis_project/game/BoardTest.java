package net.jfabricationgames.genesis_project.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.jfabricationgames.genesis_project.game.Board.Position;

class BoardTest {
	
	private Board initializeBoardWithFields() {
		Board board = new Board();
		//initialize the board with some fields
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				Position pos = new Position(x, y);
				Field field = new Field(pos, null, 0);
				board.getFields().put(pos, field);
			}
		}
		return board;
	}
	
	@Test
	public void testGetNeighbourFields_evenXField() {
		Board board = initializeBoardWithFields();
		Field center = board.getFields().get(new Position(2, 2));
		
		//add some satellites to specific fields to count them in the neighbor fields
		board.getFields().get(new Position(2, 1)).build(new PlayerBuilding(Building.SATELLITE, null), 0);
		board.getFields().get(new Position(1, 2)).build(new PlayerBuilding(Building.SATELLITE, null), 0);
		board.getFields().get(new Position(3, 3)).build(new PlayerBuilding(Building.SATELLITE, null), 0);//not a neighbor of center
		board.getFields().get(new Position(4, 4)).build(new PlayerBuilding(Building.SATELLITE, null), 0);//also no neighbor
		
		List<Field> neighbors = board.getNeighbourFields(center);
		int satellitesNear = neighbors.stream().mapToInt(field -> field.getSpaceBuildings().size()).sum();
		assertEquals(6, neighbors.size());
		assertEquals(2, satellitesNear);
	}

	@Test
	public void testGetNeighbourFields_oddXField() {
		Board board = initializeBoardWithFields();
		Field center = board.getFields().get(new Position(2, 2));
		
		//add some satellites to specific fields to count them in the neighbor fields
		board.getFields().get(new Position(2, 1)).build(new PlayerBuilding(Building.SATELLITE, null), 0);
		board.getFields().get(new Position(1, 2)).build(new PlayerBuilding(Building.SATELLITE, null), 0);//not a neighbor of center
		board.getFields().get(new Position(3, 3)).build(new PlayerBuilding(Building.SATELLITE, null), 0);
		board.getFields().get(new Position(4, 4)).build(new PlayerBuilding(Building.SATELLITE, null), 0);//also no neighbor
		
		List<Field> neighbors = board.getNeighbourFields(center);
		int satellitesNear = neighbors.stream().mapToInt(field -> field.getSpaceBuildings().size()).sum();
		assertEquals(6, neighbors.size());
		assertEquals(2, satellitesNear);
	}

	@Test
	public void testGetNeighbourFields_edgeField() {
		Board board = initializeBoardWithFields();
		Field center = board.getFields().get(new Position(0, 0));
		
		//add some satellites to specific fields to count them in the neighbor fields
		board.getFields().get(new Position(0, 0)).build(new PlayerBuilding(Building.SATELLITE, null), 0);//the field is no neighbor of itself
		board.getFields().get(new Position(0, 1)).build(new PlayerBuilding(Building.SATELLITE, null), 0);
		board.getFields().get(new Position(1, 0)).build(new PlayerBuilding(Building.SATELLITE, null), 0);
		board.getFields().get(new Position(1, 1)).build(new PlayerBuilding(Building.SATELLITE, null), 0);//also no neighbor
		
		List<Field> neighbors = board.getNeighbourFields(center);
		int satellitesNear = neighbors.stream().mapToInt(field -> field.getSpaceBuildings().size()).sum();
		assertEquals(2, neighbors.size());
		assertEquals(2, satellitesNear);
	}
}
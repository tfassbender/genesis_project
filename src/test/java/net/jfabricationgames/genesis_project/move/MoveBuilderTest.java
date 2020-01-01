package net.jfabricationgames.genesis_project.move;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.Player;

class MoveBuilderTest {
	
	private MoveBuilder getMoveBuilder() {
		MoveBuilder builder = new MoveBuilder();
		return builder;
	}
	
	public void testMoveBuilder() {
		getMoveBuilder();//works without exceptions
	}
	
	@Test
	public void testBuildMove() {
		MoveBuilder builder = getMoveBuilder();
		
		//all fields are set to default values (null or false)
		assertNull(builder.getType());
		assertNull(builder.getPlayer());
		assertNull(builder.getField());
		assertNull(builder.getBuilding());
		assertNull(builder.getResearchArea());
		assertNull(builder.getResearchResources());
		assertNull(builder.getTechnology());
		assertNull(builder.getAlliancePlanets());
		assertNull(builder.getSatelliteFields());
		assertNull(builder.getAllianceBonus());
		assertFalse(builder.isPass());
	}
	
	@Test
	public void testGetMove_emptyMove() {
		MoveBuilder builder = getMoveBuilder();
		
		//start building a move
		IMove emptyMove = builder.build();
		//all fields are to default values (null or false)
		assertNull(emptyMove.getType());
		assertNull(emptyMove.getPlayer());
		assertNull(emptyMove.getField());
		assertNull(emptyMove.getBuilding());
		assertNull(emptyMove.getResearchArea());
		assertNull(emptyMove.getResearchResources());
		assertNull(emptyMove.getTechnology());
		assertNull(emptyMove.getAlliancePlanets());
		assertNull(emptyMove.getSatelliteFields());
		assertNull(emptyMove.getAllianceBonus());
		assertFalse(emptyMove.isPassing());
	}
	
	@Test
	public void testGetMove_createRealMove() {
		MoveBuilder builder = getMoveBuilder();
		
		//start building a move
		MoveType type = MoveType.BUILD;
		Player player = mock(Player.class);
		Field field = mock(Field.class);
		Building building = Building.LABORATORY;
		
		builder.setType(type);
		builder.setPlayer(player.getUsername());
		builder.setField(field);
		builder.setBuilding(building);
		IMove buildMove = builder.build();
		
		assertEquals(type, buildMove.getType());
		assertEquals(player.getUsername(), buildMove.getPlayer());
		assertEquals(field, buildMove.getField());
		assertEquals(building, buildMove.getBuilding());
		assertNull(buildMove.getResearchArea());
		assertNull(buildMove.getResearchResources());
		assertNull(buildMove.getTechnology());
		assertNull(buildMove.getAlliancePlanets());
		assertNull(buildMove.getSatelliteFields());
		assertNull(buildMove.getAllianceBonus());
		assertFalse(buildMove.isPassing());
	}
}
package net.jfabricationgames.genesis_project.move;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.Player;

class MoveBuilderTest {
	
	private MoveBuilder getMoveBuilder() {
		Game game = mock(Game.class);
		MoveBuilder builder = new MoveBuilder(game);
		return builder;
	}
	
	@Test
	public void testMoveBuilder() {
		getMoveBuilder();//works without exceptions
		assertThrows(NullPointerException.class, () -> new MoveBuilder(null));
	}
	
	@Test
	public void testBuildMove() {
		MoveBuilder builder = getMoveBuilder();
		
		//start building a move
		builder.buildMove();
		//all fields are to default values (null or false)
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
		//a move is being constructed
		assertTrue(builder.isBuildingMove());
	}

	@Test
	public void testBuildMove_multipleMoves() {
		MoveBuilder builder = getMoveBuilder();
		
		//start building a move
		builder.buildMove();
		//building another move while the last move is not ready results in an invalid state
		assertThrows(IllegalStateException.class, () -> builder.buildMove());
	}
	
	@Test
	public void testGetMove_emptyMove() {
		MoveBuilder builder = getMoveBuilder();
		
		//start building a move
		builder.buildMove();
		IMove emptyMove = builder.getMove();
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
		//a move is being constructed
		assertFalse(builder.isBuildingMove());
	}
	
	@Test
	public void testGetMove_createRealMove() {
		MoveBuilder builder = getMoveBuilder();
		
		//start building a move
		MoveType type = MoveType.BUILD;
		Player player = mock(Player.class);
		Field field = mock(Field.class);
		Building building = Building.LABORATORY;
		
		builder.buildMove();
		builder.setType(type);
		builder.setPlayer(player);
		builder.setField(field);
		builder.setBuilding(building);
		IMove buildMove = builder.getMove();
		
		assertEquals(type, buildMove.getType());
		assertEquals(player, buildMove.getPlayer());
		assertEquals(field, buildMove.getField());
		assertEquals(building, buildMove.getBuilding());
		assertNull(buildMove.getResearchArea());
		assertNull(buildMove.getResearchResources());
		assertNull(buildMove.getTechnology());
		assertNull(buildMove.getAlliancePlanets());
		assertNull(buildMove.getSatelliteFields());
		assertNull(buildMove.getAllianceBonus());
		assertFalse(buildMove.isPassing());
		
		//a move is no longer being constructed
		assertFalse(builder.isBuildingMove());
	}
	
	@Test
	public void testGetMove_noMoveBuilt() {
		MoveBuilder builder = getMoveBuilder();
		
		//requesting a move while no move is built results in an illegal state
		assertFalse(builder.isBuildingMove());
		assertThrows(IllegalStateException.class, () -> builder.getMove());
	}
	
	@Test
	public void testAbortMove() {
		MoveBuilder builder = getMoveBuilder();
		
		//start building a move
		builder.buildMove();
		//abort the build
		builder.abortBuild();
		
		assertFalse(builder.isBuildingMove());
		//builds can be aborted although there is currently no move built
		builder.abortBuild();
	}
}
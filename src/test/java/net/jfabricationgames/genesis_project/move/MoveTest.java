package net.jfabricationgames.genesis_project.move;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import net.jfabricationgames.genesis_project.game.Game;

class MoveTest {
	
	@Test
	public void testExecuteMove() {
		Game game = mock(Game.class);
		when(game.isMoveExecutable(any(IMove.class))).thenReturn(true);
		MoveBuilder builder = new MoveBuilder(game);
		builder.buildMove();
		//constructs an empty move
		IMove move = builder.getMove();
		
		move.execute();
		
		verify(game, times(1)).executeMove(any(IMove.class));
	}
	
	@Test
	public void testIsExecutable() {
		Game game = mock(Game.class);
		when(game.isMoveExecutable(any(IMove.class))).thenReturn(true);
		MoveBuilder builder = new MoveBuilder(game);
		builder.buildMove();
		//constructs an empty move
		IMove move = builder.getMove();
		
		//as specified in the mock to move is executable
		assertTrue(move.isExecutable());
		verify(game, times(1)).isMoveExecutable(any(IMove.class));
	}
}
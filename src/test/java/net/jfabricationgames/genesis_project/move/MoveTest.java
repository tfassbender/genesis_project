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
	public void testIsExecutable() {
		Game game = mock(Game.class);
		when(game.isMoveExecutable(any(IMove.class))).thenReturn(true);
		MoveBuilder builder = new MoveBuilder();
		//constructs an empty move
		IMove move = builder.build();
		
		//as specified in the mock to move is executable
		assertTrue(game.isMoveExecutable(move));
		verify(game, times(1)).isMoveExecutable(any(IMove.class));
	}
}
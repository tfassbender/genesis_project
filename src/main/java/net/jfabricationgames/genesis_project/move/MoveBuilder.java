package net.jfabricationgames.genesis_project.move;

import net.jfabricationgames.genesis_project.game.Game;

/**
 * Create a move using a builder pattern.
 */
public class MoveBuilder {
	
	private Game game;
	
	private MoveBuilder(Game game) {
		this.game = game;
	}
	
	public static MoveBuilder createMove(Game game) {
		return new MoveBuilder(game);
	}
	
	public Game getGame() {
		return game;
	}
}
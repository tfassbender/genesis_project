package net.jfabricationgames.genesis_project.manager;

import net.jfabricationgames.genesis_project.game.Constants;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.move.IMove;

public class TurnManager implements ITurnManager {
	
	private int turn;
	private PlayerOrder<Player> playerOrder;
	
	//private Game game;
	
	public TurnManager(Game game) {
		//this.game = game;
		playerOrder = new PlayerOrder<Player>(game.getPlayers().size());
		turn = 0;
	}
	
	@Override
	public void nextTurn() {
		collectTurnStartResources();
		playerOrder.nextTurn();
		turn++;
		if (gameEnded()) {
			//TODO end the game...
		}
		else {
			//TODO save the game state to the database...
		}
	}
	
	private void collectTurnStartResources() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void receivePointsForMove(IMove move) {
		//TODO implement method when moves are implemented
	}
	
	@Override
	public int getTurn() {
		return turn;
	}
	
	@Override
	public boolean gameEnded() {
		return turn >= Constants.TURNS_PLAYED;
	}
	
	@Override
	public PlayerOrder<Player> getPlayerOrder() {
		return playerOrder;
	}
}
package net.jfabricationgames.genesis_project.manager;

import net.jfabricationgames.genesis_project.game.Constants;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.Player;

public class TurnManager implements ITurnManager {
	
	private int turn;
	private PlayerOrder<Player> playerOrder;
	
	@SuppressWarnings("unused")
	private Game game;
	
	public TurnManager(Game game) {
		this.game = game;
		playerOrder = new PlayerOrder<Player>(game.getPlayers().size());
		turn = 0;
	}
	
	public void nextTurn() {
		giveOutTurnEndPoints();
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
	
	private void giveOutTurnEndPoints() {
		// TODO Auto-generated method stub
	}
	
	public int getTurn() {
		return turn;
	}
	
	public boolean gameEnded() {
		return turn >= Constants.TURNS_PLAYED;
	}
	
	public PlayerOrder<Player> getPlayerOrder() {
		return playerOrder;
	}
}
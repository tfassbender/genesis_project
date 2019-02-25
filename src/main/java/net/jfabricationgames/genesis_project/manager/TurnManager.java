package net.jfabricationgames.genesis_project.manager;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.google.common.annotations.VisibleForTesting;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.jfabricationgames.genesis_project.game.Constants;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.TurnGoal;
import net.jfabricationgames.genesis_project.move.IMove;

public class TurnManager implements ITurnManager {
	
	private int turn;
	private PlayerOrder<Player> playerOrder;
	private List<TurnGoal> turnGoals;
	
	private ObservableList<Player> currentTurnPlayerOrder;
	private ObservableList<Player> nextTurnPlayerOrder;
	
	private ObjectProperty<Player> currentPlayer;
	
	private Game game;
	
	public TurnManager(Game game) {
		this.game = game;
		playerOrder = new PlayerOrder<Player>(game.getPlayers().size());
		playerOrder.startGame(game.getPlayers());
		turn = 0;
		currentPlayer = new SimpleObjectProperty<Player>(this, "currentPlayer");
		currentTurnPlayerOrder = FXCollections.observableArrayList(playerOrder.getOrder());
		nextTurnPlayerOrder = FXCollections.observableArrayList(playerOrder.getNextTurnOrder());
		chooseRandomTurnGoals();
	}
	
	public void chooseRandomTurnGoals() {
		Random random = new Random((long) (Math.random() * Long.MAX_VALUE));
		chooseRandomTurnGoals(TurnGoal.values(), random);
	}
	@VisibleForTesting
	public void chooseRandomTurnGoals(TurnGoal[] goals, Random randomGenerator) {
		TurnGoal swap;
		int random;
		for (int i = 0; i < goals.length; i++) {
			random = (int) (randomGenerator.nextDouble() * (goals.length - i)) + i;//select a random integer from i to goals.length
			swap = goals[random];//swap the chosen integer to position i
			goals[random] = goals[i];
			goals[i] = swap;
		}
		turnGoals = Arrays.asList(goals);
	}
	
	@Override
	public void nextTurn() {
		collectTurnStartResources();
		playerOrder.nextTurn();
		currentPlayer.set(playerOrder.getActivePlayer());
		turn++;
		if (gameEnded()) {
			//TODO end the game...
		}
		else {
			//TODO save the game state to the database...
		}
	}
	
	private void collectTurnStartResources() {
		game.collectTurnStartResources();
	}
	
	@Override
	public void receivePointsForMove(IMove move) {
		int totalPoints = 0;
		int turnGoalPoints = getActiveTurnGoal().getPointsForMove(move);
		//TODO implement technology bonus, ...
		
		totalPoints += turnGoalPoints;
		Player player = move.getPlayer();
		player.getPointManager().addPoints(totalPoints);
	}
	
	@Override
	public int getTurn() {
		return turn;
	}
	
	private void updatePlayerOrderLists() {
		currentTurnPlayerOrder.clear();
		nextTurnPlayerOrder.clear();
		currentTurnPlayerOrder.addAll(playerOrder.getOrder());
		nextTurnPlayerOrder.addAll(playerOrder.getNextTurnOrder());
	}
	
	@Override
	public void playerPassed(Player player) {
		//TODO (?)
		playerOrder.playerPassed(player);
		updatePlayerOrderLists();
	}
	
	@Override
	public boolean gameEnded() {
		return turn >= Constants.TURNS_PLAYED;
	}
	
	@Override
	public TurnGoal getActiveTurnGoal() {
		if (turn > 0) {
			return turnGoals.get(turn - 1);
		}
		else {
			throw new IllegalStateException("There is no active TurnGoal. The game hasn't yet started.");
		}
	}
	@Override
	public List<TurnGoal> getTurnGoals() {
		return turnGoals;
	}
	
	@Override
	public ObservableList<Player> getCurrentTurnPlayerOrder() {
		return currentTurnPlayerOrder;
	}
	@Override
	public ObservableList<Player> getNextTurnPlayerOrder() {
		return nextTurnPlayerOrder;
	}
	
	@Override
	public Player getNextPlayer() {
		return playerOrder.getNext();
	}
	
	@Override
	public List<Player> getPlayerOrder() {
		return playerOrder.getOrder();
	}
	
	@Override
	public List<Player> getNextTurnOrder() {
		return playerOrder.getNextTurnOrder();
	}
	
	@Override
	public Player getActivePlayer() {
		return playerOrder.getActivePlayer();
	}
	
	@Override
	public boolean isPlayersTurn(Player player) {
		return playerOrder.isPlayersTurn(player);
	}
	
	@Override
	public void nextMove() {
		playerOrder.nextMove();
		currentPlayer.set(playerOrder.getActivePlayer());
	}
	
	@Override
	public boolean isTurnEnd() {
		return playerOrder.isTurnEnd();
	}
	
	@Override
	public ObjectProperty<Player> getCurrentPlayerProperty() {
		return currentPlayer;
	}
}
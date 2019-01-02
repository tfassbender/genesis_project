package net.jfabricationgames.genesis_project.testUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.jfabricationgames.genesis_project.game.Board;
import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.Planet;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.PlayerBuilding;
import net.jfabricationgames.genesis_project.game.PlayerClass;
import net.jfabricationgames.genesis_project.game.TurnGoal;
import net.jfabricationgames.genesis_project.manager.ITurnManager;
import net.jfabricationgames.genesis_project.manager.TurnManager;
import net.jfabricationgames.genesis_project.user.User;

public class GameCreationUtil {
	
	public static Game createGame() {
		ConstantsInitializerUtil.initBuildingNumbers();
		ConstantsInitializerUtil.initStartingResearchStates();
		ConstantsInitializerUtil.initResearchResources();
		ConstantsInitializerUtil.initBuildingCosts();
		List<Player> players = new ArrayList<Player>(4);
		for (int i = 0; i < 2; i++) {
			Player player = new Player(new User("Player" + (i + 1)), PlayerClass.ENCOR);
			players.add(player);
		}
		Game game = new Game(players, players.get(0).getUser().getUsername());
		//Game turns: [MINE_TRADING_POST, ALLIANCE, GOVERNMENT_CITY, LABORATORY_RESEARCH_CENTER, NEW_PLANETS, NEIGHBORS, GENESIS_PLANET, COLONY]
		((TurnManager) game.getTurnManager()).chooseRandomTurnGoals(TurnGoal.values(), new Random(42));
		game.getTurnManager().nextTurn();
		initializeBoard(game);
		
		//player 1 has to be the active player
		ITurnManager turnManager = game.getTurnManager();
		Player player2 = game.getPlayers().get(1);
		if (turnManager.getActivePlayer().equals(player2)) {
			turnManager.nextMove();
		}
		
		return game;
	}
	
	private static void initializeBoard(Game game) {
		Board board = game.getBoard();
		//initialize the board with some fields
		for (int x = 0; x < 6; x++) {
			for (int y = 0; y < 6; y++) {
				Position pos = new Position(x, y);
				Field field = new Field(pos, null);
				board.getFields().put(pos, field);
			}
		}
		
		Player player = game.getPlayers().get(0);
		Player player2 = game.getPlayers().get(1);
		
		//planet with 2 player buildings and one opponent building
		board.getFields().get(new Position(0, 0)).setPlanet(Planet.BLACK);
		board.getFields().get(new Position(0, 0)).build(new PlayerBuilding(Building.COLONY, player), 0);
		board.getFields().get(new Position(0, 0)).build(new PlayerBuilding(Building.MINE, player), 1);
		board.getFields().get(new Position(0, 0)).build(new PlayerBuilding(Building.MINE, player2), 2);
		
		//planet with 2 player buildings (one is a CITY) and 1 opponent building
		board.getFields().get(new Position(1, 1)).setPlanet(Planet.GENESIS);
		board.getFields().get(new Position(1, 1)).build(new PlayerBuilding(Building.COLONY, player), 0);
		board.getFields().get(new Position(1, 1)).build(new PlayerBuilding(Building.CITY, player), 1);
		board.getFields().get(new Position(1, 1)).build(new PlayerBuilding(Building.COLONY, player2), 2);
		
		//planet with 3 player buildings
		board.getFields().get(new Position(0, 3)).setPlanet(Planet.BLUE);
		board.getFields().get(new Position(0, 3)).build(new PlayerBuilding(Building.TRADING_POST, player), 0);
		board.getFields().get(new Position(0, 3)).build(new PlayerBuilding(Building.LABORATORY, player), 1);
		board.getFields().get(new Position(0, 3)).build(new PlayerBuilding(Building.COLONY, player), 2);
		
		//planet with 2 player buildings and one opponent building
		board.getFields().get(new Position(2, 0)).setPlanet(Planet.BLACK);
		board.getFields().get(new Position(2, 0)).build(new PlayerBuilding(Building.LABORATORY, player), 0);
		board.getFields().get(new Position(2, 0)).build(new PlayerBuilding(Building.COLONY, player), 1);
		board.getFields().get(new Position(2, 0)).build(new PlayerBuilding(Building.COLONY, player2), 2);
		
		//planet with 1 player building
		board.getFields().get(new Position(3, 1)).setPlanet(Planet.GREEN);
		board.getFields().get(new Position(3, 1)).build(new PlayerBuilding(Building.COLONY, player), 0);
		
		//planet with 2 opponent buildings
		board.getFields().get(new Position(2, 3)).setPlanet(Planet.RED);
		board.getFields().get(new Position(2, 3)).build(new PlayerBuilding(Building.LABORATORY, player2), 0);
		board.getFields().get(new Position(2, 3)).build(new PlayerBuilding(Building.COLONY, player2), 1);
		
		//planet with 1 player building and one opponent building
		board.getFields().get(new Position(4, 2)).setPlanet(Planet.YELLOW);
		board.getFields().get(new Position(4, 2)).build(new PlayerBuilding(Building.TRADING_POST, player), 0);
		board.getFields().get(new Position(4, 2)).build(new PlayerBuilding(Building.COLONY, player2), 1);
		
		//CENTER planet with 1 player building and one opponent building
		board.getFields().get(new Position(5, 0)).setPlanet(Planet.CENTER);
		board.getFields().get(new Position(5, 0)).build(new PlayerBuilding(Building.COLONY, player), 0);
		board.getFields().get(new Position(5, 0)).build(new PlayerBuilding(Building.COLONY, player2), 1);
	}
}
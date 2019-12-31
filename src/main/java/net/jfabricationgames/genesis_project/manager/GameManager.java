package net.jfabricationgames.genesis_project.manager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.jfabricationgames.genesis_project.connection.GenesisClient;
import net.jfabricationgames.genesis_project.connection.exception.GenesisServerException;
import net.jfabricationgames.genesis_project.connection.notifier.NotifierService;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.move.IMove;
import net.jfabricationgames.genesis_project.move.InvalidMoveException;

public class GameManager {
	
	private static GameManager instance;
	
	private Map<Integer, Game> games;
	private GenesisClient client;
	private NotifierService notifier;
	
	private GameManager() throws IOException {
		games = new HashMap<Integer, Game>();
		client = new GenesisClient();
		notifier = NotifierService.getInstance();
	}
	
	public static synchronized GameManager getInstance() throws IOException {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}
	
	public void executeMove(int gameId, IMove move) throws IllegalArgumentException, InvalidMoveException {
		testGameId(gameId);
		Game game = games.get(gameId);
		if (game.isMoveExecutable(move)) {
			//execute the move
			game.executeMove(move);
			
			//send an update to the server
			try {
				client.updateGame(game);
				client.setMove(move, gameId, move.getPlayer().getUsername());
			}
			catch (GenesisServerException e) {
				e.printStackTrace();
			}
			
			//TODO notify other players
		}
		else {
			throw new InvalidMoveException("The given move can not be executed");
		}
	}
	
	//----------------------
	// Game manager proxys
	//----------------------
	
	public ITurnManager getTurnManager(int gameId) throws IllegalArgumentException {
		testGameId(gameId);
		return new TurnManagerProxy(games.get(gameId).getTurnManager());
	}
	public IResearchManager getResearchManager(int gameId) throws IllegalArgumentException {
		testGameId(gameId);
		return new ResearchManagerProxy(games.get(gameId).getResearchManager());
	}
	public IAllianceManager getAllianceManager(int gameId) throws IllegalArgumentException {
		testGameId(gameId);
		return new AllianceManagerProxy(games.get(gameId).getAllianceManager());
	}
	public IGamePointManager getGamePointManager(int gameId) throws IllegalArgumentException {
		testGameId(gameId);
		return new GamePointManagerProxy(games.get(gameId).getPointManager());
	}
	
	//-----------------------
	// Player manager proxys
	//-----------------------
	
	public IPointManager getPointManager(int gameId, String username) throws IllegalArgumentException {
		return new PointManagerProxy(getPlayer(gameId, username).getPointManager());
	}
	public IResourceManager getResourceManager(int gameId, String username) throws IllegalArgumentException {
		return new ResourceManagerProxy(getPlayer(gameId, username).getResourceManager());
	}
	public IBuildingManager getBuildingManager(int gameId, String username) throws IllegalArgumentException {
		return new BuildingManagerProxy(getPlayer(gameId, username).getBuildingManager());
	}
	public IResearchManager getResearchManager(int gameId, String username) throws IllegalArgumentException {
		return new ResearchManagerProxy(getPlayer(gameId, username).getResearchManager());
	}
	public ITechnologyManager getTechnologyManager(int gameId, String username) throws IllegalArgumentException {
		return new TechnologyManagerProxy(getPlayer(gameId, username).getTechnologyManager());
	}
	public IAllianceManager getAllianceManager(int gameId, String username) throws IllegalArgumentException {
		return new AllianceManagerProxy(getPlayer(gameId, username).getAllianceManager());
	}
	
	private Player getPlayer(int gameId, String username) {
		testGameId(gameId);
		List<Player> players = games.get(gameId).getPlayers();
		Optional<Player> player = players.stream().filter(p -> p.getUsername().equals(username)).findAny();
		return player.orElseThrow(
				() -> new IllegalArgumentException("A player with this username (" + username + ") was not found in this game (id: " + gameId + ")"));
	}
	private void testGameId(int gameId) throws IllegalArgumentException {
		if (!games.containsKey(gameId)) {
			throw new IllegalArgumentException("A game with this id (" + gameId + ") was not found");
		}
	}
}
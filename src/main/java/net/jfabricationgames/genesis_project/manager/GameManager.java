package net.jfabricationgames.genesis_project.manager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import net.jfabricationgames.genesis_project.connection.AbstractGenesisClientEventSubscriber;
import net.jfabricationgames.genesis_project.connection.GenesisClient;
import net.jfabricationgames.genesis_project.connection.exception.GenesisServerException;
import net.jfabricationgames.genesis_project.connection.exception.ServerCommunicationException;
import net.jfabricationgames.genesis_project.connection.notifier.NotificationMessageListener;
import net.jfabricationgames.genesis_project.connection.notifier.NotifierService;
import net.jfabricationgames.genesis_project.game.Board;
import net.jfabricationgames.genesis_project.game.DescriptionTexts;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.PlayerClass;
import net.jfabricationgames.genesis_project.game_frame.PlayerInfo;
import net.jfabricationgames.genesis_project.game_frame.util.DialogUtils;
import net.jfabricationgames.genesis_project.move.IMove;
import net.jfabricationgames.genesis_project.move.InvalidMoveException;
import net.jfabricationgames.genesis_project.user.UserManager;
import net.jfabricationgames.genesis_project_server.game.MoveList;

public class GameManager implements NotificationMessageListener {
	
	private static final Logger LOGGER = LogManager.getLogger(GameManager.class);
	
	public static final String NOTIFIER_PREFIX = "game_manager/";
	public static final String NOTIFIER_GAME_UPDATED = "game_updated/";
	
	private static GameManager instance;
	
	private Map<Integer, Game> games;
	private GenesisClient client;
	private NotifierService notifier;
	
	private GameManager() throws IOException {
		games = new HashMap<Integer, Game>();
		client = new GenesisClient();
		notifier = NotifierService.getInstance();
		notifier.addNotificationMessageListener(this);
		LOGGER.info("GameManager started successfully");
	}
	
	public static synchronized GameManager getInstance() throws IllegalStateException {
		if (instance == null) {
			try {
				instance = new GameManager();
			}
			catch (IOException ioe) {
				LOGGER.error("GameManager instance couldn't be created", ioe);
				//cover with a RuntimeException to not need the exception management in every call
				throw new IllegalStateException("GameManager instance couldn't be created", ioe);
			}
		}
		return instance;
	}
	
	/**
	 * Add a new game (that was created or loaded from the server)
	 */
	public void addGame(int gameId, Game game) {
		games.put(gameId, game);
	}
	
	public void executeMove(int gameId, IMove move) throws IllegalArgumentException, InvalidMoveException, ServerCommunicationException {
		LOGGER.debug("trying to execute move {}", move);
		testGameId(gameId);
		Game game = games.get(gameId);
		if (game.isMoveExecutable(move)) {
			//execute the move
			game.executeMove(move);
			
			//send an update to the server
			try {
				client.updateGame(game);
				client.setMove(move, gameId, move.getPlayer());
			}
			catch (GenesisServerException e) {
				e.printStackTrace();
			}
			
			//inform other players about the updated game
			notifier.informAllPlayers(NOTIFIER_PREFIX + NOTIFIER_GAME_UPDATED + gameId);
			
			LOGGER.debug("move was executed successfully");
		}
		else {
			throw new InvalidMoveException("The given move can not be executed");
		}
	}
	
	public boolean isMoveExecutable(int gameId, IMove move) {
		LOGGER.debug("testing move {}", move);
		testGameId(gameId);
		Game game = games.get(gameId);
		return game.isMoveExecutable(move);
	}
	
	/**
	 * Request the current game from the server and update the local game object
	 */
	private void updateGame(int gameId) {
		client.getGameAsync(gameId, new AbstractGenesisClientEventSubscriber() {
			
			@Override
			public void receiveGetGameAnswer(Game game) {
				mergeGame(game);
			}
			@Override
			public void receiveException(GenesisServerException exception) {
				handleGenesisClientException(exception);
			}
		});
	}
	/**
	 * Merge the updated game from the server into the local game
	 */
	private void mergeGame(Game game) {
		testGameId(game.getId());
		Game local = games.get(game.getId());
		local.merge(game);
	}
	/**
	 * Request the last move from the server to update the move list
	 */
	private void updateMoveList(int gameId) {
		client.listMovesAsync(gameId, null, 1, new AbstractGenesisClientEventSubscriber() {
			
			@Override
			public void receiveListMovesAnswer(MoveList moveList) {
				updateMoveList(moveList);
			}
			@Override
			public void receiveException(GenesisServerException exception) {
				handleGenesisClientException(exception);
			}
		});
	}
	/**
	 * Update the local move list with the new move from the server
	 */
	private void updateMoveList(MoveList update) {
		//TODO
	}
	
	/**
	 * Handle an exception from the GenesisClient (by logging and showing in a dialog).
	 */
	private void handleGenesisClientException(GenesisServerException ex) {
		LOGGER.error("An error occured while trying to get the updated game from the database", ex);
		Platform.runLater(
				() -> DialogUtils.showExceptionDialog("Server error", DescriptionTexts.getInstance().ERROR_TEXT_GENESIS_SERVER_EXCEPTION, ex, false));
	}
	
	/**
	 * Get the local player's name
	 */
	public String getLocalPlayer() {
		//added this method to simplify the code a bit
		return UserManager.getInstance().getLocalUsername();
	}
	
	/**
	 * Get a list of all players that participate in a game
	 */
	public List<String> getPlayers(int gameId) throws IllegalArgumentException {
		testGameId(gameId);
		Game game = games.get(gameId);
		return game.getPlayers().stream().map(p -> p.getUsername()).collect(Collectors.toList());
	}
	
	@Override
	public void receiveNotificationMessage(String notificationMessage) {
		LOGGER.debug("received notification message: {}", notificationMessage);
		if (notificationMessage.startsWith(NOTIFIER_PREFIX)) {
			String[] split = notificationMessage.split("/");
			switch (split[2] + "/") {
				case NOTIFIER_GAME_UPDATED:
					if (split.length >= 3) {
						//update the game if notified of an update
						try {
							int gameId = Integer.parseInt(split[2]);
							
							if (games.containsKey(gameId)) {
								updateGame(gameId);
								updateMoveList(gameId);
							}
						}
						catch (NumberFormatException nfe) {
							LOGGER.error("game id couldn't be parsed: ", nfe);
						}
					}
					else {
						LOGGER.warn("received a notification message with not enough content (split size was {}; should be (at least) 3)",
								split.length);
					}
					break;
				default:
					LOGGER.warn("notification message couldn't be interpreted");
					break;
			}
		}
	}
	
	/**
	 * A set of all PlayerClasses that still can be chosen
	 */
	public Set<PlayerClass> getPlayerClassesToChoose(int gameId) {
		testGameId(gameId);
		return games.get(gameId).getPlayerClassesToChoose();
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
	
	public Board getBoard(int gameId) throws IllegalArgumentException {
		testGameId(gameId);
		return games.get(gameId).getBoard();
	}
	
	public ObservableList<PlayerInfo> getPlayerInfoList(int gameId) {
		testGameId(gameId);
		return games.get(gameId).getPlayerInfoList();
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
	
	public PlayerClass getPlayerClass(int gameId, String username) throws IllegalArgumentException {
		return getPlayer(gameId, username).getPlayerClass();
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
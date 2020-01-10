package net.jfabricationgames.genesis_project.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import net.jfabricationgames.genesis_project.connection.exception.AuthenticationException;
import net.jfabricationgames.genesis_project.connection.exception.GenesisServerException;
import net.jfabricationgames.genesis_project.connection.exception.InvalidRequestException;
import net.jfabricationgames.genesis_project.connection.exception.ServerCommunicationException;
import net.jfabricationgames.genesis_project.connection.notifier.NotifierService;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.json.serializer.MixInIgnore;
import net.jfabricationgames.genesis_project.move.IMove;
import net.jfabricationgames.genesis_project_server.game.GameList;
import net.jfabricationgames.genesis_project_server.game.MoveList;
import net.jfabricationgames.genesis_project_server.user.Login;

public class GenesisClient {
	
	private List<GenesisClientEventSubscriber> consumers;
	
	private static final Logger LOGGER = LogManager.getLogger(GenesisClient.class);
	
	public static final String CONFIG_RESOURCE_FILE = "config/hosts.config";
	public static final String CONFIG_KEY_NOTIFIER_HOST = "notifier.host";
	public static final String CONFIG_KEY_NOTIFIER_PORT_REST = "notifier.port.rest";
	public static final String CONFIG_KEY_NOTIFIER_PORT_SOCKET = "notifier.port.socket";
	public static final String CONFIG_KEY_SERVER_HOST = "server.host";
	public static final String CONFIG_KEY_SERVER_PORT = "server.port";
	
	/**
	 * Encryption key for passwords. Not loaded from configuration because it's a symmetric key.
	 */
	private String passwordEncryptionKey = "vcuh31250hvcsojnl312vcnlsgr329fdsip";
	
	private static Properties hosts;
	
	static {
		try {
			loadConfiguration();
		}
		catch (IOException ioe) {
			LOGGER.fatal("Host configuration couldn't be loaded. No server communication will be possible.", ioe);
		}
	}
	
	public GenesisClient() throws IOException {
		consumers = new ArrayList<GenesisClientEventSubscriber>();
		if (hosts == null) {
			//try to reload the configuration if it's not already loaded
			loadConfiguration();
		}
		LOGGER.info("Host configuration loaded: {}", hosts);
	}
	
	/**
	 * Send a request to a host server using HTTP GET or POST.
	 */
	public static Response sendRequest(String host, String resource, String requestType, Entity<?> entity) throws ServerCommunicationException {
		return sendRequest(host, resource, requestType, entity, true);
	}
	/**
	 * Send a request to a host server using HTTP GET or POST.
	 */
	public static Response sendRequest(String host, String resource, String requestType, Entity<?> entity, boolean logEntity)
			throws ServerCommunicationException {
		if (logEntity) {
			LOGGER.debug("sending request to URI: {}{} (type: {}   entity: {})", host, resource, requestType, entity);
		}
		else {
			LOGGER.debug("sending request to URI: {}{} (type: {}   entity: {})", host, resource, requestType, "<entity_not_logged>");
		}
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(host).path(resource);
		Response response = null;
		try {
			switch (requestType) {
				case "GET":
					response = webTarget.request().get();
					break;
				case "POST":
					response = webTarget.request().post(entity);
					break;
			}
			return response;
		}
		catch (ProcessingException pe) {
			throw new ServerCommunicationException("Server not reachable", pe);
		}
	}
	
	public static String getHostProperty(String key) {
		return hosts.getProperty(key);
	}
	
	private static void loadConfiguration() throws IOException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		hosts = new Properties();
		try (InputStream resourceStream = loader.getResourceAsStream(CONFIG_RESOURCE_FILE)) {
			hosts.load(resourceStream);
		}
	}
	
	public void login(String username, String password) throws GenesisServerException {
		//verify the users login
		verifyUser(username, password);
		//start the notifier service if it's not already been started
		if (!NotifierService.isStarted()) {
			try {
				NotifierService.startNotifierService(username);
			}
			catch (IOException ioe) {
				throw new GenesisServerException("The NotifierService couldn't be started", ioe);
			}
		}
	}
	public void loginAsync(String username, String password, GenesisClientEventSubscriber subscriber) {
		Thread thread = new Thread(() -> {
			try {
				login(username, password);
				subscriber.receiveLoginSucessful();
			}
			catch (GenesisServerException gse) {
				subscriber.receiveException(gse);
			}
		}, "loginAsync");
		thread.setDaemon(true);
		thread.start();
	}
	
	public void updateGame(Game game) throws GenesisServerException {
		String serializedGame;
		try {
			serializedGame = getGameObjectMapper().writeValueAsString(game);
		}
		catch (JsonProcessingException jpe) {
			throw new GenesisServerException("Game object couldn't be parsed", jpe);
		}
		String resource = "update_game/" + game.getId() + "/" + serializedGame;
		Response response = sendServerRequest(resource, "GET", null);
		
		switch (Status.fromStatusCode(response.getStatus())) {
			case OK:
				//no exceptions or return values in this case
				break;
			case NOT_FOUND:
				throw new InvalidRequestException("The game id of this game (" + game.getId() + ") was not found in the database");
			case INTERNAL_SERVER_ERROR:
				throw new ServerCommunicationException("The request caused an internal server error");
			default:
				throw new GenesisServerException("The response contained an unexpected status code: " + response.getStatus());
		}
	}
	public void updateGameAsync(Game game, GenesisClientEventSubscriber subscriber) {
		Thread thread = new Thread(() -> {
			try {
				updateGame(game);
				subscriber.receiveUpdateGameSuccessful();
			}
			catch (GenesisServerException gse) {
				subscriber.receiveException(gse);
			}
		}, "updateGameAsync");
		thread.setDaemon(true);
		thread.start();
	}
	
	public Game getGame(int gameId) throws GenesisServerException {
		String resource = "get_game/" + gameId;
		Response response = sendServerRequest(resource, "GET", null);
		
		switch (Status.fromStatusCode(response.getStatus())) {
			case OK:
				//no exceptions in this case
				break;
			case NOT_FOUND:
				throw new InvalidRequestException("The game id of this game (" + gameId + ") was not found in the database");
			case INTERNAL_SERVER_ERROR:
				throw new ServerCommunicationException("The request caused an internal server error");
			default:
				throw new GenesisServerException("The response contained an unexpected status code: " + response.getStatus());
		}
		
		//read the response text to a string
		String responseText = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();
		try {
			//parse the response text to a Game object
			Game game = mapper.readValue(responseText, Game.class);
			return game;
		}
		catch (IOException ioe) {
			throw new GenesisServerException("Parsing of the game failed", ioe);
		}
	}
	public void getGameAsync(int gameId, GenesisClientEventSubscriber subscriber) {
		Thread thread = new Thread(() -> {
			try {
				Game game = getGame(gameId);
				subscriber.receiveGetGameAnswer(game);
			}
			catch (GenesisServerException gse) {
				subscriber.receiveException(gse);
			}
		}, "getGameAsync");
		thread.setDaemon(true);
		thread.start();
	}
	
	public void setMove(IMove move, int gameId, String username) throws GenesisServerException {
		String serializedMove;
		try {
			serializedMove = getMoveObjectMapper().writeValueAsString(move);
		}
		catch (JsonProcessingException jpe) {
			throw new GenesisServerException("IMove object couldn't be parsed", jpe);
		}
		
		String resource = "set_move/" + gameId + "/" + username + "/" + serializedMove;
		Response response = sendServerRequest(resource, "GET", null);
		
		switch (Status.fromStatusCode(response.getStatus())) {
			case OK:
				//no exceptions or return values in this case
				break;
			case NOT_FOUND:
				throw new InvalidRequestException(
						"The game id or the username (gameId" + gameId + "   username: " + username + ") was not found in the database");
			case INTERNAL_SERVER_ERROR:
				throw new ServerCommunicationException("The request caused an internal server error");
			default:
				throw new GenesisServerException("The response contained an unexpected status code: " + response.getStatus());
		}
	}
	public void setMoveAsync(IMove move, int gameId, String username, GenesisClientEventSubscriber subscriber) {
		Thread thread = new Thread(() -> {
			try {
				setMove(move, gameId, username);
				subscriber.receiveSetMoveSuccessful();
			}
			catch (GenesisServerException gse) {
				subscriber.receiveException(gse);
			}
		}, "setMoveAsync");
		thread.setDaemon(true);
		thread.start();
	}
	
	public String getConfig(String configName) throws GenesisServerException {
		String resource = "get_config/" + configName;
		Response response = sendServerRequest(resource, "GET", null);
		
		switch (Status.fromStatusCode(response.getStatus())) {
			case OK:
				//no exceptions in this case
				break;
			case NOT_FOUND:
				throw new InvalidRequestException("The configuration (" + configName + ") was not found");
			case INTERNAL_SERVER_ERROR:
				throw new ServerCommunicationException("The request caused an internal server error");
			default:
				throw new GenesisServerException("The response contained an unexpected status code: " + response.getStatus());
		}
		
		//read the response text to a string
		String responseText = response.readEntity(String.class);
		
		return responseText;
	}
	public void getConfigAsync(String configName, GenesisClientEventSubscriber subscriber) {
		Thread thread = new Thread(() -> {
			try {
				String config = getConfig(configName);
				subscriber.receiveGetConfigAnswer(config);
			}
			catch (GenesisServerException gse) {
				subscriber.receiveException(gse);
			}
		}, "getConfigAsync");
		thread.setDaemon(true);
		thread.start();
	}
	
	public int createGame(List<String> players) throws GenesisServerException {
		String resource = "create_game";
		Response response = sendServerRequest(resource, "POST", Entity.entity(players, MediaType.APPLICATION_JSON));
		
		switch (Status.fromStatusCode(response.getStatus())) {
			case OK:
				//no exceptions in this case
				break;
			case NOT_FOUND:
				throw new InvalidRequestException("At least one of the players was not found in the database (players: " + players + ")");
			case INTERNAL_SERVER_ERROR:
				throw new ServerCommunicationException("The request caused an internal server error");
			default:
				throw new GenesisServerException("The response contained an unexpected status code: " + response.getStatus());
		}
		int gameId = response.readEntity(Integer.class);
		
		return gameId;
	}
	public void createGameAsync(List<String> players, GenesisClientEventSubscriber subscriber) {
		Thread thread = new Thread(() -> {
			try {
				int id = createGame(players);
				subscriber.receiveCreateGameAnswer(id);
			}
			catch (GenesisServerException gse) {
				subscriber.receiveException(gse);
			}
		}, "createGameAsync");
		thread.setDaemon(true);
		thread.start();
	}
	
	public void createUser(String username, String password) throws GenesisServerException {
		String resource = "create_user";
		Login login = new Login(username, password);
		login.encryptPassword(passwordEncryptionKey);
		Response response = sendServerRequest(resource, "POST", Entity.entity(login, MediaType.APPLICATION_JSON), false);
		
		switch (Status.fromStatusCode(response.getStatus())) {
			case OK:
				//no exceptions or return values in this case
				break;
			case FORBIDDEN:
				throw new InvalidRequestException("A user with this username already exists in the database (username: " + username + ")");
			case INTERNAL_SERVER_ERROR:
				throw new ServerCommunicationException("The request caused an internal server error");
			default:
				throw new GenesisServerException("The response contained an unexpected status code: " + response.getStatus());
		}
	}
	public void createUserAsync(String username, String password, GenesisClientEventSubscriber subscriber) {
		Thread thread = new Thread(() -> {
			try {
				createUser(username, password);
				subscriber.receiveCreateUserSuccessful();
			}
			catch (GenesisServerException gse) {
				subscriber.receiveException(gse);
			}
		}, "");
		thread.setDaemon(true);
		thread.start();
	}
	
	public void updateUser(String currentUsername, String currentPassword, String updatedUsername, String updatedPassword)
			throws GenesisServerException {
		String resource = "update_user";
		List<Login> logins = Arrays.asList(new Login(currentUsername, currentPassword), new Login(updatedUsername, updatedPassword));
		logins.forEach(l -> l.encryptPassword(passwordEncryptionKey));
		Response response = sendServerRequest(resource, "POST", Entity.entity(logins, MediaType.APPLICATION_JSON), false);
		
		switch (Status.fromStatusCode(response.getStatus())) {
			case OK:
				//no exceptions or return values in this case
				break;
			case FORBIDDEN:
				throw new AuthenticationException("The user authentication failed (using the current username and password)");
			case NOT_FOUND:
				throw new InvalidRequestException(
						"A user with the updated username already exists in the database (username: " + updatedUsername + ")");
			case INTERNAL_SERVER_ERROR:
				throw new ServerCommunicationException("The request caused an internal server error");
			default:
				throw new GenesisServerException("The response contained an unexpected status code: " + response.getStatus());
		}
	}
	public void updateUserAsync(String currentUsername, String currentPassword, String updatedUsername, String updatedPassword,
			GenesisClientEventSubscriber subscriber) {
		Thread thread = new Thread(() -> {
			try {
				updateUser(currentUsername, currentPassword, updatedUsername, updatedPassword);
				subscriber.receiveUpdateUserSuccessful();
			}
			catch (GenesisServerException gse) {
				subscriber.receiveException(gse);
			}
		}, "updateUserAsync");
		thread.setDaemon(true);
		thread.start();
	}
	
	public void verifyUser(String username, String password) throws GenesisServerException {
		String resource = "verify_user";
		Login login = new Login(username, password);
		login.encryptPassword(passwordEncryptionKey);
		Response response = sendServerRequest(resource, "POST", Entity.entity(login, MediaType.APPLICATION_JSON), false);
		
		switch (Status.fromStatusCode(response.getStatus())) {
			case OK:
				//no exceptions or return values in this case
				break;
			case FORBIDDEN:
				throw new AuthenticationException("The user authentication failed (username: " + username + ")");
			case NOT_FOUND:
				throw new InvalidRequestException("A user with this username was not found in the database (username: " + username + ")");
			case INTERNAL_SERVER_ERROR:
				throw new ServerCommunicationException("The request caused an internal server error");
			default:
				throw new GenesisServerException("The response contained an unexpected status code: " + response.getStatus());
		}
	}
	public void verifyUserAsync(String username, String password, GenesisClientEventSubscriber subscriber) {
		Thread thread = new Thread(() -> {
			try {
				verifyUser(username, password);
				subscriber.receiveVerifyUserSuccessful();
			}
			catch (GenesisServerException gse) {
				subscriber.receiveException(gse);
			}
		}, "verifyUserAsync");
		thread.setDaemon(true);
		thread.start();
	}
	
	public GameList listGames(boolean complete, String username) throws GenesisServerException {
		String resource = "list_games/" + complete + "/";
		if (username == null) {
			resource += "-";
		}
		else {
			resource += username;
		}
		Response response = sendServerRequest(resource, "GET", null);
		
		switch (Status.fromStatusCode(response.getStatus())) {
			case OK:
				//no exceptions or return values in this case
				break;
			case NOT_FOUND:
				throw new InvalidRequestException("A user with this username was not found in the database (username: " + username + ")");
			case INTERNAL_SERVER_ERROR:
				throw new ServerCommunicationException("The request caused an internal server error");
			default:
				throw new GenesisServerException("The response contained an unexpected status code: " + response.getStatus());
		}
		
		String gameListText = response.readEntity(String.class);
		GameList gameList = getGameList(gameListText);
		
		return gameList;
	}
	public void listGamesAsync(boolean complete, String username, GenesisClientEventSubscriber subscriber) {
		Thread thread = new Thread(() -> {
			try {
				GameList gameList = listGames(complete, username);
				subscriber.receiveListGamesAnswer(gameList);
			}
			catch (GenesisServerException gse) {
				subscriber.receiveException(gse);
			}
		}, "listGamesAsync");
		thread.setDaemon(true);
		thread.start();
	}
	
	public MoveList listMoves(int gameId, String username, int numMoves) throws GenesisServerException {
		String resource = "list_moves/" + gameId + "/";
		if (username == null) {
			resource += "-";
		}
		else {
			resource += username;
		}
		resource += "/";
		if (numMoves > 0) {
			resource += numMoves;
		}
		else {
			resource += "-1";
		}
		Response response = sendServerRequest(resource, "GET", null);
		
		switch (Status.fromStatusCode(response.getStatus())) {
			case OK:
				//no exceptions or return values in this case
				break;
			case NOT_FOUND:
				throw new InvalidRequestException("A user with this username was not found in the database (username: " + username + ")");
			case INTERNAL_SERVER_ERROR:
				throw new ServerCommunicationException("The request caused an internal server error");
			default:
				throw new GenesisServerException("The response contained an unexpected status code: " + response.getStatus());
		}
		
		String moveListText = response.readEntity(String.class);
		MoveList moveList = getMoveList(moveListText);
		
		return moveList;
	}
	public void listMovesAsync(int gameId, String username, int numMoves, GenesisClientEventSubscriber subscriber) {
		Thread thread = new Thread(() -> {
			try {
				MoveList moveList = listMoves(gameId, username, numMoves);
				subscriber.receiveListMovesAnswer(moveList);
			}
			catch (GenesisServerException gse) {
				subscriber.receiveException(gse);
			}
		}, "listMovesAsync");
		thread.setDaemon(true);
		thread.start();
	}
	
	public void registerSubscriber(GenesisClientEventSubscriber subscriber) {
		consumers.add(subscriber);
	}
	public void removeSubscriber(GenesisClientEventSubscriber subscriber) {
		consumers.remove(subscriber);
	}
	
	private Response sendServerRequest(String resource, String requestType, Entity<?> entity) throws ServerCommunicationException {
		return sendServerRequest(resource, requestType, entity, true);
	}
	private Response sendServerRequest(String resource, String requestType, Entity<?> entity, boolean logEntity) throws ServerCommunicationException {
		String serverURI = "http://" + hosts.getProperty(CONFIG_KEY_SERVER_HOST) + ":" + hosts.getProperty(CONFIG_KEY_SERVER_PORT)
				+ "/genesis_project_server/genesis_project/genesis_project/";
		return sendRequest(serverURI, resource, requestType, entity, logEntity);
	}
	
	private ObjectMapper getGameObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		
		//serialize only fields (no auto detection for getter methods)
		mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY)
				.withGetterVisibility(JsonAutoDetect.Visibility.NONE).withSetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
		
		return mapper;
	}
	
	private ObjectMapper getMoveObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		
		//serialize only fields (no auto detection for getter methods)
		mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY)
				.withGetterVisibility(JsonAutoDetect.Visibility.NONE).withSetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
		
		//don't serialize Game and Player
		mapper.addMixIn(Game.class, MixInIgnore.class);
		mapper.addMixIn(Player.class, MixInIgnore.class);
		
		return mapper;
	}
	
	private GameList getGameList(String gameListText) {
		ObjectMapper mapper = new ObjectMapper();
		//register the module to parse java-8 LocalDate
		mapper.registerModule(new JavaTimeModule());
		try {
			//"manually" parse JSON to Object
			GameList resp = mapper.readValue(gameListText, GameList.class);
			return resp;
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("The response could not be read or parsed: " + gameListText, e);
		}
	}
	
	private MoveList getMoveList(String moveListText) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			//"manually" parse JSON to Object
			MoveList resp = mapper.readValue(moveListText, MoveList.class);
			return resp;
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("The response could not be read or parsed: " + moveListText, e);
		}
	}
}
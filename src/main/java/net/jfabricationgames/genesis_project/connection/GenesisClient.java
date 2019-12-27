package net.jfabricationgames.genesis_project.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.move.IMove;
import net.jfabricationgames.genesis_project_server.game.GameList;
import net.jfabricationgames.genesis_project_server.game.MoveList;

public class GenesisClient {
	
	private List<GenesisClientEventSubscriber> consumers;
	
	private static final Logger LOGGER = LogManager.getLogger(GenesisClient.class);
	
	public static final String CONFIG_RESOURCE_FILE = "config/hosts.config";
	public static final String CONFIG_KEY_NOTIFIER_HOST = "notifier.host";
	public static final String CONFIG_KEY_NOTIFIER_PORT = "notifier.port";
	public static final String CONFIG_KEY_SERVER_HOST = "server.host";
	public static final String CONFIG_KEY_SERVER_PORT = "server.port";
	
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
	
	private static void loadConfiguration() throws IOException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties configProperties = new Properties();
		try (InputStream resourceStream = loader.getResourceAsStream(CONFIG_RESOURCE_FILE)) {
			configProperties.load(resourceStream);
		}
	}
	
	public void updateGame(Game game) throws ServerCommunicationException {
		//TODO send a REST request to the server and inform the notifier
	}
	public void updateGameAsync(Game game, int subscriberIdent) {
		//TODO
	}
	
	public Game getGame(int gameId) throws ServerCommunicationException {
		//TODO
		return null;
	}
	public void getGameAsync(int gameId, int subscriberIdent) {
		//TODO
	}
	
	public void setMove(IMove move) throws ServerCommunicationException {
		//TODO
	}
	public void setMoveAsync(IMove move, int subscriberIdent) {
		//TODO
	}
	
	public String getConfig(String configName) throws ServerCommunicationException {
		//TODO
		return null;
	}
	public void getConfigAsync(String configName, int subscriberIdent) {
		//TODO
	}
	
	public void createGame(List<String> players) throws ServerCommunicationException {
		//TODO
	}
	public void createGameAsync(List<String> players, int subscriberIdent) {
		//TODO
	}
	
	public void createUser(String username, String password) throws ServerCommunicationException {
		//TODO
	}
	public void createUserAsync(String username, String password, int subscriberIdent) {
		//TODO
	}
	
	public void updateUser(String currentUsername, String currentPassword, String updatedUsername, String updatedPassword)
			throws ServerCommunicationException {
		//TODO
	}
	public void updateUserAsync(String currentUsername, String currentPassword, String updatedUsername, String updatedPassword, int subscriberIdent) {
		//TODO
	}
	
	public void verifyUser(String username, String password) throws ServerCommunicationException {
		//TODO
	}
	public void verifyUserAsync(String username, String password, int subscriberIdent) {
		//TODO
	}
	
	public GameList listGames(boolean complete, String username) throws ServerCommunicationException {
		//TODO
		return null;
	}
	public void listGamesAsync(boolean complete, String username, int subscriberIdent) {
		//TODO
	}
	
	public MoveList listMoves(int gameId, String username, int numMoves) throws ServerCommunicationException {
		//TODO
		return null;
	}
	public void listMovesAsync(int gameId, String username, int numMoves, int subscriberIdent) {
		//TODO
	}
	
	public void registerSubscriber(GenesisClientEventSubscriber subscriber) {
		consumers.add(subscriber);
	}
	public void removeSubscriber(GenesisClientEventSubscriber subscriber) {
		consumers.remove(subscriber);
	}
	
	private Response sendServerRequest(String resource, String requestType, Entity<?> entity) {
		String serverURI = "http://" + hosts.getProperty(CONFIG_KEY_SERVER_HOST) + ":" + hosts.getProperty(CONFIG_KEY_SERVER_PORT)
				+ "/genesis_project_server/genesis_project/genesis_project/";
		return sendRequest(serverURI, resource, requestType, entity);
	}
	
	/**
	 * Send a request to a host server using HTTP GET or POST.
	 */
	public static Response sendRequest(String host, String resource, String requestType, Entity<?> entity) {
		LOGGER.debug("sending request to URI: {}/{} (type: {}   entity: {})", host, resource, requestType, entity);
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(host).path(resource);
		Response response = null;
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
	
	public static String getHostProperty(String key) {
		return hosts.getProperty(key);
	}
}
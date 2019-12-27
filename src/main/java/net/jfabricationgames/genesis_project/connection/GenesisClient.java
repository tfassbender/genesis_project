package net.jfabricationgames.genesis_project.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.move.IMove;
import net.jfabricationgames.genesis_project_server.game.GameList;
import net.jfabricationgames.genesis_project_server.game.MoveList;

public class GenesisClient {
	
	private List<GenesisClientEventSubscriber> consumers;
	
	private static final Logger LOGGER = LogManager.getLogger(GenesisClient.class);
	
	private static final String CONFIG_RESOURCE_FILE = "config/hosts.config";
	private static final String CONFIG_KEY_NOTIFIER_HOST = "notifier.host";
	private static final String CONFIG_KEY_NOTIFIER_PORT = "notifier.port";
	private static final String CONFIG_KEY_SERVER_HOST = "server.host";
	private static final String CONFIG_KEY_SERVER_PORT = "server.port";
	private Properties hosts;
	
	public GenesisClient() throws IOException {
		consumers = new ArrayList<GenesisClientEventSubscriber>();
		loadConfiguration();
		LOGGER.info("Host configuration loaded: {}", hosts);
	}
	
	private void loadConfiguration() throws IOException {
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
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(serverURI).path(resource);
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
	
	private Response informPlayers(String message, String sender, List<Player> players) {
		List<String> usernames = players.stream().map(p -> p.getUser().getUsername()).collect(Collectors.toList());
		Notification notification = new Notification(message, sender, usernames);
		return sendNotifierRequest("notify", "POST", Entity.entity(notification, MediaType.APPLICATION_JSON));
	}
	private Response sendNotifierRequest(String resource, String requestType, Entity<?> entity) {
		String notifierURI = "http://" + hosts.getProperty(CONFIG_KEY_NOTIFIER_HOST) + ":" + hosts.getProperty(CONFIG_KEY_NOTIFIER_PORT)
				+ "JFG_Notification/notification/notification/";
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(notifierURI).path(resource);
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
}
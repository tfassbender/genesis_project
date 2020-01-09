package net.jfabricationgames.genesis_project.connection.notifier;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.jfabricationgames.genesis_project.connection.GenesisClient;
import net.jfabricationgames.genesis_project.connection.exception.ServerCommunicationException;

public class NotifierService {
	
	private static final Logger LOGGER = LogManager.getLogger(NotifierService.class);
	
	/**
	 * A prefix that is used for the users that subscribe to the notifier service.
	 */
	public static final String USERNAME_PREFIX = "genesis_project/";
	/**
	 * A name that is to be used to send a broadcast to all players of this game.
	 */
	public static final String BROADCAST_NAME = USERNAME_PREFIX + ".*";
	
	private static NotifierService instance;
	
	private static String username;
	
	private List<NotificationMessageListener> listeners;
	
	private NotifierSubscriberClient client;
	
	private NotifierService(String username) throws IOException {
		loadConfiguration();
		client = new NotifierSubscriberClient(username, this);
		listeners = new ArrayList<NotificationMessageListener>();
		LOGGER.info("NotifierService started");
	}
	
	public static synchronized NotifierService startNotifierService(String username) throws IOException {
		if (instance == null) {
			username = USERNAME_PREFIX + username;
			instance = new NotifierService(username);
			return instance;
		}
		else {
			throw new IllegalStateException("The NotifierService has already been initialized");
		}
	}
	public static synchronized NotifierService getInstance() {
		if (instance == null) {
			throw new IllegalStateException(
					"The NotifierService has not yet been initialized (use startNotifierService(String) method to initialize)");
		}
		return instance;
	}
	public static synchronized boolean isStarted() {
		return instance != null;
	}
	
	public void closeConnection() throws IOException {
		client.closeConnection();
	}
	
	private void loadConfiguration() throws IOException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties configProperties = new Properties();
		try (InputStream resourceStream = loader.getResourceAsStream(GenesisClient.CONFIG_RESOURCE_FILE)) {
			configProperties.load(resourceStream);
		}
	}
	
	protected void handleMessageFromService(String message) {
		LOGGER.debug("received notification message: {}", message);
		//inform all listeners
		listeners.forEach(l -> l.receiveNotificationMessage(message));
	}
	
	/**
	 * Use a broadcast to inform all other players about changes.
	 */
	public Response informAllPlayers(String message) throws ServerCommunicationException {
		Notification notification = new Notification(message, username, Arrays.asList(BROADCAST_NAME));
		return sendNotifierRequest("notify", "POST", Entity.entity(notification, MediaType.APPLICATION_JSON));
	}
	/**
	 * Use the notifier service to inform other players about any changes.
	 */
	public Response informPlayers(String message, String... players) throws ServerCommunicationException {
		return informPlayers(message, Arrays.asList(players));
	}
	/**
	 * Use the notifier service to inform other players about any changes.
	 */
	public Response informPlayers(String message, List<String> players) throws ServerCommunicationException {
		//map the players to their usernames with the default prefix for the notifier service
		List<String> usernames = players.stream().map(p -> NotifierService.USERNAME_PREFIX + p).collect(Collectors.toList());
		Notification notification = new Notification(message, username, usernames);
		return sendNotifierRequest("notify", "POST", Entity.entity(notification, MediaType.APPLICATION_JSON));
	}
	private Response sendNotifierRequest(String resource, String requestType, Entity<?> entity) throws ServerCommunicationException {
		String notifierURI = "http://" + GenesisClient.getHostProperty(GenesisClient.CONFIG_KEY_NOTIFIER_HOST) + ":"
				+ GenesisClient.getHostProperty(GenesisClient.CONFIG_KEY_NOTIFIER_PORT) + "/JFG_Notification/notification/notification/";
		return GenesisClient.sendRequest(notifierURI, resource, requestType, entity);
	}
	
	public void addNotificationMessageListener(NotificationMessageListener listener) {
		listeners.add(listener);
	}
	public void removeNotifiationMessageListener(NotificationMessageListener listener) {
		listeners.remove(listener);
	}
}
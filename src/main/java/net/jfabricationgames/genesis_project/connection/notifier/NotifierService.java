package net.jfabricationgames.genesis_project.connection.notifier;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.jfabricationgames.genesis_project.connection.GenesisClient;
import net.jfabricationgames.genesis_project.game.Player;

public class NotifierService {
	
	private static final Logger LOGGER = LogManager.getLogger(NotifierService.class);
	
	/**
	 * A prefix that is used for the users that subscribe to the notifier service.
	 */
	public static final String USERNAME_PREFIX = "genesis_project/";
	
	private static NotifierService instance;
	
	private static String username;
	
	private List<NotificationMessageListener> listeners;
	
	private NotifierService(String username) throws IOException {
		loadConfiguration();
	}
	
	public static synchronized NotifierService startNotifierService(Player player) throws IOException {
		if (instance == null) {
			username = USERNAME_PREFIX + player.getUser().getUsername();
			instance = new NotifierService(username);
			return instance;
		}
		else {
			throw new IllegalStateException("The NotifierService has already been initialized");
		}
	}
	public static NotifierService getInstance() {
		if (instance == null) {
			throw new IllegalStateException(
					"The NotifierService has not yet been initialized (use startNotifierService(Player) method to initialize)");
		}
		return instance;
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
	 * Use the notifier service to inform other players about any changes.
	 */
	public Response informPlayers(String message, List<Player> players) {
		//map the players to their usernames with the default prefix for the notifier service
		List<String> usernames = players.stream().map(p -> NotifierService.USERNAME_PREFIX + p.getUser().getUsername()).collect(Collectors.toList());
		Notification notification = new Notification(message, username, usernames);
		return sendNotifierRequest("notify", "POST", Entity.entity(notification, MediaType.APPLICATION_JSON));
	}
	private Response sendNotifierRequest(String resource, String requestType, Entity<?> entity) {
		String notifierURI = "http://" + GenesisClient.getHostProperty(GenesisClient.CONFIG_KEY_NOTIFIER_HOST) + ":"
				+ GenesisClient.getHostProperty(GenesisClient.CONFIG_KEY_NOTIFIER_PORT) + "JFG_Notification/notification/notification/";
		return GenesisClient.sendRequest(notifierURI, resource, requestType, entity);
	}
	
	public void addNotificationMessageListener(NotificationMessageListener listener) {
		listeners.add(listener);
	}
	public void removeNotifiationMessageListener(NotificationMessageListener listener) {
		listeners.remove(listener);
	}
}
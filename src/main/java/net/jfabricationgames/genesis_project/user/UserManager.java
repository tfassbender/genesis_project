package net.jfabricationgames.genesis_project.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.jfabricationgames.genesis_project.connection.notifier.NotificationMessageListener;
import net.jfabricationgames.genesis_project.connection.notifier.NotifierService;

public class UserManager implements NotificationMessageListener {
	
	private static final Logger LOGGER = LogManager.getLogger(UserManager.class);
	
	public static final String NOTIFIER_PREFIX = "user_state_change/";
	public static final String NOTIFIER_REQUEST_USERNAMES = "request_usernames";
	public static final String NOTIFIER_ANSWER_USERNAME = "user_online/";
	public static final String NOTIFIER_NEW_USER_LOGGED_IN = "logged_in/";
	public static final String NOTIFIER_USER_LOGGING_OFF = "logging_off/";
	
	private Set<String> usersOnline;
	
	private List<UserStateListener> listeners;
	
	private String localUsername;
	
	private static UserManager instance;
	
	private UserManager(String username) throws IOException {
		this.localUsername = username;
		usersOnline = new HashSet<String>();
		listeners = new ArrayList<UserStateListener>();
		
		usersOnline.add(username);
		
		if (!NotifierService.isStarted()) {
			//should already be started, but...
			NotifierService.startNotifierService(username);
		}
		NotifierService notifier = NotifierService.getInstance();
		
		//register for notifications of other users
		notifier.addNotificationMessageListener(this);
		//tell all other players that this player registered and is now online
		Response informPlayers = notifier.informAllPlayers(NOTIFIER_NEW_USER_LOGGED_IN + username);
		if (informPlayers.getStatus() != Status.OK.getStatusCode()) {
			throw new IllegalStateException(
					"Failed to inform other players about the login (Response status was HTTP " + informPlayers.getStatus() + ")");
		}
		//send a request to all other users to tell their names
		Response requestNames = notifier.informAllPlayers(NOTIFIER_REQUEST_USERNAMES);
		if (requestNames.getStatus() != Status.OK.getStatusCode()) {
			throw new IllegalStateException("Failed to request other players usernames (Response status was HTTP " + requestNames.getStatus() + ")");
		}
		
		LOGGER.info("UserManager started successfully");
	}
	
	public static synchronized UserManager startUserManager(String username) throws IllegalStateException, IOException {
		if (instance == null) {
			instance = new UserManager(username);
			return instance;
		}
		else {
			throw new IllegalStateException("The UserManager has already been initialized");
		}
	}
	public static synchronized UserManager getInstance() {
		if (instance != null) {
			return instance;
		}
		else {
			throw new IllegalStateException("The UserManager has not yet been initialized");
		}
	}
	public static synchronized boolean isInitialized() {
		return instance != null;
	}
	
	public Set<String> getUsersOnline() {
		return usersOnline;
	}
	
	public String getLocalUsername() {
		return localUsername;
	}
	
	/**
	 * Inform the other users about the logout and close the notifier service.
	 */
	public void logout() throws IOException, IllegalStateException {
		LOGGER.debug("logging out");
		NotifierService notifier = NotifierService.getInstance();
		//inform the other users
		Response logout = notifier.informAllPlayers(NOTIFIER_USER_LOGGING_OFF + localUsername);
		if (logout.getStatus() != Status.OK.getStatusCode()) {
			throw new IllegalStateException("Failed to inform other players about logout (Response status was HTTP " + logout.getStatus() + ")");
		}
		//close the connection to the notifier service
		notifier.closeConnection();
	}
	
	@Override
	public void receiveNotificationMessage(String notificationMessage) {
		//only handle messages that start with the correct prefix
		if (notificationMessage.startsWith(NOTIFIER_PREFIX)) {
			String[] split = notificationMessage.split("/");
			if (split.length > 1) {
				switch (split[1] + "/") {
					case NOTIFIER_REQUEST_USERNAMES + "/":
						//answer with a broadcast of my username
						Response answerUsername = NotifierService.getInstance().informAllPlayers(NOTIFIER_ANSWER_USERNAME + localUsername);
						if (answerUsername.getStatus() != Status.OK.getStatusCode()) {
							LOGGER.error("Couldn't send a username answer to other players (Response status was HTTP {})",
									answerUsername.getStatus());
						}
						break;
					case NOTIFIER_ANSWER_USERNAME:
					case NOTIFIER_NEW_USER_LOGGED_IN:
						//add the new user to my list
						if (split.length > 2) {
							String username = split[2];
							usersOnline.add(username);
						}
						informListeners();
						break;
					case NOTIFIER_USER_LOGGING_OFF:
						//remove the user that logged off
						if (split.length > 2) {
							String username = split[2];
							usersOnline.remove(username);
						}
						informListeners();
						break;
				}
			}
		}
	}
	
	private void informListeners() {
		listeners.forEach(l -> l.receiveUserList(new ArrayList<String>(usersOnline)));
	}
	
	public void addUserStateListener(UserStateListener listener) {
		listeners.add(listener);
	}
	public void removeUserStateListener(UserStateListener listener) {
		listeners.remove(listener);
	}
}
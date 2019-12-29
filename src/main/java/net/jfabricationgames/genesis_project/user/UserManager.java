package net.jfabricationgames.genesis_project.user;

import java.util.List;

public class UserManager {
	
	private List<String> usersOnline;
	
	private String localUsername;
	
	private static UserManager instance;
	
	private UserManager(String username) {
		this.localUsername = username;
	}
	
	public static synchronized UserManager startUserManager(String username) throws IllegalStateException {
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
	
	public List<String> getUsersOnline() {
		return usersOnline;
	}
	
	public String getLocalUsername() {
		return localUsername;
	}
}
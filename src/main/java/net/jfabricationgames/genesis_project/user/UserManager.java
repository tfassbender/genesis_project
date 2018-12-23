package net.jfabricationgames.genesis_project.user;

import java.util.List;
import java.util.Optional;

public abstract class UserManager {
	
	private static List<User> users;
	
	private static String localUsername;
	
	public static User getLocalUser() {
		Optional<User> user = users.stream().filter((u) -> u.getUsername().equals(localUsername)).findFirst();
		return user.orElse(null);
	}
	
	public static List<User> getUsers() {
		return users;
	}
	public static void setUsers(List<User> users) {
		UserManager.users = users;
	}
	
	public static String getLocalUsername() {
		return localUsername;
	}
	public static void setLocalUsername(String localUsername) {
		UserManager.localUsername = localUsername;
	}
}
package net.jfabricationgames.genesis_project.user;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "username")
public class User implements Serializable {
	
	private static final long serialVersionUID = -1283513600616522694L;
	
	private String username;
	private boolean online;
	private boolean inGame;
	
	/**
	 * DO NOT USE - empty constructor for json deserialization
	 */
	@Deprecated
	public User() {
		
	}
	
	public User(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return username;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			return username.equals(((User) obj).getUsername());
		}
		else {
			return super.equals(obj);
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(username);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	
	public boolean isInGame() {
		return inGame;
	}
	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}
}
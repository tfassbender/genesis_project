package net.jfabricationgames.genesis_project.user;

import java.util.ArrayList;

public interface UserStateListener {
	
	public void receiveUserList(ArrayList<String> usersOnline);
}
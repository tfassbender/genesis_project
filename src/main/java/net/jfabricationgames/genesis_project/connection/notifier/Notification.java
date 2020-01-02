package net.jfabricationgames.genesis_project.connection.notifier;

import java.util.Arrays;
import java.util.List;

/**
 * A simple notification for other players (source:
 * https://github.com/tfassbender/notifier/blob/master/src/main/java/net/jfabricationgames/notifier/notification/Notification.java)
 */
public class Notification {
	
	private String message;
	private String sender;
	private List<String> receivers;
	
	public Notification() {
		//default constructor for serialization
	}
	public Notification(String message, String sender, String... receivers) {
		this(message, sender, Arrays.asList(receivers));
	}
	public Notification(String message, String sender, List<String> receivers) {
		this.message = message;
		this.sender = sender;
		this.receivers = receivers;
	}
	
	@Override
	public String toString() {
		return "Notification [message=" + message + ", sender=" + sender + ", receivers=" + receivers + "]";
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public List<String> getReceivers() {
		return receivers;
	}
	public void setReceivers(List<String> receivers) {
		this.receivers = receivers;
	}
}
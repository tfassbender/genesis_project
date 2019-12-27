package net.jfabricationgames.genesis_project.connection;

public class ServerCommunicationException extends Exception {
	
	private static final long serialVersionUID = -5143887259017244585L;
	
	public ServerCommunicationException(String message, Throwable cause) {
		super(message, cause);
	}
	public ServerCommunicationException(String message) {
		super(message);
	}
}
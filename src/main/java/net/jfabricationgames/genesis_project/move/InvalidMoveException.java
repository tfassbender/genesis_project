package net.jfabricationgames.genesis_project.move;

public class InvalidMoveException extends Exception {
	
	private static final long serialVersionUID = 4511879390760276345L;
	
	public InvalidMoveException(String message, Throwable cause) {
		super(message, cause);
	}
	public InvalidMoveException(String message) {
		super(message);
	}
}
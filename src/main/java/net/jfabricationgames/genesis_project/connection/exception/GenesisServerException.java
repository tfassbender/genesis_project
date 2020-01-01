package net.jfabricationgames.genesis_project.connection.exception;

public class GenesisServerException extends Exception {
	
	private static final long serialVersionUID = -2680989490933343837L;
	
	public GenesisServerException(String message, Throwable cause) {
		super(message, cause);
	}
	public GenesisServerException(String message) {
		super(message);
	}
}
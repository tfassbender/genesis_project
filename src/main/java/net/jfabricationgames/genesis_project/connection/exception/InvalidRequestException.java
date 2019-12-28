package net.jfabricationgames.genesis_project.connection.exception;

public class InvalidRequestException extends GenesisServerException {
	
	private static final long serialVersionUID = -5123877297724450002L;
	
	public InvalidRequestException(String message, Throwable cause) {
		super(message, cause);
	}
	public InvalidRequestException(String message) {
		super(message);
	}
}
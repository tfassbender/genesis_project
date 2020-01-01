package net.jfabricationgames.genesis_project.connection.exception;

public class AuthenticationException extends GenesisServerException {
	
	private static final long serialVersionUID = -7017368585394975172L;
	
	public AuthenticationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	public AuthenticationException(String arg0) {
		super(arg0);
	}
}
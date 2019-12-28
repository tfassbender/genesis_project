package net.jfabricationgames.genesis_project.connection;

import net.jfabricationgames.genesis_project.connection.exception.GenesisServerException;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project_server.game.GameList;
import net.jfabricationgames.genesis_project_server.game.MoveList;

public class AbstractGenesisClientEventSubscriber implements GenesisClientEventSubscriber {
	
	@Override
	public void receiveLoginSucessful() {
		
	}
	
	@Override
	public void receiveUpdateGameSuccessful() {
		
	}
	
	@Override
	public void receiveGetGameAnswer(Game game) {
		
	}
	
	@Override
	public void receiveSetMoveSuccessful() {
		
	}
	
	@Override
	public void receiveGetConfigAnswer(String config) {
		
	}
	
	@Override
	public void receiveCreateGameAnswer(int gameId) {
		
	}
	
	@Override
	public void receiveCreateUserSuccessful() {
		
	}
	
	@Override
	public void receiveUpdateUserSuccessful() {
		
	}
	
	@Override
	public void receiveVerifyUserSuccessful() {
		
	}
	
	@Override
	public void receiveListGamesAnswer(GameList gameList) {
		
	}
	
	@Override
	public void receiveListMovesAnswer(MoveList moveList) {
		
	}
	
	@Override
	public void receiveException(GenesisServerException exception) {
		
	}
}
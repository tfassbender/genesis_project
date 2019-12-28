package net.jfabricationgames.genesis_project.connection;

import net.jfabricationgames.genesis_project.connection.exception.ServerCommunicationException;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project_server.game.GameList;
import net.jfabricationgames.genesis_project_server.game.MoveList;

public class AbstractGenesisClientEventSubscriber implements GenesisClientEventSubscriber {
	
	@Override
	public void receiveGameUpdateAnswer(boolean successful, int subscriberIdent) {
		
	}
	
	@Override
	public void receiveGetGameAnswer(Game game, int subscriberIdent) {
		
	}
	
	@Override
	public void receiveSetMoveAnswer(boolean successful, int subscriberIdent) {
		
	}
	
	@Override
	public void receiveGetConfigAnswer(String config, int subscriberIdent) {
		
	}
	
	@Override
	public void receiveCreateGameAnswer(int gameId, int subscriberIdent) {
		
	}
	
	@Override
	public void receiveCreateUserAnswer(boolean sucessful, int subscriberIdent) {
		
	}
	
	@Override
	public void receiveUpdateUserAnswer(boolean sucessful, int subscriberIdent) {
		
	}
	
	@Override
	public void receiveVerifyUserAnswer(boolean sucessful, int subscriberIdent) {
		
	}
	
	@Override
	public void receiveListGamesAnswer(GameList gameList, int subscriberIdent) {
		
	}
	
	@Override
	public void receiveListMovesAnswer(MoveList moveList, int subscriberIdent) {
		
	}
	
	@Override
	public void receiveException(ServerCommunicationException exception, int subscriberIdent) {
		
	}
}
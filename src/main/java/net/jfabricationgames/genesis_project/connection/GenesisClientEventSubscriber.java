package net.jfabricationgames.genesis_project.connection;

import net.jfabricationgames.genesis_project.connection.exception.ServerCommunicationException;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project_server.game.GameList;
import net.jfabricationgames.genesis_project_server.game.MoveList;

public interface GenesisClientEventSubscriber {
	
	public void receiveGameUpdateAnswer(boolean successful, int subscriberIdent);
	public void receiveGetGameAnswer(Game game, int subscriberIdent);
	public void receiveSetMoveAnswer(boolean successful, int subscriberIdent);
	public void receiveGetConfigAnswer(String config, int subscriberIdent);
	public void receiveCreateGameAnswer(int gameId, int subscriberIdent);
	public void receiveCreateUserAnswer(boolean sucessful, int subscriberIdent);
	public void receiveUpdateUserAnswer(boolean sucessful, int subscriberIdent);
	public void receiveVerifyUserAnswer(boolean sucessful, int subscriberIdent);
	public void receiveListGamesAnswer(GameList gameList, int subscriberIdent);
	public void receiveListMovesAnswer(MoveList moveList, int subscriberIdent);
	
	public void receiveException(ServerCommunicationException exception, int subscriberIdent);
}
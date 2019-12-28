package net.jfabricationgames.genesis_project.connection;

import net.jfabricationgames.genesis_project.connection.exception.GenesisServerException;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project_server.game.GameList;
import net.jfabricationgames.genesis_project_server.game.MoveList;

public interface GenesisClientEventSubscriber {
	
	public void receiveLoginSucessful();
	public void receiveUpdateGameSuccessful();
	public void receiveGetGameAnswer(Game game);
	public void receiveSetMoveSuccessful();
	public void receiveGetConfigAnswer(String config);
	public void receiveCreateGameAnswer(int gameId);
	public void receiveCreateUserSuccessful();
	public void receiveUpdateUserSuccessful();
	public void receiveVerifyUserSuccessful();
	public void receiveListGamesAnswer(GameList gameList);
	public void receiveListMovesAnswer(MoveList moveList);
	
	public void receiveException(GenesisServerException exception);
}
package net.jfabricationgames.genesis_project.game;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.jfabricationgames.genesis_project.connection.exception.ServerCommunicationException;
import net.jfabricationgames.genesis_project.connection.notifier.NotifierService;
import net.jfabricationgames.genesis_project.game_frame.BoardPaneController;
import net.jfabricationgames.genesis_project.manager.GameManager;
import net.jfabricationgames.genesis_project.move.IMove;
import net.jfabricationgames.genesis_project.testUtils.ConstantsInitializerUtil;
import net.jfabricationgames.genesis_project.user.UserManager;

public class BoardCreatorManualTest extends Application {
	
	private BoardPaneController controller;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void createGame(int gameId) {
		final String localPlayer = "Player1";
		final String player2 = "Player2";
		
		//initialize all services
		ConstantsInitializerUtil.initAll();
		try {
			NotifierService.startNotifierService(localPlayer);
			UserManager.startUserManager(localPlayer);
		}
		catch (IOException | IllegalStateException | ServerCommunicationException e) {
			e.printStackTrace();
		}
		
		//create a game
		Game game = new Game(gameId, Arrays.asList(new Player(localPlayer), new Player(player2)), localPlayer);
		//create a spy to mock only the isMoveExecutable method
		Game spy = spy(game);
		doReturn(true).when(spy).isMoveExecutable(any(IMove.class));
		
		//add the (partially mocked) game to the manager so the board controller can find the references
		GameManager.getInstance().addGame(gameId, spy);
	}
	
	@Override
	public void start(Stage primaryStage) {
		final int gameId = 42;
		createGame(gameId);
		
		controller = new BoardPaneController(gameId, true);
		try {
			URL fxmlUrl = getClass().getResource("/net/jfabricationgames/genesis_project/game_frame/BoardPane.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
			fxmlLoader.setController(controller);
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			primaryStage.setTitle("Board - Manual Test");
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
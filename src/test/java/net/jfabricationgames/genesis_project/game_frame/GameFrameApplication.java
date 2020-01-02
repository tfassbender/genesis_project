package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.manager.IResourceManager;
import net.jfabricationgames.genesis_project.testUtils.GameCreationUtil;

/**
 * This Main-Class is only used for GUI-Tests.
 * 
 * Don't start the application using this class.
 */
public class GameFrameApplication extends Application {
	
	public static final String APPLCIATION_NAME = "Genesis Project";
	
	private Game game;
	private GameFrameController controller;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		game = GameCreationUtil.createGame();
		controller = new GameFrameController(game.getId());
		game.setGameFrameController(controller);
		try {
			URL fxmlUrl = getClass().getResource("/net/jfabricationgames/genesis_project/game_frame/GameFrame.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
			fxmlLoader.setController(controller);
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root, 1500, 900);
			primaryStage.setTitle(APPLCIATION_NAME);
			primaryStage.setScene(scene);
			controller.setStage(primaryStage);
			primaryStage.show();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		//TESTS
		//increaseResourcesAfter5Seconds_testsBindings();
	}
	
	@SuppressWarnings("unused")
	private void increaseResourcesAfter5Seconds_testsBindings() {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				}
				catch (InterruptedException ie) {
					ie.printStackTrace();
					Thread.currentThread().interrupt();
				}
				IResourceManager resourceManager = game.getLocalPlayer().getResourceManager();
				resourceManager.setResourcesC(1);
				resourceManager.setResourcesSi(2);
				resourceManager.setResourcesFe(3);
				resourceManager.setResearchPoints(4);
				resourceManager.setScientists(5);
				game.getLocalPlayer().getResourceManager().setFTL(6);
				System.out.println("increased ftl");
			}
		});
	}
}
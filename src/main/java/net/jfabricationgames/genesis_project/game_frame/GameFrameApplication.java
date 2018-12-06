package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This Main-Class is only used for GUI-Tests.
 * 
 * Don't start the application using this class.
 */
public class GameFrameApplication extends Application {
	
	public static final String APPLCIATION_NAME = "Genesis Project";
	
	private GameFrameController controller = new GameFrameController();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			URL fxmlUrl = getClass().getResource("GameFrame.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
			fxmlLoader.setController(controller);
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root, 1000, 700);
			primaryStage.setTitle(APPLCIATION_NAME);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
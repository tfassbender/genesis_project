package net.jfabricationgames.genesis_project.main_menu;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginApplication extends Application {
	
	private LoginFrameController controller;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		controller = new LoginFrameController();
		try {
			URL fxmlUrl = getClass().getResource("/net/jfabricationgames/genesis_project/main_menu/LoginFrame.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
			fxmlLoader.setController(controller);
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			primaryStage.setTitle("Login - Genesis Project");
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
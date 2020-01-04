package net.jfabricationgames.genesis_project.main_menu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.jfabricationgames.genesis_project.connection.AbstractGenesisClientEventSubscriber;
import net.jfabricationgames.genesis_project.connection.GenesisClient;
import net.jfabricationgames.genesis_project.connection.exception.AuthenticationException;
import net.jfabricationgames.genesis_project.connection.exception.GenesisServerException;
import net.jfabricationgames.genesis_project.connection.exception.InvalidRequestException;
import net.jfabricationgames.genesis_project.game_frame.DialogUtils;

public class LoginFrameController implements Initializable {
	
	private static final Logger LOGGER = LogManager.getLogger(LoginFrameController.class);
	
	public static final int USERNAME_MIN_CHARS = 3;
	public static final int PASSWORD_MIN_CHARS = 5;
	
	@FXML
	private TextField textFieldName;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Button buttonLogin;
	@FXML
	private Button buttonNewAccount;
	@FXML
	private Label labelLoading;
	
	private GenesisClient genesisClient;
	
	public LoginFrameController() {
		try {
			genesisClient = new GenesisClient();
		}
		catch (IOException ioe) {
			LOGGER.fatal("GenesisClient could not be created", ioe);
			DialogUtils.showExceptionDialog("Verbindungsprobleme", "Serververbindung konnte nicht hergestellt werden", ioe, false);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		buttonLogin.setOnAction(e -> login());
		buttonNewAccount.setOnAction(e -> createNewUser());
	}
	
	private void createNewUser() {
		try {
			URL fxmlUrl = getClass().getResource("/net/jfabricationgames/genesis_project/main_menu/SignUpFrame.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
			fxmlLoader.setController(new SignUpFrameController(this));
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setTitle("Benutzer erstellen - Genesis Project");
			stage.setScene(scene);
			stage.show();
			
			((Stage) labelLoading.getScene().getWindow()).close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void startMainMenu() {
		try {
			URL fxmlUrl = getClass().getResource("/net/jfabricationgames/genesis_project/main_menu/MainMenuFrame.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
			fxmlLoader.setController(new MainMenuController());
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setTitle("Main Menü - Genesis Project");
			stage.setScene(scene);
			stage.show();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void login() {
		String username = textFieldName.getText();
		String password = passwordField.getText();
		
		if (isUsernameAndPasswordValid(username, password)) {
			if (genesisClient == null) {
				DialogUtils.showErrorDialog("Verbindungsprobleme", "Serververbindung konnte nicht hergestellt werden", "", true);
				return;
			}
			
			//login using an asynchron client request
			genesisClient.verifyUserAsync(username, password, new AbstractGenesisClientEventSubscriber() {
				
				@Override
				public void receiveVerifyUserSuccessful() {
					labelLoading.setText("");
					startMainMenu();
				}
				
				@Override
				public void receiveException(GenesisServerException exception) {
					labelLoading.setText("");
					if (exception instanceof AuthenticationException) {
						DialogUtils.showErrorDialog("Login nicht erfolgreich", "Der Benutzername oder das Passwort sind falsch", "", true);
					}
					else if (exception instanceof InvalidRequestException) {
						DialogUtils.showErrorDialog("Login nicht erfolgreich", "Dieser Benutzername wurde nicht gefunden", "", true);
					}
					else {
						DialogUtils.showExceptionDialog("Login nicht erfolgreich", "Unbekannter Fehler", exception, false);
					}
				}
			});
			
			labelLoading.setText("Login wird bearteitet...");
		}
	}
	
	protected static boolean isUsernameAndPasswordValid(String username, String password) {
		//test username and password conditions
		if (username == null || username.length() < USERNAME_MIN_CHARS) {
			DialogUtils.showErrorDialog("Ungültiger Name", "Der name ist zu kurz (mindestens " + USERNAME_MIN_CHARS + " Zeichen)", "", true);
			return false;
		}
		if (!isUsernameValid(username)) {
			DialogUtils.showErrorDialog("Ungültiger Name", "Ein Benutzername darf nur die Zeichen a-z, A-Z, 0-9, _ oder - enthallten", "", true);
			return false;
		}
		if (password == null || password.length() < PASSWORD_MIN_CHARS) {
			DialogUtils.showErrorDialog("Ungültiges Password", "Das Password ist zu kurz (mindestens " + PASSWORD_MIN_CHARS + " Zeichen)", "", true);
			return false;
		}
		return true;
	}
	
	protected static boolean isUsernameValid(String username) {
		return username.length() >= USERNAME_MIN_CHARS && Pattern.matches("[a-zA-Z0-9_-]+", username);
	}
	protected static boolean isPasswordValid(String password) {
		return password.length() >= PASSWORD_MIN_CHARS;
	}
	
	public void setUsername(String username) {
		textFieldName.setText(username);
	}
	
	public void setVisible(boolean visible) {
		Stage stage = ((Stage) labelLoading.getScene().getWindow());
		if (visible) {
			stage.show();
		}
		else {
			stage.hide();
		}
	}
}
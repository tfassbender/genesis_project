package net.jfabricationgames.genesis_project.main_menu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
	private TextField textAreaName;
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
	}
	
	private void login() {
		String username = textAreaName.getText();
		String password = passwordField.getText();
		
		//test username and password conditions
		if (genesisClient == null) {
			DialogUtils.showErrorDialog("Verbindungsprobleme", "Serververbindung konnte nicht hergestellt werden", "", true);
			return;
		}
		if (username == null || username.length() < USERNAME_MIN_CHARS) {
			DialogUtils.showErrorDialog("Ungültiger Name", "Der name ist zu kurz (mindestens " + USERNAME_MIN_CHARS + " Zeichen)", "", true);
			return;
		}
		if (!isUsernameValid(username)) {
			DialogUtils.showErrorDialog("Ungültiger Name", "Ein Benutzername darf nur die Zeichen a-z, A-Z, 0-9, _ oder - enthallten", "", true);
			return;
		}
		if (password == null || password.length() < PASSWORD_MIN_CHARS) {
			DialogUtils.showErrorDialog("Ungültiges Password", "Das Password ist zu kurz (mindestens " + PASSWORD_MIN_CHARS + " Zeichen)", "", true);
			return;
		}
		
		//login using an asynchron client request
		genesisClient.verifyUserAsync(username, password, new AbstractGenesisClientEventSubscriber() {
			
			@Override
			public void receiveVerifyUserSuccessful() {
				labelLoading.setText("");
				//TODO open main menu
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
	
	protected boolean isUsernameValid(String username) {
		return username.length() >= USERNAME_MIN_CHARS && Pattern.matches("[a-zA-Z0-9_-]+", username);
	}
	protected boolean isPasswordValid(String password) {
		return password.length() >= PASSWORD_MIN_CHARS;
	}
}
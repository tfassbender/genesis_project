package net.jfabricationgames.genesis_project.main_menu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.jfabricationgames.genesis_project.connection.AbstractGenesisClientEventSubscriber;
import net.jfabricationgames.genesis_project.connection.GenesisClient;
import net.jfabricationgames.genesis_project.connection.exception.GenesisServerException;
import net.jfabricationgames.genesis_project.connection.exception.InvalidRequestException;
import net.jfabricationgames.genesis_project.game_frame.DialogUtils;

public class SignUpFrameController implements Initializable {
	
	private static final Logger LOGGER = LogManager.getLogger(SignUpFrameController.class);
	
	@FXML
	private TextField textAreaName;
	@FXML
	private PasswordField passwordField;
	@FXML
	private PasswordField passwordFieldCheck;
	@FXML
	private Button buttonCreateUser;
	@FXML
	private Label labelLoading;
	
	private GenesisClient genesisClient;
	
	private LoginFrameController loginController;
	
	public SignUpFrameController(LoginFrameController loginController) {
		this.loginController = loginController;
		loginController.setVisible(false);
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
		buttonCreateUser.setOnAction(e -> createUser());
	}
	
	private void createUser() {
		String username = textAreaName.getText();
		String password = passwordField.getText();
		
		if (LoginFrameController.isUsernameAndPasswordValid(username, password)) {
			if (genesisClient == null) {
				DialogUtils.showErrorDialog("Verbindungsprobleme", "Serververbindung konnte nicht hergestellt werden", "", true);
				return;
			}
			if (!passwordFieldCheck.getText().equals(password)) {
				DialogUtils.showErrorDialog("Passwort wiederholung falsch", "Das Passwort und die Wiederholung des Passworts unterscheiden sich", "");
				return;
			}
			
			//create user using an asynchron client request
			genesisClient.createUserAsync(username, password, new AbstractGenesisClientEventSubscriber() {
				
				@Override
				public void receiveCreateUserSuccessful() {
					Platform.runLater(() -> {
						loginController.setVisible(true);
						((Stage) labelLoading.getScene().getWindow()).close();
					});
				}
				
				@Override
				public void receiveException(GenesisServerException exception) {
					labelLoading.setText("");
					if (exception instanceof InvalidRequestException) {
						DialogUtils.showErrorDialog("Benutzer Erstellung nicht erfolgreich", "Dieser Benutzername wird bereits verwendet", "", true);
					}
					else {
						DialogUtils.showExceptionDialog("Benutzer Erstellung nicht erfolgreich", "Unbekannter Fehler", exception, false);
					}
				}
			});
			
			labelLoading.setText("Benutzer wird erstellt...");
		}
	}
}
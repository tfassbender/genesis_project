package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import net.jfabricationgames.genesis_project.connection.notifier.NotificationMessageListener;
import net.jfabricationgames.genesis_project.connection.notifier.NotifierService;
import net.jfabricationgames.genesis_project.manager.GameManager;
import net.jfabricationgames.genesis_project.user.UserManager;

public class ChatPaneController implements Initializable, NotificationMessageListener {
	
	private static final Logger LOGGER = LogManager.getLogger(ChatPaneController.class);
	
	public static final String NOTIFIER_PREFIX = "game_chat/";
	
	@FXML
	private TextField textFieldChat;
	@FXML
	private Button buttonChatSend;
	@FXML
	private TextArea textAreaChat;
	
	private NotifierService notifier;
	
	private int gameId;
	
	public ChatPaneController(int gameId) {
		this.gameId = gameId;
		notifier = NotifierService.getInstance();
		notifier.addNotificationMessageListener(this);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		buttonChatSend.setOnAction(e -> sendMessage());
		textFieldChat.setOnKeyPressed(key -> {
			if (key.getCode().equals(KeyCode.ENTER)) {
				sendMessage();
			}
		});
	}
	
	public void updateAll() {
		//nothing to do here *flies away*
	}
	
	/**
	 * Send the content of the textField to all players of this game using the NotifierService
	 */
	private void sendMessage() {
		String message = textFieldChat.getText();
		if (message != null && !message.trim().isEmpty()) {
			String username = UserManager.getInstance().getLocalUsername();
			String completeMessage = NOTIFIER_PREFIX + username + "/" + gameId + "/" + message;
			LOGGER.debug("sending chat message: \"{}\" to game: {}", completeMessage, gameId);
			notifier.informPlayers(completeMessage, GameManager.getInstance().getPlayers(gameId));
			//the message is not added to the text area here because this player also receives the message
		}
	}
	
	@Override
	public void receiveNotificationMessage(String notificationMessage) {
		if (notificationMessage.startsWith(NOTIFIER_PREFIX)) {
			String[] split = notificationMessage.split("/");
			if (split.length >= 4) {
				String username = split[1];
				int gameId = -1;
				try {
					gameId = Integer.parseInt(split[2]);
				}
				catch (NumberFormatException nfe) {
					LOGGER.error("gameId couldn't be parsed into an int", nfe);
				}
				
				String message = split[3];
				//concatenate the message if / were used in the message
				for (int i = 4; i < split.length; i++) {
					message += "/" + split[i];
				}
				
				//only add the message if it comes from this game chat
				if (gameId == this.gameId) {
					textAreaChat.appendText(username + ": " + message);
				}
			}
		}
	}
}
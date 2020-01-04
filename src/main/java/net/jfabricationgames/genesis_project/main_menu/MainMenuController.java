package net.jfabricationgames.genesis_project.main_menu;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import net.jfabricationgames.genesis_project.connection.AbstractGenesisClientEventSubscriber;
import net.jfabricationgames.genesis_project.connection.GenesisClient;
import net.jfabricationgames.genesis_project.connection.exception.GenesisServerException;
import net.jfabricationgames.genesis_project.connection.notifier.NotificationMessageListener;
import net.jfabricationgames.genesis_project.connection.notifier.NotifierService;
import net.jfabricationgames.genesis_project.game_frame.DialogUtils;
import net.jfabricationgames.genesis_project.user.UserManager;
import net.jfabricationgames.genesis_project.user.UserStateListener;
import net.jfabricationgames.genesis_project_server.game.GameList;

public class MainMenuController implements Initializable, NotificationMessageListener, UserStateListener {
	
	private static final Logger LOGGER = LogManager.getLogger(LoginFrameController.class);
	
	public static final String NOTIFIER_PREFIX = "global_chat/";
	
	@FXML
	private TextArea textAreaChat;
	@FXML
	private TextField textFieldChat;
	@FXML
	private Button buttonSend;
	
	@FXML
	private TextArea textAreaNews;
	
	@FXML
	private ListView<String> listPlayersOnline;
	@FXML
	private ListView<GameListView> listGames;
	
	private ObservableList<String> playersOnline;
	private ObservableList<GameListView> games;
	
	@FXML
	private Button buttonCreateGame;
	@FXML
	private Button buttonLoadGame;
	
	private NotifierService notifier;
	private GenesisClient genesisClient;
	
	public MainMenuController() {
		notifier = NotifierService.getInstance();
		notifier.addNotificationMessageListener(this);
		try {
			genesisClient = new GenesisClient();
		}
		catch (IOException ioe) {
			LOGGER.fatal("GenesisClient could not be created", ioe);
			DialogUtils.showExceptionDialog("Verbindungsprobleme", "Serververbindung konnte nicht hergestellt werden", ioe, false);
			//disable all functions
			textFieldChat.setEditable(false);
			buttonSend.setDisable(true);
			buttonCreateGame.setDisable(true);
			buttonLoadGame.setDisable(true);
		}
		
		playersOnline = FXCollections.observableArrayList();
		games = FXCollections.observableArrayList();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//chat functions
		buttonSend.setOnAction(e -> sendMessage());
		textFieldChat.setOnKeyPressed(key -> {
			if (key.getCode().equals(KeyCode.ENTER)) {
				sendMessage();
			}
		});
		
		//load some news from the server
		loadNews();
		
		//list the players online
		listPlayersOnline();
		
		//list the open games of this player
		listGames();
		
		//add button functions
		buttonCreateGame.setOnAction(e -> createGame());
		buttonLoadGame.setOnAction(e -> loadGame());
	}
	
	private void loadNews() {
		if (genesisClient != null) {
			genesisClient.getConfigAsync("main_menu_dynamic_content", new AbstractGenesisClientEventSubscriber() {
				
				@Override
				public void receiveGetConfigAnswer(String config) {
					LOGGER.debug("received dynamic content");
					textAreaNews.setText(config);
				}
				
				@Override
				public void receiveException(GenesisServerException exception) {
					LOGGER.error("dynamic content couldn't be loaded", exception);
				}
			});
		}
	}
	
	private void listGames() {
		if (genesisClient != null) {
			genesisClient.listGamesAsync(false, UserManager.getInstance().getLocalUsername(), new AbstractGenesisClientEventSubscriber() {
				
				@Override
				public void receiveListGamesAnswer(GameList gameList) {
					games.clear();
					games.addAll(GameListView.fromGameList(gameList));
				}
				
				@Override
				public void receiveException(GenesisServerException exception) {
					LOGGER.error("error while loading games", exception);
				}
			});
		}
	}
	
	private void listPlayersOnline() {
		UserManager.getInstance().addUserStateListener(this);
	}
	
	private void createGame() {
		// TODO Auto-generated method stub
	}
	
	private void loadGame() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Send the content of the chat textField to all players of this game using the NotifierService
	 */
	private void sendMessage() {
		String message = textFieldChat.getText();
		if (message != null && !message.trim().isEmpty()) {
			String username = UserManager.getInstance().getLocalUsername();
			String completeMessage = NOTIFIER_PREFIX + username + "/" + message;
			LOGGER.debug("sending chat message: \"{}\"", completeMessage);
			notifier.informAllPlayers(completeMessage);
			//the message is not added to the text area here because this player also receives the message
		}
	}
	
	@Override
	public void receiveNotificationMessage(String notificationMessage) {
		if (notificationMessage.startsWith(NOTIFIER_PREFIX)) {
			String[] split = notificationMessage.split("/");
			if (split.length >= 3) {
				String username = split[1];
				String message = split[2];
				//concatenate the message if / were used in the message
				for (int i = 3; i < split.length; i++) {
					message += "/" + split[i];
				}
				
				//only add the message if it comes from this game chat
				textAreaChat.appendText(username + ": " + message);
			}
		}
	}
	
	@Override
	public void receiveUserList(ArrayList<String> usersOnline) {
		//find the new indices of the currently selected players
		List<String> selected = listPlayersOnline.getSelectionModel().getSelectedItems();
		List<Integer> indices = new ArrayList<Integer>();
		for (String s : selected) {
			indices.add(usersOnline.indexOf(s));
		}
		int[] indicesAsArray = new int[indices.size()];
		for (int i = 0; i < indices.size(); i++) {
			indicesAsArray[i] = indices.get(i);
		}
		
		//update the list
		playersOnline.clear();
		playersOnline.addAll(usersOnline);
		listPlayersOnline.getSelectionModel().selectIndices(-1, indicesAsArray);
	}
}
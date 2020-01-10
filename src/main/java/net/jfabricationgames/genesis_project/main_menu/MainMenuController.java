package net.jfabricationgames.genesis_project.main_menu;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import net.jfabricationgames.genesis_project.connection.AbstractGenesisClientEventSubscriber;
import net.jfabricationgames.genesis_project.connection.GenesisClient;
import net.jfabricationgames.genesis_project.connection.exception.GenesisServerException;
import net.jfabricationgames.genesis_project.connection.exception.InvalidRequestException;
import net.jfabricationgames.genesis_project.connection.exception.ServerCommunicationException;
import net.jfabricationgames.genesis_project.connection.notifier.NotificationMessageListener;
import net.jfabricationgames.genesis_project.connection.notifier.NotifierService;
import net.jfabricationgames.genesis_project.game.Constants;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game_frame.GameFrameController;
import net.jfabricationgames.genesis_project.game_frame.pre_game.PreGameSelectionController;
import net.jfabricationgames.genesis_project.game_frame.util.DialogUtils;
import net.jfabricationgames.genesis_project.manager.GameManager;
import net.jfabricationgames.genesis_project.user.UserManager;
import net.jfabricationgames.genesis_project.user.UserStateListener;
import net.jfabricationgames.genesis_project_server.game.GameList;

public class MainMenuController implements Initializable, NotificationMessageListener, UserStateListener {
	
	private static final Logger LOGGER = LogManager.getLogger(MainMenuController.class);
	
	public static final String NOTIFIER_PREFIX = "main_menu/";
	public static final String NOTIFIER_PREFIX_GLOBAL_CHAT = "global_chat/";
	public static final String NOTIFIER_PREFIX_GAME_INVITATION = "game_invitation/";
	public static final String NOTIFIER_PREFIX_GAME_INVITATION_ANSWER = "invitation_answer/";
	public static final String NOTIFIER_PREFIX_GAME_STARTED = "game_started/";
	
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
	
	private int invitationAnswers;
	private int invitedPlayers;
	private List<String> playersAsked;
	
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
			disableAll();
			
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
		
		//lists
		listPlayersOnline.setItems(playersOnline);
		listGames.setItems(games);
		
		//load the constants from the server
		loadConstants();
		
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
	
	/**
	 * Load the constant values from the server
	 */
	private void loadConstants() {
		if (genesisClient != null) {
			genesisClient.getConfigAsync("constants", new AbstractGenesisClientEventSubscriber() {
				
				@Override
				public void receiveGetConfigAnswer(String config) {
					Platform.runLater(() -> {
						LOGGER.debug("received constants");
						ObjectMapper mapper = new ObjectMapper();
						try {
							//"manually" parse JSON to Object
							Constants constants = mapper.readValue(config, Constants.class);
							Constants.setConstants(constants);
						}
						catch (IOException ioe) {
							LOGGER.error("constants configuration couldn't be parsed", ioe);
							LOGGER.error("constants configuration couldn't be parsed. Loaded constants were:\n{}", config);
							DialogUtils.showExceptionDialog("Fehler bei der Serververbindung",
									"Konfigurationsdaten (Spielkonstanten) konnten nicht geladen werden", ioe, false);
						}
					});
				}
				
				@Override
				public void receiveException(GenesisServerException exception) {
					Platform.runLater(() -> {
						LOGGER.error("dynamic content couldn't be loaded", exception);
						DialogUtils.showExceptionDialog("Fehler bei der Serververbindung",
								"Konfigurationsdaten (Spielkonstanten) konnten nicht geladen werden", exception, false);
					});
				}
			});
		}
	}
	
	/**
	 * Load some dynamic content from the server
	 */
	private void loadNews() {
		if (genesisClient != null) {
			genesisClient.getConfigAsync("main_menu_dynamic_content", new AbstractGenesisClientEventSubscriber() {
				
				@Override
				public void receiveGetConfigAnswer(String config) {
					Platform.runLater(() -> {
						LOGGER.debug("received dynamic content");
						textAreaNews.setText(config);
					});
				}
				
				@Override
				public void receiveException(GenesisServerException exception) {
					LOGGER.error("dynamic content couldn't be loaded", exception);
				}
			});
		}
	}
	
	/**
	 * List all games of this user in the ListView
	 */
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
	
	/**
	 * List all players that are online by subscribing to the UserManager and reacting to changes
	 */
	private void listPlayersOnline() {
		UserManager.getInstance().addUserStateListener(this);
		//add the users to not wait for the first change
		receiveUserList(new ArrayList<String>(UserManager.getInstance().getUsersOnline()));
	}
	
	/**
	 * Create a new game by asking some selected players to participate and afterwards creating a game
	 */
	private void createGame() {
		List<String> players = new ArrayList<String>(listPlayersOnline.getSelectionModel().getSelectedItems());
		if (players.isEmpty()) {
			DialogUtils.showInfoDialog("Keine Mitspieler Ausgewählt", "Es wurden keine Mitspieler ausgewählt",
					"Wähle deine Mitspieler aus der Liste um ein Spiel zu starten");
			return;
		}
		
		//add the local player to the list of players (if not already added)
		String localPlayer = UserManager.getInstance().getLocalUsername();
		if (!players.contains(localPlayer)) {
			players.add(localPlayer);
		}
		
		if (players.size() > Constants.getInstance().MAX_PLAYERS) {
			DialogUtils.showInfoDialog("Zu viele Spieler Ausgewählt", "Es können nur maximal 6 Spieler an einem Spiel teilnehmen", "");
			return;
		}
		
		//remove the local player from the list of players that get asked to participate in the game
		List<String> informedPlayers = new ArrayList<String>(players);
		informedPlayers.remove(localPlayer);
		
		//list to string to send it using the notifier
		StringBuilder sb = new StringBuilder();
		for (String player : players) {
			sb.append(player);
			sb.append(';');
		}
		sb.setLength(sb.length() - 1);//remove last ';'
		String playerListAsString = sb.toString();
		
		//keep track of how many answers were received
		invitedPlayers = informedPlayers.size();
		invitationAnswers = 0;
		playersAsked = players;
		
		//start the creation of the game by informing all other players
		try {
			notifier.informPlayers(NOTIFIER_PREFIX + NOTIFIER_PREFIX_GAME_INVITATION + localPlayer + "/" + playerListAsString, informedPlayers);
		}
		catch (ServerCommunicationException sce) {
			LOGGER.error("couldn't send notification to players", sce);
			DialogUtils.showExceptionDialog("Serververbindungs Fehler", "Server kann nicht erreicht werden", sce, false);
		}
		
		//wait for other players answers
		disableAll();
		DialogUtils.showInfoDialog("Warte auf Antwort", "Warte auf Antwort der anderen Spieler", "");
		
		//rest is done in receiveNotificationMessage(NotificationMessage)
	}
	
	/**
	 * Load a game from the database and start the GameFrame
	 */
	private void loadGame() {
		GameListView selectedGame = listGames.getSelectionModel().getSelectedItem();
		if (selectedGame == null) {
			DialogUtils.showInfoDialog("Kein Spiel Ausgewählt", "Es wurde kein Spiel ausgewählt", "Wähle ein Spiel aus der Liste um es zu laden");
			return;
		}
		
		int selectedId = selectedGame.getId();
		genesisClient.getGameAsync(selectedId, new AbstractGenesisClientEventSubscriber() {
			
			@Override
			public void receiveGetGameAnswer(Game game) {
				Platform.runLater(() -> {
					//add the game to the manager and start the game frame
					GameManager.getInstance().addGame(selectedId, game);
					startGameFrame(selectedId);
				});
			}
			
			@Override
			public void receiveException(GenesisServerException exception) {
				Platform.runLater(() -> {
					if (exception instanceof InvalidRequestException) {
						LOGGER.error("game id was not found", exception);
						DialogUtils.showErrorDialog("Fehler", "Spiel konnte nicht geladen werden", "Das Spiel wurde nicht in der Datenbank gefunden",
								true);
					}
					else {
						LOGGER.error("unknown error whil loading the game", exception);
						DialogUtils.showExceptionDialog("Fehler", "Spiel konnte nicht geladen werden - Ein Unbekannter Fehler ist aufgetreten",
								exception, true);
					}
					enableAll();
				});
			}
		});
		disableAll();
	}
	
	/**
	 * Start a new game after all players have agreed to participate
	 */
	private void startGame() {
		//request an ID for a new game from the server
		genesisClient.createGameAsync(playersAsked, new AbstractGenesisClientEventSubscriber() {
			
			@Override
			public void receiveCreateGameAnswer(int gameId) {
				Platform.runLater(() -> {
					//create the  players and the game
					List<Player> players = playersAsked.stream().map(p -> new Player(p)).collect(Collectors.toList());
					Game game = new Game(gameId, players, UserManager.getInstance().getLocalUsername());
					//add the game to the manager
					GameManager.getInstance().addGame(gameId, game);
					
					//start the pre-game selections
					startPreGameFrame(gameId);
					
					//inform all other players about the started game
					List<String> playersInvited = new ArrayList<String>(playersAsked);
					playersInvited.remove(UserManager.getInstance().getLocalUsername());
					try {
						notifier.informPlayers(NOTIFIER_PREFIX + NOTIFIER_PREFIX_GAME_STARTED + gameId, playersInvited);
					}
					catch (ServerCommunicationException sce) {
						LOGGER.error("couldn't send notification to players", sce);
						DialogUtils.showExceptionDialog("Serververbindungs Fehler", "Server kann nicht erreicht werden", sce, false);
					}
				});
			}
			
			@Override
			public void receiveException(GenesisServerException exception) {
				Platform.runLater(() -> {
					if (exception instanceof InvalidRequestException) {
						LOGGER.error("at least one player was not found in the database", exception);
					}
					else {
						LOGGER.error("an unknown error occured while trying to start the game", exception);
					}
					DialogUtils.showExceptionDialog("Fehler beim Erstellen des Spiels", "Beim Erstellen des Spiels ist ein Fehler aufgetreten",
							exception, true);
				});
			}
		});
	}
	
	private void downloadGame(int gameId) {
		genesisClient.getGameAsync(gameId, new AbstractGenesisClientEventSubscriber() {
			
			@Override
			public void receiveGetGameAnswer(Game game) {
				//add the game to the manager and start the game frame
				GameManager.getInstance().addGame(gameId, game);
			}
			
			@Override
			public void receiveException(GenesisServerException exception) {
				Platform.runLater(() -> {
					if (exception instanceof InvalidRequestException) {
						LOGGER.error("game id was not found", exception);
						DialogUtils.showErrorDialog("Fehler", "Spiel konnte nicht geladen werden", "Das Spiel wurde nicht in der Datenbank gefunden",
								true);
					}
					else {
						LOGGER.error("unknown error whil loading the game", exception);
						DialogUtils.showExceptionDialog("Fehler", "Spiel konnte nicht geladen werden - Ein Unbekannter Fehler ist aufgetreten",
								exception, true);
					}
				});
			}
		});
	}
	
	/**
	 * Start the game frame after a game was loaded
	 */
	private void startGameFrame(int gameId) {
		try {
			URL fxmlUrl = getClass().getResource("/net/jfabricationgames/genesis_project/game_frame/GameFrame.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
			fxmlLoader.setController(new GameFrameController(gameId));
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setTitle("Genesis Project");
			stage.setScene(scene);
			stage.show();
			
			((Stage) buttonLoadGame.getScene().getWindow()).close();
		}
		catch (Exception e) {
			LOGGER.error("GameFrame couldn't be loaded", e);
			DialogUtils.showExceptionDialog("Fehler", "Der GameFrame konnte nicht geladen werden", e, true);
		}
	}
	
	/**
	 * Start a pre-game frame (in which the first selections are made) after all players agreed to participate
	 */
	private void startPreGameFrame(int gameId) {
		try {
			URL fxmlUrl = getClass().getResource("/net/jfabricationgames/genesis_project/game_frame/pre_game/PreGameSelectionFrame.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
			fxmlLoader.setController(new PreGameSelectionController(gameId));
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setTitle("Genesis Project");
			stage.setScene(scene);
			stage.show();
			
			((Stage) buttonLoadGame.getScene().getWindow()).close();
		}
		catch (Exception e) {
			LOGGER.error("GameFrame couldn't be loaded", e);
			DialogUtils.showExceptionDialog("Fehler", "Der (Pre-)GameFrame konnte nicht geladen werden", e, true);
		}
	}
	
	/**
	 * Disable all functions (e.g. after a game is already been loading)
	 */
	private void disableAll() {
		textFieldChat.setEditable(false);
		buttonSend.setDisable(true);
		buttonCreateGame.setDisable(true);
		buttonLoadGame.setDisable(true);
	}
	
	/**
	 * Enable all functions
	 */
	private void enableAll() {
		textFieldChat.setEditable(true);
		buttonSend.setDisable(false);
		buttonCreateGame.setDisable(false);
		buttonLoadGame.setDisable(false);
	}
	
	/**
	 * Send the content of the chat textField to all players of this game using the NotifierService
	 */
	private void sendMessage() {
		String message = textFieldChat.getText();
		if (message != null && !message.trim().isEmpty()) {
			String username = UserManager.getInstance().getLocalUsername();
			String completeMessage = NOTIFIER_PREFIX + NOTIFIER_PREFIX_GLOBAL_CHAT + username + "/" + message;
			LOGGER.debug("sending chat message: \"{}\"", completeMessage);
			try {
				notifier.informAllPlayers(completeMessage);
			}
			catch (ServerCommunicationException sce) {
				LOGGER.error("couldn't send notification to players", sce);
				DialogUtils.showExceptionDialog("Serververbindungs Fehler", "Server kann nicht erreicht werden", sce, false);
			}
			//the message is not added to the text area here because this player also receives the message
		}
		textFieldChat.setText("");
	}
	
	@Override
	public void receiveNotificationMessage(String notificationMessage) {
		if (notificationMessage.startsWith(NOTIFIER_PREFIX)) {
			String[] split = notificationMessage.split("/");
			if (split.length >= 2) {
				switch (split[1] + "/") {
					case NOTIFIER_PREFIX_GLOBAL_CHAT:
						if (split.length >= 4) {
							String username = split[2];
							String message = split[3];
							//concatenate the message if / were used in the message
							for (int i = 4; i < split.length; i++) {
								message += "/" + split[i];
							}
							
							//only add the message if it comes from this game chat
							textAreaChat.appendText(username + ": " + message + "\n");
						}
						else {
							LOGGER.error("Received global chat message with not enough content (split.length: {} message: {})", split.length,
									notificationMessage);
						}
						break;
					case NOTIFIER_PREFIX_GAME_INVITATION:
						if (split.length >= 4) {
							String invitingPlayer = split[2];
							List<String> participatingPlayers = Arrays.asList(split[3].split(";"));
							showGameInvitationDialog(invitingPlayer, participatingPlayers);
						}
						else {
							LOGGER.error("Received game invitation message with not enough content (split.length: {} message: {})", split.length,
									notificationMessage);
						}
						break;
					case NOTIFIER_PREFIX_GAME_INVITATION_ANSWER:
						if (split.length >= 4) {
							String player = split[3];
							boolean participating = Boolean.parseBoolean(split[4]);
							receiveInvitationAnswer(player, participating);
						}
						else {
							LOGGER.error("Received invitation answer message with not enough content (split.length: {} message: {})", split.length,
									notificationMessage);
						}
					case NOTIFIER_PREFIX_GAME_STARTED:
						if (split.length >= 3) {
							try {
								int gameId = Integer.parseInt(split[2]);
								downloadGame(gameId);
								startPreGameFrame(gameId);
							}
							catch (NumberFormatException nfe) {
								LOGGER.error("Received game started message with a not parsable game id (message: {})", notificationMessage);
							}
						}
						else {
							LOGGER.error("Received game started message with not enough content (split.length: {} message: {})", split.length,
									notificationMessage);
						}
						break;
					default:
						LOGGER.error("received an unknown notification message: {}", notificationMessage);
						break;
				}
			}
			else {
				//less than 2 elements in split array
				LOGGER.error("Received a notification message with not enough content (split.length: {}, message: {})", split.length,
						notificationMessage);
			}
		}
	}
	
	/**
	 * Handle an answer to a game invitation
	 */
	private void receiveInvitationAnswer(String player, boolean participating) {
		if (participating) {
			if (invitationAnswers >= 0) {//-1 means aborted
				invitationAnswers++;
				if (invitationAnswers >= invitedPlayers) {
					//all players accepted -> start the game
					startGame();
				}
			}
		}
		else {
			//abort the game creation
			invitationAnswers = -1;
			DialogUtils.showInfoDialog("Spiel Erstellung Abgebrochen", player + " hat die Einladung abgelehnt",
					"Das Spiel kann nicht gestartet werden.");
			enableAll();
		}
	}
	
	private void showGameInvitationDialog(String invitingPlayer, List<String> participatingPlayers) {
		Optional<ButtonType> result = DialogUtils.showConfirmationDialog("Einladung zum Spiel",
				"Spieleinladung von: " + invitingPlayer + "\nSpieler: " + participatingPlayers.stream().collect(Collectors.joining(", ")),
				"Einladung annehmen?");
		if (result.isPresent() && result.get() == ButtonType.OK) {
			// ... user chose OK -> inform the inviting player
			try {
				notifier.informPlayers(
						NOTIFIER_PREFIX + NOTIFIER_PREFIX_GAME_INVITATION_ANSWER + UserManager.getInstance().getLocalUsername() + "/true",
						invitingPlayer);
			}
			catch (ServerCommunicationException sce) {
				LOGGER.error("couldn't send notification to players", sce);
				DialogUtils.showExceptionDialog("Serververbindungs Fehler", "Server kann nicht erreicht werden", sce, false);
			}
			disableAll();
			DialogUtils.showInfoDialog("Warte auf Mitspieler", "Warte auf Antwort der anderen Spieler", "");
		}
		else {
			// ... user chose CANCEL or closed the dialog -> inform all players
			try {
				notifier.informPlayers(
						NOTIFIER_PREFIX + NOTIFIER_PREFIX_GAME_INVITATION_ANSWER + UserManager.getInstance().getLocalUsername() + "/false",
						participatingPlayers);
			}
			catch (ServerCommunicationException sce) {
				LOGGER.error("couldn't send notification to players", sce);
				DialogUtils.showExceptionDialog("Serververbindungs Fehler", "Server kann nicht erreicht werden", sce, false);
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
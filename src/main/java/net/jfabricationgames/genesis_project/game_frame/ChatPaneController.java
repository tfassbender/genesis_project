package net.jfabricationgames.genesis_project.game_frame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatPaneController implements Initializable {
	
	@FXML
	private TextField textFieldChat;
	@FXML
	private Button buttonChatSend;
	@FXML
	private TextArea textAreaChat;
	
	@SuppressWarnings("unused")
	private int gameId;
	
	public ChatPaneController(int gameId) {
		this.gameId = gameId;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateAll() {
		//nothing to do here *flies away*
	}
}
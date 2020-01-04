package net.jfabricationgames.genesis_project.main_menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.jfabricationgames.genesis_project_server.game.GameList;

public class GameListView {
	
	private int id;
	@SuppressWarnings("unused")
	private LocalDate started;
	private LocalDate lastPlayed;
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
	
	public GameListView(int id, LocalDate started, LocalDate lastPlayed) {
		this.id = id;
		this.started = started;
		this.lastPlayed = lastPlayed;
	}
	
	public static List<GameListView> fromGameList(GameList gameList) {
		List<GameListView> games = new ArrayList<>();
		Set<Integer> ids = gameList.getGames().keySet();
		for (int id : ids) {
			games.add(new GameListView(id, gameList.getStarted().get(id), gameList.getLastPlayed().get(id)));
		}
		return games;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return lastPlayed.format(formatter);
	}
}
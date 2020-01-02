package net.jfabricationgames.genesis_project.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.PlayerScore;

public class GamePointManager implements IGamePointManager {
	
	private ObservableList<PlayerScore> scoreList;
	
	/**
	 * DO NOT USE - empty constructor for json deserialization
	 */
	@Deprecated
	public GamePointManager() {
		
	}
	
	public GamePointManager(Game game) {
		List<PlayerScore> scores = game.getPlayers().stream().map(p -> new PlayerScore(p, 0)).collect(Collectors.toList());
		scoreList = FXCollections.observableArrayList(scores);
	}
	
	@Override
	public void setScore(Player player, int newScore) {
		Optional<PlayerScore> score = scoreList.stream().filter(p -> p.getPlayer().equals(player)).findFirst();
		score.ifPresent(s -> s.setScore(newScore));
		Collections.sort(scoreList);
	}
	@Override
	public ObservableList<PlayerScore> getScoreList() {
		return scoreList;
	}
	
	@Override
	public int getPosition(Player player) {
		int position = -1;
		for (int i = 0; i < scoreList.size(); i++) {
			if (scoreList.get(i).getPlayer().equals(player)) {
				position = i + 1;
			}
		}
		return position;
	}
	
	@Override
	@JsonGetter("scoreList")
	public List<PlayerScore> getScoreListAsArrayList() {
		return new ArrayList<PlayerScore>(scoreList);
	}
	@Override
	@JsonSetter("scoreList")
	public void setScoreListFromList(List<PlayerScore> scoreList) {
		this.scoreList = FXCollections.observableArrayList(scoreList);
	}
}
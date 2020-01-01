package net.jfabricationgames.genesis_project.manager;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javafx.collections.ObservableList;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.PlayerScore;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({@JsonSubTypes.Type(value = GamePointManager.class, name = "GamePointManager")})
public interface IGamePointManager {
	
	public int getPosition(Player player);
	
	public void setScore(Player player, int newScore);
	
	public ObservableList<PlayerScore> getScoreList();
	public List<PlayerScore> getScoreListAsArrayList();
	
	public void setScoreListFromList(List<PlayerScore> scoreList);
}
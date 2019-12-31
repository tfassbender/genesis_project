package net.jfabricationgames.genesis_project.manager;

import java.util.List;

import javafx.collections.ObservableList;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.PlayerScore;

public interface IGamePointManager {
	
	public int getPosition(Player player);
	
	public void setScore(Player player, int newScore);
	
	public ObservableList<PlayerScore> getScoreList();
	public List<PlayerScore> getScoreListAsArrayList();
	
	public void setScoreListFromList(List<PlayerScore> scoreList);
}
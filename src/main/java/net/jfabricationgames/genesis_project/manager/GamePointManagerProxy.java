package net.jfabricationgames.genesis_project.manager;

import java.util.List;

import javafx.collections.ObservableList;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.PlayerScore;

public class GamePointManagerProxy implements IGamePointManager {
	
	private IGamePointManager manager;
	
	public GamePointManagerProxy(IGamePointManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void setScore(Player player, int newScore) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
	
	@Override
	public ObservableList<PlayerScore> getScoreList() {
		return manager.getScoreList();
	}
	
	@Override
	public int getPosition(Player player) {
		return manager.getPosition(player);
	}
	
	@Override
	public List<PlayerScore> getScoreListAsArrayList() {
		return manager.getScoreListAsArrayList();
	}
	
	@Override
	public void setScoreListFromList(List<PlayerScore> scoreList) {
		throw new UnsupportedOperationException("This method is forbidden in this proxy implementation");
	}
}
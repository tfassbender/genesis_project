package net.jfabricationgames.genesis_project.game;

public class PlayerScore implements Comparable<PlayerScore> {
	
	private Player player;
	private int score;
	
	public PlayerScore(Player player, int score) {
		this.player = player;
		this.score = score;
	}
	
	@Override
	public int compareTo(PlayerScore other) {
		return Integer.compare(score, other.getScore());
	}
	
	@Override
	public String toString() {
		return String.format("%3d - %s", score, player.getUser().getUsername());
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int newScore) {
		this.score = newScore;
	}
}
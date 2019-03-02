package net.jfabricationgames.genesis_project.game;

public class AttackBuilder {
	
	private Game game;
	private Field targetField;
	private Enemy enemy;
	private int strength;
	private AttackTarget attackTarget;
	private int[] attackPenalty;
	private int[] penaltyOffsets;
	
	public Attack build() {
		return new Attack(game, targetField, enemy, strength, attackTarget, attackPenalty, penaltyOffsets);
	}
	
	public Game getGame() {
		return game;
	}
	public AttackBuilder setGame(Game game) {
		this.game = game;
		return this;
	}
	
	public Field getTargetField() {
		return targetField;
	}
	public AttackBuilder setTargetField(Field targetField) {
		this.targetField = targetField;
		return this;
	}
	
	public Enemy getEnemy() {
		return enemy;
	}
	public AttackBuilder setEnemy(Enemy enemy) {
		this.enemy = enemy;
		return this;
	}
	
	public int getStrength() {
		return strength;
	}
	public AttackBuilder setStrength(int strength) {
		this.strength = strength;
		return this;
	}
	
	public AttackTarget getAttackTarget() {
		return attackTarget;
	}
	public AttackBuilder setAttackTarget(AttackTarget attackTarget) {
		this.attackTarget = attackTarget;
		return this;
	}
	
	public int[] getAttackPenalty() {
		return attackPenalty;
	}
	public AttackBuilder setAttackPenalty(int... attackPenalty) {
		this.attackPenalty = attackPenalty;
		return this;
	}
	
	public int[] getPenaltyOffsets() {
		return penaltyOffsets;
	}
	public AttackBuilder setPenaltyOffsets(int... penaltyOffsets) {
		this.penaltyOffsets = penaltyOffsets;
		return this;
	}
}
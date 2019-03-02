package net.jfabricationgames.genesis_project.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

public class Attack {
	
	private Game game;
	private Field targetField;
	private Enemy enemy;
	private int strength;
	
	private AttackTarget attackTarget;
	private int[] attackPenalty;//the penalties for all players on the planet depending on the attack offset
	private int[] penaltyOffsets;//the offsets on which penalties are applied
	
	public Attack(Game game, Field targetField, Enemy enemy, int strength, AttackTarget attackTarget, int[] attackPenalty, int[] penaltyOffsets) {
		if (attackPenalty.length != penaltyOffsets.length) {
			throw new IllegalArgumentException("attackPenalty and penaltyOffsets must have the same length");
		}
		if (penaltyOffsets[0] > penaltyOffsets[penaltyOffsets.length - 1]) {
			throw new IllegalArgumentException("penaltyOffsets must be sorted in ascending order");
		}
		if (penaltyOffsets[0] < 0) {
			throw new IllegalArgumentException("The lowest penalty must be greater or equal 0");
		}
		this.game = game;
		this.targetField = targetField;
		this.enemy = enemy;
		this.strength = strength;
		this.attackTarget = attackTarget;
		this.attackPenalty = attackPenalty;
		this.penaltyOffsets = penaltyOffsets;
	}
	
	public boolean defenseSuccessful() {
		return calculateDefenseStrength() >= strength;
	}
	
	public int calculateDefenseStrength() {
		return targetField.calculateDefense(game);
	}
	
	private int getAttackStrengthOffset() {
		return Math.max(0, strength - calculateDefenseStrength());
	}
	
	/**
	 * The players that had the most defense strength on the attack.
	 */
	public List<Player> getBestDefendingPlayers() {
		Map<Player, Integer> playerDefense = new HashMap<Player, Integer>();
		//initialize the map with all players
		for (Player player : game.getPlayers()) {
			playerDefense.put(player, Integer.valueOf(0));
		}
		
		Board board = game.getBoard();
		List<Field> defenseFields = board.getFields().values().stream().filter(field -> field.hasDefenseBuilding()).collect(Collectors.toList());
		
		//check every defense field if it's in range
		for (Field field : defenseFields) {
			//find a defense building (any because it can only be one)
			Optional<PlayerBuilding> defenseBuilding = field.getSpaceBuildings().stream()
					.filter(building -> building.getBuilding() == Building.DRONE || building.getBuilding() == Building.SPACE_STATION).findAny();
			
			if (defenseBuilding.isPresent()) {//should always be the case because we just iterate these fields...
				PlayerBuilding building = defenseBuilding.get();
				//get the range and defense of the building
				
				int range = 0;
				int defensePower = 0;
				if (building.getBuilding() == Building.DRONE) {
					range = building.getPlayer().getBuildingManager().getDroneFtl();
					defensePower = building.getPlayer().getBuildingManager().getDroneDefense();
				}
				else if (building.getBuilding() == Building.SPACE_STATION) {
					range = building.getPlayer().getBuildingManager().getSpaceStationFtl();
					defensePower = building.getPlayer().getBuildingManager().getSpaceStationDefense();
				}
				
				//check whether the field is in range
				if (range >= field.distanceTo(targetField)) {
					int defense = playerDefense.get(building.getPlayer()).intValue();
					defense += defensePower;
					playerDefense.put(building.getPlayer(), Integer.valueOf(defense));
				}
			}
		}
		
		//put the first three defending players into a list
		List<Entry<Player, Integer>> sortedDefendingPlayers = sortByValue(playerDefense);
		Collections.reverse(sortedDefendingPlayers);//reverse to get descending order
		List<Player> players = new ArrayList<Player>(3);
		for (int i = 0; i < sortedDefendingPlayers.size() && i < 3; i++) {
			players.add(sortedDefendingPlayers.get(i).getKey());
		}
		
		return players;
	}
	
	/**
	 * Create a list, sorted by value, from a Map.
	 */
	public static <K, V extends Comparable<? super V>> List<Entry<K, V>> sortByValue(Map<K, V> map) {
		List<Entry<K, V>> list = new ArrayList<Entry<K, V>>(map.entrySet());
		list.sort(Entry.comparingByValue());
		return list;
	}
	
	/**
	 * Calculate the penalty that is applied to all players (depending on the defense strength).
	 */
	public int calculatePenalty() {
		int strengthOffset = getAttackStrengthOffset();
		int penaltyIndex = -1;
		for (int i = 0; i < penaltyOffsets.length; i++) {
			//if the penalty offset is reached add one to the index
			if (strengthOffset >= penaltyOffsets[i]) {
				penaltyIndex++;
			}
		}
		if (penaltyIndex == -1) {
			return 0;
		}
		else {
			return attackPenalty[penaltyIndex];
		}
	}
	
	public Game getGame() {
		return game;
	}
	public Field getTarget() {
		return targetField;
	}
	public Enemy getEnemy() {
		return enemy;
	}
	public int getStrength() {
		return strength;
	}
	public AttackTarget getAttackTarget() {
		return attackTarget;
	}
}
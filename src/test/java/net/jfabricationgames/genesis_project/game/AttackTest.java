package net.jfabricationgames.genesis_project.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.jfabricationgames.genesis_project.game.Board.Position;

class AttackTest {
	
	@Test
	public void testDefenseSuccessful() {
		Game game = FieldTest.getInitializedGameWithDefenseBuildings();
		
		Field target = game.getBoard().getField(2, 3);
		Field target2 = game.getBoard().getField(3, 3);
		Attack attack1 = new Attack(game, target, Enemy.PARASITE, 10, AttackTarget.RESEARCH, new int[] {0}, new int[] {0});
		Attack attack2 = new Attack(game, target, Enemy.PARASITE, 15, AttackTarget.RESEARCH, new int[] {0}, new int[] {0});
		Attack attack3 = new Attack(game, target2, Enemy.PARASITE, 10, AttackTarget.RESEARCH, new int[] {0}, new int[] {0});
		
		assertEquals(12, target.calculateDefense(game.getBoard(), game.getResearchManager()));
		assertEquals(5, target2.calculateDefense(game.getBoard(), game.getResearchManager()));
		assertTrue(attack1.defenseSuccessful());
		assertFalse(attack2.defenseSuccessful());
		assertFalse(attack3.defenseSuccessful());
	}
	
	@Test
	public void testCalculateDefenseStrength() {
		Game game = FieldTest.getInitializedGameWithDefenseBuildings();
		
		Field target = game.getBoard().getFields().get(new Position(2, 3));
		Attack attack1 = new Attack(game, target, Enemy.PARASITE, 0, AttackTarget.RESEARCH, new int[] {0}, new int[] {0});
		
		assertEquals(12, target.calculateDefense(game.getBoard(), game.getResearchManager()));
		assertEquals(12, attack1.calculateDefenseStrength());
	}
	
	@Test
	public void testGetBestDefendingPlayers() {
		Game game = FieldTest.getInitializedGameWithDefenseBuildings();
		
		Field target = game.getBoard().getFields().get(new Position(2, 3));
		Attack attack1 = new Attack(game, target, Enemy.PARASITE, 0, AttackTarget.RESEARCH, new int[] {0}, new int[] {0});
		
		List<Player> bestDefendingPlayers = attack1.getBestDefendingPlayers();
		
		assertEquals(2, bestDefendingPlayers.size());
		assertEquals(game.getLocalPlayer(), bestDefendingPlayers.get(0));
	}
	
	@Test
	public void testCalculatePenalty() {
		Game game = FieldTest.getInitializedGameWithDefenseBuildings();
		
		Field target = game.getBoard().getField(2, 4);
		int[] attackPenalty = new int[] {1, 2, 3, 4};
		int[] penaltyOffsets = new int[] {1, 6, 7, 8};
		Attack attack1 = new Attack(game, target, Enemy.PARASITE, 5, AttackTarget.RESEARCH, attackPenalty, penaltyOffsets);//offset: -5
		Attack attack2 = new Attack(game, target, Enemy.PARASITE, 10, AttackTarget.RESEARCH, attackPenalty, penaltyOffsets);//offset: 0
		Attack attack3 = new Attack(game, target, Enemy.PARASITE, 15, AttackTarget.RESEARCH, attackPenalty, penaltyOffsets);//offset: 5
		Attack attack4 = new Attack(game, target, Enemy.PARASITE, 17, AttackTarget.RESEARCH, attackPenalty, penaltyOffsets);//offset: 7
		
		assertEquals(10, target.calculateDefense(game.getBoard(), game.getResearchManager()));
		assertEquals(0, attack1.calculatePenalty());
		assertEquals(0, attack2.calculatePenalty());
		assertEquals(attackPenalty[0], attack3.calculatePenalty());
		assertEquals(attackPenalty[2], attack4.calculatePenalty());
	}
}
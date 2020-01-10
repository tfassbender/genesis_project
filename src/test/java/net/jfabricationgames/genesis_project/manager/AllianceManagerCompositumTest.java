package net.jfabricationgames.genesis_project.manager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import net.jfabricationgames.genesis_project.game.AllianceBonus;
import net.jfabricationgames.genesis_project.game.Game;

class AllianceManagerCompositumTest {
	
	@Test
	public void testIsAllianceBonusTaken() {
		Game game = mock(Game.class);
		IAllianceManager allianceManager = new AllianceManagerCompositum(game);
		
		assertFalse(allianceManager.isAllianceBonusTaken(AllianceBonus.MILITARY_RANGE, 0));
		assertFalse(allianceManager.isAllianceBonusTaken(AllianceBonus.MILITARY_RANGE, 1));
	}
	
	@Test
	public void testSetAllianceBonusTaken() {
		Game game = mock(Game.class);
		IAllianceManager allianceManager = new AllianceManagerCompositum(game);
		
		allianceManager.setAllianceBonusTaken(AllianceBonus.MILITARY_RANGE, 0, true);
		allianceManager.setAllianceBonusTaken(AllianceBonus.PRIMARY_RESOURCES, 1, true);
		
		assertTrue(allianceManager.isAllianceBonusTaken(AllianceBonus.MILITARY_RANGE, 0));
		assertTrue(allianceManager.isAllianceBonusTaken(AllianceBonus.PRIMARY_RESOURCES, 1));
		
		assertFalse(allianceManager.isAllianceBonusTaken(AllianceBonus.MILITARY_RANGE, 1));
		assertFalse(allianceManager.isAllianceBonusTaken(AllianceBonus.PRIMARY_RESOURCES, 0));
		assertFalse(allianceManager.isAllianceBonusTaken(AllianceBonus.POINTS, 1));
		assertFalse(allianceManager.isAllianceBonusTaken(AllianceBonus.POINTS, 0));
	}
	
	@Test
	public void testIsAllianceBonusTaken_anyBonus() {
		Game game = mock(Game.class);
		IAllianceManager allianceManager = new AllianceManagerCompositum(game);
		
		allianceManager.setAllianceBonusTaken(AllianceBonus.ANY, 0, true);
		
		//the ANY bonus can't be taken away
		assertFalse(allianceManager.isAllianceBonusTaken(AllianceBonus.ANY, 0));
	}
	
	@Test
	public void testIsAllianceBonusTaken_invalidInput() {
		Game game = mock(Game.class);
		IAllianceManager allianceManager = new AllianceManagerCompositum(game);
		
		assertThrows(NullPointerException.class, () -> allianceManager.isAllianceBonusTaken(null, 0));
		assertThrows(IllegalArgumentException.class, () -> allianceManager.isAllianceBonusTaken(AllianceBonus.MILITARY_RANGE, 5));
	}
}
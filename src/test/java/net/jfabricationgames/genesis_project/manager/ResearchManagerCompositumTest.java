package net.jfabricationgames.genesis_project.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.ResearchArea;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Resource;
import net.jfabricationgames.genesis_project.testUtils.ConstantsInitializerUtil;

class ResearchManagerCompositumTest {
	
	private ResearchManagerCompositum getResearchManager() {
		ConstantsInitializerUtil.initStartingResearchStates();
		ConstantsInitializerUtil.initResearchResources();
		//add 4 mocked players to the game
		List<Player> players = new ArrayList<Player>(4);
		for (int i = 0; i < 4; i++) {
			Player player = mock(Player.class);
			IResearchManager researchManager = mock(ResearchManager.class);
			when(player.getResearchManager()).thenReturn(researchManager);
			
			players.add(player);
		}
		Game game = mock(Game.class);
		when(game.getPlayers()).thenReturn(players);
		ResearchManagerCompositum manager = new ResearchManagerCompositum(game);
		return manager;
	}
	
	@Test
	public void testIncreaseResearchState() {
		ResearchManagerCompositum manager = getResearchManager();
		
		manager.increaseState(ResearchArea.WEAPON);
		
		assertEquals(1, manager.getState(ResearchArea.WEAPON));
		for (Player player : manager.getGame().getPlayers()) {
			verify(player.getResearchManager(), times(1)).increaseState(ResearchArea.WEAPON);
		}
	}
	
	@Test
	public void testIncreaseResearchStateLimits() {
		ResearchManagerCompositum manager = getResearchManager();
		
		for (int i = 0; i < 10; i++) {
			manager.increaseState(ResearchArea.WEAPON);
		}
		
		assertEquals(10, manager.getState(ResearchArea.WEAPON));
		assertThrows(IllegalStateException.class, () -> manager.increaseState(ResearchArea.WEAPON));
		assertThrows(IllegalArgumentException.class, () -> manager.increaseState(ResearchArea.ECONOMY));
		assertThrows(IllegalArgumentException.class, () -> manager.increaseState(ResearchArea.FTL));
		assertThrows(IllegalArgumentException.class, () -> manager.increaseState(ResearchArea.MINES));
		assertThrows(IllegalArgumentException.class, () -> manager.increaseState(ResearchArea.MILITARY));
		assertThrows(IllegalArgumentException.class, () -> manager.increaseState(ResearchArea.RESEARCH));
	}
	
	@Test
	public void testGetResearchResourcesNeededTotal() {
		//test only some preset states (set in ConstantsInitializerUtil.initResearchResources()) 
		//assuming there are 4 players in this game (see getResearchManager())
		ResearchManagerCompositum manager = getResearchManager();
		
		assertEquals(new ResearchResources(5, 5, 5, 1), manager.getResearchResourcesNeededTotal(ResearchArea.WEAPON, 7));
		assertEquals(new ResearchResources(14, 14, 14, 8), manager.getResearchResourcesNeededTotal(ResearchArea.WEAPON, 10));
	}
	
	@Test
	public void testAddResearchResources() {
		ResearchManagerCompositum manager = getResearchManager();
		
		//before adding
		assertEquals(new ResearchResources(3, 3, 3, 0), manager.getResearchResourcesNeededLeft(ResearchArea.WEAPON, 2));
		
		manager.addResearchResources(Resource.CARBON, 2, ResearchArea.WEAPON);
		
		assertEquals(new ResearchResources(1, 3, 3, 0), manager.getResearchResourcesNeededLeft(ResearchArea.WEAPON, 2));
		
		ResearchResources resourcesAdded = new ResearchResources(1, 2, 3, 0);
		manager.addResearchResources(resourcesAdded, ResearchArea.WEAPON);
		
		assertEquals(new ResearchResources(0, 1, 0, 0), manager.getResearchResourcesNeededLeft(ResearchArea.WEAPON, 2));
	}
	
	@Test
	public void testAddResearchResources_errorCases() {
		ResearchManagerCompositum manager = getResearchManager();
		
		//more resources than needed
		assertThrows(IllegalArgumentException.class, () -> manager.addResearchResources(Resource.CARBON, 3, ResearchArea.MINES));
		assertThrows(IllegalArgumentException.class, () -> manager.addResearchResources(Resource.CARBON, 5, ResearchArea.FTL));
		
		//negative resources
		ResearchResources resources = new ResearchResources(1, 1, -1, 0);
		assertThrows(IllegalArgumentException.class, () -> manager.addResearchResources(resources, ResearchArea.FTL));
		
		//empty resources
		ResearchResources resources2 = new ResearchResources();
		assertThrows(IllegalArgumentException.class, () -> manager.addResearchResources(resources2, ResearchArea.FTL));
		
		//no research resources
		assertThrows(IllegalArgumentException.class, () -> manager.addResearchResources(Resource.RESEARCH_POINTS, 4, ResearchArea.FTL));
	}
	
	@Test
	public void testIsStateAccessible() {
		ResearchManagerCompositum manager = getResearchManager();
		
		assertTrue(manager.isStateAccessible(ResearchArea.WEAPON, 1));
		assertFalse(manager.isStateAccessible(ResearchArea.WEAPON, 2));
		assertFalse(manager.isStateAccessible(ResearchArea.WEAPON, 3));
		assertFalse(manager.isStateAccessible(ResearchArea.WEAPON, 4));
	}
	
	@Test
	public void testIsStateAccessible_afterAddingAllResources() {
		ResearchManagerCompositum manager = getResearchManager();
		
		ResearchResources neededLeft = manager.getResearchResourcesNeededLeft(ResearchArea.WEAPON, 2);
		manager.addResearchResources(neededLeft, ResearchArea.WEAPON);
		
		assertTrue(manager.isStateAccessible(ResearchArea.WEAPON, 2));
		assertTrue(manager.isStateAccessible(ResearchArea.WEAPON, 3));
		assertFalse(manager.isStateAccessible(ResearchArea.WEAPON, 4));
	}
}

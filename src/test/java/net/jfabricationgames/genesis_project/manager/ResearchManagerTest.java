package net.jfabricationgames.genesis_project.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import net.jfabricationgames.genesis_project.game.Constants;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.PlayerClass;
import net.jfabricationgames.genesis_project.game.ResearchArea;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Resource;

class ResearchManagerTest {
	
	private ResearchManager getResearchManager() {
		Player player1 = mock(Player.class);
		when(player1.getPlayerClass()).thenReturn(PlayerClass.ENCOR);
		final int playersInGame = 4;
		
		ConstantsInitializerUtil.initStartingResearchStates();
		ConstantsInitializerUtil.initResearchResources();
		ResearchManager manager = new ResearchManager(player1, playersInGame);
		return manager;
	}
	
	@Test
	public void testResearchManager() {
		//test the construction of a research manager
		getResearchManager();
	}
	
	@Test
	public void testIncreaseResearchState() {
		ResearchManager manager = getResearchManager();
		
		for (int i = 0; i < 3; i++) {
			manager.increaseState(ResearchArea.MINES);
		}
		for (int i = 0; i < 5; i++) {
			manager.increaseState(ResearchArea.FTL);
		}
		for (int i = 0; i < 6; i++) {
			manager.increaseState(ResearchArea.ECONOMY);
		}
		for (int i = 0; i < 10; i++) {
			manager.increaseState(ResearchArea.WEAPON);
		}
		
		assertEquals(0, manager.getState(ResearchArea.MILITARY));
		assertEquals(3, manager.getState(ResearchArea.MINES));
		assertEquals(5, manager.getState(ResearchArea.FTL));
		assertEquals(6, manager.getState(ResearchArea.ECONOMY));
		assertEquals(10, manager.getState(ResearchArea.WEAPON));
		//increasing above the maximum state causes an exception
		assertThrows(IllegalStateException.class, () -> manager.increaseState(ResearchArea.ECONOMY));
		assertThrows(IllegalStateException.class, () -> manager.increaseState(ResearchArea.WEAPON));
	}
	
	@Test
	public void testGetResearchResourcesNeededTotal() {
		//test only some preset states (set in ConstantsInitializerUtil.initResearchResources()) 
		//assuming there are 4 players in this game (see getResearchManager())
		ResearchManager manager = getResearchManager();
		
		assertEquals(new ResearchResources(), manager.getResearchResourcesNeededTotal(ResearchArea.MINES, 2));
		assertEquals(new ResearchResources(), manager.getResearchResourcesNeededTotal(ResearchArea.FTL, 1));
		assertEquals(new ResearchResources(4, 4, 4, 0), manager.getResearchResourcesNeededTotal(ResearchArea.FTL, 2));
		assertEquals(new ResearchResources(8, 8, 8, 0), manager.getResearchResourcesNeededTotal(ResearchArea.FTL, 4));
		assertEquals(new ResearchResources(5, 5, 5, 1), manager.getResearchResourcesNeededTotal(ResearchArea.WEAPON, 7));
		assertEquals(new ResearchResources(14, 14, 14, 8), manager.getResearchResourcesNeededTotal(ResearchArea.WEAPON, 10));
	}
	
	@Test
	public void testAddResearchResources() {
		ResearchManager manager = getResearchManager();
		
		//before adding
		assertEquals(new ResearchResources(8, 8, 8, 0), manager.getResearchResourcesNeededLeft(ResearchArea.FTL, 4));
		
		manager.addResearchResources(Resource.CARBON, 3, ResearchArea.FTL);
		
		assertEquals(new ResearchResources(1, 4, 4, 0), manager.getResearchResourcesNeededLeft(ResearchArea.FTL, 2));
		
		ResearchResources resourcesAdded = new ResearchResources(1, 2, 3, 0);
		manager.addResearchResources(resourcesAdded, ResearchArea.FTL);
		
		assertEquals(new ResearchResources(0, 2, 1, 0), manager.getResearchResourcesNeededLeft(ResearchArea.FTL, 2));
	}
	
	@Test
	public void testAddResearchResources_errorCases() {
		ResearchManager manager = getResearchManager();
		
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
		ResearchManager manager = getResearchManager();
		
		//in the areas MINES (and ECONOMY) all states are accessible
		for (int i = 1; i < Constants.MAX_RESEARCH_STATE_DEFAULT; i++) {
			assertTrue(manager.isStateAccessible(ResearchArea.MINES, i));
		}
		
		//in the area FTL the states 2 and 4 are not directly accessible
		assertTrue(manager.isStateAccessible(ResearchArea.FTL, 1));
		assertFalse(manager.isStateAccessible(ResearchArea.FTL, 2));
		//none of the above is accessible because the previous state (state 2) is not accessible
		assertFalse(manager.isStateAccessible(ResearchArea.FTL, 3));
		assertFalse(manager.isStateAccessible(ResearchArea.FTL, 4));
		assertFalse(manager.isStateAccessible(ResearchArea.FTL, 5));
		assertFalse(manager.isStateAccessible(ResearchArea.FTL, 6));
	}
	
	@Test
	public void testIsStateAccessible_afterAddingAllResources() {
		ResearchManager manager = getResearchManager();
		
		ResearchResources neededLeft = manager.getResearchResourcesNeededLeft(ResearchArea.FTL, 2);
		manager.addResearchResources(neededLeft, ResearchArea.FTL);
		
		assertTrue(manager.isStateAccessible(ResearchArea.FTL, 2));
	}
	
	@Test
	public void testNoReferences() {
		//test that the returned ResearchResources are not returned by reference but by copy
		ResearchManager manager = getResearchManager();
		
		ResearchResources neededLeft = manager.getResearchResourcesNeededLeft(ResearchArea.FTL, 2);
		ResearchResources neededTotal = manager.getResearchResourcesNeededTotal(ResearchArea.FTL, 2);
		ResearchResources added = manager.getResearchResourcesAdded(ResearchArea.FTL, 2);
		
		ResearchResources originalLeft = neededLeft.clone();
		ResearchResources originalTotal = neededLeft.clone();
		ResearchResources originalAdded = added.clone();
		
		//the changes mussn't affect the manager
		neededLeft.setResources(Resource.CARBON, 0);
		neededLeft.setResources(Resource.SILICIUM, 0);
		neededLeft.setResources(Resource.IRON, 0);
		
		neededTotal.setResources(Resource.CARBON, 0);
		neededTotal.setResources(Resource.SILICIUM, 0);
		neededTotal.setResources(Resource.IRON, 0);
		
		added.setResources(Resource.CARBON, 5);
		added.setResources(Resource.SILICIUM, 5);
		added.setResources(Resource.IRON, 5);
		
		assertEquals(originalLeft, manager.getResearchResourcesNeededLeft(ResearchArea.FTL, 2));
		assertEquals(originalTotal, manager.getResearchResourcesNeededTotal(ResearchArea.FTL, 2));
		assertEquals(originalAdded, manager.getResearchResourcesAdded(ResearchArea.FTL, 2));
		assertFalse(manager.getResearchResourcesNeededLeft(ResearchArea.FTL, 2).isEmpty());
		assertFalse(manager.getResearchResourcesNeededTotal(ResearchArea.FTL, 2).isEmpty());
		assertTrue(manager.getResearchResourcesAdded(ResearchArea.FTL, 2).isEmpty());
	}
}
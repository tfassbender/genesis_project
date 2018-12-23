package net.jfabricationgames.genesis_project.manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.Technology;

class TechnologyManagerTest {
	
	private TechnologyManager getTechnologyManager() {
		Player player1 = mock(Player.class);
		TechnologyManager manager = new TechnologyManager(player1);
		return manager;
	}
	
	@Test
	public void testTechnologyManager() {
		//test the creation of a technology manager
		getTechnologyManager();
	}
	
	@Test
	public void testIsTechnologyExplored() {
		TechnologyManager manager = getTechnologyManager();
		
		assertFalse(manager.isTechnologyExplored(Technology.COLONY_POINTS));
		assertFalse(manager.isTechnologyExplored(Technology.TRAIDING_POST_POINTS));
		assertFalse(manager.isTechnologyExplored(Technology.ALLIANCE_BUILDINGS));
	}
	
	@Test
	public void testExploreTechnology() {
		TechnologyManager manager = getTechnologyManager();
		
		manager.exploreTechnology(Technology.COLONY_POINTS);
		manager.exploreTechnology(Technology.ALLIANCE_BUILDINGS);
		
		assertTrue(manager.isTechnologyExplored(Technology.COLONY_POINTS));
		assertFalse(manager.isTechnologyExplored(Technology.TRAIDING_POST_POINTS));
		assertTrue(manager.isTechnologyExplored(Technology.ALLIANCE_BUILDINGS));
		assertFalse(manager.isTechnologyExplored(Technology.NEW_PLANET_POINTS));
	}
}
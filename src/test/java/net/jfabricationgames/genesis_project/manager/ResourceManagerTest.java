package net.jfabricationgames.genesis_project.manager;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import net.jfabricationgames.genesis_project.game.BuildingResources;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.PlayerClass;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.game.Resource;

class ResourceManagerTest {
	
	private Player createPlayerMock() {
		PlayerClass clazz = PlayerClass.ENCOR;//Class: Blue, C/Si/Fe
		Player player = mock(Player.class);
		IResourceManager manager = new ResourceManager(player);
		when(player.getResourceManager()).thenReturn(manager);
		when(player.getPlayerClass()).thenReturn(clazz);
		return player;
	}
	
	@Test
	public void testSetters() {
		Player player = createPlayerMock();
		IResourceManager manager = player.getResourceManager();
		
		manager.setResourcesC(5);
		manager.setResourcesSi(6);
		manager.setResourcesFe(7);
		manager.setResearchPoints(4);
		manager.setScientists(3);
		manager.setFTL(2);
		
		assertEquals(5, manager.getResourcesC());
		assertEquals(6, manager.getResourcesSi());
		assertEquals(7, manager.getResourcesFe());
		assertEquals(4, manager.getResearchPoints());
		assertEquals(3, manager.getScientists());
		assertEquals(2, manager.getFTL());
	}
	
	@Test
	public void testParameterizedSetters() {
		Player player = createPlayerMock();
		IResourceManager manager = player.getResourceManager();
		
		manager.setResources(Resource.CARBON, 5);
		manager.setResources(Resource.SILICIUM, 6);
		manager.setResources(Resource.IRON, 7);
		manager.setResources(Resource.RESEARCH_POINTS, 4);
		manager.setResources(Resource.SCIENTISTS, 3);
		manager.setResources(Resource.FTL, 2);
		
		assertEquals(5, manager.getResourcesC());
		assertEquals(6, manager.getResourcesSi());
		assertEquals(7, manager.getResourcesFe());
		assertEquals(4, manager.getResearchPoints());
		assertEquals(3, manager.getScientists());
		assertEquals(2, manager.getFTL());
	}
	
	@Test
	public void testAdders() {
		Player player = createPlayerMock();
		IResourceManager manager = player.getResourceManager();
		
		manager.setResourcesC(5);
		manager.setResourcesSi(6);
		manager.setResourcesFe(7);
		manager.setResearchPoints(4);
		manager.setScientists(3);
		manager.setFTL(2);
		
		manager.addResourcesC(5);
		manager.addResourcesSi(6);
		manager.addResourcesFe(7);
		manager.addResearchPoints(4);
		manager.addScientists(3);
		manager.addFTL(2);
		
		assertEquals(10, manager.getResourcesC());
		assertEquals(12, manager.getResourcesSi());
		assertEquals(14, manager.getResourcesFe());
		assertEquals(8, manager.getResearchPoints());
		assertEquals(6, manager.getScientists());
		assertEquals(4, manager.getFTL());
	}
	
	@Test
	public void testParameterizedAdders() {
		Player player = createPlayerMock();
		IResourceManager manager = player.getResourceManager();
		
		manager.setResourcesC(5);
		manager.setResourcesSi(6);
		manager.setResourcesFe(7);
		manager.setResearchPoints(4);
		manager.setScientists(3);
		manager.setFTL(2);
		
		manager.addResources(Resource.CARBON, 5);
		manager.addResources(Resource.SILICIUM, 6);
		manager.addResources(Resource.IRON, 7);
		manager.addResources(Resource.RESEARCH_POINTS, 4);
		manager.addResources(Resource.SCIENTISTS, 3);
		manager.addResources(Resource.FTL, 2);
		
		assertEquals(10, manager.getResourcesC());
		assertEquals(12, manager.getResourcesSi());
		assertEquals(14, manager.getResourcesFe());
		assertEquals(8, manager.getResearchPoints());
		assertEquals(6, manager.getScientists());
		assertEquals(4, manager.getFTL());
	}
	
	@Test
	public void testReducers() {
		Player player = createPlayerMock();
		IResourceManager manager = player.getResourceManager();
		
		manager.setResources(Resource.CARBON, 10);
		manager.setResources(Resource.SILICIUM, 10);
		manager.setResources(Resource.IRON, 10);
		manager.setResources(Resource.RESEARCH_POINTS, 10);
		manager.setResources(Resource.SCIENTISTS, 10);
		manager.setResources(Resource.FTL, 10);
		
		manager.reduceResources(Resource.CARBON, 1);
		manager.reduceResources(Resource.SILICIUM, 2);
		manager.reduceResources(Resource.IRON, 3);
		manager.reduceResources(Resource.RESEARCH_POINTS, 4);
		manager.reduceResources(Resource.SCIENTISTS, 5);
		manager.reduceResources(Resource.FTL, 6);
		
		assertEquals(9, manager.getResourcesC());
		assertEquals(8, manager.getResourcesSi());
		assertEquals(7, manager.getResourcesFe());
		assertEquals(6, manager.getResearchPoints());
		assertEquals(5, manager.getScientists());
		assertEquals(4, manager.getFTL());
		
		BuildingResources buildingResources = new BuildingResources(2, 3, 4);
		manager.reduceResources(buildingResources);
		
		assertEquals(7, manager.getResourcesC());
		assertEquals(5, manager.getResourcesSi());
		assertEquals(3, manager.getResourcesFe());
		assertEquals(6, manager.getResearchPoints());
		assertEquals(5, manager.getScientists());
		assertEquals(4, manager.getFTL());
		
		ResearchResources researchResources = new ResearchResources(3, 4, 1, 2);
		manager.reduceResources(researchResources);
		
		assertEquals(4, manager.getResourcesC());
		assertEquals(1, manager.getResourcesSi());
		assertEquals(2, manager.getResourcesFe());
		assertEquals(6, manager.getResearchPoints());
		assertEquals(3, manager.getScientists());
		assertEquals(4, manager.getFTL());
	}
	
	@Test
	public void testPrimaryResources() {
		Player player = createPlayerMock();
		IResourceManager manager = player.getResourceManager();
		
		manager.setResourcesPrimary(5);
		manager.addResourcesPrimary(3);
		assertEquals(8, manager.getResourcesPrimary());
	}
	
	@Test
	public void testSecundaryResources() {
		Player player = createPlayerMock();
		IResourceManager manager = player.getResourceManager();
		
		manager.setResourcesSecundary(4);
		manager.addResourcesSecundary(3);
		assertEquals(7, manager.getResourcesSecundary());
	}
	
	@Test
	public void testTertiaryResources() {
		Player player = createPlayerMock();
		IResourceManager manager = player.getResourceManager();
		
		manager.setResourcesTertiary(9);
		manager.addResourcesTertiary(2);
		assertEquals(11, manager.getResourcesTertiary());
	}
	
	@Test
	public void testIsResourcesAvailable() {
		Player player = createPlayerMock();
		IResourceManager manager = player.getResourceManager();
		
		manager.setResources(Resource.CARBON, 10);
		manager.setResources(Resource.SILICIUM, 10);
		manager.setResources(Resource.IRON, 10);
		manager.setResources(Resource.RESEARCH_POINTS, 10);
		manager.setResources(Resource.SCIENTISTS, 10);
		manager.setResources(Resource.FTL, 10);
		
		BuildingResources buildingResources = new BuildingResources(2, 3, 4);
		BuildingResources buildingResourcesNotAvailable = new BuildingResources(11, 2, 1);
		ResearchResources researchResources = new ResearchResources(3, 4, 1, 2);
		ResearchResources researchResourcesNotAvailable = new ResearchResources(0, 1, 3, 13);
		
		assertTrue(manager.isResourcesAvailable(buildingResources));
		assertFalse(manager.isResourcesAvailable(buildingResourcesNotAvailable));
		assertTrue(manager.isResourcesAvailable(researchResources));
		assertFalse(manager.isResourcesAvailable(researchResourcesNotAvailable));
	}
}
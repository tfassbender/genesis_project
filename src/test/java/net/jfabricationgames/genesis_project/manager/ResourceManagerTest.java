package net.jfabricationgames.genesis_project.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.PlayerClass;
import net.jfabricationgames.genesis_project.manager.IResourceManager;

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
}
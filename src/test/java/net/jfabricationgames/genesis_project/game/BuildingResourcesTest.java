package net.jfabricationgames.genesis_project.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BuildingResourcesTest {
	
	@Test
	public void testPrimaryResources() {
		PlayerClass clazz = PlayerClass.ENCOR;//Class: Blue, C/Si/Fe
		BuildingResources resources = new BuildingResources();
		
		resources.setResourcesPrimary(clazz, 5);
		resources.addResourcesPrimary(clazz, 3);
		assertEquals(8, resources.getResourcesPrimary(clazz));
	}
	
	@Test
	public void testSecundaryResources() {
		PlayerClass clazz = PlayerClass.ENCOR;//Class: Blue, C/Si/Fe
		BuildingResources resources = new BuildingResources();
		
		resources.setResourcesSecundary(clazz, 4);
		resources.addResourcesSecundary(clazz, 3);
		assertEquals(7, resources.getResourcesSecundary(clazz));
	}
	
	@Test
	public void testPrimaryTertiary() {
		PlayerClass clazz = PlayerClass.ENCOR;//Class: Blue, C/Si/Fe
		BuildingResources resources = new BuildingResources();
		
		resources.setResourcesTertiary(clazz, 9);
		resources.addResourcesTertiary(clazz, 2);
		assertEquals(11, resources.getResourcesTertiary(clazz));
	}
	
	@Test
	public void testEquals() {
		BuildingResources resources1 = new BuildingResources(1, 2, 3);
		BuildingResources resources2 = new BuildingResources(1, 2, 3);
		BuildingResources resources3 = new BuildingResources(1, 2, 2);
		BuildingResources clone = resources1.clone();
		
		assertTrue(resources1.equals(resources2));
		assertTrue(resources2.equals(resources1));
		assertFalse(resources1.equals(resources3));
		assertFalse(resources2.equals(resources3));
		
		assertTrue(resources1.equals(clone));
		assertTrue(resources2.equals(clone));
		assertFalse(resources3.equals(clone));
	}
}
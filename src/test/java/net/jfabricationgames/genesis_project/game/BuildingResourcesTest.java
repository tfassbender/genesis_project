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
}
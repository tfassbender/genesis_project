package net.jfabricationgames.genesis_project.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class CompleteResourcesTest {
	
	@Test
	public void testEquals() {
		CompleteResources resources1 = new CompleteResources(1, 2, 3, 4, 5, 6);
		CompleteResources resources2 = new CompleteResources(1, 2, 3, 4, 5, 6);
		CompleteResources resources3 = new CompleteResources(1, 2, 2, 4, 5, 6);
		CompleteResources clone = resources1.clone();
		
		assertTrue(resources1.equals(resources2));
		assertTrue(resources2.equals(resources1));
		assertFalse(resources1.equals(resources3));
		assertFalse(resources2.equals(resources3));
		
		assertTrue(resources1.equals(clone));
		assertTrue(resources2.equals(clone));
		assertFalse(resources3.equals(clone));
	}
	
	@Test
	public void testSetResources() {
		CompleteResources resources1 = new CompleteResources(1, 2, 3, 1, 2, 4);
		CompleteResources resources2 = new CompleteResources(2, 3, 4, 1, 2, 3);
		
		for (Resource resource : CompleteResources.COMPLETE_RESOURCES) {
			resources1.setResources(resource, resources2.getResources(resource));
		}
		
		assertEquals(resources2, resources1);
	}
	
	@Test
	public void testAddResources() {
		CompleteResources resources1 = new CompleteResources(1, 2, 3, 1, 2, 4);
		CompleteResources resources2 = new CompleteResources(2, 3, 4, 1, 2, 3);
		
		resources1.addResources(resources2);
		
		assertEquals(new CompleteResources(3, 5, 7, 2, 4, 7), resources1);
	}
}
package net.jfabricationgames.genesis_project.main_menu;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LoginFrameControllerTest {
	
	@Test
	public void testIsUsernameValid() {
		LoginFrameController controller = new LoginFrameController();
		assertTrue(controller.isUsernameValid("testUser"));
		assertTrue(controller.isUsernameValid("test_user"));
		assertTrue(controller.isUsernameValid("test_user_42"));
		assertTrue(controller.isUsernameValid("42_test_42"));
		assertTrue(controller.isUsernameValid("42-test-42"));
		assertTrue(controller.isUsernameValid("__u-s-e-r__n-a-m-e__"));
		assertTrue(controller.isUsernameValid("_TESTTEST_"));
		
		assertFalse(controller.isUsernameValid("42"));
		assertFalse(controller.isUsernameValid("test/name"));
		assertFalse(controller.isUsernameValid("%$§!$§!$"));
	}
}
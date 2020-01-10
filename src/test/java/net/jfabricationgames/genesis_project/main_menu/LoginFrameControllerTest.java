package net.jfabricationgames.genesis_project.main_menu;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LoginFrameControllerTest {
	
	@Test
	public void testIsUsernameValid() {
		assertTrue(LoginFrameController.isUsernameValid("testUser"));
		assertTrue(LoginFrameController.isUsernameValid("test_user"));
		assertTrue(LoginFrameController.isUsernameValid("test_user_42"));
		assertTrue(LoginFrameController.isUsernameValid("42_test_42"));
		assertTrue(LoginFrameController.isUsernameValid("42-test-42"));
		assertTrue(LoginFrameController.isUsernameValid("__u-s-e-r__n-a-m-e__"));
		assertTrue(LoginFrameController.isUsernameValid("_TESTTEST_"));
		
		assertFalse(LoginFrameController.isUsernameValid("42"));
		assertFalse(LoginFrameController.isUsernameValid("test/name"));
		assertFalse(LoginFrameController.isUsernameValid("%$§!$§!$"));
	}
}
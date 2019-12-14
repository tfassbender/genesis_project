package net.jfabricationgames.genesis_project.game;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.jfabricationgames.genesis_project.testUtils.GameCreationUtil;

class JsonSerializationTest {
	
	@Test
	public void testGameSerialization() throws JsonProcessingException {
		Game game = GameCreationUtil.createGame();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			//serialize only fields (no auto detection for getter methods)
			mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY)
					.withGetterVisibility(JsonAutoDetect.Visibility.NONE).withSetterVisibility(JsonAutoDetect.Visibility.NONE)
					.withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
			
			String serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(game);
			System.out.println(serialized);
		}
		catch (JsonProcessingException jpe) {
			jpe.printStackTrace();
			throw jpe;
		}
	}
	
	@Test
	public void testGameDeserialization() {
		fail("Not yet implemented");
	}
}
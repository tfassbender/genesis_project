package net.jfabricationgames.genesis_project.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.jfabricationgames.genesis_project.testUtils.GameCreationUtil;

class JsonSerializationTest {
	
	private static ObjectMapper mapper;
	
	private static final boolean printSerialized = false;
	
	@BeforeAll
	public static void initializeMapper() {
		mapper = new ObjectMapper();
		
		//serialize only fields (no auto detection for getter methods)
		mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY)
				.withGetterVisibility(JsonAutoDetect.Visibility.NONE).withSetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
	}
	
	@Test
	public void testGameSerialization() throws JsonProcessingException {
		Game game = GameCreationUtil.createGame();
		
		//just test whether there is no exception thrown
		String serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(game);
		
		if (printSerialized) {
			System.out.println(serialized);
		}
	}
	
	@Test
	public void testGameDeserialization() throws IOException {
		Game game = GameCreationUtil.createGame();
		
		try {
			String serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(game);
			
			Game deserialized = mapper.readerFor(Game.class).readValue(serialized);
			
			//test some of the values
			//Player1 is included
			assertTrue(deserialized.getPlayers().stream().map(p -> p.getUser().getUsername()).filter(name -> name.equals("Player1")).findAny()
					.isPresent());
			//Player1 has some resources (CompleteResources(10, 10, 10, 5, 5, 2))
			Player player1 = deserialized.getPlayers().stream().filter(p -> p.getUser().getUsername().equals("Player1")).findFirst().get();
			assertEquals(10, player1.getResourceManager().getResourcesC());
			assertEquals(5, player1.getResourceManager().getResearchPoints());
			assertEquals(2, player1.getResourceManager().getFTL());
			
			//there is no local player because the field is transient
			assertThrows(IllegalStateException.class, () -> deserialized.getLocalPlayer());
			
			//the first turn goal is MINE_TRAIDING_POST
			assertEquals(TurnGoal.MINE_TRADING_POST, deserialized.getTurnManager().getTurnGoals().get(0));
			
			//there is a genesis planet with a city of player1 in field (1,1)
			PlayerBuilding building = deserialized.getBoard().getField(1, 1).getBuildings()[1];
			assertEquals(Building.CITY, building.getBuilding());
			assertEquals("Player1", building.getPlayer().getUser().getUsername());
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			throw ioe;
		}
	}
}
package net.jfabricationgames.genesis_project.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.genesis_project.move.IMove;
import net.jfabricationgames.genesis_project.move.MoveBuilder;
import net.jfabricationgames.genesis_project.move.MoveType;
import net.jfabricationgames.genesis_project.testUtils.ConstantsInitializerUtil;
import net.jfabricationgames.genesis_project.testUtils.DescriptionTextsInitializerUtil;
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
		
		if (printSerialized) {
			String serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(game);
			System.out.println("\n\n\n");
			System.out.println(serialized);
		}
		else {
			//just test whether there is no exception thrown
			mapper.writeValueAsString(game);
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
			assertTrue(deserialized.getPlayers().stream().map(p -> p.getUsername()).filter(name -> name.equals("Player1")).findAny().isPresent());
			//Player1 has some resources (CompleteResources(10, 10, 10, 5, 5, 2))
			Player player1 = deserialized.getPlayers().stream().filter(p -> p.getUsername().equals("Player1")).findFirst().get();
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
			assertEquals("Player1", building.getPlayer().getUsername());
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			throw ioe;
		}
	}
	
	@Test
	public void testSerializeGameWithAlliance() throws IOException {
		Game game = GameCreationUtil.createGame();
		
		//find the alliance
		Map<Position, Field> fields = game.getBoard().getFields();
		List<Field> alliance = Arrays
				.asList(new Field[] {fields.get(new Position(0, 0)), fields.get(new Position(1, 1)), fields.get(new Position(2, 0))});
		List<Field> satellites = Arrays.asList(new Field[] {fields.get(new Position(1, 0))});
		
		//create the alliance
		game.getPlayers().get(0).getAllianceManager().addAlliance(alliance, satellites, AllianceBonus.POINTS, 0);
		
		//serialize and deserialize the game with the alliance
		try {
			String serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(game);
			
			Game deserialized = mapper.readerFor(Game.class).readValue(serialized);
			
			assertTrue(!deserialized.getAllianceManager().getAllianceBonuses().isEmpty());
			assertTrue(deserialized.getAllianceManager().getAlliances().get(0).getPlanets().get(0).getPosition().equals(new Position(0, 0)));
			assertTrue(deserialized.getAllianceManager().getAlliances().get(0).getBonus().equals(AllianceBonus.POINTS));
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			throw ioe;
		}
	}
	
	@Test
	public void testSerializeAttack() throws IOException {
		Game game = GameCreationUtil.createGame();
		Attack attack = new AttackBuilder().setGame(game).setAttackPenalty(5, 10, 15).setPenaltyOffsets(1, 2, 3).setEnemy(Enemy.PARASITE)
				.setAttackTarget(AttackTarget.POINTS).setStrength(10).build();
		
		try {
			String serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(attack);
			
			Attack deserialized = mapper.readerFor(Attack.class).readValue(serialized);
			
			assertEquals(AttackTarget.POINTS, deserialized.getAttackTarget());
			assertEquals(10, deserialized.getStrength());
			assertEquals(Enemy.PARASITE, attack.getEnemy());
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			throw ioe;
		}
	}
	
	@Test
	public void testSerializeMove() throws IOException {
		Game game = GameCreationUtil.createGame();
		
		IMove buildMove = new MoveBuilder().setField(game.getBoard().getCenterField()).setPlayer(game.getPlayers().get(0).getUsername())
				.setBuilding(Building.COLONY).setType(MoveType.BUILD).build();
		IMove researchMove = new MoveBuilder().setResearchArea(ResearchArea.WEAPON).setType(MoveType.RESEARCH).build();
		IMove passingMove = new MoveBuilder().setType(MoveType.PASS).build();
		
		try {
			String serializedBuildMove = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(buildMove);
			String serializedResearchMove = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(researchMove);
			String serializedPassingMove = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(passingMove);
			
			IMove deserializedBuildMove = mapper.readerFor(IMove.class).readValue(serializedBuildMove);
			IMove deserializedResearchMove = mapper.readerFor(IMove.class).readValue(serializedResearchMove);
			IMove deserializedPassingMove = mapper.readerFor(IMove.class).readValue(serializedPassingMove);
			
			assertEquals(MoveType.BUILD, deserializedBuildMove.getType());
			assertEquals(Building.COLONY, deserializedBuildMove.getBuilding());
			assertEquals(game.getPlayers().get(0).getUsername(), deserializedBuildMove.getPlayer());
			assertFalse(deserializedBuildMove.isPassing());
			
			assertEquals(MoveType.RESEARCH, deserializedResearchMove.getType());
			assertEquals(ResearchArea.WEAPON, deserializedResearchMove.getResearchArea());
			assertFalse(deserializedResearchMove.isPassing());
			
			assertTrue(deserializedPassingMove.isPassing());
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			throw ioe;
		}
	}
	
	@Test
	public void testSerializeConstants() throws IOException {
		Constants constants = Constants.getInstance();
		ConstantsInitializerUtil.initAll();
		
		try {
			String serialized;
			if (printSerialized) {
				serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(constants);
				System.out.println("\n\n\n");
				System.out.println(serialized);
			}
			else {
				serialized = mapper.writeValueAsString(constants);
			}
			
			mapper.readerFor(Constants.class).readValue(serialized);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			throw ioe;
		}
	}
	
	@Test
	public void testSerializeDescriptionTexts() throws IOException {
		DescriptionTexts descriptionTexts = DescriptionTexts.getInstance();
		DescriptionTextsInitializerUtil.initializeDescriptionTexts();
		
		try {
			String serialized;
			if (printSerialized) {
				serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(descriptionTexts);
				System.out.println("\n\n\n");
				System.out.println(serialized);
			}
			else {
				serialized = mapper.writeValueAsString(descriptionTexts);
			}
			
			mapper.readerFor(DescriptionTexts.class).readValue(serialized);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			throw ioe;
		}
	}
}
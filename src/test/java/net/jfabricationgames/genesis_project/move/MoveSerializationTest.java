package net.jfabricationgames.genesis_project.move;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.jfabricationgames.genesis_project.game.AllianceBonus;
import net.jfabricationgames.genesis_project.game.Board;
import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.ResearchArea;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.testUtils.GameCreationUtil;

class MoveSerializationTest {
	
	private static ObjectMapper mapper;
	
	private static Game game;
	
	@BeforeAll
	public static void initializeMapper() {
		mapper = new ObjectMapper();
		
		//serialize only fields (no auto detection for getter methods)
		mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY)
				.withGetterVisibility(JsonAutoDetect.Visibility.NONE).withSetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
		
		game = GameCreationUtil.createGame();
	}
	
	@Test
	public void testBuildMove() throws IOException {
		IMove move = new MoveBuilder(game).setPlayer(game.getPlayers().get(0)).setType(MoveType.BUILD).setField(game.getBoard().getField(3, 1))
				.setBuilding(Building.COLONY).build();
		
		String serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(move);
		IMove deserialized = mapper.readerFor(IMove.class).readValue(serialized);
		
		assertTrue(game.isMoveExecutable(move));
		
		assertEquals(move.getType(), deserialized.getType());
		assertEquals(move.getField(), deserialized.getField());
		assertEquals(move.getBuilding(), deserialized.getBuilding());
	}
	
	@Test
	public void testResearchMove() throws IOException {
		IMove move = new MoveBuilder(game).setPlayer(game.getPlayers().get(0)).setType(MoveType.RESEARCH).setResearchArea(ResearchArea.FTL).build();
		
		String serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(move);
		IMove deserialized = mapper.readerFor(IMove.class).readValue(serialized);
		
		assertTrue(game.isMoveExecutable(move));
		
		assertEquals(move.getType(), deserialized.getType());
		assertEquals(move.getResearchArea(), deserialized.getResearchArea());
	}
	
	@Test
	public void testResearchResourcesMove() throws IOException {
		IMove move = new MoveBuilder(game).setPlayer(game.getPlayers().get(0)).setType(MoveType.RESEARCH_RESOURCES).setResearchArea(ResearchArea.FTL)
				.setResearchResources(new ResearchResources(2, 2, 2, 0)).build();
		
		String serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(move);
		IMove deserialized = mapper.readerFor(IMove.class).readValue(serialized);
		
		assertTrue(game.isMoveExecutable(move));
		
		assertEquals(move.getType(), deserialized.getType());
		assertEquals(move.getResearchArea(), deserialized.getResearchArea());
		assertEquals(move.getAllianceBonus(), deserialized.getAllianceBonus());
		assertEquals(move.getAllianceBonusIndex(), deserialized.getAllianceBonusIndex());
		assertEquals(move.getAlliancePlanets(), deserialized.getAlliancePlanets());
		assertEquals(move.getSatelliteFields(), deserialized.getSatelliteFields());
	}
	
	@Test
	public void testAllianceMove() throws IOException {
		//for the planets and satellite fields see AllianceManagerTest
		Map<Board.Position, Field> fields = game.getBoard().getFields();
		IMove move = new MoveBuilder(game).setPlayer(game.getPlayers().get(0)).setType(MoveType.ALLIANCE).setAllianceBonus(AllianceBonus.POINTS)
				.setAllianceBonusIndex(0)
				.setAlliancePlanets(
						Arrays.asList(new Field[] {fields.get(new Position(0, 0)), fields.get(new Position(1, 1)), fields.get(new Position(0, 3))}))
				.setSatelliteFields(Arrays.asList(new Field[] {fields.get(new Position(0, 1)), fields.get(new Position(0, 2))})).build();
		
		String serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(move);
		IMove deserialized = mapper.readerFor(IMove.class).readValue(serialized);
		
		assertTrue(game.isMoveExecutable(move));
		
		assertEquals(move.getType(), deserialized.getType());
		assertEquals(move.getField(), deserialized.getField());
		assertEquals(move.getBuilding(), deserialized.getBuilding());
	}
	
	@Test
	public void testPassMove() throws IOException {
		IMove move = new MoveBuilder(game).setPlayer(game.getPlayers().get(0)).setType(MoveType.PASS).build();
		
		String serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(move);
		IMove deserialized = mapper.readerFor(IMove.class).readValue(serialized);
		
		assertTrue(game.isMoveExecutable(move));
		
		assertEquals(move.getType(), deserialized.getType());
		assertTrue(deserialized.isPassing());
	}
}
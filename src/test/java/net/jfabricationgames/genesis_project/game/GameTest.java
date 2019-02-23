package net.jfabricationgames.genesis_project.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.genesis_project.manager.BuildingManager;
import net.jfabricationgames.genesis_project.manager.IResearchManager;
import net.jfabricationgames.genesis_project.manager.IResourceManager;
import net.jfabricationgames.genesis_project.move.IMove;
import net.jfabricationgames.genesis_project.testUtils.GameCreationUtil;
import net.jfabricationgames.genesis_project.testUtils.MoveCreaterUtil;

class GameTest {
	
	private void skipPlayersTurn(Game game) {
		//two players -> skip the other players turn
		game.getTurnManager().nextMove();
	}
	
	@Test
	public void testExecuteMove_buildMoves() {
		Game game = GameCreationUtil.createGame();
		Player player = game.getPlayers().get(0);
		//enough resources for the first colony and the research center but not for the mine
		player.getResourceManager().setResourcesPrimary(8);
		player.getResourceManager().setResourcesSecundary(8);
		player.getResourceManager().setResourcesTertiary(4);
		
		//valid moves
		IMove buildColony = MoveCreaterUtil.getBuildingMove(game, player, Building.COLONY, 4, 2);//costs 3, 3, 0; build on space 3
		IMove buildResearchCenter = MoveCreaterUtil.getBuildingMove(game, player, Building.RESEARCH_CENTER, 0, 3);//costs 5 5 2; build on space 2
		IMove buildMine = MoveCreaterUtil.getBuildingMove(game, player, Building.MINE, 5, 0);//2 2 0
		
		game.executeMove(buildColony);
		//resources taken
		assertEquals(5, player.getResourceManager().getResourcesPrimary());
		assertEquals(5, player.getResourceManager().getResourcesSecundary());
		assertEquals(4, player.getResourceManager().getResourcesTertiary());
		//building on the planet
		Field field = game.getBoard().getFields().get(new Position(4, 2));
		assertEquals(new PlayerBuilding(Building.COLONY, player), field.getBuildings()[2]);
		
		skipPlayersTurn(game);
		game.executeMove(buildResearchCenter);
		//resources taken
		assertEquals(0, player.getResourceManager().getResourcesPrimary());
		assertEquals(0, player.getResourceManager().getResourcesSecundary());
		assertEquals(2, player.getResourceManager().getResourcesTertiary());
		//building on planet
		field = game.getBoard().getFields().get(new Position(0, 3));
		assertEquals(new PlayerBuilding(Building.RESEARCH_CENTER, player), field.getBuildings()[1]);
		
		//no resources for the last move
		assertFalse(game.isMoveExecutable(buildMine));
		
		//turn over
		assertFalse(game.getTurnManager().isPlayersTurn(player));
	}
	
	@Test
	public void testExecuteMove_allianceMoves() {
		Game game = GameCreationUtil.createGame();
		Player player = game.getPlayers().get(0);
		//enough resources for 5 satellites
		player.getResourceManager().setResourcesPrimary(5);
		player.getResourceManager().setResourcesSecundary(5);
		player.getResourceManager().setResourcesTertiary(2);
		
		Map<Position, Field> fields = game.getBoard().getFields();
		//valid moves
		Field[] planets1 = new Field[] {fields.get(new Position(0, 0)), fields.get(new Position(1, 1)), fields.get(new Position(0, 3))};
		Field[] satellites1 = new Field[] {fields.get(new Position(0, 1)), fields.get(new Position(0, 2))};
		Field[] planets2 = new Field[] {fields.get(new Position(0, 0)), fields.get(new Position(1, 1)), fields.get(new Position(2, 0))};
		Field[] satellites2 = new Field[] {fields.get(new Position(1, 0))};
		IMove allianceMove1 = MoveCreaterUtil.getAllianceMove(game, player, planets1, satellites1, AllianceBonus.MILITARY_RANGE, 0);
		IMove allianceMove2 = MoveCreaterUtil.getAllianceMove(game, player, planets2, satellites2, AllianceBonus.POINTS, 0);
		
		game.executeMove(allianceMove1);
		//new alliance created
		assertEquals(1, player.getAllianceManager().getAlliances().size());
		assertEquals(AllianceBonus.MILITARY_RANGE, player.getAllianceManager().getAllianceBonuses().get(0));
		//points for alliance
		assertEquals(4, player.getPointManager().getPoints());
		//satellites built
		assertEquals(1, game.getBoard().getFields().get(new Position(0, 1)).getSpaceBuildings().size());
		assertTrue(game.getBoard().getFields().get(new Position(0, 1)).getSpaceBuildings().contains(new PlayerBuilding(Building.SATELLITE, player)));
		assertEquals(1, game.getBoard().getFields().get(new Position(0, 2)).getSpaceBuildings().size());
		assertTrue(game.getBoard().getFields().get(new Position(0, 2)).getSpaceBuildings().contains(new PlayerBuilding(Building.SATELLITE, player)));
		//resources for satellites taken
		assertEquals(3, player.getResourceManager().getResourcesPrimary());
		assertEquals(3, player.getResourceManager().getResourcesSecundary());
		assertEquals(2, player.getResourceManager().getResourcesTertiary());
		
		skipPlayersTurn(game);
		game.executeMove(allianceMove2);
		//new alliance created
		assertEquals(2, player.getAllianceManager().getAlliances().size());
		assertTrue(player.getAllianceManager().getAllianceBonuses().contains(AllianceBonus.POINTS));
		//points for alliance
		assertEquals(14, player.getPointManager().getPoints());
		//satellites built
		assertEquals(1, game.getBoard().getFields().get(new Position(0, 1)).getSpaceBuildings().size());
		assertTrue(game.getBoard().getFields().get(new Position(0, 1)).getSpaceBuildings().contains(new PlayerBuilding(Building.SATELLITE, player)));
		assertEquals(1, game.getBoard().getFields().get(new Position(0, 2)).getSpaceBuildings().size());
		assertTrue(game.getBoard().getFields().get(new Position(0, 2)).getSpaceBuildings().contains(new PlayerBuilding(Building.SATELLITE, player)));
		//resources for satellites taken
		assertEquals(2, player.getResourceManager().getResourcesPrimary());
		assertEquals(2, player.getResourceManager().getResourcesSecundary());
		assertEquals(2, player.getResourceManager().getResourcesTertiary());
		
		//turn over
		assertFalse(game.getTurnManager().isPlayersTurn(player));
	}
	
	@Test
	public void testExecuteMove_researchMoves() {
		Game game = GameCreationUtil.createGame();
		Player player = game.getPlayers().get(0);
		IResourceManager resourceManager = player.getResourceManager();
		resourceManager.setResearchPoints(17);
		
		//valid moves
		IMove researchMoveMines = MoveCreaterUtil.getResearchMove(game, player, ResearchArea.MINES);
		IMove researchMoveEconomy = MoveCreaterUtil.getResearchMove(game, player, ResearchArea.ECONOMY);
		IMove researchMoveWeapon = MoveCreaterUtil.getResearchMove(game, player, ResearchArea.WEAPON);
		
		game.executeMove(researchMoveMines);
		skipPlayersTurn(game);
		game.executeMove(researchMoveEconomy);
		skipPlayersTurn(game);
		game.executeMove(researchMoveWeapon);
		
		assertEquals(1, player.getResearchManager().getState(ResearchArea.MINES));
		assertEquals(1, player.getResearchManager().getState(ResearchArea.ECONOMY));
		assertEquals(1, player.getResearchManager().getState(ResearchArea.WEAPON));
		assertEquals(1, game.getResearchManager().getState(ResearchArea.WEAPON));
		assertEquals(2, player.getResourceManager().getResearchPoints());
		
		//turn over
		assertFalse(game.getTurnManager().isPlayersTurn(player));
	}
	
	@Test
	public void testExecuteMove_researchResourcesMoves() {
		Game game = GameCreationUtil.createGame();
		Player player = game.getPlayers().get(0);
		IResourceManager resourceManager = player.getResourceManager();
		//clear all resources; then add specific resources for this test
		resourceManager.reduceResources(resourceManager.getCompleteResources());
		resourceManager.addResources(new ResearchResources(9, 7, 6, 2));
		
		//valid moves
		IMove researchResourcesMove = MoveCreaterUtil.getResearchResourcesMove(game, player, ResearchArea.FTL, new ResearchResources(2, 2, 2, 0));
		IMove researchResourcesMove2 = MoveCreaterUtil.getResearchResourcesMove(game, player, ResearchArea.FTL, new ResearchResources(4, 4, 4, 0));
		
		game.executeMove(researchResourcesMove);
		assertTrue(game.getResearchManager().isStateAccessible(ResearchArea.FTL, 2));
		assertEquals(4, game.getResearchManager().getNextResourceNeedingState(ResearchArea.FTL));
		assertEquals(7, player.getResourceManager().getResourcesPrimary());
		assertEquals(5, player.getResourceManager().getResourcesSecundary());
		assertEquals(4, player.getResourceManager().getResourcesTertiary());
		assertEquals(2, player.getResourceManager().getScientists());
		
		game.executeMove(researchResourcesMove2);
		assertTrue(game.getResearchManager().isStateAccessible(ResearchArea.FTL, 4));
		assertEquals(-1, game.getResearchManager().getNextResourceNeedingState(ResearchArea.FTL));
		assertEquals(3, player.getResourceManager().getResourcesPrimary());
		assertEquals(1, player.getResourceManager().getResourcesSecundary());
		assertEquals(0, player.getResourceManager().getResourcesTertiary());
		assertEquals(2, player.getResourceManager().getScientists());
		
		//turn NOT over
		assertTrue(game.getTurnManager().isPlayersTurn(player));
	}
	
	@Test
	public void testExecuteMove_passMoves() {
		Game game = GameCreationUtil.createGame();
		Player player = game.getPlayers().get(0);
		
		IMove pass = MoveCreaterUtil.getPassMove(game, player);
		
		//check the state before the execution
		assertTrue(game.getTurnManager().getPlayerOrder().contains(player));
		
		game.executeMove(pass);
		
		assertFalse(game.getTurnManager().getPlayerOrder().contains(player));
		//turn over
		assertFalse(game.getTurnManager().isPlayersTurn(player));
	}
	
	@Test
	public void testIsMoveExecutable_buildingMoves() {
		Game game = GameCreationUtil.createGame();
		Player player = game.getPlayers().get(0);
		Player player2 = game.getPlayers().get(1);
		((BuildingManager) player.getBuildingManager()).setNumBuildingsLeft(Building.GOVERNMENT, 0);
		//enough resources for everything but the research center on a black planet
		player.getResourceManager().setResourcesPrimary(8);
		player.getResourceManager().setResourcesSecundary(8);
		player.getResourceManager().setResourcesTertiary(4);
		
		//valid moves
		IMove buildColony = MoveCreaterUtil.getBuildingMove(game, player, Building.COLONY, 4, 2);
		IMove buildResearchCenter = MoveCreaterUtil.getBuildingMove(game, player, Building.RESEARCH_CENTER, 0, 3);
		IMove buildMine = MoveCreaterUtil.getBuildingMove(game, player, Building.MINE, 5, 0);
		
		//illegal moves
		IMove buildColonySpaceField = MoveCreaterUtil.getBuildingMove(game, player, Building.COLONY, 1, 0);
		IMove buildResearchCenterWithoutLaboratory = MoveCreaterUtil.getBuildingMove(game, player, Building.RESEARCH_CENTER, 2, 3);
		IMove buildResearchCenterNotEnoughResources = MoveCreaterUtil.getBuildingMove(game, player, Building.RESEARCH_CENTER, 2, 0);
		IMove buildGovernmentNoMoreBuildingsLeft = MoveCreaterUtil.getBuildingMove(game, player, Building.GOVERNMENT, 0, 3);
		IMove buildColonyNotTheActivePlayer = MoveCreaterUtil.getBuildingMove(game, player2, Building.COLONY, 2, 3);
		
		assertTrue(game.isMoveExecutable(buildColony));
		assertTrue(game.isMoveExecutable(buildResearchCenter));
		assertTrue(game.isMoveExecutable(buildMine));
		
		assertFalse(game.isMoveExecutable(buildColonySpaceField));
		assertFalse(game.isMoveExecutable(buildResearchCenterWithoutLaboratory));
		assertFalse(game.isMoveExecutable(buildResearchCenterNotEnoughResources));
		assertFalse(game.isMoveExecutable(buildGovernmentNoMoreBuildingsLeft));
		assertFalse(game.isMoveExecutable(buildColonyNotTheActivePlayer));
	}
	
	@Test
	public void testIsMoveExecutable_allianceMoves() {
		Game game = GameCreationUtil.createGame();
		Player player = game.getPlayers().get(0);
		//enough resources for 3 satellites
		player.getResourceManager().setResourcesPrimary(3);
		player.getResourceManager().setResourcesSecundary(3);
		player.getResourceManager().setResourcesTertiary(0);
		
		Map<Position, Field> fields = game.getBoard().getFields();
		//valid moves
		Field[] planets1 = new Field[] {fields.get(new Position(0, 0)), fields.get(new Position(1, 1)), fields.get(new Position(0, 3))};
		Field[] satellites1 = new Field[] {fields.get(new Position(0, 1)), fields.get(new Position(0, 2))};
		Field[] planets2 = new Field[] {fields.get(new Position(0, 0)), fields.get(new Position(1, 1)), fields.get(new Position(2, 0))};
		Field[] satellites2 = new Field[] {fields.get(new Position(1, 0))};
		IMove allianceMove1 = MoveCreaterUtil.getAllianceMove(game, player, planets1, satellites1, AllianceBonus.MILITARY_RANGE, 0);
		IMove allianceMove2 = MoveCreaterUtil.getAllianceMove(game, player, planets2, satellites2, AllianceBonus.POINTS, 0);
		
		//illegal moves
		Field[] planets3 = new Field[] {fields.get(new Position(1, 1)), fields.get(new Position(0, 3)), fields.get(new Position(2, 0))};//valid
		Field[] satellites3 = new Field[] {fields.get(new Position(1, 2)), fields.get(new Position(2, 2)), fields.get(new Position(2, 1))};//touching another planet
		Field[] planets4 = new Field[] {fields.get(new Position(1, 1)), fields.get(new Position(2, 0)), fields.get(new Position(3, 1))};//only one opponent building
		Field[] satellites4 = new Field[] {fields.get(new Position(2, 1)), fields.get(new Position(3, 0))};//more than needed but possible
		Field[] planets5 = new Field[] {fields.get(new Position(0, 0)), fields.get(new Position(1, 1)), fields.get(new Position(2, 0))};//valid
		Field[] satellites5 = new Field[] {fields.get(new Position(1, 0))};//valid
		IMove allianceMove3 = MoveCreaterUtil.getAllianceMove(game, player, planets3, satellites3, AllianceBonus.PRIMARY_RESOURCES, 0);
		IMove allianceMove4 = MoveCreaterUtil.getAllianceMove(game, player, planets4, satellites4, AllianceBonus.SCIENTISTS, 0);
		IMove allianceMove5 = MoveCreaterUtil.getAllianceMove(game, player, planets5, satellites5, null, 0);//alliance possible but no bonus chosen
		
		assertTrue(game.isMoveExecutable(allianceMove1));
		assertTrue(game.isMoveExecutable(allianceMove2));
		
		assertFalse(game.isMoveExecutable(allianceMove3));
		assertFalse(game.isMoveExecutable(allianceMove4));
		assertThrows(NullPointerException.class, () -> game.isMoveExecutable(allianceMove5));
	}
	
	@Test
	public void testIsMoveExecutable_ResearchMoves() {
		Game game = GameCreationUtil.createGame();
		Player player = game.getPlayers().get(0);
		IResourceManager resourceManager = player.getResourceManager();
		resourceManager.setResearchPoints(5);
		
		//valid moves
		IMove researchMoveMines = MoveCreaterUtil.getResearchMove(game, player, ResearchArea.MINES);
		IMove researchMoveEconomy = MoveCreaterUtil.getResearchMove(game, player, ResearchArea.ECONOMY);
		IMove researchMoveWeapon = MoveCreaterUtil.getResearchMove(game, player, ResearchArea.WEAPON);
		
		//invalid moves
		//increase the military state because state 2 is not accessible
		IResearchManager researchManager = player.getResearchManager();
		researchManager.increaseState(ResearchArea.MILITARY);
		IMove researchMoveMilitary = MoveCreaterUtil.getResearchMove(game, player, ResearchArea.MILITARY);
		
		assertTrue(game.isMoveExecutable(researchMoveMines));
		assertTrue(game.isMoveExecutable(researchMoveEconomy));
		assertTrue(game.isMoveExecutable(researchMoveWeapon));
		
		assertFalse(game.isMoveExecutable(researchMoveMilitary));
		
		resourceManager.setResearchPoints(0);
		assertFalse(game.isMoveExecutable(researchMoveMines));
	}
	
	@Test
	public void testIsMoveExecutable_ResearchResourcesMoves() {
		Game game = GameCreationUtil.createGame();
		Player player = game.getPlayers().get(0);
		IResourceManager resourceManager = player.getResourceManager();
		resourceManager.addResources(new ResearchResources(5, 5, 5, 2));
		
		//valid moves
		IMove researchResourcesMove = MoveCreaterUtil.getResearchResourcesMove(game, player, ResearchArea.FTL, new ResearchResources(2, 2, 2, 0));
		
		//invalid moves
		IMove researchResourcesMoveToManyResources = MoveCreaterUtil.getResearchResourcesMove(game, player, ResearchArea.FTL,
				new ResearchResources(5, 5, 5, 0));
		IMove researchResourcesMoveEmpty = MoveCreaterUtil.getResearchResourcesMove(game, player, ResearchArea.MILITARY, new ResearchResources());
		IMove researchResourcesMoveNoResourcesNeeded = MoveCreaterUtil.getResearchResourcesMove(game, player, ResearchArea.MINES,
				new ResearchResources(2, 2, 2, 0));
		
		assertTrue(game.isMoveExecutable(researchResourcesMove));
		
		assertFalse(game.isMoveExecutable(researchResourcesMoveToManyResources));
		assertFalse(game.isMoveExecutable(researchResourcesMoveEmpty));
		assertFalse(game.isMoveExecutable(researchResourcesMoveNoResourcesNeeded));
	}
	
	@Test
	public void testIsMoveExecutable_PassMoves() {
		Game game = GameCreationUtil.createGame();
		Player player = game.getPlayers().get(0);
		Player player2 = game.getPlayers().get(1);
		
		IMove pass = MoveCreaterUtil.getPassMove(game, player);
		IMove pass2 = MoveCreaterUtil.getPassMove(game, player2);
		
		//passing is always executable as long as it's the players turn
		assertTrue(game.isMoveExecutable(pass));
		
		assertFalse(game.isMoveExecutable(pass2));
	}
}
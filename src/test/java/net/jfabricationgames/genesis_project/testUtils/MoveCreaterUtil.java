package net.jfabricationgames.genesis_project.testUtils;

import java.util.Arrays;

import net.jfabricationgames.genesis_project.game.AllianceBonus;
import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.Game;
import net.jfabricationgames.genesis_project.game.Player;
import net.jfabricationgames.genesis_project.game.ResearchArea;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.move.IMove;
import net.jfabricationgames.genesis_project.move.MoveBuilder;
import net.jfabricationgames.genesis_project.move.MoveType;

public class MoveCreaterUtil {
	
	public static IMove getBuildingMove(Game game, Player player, Building building, int x, int y) {
		IMove buildMove;
		MoveBuilder builder = new MoveBuilder(game);
		builder.buildMove();
		builder.setType(MoveType.BUILD);
		builder.setPlayer(player);
		builder.setBuilding(building);
		builder.setField(game.getBoard().getFields().get(new Position(x, y)));
		buildMove = builder.getMove();
		
		return buildMove;
	}
	public static IMove getAllianceMove(Game game, Player player, Field[] planets, Field[] satellites, AllianceBonus bonus) {
		IMove allianceMove;
		MoveBuilder builder = new MoveBuilder(game);
		builder.buildMove();
		builder.setType(MoveType.ALLIANCE);
		builder.setPlayer(player);
		builder.setAlliancePlanets(Arrays.asList(planets));
		builder.setSatelliteFields(Arrays.asList(satellites));
		builder.setAllianceBonus(bonus);
		
		allianceMove = builder.getMove();
		return allianceMove;
	}
	public static IMove getResearchMove(Game game, Player player, ResearchArea area) {
		IMove researchMove;
		MoveBuilder builder = new MoveBuilder(game);
		builder.buildMove();
		builder.setType(MoveType.RESEARCH);
		builder.setPlayer(player);
		builder.setResearchArea(area);
		
		researchMove = builder.getMove();
		return researchMove;
	}
	public static IMove getResearchResourcesMove(Game game, Player player, ResearchArea area, ResearchResources resources) {
		IMove researchMove;
		MoveBuilder builder = new MoveBuilder(game);
		builder.buildMove();
		builder.setType(MoveType.RESEARCH_RESOURCES);
		builder.setPlayer(player);
		builder.setResearchArea(area);
		builder.setResearchResources(resources);
		
		researchMove = builder.getMove();
		return researchMove;
	}
	public static IMove getPassMove(Game game, Player player) {
		IMove researchMove;
		MoveBuilder builder = new MoveBuilder(game);
		builder.buildMove();
		builder.setType(MoveType.PASS);
		builder.setPlayer(player);
		builder.setPass(true);
		
		researchMove = builder.getMove();
		return researchMove;
	}
}
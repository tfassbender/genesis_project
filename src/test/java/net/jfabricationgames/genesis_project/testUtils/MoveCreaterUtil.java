package net.jfabricationgames.genesis_project.testUtils;

import java.util.Arrays;

import net.jfabricationgames.genesis_project.game.AllianceBonus;
import net.jfabricationgames.genesis_project.game.Board;
import net.jfabricationgames.genesis_project.game.Board.Position;
import net.jfabricationgames.genesis_project.game.Building;
import net.jfabricationgames.genesis_project.game.Field;
import net.jfabricationgames.genesis_project.game.ResearchArea;
import net.jfabricationgames.genesis_project.game.ResearchResources;
import net.jfabricationgames.genesis_project.move.IMove;
import net.jfabricationgames.genesis_project.move.MoveBuilder;
import net.jfabricationgames.genesis_project.move.MoveType;

public class MoveCreaterUtil {
	
	public static IMove getBuildingMove(Board board, String player, Building building, int x, int y) {
		IMove buildMove;
		MoveBuilder builder = new MoveBuilder();
		builder.setType(MoveType.BUILD);
		builder.setPlayer(player);
		builder.setBuilding(building);
		builder.setField(board.getFields().get(new Position(x, y)));
		buildMove = builder.build();
		
		return buildMove;
	}
	public static IMove getAllianceMove(String player, Field[] planets, Field[] satellites, AllianceBonus bonus, int bonusIndex) {
		IMove allianceMove;
		MoveBuilder builder = new MoveBuilder();
		builder.setType(MoveType.ALLIANCE);
		builder.setPlayer(player);
		builder.setAlliancePlanets(Arrays.asList(planets));
		builder.setSatelliteFields(Arrays.asList(satellites));
		builder.setAllianceBonus(bonus);
		builder.setAllianceBonusIndex(bonusIndex);
		
		allianceMove = builder.build();
		return allianceMove;
	}
	public static IMove getResearchMove(String player, ResearchArea area) {
		IMove researchMove;
		MoveBuilder builder = new MoveBuilder();
		builder.setType(MoveType.RESEARCH);
		builder.setPlayer(player);
		builder.setResearchArea(area);
		
		researchMove = builder.build();
		return researchMove;
	}
	public static IMove getResearchResourcesMove(String player, ResearchArea area, ResearchResources resources) {
		IMove researchMove;
		MoveBuilder builder = new MoveBuilder();
		builder.setType(MoveType.RESEARCH_RESOURCES);
		builder.setPlayer(player);
		builder.setResearchArea(area);
		builder.setResearchResources(resources);
		
		researchMove = builder.build();
		return researchMove;
	}
	public static IMove getPassMove(String player) {
		IMove researchMove;
		MoveBuilder builder = new MoveBuilder();
		builder.setType(MoveType.PASS);
		builder.setPlayer(player);
		
		researchMove = builder.build();
		return researchMove;
	}
}
package net.jfabricationgames.genesis_project.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.jfabricationgames.genesis_project.game_frame.GameFrameController;
import net.jfabricationgames.genesis_project.game_frame.PlayerInfo;
import net.jfabricationgames.genesis_project.json.serializer.SerializationIdGenerator;
import net.jfabricationgames.genesis_project.manager.AllianceManagerCompositum;
import net.jfabricationgames.genesis_project.manager.GamePointManager;
import net.jfabricationgames.genesis_project.manager.IAllianceManager;
import net.jfabricationgames.genesis_project.manager.IBuildingManager;
import net.jfabricationgames.genesis_project.manager.IResearchManager;
import net.jfabricationgames.genesis_project.manager.IResourceManager;
import net.jfabricationgames.genesis_project.manager.ITurnManager;
import net.jfabricationgames.genesis_project.manager.ResearchManagerCompositum;
import net.jfabricationgames.genesis_project.manager.TurnManager;
import net.jfabricationgames.genesis_project.move.IMove;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Game {
	
	//final id for json serialization
	private final int id = SerializationIdGenerator.getNextId();
	
	private List<Player> players;
	private transient String localPlayerName;
	
	private Board board;
	
	private ITurnManager turnManager;
	private IResearchManager researchManager;
	private IAllianceManager allianceManager;
	private GamePointManager pointManager;
	
	private ObservableList<PlayerInfo> playerInfoList;
	
	@JsonIgnore
	private GameFrameController gameFrameController;
	
	/**
	 * DO NOT USE - empty constructor for json deserialization
	 */
	@Deprecated
	public Game() {
		
	}
	
	public Game(List<Player> players, String localPlayerName) {
		this.players = players;
		this.localPlayerName = localPlayerName;
		this.board = new Board();
		this.turnManager = new TurnManager(this);
		this.researchManager = new ResearchManagerCompositum(this);
		this.allianceManager = new AllianceManagerCompositum(this);
		this.pointManager = new GamePointManager(this);
		for (Player player : players) {
			player.setGame(this);
		}
		playerInfoList = FXCollections.observableArrayList(players.stream().map(p -> new PlayerInfo(p)).collect(Collectors.toList()));
	}
	
	public void collectGameStartResources() {
		for (Player player : players) {
			player.getResourceManager().collectGameStartResources();
		}
	}
	public void collectTurnStartResources() {
		for (Player player : players) {
			player.getResourceManager().collectTurnStartResources();
		}
	}
	
	/**
	 * Executes a move without checking for it's validity. When the move can't be executed a RuntimeException will occur.
	 */
	public void executeMove(IMove move) {
		Objects.requireNonNull(move, "Can't execute a move that is null");
		
		Player player;
		ResearchArea area;
		IResourceManager localResourceManager;
		IResearchManager localResearchManager;
		
		if (!turnManager.isPlayersTurn(move.getPlayer())) {
			throw new IllegalArgumentException(
					"It's not the players turn (move from player: " + move.getPlayer() + "; current player: " + turnManager.getActivePlayer() + ")");
		}
		
		switch (move.getType()) {
			case BUILD:
				Building building = move.getBuilding();
				Field field = move.getField();
				IBuildingManager buildingManager = move.getPlayer().getBuildingManager();
				buildingManager.build(building, field);
				turnManager.nextMove();
				break;
			case ALLIANCE:
				List<Field> planets = move.getAlliancePlanets();
				List<Field> satellites = move.getSatelliteFields();
				AllianceBonus bonus = move.getAllianceBonus();
				int bonusIndex = move.getAllianceBonusIndex();
				
				IAllianceManager allianceManager = move.getPlayer().getAllianceManager();
				allianceManager.addAlliance(planets, satellites, bonus, bonusIndex);
				turnManager.nextMove();
				break;
			case RESEARCH:
				area = move.getResearchArea();
				
				//pay here because not everyone pays for a WEAPON increase but everyone gets the increase
				int currentState = move.getPlayer().getResearchManager().getState(area);
				int nextState = currentState + 1;
				
				player = move.getPlayer();
				localResearchManager = player.getResearchManager();
				localResourceManager = player.getResourceManager();
				int researchPointsNeeded = Constants.getInstance().RESEARCH_POINTS_FOR_STATE_INCREASE;
				int researchScientistsNeeded = Constants.getInstance().RESEARCH_SCIENTISTS_FOR_LOW_STATE;
				if (nextState >= Constants.getInstance().RESEARCH_STATE_HIGH) {
					researchScientistsNeeded = Constants.getInstance().RESEARCH_SCIENTISTS_FOR_HIGH_STATE;
				}
				
				localResourceManager.reduceResources(Resource.RESEARCH_POINTS, researchPointsNeeded);
				localResourceManager.reduceResources(Resource.SCIENTISTS, researchScientistsNeeded);
				
				if (area == ResearchArea.WEAPON) {
					//WEAPON upgrades are executed on the global IResearchManager (composite)
					this.researchManager.increaseState(area);
				}
				else {
					//other ResearchAreas are executed locally on the player's IResearchManager
					localResearchManager.increaseState(area);
				}
				turnManager.nextMove();
				break;
			case RESEARCH_RESOURCES:
				ResearchResources resources = move.getResearchResources();
				area = move.getResearchArea();
				
				//pay here because resources are added on the global manager that has no player references
				player = move.getPlayer();
				localResourceManager = player.getResourceManager();
				localResourceManager.reduceResources(resources);
				
				this.researchManager.addResearchResources(resources, area);
				break;
			case PASS:
				player = move.getPlayer();
				turnManager.playerPassed(player);
				turnManager.nextMove();
				break;
			default:
				throw new IllegalArgumentException("The MoveType " + move.getType() + " is unknown.");
		}
		
		//receive turn points for a move
		turnManager.receivePointsForMove(move);
		
		updateBoard();
		updatePlayerInfo();
	}
	
	/**
	 * Check whether a given move would be valid and could be executed.
	 */
	public boolean isMoveExecutable(IMove move) {
		Objects.requireNonNull(move, "Can't test a move that is null");
		
		boolean moveExecutable = true;
		
		//it has to be the players turn for every move
		moveExecutable &= turnManager.isPlayersTurn(move.getPlayer());
		
		ResearchArea area;
		Player player;
		IResourceManager localResourceManager;
		switch (move.getType()) {
			case BUILD:
				//the building has to be valid and the resources for the building have to be there
				Field field = move.getField();
				Building building = move.getBuilding();
				
				//the IBuildingManager checks for the resources available
				moveExecutable &= move.getPlayer().getBuildingManager().canBuild(building, field);
				break;
			case ALLIANCE:
				//enough buildings, government or city included, planets, opponent's buildings, none already in an alliance of this player
				player = move.getPlayer();
				IAllianceManager allianceManager = player.getAllianceManager();
				List<Field> planets = move.getAlliancePlanets();
				List<Field> satellites = move.getSatelliteFields();
				AllianceBonus bonus = move.getAllianceBonus();
				int bonusIndex = move.getAllianceBonusIndex();
				
				moveExecutable &= allianceManager.isAllianceValid(planets, satellites, bonus, bonusIndex);
				break;
			case RESEARCH:
				//the research step has to be accessible and the player has to have the needed research points
				area = move.getResearchArea();
				int currentState = move.getPlayer().getResearchManager().getState(area);
				int nextState = currentState + 1;
				
				localResourceManager = move.getPlayer().getResourceManager();
				int researchPointsNeeded = Constants.getInstance().RESEARCH_POINTS_FOR_STATE_INCREASE;
				int researchScientistsNeeded = Constants.getInstance().RESEARCH_SCIENTISTS_FOR_LOW_STATE;
				if (nextState >= Constants.getInstance().RESEARCH_STATE_HIGH) {
					researchScientistsNeeded = Constants.getInstance().RESEARCH_SCIENTISTS_FOR_HIGH_STATE;
				}
				
				boolean stateAccessible = this.researchManager.isStateAccessible(area, nextState);
				boolean resourcesAvialable = localResourceManager.isResourceAvailable(Resource.RESEARCH_POINTS, researchPointsNeeded);
				resourcesAvialable &= localResourceManager.isResourceAvailable(Resource.SCIENTISTS, researchScientistsNeeded);
				
				moveExecutable &= stateAccessible & resourcesAvialable;
				break;
			case RESEARCH_RESOURCES:
				//the player has to have the resources and the resources have to be needed
				ResearchResources resourcesAdded = move.getResearchResources();
				
				player = move.getPlayer();
				moveExecutable &= player.getResourceManager().isResourcesAvailable(resourcesAdded);
				moveExecutable &= !move.getResearchResources().isEmpty();
				
				area = move.getResearchArea();
				int nextResoucesNeedingState = this.researchManager.getNextResourceNeedingState(area);
				
				if (nextResoucesNeedingState != -1) {
					ResearchResources neededLeft = this.researchManager.getResearchResourcesNeededLeft(area, nextResoucesNeedingState);
					
					for (Resource resource : ResearchResources.RESEARCH_RESOURCES) {
						moveExecutable &= neededLeft.getResources(resource) >= resourcesAdded.getResources(resource);
					}
				}
				else {
					//the state needs no resources because all states are accessible
					moveExecutable = false;
				}
				break;
			case PASS:
				//as long as it's the players turn it's always possible to pass
				break;
			default:
				throw new IllegalArgumentException("The MoveType " + move.getType() + " is unknown.");
		}
		
		return moveExecutable;
	}
	
	private void updatePlayerInfo() {
		List<PlayerInfo> newPlayerInfo = players.stream().map(p -> new PlayerInfo(p)).collect(Collectors.toList());
		playerInfoList.clear();
		playerInfoList.addAll(newPlayerInfo);
	}
	
	/**
	 * Update the displayed board to show new buildings, alliances, ...
	 */
	private void updateBoard() {
		if (gameFrameController != null) {
			//only if the controller is already set (will not be set in tests)
			gameFrameController.getBoardPaneController().buildField();
		}
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	public Player getLocalPlayer() {
		Optional<Player> local = players.stream().filter(p -> p.getUser().getUsername().equals(localPlayerName)).findFirst();
		return local.orElseThrow(() -> new IllegalStateException("No local player found."));
	}
	
	public ObservableList<PlayerInfo> getPlayerInfoList() {
		return playerInfoList;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public ITurnManager getTurnManager() {
		return turnManager;
	}
	public IResearchManager getResearchManager() {
		return researchManager;
	}
	public IAllianceManager getAllianceManager() {
		return allianceManager;
	}
	public GamePointManager getPointManager() {
		return pointManager;
	}
	
	public GameFrameController getGameFrameController() {
		return gameFrameController;
	}
	public void setGameFrameController(GameFrameController gameFrameController) {
		this.gameFrameController = gameFrameController;
	}
	
	public int getId() {
		return id;
	}
	
	@JsonGetter("playerInfoList")
	public List<PlayerInfo> getPlayerInfoListAsArrayList() {
		return new ArrayList<PlayerInfo>(playerInfoList);
	}
	@JsonSetter("playerInfoList")
	public void setPlayerInfoListFromList(List<PlayerInfo> playerInfoList) {
		this.playerInfoList = FXCollections.observableArrayList(playerInfoList);
	}
}
package net.jfabricationgames.genesis_project.game;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Supplier;

import net.jfabricationgames.genesis_project.game_frame.util.GuiUtils;

public enum Building {
	
	COLONY(() -> Constants.getInstance().BUILDING_COSTS_COLONIE, null, "colonies/colonie_"),//
	MINE(() -> Constants.getInstance().BUILDING_COSTS_MINE, COLONY, "mines/mine_"),//
	TRADING_POST(() -> Constants.getInstance().BUILDING_COSTS_TRAIDING_POST, COLONY, "trading_posts/trading_post_"),//
	LABORATORY(() -> Constants.getInstance().BUILDING_COSTS_LABORATORY, COLONY, "laboratories/laboratory_"),//
	GOVERNMENT(() -> Constants.getInstance().BUILDING_COSTS_GOVERNMENT, TRADING_POST, "goverments/goverment_"),//
	CITY(() -> Constants.getInstance().BUILDING_COSTS_CITY, TRADING_POST, "cities/city_"),//
	RESEARCH_CENTER(() -> Constants.getInstance().BUILDING_COSTS_RESEARCH_CENTER, LABORATORY, "research_centers/research_center_"),//
	DRONE(() -> Constants.getInstance().BUILDING_COSTS_DRONE, null, "drones/drone_"),//
	SPACE_STATION(() -> Constants.getInstance().BUILDING_COSTS_SPACE_STATION, DRONE, "space_stations/space_station_"),//
	SATELLITE(() -> Constants.getInstance().BUILDING_COSTS_SATELLITE, null, "satellites/satellite_");//
	
	private final Supplier<int[][]> costs;
	
	//the building that can be upgraded to this building
	private final Building previousBuilding;
	
	//private final Map<PlayerColor, Image> images;
	private final Map<PlayerColor, String> imagePathes;
	
	private Building(Supplier<int[][]> costs, Building previouseBuilding, String imagePath) {
		this.costs = costs;
		this.previousBuilding = previouseBuilding;
		//this.images = new HashMap<PlayerColor, Image>((int) (PlayerColor.values().length * 1.3));
		this.imagePathes = new HashMap<PlayerColor, String>((int) (PlayerColor.values().length * 1.3));
		if (imagePath != null) {
			for (PlayerColor color : PlayerColor.values()) {
				String completePath = "net/jfabricationgames/genesis_project/images/buildings/" + imagePath + color.name().toLowerCase() + ".png";
				imagePathes.put(color, completePath);
				
				//TODO images can't be loaded for unknown reasons
				//Image image = new Image(completePath);
				//images.put(color, image);
			}
		}
	}
	
	public int[][] getCosts() {
		return costs.get();
	}
	
	public Building getPreviousBuilding() {
		return previousBuilding;
	}
	
	/*public Map<PlayerColor, Image> getImages() {
		return images;
	}*/
	public Map<PlayerColor, String> getImagePathes() {
		return imagePathes;
	}
	
	public boolean isSpaceBuilding() {
		switch (this) {
			case COLONY:
			case MINE:
			case TRADING_POST:
			case GOVERNMENT:
			case CITY:
			case LABORATORY:
			case RESEARCH_CENTER:
				return false;
			case DRONE:
			case SPACE_STATION:
			case SATELLITE:
				return true;
			default:
				throw new IllegalArgumentException("Unknown building type: " + this.name());
		}
	}
	
	public String getName() {
		switch (this) {
			case RESEARCH_CENTER:
				return "Research Center";
			case SPACE_STATION:
				return "Space Station";
			case TRADING_POST:
				return "Trainding Post";
			default:
				return GuiUtils.toLeadingCapitalLetter(name());
		}
	}
}
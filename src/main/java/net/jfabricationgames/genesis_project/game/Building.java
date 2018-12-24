package net.jfabricationgames.genesis_project.game;

import java.util.HashMap;
import java.util.Map;

public enum Building {
	
	COLONY(Constants.BUILDING_COSTS_COLONIE, null, "colonies/colonie_"),//
	MINE(Constants.BUILDING_COSTS_MINE, COLONY, "mines/mine_"),//
	TRAIDING_POST(Constants.BUILDING_COSTS_TRAIDING_POST, COLONY, "traiding_posts/traiding_post_"),//
	LABORATORY(Constants.BUILDING_COSTS_LABORATORY, COLONY, "laboratories/laboratory_"),//
	GOVERMENT(Constants.BUILDING_COSTS_GOVERNMENT, TRAIDING_POST, "goverments/goverment_"),//
	CITY(Constants.BUILDING_COSTS_CITY, TRAIDING_POST, "cities/city_"),//
	RESEARCH_CENTER(Constants.BUILDING_COSTS_RESEARCH_CENTER, LABORATORY, "research_centers/research_center_"),//
	DRONE(Constants.BUILDING_COSTS_DRONE, null, "drones/drone_"),//
	SPACE_STATION(Constants.BUILDING_COSTS_SPACE_STATION, DRONE, "space_stations/space_station_"),//
	SATELLITE(Constants.BUILDING_COSTS_SATELLITE, null, null);//TODO add satellite images
	
	private final int[][] costs;
	
	//the building that can be upgraded to this building
	private final Building previousBuilding;
	
	//private final Map<PlayerColor, Image> images;
	private final Map<PlayerColor, String> imagePathes;
	
	private Building(int[][] costs, Building previouseBuilding, String imagePath) {
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
		return costs;
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
			case TRAIDING_POST:
			case GOVERMENT:
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
}
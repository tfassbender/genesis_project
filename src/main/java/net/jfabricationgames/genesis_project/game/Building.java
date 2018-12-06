package net.jfabricationgames.genesis_project.game;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public enum Building {
	
	COLONIE(Constants.BUILDING_COSTS_COLONIE, "colonies/colonie_"),
	MINE(Constants.BUILDING_COSTS_MINE, "mines/mine_"),
	TRAIDING_POST(Constants.BUILDING_COSTS_TRAIDING_POST, "traiding_posts/traiding_post_"),
	LABORATORY(Constants.BUILDING_COSTS_LABORATORY, "laboratories/laboratory_"),
	GOVERMENT(Constants.BUILDING_COSTS_GOVERMENT, "goverments/goverment_"),
	CITY(Constants.BUILDING_COSTS_CITY, "cities/city_"),
	RESEARCH_CENTER(Constants.BUILDING_COSTS_RESEARCH_CENTER, "research_centers/research_center_"),
	DRONE(Constants.BUILDING_COSTS_DRONE, "drones/drone_"),
	SPACE_STATION(Constants.BUILDING_COSTS_SPACE_STATION, "space_stations/space_station_"),
	SATELLITE(Constants.BUILDING_COSTS_SATELLITE, null);//TODO add satellite images
	
	private final int[][] costs;
	
	private final Map<PlayerColor, Image> images;
	
	private Building(int[][] costs, String imagePath) {
		this.costs = costs;
		this.images = new HashMap<PlayerColor, Image>((int) (PlayerColor.values().length * 1.3));
		if (imagePath != null) {
			for (PlayerColor color : PlayerColor.values()) {
				Image image = new Image("net/jfabricationgames/genesis_project/images/buildings/" + imagePath + color.name().toLowerCase() + ".png");
				images.put(color, image);
			}
		}
	}
	
	public int[][] getCosts() {
		return costs;
	}
}
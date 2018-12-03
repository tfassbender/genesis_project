package net.jfabricationgames.genesis_project.game_frame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class GuiUtils {
	
	private static final Logger LOGGER = LogManager.getLogger(GuiUtils.class);
	
	public static void loadImageToView(String imagePath, boolean addPathPrefix, ImageView imageView) {
		try {
			if (addPathPrefix) {
				imagePath = "net/jfabricationgames/genesis_project/images/" + imagePath;
			}
	        Image image = new Image(imagePath);
	        imageView.setImage(image);
	        imageView.setCache(true);
	    }
		catch (IllegalArgumentException | IllegalStateException e) {
	        LOGGER.warn("Could not load image for GUI element.", e);
	    }
	}
}
package net.jfabricationgames.genesis_project.game_frame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class GuiUtils {
	
	private static final Logger LOGGER = LogManager.getLogger(GuiUtils.class);
	
	public static final String DEFAULT_PATH_PREFIX = "net/jfabricationgames/genesis_project/images/";
	
	public static void loadImageToView(String imagePath, boolean addPathPrefix, ImageView imageView) {
		try {
			if (addPathPrefix) {
				imagePath = DEFAULT_PATH_PREFIX + imagePath;
			}
	        Image image = new Image(imagePath);
	        imageView.setImage(image);
	        imageView.setCache(true);
	    }
		catch (IllegalArgumentException | IllegalStateException e) {
	        LOGGER.warn("Could not load image for GUI element.", e);
	    }
	}
	
	public static Image loadImage(String imagePath, boolean addPathPrefix) {
		try {
			if (addPathPrefix) {
				imagePath = DEFAULT_PATH_PREFIX + imagePath;
			}
	        Image image = new Image(imagePath);
	        return image;
	    }
		catch (IllegalArgumentException | IllegalStateException e) {
	        LOGGER.warn("Could not load image for GUI element.", e);
	        throw e;
	    }
	}
	
	public static String toLeadingCapitalLetter(String text) {
		text = text.toLowerCase();
		char firstChar = text.charAt(0);
		firstChar = Character.toUpperCase(firstChar);
		text = firstChar + text.substring(1);
		return text;
	}
}
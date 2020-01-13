package net.jfabricationgames.genesis_project.game_frame.util;

import java.io.IOException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GuiUtils {
	
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
			LOGGER.warn("Could not load image for GUI element: " + imagePath, e);
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
			LOGGER.warn("Could not load image for GUI element: " + imagePath, e);
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
	
	/**
	 * Load an fxml pane into an anchor pane. Note that the filename must be relative to this class (GuiUtils)
	 */
	public void insertPane(String fxmlFileName, AnchorPane parent, Initializable controller, String cssFileName) {
		insertPane(fxmlFileName, parent, controller, cssFileName, this);
	}
	/**
	 * Load an fxml pane into an anchor pane with a loader object (any object) for the relative path
	 */
	public void insertPane(String fxmlFileName, AnchorPane parent, Initializable controller, String cssFileName, Object loader) {
		try {
			URL fxmlUrl = loader.getClass().getResource(fxmlFileName);
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
			fxmlLoader.setController(controller);
			Parent pane = fxmlLoader.load();
			if (cssFileName != null) {
				pane.getStylesheets().add(getClass().getResource(cssFileName).toExternalForm());
			}
			parent.getChildren().add(pane);
			AnchorPane.setBottomAnchor(pane, 0d);
			AnchorPane.setTopAnchor(pane, 0d);
			AnchorPane.setLeftAnchor(pane, 0d);
			AnchorPane.setRightAnchor(pane, 0d);
		}
		catch (IOException ioe) {
			LOGGER.error("An exception occured while inserting a pane: " + fxmlFileName, ioe);
		}
	}
	
	public Parent loadPane(String fxmlFileName, Initializable controller, String cssFileName, Object loader) {
		try {
			URL fxmlUrl = loader.getClass().getResource(fxmlFileName);
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
			fxmlLoader.setController(controller);
			Parent pane = fxmlLoader.load();
			if (cssFileName != null) {
				pane.getStylesheets().add(getClass().getResource(cssFileName).toExternalForm());
			}
			return pane;
		}
		catch (IOException ioe) {
			LOGGER.error("An exception occured while loading the pane: " + fxmlFileName, ioe);
			return null;
		}
	}
}
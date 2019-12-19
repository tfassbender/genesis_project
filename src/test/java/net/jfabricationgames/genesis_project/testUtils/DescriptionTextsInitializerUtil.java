package net.jfabricationgames.genesis_project.testUtils;

import java.util.HashMap;

import net.jfabricationgames.genesis_project.game.DescriptionTexts;
import net.jfabricationgames.genesis_project.game.PlayerClass;

public class DescriptionTextsInitializerUtil {
	
	public static void initializeDescriptionTexts() {
		DescriptionTexts descriptionTexts = DescriptionTexts.getInstance();
		descriptionTexts.CLASS_EFFECT_NAMES = new HashMap<PlayerClass, String>();
		descriptionTexts.CLASS_EFFECT_DESCRIPTIONS = new HashMap<PlayerClass, String>();
		descriptionTexts.GOVERNMENT_EFFECT_NAMES = new HashMap<PlayerClass, String>();
		descriptionTexts.GOVERNMENT_EFFECT_DESCRIPTIONS = new HashMap<PlayerClass, String>();
		
		for (PlayerClass playerClass : PlayerClass.values()) {
			descriptionTexts.CLASS_EFFECT_NAMES.put(playerClass, "");
			descriptionTexts.CLASS_EFFECT_DESCRIPTIONS.put(playerClass, "");
			descriptionTexts.GOVERNMENT_EFFECT_NAMES.put(playerClass, "");
			descriptionTexts.GOVERNMENT_EFFECT_DESCRIPTIONS.put(playerClass, "");
		}
	}
}
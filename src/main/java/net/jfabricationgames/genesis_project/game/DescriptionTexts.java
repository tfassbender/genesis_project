package net.jfabricationgames.genesis_project.game;

import java.util.Map;

public class DescriptionTexts {
	
	private static DescriptionTexts instance;
	
	/**
	 * DO NOT USE - empty constructor for json deserialization
	 */
	@Deprecated
	public DescriptionTexts() {
		
	}
	
	public static synchronized DescriptionTexts getInstance() {
		if (instance == null) {
			instance = new DescriptionTexts();
		}
		return instance;
	}
	
	public Map<PlayerClass, String> CLASS_EFFECT_NAMES;
	public Map<PlayerClass, String> CLASS_EFFECT_DESCRIPTIONS;
	public Map<PlayerClass, String> GOVERNMENT_EFFECT_NAMES;
	public Map<PlayerClass, String> GOVERNMENT_EFFECT_DESCRIPTIONS;
}
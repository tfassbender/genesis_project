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
			throw new IllegalStateException("DescriptionTexts were not initialized yet (need to be loaded from server)");
		}
		return instance;
	}
	
	public Map<PlayerClass, String> CLASS_EFFECT_NAMES;
	public Map<PlayerClass, String> CLASS_EFFECT_DESCRIPTIONS;
	public Map<PlayerClass, String> GOVERNMENT_EFFECT_NAMES;
	public Map<PlayerClass, String> GOVERNMENT_EFFECT_DESCRIPTIONS;
	
	public Map<ResearchArea, Map<Integer, String>> RESEARCH_STATE_DESCRIPTIONS;
	
	public String ERROR_TEXT_GENESIS_SERVER_EXCEPTION;
	public String ERROR_TEXT_MOVE_EXECUTION;
	
	public static void setDescriptionTexts(DescriptionTexts descriptionTexts) {
		instance = descriptionTexts;
	}
}
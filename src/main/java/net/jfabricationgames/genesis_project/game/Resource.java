package net.jfabricationgames.genesis_project.game;

import net.jfabricationgames.genesis_project.game_frame.util.GuiUtils;

public enum Resource {
	
	CARBON,
	SILICIUM,
	IRON,
	RESEARCH_POINTS,
	SCIENTISTS,
	FTL;
	
	public String getName() {
		switch (this) {
			case FTL:
				return "FTL";
			case RESEARCH_POINTS:
				return "Research Points";
			default:
				return GuiUtils.toLeadingCapitalLetter(name());
		}
	}
}
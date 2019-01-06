package net.jfabricationgames.genesis_project.game;

public class CompleteResources extends ResearchResources implements Cloneable {
	
	public static final Resource[] COMPLETE_RESOURCES = new Resource[] {Resource.CARBON, Resource.SILICIUM, Resource.IRON, Resource.SCIENTISTS,
			Resource.RESEARCH_POINTS, Resource.FTL};
	
	private int researchPoints;
	private int ftl;
	
	public CompleteResources(int resourceC, int resourceSi, int resourceFe, int scientists, int researchPoints, int ftl) {
		super(resourceC, resourceSi, resourceFe, scientists);
		this.researchPoints = researchPoints;
		this.ftl = ftl;
	}
	public CompleteResources() {
		super();
		this.researchPoints = 0;
		this.ftl = 0;
	}
	
	@Override
	public boolean isEmpty() {
		return super.isEmpty() && researchPoints == 0 && ftl == 0;
	}
	@Override
	public String toString() {
		return "CompleteResources[C: " + getResourcesC() + "; Si: " + getResourcesSi() + "; Fe:" + getResourcesFe() + "; Scient.:" + getScientists()
				+ "; Res. Points: " + getResearchPoints() + "; FTL: " + getFTL() + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ResearchResources) {
			ResearchResources res = (ResearchResources) obj;
			return this.getResourcesC() == res.getResourcesC() && this.getResourcesSi() == res.getResourcesSi()
					&& this.getResourcesFe() == res.getResourcesFe() && this.getScientists() == res.getScientists();
		}
		else {
			return super.equals(obj);
		}
	}
	
	@Override
	public CompleteResources clone() {
		return new CompleteResources(getResourcesC(), getResourcesSi(), getResourcesFe(), getScientists(), researchPoints, ftl);
	}
	
	public int getResearchPoints() {
		return researchPoints;
	}
	public void setResearchPoints(int researchPoints) {
		this.researchPoints = researchPoints;
	}
	public void addResearchPoints(int researchPoints) {
		this.researchPoints += researchPoints;
	}
	
	public int getFTL() {
		return ftl;
	}
	public void setFTL(int ftl) {
		this.ftl = ftl;
	}
	public void addFTL(int ftl) {
		this.ftl += ftl;
	}
	
	@Override
	public int getResources(Resource resource) {
		switch (resource) {
			case CARBON:
			case IRON:
			case SILICIUM:
			case SCIENTISTS:
				return super.getResources(resource);
			case RESEARCH_POINTS:
				return getResearchPoints();
			case FTL:
				return getFTL();
			default:
				throw new IllegalArgumentException("Unknown resource type: " + resource);
		}
	}
	@Override
	public void setResources(Resource resource, int resources) {
		switch (resource) {
			case CARBON:
			case IRON:
			case SILICIUM:
			case SCIENTISTS:
				super.setResources(resource, resources);
				break;
			case RESEARCH_POINTS:
				setResearchPoints(resources);
				break;
			case FTL:
				setFTL(resources);
				break;
			default:
				throw new IllegalArgumentException("Unknown resource type: " + resource);
		}
	}
	@Override
	public void addResources(Resource resource, int resources) {
		switch (resource) {
			case CARBON:
			case IRON:
			case SILICIUM:
			case SCIENTISTS:
				super.addResources(resource, resources);
				break;
			case RESEARCH_POINTS:
				addResearchPoints(resources);
				break;
			case FTL:
				addFTL(resources);
				break;
			default:
				throw new IllegalArgumentException("Unknown resource type: " + resource);
		}
	}
	
	public void addResources(CompleteResources resources) {
		for (Resource res : COMPLETE_RESOURCES) {
			addResources(res, resources.getResources(res));
		}
	}
	@Override
	public String getShortenedDescription() {
		return "[C/Si/Fe/Ek/FP/FTL]: " + getResourcesC() + "/" + getResourcesSi() + "/" + getResourcesFe() + "/" + getScientists() + "/"
				+ getResearchPoints() + "/" + getFTL();
	}
}
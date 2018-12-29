package net.jfabricationgames.genesis_project.game;

public class ResearchResources extends BuildingResources implements Cloneable {
	
	private int scientists;
	
	public static final Resource[] RESEARCH_RESOURCES = new Resource[] {Resource.CARBON, Resource.SILICIUM, Resource.IRON, Resource.SCIENTISTS};
	
	public ResearchResources(int resourceC, int resourceSi, int resourceFe, int scientists) {
		super(resourceC, resourceSi, resourceFe);
		this.scientists = scientists;
	}
	public ResearchResources() {
		super();
		scientists = 0;
	}
	
	@Override
	public boolean isEmpty() {
		return super.isEmpty() && scientists == 0;
	}
	@Override
	public String toString() {
		return "ResearchResources[C: " + getResourcesC() + "; Si: " + getResourcesSi() + "; Fe:" + getResourcesFe() + "; Scient.:" + getScientists()
				+ "]";
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
	public ResearchResources clone() {
		return new ResearchResources(getResourcesC(), getResourcesSi(), getResourcesFe(), scientists);
	}
	
	public int getScientists() {
		return scientists;
	}
	public void setScientists(int scientists) {
		this.scientists = scientists;
	}
	public void addScientists(int scientists) {
		this.scientists += scientists;
	}
	
	@Override
	public int getResources(Resource resource) {
		switch (resource) {
			case CARBON:
			case IRON:
			case SILICIUM:
				return super.getResources(resource);
			case SCIENTISTS:
				return scientists;
			default:
				throw new IllegalArgumentException("Research resources are only CARBON, IRON, SILICIUM and SCIENTISTS. Not " + resource);
		}
	}
	@Override
	public void setResources(Resource resource, int resources) {
		switch (resource) {
			case CARBON:
			case IRON:
			case SILICIUM:
				super.setResources(resource, resources);
				break;
			case SCIENTISTS:
				setScientists(resources);
				break;
			default:
				throw new IllegalArgumentException("Research resources are only CARBON, IRON, SILICIUM and SCIENTISTS. Not " + resource);
		}
	}
	@Override
	public void addResources(Resource resource, int resources) {
		switch (resource) {
			case CARBON:
			case IRON:
			case SILICIUM:
				super.addResources(resource, resources);
				break;
			case SCIENTISTS:
				addScientists(resources);
				break;
			default:
				throw new IllegalArgumentException("Research resources are only CARBON, IRON, SILICIUM and SCIENTISTS. Not " + resource);
		}
	}
	
	public void addResources(ResearchResources resources) {
		for (Resource res : RESEARCH_RESOURCES) {
			addResources(res, resources.getResources(res));
		}
	}
	public String getShortenedDescription() {
		return "[C/Si/Fe/Ek]: " + getResourcesC() + "/" + getResourcesSi() + "/" + getResourcesFe() + "/" + getScientists();
	}
}
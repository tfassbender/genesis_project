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
			default:
				throw new IllegalArgumentException("Research resources are only CARBON, IRON, SILICIUM and SCIENTISTS. Not " + resource);
		}
	}
}
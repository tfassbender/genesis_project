package net.jfabricationgames.genesis_project.game;

public class BuildingResources {
	
	private int resourcesC;
	private int resourcesSi;
	private int resourcesFe;
	
	
	public BuildingResources() {
		resourcesC = 0;
		resourcesSi = 0;
		resourcesFe = 0;
	}
	public BuildingResources(int resourcesC, int resourcesSi, int resourcesFe) {
		this.resourcesC = resourcesC;
		this.resourcesSi = resourcesSi;
		this.resourcesFe = resourcesFe;
	}
	
	public int getResourcesC() {
		return resourcesC;
	}
	public void setResourcesC(int resourcesC) {
		this.resourcesC = resourcesC;
	}
	public void addResourcesC(int resourcesC) {
		this.resourcesC += resourcesC;
	}
	
	public int getResourcesSi() {
		return resourcesSi;
	}
	public void setResourcesSi(int resourcesSi) {
		this.resourcesSi = resourcesSi;
	}
	public void addResourcesSi(int resourcesSi) {
		this.resourcesSi += resourcesSi;
	}
	
	public int getResourcesFe() {
		return resourcesFe;
	}
	public void setResourcesFe(int resourcesFe) {
		this.resourcesFe = resourcesFe;
	}
	public void addResourcesFe(int resourcesFe) {
		this.resourcesFe += resourcesFe;
	}
	
	public int getResourcesPrimary(PlayerClass clazz) {
		return getResources(clazz.getPrimaryResource());
	}
	public void setResourcesPrimary(PlayerClass clazz, int resources) {
		setResources(clazz.getPrimaryResource(), resources);
	}
	public void addResourcesPrimary(PlayerClass clazz, int resources) {
		addResources(clazz.getPrimaryResource(), resources);
	}
	
	public int getResourcesSecundary(PlayerClass clazz) {
		return getResources(clazz.getSecundaryResource());
	}
	public void setResourcesSecundary(PlayerClass clazz, int resources) {
		setResources(clazz.getSecundaryResource(), resources);
	}
	public void addResourcesSecundary(PlayerClass clazz, int resources) {
		addResources(clazz.getSecundaryResource(), resources);
	}
	
	public int getResourcesTertiary(PlayerClass clazz) {
		return getResources(clazz.getTertiaryResource());
	}
	public void setResourcesTertiary(PlayerClass clazz, int resources) {
		setResources(clazz.getTertiaryResource(), resources);
	}
	public void addResourcesTertiary(PlayerClass clazz, int resources) {
		addResources(clazz.getTertiaryResource(), resources);
	}
	
	public int getResources(Resource resource) {
		switch (resource) {
			case CARBON:
				return getResourcesC();
			case IRON:
				return getResourcesFe();
			case SILICIUM:
				return getResourcesSi();
			default:
				throw new IllegalArgumentException("Building resources are only CARBON, IRON, SILICIUM. Not " + resource);
		}
	}
	public void setResources(Resource resource, int resources) {
		switch (resource) {
			case CARBON:
				setResourcesC(resources);
				break;
			case IRON:
				setResourcesFe(resources);
				break;
			case SILICIUM:
				setResourcesSi(resources);
				break;
			default:
				throw new IllegalArgumentException("Building resources are only CARBON, IRON, SILICIUM. Not " + resource);
		}
	}
	public void addResources(Resource resource, int resources) {
		switch (resource) {
			case CARBON:
				addResourcesC(resources);
				break;
			case IRON:
				addResourcesFe(resources);
				break;
			case SILICIUM:
				addResourcesSi(resources);
				break;
			default:
				throw new IllegalArgumentException("Building resources are only CARBON, IRON, SILICIUM. Not " + resource);
		}
	}
}
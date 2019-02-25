package net.jfabricationgames.genesis_project.game;

/**
 * Resources marked as primary secondary and tertiary instead of Carbon, Silicium and Iron.
 */
public class DependentResources {
	
	private int resourcesPrimary;
	private int resourcesSecondary;
	private int resourcesTertiary;
	
	public DependentResources(int resourcesPrimary, int resourcesSecondary, int resourcesTertiary) {
		this.resourcesPrimary = resourcesPrimary;
		this.resourcesSecondary = resourcesSecondary;
		this.resourcesTertiary = resourcesTertiary;
	}
	public DependentResources() {
		resourcesPrimary = 0;
		resourcesSecondary = 0;
		resourcesTertiary = 0;
	}
	
	public int getResourcesPrimary() {
		return resourcesPrimary;
	}
	public void setResourcesPrimary(int resourcesPrimary) {
		this.resourcesPrimary = resourcesPrimary;
	}
	public void addResourcesPrimary(int resourcesPrimary) {
		this.resourcesPrimary += resourcesPrimary;
	}
	
	public int getResourcesSecondary() {
		return resourcesSecondary;
	}
	public void setResourcesSecondard(int resourcesSecondard) {
		this.resourcesSecondary = resourcesSecondard;
	}
	public void addResourcesSecondard(int resourcesSecondary) {
		this.resourcesSecondary += resourcesSecondary;
	}
	
	public int getResourcesTertiary() {
		return resourcesTertiary;
	}
	public void setResourcesTertiary(int resourcesTertiary) {
		this.resourcesTertiary = resourcesTertiary;
	}
	public void addResourcesTertiary(int resourcesTertiary) {
		this.resourcesTertiary += resourcesTertiary;
	}
}
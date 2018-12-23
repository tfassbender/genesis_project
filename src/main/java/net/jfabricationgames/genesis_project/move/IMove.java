package net.jfabricationgames.genesis_project.move;

public interface IMove {
	
	public void execute();
	public boolean isResourcesAvailable();
	public Move getType();
}
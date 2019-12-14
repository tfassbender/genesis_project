package net.jfabricationgames.genesis_project.json.serializer;

/**
 * Creates unique id's for the object serialization using the @JsonIdentityInfo annotation
 */
public class SerializationIdGenerator {
	
	private static int id;
	
	public static int getNextId() {
		return id++;
	}
}
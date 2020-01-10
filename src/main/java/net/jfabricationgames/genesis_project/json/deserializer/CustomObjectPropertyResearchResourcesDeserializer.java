package net.jfabricationgames.genesis_project.json.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.jfabricationgames.genesis_project.game.ResearchResources;

public class CustomObjectPropertyResearchResourcesDeserializer extends StdDeserializer<ObjectProperty<ResearchResources>> {
	
	private static final long serialVersionUID = -2522879376792513876L;
	
	public CustomObjectPropertyResearchResourcesDeserializer() {
		this(null);
	}
	
	protected CustomObjectPropertyResearchResourcesDeserializer(Class<?> vc) {
		super(vc);
	}
	
	@Override
	public ObjectProperty<ResearchResources> deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		ResearchResources resources = parser.readValueAs(ResearchResources.class);
		return new SimpleObjectProperty<ResearchResources>(resources);
	}
}
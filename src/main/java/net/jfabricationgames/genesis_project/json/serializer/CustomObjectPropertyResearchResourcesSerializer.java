package net.jfabricationgames.genesis_project.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import javafx.beans.property.ObjectProperty;
import net.jfabricationgames.genesis_project.game.ResearchResources;

public class CustomObjectPropertyResearchResourcesSerializer extends JsonSerializer<ObjectProperty<ResearchResources>> {
	
	@Override
	public void serialize(ObjectProperty<ResearchResources> value, JsonGenerator jsonGenerator, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		jsonGenerator.writeObject(value.get());
	}
}
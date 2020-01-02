package net.jfabricationgames.genesis_project.json.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import javafx.beans.property.BooleanProperty;

public class CustomBooleanPropertyArraySerializer extends JsonSerializer<BooleanProperty[]> {
	
	@Override
	public void serialize(BooleanProperty[] value, JsonGenerator jsonGenerator, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		if (value != null) {
			jsonGenerator.writeStartArray();
			for (int i = 0; i < value.length; i++) {
				jsonGenerator.writeBoolean(value[i].get());
			}
			jsonGenerator.writeEndArray();
		}
	}
}
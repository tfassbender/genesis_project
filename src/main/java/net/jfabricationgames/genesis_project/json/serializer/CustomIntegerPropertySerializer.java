package net.jfabricationgames.genesis_project.json.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import javafx.beans.property.IntegerProperty;

public class CustomIntegerPropertySerializer extends JsonSerializer<IntegerProperty> {
	
	@Override
	public void serialize(IntegerProperty value, JsonGenerator jsonGenerator, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		jsonGenerator.writeNumber(value.get());
	}
}
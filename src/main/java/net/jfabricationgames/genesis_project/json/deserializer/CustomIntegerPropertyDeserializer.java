package net.jfabricationgames.genesis_project.json.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CustomIntegerPropertyDeserializer extends StdDeserializer<IntegerProperty> {
	
	private static final long serialVersionUID = -3536423964915572579L;
	
	public CustomIntegerPropertyDeserializer() {
		this(null);
	}
	
	protected CustomIntegerPropertyDeserializer(Class<?> vc) {
		super(vc);
	}
	
	@Override
	public IntegerProperty deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		int value = parser.readValueAs(Integer.class);
		return new SimpleIntegerProperty(value);
	}
}
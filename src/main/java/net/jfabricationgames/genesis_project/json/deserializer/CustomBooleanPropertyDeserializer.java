package net.jfabricationgames.genesis_project.json.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class CustomBooleanPropertyDeserializer extends StdDeserializer<BooleanProperty> {
	
	private static final long serialVersionUID = 7146081862502753309L;
	
	public CustomBooleanPropertyDeserializer() {
		this(null);
	}
	
	protected CustomBooleanPropertyDeserializer(Class<?> vc) {
		super(vc);
	}
	
	@Override
	public BooleanProperty deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		boolean value = parser.readValueAs(Boolean.class);
		return new SimpleBooleanProperty(value);
	}
}
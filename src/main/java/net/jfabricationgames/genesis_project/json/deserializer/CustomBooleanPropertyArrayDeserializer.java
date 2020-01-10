package net.jfabricationgames.genesis_project.json.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class CustomBooleanPropertyArrayDeserializer extends StdDeserializer<BooleanProperty[]> {
	
	private static final long serialVersionUID = 6600508452024242381L;
	
	public CustomBooleanPropertyArrayDeserializer() {
		this(null);
	}
	
	protected CustomBooleanPropertyArrayDeserializer(Class<?> vc) {
		super(vc);
	}
	
	@Override
	public BooleanProperty[] deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		boolean[] value = parser.readValueAs(boolean[].class);
		BooleanProperty[] propertyValues = new BooleanProperty[value.length];
		for (int i = 0; i < value.length; i++) {
			propertyValues[i] = new SimpleBooleanProperty(value[i]);
		}
		return propertyValues;
	}
}
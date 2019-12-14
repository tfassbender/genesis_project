package net.jfabricationgames.genesis_project.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import javafx.beans.property.ObjectProperty;
import net.jfabricationgames.genesis_project.game.Player;

public class CustomObjectPropertyPlayerSerializer extends JsonSerializer<ObjectProperty<Player>> {
	
	@Override
	public void serialize(ObjectProperty<Player> value, JsonGenerator jsonGenerator, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		jsonGenerator.writeObject(value.get().getUser());
	}
}
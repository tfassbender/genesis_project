package net.jfabricationgames.genesis_project.json.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import net.jfabricationgames.genesis_project.game.Board;

public class CustomBoardPositionSerializer extends JsonSerializer<Board.Position> {
	
	public static final String DELIMITER = ",";
	
	@Override
	public void serialize(Board.Position value, JsonGenerator jsonGenerator, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		jsonGenerator.writeFieldName(value.getX() + DELIMITER + value.getY());
	}
}
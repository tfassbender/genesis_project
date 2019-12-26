package net.jfabricationgames.genesis_project.json.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import net.jfabricationgames.genesis_project.game.Board;
import net.jfabricationgames.genesis_project.json.serializer.CustomBoardPositionSerializer;

public class CustomBoardPositionDeserializer extends KeyDeserializer {
	
	@Override
	public Object deserializeKey(String key, DeserializationContext context) throws IOException, JsonProcessingException {
		String[] values = key.split(CustomBoardPositionSerializer.DELIMITER);
		//no exception handling here, because if anything fails here the object can't be build anyway
		int x = Integer.valueOf(values[0]);
		int y = Integer.valueOf(values[1]);
		return new Board.Position(x, y);
	}
}
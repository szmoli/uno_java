package com.akos.uno.communication.action;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Serializer for GameAction objects.
 */
public class GameActionSerializer implements JsonSerializer<GameAction> {
    /**
     * Serialize a GameAction object into a JSON element.
     * Source: https://www.baeldung.com/gson-serialization-guide
     * Source: https://medium.com/@alexandre.therrien3/personalized-serializer-and-deserializer-using-java-gson-library-c079de3974d4
     * @param gameAction GameAction object to serialize.
     * @param type Type of the object to serialize.
     * @param jsonSerializationContext Context for serialization.
     * @return Serialized JSON element.
     */
    @Override
    public JsonElement serialize(GameAction gameAction, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        JsonElement actionData = jsonSerializationContext.serialize(gameAction, gameAction.getClass());

        jsonObject.addProperty("type", gameAction.getType().name());
        jsonObject.addProperty("playerName", gameAction.getPlayerName());
        jsonObject.add("actionData", actionData);

        return jsonObject;
    }
}

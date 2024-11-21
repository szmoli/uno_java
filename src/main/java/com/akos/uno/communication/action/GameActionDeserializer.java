package com.akos.uno.communication.action;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Deserializer for GameAction objects.
 */
public class GameActionDeserializer implements JsonDeserializer<GameAction> {
    /**
     * Deserialize a JSON element into a GameAction object.
     * @param jsonElement JSON element to deserialize.
     * @param type Type of the object to deserialize.
     * @param jsonDeserializationContext Context for deserialization.
     * @return Deserialized GameAction object.
     * @throws JsonParseException If the JSON element cannot be deserialized.
     */
    @Override
    public GameAction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String gameActionTypeString = jsonObject.get("type").getAsString();
        GameActionType gameActionType = GameActionType.valueOf(gameActionTypeString);
        Class<? extends GameAction> gameActionClass = gameActionType.getActionClass();
        return jsonDeserializationContext.deserialize(jsonElement, gameActionClass);
    }
}

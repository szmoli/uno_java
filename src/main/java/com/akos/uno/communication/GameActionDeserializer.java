package com.akos.uno.communication;

import com.google.gson.*;

import java.lang.reflect.Type;

public class GameActionDeserializer implements JsonDeserializer<GameAction> {
    @Override
    public GameAction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String gameActionTypeString = jsonObject.get("type").getAsString();
        GameActionType gameActionType = GameActionType.valueOf(gameActionTypeString);
        Class<? extends GameAction> gameActionClass = gameActionType.getActionClass();
        return jsonDeserializationContext.deserialize(jsonElement, gameActionClass);
    }
}

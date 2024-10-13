package com.akos.uno.communication;

import com.google.gson.*;

import java.lang.reflect.Type;

public class GameActionSerializer implements JsonSerializer<GameAction> {
    // sources:
    // - https://www.baeldung.com/gson-serialization-guide
    // - https://medium.com/@alexandre.therrien3/personalized-serializer-and-deserializer-using-java-gson-library-c079de3974d4
    @Override
    public JsonElement serialize(GameAction gameAction, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        JsonElement actionData = jsonSerializationContext.serialize(gameAction, gameAction.getClass());

        jsonObject.addProperty("type", gameAction.getActionType().name());
        jsonObject.addProperty("playerName", gameAction.getPlayerName());
        jsonObject.add("actionData", actionData);

        return jsonObject;
    }
}

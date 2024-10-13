package com.akos.uno.communication.response;

import com.google.gson.*;

import java.lang.reflect.Type;

public class ResponseDeserializer implements JsonDeserializer<Response> {
    @Override
    public Response deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String responseTypeJson = jsonObject.get("type").getAsString();
        ResponseType responseType = ResponseType.valueOf(responseTypeJson);
        Class<? extends Response> gameActionClass = responseType.getResponseClass();
        return jsonDeserializationContext.deserialize(jsonElement, gameActionClass);
    }
}

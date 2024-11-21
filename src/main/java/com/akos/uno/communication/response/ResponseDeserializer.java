package com.akos.uno.communication.response;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Deserializer for responses.
 */
public class ResponseDeserializer implements JsonDeserializer<Response> {
    /**
     * Deserializes a response from a JSON element.
     * @param jsonElement The JSON element
     * @param type The type
     * @param jsonDeserializationContext The context
     * @return The response
     * @throws JsonParseException If the JSON is invalid
     */
    @Override
    public Response deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String responseTypeJson = jsonObject.get("type").getAsString();
        ResponseType responseType = ResponseType.valueOf(responseTypeJson);
        Class<? extends Response> gameActionClass = responseType.getResponseClass();
        return jsonDeserializationContext.deserialize(jsonElement, gameActionClass);
    }
}

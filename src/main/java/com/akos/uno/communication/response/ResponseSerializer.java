package com.akos.uno.communication.response;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Serializer for responses.
 */
public class ResponseSerializer implements JsonSerializer<Response> {
    /**
     * Serializes a response to a JSON element.
     * @param response The response
     * @param type The type
     * @param jsonSerializationContext The context
     * @return The JSON element
     */
    @Override
    public JsonElement serialize(Response response, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        JsonElement responseData = jsonSerializationContext.serialize(response, response.getClass());

        jsonObject.addProperty("type", response.getType().name());
        jsonObject.add("responseData", responseData);

        return jsonObject;
    }
}

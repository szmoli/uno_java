package com.akos.uno.communication.response;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class ResponseSerializer implements JsonSerializer<Response> {
    @Override
    public JsonElement serialize(Response response, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        JsonElement responseData = jsonSerializationContext.serialize(response, response.getClass());

        jsonObject.addProperty("type", response.getType().name());
        jsonObject.add("responseData", responseData);

        return jsonObject;
    }
}

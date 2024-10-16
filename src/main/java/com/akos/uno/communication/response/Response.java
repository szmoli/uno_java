package com.akos.uno.communication.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class Response {
    public Response(ResponseType type) {
        this.type = type;
    }

    public ResponseType getType() {
        return type;
    }

    public Response createFromJson(String responseJson) {
        return gson.fromJson(responseJson, Response.class);
    }

    public String getAsJson() {
        return gson.toJson(this, Response.class);
    }

    private ResponseType type;
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(ResponseSerializer.class, new ResponseSerializer())
            .registerTypeAdapter(ResponseDeserializer.class, new ResponseDeserializer())
            .create();
}

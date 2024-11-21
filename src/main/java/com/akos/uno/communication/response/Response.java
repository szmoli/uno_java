package com.akos.uno.communication.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Base class for responses.
 */
public abstract class Response {
    /**
     * Instantiates a new Response.
     * @param type The type of the response
     */
    protected Response(ResponseType type) {
        this.type = type;
    }

    /**
     * Gets the type of the response.
     * @return The type of the response
     */
    public ResponseType getType() {
        return type;
    }

    /**
     * Creates a response from a JSON string.
     * @param responseJson The JSON string
     * @return The response
     */
    public static Response createFromJson(String responseJson) {
        return gson.fromJson(responseJson, Response.class);
    }

    /**
     * Gets the response as a JSON string.
     * @return The response as a JSON string
     */
    public String getAsJson() {
        return gson.toJson(this, this.getClass());
    }

    private final ResponseType type;
    /**
     * The Gson instance used for serialization and deserialization.
     */
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Response.class, new ResponseSerializer())
            .registerTypeAdapter(Response.class, new ResponseDeserializer())
            .create();
}

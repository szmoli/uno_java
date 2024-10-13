package com.akos.uno.communication.response;

public abstract class Response {
    public Response(ResponseType type) {
        this.type = type;
    }

    public ResponseType getType() {
        return type;
    }

    private ResponseType type;
}

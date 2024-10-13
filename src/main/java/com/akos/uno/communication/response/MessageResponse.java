package com.akos.uno.communication.response;

public class MessageResponse extends Response {
    public MessageResponse(String message) {
        super(ResponseType.MESSAGE);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    String message;
}

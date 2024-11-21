package com.akos.uno.communication.response;

/**
 * Response with a message.
 */
public class MessageResponse extends Response {
    /**
     * Instantiates a new Message response.
     * @param message The message
     */
    public MessageResponse(String message) {
        super(ResponseType.MESSAGE);
        this.message = message;
    }

    /**
     * Gets the message.
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    private final String message;
}

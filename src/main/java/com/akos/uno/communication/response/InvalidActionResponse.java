package com.akos.uno.communication.response;

/**
 * Response with an invalid action.
 */
public class InvalidActionResponse extends Response {
    /**
     * Instantiates a new Invalid action response.
     */
    public InvalidActionResponse() {
        super(ResponseType.INVALID_ACTION);
    }
}

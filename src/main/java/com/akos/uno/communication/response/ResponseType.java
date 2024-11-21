package com.akos.uno.communication.response;

/**
 * Enum for response types.
 * Each type has a corresponding response class.
 */
public enum ResponseType {
    /**
     * Response to an invalid action.
     */
    INVALID_ACTION(InvalidActionResponse.class),
    /**
     * Response to a successful action.
     */
    PARTIAL_GAME_STATE(PartialGameStateResponse.class),
    /**
     * General message response.
     */
    MESSAGE(MessageResponse.class);

    private final Class<? extends Response> responseClass;

    /**
     * Instantiates a new Response type.
     * @param responseClass The response class
     */
    ResponseType(Class<? extends Response> responseClass) {
        this.responseClass = responseClass;
    }

    /**
     * Gets the response class.
     * @return The response class
     */
    public Class<? extends Response> getResponseClass() {
        return responseClass;
    }
}

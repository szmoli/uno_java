package com.akos.uno.communication.response;

/**
 * Enum for response types.
 * Each type has a corresponding response class.
 */
public enum ResponseType {
    INVALID_ACTION(InvalidActionResponse.class),
    PARTIAL_GAME_STATE(PartialGameStateResponse.class),
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

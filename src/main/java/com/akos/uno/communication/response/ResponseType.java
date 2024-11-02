package com.akos.uno.communication.response;

public enum ResponseType {
    INVALID_ACTION(InvalidActionResponse.class),
    PARTIAL_GAME_STATE(PartialGameStateResponse.class),
    MESSAGE(MessageResponse.class);

    private Class<? extends Response> responseClass;

    ResponseType(Class<? extends Response> responseClass) {
        this.responseClass = responseClass;
    }

    public Class<? extends Response> getResponseClass() {
        return responseClass;
    }
}

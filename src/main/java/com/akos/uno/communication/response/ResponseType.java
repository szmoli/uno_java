package com.akos.uno.communication.response;

import com.akos.uno.communication.action.*;

public enum ResponseType {
    INVALID_MOVE(InvalidMoveResponse.class),
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

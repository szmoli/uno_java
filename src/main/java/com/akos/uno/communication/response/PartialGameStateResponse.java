package com.akos.uno.communication.response;

import com.akos.uno.game.PartialGameState;

public class PartialGameStateResponse extends Response {
    public PartialGameStateResponse(PartialGameState gameState) {
        super(ResponseType.PARTIAL_GAME_STATE);
        this.gameState = gameState;
    }

    public PartialGameState getGameState() {
        return gameState;
    }

    private PartialGameState gameState;
}

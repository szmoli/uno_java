package com.akos.uno.communication.response;

import com.akos.uno.game.PartialGameState;

/**
 * Response with a partial game state.
 */
public class PartialGameStateResponse extends Response {
    /**
     * Instantiates a new Partial game state response.
     * @param gameState The partial game state
     */
    public PartialGameStateResponse(PartialGameState gameState) {
        super(ResponseType.PARTIAL_GAME_STATE);
        this.gameState = gameState;
    }

    /**
     * Gets the partial game state.
     * @return The partial game state
     */
    public PartialGameState getGameState() {
        return gameState;
    }

    private final PartialGameState gameState;
}

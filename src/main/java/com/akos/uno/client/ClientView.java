package com.akos.uno.client;

import com.akos.uno.communication.response.PartialGameStateResponse;

public class ClientView {
    public ClientView(PartialGameStateResponse gameState) {
        this.gameState = gameState;
    }

    public void setGameState(PartialGameStateResponse gameState) {
        this.gameState = gameState;
    }

    private PartialGameStateResponse gameState;
}

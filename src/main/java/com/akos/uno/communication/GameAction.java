package com.akos.uno.communication;

import com.akos.uno.game.Player;

public abstract class GameAction {
    public String getPlayerName() {
        return playerName;
    }

    public GameAction(String playerName) {
        this.playerName = playerName;
    }

    public abstract GameActionType getActionType();

    private String playerName;
}

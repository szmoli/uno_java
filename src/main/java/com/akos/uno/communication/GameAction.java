package com.akos.uno.communication;

import com.akos.uno.game.Player;

public abstract class GameAction {
    public String getPlayerName() {
        return playerName;
    }

    public GameAction(String playerName, GameActionType type) {
        this.playerName = playerName;
        this.type = type;
    }

    public GameActionType getActionType() {
        return type;
    }

    private String playerName;
    private GameActionType type;
}

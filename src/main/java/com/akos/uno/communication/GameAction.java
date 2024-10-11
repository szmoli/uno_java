package com.akos.uno.communication;

import com.google.gson.Gson;

public abstract class GameAction {
    public String getPlayerName() {
        return playerName;
    }

    public Gson getGson() {
        return gson;
    }

    public GameAction(String playerName, GameActionType type) {
        this.playerName = playerName;
        this.type = type;
    }

    public GameActionType getActionType() {
        return type;
    }

    public String getActionMessage() {
        return gson.toJson(this);
    }

    private String playerName;
    private GameActionType type;
    private static final Gson gson = new Gson();
}

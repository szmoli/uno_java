package com.akos.uno.communication.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class GameAction {
    public String getPlayerName() {
        return playerName;
    }

    public GameAction(String playerName, GameActionType type) {
        this.playerName = playerName;
        this.type = type;
    }

    public GameActionType getType() {
        return type;
    }

    // source: https://stackoverflow.com/questions/929021/what-are-static-factory-methods
    public static GameAction createFromJson(String actionJson) {
        return gson.fromJson(actionJson, GameAction.class);
    }

    public String getAsJson() {
        return gson.toJson(this, this.getClass());
    }

    private String playerName;
    private GameActionType type;
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(GameAction.class, new GameActionSerializer())
            .registerTypeAdapter(GameAction.class, new GameActionDeserializer())
            .create();
}

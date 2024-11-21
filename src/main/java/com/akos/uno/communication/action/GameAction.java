package com.akos.uno.communication.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Base class for game actions.
 */
public abstract class GameAction {
    /**
     * Constructor.
     * @param playerName Acting player's name.
     * @param type Type of the action.
     */
    protected GameAction(String playerName, GameActionType type) {
        this.playerName = playerName;
        this.type = type;
    }

    /**
     * Get the name of the player who initiated the action.
     * @return
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Get the type of the action.
     * @return
     */
    public GameActionType getType() {
        return type;
    }

    /**
     * Create a GameAction object from a JSON string.
     * Source: https://stackoverflow.com/questions/929021/what-are-static-factory-methods
     * @param actionJson JSON string representing the action.
     * @return GameAction object.
     */
    public static GameAction createFromJson(String actionJson) {
        return gson.fromJson(actionJson, GameAction.class);
    }

    /**
     * Get the JSON representation of the action.
     * @return JSON string.
     */
    public String getAsJson() {
        return gson.toJson(this, this.getClass());
    }

    private String playerName;
    private GameActionType type;
    /**
     * Gson object for JSON serialization/deserialization.
     */
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(GameAction.class, new GameActionSerializer())
            .registerTypeAdapter(GameAction.class, new GameActionDeserializer())
            .create();
}

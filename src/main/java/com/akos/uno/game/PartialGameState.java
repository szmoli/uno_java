package com.akos.uno.game;

import com.google.gson.Gson;

import java.util.List;

public class PartialGameState {
    public PartialGameState(Player player, List<String> otherPlayerNames, List<Integer> otherPlayerHandSizes, Card topCard, GameStatus gameStatus) {
        this.player = player;
        this.otherPlayerNames = otherPlayerNames;
        this.otherPlayerHandSizes = otherPlayerHandSizes;
        this.topCard = topCard;
        this.gameStatus = gameStatus;
    }

    public static PartialGameState createFromJson(String json) {
        return gson.fromJson(json, PartialGameState.class);
    }

    public Player getPlayer() {
        return player;
    }

    public Card getTopCard() {
        return topCard;
    }

    public List<String> getOtherPlayerNames() {
        return otherPlayerNames;
    }

    public List<Integer> getOtherPlayerHandSizes() {
        return otherPlayerHandSizes;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    private Player player;
    private List<String> otherPlayerNames;
    private List<Integer> otherPlayerHandSizes;
    private Card topCard;
    private GameStatus gameStatus;
    private static final Gson gson = new Gson();
}

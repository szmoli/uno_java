package com.akos.uno.game;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartialGameState {
    public PartialGameState(Player player) {
        this.player = player;
        otherPlayerNames = null;
        otherPlayerHandSizes = null;
        topCard = null;
        gameStatus = GameStatus.INIT;
    }

    public PartialGameState(Player player, FullGameState gameState) {
        this.player = player;
        otherPlayerNames = gameState.getPlayers().keySet().stream().filter(name -> !name.equals(player.getPlayerName())).collect(Collectors.toList());
        otherPlayerHandSizes = gameState.getPlayers().values().stream().filter(p -> !p.getPlayerName().equals(player.getPlayerName())).map(p -> p.getHand().size()).collect(Collectors.toList());
        topCard = gameState.getDeck().getDiscardPile().top();
        gameStatus = gameState.getGameStatus();
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

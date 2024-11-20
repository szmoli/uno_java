package com.akos.uno.game;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PartialGameState {
    public PartialGameState(Player player) {
        this.player = player;
        currentPlayerName = null;
        winnerName = null;
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
        currentPlayerName = gameState.getPlayerNamesInOrder().get(gameState.getCurrentPlayerIndex());
        winnerName = gameState.getWinner() == null ? null : gameState.getWinner().getPlayerName();
    }

    public static PartialGameState createFromJson(String json) {
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        JsonObject gameStateJson = root.getAsJsonObject("gameState");
        return gson.fromJson(gameStateJson, PartialGameState.class);
    }

    public String getAsJson() {
        return gson.toJson(this);
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

    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    public String getWinnerName() {
        return winnerName;
    }

    private final Player player;
    private final List<String> otherPlayerNames;
    private final List<Integer> otherPlayerHandSizes;
    private final Card topCard;
    private final GameStatus gameStatus;
    private final String currentPlayerName;
    private final String winnerName;
    private static final Gson gson = new Gson();
}

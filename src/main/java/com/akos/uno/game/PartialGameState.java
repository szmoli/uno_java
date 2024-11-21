package com.akos.uno.game;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Represents a partial view of the game state.
 */
public class PartialGameState {
    /**
     * Constructs a new PartialGameState instance.
     * @param player The player to create the partial state for
     */
    public PartialGameState(Player player) {
        this.player = player;
        currentPlayerName = null;
        winnerName = null;
        otherPlayerNames = null;
        otherPlayerHandSizes = null;
        topCard = null;
        gameStatus = GameStatus.INIT;
    }

    /**
     * Constructs a new PartialGameState instance.
     * @param player The player to create the partial state for
     * @param gameState The full game state
     */
    public PartialGameState(Player player, FullGameState gameState) {
        this.player = player;
        otherPlayerNames = gameState.getPlayers().keySet().stream().filter(name -> !name.equals(player.getPlayerName())).collect(Collectors.toList());
        otherPlayerHandSizes = gameState.getPlayers().values().stream().filter(p -> !p.getPlayerName().equals(player.getPlayerName())).map(p -> p.getHand().size()).collect(Collectors.toList());
        topCard = gameState.getDeck().getDiscardPile().top();
        gameStatus = gameState.getGameStatus();
        currentPlayerName = gameState.getPlayerNamesInOrder().get(gameState.getCurrentPlayerIndex());
        winnerName = gameState.getWinner() == null ? null : gameState.getWinner().getPlayerName();
    }

    /**
     * Creates a new PartialGameState instance from a JSON string.
     * Static factory method.
     * @param json The JSON string to create the partial state from
     * @return A new PartialGameState instance
     */
    public static PartialGameState createFromJson(String json) {
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        JsonObject gameStateJson = root.getAsJsonObject("gameState");
        return gson.fromJson(gameStateJson, PartialGameState.class);
    }

    /**
     * Returns the partial game state as a JSON string.
     * @return The partial game state as a JSON string
     */
    public String getAsJson() {
        return gson.toJson(this);
    }

    /**
     * Returns the player associated with this partial game state.
     * @return The player associated with this partial game state
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the top card in the discard pile.
     * @return The top card in the discard pile
     */
    public Card getTopCard() {
        return topCard;
    }

    /**
     * Returns the names of the other players in the game.
     * @return The names of the other players in the game
     */
    public List<String> getOtherPlayerNames() {
        return otherPlayerNames;
    }

    /**
     * Returns the hand sizes of the other players in the game.
     * @return The hand sizes of the other players in the game
     */
    public List<Integer> getOtherPlayerHandSizes() {
        return otherPlayerHandSizes;
    }

    /**
     * Returns the status of the game.
     * @return The status of the game
     */
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    /**
     * Returns the name of the current player.
     * @return The name of the current player
     */
    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    /**
     * Returns the name of the winner of the game.
     * @return The name of the winner of the game
     */
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

package com.akos.uno.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the full state of the game.
 */
public class FullGameState {
    /**
     * Constructs a new FullGameState instance.
     * @param currentPlayerIndex The index of the current player
     * @param players The players in the game
     * @param deck The deck of cards
     * @param currentRound The current round number
     * @param isOrderReversed Whether the order of play is reversed
     * @param gameStatus The status of the game
     */
    public FullGameState(int currentPlayerIndex, Map<String, Player> players, Deck deck, int currentRound, boolean isOrderReversed, GameStatus gameStatus) {
        this.currentPlayerIndex = currentPlayerIndex;
        this.players = new HashMap<>(players);
        this.deck = deck;
        this.currentRound = currentRound;
        this.isOrderReversed = isOrderReversed;
        this.gameStatus = gameStatus;
        this.hostPlayer = null;
        this.winner = null;
    }

    /**
     * Returns the status of the game.
     * @return The status of the game
     */
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    /**
     * Sets the status of the game.
     * @param gameStatus The status of the game
     */
    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    /**
     * Returns the index of the current player.
     * @return The index of the current player
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * Sets the index of the current player.
     * @param idx The index of the current player
     */
    public void setCurrentPlayerIndex(int idx) {
        this.currentPlayerIndex = idx;
    }

    /**
     * Returns the players in the game.
     * @return The players in the game
     */
    public Map<String, Player> getPlayers() {
        return players;
    }

    /**
     * Returns the players' names in the order they joined the game.
     * @return The players' names in the order they joined the game
     */
    public List<String> getPlayerNamesInOrder() {
        return players.keySet().stream().toList();
    }

    /**
     * Returns the deck of cards.
     * @return The deck of cards
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Returns the current round number.
     * @return The current round number
     */
    public int getCurrentRound() {
        return currentRound;
    }

    /**
     * Sets the current round number.
     * @param n The current round number
     */
    public void setCurrentRound(int n) {
        currentRound = n;
    }

    /**
     * Returns whether the order of play is reversed.
     * @return True if the order of play is reversed, false otherwise
     */
    public boolean isOrderReversed() {
        return isOrderReversed;
    }

    /**
     * Reverses the order of play.
     */
    public void reverseOrder() {
        isOrderReversed = !isOrderReversed;
    }

    /**
     * Sets the host player of the game.
     * @param player The host player
     */
    public void setHostPlayer(Player player) {
        this.hostPlayer = player;
    }

    /**
     * Returns the host player of the game.
     * @return The host player of the game
     */
    public Player getHostPlayer() {
        return hostPlayer;
    }

    /**
     * Returns the winner of the game.
     * @return The winner of the game
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * Sets the winner of the game.
     * @param winner The winner of the game
     */
    public void setWinner(Player winner) {
        this.winner = winner;
    }

    private int currentPlayerIndex;
    private final Map<String, Player> players; // this is a HashMap so we can get the player efficiently by its name
    private final Deck deck;
    private int currentRound;
    private boolean isOrderReversed;
    private GameStatus gameStatus;
    private Player hostPlayer;
    private Player winner;
}

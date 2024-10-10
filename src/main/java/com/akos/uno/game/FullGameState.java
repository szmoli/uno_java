package com.akos.uno.game;

import java.util.List;

public class FullGameState {
    public FullGameState(int currentPlayerIndex, List<Player> players, Deck deck, int currentRound, boolean isOrderReversed, GameStatus gameStatus) {
        this.currentPlayerIndex = currentPlayerIndex;
        this.players = players;
        this.deck = deck;
        this.currentRound = currentRound;
        this.isOrderReversed = isOrderReversed;
        this.gameStatus = gameStatus;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int idx) {
        this.currentPlayerIndex = idx;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int n) {
        currentRound = n;
    }

    public boolean isOrderReversed() {
        return isOrderReversed;
    }

    public void reverseOrder() {
        isOrderReversed = !isOrderReversed;
    }

    private int currentPlayerIndex;
    private List<Player> players;
    private Deck deck;
    private int currentRound;
    private boolean isOrderReversed;
    private GameStatus gameStatus;
}

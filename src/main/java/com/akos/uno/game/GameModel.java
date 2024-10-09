package com.akos.uno.game;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    public GameModel(GameRules rules) {
        this.state = GameState.OPEN;
        this.rules = rules;
        this.currentPlayerIndex = 0;
        this.players = new ArrayList<>();
        this.deck = new Deck();
        this.currentRound = 1;
        this.isOrderReversed = false;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public GameRules getRules() {
        return rules;
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

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
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

    private GameState state;
    private GameRules rules;
    private int currentPlayerIndex;
    private List<Player> players;
    private Deck deck;
    private int currentRound;
    private boolean isOrderReversed;
}

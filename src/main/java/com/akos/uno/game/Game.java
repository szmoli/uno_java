package com.akos.uno.game;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public Game(GameRules rules) {
        this.state = GameState.OPEN;
        this.rules = rules;
        this.currentPlayerIndex = 0;
        this.players = new ArrayList<>();
        this.deck = new Deck();
        this.currentRound = 1;
        this.isOrderReversed = false;
    }

    private GameState state;
    private GameRules rules;
    private int currentPlayerIndex;
    private List<Player> players;
    private Deck deck;
    private int currentRound;
    private boolean isOrderReversed;
}

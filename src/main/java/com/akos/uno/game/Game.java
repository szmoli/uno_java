package com.akos.uno.game;

import java.util.HashMap;

public class Game {
    public Game() {
        this.rules = new GameRules(this);
        this.state = new FullGameState(0, new HashMap<>(), new Deck(true), 0, false, GameStatus.OPEN);
    }

    public GameRules getRules() {
        return rules;
    }

    public FullGameState getState() {
        return state;
    }

    private final GameRules rules;
    private final FullGameState state;
}

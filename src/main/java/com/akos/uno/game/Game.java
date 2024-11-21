package com.akos.uno.game;

import java.util.HashMap;

/**
 * Represents a game of UNO.
 */
public class Game {
    /**
     * Constructs a new Game instance.
     * Initializes the game with default rules and an empty state.
     */
    public Game() {
        this.rules = new GameRules(this);
        this.state = new FullGameState(0, new HashMap<>(), new Deck(true), 0, false, GameStatus.OPEN);
    }

    /**
     * Returns the rules of the game.
     * @return The rules of the game
     */
    public GameRules getRules() {
        return rules;
    }

    /**
     * Returns the current state of the game.
     * @return The current state of the game
     */
    public FullGameState getState() {
        return state;
    }

    private final GameRules rules;
    private final FullGameState state;
}

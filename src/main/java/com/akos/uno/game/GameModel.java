package com.akos.uno.game;

import java.util.ArrayList;
import java.util.HashMap;

public class GameModel {
    public GameModel(GameRules rules) {
        this.rules = rules;
        this.gameState = new FullGameState(0, new HashMap<>(), new Deck(), 0, false, GameStatus.OPEN);
    }

    public GameRules getRules() {
        return rules;
    }

    public FullGameState getGameState() {
        return gameState;
    }

    private GameRules rules;
    private FullGameState gameState;
}

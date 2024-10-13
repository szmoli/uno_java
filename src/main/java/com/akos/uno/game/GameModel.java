package com.akos.uno.game;

public class GameModel {
    public GameModel(GameRules rules, FullGameState gameState) {
        this.rules = rules;
        this.gameState = gameState;
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

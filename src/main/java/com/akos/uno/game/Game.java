package com.akos.uno.game;

import java.util.List;

public class Game {
    private GameState state;
    private GameRules rules;
    private int currentPlayerIndex;
    private List<Player> players;
    private Deck deck;
    private int currentRound;
    private boolean isOrderReversed;
}

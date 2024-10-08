package com.akos.uno.game;

public enum GameState {
    OPEN, // game is open for players to join
    IN_PROGRESS, // game is in progress, no more players are allowed to join
    FINISHED, // the game is finished, points are calculated
    CLOSED // the game was closed after it finished
}

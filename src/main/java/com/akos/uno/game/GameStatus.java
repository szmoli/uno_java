package com.akos.uno.game;

/**
 * Represents the status of a game.
 */
public enum GameStatus {
    /**
     * Game is being initialized.
     */
    INIT,
    /**
     * Game is open for new players to join.
     */
    OPEN,
    /**
     * Game is in progress. No players can join.
     */
    IN_PROGRESS,
    /**
     * Game has finished.
     */
    FINISHED
}

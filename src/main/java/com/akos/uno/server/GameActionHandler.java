package com.akos.uno.server;

import com.akos.uno.communication.action.GameAction;

/**
 * Handles game actions.
 */
public interface GameActionHandler<T extends GameAction> {
    /**
     * Handles the game action.
     * @param action Game action
     */
    void handle(T action);

    /**
     * Handles the game action.
     * @param action Game action
     */
    default void handleAction(GameAction action) {
        @SuppressWarnings("unchecked")
        T typedAction = (T) action;
        handle(typedAction);
    }
}

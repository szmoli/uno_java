package com.akos.uno.server;

import com.akos.uno.communication.action.GameAction;

public interface GameActionHandler<T extends GameAction> {
    void handle(T action);
}

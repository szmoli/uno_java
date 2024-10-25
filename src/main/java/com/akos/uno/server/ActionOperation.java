package com.akos.uno.server;

import com.akos.uno.communication.action.GameAction;

public interface ActionOperation {
    void execute(GameAction gameAction);
}

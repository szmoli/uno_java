package com.akos.uno.communication;

import com.akos.uno.game.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.akos.uno.communication.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameActionTest {
    @Test
    public void testGameActionCreation() {
        DiscardCardAction action = new DiscardCardAction("player1", new Card(CardColor.RED, CardSymbol.SEVEN));
        String actionJson = action.getActionMessage();
    }

    private static final Logger logger = LogManager.getLogger();
}

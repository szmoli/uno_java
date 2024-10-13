package com.akos.uno.communication.action;

import com.akos.uno.game.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameActionTest {
    @Test
    public void testGameActionCreation() {
        DiscardCardAction action = new DiscardCardAction("player1", new Card(CardColor.RED, CardSymbol.SEVEN));

        String actionJson = action.getAsJson();

        logger.debug(actionJson);

        GameAction deserializedAction = GameAction.createFromJson(actionJson);
        String actionJson2 = deserializedAction.getAsJson();

        assertEquals(action.getClass(), deserializedAction.getClass());
        assertEquals(actionJson, actionJson2);
    }

    private static final Logger logger = LogManager.getLogger();
}

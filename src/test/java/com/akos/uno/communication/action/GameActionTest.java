package com.akos.uno.communication.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.akos.uno.game.Card;
import com.akos.uno.game.CardColor;
import com.akos.uno.game.CardSymbol;

public class GameActionTest {
    @Test
    public void testGameActionCreation() {
        DiscardCardAction action = new DiscardCardAction("player1", new Card(CardColor.RED, CardSymbol.SEVEN), CardColor.NONE);

        String actionJson = action.getAsJson();

        logger.debug(actionJson);

        GameAction deserializedAction = GameAction.createFromJson(actionJson);
        String actionJson2 = deserializedAction.getAsJson();

        assertEquals(action.getClass(), deserializedAction.getClass());
        assertEquals(actionJson, actionJson2);
    }

    private static final Logger logger = LogManager.getLogger();
}

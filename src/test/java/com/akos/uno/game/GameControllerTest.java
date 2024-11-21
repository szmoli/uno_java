package com.akos.uno.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameControllerTest {
    private GameController gameController;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    void setUp() {
        gameController = new GameController();
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        player3 = new Player("Player 3");
        gameController.addPlayer(player1);
        gameController.addPlayer(player2);
        gameController.addPlayer(player3);
        gameController.setHostPlayer(player1);
    }

    void tryUntilTopCardIsntEffectCard() {
        while (gameController.getTopCard() == null || gameController.isEffectCard(gameController.getTopCard()) || gameController.getTopCard().equals(new Card(CardColor.NONE, CardSymbol.NONE))) {
            setUp();
            gameController.startGame();
        }
    }

    @Test
    void testStartGame() {
        tryUntilTopCardIsntEffectCard();

        assertEquals(3, gameController.getPlayers().size());
        assertEquals(player1, gameController.getHostPlayer());
        assertEquals(player1, gameController.getPlayerWithDelta(0));
        assertEquals(player2, gameController.getPlayerWithDelta(1));
        assertEquals(player3, gameController.getPlayerWithDelta(2));

        assertEquals(7, player1.getHand().size());
        assertEquals(7, player2.getHand().size());
        assertEquals(7, player3.getHand().size());
    }

    @Test
    void testNextPlayerAndReverse() {
        tryUntilTopCardIsntEffectCard();

        assertEquals(player1, gameController.getPlayerWithDelta(0));
        gameController.nextRound();
        assertEquals(player2, gameController.getPlayerWithDelta(0));
        gameController.nextRound();
        assertEquals(player3, gameController.getPlayerWithDelta(0));
        gameController.nextRound();
        assertEquals(player1, gameController.getPlayerWithDelta(0));

        gameController.reverseOrder();

        assertEquals(player1, gameController.getPlayerWithDelta(0));
        gameController.nextRound();
        assertEquals(player3, gameController.getPlayerWithDelta(0));
        gameController.nextRound();
        assertEquals(player2, gameController.getPlayerWithDelta(0));
        gameController.nextRound();
        assertEquals(player1, gameController.getPlayerWithDelta(0));
    }

    @Test
    void testApplySkip() {
        tryUntilTopCardIsntEffectCard();

        gameController.applyCardEffects(new Card(CardColor.RED, CardSymbol.SKIP), gameController.getPlayerWithDelta(0));
        assertEquals(player2, gameController.getPlayerWithDelta(0));
    }

    @Test
    void testApplyReverse() {
        tryUntilTopCardIsntEffectCard();

        gameController.applyCardEffects(new Card(CardColor.RED, CardSymbol.REVERSE), gameController.getPlayerWithDelta(0));
        assertTrue(gameController.getGame().getState().isOrderReversed());
    }

    @Test
    void testApplyDrawTwo() {
        tryUntilTopCardIsntEffectCard();

        gameController.applyCardEffects(new Card(CardColor.RED, CardSymbol.DRAW_TWO), gameController.getPlayerWithDelta(0));
        assertEquals(9, gameController.getPlayerWithDelta(-1).getHand().size());
        assertEquals(player2, gameController.getPlayerWithDelta(0));
    }

    @Test
    void testApplyWildFour() {
        tryUntilTopCardIsntEffectCard();

        gameController.applyCardEffects(new Card(CardColor.RED, CardSymbol.WILD_FOUR), gameController.getPlayerWithDelta(0));
        assertEquals(11, gameController.getPlayerWithDelta(-1).getHand().size());
        assertEquals(player2, gameController.getPlayerWithDelta(0));
    }

    @Test
    void testIsPlayersTurn() {
        tryUntilTopCardIsntEffectCard();

        assertTrue(gameController.isPlayersTurn(player1));
        gameController.nextRound();
        assertTrue(gameController.isPlayersTurn(player2));
        gameController.nextRound();
        assertTrue(gameController.isPlayersTurn(player3));
        gameController.nextRound();
        assertTrue(gameController.isPlayersTurn(player1));
    }

    @Test
    void testJoinFullGame() {
        assertTrue(gameController.addPlayer(new Player("Player 4")));
        assertTrue(gameController.addPlayer(new Player("Player 5")));
        assertTrue(gameController.addPlayer(new Player("Player 6")));
        assertTrue(gameController.addPlayer(new Player("Player 7")));
        assertTrue(gameController.addPlayer(new Player("Player 8")));
        assertTrue(gameController.addPlayer(new Player("Player 9")));
        assertTrue(gameController.addPlayer(new Player("Player 10")));
        assertFalse(gameController.addPlayer(new Player("Player 11")));
    }
}

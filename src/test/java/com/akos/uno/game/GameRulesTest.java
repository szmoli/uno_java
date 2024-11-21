package com.akos.uno.game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameRulesTest {
    Game game;
    Player player1;

    @BeforeEach
    void setUp() {
        game = new Game();
        player1 = new Player("Player 1");
        player1.drawCard(new Card(CardColor.RED, CardSymbol.ONE));
        player1.drawCard(new Card(CardColor.RED, CardSymbol.TWO));
        player1.drawCard(new Card(CardColor.RED, CardSymbol.THREE));
        player1.drawCard(new Card(CardColor.YELLOW, CardSymbol.ONE));
        player1.drawCard(new Card(CardColor.YELLOW, CardSymbol.TWO));
        player1.drawCard(new Card(CardColor.YELLOW, CardSymbol.THREE));
    }

    @Test
    void testHasMatchingColorCard() {
        Card card = new Card(CardColor.RED, CardSymbol.FOUR);
        assertTrue(game.getRules().hasMatchingColorCard(player1, card));
        assertFalse(game.getRules().hasMatchingColorCard(player1, new Card(CardColor.GREEN, CardSymbol.FOUR)));
    }

    @Test
    void testValidMove() {
        Card topCard = new Card(CardColor.RED, CardSymbol.FOUR);
        assertTrue(game.getRules().isValidMove(new Card(CardColor.RED, CardSymbol.FOUR), topCard));
        assertTrue(game.getRules().isValidMove(new Card(CardColor.RED, CardSymbol.FIVE), topCard));
        assertTrue(game.getRules().isValidMove(new Card(CardColor.GREEN, CardSymbol.FOUR), topCard));
        assertTrue(game.getRules().isValidMove(new Card(CardColor.WILD, CardSymbol.WILD), topCard));
        assertTrue(game.getRules().isValidMove(new Card(CardColor.WILD, CardSymbol.WILD_FOUR), topCard));
        assertFalse(game.getRules().isValidMove(new Card(CardColor.GREEN, CardSymbol.FIVE), topCard));
    }

    @Test
    void testIsValidSayUno() {
        player1.getHand().clear();
        assertFalse(game.getRules().isValidSayUno(player1));
        player1.drawCard(new Card(CardColor.RED, CardSymbol.FOUR));
        assertTrue(game.getRules().isValidSayUno(player1));
    }
}

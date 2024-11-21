package com.akos.uno.game;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class DeckTest {
    @Test
    void testDeckCreation() {
        Deck deck = new Deck(false);
//        for (Card card : deck.getDrawPile()) {
//            logger.debug(card.toString());
//        }

        assertEquals(108, deck.getDrawPile().size());
        assertEquals(0, deck.getDiscardPile().size());
    }

    @Test
    void testSingleDrawAndDiscard() {
        Deck deck = new Deck(false);
        Card singleCard = deck.drawCards(1).getFirst();

        assertEquals(107, deck.getDrawPile().size());
        assertEquals(0, deck.getDiscardPile().size());

        deck.addCardToDiscardPile(singleCard);

        assertEquals(107, deck.getDrawPile().size());
        assertEquals(1, deck.getDiscardPile().size());
        assertEquals(singleCard, deck.getDiscardPile().top());
    }

    @Test
    void testMultipleDrawsAndDiscards() {
        Deck deck = new Deck(false);
        List<Card> twoCards = deck.drawCards(2);

        assertEquals(106, deck.getDrawPile().size());
        assertEquals(0, deck.getDiscardPile().size());

        deck.addCardToDiscardPile(twoCards.getFirst());

        assertEquals(106, deck.getDrawPile().size());
        assertEquals(1, deck.getDiscardPile().size());
        assertEquals(twoCards.getFirst(), deck.getDiscardPile().top());
    }

    @Test
    void testDrawCardRanOutOfCards() {
        Deck deck = new Deck(false);
        List<Card> cards = deck.drawCards(107);

        for (Card card : cards) {
            deck.addCardToDiscardPile(card);
        }

        assertEquals(1, deck.getDrawPile().size());
        assertEquals(107, deck.getDiscardPile().size());

        List<Card> cards2 = deck.drawCards(2);

        assertEquals(105, deck.getDrawPile().size());
        assertEquals(1, deck.getDiscardPile().size());
        assertEquals(2, cards2.size());
    }
}

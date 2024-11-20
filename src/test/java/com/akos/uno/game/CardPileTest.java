package com.akos.uno.game;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CardPileTest {
    @Test
    public void testPushCards() {
        CardPile pile = new CardPile();
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(CardColor.RED, CardSymbol.SEVEN));
        cards.add(new Card(CardColor.GREEN, CardSymbol.SEVEN));
        cards.add(new Card(CardColor.BLUE, CardSymbol.SEVEN));
        cards.add(new Card(CardColor.YELLOW, CardSymbol.SEVEN));

        pile.pushCards(cards);

        assertEquals(4, pile.size());
        assertEquals(cards.getLast(), pile.top());
    }

    @Test
    public void testPushCard() {
        CardPile pile = new CardPile();
        Card card = new Card(CardColor.RED, CardSymbol.SEVEN);

        pile.pushCard(card);

        assertEquals(1, pile.size());
        assertEquals(card, pile.top());
    }

    @Test
    public void testPopCard() {
        CardPile pile = new CardPile();
        Card card = new Card(CardColor.RED, CardSymbol.SEVEN);
        pile.pushCard(card);

        Card popped = pile.popCard();

        assertEquals(0, pile.size());
        assertEquals(card, popped);

        popped = pile.popCard();
        assertEquals(new Card(CardColor.NONE, CardSymbol.NONE), popped);
    }

    @Test
    public void testPopCards() {
        CardPile pile = new CardPile();
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(CardColor.RED, CardSymbol.SEVEN));
        cards.add(new Card(CardColor.GREEN, CardSymbol.SEVEN));
        cards.add(new Card(CardColor.BLUE, CardSymbol.SEVEN));
        cards.add(new Card(CardColor.YELLOW, CardSymbol.SEVEN));

        pile.pushCards(cards);

        List<Card> poppedCards = pile.popCards(4);

        assertEquals(0, pile.size());
        assertEquals(cards.getLast(), poppedCards.getFirst());
    }
}

package com.akos.uno.game;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class CardPileTest {
    @Test
    void testPushCards() {
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
    void testPushCard() {
        CardPile pile = new CardPile();
        Card card = new Card(CardColor.RED, CardSymbol.SEVEN);

        pile.pushCard(card);

        assertEquals(1, pile.size());
        assertEquals(card, pile.top());
    }

    @Test
    void testPopCard() {
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
    void testPopCards() {
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

        poppedCards = pile.popCards(2);
        assertEquals(1, poppedCards.size());
        assertEquals(new Card(CardColor.NONE, CardSymbol.NONE), poppedCards.getFirst());
    }

    @Test
    void testPushNone() {
        CardPile pile = new CardPile();
        Card card = new Card(CardColor.NONE, CardSymbol.NONE);

        pile.pushCard(card);

        assertEquals(0, pile.size());
    }

    @Test
    void testShuffle() {
        CardPile pile = new CardPile();
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(CardColor.RED, CardSymbol.SEVEN));
        cards.add(new Card(CardColor.GREEN, CardSymbol.SEVEN));
        cards.add(new Card(CardColor.BLUE, CardSymbol.SEVEN));
        cards.add(new Card(CardColor.YELLOW, CardSymbol.SEVEN));
        pile.pushCards(cards);
        Card originalTop = pile.top();
        pile.shuffle();
        Card newTop = pile.top();

        while (originalTop.equals(newTop)) {
            pile.shuffle();
            newTop = pile.top();
        }

        assertEquals(4, pile.size());
        assertNotEquals(originalTop, newTop); 
    }

    @Test
    void testClear() {
        CardPile pile = new CardPile();
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(CardColor.RED, CardSymbol.SEVEN));
        cards.add(new Card(CardColor.GREEN, CardSymbol.SEVEN));
        cards.add(new Card(CardColor.BLUE, CardSymbol.SEVEN));
        cards.add(new Card(CardColor.YELLOW, CardSymbol.SEVEN));
        pile.pushCards(cards);

        pile.clear();

        assertEquals(0, pile.size());
    }

    @Test
    void testNoneTop() {
        CardPile pile = new CardPile();
        Card top = pile.top();

        assertEquals(new Card(CardColor.NONE, CardSymbol.NONE), top);
    }

    @Test
    void testGetSecondCard() {
        CardPile pile = new CardPile();
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(CardColor.RED, CardSymbol.SEVEN));
        cards.add(new Card(CardColor.GREEN, CardSymbol.SEVEN));

        pile.pushCards(cards);

        Card secondCard = pile.getSecondCard();

        assertEquals(new Card(CardColor.GREEN, CardSymbol.SEVEN), secondCard);
    }
    
    @Test
    void testGetSecondCardNone() {
        CardPile pile = new CardPile();
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(CardColor.RED, CardSymbol.SEVEN));

        pile.pushCards(cards);

        Card secondCard = pile.getSecondCard();

        assertEquals(new Card(CardColor.NONE, CardSymbol.NONE), secondCard);
    }
}

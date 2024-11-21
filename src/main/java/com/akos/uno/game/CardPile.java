package com.akos.uno.game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a pile of cards in the game.
 */
public class CardPile implements Iterable<Card> {
    /**
     * Constructs a new CardPile instance.
     * Initializes the pile as an empty deque.
     */
    public CardPile() {
        cards = new ArrayDeque<>();
    }

    /**
     * Adds cards to the top of the pile.
     * @param cards The cards to initialize the pile with
     */
    public void pushCards(Collection<Card> cards) {
        for (Card card : cards) {
            this.cards.push(card);
        }
    }

    /**
     * Removes and returns a specified number of cards from the top of the pile.
     * @param n The number of cards to remove
     * @return A list of the removed cards
     */
    public List<Card> popCards(int n) {
        if (this.cards.isEmpty()) {
            throw new IllegalStateException("CardPile is empty.");
        }

        List<Card> removedCards = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            removedCards.add(this.cards.pop());
        }

        return removedCards;
    }

    /**
     * Adds a single card to the top of the pile.
     * @param card The card to add
     */
    public void pushCard(Card card) {
        if (card.getColor() == CardColor.NONE || card.getSymbol() == CardSymbol.NONE) {
            return;
        }

        cards.push(card);
    }

    /**
     * Removes and returns the top card from the pile.
     * @return The top card
     */
    public Card popCard() {
        if (this.cards.isEmpty()) {
            return new Card(CardColor.NONE, CardSymbol.NONE);
        }

        return cards.pop();
    }

    /**
     * Shuffles the cards in the pile.
     */
    public void shuffle() {
        List<Card> cardsList = new ArrayList<>(cards);
        Collections.shuffle(cardsList);

        cards.clear();
        for (Card card : cardsList) {
            cards.push(card);
        }
    }

    /**
     * Returns the number of cards in the pile.
     * @return The number of cards in the pile
     */
    public int size() {
        return cards.size();
    }

    /**
     * Returns whether the pile is empty.
     * @return True if the pile is empty, false otherwise
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Removes all cards from the pile.
     */
    public void clear() {
        cards.clear();
    }

    /**
     * Returns an iterator over the cards in the pile.
     * @return An iterator over the cards in the pile
     */
    @Override
    public Iterator<Card> iterator() {
        return cards.iterator();
    }

    /**
     * Returns the top card from the pile without removing it.
     * @return The top card
     */
    public Card top() {
        if (cards.isEmpty()) {
            return new Card(CardColor.NONE, CardSymbol.NONE);
        }

        return cards.peek();
    }

    /**
     * Returns the second card from the top of the pile without removing it.
     * @return The second card from the top
     */
    public Card getSecondCard() {
        if (cards.isEmpty()) {
            return new Card(CardColor.NONE, CardSymbol.NONE);
        }

        return cards.stream().toList().get(cards.size() - 2);
    }

    private final Deque<Card> cards;
}

package com.akos.uno.game;

import java.util.*;

// sources:
// - https://www.geeksforgeeks.org/stack-class-in-java/
public class CardPile implements Iterable<Card> {
    public CardPile() {
        cards = new Stack<>();
    }

    // adds cards to the top of the pile
    public void pushCards(Collection<Card> cards) {
        for (Card card : cards) {
            this.cards.push(card);
        }
    }

    // removes n cards from the top of the pile
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

    // adds a single card to the top of the pile
    public void pushCard(Card card) {
        cards.push(card);
    }

    // removes and returns a single card from the top of the pile
    public Card popCard() {
        if (this.cards.isEmpty()) {
            throw new IllegalStateException("CardPile is empty.");
        }

        return cards.pop();
    }

    public void shuffle() {
        List<Card> cardsList = new ArrayList<>(cards);
        Collections.shuffle(cardsList);

        cards.clear();
        for (Card card : cardsList) {
            cards.push(card);
        }
    }

    public int size() {
        return cards.size();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public void clear() {
        cards.clear();
    }

    public Iterator<Card> iterator() {
        return cards.iterator();
    }

    public Card top() {
        return cards.peek();
    }

    private Stack<Card> cards;
}

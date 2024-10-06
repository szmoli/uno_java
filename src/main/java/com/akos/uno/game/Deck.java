package com.akos.uno.game;

import java.util.List;
import java.util.ArrayList;

public class Deck {
    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    List<Card> cards;
}

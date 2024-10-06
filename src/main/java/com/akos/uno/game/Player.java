package com.akos.uno.game;

import java.util.List;

public class Player {
    public void drawCard(Card card) {
        hand.add(card);
    }

    public void playCard(Card card) {
        hand.remove(card);
    }

    private List<Card> hand;
    private String playerName;
    boolean hasSaidUno;
}

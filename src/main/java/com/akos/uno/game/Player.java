package com.akos.uno.game;

import java.util.ArrayList;
import java.util.List;

public class Player {
    public Player(String playerName) {
        this.playerName = playerName;
        this.hand = new ArrayList<>();
        this.hasSaidUno = false;
        this.canDiscard = false;
    }

    public boolean drawCards(List<Card> cards) {
        return hand.addAll(cards);
    }

    public boolean drawCard(Card card) {
        return hand.add(card);
    }

    public boolean discardCard(Card card) {
        return hand.remove(card);
    }

    public List<Card> getHand() {
        return hand;
    }

    public boolean canDiscard() {
        return canDiscard;
    }

    public void setCanDiscard(boolean state) {
        canDiscard = state;
    }

    public String getPlayerName() {
        return playerName;
    }

    private List<Card> hand;
    private String playerName;
    boolean hasSaidUno;
    boolean canDiscard;
}

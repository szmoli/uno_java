package com.akos.uno.game;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        return this.playerName.equals(((Player) other).playerName);
    }

    private List<Card> hand;
    private String playerName;
    private boolean hasSaidUno;
    private boolean canDiscard;
    private static final Logger logger = LogManager.getLogger(); 
}

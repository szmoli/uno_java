package com.akos.uno.game;

public class Card {
    public Card(CardColor color, CardSymbol symbol) {
        this.color = color;
        this.symbol = symbol;
    }

    public CardColor getColor() {
        return color;
    }

    public CardSymbol getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "Card{" + color.name() + " " + symbol.name() + "}";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || this.getClass() != other.getClass()) {
            return false;
        } 

        Card otherCard = (Card) other;
        return this.getColor() == otherCard.getColor() && this.getSymbol() == otherCard.getSymbol();
    }

    private CardColor color;
    private CardSymbol symbol;
}

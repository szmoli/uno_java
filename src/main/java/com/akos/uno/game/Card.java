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

    private CardColor color;
    private CardSymbol symbol;
}

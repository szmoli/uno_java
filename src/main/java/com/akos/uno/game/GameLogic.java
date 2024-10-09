package com.akos.uno.game;

import java.util.List;

public class GameLogic {
    public boolean addPlayer(Player player) {
        if (game.getPlayers().size() >= 10) {
            throw new IllegalStateException("Maximum player capacity reached.");
        }

        return game.getPlayers().add(player);
    }

    public boolean removePlayer(Player player) {
        return game.getPlayers().remove(player);
    }

    public Card topDiscardPile() {
        return game.getDeck().getDiscardPile().top();
    }

    public void addCardToDiscardPile(Card card) {
        Card topCard = topDiscardPile();

        // check if the card can be discarded according to the rules
        if (card.getColor() != topCard.getColor() && card.getSymbol() != topCard.getSymbol()) {
            throw new IllegalStateException("This card cannot be played right now.");
        }

        game.getDeck().addCardToDiscardPile(card);
    }

    public boolean drawCards(Player player, int n) {
        return player.drawCards(game.getDeck().drawCards(n));
    }

    public void selectNextPlayer() {
        if (game.isOrderReversed()) {
            game.setCurrentPlayerIndex((game.getCurrentPlayerIndex() - 1) % game.getPlayers().size()); // modulo to wrap around the list if the index goes out of range
        } else {
            game.setCurrentPlayerIndex((game.getCurrentPlayerIndex() + 1) % game.getPlayers().size());
        }

        // check if player can discard a card
        // todo: implement it in another place
//        Player player = game.getPlayers().get(game.getCurrentPlayerIndex());
//        player.setCanDiscard(game.getRules().canDiscard(player, topDiscardPile()));
    }

    public boolean applyPenaltyToPlayer(Player player, int numOfCardsToBeDrawn) {
        return player.drawCards(game.getDeck().drawCards(numOfCardsToBeDrawn));
    }

    public void setGameState(GameState state) {
        game.setState(state);
    }

    private GameModel game;
}

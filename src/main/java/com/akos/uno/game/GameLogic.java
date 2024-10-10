package com.akos.uno.game;

import com.akos.uno.server.Server;

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
    }

    public Player getPreviousPlayer() {
        if (game.isOrderReversed()) {
            return game.getPlayers().get((game.getCurrentPlayerIndex() + 1) % game.getPlayers().size()); // modulo to wrap around the list if the index goes out of range
        } else {
            return game.getPlayers().get((game.getCurrentPlayerIndex() - 1) % game.getPlayers().size()); // modulo to wrap around the list if the index goes out of range
        }
    }

    public void startGame() {
        game.setState(GameState.IN_PROGRESS);

        while (game.getState() != GameState.FINISHED) {
            Player previousPlayer = getPreviousPlayer();
            Player player = game.getPlayers().get(game.getCurrentPlayerIndex());
//            player.playTurn(deck);
            game.getRules().enforceForgotToSayUNOPenalty(previousPlayer, game.getDeck());
            selectNextPlayer();
        }
    }

    private GameModel game;
    private Server server;
}

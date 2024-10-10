package com.akos.uno.game;

import com.akos.uno.server.Server;
import java.util.List;

public class GameLogic {
    public boolean addPlayer(Player player) {
        FullGameState gameState = game.getGameState();

        if (gameState.getPlayers().size() >= game.getRules().getMaxPlayerCount()) {
            throw new IllegalStateException("Maximum player capacity reached.");
        }

        return gameState.getPlayers().add(player);
    }

    public boolean removePlayer(Player player) {
        return game.getGameState().getPlayers().remove(player);
    }

    public Card getTopCard() {
        return game.getGameState().getDeck().getDiscardPile().top();
    }

    public void addCardToDiscardPile(Card card) {
        Card topCard = getTopCard();

        if (!game.getRules().isValidMove(card, topCard)) {
            throw new IllegalStateException("Illegal move.");
        }

        game.getGameState().getDeck().addCardToDiscardPile(card);
    }

    public List<Card> drawCards(int n) {
        return game.getGameState().getDeck().drawCards(n);
    }

    public void selectNextPlayer() {
        FullGameState gameState = game.getGameState();

        if (gameState.isOrderReversed()) {
            gameState.setCurrentPlayerIndex((gameState.getCurrentPlayerIndex() - 1) % gameState.getPlayers().size()); // modulo to wrap around the list if the index goes out of range
        } else {
            gameState.setCurrentPlayerIndex((gameState.getCurrentPlayerIndex() + 1) % gameState.getPlayers().size());
        }
    }

    public Player getPreviousPlayer() {
        FullGameState gameState = game.getGameState();

        if (gameState.isOrderReversed()) {
            return gameState.getPlayers().get((gameState.getCurrentPlayerIndex() + 1) % gameState.getPlayers().size()); // modulo to wrap around the list if the index goes out of range
        } else {
            return gameState.getPlayers().get((gameState.getCurrentPlayerIndex() - 1) % gameState.getPlayers().size()); // modulo to wrap around the list if the index goes out of range
        }
    }

    public void startGame() {
        FullGameState gameState = game.getGameState();

        gameState.setGameStatus(GameStatus.IN_PROGRESS);

        while (gameState.getGameStatus() != GameStatus.FINISHED) {
            Player previousPlayer = getPreviousPlayer();
            Player player = gameState.getPlayers().get(gameState.getCurrentPlayerIndex());
//            player.playTurn(deck);
            game.getRules().enforceForgotToSayUNOPenalty(previousPlayer, gameState.getDeck());
            selectNextPlayer();
        }
    }

    private GameModel game;
    private Server server;
}

package com.akos.uno.game;

import java.util.List;

public class GameController {
    public GameController() {
        this.game = new Game();
    }

    public Game getGame() {
        return game;
    }

    public Player addPlayer(Player player) {
        FullGameState gameState = game.getState();

        if (gameState.getPlayers().size() >= 10) {
            throw new IllegalStateException("Maximum player capacity reached.");
        }

        return gameState.getPlayers().put(player.getPlayerName(), player);
    }

    public Player removePlayer(Player player) {
        return game.getState().getPlayers().remove(player.getPlayerName());
    }

    public Card getTopCard() {
        return game.getState().getDeck().getDiscardPile().top();
    }

    public boolean addCardToDiscardPile(Card card) {
        Card topCard = getTopCard();

        if (!game.getRules().isValidMove(card)) {
            return false;
        }

        game.getState().getDeck().addCardToDiscardPile(card);
        return true;
    }

    public List<Card> drawCards(int n) {
        return game.getState().getDeck().drawCards(n);
    }

    public void selectNextPlayer() {
        FullGameState gameState = game.getState();

        if (gameState.isOrderReversed()) {
            gameState.setCurrentPlayerIndex((gameState.getCurrentPlayerIndex() - 1) % gameState.getPlayers().size()); // modulo to wrap around the list if the index goes out of range
        } else {
            gameState.setCurrentPlayerIndex((gameState.getCurrentPlayerIndex() + 1) % gameState.getPlayers().size());
        }
    }

    public Player getPreviousPlayer() {
        FullGameState gameState = game.getState();

        if (gameState.isOrderReversed()) {
            return gameState.getPlayers().get((gameState.getCurrentPlayerIndex() + 1) % gameState.getPlayers().size()); // modulo to wrap around the list if the index goes out of range
        } else {
            return gameState.getPlayers().get((gameState.getCurrentPlayerIndex() - 1) % gameState.getPlayers().size()); // modulo to wrap around the list if the index goes out of range
        }
    }

    public void startGame() {
        FullGameState gameState = game.getState();

        gameState.setGameStatus(GameStatus.IN_PROGRESS);

        while (gameState.getGameStatus() != GameStatus.FINISHED) {
            Player previousPlayer = getPreviousPlayer();
            Player player = gameState.getPlayers().get(gameState.getCurrentPlayerIndex());
//            player.playTurn(deck);
//            game.getRules().enforceForgotToSayUNOPenalty(previousPlayer, gameState.getDeck());
            selectNextPlayer();
        }
    }

    private Game game;
}

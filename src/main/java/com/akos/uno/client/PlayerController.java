package com.akos.uno.client;

import java.util.List;

import com.akos.uno.communication.action.DiscardCardAction;
import com.akos.uno.communication.action.DrawCardAction;
import com.akos.uno.communication.action.JoinAction;
import com.akos.uno.communication.action.StartAction;
import com.akos.uno.game.Card;
import com.akos.uno.game.CardColor;
import com.akos.uno.game.CardSymbol;
import com.akos.uno.game.Player;

public class PlayerController {
    public PlayerController(ClientController clientController) {
        this.clientController = clientController;
    }

    public Player getPlayer() {
        return clientController.getClient().getGameState().getPlayer();
    }

    public List<Card> getHand() {
        return getPlayer().getHand();
    }

    public void joinGame() {
        try {
            clientController.getClient().getConnectionLatch().await();            
        } catch (InterruptedException e) {
        }
        clientController.getClient().sendMessageToServer(new JoinAction(getPlayer().getPlayerName()).getAsJson());
    }

    public void startGame() {
        clientController.getClient().sendMessageToServer(new StartAction(getPlayer().getPlayerName()).getAsJson());
    }

    public void drawCards(int n) {
        clientController.getClient().sendMessageToServer(new DrawCardAction(getPlayer().getPlayerName(), n).getAsJson());
    }

    public void discardCard(Card card) {
        clientController.getClient().sendMessageToServer(new DiscardCardAction(getPlayer().getPlayerName(), card).getAsJson());
    }

    public void discardCard(int index) {
        if (getPlayer().getHand().isEmpty()) {
            return;
        }

        Card card = getPlayer().getHand().get(index);
        clientController.getClient().sendMessageToServer(new DiscardCardAction(getPlayer().getPlayerName(), card).getAsJson());
    }

    public void discardWildCard(Card card, CardColor desiredColor) {
        if (card.getSymbol() != CardSymbol.WILD || card.getSymbol() != CardSymbol.WILD_FOUR) {
            throw new IllegalStateException("discardWildCard() called on non-wild card");
        }

        
    }

    public void discardWildCard(int i, CardColor desiredColor) {
        
    }

    private final ClientController clientController;
}

package com.akos.uno.client;

import java.util.List;

import com.akos.uno.communication.action.ChallengePlayerAction;
import com.akos.uno.communication.action.DiscardCardAction;
import com.akos.uno.communication.action.DrawCardAction;
import com.akos.uno.communication.action.JoinAction;
import com.akos.uno.communication.action.QuitAction;
import com.akos.uno.communication.action.SayUnoAction;
import com.akos.uno.communication.action.StartAction;
import com.akos.uno.game.Card;
import com.akos.uno.game.CardColor;
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

    public void discardCard(Card card, CardColor desiredColor) {
        clientController.getClient().sendMessageToServer(new DiscardCardAction(getPlayer().getPlayerName(), card, desiredColor).getAsJson());
    }

    public void discardCard(int index, CardColor desiredColor) {
        if (getPlayer().getHand().isEmpty()) {
            return;
        }

        Card card = getPlayer().getHand().get(index);
        clientController.getClient().sendMessageToServer(new DiscardCardAction(getPlayer().getPlayerName(), card, desiredColor).getAsJson());
    }

    public void sayUno() {
        clientController.getClient().sendMessageToServer(new SayUnoAction(getPlayer().getPlayerName()).getAsJson());
    }

    public boolean hasSaidUno() {
        return getPlayer().hasSaidUno();
    }

    public void quitGame() {
        clientController.getClient().sendMessageToServer(new QuitAction(getPlayer().getPlayerName()).getAsJson());
    }

    public void challengePlayer() {
        clientController.getClient().sendMessageToServer(new ChallengePlayerAction(getPlayer().getPlayerName()).getAsJson());
    }

    private final ClientController clientController;
}

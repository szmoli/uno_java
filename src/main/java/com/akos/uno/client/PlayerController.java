package com.akos.uno.client;

import java.util.List;

import com.akos.uno.communication.action.DrawCardAction;
import com.akos.uno.communication.action.JoinAction;
import com.akos.uno.communication.action.StartAction;
import com.akos.uno.game.Card;
import com.akos.uno.game.Player;

public class PlayerController {
    public PlayerController(ClientController clientController) {
        this.clientController = clientController;
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

    private final ClientController clientController;

    private Player getPlayer() {
        return clientController.getClient().getGameState().getPlayer();
    }
}

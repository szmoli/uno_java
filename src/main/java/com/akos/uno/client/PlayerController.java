package com.akos.uno.client;

import com.akos.uno.communication.action.JoinAction;
import com.akos.uno.communication.action.StartAction;
import com.akos.uno.game.Player;

public class PlayerController {
    public PlayerController(Player player, ClientController clientController) {
        this.clientController = clientController;
        this.player = player;
    }

    public void joinGame() {
        try {
            clientController.getClient().getConnectionLatch().await();            
        } catch (InterruptedException e) {
        }
        clientController.getClient().sendMessageToServer(new JoinAction(player.getPlayerName()).getAsJson());
    }

    public void startGame() {
        clientController.getClient().sendMessageToServer(new StartAction(player.getPlayerName()).getAsJson());
    }

    private Player player;
    private ClientController clientController;
}

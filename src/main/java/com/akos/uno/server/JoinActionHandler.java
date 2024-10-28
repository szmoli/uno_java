package com.akos.uno.server;

import com.akos.uno.communication.action.GameAction;
import com.akos.uno.communication.action.GameActionType;
import com.akos.uno.communication.action.JoinAction;
import com.akos.uno.communication.response.PartialGameStateResponse;
import com.akos.uno.game.GameController;
import com.akos.uno.game.GameModel;
import com.akos.uno.game.PartialGameState;
import com.akos.uno.game.Player;

public class JoinActionHandler implements GameActionHandler<JoinAction> {
    public JoinActionHandler(GameController gameController, Server server) {
        this.gameController = gameController;
        this.server = server;
    }

    @Override
    public void handle(JoinAction action) {
        GameModel game = gameController.getGame();
        String playerName = action.getPlayerName();
        Player player = new Player(playerName);
        game.getGameState().getPlayers().put(playerName, player);
        server.broadcastMessage(new PartialGameStateResponse(new PartialGameState(player, game.getGameState())).getAsJson());
    }

    private GameController gameController;
    private Server server;
}

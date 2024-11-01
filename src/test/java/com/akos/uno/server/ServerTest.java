package com.akos.uno.server;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.akos.uno.asserts.PartialGameStateAssert;
import com.akos.uno.client.ClientController;
import com.akos.uno.game.GameStatus;
import com.akos.uno.game.Player;

public class ServerTest {
    private CountDownLatch latch;
    private Server server;
    private ClientController client1;
    private ClientController client2;
    private ClientController client3;
    private static final Logger logger = LogManager.getLogger();

    @BeforeEach
    void createServerAndClients() {
        latch = new CountDownLatch(1);
        server = new Server(12345, latch);
        client1 = new ClientController("player1", "127.0.0.1", 12345, latch);
        client2 = new ClientController("player2", "127.0.0.1", 12345, latch);
        client3 = new ClientController("player3", "127.0.0.1", 12345, latch);
    }

    @Test
    void serverConnectionTest() {
        server.start();
        client1.start();
        client2.start();
        client3.start();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }       

        assertEquals(3, server.getClients().size());
        Player player1 = server.getGameController().getGame().getState().getPlayers().get("player1");
        PartialGameStateAssert.assertThat(client1.getClient().getGameState())
            .hasPlayer(player1)
            .hasOtherPlayerNames(server.getGameController().getOtherPlayerNames(player1))
            .hasOtherPlayerHandSizes(server.getGameController().getOtherPlayerHandSizes(player1))
            .hasGameStatus(GameStatus.OPEN);
        Player player2 = server.getGameController().getGame().getState().getPlayers().get("player2");
        PartialGameStateAssert.assertThat(client2.getClient().getGameState())
                .hasPlayer(player2)
                .hasOtherPlayerNames(server.getGameController().getOtherPlayerNames(player2))
                .hasOtherPlayerHandSizes(server.getGameController().getOtherPlayerHandSizes(player2))
                .hasGameStatus(GameStatus.OPEN);
        Player player3 = server.getGameController().getGame().getState().getPlayers().get("player3");
        PartialGameStateAssert.assertThat(client3.getClient().getGameState())
            .hasPlayer(player3)
            .hasOtherPlayerNames(server.getGameController().getOtherPlayerNames(player3))
            .hasOtherPlayerHandSizes(server.getGameController().getOtherPlayerHandSizes(player3))
            .hasGameStatus(GameStatus.OPEN);
    }

    @AfterEach
    void closeServerAndClients() {
        client1.stopConnection();
        client2.stopConnection();
        client3.stopConnection();
        server.stopServer();
    }
}

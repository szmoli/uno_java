package com.akos.uno.server;
import java.util.ArrayList;
import java.util.List;

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
    private Server server;
    private ClientController client1;
    private ClientController client2;
    private ClientController client3;
    private static final Logger logger = LogManager.getLogger();

    @BeforeEach
    void createServerAndClients() {
        server = new Server(12345);
        client1 = new ClientController("player1", "127.0.0.1", 12345, server.getReadyLatch());
        client2 = new ClientController("player2", "127.0.0.1", 12345, server.getReadyLatch());
        client3 = new ClientController("player3", "127.0.0.1", 12345, server.getReadyLatch());
    }

    @AfterEach
    void closeServerAndClients() {
        client1.stopConnection();
        client2.stopConnection();
        client3.stopConnection();
        server.stopServer();
    }

    @Test
    void validJoinTest() {
        server.start();
        client1.start();
        client2.start();
        client3.start();

        client1.getPlayerController().joinGame();
        client2.getPlayerController().joinGame();
        client3.getPlayerController().joinGame();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
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

    @Test
    void invalidJoinTest1() {
        server.start();
        client1.start();
        client1.getPlayerController().joinGame();
        ClientController duplicateClient = new ClientController("player1", "127.0.0.1", 12345, server.getReadyLatch());
        duplicateClient.start();
        duplicateClient.getPlayerController().joinGame();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }

        assertEquals(1, server.getClients().size());
        Player player1 = server.getGameController().getGame().getState().getPlayers().get("player1");
        PartialGameStateAssert.assertThat(client1.getClient().getGameState())
            .hasPlayer(player1)
            .hasOtherPlayerNames(new ArrayList<>())
            .hasOtherPlayerHandSizes(new ArrayList<>())
            .hasGameStatus(GameStatus.OPEN);
    }

    @Test
    void invalidJoinTest2() {
        ClientController client4 = new ClientController("player4", "127.0.0.1", 12345, server.getReadyLatch());
        ClientController client5 = new ClientController("player5", "127.0.0.1", 12345, server.getReadyLatch());
        ClientController client6 = new ClientController("player6", "127.0.0.1", 12345, server.getReadyLatch());
        ClientController client7 = new ClientController("player7", "127.0.0.1", 12345, server.getReadyLatch());
        ClientController client8 = new ClientController("player8", "127.0.0.1", 12345, server.getReadyLatch());
        ClientController client9 = new ClientController("player9", "127.0.0.1", 12345, server.getReadyLatch());
        ClientController client10 = new ClientController("player10", "127.0.0.1", 12345, server.getReadyLatch());
        ClientController client11 = new ClientController("player11", "127.0.0.1", 12345, server.getReadyLatch());

        List<ClientController> ccList = new ArrayList<>(List.of(client1, client2, client3, client4, client5, client6, client7, client8, client9, client10));
        
        server.start();
        client1.start();
        client2.start();
        client3.start();
        client4.start();
        client5.start();
        client6.start();
        client7.start();
        client8.start();
        client9.start();
        client10.start();
        client11.start();
        client1.getPlayerController().joinGame();
        client2.getPlayerController().joinGame();
        client3.getPlayerController().joinGame();
        client4.getPlayerController().joinGame();
        client5.getPlayerController().joinGame();
        client6.getPlayerController().joinGame();
        client7.getPlayerController().joinGame();
        client8.getPlayerController().joinGame();
        client9.getPlayerController().joinGame();
        client10.getPlayerController().joinGame();
        client11.getPlayerController().joinGame();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }

        assertEquals(10, server.getClients().size());
        
        for (ClientController cc : ccList) {
            Player p = server.getGameController().getGame().getState().getPlayers().get(cc.getClient().getGameState().getPlayer().getPlayerName());
            PartialGameStateAssert.assertThat(cc.getClient().getGameState())
                .hasPlayer(p)
                .hasOtherPlayerNames(server.getGameController().getOtherPlayerNames(p))
                .hasOtherPlayerHandSizes(server.getGameController().getOtherPlayerHandSizes(p))
                .hasGameStatus(GameStatus.OPEN);
        }
    }
}

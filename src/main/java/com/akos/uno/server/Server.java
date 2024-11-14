package com.akos.uno.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.communication.action.GameAction;
import com.akos.uno.communication.action.GameActionType;
import com.akos.uno.communication.response.InvalidActionResponse;
import com.akos.uno.communication.response.MessageResponse;
import com.akos.uno.communication.response.PartialGameStateResponse;
import com.akos.uno.game.GameController;
import com.akos.uno.game.PartialGameState;
import com.akos.uno.game.Player;

// sources:
// - https://www.geeksforgeeks.org/multithreaded-servers-in-java/
// - https://betterstack.com/community/guides/logging/how-to-start-logging-with-log4j/
public class Server extends Thread {
    public Server(int port) {
        this.port = port;
        this.gameController = new GameController();
        gameActionHandlers.put(GameActionType.CHALLENGE_PLAYER, new ChallengePlayerActionHandler(gameController, this));
        gameActionHandlers.put(GameActionType.DISCARD_CARD, new DiscardCardActionHandler(gameController, this));
        gameActionHandlers.put(GameActionType.DRAW_CARD, new DrawCardActionHandler(gameController, this));
        gameActionHandlers.put(GameActionType.JOIN, new JoinActionHandler(gameController, this));
        gameActionHandlers.put(GameActionType.QUIT, new QuitActionHandler(gameController, this));
        gameActionHandlers.put(GameActionType.SAY_UNO, new SayUnoActionHandler(gameController, this));
        gameActionHandlers.put(GameActionType.START, new StartActionHandler(gameController, this));
    }

    @Override
    public void run() {
        startServer();
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            logger.info("Server is listening on port: {}", serverSocket.getLocalPort());
            readyLatch.countDown();

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                clientSocket.setKeepAlive(true); // keep alive connection because of the real-time nature of UNO
                logger.info("New client connected");

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String playerName = in.readLine();

                if (clients.size() >= 10) {
                    out.println(new MessageResponse("server is full").getAsJson());
                    clientSocket.close();
                }

                if (playerName == null || playerName.equals("")) {
                    out.println(new MessageResponse("player name empty").getAsJson());
                    clientSocket.close();
                }

                if (!clients.containsKey(playerName)) {
                    clients.put(playerName, new ClientHandler(clientSocket, this));
                    clients.get(playerName).start();
                    clients.get(playerName).sendMessageToClient(new MessageResponse(playerName + " connected").getAsJson());
                } else {
                    out.println(new MessageResponse("player name already taken").getAsJson());
                    clientSocket.close();
                }  
            }
        } catch (IOException e) {
            logger.error("Error starting server: {}", e.getMessage());
            stopServer();
        }
    }

    public void stopServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            logger.error("Error stopping server: {}", e.getMessage());
        }
    }

    public synchronized void sendMessageToClient(ClientHandler clientHandler, String message) {
        clientHandler.sendMessageToClient(message);
    }

    public synchronized void broadcastMessage(String messageJson) {
        for (ClientHandler clientHandler : clients.values()) {
            clientHandler.sendMessageToClient(messageJson);
        }
    }

    public synchronized void updateClients() {
        logger.debug("updating clients");

        for (Player p : gameController.getPlayers().values()) {
            String message = new PartialGameStateResponse(new PartialGameState(p, gameController.getGame().getState())).getAsJson();
            
            clients.get(p.getPlayerName()).sendMessageToClient(message);
        }
    }

    public synchronized Map<String, ClientHandler> getClients() {
        return clients;
    }

    public synchronized GameController getGameController() {
        return gameController;
    }

    public synchronized CountDownLatch getReadyLatch() {
        return readyLatch;
    }

    public synchronized void processMessage(String message, ClientHandler clientHandler) {
        logger.debug("Processing message: {}", message);
        GameAction action = GameAction.createFromJson(message);

        if (gameActionHandlers.containsKey(action.getType())) {
            gameActionHandlers.get(action.getType()).handleAction(action);
        } else {
            clientHandler.sendMessageToClient(new InvalidActionResponse().getAsJson());
        }
    }

    public synchronized void disconnectClient(String playerName) {
        if (playerName == null) {
            logger.error("player name was null");
            return;
        }

        ClientHandler clientHandler = clients.get(playerName);

        if (clientHandler == null) {
            logger.error("client handler not found for {}", playerName);
            return;
        }

        if (clientHandler.getSocket() == null || clientHandler.getSocket().isClosed()) {
            logger.error("socket was null or is already closed for {}", playerName);
            return;
        }

        try {
            clientHandler.getSocket().close();
            clients.remove(playerName);
        } catch (IOException e) {
            logger.error("error disconnecting client: {}", e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server(12345);
        server.startServer();
    }

    private final int port;
    private ServerSocket serverSocket;
    private final HashMap<String, ClientHandler> clients = new HashMap<>();
    private final GameController gameController;
    private final Map<GameActionType, GameActionHandler<?>> gameActionHandlers = new EnumMap<>(GameActionType.class);
    private final Logger logger = LogManager.getLogger();
    private final CountDownLatch readyLatch = new CountDownLatch(1);
}

package com.akos.uno.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.akos.uno.communication.response.PartialGameStateResponse;
import com.akos.uno.game.PartialGameState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {
    public void startConnection(String address, int port) {
        try {
            clientSocket = new Socket(address, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            clientLogger.error("Error initializing client socket: {}", e.getMessage());
        }
    }

    public void stopConnection() {
        try {
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            clientLogger.error("Error closing connection: {}", e.getMessage());
        }
    }

    public void sendMessageToServer(String message) {
        if (out == null) {
            clientLogger.error("Error sending message to server.");
        }

        out.println(message);
    }

    public String getResponseFromServer() throws IOException {
        return in.readLine();
    }

    public void setGameState(PartialGameState gameState) {
        this.gameState = gameState;
    }

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private PartialGameState gameState;
    protected Logger clientLogger = LogManager.getLogger();
}

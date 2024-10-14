package com.akos.uno.client;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientListener extends Thread {
    public ClientListener(Client client, ClientController controller) {
        this.client = client;
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            // Continuously listen for messages from the server
            while (!client.getSocket().isClosed()) {
                String serverResponse = client.getResponseFromServer();

                if (serverResponse != null) {
                    processResponse(serverResponse);
                }
            }
        } catch (IOException e) {
            logger.error("Error while listening: {}", e.getMessage());
        }
    }

    private Client client;
    private ClientController controller;
    private static final Logger logger = LogManager.getLogger();

    private void processResponse(String response) {
        logger.debug("Got response from server: {}", response);
    }
}

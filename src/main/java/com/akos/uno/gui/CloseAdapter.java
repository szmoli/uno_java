package com.akos.uno.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.akos.uno.client.ClientController;

/**
 * CloseAdapter
 * Sends a quit game request to the server when the window is closed.
 * Exits the application.
 */
public class CloseAdapter extends WindowAdapter {
    public CloseAdapter(ClientController clientController, JFrame frame) {
        this.clientController = clientController;
        this.frame = frame;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        clientController.getPlayerController().quitGame();
        frame.dispose();
        System.exit(0);
    }

    private final ClientController clientController;
    private final JFrame frame;
}

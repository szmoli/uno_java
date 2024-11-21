package com.akos.uno.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * MainWindow
 * Main window for the application.
 * Displays the main menu panel.
 * Exits the application on window close.
 */
public class MainWindow {
    /**
     * Get the frame
     * @return The frame
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Main method
     * Create the main window
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Card Game");
            
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setContentPane(new MainMenuPanel(frame).getPanel());
            frame.pack();
            frame.setLocationByPlatform(true);
            frame.setVisible(true);
        });
    }

    private static JFrame frame;
}

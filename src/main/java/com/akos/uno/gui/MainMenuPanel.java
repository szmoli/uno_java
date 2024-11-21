package com.akos.uno.gui;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * MainMenuPanel
 * Panel for the main menu.
 * Allows the user to join or host a game.
 * Switches to the join game or host game panel on button press.
 * Exits the application on window close.
 * Source: https://stackoverflow.com/questions/15867148/why-do-we-need-to-extend-jframe-in-a-swing-application
 */
public class MainMenuPanel extends WindowContentPanel {
    public MainMenuPanel(JFrame frame) {
        super(new JPanel(), frame);
        getFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        joinGameButton = new JButton("Join Game");
        joinGameButton.addActionListener(actionEvent -> {
            frame.setContentPane(new JoinGameMenuPanel(frame).getPanel());
            frame.pack();
        });

        hostGameButton = new JButton("Host Game");
        hostGameButton.addActionListener(actionEvent -> {
            frame.setContentPane(new HostGameMenuPanel(frame).getPanel());
            frame.pack();
        });

        super.getPanel().setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        super.getPanelComponents().addAll(List.of(joinGameButton, hostGameButton));
        for (JComponent component : super.getPanelComponents()) {
            super.getPanel().add(component);
        }
    }

    private final JButton joinGameButton;
    private final JButton hostGameButton;
}

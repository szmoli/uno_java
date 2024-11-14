package com.akos.uno.gui;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

// source: https://stackoverflow.com/questions/15867148/why-do-we-need-to-extend-jframe-in-a-swing-application
public class MainMenuPanel extends WindowContentPanel {
    public MainMenuPanel(JFrame frame) {
        super(new JPanel(), frame);
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

    public JPanel getPanel() {
        return super.getPanel();
    }

    private JButton joinGameButton;
    private JButton hostGameButton;
}

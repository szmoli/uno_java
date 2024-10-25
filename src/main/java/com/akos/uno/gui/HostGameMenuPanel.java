package com.akos.uno.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HostGameMenuPanel extends WindowContentPanel {
    public HostGameMenuPanel(JFrame frame) {
        super(new JPanel(), frame);

        serverPortLabel = new JLabel("Server port:");
        serverPortInput = new JTextField();
        serverPortInput.setPreferredSize(new Dimension(100, 24));

        hostButton = new JButton("Host Game");
        hostButton.addActionListener(actionEvent -> {
            // TODO: start server and join it
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(actionEvent -> {
            frame.setContentPane(new MainMenuPanel(frame).getPanel());
            frame.pack();
        });

        super.getPanel().setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        super.getPanelComponents().addAll(List.of(serverPortLabel, serverPortInput, hostButton, cancelButton));
        for (JComponent component : super.getPanelComponents()) {
            super.getPanel().add(component);
        }
    }

    public JPanel getPanel() {
        return super.getPanel();
    }

    private JLabel serverPortLabel;
    private JTextField serverPortInput;
    private JButton hostButton;
    private JButton cancelButton;
}

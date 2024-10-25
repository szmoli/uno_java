package com.akos.uno.gui;

import javax.swing.*;
import java.util.List;

public class HostGameMenuPanel extends WindowContentPanel {
    public HostGameMenuPanel() {
        super(new JPanel());
        serverAddressLabel = new JLabel("Server address and port:");
        serverAddressInput = new JTextField();
        hostButton = new JButton("Host Game");
        cancelButton = new JButton("Cancel");

        super.getMainPanel().setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        super.getMainPanelComponents().addAll(List.of(serverAddressLabel, serverAddressInput, hostButton, cancelButton));
        for (JComponent component : super.getMainPanelComponents()) {
            super.getMainPanel().add(component);
        }
    }

    public JPanel getMainPanel() {
        return super.getMainPanel();
    }

    private JLabel serverAddressLabel;
    private JTextField serverAddressInput;
    private JButton hostButton;
    private JButton cancelButton;
}

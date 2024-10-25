package com.akos.uno.gui;

import javax.swing.*;
import java.util.*;

// source: https://stackoverflow.com/questions/15867148/why-do-we-need-to-extend-jframe-in-a-swing-application
public class MainMenuPanel extends WindowContentPanel {
    public MainMenuPanel() {
        super(new JPanel());
        joinGameButton = new JButton("Join Game");
        hostGameButton = new JButton("Host Game");
        rulesetEditorButton = new JButton("Ruleset Editor");
        exitButton = new JButton("Exit");

        super.getMainPanel().setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        super.getMainPanelComponents().addAll(List.of(joinGameButton, hostGameButton, rulesetEditorButton, exitButton));
        for (JComponent component : super.getMainPanelComponents()) {
            super.getMainPanel().add(component);
        }
    }

    public JPanel getMainPanel() {
        return super.getMainPanel();
    }

    private JButton joinGameButton;
    private JButton hostGameButton;
    private JButton rulesetEditorButton;
    private JButton exitButton;
}

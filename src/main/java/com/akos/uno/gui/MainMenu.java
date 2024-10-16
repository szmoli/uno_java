package com.akos.uno.gui;

import javax.swing.*;

public class MainMenu extends JFrame {
    private JPanel panel;
    private JButton joinGameButton;
    private JButton hostsGameButton;
    private JButton editRulesetsButton;
    private JButton exitButton;

    public MainMenu() {
        setTitle("Unofficial UNO-like card game: Main Menu");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setSize(800, 600);
        setVisible(true);
        joinGameButton.addActionListener(e -> System.out.println("Join game clicked"));
        hostsGameButton.addActionListener(e -> System.out.println("Host game clicked"));
        editRulesetsButton.addActionListener(e -> System.out.println("Edit rulesets clicked"));
        exitButton.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu());
    }
}

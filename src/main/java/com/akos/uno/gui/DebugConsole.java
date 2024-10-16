package com.akos.uno.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public class DebugConsole extends JFrame {
    public DebugConsole() {
        setTitle("Unofficial UNO-like card game: Debug Console");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setContentPane(panel);
        setVisible(true);

        inputField.addActionListener(e -> executeCommand());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DebugConsole::new);
    }

    private JTextArea textArea;
    private JPanel panel;
    private JTextArea textArea1;
    private JTextField textField1;
    private JTextField inputField;
    private static final Logger logger = LogManager.getLogger();

    private void executeCommand() {
        String command = inputField.getText().trim();

        if (!command.isEmpty()) {
            appendString(">> " + command);
        }

        inputField.setText("");
    }

    private void appendString(String string) {
        textArea.append(string + "\n");
    }
}

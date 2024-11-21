package com.akos.uno.gui;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * PlayerNameVerifier
 * Verifies that the player name is not empty and is less than or equal to 16 characters.
 */
public class PlayerNameVerifier extends InputVerifier {
    /**
     * Verify that the player name is not empty and is less than or equal to 16 characters.
     * @param input The component to verify
     * @return True if the player name is not empty and is less than or equal to 16 characters, false otherwise
     */
    @Override
    public boolean verify(JComponent input) {
        String s = ((JTextField) input).getText();
        return !s.isEmpty() && s.length() <= 16;
    }
}

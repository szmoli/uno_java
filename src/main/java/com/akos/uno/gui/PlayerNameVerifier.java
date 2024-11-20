package com.akos.uno.gui;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class PlayerNameVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        String s = ((JTextField) input).getText();
        return !s.isEmpty() && s.length() <= 16;
    }
}

package com.akos.uno.gui;

import java.util.regex.Pattern;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class IPInputVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent component) {
        final String IP_PORT_REGEX = 
        "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?):([0-9]{1,5})$";
        final Pattern IP_PORT_PATTERN = Pattern.compile(IP_PORT_REGEX);
        String input = ((JTextField) component).getText();
        
        return input != null && !input.isEmpty() && IP_PORT_PATTERN.matcher(input).matches();
    }
}
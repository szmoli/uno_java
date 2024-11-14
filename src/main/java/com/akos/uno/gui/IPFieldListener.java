package com.akos.uno.gui;

import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class IPFieldListener implements DocumentListener {
    public IPFieldListener(JTextField component) {
        this.component = component;
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        verify();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        verify();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        verify();
    }

    private final JTextField component;

    private boolean verify() {
        final String IP_PORT_REGEX = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?):([0-9]{1,5})$";
        final Pattern IP_PORT_PATTERN = Pattern.compile(IP_PORT_REGEX);
        String input = component.getText();
        
        return input != null && !input.isEmpty() && IP_PORT_PATTERN.matcher(input).matches();
    }
}

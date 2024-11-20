package com.akos.uno.gui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class InputFieldListener implements DocumentListener {
    public InputFieldListener(Runnable callback) {
        this.callback = callback;
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        callback.run();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        callback.run();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        callback.run();
    }

    private final Runnable callback;
}

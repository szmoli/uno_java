package com.akos.uno.gui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * InputFieldListener
 * Listens for changes in an input field and runs a callback when a change occurs.
 */
public class InputFieldListener implements DocumentListener {
    /**
     * InputFieldListener constructor
     * @param callback The callback to run when a change occurs
     */
    public InputFieldListener(Runnable callback) {
        this.callback = callback;
    }

    /**
     * Run the callback when a change occurs
     * @param e The document event
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        callback.run();
    }

    /**
     * Run the callback when a change occurs
     * @param e The document event
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        callback.run();
    }

    /**
     * Run the callback when a change occurs
     * @param e The document event
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        callback.run();
    }

    private final Runnable callback;
}

package com.librasys.utils;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPasswordField;

public class JPasswordFieldWithPlaceholder extends JPasswordField {
    private String placeholder;

    public JPasswordFieldWithPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        setEchoChar((char) 0); // Make the characters visible initially
        setForeground(Color.GRAY);
        setText(placeholder);

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (isPlaceholder()) {
                    setText("");
                    setEchoChar('•'); // Set the echo char to '*' for password field
                    setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(getPassword()).isEmpty()) {
                    setText(placeholder);
                    setEchoChar((char) 0);
                    setForeground(Color.GRAY);
                }
            }
        });
    }

    private boolean isPlaceholder() {
        return new String(getPassword()).equals(placeholder);
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        setText(placeholder);
        setForeground(Color.GRAY);
    }
}

package com.librasys;

import javax.swing.UIManager;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatArcOrangeIJTheme());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
    }
}
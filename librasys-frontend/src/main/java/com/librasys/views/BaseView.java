package com.librasys.views;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public abstract class BaseView extends JFrame {
    public BaseView() {
        initComponents();
        initWindow();
    }

    protected abstract void initComponents();

    private void initWindow() {
        // To stop program when window closed
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        // To make window fullscreen
        setExtendedState(MAXIMIZED_BOTH);
        // To center the window when opened
        setLocationRelativeTo(null);
        // To set background to pure white
        setBackground(Color.decode("#FFFFFF"));
    }

    public void initView() {
        setVisible(true);
    }
}

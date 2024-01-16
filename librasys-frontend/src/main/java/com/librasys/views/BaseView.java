package com.librasys.views;

import javax.swing.JPanel;

public abstract class BaseView extends JPanel {
    public BaseView() {
        initComponents();
    }

    protected abstract void initComponents();
}

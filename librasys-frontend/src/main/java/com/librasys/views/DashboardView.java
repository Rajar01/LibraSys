package com.librasys.views;

import javax.swing.JLabel;

public class DashboardView extends BaseView {
    @Override
    protected void initComponents() {
        JLabel jLabel = new JLabel("Hello World!");
        add(jLabel);
    }
}

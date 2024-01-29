package com.librasys.core;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.librasys.controllers.BaseController;
import com.librasys.views.BaseView;

public enum ViewManager {
    INSTANCE;

    private JFrame frame;
    private Map<String, BaseView> views;

    ViewManager() {
        views = new HashMap<>();
    }

    public void addView(String viewName, BaseView view) {
        views.put(viewName, view);
    }

    public void setCurrentView(JPanel view, BaseController controller) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(view);
        frame.revalidate();
        frame.repaint();

        controller.initController();
    }

    public BaseView getView(String viewName) {
        return views.get(viewName);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}

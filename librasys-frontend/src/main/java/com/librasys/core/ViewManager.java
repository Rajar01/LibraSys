package com.librasys.core;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.librasys.views.BaseView;

public class ViewManager {
    private static ViewManager viewManager;
    private Map<String, BaseView> views;

    public ViewManager() {
        views = new HashMap<>();
    }

    public static ViewManager getViewManager() {
        if (viewManager == null) {
            viewManager = new ViewManager();
            return viewManager;
        } else {
            return viewManager;
        }
    }

    public void addView(String viewName, BaseView view) {
        views.put(viewName, view);
    }

    public void switchView(String oldViewName, String newViewName) {
        if (views.get(newViewName) == null) {
            Logger.getLogger(ViewManager.class.getName()).log(Level.SEVERE, "View not found");
            System.exit(1);
        } else {
            BaseView oldView = views.get(oldViewName);
            BaseView newView = views.get(newViewName);
            oldView.dispose();
            newView.initView();
        }
    }
}

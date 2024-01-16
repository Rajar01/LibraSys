package com.librasys.core;

import java.util.HashMap;
import java.util.Map;

import com.librasys.controllers.BaseController;

public enum ControllerManager {
    INSTANCE;

    private Map<String, BaseController> controllers;

    ControllerManager() {
        controllers = new HashMap<>();
    }

    public void addController(String controllerName, BaseController controller) {
        controllers.put(controllerName, controller);
    }

    public BaseController getController(String controllerName) {
        return controllers.get(controllerName);
    }
}

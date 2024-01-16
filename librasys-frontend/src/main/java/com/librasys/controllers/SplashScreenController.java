package com.librasys.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.librasys.core.ControllerManager;
import com.librasys.core.ViewManager;
import com.librasys.views.LoginView;

public class SplashScreenController extends BaseController {
    @Override
    public void initController() {
        try {
            Thread.sleep(3000);
            ViewManager.INSTANCE.setCurrentView(
                    ViewManager.INSTANCE.getView(LoginView.class.getName()),
                    ControllerManager.INSTANCE.getController(LoginController.class.getName()));
        } catch (InterruptedException e) {
            Logger.getLogger(SplashScreenController.class.getName()).log(Level.SEVERE, e.getMessage());
        }
    }
}

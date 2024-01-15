package com.librasys;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;

import com.librasys.core.ViewManager;
import com.librasys.views.BaseView;
import com.librasys.views.SplashScreenView;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatArcOrangeIJTheme());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        ViewManager viewManager = new ViewManager();

        BaseView splashScreenView = new SplashScreenView();

        viewManager.addView(splashScreenView.getClass().getName(), splashScreenView);

        try {
            splashScreenView.initView();
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, e.getMessage());
        }
    }
}
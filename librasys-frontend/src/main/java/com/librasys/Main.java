package com.librasys;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import com.librasys.controllers.DashboardController;
import com.librasys.controllers.LoginController;
import com.librasys.controllers.SplashScreenController;
import com.librasys.core.ControllerManager;
import com.librasys.core.FontManager;
import com.librasys.core.ViewManager;
import com.librasys.views.DashboardView;
import com.librasys.views.LoginView;
import com.librasys.views.SplashScreenView;

public class Main {
    public static void main(String[] args) {
        // Setting up the Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatLightFlatIJTheme());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        // Init window
        JFrame frame = new JFrame();
        // To stop program when window closed
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // To make window fullscreen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // To center the window when opened
        frame.setLocationRelativeTo(null);
        // To set background to pure white
        frame.setBackground(Color.decode("#FFFFFF"));
        ViewManager.INSTANCE.setFrame(frame);

        // Fonts
        FontManager.INSTANCE.addFont("Roboto-Bold.ttf");
        FontManager.INSTANCE.addFont("SpaceMono-Regular.ttf");
        FontManager.INSTANCE.addFont("Roboto-Medium.ttf");
        FontManager.INSTANCE.addFont("Roboto-Regular.ttf");
        FontManager.INSTANCE.addFont("Roboto-Black.ttf");
        FontManager.INSTANCE.addFont("Roboto-Thin.ttf");
        FontManager.INSTANCE.addFont("PassionOne-Regular.ttf");

        // Models
        // !Add Models Here

        // Views
        SplashScreenView splashScreenView = new SplashScreenView();
        LoginView loginView = new LoginView();
        DashboardView dashboardView = new DashboardView();

        // Controllers
        SplashScreenController splashScreenController = new SplashScreenController();
        LoginController loginController = new LoginController();
        DashboardController dashboardController = new DashboardController();

        // Add view to view manager
        ViewManager.INSTANCE.addView(splashScreenView.getClass().getName(), splashScreenView);
        ViewManager.INSTANCE.addView(loginView.getClass().getName(), loginView);
        ViewManager.INSTANCE.addView(dashboardView.getClass().getName(), dashboardView);

        // Add controller to controller manager
        ControllerManager.INSTANCE.addController(splashScreenController.getClass().getName(), splashScreenController);
        ControllerManager.INSTANCE.addController(loginController.getClass().getName(), loginController);
        ControllerManager.INSTANCE.addController(dashboardController.getClass().getName(), dashboardController);

        // Set frame to visible
        frame.setVisible(true);

        // Set current view to splash screen
        ViewManager.INSTANCE.setCurrentView(splashScreenView, splashScreenController);
    }
}
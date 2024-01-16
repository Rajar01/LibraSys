package com.librasys.controllers;

import java.awt.Color;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.librasys.core.ControllerManager;
import com.librasys.core.ViewManager;
import com.librasys.models.User;
import com.librasys.utils.JPasswordFieldWithPlaceholder;
import com.librasys.utils.JTextFieldWithPlaceholder;
import com.librasys.views.DashboardView;
import com.librasys.views.LoginView;

public class LoginController extends BaseController {
    private LoginView loginView;

    @Override
    public void initController() {
        this.loginView = (LoginView) ViewManager.INSTANCE.getView(LoginView.class.getName());

        this.loginView.getJButtonLogin().addActionListener(e -> login());
    }

    public void login() {
        String username = loginView.getJTextFieldWithPlaceholderUsername().getText();
        String password = new String(loginView.getJPasswordFieldWithPlaceholder().getPassword());

        User user = new User(username, password);
        Gson gson = new Gson();

        try {
            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/login"))
                    .POST(BodyPublishers.ofString(gson.toJson(user)))
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> postResponse = httpClient.send(postRequest, BodyHandlers.ofString());

            if (postResponse.statusCode() == HttpURLConnection.HTTP_OK) {
                ViewManager.INSTANCE.setCurrentView(
                        ViewManager.INSTANCE.getView(DashboardView.class.getName()),
                        ControllerManager.INSTANCE.getController(DashboardController.class.getName()));
            } else {
                JOptionPane.showMessageDialog(
                        loginView.getParent(),
                        "Kredensial tidak valid untuk username dan password",
                        "Invalid Credentials",
                        JOptionPane.ERROR_MESSAGE);

                JTextFieldWithPlaceholder jTextFieldWithPlaceholderUsername = loginView
                        .getJTextFieldWithPlaceholderUsername();
                jTextFieldWithPlaceholderUsername.setText(jTextFieldWithPlaceholderUsername.getPlaceholder());
                jTextFieldWithPlaceholderUsername.setForeground(Color.GRAY);

                JPasswordFieldWithPlaceholder jPasswordFieldWithPlaceholder = loginView
                        .getJPasswordFieldWithPlaceholder();
                jPasswordFieldWithPlaceholder.setText(jPasswordFieldWithPlaceholder.getPlaceholder());
                jPasswordFieldWithPlaceholder.setForeground(Color.GRAY);
                jPasswordFieldWithPlaceholder.setEchoChar((char) 0);
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, e.getMessage());
        }
    }
}

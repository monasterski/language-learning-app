package pl.edu.agh.languagelearningclient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainPageController {
    private AppController appController;

    @FXML
    Button loginButton;

    @FXML
    Button registerButton;

    public void initialize(AppController appController) {
        this.appController = appController;
    }

    @FXML
    public void handleLoginPageAction() throws IOException {
        appController.initLoginScene();
    }

    @FXML
    public void handleRegisterPageAction() throws IOException {
        appController.initRegisterScene();
    }
}

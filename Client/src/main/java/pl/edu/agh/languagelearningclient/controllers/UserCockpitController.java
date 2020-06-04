package pl.edu.agh.languagelearningclient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserCockpitController extends PageController {

    @FXML
    Label userName;
    @FXML
    Button goBackButton;

    public void initialize(AppController appController) {

        this.appController = appController;
        this.userName.setText(appController.getUsername());
    }

    @FXML
    public void handleGoBackAction() {
        appController.switchScene(appController.mainPage);
    }
}

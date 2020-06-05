package pl.edu.agh.languagelearningclient.controllers;

import javafx.scene.control.Alert;

public class PageController {

    AppController appController;

    public void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showInfoMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void initialize(AppController appController) {
        this.appController = appController;
    }
}

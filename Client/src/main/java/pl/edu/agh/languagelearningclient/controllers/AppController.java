package pl.edu.agh.languagelearningclient.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AppController {

    private Stage primaryStage;

    private Scene mainPage;
    private Scene loginPage;
    private Scene registerPage;

    public AppController(Stage stage){
        this.primaryStage = stage;
    }

    public void goBackToFirstWindow() {
        primaryStage.setScene(mainPage);
        primaryStage.show();
    }

    public void initialize() throws IOException {

        primaryStage.setTitle("Main page");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AppController.class.getResource("/views/MainPage.fxml"));
        AnchorPane layout = loader.load();

        MainPageController controller = loader.getController();
        controller.initialize(this);
        mainPage = new Scene(layout);
        primaryStage.setScene(mainPage);
        primaryStage.show();
    }

    public void initLoginScene() throws IOException {

        primaryStage.setTitle("Login");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AppController.class.getResource("/views/LoginPage.fxml"));
        AnchorPane layout = loader.load();

        LoginController controller = loader.getController();
        controller.initialize(this);
        loginPage = new Scene(layout);
        primaryStage.setScene(loginPage);
        primaryStage.show();
    }

    public void initRegisterScene() throws IOException {
        primaryStage.setTitle("Register");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AppController.class.getResource("/views/RegisterPage.fxml"));
        AnchorPane layout = loader.load();

        RegisterController controller = loader.getController();
        controller.initialize(this);
        registerPage = new Scene(layout);
        primaryStage.setScene(registerPage);
        primaryStage.show();
    }
}

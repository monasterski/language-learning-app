package pl.edu.agh.languagelearningclient;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.edu.agh.languagelearningclient.controllers.AppController;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        AppController appController = new AppController(primaryStage);
        appController.initMainPageScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
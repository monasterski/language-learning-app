package pl.edu.agh.languagelearningclient;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.edu.agh.languagelearningclient.controllers.AppController;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;

    private AppController appController;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.appController = new AppController(primaryStage);
        this.appController.initialize();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
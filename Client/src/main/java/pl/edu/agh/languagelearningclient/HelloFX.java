package pl.edu.agh.languagelearningclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloFX extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Hello world");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloFX.class.getResource("/views/Hello.fxml"));
        AnchorPane layout = loader.load();

        Scene helloScene = new Scene(layout);
        primaryStage.setScene(helloScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
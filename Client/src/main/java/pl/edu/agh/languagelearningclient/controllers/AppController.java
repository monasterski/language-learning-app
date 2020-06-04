package pl.edu.agh.languagelearningclient.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AppController {

    private String sessionID;

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    private final Stage primaryStage;

    public Scene mainPage;

    public Scene loginPage;



    public Scene registerPage;
    public Scene userCockpitPage;


    private String username;
    public String getUsername() { return username;}
    public void setUsername(String username) {this.username = username; }

    public AppController(Stage stage){
        this.primaryStage = stage;
    }

    public void initMainPageScene() throws IOException {
        AnchorPane layout = initScene("Main page","/views/MainPage.fxml",new MainPageController());
        this.mainPage = new Scene(layout);
        primaryStage.setScene(this.mainPage);
        primaryStage.show();
    }

    public void initLoginPageScene() throws IOException {
        AnchorPane layout = initScene("Login","/views/LoginPage.fxml",new LoginPageController());
        this.loginPage = new Scene(layout);
        primaryStage.setScene(this.loginPage);
        primaryStage.show();
    }

    public void initRegisterPageScene() throws IOException {
        AnchorPane layout = initScene("Login","/views/RegisterPage.fxml",new RegisterPageController());
        this.registerPage = new Scene(layout);
        primaryStage.setScene(this.registerPage);
        primaryStage.show();
    }

    public void initUserCockpitScene() throws IOException {
        AnchorPane layout = initScene("Cockpit","/views/UserCockpit.fxml",new UserCockpitController());
        this.userCockpitPage = new Scene(layout);
        primaryStage.setScene(this.userCockpitPage);
        primaryStage.show();
    }

    public void switchScene(Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public AnchorPane initScene(String sceneTitle, String viewResourcePath, PageController controller) throws IOException {

        primaryStage.setTitle(sceneTitle);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AppController.class.getResource(viewResourcePath));
        AnchorPane layout = loader.load();
        controller = loader.getController();
        controller.initialize(this);
        return layout;
    }
}

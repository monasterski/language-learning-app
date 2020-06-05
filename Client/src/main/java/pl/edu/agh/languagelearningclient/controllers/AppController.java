package pl.edu.agh.languagelearningclient.controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.languagelearningclient.AppProperties;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

public class AppController extends PageController{

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
    public Scene quizPage;
    public Scene revisionPage;


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

    public void initQuizScene() throws IOException {
        AnchorPane layout = initScene("Quiz","/views/QuizPage.fxml",new QuizController());
        this.quizPage = new Scene(layout);
        primaryStage.setScene(this.quizPage);
        primaryStage.show();
    }

    public void initRevisionScene() throws IOException {
        AnchorPane layout = initScene("Revision","/views/RevisionPage.fxml",new RevisionPageController());
        this.revisionPage = new Scene(layout);
        primaryStage.setScene(this.revisionPage);
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

    public void initChoiceWordToCategoryScene(Set<String> wordsWithoutCategory, String categoryName){
        primaryStage.setTitle("Slowa do kategorii");
        VBox vBox = new VBox();
        Button button1 = new Button("Wyślij");
        Button button2 = new Button("Powrót");

        vBox.setAlignment(Pos.CENTER);
        button1.setAlignment(Pos.CENTER);
        button2.setAlignment(Pos.CENTER);

        ArrayList<CheckBox> checkBoxes = wordsWithoutCategory.stream().map(CheckBox::new).collect(Collectors.toCollection(ArrayList::new));
        vBox.getChildren().addAll(checkBoxes);
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @SneakyThrows
            @Override
            public void handle(ActionEvent actionEvent) {
                sendWordToChangeCategory(checkBoxes, categoryName);
                initUserCockpitScene();
            }
        });
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @SneakyThrows
            @Override
            public void handle(ActionEvent actionEvent) {
                initUserCockpitScene();
            }
        });

        vBox.getChildren().add(button1);
        vBox.getChildren().add(button2);


        Scene scene = new Scene(vBox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    private void sendWordToChangeCategory(ArrayList<CheckBox> checkBoxes, String categoryName) throws URISyntaxException {
        Map<String, List<String>> wordToChange = new HashMap<>();
        ArrayList<String> words = new ArrayList<>();
        for (CheckBox check: checkBoxes) {
            if(check.isSelected()){
                words.add(check.getText());
            }
        }
        if(words.size() == 0){
            return;
        }
        wordToChange.put(categoryName, words);
        String URL = AppProperties.SERVER_URL;
        URL = URL + "category/word";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", getSessionID());
        RequestEntity< Map<String, List<String>>> request = RequestEntity
                .post(new URI(URL))
                .headers(headers)
                .body(wordToChange);
        ResponseEntity<String> response;
        RestTemplate restTemplate = new RestTemplate();
        response = restTemplate.exchange(request, String.class);
        if(response.getStatusCode() == HttpStatus.OK){
            showInfoMessage("Prawidlowo dodano slowa");
        }else{
            showInfoMessage("Blad przy dodaniu slow");
        }
    }
}

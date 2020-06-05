package pl.edu.agh.languagelearningclient.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.languagelearningclient.AppProperties;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizController extends PageController{

    AppController appController;

    List<String> wordsToTranslate = new ArrayList<>();
    Map<String, String> answers = new HashMap<>();
    int currWordIndx = 0;

    @FXML
    public Button startQuizButton;

    @FXML
    Label currentWord;

    @FXML
    ComboBox<String> categoryChooser;

    @FXML
    Button submitButton;

    @FXML
    TextField userTranslation;


    public void handleStartQuizAction() {
        String chosenCategory = categoryChooser.getValue();
        String URL = AppProperties.SERVER_URL;
        URL = URL + "word?category="+chosenCategory;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", appController.getSessionID());
        URI uri = null;
        try {
            uri = new URI(URL);
        }
        catch (URISyntaxException e){
            e.printStackTrace();
        }
        RequestEntity<Void> request = RequestEntity
                .get(uri)
                .headers(headers)
                .build();
        ResponseEntity<Map> response = restTemplate.exchange(request, Map.class);
        Map<String, List<String>> respBody = response.getBody();
        wordsToTranslate.addAll(respBody.keySet());
        currentWord.setText(wordsToTranslate.get(currWordIndx));
    }

    public void handleSubmitWordAction() {
        answers.put(wordsToTranslate.get(currWordIndx), userTranslation.getText());
        if(currWordIndx == wordsToTranslate.size()-1){
            String URL = AppProperties.SERVER_URL;
            URL = URL + "quiz";
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cookie", appController.getSessionID());
            URI uri = null;
            try {
                uri = new URI(URL);
            }
            catch (URISyntaxException e){
                e.printStackTrace();
            }
            RequestEntity<Map<String,String>> request = RequestEntity
                    .post(uri)
                    .headers(headers)
                    .body(answers);
            ResponseEntity<String> response = restTemplate.exchange(request, String.class);
            String responseBody = response.getBody();
            showInfoMessage("Koniec. Wynik: "+ responseBody.substring(responseBody.indexOf(':')));
            appController.switchScene(appController.userCockpitPage);
            return;
        }
        currWordIndx++;
        currentWord.setText(wordsToTranslate.get(currWordIndx));
        userTranslation.clear();
    }

    public void initialize(AppController appController) {

        this.appController = appController;
        List<String> categories = new ArrayList<>();
        categories.add("all");

        String URL = AppProperties.SERVER_URL;
        URL = URL + "category";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", appController.getSessionID());
        URI uri = null;
        try {
            uri = new URI(URL);
        }
        catch (URISyntaxException e){
            e.printStackTrace();
        }
        RequestEntity<Void> request = RequestEntity
                .get(uri)
                .headers(headers)
                .build();
        ResponseEntity<List> response = restTemplate.exchange(request, List.class);
        List<String> respBody = response.getBody();
        if(respBody != null) {
            respBody.forEach(category -> categories.add(category));
        }
        ObservableList<String> options =
                FXCollections.observableArrayList(categories);
        this.categoryChooser.setItems(options);

    }
}

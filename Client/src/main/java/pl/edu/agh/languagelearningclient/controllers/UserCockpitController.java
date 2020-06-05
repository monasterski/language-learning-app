package pl.edu.agh.languagelearningclient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import pl.edu.agh.languagelearningclient.AppProperties;


import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserCockpitController extends PageController {

    @FXML
    Label userName;
    @FXML
    Button goBackButton;

    @FXML
    Button addWordButton;

    @FXML
    Button quizButton;

    @FXML
    Button addCategoryButton;

    @FXML
    Button revisionButton;

    public void initialize(AppController appController) {

        this.appController = appController;
        this.userName.setText(appController.getUsername());
    }

    @FXML
    public void handleGoBackAction() {
        appController.switchScene(appController.mainPage);
    }

    @FXML
    public void handleAddWordAction() throws URISyntaxException {

        TextInputDialog dialog = new TextInputDialog("słowo");
        dialog.setTitle("Dodawanie słowa");
        dialog.setHeaderText("Słowo, które należy przetłumaczyć");
        dialog.setContentText("Proszę podać słowo");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            String URL = AppProperties.SERVER_URL;
            URL = URL + "word";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cookie", appController.getSessionID());
            RequestEntity<String> request = RequestEntity
                    .post(new URI(URL))
                    .headers(headers)
                    .body(result.get());
            ResponseEntity<String> response;
            RestTemplate restTemplate = new RestTemplate();
            try {
                response = restTemplate.exchange(request, String.class);
                String responseString = response.getBody();
                showInfoMessage(responseString.substring(responseString.indexOf('[')+1, responseString.indexOf(']')));
            }
            catch (HttpClientErrorException e) {
                showErrorMessage("Słowo już zostało wcześniej dodane");
            }
        }
        else {
            showInfoMessage("Nie wpisałeś żadnego słowa");
            return;
        }
    }

    @FXML
    public void handleStartQuizAction() throws IOException {
        appController.initQuizScene();
    }

    @FXML
    public void handleAddCategoryAction() throws URISyntaxException {

        TextInputDialog dialog = new TextInputDialog("kategoria");
        dialog.setTitle("Dodawanie Kategorii");
        dialog.setHeaderText("Kategoriam która należy dodać");
        dialog.setContentText("Proszę podać nazwę kategorii");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            String URL = AppProperties.SERVER_URL;
            URL = URL + "category";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cookie", appController.getSessionID());
            RequestEntity<String> request = RequestEntity
                    .post(new URI(URL))
                    .headers(headers)
                    .body(result.get());
            RestTemplate restTemplate = new RestTemplate();
            try {
                restTemplate.exchange(request, String.class);
                showInfoMessage("Pomyślnie dodano kategorię");
            }
            catch (HttpClientErrorException e) {
                showErrorMessage("Podana kategoria istnieje");
                return;
            }
            String URLNext = AppProperties.SERVER_URL;
            URLNext = URLNext + "/word?category=none";
            RequestEntity<Void> requestNext = RequestEntity
                    .get(new URI(URLNext)).headers(headers).build();
            ResponseEntity<Map<String, List<String>>> responseNext;
            RestTemplate restTemplateNext = new RestTemplate();
            ParameterizedTypeReference<Map<String, List<String>>> resultType = new ParameterizedTypeReference<>(){};
            responseNext = restTemplateNext.exchange(requestNext, resultType);
            if(responseNext.getBody() != null){
                appController.initChoiceWordToCategoryScene(responseNext.getBody().keySet(), result.get());
            }else{
                showInfoMessage("Brak nowych slow do przyporzadkowania kategorii");
            }
        }
        else {
            showInfoMessage("Nie wpisałeś żadnego słowa");
        }
    }

    @FXML
    public void handleRevisionAction() throws IOException {
        appController.initRevisionScene();
    }

}

package pl.edu.agh.languagelearningclient.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.languagelearningclient.AppProperties;
import pl.edu.agh.languagelearningclient.model.WordWithTranslation;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RevisionPageController extends PageController {
    AppController appController;

    ArrayList<WordWithTranslation> revision = new ArrayList<>();
    ObservableList<WordWithTranslation> data;

    @FXML
    public Button startRevisionButton;


    @FXML
    ComboBox<String> categoryChooser;

    @FXML
    TableView revisionTable;



    public void handleStartRevisionAction() {
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
        if(respBody == null){
            showErrorMessage("W wybranej kategorii nie ma slow");
            return;
        }else{
            for (String key: respBody.keySet()) {
                WordWithTranslation wordWithTranslation = new WordWithTranslation(key);
                int counter = 1;
                for (String translation: respBody.get(key)) {
                    if(counter==1){
                        wordWithTranslation.setFirst(translation);
                    }else if(counter == 2){
                        wordWithTranslation.setSecond(translation);
                    }else{
                        wordWithTranslation.setThird(translation);
                    }
                    counter++;
                }
                revision.add(wordWithTranslation);
            }
        }

        addDataToTableView();
    }


    public void initialize(AppController appController) {

        this.appController = appController;
        initTableView();
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

    @FXML
    public void handleGoBackAction() {
        appController.switchScene(appController.userCockpitPage);
    }


    private void initTableView(){


        TableColumn englishWord = new TableColumn("Word");
        TableColumn translation1 = new TableColumn("First");
        TableColumn translation2 = new TableColumn("Second");
        TableColumn translation3 = new TableColumn("Thrid");
        englishWord.setMinWidth(100);
        translation1.setMinWidth(100);
        translation2.setMinWidth(100);
        translation3.setMinWidth(100);

        revisionTable.getColumns().addAll(englishWord, translation1, translation2, translation3);

        englishWord.setCellValueFactory(new PropertyValueFactory<WordWithTranslation, String>("word"));
        translation1.setCellValueFactory(new PropertyValueFactory<WordWithTranslation, String>("first"));
        translation2.setCellValueFactory(new PropertyValueFactory<WordWithTranslation, String>("second"));
        translation3.setCellValueFactory(new PropertyValueFactory<WordWithTranslation, String>("third"));





    }

    private void addDataToTableView(){

        data = FXCollections.emptyObservableList();
        data = FXCollections.observableArrayList(revision);
        revisionTable.setItems(data);

    }
}

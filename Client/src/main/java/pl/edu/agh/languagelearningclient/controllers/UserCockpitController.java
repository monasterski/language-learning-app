package pl.edu.agh.languagelearningclient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.languagelearningclient.AppProperties;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

public class UserCockpitController extends PageController {

    @FXML
    Label userName;
    @FXML
    Button goBackButton;

    @FXML
    Button addWordButton;

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
            response = restTemplate.exchange(request, String.class);
            showInfoMessage("Otrzymana odpowiedz: " + response.getBody());


        }
        else {
            showInfoMessage("Nie wpisałeś żadnego słowa");
            return;
        }
    }


}

package pl.edu.agh.languagelearningclient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.languagelearningclient.AppProperties;
import pl.edu.agh.languagelearningclient.model.User;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class LoginController {


    AppController appController;

    @FXML
    private TextField loginText;

    @FXML
    private TextField passwordText;

    @FXML
    Button authorizeButton;

    public void initialize(AppController appController) {
        this.appController = appController;
    }

    @FXML
    public Button goBackButton;

    @FXML
    public void handleLoginAction() throws URISyntaxException {

        String login = loginText.getText();
        String password = passwordText.getText();
        User user = new User(login, password);

        String URL = AppProperties.SERVER_URL;
        URL = URL + "user/login";
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
        RequestEntity<User> request = RequestEntity
                .post(new URI(URL))
                .contentType(MediaType.APPLICATION_JSON)
                .body(user);
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(request, String.class);
        }
        catch (HttpClientErrorException errorException){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Bad credentials");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText("User logged: app context: " + response.getBody());
        alert.showAndWait();
    }

    @FXML
    public void handleGoBackAction() {
        appController.goBackToFirstWindow();
    }

}

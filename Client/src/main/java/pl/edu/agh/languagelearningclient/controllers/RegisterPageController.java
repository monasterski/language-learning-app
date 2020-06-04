package pl.edu.agh.languagelearningclient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import pl.edu.agh.languagelearningclient.AppProperties;
import org.springframework.http.RequestEntity;
import pl.edu.agh.languagelearningclient.model.User;

import java.net.URI;
import java.net.URISyntaxException;

public class RegisterPageController extends PageController {

    AppController appController;

    @FXML
    private TextField loginText;

    @FXML
    private TextField passwordText;

    @FXML
    Button authorizeButton;

    @FXML
    Button goBackButton;

    public void initialize(AppController appController) {
        this.appController = appController;
    }

    @FXML
    public void handleRegisterAction() throws URISyntaxException {

        String login = loginText.getText();
        String password = passwordText.getText();
        User user = new User(login, password);

        String URL = AppProperties.SERVER_URL;
        URL = URL + "user/register";
        RequestEntity<User> request = RequestEntity
                .post(new URI(URL))
                .contentType(MediaType.APPLICATION_JSON)
                .body(user);
        try {
            ResponseEntity<String> response = sendRequest(request);
        }
        catch (HttpClientErrorException errorException) {
            showErrorMessage("User already exists");
            return;
        }
        showInfoMessage("User registered");
    }

    @FXML
    public void handleGoBackAction() {
        appController.switchScene(appController.mainPage);
    }
}

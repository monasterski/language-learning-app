package pl.edu.agh.languagelearningclient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import pl.edu.agh.languagelearningclient.AppProperties;
import pl.edu.agh.languagelearningclient.model.User;

import java.net.URI;
import java.net.URISyntaxException;

public class LoginController extends AbstractController {


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
        RequestEntity<User> request = RequestEntity
                .post(new URI(URL))
                .contentType(MediaType.APPLICATION_JSON)
                .body(user);
        ResponseEntity<String> response;
        try {
            response = sendRequest(request);
        }
        catch (HttpClientErrorException errorException){
            showErrorMessage("Bad credentials");
            return;
        }
        showInfoMessage("User logged: app context: " + response.getBody());
    }

    @FXML
    public void handleGoBackAction() {
        appController.goBackToFirstWindow();
    }

}

package pl.edu.agh.languagelearningclient.controllers;

import javafx.scene.control.Alert;

public class PageController {

    AppController appController;

//    public ResponseEntity<String> sendRequest(RequestEntity<?> requestEntity) {
//
//        RestTemplate restTemplate = new RestTemplate();
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//        messageConverters.add(new MappingJackson2HttpMessageConverter());
//        messageConverters.add(new StringHttpMessageConverter());
//        restTemplate.setMessageConverters(messageConverters);
//        return restTemplate.exchange(requestEntity, String.class);
//    }

    public void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showInfoMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void initialize(AppController appController) {
        this.appController = appController;
    }
}

package pl.edu.agh.languagelearningclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @ToString
public class User {
    private String login;
    private String password;
}

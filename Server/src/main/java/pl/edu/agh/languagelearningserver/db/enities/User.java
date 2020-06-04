package pl.edu.agh.languagelearningserver.db.enities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="Users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;

    public User() {}

    public User(String login, String password) {
        this.username = login;
        this.password = password;
    }

}

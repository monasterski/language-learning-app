package pl.edu.agh.languagelearningserver.beans;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.languagelearningserver.db.enities.User;
import pl.edu.agh.languagelearningserver.db.repositories.UserRepository;

public class AuthorizationService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(String login) {
        return userRepository.findByUsername(login);
    }

    public void registerUser(User user) {
        userRepository.save(user);
    }

    public boolean isUserRegistered(String username) {
        return userRepository.existsByUsername(username);
    }
}
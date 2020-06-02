package pl.edu.agh.languagelearningserver.controllers.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.languagelearningserver.db.enities.User;
import pl.edu.agh.languagelearningserver.db.repositories.UserRepository;

public class ApplicationContext {

    @Autowired
    private UserRepository userRepository;

    private User user;

    public User getUser() {
        return user;
    }

    public void initContext(User user){
        this.user = user;
    }
}

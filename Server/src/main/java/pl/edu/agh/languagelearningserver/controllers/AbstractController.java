package pl.edu.agh.languagelearningserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.languagelearningserver.controllers.authorization.ApplicationContext;

public class AbstractController {

    @Autowired
    private ApplicationContext applicationContext;

    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}

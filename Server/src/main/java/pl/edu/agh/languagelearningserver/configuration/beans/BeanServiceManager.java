package pl.edu.agh.languagelearningserver.configuration.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import pl.edu.agh.languagelearningserver.beans.AuthorizationService;
import pl.edu.agh.languagelearningserver.controllers.authorization.ApplicationContext;

@Configuration
public class BeanServiceManager {
    @Bean
    public AuthorizationService authorizationService(){
        return new AuthorizationService();
    }

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ApplicationContext applicationContext() {
        return new ApplicationContext();
    }
}

package pl.edu.agh.languagelearningserver.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.edu.agh.languagelearningserver.beans.AuthorizationService;
import pl.edu.agh.languagelearningserver.db.enities.User;

public class UserLoginService implements UserDetailsService {

    @Autowired
    private AuthorizationService authorizationService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authorizationService.getUser(username);
        if(user == null)
            throw new UsernameNotFoundException(username);
        return new UserDetails(user);
    }

}

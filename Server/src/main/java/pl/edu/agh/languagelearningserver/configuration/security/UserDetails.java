package pl.edu.agh.languagelearningserver.configuration.security;

import org.springframework.security.core.GrantedAuthority;
import pl.edu.agh.languagelearningserver.db.enities.User;

import java.util.Collection;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private User user;

    UserDetails(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

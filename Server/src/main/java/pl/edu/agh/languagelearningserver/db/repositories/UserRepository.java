package pl.edu.agh.languagelearningserver.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pl.edu.agh.languagelearningserver.db.enities.User;

import java.util.List;

@Component
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUsername(String login);
    User findByUsername(String login);
}

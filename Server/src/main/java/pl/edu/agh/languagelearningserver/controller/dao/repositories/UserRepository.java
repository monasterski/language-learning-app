package pl.edu.agh.languagelearningserver.controller.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.languagelearningserver.controller.dao.enities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

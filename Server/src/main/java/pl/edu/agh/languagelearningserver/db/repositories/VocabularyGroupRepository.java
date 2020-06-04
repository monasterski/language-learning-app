package pl.edu.agh.languagelearningserver.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.languagelearningserver.db.enities.User;
import pl.edu.agh.languagelearningserver.db.enities.VocabularyGroup;

import java.util.List;

@Repository
public interface VocabularyGroupRepository extends JpaRepository<VocabularyGroup, Long> {
    VocabularyGroup findVocabularyGroupByNameAndUser(String name, User user);
    List<VocabularyGroup> findVocabularyGroupsByUser(User user);
    Boolean existsVocabularyGroupByNameAndUser(String name, User user);
}

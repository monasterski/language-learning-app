package pl.edu.agh.languagelearningserver.controller.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.languagelearningserver.controller.dao.enities.UserVocabulary;
import pl.edu.agh.languagelearningserver.controller.dao.enities.ids.UserVocabularyId;

@Repository
public interface UserVocabularyRepository extends JpaRepository<UserVocabulary, UserVocabularyId> {
}

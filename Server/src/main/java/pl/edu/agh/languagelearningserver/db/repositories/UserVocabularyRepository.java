package pl.edu.agh.languagelearningserver.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.languagelearningserver.db.enities.UserVocabulary;
import pl.edu.agh.languagelearningserver.db.enities.ids.UserVocabularyId;

@Repository
public interface UserVocabularyRepository extends JpaRepository<UserVocabulary, UserVocabularyId> {
}

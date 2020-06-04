package pl.edu.agh.languagelearningserver.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.languagelearningserver.db.enities.EnglishWord;
import pl.edu.agh.languagelearningserver.db.enities.User;
import pl.edu.agh.languagelearningserver.db.enities.UserVocabulary;
import pl.edu.agh.languagelearningserver.db.enities.VocabularyGroup;
import pl.edu.agh.languagelearningserver.db.enities.ids.UserVocabularyId;

import java.util.List;
import java.util.Set;

@Repository
public interface UserVocabularyRepository extends JpaRepository<UserVocabulary, UserVocabularyId> {
    Boolean existsByUserAndVocabularyGroup(User user, VocabularyGroup vocabularyGroup);
    Boolean existsByUserAndEnglishWord(User user, EnglishWord englishWord);
    List<UserVocabulary> findUserVocabulariesByUserAndVocabularyGroup(User user, VocabularyGroup vocabularyGroup);
    List<UserVocabulary> findUserVocabulariesByUser(User user);
    List<UserVocabulary> findUserVocabulariesByUserAndEnglishWordIn(User user, Set<EnglishWord> englishWords);

}
